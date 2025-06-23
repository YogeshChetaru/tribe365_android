package com.chetaru.tribe365_new.UI.Know;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.HomeBelief;
import com.chetaru.tribe365_new.API.Models.report.BeliefCount;
import com.chetaru.tribe365_new.API.Models.report.EngagementIndexCount;
import com.chetaru.tribe365_new.API.Models.report.IndividualReport;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.IndividualReport.Ad_IndividualKudosReport;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Act_Individual_Report extends BaseActivity {

    /*********************** View Initialization *****************/


    /********** for Sentiment ************/

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sentiment_card_view)
    CardView sentiment_card_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_org_title)
    TextView tvOrgTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.org_image_face)
    ImageView orgImageFace;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_org_value)
    TextView tvOrgValue;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_team_title)
    TextView tvTeamTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.team_image_face)
    ImageView teamImageFace;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_team_value)
    TextView tvTeamValue;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_own_title)
    TextView tvOwnTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.own_face_image)
    ImageView ownFaceImage;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_own_value)
    TextView tvOwnValue;
    /*************************/
    /****** Kudos count Initialize *********/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.kudos_count_card_view)
    CardView kudos_count_card_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.kudos_report_count_rv)
    RecyclerView kudos_count_rv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.no_data_tv)
    TextView no_data_tv;

    /********** for Engagement ************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_org_eng_title)
    TextView tvOrgEngTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.org_eng_image_face)
    ImageView orgEngImageFace;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_org_eng_value)
    TextView tvOrgEngValue;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.org_eng_value_diff_tv)
    TextView org_eng_value_diff_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_team_eng_title)
    TextView tvTeamEngTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.team_eng_image_face)
    ImageView teamEngImageFace;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_team_eng_value)
    TextView tvTeamEngValue;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.team_eng_value_diff_tv)
    TextView team_eng_value_diff_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_own_eng_title)
    TextView tvOwnEngTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.own_eng_face_image)
    ImageView ownEngFaceImage;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_own_eng_value)
    TextView tvOwnEngValue;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.own_eng_value_diff_tv)
    TextView own_eng_value_diff_tv;
    /*************************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_offload_count)
    TextView tvOffloadCount;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView iv_top_companylogo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sp_month_selection)
    Spinner sp_month_filter;


    /************************** Initialize variable and Adapter ***********/
    /************* get Default array for selection *************/
    //String[] selection = { "Day Wise" ,"Week Wise","Month Wise" };
    List<String> selection;
    Ad_IndividualKudosReport adapter;
    List<HomeBelief> kudosList;
    SessionParam sessionParam;
    String orgId="",type="Month";
    Utility utility;
    BaseRequest baseRequest;
    IndividualReport individualReport;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    List<BeliefCount> orgDotKudos;
    List<BeliefCount> departDotKudos;
    List<BeliefCount> userDotKudos;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_individual_report);
        ButterKnife.bind(this);
        kudosList= new ArrayList<>();
        selection=  new ArrayList<>();
        orgDotKudos=  new ArrayList<>();
        departDotKudos=  new ArrayList<>();
        userDotKudos=  new ArrayList<>();
        sessionParam = new SessionParam(mContext);
        utility= new Utility();

        selection.add("Day");
        selection.add("Week");
        selection.add("Month");


        try {

            getSessionParam();
            getMonthFilter();

            initRecyclerView();
            sp_month_filter.setSelection(2);
        }catch (Exception e){
            e.printStackTrace();
        }

       /* sentiment_card_view.setOnClickListener(v->{
            Intent intent=new Intent(mContext,Act_SentimentIndex_Calendar.class);
            startActivity(intent);
        });*/

    }

    private void initRecyclerView(){

        try {
            /*****
             * "id": 132,
             * "name": "Proactive",
             * "valueUrl": "",
             * "valueDesc": "Chetaruvians do not find reasons to stop doing a task. Chetaruvians find ways to generate a positive from a negative no matter what adversity they face.",
             * "valueId": "32",
             * "beliefId": 28,
             * "dotId": 2,
             * ******/


          /*  HomeBelief belief1= new HomeBelief();
            belief1.setId(132);
            belief1.setName("Proactive");
            belief1.setDotId(2);
            belief1.setBeliefId(28);
            kudosList.add(belief1);

            HomeBelief belief2= new HomeBelief();
            belief2.setId(133);
            belief2.setName("clear");
            belief2.setDotId(2);
            belief2.setBeliefId(28);
            kudosList.add(belief2);

            HomeBelief belief3= new HomeBelief();
            belief3.setId(134);
            belief3.setName("Accurate");
            belief3.setDotId(2);
            belief3.setBeliefId(28);
            kudosList.add(belief3);

            HomeBelief belief4= new HomeBelief();
            belief4.setId(135);
            belief4.setName("collaborative");
            belief4.setDotId(2);
            belief4.setBeliefId(29);
            kudosList.add(belief4);

            HomeBelief belief5= new HomeBelief();
            belief5.setId(136);
            belief5.setName("Open");
            belief5.setDotId(2);
            belief5.setBeliefId(29);
            kudosList.add(belief5);

            HomeBelief belief6= new HomeBelief();
            belief6.setId(137);
            belief6.setName("Goal Focused");
            belief6.setDotId(2);
            belief6.setBeliefId(29);
            kudosList.add(belief6);*/

            no_data_tv.setVisibility(View.GONE);
            kudos_count_rv.setVisibility(View.VISIBLE);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            kudos_count_rv.setLayoutManager(layoutManager);
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(Act_Individual_Report.this, R.dimen.item_offset_small);
            kudos_count_rv.addItemDecoration(itemDecoration);

            kudos_count_rv.setNestedScrollingEnabled(false);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void setKudosRecyclerView(List<BeliefCount> orgDotKudos,List<BeliefCount> departDotKudos,List<BeliefCount> userDotKudos){
        adapter= new Ad_IndividualKudosReport(orgDotKudos,departDotKudos,userDotKudos,mContext);
        kudos_count_rv.setAdapter(adapter);

    }
    /*********** getSessionParam **************/
    private void getSessionParam() {
        try {
            if (orgId.equals("")) {
                orgId = sessionParam.orgId;
            }
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /******************* get spinner for day,Week,Month selection **************/
    public void getMonthFilter(){
        if (selection != null){
            ArrayAdapter<String> monthAdapter= new ArrayAdapter<>(Act_Individual_Report.this, R.layout.spinner_month_item, selection);
            monthAdapter.setDropDownViewResource(R.layout.spinner_month_item);
            sp_month_filter.setAdapter(monthAdapter);
            sp_month_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(mContext,"Item selected "+ selection.get(position), Toast.LENGTH_LONG).show();
                    type=selection.get(position).toString().trim();
                    getIndividualReport(type);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //do nothing
                }
            });
        }
    }



    private void SentimentDataSet(IndividualReport individualReport) {
        //OrganizationData set
        Double orgValue= Double.valueOf(individualReport.getOrgHIPercent());
        setSentimentData(orgValue,tvOrgValue,orgImageFace);
        Double teamValue= Double.valueOf(individualReport.getTeamHIPercent());
        setSentimentData(teamValue,tvTeamValue,teamImageFace);
        Double yourValue= Double.valueOf(individualReport.getUserHIPercent());
        setSentimentData(yourValue,tvOwnValue,ownFaceImage);
    }

    private void setSentimentData(Double finalCount,TextView valueText,ImageView imageFace){
        valueText.setText(finalCount + "%");
        if (finalCount >= 0 && finalCount <= 30) {
            imageFace.setImageResource(R.drawable.low);
            valueText.setTextColor(getResources().getColor(R.color.motion_index_low));
        } else if (finalCount >= 31 && finalCount <= 60) {
            imageFace.setImageResource(R.drawable.medium);
            valueText.setTextColor(getResources().getColor(R.color.motion_index_medium));
        } else if (finalCount >= 60) {
            imageFace.setImageResource(R.drawable.smile_green_big);
            valueText.setTextColor(getResources().getColor(R.color.motion_index_high));
        }
    }

    /************* set Engagement Data From Organisation
     * @param engagementData
     * @param differenceData**********/
    @SuppressLint("SetTextI18n")
    private void setEngagementData(Double engagementData, Double differenceData,ImageView faceModeImage, TextView scoreValueTextView ) {
        Double finalCount=engagementData;


        String showEngValue=trimTrailingZeros(String.valueOf(engagementData));
        scoreValueTextView.setText(showEngValue + "");
        if (engagementData!=null) {
            if (finalCount <= 499) {
                faceModeImage.setImageResource(R.drawable.low);
            } else if (finalCount >= 500 && finalCount <= 1099) {
                faceModeImage.setImageResource(R.drawable.medium);
            } else if (finalCount >= 1100) {
                faceModeImage.setImageResource(R.drawable.smile_green_big);
            }
        }

    }
    private static String trimTrailingZeros(String number) {
        if(!number.contains(".")) {
            return number;
        }

        return number.replaceAll("\\.?0*$", "");
    }


    /******************************* APi Section ************************/
    /**************** get Individuals data *****************/
    private void getIndividualReport(String typeValue) {
        //clear previous list data
        orgDotKudos.clear();
        departDotKudos.clear();
        userDotKudos.clear();
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    Gson gson = new Gson();
                    try {
                        individualReport = gson.fromJson(object.toString(), IndividualReport.class);
                        try {
                            /********************* set Sentiment Index data *******************/
                            SentimentDataSet(individualReport);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        try {
                            orgDotKudos= individualReport.getOrgDot();
                            departDotKudos= individualReport.getDepartDot();
                            userDotKudos= individualReport.getUserDot();
                            if (orgDotKudos.size()>0){
                                no_data_tv.setVisibility(View.GONE);
                                kudos_count_card_view.setVisibility(View.VISIBLE);
                                setKudosRecyclerView(orgDotKudos,departDotKudos,userDotKudos);
                            }else{
                                no_data_tv.setVisibility(View.VISIBLE);
                                kudos_count_card_view.setVisibility(View.GONE);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            tvOffloadCount.setText(mContext.getString(R.string.offloads_report)+ " : "+ individualReport.getOffloadCount());

                        }catch (Exception e){
                            tvOffloadCount.setText(mContext.getString(R.string.offloads_report)+ " : "+"0");
                            e.printStackTrace();
                        }
                        try{
                            /*********************** set Engagement Index for Organization *******************/
                            setOrgEngagementIndex(individualReport.getOrgEngagementIndex());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try{
                            /*********************** set Engagement Index for department *******************/
                            setTeamEngagementIndex(individualReport.getDepartEngagementIndex());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try{
                            /*********************** set Engagement Index for User *******************/
                            setUserEngagementIndex(individualReport.getUserEngagementIndex());

                        }catch (Exception e){
                            e.printStackTrace();
                        }




                    }catch (Exception e){
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext, message);
                no_data_tv.setVisibility(View.VISIBLE);
                kudos_count_card_view.setVisibility(View.GONE);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", orgId,
                "type", typeValue,
                "api","1"
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getIndividualReport);


    }

    /*********************** set Engagement Index for User *******************/
    @SuppressLint("SetTextI18n")
    private void setUserEngagementIndex(EngagementIndexCount userEngagementIndex) {
        Double orgData=Double.valueOf(userEngagementIndex.getCurrent());
        Double lastOrgData= Double.valueOf(userEngagementIndex.getLast());
        Double orgDifference= orgData- lastOrgData;
        own_eng_value_diff_tv.setText("("+df.format(orgDifference)+")");
        if (orgDifference >= 0) {
            own_eng_value_diff_tv.setTextColor(mContext.getResources().getColor(R.color.color_green));
        } else {
            own_eng_value_diff_tv.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        setEngagementData(orgData,orgDifference,ownEngFaceImage,tvOwnEngValue);
    }
    /*********************** set Engagement Index for department *******************/
    @SuppressLint("SetTextI18n")
    private void setTeamEngagementIndex(EngagementIndexCount departEngagementIndex) {
        Double orgData=Double.valueOf(departEngagementIndex.getCurrent());
        Double lastOrgData= Double.valueOf(departEngagementIndex.getLast());
        Double orgDifference= orgData- lastOrgData;
        team_eng_value_diff_tv.setText("("+df.format(orgDifference)+")");
        if (orgDifference >= 0) {
            team_eng_value_diff_tv.setTextColor(mContext.getResources().getColor(R.color.color_green));
        } else {
            team_eng_value_diff_tv.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        setEngagementData(orgData,orgDifference,teamEngImageFace,tvTeamEngValue);
    }

    /*********************** set Engagement Index for Organization *******************/
    @SuppressLint("SetTextI18n")
    private void setOrgEngagementIndex(EngagementIndexCount orgEngagementIndex) {
        Double orgData=Double.valueOf(orgEngagementIndex.getCurrent());
        Double lastOrgData= Double.valueOf(orgEngagementIndex.getLast());
        Double orgDifference= orgData- lastOrgData;
        org_eng_value_diff_tv.setText("("+df.format(orgDifference)+")");
        if (orgDifference >= 0) {
            org_eng_value_diff_tv.setTextColor(mContext.getResources().getColor(R.color.color_green));
        } else {
            org_eng_value_diff_tv.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        setEngagementData(orgData,orgDifference,orgEngImageFace,tvOrgEngValue);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_individual_report;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_know;
    }
}