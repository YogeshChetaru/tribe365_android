package com.chetaru.tribe365_new.UI.Know.COT;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
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
import com.chetaru.tribe365_new.Adapter.Adap_COT_Questionlist;
import com.chetaru.tribe365_new.Adapter.COTAdapters.Adap_New_COT_Questionlist;
import com.chetaru.tribe365_new.API.Models.ModelCOTQuestion;
import com.chetaru.tribe365_new.API.Models.Option;
import com.chetaru.tribe365_new.R;

import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Cot_New_Question extends BaseActivity implements Adap_New_COT_Questionlist.AdapterCallback {
    private static final String TAG = Cot_New_Question.class.getSimpleName();
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_tot_point)
    public TextView tv_tot_point;
    public Context mContext;
    public int index = 0;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_cot_question)
    RecyclerView rv_cot_question;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_ques_name)
    TextView tv_ques_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_next)
    Button btn_next;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button btn_submit;
    //    private ErrorLayout errorLayout;
    BaseRequest baseRequest;
    Utility utility;
    String companyString = "";
    SessionParam sessionParam;
    int sum = 0;
    ArrayList<ModelCOTQuestion> list_modelCOTQuestions = new ArrayList<>();
    ArrayList<Option> list_qus = new ArrayList<>();
    NumberPicker numberPicker;
    Adap_COT_Questionlist adap_cot_questionlist = new Adap_COT_Questionlist();
    ArrayList<String> list_ans = new ArrayList<>();
    int st_total_lians = 0;
    int count = 0;
    String SaveQuslist = "SaveQuslist";
    String shred_json_list = "";
    int total_points = 0;
    String checklist = " ";
    boolean handelBackPress = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.list_questionlist);
        list_qus.clear();
        list_modelCOTQuestions.clear();
        try {
            checklist = getIntent().getStringExtra("checklist");
            handelBackPress = getIntent().getBooleanExtra("backHandel", false);
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

        if (!sessionParam.organisation_logo.equals("")) {
            Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
        }

        companyString = sessionParam.companyList;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        shred_json_list = prefs.getString(SaveQuslist, "");
        Type type = new TypeToken<ArrayList<ModelCOTQuestion>>() {
        }.getType();
    }

    @OnClick({R.id.btn_next, R.id.btn_submit, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:

                TotalCount();
                validation();
//                errorLayout.hideError();
//                index = index + 1;
//                callArray(index);
                break;
            case R.id.btn_submit:
                Submitvalidation();

                break;
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
        }
    }


    //"Please provide score for option optionname
    private void validation() {
        list_ans.clear();
        st_total_lians = 0;
        //"Please provide score for option optionname
        for (int i = 0; i < list_modelCOTQuestions.get(index).getOption().size(); i++) {
            if (list_modelCOTQuestions.get(index).getOption().get(i).getmAnswer().equals("")) {
//                errorLayout.showError("Please provide score for option " + list_qus.get(i).getmAlphabate());
//                errorLayout.hideError();
                list_modelCOTQuestions.get(index).getOption().get(i).setmAnswer("0");
//                utility.showToast(mContext, "Please provide score for option " + list_qus.get(i).getmAlphabate());
//                return;

            }

            list_ans.add(list_modelCOTQuestions.get(index).getOption().get(i).getmAnswer());
            if (list_ans.size() == list_modelCOTQuestions.get(index).getOption().size()) {
                for (int j = 0; j < list_ans.size(); j++) {
                    st_total_lians = st_total_lians + Integer.parseInt(list_ans.get(j));
                }
                if (st_total_lians == 10) {
                    index = index + 1;
                    tv_tot_point.setText("0");
                    callArray(index);
                    //   errorLayout.hideError();
                } else {
                    utility.showToast(mContext, getString(R.string.please_revise_your_score));
                }

            }
        }


    }

    private void Submitvalidation() {
        list_ans.clear();
        st_total_lians = 0;
        //"Please provide score for option optionname
        for (int i = 0; i < list_modelCOTQuestions.get(index).getOption().size(); i++) {
            if (list_modelCOTQuestions.get(index).getOption().get(i).getmAnswer().equals("")) {
//                errorLayout.showError("Please provide score for option " + list_qus.get(i).getmAlphabate());
//                errorLayout.hideError();
//                utility.showToast(mContext, "Please provide score for option " + list_qus.get(i).getmAlphabate());
                list_modelCOTQuestions.get(index).getOption().get(i).setmAnswer("0");


            } else {
                list_ans.add(list_modelCOTQuestions.get(index).getOption().get(i).getmAnswer());
                if (list_ans.size() == list_modelCOTQuestions.get(index).getOption().size()) {
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


    private void TotalCount() {
        sum = 0;
        try {
            for (int i = 0; i < list_qus.size(); i++) {
                int get_ans = Integer.parseInt(list_qus.get(index).getmAnswer());
                sum = sum + get_ans;
                if (sum == 10) {
                    //  errorLayout.showError("total scores should be 10");
//                    utility.showToast(mContext, "total scores should be 10");
                }
            }
            for (int i = 0; i < list_modelCOTQuestions.size(); i++) {
                for (int j = 0; j < list_qus.size(); j++) {
                    if (list_qus.get(j).isFlag() == true) {
                        list_modelCOTQuestions.get(i).setFlag(true);
                    }
                }
            }

        } catch (Exception e) {
            Log.e("TotalCount CotQuest", e.toString());
        }

    }

    private void callArray(int index) {
        if (!(shred_json_list.equals(""))) {
            Log.e("list json", shred_json_list);
            list_qus.clear();
            try {
                JSONArray jr = new JSONArray(shred_json_list);
//                for (int i = 0; i < jr.length(); i++) {
                JSONObject jsonObject = jr.getJSONObject(index);
                String questionId = jsonObject.optString("questionId");
                String questionName = jsonObject.optString("questionName");
                tv_ques_name.setText(questionName);

                JSONArray jsonArray = jsonObject.getJSONArray("option");
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject object = jsonArray.getJSONObject(j);
                    String ans = object.optString("answer");
                    String optnm = object.optString("option");
                    String optID = object.optString("OptionId");
                    String Opt_mapId = object.optString("roleMapId");
                    list_modelCOTQuestions.get(index).getOption().get(j).setmAnswer(ans);
                    String value = "A";
                    int charValue = value.charAt(0);
                    String next = String.valueOf((char) (charValue + j));
                    Long optid = Long.valueOf(optID);
                    Option option = new Option(optnm, optid, Opt_mapId, next, ans);
                    list_qus.add(option);

//                    }
                }
            } catch (Exception e) {
                Log.e("Exp=>", e.toString());
            }
        } else {
            tv_ques_name.setText(list_modelCOTQuestions.get(index).getQuestionName().toString());
            list_qus.clear();
            for (int j = 0; j < list_modelCOTQuestions.get(index).getOption().size(); j++) {

                list_modelCOTQuestions.get(index).getOption().get(j).setmAnswer("0");
                String optnm = list_modelCOTQuestions.get(index).getOption().get(j).getOption().toString();
                Long optID = list_modelCOTQuestions.get(index).getOption().get(j).getOptionId();
                String Opt_mapId = list_modelCOTQuestions.get(index).getOption().get(j).getRoleMapId().toString();
                String value = "A";
                String ans = "0";
                int charValue = value.charAt(0);
                String next = String.valueOf((char) (charValue + j));
                Option option = new Option(optnm, optID, Opt_mapId, next, ans);
                list_qus.add(option);
            }
        }
        for (int i = 0; i < list_qus.size(); i++) {
            if (list_qus.get(i).getmAnswer().equals("")) {
                list_qus.get(i).setmAnswer("0");
            }
        }

        Adap_New_COT_Questionlist adap_cot_questionlist = new Adap_New_COT_Questionlist(index, list_modelCOTQuestions, list_qus, mContext);
        rv_cot_question.setLayoutManager(new LinearLayoutManager(mContext));
        rv_cot_question.setItemAnimator(new DefaultItemAnimator());
        rv_cot_question.setHasFixedSize(true);
        rv_cot_question.setAdapter(adap_cot_questionlist);
        adap_cot_questionlist.notifyDataSetChanged();
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
                        String questionName = object1.optString("questionName");
                        Log.e("questionName", questionName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                list_modelCOTQuestions = baseRequest.getDataList(jsonArray, ModelCOTQuestion.class);
                tv_ques_name.setText(list_modelCOTQuestions.get(0).getQuestionName().toString());
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
        baseRequest.callAPIGET(1, map, ConstantAPI.api_get_COTQuestions);
    }

    public void SubmitApi() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                //utility.showToast(mContext, "COT_new_Ques answer added successfully.");
                try {
                    String msg = "";
                    JSONObject obj = new JSONObject(Json);
                    msg = obj.optString("message");
                    utility.showToast(mContext, msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                if (checklist != null) {
                    if (checklist.equals("")) {


                        finish();
                    } else {
                        finish();
                    }
                } else {


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
        object.addProperty("userId", sessionParam.id);
        object.addProperty("orgId", sessionParam.orgId);
        answer_JArray = new JsonArray();

        answer_data_Jobject = new JsonObject();
        for (int i = 0; i < list_modelCOTQuestions.size(); i++) {
            answer_data_Jobject.addProperty("questionId", list_modelCOTQuestions.get(i).getQuestionId().toString());
            jsonObject = new JsonObject();
            //position acouning value get
            for (int j = 0; j < list_modelCOTQuestions.get(i).getOption().size(); j++) {
                jsonObject.addProperty("optionId", list_modelCOTQuestions.get(i).getOption().get(j).getmOptionId().toString());
                jsonObject.addProperty("point", list_modelCOTQuestions.get(i).getOption().get(j).getmAnswer().toString());
                jsonObject.addProperty("roleMapId", list_modelCOTQuestions.get(i).getOption().get(j).getRoleMapId().toString());
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
        baseRequest.callAPIPost(1, object,ConstantAPI.addCOTAnswer);
        //-----------------For Testing-----------

    }

    public void saveArrayList(List<ModelCOTQuestion> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        count = 0;
//        for (int i = 0; i < li_TotQus.size(); i++) {
//
//            for (int j = 0; j < li_TotQus.get(i).getmSotQusDetails().size(); j++) {
//                if (li_TotQus.get(i).getmSotQusDetails().get(j).isCheck() == true) {
//                    count ++;
//                }
//            }
//
//        }
//        if (count ==0) {
//            count=0;
//            finish();
//        }
//        else{
//            savedailogbox();
//        }
        for (int i = 0; i < list_modelCOTQuestions.size(); i++) {
            for (int j = 0; j < list_qus.size(); j++) {
                if (list_qus.get(j).isFlag() == true) {
                    list_modelCOTQuestions.get(i).setFlag(true);
                }
            }
        }
        count = 0;
        for (int i = 0; i < list_modelCOTQuestions.size(); i++) {

            if (list_modelCOTQuestions.get(i).isFlag() == true) {
                count++;
            }
        }
        if (count != 0) {
            savedailogbox();
            count = 0;

        } else {
            count = 0;
            if (handelBackPress) {
                startActivity(new Intent(mContext, Act_Home.class));
            } else {
                finish();
            }
            //------------Intent Comment plz check reffrect any after pages comment releted to CheckList pages

           /* Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);*/

            //moveTaskToBack(false);
            //finish();

        }
    }

    private void savedailogbox() {
        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle(Html.fromHtml("<font color='#515050'>Answers will not be saved!</font>"))
                .setMessage(Html.fromHtml("<font color='#515050'>Do you want to save the answers?</font>"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        saveArrayList(list_modelCOTQuestions, SaveQuslist);
                        //------------Intent Comment plz check reffrect any after pages comment releted to CheckList pages
                        finish();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //------------Intent Comment plz check reffrect any after pages comment releted to CheckList pages

                        finish();

                    }
                })
                .show();

        final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
//        layoutParams.weight = 20;
        positiveButton.setLayoutParams(layoutParams);
        positiveButton.setLayoutParams(layoutParams);
        positiveButton.setTextColor(getResources().getColor(R.color.color_0_red));

        final Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        layoutParams = (LinearLayout.LayoutParams) negativeButton.getLayoutParams();
//        layoutParams.weight = 10;
        negativeButton.setLayoutParams(layoutParams);
        negativeButton.setLayoutParams(layoutParams);
        negativeButton.setTextColor(getResources().getColor(R.color.cardback));
//
//        final Button NeutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
//        layoutParams = (LinearLayout.LayoutParams) NeutralButton.getLayoutParams();
//        layoutParams.weight = 10;
//        NeutralButton.setLayoutParams(layoutParams);
//        NeutralButton.setLayoutParams(layoutParams);
//        NeutralButton.setTextColor(getResources().getColor(R.color.cardback));
    }
    @Override
    public int getLayoutId() {
        return R.layout.list_questionlist;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}
