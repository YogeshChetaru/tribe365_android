package com.chetaru.tribe365_new.UI.CustomerSupport;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelSupportHistory;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_supportList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.UserInfo.Act_ProfileUser;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.PaginationScrollListener;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;

public class Act_CustomerSupport extends BaseActivity implements View.OnClickListener {

    private static final int PAGE_START=1;
    private static int TOTAL_PAGE = 1;
    private boolean isLoading= false;
    private int currentPage=PAGE_START;
    private boolean isLastPage= false;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int REQ_CODE_Gallery = 1;
    private final int REQ_CODE_Camera = 1888;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    TextView tv_send, tv_no_feedback;
    ImageView  iv_image, iv_help, iv_msg, iv_history, iv_selectImage;

    EditText et_comment;
    SessionParam sessionParam;
    RecyclerView rv_list;
    Utility utility;
    MarshMallowPermission marshMallowPermission;
    Activity activity;
    String imgBase64 = "";
    File sourceFile;
    String locationDetail = "", msg = "";
    String deviceId = "";
    String previousQuestion = "";
    //Layout view handel
    ImageView iv_top_companylogo;


    ArrayList<ModelSupportHistory> list;
    Ad_supportList ad_supportList;


    private BaseRequest baseRequest;
    private String lat_current = "";
    private String lon_current = "";
    private List<Address> addresses;
    private int TakePicture = 1, SELECT_FILE = 2, RESULT_OK = -1;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_customer_support);
        ButterKnife.bind(this);
        tv_send = findViewById(R.id.tv_send);


        iv_image = findViewById(R.id.iv_image);
        et_comment = findViewById(R.id.et_comment);
        iv_help = findViewById(R.id.iv_help);
        iv_msg = findViewById(R.id.iv_msg);
        iv_history = findViewById(R.id.iv_history);
        rv_list = findViewById(R.id.rv_list);
        tv_no_feedback = findViewById(R.id.tv_no_feedback);
        iv_selectImage = findViewById(R.id.iv_selectImage);

        iv_top_companylogo = findViewById(R.id.tribe365);


        utility = new Utility();
        sessionParam = new SessionParam(mContext);


        if (!sessionParam.organisation_logo.equals("")) {
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);
        }

        iv_top_companylogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });

