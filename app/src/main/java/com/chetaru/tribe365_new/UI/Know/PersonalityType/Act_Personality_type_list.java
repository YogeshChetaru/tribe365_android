package com.chetaru.tribe365_new.UI.Know.PersonalityType;

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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelDiagnosticsQusList;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.personality.Ad_personality_list;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Act_Personality_type_list extends BaseActivity {

    @BindView(R.id.tribe365)
    ImageView tribe365;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    Utility utility;
    @BindView(R.id.rv_personality_qustion)
    RecyclerView rv_diagnostic_qustion;
    @BindView(R.id.btn_submit_personality_Qus)
    Button btn_submit_diagnostic_Qus;
    ArrayList<ModelDiagnosticsQusList> li_diagnostics = new ArrayList<>();
    String SaveQuslist = "SaveDiagnlist";
    String shred_json_list = "";
    String checklist = "";
    boolean handelBackPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act__personality_type_list);
        try {
            checklist = getIntent().getStringExtra("checklist");
            handelBackPress = getIntent().getBooleanExtra("backHandel", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //save list aray in shared phrep.-----------------------
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        shred_json_list = prefs.getString(SaveQuslist, "");
        call_QuestionList();
        inti();
    }

    private void inti() {
        ButterKnife.bind(this);
        sessionParam = new SessionParam(mContext);
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        utility = new Utility();
    }


    private void call_QuestionList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray = (JSONArray) object;
                li_diagnostics = baseRequest.getDataList(jsonArray, ModelDiagnosticsQusList.class);
                //---------------
                if (!(shred_json_list.equals("[]"))) {
//                    Log.e("list json", shred_json_list);
                    try {
                        JSONArray jr = new JSONArray(shred_json_list);
                        for (int i = 0; i < jr.length(); i++) {
                            JSONObject jsonObject = jr.getJSONObject(i);
                            String sh_answer = jsonObject.optString("answer");
                            li_diagnostics.get(i).setmAnswer(sh_answer);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //--------------------------
                rv_diagnostic_qustion.setLayoutManager(new LinearLayoutManager(mContext));
                Ad_personality_list ad_diagnostics_list = new Ad_personality_list(li_diagnostics, mContext);
                rv_diagnostic_qustion.setAdapter(ad_diagnostics_list);

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
        baseRequest.callAPIGET(1, map, ConstantAPI.api_getPersonalityTypeQuestionList);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.btn_submit_personality_Qus, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.btn_submit_personality_Qus:
                int count = 0;
                for (int i = 0; i < li_diagnostics.size(); i++) {
                    if (li_diagnostics.get(i).getmAnswer().toString().equals("")) {
                        count = 0;
                        Toast.makeText(mContext, "Please agree with a statement for Question  " + li_diagnostics.get(i).getQuestionId(), Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        count++;
                    }
                }
                if (count == li_diagnostics.size()) {
                    call_AnswerList();
                }
                break;
        }
    }

    public void call_AnswerList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                // JSONArray jsonArray = (JSONArray) object;
                //clear shared pre.
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                //------clear------------------
                Toast.makeText(mContext, "Personality Type answer added successfully.", Toast.LENGTH_SHORT).show();
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
        JsonObject jsonObject = null;
        JsonArray jsonArray = null;
        JsonObject object = null;
        jsonObject = new JsonObject();
        jsonArray = new JsonArray();
        object = new JsonObject();
        for (int i = 0; i < li_diagnostics.size(); i++) {
            jsonObject.addProperty("questionId", li_diagnostics.get(i).getQuestionId().toString());
            jsonObject.addProperty("optionId", li_diagnostics.get(i).getmAnswer().toString());
            jsonArray.add(jsonObject);
            jsonObject = new JsonObject();
        }

        object.add("answer", jsonArray);
        Log.e("Ans Array=>", String.valueOf(object));
        //baseRequest.callAPIPost(1, object, getString(R.string.api_addDiagnosticAnswers));
        baseRequest.callAPIPost(1, object,ConstantAPI.api_addPersonalityTypeAnswers );
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBackPressed() {
        int count = 0;
//        for (int i = 0; i < li_diagnostics.size(); i++) {
//            if (li_diagnostics.get(i).getmAnswer().toString().equals("")) {
////                count  = 0;
////                Toast.makeText(mContext, "Please agree with a statement for Question  " + li_tribeometer.get(i).getQuestionId(), Toast.LENGTH_SHORT).show();
////                return;
//                count++;
//            }
//        }
//        if (count == li_diagnostics.size()) {
//            finish();
//            count = 0;
//        }
        for (int i = 0; i < li_diagnostics.size(); i++) {
            if (li_diagnostics.get(i).isFlag() == true) {

                count++;
            }

        }
        if (count != 0) {
            callSaveDialog();
            count = 0;
            ;
        } else {
            if (handelBackPress) {
                startActivity(new Intent(mContext, Act_Home.class));
            } else {
                finish();
            }
            count = 0;
        }
    }

    private void callSaveDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle("Answers will not be saved!")
//              .setTitle(Html.fromHtml("<font color='#515050'>Answers will not be saved!</font>"))
                .setMessage(Html.fromHtml("<font color='#515050'>Do you want to save the answers?</font>"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveArrayList(li_diagnostics, SaveQuslist);
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

    public void saveArrayList(List<ModelDiagnosticsQusList> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }
    @Override
    public int getLayoutId() {
        return R.layout.act__personality_type_list;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}
