package com.chetaru.tribe365_new.UI.HPTM;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.HPTM.ChildCheckList;
import com.chetaru.tribe365_new.API.Models.HPTM.HPTMHomeResponse;
import com.chetaru.tribe365_new.API.Models.HPTM.HPTMLearningCheckList;
import com.chetaru.tribe365_new.API.Models.HPTM.HPTM_Principle;
import com.chetaru.tribe365_new.API.Models.HPTM.PrincipleFirst;
import com.chetaru.tribe365_new.API.Models.HPTM.PrincipleResponse;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.HPTM.Ad_HPTM_Main;
import com.chetaru.tribe365_new.Adapter.HPTM.Ad_watchedList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Home.Act_FreeVersionHome;
import com.chetaru.tribe365_new.UI.Know.Act_Know_Home;
import com.chetaru.tribe365_new.UI.Offloading.Act_IOTHome;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_HPTM_main extends BaseActivity implements View.OnClickListener{

    Utility utility;
    SessionParam sessionParam;
    private BaseRequest baseRequest;

    int flag =1;
    Ad_HPTM_Main hptmAdapter;
    Ad_watchedList watchAdapter;
    List<PrincipleFirst> firstLearningList;
    List<PrincipleFirst> newChildList;
    List<HPTM_Principle> principleList;
    List<HPTM_Principle> reversPrincipleList=new ArrayList<>();
    List<HPTMLearningCheckList> learningCheckLists;
    boolean expand=false;
    String setPrincipleId="";

    /******************* view Initailize ***************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_ll)
    LinearLayout main_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_main_block)
    RecyclerView rv_main_block;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_watch_link)
    RecyclerView rv_watch_link;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.feedback_score_ll)
    LinearLayout feedback_score_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_hptm_score)
    TextView tv_hptm_score;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.principle_imageview)
    ImageView principle_imageview;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.committed_txt)
    TextView committed_txt;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.directed_txt)
    TextView directed_txt;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.selfless_txt)
    TextView selfless_txt;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.honest_txt)
    TextView honest_txt;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.selfless_again_txt)
    TextView selfless_again_txt;

   /* @BindView(R.id.tv_start_tf)
    TextView tv_start_tf;*/
    String backPress="back";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_hptm_main);
        ButterKnife.bind(this);
        utility= new Utility();
        sessionParam= new SessionParam(mContext);
        LinearLayoutManager watch_manager=new LinearLayoutManager(mContext);
        rv_watch_link.setLayoutManager(watch_manager);
        principleList=new ArrayList<>();
        newChildList= new ArrayList<>();
        firstLearningList=new ArrayList<>();
        learningCheckLists= new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        rv_main_block.setLayoutManager(layoutManager);
        rv_main_block.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        rv_main_block.addItemDecoration(itemDecoration);

        /*************** set session Param **********/
        setSessionParam();
        try{
            backPress = getIntent().getStringExtra("backHandel");
        }catch (Exception e){
            e.printStackTrace();
        }

        /************************set Tab selection *****************/
        setLayoutLinear();

        /************* call Api for user Status *************/
        if (!backPress.equals("freeVersion")) {
            getUserStatus();
        }else {
           // tv_start_tf.setVisibility(View.GONE);
           // feedback_score_ll.setVisibility(View.GONE);
        }
        /********************* handle click listener ***************/
       // tv_start_tf.setOnClickListener(this);
        principle_imageview.setOnClickListener(this);
        iv_top_back.setOnClickListener(this);
        tribe365.setOnClickListener(this);
        honest_txt.setOnClickListener(v->{
            flag=1;
            setLayoutLinear();
            if (principleList!=null&& principleList.size()>=3){
                String principleId=String.valueOf(principleList.get(3).getId());
                setPrincipleId=principleId;
                getSelfLearningList(principleId);
            }
        });
        committed_txt.setOnClickListener(v->{
            flag=4;
            setLayoutLinear();
            if (principleList.size()>0){
                String principleId= String.valueOf(principleList.get(0).getId());
                setPrincipleId=principleId;
                getSelfLearningList(principleId);
            }
        });
        directed_txt.setOnClickListener(v->{
            flag=2;
            setLayoutLinear();
            if (principleList!=null && principleList.size()>=2){
                String principleId=String.valueOf(principleList.get(1).getId());
                setPrincipleId=principleId;
                getSelfLearningList(principleId);
            }

        });
        selfless_txt.setOnClickListener(v->{
            flag=3;
            setLayoutLinear();
            if (principleList!=null && principleList.size()>=3){
                String principleId=String.valueOf(principleList.get(2).getId());
                setPrincipleId=principleId;
                getSelfLearningList(principleId);
            }
        });

        selfless_again_txt.setOnClickListener(v ->{
            flag=5;
            setLayoutLinear();
            if (principleList!=null&& principleList.size()>=4){
                String principleId=String.valueOf(principleList.get(4).getId());
                setPrincipleId=principleId;
                getSelfLearningList(principleId);
            }
        });
        iv_top_back.setOnClickListener(this);


        /********************** call Api for priniciple List ************/
        getPrincipleList();
        //setHPTMPrinciple(principleList);
    }
    /**************************** OnCreate Finish **********************/

    public void setBottomMargin(){
        /*try{
            LinearLayout.LayoutParams params=main_ll.getLayoutParams();
            params.setMargins(0,0,0,50);//left, top, right, bottom
            main_ll.setLayoutParams(params);
        }catch (Exception e){e.printStackTrace();}*/
    }
    /***************** set session data *******************/
    public  void setSessionParam(){
        try {
            if (!sessionParam.organisation_logo.equals("")){
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*********************** set Principle box expand and colleps **************/
    public void setPrincipleExpand(){
        if (!expand){
            expand = true;
            rv_main_block.setVisibility(View.VISIBLE);
            principle_imageview.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_arrow_down_gray));

        }else {
            expand =false;
            rv_main_block.setVisibility(View.GONE);
            principle_imageview.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_arrow_right_black));
        }
    }
    /************************* set Tab selection bg color ***************/
    public void setLayoutLinear(){
        if (flag==1){
            committed_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            committed_txt.setTextColor(getResources().getColor(R.color.textcolor));

            directed_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            directed_txt.setTextColor(getResources().getColor(R.color.textcolor));

            selfless_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            selfless_txt.setTextColor(getResources().getColor(R.color.textcolor));

            honest_txt.setBackground(getResources().getDrawable(R.drawable.team_role_edittext_red));
            honest_txt.setTextColor(getResources().getColor(R.color.white));

            selfless_again_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            selfless_again_txt.setTextColor(getResources().getColor(R.color.textcolor));

        }else if (flag==2){

            committed_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            committed_txt.setTextColor(getResources().getColor(R.color.textcolor));
            directed_txt.setBackground(getResources().getDrawable(R.drawable.team_role_edittext_red));
            directed_txt.setTextColor(getResources().getColor(R.color.white));
            selfless_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            selfless_txt.setTextColor(getResources().getColor(R.color.textcolor));
            honest_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            honest_txt.setTextColor(getResources().getColor(R.color.textcolor));
            selfless_again_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            selfless_again_txt.setTextColor(getResources().getColor(R.color.textcolor));

        }else if (flag==3){
            committed_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            committed_txt.setTextColor(getResources().getColor(R.color.textcolor));
            directed_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            directed_txt.setTextColor(getResources().getColor(R.color.textcolor));
            selfless_txt.setBackground(getResources().getDrawable(R.drawable.team_role_edittext_red));
            selfless_txt.setTextColor(getResources().getColor(R.color.white));
            honest_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            honest_txt.setTextColor(getResources().getColor(R.color.textcolor));
            selfless_again_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            selfless_again_txt.setTextColor(getResources().getColor(R.color.textcolor));

        }else if (flag==4){

            committed_txt.setBackground(getResources().getDrawable(R.drawable.team_role_edittext_red));
            committed_txt.setTextColor(getResources().getColor(R.color.white));
            directed_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            directed_txt.setTextColor(getResources().getColor(R.color.textcolor));
            selfless_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            selfless_txt.setTextColor(getResources().getColor(R.color.textcolor));
            honest_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            honest_txt.setTextColor(getResources().getColor(R.color.textcolor));
            selfless_again_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            selfless_again_txt.setTextColor(getResources().getColor(R.color.textcolor));

        }else if (flag==5){
            committed_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            committed_txt.setTextColor(getResources().getColor(R.color.textcolor));
            directed_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            directed_txt.setTextColor(getResources().getColor(R.color.textcolor));
            selfless_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            selfless_txt.setTextColor(getResources().getColor(R.color.textcolor));
            honest_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            honest_txt.setTextColor(getResources().getColor(R.color.textcolor));
            selfless_again_txt.setBackground(getResources().getDrawable(R.drawable.team_role_edittext_red));
            selfless_again_txt.setTextColor(getResources().getColor(R.color.white));

        }
    }

    /******************** set Principle value on Top***************/
    public void setHPTMPrinciple(List<HPTM_Principle> list){
        /************** first list **************/
        /*HPTM_Principle principle=new HPTM_Principle();
        principle.setDescription("sdasadasdasda");
        principle.setTitle("Committed");
        principle.setId(1);
        list.add(principle);*/
        /***************** sceond list***********/
       /* HPTM_Principle princ=new HPTM_Principle();
        princ.setId(2);
        princ.setDescription("sdadsadsad");
        princ.setTitle("Directed");
        list.add(princ);*/
        /********************* third list *************/
       /* HPTM_Principle listThird=new HPTM_Principle();
        listThird.setId(3);
        listThird.setTitle("Selfless");
        listThird.setDescription("asduu asdsao ashoisoid a");
        list.add(listThird);*/
        /********** forth list *************/
        /*HPTM_Principle listFourth=new HPTM_Principle();
        listFourth.setId(4);
        listFourth.setTitle("Honest");
        listFourth.setDescription("df gdas ahd asda   iuad");
        list.add(listFourth);*/

        rv_main_block.setNestedScrollingEnabled(false);
        rv_main_block.setAdapter(new Ad_HPTM_Main(list,mContext,backPress));

    }

    /************************* set learningList value on Bottom **************/
    public void setWatchList(List<HPTMLearningCheckList> hptmCheckList){
        List<ChildCheckList> newValue= new ArrayList<>();
        List<PrincipleFirst> getValue= new ArrayList<>();


        rv_watch_link.setNestedScrollingEnabled(true);
        rv_watch_link.setAdapter(new Ad_watchedList(hptmCheckList, mContext, new Ad_watchedList.watchListListener() {
            @Override
            public void radioOptionClick(PrincipleFirst list) {
                boolean flag=false;
                flag= !list.getUserReadChecklist();
                changeReadStatus(list.getChecklistId(),flag);
           }
        }));
    }

    @SuppressLint("NewApi")
    public List<PrincipleFirst> getHierarchicalList(final List<PrincipleFirst> originalList) {
        final List<PrincipleFirst> copyList = new ArrayList<>(originalList);
        copyList.forEach(element -> {
            originalList
                    .stream()
                    .filter(parent -> parent.getLearningTypeTitle().contentEquals(element.getLearningTypeTitle()) && parent.getLearningTypeTitle() != element.getLearningTypeTitle())
                    .findAny()
                    .ifPresent(parent -> {
                       // if (parent.getLearningTypeTitle().equals(element.getLearningTypeTitle())) {
                        boolean flag=true;
                        if (parent.childList == null) {
                            if (flag) {
                                parent.childList = new ArrayList<>();
                                flag=false;
                            }
                        }

                        parent.getChildList().add(element);
                        originalList.remove(element);
                    });
        });
        return originalList;
    }

    /********************** handel On click listener *******************/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tribe365:
                if (!backPress.equals("freeVersion")) {
                    callHomeAct(mContext);
                }
                break;
            case R.id.iv_top_back:
                if (backPress.equals("Home")){
                    callHomeAct(mContext);

                }else if (backPress.equals("offloading")){
                    startActivity(new Intent(mContext,Act_IOTHome.class));
                    finish();
                }else if (backPress.equals("know")){
                    startActivity(new Intent(mContext,Act_Know_Home.class));
                    finish();

                }else if (backPress.equals("freeVersion")){
                    startActivity(new Intent(mContext,Act_FreeVersionHome.class));
                    finish();
                }else {
                    finish();
                }

                break;

           /* case R.id.tv_start_tf:
                getSelectedUser();
                break;*/

            case R.id.principle_imageview:
                setPrincipleExpand();
                break;

        }
    }

    /*********** Api section Start ***********/
    /********************** user status Api call **********/
    public void getUserStatus(){
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(object.toString());
                    if (jsonObject.getBoolean("status")){
                       // tv_start_tf.setVisibility(View.GONE);
                    }else {
                        //tv_start_tf.setVisibility(View.VISIBLE);
                        setBottomMargin();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);
            }
        });
        JsonObject object=Functions.getClient().getJsonMapObject("","");
        baseRequest.callAPIPost(1,object,ConstantAPI.teamFeedbackUserStatus);
    }
    /********************** call api for principleList *************/
    public void getPrincipleList(){
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    Gson gson = new Gson();
                    PrincipleResponse user = gson.fromJson(Json, PrincipleResponse.class);

                    JSONObject jsonObject = (JSONObject) object;

                   String hptmScore = jsonObject.optString("hptmScore");
                    tv_hptm_score.setText(hptmScore);
                    JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("principleData").toString());
                    principleList  = baseRequest.getDataList(jsonArray, HPTM_Principle.class);


                    if (principleList.size()>0) {
                        if (flag == 1) {
                            try {
                                int position=principleList.size();
                                String principleId = String.valueOf(principleList.get(0).getId());
                                setPrincipleId=principleId;
                                getSelfLearningList(principleId);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    for (int i=0;i<principleList.size();i++){
                        if (i==0) {
                            committed_txt.setText(principleList.get(i).getTitle());
                        }else if (i==1){
                            directed_txt.setText(principleList.get(i).getTitle());
                        }else if(i==2){
                            selfless_txt.setText(principleList.get(i).getTitle());
                        }else if (i==3){
                            honest_txt.setText(principleList.get(i).getTitle());
                        }else if (i==4){
                            selfless_again_txt.setText(principleList.get(i).getTitle());
                        }
                    }
                    setLayoutLinear();
                    reversPrincipleList.clear();
                    for(int i=0;i<principleList.size();i++){
                        if (i==(principleList.size()-1)){
                            reversPrincipleList.add(0,principleList.get(i));
                        }else {
                            reversPrincipleList.add(principleList.get(i));
                        }
                    }

                    setHPTMPrinciple(reversPrincipleList);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("","");
        baseRequest.callAPIPost(1,object,ConstantAPI.getPrinciplesList);
    }
    /********************** call Api for learning List ************/
    public void getSelfLearningList(String principleId){
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    JSONObject jsonObject= new JSONObject(object.toString());
                    firstLearningList.clear();
                    Gson gson = new Gson();
                    try {
                        HPTMHomeResponse learningList = gson.fromJson(jsonObject.toString(), HPTMHomeResponse.class);
                        List<String> name = learningList.getLearningTypeArr();
                        Map<String,List<PrincipleFirst>> map=learningList.getLearningCheckListMap();

                        List<HPTMLearningCheckList> hptmCheckList= new ArrayList<>();
                        hptmCheckList.clear();
                        for (Map.Entry<String , List<PrincipleFirst>> listEntry: map.entrySet()){
                            //get the Key
                            String myKey = listEntry.getKey();
                            //getting the value (your List)
                            List<PrincipleFirst> myList= listEntry.getValue();
                            //the ineer loop iterate over the List
                           /* for (PrincipleFirst checkList: myList){
                                //here you have the Learning CheckList

                            }*/
                            if (myList.size()>0) {
                                HPTMLearningCheckList checkList = new HPTMLearningCheckList();
                                checkList.setHeadingTitle(myKey);
                                checkList.setLearningList(myList);
                                hptmCheckList.add(checkList);
                            }
                        }


                        if (hptmCheckList.size() > 0) {
                            rv_watch_link.setVisibility(View.VISIBLE);
                            setWatchList(hptmCheckList);
                        } else {
                            rv_watch_link.setVisibility(View.GONE);
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    try {


                        JSONArray jsonArray = new JSONArray(object.toString());
                        firstLearningList = baseRequest.getDataList(jsonArray, PrincipleFirst.class);

                        if (firstLearningList.size() > 0) {
                            rv_watch_link.setVisibility(View.VISIBLE);
                            //setWatchList(firstLearningList);
                        } else {
                            rv_watch_link.setVisibility(View.GONE);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);

            }
        });

        JsonObject object = Functions.getClient().getJsonMapObject("principleId",principleId);
        baseRequest.callAPIPost(1,object,ConstantAPI.getLearningCheckList);

    }
    public <T> List<T> getValue(Map<?, T> map){
        return new ArrayList<>(map.values());
    }
    public <T> List<T> getValues(Map<?, T> map) {
        return new ArrayList<>(map.values());
    }
    /*********************************upadte radio click and set learing api data *******************/
    public void updateLearningList(String principleId){
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    JSONObject jsonObject= new JSONObject(object.toString());
                    firstLearningList.clear();
                    Gson gson = new Gson();
                    //firstLearningList = gson.fromJson(object.toString(), PrincipleFirst.class);
                    //JSONArray jsonArray = new JSONArray(object.toString());
                   // firstLearningList  = baseRequest.getDataList(jsonArray, PrincipleFirst.class);
                    HPTMHomeResponse learningList = gson.fromJson(jsonObject.toString(), HPTMHomeResponse.class);
                    List<String> name = learningList.getLearningTypeArr();
                    Map<String,List<PrincipleFirst>> map=learningList.getLearningCheckListMap();

                    List<HPTMLearningCheckList> hptmCheckList= new ArrayList<>();
                    for (Map.Entry<String , List<PrincipleFirst>> listEntry: map.entrySet()){
                        //get the Key
                        String myKey = listEntry.getKey();
                        //getting the value (your List)
                        List<PrincipleFirst> myList= listEntry.getValue();
                        //the ineer loop iterate over the List
                           /* for (PrincipleFirst checkList: myList){
                                //here you have the Learning CheckList

                            }*/
                        HPTMLearningCheckList checkList= new HPTMLearningCheckList();
                        checkList.setHeadingTitle(myKey);
                        checkList.setLearningList(myList);
                        hptmCheckList.add(checkList);
                    }

                    if (hptmCheckList.size()>0){
                        rv_watch_link.setVisibility(View.VISIBLE);
                        setWatchList(hptmCheckList);
                    }else {
                        rv_watch_link.setVisibility(View.GONE);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);
            }
        });

        JsonObject object = Functions.getClient().getJsonMapObject("principleId",principleId);
        baseRequest.callAPIPostWOLoader(1,object,ConstantAPI.getLearningCheckList);

    }




    /*********************** set read Status Api *******************/
    public void changeReadStatus(int checkListId,boolean readStatus){
        baseRequest =new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    Gson gson = new Gson();

                    JSONObject jsonObject = (JSONObject) object;
                    String hptmScore= jsonObject.optString("hptmScore");
                    tv_hptm_score.setText(hptmScore);
                    //firstLearningList = gson.fromJson(object.toString(), PrincipleFirst.class);
                    //watchAdapter.notifyDataSetChanged();
                    //updateLearningList(setPrincipleId+"");

                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);
            }
        });

        int readValue=0;
        if (readStatus){
            readValue=0;
        }else {
            readValue=1;
        }
        JsonObject object=Functions.getClient().getJsonMapObject("checklistId",checkListId+"",
                "readStatus",readValue+"");
        baseRequest.callAPIPostWOLoader(1,object,ConstantAPI.changeReadStatusOfUserChecklist);

    }

    /************************ send Team Feedback Api ******************/
    public void getSelectedUser(){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    try {
                        JSONObject resObject = (JSONObject) object;
                        String hptmScore = resObject.optString("hptmScore");
                        tv_hptm_score.setText(hptmScore);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(Json);
                        String msg = jsonObject.getString("message");
                        if (msg.equals("")) {
                            msg = "Team Feedback sent successfully";
                        }
                        utility.showToast(mContext,msg);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
               // tv_start_tf.setVisibility(View.GONE);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);
            }
        });
        JsonObject object=Functions.getClient().getJsonMapObject("","");
        baseRequest.callAPIPost(1,object,ConstantAPI.sendTeamFeedbackToSelectedUsers);
    }




    /***************** handel OnBack ***********************/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(backPress.equals("home")){
            callHomeAct(mContext);

        }else if (backPress.equals("offloading")) {
            startActivity(new Intent(mContext, Act_IOTHome.class));
            finish();
        }else if (backPress.equals("know")){
            startActivity(new Intent(mContext,Act_Know_Home.class));
            finish();
        }else if (backPress.equals("freeVersion")){
            Intent intent=new Intent(mContext, Act_FreeVersionHome.class);
            startActivity(intent);
            finish();
        }else{
            finish();
        }
    }
    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_hptm_main;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_offloading;
    }
}