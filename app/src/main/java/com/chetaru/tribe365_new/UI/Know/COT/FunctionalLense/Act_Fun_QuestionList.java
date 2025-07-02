package com.chetaru.tribe365_new.UI.Know.COT.FunctionalLense;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.chetaru.tribe365_new.Adapter.COTAdapters.AD_Fun_Question;
import com.chetaru.tribe365_new.API.Models.COTBeans.ModelFunctionQues;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
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

public class Act_Fun_QuestionList extends BaseActivity {
    BaseRequest baseRequest;
    SessionParam sessionParam;
    Utility utility;
    @BindView(R.id.rv_functional_lens)
    RecyclerView rv_functional_lens;
    @BindView(R.id.btn_fun_qus_submit)
    Button btn_fun_qus_submit;
    ArrayList<ModelFunctionQues> li_Fun_Qus = new ArrayList<>();
    ArrayList<ModelFunctionQues> li_Fun_Qus2 = new ArrayList<>();
    //    ArrayList<ModelFunQusAns>modelCOTFuntAns;
    String SaveQuslist = "Quslist";
    String shred_json_list;
    String checklist = "";
    @BindView(R.id.tribe365)
    ImageView tribe365;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_function_lens_qustion);
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
        //save list aray in shared phrep.-----------------------
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        shred_json_list = prefs.getString(SaveQuslist, "");
        Type type = new TypeToken<ArrayList<ModelFunctionQues>>() {
        }.getType();
        call_QuestionList();
    }

    @OnClick({R.id.btn_fun_qus_submit, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            /*{
     "answer": [
          {
               "questionId": "1",
               "optionId": "1"
          },*/
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.btn_fun_qus_submit:
//                int count = 0;
//                for (int i = 0; i < li_Fun_Qus.size(); i++) {
//                    if (li_Fun_Qus.get(i).getOptionId().toString().equals("")) {
//                        count = 0;
//                        Toast.makeText(mContext, "Please agree with a statement for Question  " + li_Fun_Qus.get(i).getQuestionId(), Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        count++;
//                    }
//                }
//                if (count == li_Fun_Qus.size()) {
//                    call_AnswerList();
//                }
//
//                break;
//        }
                int count = 0;
                for (int i = 0; i < li_Fun_Qus.size(); i++) {
                    if (li_Fun_Qus.get(i).getOptions().get(0).isCheck()) {
                        li_Fun_Qus2.get(i).setOptionId(li_Fun_Qus.get(i).getOptions().get(0).getOptionId() + "");
                    } else if (li_Fun_Qus.get(i).getOptions().get(1).isCheck()) {
                        li_Fun_Qus2.get(i).setOptionId(li_Fun_Qus.get(i).getOptions().get(1).getOptionId() + "");
                    } else if (li_Fun_Qus2.get(i).getOptionId().toString().equals("")) {
                        count = 0;
                        Toast.makeText(mContext, "Please agree with a statement for Question  " + li_Fun_Qus2.get(i).getQuestionId(), Toast.LENGTH_SHORT).show();
                        return;

                    }
                    count++;
                }
                Log.e("Count=>", li_Fun_Qus2 + "");
                if (count == li_Fun_Qus2.size()) {
                    Log.e("Count=>", count + "");
                    call_AnswerList();
                }
        }

    }

    public void call_QuestionList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray = (JSONArray) object;
                li_Fun_Qus = baseRequest.getDataList(jsonArray, ModelFunctionQues.class);
                li_Fun_Qus2 = baseRequest.getDataList(jsonArray, ModelFunctionQues.class);
                //---------------
                if (!(shred_json_list.equals("[]"))) {
                    Log.e("list json", shred_json_list);
                    try {
                        JSONArray jr = new JSONArray(shred_json_list);
                        for (int i = 0; i < jr.length(); i++) {
                            JSONObject jsonObject = jr.getJSONObject(i);
                            String sh_questionId = jsonObject.optString("questionId");
                            String sh_questionName = jsonObject.optString("questionName");
                            String sh_optionID = jsonObject.optString("optionId");
                            JSONArray opt_array = jsonObject.getJSONArray("options");

                            for (int j = 0; j < opt_array.length(); j++) {
                                JSONObject opt_object = opt_array.getJSONObject(j);
                                String ischeck = opt_object.optString("ischeck");
                                String initialValue = opt_object.optString("initialValue");
                                String OptionId = opt_object.optString("OptionId");
                                String optionName = opt_object.getString("optionName");
                                String valueName = opt_object.getString("valueName");
                                if (ischeck.equals("true")) {
                                    li_Fun_Qus.get(i).getOptions().get(j).setCheck(true);
                                    li_Fun_Qus2.get(i).getOptions().get(j).setCheck(true);
                                } else {
                                    li_Fun_Qus.get(i).getOptions().get(j).setCheck(false);
                                    li_Fun_Qus2.get(i).getOptions().get(j).setCheck(false);
                                }


                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //--------------------------
                rv_functional_lens.setLayoutManager(new LinearLayoutManager(mContext));
                AD_Fun_Question ad_fun_question = new AD_Fun_Question(li_Fun_Qus, mContext);
                rv_functional_lens.setAdapter(ad_fun_question);

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
        baseRequest.callAPIGET(1, map, ConstantAPI.api_fubction_qusList);
    }

    public void call_AnswerList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                //clear shared pre.
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                //------clear------------------

                Toast.makeText(mContext, "personality type answer added successfully.", Toast.LENGTH_SHORT).show();
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
        for (int i = 0; i < li_Fun_Qus2.size(); i++) {
            jsonObject.addProperty("questionId", li_Fun_Qus2.get(i).getQuestionId().toString());
            jsonObject.addProperty("optionId", li_Fun_Qus2.get(i).getOptionId().toString());
            jsonArray.add(jsonObject);
            jsonObject = new JsonObject();
        }

        object.add("answer", jsonArray);
        Log.e("Ans Array=>", String.valueOf(object));
        baseRequest.callAPIPost(1, object,ConstantAPI.api_addCOTfunctionalLensAnswer);
    }


    @Override
    public void onBackPressed() {
//        savedailogbox();
//        new AlertDialog.Builder(this)
//                .setTitle("Answers will not be saved!")
//                .setMessage("Are you sure you want to go back?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        saveArrayList(li_Fun_Qus, SaveQuslist);
//                        finish();
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//        int count = 0;
//        for (int i = 0; i < li_Fun_Qus.size(); i++) {
//            if (li_Fun_Qus.get(i).getOptionId().toString().equals("")) {
//                count++;
//            }
//
//        }
//        Log.e("Count=>", li_Fun_Qus + "");
//        if (count == li_Fun_Qus.size()) {
//            finish();
//            Log.e("Count=>", count + "");
//        } else {
//            count = 0;
//
//        }
        int count = 0;
        for (int i = 0; i < li_Fun_Qus.size(); i++) {
            if (li_Fun_Qus.get(i).isFlag() == true) {
                count++;
            }
        }
        if (count != 0) {
            savedailogbox();
        } else {
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
    }

    private void savedailogbox() {
        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle(Html.fromHtml("<font color='#515050'>Answers will not be saved!</font>"))
                .setMessage(Html.fromHtml("<font color='#515050'>Do you want to save the answers?</font>"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveArrayList(li_Fun_Qus, SaveQuslist);
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

    public void saveArrayList(List<ModelFunctionQues> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_function_lens_qustion;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}