package com.chetaru.tribe365_new.UI.Risk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Risk.RiskArr;
import com.chetaru.tribe365_new.API.Models.Risk.RiskCount;
import com.chetaru.tribe365_new.API.Models.Risk.Swot;
import com.chetaru.tribe365_new.API.Models.TestModel;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Risk.Ad_RiskHome;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.DismissType;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.orientation;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.GuideView;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Listener.GuideListener;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Act_RiskHome extends BaseActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    SessionParam sessionParam;
    Utility utility;
    public static final String TAG= "RiskHome";
    private BaseRequest baseRequest;
    ArrayList<String> valueList= new ArrayList<>();
    ArrayList<String> sp_id= new ArrayList<>();
    List<Swot> swotList=new ArrayList<>();
    String orgId="";

    String swotId="";
    String status="2";
    int flag=2;
    List<RiskCount>  riskCount=new ArrayList<>();
    boolean newUser=false;
    int appTour=0;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    /***************************** initialize View *********************************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sp_check_list)
    Spinner sp_check_list;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_risk_show)
    RecyclerView rv_risk_show;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.no_risk_msg)
    TextView no_risk_msg;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.app_tour_ll)
    LinearLayout app_tour_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tour_show_tv)
    TextView tour_show_tv;

    /******************************** initialize first card View ********************************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.first_card)
    CardView first_card;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.first_name_tv)
    TextView first_name_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.first_value_tv)
    TextView first_value_tv;

    /********************************* initialize sceond card View *************************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sceond_card)
    CardView sceond_card;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sceond_name_tv)
    TextView sceond_name_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sceond_value_tv)
    TextView sceond_value_tv;
    /******************************** initialize third card value *****************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.third_card)
    CardView third_card;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.third_name_tv)
    TextView third_name_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.third_value_tv)
    TextView third_value_tv;
    /************************ initialize *********************/
    boolean isTablet;
    Ad_RiskHome mAdapter;
    List<TestModel> showList=new ArrayList<>();

    public Act_RiskHome() {
    }

    public Act_RiskHome(Context mMockContext) {
        mContext= mMockContext;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_risk_home);
        ButterKnife.bind(this);
        utility=new Utility();
        sessionParam=new SessionParam(this);
        setSessionParam();
        LinearLayoutManager layoutmanager=new LinearLayoutManager(mContext);
        rv_risk_show.setLayoutManager(layoutmanager);
        rv_risk_show.setHasFixedSize(true);
        first_card.setOnClickListener(this);
        sceond_card.setOnClickListener(this);
        third_card.setOnClickListener(this);
        /************ set Tab selection **************/
        //switchTab(flag);
        isTablet=getResources().getBoolean(R.bool.isTablet);
        /************* api call selection ***************/
        getRiskList();
        Observable<String> animalsObservable= Observable.just("Ant","Bee","Cat","Dog","Fox");
        Observer<String> animalsObserver= getAnimalsObserver();
        animalsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animalsObserver);
       // rv_risk_show.addItemDecoration(new DividerItemDecoration(rv_risk_show.getContext(),DividerItemDecoration.VERTICAL));
        /*for (int i=1;i<5;i++){
            TestModel test = new TestModel();
            test.setmId(i);
            if (i==1) {
                test.setmName("Team building");
                test.setmDesc("High");

            }else if (i==2){
                test.setmName("Reputaion");
                test.setmDesc("Low");
            }else if (i==3){
                test.setmName("Comms");
                test.setmDesc("Low");
            }else if (i==4){
                test.setmName("Covid");
                test.setmDesc("Low");

            }

            showList.add(test);
        }*/

        try {
            appTour = getIntent().getIntExtra("appTourType", 0);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    /************** handle onClick setcion ******************/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.first_card:
                flag=1;
                status="1";
                switchTab(flag);
                getRiskList();
                break;
            case R.id.sceond_card:
                flag=2;
                status="2";
                switchTab(flag);
                getRiskList();
                break;
            case R.id.third_card:
                flag=3;
                status="3";
                switchTab(flag);
                getRiskList();
                break;
        }
    }

    /**************** set session param **************/
    public void setSessionParam(){
        try {
            if (orgId.equals("")){
                orgId=sessionParam.orgId;
            }
            Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (valueList.size()>1) {
            if (sp_check_list.getSelectedItem().toString().equals("SWOT")){
                swotId="";
                    getRiskList();

            }else {
                String newId = String.valueOf(sp_id.get(position));
                if (!swotId.equals(newId)) {
                    swotId = String.valueOf(sp_id.get(position));
                    getRiskList();
                }
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setListData(List<RiskArr> arrList){
        mAdapter=new Ad_RiskHome(mContext,arrList,new Ad_RiskHome.ActionListener(){
            @Override
            public void riskAction(RiskArr riskActionList) {
                Intent intent = new Intent(mContext,Act_RiskAction.class);
                intent.putExtra("riskId",riskActionList.getId());
                startActivity(intent);

            }
        });
        rv_risk_show.setAdapter(mAdapter);
        int riskId=0;
        if (arrList.size()>0){
            riskId=arrList.get(0).getId();
        }
        if(appTour!=0) {
            tour_show_tv.setVisibility(View.VISIBLE);
            sp_check_list.setVisibility(View.GONE);
            rv_risk_show.setVisibility(View.GONE);
            ShowIntro(getString(R.string.risk_spinner_ppt),R.id.sp_check_list,5, riskId);


        }else {
            app_tour_ll.setVisibility(View.GONE);
        }
    }

    /****************** switch Tab *******************/
    @SuppressLint("UseCompatLoadingForDrawables")
    public void switchTab(int flag){
        if (flag==1){
            /* committed_txt.setBackground(getResources().getDrawable(R.drawable.team_role_edittext_red));
            committed_txt.setTextColor(getResources().getColor(R.color.white));

            directed_txt.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            directed_txt.setTextColor(getResources().getColor(R.color.black));*/
            first_card.setBackground(this.getResources().getDrawable(R.drawable.team_role_edittext_red));
            first_name_tv.setTextColor(getResources().getColor(R.color.white));
            first_value_tv.setTextColor(getResources().getColor(R.color.white));

            sceond_card.setBackground(this.getResources().getDrawable(R.drawable.edit_text_card_rounded));
            sceond_name_tv.setTextColor(getResources().getColor(R.color.textcolor));
            sceond_value_tv.setTextColor(getResources().getColor(R.color.textcolor));

            third_card.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            third_name_tv.setTextColor(getResources().getColor(R.color.textcolor));
            third_value_tv.setTextColor(getResources().getColor(R.color.textcolor));
        }else if (flag==2){
            first_card.setBackground(this.getResources().getDrawable(R.drawable.edit_text_card_rounded));
            first_name_tv.setTextColor(this.getResources().getColor(R.color.textcolor));
            first_value_tv.setTextColor(this.getResources().getColor(R.color.textcolor));

            sceond_card.setBackground(this.getResources().getDrawable(R.drawable.team_role_edittext_red));
            sceond_name_tv.setTextColor(this.getResources().getColor(R.color.white));
            sceond_value_tv.setTextColor(this.getResources().getColor(R.color.white));

            third_card.setBackground(this.getResources().getDrawable(R.drawable.edit_text_card_rounded));
            third_name_tv.setTextColor(this.getResources().getColor(R.color.textcolor));
            third_value_tv.setTextColor(this.getResources().getColor(R.color.textcolor));
        }else if (flag==3){
            first_card.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            first_name_tv.setTextColor(getResources().getColor(R.color.textcolor));
            first_value_tv.setTextColor(getResources().getColor(R.color.textcolor));

            sceond_card.setBackground(getResources().getDrawable(R.drawable.edit_text_card_rounded));
            sceond_name_tv.setTextColor(getResources().getColor(R.color.textcolor));
            sceond_value_tv.setTextColor(getResources().getColor(R.color.textcolor));

            third_card.setBackground(getResources().getDrawable(R.drawable.team_role_edittext_red));
            third_name_tv.setTextColor(getResources().getColor(R.color.white));
            third_value_tv.setTextColor(getResources().getColor(R.color.white));
            }
    }

    /**************** set Spinner Data **********/
    public void setSpinnerData(List<Swot> swotList){

        try {
            valueList.clear();
            sp_id.clear();
            valueList.add("SWOT");
            sp_id.add("");
            if(swotList.size()>0) {
                for (int i = 0; i < swotList.size(); i++) {
                    //valueList.set(i,swotList.get(i).getTitle());
                    String changevalue=capitalize(swotList.get(i).getTitle());
                    valueList.add(changevalue);
                    sp_id.add(String.valueOf(swotList.get(i).getId()));
                }
            }

                ArrayAdapter<String> ad = new ArrayAdapter<>(Act_RiskHome.this, R.layout.spinner_item_big, valueList);
                ad.setDropDownViewResource(R.layout.spinner_item_big);
                sp_check_list.setAdapter(ad);
                sp_check_list.setOnItemSelectedListener(this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    /************************* calling Risk Api *******************************/
    public void getRiskList(){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                  /*try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = (JSONObject) object;
                        RiskHome responseData= gson.fromJson(Json,RiskHome.class);
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/
                    riskCount.clear();
                    swotList.clear();
                    JSONObject jsonObject= (JSONObject) object;
                   // JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("principleData").toString());
                    JSONArray jsonArray=new JSONArray(jsonObject.getJSONArray("riskCount").toString());
                     riskCount=baseRequest.getDataList(jsonArray,RiskCount.class);
                    JSONArray swotArray= new JSONArray(jsonObject.getJSONArray("swotList").toString());
                    swotList=baseRequest.getDataList(swotArray,Swot.class);
                    JSONArray riskArray=new JSONArray(jsonObject.getJSONArray("riskArr").toString());
                    List<RiskArr> riskArrs=baseRequest.getDataList(riskArray,RiskArr.class);
                    try{
                        if (riskArrs.size()>0){
                            rv_risk_show.setVisibility(View.VISIBLE);
                            no_risk_msg.setVisibility(View.GONE);
                            setListData(riskArrs);
                        }else {
                            rv_risk_show.setVisibility(View.GONE);
                            no_risk_msg.setVisibility(View.VISIBLE);
                            if (status.equals("1")){
                                no_risk_msg.setText("No new risk");
                            }else if (status.equals("2")){
                                no_risk_msg.setText("No ongoing risk");
                            }else if (status.equals("3")){
                                no_risk_msg.setText("No risk closed yet");
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        if(riskCount.size()>0){
                            for (int i=0;i<riskCount.size();i++){
                                if (i==0){
                                    int countFirst=riskCount.get(0).getCount();
                                    first_value_tv.setText(String.valueOf(countFirst));
                                    first_name_tv.setText(riskCount.get(0).getStatusTitle());
                                }else if (i==1){
                                    int countSceond=riskCount.get(1).getCount();
                                    sceond_value_tv.setText(String.valueOf(countSceond));
                                    sceond_name_tv.setText(riskCount.get(1).getStatusTitle());
                                }else if (i==2){
                                    int countThird=riskCount.get(2).getCount();
                                    third_value_tv.setText(String.valueOf(countThird));
                                    third_name_tv.setText(riskCount.get(2).getStatusTitle());
                                }
                            }
                        }
                    }catch ( Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (swotList.size()>0) {
                            if (valueList.size()==0)
                            setSpinnerData(swotList);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                   /* if(appTour!=0) {
                        ShowIntro(getString(R.string.risk_spinner_ppt),R.id.sp_check_list,5);
                    }*/


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
        Log.d("RiskHome: ", "SwotId: "+swotId +" status : "+status);
        JsonObject object= Functions.getClient().getJsonMapObject("swotId",swotId,"status", status);
        baseRequest.callAPIPost(1,object, ConstantAPI.api_riskRegisterList);
    }

    public Observer<String> getAnimalsObserver(){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"onSubscribe call" + d.toString());

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,"onNext call"+ s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError call"+ e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete call");
            }
        };
    }

    /********************************* set App Tour ***********************/
    private void ShowIntro(String stringTitle,int viewId,int type,int riskId){
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
                .setDismissType(DismissType.skipe)
                .setTitleGravity(Gravity.LEFT)
                .setContentGravity(Gravity.LEFT)
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

        mGuideView = builder.build();
        mGuideView.show();
        if (type>12)
        {
            mGuideView.dismiss();
        }

        mGuideView.mMessageView.skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGuideView.dismiss();
                callHomeAct(mContext);
            }
        });
        mGuideView.mMessageView.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGuideView.dismiss();
                if (type== 5) {
                    app_tour_ll.setVisibility(View.VISIBLE);
                    Handler h = new Handler();
                    long delayInMilliseconds = 500;
                    h.postDelayed(new Runnable() {
                        public void run() {
                            ShowIntro(getString(R.string.risk_list_ppt),R.id.app_tour_ll,6,riskId);
                        }
                    }, delayInMilliseconds);
                }if (type==6){
                    Intent intent = new Intent(mContext, Act_RiskAction.class);
                    intent.putExtra("appTourType", 7);
                    intent.putExtra("riskId",riskId);
                    startActivity(intent);
                    finish();
                }
            }
        });
        updatingForDynmicLocationViews();
    }
    private void updatingForDynmicLocationViews(){
        no_risk_msg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mGuideView.updateGuideViewLocation();
            }
        });
    }
    public void add(int i) {
    }
    public String getHelloWorldString() {
        return mContext.getString(R.string.app_name);
    }

    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_risk_home;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_risk;
    }

}