package com.chetaru.tribe365_new.UI.Know.Diagnostics;

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

import com.chetaru.tribe365_new.Adapter.Diagnostics.Ad_Tribeometer_list;
import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelTribeometerQusList;
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

public class Act_Tribeometer_list extends BaseActivity {
    BaseRequest baseRequest;
    SessionParam sessionParam;
    Utility utility;
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @BindView(R.id.rv_tribeometer_qus)
    RecyclerView rv_tribeometer_qus;
    @BindView(R.id.btn_submit_tribeometer_qus)
    Button btn_submit_tribeometer_qus;
    ArrayList<ModelTribeometerQusList> li_tribeometer = new ArrayList<>();
    // ArrayList<ModelFunQusAns>modelCOTFuntAns;
    String SaveQuslist = "SaveTribeolist";
    String shred_json_list = "";
    String checklist = "";
    boolean handelBackPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_tribeometer_list);
        try {
            checklist = getIntent().getStringExtra("checklist");
            handelBackPress = getIntent().getBooleanExtra("backHandel", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        //save list aray in shared phrep.-----------------------
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        shred_json_list = prefs.getString(SaveQuslist, "");
        Type type = new TypeToken<ArrayList<ModelTribeometerQusList>>() {
        }.getType();
        call_QuestionList();
    }

    @OnClick({R.id.btn_submit_tribeometer_qus, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.btn_submit_tribeometer_qus:
                int count = 0;
                for (int i = 0; i < li_tribeometer.size(); i++) {
                    if (li_tribeometer.get(i).getmAnswer().toString().equals("")) {
                        count = 0;
                        Toast.makeText(mContext, "Please agree with a statement for Question  " + li_tribeometer.get(i).getQuestionId(), Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        count++;
                    }
                }
                if (count == li_tribeometer.size()) {
                    call_AnswerList();
                }
                break;
        }
    }

    public void call_QuestionList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray = (JSONArray) object;
                li_tribeometer = baseRequest.getDataList(jsonArray, ModelTribeometerQusList.class);

                if (!shred_json_list.isEmpty()) {
                    Log.e("list json", shred_json_list);
                    try {
                        JSONArray jr = new JSONArray(shred_json_list);
                        for (int i = 0; i < jr.length(); i++) {
                            JSONObject jsonObject = jr.getJSONObject(i);
                            String sh_answer = jsonObject.optString("answer");
                            li_tribeometer.get(i).setmAnswer(sh_answer);

//                            String sh_questionName = jsonObject.optString("questionName");
//                            String sh_optionID = jsonObject.optString("optionId");
//                            JSONArray opt_array = jsonObject.getJSONArray("options");
//
//                            for (int j = 0; j < opt_array.length(); j++) {
//                                JSONObject opt_object = opt_array.getJSONObject(j);
//                                String ischeck = opt_object.optString("ischeck");
//                                String initialValue = opt_object.optString("initialValue");
//                                String OptionId = opt_object.optString("OptionId");
//                                String optionName = opt_object.getString("optionName");
//                                String valueName = opt_object.getString("valueName");
//
//
//
//                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                rv_tribeometer_qus.setLayoutManager(new LinearLayoutManager(mContext));
                Ad_Tribeometer_list ad_tribeometer_list = new Ad_Tribeometer_list(li_tribeometer, mContext);
                rv_tribeometer_qus.setAdapter(ad_tribeometer_list);
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
        baseRequest.callAPIGET(1, map, getString(R.string.api_getTribeometerQuestionList));
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
                Toast.makeText(mContext, "Tribeometer answer added successfully.", Toast.LENGTH_SHORT).show();
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
        for (int i = 0; i < li_tribeometer.size(); i++) {
            jsonObject.addProperty("questionId", li_tribeometer.get(i).getQuestionId().toString());
            jsonObject.addProperty("optionId", li_tribeometer.get(i).getmAnswer().toString());
            jsonArray.add(jsonObject);
            jsonObject = new JsonObject();
        }

        object.add("answer", jsonArray);
        Log.e("Ans Array=>", String.valueOf(object));
        baseRequest.callAPIPost(1, object, getString(R.string.api_addTribeometerAnswers));
    }

    @Override
    public void onBackPressed() {


        int count = 0;
//        for (int i = 0; i < li_tribeometer.size(); i++) {
//            if (li_tribeometer.get(i).getmAnswer().toString().equals("")) {
////                count  = 0;
////                Toast.makeText(mContext, "Please agree with a statement for Question  " + li_tribeometer.get(i).getQuestionId(), Toast.LENGTH_SHORT).show();
////                return;
//                count++;
//            }
//        }
//        if (count == li_tribeometer.size()) {
//            count = 0;
//            finish();
//
//        }
//        else{
//
//            savedailogbox();
//            count = 0;
//        }
        for (int i = 0; i < li_tribeometer.size(); i++) {
            if (li_tribeometer.get(i).isFlag() == true) {
                count++;
            }
        }
        if (count != 0) {
            savedailogbox();
            count = 0;
        } else {
            if (handelBackPress) {
                startActivity(new Intent(mContext, Act_Home.class));
            } else {
                finish();
            }
            count = 0;
        }

//        new AlertDialog.Builder(this)
//                .setTitle("Answers will not be saved!")
//                .setMessage("Are you sure you want to go back?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        saveArrayList(li_tribeometer, SaveQuslist);
//                        finish();
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();


    }

    private void savedailogbox() {
        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle(Html.fromHtml("<font color='#515050'>Answers will not be saved!</font>"))
                .setMessage(Html.fromHtml("<font color='#515050'>Do you want to save the answers?</font>"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveArrayList(li_tribeometer, SaveQuslist);
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

    public void saveArrayList(List<ModelTribeometerQusList> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    /******************* initalize layout *********/
    @Override
    public int getLayoutId() {
        return R.layout.act_tribeometer_list;
    }

    /*************** navigation id ********/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }

}