package com.chetaru.tribe365_new.UI.Offloading;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelHistory;
import com.chetaru.tribe365_new.API.Models.reflection.Reflection;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_reflectionList;
import com.chetaru.tribe365_new.Adapter.IOT_Adapter.Ad_historyList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.DismissType;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.orientation;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.GuideView;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Listener.GuideListener;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.BasicVersion.BasicHomeActivity;
import com.chetaru.tribe365_new.UI.HPTM.Act_HPTM_main;
import com.chetaru.tribe365_new.UI.Home.Act_Help;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.utility.Constant;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.NetworkConnection;
import com.chetaru.tribe365_new.utility.PaginationAdapterCallback;
import com.chetaru.tribe365_new.utility.PaginationScrollListenerOF;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Act_IOTHome extends BaseActivity implements View.OnClickListener, PaginationAdapterCallback {

    private static final int PAGE_START=1;
    private static int TOTAL_PAGE = 0;
    private boolean isLoading= false;

    private int currentPage=PAGE_START;
    private boolean isLastPage= false;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int REQ_CODE_Gallery = 1;
    private final int REQ_CODE_Camera = 1888;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public TextView _msgQueTxt = null;
    public ImageView _happyTxt = null;
    public ImageView _nutralTxt = null;
    public ImageView _sadTxt = null;
    public TextView _notPreTxt = null;

    TextView tv_send,  tv_no_feedback;


    ImageView   iv_image, iv_help, iv_msg, iv_history, iv_selectImage;
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
    Dialog feed_dialog;
    String qId = "";
    String que = "";
    String body = "";
    //@BindView(R.id.tribe365)
    ImageView iv_top_companylogo;
    ArrayList<ModelHistory> list;
    DrawerLayout drawerLayout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_hptm)
    TextView tv_action_right;
    boolean isTablet;
    LinearLayoutManager linearLayoutManager= null;


    private BaseRequest baseRequest;
    private final String lat_current = "";
    private final String lon_current = "";
    private List<Address> addresses;
    private final int TakePicture = 1;
    private final int SELECT_FILE = 2;
    private final int RESULT_OK = -1;
    private Bitmap bitmap = null;
    int appTour=0;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    String data="";


    TextView reflection_txt;
    TextView offloading_txt;
    boolean flag=true;
    ArrayList<Reflection> ref_list;

    /********* handle  new layout ****************/
    LinearLayout offloading_main_ll;
    LinearLayout reflection_main_ll;
    TextView ref_txt_send,no_reflection_txt,view_all_msg_txt;
    ImageView ref_iv_selectImage;
    EditText ref_et_comment;
    RecyclerView rv_reflection;
    Ad_historyList adHistoryList;



    @SuppressLint({"UseCompatLoadingForDrawables", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_iot_nav);
        ButterKnife.bind(this);
        tv_send = findViewById(R.id.tv_send);
        ref_list=new ArrayList<>();


        iv_image = findViewById(R.id.iv_image);
        et_comment = findViewById(R.id.et_comment);
        iv_help = findViewById(R.id.iv_help);
        iv_msg = findViewById(R.id.iv_msg);
        iv_history = findViewById(R.id.iv_history);
        rv_list = findViewById(R.id.rv_list);
        tv_no_feedback = findViewById(R.id.tv_no_feedback);
        iv_selectImage = findViewById(R.id.iv_selectImage);

        iv_top_companylogo = findViewById(R.id.tribe365);
        /************ new layout initilize **********/
        reflection_txt=findViewById(R.id.reflection_txt);
        offloading_txt=findViewById(R.id.offloading_txt);
        offloading_main_ll=findViewById(R.id.offloading_main_ll);
        reflection_main_ll=findViewById(R.id.reflection_main_ll);

        ref_iv_selectImage=findViewById(R.id.ref_iv_selectImage);
        ref_et_comment=findViewById(R.id.ref_et_comment);
        ref_txt_send=findViewById(R.id.ref_txt_send);
        no_reflection_txt=findViewById(R.id.no_reflection_txt);
        rv_reflection=findViewById(R.id.rv_reflection);
        view_all_msg_txt=findViewById(R.id.view_all_msg_txt);
        isTablet=getResources().getBoolean(R.bool.isTablet);
        /*********** show Title layout **********/
        showTitleChange(flag);
        /************* show App Tour *************/
         appTour = getIntent().getIntExtra("appTourType", 0);
         if(appTour!=0) {
             Handler h = new Handler();
             long delayInMilliseconds = 1000;
             h.postDelayed(new Runnable() {
                 public void run() {

                     // ShowIntro("SetTheme", getString(R.string.belief_ppt), R.id.rv_belief_detailslist, 8);
                     ShowIntro(getString(R.string.feedback_ppt), R.id.et_comment, 1);

                 }
             }, delayInMilliseconds);

         }
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        /*add a tribe image from session*/
        if (!sessionParam.organisation_logo.equals("")) {
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);
        }


        try {
            que = getIntent().getStringExtra("que");
            body = getIntent().getStringExtra("body");
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        //redirect to home screen
        iv_top_companylogo.setOnClickListener(v -> callHomeAct(mContext));


        activity = this;
        marshMallowPermission = new MarshMallowPermission(this);
        iv_image.setOnClickListener(this);

        iv_history.setOnClickListener(this);

        iv_msg.setOnClickListener(this);
        iv_help.setOnClickListener(this);
        iv_selectImage.setOnClickListener(this);
        tv_action_right.setOnClickListener(this);
        offloading_txt.setOnClickListener(this);
        reflection_txt.setOnClickListener(this);


        et_comment.setOnTouchListener((v, event) -> {
            if (et_comment.hasFocus()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
            }
            return false;
        });
        ref_et_comment.setOnTouchListener((v, event) -> {
            if (ref_et_comment.hasFocus()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
            }
            return false;
        });

        // user lambda function
        tv_send.setOnClickListener(v->{
            if (et_comment.getText().toString().trim().isEmpty()) {
                utility.showToast(mContext, getString(R.string.please_enter_offloading));
                return;
            }
            msg = et_comment.getText().toString().trim();
            api_postDetails();
        });
        ref_txt_send.setOnClickListener(v -> {
            if (ref_et_comment.getText().toString().trim().isEmpty()){
                utility.showToast(mContext,getString(R.string.please_enter_reflection));
                return;
            }else {
                ref_postDetail();
            }

        });



        deviceId = utility.getAndroidID(this);
        sessionParam.saveDeviceId(mContext, deviceId);
        permission();
       LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(Act_IOTHome.this,LinearLayoutManager.VERTICAL,false);
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        list= new ArrayList<>();
        rv_list.setItemAnimator(new DefaultItemAnimator());
        adHistoryList= new Ad_historyList(list,mContext,"");
        rv_list.setAdapter(adHistoryList);
        rv_list.setLayoutManager(linearLayoutManager);
        rv_list.setNestedScrollingEnabled(false);
        rv_list.addOnScrollListener(new PaginationScrollListenerOF(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading= true;
                currentPage+=1;
                api_next_page_history();

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


        LinearLayoutManager ref_linearLayoutManager =new LinearLayoutManager(mContext);
        ref_linearLayoutManager.setReverseLayout(true);
        ref_linearLayoutManager.setStackFromEnd(true);
        rv_reflection.setNestedScrollingEnabled(false);
        rv_reflection.setLayoutManager(ref_linearLayoutManager);
        //apihistory();
        getOffloadingFirst();
        api_ref_history();
       // setRef_list();
    }

    private void setRef_list(){

        /*for (int i=1;i<10;i++){
            Reflection newValue = new Reflection();
            newValue.setmId(i + "");
            newValue.setmCreated_at("2021-05-03 10:29:53");
            newValue.setmName("Ritesh Bagul");
            if(i==1) {
                newValue.setDescription("Hello  this is first reflection. I am glad to introduce the new module" +
                        "in Tribe365 need:");

            }else if (i==2){
                newValue.setDescription("It is high performing team members.");
            }else if (i==3){
                newValue.setDescription("Hope this goes Well.");
            }else {
                newValue.setDescription("sdfsfsdfsdfsd");
            }
            ref_list.add(newValue);
        }*/
        rv_reflection.setAdapter(new Ad_reflectionList(ref_list, mContext));

    }

    private void showTitleChange(boolean flag) {
        if (!flag){
            offloading_main_ll.setVisibility(View.GONE);
            reflection_main_ll.setVisibility(View.VISIBLE);
            offloading_txt.setBackgroundColor(this.getResources().getColor(R.color.white));
            offloading_txt.setTextColor(this.getResources().getColor(R.color.black));
            reflection_txt.setBackgroundColor(this.getResources().getColor(R.color.red));
            reflection_txt.setTextColor(this.getResources().getColor(R.color.white));
        }else{
            offloading_main_ll.setVisibility(View.VISIBLE);
            reflection_main_ll.setVisibility(View.GONE);
            offloading_txt.setBackgroundColor(this.getResources().getColor(R.color.red));
             offloading_txt.setTextColor(this.getResources().getColor(R.color.white));
             reflection_txt.setBackgroundColor(this.getResources().getColor(R.color.white));
            reflection_txt.setTextColor(this.getResources().getColor(R.color.black));

        }




    }

    /******************* Api calling  ****************************/
    /* API call to post your feedback.*/
    public void api_postDetails() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    String msg = jsonObject.getString("message");
                    utility.showToast(mContext,msg);
                }catch (Exception e){
                    e.printStackTrace();
                }

                et_comment.setText("");
                iv_image.setImageResource(R.drawable.add_image);
                iv_image.setVisibility(View.GONE);
                //apihistory();
                getOffloadingFirst();
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
        JsonObject object = Functions.getClient().getJsonMapObject("message", et_comment.getText().toString().trim(),
                "userId", sessionParam.id,
                "orgId", sessionParam.orgId,
                "image", imgBase64
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.postFeedback);
    }

    public void ref_postDetail(){
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    String msg = jsonObject.getString("message");
                    utility.showToast(mContext,msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
                ref_et_comment.setText("");
                api_ref_history();

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
        JsonObject object=Functions.getClient().getJsonMapObject("message",ref_et_comment.getText().toString().trim(),
                "userId",sessionParam.id,
                "orgId",sessionParam.orgId,
                "image", imgBase64);
        baseRequest.callAPIPost(1,object,ConstantAPI.postHPTMReflection);
    }
    /********************************************************************/


    /******************** onclick action *******************************/
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_hptm:
               // startActivity(new Intent(mContext, Act_ActionList.class));
                Intent hptm_intent=new Intent(mContext, Act_HPTM_main.class);
                hptm_intent.putExtra("backHandel","offloading");
                startActivity(hptm_intent);
                break;

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
            case R.id.iv_history:
                startActivity(new Intent(mContext, Act_History.class));
                break;
            case R.id.iv_msg:
                startActivity(new Intent(mContext, Act_MsgList.class));
                break;
            case R.id.iv_help:
                startActivity(new Intent(mContext, Act_Help.class).putExtra("url", "IOT.html"));
                break;
            case R.id.offloading_txt:
                flag=true;
                showTitleChange(flag);
                break;
            case R.id.reflection_txt:
                flag=false;
                showTitleChange(flag);
                break;


        }
    }

    /************** add permission on list *******************/
    /*request user for certain permission*/
    private void permission() {
        //datafinish = true;
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();

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
                return shouldShowRequestPermissionRationale(permission);
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

                imgBase64 = Utility.image_to_Base64(mContext, utility.getPath(utility.getImageUri(mContext, bitmap), mContext));
            }
            if (requestCode == SELECT_FILE) {
                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    sourceFile = new File(utility.getPath(data.getData(), mContext));
                    bitmap = new Compressor(mContext).compressToBitmap(sourceFile);
                    imgBase64 = Utility.image_to_Base64(mContext, utility.getPath(data.getData(), mContext));
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

    /********************* Api Selection  **************/
    /*not in use*/
    public void apiGetQuestion() {
        utility = new Utility();
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObjectTotal = new JSONObject(Json);
                    int total = jsonObjectTotal.getInt("total");
                    if (total > 0) {
                        JSONArray jsonArray = new JSONArray(object.toString());
                        JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(0).toString());
                        qId = jsonObject.getString("ques_id");
                        String question = jsonObject.getString("question");
                        dialog_feedback(question, qId);

                    }
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
        Map<String, String> map = new HashMap<>();
        map.put("email_id", sessionParam.email);
        map.put("app_name", "tribe365");

        //baseRequest.callAPIGETCustomURLTellSid(1, map, "http://live.thechangeconsultancy.co/demo_tellsid/index.php/apitellsid/getquestion/"); //test
        //demo-----------------------------------------
        baseRequest.callAPIGETCustomURLTellSid(1, map, "http://demo.thechangeconsultancy.co/tellsid/index.php/apitellsid/getquestion/"); //live
//

        //live-----------------------------------------
//       baseRequest.callAPIGETCustomURLTellSid(1, map, "http://tellsid.softintelligence.co.uk/index.php/apitellsid/getquestion/"); //live
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessionParam = new SessionParam(mContext);
        try {
            que = getIntent().getStringExtra("que");
            body = getIntent().getStringExtra("body");
        } catch (NullPointerException npe) {

            npe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* if (que != null) {
            //dialog_feedback(body,que);
        } else {
            // apiGetQuestion();
        }*/

    }

    /*not in use*/
    public void apiGetUserAnswer(String reply) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Tell Sid")
                        .setMessage("Thanks for your Offloading")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                if (NetworkConnection.checkNetworkStatus(mContext)) {
                                    apiGetQuestion();
                                } else {
                                    Constant.showAlertDialog(getResources().getString(R.string.messageText), getResources().getString(R.string.pleaseCheckInternet), mContext, false);
                                }
                            }
                        })
                        .show();


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

        RequestBody email_id = RequestBody.create(MediaType.parse("text/plain"), sessionParam.email);
        RequestBody ques_id = RequestBody.create(MediaType.parse("text/plain"), qId);
        RequestBody reply_ = RequestBody.create(MediaType.parse("text/plain"), reply);
        RequestBody app_name = RequestBody.create(MediaType.parse("text/plain"), "tribe365");

        //baseRequest.callAPIgetUserAnswer(1, "http://live.thechangeconsultancy.co/demo_tellsid/index.php/apitellsid/getuseranser/", //test
        //demo------------------------------
        baseRequest.callAPIgetUserAnswer(1, "http://demo.thechangeconsultancy.co/tellsid/index.php/apitellsid/getuseranser/", //live
                email_id,
                ques_id,
                reply_,
                app_name); //test url
        //live-----------------------
