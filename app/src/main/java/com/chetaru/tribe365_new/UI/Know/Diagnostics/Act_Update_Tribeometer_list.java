package com.chetaru.tribe365_new.UI.Know.Diagnostics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.Adapter.Diagnostics.Ad_Update_Tribeometer_list;
import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.Update.ModelUpdateDiagnostics;
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

public class Act_Update_Tribeometer_list extends BaseActivity {
    BaseRequest baseRequest;
    SessionParam sessionParam;
    Utility utility;
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @BindView(R.id.rv_tribeometer_qus)
    RecyclerView rv_tribeometer_qus;
    @BindView(R.id.btn_submit_tribeometer_qus)
    Button btn_submit_tribeometer_qus;
    ArrayList<ModelUpdateDiagnostics> li_tribeometer = new ArrayList<>();
    //    ArrayList<ModelFunQusAns>modelCOTFuntAns;
    String checklist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_tribeometer_list);
        try {
            checklist = getIntent().getStringExtra("checklist");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        call_QuestionList();
    }

    @OnClick({R.id.btn_submit_tribeometer_qus, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.btn_submit_tribeometer_qus:
                call_AnswerList();
//                int count = 0;
//                for (int i = 0; i < li_tribeometer.size(); i++) {
//                    if (li_tribeometer.get(i).getmAnswer().toString().equals("")) {
//                        count = 0;
//                        Toast.makeText(mContext, "Please agree with a statement for Question  " + li_tribeometer.get(i).getQuestionId(), Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        count++;
//                    }
//                }
//                if (count == li_tribeometer.size()) {
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

                li_tribeometer = baseRequest.getDataList(jsonArray, ModelUpdateDiagnostics.class);
                Ad_Update_Tribeometer_list ad_tribeometer_list = new Ad_Update_Tribeometer_list(li_tribeometer, mContext);
                rv_tribeometer_qus.setAdapter(ad_tribeometer_list);
                rv_tribeometer_qus.setLayoutManager(new LinearLayoutManager(mContext));
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
        baseRequest.callAPIGET(1, map, getString(R.string.api_getTribeometerCompletedAnswers));
    }

    public void call_AnswerList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                //  JSONArray jsonArray = (JSONArray) object;
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
            if (li_tribeometer.get(i).getmAnswer().equals("")) {
                for (int j = 0; j < li_tribeometer.get(i).getOptions().size(); j++) {
                    if (li_tribeometer.get(i).getOptions().get(j).getIsChecked().equals(true)) {
                        jsonObject.addProperty("optionId", li_tribeometer.get(i).getOptions().get(j).getOptionId());
                    }
                }

            }
            jsonArray.add(jsonObject);
            jsonObject = new JsonObject();
        }

        object.add("answer", jsonArray);
        Log.e("Ans Array=>", String.valueOf(object));
        baseRequest.callAPIPost(1, object, getString(R.string.api_updateTribeometerAnswers));
    }


//    @Override
//    public void onBackPressed() {
//
////        new AlertDialog.Builder(this)
////                .setTitle("Answers will not be saved!")
////                .setMessage("Are you sure you want to go back?")
////                .setCancelable(false)
////                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        Act_Tribeometer_list.super.onBackPressed();
////                    }
////                })
////                .setNegativeButton("No", null)
////                .show();
//    }

    @Override
    public int getLayoutId() {
        return R.layout.act_update_tribeometer_list;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}