package com.chetaru.tribe365_new.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONObject;

/*import com.facebook.AccessToken;
import com.facebook.login.LoginManager;*/


public class SessionParam {
    public String signupStage = "0", loginSession = "n";
    //check kudos click value
    public String todayDate = "";
    public Boolean clickValue = false, firstLogin = false;
    public int loginVersion=1; //1->Free Version,2->basic version,3->paid version
    public int leaveStatus=0; //1-> Absent 2->present
    public String id, isDot, name, email, officeId, departmentId, orgId, office, department, token, organisation_logo, role;
    public String lastName = "";
    public String companyList = "";
    public String companyOrgId = "";
    public String deviceId = "";
    public String fcm_token = "";
    public String orgname = "", profileImage = "";
    public Boolean isOnHomeScreen = false;
    public Boolean isSubscribeNoti=true;
    public Boolean autoStart= false;
    public String logintellsid = ""; //creating this string to check if tellsid login api worked properly or not.
    String PREF_NAME = "MyPref";
    String FCM_NAME = "FcmToken";
    public static String PREF_KEY_APP_AUTO_START;
    Context _context;
    public SessionParam(Context context, String signupStage) {
        this.signupStage = signupStage;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("signupStage", signupStage);
        prefsEditor.commit();
    }

    public static boolean writeBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
        return false;
    }
    //by calling this method from api response we can directly save our data to shared preference
    public SessionParam(JSONObject jsonObject) {
        if (jsonObject != null) {
            //userId = jsonObject.optString("userId", "");
            id = jsonObject.optString("id", "");
            name = jsonObject.optString("name", "");
            lastName = jsonObject.optString("lastName", "");
            firstLogin = jsonObject.optBoolean("firstLogin", false);
            email = jsonObject.optString("email", "");
            officeId = jsonObject.optString("officeId", "");
            departmentId = jsonObject.optString("departmentId", "");
            office = jsonObject.optString("office", "");
            department = jsonObject.optString("department", "");
            token = jsonObject.optString("token", "");
            role = jsonObject.optString("role", "");
            orgId = jsonObject.optString("orgId", "");
            isDot = jsonObject.optString("isDot", "");
            organisation_logo = jsonObject.optString("organisation_logo", "");
            deviceId = jsonObject.optString("deviceId", "");
            orgname = jsonObject.optString("orgname", "");
            profileImage = jsonObject.optString("profileImage", "");
            loginVersion = jsonObject.optInt("appPaymentVersion", 1);

            // firstLogin=true;

        }
    }


    //this method is used to initialize this class and then we will get access to all saved data
    public SessionParam(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesFcm = context.getSharedPreferences(
                FCM_NAME, Context.MODE_PRIVATE);
        this.id = sharedPreferences.getString("id", "");
        this.logintellsid = sharedPreferences.getString("logintellsid", "");
        this.name = sharedPreferences.getString("name", "");
        this.lastName = sharedPreferences.getString("lastName", "");
        this.email = sharedPreferences.getString("email", "");
        this.officeId = sharedPreferences.getString("officeId", "");
        this.departmentId = sharedPreferences.getString("departmentId", "");
        this.office = sharedPreferences.getString("office", "");
        this.department = sharedPreferences.getString("department", "");
        this.token = sharedPreferences.getString("token", "");
        this.role = sharedPreferences.getString("role", "");
        this.orgId = sharedPreferences.getString("orgId", "");
        this.isDot = sharedPreferences.getString("isDot", "");
        this.firstLogin = sharedPreferences.getBoolean("firstLogin", false);
        this.orgname = sharedPreferences.getString("orgname", "");
        this.profileImage = sharedPreferences.getString("profileImage", "");

        this.companyList = sharedPreferences.getString("companyList", "");
        this.companyOrgId = sharedPreferences.getString("companyOrgId", "");
        this.organisation_logo = sharedPreferences.getString("organisation_logo", "");

        this.deviceId = sharedPreferences.getString("deviceId", "");
        this.fcm_token = sharedPreferencesFcm.getString("fcm_token", "");
        this.isOnHomeScreen = sharedPreferencesFcm.getBoolean("isOnHomeScreen", false);

        this.todayDate = sharedPreferences.getString("todayDate", "");
        this.clickValue = sharedPreferences.getBoolean("clickValue", false);
        this.loginVersion=sharedPreferences.getInt("appPaymentVersion",1);
        this.leaveStatus=sharedPreferences.getInt("leaveStatus",1);
        this.isSubscribeNoti=sharedPreferences.getBoolean("isSubscribeNoti",true);
        this.autoStart=sharedPreferences.getBoolean("autoStart",false);

    }

    public void SessionParam() {
    }

    public void setAutoStart(Context context,boolean autoStart){
        SharedPreferences sharedPreferences=context.getSharedPreferences(
                PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean("autoStart",autoStart);
        prefsEditor.commit();
    }

    public void companyList(Context context, String list, String companyOrgId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        companyList = list;
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        if (!list.equals("")) {
            prefsEditor.putString("companyList", companyList);
        }
        prefsEditor.putString("companyOrgId", companyOrgId);
        prefsEditor.commit();
    }
    public void isNotification(Context context,boolean isSubscribe){
        SharedPreferences sharedPreferences=context.getSharedPreferences(
                PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean("isSubscribeNoti",isSubscribe);
        prefsEditor.commit();
    }
    public void happyIndexDialogShow(Context context, String saveDate, boolean clickValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("todayDate", saveDate);
        prefsEditor.putBoolean("clickValue", clickValue);
        prefsEditor.commit();

    }

    //this method is used to save device id
    public void saveDeviceId(Context context, String deviceId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

        prefsEditor.putString("deviceId", deviceId);
        prefsEditor.commit();
    }

    public void saveFreeVersion(Context context,int loginVersion){
        SharedPreferences sharedPreferences=context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt("appPaymentVersion", loginVersion);
        prefsEditor.commit();

    }
    public void getLeaveStatus(Context context,int leaveStatus){
        SharedPreferences sharedPreferences= context.getSharedPreferences(
                PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor= sharedPreferences.edit();
        prefsEditor.putInt("leaveStatus",leaveStatus);
        prefsEditor.commit();
    }

    //as name suggested this method write the data which we want to save in shared Preference
    public void persist(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferenceFcm = context.getSharedPreferences(
                FCM_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        SharedPreferences.Editor prefsEditorFCM = sharedPreferences.edit();
        if (!logintellsid.isEmpty()) {
            prefsEditor.putString("logintellsid", logintellsid);
        }
        if (!token.isEmpty()) {
            prefsEditor.putString("token", token);
        }
        if (!id.isEmpty()) {
            prefsEditor.putString("id", id);
        }
        if (!email.isEmpty()) {
            prefsEditor.putString("email", email);
        }
        if (!name.isEmpty()) {
            prefsEditor.putString("name", name);
        }
        if (!lastName.isEmpty()) {
            prefsEditor.putString("lastName", lastName);
        }
        if (!officeId.isEmpty()) {
            prefsEditor.putString("officeId", officeId);
        }
        if (!departmentId.isEmpty()) {
            prefsEditor.putString("departmentId", departmentId);
        }
        if (!office.isEmpty()) {
            prefsEditor.putString("office", office);
        }
        if (!department.isEmpty()) {
            prefsEditor.putString("department", department);
        }
        if (!organisation_logo.isEmpty()) {
            prefsEditor.putString("organisation_logo", organisation_logo);
        }
        if (!role.isEmpty()) {
            prefsEditor.putString("role", role);
        }
        if (!orgId.isEmpty()) {
            prefsEditor.putString("orgId", orgId);
        }
        if (!fcm_token.isEmpty()) {
            prefsEditorFCM.putString("fcm_token", fcm_token);
        }
        if (!isDot.isEmpty()) {
            prefsEditor.putString("isDot", isDot);
        }
        if (!orgname.isEmpty()) {
            prefsEditor.putString("orgname", orgname);
        }
        if (!profileImage.isEmpty()) {
            prefsEditor.putString("profileImage", profileImage);
        }

        prefsEditor.putBoolean("firstLogin", firstLogin);

        prefsEditor.commit();
    }

    // to check login status we have used this method
    public void saveToken(Context context, String Token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("logintellsid", Token);
        prefsEditor.commit();
    }

    //this method is used to save fcm token
    public void saveFCM_Token(Context context, String fcm_token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                FCM_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("fcm_token", fcm_token);
        prefsEditor.commit();
    }

    public void inOnHomeScreen(Context context, Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                FCM_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean("isOnHomeScreen", status);
        prefsEditor.commit();
    }

    //this method will clear all the saved data in shared preference
    public void clearPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        /*AccessToken.setCurrentAccessToken(null);
        LoginManager.getInstance().logOut();*/
        prefsEditor.clear();
        prefsEditor.commit();
    }

    @Override
    public String toString() {
        return "SessionParam [name=" + "]";
    }
}
