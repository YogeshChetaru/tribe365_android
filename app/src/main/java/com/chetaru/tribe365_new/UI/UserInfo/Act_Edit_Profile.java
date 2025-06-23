package com.chetaru.tribe365_new.UI.UserInfo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.chetaru.tribe365_new.API.Models.ModelUserDetails;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;

public class Act_Edit_Profile extends BaseActivity implements View.OnClickListener{

    Utility utility;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    String imgBase64 = "";
    File sourceFile;
    boolean resultImage=false;

    int personalData = 0;
    int switchMode = 0;
    private BaseRequest baseRequest;
    private int TakePicture = 1, SELECT_FILE = 2, RESULT_OK = -1;
    private Bitmap bitmap = null;
    Boolean perTypeStatus = false;
    String departmentId = "";
    String officeId = "";

    /***************** initialize layout View *************************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365HomeImage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_DP)
    CircularImageView iv_DP_user;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_editDP)
    ImageView iv_editDP;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_user_name_txt)
    TextView tv_user_name_txt;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_designation)
    TextView tv_designation;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;

    /******* edit view related section ********/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_name)
    TextView et_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_last_name)
    TextView et_last_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_office_name)
    TextView tv_office_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_department_name)
    TextView tv_department_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_contact)
    TextView et_contact;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.switch_personal)
    SwitchCompat personal_switch;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_save)
    TextView tv_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_edit_profile);
        ButterKnife.bind(this);
        utility=new Utility();
        sessionParam=new SessionParam(mContext);
        /************ permission for images upload************/
        getPermission();
        /******************** handle Switch Case****************/
        personal_switch.setClickable(true);
        switchCase();

        iv_editDP.setOnClickListener(this);
        tribe365HomeImage.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        /************** get session manager **************/
        getSessionParam();
        /*************** api calling ************/
        getUserDetails();
    }

    /*********** get permission *************/
    private void getPermission() {
        marshMallowPermission = new MarshMallowPermission(this);
        if (!marshMallowPermission.checkPermissionForCamera() && !marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForCamera();
            marshMallowPermission.requestPermissionForExternalStorage();
        } else if (!marshMallowPermission.checkPermissionForCamera()) {
            marshMallowPermission.requestPermissionForCamera();
        } else if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage();
        }

    }


    /******************** handle Switch Case****************/
    private void switchCase() {
        personal_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    personal_switch.isChecked();
                    personalData = 1;
                } else {
                    personalData = 0;
                }
            }
        });
    }


    /*********** getSessionParam **************/
    private void getSessionParam() {
        try {
            Picasso.get().load(sessionParam.organisation_logo).into(tribe365HomeImage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**************** api Calling *****************/
    /******** get user profile Api **********/
    /* this method will be used for getting user details
     */
    public void getUserDetails() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                ModelUserDetails modelUserDetails = new ModelUserDetails();
                Gson gson = new Gson();
                modelUserDetails = gson.fromJson(object.toString(), ModelUserDetails.class);
                et_name.setText(modelUserDetails.getName());
                et_last_name.setText(modelUserDetails.getLastName());
                tv_office_name.setText(modelUserDetails.getOfficeName());
                tv_department_name.setText(modelUserDetails.getDepartmentName());

                et_contact.setText(modelUserDetails.getUserContact());
                officeId = modelUserDetails.getOfficeId() + "";
                departmentId = modelUserDetails.getDepartmentId() + "";
                perTypeStatus = modelUserDetails.getPerTypeStatus();
                personalData = modelUserDetails.getPersonaliseData();
                switchMode = modelUserDetails.getPersonaliseData();


                if (personalData == 1) {
                    personal_switch.setChecked(true);
                } else {
                    personal_switch.setChecked(false);
                }

                //check user profile image
                if (!modelUserDetails.getProfileImage().isEmpty()) {
                    Picasso.get().load(modelUserDetails.getProfileImage()).placeholder(getResources().getDrawable(R.drawable.user_circle)).into(iv_DP_user);
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

        Map<String, String> map = new HashMap<>();
        map.put("", "");
        baseRequest.callAPIGET(1, map, ConstantAPI.userProfile);
    }

    /************  update profile *********/
    /*API call to update profile.*/
    public void api_updateDetails() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                ModelUserDetails modelUserDetails = new ModelUserDetails();
                Gson gson = new Gson();
                modelUserDetails = gson.fromJson(object.toString(), ModelUserDetails.class);
                et_name.setText(modelUserDetails.getName());
                et_last_name.setText(modelUserDetails.getLastName());

                tv_office_name.setText(modelUserDetails.getOfficeName());
                tv_department_name.setText(modelUserDetails.getDepartmentName());

                et_contact.setText(modelUserDetails.getUserContact());
                personalData = modelUserDetails.getPersonaliseData();
                switchMode = modelUserDetails.getPersonaliseData();
                if (personalData == 1) {
                    personal_switch.setChecked(true);
                } else {
                    personal_switch.setChecked(false);
                }

               /* if (!modelUserDetails.getProfileImage().isEmpty()) {
                    Picasso.get().load(modelUserDetails.getProfileImage()).into(iv_DP);
                } */
                //check user profile image
                if (!modelUserDetails.getProfileImage().isEmpty()) {
                    Picasso.get().load(modelUserDetails.getProfileImage()).placeholder(getResources().getDrawable(R.drawable.user_circle)).into(iv_DP_user);
                } else {
                    //iv_DP.setImageResource(R.drawable.user_circle);
                    //iv_DPAdmin.setImageResource(R.drawable.user_circle);
                }

                personal_switch.setClickable(false);
                et_name.setEnabled(false);
                et_last_name.setEnabled(false);
                et_contact.setEnabled(false);
                iv_editDP.setVisibility(View.GONE);
                sessionParam.name = modelUserDetails.getName();
                sessionParam.lastName = modelUserDetails.getLastName();
                sessionParam.profileImage = modelUserDetails.getProfileImage();
                sessionParam.persist(mContext);
                Intent intent=new Intent(mContext,Act_ProfileUser.class);
                startActivity(intent);
                finish();


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
        JsonObject object = Functions.getClient().getJsonMapObject("name", et_name.getText().toString(),
                "lastName", et_last_name.getText().toString(),
                "email", sessionParam.email,
                "contact", et_contact.getText().toString(),
                "profileImage", imgBase64,
                "officeId", officeId,
                "personaliseData", String.valueOf(personalData),
                "departmentId", departmentId
                //JsonObject object = Functions.getClient().getJsonMapObject("orgId", "9"
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.updateUserProfile);
    }


    /*********** handle onCLick Listener *******************/
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.iv_editDP:
                selectImage();
                break;
            case R.id.tv_save:
                if (et_name.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.enter_first_name));
                    return;
                }
                if (et_contact.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.enter_contact_details));
                    return;
                }
                if (et_last_name.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.enter_last_name));
                    return;
                }
                api_updateDetails();
                break;
            case R.id.tv_cancel:
                onBackPressed();
                break;
        }

    }
    /******** selection image *********/
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
                        startActivityForResult(takePictureIntent,TakePicture);
                    }
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

    /************** handle image upload and selection************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resultImage = true;
        //String imageFileName = System.currentTimeMillis() + ".png";
        if (resultCode == RESULT_OK) {
            if (requestCode == TakePicture) {
                try {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    iv_DP_user.setImageBitmap(bitmap);

                    imgBase64 = utility.image_to_Base64(mContext, utility.getPath(utility.getImageUri(mContext, bitmap), mContext));
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this,getString(R.string.camera_msg), Toast.LENGTH_SHORT).show();


                }
            }
            if (requestCode == SELECT_FILE) {
                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    sourceFile = new File(utility.getPath(data.getData(), mContext));
                    bitmap = new Compressor(mContext).compressToBitmap(sourceFile);
                    imgBase64 = utility.image_to_Base64(mContext, utility.getPath(data.getData(), mContext));
                    iv_DP_user.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Image not set please try again", Toast.LENGTH_SHORT).show();
                }


            }
                /*bitmap = utility.decodeSampledBitmapFromResource(data.getExtras().get("data").toString(), 100, 100);
                img_1.setVisibility(View.VISIBLE);
                img_1.setImageBitmap(bitmap);*/
        }
    }


    /***************** handle back *********/
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        mContext.startActivity(new Intent(Act_Edit_Profile.this,Act_ProfileUser.class));
        finish();


    }

    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_edit_profile;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}