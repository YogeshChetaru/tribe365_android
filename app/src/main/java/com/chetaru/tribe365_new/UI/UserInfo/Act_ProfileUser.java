package com.chetaru.tribe365_new.UI.UserInfo;

import static com.chetaru.tribe365_new.R.color.transparent;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.MemberHome.PersonalityType;
import com.chetaru.tribe365_new.API.Models.ModelDepartmentList;
import com.chetaru.tribe365_new.API.Models.ModelUserDetails;
import com.chetaru.tribe365_new.API.Models.Office;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_DepartmentList_profile;
import com.chetaru.tribe365_new.Adapter.Ad_OfficeDialog;
import com.chetaru.tribe365_new.BuildConfig;
import com.chetaru.tribe365_new.CallBack.OnParentItemClick;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.DismissType;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.GuideView;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Listener.GuideListener;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.BasicVersion.BasicHomeActivity;
import com.chetaru.tribe365_new.UI.CustomerSupport.Act_CustomerSupport;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.UI.Home.Act_knowcompany;
import com.chetaru.tribe365_new.UI.Home.Actions.Act_ActionList;
import com.chetaru.tribe365_new.UI.Know.COT.Cot_Individual;
import com.chetaru.tribe365_new.UI.Know.COT.Cot_New_Question;
import com.chetaru.tribe365_new.UI.Know.COT.FunctionalLense.Act_CotDetails;
import com.chetaru.tribe365_new.UI.Know.PersonalityType.Act_Personality_type_list;
import com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation.Act_SOT_Motivation_Individual_graph;
import com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation.Act_SOT_Motivation_Question;
import com.chetaru.tribe365_new.UI.Setting.Act_Setting;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.RecyclerItemClickListener;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

