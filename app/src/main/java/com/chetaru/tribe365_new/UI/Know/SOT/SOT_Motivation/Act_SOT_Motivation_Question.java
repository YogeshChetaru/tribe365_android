package com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation;

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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.SOTAdapters.Ad_Sot_Motivation_Question;
import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelSOTMotivationQues;
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

public class Act_SOT_Motivation_Question extends BaseActivity {
    @BindView(R.id.rv_sot_motiviation_quslist)
    RecyclerView rv_sot_motiviation_quslist;
    @BindView(R.id.btn_sot_qus_submit)
    Button btn_sot_qus_submit;
    BaseRequest baseRequest;
    Utility utility;
    SessionParam sessionParam;
    ArrayList<ModelSOTMotivationQues> list_modelCOTQuestions = new ArrayList<>();
    int count = 0;
    String Savelist = "Savelist";
    String shred_json_list = "";
    String checklist = "checklist";
    @BindView(R.id.tribe365)
    ImageView tribe;

    int resultCount = 0;
    boolean handelBackPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_sot_motivaiton_question);
        try {
            if (getIntent().getStringExtra("checklist") == null) {
                checklist = getIntent().getStringExtra("checklist");
            }
            handelBackPress = getIntent().getBooleanExtra("backHandel", false);
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
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe);
            }
        }
        list_modelCOTQuestions.clear();

        //save list aray in shared phrep.-----------------------
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        shred_json_list = prefs.getString(Savelist, "");
        Type type = new TypeToken<ArrayList<ModelSOTMotivationQues>>() {
        }.getType();


        //----------------------------------------------------------------------------------------
        getQuestionList();

        btn_sot_qus_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //---------------------------------------------------------------------------------------remove cod
                count = 0;
                for (int i = 0; i < list_modelCOTQuestions.size(); i++) {
                    for (int j = 0; j < list_modelCOTQuestions.get(i).getOption().size(); j++) {
                        if (list_modelCOTQuestions.get(i).getOption().get(j).getRating().equals("")) {
                            Toast.makeText(mContext, "Please provide score for Question : " + (i + 1), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            count++;
                        }
                    }
                }
                if (count == resultCount) {
                    APi_sot_answer();
                }
            }
        });
    }

    private void call_sharedlist(String shar_list) {
        try {
            JSONArray jr = new JSONArray(shar_list);
            for (int i = 0; i < jr.length(); i++) {
                JSONObject jsonObject = jr.getJSONObject(i);
                String sh_questionId = jsonObject.optString("questionId");
                String sh_questionName = jsonObject.optString("questionName");
                JSONArray opt_array = jsonObject.getJSONArray("option");

                for (int j = 0; j < opt_array.length(); j++) {
                    JSONObject opt_object = opt_array.getJSONObject(j);
                    String categoryId = opt_object.optString("categoryId");
                    String option = opt_object.optString("option");
                    String OptionId = opt_object.optString("OptionId");
                    String rating = opt_object.getString("rating");
                    if (list_modelCOTQuestions.get(i).getOption().get(j).getRating().equals("")) {
                        list_modelCOTQuestions.get(i).getOption().get(j).setRating(rating);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                list_modelCOTQuestions = baseRequest.getDataList(jsonArray, ModelSOTMotivationQues.class);

                if (!(shred_json_list.equals("[]"))) {
                    Log.e("list json", shred_json_list);
                    try {
                        JSONArray jr = new JSONArray(shred_json_list);
                        for (int i = 0; i < jr.length(); i++) {
                            JSONObject jsonObject = jr.getJSONObject(i);
                            String sh_questionId = jsonObject.optString("questionId");
                            String sh_questionName = jsonObject.optString("questionName");
                            JSONArray opt_array = jsonObject.getJSONArray("option");

                            for (int j = 0; j < opt_array.length(); j++) {
                                JSONObject opt_object = opt_array.getJSONObject(j);
                                String categoryId = opt_object.optString("categoryId");
                                String option = opt_object.optString("option");
                                String OptionId = opt_object.optString("OptionId");
                                String rating = opt_object.getString("rating");
                                list_modelCOTQuestions.get(i).getOption().get(j).setRating(rating);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                resultCount = list_modelCOTQuestions.size() * 2;
                Ad_Sot_Motivation_Question adap_sot_questionlist = new Ad_Sot_Motivation_Question(list_modelCOTQuestions, shred_json_list, mContext);
                rv_sot_motiviation_quslist.setHasFixedSize(true);
                rv_sot_motiviation_quslist.setAdapter(adap_sot_questionlist);
                rv_sot_motiviation_quslist.setLayoutManager(new LinearLayoutManager(mContext));
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
        baseRequest.callAPIGET(1, map, ConstantAPI.api_getSOTmotivationQuestions);
    }

    public void APi_sot_answer() {
        baseRequest = new BaseRequest(mContext);

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Log.e("api_get_SOTQuestions=>", Json);
//clear shared pre.
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

                Gson gson = new Gson();
                shred_json_list = prefs.getString(Savelist, "");
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                //------clear------------------
                Toast.makeText(mContext, "SOT motivation answer added successfully.", Toast.LENGTH_SHORT).show();
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
                subobject.addProperty("optionId", list_modelCOTQuestions.get(i).getOption().get(j).getOptionId());
                subobject.addProperty("rating", list_modelCOTQuestions.get(i).getOption().get(j).getRating());
                subAry.add(subobject);
                subobject = new JsonObject();
            }
            JsonObject qus_json = new JsonObject();
            qus_json.addProperty("questionId", list_modelCOTQuestions.get(i).getQuestionId());
            qus_json.add("option", subAry);
            jsonArray.add(qus_json);
            subAry = new JsonArray();

        }


        mainObject.add("answer", jsonArray);

        Log.e("Ans SOT=>", jsonArray + "");
        //-----------------------------------------------------------------------------------------------------------------------------------
        baseRequest.callAPIPost(1, mainObject,ConstantAPI.api_addSOTmotivationAnswer);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

//
//        saveArrayList(list_modelCOTQuestions, Savelist);
//        finish();

//        count = 0;
//        for (int i = 0; i < list_modelCOTQuestions.size(); i++) {
//            for (int j = 0; j < list_modelCOTQuestions.get(i).getOption().size(); j++) {
//                if (list_modelCOTQuestions.get(i).getOption().get(j).getRating().equals("")) {
//                    count++;
//                }
//            }
//        }
//        if (count == 120) {
//            count=0;
//            finish();
//        }
//        else {
//            savedailogbox();
//        }
        count = 0;
        for (int i = 0; i < list_modelCOTQuestions.size(); i++) {

            if (list_modelCOTQuestions.get(i).isFalg() == true) {
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

    public void saveArrayList(List<ModelSOTMotivationQues> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    private void savedailogbox() {
        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle(Html.fromHtml("<font color='#515050'>Answers will not be saved!</font>"))
                .setMessage(Html.fromHtml("<font color='#515050'>Do you want to save the answers?</font>"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        saveArrayList(list_modelCOTQuestions, Savelist);

                        finish();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
        return R.layout.act_sot_motivaiton_question;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}
