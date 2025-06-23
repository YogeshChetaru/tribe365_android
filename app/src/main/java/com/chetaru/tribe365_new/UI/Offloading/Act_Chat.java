package com.chetaru.tribe365_new.UI.Offloading;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.chetaru.tribe365_new.utility.RecyclerItemClickListener;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;

public class Act_Chat extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_chat)
    RecyclerView rv_chat;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_msg)
    EditText et_msg;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_send)
    ImageView iv_send;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_attachment)
    ImageView iv_attachment;

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
    private final int TakePicture = 1;
    private final int SELECT_FILE = 2;
    private final int RESULT_OK = -1;
    private Bitmap bitmap = null;
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_chat);
        init();
        tribe365.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
    }

    /*used to initialise all the views
     */
    public void init() {
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setStackFromEnd(true);
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
        try {
            changeit_id = getIntent().getStringExtra("changeItId");
            apiGetChat();
        } catch (NullPointerException npe) {

        }

        rv_chat.setLayoutManager(layoutManager);

        rv_chat.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_chat, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!list.get(position).getmMsgImageUrl().equalsIgnoreCase(""))
                    startActivity(new Intent(mContext, Act_ChatImgViewer.class)
                            .putExtra("img", list.get(position).getmMsgImageUrl()));
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        list = new ArrayList<>();
        //ArrayList<ModelMessageList> list = (ArrayList<ModelMessageList>) getIntent().getSerializableExtra("msgList");
        //position = getIntent().getStringExtra("position") + "";
        //.get(Integer.parseInt(position)).getItemchat();
        //rv_chat.setAdapter(new Ad_ChatList(list, mContext));
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
                    utility.showToast(mContext, getString(R.string.please_enter_offloading));
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
                    jsonArray = jsonObject.getJSONArray("messages");
                    list = baseRequest.getDataList(jsonArray, ModelChat.class);
                    rv_chat.setAdapter(new Ad_ChatList(list, mContext, new Ad_ChatList.chatImageViewListener() {
                        @Override
                        public void chatImageClick(ModelChat chat) {

                            Bundle bundle = new Bundle();
                            bundle.putString("image", chat.getmMsgImageUrl());
                            Intent intent = new Intent(Act_Chat.this, Act_imageShow.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }));
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
    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_chat;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_offloading;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
    }
}
