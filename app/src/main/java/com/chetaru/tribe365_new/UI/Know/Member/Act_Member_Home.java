package com.chetaru.tribe365_new.UI.Know.Member;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.HomeBelief;
import com.chetaru.tribe365_new.API.Models.Home.HomeDetailResponse;
import com.chetaru.tribe365_new.API.Models.MemberHome.KnowMemberDetails;
import com.chetaru.tribe365_new.API.Models.MemberHome.KudosCount;
import com.chetaru.tribe365_new.API.Models.MemberHome.PersonalityType;
import com.chetaru.tribe365_new.API.Models.ModelAddActionUser;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_UserListDialog;
import com.chetaru.tribe365_new.Adapter.KnowMember.Ad_Motivational;
import com.chetaru.tribe365_new.Adapter.KnowMember.Ad_TeamRole;
import com.chetaru.tribe365_new.Adapter.KnowMember.Ad_belief_member;
import com.chetaru.tribe365_new.Adapter.KnowMember.Ad_personalityType;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Know.COT.Cot_New_Question;
import com.chetaru.tribe365_new.UI.Know.PersonalityType.Act_Personality_type_list;
import com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation.Act_SOT_Motivation_Question;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.RecyclerItemClickListener;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.chetaru.tribe365_new.utility.customView.SimpleDividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Act_Member_Home extends BaseActivity implements View.OnClickListener{

    BaseRequest baseRequest;
    ArrayList<ModelAddActionUser> modelUserList;
    Utility utility;
    SessionParam sessionParam;
    String orgId="";
    String userId = "";
    KnowMemberDetails memberDetail;
    boolean personaliseDataStatus=false;
    String firstName="",lastName="";
    /*********************** initialize Top layout ***************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView iv_top_companylogo;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_userName)
    TextView tv_userName;
    /********************** first card layout **************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.engagement_face_image)
    ImageView engagement_face_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_engagement_value)
    TextView tv_engagement_value;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_engagement_status)
    TextView tv_engagement_status;
    /************** set belief layout ******************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_belief_list)
    RecyclerView rv_belief_list;
    @BindView(R.id.kudos_count_card_view)
    CardView kudos_count_card_view;
    /************** set personality layout ******************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_personality_list)
    RecyclerView rv_personality_list;
    @BindView(R.id.personality_type_tv_msg)
    TextView personality_type_tv_msg;
    /************** set TeamRole layout ******************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_team_role_view)
    RecyclerView rv_team_role_view;
    @BindView(R.id.team_role_tv_msg)
    TextView team_role_tv_msg;
    /************** set belief layout ******************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_motivational_list)
    RecyclerView rv_motivational_list;
    @BindView(R.id.motivation_tv_msg)
    TextView motivation_tv_msg;

    /************** bottom layout to set user data private*************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_personalise_data_msg)
    TextView personaliseDataMsg;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.allKnowMemberLayout)
    LinearLayout allKnowMemberLayout;


    /********* set Adapter and List **************/
    Ad_belief_member ad_belief_member;
    List<HomeBelief> beliefList=new ArrayList<>();
    List<KudosCount> kudosCountList=new ArrayList<>();

    Ad_personalityType ad_personalityType;
    List<PersonalityType> personalityList=new ArrayList<>();

    Ad_Motivational ad_motivational;
    List<String> motivationalList=new ArrayList<>();

    Ad_TeamRole ad_teamRole;
    List<String> teamRoleList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_member_home);
        ButterKnife.bind(this);
        utility=new Utility();
        sessionParam =new SessionParam(mContext);
        /************** get session manager **************/
        getSessionParam();

        /************* initialize selection ***********/
        init();
        tv_userName.setOnClickListener(this);
        iv_top_companylogo.setOnClickListener(this);
        personality_type_tv_msg.setOnClickListener(this);
        team_role_tv_msg.setOnClickListener(this);
        motivation_tv_msg.setOnClickListener(this);



        /************* Api calling **************/
        api_getUserByType();
        getMemberHome(userId);
    }


    private void init() {
        kudosCountList=new ArrayList<>();
        beliefList=new ArrayList<>();
        personalityList=new ArrayList<>();
        teamRoleList=new ArrayList<>();
        motivationalList=new ArrayList<>();
        /********** get belief recycler View *******/
        LinearLayoutManager beliefLayoutManager = new LinearLayoutManager(mContext);
        rv_belief_list.setLayoutManager(beliefLayoutManager);
        rv_belief_list.setHasFixedSize(true);
        rv_belief_list.addItemDecoration(new SimpleDividerItemDecoration(this));
        rv_belief_list.setNestedScrollingEnabled(false);



    }

    private void setMotivationalData(List<String> motivationalList) {
        /********** get Motivational recycler View *******/
        LinearLayoutManager motivationLayoutManager = new GridLayoutManager(mContext, 2);
      //  ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);

        rv_motivational_list.setLayoutManager(motivationLayoutManager);
        rv_motivational_list.setHasFixedSize(true);
       // rv_motivational_list.addItemDecoration(itemDecoration);
        rv_motivational_list.setNestedScrollingEnabled(false);
        ad_motivational=new Ad_Motivational(motivationalList,mContext);
        rv_motivational_list.setAdapter(ad_motivational);
    }

    private void setTeamRoleDAta(List<String> teamRoleList) {
        /********** get TeamRole recycler View *******/
        LinearLayoutManager TeamRoleLayoutManager = new GridLayoutManager(mContext, 2);
       // ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);

        rv_team_role_view.setLayoutManager(TeamRoleLayoutManager);
        rv_team_role_view.setHasFixedSize(true);
       // rv_team_role_view.addItemDecoration(itemDecoration);
        rv_team_role_view.setNestedScrollingEnabled(false);
        ad_teamRole=new Ad_TeamRole(teamRoleList,mContext);
        rv_team_role_view.setAdapter(ad_teamRole);
    }

    private void setPersonalityData(List<PersonalityType> personalityList) {
        /********** get personality recycler View *******/
        LinearLayoutManager personalityLayoutManager = new GridLayoutManager(mContext, 2);

        rv_personality_list.setLayoutManager(personalityLayoutManager);
        rv_personality_list.setHasFixedSize(true);
        rv_personality_list.setNestedScrollingEnabled(false);
        ad_personalityType=new Ad_personalityType(personalityList,mContext);
        rv_personality_list.setAdapter(ad_personalityType);

    }
    private void setbeliefListDate(List<KudosCount> kudosList) {
        ad_belief_member=new Ad_belief_member(kudosList,mContext);
        rv_belief_list.setAdapter(ad_belief_member);
    }

    /*********** getSessionParam **************/
    private void getSessionParam() {
        try {
            if (orgId.equals("")) {
                orgId = sessionParam.orgId;
            }
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);

            userId=sessionParam.id;
            tv_userName.setText(sessionParam.name + " " + sessionParam.lastName);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (sessionParam.loginVersion==3){
            kudos_count_card_view.setVisibility(View.GONE);
        }else {
            kudos_count_card_view.setVisibility(View.VISIBLE);
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_userName:
                dialogResponsible();
                break;
            case R.id.iv_top_companylogo:
                callHomeAct(mContext);
                break;
            case R.id.personality_type_tv_msg:
                if (userId.equals(sessionParam.id))
                startActivity(new Intent(mContext, Act_Personality_type_list.class));

                break;
            case R.id.team_role_tv_msg:
                if (userId.equals(sessionParam.id))
                startActivity(new Intent(mContext, Cot_New_Question.class));

                break;
            case R.id.motivation_tv_msg:
                if (userId.equals(sessionParam.id))
                startActivity(new Intent(mContext, Act_SOT_Motivation_Question.class));
                break;
        }
    }

    /*pop to select user*/
    /****************** user Selection dialog **********/
    public void dialogResponsible() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tier_list);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final ImageView iv_close = dialog.findViewById(R.id.iv_close);
        final RecyclerView rv_list = dialog.findViewById(R.id.rv_list);
        final TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText("Select Actions");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        rv_list.setAdapter(new Ad_UserListDialog(modelUserList, mContext));

        rv_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_list, new RecyclerItemClickListener.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(View view, int position) {
                tv_userName.setText(modelUserList.get(position).getName() + " " + modelUserList.get(position).getLastName());
                userId = modelUserList.get(position).getId() + "";
                firstName=modelUserList.get(position).getName();
                lastName=modelUserList.get(position).getLastName();

                personaliseDataMsg.setVisibility(View.GONE);
                personaliseDataMsg.setText(modelUserList.get(position).getName() + " " + modelUserList.get(position).getLastName() +" "+ " data is personalised");
                /*********** call Api for get user Report data ********/
                getMemberHome(userId);
                dialog.dismiss();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    /************************** call Api For get user List ************/
    public void api_getUserByType() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    modelUserList = baseRequest.getDataList(jsonArray, ModelAddActionUser.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {

                utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {

                utility.showToast(mContext, message);
            }
        });


        JsonObject object = Functions.getClient().getJsonMapObject("type", "organisation",
                "typeId", sessionParam.orgId

        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getUserByType);
    }
    /************ get User report details Api **********************/
    private void getMemberHome(String userId) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

                try {
                    Gson gson = new Gson();
                   personalityList.clear();
                    teamRoleList.clear();
                    motivationalList.clear();
                    memberDetail = gson.fromJson(object.toString(), KnowMemberDetails.class);
                    if (sessionParam.loginVersion==3)
                    setEngagementScore(memberDetail.getEngagementIndexScore());
                    else
                    setEngagementScoreBasicVersion(memberDetail.getEngagementIndexScore());
                    personaliseDataStatus=memberDetail.getPersonaliseData();
                    personalityList=memberDetail.getPersonalityType();
                    motivationalList=memberDetail.getMotivation();
                    teamRoleList=memberDetail.getTeamRole();

                    //KnowMemberDetails


                    if (!personaliseDataStatus) {
                        allKnowMemberLayout.setVisibility(View.VISIBLE);
                        personaliseDataMsg.setVisibility(View.GONE);
                    } else {
                        if (!userId.equals(sessionParam.id)) {
                            personaliseDataMsg.setText(firstName + " " + lastName+" " + getString(R.string.msg_data_private));
                            allKnowMemberLayout.setVisibility(View.GONE);
                            personaliseDataMsg.setVisibility(View.VISIBLE);
                        } else {
                            allKnowMemberLayout.setVisibility(View.VISIBLE);
                            personaliseDataMsg.setVisibility(View.GONE);
                        }
                    }
                     kudosCountList=memberDetail.getKudosCount();
                    setbeliefListDate(kudosCountList);
                    if (!memberDetail.getPerTypeDetails().equals("")){

                        rv_personality_list.setVisibility(View.GONE);
                        personality_type_tv_msg.setVisibility(View.VISIBLE);
                        personality_type_tv_msg.setText(memberDetail.getPerTypeDetails());

                    }else{
                        personality_type_tv_msg.setVisibility(View.GONE);
                        setPersonalityData(memberDetail.getPersonalityType());
                    }
                    if (!memberDetail.getTeamRoleDetails().equals("")) {
                        rv_team_role_view.setVisibility(View.GONE);
                        team_role_tv_msg.setVisibility(View.VISIBLE);
                        team_role_tv_msg.setText(memberDetail.getTeamRoleDetails());
                    }else {

                        team_role_tv_msg.setVisibility(View.GONE);
                        setTeamRoleDAta(memberDetail.getTeamRole());
                    }
                    if (!memberDetail.getMotivationDetails().equals("")) {
                        motivation_tv_msg.setVisibility(View.VISIBLE);
                        rv_motivational_list.setVisibility(View.GONE);
                        motivation_tv_msg.setText(memberDetail.getMotivationDetails());
                    }else{

                        motivation_tv_msg.setVisibility(View.GONE);
                        setMotivationalData(memberDetail.getMotivation());
                    }

                    if(memberDetail.getPersonalityType().size()>0){
                        rv_personality_list.setVisibility(View.VISIBLE);

                        setPersonalityData(personalityList);
                    }
                    if (memberDetail.getTeamRole().size()>0){
                        rv_team_role_view.setVisibility(View.VISIBLE);
                        setTeamRoleDAta(teamRoleList);
                    }
                    if (memberDetail.getMotivation().size()>0){
                        rv_motivational_list.setVisibility(View.VISIBLE);
                        setMotivationalData(motivationalList);
                    }




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });


        JsonObject object = Functions.getClient().getJsonMapObject(
                "orgId", sessionParam.orgId,
                "userId", userId
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getKnowMemberDetails);
    }
    private static String trimTrailingZeros(String number) {
        if(!number.contains(".")) {
            return number;
        }

        return number.replaceAll("\\.?0*$", "");
    }

    private void setEngagementScore(String engagementIndexScore) {
        Double finalCount= Double.valueOf(engagementIndexScore);
        String showEngValue=trimTrailingZeros(engagementIndexScore);
        tv_engagement_value.setText(showEngValue);
        if ( finalCount <= 499) {
            engagement_face_image.setImageResource(R.drawable.low);
            //eng_tv_score.setText(indexEngScore+"");
            tv_engagement_status.setText("low" + "");
           // tv_engagement_status.setTextColor(getResources().getColor(R.color.motion_index_low));
            tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_low));
        } else if (finalCount >= 500 && finalCount <= 1099) {
            engagement_face_image.setImageResource(R.drawable.medium);
            //tv_index_eng_number.setText(indexEngScore+"");
            tv_engagement_status.setText("medium" + "");
           // tv_engagement_status.setTextColor(getResources().getColor(R.color.motion_index_medium));
            tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_medium));
        } else if (finalCount >= 1100) {
            engagement_face_image.setImageResource(R.drawable.smile_green_big);
            //tv_index_eng_number.setText(indexEngScore+"");
            tv_engagement_status.setText("High" + "");
           // tv_engagement_status.setTextColor(getResources().getColor(R.color.motion_index_high));
            tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_high));
        }
    }
    private void setEngagementScoreBasicVersion(String engagementIndexScore) {
        Double   finalCount= Double.valueOf(engagementIndexScore);
        String showEngValue=trimTrailingZeros(engagementIndexScore);
        tv_engagement_value.setText(showEngValue);
        if ( finalCount <= 499) {
            engagement_face_image.setImageResource(R.drawable.low);
            //eng_tv_score.setText(indexEngScore+"");
            tv_engagement_status.setText("low" + "");
            // tv_engagement_status.setTextColor(getResources().getColor(R.color.motion_index_low));
            tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_low));
        } else if (finalCount >= 500 && finalCount <= 1099) {
            engagement_face_image.setImageResource(R.drawable.medium);
            //tv_index_eng_number.setText(indexEngScore+"");
            tv_engagement_status.setText("medium" + "");
            // tv_engagement_status.setTextColor(getResources().getColor(R.color.motion_index_medium));
            tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_medium));
        } else if (finalCount >= 1100) {
            engagement_face_image.setImageResource(R.drawable.smile_green_big);
            //tv_index_eng_number.setText(indexEngScore+"");
            tv_engagement_status.setText("High" + "");
            // tv_engagement_status.setTextColor(getResources().getColor(R.color.motion_index_high));
            tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_high));
        }
    }



    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_member_home;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_know;
    }


}