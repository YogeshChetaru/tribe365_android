package com.chetaru.tribe365_new.UI.Know.COT.FunctionalLense;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.COTAdapters.AD_update_Fun_Question;
import com.chetaru.tribe365_new.API.Models.COTBeans.UpdateBean.UpdateFunctionBean.ModelFuctionLens;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Act_Update_Fun_QuestionList extends BaseActivity {
    @BindView(R.id.tribe365)
    ImageView tribe365;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    Utility utility;
    @BindView(R.id.rv_functional_lens)
    RecyclerView rv_functional_lens;
    @BindView(R.id.btn_fun_qus_submit)
    Button btn_fun_qus_submit;
    ArrayList<ModelFuctionLens> li_Fun_Qus = new ArrayList<>();
    ArrayList<ModelFuctionLens> li_Fun_QusMain = new ArrayList<>();
    //    ArrayList<ModelFunQusAns>modelCOTFuntAns;
    AD_update_Fun_Question ad_fun_question;
    String checklist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_function_lens_qustion);

        inti();
    }

    private void inti() {
        ButterKnife.bind(this);
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        utility = new Utility();
//        rv_functional_lens.setVisibility(View.GONE);
        call_QuestionList();
        btn_fun_qus_submit.setText("Update");

    }

    @OnClick({R.id.btn_fun_qus_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            /*{
     "answer": [
          {
               "questionId": "1",
               "optionId": "1"
          },*/
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.btn_fun_qus_submit:

                for (int i = 0; i < li_Fun_Qus.size(); i++) {
                    if (li_Fun_Qus.get(i).getOptions().get(0).getIsChecked()) {
                        li_Fun_QusMain.get(i).setOptionId(li_Fun_Qus.get(i).getOptions().get(0).getOptionId() + "");
                    } else {
                        li_Fun_QusMain.get(i).setOptionId(li_Fun_Qus.get(i).getOptions().get(1).getOptionId() + "");
                    }
//                    if(!li_Fun_QusMain.get(i).getOptionId().equals(li_Fun_Qus.get(i).getOptionId())&&li_Fun_Qus.get(i).getAnswerId()!=null){
//                        if(!li_Fun_Qus.get(i).getOptionId().equals("")){
//                            li_Fun_QusMain.get(i).setOptionId(li_Fun_Qus.get(i).getOptionId());
//                        }else{
//
//                        }
//
//                    }
//                    else{
//                        li_Fun_QusMain.get(i).setOptionId(li_Fun_Qus.get(i).getOptionId());
//                    }

                }
                call_AnswerList(li_Fun_QusMain);

                //old code
//
//                int count = 0;
////                li_Fun_Qus.lastIndexOf(li_Fun_Qus.size()-1);
//
//                for (int i = 0; i < li_Fun_Qus.size(); i++) {
//                    if (li_Fun_Qus.get(i).getOptionId().toString().equals("")) {
//                        count = 0;
//                       Toast.makeText(mContext, "Please agree with a statement for Question  " + li_Fun_Qus.get(i).getQuestionId(), Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        count++;
//                    }
//                }
//                if (count == li_Fun_Qus.size()) {
//                    call_AnswerList();
//                }

                break;
        }
    }

    public void call_QuestionList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray = (JSONArray) object;
                li_Fun_Qus = baseRequest.getDataList(jsonArray, ModelFuctionLens.class);
                li_Fun_QusMain = baseRequest.getDataList(jsonArray, ModelFuctionLens.class);
                rv_functional_lens.setLayoutManager(new LinearLayoutManager(mContext));
                ad_fun_question = new AD_update_Fun_Question(li_Fun_Qus, mContext);
//                rv_functional_lens.smoothScrollToPosition(li_Fun_Qus.size()-1);
                rv_functional_lens.setAdapter(ad_fun_question);

                rv_functional_lens.post(new Runnable() {
                    @Override
                    public void run() {
                        // Call smooth scroll
                        // rv_functional_lens.smoothScrollToPosition(ad_fun_question.getItemCount() - 1);

                    }
                });
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        rv_functional_lens.setVisibility(View.VISIBLE);
//                    }
//                }, 2000);


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
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        baseRequest.callAPIGET(1, map, ConstantAPI.api_getCOTfuncLensCompletedAnswers);
    }

    public void call_AnswerList(ArrayList<ModelFuctionLens> li_Fun_Qus) {
        ArrayList<ModelFuctionLens> li_Fun_Qus1 = li_Fun_QusMain;
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray = (JSONArray) object;
                Toast.makeText(mContext, "cot personality type answer updated successfully.", Toast.LENGTH_SHORT).show();
                if (checklist != null) {
                    if (checklist.equals("checklist")) {
                        finish();
                    } else {
                        Intent in = new Intent(Act_Update_Fun_QuestionList.this, Act_CotDetails.class);
                        startActivity(in);
                        finish();
                    }
                } else {
                    Intent in = new Intent(Act_Update_Fun_QuestionList.this, Act_CotDetails.class);
                    startActivity(in);
                    finish();
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
        JsonObject jsonObject = null;
        JsonArray jsonArray = null;
        JsonObject object = null;
        jsonObject = new JsonObject();
        jsonArray = new JsonArray();
        object = new JsonObject();
        for (int i = 0; i < li_Fun_Qus.size(); i++) {
            try {
                jsonObject.addProperty("answerId", li_Fun_QusMain.get(i).getAnswerId().toString());
                jsonObject.addProperty("optionId", li_Fun_QusMain.get(i).getOptionId().toString());
                jsonArray.add(jsonObject);
                jsonObject = new JsonObject();
            } catch (Exception e) {

            }
        }
        object.add("answer", jsonArray);
        Log.e("Ans Array=>", String.valueOf(object));
        baseRequest.callAPIPost(1, object,ConstantAPI.api_updateCOTfunLensAnswers );
    }


    @Override
    public void onBackPressed() {
        if (checklist != null) {
            if (checklist.equals("checklist")) {
                finish();
            } else {
                Act_Update_Fun_QuestionList.super.onBackPressed();
            }
        } else {
            finish();
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_function_lens_qustion;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}