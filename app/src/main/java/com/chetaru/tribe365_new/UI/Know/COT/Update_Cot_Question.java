package com.chetaru.tribe365_new.UI.Know.COT;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.COTAdapters.Update.Adap_COT_Update_Questionlist;
import com.chetaru.tribe365_new.API.Models.COTBeans.UpdateBean.ModelCOTTeamUpdate;
import com.chetaru.tribe365_new.API.Models.COTBeans.UpdateBean.Option;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Update_Cot_Question extends BaseActivity implements Adap_COT_Update_Questionlist.AdapterCallback {
    private static final String TAG = Update_Cot_Question.class.getSimpleName();
    @BindView(R.id.tv_tot_point)
    public TextView tv_tot_point;
    public Context mContext;
    public int index = 0;
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @BindView(R.id.rv_cot_question)
    RecyclerView rv_cot_question;
    @BindView(R.id.tv_ques_name)
    TextView tv_ques_name;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.lyt_parent)
    LinearLayout lyt_parent;
    //private ErrorLayout errorLayout;
    BaseRequest baseRequest;
    Utility utility;
    String companyString = "";
    SessionParam sessionParam;
    int sum = 0;
    ArrayList<ModelCOTTeamUpdate> list_modelCOTQuestions = new ArrayList<>();
    ArrayList<ModelCOTTeamUpdate> list_modelCOTQuestions_copy = new ArrayList<>();
    ArrayList<Option> list_qus = new ArrayList<>();
    NumberPicker numberPicker;
    //Adap_COT_Update_Questionlist adap_cot_questionlist = new Adap_COT_Update_Questionlist();
    ArrayList<String> list_ans = new ArrayList<>();
    int st_total_lians = 0;
    Adap_COT_Update_Questionlist adap_cot_questionlist;
    String checklist = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_questionlist);
        list_qus.clear();
        list_modelCOTQuestions.clear();
        try {
            checklist = getIntent().getStringExtra("checklist");
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
        getQuestionList();
    }

    @Override
    public void onMethodCallback(int total) {
        // get your value here

        tv_tot_point.setText(total + "");

        //Toast.makeText(mContext, total+"", Toast.LENGTH_SHORT).show();
    }

    private void init() {
        ButterKnife.bind(this);
//      errorLayout = new ErrorLayout(findViewById(R.id.error_rl));
        utility = new Utility();
        this.mContext = this;
        sessionParam = new SessionParam(mContext);
        companyString = sessionParam.companyList;
        btn_submit.setText("Finish");
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
    }

    @OnClick({R.id.btn_next, R.id.btn_submit, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.btn_next:
                TotalCount();
                validation();
//                errorLayout.hideError();
//                index = index + 1;
//                callArray(index);
                break;
            case R.id.btn_submit:
                for (int i = 0; i < list_modelCOTQuestions.get(index).getOptions().size(); i++) {
                    if ((list_modelCOTQuestions.get(index).getOptions().get(i).getAnswer() == null)) {
//                errorLayout.showError("Please provide score for option " + list_qus.get(i).getmAlphabate());
//                errorLayout.hideError();
                        list_ans.add(list_modelCOTQuestions.get(index).getOptions().get(i).getPoint());
                        list_modelCOTQuestions.get(index).getOptions().get(i).setAnswer(list_modelCOTQuestions.get(index).getOptions().get(i).getPoint());
                        //         utility.showToast(mContext, "Please provide score for option " + list_qus.get(i).getmAlphabate());
//                return;

                    } else {
                        list_ans.add(list_modelCOTQuestions.get(index).getOptions().get(i).getAnswer());
                        // utility.showToast(mContext, "option " + list_qus.get(i).getAnswer());

                    }
                }
                Submitvalidation();

                break;
        }
    }


    //"Please provide score for option optionname
    private void validation() {
        list_ans.clear();
        st_total_lians = 0;

        //"Please provide score for option optionname
        for (int i = 0; i < list_modelCOTQuestions.get(index).getOptions().size(); i++) {
            if ((list_modelCOTQuestions.get(index).getOptions().get(i).getAnswer() == null)) {
//                errorLayout.showError("Please provide score for option " + list_qus.get(i).getmAlphabate());
//                errorLayout.hideError();
                list_ans.add(list_modelCOTQuestions.get(index).getOptions().get(i).getPoint());
                list_modelCOTQuestions.get(index).getOptions().get(i).setAnswer(list_modelCOTQuestions.get(index).getOptions().get(i).getPoint());
                //         utility.showToast(mContext, "Please provide score for option " + list_qus.get(i).getmAlphabate());
//                return;

            } else {
                list_ans.add(list_modelCOTQuestions.get(index).getOptions().get(i).getAnswer());
                // utility.showToast(mContext, "option " + list_qus.get(i).getAnswer());

            }
        }
        if (list_ans.size() == list_modelCOTQuestions.get(index).getOptions().size()) {
            for (int j = 0; j < list_ans.size(); j++) {
                st_total_lians = st_total_lians + Integer.parseInt(list_ans.get(j));
            }
            if (st_total_lians == 10) {
                index = index + 1;
                tv_tot_point.setText("0");
                callArray(index);
                //   errorLayout.hideError();
            } else {
                utility.showToast(mContext,getString(R.string.please_revise_your_score));
            }
        }

    }

    private void Submitvalidation() {
        list_ans.clear();
        st_total_lians = 0;
        //"Please provide score for option optionname
        for (int i = 0; i < list_modelCOTQuestions.get(index).getOptions().size(); i++) {
            if (list_modelCOTQuestions.get(index).getOptions().get(i).getAnswer().equals("")) {
//                errorLayout.showError("Please provide score for option " + list_qus.get(i).getmAlphabate());
//                errorLayout.hideError();
//                utility.showToast(mContext, "Please provide score for option " + list_qus.get(i).getmAlphabate());
                list_modelCOTQuestions.get(index).getOptions().get(i).setAnswer("0");


            } else {
                list_ans.add(list_modelCOTQuestions.get(index).getOptions().get(i).getAnswer());
                if (list_ans.size() == list_modelCOTQuestions.get(index).getOptions().size()) {
                    for (int j = 0; j < list_ans.size(); j++) {
                        st_total_lians = st_total_lians + Integer.parseInt(list_ans.get(j));
                    }
                    if (st_total_lians == 10) {

                        SubmitApi();
                        //   errorLayout.hideError();
                    } else {
                        utility.showToast(mContext, getString(R.string.please_revise_your_score));
                    }
                }
            }
        }


    }

