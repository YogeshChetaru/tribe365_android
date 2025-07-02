package com.chetaru.tribe365_new.UI.Know.SOT;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelSOTOPTIONLIST;
import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelSOTQuesListResponse;
import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelSOTQusList;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.SOTAdapters.Adap_SOT_Questionlist;
import com.chetaru.tribe365_new.R;
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


public class Act_SOT_Questionlist extends BaseActivity {
    @BindView(R.id.rv_sot_quslist)
    RecyclerView rv_sot_quslist;
    @BindView(R.id.btn_sot_qus_submit)
    Button btn_sot_qus_submit;
    Utility utility;
    SessionParam sessionParam;


    BaseRequest baseRequest;
    Adap_SOT_Questionlist adap_sot_questionlist;
    ArrayList<ModelSOTQuesListResponse> li_diagnostics = new ArrayList<>();
    ArrayList<ModelSOTOPTIONLIST> li_qus = new ArrayList<>();
    ArrayList<ModelSOTQusList> li_TotQus = new ArrayList<>();
    ArrayList<String> li_answers = new ArrayList<String>();
    int count = 0;
    String SaveQuslist = "SaveQuslist";
    String shred_json_list = "";
    String checklist = "";
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe;
    boolean handelBackPress = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sot_question_list);
        try {
            checklist = getIntent().getStringExtra("checklist");
            handelBackPress = getIntent().getBooleanExtra("backHandel", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        init();
        tribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
    }

    @SuppressLint("TimberArgCount")
    private void init() {
        ButterKnife.bind(this);

        utility = new Utility();
        // this.mContext = this;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        try {
            sessionParam = new SessionParam(mContext);
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        shred_json_list = prefs.getString(SaveQuslist, "");
        Log.e("shred_json_list=>", shred_json_list.toString());
        Type type = new TypeToken<ArrayList<ModelSOTQusList>>() {
        }.getType();
        APi_getQuestionList();
    }

    public void APi_getQuestionList() {
        baseRequest = new BaseRequest(mContext);

        li_qus.clear();
        li_TotQus.clear();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @SuppressLint("TimberArgCount")
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Log.e("api_get_SOTQuestions=>", Json);

                // JSONArray jsonArray;
                // jsonArray = (JSONArray) object;

               /* try {

                    JSONArray jsonArray = (JSONArray) object;
                    li_diagnostics = baseRequest.getDataList(jsonArray, ModelSOTQuesListResponse.class);
                    Log.e("api_get_SOTQuestions=>", li_diagnostics.get(0).getQuestion().toString());

                   try{
                       for (int i=0;i<li_diagnostics.size();i++){
                           li_qus = new ArrayList<>();
                           for (int j = 0; j < li_diagnostics.get(i).getOption().size(); j++) {
                               String id = li_diagnostics.get(i).getOption().get(j).getId();
                               String section = li_diagnostics.get(i).getOption().get(j).getSection();
                               String type = li_diagnostics.get(i).getOption().get(j).getType();
                               String question = li_diagnostics.get(i).getOption().get(j).getQuestion();
                               Log.e("Question", question);

                               ModelSOTOPTIONLIST modelSOTOPTIONLIST = new ModelSOTOPTIONLIST(id, question, section, type);
                               li_qus.add(modelSOTOPTIONLIST);
                           }
                           ModelSOTQusList modelSOTQusList = new ModelSOTQusList(li_diagnostics.get(i).getQuestion() + "", li_qus);
                           li_TotQus.add(modelSOTQusList);
                          *//* if (!(shred_json_list.equals("[]"))) {
                               Log.e("list json", shred_json_list);

                               try {
                                   JSONArray jr = new JSONArray(shred_json_list);
                                   for (int k = 0; k < jr.length(); k++) {
                                       JSONObject jsonObject = jr.getJSONObject(k);
                                       String qusno = jsonObject.optString("qusno");
//                            String sh_questionName = jsonObject.optString("questionName");
//                            String sh_optionID = jsonObject.optString("optionId");
                                       JSONArray opt_array = jsonObject.getJSONArray("sotQusDetail");
                                       for (int j = 0; j < opt_array.length(); j++) {
                                           JSONObject opt_object = opt_array.getJSONObject(j);
                                           String ischeck = opt_object.optString("ischeck");
//                                String id = opt_object.optString("id");
//                                String OptionId = opt_object.optString("OptionId");
//                                String optionName = opt_object.getString("optionName");
//                                String valueName = opt_object.getString("valueName");
                                           if (ischeck.equals("true")) {
                                               li_TotQus.get(k).getmSotQusDetails().get(j).setCheck(true);
                                           } else {
                                               li_TotQus.get(k).getmSotQusDetails().get(j).setCheck(false);
                                           }
                                       }
                                   }
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                           }*//*



                       }
                    *//*

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                        li_qus = new ArrayList<>();
                        // JsonObject object1= (JsonObject) jsonArray1.get(0);
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject = jsonArray1.getJSONObject(j);
                            String id = jsonObject.optString("id");
                            String section = jsonObject.optString("section");
                            String type = jsonObject.optString("type");
                            String question = jsonObject.optString("question");
                            Log.e("Question", question);

                            ModelSOTOPTIONLIST modelSOTOPTIONLIST = new ModelSOTOPTIONLIST(id, question, section, type);
                            li_qus.add(modelSOTOPTIONLIST);
                        }

                    }*//*
                   }catch(Exception e){
                       e.printStackTrace();
                   }
                    adap_sot_questionlist = new Adap_SOT_Questionlist(li_TotQus, mContext);
                    rv_sot_quslist.setHasFixedSize(true);
                    rv_sot_quslist.setAdapter(adap_sot_questionlist);
                    rv_sot_quslist.setLayoutManager(new LinearLayoutManager(mContext));


                }catch (Exception e){
                    e.printStackTrace();
                }*/
                try {
                    JSONArray jsonArray = (JSONArray) object;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String question1 = jsonObject2.optString("question");

                        JSONArray jsonArray1 = jsonObject2.getJSONArray("option");
                        li_qus = new ArrayList<>();
                        // JsonObject object1= (JsonObject) jsonArray1.get(0);
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject = jsonArray1.getJSONObject(j);
                            String id = jsonObject.optString("id");
                            String section = jsonObject.optString("section");
                            String type = jsonObject.optString("type");
                            String question = jsonObject.optString("question");
                            Log.e("Question", question);

                            ModelSOTOPTIONLIST modelSOTOPTIONLIST = new ModelSOTOPTIONLIST(id, question, section, type);
                            li_qus.add(modelSOTOPTIONLIST);
                        }
                        ModelSOTQusList modelSOTQusList = new ModelSOTQusList(question1 + "", li_qus);
                        li_TotQus.add(modelSOTQusList);
                        if (!(shred_json_list.equals("[]"))) {
                            Log.e("list json", shred_json_list.toString());

                            try {
                                JSONArray jr = new JSONArray(shred_json_list);
                                Log.e("list_json_list", jr.toString());
                                for (int k = 0; k < jr.length(); k++) {
                                    JSONObject jsonObject = jr.getJSONObject(k);
                                    String qusno = jsonObject.optString("qusno");
                                    String flag = jsonObject.optString("flag");
//                            String sh_questionName = jsonObject.optString("questionName");
//                            String sh_optionID = jsonObject.optString("optionId");

                                    JSONArray opt_array = jsonObject.getJSONArray("sotQusDetail");
                                    for (int j = 0; j < opt_array.length(); j++) {
                                        JSONObject opt_object = opt_array.getJSONObject(j);
                                        String type = opt_object.optString("type");
                                        String section = opt_object.optString("section");
                                        String ischeck = opt_object.optString("ischeck");

//                                String OptionId = opt_object.optString("OptionId");
//                                String optionName = opt_object.getString("optionName");
//                                String valueName = opt_object.getString("valueName");
                                        if (ischeck.equals("true")) {
                                            li_TotQus.get(k).getmSotQusDetails().get(j).setCheck(true);
                                        } else {
                                            li_TotQus.get(k).getmSotQusDetails().get(j).setCheck(false);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        adap_sot_questionlist = new Adap_SOT_Questionlist(li_TotQus, mContext);
                        rv_sot_quslist.setHasFixedSize(true);
                        rv_sot_quslist.setAdapter(adap_sot_questionlist);
                        rv_sot_quslist.setLayoutManager(new LinearLayoutManager(mContext));
                    }

                } catch (Exception e) {
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
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        baseRequest.callAPIGET(1, map, ConstantAPI.api_sot_quest);
    }

    @OnClick({R.id.btn_sot_qus_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sot_qus_submit:
                call_Validation();
                break;
        }
    }

    private void call_Validation() {
        li_answers.clear();

//        //clear shared pre.
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.clear();
//        editor.commit();
//        //------clear------------------
        for (int i = 0; i < li_TotQus.size(); i++) {
            count = 0;
            for (int j = 0; j < li_TotQus.get(i).getmSotQusDetails().size(); j++) {
                if (li_TotQus.get(i).getmSotQusDetails().get(j).isCheck() == true) {
                    li_answers.add(li_TotQus.get(i).getmSotQusDetails().get(j).getId().toString());
                } else {
                    count++;
                }
                // count++;

            }

            if (count >= 4) {
                int qusno = i + 1;
                Toast.makeText(mContext, "Please agree with a statement for Question " + qusno, Toast.LENGTH_SHORT).show();
                count = 0;
                return;
            }
            if (li_answers.size() == li_TotQus.size()) {
                //clear shared pre.

                //------clear------------------
                APi_sot_answer();
//                Toast.makeText(mContext, "Submit Clear data", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void APi_sot_answer() {
        baseRequest = new BaseRequest(mContext);

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Log.e("api_get_SOTQuestions=>", Json);
                //clear shared pre.
                li_TotQus.clear();
                li_qus.clear();
                li_answers.clear();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                //------clear------------------
                if (checklist != null) {
                    if (checklist.equals("checklist")) {
                        finish();
                    } else {

                        finish();
                    }
                } else {
                    finish();
                }
                Toast.makeText(mContext, "SOT answer added successfully.", Toast.LENGTH_SHORT).show();
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

        JsonObject main_object = new JsonObject();
        JsonObject sub_object = new JsonObject();
        JsonArray main_array = new JsonArray();
        for (int i = 0; i < li_answers.size(); i++) {
            sub_object.addProperty("id", li_answers.get(i).toString());
            main_array.add(sub_object);
            sub_object = new JsonObject();
        }
//      main_object.add("answer",main_array);

//      JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId
        main_object.add("answer", main_array);
        baseRequest.callAPIPost(1, main_object,ConstantAPI.api_sot_addSOTanswers);
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
        count = 0;
        for (int i = 0; i < li_TotQus.size(); i++) {
            if (li_TotQus.get(i).isFlag() == true) {
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

        }
    }

    private void savedailogbox() {
        AlertDialog dialog = new AlertDialog.Builder(this)
//              .setTitle(Html.fromHtml("<font color='#515050'>Answers will not be saved!</font>"))
                .setMessage(Html.fromHtml("<font color='#515050'>Do you want to save the answers?</font>"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (checklist != null) {
                            if (checklist.equals("checklist")) {
                                saveArrayList(li_TotQus, SaveQuslist);

                                finish();
                            } else {
                                saveArrayList(li_TotQus, SaveQuslist);
                                finish();
                            }
                        } else {
                            saveArrayList(li_TotQus, SaveQuslist);
                            finish();
                        }
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checklist != null) {
                            if (checklist.equals("checklist")) {

                                finish();
                            } else {

                                finish();
                            }
                        } else {

                            finish();
                        }
                    }
                })
                .show();

        final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
//      layoutParams.weight = 20;
        positiveButton.setLayoutParams(layoutParams);
        positiveButton.setLayoutParams(layoutParams);
        positiveButton.setTextColor(getResources().getColor(R.color.color_0_red));

        final Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        layoutParams = (LinearLayout.LayoutParams) negativeButton.getLayoutParams();
//      layoutParams.weight = 10;
        negativeButton.setLayoutParams(layoutParams);
        negativeButton.setLayoutParams(layoutParams);
        negativeButton.setTextColor(getResources().getColor(R.color.cardback));

//      final Button NeutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
//      layoutParams = (LinearLayout.LayoutParams) NeutralButton.getLayoutParams();
//      layoutParams.weight = 10;
//      NeutralButton.setLayoutParams(layoutParams);
//      NeutralButton.setLayoutParams(layoutParams);
//      NeutralButton.setTextColor(getResources().getColor(R.color.cardback));
    }

    public void saveArrayList(List<ModelSOTQusList> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    @Override
    public int getLayoutId() {
        return R.layout.sot_question_list;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
