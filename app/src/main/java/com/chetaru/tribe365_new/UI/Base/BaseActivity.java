package com.chetaru.tribe365_new.UI.Base;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Config;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.app.NotificationManagerCompat;

import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.MyAppContext;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Notification.NotificationLostCallback;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.BasicVersion.BasicHomeActivity;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.UI.Know.Act_Know_Home;
import com.chetaru.tribe365_new.UI.Offloading.Act_IOTHome;
import com.chetaru.tribe365_new.UI.Risk.Act_RiskHome;
import com.chetaru.tribe365_new.UI.UserInfo.ActLogin;
import com.chetaru.tribe365_new.UI.UserInfo.Act_ProfileUser;
import com.chetaru.tribe365_new.utility.Res;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Base activity class. This may be useful in<br/>
 * Implementing google analytics or <br/>
 * Any app wise implementation
 */


public abstract class BaseActivity extends AppCompatActivity implements NotificationLostCallback,
        BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    TextView tv_notiCount;
    int countValue=0;

    public static final String ACTION_REPLY_TAPPED =
            "com.chetaru.tribe365_new.ACTION_REPLY_TAPPED";
    public static boolean lastSession = false;
    private static final List<Activity> sActivities = new ArrayList<Activity>();
    private static final int notificationId = 101;
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    public Activity currentAct;
    public Context mContext;
    SessionParam sessionParam;
    Utility utility;
    boolean firstCheck = true;
    String startInteractionDate = "";
    String lastInteractionDate = "";
    Dialog dialog = null;
    String channelId = "";
    String   userId="";
    NotificationManager notificationManager;
    private FirebaseAnalytics mFirebaseAnalytics;
    private BaseRequest baseRequest;
    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getStringExtra("action");
            //  String actionReply = intent.getStringExtra("actionReply");
            Log.d("notiDialog", "handel noti");
            if (sessionParam.role.equals("3")) {
                try {
                    if (action != null && action.equals("noti")) {
                        if (!sessionParam.clickValue) {
                            String kudosDate = utility.getTodayDate();
                            if (!sessionParam.todayDate.equals(kudosDate)) {
                                Log.d("notiDialog", "condition true open");
                              //  dialogBaseActivity();
                            }
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Res res;


    public BaseActivity() {
    }

    public static void finishAllActivitiesStatic() {
        if (sActivities != null) {
            for (Activity activity : sActivities) {
                if (Config.DEBUG) {
                    Log.d("BaseActivity", "finishAllActivities activity=" + activity.getClass()
                            .getSimpleName());
                }
                activity.finish();
            }
            //lastSession=ture;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            getFullViewScreen();
        }catch (Exception e){
            e.printStackTrace();
        }
       /* supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);*/
        if (getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
        setContentView(getLayoutId());
        mContext = this;
        currentAct = this;
        sessionParam = new SessionParam(mContext);
        userId=sessionParam.id;
        sActivities.add(this);
        utility = new Utility();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        try {
            navigationView = findViewById(R.id.nav_view);
            navigationView.setOnNavigationItemSelectedListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (sessionParam.isSubscribeNoti) {
            FirebaseMessaging.getInstance().subscribeToTopic("Tribe");
            FirebaseMessaging.getInstance().setDeliveryMetricsExportToBigQuery(true);
        }else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("Tribe");
            FirebaseMessaging.getInstance().setDeliveryMetricsExportToBigQuery(false);
        }


     //  registerReceiver(myReceiver, new IntentFilter("Notification"));//----------HJ
       registerReceiver(myReceiver, new IntentFilter("Notification"), Context.RECEIVER_NOT_EXPORTED);

        channelId = getString(R.string.default_notification_channel_id);



       //Crashlytics.getInstance().crash();
        //AutoStartHelper.getInstance().getAutoStartPermission(this);
        Button crashButton = new Button(this);

        // crashButton.setText("Crash!");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Crashlytics.getInstance().crash(); // Force a crash
//            }
//        });
        /*addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));*/

        // themeUtils.changeToTheme(this, themeUtils.BLACK);
        if (Utility.isAppRunning(this, "com.chetaru.tribe365_new")) {

            if (firstCheck) {
                String timeDate = utility.getCurrentDate();
                firstCheck = false;
                Log.d("appStatus", "App is Running: " + timeDate);
                if (startInteractionDate.equals("")) {
                    startInteractionDate = timeDate;
                }
            }

        } else {
            String timeDate = utility.getCurrentDate();

            Log.d("appStatus", "App is disconnect: " + timeDate);
            lastInteractionDate = timeDate;
        }
        /*if (!userId.equals("")) {
            api_getnotificationCount();
        }*/

    }

    private void getFullViewScreen() {
        try {
             View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
       /* if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            BottomNavigationView navigation= (BottomNavigationView) findViewById(R.id.nav_view);
            navigation.setRotation(90f);
            navigation.getLayoutParams().width=480;
            navigation.requestLayout();
            navigation.setY(600f);
            navigation.setX(-635f);
            // navigation.requestLayout();
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                final View iconView = menuView.getChildAt(i);
                iconView.setRotation(-90f);
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            recreate();
        }*/
    }

    public static void showBadge(Context context, BottomNavigationView
            bottomNavigationView, @IdRes int itemId, int value) {
        try {
            removeBadge(bottomNavigationView, itemId, value);
            BottomNavigationItemView itemView = bottomNavigationView.findViewById(itemId);

            View badge = LayoutInflater.from(context).inflate(R.layout.notification_bages, bottomNavigationView, false);

           // BottomNavigationView NavigationView1 = bottomNavigationView.findViewById(R.id.nav_view);
               /* int seletedItemId = bottomNavigationView.getSelectedItemId();

            if (value > 0) {
                if (R.id.nav_risk == seletedItemId) {
                    removeBadge(bottomNavigationView, itemId, 0);
                    badge.setVisibility(View.GONE);
                }else {
                    String valueString= "1";
                    if (value>=100){
                        valueString="99+";
                        TextView text = badge.findViewById(R.id.notification_badge);
                        text.setText(String.valueOf(valueString));
                        itemView.addView(badge);
                    }else {

                        TextView text =badge.findViewById(R.id.notification_badge);
                        text.setText(String.valueOf(value));
                        itemView.addView(badge);
                    }

                }
            } else {
                badge.setVisibility(View.GONE);
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void removeTextLabel(@NonNull BottomNavigationView bottomNavigationView, @IdRes int menuItemId) {
        View view = bottomNavigationView.findViewById(menuItemId);
        if (view == null) return;
        if (view instanceof MenuView.ItemView) {
            ViewGroup viewGroup = (ViewGroup) view;
            int padding = 0;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View v = viewGroup.getChildAt(i);
                if (v instanceof ViewGroup) {
                    padding = v.getHeight();
                    viewGroup.removeViewAt(i);
                }
            }
            viewGroup.setPadding(view.getPaddingLeft(), (viewGroup.getPaddingTop() + padding) / 2, view.getPaddingRight(), view.getPaddingBottom());
        }
    }
    public static void removeBadge(BottomNavigationView bottomNavigationView, @IdRes int itemId, int value) {
        try {
            BottomNavigationItemView itemView = bottomNavigationView.findViewById(itemId);

            try {
              //  Log.d("TAG", "id:" + itemId + " getChildCount: " + itemView.getChildCount());

                for (int i = 0; i < itemView.getChildCount(); i++) {
                    View v = itemView.getChildAt(i);
                    if (v instanceof ViewGroup) {
                        itemView.removeViewAt(i);
                    }
                    if(i==4){
                        itemView.removeViewAt(i);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {


                /*if (value <= 0) {
                    // BottomNavigationItemView v=itemView.getChildAt(4);
                    if (itemView != null) {
                        itemView.removeViewAt(4);
                    }

                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public Void NotiCallBack(String Action) {


        return null;
    }

    @Override
    protected void onDestroy() {
        sActivities.remove(this);
        super.onDestroy();

        String timeDate = utility.getCurrentDate();

        if (MyAppContext.isAppStop) {
            lastInteractionDate = ((MyAppContext) getApplication()).lastInteractionDate;
            getOpenCloseAppStatus();
        }

        Log.d("appStatus", "App is disconnect time: " + timeDate);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // pref.edit().putLong("LAST_RUN", System.currentTimeMillis()).apply();


    }

    @Override
    protected void onStart() {

        super.onStart();
        updateNavigationBarState();
        String timeDate = utility.getCurrentDate();
        firstCheck = false;
        Log.d("appStatus", "App is Running time: " + timeDate);
    }

    /*public void setStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = BaseActivity.this.getWindow();
            int statusBarColor = Color.parseColor(color);
            if (statusBarColor == Color.BLACK && window.getNavigationBarColor() == Color.BLACK) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setStatusBarColor(statusBarColor);
        }
    }*/

    @Override
    public Resources getResources() {
        if (res == null) {
            res = new Res(super.getResources());
        }
        return res;
    }

   /* public void setAppBar(String title, boolean isBackVisible) {
        Toolbar toolbar = findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isBackVisible);
        getSupportActionBar().setDisplayShowHomeEnabled(isBackVisible);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                hideSoftKeyboard();
            }
        });
        getSupportActionBar().setTitle(title);
        //existingMsg
    }*/

    public void callHomeAct(Context context) {
            finishAllActivities();
            if (sessionParam.loginVersion==3){
                startActivity(new Intent(context, BasicHomeActivity.class));
            }else {
                startActivity(new Intent(context, Act_Home.class));
            }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, new IntentFilter("Notification"), Context.RECEIVER_NOT_EXPORTED);
         //registerReceiver(myReceiver, new IntentFilter("Notification"));//....hj

        //((MyAppContext)getApplication()).setIsAppRunning(true);
        startInteractionDate = ((MyAppContext) getApplication()).startInteractionDate;
        //hideSoftKeyboard();
        if (!sessionParam.id.equals("")) {
            if (MyAppContext.isAppBackGround) {
                getOpenCloseAppStatus();
            } else if (MyAppContext.isAppStop) {
                getOpenCloseAppStatus();
            }
        }
        try {
            if(!userId.equals("")&& userId!=null) {
                BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

                /*int seletedItemId = bottomNavigationView.getSelectedItemId();

                if (R.id.nav_risk != seletedItemId) {
                    api_getnotificationCount();
                }*/

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
        overridePendingTransition(0, 0);
        // ((MyAppContext)getApplication()).setIsAppRunning(false);
        //hideSoftKeyboard();
        lastInteractionDate = ((MyAppContext) getApplication()).lastInteractionDate;
        if (!sessionParam.id.equals("")) {
            if (MyAppContext.isAppBackGround) {
                getOpenCloseAppStatus();
            } else if (MyAppContext.isAppStop) {
                getOpenCloseAppStatus();
            }
        }

    }

    public void hideSoftKeyboard(Context mContext) {
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) mContext
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken
                    (), 0);
        } catch (Exception e) {
        }
    }

    public void getOpenCloseAppStatus() {
        baseRequest = new BaseRequest(this);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject(
                "startInteractionDate", startInteractionDate,
                "lastInteractionDate", lastInteractionDate
        );
        Log.d("appStatus", "app status time save in api " + startInteractionDate + " LastInteraction  " + lastInteractionDate);
        baseRequest.callAPIPostWOLoader(1, object, ConstantAPI.userInteractionOnApp);

    }

    public void finishAllActivities() {
        if (sActivities != null) {
            for (Activity activity : sActivities) {
                if (Config.DEBUG) {
                    Log.d("BaseActivity", "finishAllActivities activity=" + activity.getClass()
                            .getSimpleName());
                }
                activity.finish();
            }
        }
    }

    public String getAppString(int id) {
        String str = "";
        if (!TextUtils.isEmpty(this.getResources().getString(id))) {
            str = this.getResources().getString(id);
        } else {
            str = "";
        }
        return str;
    }

    public void dialogBaseActivity() {

        if (dialog == null) {
            dialog = new Dialog(mContext);
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feedback_hows_your_day);
        //DialogFeedbackHowsYourDayBinding binding=DialogFeedbackHowsYourDayBinding.inflate(LayoutInflater.from(mContext));
        //dialog.setContentView(binding.getRoot());
        dialog.setCanceledOnTouchOutside(true);
        final ImageView happy = dialog.findViewById(R.id.happy);
        final ImageView neutral = dialog.findViewById(R.id.neutral);
        final ImageView sad = dialog.findViewById(R.id.sad);
        happy.setOnClickListener(v -> {
           // api_addHappyIndex("3");
            dialog.dismiss();
        });
        neutral.setOnClickListener(v -> {
          //  api_addHappyIndex("2");
            dialog.dismiss();
        });
        sad.setOnClickListener(v -> {
           // api_addHappyIndex("1");
            dialog.dismiss();
        });
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        dialog.show();
    }

    /*public void api_addHappyIndex(String status) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    String timeDate = utility.getCurrentDate();
                    sessionParam.happyIndexDialogShow(mContext, timeDate, true);
                    JSONObject jsonObject = new JSONObject(object.toString());
                    String todayEIScore = jsonObject.getString("todayEIScore");
                    if(status.equals("1")){
                       // dialogGetFeedback("happyIndexSend",todayEIScore);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                try {
                    utility.showToast(mContext, message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id, "moodStatus", status);
        baseRequest.callAPIPostWOLoader(1, object,ConstantAPI.api_addHappyIndex);
    }*/
    /********************************* Dialog to get Feedback when user not happy **************/
    private void dialogGetFeedback(String title,String todayEIScore) {
        try {
            Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_get_feedback_sentiment);
            //DialogGetFeedbackSentimentBinding binding=DialogGetFeedbackSentimentBinding.inflate(LayoutInflater.from(mContext));
            //dialog.setContentView(binding.getRoot());
          //  dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            final TextView dialogTitleTv = dialog.findViewById(R.id.dialog_title_tv);
            final TextView eng_tv_score = dialog.findViewById(R.id.eng_score_tv);
            final ImageView eng_image = dialog.findViewById(R.id.eng_image);
            final EditText etComment =dialog.findViewById(R.id.et_comment);
            final TextView tvSave =dialog.findViewById(R.id.tv_save);

            Double indexEngScore = Double.valueOf(todayEIScore);


            dialogTitleTv.setText(getString(R.string.sentiment_submit));
            tvSave.setOnClickListener(v->{
                if (etComment.getText().toString().trim().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_enter_offloading));
                    return;
                }
                if (!etComment.getText().toString().trim().equals("")){
                    String msg=etComment.getText().toString().trim();
                    dialog.dismiss();
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                    sendOffloading(msg);

                    if (dialog!=null) {
                        dialog.dismiss();
                    }
                    etComment.setText("");
                }else {
                    utility.showToast(mContext, getString(R.string.please_enter_offloading));
                }
            });


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            dialog.show();
            Float engScore= Float.valueOf(todayEIScore);


            //  Integer engScoreInt= Integer.parseInt(todayEIScore);
            try {
                int engScoreInt = Math.round(engScore);
                int startValue= 0;

                if (engScoreInt>800 )
                {
                    startValue=400;
                }else if (engScoreInt>600){
                    startValue=200;
                }else if (engScore>400){
                    startValue=100;
                }else {
                    startValue=0;
                }

               // animateTextView(0, engScoreInt, eng_tv_score, eng_image);
            }catch (Exception e){
                e.printStackTrace();
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /******************************** Api for send Feed back **********************/
    public void sendOffloading(String msgString){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    JSONObject jsonObject=new JSONObject(Json);
                    String msg=jsonObject.getString("message");
                    utility.showToast(mContext,msg);

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
        JsonObject object=Functions.getClient().getJsonMapObject("message",msgString,
                "userId", sessionParam.id,
                "orgId",sessionParam.orgId,
                "image","");
        baseRequest.callAPIPost(1,object,ConstantAPI.postFeedback);

    }

    public void logoutDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        //DialogLogoutBinding binding=DialogLogoutBinding.inflate(LayoutInflater.from(mContext));
        //dialog.setContentView(binding.getRoot());
        dialog.setCanceledOnTouchOutside(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvLogout = dialog.findViewById(R.id.tv_logout);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                if (!sessionParam.id.equals("")) {
                    // lastInteractionDate=utility.getCurrentDate();
                    lastInteractionDate = ((MyAppContext) getApplication()).lastInteractionDate;
                    getOpenCloseAppStatus();
                }
                api_logout();

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
                utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("", "");
        baseRequest.callAPIPost(1, object, ConstantAPI.userLogout);
    }



   /* public void handleIntent() {

        Intent intent = this.getIntent();

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        CharSequence message = MyFirebaseMessagingService.getReplyMessage(intent);
        Log.d("BroadcastRecevier", "message: " + message.toString());
        if (remoteInput != null) {

            // TextView myTextView = (TextView) findViewById(R.id.text_desc);
            String inputString = remoteInput.getCharSequence(
                    KEY_TEXT_REPLY).toString();

            //myTextView.setText(inputString);
            if (inputString.contains(new String(Character.toChars(0x1F60A)))) {
                //happy index
                // api_addHappyIndex("3");
                utility.showToast(mContext, "user Happy");
            } else if (inputString.contains(new String(Character.toChars(0x1F610)))) {
                //nutral index
                // api_addHappyIndex("2");
                utility.showToast(mContext, "user Neutral");
            } else if (inputString.contains(new String(Character.toChars(0x1F61F)))) {
                //sad index
                // api_addHappyIndex("1");
                utility.showToast(mContext, "user Sad");
            }

            Notification repliedNotification =
                    null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                repliedNotification = new Notification.Builder(this, getString(R.string.default_notification_channel_id))
                        .setSmallIcon(R.drawable.ic_noti_icon)
                        .setContentText("Reply received")
                        .build();
            }

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId,
                    repliedNotification);
        }
    }*/

    public void api_notificationRead(String id) {
        //hideErrorView();
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

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
        //JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.companyOrgId
        JsonObject object = Functions.getClient().getJsonMapObject("notificationId", id
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.changeNotificationStatus);
    }
    public void api_NotificationCount() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    if (jsonObject.optInt("notificationCount") > 0) {
                        // tv_notiCount.setText(jsonObject.optInt("notificationCount") + "");
                        countValue = jsonObject.optInt("notificationCount");
                        // finalCount=340;
                       // showBadge(BaseActivity.this,navigationView,R.id.nav_risk,countValue);

                    } else {
                        //removeTextLabel(navigationView,R.id.nav_notification);
//                        api_notificationListUnread();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
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
        JsonObject object = Functions.getClient().getJsonMapObject("userId", userId
                //JsonObject object = Functions.getClient().getJsonMapObject("orgId", "9"
        );
        baseRequest.callAPIPostWOLoader(1, object,ConstantAPI.api_getBubbleUnReadNotifications);
    }

    public boolean areNotificationsEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            if (!manager.areNotificationsEnabled()) {
                return false;
            }
            List<NotificationChannel> channels = manager.getNotificationChannels();
            for (NotificationChannel channel : channels) {
                if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    return false;
                }
            }
            return true;
        } else {
            return NotificationManagerCompat.from(mContext).areNotificationsEnabled();
        }
    }
    /************************** Notification Api Call **********/
    public void updateNotificationStatus(){
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                    //JSONArray jsonArray = new JSONArray(object.toString());
                    JSONObject jsonObject= new JSONObject(object.toString());
                    String notiStatus= jsonObject.getString("notificationPush");
                    Log.d("NotificationStatus",notiStatus);
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

        int appStatus=0;
        try{
            boolean status= sessionParam.isSubscribeNoti;
        if (status){
            appStatus=1;
        }else {
            appStatus=0;
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        //0: not enable , 1: enable
        int deviceStatus=0;
        try {
            boolean getStatus=areNotificationsEnabled();
            if (getStatus){
                deviceStatus= 1;
            }else{
                deviceStatus=0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        JsonObject object = Functions.getClient().getJsonMapObject(
                "userId", sessionParam.id,
                "appStatus", appStatus+"",
                "deviceStatus", deviceStatus+"");
        baseRequest.callAPIPostWOLoader(1,object, ConstantAPI.api_updatePushNotificationStatus);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       // navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                if (sessionParam.loginVersion==3){
                    startActivity(new Intent(this,BasicHomeActivity.class));
                }else {
                    startActivity(new Intent(this, Act_Home.class));
                }
            } else if (itemId == R.id.nav_know) {
                startActivity(new Intent(this, Act_Know_Home.class));
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(this, Act_ProfileUser.class));
            } else if (itemId == R.id.nav_risk) {
               // startActivity(new Intent(this,Act_NotificationList.class));
                startActivity(new Intent(this, Act_RiskHome.class));
            }else if(itemId ==R.id.nav_offloading){
                startActivity(new Intent(this, Act_IOTHome.class));
            }
            finish();
       // }, 300);
        return true;
    }

    private void updateNavigationBarState() {
        try {
            Menu menu = navigationView.getMenu();
          //  MenuItem menuItem = menu.getItem(INSERT_INDEX_HERE);

            int actionId = getBottomNavigationMenuItemId();
            selectBottomNavigationBarItem(actionId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        try {
            int seletedItemId = bottomNavigationView.getSelectedItemId();

            if (R.id.nav_home != seletedItemId) {
                setHomeItem(BaseActivity.this);
            } else {
                super.onBackPressed();

            }
        }catch (Exception e){
            e.printStackTrace();
            super.onBackPressed();
        }
    }
    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }




    public  abstract int getLayoutId(); // this is to return which layout(activity) needs to display when clicked on tabs.

    public  abstract int getBottomNavigationMenuItemId();//Which menu item selected and change the state of that menu item
}
