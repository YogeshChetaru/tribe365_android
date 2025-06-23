package com.chetaru.tribe365_new.UI.Home.Actions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelACtionList;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.Ad_ActionCommentList;
import com.chetaru.tribe365_new.API.Models.ModelActionCommentList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class   Act_ActionComment extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_name)
    TextView tv_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_description)
    TextView tv_description;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_startdate)
    TextView tv_startdate;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_duedate)
    TextView tv_duedate;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_send)
    TextView tv_send;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_themes)
    TextView tv_themes;
    @BindView(R.id.ll_theme)
    LinearLayout ll_theme;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_responsiblePerson)
    TextView tv_responsiblePerson;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_status)
    ImageView iv_status;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_chat)
    ImageView iv_chat;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_edit)
    ImageView iv_edit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_comment)
    EditText et_comment;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.no_action_found_tv)
    TextView no_action_found_tv;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_linked_offloads)
    TextView tv_linked_offloads;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_offloads)
    LinearLayout ll_offloads;

    Utility utility;
    SessionParam sessionParam;
    ArrayList<ModelACtionList> parentList = new ArrayList<>();

    ArrayList<ModelActionCommentList> commentList;
    String name = "",
            description = "",
            startdate = "",
            duedate = "",
            status = "",
            actionId = "",
            respons_person = "",
            offloadsNumber="",
            theme = "";
    String backHandel="";
    int riskId=0;
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_action_comment);
        ButterKnife.bind(this);
        init();
    }



    public void init() {
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        utility = new Utility();
        iv_chat.setVisibility(View.GONE);
        iv_edit.setVisibility(View.GONE);
        commentList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setLayoutManager(layoutManager);
        try {
            actionId = getIntent().getStringExtra("actionId");
            name = getIntent().getStringExtra("name");
            description = getIntent().getStringExtra("description");
            startdate = getIntent().getStringExtra("startdate");
            duedate = getIntent().getStringExtra("duedate");
            status = getIntent().getStringExtra("status");

            respons_person = getIntent().getStringExtra("respons_person");
            theme = getIntent().getStringExtra("theme");
            backHandel = getIntent().getStringExtra("backHandel");
            riskId = getIntent().getIntExtra("riskId",0);
        }catch (Exception e){
            e.printStackTrace();

        }
        getCommentList();
        getActionDetail();
        try {
            if (!name.equals(null)&&!name.equals("") && !name.isEmpty()){
                setParentData(name,description,startdate,duedate,status,respons_person,theme,offloadsNumber);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public void setParentData(String name,String description,String startdate ,String duedate
                                ,String status,String respons_person,String theme,String offloadsNumber){

        try {

            tv_name.setText(name);

            tv_description.setText(description);
            tv_startdate.setText(startdate);
            tv_duedate.setText(duedate);
            tv_responsiblePerson.setText(respons_person);

            if (!status.isEmpty() && !status.equals("")) {
                if (status.equalsIgnoreCase("STARTED")) {
                    iv_status.setImageDrawable(getResources().getDrawable(R.drawable.started));
                }
                if (status.equalsIgnoreCase("COMPLETED")) {
                    iv_status.setImageDrawable(getResources().getDrawable(R.drawable.complete));
                }
                if (status.equalsIgnoreCase("NOT STARTED")) {
                    iv_status.setImageDrawable(getResources().getDrawable(R.drawable.notstarted));
                }
            }
            if (theme.equals("")){
                ll_theme.setVisibility(View.GONE);
            }else{
                tv_themes.setText(theme);
            }
            if (offloadsNumber.trim() != null && !offloadsNumber.trim().equals("") && !offloadsNumber.trim().equals("0")){
                ll_offloads.setVisibility(View.VISIBLE);
                tv_linked_offloads.setVisibility(View.VISIBLE);
                tv_linked_offloads.setText(offloadsNumber);
            }else{
                tv_linked_offloads.setVisibility(View.GONE);
                ll_offloads.setVisibility(View.GONE);
            }
        }catch ( Exception e){
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tv_send, R.id.tribe365})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.tv_send:
                if (et_comment.getText().toString().trim().isEmpty()) {
                    utility.showToast(mContext, "Enter Comment");
                } else {
                    addComment();
                }

                break;
        }
    }

    public void getCommentList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                commentList = baseRequest.getDataList(jsonArray, ModelActionCommentList.class);
                rv_list.setAdapter(new Ad_ActionCommentList(commentList, mContext));

                if (commentList.size()>0){
                    rv_list.setVisibility(View.VISIBLE);
                    no_action_found_tv.setVisibility(View.GONE);
                }else {
                    rv_list.setVisibility(View.GONE);
                    no_action_found_tv.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });

        JsonObject object = Functions.getClient().getJsonMapObject("actionId", actionId);
        baseRequest.callAPIPost(1, object, ConstantAPI.listComment);
    }

    public void getActionDetail() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                parentList = baseRequest.getDataList(jsonArray, ModelACtionList.class);
                if(parentList.size()>0){
                    for (int i=0;i<parentList.size();i++) {

                     name= parentList.get(i).getName();
                       description= parentList.get(i).getDescription();
                      startdate=  parentList.get(i).getStartedDate();
                       duedate= parentList.get(i).getDueDate();
                       status= parentList.get(i).getCurrentStatus();
                       respons_person= parentList.get(i).getResponsibleName();
                       try {
                           offloadsNumber=String.valueOf(parentList.get(i).getLinkedActionOffloads());
                           ll_offloads.setVisibility(View.VISIBLE);
                       }catch (Exception e){
                           e.printStackTrace();
                           ll_offloads.setVisibility(View.GONE);
                       }

                       try {


                           if (parentList.get(i).getmThemes().size() > 0) {
                               String value ="";
                               for (int j = 0; j < parentList.get(i).getmThemes().size(); j++) {
                                   if (j == 0) {
                                       if (j == parentList.get(i).getmThemes().size() - 1) {
                                           value = parentList.get(i).getmThemes().get(j).getTitle();
                                       } else {
                                           value = parentList.get(i).getmThemes().get(j).getTitle() + ", ";
                                       }
                                   } else {
                                       if (j == parentList.get(i).getmThemes().size() - 1) {
                                           value = value + parentList.get(i).getmThemes().get(j).getTitle();
                                       } else {
                                           value = value + parentList.get(i).getmThemes().get(j).getTitle() + ", ";
                                       }
                                   }
                               }
                               theme = value;
                               ll_theme.setVisibility(View.VISIBLE);
                           }else {
                               ll_theme.setVisibility(View.GONE);
                               theme= "";
                           }
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                    }
                    setParentData(name,description,startdate,duedate,status,respons_person,theme,offloadsNumber);

                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });

        JsonObject object = Functions.getClient().getJsonMapObject("actionId", actionId,
                "riskId" , riskId+ ""
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getActionDetail);
    }

    public void addComment() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONObject jsonObject = (JSONObject) object;
                ModelActionCommentList modelActionCommentList = new ModelActionCommentList();
                modelActionCommentList.setName(jsonObject.optString("name"));
                modelActionCommentList.setComment(jsonObject.optString("comment"));
                modelActionCommentList.setUserId(jsonObject.optString("userId") + "");
                modelActionCommentList.setCreatedAt(jsonObject.optString("created_at"));
                modelActionCommentList.setId(jsonObject.optString("actionId"));
                et_comment.setText("");


                commentList.add(0, modelActionCommentList);
                rv_list.setAdapter(new Ad_ActionCommentList(commentList, mContext));
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });

        JsonObject object = Functions.getClient().getJsonMapObject("actionId", actionId,
                "comment", et_comment.getText().toString().trim()
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.addComment);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_action_comment;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