public class Act_ProfileUser extends BaseActivity implements View.OnClickListener{


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_edit_details)
    ImageView iv_edit_details;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_editDP)
    ImageView iv_editDP;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_editDPAdmin)
    ImageView iv_editDPAdmin;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_DP)
    ImageView iv_DP;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_DPAdmin)
    ImageView iv_DPAdmin;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_edit_buttons)
    RelativeLayout rl_edit_buttons;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cv_editDetails)
    CardView cv_editDetails;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_name)
    EditText et_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_last_name)
    EditText et_last_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_contact)
    EditText et_contact;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_changepassword)
    TextView tv_changepassword;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_logout)
    TextView tv_logout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_save)
    TextView tv_save;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_office_name)
    TextView tv_office_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_department_name)
    TextView tv_department_name;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_data_rl)
    RelativeLayout edit_Data_RelativeLayout;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_admin_imageBlock)
    RelativeLayout rv_admin_imageBlock;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @BindView(R.id.setting_image)
    ImageView setting_image;

    /****************************************** new Layout Add *****************************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_user_name_txt)
    TextView tv_user_name_txt;
    @BindView(R.id.tv_user_email)
    TextView tv_user_email;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_designation)
    TextView tv_designation;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.personality_type_tv_msg)
    TextView personality_type_tv_msg;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.team_role_tv_msg)
    TextView team_role_tv_msg;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.motivation_tv_msg)
    TextView motivation_tv_msg;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_personality_type)
    RecyclerView   rv_personality_type;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_team_role)
    RecyclerView rv_team_role_type;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_motivation)
    RecyclerView rv_motivational_type;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_profile_ll)
    LinearLayout edit_profile_ll;
    @BindView(R.id.action_click)
    LinearLayout action_click;
    @BindView(R.id.detail_state_profile_ll)
    LinearLayout detail_state_profile_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.support_profile_ll)
    LinearLayout support_profile_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.app_tour_profile_ll)
    LinearLayout app_tour_profile_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.leave_profile_ll)
    LinearLayout leave_profile_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_view_app_tour)
    View bottom_view_app_tour;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.change_password_ll)
     LinearLayout change_password_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.logout_profile_ll)
     LinearLayout logout_profile_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.org_name_detail_tv)
     TextView org_name_detail_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.version_tv)
    TextView version_tv;


    /*************************************** close new layout creation *******************/

    Utility utility;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    String imgBase64 = "";
    File sourceFile;
    Boolean editable = false;
    Boolean perTypeStatus = false;
    Boolean motivatedTypeStatus = false;
    Boolean teamRoleTypeStatus = false;
    String departmentId = "";
    String officeId = "";
    String teamRole = "";
    String personalityType = "";
    String motivation = "";
    Boolean resultImage = false;
    ArrayList<ModelDepartmentList> modelDepartmentLists;
    ArrayList<Office> officeList;
    SwitchCompat personal_switch;
    int personalData = 0;
    int switchMode = 0;
    int currentLeaveStatus=0;
    private BaseRequest baseRequest;
    private int TakePicture = 1, SELECT_FILE = 2, RESULT_OK = -1;
    private Bitmap bitmap = null;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    List<String> personalityList=new ArrayList<>();
    List<String> teamRoleList=new ArrayList<>();
    List<String> motivationalList=new ArrayList<>();

    String newVersion="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_profile_user);
        init();

    }

    /*used to initialise all the views*/
    public void init() {
        ButterKnife.bind(this);
        utility = new Utility();
        modelDepartmentLists = new ArrayList<>();
        officeList = new ArrayList<>();
        sessionParam = new SessionParam(mContext);
        currentLeaveStatus= sessionParam.leaveStatus;
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
            org_name_detail_tv.setText(sessionParam.orgname+ " Studies");
        }
        if (sessionParam.loginVersion==3){
            app_tour_profile_ll.setVisibility(View.VISIBLE);
            bottom_view_app_tour.setVisibility(View.VISIBLE);
        }else {
            app_tour_profile_ll.setVisibility(View.VISIBLE);
            bottom_view_app_tour.setVisibility(View.VISIBLE);
        }
        newVersion=BuildConfig.VERSION_NAME;
        version_tv.setText("Version "+ newVersion);
        Log.d("versionName", newVersion);
       /* marshMallowPermission = new MarshMallowPermission(this);
        if (!marshMallowPermission.checkPermissionForCamera() && !marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForCamera();
            marshMallowPermission.requestPermissionForExternalStorage();
        } else if (!marshMallowPermission.checkPermissionForCamera()) {
            marshMallowPermission.requestPermissionForCamera();
        } else if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage();
        }*/

        personal_switch = findViewById(R.id.switch_personal);
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
        personal_switch.setClickable(false);
        edit_Data_RelativeLayout.setClickable(true);
        edit_profile_ll.setOnClickListener(this);
        detail_state_profile_ll.setOnClickListener(this);
        support_profile_ll.setOnClickListener(this);
        leave_profile_ll.setOnClickListener(this);
        change_password_ll.setOnClickListener(this);
        logout_profile_ll.setOnClickListener(this);
        personality_type_tv_msg.setOnClickListener(this);
        team_role_tv_msg.setOnClickListener(this);
        motivation_tv_msg.setOnClickListener(this);
        action_click.setOnClickListener(this);
        setting_image.setOnClickListener(this);
        /************************ get all type RecyclerView **************/
        getPersonalityValue();
        getTeamRoleValue();
        getMotivationValue();
        /**************************** Api Call ****************/
        getUserDetails();
        api_getOfficeAndDepartment();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.tribe365, R.id.iv_edit_details,
            R.id.tv_department_name, R.id.tv_office_name,  R.id.tv_cancel,R.id.edit_profile_ll,R.id.change_password_ll,
            R.id.personality_type_tv_msg,R.id.team_role_tv_msg,R.id.motivation_tv_msg,R.id.detail_state_profile_ll,R.id.action_click,
            R.id.logout_profile_ll,R.id.support_profile_ll,R.id.leave_profile_ll,R.id.app_tour_profile_ll,R.id.tv_save, R.id.iv_editDPAdmin, R.id.edit_data_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_data_rl:
                // ShowIntro("SetTheme", getString(R.string.edit_profile_ppt), R.id.iv_edit_details, 1);
                break;
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.motivation_tv_msg:
                startActivity(new Intent(mContext, Act_SOT_Motivation_Question.class));
                break;

            case R.id.personality_type_tv_msg:
                startActivity(new Intent(mContext, Act_Personality_type_list.class));
                break;
            case R.id.team_role_tv_msg:
                startActivity(new Intent(mContext, Cot_New_Question.class));
                break;
            case R.id.leave_profile_ll:
                if (currentLeaveStatus != 1) {
                    dialogAbsent();
                }else {
                    utility.showToast(mContext,"You are already on leave");
                }
                break;

            case R.id.tv_office_name:
                if (editable) {
                    dialogOffice_n_Dialog("OFFICE");
                }
                break;
            case R.id.tv_department_name:
                if (editable) {
                    dialogOffice_n_Dialog("DEPARTMENT");
                }

                break;
            case R.id.iv_edit_details:
                editable = true;
                personal_switch.setClickable(true);
                edit_Data_RelativeLayout.setClickable(false);
                if (!marshMallowPermission.checkPermissionForCamera() && !marshMallowPermission.checkPermissionForExternalStorage()) {
                    marshMallowPermission.requestPermissionForCamera();
                    marshMallowPermission.requestPermissionForExternalStorage();
                } else if (!marshMallowPermission.checkPermissionForCamera()) {
                    marshMallowPermission.requestPermissionForCamera();
                } else if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                    marshMallowPermission.requestPermissionForExternalStorage();
                }
            {
                et_name.setEnabled(true);
                et_last_name.setEnabled(true);
                et_contact.setEnabled(true);
                //rl_edit_buttons.setVisibility(View.VISIBLE);
                rl_edit_buttons.setVisibility(View.GONE);

                tv_changepassword.setVisibility(View.GONE);
                tv_logout.setVisibility(View.GONE);
                iv_edit_details.setVisibility(View.GONE);
//                    if (sessionParam.role.equalsIgnoreCase("1")) {
//                        et_name.setEnabled(true);
//                        et_contact.setEnabled(true);
//                        rl_edit_buttons.setVisibility(View.VISIBLE);
//                        iv_editDPAdmin.setVisibility(View.VISIBLE);
//                        tv_changepassword.setVisibility(View.GONE);
//                        tv_logout.setVisibility(View.GONE);
//                        iv_edit_details.setVisibility(View.GONE);
//                    }
            }
            break;

            case R.id.iv_editDPAdmin:
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
            case R.id.tv_cancel:
                edit_Data_RelativeLayout.setClickable(true);
                et_name.setEnabled(false);
                et_last_name.setEnabled(false);
                personal_switch.setClickable(false);
                if (switchMode == 1) {
                    personal_switch.setChecked(true);
                } else {
                    personal_switch.setChecked(false);
                }
                editable = false;
                et_contact.setEnabled(false);
                rl_edit_buttons.setVisibility(View.GONE);

                tv_changepassword.setVisibility(View.VISIBLE);
                tv_logout.setVisibility(View.VISIBLE);
                iv_edit_details.setVisibility(View.VISIBLE);

                if (sessionParam.role.equalsIgnoreCase("1")) {
                    iv_editDPAdmin.setVisibility(View.GONE);
                }
                getUserDetails();
                break;
            case R.id.tv_save:
                edit_Data_RelativeLayout.setClickable(true);
                if (et_name.getText().toString().isEmpty()) {
                    utility.showToast(mContext, "Please enter firstName");
                    return;
                }
                if (et_contact.getText().toString().isEmpty()) {
                    utility.showToast(mContext, "Please enter contact details.");
                    return;
                }
                if (et_last_name.getText().toString().isEmpty()) {
                    utility.showToast(mContext, "Please enter lastName");
                    return;
                }
                api_updateDetails();
                break;
            case R.id.iv_editDP:
                selectImage();
                break;
            case R.id.edit_profile_ll:
                Intent intent=new Intent(mContext,Act_Edit_Profile.class);
                startActivity(intent);
                break;
            case R.id.action_click:
                startActivity(new Intent(mContext, Act_ActionList.class));
                break;
            case R.id.detail_state_profile_ll:
                Intent intent4=new Intent(mContext, Act_knowcompany.class);
                startActivity(intent4);
                break;
            case R.id.support_profile_ll:
                Intent intent1=new Intent(mContext, Act_CustomerSupport.class);
                startActivity(intent1);
                break;
            case R.id.app_tour_profile_ll:
                Intent intent2= null;
                if (sessionParam.loginVersion== 3){
                    intent2 = new Intent(mContext, BasicHomeActivity.class);
                    intent2.putExtra("appTourType", 1);
                    startActivity(intent2);
                    finish();
                }else {
                     intent2 = new Intent(mContext, Act_Home.class);
                    intent2.putExtra("appTourType", 1);
                    startActivity(intent2);
                    finish();
                }
                break;
            case R.id.change_password_ll:
                dialogChangePassword();
                break;
            case R.id.logout_profile_ll:
                logoutDialog();
                break;
            case R.id.setting_image:
                Intent intent3= new Intent(mContext, Act_Setting.class);
                startActivity(intent3);
                break;


        }
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
        resultImage = true;
        String imageFileName = System.currentTimeMillis() + ".png";
        if (resultCode == RESULT_OK) {
            if (requestCode == TakePicture) {
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                iv_DP.setImageBitmap(bitmap);
                if (sessionParam.role.equalsIgnoreCase("1")) {
                    iv_DPAdmin.setImageBitmap(bitmap);
                }
                imgBase64 = utility.image_to_Base64(mContext, utility.getPath(utility.getImageUri(mContext, bitmap), mContext));
            }
            if (requestCode == SELECT_FILE) {
                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    sourceFile = new File(utility.getPath(data.getData(), mContext));
                    bitmap = new Compressor(mContext).compressToBitmap(sourceFile);
                    imgBase64 = utility.image_to_Base64(mContext, utility.getPath(data.getData(), mContext));
                    iv_DP.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Image not set please try again", Toast.LENGTH_SHORT).show();
                }

                if (sessionParam.role.equalsIgnoreCase("1")) {
                    iv_DPAdmin.setImageBitmap(bitmap);
                }
            }
                /*bitmap = utility.decodeSampledBitmapFromResource(data.getExtras().get("data").toString(), 100, 100);
                img_1.setVisibility(View.VISIBLE);
                img_1.setImageBitmap(bitmap);*/
        }
    }

    /* this method will be used for getting user details
     */
    public void getUserDetails() {
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
                officeId = modelUserDetails.getOfficeId() + "";
                departmentId = modelUserDetails.getDepartmentId() + "";
                perTypeStatus = modelUserDetails.getPerTypeStatus();
                personalData = modelUserDetails.getPersonaliseData();
                switchMode = modelUserDetails.getPersonaliseData();
                tv_user_name_txt.setText(modelUserDetails.getName() + " "+ modelUserDetails.getLastName());
                tv_user_email.setText(modelUserDetails.getEmail());
                tv_designation.setText(modelUserDetails.getDepartmentName() +", "+modelUserDetails.getOfficeName());
               // tv_company_name.setText(modelUserDetails.getOrganisationName());
                if (personalData == 1) {
                    personal_switch.setChecked(true);
                } else {
                    personal_switch.setChecked(false);
                }

                org_name_detail_tv.setText(sessionParam.orgname +" Studies");

                if (modelUserDetails.getCotTeamRoleMapArr().size()>0) {

                        /*teamRole =
                                modelUserDetails.getmCotTeamRoleMap().split(",")[0].trim() + "\n"
                                        + modelUserDetails.getmCotTeamRoleMap().split(",")[1].trim() + "\n"
                                        + modelUserDetails.getmCotTeamRoleMap().split(",")[2].trim();

                        *//************** save String in a ArrayList ************//*
                        if (teamRoleList.size() > 0) {
                            teamRoleList.clear();
                        }
                        String[] arrayString = modelUserDetails.getmCotTeamRoleMap().split(",");
                        teamRoleList.addAll(Arrays.asList(arrayString));*/
                        setTeamRoleValue(modelUserDetails.getCotTeamRoleMapArr());
                        teamRoleTypeStatus = true;
                        team_role_tv_msg.setVisibility(View.GONE);
                        rv_team_role_type.setVisibility(View.VISIBLE);

                }else{
                    team_role_tv_msg.setVisibility(View.VISIBLE);
                    rv_team_role_type.setVisibility(View.GONE);
                    team_role_tv_msg.setText(modelUserDetails.getCotTeamRoleMap().toString().trim());
                }

                /*personalityType = modelUserDetails.getmSotDetail().split(",")[0].trim() + "\n"
                        + modelUserDetails.getmSotDetail().split(",")[1].trim() + "\n"
                        + modelUserDetails.getmSotDetail().split(",")[2].trim() + "\n"
                        + modelUserDetails.getmSotDetail().split(",")[3].trim();*/

                   /* personalityType = modelUserDetails.getMpersonalityTypeDetails().split(",")[0].trim() + "\n"
                            + modelUserDetails.getMpersonalityTypeDetails().split(",")[1].trim() + "\n"
                            + modelUserDetails.getMpersonalityTypeDetails().split(",")[2].trim() + "\n"
                            + modelUserDetails.getMpersonalityTypeDetails().split(",")[3].trim();
*/

                    /************** save String in a ArrayList ************/
                    if (perTypeStatus){
                       /* if (modelUserDetails.getPersonalityArr().contains(",")) {
                            personalityList = new ArrayList<>();
                            String[] arrayString = modelUserDetails.getPersonalityTypeDetails().split(",");
                            personalityList.addAll(Arrays.asList(arrayString));

                        }*/
                        setPersonalityValue(modelUserDetails.getPersonalityArr());
                        personality_type_tv_msg.setVisibility(View.GONE);
                        rv_personality_type.setVisibility(View.VISIBLE);
                    }else{
                        personality_type_tv_msg.setVisibility(View.VISIBLE);
                        rv_personality_type.setVisibility(View.GONE);
                        personality_type_tv_msg.setText(modelUserDetails.getPersonalityTypeDetails().toString().trim());
                    }



                if (modelUserDetails.getSotMotivationDetailArr().size()>0) {

                        /*motivation = modelUserDetails.getmSotMotivationDetail().split(",")[0].trim() + "\n"
                                + modelUserDetails.getmSotMotivationDetail().split(",")[1].trim() + "\n"
                                + modelUserDetails.getmSotMotivationDetail().split(",")[2].trim();
                        motivatedTypeStatus = true;
                        *//************** save String in a ArrayList ************//*
                        motivationalList.clear();
                        String[] arrayString = modelUserDetails.getmSotMotivationDetail().split(",");
                        motivationalList.addAll(Arrays.asList(arrayString));*/
                    motivatedTypeStatus=true;
                        setMotivationalValue(modelUserDetails.getSotMotivationDetailArr());
                    motivation_tv_msg.setVisibility(View.GONE);
                    rv_motivational_type.setVisibility(View.VISIBLE);

                }else{
                    motivation_tv_msg.setVisibility(View.VISIBLE);
                    rv_motivational_type.setVisibility(View.GONE);
                    motivation_tv_msg.setText(modelUserDetails.getSotMotivationDetail().toString().trim());
                }




                //check user profile image
                if (!modelUserDetails.getProfileImage().isEmpty()) {
                    Picasso.get().load(modelUserDetails.getProfileImage()).placeholder(getResources().getDrawable(R.drawable.user_circle)).into(iv_DP);
                    Picasso.get().load(modelUserDetails.getProfileImage()).placeholder(getResources().getDrawable(R.drawable.user_circle)).into(iv_DPAdmin);
                } else {
                   iv_DP.setImageResource(R.drawable.user_circle);
                    //iv_DPAdmin.setImageResource(R.drawable.user_circle);
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

    public void logoutDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.setCanceledOnTouchOutside(true);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
        TextView tv_logout = dialog.findViewById(R.id.tv_logout);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                api_logout();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (!resultImage) {
            getUserDetails();
        }*/
        getUserDetails();
    }

    public void api_logout() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                sessionParam.clearPreferences(mContext);
                startActivity(new Intent(mContext, ActLogin.class));
                finishAllActivities();

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
        JsonObject object = Functions.getClient().getJsonMapObject("", ""
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.userLogout);
    }

    /* API call to get an office and department list according to the user.
     */
    public void api_getOfficeAndDepartment() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                //sessionParam.clearPreferences(mContext);
                JSONObject jsonObject = (JSONObject) object;
                officeList = baseRequest.getDataList(jsonObject.optJSONArray("offices"), Office.class);
                modelDepartmentLists = baseRequest.getDataList(jsonObject.optJSONArray("department"), ModelDepartmentList.class);
                //Toast.makeText(mContext, "check", Toast.LENGTH_SHORT).show();
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
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId

        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getAllOfficenDepartments);
    }

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
                    Picasso.get().load(modelUserDetails.getProfileImage()).placeholder(getResources().getDrawable(R.drawable.user_circle)).into(iv_DP);
                    Picasso.get().load(modelUserDetails.getProfileImage()).placeholder(getResources().getDrawable(R.drawable.user_circle)).into(iv_DPAdmin);
                } else {
                    //iv_DP.setImageResource(R.drawable.user_circle);
                    //iv_DPAdmin.setImageResource(R.drawable.user_circle);
                }

                personal_switch.setClickable(false);
                et_name.setEnabled(false);
                et_last_name.setEnabled(false);
                et_contact.setEnabled(false);
                rl_edit_buttons.setVisibility(View.GONE);

                iv_editDPAdmin.setVisibility(View.GONE);
                iv_edit_details.setVisibility(View.VISIBLE);
                editable = false;

                sessionParam.name = modelUserDetails.getName();
                sessionParam.lastName = modelUserDetails.getLastName();
                sessionParam.profileImage = modelUserDetails.getProfileImage();
                sessionParam.persist(mContext);

                org_name_detail_tv.setText(sessionParam.orgname +" Studies");


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

    private void callAbsentApi(String startDate, String endDate) {
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    String leaveStatus = jsonObject.getString("leaveStatus");
                    int status=0;
                    status= Integer.parseInt(leaveStatus.trim());
                    sessionParam.getLeaveStatus(mContext,status);
                    sessionParam.persist(mContext);
                    currentLeaveStatus=status;
                    JSONObject jsonData = new JSONObject(Json);
                    String msg = jsonData.getString("message");
                    utility.showToast(mContext,msg);
                    //card_happy_index_ll.setVisibility(View.GONE);
                    //absent_card_view.setVisibility(View.VISIBLE);
                    callHomeAct(mContext);


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
        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id
                , "startDate",startDate, "endDate", endDate);
        baseRequest.callAPIPost(1, object, ConstantAPI.api_userApplyLeave);
    }

    /*popup to change password
     */
    public void dialogChangePassword() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_password);
        //DialogChangePasswordBinding binding=DialogChangePasswordBinding.inflate(LayoutInflater.from(mContext));
        //dialog.setContentView(binding.getRoot());
        dialog.setCanceledOnTouchOutside(true);
        final EditText etCurrentPass = dialog.findViewById(R.id.et_current_pass);
        final EditText etNewPassword = dialog.findViewById(R.id.et_new_password);
        final EditText etConfirm = dialog.findViewById(R.id.et_confirm);

        final TextView btClose = dialog.findViewById(R.id.bt_close);
        final Button btDone = dialog.findViewById(R.id.bt_done);

        etNewPassword.setFilters(new InputFilter[]{
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
                        return "";
                    }
                }
        });
        etConfirm.setFilters(new InputFilter[]{
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
                        return "";
                    }
                }
        });
        etCurrentPass.setFilters(new InputFilter[]{
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
                        return "";
                    }
                }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCurrentPass.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.enter_your_current_password));
                    return;
                }
                if (etNewPassword.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_enter_password));
                    return;
                }
                if (etConfirm.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.enter_confirm_password));
                    return;
                }
                if (!etNewPassword.getText().toString().equals(etConfirm.getText().toString())) {
                    utility.showToast(mContext, getString(R.string.password_and_confirm_pass_validation));
                    return;
                } else {
                    baseRequest = new BaseRequest(mContext);
                    baseRequest.setBaseRequestListner(new RequestReciever() {
                        @Override
                        public void onSuccess(int requestCode, String Json, Object object) {
                            try {
                                JSONObject jsonObject = new JSONObject(Json);
                                utility.showToast(mContext, jsonObject.getString("message"));
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
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
                    JsonObject object = Functions.getClient().getJsonMapObject(
                            "currentPassword", etCurrentPass.getText().toString(),
                            "newPassword", etNewPassword.getText().toString()
                            //JsonObject object = Functions.getClient().getJsonMapObject("orgId", "9"
                    );
                    baseRequest.callAPIPost(1, object, ConstantAPI.updatePasswordWithCurrentPassword);
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    private  void dialogAbsent(){
        Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_absent);
        dialog.setCanceledOnTouchOutside(true);
        TextView starDateET= dialog.findViewById(R.id.start_date_et);
        TextView endDateET= dialog.findViewById(R.id.end_date_et);
        TextView submitTV= dialog.findViewById(R.id.tv_submit);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        starDateET.setText(formattedDate);

        /*starDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starDateET.setText(start);
                showDateCalendar(starDateET);
            }
        });*/
        endDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDate(endDateET);
            }
        });
        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!endDateET.getText().toString().trim().equals(null) ){
                    String startDate=starDateET.getText().toString().trim();
                    String endDate= endDateET.getText().toString().trim();
                    String start = utility.changeDateDMYtoYMD(startDate);
                    String end = utility.changeDateDMYtoYMD(endDate);

                    callAbsentApi(start,end);

                    dialog.dismiss();
                }
            }
        });


        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        Window window=dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(transparent);
        window.setGravity(Gravity.CENTER);
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }


    public void showEndDate(TextView endDate){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                String dateString = format.format(calendar.getTime());
                endDate.setText(dateString);
            }
        },mYear,mMonth,mDay);
        long now = c.getTimeInMillis();
        datePickerDialog.getDatePicker().setMinDate(now);
        datePickerDialog.show();
    }

    /*method is using to show a list of office and department.*/
    public void dialogOffice_n_Dialog(final String title) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tier_list);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final ImageView iv_close = dialog.findViewById(R.id.iv_close);
        final RecyclerView rv_list = dialog.findViewById(R.id.rv_list);
        final TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText(title);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        if (title.contains("OFFICE")) {
            rv_list.setAdapter(new Ad_OfficeDialog(officeList, mContext));
        } else if (title.contains("DEPARTMENT")) {
            rv_list.setAdapter(new Ad_DepartmentList_profile(modelDepartmentLists, mContext));
        } else {
            // rv_list.setAdapter(new Ad_UserListDialog(userList, mContext));
        }

        rv_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (sessionParam.role.equalsIgnoreCase("1")) {
                    departmentId = "";
                    officeId = "";
                }

                if (title.contains("OFFICE")) {
                    officeId = officeList.get(position).getmOfficeIdForUserProfile() + "";
                    tv_office_name.setText(officeList.get(position).getOffice());
                } else if (title.contains("DEPARTMENT")) {
                    tv_department_name.setText(modelDepartmentLists.get(position).getDepartment());
                    departmentId = modelDepartmentLists.get(position).getId() + "";
                } else {
                    //   tv_responsible_value.setText(userList.get(position).getName());
                    // individualUserId = userList.get(position).getId()+"";
                }
                dialog.dismiss();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    private void ShowIntro(String title, String text, int viewId, final int type) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        builder = new GuideView.Builder(this)
                //.setTitle(title)
                .setContentText(text)
                .setTargetView(findViewById(viewId))
                // .setContentSpan((Spannable) Html.fromHtml("<font color='white' ,background color='red'>text</p>"))
                .setContentTextSize(18)//optional
                .setTitleTextSize(14)//optional
                .setDismissType(DismissType.skipe) //optional - default dismissible by TargetView
                .setTitleGravity(Gravity.LEFT)
                .setContentGravity(Gravity.LEFT)
                .setButtonGravity(Gravity.LEFT)
                // .setButtonBackground(ContextCompat.getDrawable(this, R.drawable.rounded))
                //.setButtonTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                // .setPaddingTitle(50,10,40,10)
                //.setPaddingMessage(50,10,40,10)
                .setPaddingButton(10, 0, 10, 0)
                .setButtonText("Skip Tutorial")
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {

                    }

                });


        if (type >= 1) {
            builder.setButtonText("End").build();
            builder.setNextButtonText("").build();
        }
        mGuideView = builder.build();
        mGuideView.show();

        updatingForDynamicLocationViews();

        mGuideView.mMessageView.skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGuideView.dismiss();

            }
        });
        mGuideView.mMessageView.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    builder.setButtonText("End").build();
                    builder.setNextButtonText("").build();
                    mGuideView.dismiss();
                }
                /* } else if (type == 3) {
                ShowIntro("Tribe365", getString(R.string.belief_ppt), R.id.rv_list_values, 4);
            } else if (type == 4) {
                ShowIntro("Tribe365", getString(R.string.comp_logo_ppt), R.id.iv_top_companylogo, 5);

            }*/
            }

        });


    }

    private void updatingForDynamicLocationViews() {
        iv_edit_details.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mGuideView.updateGuideViewLocation();
            }
        });
    }
    /************************* handle new layout View *******************************/
    /*************************get personality type *******************************/
    public void getPersonalityValue(){
        personalityList=new ArrayList<>();
        LinearLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        rv_personality_type.setLayoutManager(layoutManager);
        rv_personality_type.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        rv_personality_type.addItemDecoration(itemDecoration);
        rv_personality_type.setNestedScrollingEnabled(false);

    }
    /****************** set personality type ********************/
    public void setPersonalityValue(List<PersonalityType> personalityList){
        personalityValueAdapter  personalityValueAdapter= new personalityValueAdapter(personalityList, new OnParentItemClick() {
            @Override
            public void onClick(int id, int position, Object object) {
                if (perTypeStatus) {
                    startActivity(new Intent(mContext, Act_CotDetails.class));
                } else {
                    startActivity(new Intent(mContext, Act_Personality_type_list.class));
                }
            }
        });
        rv_personality_type.setAdapter(personalityValueAdapter);
    }
    /******************** get TeamRole *********************/
    public void getTeamRoleValue(){
        teamRoleList=new ArrayList<>();
        LinearLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        rv_team_role_type.setLayoutManager(layoutManager);
        rv_team_role_type.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        rv_team_role_type.addItemDecoration(itemDecoration);
        rv_team_role_type.setNestedScrollingEnabled(false);

    }
    /******************************* set TeamRole **************/
    public void setTeamRoleValue(List<String> teamRoleList){
        TeamRoleSubAdapter  team_role_value_adapter= new TeamRoleSubAdapter(teamRoleList, new OnParentItemClick() {
            @Override
            public void onClick(int id, int position, Object object) {
                if (teamRoleTypeStatus) {
                    startActivity(new Intent(mContext, Cot_Individual.class));
                } else {
                    startActivity(new Intent(mContext, Cot_New_Question.class));
                }

            }
        });
        rv_team_role_type.setAdapter(team_role_value_adapter);
    }
    /******************* get Motivation Recycler *******************/
    public void getMotivationValue(){
        motivationalList=new ArrayList<>();
        LinearLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        rv_motivational_type.setLayoutManager(layoutManager);
        rv_motivational_type.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        rv_motivational_type.addItemDecoration(itemDecoration);
        rv_motivational_type.setNestedScrollingEnabled(false);
    }
    /*********************************** set Motivation Recycler **************/
    public void setMotivationalValue(List<String> motivationalList){
        MotivationAdapter  motivation_value_ad= new MotivationAdapter(motivationalList, new OnParentItemClick() {
            @Override
            public void onClick(int id, int position, Object object) {
                if (motivatedTypeStatus) {
                    startActivity(new Intent(mContext, Act_SOT_Motivation_Individual_graph.class));
                } else {
                    startActivity(new Intent(mContext, Act_SOT_Motivation_Question.class));
                }
            }
        });
        rv_motivational_type.setAdapter(motivation_value_ad);
    }

    /************************************* add Custom Adapter for Personality type ***********************/
    public static class personalityValueAdapter extends RecyclerView.Adapter<personalityValueAdapter.ViewHolder>{

        List<PersonalityType> listName =new ArrayList<>();
        OnParentItemClick onClickListener;

        public personalityValueAdapter(List<PersonalityType> perValue, OnParentItemClick onParentItemClick) {
            this.listName = perValue;
            this.onClickListener=onParentItemClick;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pers_row_options_list,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

           /* if(listName.get(position).contains("(")){
                String abc=listName.get(position).replace("(","\n");
                String newValue=abc.replace(")","");
                holder.row_name_tv.setText(newValue);
            }else {
                holder.row_name_tv.setText(listName.get(position));
            }*/
            holder.row_name_tv.setText(listName.get(position).getCateName()+"\n "
            + listName.get(position).getScore());
            holder.main_card.setOnClickListener(v->{
                onClickListener.onClick(R.id.row_name_tv,position,listName.get(position));
            });
        }

        @Override
        public int getItemCount() {
            try {
                return listName.size();
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView row_name_tv;
            CardView main_card;
            public ViewHolder(@NonNull View v) {
                super(v);
                main_card=v.findViewById(R.id.card_main_ll);
                row_name_tv=v.findViewById(R.id.row_name_tv);
            }
        }
    }
    /************************************* add Custom Adapter for Team Role type ***********************/
    public static class TeamRoleSubAdapter extends RecyclerView.Adapter<TeamRoleSubAdapter.ViewHolder>{

        List<String> listName =new ArrayList<>();
        OnParentItemClick onClickListener;

        public TeamRoleSubAdapter(List<String> perValue, OnParentItemClick onParentItemClick) {
            this.listName = perValue;
            this.onClickListener=onParentItemClick;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_options_list,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.row_name_tv.setText(listName.get(position));
            holder.main_card.setOnClickListener(v ->{
                onClickListener.onClick(R.id.row_name_tv,position,listName.get(position));
            });
        }

        @Override
        public int getItemCount() {
            try {
                return listName.size();
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView row_name_tv;
            CardView main_card;
            public ViewHolder(@NonNull View v) {
                super(v);
                main_card=v.findViewById(R.id.card_main_ll);
                row_name_tv=v.findViewById(R.id.row_name_tv);
            }
        }
    }
    /************************************* add Custom Adapter for Personality type ***********************/
    public static class MotivationAdapter extends RecyclerView.Adapter<MotivationAdapter.ViewHolder>{

        List<String> listName =new ArrayList<>();
        OnParentItemClick onclickListener;

        public MotivationAdapter(List<String> perValue, OnParentItemClick onParentItemClick) {
            this.listName = perValue;
            this.onclickListener=onParentItemClick;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_options_list,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.row_name_tv.setText(listName.get(position));
            holder.main_card.setOnClickListener(v->{
                onclickListener.onClick(R.id.row_name_tv,position,listName.get(position));
            });
        }

        @Override
        public int getItemCount() {
            try {
                return listName.size();
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView row_name_tv;
            CardView main_card;
            public ViewHolder(@NonNull View v) {
                super(v);
                main_card=v.findViewById(R.id.card_main_ll);
                row_name_tv=v.findViewById(R.id.row_name_tv);
            }
        }
    }

    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_profile_user;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}
