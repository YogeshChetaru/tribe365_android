//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Html;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import ModelFuctionLens;
//import com.chetaru.tribe365_new.R;
//import BaseRequest;
//import RequestReciever;
//import BaseActivity;
//import SessionParam;
//import Utility;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//
//import org.json.JSONArray;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class Act_Update_Fun_QuestionList_ListView extends BaseActivity {
//    BaseRequest baseRequest;
//    SessionParam sessionParam;
//    Utility utility;
//    @BindView(R.id.rv_functional_lens)
//    ListView rv_functional_lens;
//    @BindView(R.id.btn_fun_qus_submit)
//    Button btn_fun_qus_submit;
//    ArrayList<ModelFuctionLens> li_Fun_Qus = new ArrayList<>();
//        ArrayList<ModelFuctionLens> li_Fun_QusMain = new ArrayList<>();
////    ArrayList<ModelFunQusAns>modelCOTFuntAns;
////AD_update_Fun_Question ad_fun_question;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_function_lens_qustion);
//
//        inti();
//    }
//
//    private void inti() {
//        ButterKnife.bind(this);
//        sessionParam = new SessionParam(mContext);
//        utility = new Utility();
////        rv_functional_lens.setVisibility(View.GONE);
//        call_QuestionList();
//        btn_fun_qus_submit.setText("Update");
//
//    }
//
//    @OnClick({R.id.btn_fun_qus_submit})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            /*{
//     "answer": [
//          {
//               "questionId": "1",
//               "optionId": "1"
//          },*/
//            case R.id.btn_fun_qus_submit:
//
//
//                for(int i = 0; i < li_Fun_Qus.size(); i++){
//                    if(!li_Fun_QusMain.get(i).getOptionId().equals(li_Fun_Qus.get(i).getOptionId())&&li_Fun_Qus.get(i).getAnswerId()!=null){
//                        if(!li_Fun_Qus.get(i).getOptionId().equals("")){
//                            li_Fun_QusMain.get(i).setOptionId(li_Fun_Qus.get(i).getOptionId());
//                        }
//
//                    }
////                    else{
////                        li_Fun_QusMain.get(i).setOptionId(li_Fun_Qus.get(i).getOptionId());
////                    }
//
//                }
//
//                call_AnswerList(li_Fun_QusMain);
//               // int count = 0;
////                li_Fun_Qus.lastIndexOf(li_Fun_Qus.size()-1);
//
//              /*  for (int i = 0; i < li_Fun_Qus.size(); i++) {
//                    if (li_Fun_Qus.get(i).getOptionId().toString().equals("")) {
//                        count = 0;
//                       Toast.makeText(mContext, "Please agree with a statement for Question  " + li_Fun_Qus.get(i).getQuestionId(), Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        count++;
//                    }
//                }*/
////                if (count == li_Fun_Qus.size()) {
////                    call_AnswerList();
////                }
//
//                break;
//        }
//    }
//
//    public void call_QuestionList() {
//        baseRequest = new BaseRequest(mContext);
//        baseRequest.setBaseRequestListner(new RequestReciever() {
//            @Override
//            public void onSuccess(int requestCode, String Json, Object object) {
//                JSONArray jsonArray = (JSONArray) object;
//                li_Fun_Qus = baseRequest.getDataList(jsonArray, ModelFuctionLens.class);
//                li_Fun_QusMain = baseRequest.getDataList(jsonArray, ModelFuctionLens.class);
//                Ad_UpComing up_adap = new Ad_UpComing(Act_Update_Fun_QuestionList_ListView.this, android.R.layout.simple_list_item_1, li_Fun_Qus);
//                rv_functional_lens.setAdapter(up_adap);
//                up_adap.notifyDataSetChanged();
//                rv_functional_lens.deferNotifyDataSetChanged();
//
//
////                rv_functional_lens.setLayoutManager(new LinearLayoutManager(mContext));
////                 ad_fun_question = new AD_update_Fun_Question(li_Fun_Qus, mContext);
//////                rv_functional_lens.smoothScrollToPosition(li_Fun_Qus.size()-1);
////                rv_functional_lens.setAdapter(ad_fun_question);
////
////                rv_functional_lens.post(new Runnable() {
////            @Override
////            public void run() {
////                // Call smooth scroll
////                rv_functional_lens.smoothScrollToPosition(ad_fun_question.getItemCount() - 1);
//
////            }
////        });
////                Handler handler = new Handler();
////                handler.postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////
////                        rv_functional_lens.setVisibility(View.VISIBLE);
////                    }
////                }, 2000);
//
////    }
//            }
//
//            @Override
//            public void onFailure(int requestCode, String errorCode, String message) {
//                utility.showToast(mContext, message);
//            }
//
//            @Override
//            public void onNetworkFailure(int requestCode, String message) {
//                utility.showToast(mContext, message);
//            }
//        });
//        Map<String, String> map = new HashMap<>();
//        map.put("", "");
//        baseRequest.callAPIGET(1, map, ConstantAPI.api_getCOTfuncLensCompletedAnswers );
//    }
//
//    public void call_AnswerList( ArrayList<ModelFuctionLens> li_Fun_Qus) {
//            ArrayList<ModelFuctionLens> li_Fun_Qus1 = li_Fun_QusMain;
//        baseRequest = new BaseRequest(mContext);
//        baseRequest.setBaseRequestListner(new RequestReciever() {
//            @Override
//            public void onSuccess(int requestCode, String Json, Object object) {
//                JSONArray jsonArray = (JSONArray) object;
//                Toast.makeText(mContext, "cot functional lens answer updated successfully.", Toast.LENGTH_SHORT).show();
//                Intent in=new Intent(Act_Update_Fun_QuestionList_ListView.this,Act_CotDetails.class);
//                startActivity(in);
//                finish();
//
//            }
//
//            @Override
//            public void onFailure(int requestCode, String errorCode, String message) {
//                utility.showToast(mContext, message);
//            }
//
//            @Override
//            public void onNetworkFailure(int requestCode, String message) {
//                utility.showToast(mContext, message);
//            }
//        });
//        JsonObject jsonObject = null;
//        JsonArray jsonArray = null;
//        JsonObject object = null;
//        jsonObject = new JsonObject();
//        jsonArray = new JsonArray();
//        object = new JsonObject();
//        for (int i = 0; i < li_Fun_Qus1.size(); i++) {
//            jsonObject.addProperty("answerId", li_Fun_QusMain.get(i).getAnswerId().toString());
//            jsonObject.addProperty("optionId", li_Fun_QusMain.get(i).getOptionId().toString());
//            jsonArray.add(jsonObject);
//            jsonObject = new JsonObject();
//        }
//
//        object.add("answer", jsonArray);
//        Log.e("Ans Array=>", String.valueOf(object));
//        baseRequest.callAPIPost(1, object, ConstantAPI.api_updateCOTfunLensAnswers);
//    }
//
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
//                        Act_Update_Fun_QuestionList_ListView.super.onBackPressed();
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//    }
//
//
//    private class Ad_UpComing extends BaseAdapter {
//        TextView tv_tv_fun_lens_qus, tv_qus_no;
//        TextView tv_sot_ques1, tv_sot_ques2;
//        CheckBox chk_sot_ques1, chk_sot_ques2;
//
//        public Ad_UpComing(Act_Update_Fun_QuestionList_ListView act_update_fun_questionList_listView, int simple_list_item_1, ArrayList<ModelFuctionLens> li_fun_qus) {
//        }
//
//
//        @Override
//        public int getCount() {
//            return li_Fun_Qus.size();
//        }
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//        @Override
//        public View getView(final int position, View view, ViewGroup viewGroup) {
//            final ModelFuctionLens u_bean = li_Fun_Qus.get(position);
//            LayoutInflater inflater = getLayoutInflater();
//            View v = inflater.inflate(R.layout.row_functional_lens_question, null);
//            tv_tv_fun_lens_qus = v.findViewById(R.id.tv_tv_fun_lens_qus);
//            tv_sot_ques1 = v.findViewById(R.id.tv_sot_ques1);
//            tv_sot_ques2 = v.findViewById(R.id.tv_sot_ques2);
//            chk_sot_ques1 = v.findViewById(R.id.chk_sot_ques1);
//            chk_sot_ques2 = v.findViewById(R.id.chk_sot_ques2);
//            tv_qus_no = v.findViewById(R.id.tv_qus_no);
//
////            kyc_appname.setText(u_bean.getApp_Name());
////            kyc_fathername.setText(u_bean.getFatherName());
////            kyc_mobile.setText(u_bean.getMobile_no());
////            kyc_dob.setText(u_bean.getDOB());
////            kyc_email.setText(u_bean.getEmail_id());
////            String appicant=u_bean.getApplicant_status();
////            kyc_profile.setText(u_bean.getApplicant_status());
////            ImageView kyc_delete=(ImageView)v.findViewById(R.id.kyc_delete);
////            if(appicant.equals("Applicant"))
////            {
////
////                kyc_delete.setVisibility(View.GONE);
////            }
//
//            tv_tv_fun_lens_qus.setText(li_Fun_Qus.get(position).getQuestion());
//            tv_qus_no.setText(li_Fun_Qus.get(position).getQuestionId().toString());
//
//            chk_sot_ques1.setChecked(li_Fun_Qus.get(position).getOptions().get(0).getIsChecked());
//            chk_sot_ques2.setChecked(li_Fun_Qus.get(position).getOptions().get(1).getIsChecked());
//
//            for (int i = 0; i < li_Fun_Qus.get(position).getOptions().size(); i++) {
//                tv_sot_ques1.setText(li_Fun_Qus.get(position).getOptions().get(0).getOptionName());
//
//                tv_sot_ques2.setText(li_Fun_Qus.get(position).getOptions().get(1).getOptionName());
//                if (li_Fun_Qus.get(position).getOptions().get(0).getIsChecked() == true) {
//                    chk_sot_ques1.setChecked(true);
//                    chk_sot_ques2.setChecked(false);
//                    li_Fun_Qus.get(position).setOptionId(li_Fun_Qus.get(position).getOptions().get(0).getOptionId().toString());
//
//                }
//                if (li_Fun_Qus.get(position).getOptions().get(1).getIsChecked() == true) {
//                    chk_sot_ques1.setChecked(false);
//                    chk_sot_ques2.setChecked(true);
//                    li_Fun_Qus.get(position).setOptionId(li_Fun_Qus.get(position).getOptions().get(1).getOptionId().toString());
//
//                }
//            }
//            chk_sot_ques1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (chk_sot_ques1.isChecked()) {
//                        chk_sot_ques1.setChecked(true);
//                        chk_sot_ques1.setClickable(false);
//                        chk_sot_ques2.setClickable(true);
//                    }
//                    if (isChecked == true) {
////                        li_Fun_Qus.get(position).getOptions().get(0).getIsChecked();
////                        li_Fun_Qus.get(position).getOptions().get(1).getIsChecked();
//                        chk_sot_ques1.setChecked(true);
//                        chk_sot_ques2.setChecked(false);
//                        li_Fun_Qus.get(position).setOptionId(li_Fun_Qus.get(position).getOptions().get(0).getOptionId().toString());
//                        li_Fun_Qus.get(position).getOptions().get(0).setIsChecked(true);
//                        li_Fun_Qus.get(position).getOptions().get(1).setIsChecked(false);
//                        li_Fun_Qus.get(position).getOptions().get(0).getIsChecked();
//
//                        //testing with another list
////                        li_Fun_QusMain.get(position).setOptionId(li_Fun_Qus.get(position).getOptions().get(0).getOptionId().toString());
////                        li_Fun_QusMain.get(position).getOptions().get(0).setIsChecked(true);
////                        li_Fun_QusMain.get(position).getOptions().get(1).setIsChecked(false);
////                        li_Fun_QusMain.get(position).getOptions().get(0).getIsChecked();
//
//                        notifyDataSetChanged();
//
//                    } else {
//                        chk_sot_ques1.setChecked(false);
//                       // li_Fun_QusMain.get(position).setOptionId(li_Fun_Qus.get(position).getOptions().get(1).getOptionId().toString());
////                        //li_Fun_Qus.get(position).setOptionId(li_Fun_Qus.get(position).getOptions().get(1).getOptionId().toString());
////                        li_Fun_Qus.get(position).getOptions().get(0).getIsChecked();
////                        //testing with another list
////                        li_Fun_QusMain.get(position).getOptions().get(0).getIsChecked();
//                        notifyDataSetChanged();
//                    }
//
//                }
//            });
//
//            chk_sot_ques2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (chk_sot_ques2.isChecked()) {
//                        chk_sot_ques2.setChecked(true);
//                        chk_sot_ques2.setClickable(false);
//                        chk_sot_ques1.setClickable(true);
//                    }
//                    if (isChecked == true) {
//                        li_Fun_Qus.get(position).getOptions().get(0).getIsChecked();
//                        li_Fun_Qus.get(position).getOptions().get(1).getIsChecked();
//                        chk_sot_ques2.setChecked(true);
//                        chk_sot_ques1.setChecked(false);
//                        li_Fun_Qus.get(position).getOptions().get(1).getIsChecked();
//                        li_Fun_Qus.get(position).setOptionId(li_Fun_Qus.get(position).getOptions().get(1).getOptionId().toString());
//                        li_Fun_Qus.get(position).getOptions().get(0).setIsChecked(false);
//                        li_Fun_Qus.get(position).getOptions().get(1).setIsChecked(true);
//                        notifyDataSetChanged();
//                    } else {
//                        li_Fun_Qus.get(position).getOptions().get(1).getIsChecked();
//                        chk_sot_ques2.setChecked(false);
//                        notifyDataSetChanged();
//                    }
//
//                }
//            });
//
//
//            return v;
//        }
//    }
//
//}