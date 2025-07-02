package com.chetaru.tribe365_new.UI.Offloading;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.ModelChat;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.IOT_Adapter.Ad_ChatList;
import com.chetaru.tribe365_new.Notification.Act_NotificationList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Home.Act_imageShow;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;

public class Act_Ref_HistoryDetail extends BaseActivity {

    private BaseRequest baseRequest;
    Utility utility;
    SessionParam sessionParam;
    String changeId="";
    int index=0;

    String handelBackPress="";

    List<ModelChat> chatList;
    String post_type = "";
    String message = "";
    MarshMallowPermission marshMallowPermission;
    String imgBase64 = "", imageUrl ="";
    File sourceFile;
    private final int TakePicture = 1;
    private final int SELECT_FILE = 2;
    private final int RESULT_OK = -1;
    private Bitmap bitmap = null;
    /********************* initialize layout **********/
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tribe365)
    ImageView tribe;
    @BindView(R.id.detail_text)
    TextView detail_text;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.rv_chat)
    RecyclerView rv_chat;

    @BindView(R.id.iv_attachment)
    ImageView iv_attachment;
    @BindView(R.id.et_msg)
    EditText et_msg;
    @BindView(R.id.iv_send)
    TextView iv_send;
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        utility=new Utility();
        sessionParam=new SessionParam(mContext);
        //setContentView(R.layout.act_ref_history_detail);
        marshMallowPermission = new MarshMallowPermission(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setStackFromEnd(true);
        rv_chat.setLayoutManager(layoutManager);
       // tv_desc.setMovementMethod(new ScrollingMovementMethod());
        tv_desc.setMovementMethod(new ScrollingMovementMethod());
        getExtras();
        setSessionParam();
        apiGetChat();
        tribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
        iv_image.setOnClickListener(v ->{
            startActivity(new Intent(mContext, Act_ChatImgViewer.class)
                                        .putExtra("img",imageUrl)
                                        .putExtra("backHandel","reflection"));
        });
        iv_send.setOnClickListener(v->{
            if (et_msg.getText().toString().trim().isEmpty()){
                utility.showToast(mContext,getString(R.string.custom_support_msg));
            }else {
                if (!et_msg.getText().toString().trim().isEmpty()){
                    post_type="msg";
                }
                if (!changeId.isEmpty()){
                    apiSendMsg();
                }else {
                    utility.showToast(mContext,"Id cannot be blank!");
                }
            }
        });
        iv_attachment.setOnClickListener(v->{
            selectImage();
        });
        iv_top_back.setOnClickListener(v->{
            try {
                if (handelBackPress.equals("notiBack")){
                    mContext.startActivity(new Intent(mContext,Act_NotificationList.class));
                    finish();
                }else {
                    Intent intent = new Intent(mContext, Act_IOTHome.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){
                e.printStackTrace();
                callHomeAct(mContext);
            }

        });
        isTablet=getResources().getBoolean(R.bool.isTablet);
        try {
            /*************** send notification read Status ***************/
            if (getIntent().getStringExtra("readNotificationId") != null) {
                String readNotificationId = getIntent().getStringExtra("readNotificationId");
                api_notificationRead(readNotificationId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            handelBackPress = getIntent().getStringExtra("backHandel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (isTablet){
            }else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }

    }
    public void getExtras(){
        try {
            changeId=getIntent().getStringExtra("changeItId");
            index=Integer.parseInt(getIntent().getStringExtra("index"));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void setSessionParam(){
        Picasso.get().load(sessionParam.organisation_logo).into(tribe);
    }

    public void apiGetChat(){
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    /*JSONObject jsonObject = new JSONObject(object.toString());
                    JSONArray jsonArray;
                    JSONObject feedbackHeaderData;
                    jsonArray = (JSONArray) jsonObject.getJSONArray("messages");*/
                    JSONObject jsonObject= new JSONObject(object.toString());
                    JSONArray jsonArray;
                    JSONObject headerData;
                    jsonArray= jsonObject.getJSONArray("messages");
                    try {
                        headerData= jsonObject.getJSONObject("reflection");
                        if (!headerData.getString("initialMsgDate").equals("")) {
                            tv_date.setText(utility.getDate(headerData.getString("initialMsgDate")));
                        }
                        String desc = headerData.getString("initialMessage");
                        int descSize= desc.length();
                        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                            if (isTablet){
                                if (descSize>200){
                                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) tv_desc.getLayoutParams();
                                    params.height = 250;
                                    tv_desc.setLayoutParams(params);
                                    //tv_desc.setHeight(200);
                                }
                            }else {
                                if (descSize > 100) {
                                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) tv_desc.getLayoutParams();
                                    params.height = 150;
                                    tv_desc.setLayoutParams(params);
                                    //tv_desc.setHeight(200);
                                }
                            }
                        }else {
                            if (descSize>200){
                                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) tv_desc.getLayoutParams();
                                params.height = 350;
                                tv_desc.setLayoutParams(params);
                                //tv_desc.setHeight(200);
                            }
                        }

                        tv_desc.setText(headerData.getString("initialMessage"));
                        imageUrl= headerData.getString("msgImageUrl");

                        try {
                            if (!imageUrl.equalsIgnoreCase("") && !imageUrl.equals(null)) {
                                /* Picasso.get().load(history_list.get(i).getmImage()).placeholder(R.drawable.noimage_big).into(iv_image);*/
                                Glide.with(Act_Ref_HistoryDetail.this)
                                        .load(imageUrl)
                                        .placeholder(R.drawable.noimage_big)
                                        .into(iv_image);
                                iv_image.setVisibility(View.VISIBLE);
                            } else {
                                iv_image.setImageResource(R.drawable.noimage_big);
                                iv_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                iv_image.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    chatList= baseRequest.getDataList(jsonArray, ModelChat.class);
                    rv_chat.setAdapter(new Ad_ChatList(chatList, mContext, new Ad_ChatList.chatImageViewListener() {
                        @Override
                        public void chatImageClick(ModelChat chat) {
                            Bundle bundle=new Bundle();
                            bundle.putString("image",chat.getmMsgImageUrl());
                            Intent intent= new Intent(Act_Ref_HistoryDetail.this, Act_imageShow.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }));

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);

            }
        });
        JsonObject object= Functions.getClient().getJsonMapObject("reflectionId",changeId);
        baseRequest.callAPIPost(1,object, ConstantAPI.getReflectionChatMessages);
    }

    public void apiSendMsg() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                et_msg.setText("");
                apiGetChat();
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

        if (post_type.equals("img")) {
            message = imgBase64;
        } else {
            message = et_msg.getText().toString().trim();
        }

        JsonObject object = Functions.getClient().getJsonMapObject(
                "sendFrom", sessionParam.id,
                "sendTo", "1",
                "message", message,
                "reflectionId",changeId,
                "postType", post_type
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.sendReflectionChatMessage);
    }


    private void selectImage() {
        //here user will get options to choose image
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {
                    if (!marshMallowPermission.checkPermissionForCamera() && !marshMallowPermission.checkPermissionForExternalStorage()) {
                        marshMallowPermission.requestPermissionForCamera();
                        marshMallowPermission.requestPermissionForExternalStorage();
                    } else if (!marshMallowPermission.checkPermissionForCamera()) {
                        marshMallowPermission.requestPermissionForCamera();
                    } else if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                        marshMallowPermission.requestPermissionForExternalStorage();
                    } else {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, TakePicture);
                        }
                    }
//                startActivityForResult(intent, actionCode);
                } else if (items[item].equals("Gallery")) {
                    if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                        marshMallowPermission.requestPermissionForExternalStorage();
                    } else {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"),
                                SELECT_FILE);
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String imageFileName = System.currentTimeMillis() + ".png";
        if (resultCode == RESULT_OK) {
            if (requestCode == TakePicture) {
                post_type = "img";
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

                //imgBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                imgBase64 = Utility.image_to_Base64(mContext, utility.getPath(utility.getImageUri(mContext, decoded), mContext));

                apiSendMsg();
            }
            if (requestCode == SELECT_FILE) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                post_type = "img";
                //bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                sourceFile = new File(utility.getPath(data.getData(), mContext));
                try {
                    bitmap = new Compressor(mContext).compressToBitmap(sourceFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
                imgBase64 = Utility.image_to_Base64(mContext, utility.getPath(utility.getImageUri(mContext, decoded), mContext));
                //imgBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                apiSendMsg();
                // iv_DP.setImageBitmap(bitmap);

            }
                /*bitmap = utility.decodeSampledBitmapFromResource(data.getExtras().get("data").toString(), 100, 100);
                img_1.setVisibility(View.VISIBLE);
                img_1.setImageBitmap(bitmap);*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (handelBackPress.equals("notiBack")) {
                mContext.startActivity(new Intent(mContext, Act_NotificationList.class));
                finish();
            } else {
                finish();
            }
        }catch(Exception e){
            e.printStackTrace();
            callHomeAct(mContext);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_ref_history_detail;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_offloading;
    }
}