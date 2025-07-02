package com.chetaru.tribe365_new.UI.Know.SOT;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.SOTAdapters.Adap_SOT_Update_Questionlist;
import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelSOTOPTIONLIST;
import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelSOTQusList;
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


public class Act_SOT_Update_Questionlist extends BaseActivity {
    public Context mContext;
    @BindView(R.id.rv_sot_quslist)
    RecyclerView rv_sot_quslist;
    @BindView(R.id.btn_sot_qus_submit)
    Button btn_sot_qus_submit;
    Utility utility;
    SessionParam sessionParam;
    @BindView(R.id.tribe365)
    ImageView tribe;
    BaseRequest baseRequest;
    Adap_SOT_Update_Questionlist adap_sot_update_questionlist;
    ArrayList<ModelSOTOPTIONLIST> li_qus = new ArrayList<>();
    ArrayList<ModelSOTQusList> li_TotQus = new ArrayList<>();
    ArrayList<String> li_answers = new ArrayList<String>();
    int count = 0;
    String checklist = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sot_update_question_list);
        try {
            checklist = getIntent().getStringExtra("checklist");
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        utility = new Utility();
        this.mContext = this;
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).placeholder(R.drawable.icon_tribe365).into(tribe);
            }
        }
        APi_getQuestionList();
    }

    public void APi_getQuestionList() {
        baseRequest = new BaseRequest(mContext);
        li_qus.clear();
        li_TotQus.clear();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Log.e("api_get_SOTQuestions=>", Json);

                /*JSONArray jsonArray;
                jsonArray = (JSONArray) object;*/

               /* try {
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
                            boolean isChecked = Boolean.parseBoolean(jsonObject.optString("isChecked"));
                            ModelSOTOPTIONLIST modelSOTOPTIONLIST = new ModelSOTOPTIONLIST(id, question, section, type, isChecked);
                            li_qus.add(modelSOTOPTIONLIST);
                        }
                        ModelSOTQusList modelSOTQusList = new ModelSOTQusList(i + "", li_qus);
                        li_TotQus.add(modelSOTQusList);
                        adap_sot_update_questionlist = new Adap_SOT_Update_Questionlist(li_TotQus, mContext);
                        rv_sot_quslist.setHasFixedSize(true);
                        rv_sot_quslist.setAdapter(adap_sot_update_questionlist);
                        rv_sot_quslist.setLayoutManager(new LinearLayoutManager(mContext));
                    }
               } catch (JSONException e) {
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
                            boolean isChecked = jsonObject.optBoolean("isChecked");
                            Log.e("Question", question);

                            ModelSOTOPTIONLIST modelSOTOPTIONLIST = new ModelSOTOPTIONLIST(id, question, section, type, isChecked);
                            li_qus.add(modelSOTOPTIONLIST);
                        }
                        ModelSOTQusList modelSOTQusList = new ModelSOTQusList(question1 + "", li_qus);
                        li_TotQus.add(modelSOTQusList);
                       /* ModelSOTQusList modelSOTQusList = new ModelSOTQusList(i + "", li_qus);
                        li_TotQus.add(modelSOTQusList);*/
                        adap_sot_update_questionlist = new Adap_SOT_Update_Questionlist(li_TotQus, mContext);
                        rv_sot_quslist.setHasFixedSize(true);
                        rv_sot_quslist.setAdapter(adap_sot_update_questionlist);
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
        baseRequest.callAPIGET(1, map, ConstantAPI.api_sot_addSOTQusAns );
    }

    @OnClick({R.id.btn_sot_qus_submit, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.btn_sot_qus_submit:
                call_Validation();
                break;
        }
    }

    private void call_Validation() {
        li_answers.clear();
        for (int i = 0; i < li_TotQus.size(); i++) {
            count = 0;
            for (int j = 0; j < li_TotQus.get(i).getmSotQusDetails().size(); j++) {
                if (li_TotQus.get(i).getmSotQusDetails().get(j).isCheck() == true) {
                    li_answers.add(li_TotQus.get(i).getmSotQusDetails().get(j).getId().toString());
                } else {
                    count++;
                }
            }
            if (count >= 4) {
                int qusno = i + 1;
                Toast.makeText(mContext, "Please agree with a statement for Question " + qusno, Toast.LENGTH_SHORT).show();
                count = 0;
                return;
            }
            if (li_answers.size() == li_TotQus.size()) {
                APi_sot_answer();
//                Toast.makeText(mContext, "Submit success", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void APi_sot_answer() {
        baseRequest = new BaseRequest(mContext);

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Log.e("api_get_SOTQuestions=>", Json);
                if (checklist != null) {
                    if (checklist.equals("checklist")) {
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

        JsonObject main_object = new JsonObject();
        JsonObject sub_object = new JsonObject();
        JsonArray main_array = new JsonArray();
        for (
                int i = 0; i < li_answers.size(); i++) {
            sub_object.addProperty("id", li_answers.get(i).toString());
            main_array.add(sub_object);
            sub_object = new JsonObject();
        }
//        main_object.add("answer",main_array);


//        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId
        main_object.add("answer", main_array);
        //-----------------------------------------------------------------------------------------------------------------------------------
        baseRequest.callAPIPost(1, main_object,ConstantAPI.api_sot_updateSOTquestionAnswer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    public int getLayoutId() {
        return R.layout.sot_update_question_list;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
