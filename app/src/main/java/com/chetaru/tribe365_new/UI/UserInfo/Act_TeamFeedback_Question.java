package com.chetaru.tribe365_new.UI.UserInfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.TeamFeedback.QuestionArr;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_TeamFeedback_question;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_TeamFeedback_Question extends BaseActivity {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    Utility utility;
    String fromUserId="";
    String backHandel="";
    String mdate="";
    String titleMsg="";
    String teamId="";
    List<QuestionArr> questionArrList;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_team_question)
    RecyclerView rv_team_question;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit_team_Qus)
    Button btn_submit_team_Qus;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_team_title)
    TextView  tv_team_title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_team_feedback_question);
        ButterKnife.bind(this);
        utility=new Utility();
        sessionParam=new SessionParam(mContext);
        setSessionParam();
        questionArrList=new ArrayList<>();
       // tv_team_title.setMovementMethod(new ScrollingMovementMethod());

        /*************** send notification read Status ***************/
        if (getIntent().getStringExtra("readNotificationId") != null) {
            String readNotificationId = getIntent().getStringExtra("readNotificationId");
            api_notificationRead(readNotificationId);
        }
        try {
            fromUserId = getIntent().getStringExtra("userId");
            backHandel = getIntent().getStringExtra("backHandel");
            teamId=getIntent().getStringExtra("teamId");
            mdate=getIntent().getStringExtra("date");



        } catch (Exception e) {
            e.printStackTrace();
        }
        init();

        if (!mdate.equals("")) {
            getFeedbackUserAnswerStatus(fromUserId);
        }else {
            getAllQuestions(fromUserId);
        }
        btn_submit_team_Qus.setOnClickListener(v->{
            int count=0;
            for (int i=0;i<questionArrList.size();i++){
                if(questionArrList.get(i).getmAnswer().equals("")){
                    count=0;
                    utility.showToast(mContext,getString(R.string.please_select_answer)+questionArrList.get(i).getQuestionId());
                    return;
                }else {
                    count++;
                }
                if (count ==questionArrList.size()){
                    submitAnswer();
                }
            }
        });
        tribe365.setOnClickListener(v->{
            callHomeAct(mContext);
        });
        iv_top_back.setOnClickListener(v->{
            try {
                if (!backHandel.equals("")) {
                    if (backHandel.equals("notiBack")) {
                        finish();
                    } else if (backHandel.equals("HomeBack")) {
                        callHomeAct(mContext);
                    } else {
                        callHomeAct(mContext);
                    }
                } else {
                    callHomeAct(mContext);
                }
            }catch (Exception e){
                e.printStackTrace();
                callHomeAct(mContext);
            }
        });
    }

    public void init(){
        rv_team_question.setLayoutManager(new LinearLayoutManager(mContext));

    }
    public void setSessionParam(){
        if (!sessionParam.organisation_logo.equalsIgnoreCase("")){
            Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
        }
    }

    public void getFeedbackUserAnswerStatus(String fromUserId){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(Json);
                        titleMsg=jsonObject.getString("message");
                       /* try {
                            int titleTextSize=titleMsg.length();

                            if (titleTextSize>200){
                                ViewGroup.LayoutParams params= (ViewGroup.LayoutParams) tv_team_title.getLayoutParams();
                                params.height = 300;
                                tv_team_title.setLayoutParams(params);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }*/
                        tv_team_title.setText(titleMsg);

                      //JSONArray jsonArray = new JSONArray(object.toString());
                    JSONObject responseObject = (JSONObject) object;
                    boolean status=responseObject.getBoolean("status");
                        if (!status){
                            getAllQuestions(fromUserId);
                        }else {
                            rv_team_question.setVisibility(View.GONE);
                            btn_submit_team_Qus.setVisibility(View.GONE);
                        }
                        //TOTAL_PAGES = jsonObject.optInt("totalPageCount");
                      //  JSONArray jsonArray = new JSONArray(object.toString());

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
        JsonObject object= Functions.getClient().getJsonMapObject("fromUserId",fromUserId,"date",mdate);
        Log.d("teamFeedback",object.toString());
        baseRequest.callAPIPost(1,object, ConstantAPI.teamFeedbackUserAnswerStatus);
    }
    public void getAllQuestions(String fromUserId){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    btn_submit_team_Qus.setVisibility(View.VISIBLE);
                    rv_team_question.setVisibility(View.VISIBLE);
                    JSONObject jsonObject = (JSONObject) object;
                    String userName = jsonObject.getString("userName");
                    JSONArray jsonArray =  jsonObject.getJSONArray("questionArr");
                    questionArrList=baseRequest.getDataList(jsonArray,QuestionArr.class);
                    setQuestions(userName, questionArrList);
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
        JsonObject object= Functions.getClient().getJsonMapObject("userId",fromUserId);
        baseRequest.callAPIPost(1,object, ConstantAPI.getIndividualQuestionnaireList);
    }
    public void setQuestions(String name, List<QuestionArr> questionArrs){
        Ad_TeamFeedback_question ad_teamFeedback_question= new Ad_TeamFeedback_question(questionArrList,mContext,name);
        rv_team_question.setAdapter(ad_teamFeedback_question);
    }
    public void submitAnswer(){
        baseRequest =new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    utility.showToast(mContext, jsonObject.getString("message"));
                    if (backHandel.equals("HomeBack")){
                        callHomeAct(mContext);
                    }else {
                        finish();
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
        JsonObject jsonObject=null;
        JsonArray jsonArray=null;
        JsonObject object=null;
        jsonObject=new JsonObject();
        jsonArray=new JsonArray();
        object=new JsonObject();
        JsonObject value=new JsonObject();
        for (int i=0;i<questionArrList.size();i++){
            jsonObject.addProperty("questionId",questionArrList.get(i).getQuestionId());
            for (int j=0;j<questionArrList.get(i).getOptions().size();j++) {
                if (questionArrList.get(i).getOptions().get(j).isAnswerFlag()) {
                    jsonObject.addProperty("optionId", questionArrList.get(i).getOptions().get(j).getOptionId());
                }
            }
            jsonArray.add(jsonObject);
            jsonObject =new JsonObject();
        }
        object.addProperty("fromUserId",fromUserId+"");
        object.addProperty("date",mdate);
        object.addProperty("teamFeedbackId",teamId);
        object.add("answer",jsonArray);
        Log.e("Ans Array->",String.valueOf(object));
        baseRequest.callAPIPost(1,object,ConstantAPI.addIndividualQuestionnaireAnswers);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!backHandel.equals("")){
            if (backHandel.equals("HomeBack")){
                callHomeAct(mContext);
            }else if (backHandel.equals("notiBack")){
                finish();
            }
        }else {
            callHomeAct(mContext);
        }

    }

    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_team_feedback_question;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }


}