//    private void Submitvalidation() {
//        list_ans.clear();
//        st_total_lians = 0;
//        //"Please provide score for option optionname
//        for (int i = 0; i < list_modelCOTQuestions.get(index).getOptions().size(); i++) {
//
//            if (list_modelCOTQuestions.get(index).getOptions().get(i).getAnswer() == null) {
////                errorLayout.showError("Please provide score for option " + list_qus.get(i).getmAlphabate());
////                errorLayout.hideError();
////                utility.showToast(mContext, "Please provide score for option " + list_qus.get(i).getmAlphabate());
////
////                return;
//                list_modelCOTQuestions.get(index).getOptions().get(i).setAnswer(list_modelCOTQuestions.get(index).getOptions().get(i).getPoint());
//                list_ans.add(list_modelCOTQuestions.get(index).getOptions().get(i).getPoint());
//            } else {
//                list_ans.add(list_modelCOTQuestions.get(index).getOptions().get(i).getAnswer());
//            }
//        }
//
//        SubmitApi();
////        if (list_ans.size() == list_modelCOTQuestions.get(index).getOptions().size()) {
////            for (int j = 0; j < list_ans.size(); j++) {
////                st_total_lians = st_total_lians + Integer.parseInt(list_ans.get(j));
////            }
//////            if (st_total_lians == 10) {
////
////                utility.showToast(mContext, " total scores  be 10");
////                //   errorLayout.hideError();
//////            } else {
////////                errorLayout.showError("Please revise your scores, total scores should be 10");
//////                utility.showToast(mContext, "Please revise your scores, total scores should be 10");
//////            }
////        }
//
//    }


    private void TotalCount() {
        sum = 0;
        try {
            //    adap_cot_questionlist.notifyItemRangeChanged(0, adap_cot_questionlist.getItemCount());
            // rv_cot_question.smoothScrollToPosition(list_qus.size() - 1);
            for (int i = 0; i < list_qus.size(); i++) {
                int get_ans = Integer.parseInt(list_qus.get(index).getAnswer());
                sum = sum + get_ans;
                if (sum == 10) {
                    //  errorLayout.showError("total scores should be 10");
                    //  utility.showToast(mContext, "total scores be 10");
                }
            }

        } catch (Exception e) {
            Log.e("TotalCount CotQuest", e.toString());
        }

    }

    private void callArray(int index) {

        tv_ques_name.setText(list_modelCOTQuestions.get(index).getQuestion().toString());
        list_qus.clear();
        for (int j = 0; j < list_modelCOTQuestions.get(index).getOptions().size(); j++) {
            String optnm = list_modelCOTQuestions.get(index).getOptions().get(j).getOptionName().toString();
            Long optID = list_modelCOTQuestions.get(index).getOptions().get(j).getOptionId();
            String Opt_point = list_modelCOTQuestions.get(index).getOptions().get(j).getPoint().toString();

            String value = "A";
            int charValue = value.charAt(0);
            String next = String.valueOf((char) (charValue + j));
            Option option = new Option(optnm, optID, Opt_point, next);
            list_qus.add(option);
        }
        for (int i = 0; i < list_qus.size(); i++) {
            if (list_qus.get(i).getAnswer().equals("")) {
                list_qus.get(i).setAnswer(list_qus.get(i).getPoint());
            }
        }
        adap_cot_questionlist = new Adap_COT_Update_Questionlist(index, list_modelCOTQuestions, list_qus, mContext);
        rv_cot_question.setLayoutManager(new LinearLayoutManager(mContext));
//        rv_cot_question.smoothScrollToPosition(list_modelCOTQuestions.size()-1);
        rv_cot_question.setItemAnimator(new DefaultItemAnimator());
//        rv_cot_question.getRecycledViewPool().setMaxRecycledViews(1, list_modelCOTQuestions.size());

        rv_cot_question.setHasFixedSize(true);
        rv_cot_question.setAdapter(adap_cot_questionlist);

        adap_cot_questionlist.notifyDataSetChanged();
        tv_tot_point.setText(10 + "");
        rv_cot_question.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                // rv_cot_question.smoothScrollToPosition(adap_cot_questionlist.getItemCount() - 1);
            }
        });

        if (list_modelCOTQuestions.size() - 1 == index) {
            btn_submit.setVisibility(View.VISIBLE);
            btn_next.setVisibility(View.GONE);
        }
    }


    public void getQuestionList() {
        baseRequest = new BaseRequest(mContext);

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Log.e("api_get_COTQuestions=>", Json);

                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object1 = jsonArray.getJSONObject(i);
                        String questionName = object1.optString("question");
                        Log.e("questionName", questionName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list_modelCOTQuestions = baseRequest.getDataList(jsonArray, ModelCOTTeamUpdate.class);
                list_modelCOTQuestions_copy = baseRequest.getDataList(jsonArray, ModelCOTTeamUpdate.class);
                tv_ques_name.setText(list_modelCOTQuestions.get(0).getQuestion().toString());

                callArray(0);


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
        baseRequest.callAPIGET(1, map, "getCOTteamRoleCompletedAnswers");
    }

    public void SubmitApi() {
        baseRequest = new BaseRequest(mContext);
//        ArrayList<ModelCOTTeamUpdate> list_modelCOTQuestions_copy=null;
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    String msg = "";
                    JSONObject obj = new JSONObject(Json);
                    msg = obj.optString("message");
                    utility.showToast(mContext, msg);
                }catch (Exception e){
                    e.printStackTrace();
                }

                if (checklist != null) {
                    if (checklist.equals("checklist")) {

                        finish();
                    } else {
                     /*Intent in = new Intent(mContext, Cot_Individual.class);
                     startActivity(in);*/
                        finish();

                    }
                } else {
                 /*Intent in = new Intent(mContext, Cot_Individual.class);
                 startActivity(in);*/
                    finish();

                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext, message);
//                errorLayout.showError(message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
//                errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });

        //Perametter on Json --------------------------------------------------------------------------------------------------------


        JsonArray obtion_JArray = null;
        JsonArray answer_JArray = null;
        JsonObject answer_data_Jobject = null;
        JsonObject jsonObject = null;
        JsonObject object = new JsonObject();
        obtion_JArray = new JsonArray();
//        object.addProperty("userId", sessionParam.id);
//        object.addProperty("orgId", sessionParam.orgId);
        answer_JArray = new JsonArray();

        answer_data_Jobject = new JsonObject();
        for (int i = 0; i < list_modelCOTQuestions.size(); i++) {
            answer_data_Jobject.addProperty("questionId", list_modelCOTQuestions.get(i).getQuestionId().toString());
            jsonObject = new JsonObject();
            //position acouning value get
            for (int j = 0; j < list_modelCOTQuestions.get(i).getOptions().size(); j++) {
                jsonObject.addProperty("answerId", list_modelCOTQuestions.get(i).getOptions().get(j).getAnswerId().toString());
                jsonObject.addProperty("point", list_modelCOTQuestions.get(i).getOptions().get(j).getAnswer().toString());
//                jsonObject.addProperty("roleMapId", list_modelCOTQuestions.get(i).getOptions().get(j).getRoleMapId().toString());
                obtion_JArray.add(jsonObject);
                jsonObject = new JsonObject();
            }
            answer_data_Jobject.add("option", obtion_JArray);
            obtion_JArray = new JsonArray();
            answer_JArray.add(answer_data_Jobject);
            answer_data_Jobject = new JsonObject();
//            try {
//
//                for (int j = 0; j < list_qus.get(i).getOption().length(); j++) {
//                    JsonObject option_data_Jobject = new JsonObject();
//                    answer_data_Jobject.addProperty("inputTypeId", list_userdirective.get(i).getInputTypeId());
//                    answer_data_Jobject.addProperty("answer", list_userdirective.get(i).getAnswer());
//                    obtion_JArray.add(list_qus.get(i).getOption().get(j));
//
//                    //---------------------------------------------
//
//                }
//                answer_data_Jobject.add("option", obtion_JArray);
//                option_data_Jobject = new JsonObject();
//                obtion_JArray = new JsonArray();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

        object.add("answer", answer_JArray);
        //-----------------------------------------------------------------------------------------------------------------------------------
        baseRequest.callAPIPost(1, object, ConstantAPI.api_updateCOTteamRoleMapAnswers);
        //-----------------For Testing-----------
//
    }
    @Override
    public int getLayoutId() {
        return R.layout.list_questionlist;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }

}