//        if(sessionParam.role.equalsIgnoreCase("3")){
//            api_unreadNotification();
//        }


        //initialize onClick function
        activity = this;
        marshMallowPermission = new MarshMallowPermission(this);
        iv_image.setOnClickListener(this);
        iv_history.setOnClickListener(this);

        iv_msg.setOnClickListener(this);
        iv_help.setOnClickListener(this);
        iv_selectImage.setOnClickListener(this);


        //navigation click



        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_comment.getText().toString().trim().equals("")) {
                    utility.showToast(mContext, getString(R.string.custom_support_msg));
                    return;
                } else {
                    msg = et_comment.getText().toString().trim();
                    api_postDetails(msg);

                }
            }
        });

        deviceId = utility.getAndroidID(this);
        sessionParam.saveDeviceId(mContext, deviceId);
        permission();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setLayoutManager(linearLayoutManager);
        list= new ArrayList<>();
       // ad_supportList= new Ad_supportList(list,mContext,"");

        rv_list.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading= true;
                currentPage+=1;
                api_next_Support_page();

            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGE;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        //call support  msgs History
        supportHistory();
    }

    public void supportHistory() {
        currentPage=PAGE_START;
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    list.clear();
                    JSONObject jsonObject = new JSONObject(Json);
                    TOTAL_PAGE = jsonObject.optInt("totalPageCount");
                    JSONArray jsonArray;
                    jsonArray = (JSONArray) object;
                    List<ModelSupportHistory> list = baseRequest.getDataList(jsonArray, ModelSupportHistory.class);
                   // Collections.reverse(list);
                    ad_supportList= new Ad_supportList(list,mContext,"");
                    ad_supportList.notifyDataSetChanged();
                    rv_list.setAdapter(ad_supportList);
                    rv_list.setVisibility(View.VISIBLE);
                    iv_image.setVisibility(View.GONE);
                    if (list.size() > 0) {
                        try {
                            if (list.get(0).getId()>0){
                            tv_no_feedback.setVisibility(View.GONE);
                            rv_list.setVisibility(View.VISIBLE);
                            }else {
                                tv_no_feedback.setVisibility(View.VISIBLE);
                                rv_list.setVisibility(View.GONE);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        tv_no_feedback.setVisibility(View.VISIBLE);
                        rv_list.setVisibility(View.GONE);
                    }
                    if (currentPage<=TOTAL_PAGE)
                        ad_supportList.addLoadingFooter();
                    else
                        isLastPage= true;

                    if (currentPage == TOTAL_PAGE){
                        isLastPage = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                // utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject(
                "userId", sessionParam.id,"Page",currentPage+""
                // "userId" ,335+""
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getSupportHistory);
    }


    public void api_next_Support_page() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONArray jsonArray;
                    jsonArray = (JSONArray) object;
                    List<ModelSupportHistory> list = baseRequest.getDataList(jsonArray, ModelSupportHistory.class);
                    ad_supportList.removeLoadingFooter();
                    isLoading = false;
                   // Collections.reverse(list);
                    ad_supportList.addAll(list);
                    if (currentPage != TOTAL_PAGE)
                        ad_supportList.addLoadingFooter();
                    else
                        isLastPage = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                // utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject(
                "userId", sessionParam.id,"page",currentPage+""
                // "userId" ,335+""
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getSupportHistory);
    }
    /* API call to post your Custom Support.*/
    public void api_postDetails(String msg) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

                et_comment.setText("");
                iv_image.setImageResource(R.drawable.add_image);
                iv_image.setVisibility(View.GONE);
                tv_no_feedback.setVisibility(View.GONE);
                // rv_list.setVisibility(View.GONE);
                supportHistory();

                try {
                    String msg = "";
                    JSONObject obj = new JSONObject(Json);
                    msg = obj.optString("message");
                    utility.showToast(mContext, msg);
                }catch (Exception e){
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
        JsonObject object = Functions.getClient().getJsonMapObject("message", msg,
                "userId", sessionParam.id,
                "orgId", sessionParam.orgId,
                "image", imgBase64
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.addCustomerSupport);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_selectImage:
                if (!marshMallowPermission.checkPermissionForCamera() && !marshMallowPermission.checkPermissionForExternalStorage()) {
                    marshMallowPermission.requestPermissionForCamera();
                    marshMallowPermission.requestPermissionForExternalStorage();
                } else if (!marshMallowPermission.checkPermissionForCamera()) {
                    marshMallowPermission.requestPermissionForCamera();
                } else if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                    marshMallowPermission.requestPermissionForExternalStorage();
                } else {
                    selectImage();
                }

                break;

        }
    }

    /*request user for certain permission*/
    private void permission() {
        //datafinish = true;
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
       /* if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
            permissionsNeeded.add("Location");
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("phone status");*/
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_NETWORK_STATE))
            permissionsNeeded.add("Network state");
        if (!addPermission(permissionsList, Manifest.permission.INTERNET))
            permissionsNeeded.add("Internet");

        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write storage");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                /*showMessageOKCancel(message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                        }
                    }
                });*/
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            return;
        }
        //init();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                //Check for Rationale Optiong
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mContext)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    private void selectImage() {
        //here user will get options to choose image
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
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
                } else if (items[item].equals("Choose from Library")) {
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
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                iv_image.setImageBitmap(bitmap);
                iv_image.setVisibility(View.VISIBLE);

                imgBase64 = utility.image_to_Base64(mContext, utility.getPath(utility.getImageUri(mContext, bitmap), mContext));
            }
            if (requestCode == SELECT_FILE) {
                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    sourceFile = new File(utility.getPath(data.getData(), mContext));
                    bitmap = new Compressor(mContext).compressToBitmap(sourceFile);
                    imgBase64 = utility.image_to_Base64(mContext, utility.getPath(data.getData(), mContext));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iv_image.setImageBitmap(bitmap);
                iv_image.setVisibility(View.VISIBLE);

            }
                /*bitmap = utility.decodeSampledBitmapFromResource(data.getExtras().get("data").toString(), 100, 100);
                img_1.setVisibility(View.VISIBLE);
                img_1.setImageBitmap(bitmap);*/
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_customer_support;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.nav_profile != seletedItemId) {
            mContext.startActivity(new Intent(Act_CustomerSupport.this,Act_ProfileUser.class));
        }else {
            super.onBackPressed();
        }

    }
}