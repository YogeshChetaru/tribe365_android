package com.chetaru.tribe365_new.UI.UserInfo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.BasicVersion.BasicHomeActivity;
import com.chetaru.tribe365_new.UI.Home.Act_FreeVersionHome;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.utility.ErrorLayout;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActLogin extends BaseActivity implements View.OnClickListener{

    private final static int ALL_PERMISSIONS_RESULT = 101;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    Context context;
    Utility utility;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    int role = 3;
    String device_id = "";
    String fcm_token = "";
    MarshMallowPermission marshMallowPermission;
    int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 101;
    private ErrorLayout errorLayout;
    private ArrayList<String> permissionsToRequest;
    private final ArrayList<String> permissionsRejected = new ArrayList<>();
    private final ArrayList<String> permissions = new ArrayList<>();
    boolean Exist_user_selected=true;

    /********************* initialize layout File **************/
    /********** top Header layout *****/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_topblock)
    LinearLayout ll_topblock;
    /********** top slide up layout *****/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.slide_up_ll)
    LinearLayout slide_up_ll;

    /********** Tab switch layout *****/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_existing_user)
    TextView tv_existing_user;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_new_user)
    TextView tv_new_user;

    /********** Edit page layout *****/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_username)
    EditText et_username;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_password)
    EditText et_password;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.confirm_password_ll)
    LinearLayout confirm_password_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    /********* button layout************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_signin)
    TextView tv_signin;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_forgotPass)
    TextView tv_forgotPass;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_help)
    TextView tv_help;
    /*********** unUsed layout *********/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rb_user)
    RadioButton rb_user;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rb_super_admin)
    RadioButton rb_super_admin;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_act_login);
        ButterKnife.bind(this);

        /********* initialize view and other Permission *************/
        init();
        /********* for soft input keyboard *************/
        ll_topblock.setOnClickListener(v->{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });

        /********* get password string value *************/
        et_password.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z0-9$&+,:;=?@#|'<>.^*()%!]+")) {
                            // here no space character
                            return cs;
                        }
                        return "";
                    }
                }
        });
        /********* get Confirm Password value *************/
        et_confirm_password.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z0-9$&+,:;=?@#|'<>.^*()%!]+")) { // here no space character
                            return cs;
                        }
                        /*if (cs.toString().matches(et_password.getText().toString())){
                            return cs;
                        }else {
                            errorLayout.showError("password not match ");
                        }*/
                        return "";
                    }
                }
        });

    }

   /**************** initialize value and add permission***************/
    public void init() {
        context = this;
        utility = new Utility();
        sessionParam = new SessionParam(context);
        marshMallowPermission = new MarshMallowPermission(this);
        permission();
        errorLayout = new ErrorLayout(findViewById(R.id.error_rl));
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        device_id = utility.getAndroidID(this);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }


    /************* ha***/
    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    /*request user for certain permission*/
    private void permission() {
        //datafinish = true;
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        /*if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("GPS");

        if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
            permissionsNeeded.add("Location");
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("phone status");*/
        if (!addPermission(permissionsList, Manifest.permission.INTERNET))
            permissionsNeeded.add("Internet");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_NETWORK_STATE))
            permissionsNeeded.add("Network state");
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


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ALL_PERMISSIONS_RESULT) {
            for (String perms : permissionsToRequest) {
                if (!hasPermission(perms)) {
                    permissionsRejected.add(perms);
                }
            }

            if (permissionsRejected.size() > 0) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                        showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    }
                                });
                    }
                }

            }
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ActLogin.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.tv_signin, R.id.tv_forgotPass, R.id.et_username, R.id.et_password, R.id.rb_super_admin, R.id.rb_user, R.id.tv_help,
                R.id.tv_new_user,R.id.tv_existing_user})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_signin:
                errorLayout.hideError();
                if (validation()) {
                    //getDeviceId();
                    if (!Exist_user_selected){
                        if (et_confirm_password.getText().toString().trim().isEmpty()){
                           // errorLayout.showError("Please enter Confirm Password");
                            errorLayout.showError(getString(R.string.confirm_pass_validation));
                            return;
                        }else {
                            if (ValidationPassword()) {
                                setpasswordApi();
                            }
                        }
                    }else {
                        loginApi();
                    }
                }
                break;
            case R.id.tv_forgotPass:
                startActivity(new Intent(mContext, Act_Forgot_Password.class));
                break;
            case R.id.et_username:
            case R.id.et_password:
            case R.id.et_confirm_password:
                errorLayout.hideError();
                break;
            case R.id.rb_super_admin:
                role = 1;
                break;
            case R.id.rb_user:
                role = 3;
                break;
            case R.id.tv_help:
