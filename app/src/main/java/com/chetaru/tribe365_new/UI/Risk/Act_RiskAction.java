package com.chetaru.tribe365_new.UI.Risk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelACtionList;
import com.chetaru.tribe365_new.API.Models.Risk.RiskDetail;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Risk.Ad_Risk_ActionList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.DismissType;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.orientation;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.GuideView;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Listener.GuideListener;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.BasicVersion.BasicHomeActivity;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.UI.Home.Actions.Act_AddAction_new;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_RiskAction extends BaseActivity implements AdapterView.OnItemSelectedListener {


    SessionParam sessionParam;
    Utility utility;
    String orgId="";
    private BaseRequest baseRequest;
    ArrayList<ModelACtionList> list;
    ArrayList<ModelACtionList> list2;

    @BindView(R.id.iv_chat)
    ImageView iv_chat;
    @BindView(R.id.app_tour_action_ll)
    LinearLayout app_tour_action_ll;
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @BindView(R.id.action_title_tv)
    TextView action_title_tv;
    @BindView(R.id.action_desc_tv)
    TextView action_des_tv;
    @BindView(R.id.action_date_tv)
    TextView action_date_tv;
    @BindView(R.id.action_risk_tv)
    TextView action_risk_tv;
    @BindView(R.id.action_risk_level_tv)
    TextView action_risk_level_tv;
    @BindView(R.id.num_offload_tv)
    TextView num_offload_tv;
    @BindView(R.id.risk_spinner)
    Spinner spinner;

    @BindView(R.id.rv_action_list)
    RecyclerView rv_action_list;
    @BindView(R.id.show_app_tour_tv)
    TextView show_app_tour_tv;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tv_error_msg)
    TextView tv_error_msg;
    @BindView(R.id.no_action_found_tv)
     TextView no_action_found_tv;
    String headingName,riskLevel;
    List<String> valueList= new ArrayList<>();
    int riskId=0;
    boolean flag=true;

    String[] type= {"All Tier", "Primary", "Secondary","Tertiary","Office","Department","Individual"};
    ArrayAdapter aa;
    /* int appTour=0;
    private GuideView mGuideView;
    private GuideView.Builder builder;*/
    int appTour=0;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_risk_action);
        ButterKnife.bind(this);
        utility=new Utility();
        sessionParam=new SessionParam(mContext);
        setSesssionParam();
        try {
            setSpinnerData();
        }catch (Exception e){
            e.printStackTrace();
        }

      //  LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(Act_RiskAction.this);
        rv_action_list.setLayoutManager(layoutManager);
        rv_action_list.setHasFixedSize(true);
        rv_action_list.setNestedScrollingEnabled(false);

        try{
            riskId=getIntent().getIntExtra("riskId",0);
           /* action_title_tv.setText(headingName);
            action_date_tv.setText("Created on 01/05/2021 with");
            action_risk_tv.setText(riskLevel);
            action_risk_level_tv.setText(" risk level");*/
        }catch (Exception e){
            e.printStackTrace();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Act_AddAction_new.class));
                finish();
            }
        });
       // getActionList();
        try {
            appTour= getIntent().getIntExtra("appTourType",0);
        }catch (Exception e){
            e.printStackTrace();
        }
        isTablet=getResources().getBoolean(R.bool.isTablet);
        /******** call risk Detail Api ********/
        getRiskData();

    }

    /**************** set Param *******************/
    public void setSesssionParam(){
        try {
            orgId=sessionParam.orgId;
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /****************** set spinner data ************/
    public void setSpinnerData(){
        /*for (int i=0;i<5;i++){
            valueList.add("All Tiers");
        }*/
        if (type.length>0) {
            try {

                 //aa=new ArrayAdapter(this,R.layout.spinner_item_big,type);
                //aa.setDropDownViewResource(R.layout.spinner_item_big);
                aa= new ArrayAdapter(Act_RiskAction.this, android.R.layout.simple_spinner_item,type);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner.setAdapter(aa);
                /* ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.spinner_item_big, type);
                spinner.setAdapter(ad);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });*/
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (list.size()>0){

            if (type[position].equalsIgnoreCase("primary")){
                list2.clear();
                for (int i=0;i<list.size();i++){
                    if (list.get(i).getTier().equalsIgnoreCase("primary")){
                        list2.add(list.get(i));
                    }
                }
            }else if (type[position].equalsIgnoreCase("secondary")){
                list2.clear();
                for (int i=0;i<list.size();i++){
                    if (list.get(i).getTier().equalsIgnoreCase("secondary")){
                        list2.add(list.get(i));
                    }
                }
            }else if (type[position].equalsIgnoreCase("tertiary")) {
                list2.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTier().equalsIgnoreCase("tertiary")) {
                        list2.add(list.get(i));
                    }
                }

            } else if (type[position].equalsIgnoreCase("office")) {
                list2.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTier().equalsIgnoreCase("office")) {
                        list2.add(list.get(i));
                    }
                }

            } else if (type[position].equalsIgnoreCase("department")) {
                list2.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTier().equalsIgnoreCase("department")) {
                        list2.add(list.get(i));
                    }
                }

            } else if (type[position].equalsIgnoreCase("individual")) {
                list2.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTier().equalsIgnoreCase("individual")) {
                        list2.add(list.get(i));
                    }
                }

            } else if (type[position].equalsIgnoreCase("all tier")) {
                rv_action_list.setAdapter(new Ad_Risk_ActionList(list, riskId, mContext, new Ad_Risk_ActionList.riskActionListener() {
                    @Override
                    public void riskAppTourClick(int viewId,View view1,ModelACtionList list) {

                    }
                }));
                rv_action_list.setVisibility(View.VISIBLE);
                tv_error_msg.setVisibility(View.GONE);
                //list2 = list;
                return;
            }
            rv_action_list.setAdapter(new Ad_Risk_ActionList(list2,riskId, mContext, new Ad_Risk_ActionList.riskActionListener() {
                @Override
                public void riskAppTourClick(int viewId,View view1,ModelACtionList list) {

                }
            }));
            if (list2.size()<1){
                rv_action_list.setVisibility(View.GONE);
                tv_error_msg.setVisibility(View.VISIBLE);
                no_action_found_tv.setVisibility(View.VISIBLE);
            }else {
                rv_action_list.setVisibility(View.VISIBLE);
                tv_error_msg.setVisibility(View.GONE);
                no_action_found_tv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /************************** set Api Section ***********************************/


    public void getRiskData(){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                    JSONObject jsonObject = (JSONObject) object;
                    RiskDetail responseData= gson.fromJson(object.toString(),RiskDetail.class);
                    responseData.getTitle();
                    action_title_tv.setText(responseData.getTitle());
                    action_des_tv.setText(responseData.getDescription());
                    action_date_tv.setText("Created on "+utility.changeDateYMDtoDMY(responseData.getDate()) + " with ");
                    action_risk_tv.setText(" " +responseData.getPriority()+" ");
                    action_risk_level_tv.setText(" risk level");
                    try {
                        if (responseData.getOffloads() != null) {
                            num_offload_tv.setText("Linked Offloads : " + responseData.getOffloads().toString());
                            num_offload_tv.setVisibility(View.VISIBLE);
                        }else {
                            num_offload_tv.setVisibility(View.GONE);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        num_offload_tv.setVisibility(View.GONE);
                    }

                    //jsonArray=(JSONArray) object;
                    try {
                        JSONArray jsonArray;
                        jsonArray=jsonObject.getJSONArray("actions");
                        list2 = baseRequest.getDataList(jsonArray, ModelACtionList.class);
                        list = baseRequest.getDataList(jsonArray, ModelACtionList.class);
                        rv_action_list.setAdapter(new Ad_Risk_ActionList(list2, riskId,mContext, new Ad_Risk_ActionList.riskActionListener() {
                            @Override
                            public void riskAppTourClick(int viewId,View view,ModelACtionList list) {
                                /*if (list.getId().equals("")) {
                                    if (appTour != 0) {
                                        show_app_tour_tv.setVisibility(View.VISIBLE);
                                        ShowIntro(getString(R.string.risk_action_ppt), R.id.show_app_tour_tv, 10);
                                    }
                                }*/
                            }
                        }));
                        if (list.size()>0){
                            rv_action_list.setVisibility(View.VISIBLE);
                            no_action_found_tv.setVisibility(View.GONE);
                        }else {
                            rv_action_list.setVisibility(View.GONE);
                            no_action_found_tv.setVisibility(View.VISIBLE);
                        }
                        try {
                            if (appTour != 0) {
                                show_app_tour_tv.setVisibility(View.GONE);
                                app_tour_action_ll.setVisibility(View.VISIBLE);
                                rv_action_list.setVisibility(View.GONE);
                                no_action_found_tv.setVisibility(View.GONE);
                                Handler h = new Handler();
                                long delayInMilliseconds = 1000;
                                h.postDelayed(new Runnable() {
                                    public void run() {
                                        ShowIntro(getString(R.string.risk_action_ppt), R.id.iv_chat, 10);
                                    }
                                }, delayInMilliseconds);
                                // ShowIntro(getString(R.string.risk_action_ppt), R.id.show_app_tour_tv, 10);

                            } else {
                                show_app_tour_tv.setVisibility(View.GONE);
                                app_tour_action_ll.setVisibility(View.GONE);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        setSpinnerData();
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
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);
            }
        });
        JsonObject jsonObject=Functions.getClient().getJsonMapObject("riskId",riskId+"");
        baseRequest.callAPIPost(1,jsonObject,ConstantAPI.api_riskDetail);
    }

    /*************** handle Back Activity ************************/
    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent=new Intent(mContext,Act_RiskHome.class);
        startActivity(intent);
        finish();
    }

    /******************************* App Tour **************************************/
    private void ShowIntro(String stringTitle,int viewId,int type){
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int sizeInPixels = 18;
        if (isTablet){
            sizeInPixels=24;
        }else {
            sizeInPixels=20;
        }
        builder = new GuideView.Builder(this)
                .setContentText(stringTitle)
                .setTargetView(findViewById(viewId))
                .setContentTextSize(sizeInPixels)
                .setTitleTextSize(sizeInPixels)
                //.setGravity(Gravity.auto)
                //.setGravity(Gravity.auto)
                .setDismissType(DismissType.skipe)
                .setTitleGravity(android.view.Gravity.LEFT)
                .setContentGravity(android.view.Gravity.LEFT)
                .setButtonGravity(Gravity.LEFT)
                .setPaddingButton(10,0,10,0)
                .setButtonText("Skip Tutorial")
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                    }
                });
      /*  if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // builder.setOrientation(orientation.landscape);
        }else if (!isTablet){
            builder.setOrientation(orientation.portrait);
        }*/
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // builder.setOrientation(orientation.landscape);
            if (!isTablet){
                builder.setOrientation(orientation.portrait);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }else {
                builder.setOrientation(orientation.landscape);
            }
        }

        mGuideView=builder.build();
        mGuideView.show();
        mGuideView.mMessageView.skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGuideView.dismiss();
                callHomeAct(mContext);
            }
        });
        if (flag){
            flag=false;
           // mGuideView.dismiss();
        }
        if (type>12){
            mGuideView.dismiss();
        }
        mGuideView.mMessageView.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_app_tour_tv.setVisibility(View.GONE);
                Intent intent= null;
                if (sessionParam.loginVersion == 3) {
                     intent = new Intent(mContext, BasicHomeActivity.class);
                    intent.putExtra("appTourType", 10);
                    startActivity(intent);
                } else{
                     intent = new Intent(mContext, Act_Home.class);
                    intent.putExtra("appTourType", 10);
                    startActivity(intent);
                }

            }
        });
        updatingForDynamicLocationViews();
    }
     private void updatingForDynamicLocationViews(){
        tv_error_msg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mGuideView.updateGuideViewLocation();
            }
        });
     }

    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_risk_action;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_risk;
    }
}