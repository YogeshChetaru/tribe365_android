package com.chetaru.tribe365_new.UI.CustomerSupport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.IOT_Adapter.Ad_ChatList;
import com.chetaru.tribe365_new.API.Models.ModelChat;
import com.chetaru.tribe365_new.UI.Home.Act_imageShow;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_SupportChat extends BaseActivity {

    //layout view initialization
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @BindView(R.id.rv_chat)
    RecyclerView rv_chat;
    @BindView(R.id.et_msg)
    EditText et_msg;
    @BindView(R.id.iv_send)
    ImageView iv_send;
    @BindView(R.id.iv_attachment)
    ImageView iv_attachment;


    //Value initialization
    String position;
    Utility utility;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    String imgBase64 = "";
    File sourceFile;
    String changeit_id = "";
    ArrayList<ModelChat> list;
    String post_type = "";
    String message = "";
    LinearLayoutManager layoutManager;
    private int TakePicture = 1, SELECT_FILE = 2, RESULT_OK = -1;
    private Bitmap bitmap = null;
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_act__support_chat);
        init();
        tribe365.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
    }

    public void init() {
        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setStackFromEnd(true);
        // rv_chat.setHasFixedSize(true);

        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        marshMallowPermission = new MarshMallowPermission(this);
        rv_chat.setLayoutManager(layoutManager);
        try {
            changeit_id = getIntent().getStringExtra("changeItId");
            apiGetChat();
            rv_chat.smoothScrollToPosition(list.size() - 1);
        } catch (NullPointerException npe) {

            npe.printStackTrace();
        }


    }


    /*API call to get chat
     */
    public void apiGetChat() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    JSONArray jsonArray;
                    jsonArray = (JSONArray) jsonObject.getJSONArray("messages");
                    list = baseRequest.getDataList(jsonArray, ModelChat.class);
                    rv_chat.setAdapter(new Ad_ChatList(list, mContext, new Ad_ChatList.chatImageViewListener() {
                        @Override
                        public void chatImageClick(ModelChat chat) {

                            Bundle bundle = new Bundle();
                            bundle.putString("image", chat.getmMsgImageUrl());
                            Intent intent = new Intent(Act_SupportChat.this, Act_imageShow.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }));

                    //mAdapter.notifyItemInserted(data.size() - 1);
                    //rv_chat.scrollToPosition(list.size() - 1);
                    if (list.size() > 0)
                        rv_chat.smoothScrollToPosition(list.size() - 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("supportId", changeit_id
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getUserChatMessages);

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_act__support_chat;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}