//              startActivity(new Intent(mContext,Act_Login_Home_Help.class));
                dialog();
                break;
            case R.id.tv_existing_user:
                if (!Exist_user_selected)
                switchTab();
                break;
            case R.id.tv_new_user:
                if (Exist_user_selected)
                switchTab();
                break;
        }
    }


    private boolean ValidationPassword() {
        boolean temp=true;
        String pass=et_password.getText().toString();
        String cpass=et_confirm_password.getText().toString();

         if(!pass.equals(cpass)){
             errorLayout.showError(getString(R.string.password_and_confirm_pass_validation));
             temp=false;
        }
        return temp;
    }

    private void switchTab() {


            if (Exist_user_selected) {
                /******************* handle New user ************/
                //ll_topblock.startAnimation(AnimationUtils.loadAnimation(this,R.anim.move_up));
               // slide_up_ll.startAnimation(AnimationUtils.loadAnimation(this,R.anim.move_up));

                /*overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);*/

                //Toast.makeText(mContext, "archive selected", Toast.LENGTH_SHORT).show();
                confirm_password_ll.setVisibility(View.VISIBLE);
                tv_existing_user.setTextColor(getResources().getColor(R.color.black));
                tv_existing_user.setBackgroundColor(getResources().getColor(R.color.white));
                tv_new_user.setTextColor(getResources().getColor(R.color.white));
                tv_new_user.setBackgroundColor(getResources().getColor(R.color.red));
                Exist_user_selected = false;
               // slide_up_ll.startAnimation(AnimationUtils.loadAnimation(this,R.anim.move_down));





            } else {
                /********** handle Existing User only Login ************/
                //ll_topblock.startAnimation(AnimationUtils.loadAnimation(this,R.anim.move_up));
               // slide_up_ll.startAnimation(AnimationUtils.loadAnimation(this,R.anim.move_up));

                confirm_password_ll.setVisibility(View.GONE);
                tv_new_user.setTextColor(getResources().getColor(R.color.black));
                tv_new_user.setBackgroundColor(getResources().getColor(R.color.white));
                tv_existing_user.setTextColor(getResources().getColor(R.color.white));
                tv_existing_user.setBackgroundColor(getResources().getColor(R.color.red));
                Exist_user_selected=true;
               // slide_up_ll.startAnimation(AnimationUtils.loadAnimation(this,R.anim.move_down));



            }

    }

    /* is used to validate all the fields.
     */
    public boolean validation() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString();
        if (username.isEmpty()) {
            errorLayout.showError(getString(R.string.enter_valid_user));
            return false;
        } else if (password.isEmpty()) {
            errorLayout.showError(getString(R.string.enter_password));
            return false;
        } /*else if (!rb_super_admin.isChecked() && !rb_user.isChecked()) {
            errorLayout.showError("Select user type");
            return false;
        }*/ /*else if (!marshMallowPermission.checkPermissionForPhoneState()) {
            marshMallowPermission.requestPermissionForPhoneState();
            return false;
        }*/ else {
            return true;
        }

    }

    /*is api call used for calling login API
     */
    public void loginApi() {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    boolean firstLogin = false;
                    JSONObject jsonObject = (JSONObject) object;
                    firstLogin = jsonObject.getBoolean("firstLogin");
                    int appVersion = jsonObject.getInt("appPaymentVersion");
                    //firstLogin=true;
                    sessionParam = new SessionParam((JSONObject) object);
                    sessionParam.persist(mContext);
                    //firstLogin=true;
                    //code uncomment after api correction
                    if (sessionParam.role.equals("3")) {
                        sessionParam = new SessionParam(context);
                        sessionParam.saveToken(context, "y");

                        if (appVersion==1){
                            finishAllActivities();
                            startActivity(new Intent(mContext, Act_FreeVersionHome.class));
                        }else if (appVersion==3){
                            finishAllActivities();
                            startActivity(new Intent(mContext, BasicHomeActivity.class));
                        }else {
                            if (!firstLogin) {
                                finishAllActivities();
                                startActivity(new Intent(mContext, Act_Home.class));
                            } else {
                                // Toast.makeText(context, "FirstLogin", Toast.LENGTH_SHORT).show();
                                finishAllActivities();
                                Intent intent = new Intent(mContext, Act_Home.class);
                                intent.putExtra("appTourType", 1);
                                startActivity(intent);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                errorLayout.showError(message);
                et_password.setText("");
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                errorLayout.showError(message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject(
                "email", et_username.getText().toString().trim(),
                "password", et_password.getText().toString(),
                "role", 3 + "",
                "deviceType", 1 + "",
                "deviceId", device_id,
                "fcmToken", sessionParam.fcm_token

        );
        baseRequest.callAPIPost(1, object, ConstantAPI.api_userLogin);
    }
    private void setpasswordApi() {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    loginApi();
                  /* switchTab();
                   et_confirm_password.setText("");
                   et_password.setText("");*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                errorLayout.showError(message);
                if (message.contains("User is already verified.")){
                    et_confirm_password.setText("");
                    et_password.setText("");
                    switchTab();
                }
                et_password.setText("");
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                errorLayout.showError(message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("email", et_username.getText().toString().trim(),
                "password", et_password.getText().toString()

        );
        baseRequest.callAPIPost(1, object, ConstantAPI.api_userSetPassword);
    }

    public void dialog() {
        final Dialog mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.help_login_lyt);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

//      mDialog.setCancelable(false);
        TextView help_web_link = mDialog.findViewById(R.id.help_web_link);
        mDialog.show();
        help_web_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tribe365.co/"));
                startActivity(browserIntent);
                mDialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp= new WindowManager.LayoutParams();
        Window window= mDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }
    public void JavaUser(String firstName, String lastName){
        utility.showToast(mContext,"show User: "+ firstName+" : "+ lastName);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_act_login;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
