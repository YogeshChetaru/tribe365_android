package com.chetaru.tribe365_new.UI.Offloading;


import android.annotation.SuppressLint;
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
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.ModelChat;
import com.chetaru.tribe365_new.API.Models.ModelHistory;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.IOT_Adapter.Ad_ChatList;
import com.chetaru.tribe365_new.Notification.Act_NotificationList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.UI.Home.Act_imageShow;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;


public class Act_HistoryDetail extends BaseActivity {
    RecyclerView rv_list;
    ImageView iv_image;
    TextView tv_location, tv_date, tv_notFound, tv_desc, tv_sendQuery;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_chat)
    RecyclerView rv_chat;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_msg)
    EditText et_msg;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_send)
    TextView iv_send;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_attachment)
    ImageView iv_attachment;
    Utility utility;
    SessionParam sessionParam;
    List<ModelHistory> history_list = new ArrayList<>();
    List<ModelChat> chat_list;
    int index = 0;
    String jsonData = "";
    String changeit_id = "";
    String post_type = "";
    String message = "";

    MarshMallowPermission marshMallowPermission;
    String imgBase64 = "", imageUrl ="";
    File sourceFile;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe;
    String handelBackPress = "";
    private BaseRequest baseRequest;
    private final int TakePicture = 1;
    private final int SELECT_FILE = 2;
    private final int RESULT_OK = -1;
    private Bitmap bitmap = null;
    boolean isTablet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_history_detail);
        marshMallowPermission = new MarshMallowPermission(this);
        init();

        /*************** send notification read Status ***************/
        if (getIntent().getStringExtra("readNotificationId") != null) {
            String readNotificationId = getIntent().getStringExtra("readNotificationId");
            api_notificationRead(readNotificationId);
        }
        isTablet=getResources().getBoolean(R.bool.isTablet);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (isTablet){
            }else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    /*used to initialise all the views
     */
    public void init() {
        ButterKnife.bind(this);
        rv_list = findViewById(R.id.rv_list);
        iv_image = findViewById(R.id.iv_image);
        tv_location = findViewById(R.id.tv_location);
        tv_date = findViewById(R.id.tv_date);
        tv_notFound = findViewById(R.id.tv_notFound);
        tv_desc = findViewById(R.id.tv_desc);
        tv_desc.setMovementMethod(new ScrollingMovementMethod());

        tv_sendQuery = findViewById(R.id.tv_sendQuery);
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe);
            }
        }
        try {
            changeit_id = getIntent().getStringExtra("changeItId");
            index = Integer.parseInt(getIntent().getStringExtra("index"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            handelBackPress = getIntent().getStringExtra("backHandel");
        } catch (Exception e) {
            e.printStackTrace();
        }


        apiGetChat();
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Act_ChatImgViewer.class)
                        .putExtra("img", imageUrl)
                        .putExtra("backHandel", "offloading")
                );
            }
        });
        iv_top_back.setOnClickListener(v-> {
            Intent intent=new Intent(mContext,Act_IOTHome.class);
            startActivity(intent);
            finish();
        });

        tv_sendQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Act_HistoryDetail.class)
                        .putExtra("changeItId", history_list.get(index).getmId() + ""));
            }
        });

        iv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_msg.getText().toString().trim().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.custom_support_msg));
                } else {
                    if (!et_msg.getText().toString().trim().isEmpty()) {
                        post_type = "msg";
                    }
                    if (!changeit_id.isEmpty()) {
                        apiSendMsg();
                    } else {
                        utility.showToast(mContext, "Id cannot be blank!");
                    }
                }
            }
        });


        //m using tv_location textview to hold image url as its not it use nd to get image url we need index which is way more complicated

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonData);
            baseRequest = new BaseRequest(mContext);
            history_list = baseRequest.getDataList(jsonArray, ModelHistory.class);
            for (int i = 0; i < history_list.size(); i++) {
                if (index == i) {
                    tv_date.setText(utility.getDate(history_list.get(i).getMCreatedAt()));
                    changeit_id = history_list.get(index).getmId() + "";
                    if (!history_list.get(i).getmImage().equalsIgnoreCase("")) {
                        /* Picasso.get().load(history_list.get(i).getmImage()).placeholder(R.drawable.noimage_big).into(iv_image);*/
                        Glide.with(this)
                                .load(history_list.get(i).getmImage())
                                .placeholder(R.drawable.noimage_big)
                                .into(iv_image);
                        tv_location.setText(history_list.get(i).getmImage());
                    } else {
                        iv_image.setImageResource(R.drawable.noimage_big);
                        iv_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        iv_image.setVisibility(View.GONE);
                    }
                    tv_desc.setText(history_list.get(index).getmMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setStackFromEnd(true);
        rv_chat.setLayoutManager(layoutManager);
        rv_chat.setNestedScrollingEnabled(false);
        tribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
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

    /*API call to get chat
     */
    public void apiGetChat() {
        utility = new Utility();
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    JSONArray jsonArray;
                    JSONObject feedbackHeaderData;
                    jsonArray = jsonObject.getJSONArray("messages");


                    try {
                        feedbackHeaderData = jsonObject.getJSONObject("feedback");
                        if(!feedbackHeaderData.getString("initialMsgDate").equals("")){
                        tv_date.setText(utility.getDate(feedbackHeaderData.getString("initialMsgDate")));
                        }

                        String desc= feedbackHeaderData.getString("initialMessage");
                        int descSize = desc.length();


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


                                tv_desc.setText(feedbackHeaderData.getString("initialMessage"));
                        imageUrl = feedbackHeaderData.getString("msgImageUrl");
                        try {
                            if (!imageUrl.equalsIgnoreCase("") && !imageUrl.equals(null)) {
                                /* Picasso.get().load(history_list.get(i).getmImage()).placeholder(R.drawable.noimage_big).into(iv_image);*/
                                Glide.with(Act_HistoryDetail.this)
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    chat_list = baseRequest.getDataList(jsonArray, ModelChat.class);
                    rv_chat.setAdapter(new Ad_ChatList(chat_list, mContext, new Ad_ChatList.chatImageViewListener() {
                        @Override
                        public void chatImageClick(ModelChat chat) {
                            Bundle bundle = new Bundle();
                            bundle.putString("image", chat.getmMsgImageUrl());
                            Intent intent = new Intent(Act_HistoryDetail.this, Act_imageShow.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }));
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
        JsonObject object = Functions.getClient().getJsonMapObject("feedbackId", changeit_id
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getChatMessages);

    }

    /* API call to send a message.
     */
    public void apiSendMsg() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                et_msg.setText("");
                apiGetChat();
                //utility.showToast(mContext, "sent");
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


        //demo----------------------------------


        JsonObject object = Functions.getClient().getJsonMapObject(
                "sendFrom", sessionParam.id,
                "sendTo", "1",
                "message", message,
                "feedbackId", changeit_id,
                "postType", post_type
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.iotSendMsg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        try {
            BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
            int seletedItemId = bottomNavigationView.getSelectedItemId();
            if (R.id.nav_home != seletedItemId) {
                startActivity(new Intent(mContext, Act_NotificationList.class));
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
            if (handelBackPress.contains("homeBack")) {
                startActivity(new Intent(mContext,Act_Home.class));
                finish();
            /*startActivity(new Intent(mContext, Act_NotificationList.class));
            finish();*/

            }
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_history_detail;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_offloading;
    }
}
