package com.chetaru.tribe365_new.UI.CustomerSupport;

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
import com.chetaru.tribe365_new.API.Models.ModelSupportHistory;
import com.chetaru.tribe365_new.API.Models.SupportMessage;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_SupportChatList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.UI.Home.Act_imageShow;
import com.chetaru.tribe365_new.UI.Offloading.Act_ChatImgViewer;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
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

public class Act_SupportListDetails extends BaseActivity {

    //layout initilization
    RecyclerView rv_list;
    ImageView iv_image;
    TextView tv_location, tv_date, tv_notFound, tv_desc, tv_sendQuery;
    @BindView(R.id.rv_chat)
    RecyclerView rv_chat;

    @BindView(R.id.et_msg)
    EditText et_msg;
    @BindView(R.id.iv_send)
    TextView iv_send;
    @BindView(R.id.iv_attachment)
    ImageView iv_attachment;
    @BindView(R.id.tribe365)
    ImageView tribe;
    Utility utility;
    SessionParam sessionParam;
    List<ModelSupportHistory> history_list = new ArrayList<>();
    List<SupportMessage> chat_list;
    int index;
    String jsonData = "";
    String changeit_id = "";
    String post_type = "";
    String message = "";
    MarshMallowPermission marshMallowPermission;
    String imgBase64 = "";
    File sourceFile;
    boolean handelBackPress = false;
    private BaseRequest baseRequest;
    private final int TakePicture = 1;
    private final int SELECT_FILE = 2;
    private final int RESULT_OK = -1;
    private Bitmap bitmap = null;
    String imageUrl="";
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_support_list_details);
        marshMallowPermission = new MarshMallowPermission(this);
        isTablet=getResources().getBoolean(R.bool.isTablet);
        init();
        /*************** send notification read Status ***************/
        if (getIntent().getStringExtra("readNotificationId") != null) {
            String readNotificationId = getIntent().getStringExtra("readNotificationId");
            api_notificationRead(readNotificationId);
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (isTablet){
            }else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

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
        //pass intent from parent Class
        try {
            changeit_id = getIntent().getStringExtra("changeItId");
            index = Integer.parseInt(getIntent().getStringExtra("index"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            handelBackPress = getIntent().getBooleanExtra("backHandel", false);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //imge view on full mode
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Act_ChatImgViewer.class)
                        .putExtra("img",   imageUrl)
                        .putExtra("backHandel",   "support"));
            }
        });

        //set query
        tv_sendQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Act_SupportChat.class)
                        .putExtra("changeItId", history_list.get(index).getId() + ""));
            }
        });

        //attach a file or image
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
            history_list = baseRequest.getDataList(jsonArray, ModelSupportHistory.class);
            for (int i = 0; i < history_list.size(); i++) {
                if (index == i) {
                    tv_date.setText(utility.getDate(history_list.get(i).getCreatedAt()));
                    changeit_id = history_list.get(index).getId() + "";
                    if (!history_list.get(i).getImage().equalsIgnoreCase("")) {
                        /* Picasso.get().load(history_list.get(i).getImage()).placeholder(R.drawable.noimage_big).into(iv_image);*/
                        Glide.with(this)
                                .load(history_list.get(i).getImage())
                                .placeholder(R.drawable.noimage_big)
                                .into(iv_image);
                        iv_image.setVisibility(View.VISIBLE);
                    } else {
                        iv_image.setImageResource(R.drawable.noimage_big);
                        iv_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        iv_image.setVisibility(View.GONE);
                    }
                    tv_desc.setText(history_list.get(index).getMessage());
                    /*chat_list = history_list.get(index).getMmessages();
                    rv_chat.setAdapter(new Ad_ChatList(chat_list, mContext,new Ad_ChatList.chatImageViewListener() {
                        @Override
                        public void chatImageClick(ModelChat chat) {
                            Bundle bundle=new Bundle();
                            bundle.putString("image",chat.getmMsgImageUrl());
                            Intent intent=new Intent(Act_HistoryDetail.this, Act_imageShow.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }));
                    if (chat_list.size() == 0) {
                        rv_chat.setVisibility(View.GONE);
                    }*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setStackFromEnd(true);
        rv_chat.setLayoutManager(layoutManager);
        tribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });

        //call chat detail api
        apiGetChat();
    }

    private void apiGetChat() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    JSONArray jsonArray;
                    JSONObject supportData;
                    jsonArray = (JSONArray) jsonObject.getJSONArray("messages");
                    supportData = jsonObject.getJSONObject("supportMessage");
                    try {
                        if (supportData != null) {
                            tv_date.setText(utility.getDate(supportData.getString("initialMsgDate")));
                            String desc= supportData.getString("initialMessage");
                            int descSize=desc.length();
                            if (descSize>200){
                                ViewGroup.LayoutParams params= tv_desc.getLayoutParams();
                                params.height = 350;
                                tv_desc.setLayoutParams(params);
                            }
                            tv_desc.setText(supportData.getString("initialMessage"));
                             imageUrl = supportData.getString("msgImageUrl");
                            if (!imageUrl.equals(null) && !imageUrl.equals("")) {
                                //  Picasso.get().load(imageUrl).placeholder(R.drawable.noimage_big).into(iv_image);
                                Glide.with(Act_SupportListDetails.this)
                                        .load(imageUrl)
                                        .placeholder(R.drawable.noimage_big)
                                        .into(iv_image);
                                iv_image.setVisibility(View.VISIBLE);
                            } else {
                                iv_image.setImageResource(R.drawable.noimage_big);
                                iv_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                iv_image.setVisibility(View.GONE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    chat_list = baseRequest.getDataList(jsonArray, SupportMessage.class);
                    rv_chat.setAdapter(new Ad_SupportChatList(chat_list, mContext, new Ad_SupportChatList.SupportDetailImageViewListener() {
                        @Override
                        public void detailChatImageClick(SupportMessage chat) {
                            Bundle bundle = new Bundle();
                            bundle.putString("image", chat.getMsgImageUrl());
                            Intent intent = new Intent(Act_SupportListDetails.this, Act_imageShow.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }));
                    if (chat_list.size() > 0)
                        rv_chat.smoothScrollToPosition(chat_list.size() - 1);

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
                "supportId", changeit_id,
                "postType", post_type,
                "message", message

        );
        baseRequest.callAPIPost(1, object, ConstantAPI.sendChatMessage);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (handelBackPress) {
            startActivity(new Intent(mContext, Act_Home.class));
            finish();
        } else {
            finish();
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_support_list_details;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}