//        baseRequest.callAPIgetUserAnswer(1, "http://tellsid.softintelligence.co.uk/index.php/apitellsid/getuseranser/", //live
//                email_id,
//                ques_id,
//                reply_,
//                app_name); //live url
    }

    /*not in use*/
    public void dialog_feedback(String quetion, String qId) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_iot_feedback);
        dialog.setCanceledOnTouchOutside(false);
        TextView _msgQueTxt = dialog.findViewById(R.id.msgqueTxt);

        _happyTxt = dialog.findViewById(R.id.happy);
        _nutralTxt = dialog.findViewById(R.id.neutral);
        _sadTxt = dialog.findViewById(R.id.sad);
        _notPreTxt = dialog.findViewById(R.id.notTxt);

        _happyTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiGetUserAnswer("Happy");
                dialog.dismiss();
            }
        });
        _nutralTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiGetUserAnswer("Neutral");
                dialog.dismiss();
            }
        });
        _sadTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiGetUserAnswer("Sad");
                dialog.dismiss();
            }
        });
        _notPreTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiGetUserAnswer("Prefer Not To Answer");
                dialog.dismiss();
            }
        });
        _msgQueTxt.setText(quetion);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
               /* perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);

                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);*/
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                try {
                    // Fill with results
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    // init();
                } else {
                    AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
                    ad.setMessage("Some Permission is Denied,You could not able to access this App!Please allow for all permission").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            permission();
                        }
                    }).setCancelable(false).create().show();
                    // Permission Denied
                    // Toast.makeText(HomeActivity.this, "Some Permission is Denied,You could not able to access some functionalities of app.", Toast.LENGTH_LONG).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void api_ref_history(){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{

                    JSONArray jsonArray;
                    jsonArray=(JSONArray) object;
                    ref_list=baseRequest.getDataList(jsonArray,Reflection.class);
                    if (ref_list.size()>0){
                        no_reflection_txt.setVisibility(View.GONE);
                        rv_reflection.setVisibility(View.VISIBLE);
                    }else {
                        no_reflection_txt.setVisibility(View.VISIBLE);
                        rv_reflection.setVisibility(View.GONE);
                    }
                    setRef_list();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {

            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {

            }
        });
        JsonObject jsonObject=Functions.getClient().getJsonMapObject("userId",sessionParam.id);
        baseRequest.callAPIPost(3,jsonObject, ConstantAPI.getHptmReflectionDetail);
    }
    /*APIs call to get previous feedback.
     */
    public void apihistory() {
        currentPage=PAGE_START;
        utility = new Utility();
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
                  List<ModelHistory>  list = baseRequest.getDataList(jsonArray, ModelHistory.class);
                   // Collections.reverse(list);


                    //rv_list.setVisibility(View.VISIBLE);
                    if (list.size() > 0) {
                        tv_no_feedback.setVisibility(View.GONE);
                        rv_list.setVisibility(View.VISIBLE);
                    } else {
                        tv_no_feedback.setVisibility(View.VISIBLE);
                        rv_list.setVisibility(View.GONE);
                    }
                    rv_list.setAdapter(new Ad_historyList(list, mContext, object.toString()));
                  //  adHistoryList.addAll(list);
                    if (currentPage<=TOTAL_PAGE)
                        adHistoryList.addLoadingFooter();
                    else
                        isLastPage =true;

                    if (currentPage == TOTAL_PAGE){
                        isLastPage = true;
                    }
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
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject(
                "userId", sessionParam.id
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getFeedbackDetail);
    }
    private void getOffloadingFirst(){
        currentPage=PAGE_START;
        isLastPage = false;
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    list.clear();
                    JSONObject jsonObject = new JSONObject(Json);
                    TOTAL_PAGE = jsonObject.optInt("totalPageCount");
                    JSONArray jsonArray;
                    jsonArray = (JSONArray) object;
                    list = baseRequest.getDataList(jsonArray, ModelHistory.class);
                    // Collections.reverse(list);
                    //adHistoryList.addAll(list);
                    try{
                    if (list.size()>0){
                        for (int i=0;i< list.size();i++){
                            if (list.get(i).getmId() == 0){
                                list.remove(i);
                            }
                        }
                    }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (list.size() > 0) {
                        tv_no_feedback.setVisibility(View.GONE);
                        rv_list.setVisibility(View.VISIBLE);
                    } else {
                        tv_no_feedback.setVisibility(View.VISIBLE);
                        rv_list.setVisibility(View.GONE);
                    }
                    adHistoryList= new Ad_historyList(list,mContext,"");
                    rv_list.setAdapter(adHistoryList);
                    if (currentPage <= TOTAL_PAGE)
                        adHistoryList.addLoadingFooter();
                    else
                        isLastPage =true;

                    if (currentPage == TOTAL_PAGE){
                        isLastPage = true;
                    }else {
                        isLastPage = false;
                    }


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

        JsonObject object = Functions.getClient().getJsonMapObject(
                "userId", sessionParam.id,"page", currentPage+""
        );
        baseRequest.callAPIPost(1,object,ConstantAPI.getFeedbackDetail);
    }

    private void api_next_page_history(){
        utility = new Utility();
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    TOTAL_PAGE = jsonObject.optInt("totalPageCount");
                    JSONArray jsonArray;
                    jsonArray = (JSONArray) object;
                    list = baseRequest.getDataList(jsonArray, ModelHistory.class);
                    //Collections.reverse(list);
                    adHistoryList.removeLoadingFooter();
                    isLoading = false;

                    adHistoryList.addAll(list);
                    if (currentPage != TOTAL_PAGE) adHistoryList.addLoadingFooter();
                    else isLastPage = true;

                    if (currentPage == TOTAL_PAGE){
                        isLastPage = true;
                    }

                /*rv_list.setAdapter(new Ad_historyList(list, mContext, object.toString()));
                rv_list.setVisibility(View.VISIBLE);
                if (list.size() > 0) {

                    tv_no_feedback.setVisibility(View.GONE);
                    rv_list.setVisibility(View.VISIBLE);
                }else {
                    tv_no_feedback.setVisibility(View.VISIBLE);
                    rv_list.setVisibility(View.GONE);
                }*/
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
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject(
                "userId", sessionParam.id,"page", currentPage+""
        );
        baseRequest.callAPIPost(2, object, ConstantAPI.getFeedbackDetail);
    }

    /****************************** show App Tour method ***************/
    private void ShowIntro(String stringTitle,int viewId,final int type){
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       // Paint p =new Paint();
        //p.setTextSize(getResources().getDimension(R.dimen.text_size_big));
        int sizeInPixels = 18;
        if (isTablet){
            sizeInPixels=24;
        }else {
            sizeInPixels=20;
        }
        builder=new GuideView.Builder(this)
                    .setContentText(stringTitle)
                    .setTargetView(findViewById(viewId))
                    .setContentTextSize(sizeInPixels)
                    .setTitleTextSize(sizeInPixels)
                    .setDismissType(DismissType.skipe)
                .setTitleGravity(Gravity.LEFT)
                .setContentGravity(Gravity.LEFT)
                .setButtonGravity(Gravity.LEFT)
                .setPaddingButton(10,0,10,0)
                .setButtonText("Skip Tutorial")
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {

                    }
                });
       /* if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // builder.setOrientation(orientation.landscape);
        }else if (!isTablet){
            builder.setOrientation(orientation.portrait);
        }*/
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // builder.setOrientation(orientation.landscape);
            if (!isTablet){
                builder.setOrientation(orientation.portrait);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }else {
                builder.setOrientation(orientation.landscape);
            }
        }

        if (type == 11) {
            builder.setButtonText("End").build();
            builder.setNextButtonText("").build();
            mGuideView.dismiss();
        }
        if (type>11){
            mGuideView.dismiss();
        }


        mGuideView=builder.build();
        mGuideView.show();
        mGuideView.mMessageView.skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGuideView.dismiss();
                callHomeAct(mContext);
            }
        });
        mGuideView.mMessageView.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type < 12)
                    mGuideView.dismiss();
                if (type == 1) {
                    Intent intent= null;
                    if (sessionParam.loginVersion == 3) {
                         intent = new Intent(mContext, BasicHomeActivity.class);
                        intent.putExtra("appTourType", 8);
                        startActivity(intent);
                    } else {
                     intent = new Intent(mContext, Act_Home.class);
                    intent.putExtra("appTourType", 8);
                    startActivity(intent);
                    }
                    /*Intent intent = new Intent(mContext, Act_DOT_Details.class);
                    intent.putExtra("checklist", "");
                    intent.putExtra("data", data);
                    intent.putExtra("introType", 8);
                    startActivity(intent);*/

                   // ShowIntro(getString(R.string.thanks_ppt), R.id.well_come_msg_view, 11);

                }
            }
        });
        updatingForDynamicLocationViews();
    }
    private void updatingForDynamicLocationViews() {
        iv_msg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mGuideView.updateGuideViewLocation();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_iot_nav;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_offloading;
    }


    @Override
    public void retryPageLoad() {

    }
}
