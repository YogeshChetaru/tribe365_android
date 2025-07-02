package com.chetaru.tribe365_new.UI.Know.Diagnostics;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.Adapter.Diagnostics.Ad_Update_Diagnostics_list;
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

public class Act_Update_Diagnostics_list extends BaseActivity {
    BaseRequest baseRequest;
    SessionParam sessionParam;
    Utility utility;
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @BindView(R.id.rv_diagnostic_qustion)
    RecyclerView rv_diagnostic_qustion;
    @BindView(R.id.btn_submit_diagnostic_Qus)
    Button btn_submit_diagnostic_Qus;
    ArrayList<ModelUpdateDiagnostics> li_diagnostics = new ArrayList<>();
    String checklist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_diagnostic_list);
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
        call_UpdateQuestionList();
    }

    @OnClick({R.id.btn_submit_diagnostic_Qus, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.btn_submit_diagnostic_Qus:
                call_AnswerList();
//                int count = 0;
//                for (int i = 0; i < li_diagnostics.size(); i++) {
//                    if (li_diagnostics.get(i).getQuestionId().toString().equals("")) {
//                        count = 0;
//                        Toast.makeText(mContext, "Please agree with a statement for Question  " + li_diagnostics.get(i).getQuestionId(), Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        count++;
//                    }
//                }
//                if (count == li_diagnostics.size()) {
//                    call_AnswerList();
//                }
                break;
        }
    }

    public void call_UpdateQuestionList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray = (JSONArray) object;
                li_diagnostics = baseRequest.getDataList(jsonArray, ModelUpdateDiagnostics.class);

                Ad_Update_Diagnostics_list ad_diagnostics_list = new Ad_Update_Diagnostics_list(li_diagnostics, mContext);
                rv_diagnostic_qustion.setAdapter(ad_diagnostics_list);
                rv_diagnostic_qustion.setLayoutManager(new LinearLayoutManager(mContext));

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
        baseRequest.callAPIGET(1, map, getString(R.string.api_getDiagnosticCompletedAnswers));
    }

    public void call_AnswerList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray = (JSONArray) object;
                Toast.makeText(mContext, "Diagnostic answer Update successfully.", Toast.LENGTH_SHORT).show();
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
            jsonObject.addProperty("answerId", li_diagnostics.get(i).getAnswerId().toString());
            jsonObject.addProperty("optionId", li_diagnostics.get(i).getmAnswer().toString());


            if (li_diagnostics.get(i).getmAnswer().equals("")) {
                for (int j = 0; j < li_diagnostics.get(i).getOptions().size(); j++) {
                    if (li_diagnostics.get(i).getOptions().get(j).getIsChecked().equals(true)) {
                        jsonObject.addProperty("optionId", li_diagnostics.get(i).getOptions().get(j).getOptionId());
                    }
                }
            }
            jsonArray.add(jsonObject);
            jsonObject = new JsonObject();

        }

        object.add("answer", jsonArray);
        Log.e("Ans Array=>", String.valueOf(object));
        baseRequest.callAPIPost(1, object, getString(R.string.api_updateDiagnosticAnswers));
    }

//
//    @Override
//    public void onBackPressed() {
//
//        new AlertDialog.Builder(this)
//                .setTitle("Answers will not be saved!")
//                .setMessage("Are you sure you want to go back?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Act_Diagnostics_list.super.onBackPressed();
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//    }
@Override
public int getLayoutId() {
    return R.layout.act_update_diagnostic_list;
}

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}