package com.chetaru.tribe365_new.UI.Home;

import static com.chetaru.tribe365_new.Notification.MyFirebaseMessagingService.REPLY_ACTION;
import static com.chetaru.tribe365_new.R.color.transparent;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.BeliefValue;
import com.chetaru.tribe365_new.API.Models.Department;
import com.chetaru.tribe365_new.API.Models.Home.AwardList;
import com.chetaru.tribe365_new.API.Models.Home.HomeBelief;
import com.chetaru.tribe365_new.API.Models.Home.HomeDetailResponse;
import com.chetaru.tribe365_new.API.Models.Home.HomeKudosResponse;
import com.chetaru.tribe365_new.API.Models.Home.LatestKudoAward;
import com.chetaru.tribe365_new.API.Models.ModelDepartmentDialog;
import com.chetaru.tribe365_new.API.Models.User;
import com.chetaru.tribe365_new.API.Models.VersionUpdateModel;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_HomeKudosList;
import com.chetaru.tribe365_new.Adapter.Ad_Individual;
import com.chetaru.tribe365_new.Adapter.Ad_award_list;
import com.chetaru.tribe365_new.Adapter.CircleValueAdapter;
import com.chetaru.tribe365_new.Adapter.OnItemClickCustom;
import com.chetaru.tribe365_new.BuildConfig;
import com.chetaru.tribe365_new.CallBack.OnChildItemsClick;
import com.chetaru.tribe365_new.Notification.Act_NotificationList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.AppTour.Link_click_Activity;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.DismissType;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.orientation;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.GuideView;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Listener.GuideListener;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.BasicVersion.BasicHomeActivity;
import com.chetaru.tribe365_new.UI.HPTM.Act_HPTM_main;
import com.chetaru.tribe365_new.UI.Know.Act_Award_List;
import com.chetaru.tribe365_new.UI.Know.DOT.Act_DOT_Details;
import com.chetaru.tribe365_new.UI.Offloading.Act_IOTHome;
import com.chetaru.tribe365_new.UI.Risk.Act_RiskHome;
import com.chetaru.tribe365_new.databinding.RowBeliefFilterListBinding;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.MyCircleImageView;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_Home extends BaseActivity implements View.OnClickListener{

    /******** Handel broadcast msg reply option *****************/
    private static final String KEY_MESSAGE_ID = "key_message_id";
    private static final String KEY_NOTIFY_ID = "key_notify_id";
    private static final String KEY_NOTIFY_ID_OFF = "key_notify_id";
    private static final String KEY_TEXT_REPLY_sceond = "key_text_reply_sceond";
    private static final int notificationId=101;
    private static final int offloading_notificationId=102;

    /************** creating variable to access home page related data ******************/
    SessionParam sessionParam;
    Utility utility;
    Activity activity;
    private BaseRequest baseRequest;
    String deviceId = "";
    Boolean dailyNotiStatus = false;
    String dot_id = "";
    int indexEngScore = 0;
    String indexEngValue = "";
    String orgId = "";
    boolean showButton=false;
    String data = "";
    ModelDepartmentDialog modelDepartmentDialog;
    int countValue=0;
    boolean flag=false,clickableFlag=true;
    //handel push for show Kudos report
    boolean flagClick=false;
    String backHandel="";
    String userId="",amazingComment="";

    int introType = 0;

    private GuideView mGuideView;
    private GuideView.Builder builder;
    public static boolean checkLater = false;
    String newVersion = "1.55";
    NotificationManager notificationManager;
    Dialog mDialog=null;
    boolean isTablet;
    final Calendar myCalendar= Calendar.getInstance();

    /******** Initialize View Top to Bottom *********************/
    /***************** App Tour msg****************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.well_come_msg_view)
    View well_come_msg_view;

    @BindView(R.id.iv_notic)
    ImageView noti_bell_image;
    @BindView(R.id.tv_notiCount)
    TextView circle_count_tv;


    /**************** update layout **************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.update_layout)
    LinearLayout update_layout;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.later_button)
    TextView later_button;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.update_button)
    TextView update_button;

    /************Top header Layout **********/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_action_right)
    TextView tv_action_right;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_back_ll)
    LinearLayout iv_top_back_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_companylogo)
    ImageView iv_top_companylogo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cv_index_eng)
    CardView cv_index_eng;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.index_eng_image)
    ImageView index_eng_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_index_eng_number)
    TextView tv_index_eng_number;

    /*************** Happy Index Card layout ***********/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.card_happy_index_ll)
    CardView card_happy_index_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.happy)
    ImageView happyEmoji;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.neutral)
    ImageView neutralEmoji;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sad)
    ImageView sadEmoji;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.absent_Txt)
    TextView absent_Txt;
    /********************* Absent Card View **********/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.absent_card_view)
    CardView absent_card_view;



    /************* card View to show Vision *************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.vision_card_view)
    CardView vision_card_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_vision)
    TextView tv_vision;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_viewPlay_map)
    LinearLayout ll_viewPlay_map;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_video_cv)
    CardView show_video_cv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_view_more)
    TextView tv_view_more;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_view_play_map)
    TextView tv_view_play_map;

    /************ main layout to show Kudos*************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.kudos_recycler_view_ll)
    RelativeLayout kudos_recycler_view_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_circle_value)
    RecyclerView rv_circle_value;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_circle_tv)
    TextView show_circle_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fb_select_kudos_next)
    CircularImageView fb_select_kudos_next;

    /*************** second main Layout *********/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.individual_card_view_ll)
     CardView  individual_card_view_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.search_bar)
    SearchView search_bar;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_individual)
    RecyclerView  rv_individual;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fb_select_individual_next)
    CircularImageView fb_select_individual_next;
    @BindView(R.id.fb_select_amazing_next)
    CircularImageView fb_select_amazing_next;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fb_select_individual_prevoius)
      FloatingActionButton fb_select_individual_prevoius;


    /*******************get Amazing Cart layout **************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_amazing)
    RelativeLayout ll_amazing;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.card_amazing_ll)
    CardView card_amazing_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_tv_name)
    TextView amazing_tv_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_value_card_ll)
    CardView amazing_value_card_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_dot_value)
    TextView amazing_dot_value;


    /****************** new Amazing card layout *******************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_card_view_ll)
    CardView amazing_card_view_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_name_image)
    ImageView amazing_name_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.card_amazing_value_ll)
    CardView card_amazing_value_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_award_count_tv)
    TextView amazing_award_count_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.award_value_ll)
    LinearLayout award_value_ll;


    /************** award cardView *************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.award_tv_space)
    TextView award_tv_space;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.award_card_ll)
    CardView award_card_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.user_image_kudos)
    MyCircleImageView user_image_kudos;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.kudos_description_tv)
    TextView kudos_description_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.kudos_value_tv)
    TextView kudos_value_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.kudos_award_date)
    TextView kudos_award_date;



    /******************* Navigation View **************/
    TextView tv_notiCount;
    List<User> resetUserlist;
    List<Department> resetDepartmentsList;


    /****** Adapter and Array list for Kudos Value **/
    CircleValueAdapter ad_circle_value;
    List<HomeBelief> kudosList;
    List<HomeBelief> kudosSelectionList;
    List<HomeBelief> filteredUserList;
    int kudosCount = 0;
    int amazingValue=0;
    boolean amazingFlag= false;

    /***** Adapter and array list fro individual list ******/
    Ad_Individual ad_individual;
    List<User> userList;
    List<User> userSelectedList;
    int individualCount=0;
    String notificationType="";
    String LongPressBeliefId= "";
    String LongPressDotValue= "";
    String LongPressValueNameId="";
    List<AwardList> awardList;
    LatestKudoAward latestKudoAward= null;


    /******************** set Amazing Value and key ******************/
    String amazingValueKey="";
    int todayAwardCount=0;
    int yesterdayAwardCount=0;
    int thisWeekAwardCount=0;
    int lastWeekAwardCount=0;
    int thisMonthAwardCount=0;
    int lastMonthAwardCount=0;
    int totalAwardCount=0;



    /************** get Desboard Details throw Api *********/
    HomeDetailResponse homeDetail = new HomeDetailResponse();
    HomeKudosResponse kudosResponse= new HomeKudosResponse();
    List<HomeBelief> totalKudosList;


    /************* handel notification msg***********/
    public static Intent getReplyMessageIntent(Context context,int notifyId,int messageId){
        Intent intent=new Intent(context,Act_Home.class);
        intent.setAction(REPLY_ACTION);
        intent.putExtra(KEY_MESSAGE_ID,messageId);
        intent.putExtra(KEY_NOTIFY_ID,notifyId);
        return intent;

    }
    public static Intent getReplyMessageIntentOff(Context context,int notificationId,int messageId){
        Intent intent=new Intent(context,Act_Home.class);
        intent.setAction(ACTION_REPLY_TAPPED);
        intent.putExtra(KEY_MESSAGE_ID,messageId);
        intent.putExtra(KEY_NOTIFY_ID_OFF,notificationId);
        return  intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_home);
        sessionParam = new SessionParam(mContext);
        utility = new Utility();
        userId=sessionParam.id;
        userList = new ArrayList<>();
        resetUserlist = new ArrayList<>();
        totalKudosList= new ArrayList<>();
        awardList = new ArrayList<>();
        ButterKnife.bind(this);
        activity = this;
        /*********** getSessionParam **************/
        getSessionParam();

        /*********** check update App Version on play sotre *********/
        newVersion = BuildConfig.VERSION_NAME;
        Log.d("versionName", newVersion);
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            newVersion = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();

        }
        /************* check current working orientation ********/
        isTablet=getResources().getBoolean(R.bool.isTablet);


        /********** check update app Api for show popup****************/
        if (!checkLater){
            checkAppStaus();
        }
        /*********** send notification read status ************/
        if (getIntent().getStringExtra("readNotificationId")!=null){
            String readNotificationId=getIntent().getStringExtra("readNotificationId");
            api_notificationRead(readNotificationId);
        }
        try {
            /** To diagnose an issue, it’s often helpful to know which of your users experienced a given crash.
             * Crashlytics includes a way to anonymously identify users in your crash reports**/
            FirebaseCrashlytics.getInstance().setUserId(sessionParam.id);
        }catch (Exception e){
            e.printStackTrace();
        }

        /******** handel Bottom nav with notification Count badge*******/

      /*  bottomNavigation = findViewById(R.id.nav_view);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getNotificationBadge(bottomNavigation, mContext);*/


        /******* get kudos Value and set on RecyclerView *******/
        getCircleValueRecycler();
        /******** get Individual Data and set RecyclerView *****/
        getIndividualDataRecyclerView();

        /*************** api Calling ******/
        //getDashboardDetail();
        getHomePageDetails();
        //getKudos();
        getDepartment();
        api_getnotificationCount();
        updateNotificationStatus();

        /********* switch Layout ***********/
        tv_action_right.setVisibility(View.VISIBLE);
        kudos_recycler_view_ll.setVisibility(View.VISIBLE);
        individual_card_view_ll.setVisibility(View.GONE);
        amazing_card_view_ll.setVisibility(View.GONE);

        /***** Selecting View Listener click *******/
        tv_vision.setOnClickListener(this);
        tv_view_more.setOnClickListener(this);
        tv_view_play_map.setOnClickListener(this);
        fb_select_kudos_next.setOnClickListener(this);
        fb_select_individual_next.setOnClickListener(this);
        fb_select_amazing_next.setOnClickListener(this);
        iv_top_back.setOnClickListener(this);
        card_happy_index_ll.setOnClickListener(this);
        happyEmoji.setOnClickListener(this);
        neutralEmoji.setOnClickListener(this);
        sadEmoji.setOnClickListener(this);
        tv_action_right.setOnClickListener(this);
        update_button.setOnClickListener(this);
        later_button.setOnClickListener(this);
        noti_bell_image.setOnClickListener(this);
        absent_Txt.setOnClickListener(this);

        //switchPresentDialog();
        /*************** App tour ********/
        int getIntroType = getIntent().getIntExtra("appTourType", 0);
        notificationType=getIntent().getStringExtra("notificationType");
        try{
            flagClick=getIntent().getBooleanExtra("kudosClick",false);
            backHandel=getIntent().getStringExtra("handelBack");
        }catch (Exception e){
            e.printStackTrace();
        }


        if (getIntroType!=0){
            introType=getIntroType;
        }
        if (introType == 1) {
            show_video_cv.setVisibility(View.VISIBLE);
            ll_viewPlay_map.setVisibility(View.VISIBLE);
            ShowIntro( getString(R.string.wellcome_ppt), R.id.well_come_msg_view, 1);

        }else if(introType == 4){
            show_circle_tv.setVisibility(View.VISIBLE);
            card_happy_index_ll.setVisibility(View.GONE);
            Handler h = new Handler();
            long delayInMilliseconds = 500;
            h.postDelayed(new Runnable() {
                public void run() {
                    card_happy_index_ll.setVisibility(View.GONE);
                    //ShowIntro( getString(R.string.nav_menu_ppt), R.id.nav_view, 1);
                    ShowIntro(getString(R.string.kudous_ppt), R.id.show_circle_tv, 5);
                    builder.setGravity(com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.Gravity.auto);
                    show_circle_tv.setVisibility(View.GONE);
                }
            }, delayInMilliseconds);
        }else if (introType == 8){
            ShowIntro(getString(R.string.profile_ppt), R.id.nav_profile, 10);
        }else if (introType > 8) {
          /*  scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });*/


        }
       /* try {
            if (notificationType!=null){
                if (!notificationType.equals(null) && notificationType.equals("badDayOffload")) {
                        dialogGetFeedback("", "");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

        try{
            String reply = getIntent().getStringExtra("reply");
            if (reply != null && !reply.equals("")) {
                notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(offloading_notificationId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        award_card_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Act_Home.this, Act_Award_List.class);
                startActivity(intent);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            handleReplyIntent();
        }
        /*********** get Amazing Card Value**************/
        ll_amazing.setVisibility(View.GONE);
        absent_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEnableAbsent();
            }
        });


       // handler.post(yourRunnable);// this will call immediately and run youRunnable defined in post.

    }


    /************** handel Amazing circle ***************************/
    @SuppressLint("UseCompatLoadingForDrawables")
    public void getAmazingCard(String name, int awardValue){

        ll_amazing.setVisibility(View.GONE);
        amazing_tv_name.setText(name.trim());
        amazing_dot_value.setText(awardValue+"");
        amazing_tv_name.setBackground(this.getResources().getDrawable(R.drawable.circle_red_row));
        card_amazing_ll.setBackground(this.getResources().getDrawable(R.drawable.circle_red_row));
        amazing_value_card_ll.setBackground(this.getResources().getDrawable(R.drawable.bottom_edge_white));
        amazing_tv_name.setTextColor(this.getResources().getColor(R.color.white));
        amazing_dot_value.setTextColor(this.getResources().getColor(R.color.red));
        card_amazing_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAmazingShow(name);
               amazingFlag=true;
            }
        });
        amazing_value_card_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kudosList.size()>0){
                    Object object= new Object();

                    showChangeDialog(kudosList,object , 1);

                }
            }
        });

        /*********** new Amazing CardView ****************/
        amazing_card_view_ll.setVisibility(View.VISIBLE);
        card_amazing_value_ll.setBackground(mContext.getResources().getDrawable(R.drawable.bottom_edge_white));
        amazing_name_image.setOnClickListener(view -> {
            dialogAmazingShow(name);
            amazingFlag=true;
        });
        amazing_award_count_tv.setText(awardValue+"");
        award_value_ll.setOnClickListener(view -> {
            if(kudosList.size()>0){
                Object object= new Object();

                showChangeDialog(kudosList,object , 1);

            }
        });


    }
    /********************************** close OnCreate ******************************/

       @RequiresApi(api = Build.VERSION_CODES.O)
       public  void handleReplyIntent() {

        Intent intent = this.getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
           // TextView myTextView = (TextView) findViewById(R.id.tv_title);
            String inputString = remoteInput.getCharSequence(
                    ACTION_REPLY_TAPPED).toString();
           // myTextView.setText(inputString);
            utility.showToast(mContext,inputString);
            sendOffloading(inputString);
            Notification repliedNotification =
                    new Notification.Builder(this, getString(R.string.default_notification_channel_id))
                            .setSmallIcon(
                                   R.drawable.app_icon)
                            .setContentText("Reply received")
                            .build();
            notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(offloading_notificationId, repliedNotification);
        }
    }

    /***************** handel App Tour **************/
    private void ShowIntro(String stringTitle, int viewId, final int type) {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int sizeInPixels = 18;
        if (isTablet){
            sizeInPixels=24;
        }else {
            sizeInPixels=20;
        }
        card_happy_index_ll.setVisibility(View.GONE);
        builder = new GuideView.Builder(this)
                //.setTitle(title)
                .setContentText(stringTitle)
                .setTargetView(findViewById(viewId))
                // .setContentSpan((Spannable) Html.fromHtml("<font color='white' ,background color='red'>text</p>"))
                .setContentTextSize(sizeInPixels)//optional
                .setTitleTextSize(sizeInPixels)//optional
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
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public void onDismiss(View view) {

                    }
                });
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
           // builder.setOrientation(orientation.landscape);
            if (!isTablet){
                builder.setOrientation(orientation.portrait);

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }else {
                builder.setOrientation(orientation.landscape);
                card_happy_index_ll.setVisibility(View.GONE);
            }
        }
        if (type==7){
           // builder.setGravity(com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.Gravity.auto);
        }
        if (type == 13) {
            builder.setButtonText("End").build();
            builder.setNextButtonText("").build();
           // mGuideView.dismiss();
        }
        if (type>15){
            mGuideView.dismiss();
        }

        mGuideView = builder.build();
        mGuideView.show();
        mGuideView.mMessageView.skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGuideView.dismiss();
                getHomePageDetails();
            }
        });

        mGuideView.mMessageView.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type < 15)
                    mGuideView.dismiss();
                if (type == 1){
                    ShowIntro(getString(R.string.vision_ppt), R.id.tv_view_more, 2);
                } else if (type == 2) {
                    ShowIntro(getString(R.string.vision_map_ppt), R.id.tv_view_play_map, 3);
                }else if(type == 3) {
                    Intent intent = new Intent(mContext, Act_DOT_Details.class);
                    intent.putExtra("checklist", "");
                    intent.putExtra("data", data);
                    intent.putExtra("introType", 8);
                    startActivity(intent);
                    show_circle_tv.setVisibility(View.VISIBLE);
                }else if(type == 5){
                    ShowIntro(getString(R.string.daily_enga_ppt), R.id.cv_index_eng, 6);
                } else if (type == 6) {
                   // ShowIntro(getString(R.string.daily_enga_ppt), R.id.cv_index_eng, 7);
                    ShowIntro(getString(R.string.notification_ppt), R.id.iv_notic, 7);

                }else if (type == 7){
                    Intent intent = new Intent(mContext, Act_IOTHome.class);
                    intent.putExtra("appTourType", 8);
                    startActivity(intent);
                } else  if (type==10) {
                    ShowIntro(getString(R.string.risk_ppt), R.id.nav_risk, 11);
                }else if(type==11){
                    Intent intent= new Intent(mContext, Act_RiskHome.class);
                    intent.putExtra("appTourType",4);
                    startActivity(intent);
                }else if (type==12){
                    ShowIntro(getString(R.string.thanks_ppt), R.id.well_come_msg_view, 13);
                }
                /*
                * ShowIntro(getString(R.string.nav_menu_ppt), R.id.nav_view, 7);
                    builder.setGravity(com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.Gravity.auto);
                * */


            }
        });


        updatingForDynamicLocationViews();

    }
    /****** update dynamicLocation whwn show error in appTour ********/
    private void updatingForDynamicLocationViews() {
        individual_card_view_ll.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mGuideView.updateGuideViewLocation();
            }
        });
    }

    /********** onPause call for reciewver ********/
    @Override
    protected void onPause() {
        super.onPause();
        //getHomePageDetails();
        kudos_recycler_view_ll.setVisibility(View.VISIBLE);
        fb_select_kudos_next.setVisibility(View.GONE);
        iv_top_back.setVisibility(View.GONE);
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
       // handler.removeCallbacks(yourRunnable);
    }

    /*************** handel OnClick Listener ***************/
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.later_button:
                checkLater = true;
                update_layout.setVisibility(View.GONE);
                break;
            case R.id.update_button:
                checkLater=false;
                final String appPackageName=getPackageName();
                try{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                }catch (android.content.ActivityNotFoundException anfe){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.tv_action_right:
                //startActivity(new Intent(mContext, Act_ActionList.class));
               // startActivity(new Intent(mContext, Act_HPTM_main.class));
               /* Intent intent = new Intent(mContext, Act_IOTHome.class);
                intent.putExtra("appTourType", 9);
                startActivity(intent);*/
                Intent hptm_intent=new Intent(mContext, Act_HPTM_main.class);
                hptm_intent.putExtra("backHandel","Home");
                startActivity(hptm_intent);
                break;
            case R.id.fb_select_kudos_next:
                individual_card_view_ll.setVisibility(View.VISIBLE);
               // individual_card_view_ll.setVisibility(View.GONE);
                iv_top_back.setVisibility(View.VISIBLE);
                award_card_ll.setVisibility(View.GONE);
                fb_select_kudos_next.setVisibility(View.GONE);
                fb_select_individual_next.setVisibility(View.GONE);
               // iv_top_back_ll.setVisibility(View.VISIBLE);
                kudos_recycler_view_ll.setVisibility(View.GONE);
                kudosSelectionList=new ArrayList<>();
                for (int i = 0; i < kudosList.size(); i++) {
                    if (kudosList.get(i).isSelected()) {
                        kudosSelectionList.add(kudosList.get(i));
                    }
                }
                break;
            case R.id.fb_select_individual_next:
                //award_card_ll.setVisibility(View.VISIBLE);
                checkAwardBottom(latestKudoAward);
                userSelectedList=new ArrayList<>();
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).isSelected()) {
                        userSelectedList.add(userList.get(i));
                    }
                }
                /**** send Kudos Api *******/
                kudosSendApi();
                individual_card_view_ll.setVisibility(View.GONE);
                iv_top_back.setVisibility(View.GONE);
               // iv_top_back_ll.setVisibility(View.GONE);
                kudos_recycler_view_ll.setVisibility(View.VISIBLE);
                break;
            case R.id.fb_select_amazing_next:
                //award_card_ll.setVisibility(View.VISIBLE);
                checkAwardBottom(latestKudoAward);
                if (amazingFlag){
                    sendAmazingAward(amazingComment);
                }else {
                    userSelectedList=new ArrayList<>();
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).isSelected()) {
                            userSelectedList.add(userList.get(i));
                        }
                    }

                    sendDotValueKudosAward(amazingComment);
                }
                individual_card_view_ll.setVisibility(View.GONE);
                iv_top_back.setVisibility(View.GONE);
                // iv_top_back_ll.setVisibility(View.GONE);
                kudos_recycler_view_ll.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_vision:
                
                //cv_index_eng.startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
                //card_happy_index_ll.startAnimation(AnimationUtils.loadAnimation(this,R.anim.blink));


                if (!showButton) {
                    ll_viewPlay_map.setVisibility(View.VISIBLE);
                    showButton=true;
                }else {
                    ll_viewPlay_map.setVisibility(View.GONE);
                    showButton=false;
                }
                break;

            case R.id.tv_view_more:
                try {
                    if (!homeDetail.getVisionUrl().trim().equals("") || !homeDetail.getVisionDesc().trim().equals("")) {
                        Intent linkIntent = new Intent(mContext, Link_click_Activity.class);
                        linkIntent.putExtra("TitleName", "Vision");
                        linkIntent.putExtra("subTitleName", homeDetail.getVision());
                        linkIntent.putExtra("Description", homeDetail.getVisionDesc());
                        linkIntent.putExtra("videoURL", homeDetail.getVisionUrl());
                        startActivity(linkIntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_view_play_map:
                Intent intent = new Intent(mContext, Act_DOT_Details.class);
                intent.putExtra("checklist", "");
                intent.putExtra("data", data);
                startActivity(intent);
                break;
            case R.id.iv_top_back:
                individual_card_view_ll.setVisibility(View.GONE);
                kudos_recycler_view_ll.setVisibility(View.VISIBLE);
                iv_top_back.setVisibility(View.GONE);
                getHomePageDetails();
               // iv_top_back_ll.setVisibility(View.GONE);
                checkAwardBottom(latestKudoAward);
                break;
            case R.id.card_happy_index_ll:
                //card_happy_index_ll.startAnimation(AnimationUtils.loadAnimation(this,R.anim.blink));
                card_happy_index_ll.clearAnimation();
                break;
            case R.id.iv_notic:
                startActivity(new Intent(this, Act_NotificationList.class));
                break;
            case R.id.absent_Txt:
                /*************** show date submission popup *********/
                //dialogHappyIndexAbsent();
                dialogAbsent();


                break;
        }

    }

    /*********** getSessionParam **************/
    private void getSessionParam() {
        try {
            deviceId = utility.getAndroidID(this);
            sessionParam.saveDeviceId(mContext, deviceId);
            if (orgId.equals("")) {
                orgId = sessionParam.orgId;
            }
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*********** onResume For get Notification Badge and update kudos *****/
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, new IntentFilter("Notification"), Context.RECEIVER_NOT_EXPORTED);

       // registerReceiver(myReceiver,new IntentFilter("Notification"));
           // getHomePageDetails();
        api_getnotificationCount();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    /************ handle Broadcast Receiver  *********/
    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getStringExtra("action");
            try{
                if (action !=null && action.equals("kudos")){
                    getHomePageDetails();
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    /******** handle Bottom nav with notification Count badge*******/
    private void getNotificationBadge(BottomNavigationView bottomNavigation, Context mContext) {
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3);

        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_bages, bottomNavigationMenuView, false);
        tv_notiCount = badge.findViewById(R.id.notification_badge);
        itemView.addView(badge);
        // itemView.addView(badgeChat);

        tv_notiCount.setVisibility(View.GONE);

    }


    /****** showBadges *****************/
    public static void showBadge(Context context, BottomNavigationView
            bottomNavigationView, @IdRes int itemId, int value) {
        try {
            removeBadge(bottomNavigationView, itemId, value);
            BottomNavigationItemView itemView = bottomNavigationView.findViewById(itemId);

            View badge = LayoutInflater.from(context).inflate(R.layout.notification_bages, bottomNavigationView, false);

            if (value > 0) {

                if (value>=100){
                    TextView text= badge.findViewById(R.id.notification_badge);
                    text.setText("99+");
                    itemView.addView(badge);
                }else {
                    TextView text = badge.findViewById(R.id.notification_badge);
                    text.setText(String.valueOf(value));
                    itemView.addView(badge);
                }
            }else {
                badge.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /******************** removeBadges **************/
    public static void removeBadge(BottomNavigationView bottomNavigationView, @IdRes int itemId, int value) {
        BottomNavigationItemView itemView = bottomNavigationView.findViewById(itemId);
       // Log.d("TAG", "id:" + itemId + " getChildCount: " + itemView.getChildCount());
        try{
            for (int i = 0; i < itemView.getChildCount(); i++) {
                View v = itemView.getChildAt(i);
                if (v instanceof ViewGroup) {
                    itemView.removeViewAt(i);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if (itemView.getChildCount() >= 2) {
                itemView.removeViewAt(4);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {


            if (value <= 0) {
                // BottomNavigationItemView v=itemView.getChildAt(4);
                if (itemView!=null) {
                    itemView.removeViewAt(4);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /******* get kudos Value and set on RecyclerView *******/
    private void getCircleValueRecycler() {
        kudosList=new ArrayList<>();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Do some stuff
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            rv_circle_value.setLayoutManager(gridLayoutManager);
            rv_circle_value.setNestedScrollingEnabled(false);
        }else if (isTablet){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            rv_circle_value.setLayoutManager(gridLayoutManager);
            rv_circle_value.setNestedScrollingEnabled(false);
        }else {

            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            rv_circle_value.setLayoutManager(gridLayoutManager);
        }
         rv_circle_value.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        rv_circle_value.addItemDecoration(itemDecoration);


    }

    /******** get Individual Data and set RecyclerView *****/
    private void getIndividualDataRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_individual.setLayoutManager(layoutManager);
        rv_individual.setHasFixedSize(true);
        // Get drawable object
        Drawable mDivider = ContextCompat.getDrawable(this, R.drawable.divider);
        // Create a DividerItemDecoration whose orientation is Horizontal
        DividerItemDecoration hItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        // Set the drawable on it
        assert mDivider != null;
        hItemDecoration.setDrawable(mDivider);
        rv_individual.setNestedScrollingEnabled(false);
       // rv_individual.addItemDecoration(hItemDecoration);

        search_bar.setIconifiedByDefault(false);
        try {
            search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    try {
                        ad_individual.getFilter().filter(newText);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return true;
                }
            });
            rv_individual.addItemDecoration(new DividerItemDecoration(rv_individual.getContext(), DividerItemDecoration.VERTICAL));
        }catch (Exception e){
            e.printStackTrace();
        }
       // rv_individual.addItemDecoration(new SimpleDividerItemDecoration(this));

    }

    /******************* set Engagement score **************/
    //score index Engagement data
    private void indexEngScoreData( String EngValue) {
        //int indexEngScore=1506;
        Double   indexEngScore= Double.valueOf(EngValue);
        String showEngValue=trimTrailingZeros(EngValue);
        if (  indexEngScore <= 499) {
            index_eng_image.setImageResource(R.drawable.low);
            //tv_index_eng_number.setText(indexEngScore+"");
            tv_index_eng_number.setText(showEngValue + "");
            tv_index_eng_number.setTextColor(getResources().getColor(R.color.motion_index_low));
        } else if (indexEngScore >= 500 && indexEngScore <= 1099) {
            index_eng_image.setImageResource(R.drawable.medium);
            //tv_index_eng_number.setText(indexEngScore+"");
            tv_index_eng_number.setText(showEngValue + "");
            tv_index_eng_number.setTextColor(getResources().getColor(R.color.motion_index_medium));
        } else if (indexEngScore >= 1100) {
            index_eng_image.setImageResource(R.drawable.smile_green_big);
            //tv_index_eng_number.setText(indexEngScore+"");
            tv_index_eng_number.setText(showEngValue + "");
            tv_index_eng_number.setTextColor(getResources().getColor(R.color.motion_index_high));
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(150, 600.0f);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                tv_index_eng_number.setText(showEngValue +"");
            }
        });
        valueAnimator.start();
    }
    private static String trimTrailingZeros(String number) {
        if(!number.contains(".")) {
            return number;
        }

        return number.replaceAll("\\.?0*$", "");
    }
    public static String removeZero(double number) {
        DecimalFormat  format = new DecimalFormat("#.###########");
        return format.format(number);
    }


    /************************** Api calling Section start ***************************/


    /****************** show happyIndex on 4 O'clock******/
    private void getHappyIndexBlink(Boolean dailyNotiStatus, Integer leaveStatus) {

       // dailyNotiStatus=false;
        if (leaveStatus!= 1) {
            if (!dailyNotiStatus && leaveStatus != 1) {

                if (checkTime()) {

                    card_happy_index_ll.setVisibility(View.VISIBLE);
                    //happy index click handel with happy
                    happyEmoji.setOnClickListener(v -> {
                        api_addHappyIndex("3");
                        card_happy_index_ll.clearAnimation();
                    });
                    //happy index click handel with neutral
                    neutralEmoji.setOnClickListener(v -> {
                        api_addHappyIndex("2");
                        card_happy_index_ll.clearAnimation();
                    });
                    //happy index click handel with sad
                    sadEmoji.setOnClickListener(v -> {
                        api_addHappyIndex("1");
                        card_happy_index_ll.clearAnimation();
                    });
                    // card_happy_index_ll.startAnimation(AnimationUtils.loadAnimation(this,R.anim.blink));
                } else {
                    card_happy_index_ll.setVisibility(View.GONE);
                }
            } else {
                card_happy_index_ll.setVisibility(View.GONE);
            }
        }else{
            card_happy_index_ll.setVisibility(View.GONE);
        }

    }

    /*********************************** get HomePage details api ***********************/
    private void getHomePageDetails() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                    homeDetail = gson.fromJson(object.toString(), HomeDetailResponse.class);

                    try{
                        dailyNotiStatus = homeDetail.isUserGivenfeedback();
                        if (homeDetail.getAppPaymentVersion()==1){
                            Intent intent = new Intent(mContext, Act_FreeVersionHome.class);
                            startActivity(intent);
                            finish();
                        }else if (homeDetail.getAppPaymentVersion()==3){
                            Intent intent= new Intent(mContext,BasicHomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        sessionParam.saveFreeVersion(mContext,homeDetail.getAppPaymentVersion());
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    kudosList= homeDetail.getBelief();
                    try{
                        int status = homeDetail.getLeaveStatus();
                        if (status == 1){
                            //dialogEnableAbsent();
                            absent_card_view.setVisibility(View.VISIBLE);
                        }else {
                            absent_card_view.setVisibility(View.GONE);
                        }
                        sessionParam.getLeaveStatus(mContext,status);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        int status= homeDetail.getNotificationPush();
                        boolean notificationStatus;
                        if (status == 1){
                            notificationStatus = true;
                        }else {
                            notificationStatus = false;
                        }
                        sessionParam.isNotification(mContext,notificationStatus);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    try {
                        List<LatestKudoAward> newKudos= homeDetail.getLatestKAward();
                        if (newKudos.size()>0) {
                            latestKudoAward = newKudos.get(0);
                            checkAwardBottom(latestKudoAward);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        amazingValue=homeDetail.getKudoAwardValue();
                        String awardName=homeDetail.getKudoAwardKey();
                        getAmazingCard(awardName,amazingValue);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    try {

                        dot_id = homeDetail.getDotId();
                        if (dot_id.equals("")){
                            tv_view_play_map.setVisibility(View.GONE);
                        }else{
                            tv_view_play_map.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /************** get Happy index card layout for feedback ***********/
                        getHappyIndexBlink(dailyNotiStatus,homeDetail.getLeaveStatus());
                        if(homeDetail.getVision().trim().isEmpty()) {
                            tv_vision.setText("");
                            vision_card_view.setVisibility(View.GONE);
                        }else{
                            vision_card_view.setVisibility(View.VISIBLE);
                            tv_vision.setText("\"" + homeDetail.getVision() + "\"");
                        }
                    try {
                        indexEngScoreData( homeDetail.getTodayEIScore());
                       // dialogKudosSendResponse(homeDetail.getTodayEIScore());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (homeDetail.getBadDayOffload()){
                            if (!flag) {
                                dialogGetFeedback("", "");
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (homeDetail.getVisionDesc().equals("") && homeDetail.getVisionUrl().equals("")) {
                            tv_view_more.setVisibility(View.GONE);
                            show_video_cv.setVisibility(View.GONE);
                        } else {
                            tv_view_more.setVisibility(View.VISIBLE);
                            show_video_cv.setVisibility(View.VISIBLE);
                        }
                        getDepartment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                /*ad_circle_value=new CircleValueAdapter(kudosList, mContext, new OnChildItemsClick() {
                        @Override
                        public void onParentClick(int id, int position, HomeBelief belief) {
                            try {
                               // HomeBelief beliefList= object;
                                if (!belief.getValueUrl().trim().equals("")|| !belief.getValueDesc().trim().equals("")){
                                    Intent linkIntent=new Intent(mContext,Link_click_Activity.class);
                                    linkIntent.putExtra("TitleName","VALUE");
                                    linkIntent.putExtra("subTitleName",belief.getName());
                                    linkIntent.putExtra("videoURL",belief.getValueUrl());
                                    linkIntent.putExtra("Description",belief.getValueDesc());
                                    startActivity(linkIntent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onChildClick(int id, int position, Object object) {

                            fb_select_kudos_next.setVisibility(View.VISIBLE);

                            kudosCount = 0;
                            for (int i = 0; i < kudosList.size(); i++) {
                                if (kudosList.get(i).isSelected()) {
                                    kudosCount++;
                                    break;
                                }
                            }

                            if(kudosCount>0){
                                fb_select_kudos_next.setVisibility(View.VISIBLE);
                                checkAwardBottom(latestKudoAward);
                            }else {
                                fb_select_kudos_next.setVisibility(View.GONE);

                            }
                        }

                        @Override
                        public void onSubChildClick(int id, int position, Object object) {
                           *//*****************change belife value on today to last week ********************//*
                            showChangeDialog(kudosList,object , position);
                        }

                    @Override
                    public void onLongClick(int id, int position, HomeBelief belief) {
                           String titleName= belief.getName().trim();
                            LongPressBeliefId= belief.getBeliefId().toString();
                            LongPressDotValue= belief.getId().toString();
                            LongPressValueNameId= belief.getValueId();
                        dialogAmazingShow(titleName);
                        amazingFlag=false;
                    }
                });
                    rv_circle_value.setAdapter(ad_circle_value);*/
                    Ad_HomeKudosList ad_homeKudosList=new Ad_HomeKudosList(kudosList,mContext, new OnChildItemsClick() {
                        @Override
                        public void onParentClick(int id, int position, HomeBelief belief) {
                            try {
                                // HomeBelief beliefList= object;
                                if (!belief.getValueUrl().trim().equals("")|| !belief.getValueDesc().trim().equals("")){
                                    Intent linkIntent=new Intent(mContext,Link_click_Activity.class);
                                    linkIntent.putExtra("TitleName","VALUE");
                                    linkIntent.putExtra("subTitleName",belief.getName());
                                    linkIntent.putExtra("videoURL",belief.getValueUrl());
                                    linkIntent.putExtra("Description",belief.getValueDesc());
                                    startActivity(linkIntent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onChildClick(int id, int position, Object object) {
                            fb_select_kudos_next.setVisibility(View.VISIBLE);

                            kudosCount = 0;
                            for (int i = 0; i < kudosList.size(); i++) {
                                if (kudosList.get(i).isSelected()) {
                                    kudosCount++;
                                    break;
                                }
                            }

                            if(kudosCount>0){
                                fb_select_kudos_next.setVisibility(View.VISIBLE);
                                checkAwardBottom(latestKudoAward);
                            }else {
                                fb_select_kudos_next.setVisibility(View.GONE);

                            }
                        }

                        @Override
                        public void onSubChildClick(int id, int position, Object object) {
                             /***************** change belife value on today to last week ********************/
                            showChangeDialog(kudosList,object , position);
                        }

                        @Override
                        public void onLongClick(int id, int position, HomeBelief belief) {
                            String titleName= belief.getName().trim();
                            LongPressBeliefId= belief.getBeliefId().toString();
                            LongPressDotValue= belief.getId().toString();
                            LongPressValueNameId= belief.getValueId();
                            dialogAmazingShow(titleName);
                            amazingFlag=false;
                        }
                    });
                    rv_circle_value.setAdapter(ad_homeKudosList);


                    /*****************************************get Kudos Value ************************************/
                    getKudos();
                } catch (Exception e) {
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
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", orgId,
                "deviceType", 1 + ""
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getHomePageDetails);
    }
    /****************** call api for kudos Count **************************/
    private void getKudos() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                    //JSONArray jsonArray = new JSONArray(object.toString());
                    JSONObject jsonObject= new JSONObject(object.toString());
                    kudosResponse = gson.fromJson(jsonObject.toString(), HomeKudosResponse.class);
                    totalKudosList=kudosResponse.getBelief();
                     amazingValueKey=kudosResponse.getKudoAwardKey();
                     todayAwardCount=kudosResponse.getTodayKudosAwardCount();
                     yesterdayAwardCount=kudosResponse.getYesterdayKudosAwardCount();
                     thisWeekAwardCount=kudosResponse.getThisWeekKudosAwardCount();
                     lastWeekAwardCount=kudosResponse.getLastWeekKudosAwardCount();
                     thisMonthAwardCount=kudosResponse.getThisMonthKudosAwardCount();
                     lastMonthAwardCount=kudosResponse.getLastMonthKudosAwardCount();
                     totalAwardCount=kudosResponse.getTotalKudosAwardCount();
                    /******* show popup on kudos count ****************/
                    if(flagClick){
                        showChangeDialog(totalKudosList,object , 1);
                        flagClick=false;

                    }

                }catch ( Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
            //    utility.showToast(mContext,message);//todo Hemant
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
          //      utility.showToast(mContext,message);//todo Hemant
            }
        });
        JsonObject object=Functions.getClient().getJsonMapObject("orgId",orgId);
        baseRequest.callAPIPostWOLoader(1, object, ConstantAPI.getHomePageKudosCount);
    }
    /********************* send AmazingAward msg ******************/
    private void sendAmazingAward(String amazingCommentString){
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    fb_select_amazing_next.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(object.toString());
                    String todayEIScore = jsonObject.getString("todayEIScore");
                    indexEngScoreData(todayEIScore);
                    for (int i = 0; i < kudosList.size(); i++) {
                        kudosList.get(i).setSelected(false);
                    }
                    for (int i = 0; i < userList.size(); i++) {
                        userList.get(i).setSelected(false);

                    }
                    fb_select_kudos_next.setVisibility(View.GONE);
                    amazingComment="";


                       /* kudosList.clear();
                        userList.clear();*/
                    individualCount=0;
                    userList.clear();
                    userSelectedList.clear();
                    getHomePageDetails();
                    getDepartment();
                    try {
                        String msg = "";
                        JSONObject obj = new JSONObject(Json);
                        msg = obj.optString("message");
                        utility.showToast(mContext, msg);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //getDepartment();
                   // dialogKudosSendResponse("kudosSend",todayEIScore);
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
        JsonObject object=Functions.getClient().getJsonMapObject();

        JsonArray user_id_array = new JsonArray();
        for (int ui = 0; ui < userList.size(); ui++) {
            if (userList.get(ui).isSelected())
                user_id_array.add(userList.get(ui).getId() + "");
        }

        object.add("toUserId", user_id_array);
        object.addProperty("description",amazingCommentString);
        baseRequest.callAPIPost(1,object,ConstantAPI.addKudosAward);
    }
    public void sendDotValueKudosAward(String amazingCommentString){
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    fb_select_individual_next.setVisibility(View.GONE);
                    fb_select_kudos_next.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(object.toString());
                    String todayEIScore = jsonObject.getString("todayEIScore");
                     LongPressBeliefId= "";
                     LongPressDotValue= "";
                     LongPressValueNameId="";
                     amazingComment="";
                    indexEngScoreData(todayEIScore);
                    for (int i = 0; i < kudosList.size(); i++) {
                        kudosList.get(i).setSelected(false);
                    }

                    for (int i = 0; i < userList.size(); i++) {
                        userList.get(i).setSelected(false);
                    }


                       /* kudosList.clear();
                        userList.clear();*/
                    individualCount=0;
                    userList.clear();
                    userSelectedList.clear();
                    getHomePageDetails();
                    getDepartment();
                    try {
                        String msg = "";
                        JSONObject obj = new JSONObject(Json);
                        msg = obj.optString("message");
                        utility.showToast(mContext, msg);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //getDepartment();
                   // dialogKudosSendResponse("kudosSend",todayEIScore);
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
        JsonObject main_object = new JsonObject();
        JsonArray user_id_array = new JsonArray();
        JsonArray dep_id_array = new JsonArray();
        JsonArray options_array = new JsonArray();
        for (int ui = 0; ui < userList.size(); ui++) {
            if (userList.get(ui).isSelected())
                user_id_array.add(userList.get(ui).getId() + "");
        }

        main_object.add("toUserId", user_id_array);
       // main_object.add("departmentIdArray", dep_id_array);
        main_object.addProperty("dotId", dot_id);
        main_object.addProperty("bubbleFlag", "1");
        JsonObject sub_object = new JsonObject();
       /* for (int i = 0; i < kudosList.size(); i++) {
            if (kudosList.get(i).isSelected()) {
                sub_object.addProperty("dotBeliefId", kudosList.get(i).getBeliefId()+"");
                sub_object.addProperty("dotValueId", kudosList.get(i).getId() + "");
                sub_object.addProperty("dotValueNameId", kudosList.get(i).getValueId());
                sub_object.addProperty("description",amazingComment);
                options_array.add(sub_object);
                sub_object = new JsonObject();
            }
        }*/


        sub_object.addProperty("dotBeliefId",LongPressBeliefId);
        sub_object.addProperty("dotValueId",LongPressDotValue);
        sub_object.addProperty("dotValueNameId",LongPressValueNameId);
        sub_object.addProperty("description",amazingCommentString);
        options_array.add(sub_object);
        main_object.add("options", options_array);
        Log.d("AwardRequest", main_object.toString());
        baseRequest.callAPIPost(1,main_object,ConstantAPI.addDotValueKudosAward);
    }


    /***********************get Department Api for user list ************************/
    /*API call to get department list
     */
    public void getDepartment() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();

                modelDepartmentDialog = gson.fromJson(object.toString(), ModelDepartmentDialog.class);
                resetDepartmentsList = modelDepartmentDialog.getDepartments();
                //userModel = gson.fromJson(object.toString(),Actions.class);
                userList = modelDepartmentDialog.getUsers();
                resetUserlist = modelDepartmentDialog.getUsers();
                for (int i = 0; i < userList.size(); i++) {
                    String id = userList.get(i).getId() + "";
                    if (id.equals(sessionParam.id + "")) {
                        userList.remove(i);
                    }
                }

                ad_individual =new  Ad_Individual(clickableFlag,userList, Act_Home.this, new OnItemClickCustom() {
                    @Override
                    public void onClick(int id, int position, Object object) {

                        individualCount = 0;
                        for (int i = 0; i < userList.size(); i++) {
                            if (userList.get(i).isSelected()) {
                                individualCount++;
                                break;
                            }
                        }
                        if (individualCount>0) {
                            if (!amazingComment.isEmpty()) {
                                fb_select_individual_next.setVisibility(View.GONE);
                                fb_select_amazing_next.setVisibility(View.VISIBLE);
                            } else {
                                fb_select_amazing_next.setVisibility(View.GONE);
                                if (individualCount > 0) {
                                    fb_select_individual_next.setVisibility(View.VISIBLE);
                                } else {
                                    fb_select_individual_next.setVisibility(View.GONE);
                                }
                            }
                        }else {
                            fb_select_amazing_next.setVisibility(View.GONE);
                            fb_select_individual_next.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void dotValueLongClick(BeliefValue beliefValue) {

                    }
                });
                rv_individual.setAdapter(ad_individual);
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                //utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                //utility.showToast(mContext, message);
            }
        });
        //companyorgId is used in action only
        String orgId = "";

            orgId = sessionParam.orgId;

        //JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.companyOrgId

        JsonObject object = Functions.getClient().getJsonMapObject("orgId", orgId
                //at orgid 29 i found department so m using it temporarily
        );
        baseRequest.callAPIPostWOLoader(1, object, ConstantAPI.getDepartmentuserList);
    }

    /**************************** notification Count ********************/
    /*API call to get notification count.
     */
    public void api_getnotificationCount() {
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
                        circle_count_tv.setVisibility(View.VISIBLE);

                        if (countValue > 99) {
                            circle_count_tv.setText("99+" + "");
                        } else {
                            circle_count_tv.setText(countValue + "");
                        }
                        //showBadge(Act_Home.this,navigationView,R.id.nav_risk,countValue);
                    } else {
                        circle_count_tv.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                //utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
               // utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("userId", userId
                //JsonObject object = Functions.getClient().getJsonMapObject("orgId", "9"
        );
        baseRequest.callAPIPostWOLoader(1, object,ConstantAPI.api_getBubbleUnReadNotifications );
    }


    /******************************* send Kudos Api **********************/
    /*API call to save values and rating kudos
     */
    private void kudosSendApi() {
            baseRequest = new BaseRequest(mContext);
            baseRequest.setBaseRequestListner(new RequestReciever() {
                @Override
                public void onSuccess(int requestCode, String Json, Object object) {
                    //here we are resetting all the list
                    try{
                        //showDialogKudosSend();
                        fb_select_kudos_next.setVisibility(View.GONE);
                        fb_select_individual_next.setVisibility(View.GONE);
                         JSONObject jsonObject = new JSONObject(object.toString());
                       String todayEIScore = jsonObject.getString("todayEIScore");
                        indexEngScoreData(todayEIScore);
                        for (int i = 0; i < kudosList.size(); i++) {
                            kudosList.get(i).setSelected(false);
                        }
                        for (int i = 0; i < userList.size(); i++) {
                            userList.get(i).setSelected(false);
                        }


                       /* kudosList.clear();
                        userList.clear();*/
                        userSelectedList.clear();
                        getHomePageDetails();
                        getDepartment();
                        //getDepartment();
                        dialogKudosSendResponse("kudosSend",todayEIScore);


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
            JsonObject main_object = new JsonObject();
            JsonArray user_id_array = new JsonArray();
            JsonArray dep_id_array = new JsonArray();
            JsonArray options_array = new JsonArray();
            for (int ui = 0; ui < userList.size(); ui++) {
                if (userList.get(ui).isSelected())
                    user_id_array.add(userList.get(ui).getId() + "");
            }

            main_object.add("toUserId", user_id_array);
            main_object.add("departmentIdArray", dep_id_array);
            main_object.addProperty("dotId", dot_id);
            main_object.addProperty("bubbleFlag", "1");
            JsonObject sub_object = new JsonObject();
            for (int i = 0; i < kudosList.size(); i++) {
                if (kudosList.get(i).isSelected()) {
                    sub_object.addProperty("dotBeliefId", kudosList.get(i).getBeliefId()+"");
                    sub_object.addProperty("dotValueId", kudosList.get(i).getId() + "");
                    sub_object.addProperty("dotValueNameId", kudosList.get(i).getValueId());
                    options_array.add(sub_object);
                    sub_object = new JsonObject();
                }
            }
            main_object.add("options", options_array);
            Log.d("kudous Value", main_object.toString());
            //baseRequest.callAPIPostWOLoader(1, main_object, getString(R.string.api_getDOTValuesList));
            baseRequest.callAPIPost(1, main_object, ConstantAPI.addDOTBubbleRatingsToMultiDepartment);

    }

    /************* check Update api ***********/
    public void checkAppStaus() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                     /* JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    boolean versionStatus = jsonObject1.optBoolean("versionUpdate");
                    String statusNotStarted = jsonObject1.optString("storedVersion");*/

                    Gson gson = new Gson();
                    VersionUpdateModel updateModel = gson.fromJson(object.toString(), VersionUpdateModel.class);
                    if (!updateModel.getVersionUpdate()) {
                        update_layout.setVisibility(View.VISIBLE);
                    } else {
                        update_layout.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                //ll_checklist.setVisibility(View.GONE);
                //Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
               // Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("version", newVersion
                , "appType", "android");
        baseRequest.callAPIPostWOLoader(1, object, ConstantAPI.api_getCurrentVersionOfApp);
    }

    /***************************** set Mood Status Feedback *****************/
    public void api_addHappyIndex(String status) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    card_happy_index_ll.setVisibility(View.GONE);
                    String timeDate = utility.getCurrentDate();
                    sessionParam.happyIndexDialogShow(mContext, timeDate, true);
                    JSONObject jsonObject = new JSONObject(object.toString());
                   String todayEIScore = jsonObject.getString("todayEIScore");
                    indexEngScoreData(todayEIScore);
                   //sentiment index user send not happy
                   if (status.equals("1")){
                       dialogGetFeedback("happyIndexSend",todayEIScore);
                   }else {
                       dialogKudosSendResponse("happyIndexSend", todayEIScore);
                   }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                try {
                    utility.showToast(mContext, message);
                    card_happy_index_ll.setVisibility(View.GONE);
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
        baseRequest.callAPIPostWOLoader(1, object,ConstantAPI.addHappyIndex);
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

     public  void kudoAwardDetail(String kudosId,String KudosValue){
        BaseRequest baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    Gson gson= new Gson();
                 //AwardList   awardList = gson.fromJson(object.toString(),AwardList.class);
                 /*JSONObject jsonObject = new JSONObject(Json);
                    TOTAL_PAGES = jsonObject.optInt("totalPageCount");
                    JSONArray jsonArray = new JSONArray(object.toString());
                    list = baseRequest.getDataList(jsonArray, ModelNotificationList.class);*/
                    JSONArray jsonArray= new JSONArray(object.toString());
                    awardList= baseRequest.getDataList(jsonArray,AwardList.class);

                    getDialogTop(kudosId,KudosValue);
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
        JsonObject jsonObject= Functions.getClient().getJsonMapObject("dotValueId", kudosId);
        baseRequest.callAPIPost(1,jsonObject,ConstantAPI.kudoAwardDetail);

     }

     public void getKudosAwardList(){
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{

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
         JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id);
         //baseRequest.callAPIPostWOLoader(1, object,ConstantAPI.addHappyIndex);
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
                    card_happy_index_ll.setVisibility(View.GONE);
                    absent_card_view.setVisibility(View.VISIBLE);
                    JSONObject jsonData = new JSONObject(Json);
                    String msg = jsonData.getString("message");
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
        JsonObject object = Functions.getClient().getJsonMapObject("userId", userId
                , "startDate",startDate, "endDate", endDate);
        Log.d("request param" ,object.toString());
        baseRequest.callAPIPost(1, object, ConstantAPI.api_userApplyLeave);
    }
    private void absentStatusChange(){
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

                    JSONObject jsonData = new JSONObject(Json);
                    String msg = jsonData.getString("message");
                    utility.showToast(mContext,msg);
                    getHomePageDetails();
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
        JsonObject object= Functions.getClient().getJsonMapObject("userId",userId);
        baseRequest.callAPIPost(1,object,ConstantAPI.api_userChangeLeaveStatus);
    }
     /************************************** close APi Calling *********************************************/

    /***************************** handel Dialogs *********************/


    /***************************** Dialogs to show Success Msg*********************/
    private void showDialogKudosSend() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_kudos_send);
        dialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report

            }
        }, 2000); // after 2 second (or 2000 miliseconds), the task will be active.

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }
    private void switchPresentDialog(){
        Dialog dialog=new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.swith_notification_present);
        dialog.setCanceledOnTouchOutside(true);
        TextView yes_text=dialog.findViewById(R.id.yes_text_view);
        TextView no_text=dialog.findViewById(R.id.no_text_view);
        no_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        Window window=dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(transparent);
        window.setGravity(Gravity.CENTER);
        lp.width=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
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



    public void showDateCalendar(TextView startDate){
        String newDate="";
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);

        try {

            //c.setTime(new SimpleDateFormat("MMM").parse("July"));
            int mMonth = c.get(Calendar.MONTH) ;
            //String mMonth = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day


            // date picker dialog
           DatePickerDialog datePickerDialog = new DatePickerDialog(Act_Home.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth){
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            long now = calendar.getTimeInMillis();
                            //view.setMinDate(now);
                            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                            String dateString = format.format(calendar.getTime());
                            startDate.setText(dateString);
                            //newDate= dateString;
                        }
                    }, mYear, mMonth, mDay);
            long now = c.getTimeInMillis();
            datePickerDialog.getDatePicker().setMinDate(now);
           datePickerDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
       // return newDate;
    }
    private void dialogEnableAbsent(){
        Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_enble_absent);
        dialog.setCanceledOnTouchOutside(true);

        TextView tv_enable= dialog.findViewById(R.id.tv_enable);
        tv_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                absentStatusChange();
                dialog.dismiss();
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

    private void dialogHappyIndexAbsent(){
        Dialog dialog=new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.kudos_absent_dialog);
        dialog.setCanceledOnTouchOutside(false);
        ImageView close_image=dialog.findViewById(R.id.close_image);

        CalendarView calendarView=dialog.findViewById(R.id.calendar_view);
        TextView submitDate=dialog.findViewById(R.id.show_submit_date_tv);

        calendarView.setMinDate(myCalendar.getTimeInMillis());
        Long currentDate=myCalendar.getTimeInMillis();
        SimpleDateFormat sdf = new SimpleDateFormat(" dd/MMM/yyyy HH:mm");
        Date resultdate = new Date(currentDate);
        String startDate=sdf.format(resultdate);
        Log.d("startDate:- ",startDate);
        //System.out.println(sdf.format(resultdate));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                //Calendar calendar = Calendar.getInstance();
                //int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
              // calendarView.setMaxDate(daysInMonth);
                String endDate= dayOfMonth+"/"+(month + 1)+"/"+ year;
                submitDate.setText(endDate);
            }
        });


        close_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        Window window=dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(transparent);
        window.setGravity(Gravity.CENTER);
        lp.width=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();

    }


    private void updateStartLabel(TextView date_tv){
        String myFormat= "MM/dd/yy";
        SimpleDateFormat sdf= new SimpleDateFormat(myFormat, Locale.US);
        date_tv.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateEndLabel(TextView date_tv){
        String myFormat="MM/dd/yy";
        SimpleDateFormat sdf=new SimpleDateFormat(myFormat,Locale.US);
        date_tv.setText(sdf.format(myCalendar.getTime()));
    }

    /***************************** Dialogs to show Engagement Score
     * @param todayEIScore*********************/
    private void dialogKudosSendResponse(String title,String todayEIScore) {
        try {
            Dialog dialog = new Dialog(Act_Home.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_kudos_send_response);
            //DialogKudosSendResponseBinding binding= DialogKudosSendResponseBinding.inflate(LayoutInflater.from(mContext));
            //dialog.setContentView(binding.getRoot());
            dialog.setCanceledOnTouchOutside(true);
            TextView dialogTitleTv=dialog.findViewById(R.id.dialog_title_tv);
            ImageView engImage=dialog.findViewById(R.id.eng_image);
            TextView engScoreTv=dialog.findViewById(R.id.eng_score_tv);


            Double indexEngScore = Double.valueOf(todayEIScore);
            if(title.contains("kudosSend")){
                dialogTitleTv.setText(getString(R.string.kudos_sent));
            }else{
                dialogTitleTv.setText(getString(R.string.sentiment_submit));
            }


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

           /* ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, engScore);
            valueAnimator.setDuration(8000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    eng_tv_score.setTranslationX((float)animation.getAnimatedValue());

                   // eng_tv_score.setText(valueAnimator +"");
                }
            });
            valueAnimator.setRepeatCount(5);
            valueAnimator.start();*/

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

                animateTextView(0, engScoreInt, engScoreTv, engImage,todayEIScore);
               // engScoreTv.setText(todayEIScore+".00");
            }catch (Exception e){
                e.printStackTrace();
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /********************************* Dialog to get Feedback when user not happy **************/
    private void dialogGetFeedback(String title,String todayEIScore) {
        try {
            if (mDialog!=null)
            {
                mDialog.dismiss();
            }
            mDialog = new Dialog(Act_Home.this);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.dialog_get_feedback_sentiment);
            //DialogGetFeedbackSentimentBinding binding=DialogGetFeedbackSentimentBinding.inflate(LayoutInflater.from(mContext));
            //mDialog.setContentView(binding.getRoot());
           // dialog.setCanceledOnTouchOutside(true);
            mDialog.setCancelable(true);
            if(mDialog!=null){
                mDialog.dismiss();
            }
            flag=true;

            TextView dialogTitleTv=mDialog.findViewById(R.id.dialog_title_tv);
            TextView engagmentIndex=mDialog.findViewById(R.id.engagment_index);
            LinearLayout feedbackScoreLl=mDialog.findViewById(R.id.feedback_score_ll);
            ImageView engImage=mDialog.findViewById(R.id.eng_image);
            TextView engScoreTv=mDialog.findViewById(R.id.eng_score_tv);
            EditText etComment=mDialog.findViewById(R.id.et_comment);
            TextView tvSave=mDialog.findViewById(R.id.tv_save);

            if (title.equals("")){
                dialogTitleTv.setText("Offloading");
                engagmentIndex.setVisibility(View.GONE);
                feedbackScoreLl.setVisibility(View.GONE);
            }else {
                dialogTitleTv.setText(getString(R.string.sentiment_submit));
            }
                tvSave.setOnClickListener(v->{
                    if (etComment.getText().toString().trim().isEmpty()) {
                        utility.showToast(mContext, getString(R.string.please_enter_offloading));
                        return;
                    }
                    if (!etComment.getText().toString().trim().equals("")){
                        String msg=etComment.getText().toString().trim();
                        mDialog.dismiss();
                        flag=false;
                        if (mDialog!=null){
                            mDialog.dismiss();
                        }
                        sendOffloading(msg);
                        if (mDialog!=null) {
                            mDialog.dismiss();
                        }
                        etComment.setText("");
                    }else {
                            utility.showToast(mContext, getString(R.string.please_enter_offloading));
                    }
                });


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = mDialog.getWindow();
            lp.copyFrom(window.getAttributes());
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            if (mDialog!=null){
                mDialog.dismiss();
            }

                mDialog.show();

            try {
                Double indexEngScore = Double.valueOf(todayEIScore);
                Float engScore = Float.valueOf(todayEIScore);

                //  Integer engScoreInt= Integer.parseInt(todayEIScore);
                try {
                    int engScoreInt = Math.round(engScore);
                    int startValue = 0;

                    if (engScoreInt > 800) {
                        startValue = 400;
                    } else if (engScoreInt > 600) {
                        startValue = 200;
                    } else if (engScore > 400) {
                        startValue = 100;
                    } else {
                        startValue = 0;
                    }

                    animateTextView(0, engScoreInt, engScoreTv,engImage,todayEIScore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*********** show Animation for Increise Score ***********/
    public void animateTextView(int initialValue, int finalValue, final TextView eng_tv_score, ImageView eng_image,String todayEIScore) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.8f);
        int start = Math.min(initialValue, finalValue);
        int end = Math.max(initialValue, finalValue);
        int difference = Math.abs(finalValue - initialValue);
        Handler handler = new Handler();
        Float engScore= Float.valueOf(todayEIScore);
        int newValue=Math.round(engScore);
        for (int count = start; count <= end; count++) {
            int time = Math.round(decelerateInterpolator.getInterpolation(((float) count  / difference)) * 100) * (count/10);
            int maxTime=(time-count);
            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            Log.d("Eng index value",finalCount+"");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if ( finalCount <= 499) {
                        eng_image.setImageResource(R.drawable.low);
                        //eng_tv_score.setText(indexEngScore+"");
                        //eng_tv_score.setText(todayEIScore + "");
                        eng_tv_score.setTextColor(getResources().getColor(R.color.motion_index_low));
                    } else if (finalCount >= 500 && finalCount <= 1099) {
                        eng_image.setImageResource(R.drawable.medium);
                        //tv_index_eng_number.setText(indexEngScore+"");
                        //eng_tv_score.setText(todayEIScore + "");
                        eng_tv_score.setTextColor(getResources().getColor(R.color.motion_index_medium));
                    } else if (finalCount >= 1100) {
                        eng_image.setImageResource(R.drawable.smile_green_big);
                        //tv_index_eng_number.setText(indexEngScore+"");
                        //eng_tv_score.setText(todayEIScore + "");
                        eng_tv_score.setTextColor(getResources().getColor(R.color.motion_index_high));
                    }
                    if ((finalCount)>(newValue-10)){
                        eng_tv_score.setText(todayEIScore);
                    }else {
                        eng_tv_score.setText(finalCount + ".00");
                    }
                }
            }, maxTime);

        }

    }
    /***************************** Amazing Dailog *********************/
    private void dialogAmazingShow(String nameTitle){
        final Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_amazing_layout);

        TextView amazingTitleTv= dialog.findViewById(R.id.amazing_title_tv);
        EditText etComment=dialog.findViewById(R.id.et_comment);
        Button submitButton=dialog.findViewById(R.id.submit_button);
        dialog.setCanceledOnTouchOutside(true);


        amazingTitleTv.setText( nameTitle+" Award");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment= etComment.getText().toString().trim();
                if (!comment.isEmpty()){
                    dialog.cancel();
                    amazingComment=comment;
                    individual_card_view_ll.setVisibility(View.VISIBLE);
                   // individual_card_view_ll.setVisibility(View.GONE);

                    iv_top_back.setVisibility(View.VISIBLE);
                    award_card_ll.setVisibility(View.GONE);
                    // iv_top_back_ll.setVisibility(View.VISIBLE);
                    kudos_recycler_view_ll.setVisibility(View.GONE);
                    if(!amazingFlag) {
                        kudosSelectionList = new ArrayList<>();
                        for (int i = 0; i < kudosList.size(); i++) {
                            if (kudosList.get(i).isSelected()) {
                                kudosSelectionList.add(kudosList.get(i));
                            }
                        }
                    }

                }else {
                    utility.showToast(mContext,"please enter comment first");
                }
            }
        });

        WindowManager.LayoutParams lp= new WindowManager.LayoutParams();
        Window window=dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(transparent);
        window.setGravity(Gravity.CENTER);
        lp.width= WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();

    }
    /*****************************  Dialogs to show Belief Value on filter*********************/
    private void showChangeDialog(List<HomeBelief> kudosList, Object object, int position) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_kudos_filter);
       /* DialogKudosFilterBinding binding=DialogKudosFilterBinding.inflate(LayoutInflater.from(mContext));
        dialog.setContentView(binding.getRoot());*/
        dialog.setCanceledOnTouchOutside(true);
         ArrayList<String> titleList= new ArrayList<>();
        titleList.add("Today");
        titleList.add("Yesterday");
        titleList.add("Last week");
        titleList.add("This week");
        titleList.add("Last month");
        titleList.add("This month");
        titleList.add("Total");
        ArrayList<String> titleTopList= new ArrayList<>();
        titleTopList.add("Kudos Values");
        titleTopList.add("Kudos Awards");

        final ImageView topLeftImageClick= dialog.findViewById(R.id.top_left_image_click);
        final ImageView topNextImageClick=dialog.findViewById(R.id.top_next_image_click);
        final TextView kudosTopTitleTv= dialog.findViewById(R.id.kudos_top_title_tv);

        final ImageView previousImageClick = dialog.findViewById(R.id.previous_image_click);
        final ImageView nextImageClick = dialog.findViewById(R.id.next_image_click);
        final TextView kudosTitleTv = dialog.findViewById(R.id.kudos_title_tv);
        final LinearLayout bottomLL= dialog.findViewById(R.id.bottom_ll);
        final TextView tvAmazingName= dialog.findViewById(R.id.tv_Amazing_name);
        final TextView tvAmazingValue= dialog.findViewById(R.id.tv_amazing_value);
        final TextView awardImage= dialog.findViewById(R.id.award_image);
         RecyclerView rvKudosFilter = dialog.findViewById(R.id.rv_kudos_filter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvKudosFilter.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        rvKudosFilter.addItemDecoration(itemDecoration);
        rvKudosFilter.addItemDecoration(new ItemOffsetDecoration(mContext,R.dimen.item_offset_small));
        //rv_list.getRecycledViewPool().setMaxRecycledViews(TYPE_CAROUSEL, 0);
        kudosTitleTv.setText(titleList.get(0));
        kudosTopTitleTv.setText(titleTopList.get(0));

        awardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext,Act_Award.class);
                startActivity(intent);
            }
        });

        setAmazingValue(tvAmazingValue,"Today",todayAwardCount,yesterdayAwardCount
                ,thisWeekAwardCount,lastWeekAwardCount,thisMonthAwardCount,lastMonthAwardCount,totalAwardCount);

        topNextImageClick.setOnClickListener(view ->{
            try{
                String titleTop=kudosTopTitleTv.getText().toString().trim();
                for (int i=0; i<titleTopList.size();i++){
                    if (titleTop.equals(titleTopList.get(i))){

                         if (i == (titleTopList.size()-1)) {
                            kudosTopTitleTv.setText(titleTopList.get(0));
                        } else {
                            kudosTopTitleTv.setText(titleTopList.get(i + 1));
                        }
                    }
                }
                String newTitle=kudosTitleTv.getText().toString().trim();
                String topTitleString=kudosTopTitleTv.getText().toString().trim();
                if(topTitleString.equals("Kudos Awards")){
                    bottomLL.setVisibility(View.VISIBLE);
                    tvAmazingName.setText("Amazing");
                    tvAmazingValue.setText(amazingValue + "");
                    awardImage.setVisibility(View.VISIBLE);
                    setAmazingValue(tvAmazingValue,newTitle,todayAwardCount,yesterdayAwardCount
                            ,thisWeekAwardCount,lastWeekAwardCount,thisMonthAwardCount,lastMonthAwardCount,totalAwardCount);

                }else {
                    bottomLL.setVisibility(View.GONE);
                    awardImage.setVisibility(View.GONE);
                }
                Ad_home_belief_filter belief_filter= new Ad_home_belief_filter(kudosList, mContext, newTitle, topTitleString, new todayCountListener() {
                    @Override
                    public void awardDescription() {

                    }
                });
                rvKudosFilter.setAdapter(belief_filter);
                belief_filter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        topLeftImageClick.setOnClickListener(view ->{
            try {
                String titleTop=kudosTopTitleTv.getText().toString().trim();
                for (int i=0; i<titleTopList.size();i++){
                    if (titleTop.equals(titleTopList.get(i))){


                        if (i == 0) {
                            kudosTopTitleTv.setText(titleTopList.get(titleTopList.size()-1));
                        } else {
                            kudosTopTitleTv.setText(titleTopList.get(i - 1));
                        }
                    }
                }

                String newTitle=kudosTitleTv.getText().toString().trim();
                String topTitleString=kudosTopTitleTv.getText().toString().trim();
                if(topTitleString.equals("Kudos Awards")){
                    bottomLL.setVisibility(View.VISIBLE);
                    tvAmazingName.setText("Amazing");
                    tvAmazingValue.setText(amazingValue+ "");
                    awardImage.setVisibility(View.VISIBLE);
                    setAmazingValue(tvAmazingValue,newTitle,todayAwardCount,yesterdayAwardCount
                            ,thisWeekAwardCount,lastWeekAwardCount,thisMonthAwardCount,lastMonthAwardCount,totalAwardCount);

                }else {
                    bottomLL.setVisibility(View.GONE);
                    awardImage.setVisibility(View.GONE);
                }
                Ad_home_belief_filter belief_filter= new Ad_home_belief_filter(kudosList, mContext, newTitle, topTitleString, new todayCountListener() {
                    @Override
                    public void awardDescription() {

                    }
                });
                rvKudosFilter.setAdapter(belief_filter);
                belief_filter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        previousImageClick.setOnClickListener(view -> {
            String getTitle=kudosTitleTv.getText().toString().trim();
            try {
                for (int i = 0; i < titleList.size(); i++) {
                    if (getTitle.equals(titleList.get(i))) {
                      if (i == 0) {
                            kudosTitleTv.setText(titleList.get(titleList.size()-1));
                        } else {
                            kudosTitleTv.setText(titleList.get(i - 1));
                        }

                    }
                }
                String newTitle=kudosTitleTv.getText().toString().trim();
                String topTitleString=kudosTopTitleTv.getText().toString().trim();
                if(topTitleString.equals("Kudos Awards")){
                    bottomLL.setVisibility(View.VISIBLE);
                    tvAmazingName.setText("Amazing");
                    tvAmazingValue.setText(amazingValue+"");
                    awardImage.setVisibility(View.VISIBLE);
                    setAmazingValue(tvAmazingValue,newTitle,todayAwardCount,yesterdayAwardCount
                            ,thisWeekAwardCount,lastWeekAwardCount,thisMonthAwardCount,lastMonthAwardCount,totalAwardCount);

                }else {
                    bottomLL.setVisibility(View.GONE);
                    awardImage.setVisibility(View.GONE);
                }
                Ad_home_belief_filter belief_filter = new Ad_home_belief_filter(kudosList, mContext, newTitle, topTitleString, new todayCountListener() {
                    @Override
                    public void awardDescription() {

                    }
                });
                rvKudosFilter.setAdapter(belief_filter);
                belief_filter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        nextImageClick.setOnClickListener(v->{
            String getTitle=kudosTitleTv.getText().toString().trim();
            try {
                for (int i = 0; i < titleList.size(); i++) {
                    if (getTitle.equals(titleList.get(i))) {
                        if (i == (titleList.size()-1)) {
                            kudosTitleTv.setText(titleList.get(0));
                        } else {
                            kudosTitleTv.setText(titleList.get(i + 1));
                        }
                    }
                }
                String topTitleString= kudosTopTitleTv.getText().toString().trim();
                String newTitle=kudosTitleTv.getText().toString().trim();
                Ad_home_belief_filter belief_filter = new Ad_home_belief_filter(kudosList, mContext, newTitle, topTitleString, new todayCountListener() {
                    @Override
                    public void awardDescription() {

                    }
                });
                rvKudosFilter.setAdapter(belief_filter);
                belief_filter.notifyDataSetChanged();
                if(topTitleString.equals("Kudos Awards")){
                    bottomLL.setVisibility(View.VISIBLE);
                    tvAmazingName.setText("Amazing");
                    tvAmazingValue.setText(amazingValue+"");
                    awardImage.setVisibility(View.VISIBLE);
                    setAmazingValue(tvAmazingValue,newTitle,todayAwardCount,yesterdayAwardCount
                            ,thisWeekAwardCount,lastWeekAwardCount,thisMonthAwardCount,lastMonthAwardCount,totalAwardCount);

                }else {
                    bottomLL.setVisibility(View.GONE);
                    awardImage.setVisibility(View.GONE);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        });
        String getTitle=kudosTitleTv.getText().toString().trim();
        String topTitleString= "Kudos Values";

        Ad_home_belief_filter belief_filter = new Ad_home_belief_filter(kudosList, mContext, getTitle, topTitleString, new todayCountListener() {
            @Override
            public void awardDescription() {
            }
        });
        rvKudosFilter.setAdapter(belief_filter);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }else{
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }

        window.setAttributes(lp);
        dialog.show();
    }
    private void setAmazingValue(final TextView amazing_value_TV, String showText,
                                 int todayAwardCount, int yesterdayAwardCount, int thisWeekAwardCount,
                                 int lastWeekAwardCount, int thisMonthAwardCount, int lastMonthAwardCount,
                                 int totalAwardCount) {
        try{
            int amazingValue=0;
            int position=0;
            if (showText.equals("Today")) {
               // amazing_value_TV.setText(todayAwardCount + "");
                amazingValue=todayAwardCount;
            } else if (showText.equals("Yesterday")) {
               // amazing_value_TV.setText(yesterdayAwardCount + "");
                amazingValue=yesterdayAwardCount;
            } else if (showText.equals("Last week")) {
                //amazing_value_TV.setText(lastWeekAwardCount + "");
                amazingValue=lastWeekAwardCount;

            } else if (showText.equals("This week")) {
                //amazing_value_TV.setText(thisWeekAwardCount + "");
                amazingValue=thisWeekAwardCount;

            } else if (showText.equals("Last month")) {
                //amazing_value_TV.setText(lastMonthAwardCount + "");
                amazingValue=lastMonthAwardCount;
            } else if (showText.equals("This month")) {
                //amazing_value_TV.setText(thisMonthAwardCount + "");
                amazingValue=thisMonthAwardCount;
            } else if (showText.equals("Total")) {
                //amazing_value_TV.setText(totalAwardCount);
                amazingValue=totalAwardCount;
            }
            amazing_value_TV.setText(String.valueOf(amazingValue));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getDialogTop(String kudosId,String kudosValue){

        final  Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_award_msg_list);
        //DialogAwardMsgListBinding binding=DialogAwardMsgListBinding.inflate(LayoutInflater.from(mContext));
        //dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);

         final TextView awardTitleTv= dialog.findViewById(R.id.award_title_tv);
         RecyclerView awardRv= dialog.findViewById(R.id.award_rv);
         ImageView cancelButton=dialog.findViewById(R.id.cancel_button);

         awardTitleTv.setText(kudosValue);
         cancelButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialog.cancel();
             }
         });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        awardRv.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        awardRv.addItemDecoration(itemDecoration);
        awardRv.addItemDecoration(new ItemOffsetDecoration(mContext,R.dimen.item_offset_small));
        Ad_award_list adAwardList= new Ad_award_list(awardList,mContext);
        awardRv.setAdapter(adAwardList);
        WindowManager.LayoutParams lp= new WindowManager.LayoutParams();
        Window window=dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();

    }


    /***************************** handle Adapter for filter Kudos count and show Today-This month  *********************/
    //Adapter for indi dialog add user list
    public class Ad_home_belief_filter extends RecyclerView.Adapter<Ad_home_belief_filter.ViewHolder> {
       List<HomeBelief> list;
       Context context;
       private final String showText;
       private final String topTitle;
       public todayCountListener mListener;


        public Ad_home_belief_filter(List<HomeBelief> list, Context context,final String getTitle,final String topTitleString ,todayCountListener mListener) {
            this.list = list;
            filteredUserList = list;
            notifyDataSetChanged();
            this.context = context;
            this.topTitle=topTitleString;
            this.showText=getTitle;
            this.mListener=mListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(mContext);
            RowBeliefFilterListBinding binding=RowBeliefFilterListBinding.inflate(inflater,parent,false);
           // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_belief_filter_list, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            try {


                holder.setIsRecyclable(false);
                holder.itemBinding.tvBeliefName.setText(filteredUserList.get(position).getName());
                //holder.tv_name.setText(filteredUserList.get(position).getName());
                if (topTitle.equals("Kudos Values")) {
                    if (position == (filteredUserList.size() - 1)) {
                        holder.itemBinding.tvBeliefName.setVisibility(View.GONE);
                        holder.itemBinding.tvTodayValue.setVisibility(View.GONE);
                        holder.itemBinding.baseline.setVisibility(View.GONE);
                    }
                    if (filteredUserList.get(position).getName().equals("Amazing")) {
                        holder.itemBinding.tvBeliefName.setVisibility(View.GONE);
                        holder.itemBinding.tvTodayValue.setVisibility(View.GONE);
                    } else {
                        holder.itemBinding.tvBeliefName.setVisibility(View.VISIBLE);
                        holder.itemBinding.tvTodayValue.setVisibility(View.VISIBLE);
                    }
                    if (position >= 7) {
                        holder.itemBinding.tvBeliefName.setVisibility(View.GONE);
                        holder.itemBinding.tvTodayValue.setVisibility(View.GONE);
                    } else {
                        holder.itemBinding.tvBeliefName.setVisibility(View.VISIBLE);
                        holder.itemBinding.tvTodayValue.setVisibility(View.VISIBLE);
                    }
                    if (showText.equals("Today")) {
                        holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getTodayKudosCount() + "");
                    } else if (showText.equals("Yesterday")) {
                        holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getYesterdayKudosCount() + "");
                    } else if (showText.equals("Last week")) {
                        holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getLastWeekKudosCount() + "");
                    } else if (showText.equals("This week")) {
                        holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getThisWeekKudosCount() + "");
                    } else if (showText.equals("Last month")) {
                        holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getLastMonthKudosCount() + "");
                    } else if (showText.equals("This month")) {
                        holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getThisMonthKudosCount() + "");
                    } else if (showText.equals("Total")) {
                        try {
                            holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getTotalKudosCount().toString() + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        holder.itemBinding.tvBeliefName.setVisibility(View.VISIBLE);
                        holder.itemBinding.tvTodayValue.setVisibility(View.VISIBLE);
                        holder.itemBinding.tvBeliefName.setText(filteredUserList.get(position).getName());
                        if (showText.equals("Today")) {
                            holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getTodayDotValueKudoAwardCount() + "");
                        } else if (showText.equals("Yesterday")) {
                            holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getYesterdayDotValueKudoAwardCount() + "");
                        } else if (showText.equals("Last week")) {
                            holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getLastWeekDotValueKudoAwardCount() + "");
                        } else if (showText.equals("This week")) {
                            holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getThisWeekDotValueKudoAwardCount() + "");
                        } else if (showText.equals("Last month")) {
                            holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getLastMonthDotValueKudoAwardCount() + "");
                        } else if (showText.equals("This month")) {
                            holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getThisMonthDotValueKudoAwardCount() + "");
                        } else if (showText.equals("Total")) {
                            // if (filteredUserList.get(position).getTodayDotValueKudoAwardCount()>0) {
                            holder.itemBinding.tvTodayValue.setText(filteredUserList.get(position).getTotalDotValueKudoAwardCount().toString());
                       /* }else {
                            holder.tv_value.setText("0");
                        }*/
                        }
                        if (position == (filteredUserList.size() - 1)) {
                            holder.itemBinding.baseline.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                holder.itemBinding.llMain.setOnClickListener(v -> {
               /* if (topTitle.equals("Kudos Awards")){
                    String kudosId=filteredUserList.get(position).getId()+"";
                    String positionValue=filteredUserList.get(position).getName();
                   // getDialogTop(kudosId);
                    kudoAwardDetail(kudosId,positionValue);
                }*/
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {

            try {
                return filteredUserList.size();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        filteredUserList = list;
                    } else {
                        ArrayList<HomeBelief> userList = new ArrayList<>();
                        for (HomeBelief department : list) {
                            if (department.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                    department.getName().toUpperCase().contains(charString.toUpperCase())) {
                                userList.add(department);
                            }
                        }
                        filteredUserList = userList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredUserList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filteredUserList = (ArrayList<HomeBelief>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name,tv_value;
            LinearLayout ll_main;
            View baseLine;
           private RowBeliefFilterListBinding itemBinding;

            public ViewHolder(RowBeliefFilterListBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding=itemBinding;

            }
        }


    }

    /*method to check the time span between 4 pm to 11 pm
     */
    public boolean checkTime() {
        try {
            String string1 = "16:00:00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            //calendar1.add(Calendar.DATE, 1);
            String string2 = "23:59:00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            //calendar2.add(Calendar.DATE, 1);
            String someRandomTime = "01:00:00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            //calendar3.add(Calendar.DATE, 1);
            //Date x = calendar3.getTime();
            Calendar currTime = Calendar.getInstance();
            int hour = currTime.get(Calendar.HOUR_OF_DAY);
            int minut = currTime.get(Calendar.MINUTE);
            Float time = Float.parseFloat(hour + "." + minut);
            // Float time = Float.parseFloat(currTime.get(Calendar.HOUR_OF_DAY)+"."+currTime.get(Calendar.MINUTE));

            Date x = Calendar.getInstance().getTime();
//            int starttime= hour-1;
//            int endtime=hour+1;
            // Toast.makeText(activity, "true in time", Toast.LENGTH_SHORT).show();
            //Toast.makeText(activity, "not in time \nstarttime "+time +"endtime "+time, Toast.LENGTH_SHORT).show();
            return (time >= 16) && (time < 23.59);
//            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
//                //checkes whether the current time is between 14:49:00 and 20:11:13.
//                //Toast.makeText(activity, "true in time", Toast.LENGTH_SHORT).show();
//                return true;
//            }else{
//                //Toast.makeText(activity, "not in time", Toast.LENGTH_SHORT).show();
//                return false;
//            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }



    /******************************* handel back Press **************************/

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
         if (R.id.nav_home != seletedItemId) {
            setHomeItem(Act_Home.this);
        } else {
            //super.onBackPressed();
            try {
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.Are_you_sure_you_want_to_exit))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finishAllActivities();
                            }
                        })
                        .setNegativeButton(getString(R.string.No), null)
                        .show();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        //grid.setNumColumns(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;
        recreate();

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Do some stuff
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            rv_circle_value.setLayoutManager(gridLayoutManager);
            rv_circle_value.setNestedScrollingEnabled(false);
            rv_circle_value.setHasFixedSize(true);
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
            rv_circle_value.addItemDecoration(itemDecoration);
            navigationView.setVisibility(View.VISIBLE);
        }else{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            rv_circle_value.setLayoutManager(gridLayoutManager);
            rv_circle_value.setHasFixedSize(true);
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
            rv_circle_value.addItemDecoration(itemDecoration);
            recreate();
        }


    }

    public interface todayCountListener{
        void awardDescription();
    }
    public void changePosition(View view){
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        view.setLayoutParams(params);

    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        //if (!isTablet) {
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                p.setMargins(left, top, right, bottom);
                view.requestLayout();
            }
        //}
    }
    public void checkAwardBottom(LatestKudoAward latestKudoAward){
        try {
            //if (latestKudoAward!=null)
            if (latestKudoAward !=null && !latestKudoAward.getAwardValue().isEmpty() && latestKudoAward.getAwardValue()!=null) {
                award_card_ll.setVisibility(View.VISIBLE);
                award_tv_space.setVisibility(View.VISIBLE);

                kudos_description_tv.setText(Html.fromHtml(latestKudoAward.getAwardDescription()));
                // kudos_description_tv.setText(latestKudoAward.getAwardDescription());
                if (latestKudoAward.getKudoAwardCount() > 0) {
                    kudos_value_tv.setText(latestKudoAward.getAwardValue() + " - " + latestKudoAward.getUserName() + " & " + latestKudoAward.getKudoAwardCount() + " more");
                    Glide.with(mContext)
                            .load(R.drawable.group_award)
                            .placeholder(R.drawable.group_award)
                            .into(user_image_kudos);

                } else {
                    kudos_value_tv.setText(latestKudoAward.getAwardValue() + " - " + latestKudoAward.getUserName());
                    Glide.with(mContext)
                            .load(latestKudoAward.getUserImage())
                            .placeholder(R.drawable.user_circle)
                            .into(user_image_kudos);

                }
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    setMargins(fb_select_kudos_next, 8, 8, 48, 450);
                }else {
                    setMargins(fb_select_kudos_next, 8, 8, 48, 380);
                }
                String newDate = latestKudoAward.getAwardDate();
                kudos_award_date.setText(utility.getDate(newDate));

                //if(countValue)
                //String image_url="https://console.tribe365.co/public/uploads/user_profile/user_1610001900.png";
                //Picasso.get().load(latestKudoAward.getUserImage()).into(user_image_kudos);

            } else {
                award_card_ll.setVisibility(View.GONE);
                award_tv_space.setVisibility(View.GONE);
                //if (!isTablet)
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    setMargins(fb_select_kudos_next, 8, 8, 48, 280);
                }else {
                    setMargins(fb_select_kudos_next, 8, 8, 48, 80);
                }
            }
        }catch (Exception e){
            award_card_ll.setVisibility(View.GONE);
            e.printStackTrace();
            if (!isTablet){
                setMargins(fb_select_kudos_next, 8, 8, 48, 80);
            }
        }
    }

    /************************** set view on BaseActivity layout *********************/
    @Override
    public int getLayoutId() {
        return R.layout.act_home_land;
    }

    /******************* set navigation view ****************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }

}