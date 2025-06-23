package com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation;

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
import com.chetaru.tribe365_new.Adapter.SOTAdapters.Ad_Sot_Update_Motivation_Question;
import com.chetaru.tribe365_new.API.Models.SOTBeans.UpdateBean.ModelSOtMotivationIndividual;
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

public class Act_SOT_Update_Motivation_Question extends BaseActivity {
    @BindView(R.id.rv_sot_motiviation_quslist)
    RecyclerView rv_sot_motiviation_quslist;
    @BindView(R.id.tribe365)
    ImageView tribe;
    @BindView(R.id.btn_sot_qus_submit)
    Button btn_sot_qus_submit;
    BaseRequest baseRequest;
    Utility utility;
    SessionParam sessionParam;
    ArrayList<ModelSOtMotivationIndividual> list_modelCOTQuestions = new ArrayList<>();
    ArrayList<ModelSOtMotivationIndividual> list_modelCOTQuestions_copy = new ArrayList<>();
    int count = 0;
    String checklist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sot_motivaiton_question);
        try {
            checklist = getIntent().getStringExtra("checklist");
        } catch (Exception e) {
            e.printStackTrace();
        }
        inti();
        tribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });

    }

    private void inti() {
        ButterKnife.bind(this);
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        list_modelCOTQuestions.clear();
        btn_sot_qus_submit.setText("Update");
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe);
            }
        }
//        rv_sot_motiviation_quslist.setVisibility(View.GONE);
        getQuestionList();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rv_sot_motiviation_quslist.setVisibility(View.VISIBLE);
//            }
//        }, 2000);

        btn_sot_qus_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list_modelCOTQuestions.size(); i++) {
                    if (list_modelCOTQuestions.get(i).getOption().get(0).getRating().toString().equals("")) {
                        list_modelCOTQuestions_copy.get(i).getOption().get(0).setRating(list_modelCOTQuestions.get(i).getOption().get(0).getPoints() + "");
                    } else {
                        list_modelCOTQuestions_copy.get(i).getOption().get(0).setRating(list_modelCOTQuestions.get(i).getOption().get(0).getRating());

                    }
                    if (list_modelCOTQuestions.get(i).getOption().get(1).getRating().toString().equals("")) {
                        list_modelCOTQuestions_copy.get(i).getOption().get(1).setRating(list_modelCOTQuestions.get(i).getOption().get(1).getPoints() + "");
                    } else {
                        list_modelCOTQuestions_copy.get(i).getOption().get(1).setRating(list_modelCOTQuestions.get(i).getOption().get(1).getRating());

                    }
                }

                APi_sot_answer(list_modelCOTQuestions_copy);
//                count=0;
//                for(int i=0;i<list_modelCOTQuestions.size();i++)
//                {
//                    for(int j=0;j<list_modelCOTQuestions.get(i).getOption().size();j++)
//                    {
//                        if(list_modelCOTQuestions.get(i).getOption().get(j).getRating().equals(""))
//                        {
//                            Toast.makeText(mContext, "Please provide score for Question : "+(i+1), Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        else{
//                            count++;
//                        }
//                    }
//                }
//                if(count==120) {
//                    APi_sot_answer();
//                }
            }
        });
    }

    private void answerResponce() {
     /*   {
            "answer": [
            {
                "questionId": "1",
                    "option": [
                {
                    "optionId": "1",
                        "rating": "5"
                },
                {
                    "optionId": "2",
                        "rating": "0"
                }
               ]
            }*/

    }

    public void getQuestionList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Log.e("api_get_COTQuestions=>", Json);

                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                list_modelCOTQuestions = baseRequest.getDataList(jsonArray, ModelSOtMotivationIndividual.class);
                list_modelCOTQuestions_copy = baseRequest.getDataList(jsonArray, ModelSOtMotivationIndividual.class);
                final Ad_Sot_Update_Motivation_Question adap_sot_questionlist = new Ad_Sot_Update_Motivation_Question(list_modelCOTQuestions, mContext);
                rv_sot_motiviation_quslist.setHasFixedSize(true);
                rv_sot_motiviation_quslist.setAdapter(adap_sot_questionlist);
                rv_sot_motiviation_quslist.setLayoutManager(new LinearLayoutManager(mContext));
//                rv_sot_motiviation_quslist.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Call smooth scroll
//                        rv_sot_motiviation_quslist.smoothScrollToPosition(adap_sot_questionlist.getItemCount() - 1);
//                    }
//                });


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
        baseRequest.callAPIGET(1, map, ConstantAPI.api_getSOTmotivationCompletedAnswer);
    }

    public void APi_sot_answer(ArrayList<ModelSOtMotivationIndividual> list_modelCOTQuestions) {
        baseRequest = new BaseRequest(mContext);
        ArrayList<ModelSOtMotivationIndividual> list_modelCOTQuestions_copy1 = list_modelCOTQuestions_copy;
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Log.e("api_get_SOTQuestions=>", Json);

                Toast.makeText(mContext, "cot motivation answer updated successfully.", Toast.LENGTH_SHORT).show();
                if (checklist != null) {
                    if (checklist.equals("checklist")) {
                        finish();
                    } else {
                        Intent in = new Intent(Act_SOT_Update_Motivation_Question.this, Act_SOT_Motivation_Individual_graph.class);
                        startActivity(in);
                        finish();
                    }
                } else {
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

        JsonObject mainObject = new JsonObject();
        JsonObject subobject = new JsonObject();
        JsonArray jsonArray = null;
        jsonArray = new JsonArray();
        JsonArray subAry = new JsonArray();

        for (int i = 0; i < list_modelCOTQuestions.size(); i++) {
            for (int j = 0; j < list_modelCOTQuestions.get(i).getOption().size(); j++) {
                subobject.addProperty("answerId", list_modelCOTQuestions_copy.get(i).getOption().get(j).getAnswerId());
                subobject.addProperty("rating", list_modelCOTQuestions_copy.get(i).getOption().get(j).getRating());
                subAry.add(subobject);
                subobject = new JsonObject();
            }
            JsonObject qus_json = new JsonObject();
            qus_json.addProperty("questionId", list_modelCOTQuestions_copy.get(i).getQuestionId());
            qus_json.add("option", subAry);
            jsonArray.add(qus_json);
            subAry = new JsonArray();

        }


        mainObject.add("answer", jsonArray);

        Log.e("Ans SOT=>", jsonArray + "");
        //-----------------------------------------------------------------------------------------------------------------------------------
        baseRequest.callAPIPost(1, mainObject, ConstantAPI.api_updateSOTmotivationAnswers );
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_sot_motivaiton_question;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }

}
