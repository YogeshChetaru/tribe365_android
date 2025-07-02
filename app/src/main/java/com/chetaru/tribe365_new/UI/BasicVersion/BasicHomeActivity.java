package com.chetaru.tribe365_new.UI.BasicVersion;

import static com.chetaru.tribe365_new.R.color.transparent;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.BeliefValue;
import com.chetaru.tribe365_new.API.Models.Department;
import com.chetaru.tribe365_new.API.Models.Home.LatestKudoAward;
import com.chetaru.tribe365_new.API.Models.KnowHome.KudosAwardList;
import com.chetaru.tribe365_new.API.Models.MemberHome.LatestKudosAward;
import com.chetaru.tribe365_new.API.Models.ModelDepartmentDialog;
import com.chetaru.tribe365_new.API.Models.User;
import com.chetaru.tribe365_new.API.Models.basicVersion.BasicHomeDetail;
import com.chetaru.tribe365_new.API.Models.freeVersion.HappyIndexMonthly;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_Individual;
import com.chetaru.tribe365_new.Adapter.KnowHome.Ad_kudos_awards_list;
import com.chetaru.tribe365_new.Adapter.OnItemClickCustom;
import com.chetaru.tribe365_new.Adapter.award.Ad_AwardUserList;
import com.chetaru.tribe365_new.Adapter.freeVersion.Ad_HICalender;
import com.chetaru.tribe365_new.Notification.Act_NotificationList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.DismissType;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.orientation;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.GuideView;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Listener.GuideListener;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.HPTM.Act_HPTM_main;
import com.chetaru.tribe365_new.UI.Home.Act_AwardDescriptions;
import com.chetaru.tribe365_new.UI.Home.Act_FreeVersionHome;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.UI.Know.Act_Award_List;
import com.chetaru.tribe365_new.UI.Offloading.Act_IOTHome;
import com.chetaru.tribe365_new.UI.Risk.Act_RiskHome;
import com.chetaru.tribe365_new.utility.DoneOnEditorActionListener;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.MyCircleImageView;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BasicHomeActivity extends BaseActivity  {

    /************************************ creating Variable for handle instance **********/
    SessionParam sessionParam;
    Utility utility;
    private BaseRequest baseRequest;
    String orgId;
    int amazingValue=0;
    ModelDepartmentDialog modelDepartmentDialog;
    List<User> resetUserlist;
    List<Department> resetDepartmentsList;
    List<User> userList;

    boolean dailyNotiStatus= false;
    int individualCount=0;
    boolean flag=false;
    boolean userSelect=false;
    BasicHomeDetail homeDetail = new BasicHomeDetail();
    boolean clickableFlag= false;
    int introType=0;
    private GuideView mGuideView;
    boolean isTablet;
    boolean amazingFlag=false;
    String comment="";
    Ad_kudos_awards_list ad_kudosAward;
    List<LatestKudosAward> kudosAwardList= new ArrayList<>();
    ArrayList<HappyIndexMonthly> calenderList;
    Ad_HICalender ad_calenderView;

    /*********************** initialize layout View ***************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_ll)
    LinearLayout main_ll;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.absent_card_view)
    CardView absent_card_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.index_eng_image)
    ImageView index_eng_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_index_eng_number)
    TextView tv_index_eng_number;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_comment)
    EditText et_comment;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.next_button)
    Button next_button;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_action_right)
    TextView tv_action_right;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_dot_value)
    TextView amazing_dot_value;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sentiment_index_title)
    TextView sentiment_index_title;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.calender_card_view)
    CardView calender_card_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.calender_recycler_view)
    RecyclerView calender_recycler_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_empty_msg)
    TextView show_empty_msg;

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

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_kudos_awards)
    RecyclerView rv_kudos_awards;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_no_record_found_awards)
    TextView tv_no_record_found_awards;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.card_amazing_ll)
    CardView card_amazing_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_tv_name)
    TextView amazing_tv_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_bottom_dot_value)
    TextView amazing_bottom_value;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_value_card_ll)
    CardView amazing_value_card_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_award_list)
    TextView title_award_list;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.award_list_card)
    CardView award_list_card;

    /********************** new Amazing CardView ******************/
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
    @BindView(R.id.award_value_ll)
    LinearLayout award_value_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.amazing_award_count_tv)
    TextView amazing_award_count_tv;





    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.user_frame_layout)
    FrameLayout user_frame_layout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.user_card)
    CardView user_card;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.user_selection_rl)
    RelativeLayout user_selection_rl;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_notic)
    ImageView noti_bell_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_notiCount)
    TextView circle_count_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_companylogo)
    ImageView iv_top_companylogo;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.on_focus_tv)
    TextView on_focus_tv;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.search_bar)
    SearchView searchBar;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_user_selection)
    RecyclerView rv_userList;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.submit_msg_button)
    Button submit_msg_button;
    Ad_Individual ad_individual;

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
    TextView kudosValueTv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.kudos_award_date)
    TextView kudos_award_date;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.well_app_tour)
    TextView well_app_tour;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.award_app_tour_ll)
    LinearLayout award_app_tour_ll;

    /********
     * 1) onCreate section
     * 2) set view
     * 3) Api calling
     * 4) Dialog
     * 5) App Tour
     * 6) navigation
     * ************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        sessionParam=new SessionParam(mContext);
        utility=new Utility();
        resetUserlist = new ArrayList<>();
        kudosAwardList = new ArrayList<>();
        calenderList= new ArrayList<>();
        userList= new ArrayList<>();
        orgId=sessionParam.orgId;
        getSessionParam();
        getUserSelectionView();
        getCalenderSentiment();
        KudosAwardsList();
        /********** for Notification enable status ************/
        updateNotificationStatus();

        /**************** Api calling Section ***************/
        getUserList();
        getHomePageDetail();
        api_getNotificationCount();
        iv_top_back.setVisibility(View.GONE);
        card_happy_index_ll.setVisibility(View.GONE);
        sentiment_index_title.setVisibility(View.GONE);
        calender_card_view.setVisibility(View.GONE);
        card_amazing_ll.setVisibility(View.GONE);
        award_list_card.setVisibility(View.GONE);
        title_award_list.setVisibility(View.GONE);

        et_comment.setOnEditorActionListener(new DoneOnEditorActionListener());
        et_comment.setImeOptions(EditorInfo.IME_ACTION_DONE);

        /************* check current working orientation ********/
        isTablet=getResources().getBoolean(R.bool.isTablet);

        /************************ App Tour *****************/
        int getIntroType = getIntent().getIntExtra("appTourType", 0);
         if (getIntroType!=0){
            introType=getIntroType;
        }
         /************ show Intro ********************/
         if (introType == 1) {
             ShowIntro(getString(R.string.wellcome_ppt), R.id.well_app_tour, 1);
         }else if (introType == 8){
            ShowIntro(getString(R.string.profile_ppt), R.id.nav_profile, 10);
        }else if (introType > 8) {
            ShowIntro(getString(R.string.thanks_ppt), R.id.well_app_tour, 13);
        }
        //user_selection_rl.setEnabled(false);

        noti_bell_image.setOnClickListener(v -> startActivity(new Intent(BasicHomeActivity.this, Act_NotificationList.class)));
         iv_top_back.setOnClickListener(v -> {
             iv_top_back.setVisibility(View.GONE);
             sentiment_index_title.setVisibility(View.VISIBLE);
             calender_card_view.setVisibility(View.VISIBLE);
             card_amazing_ll.setVisibility(View.VISIBLE);
             award_list_card.setVisibility(View.VISIBLE);
             title_award_list.setVisibility(View.VISIBLE);
             user_frame_layout.setVisibility(View.GONE);
             searchBar.setQuery("", false);
         });


        tv_action_right.setOnClickListener(v -> {
            Intent hptm_intent=new Intent(mContext, Act_HPTM_main.class);
            hptm_intent.putExtra("backHandel","Home");
            startActivity(hptm_intent);
        });
        submit_msg_button.setOnClickListener(v -> {
            if (!comment.equals("")){
                submit_msg_button.setEnabled(false);
                userSelect= false;
                searchBar.setQuery("", false);
                for (int i=0;i<userList.size();i++){
                    if (userList.get(i).isSelected())
                        userSelect= true;
                }
                if (userSelect) {
                    sendAmazingAward(comment);
                }else {
                    utility.showToast(mContext,"Please select user first");
                    submit_msg_button.setEnabled(true);
                }
            }else {
                utility.showToast(mContext,"Please enter comment first");
                submit_msg_button.setEnabled(true);
            }
        });

        award_card_ll.setOnClickListener(v -> {
            Intent intent= new Intent(BasicHomeActivity.this, Act_Award_List.class);
            startActivity(intent);
        });
        main_ll.setOnClickListener(v -> setupUI(main_ll));
        award_app_tour_ll.setOnClickListener(v-> setupUI(award_app_tour_ll));
        absent_card_view.setOnClickListener(v -> dialogEnableAbsent());
        absent_Txt.setOnClickListener(v -> dialogAbsent());

    }



    private void getCalenderSentiment() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 7);
        calender_recycler_view.setLayoutManager(layoutManager);
        calender_recycler_view.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_small);
        calender_recycler_view.addItemDecoration(itemDecoration);
        calender_recycler_view.setNestedScrollingEnabled(true);
    }

    /************************** OnCreate section Close *************************/

    @Override
    protected void onResume() {
        super.onResume();
        api_getNotificationCount();
    }

    /************************* resetUser Lit ************************/
    private void resetUserList(boolean clickableFlag) {
        clickableFlag= true;
        ad_individual=new Ad_Individual(clickableFlag,userList, BasicHomeActivity.this, new OnItemClickCustom() {
            @Override
            public void onClick(int id, int position, Object object) {
                individualCount=0;
                for (int i=0;i<userList.size();i++){
                    if (userList.get(i).isSelected()){
                        individualCount++;
                        break;
                    }
                }
            }
            @Override
            public void dotValueLongClick(BeliefValue beliefValue) {
            }
        });
        rv_userList.setAdapter(ad_individual);
    }
    public void setAwardList(){
        ad_kudosAward =new Ad_kudos_awards_list(kudosAwardList, mContext, new Ad_kudos_awards_list.groupUsersListener() {
            @Override
            public void groupUser(String awardValue,String desc, List<KudosAwardList> userList) {

                groupUserDialog(awardValue,desc,userList);
            }
        });
        rv_kudos_awards.setAdapter(ad_kudosAward);
    }
    private void groupUserDialog(String awardValue,String description, List<KudosAwardList> userList) {
        final Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_award_user_list);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        final TextView iv_top_previous= dialog.findViewById(R.id.award_name_tv);
        final TextView description_tv= dialog.findViewById(R.id.award_desc_tv);
        RecyclerView award_list= dialog.findViewById(R.id.award_rv);

        iv_top_previous.setText(awardValue);
        try {
            description_tv.setText(description);
        }catch (Exception e){
            e.printStackTrace();
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        award_list.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        award_list.addItemDecoration(itemDecoration);
        award_list.addItemDecoration(new ItemOffsetDecoration(mContext,R.dimen.item_offset_small));
        List<String> personList = new ArrayList<>();
        Ad_AwardUserList adAwardUserList = new Ad_AwardUserList(awardValue, userList, personList, mContext);
        award_list.setNestedScrollingEnabled(false);

        award_list.setAdapter(adAwardUserList);
        WindowManager.LayoutParams lp= new WindowManager.LayoutParams();
        Window window=dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    /*************************** Enable or Disable View click section ******************/
    private void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    /*********** getSessionParam **************/
    private void getSessionParam() {
        try {

            if (orgId.equals("")) {
                orgId = sessionParam.orgId;
            }
            Picasso.get().load(sessionParam.organisation_logo).placeholder(R.drawable.icon_tribe365).into(iv_top_companylogo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void KudosAwardsList(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(mContext);
        rv_kudos_awards.setLayoutManager(layoutManager);
        rv_kudos_awards.setHasFixedSize(true);
        rv_kudos_awards.setNestedScrollingEnabled(false);
        Drawable mDivider= ContextCompat.getDrawable(this,R.drawable.divider);
        DividerItemDecoration hItemDecoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        assert mDivider !=null;
        hItemDecoration.setDrawable(mDivider);
    }
    /******************** set user View ***************/
    public void getUserSelectionView(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        rv_userList.setLayoutManager(layoutManager);
        rv_userList.setHasFixedSize(true);
        Drawable mDivider= ContextCompat.getDrawable(this,R.drawable.divider);
        DividerItemDecoration hItemDecoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        assert mDivider !=null;
        hItemDecoration.setDrawable(mDivider);

        rv_userList.setNestedScrollingEnabled(false);
        searchBar.setIconifiedByDefault(false);
        // Clear the text in search bar but (don't trigger a new search!)
        searchBar.setQuery("", false);
        try{
            searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                    return false;
                }
            });
            rv_userList.addItemDecoration(new DividerItemDecoration(rv_userList.getContext(),DividerItemDecoration.VERTICAL));

        }catch (Exception e){e.printStackTrace();}

    }
    /******************** set EngScore ***************/
    @SuppressLint("SetTextI18n")
    private void indexEngScoreData(String EngValue){
        double indexEngScore= Double.parseDouble(EngValue);
        String showEngValue=trimTrailingZeros(EngValue);
        if (indexEngScore<=299){
            index_eng_image.setImageResource(R.drawable.low);
            tv_index_eng_number.setText(showEngValue + "");
            tv_index_eng_number.setTextColor(getResources().getColor(R.color.motion_index_low));
        }else if (indexEngScore >= 300 && indexEngScore < 800 ){

            index_eng_image.setImageResource(R.drawable.medium);
            tv_index_eng_number.setText(showEngValue+"");
            tv_index_eng_number.setTextColor(getResources().getColor(R.color.motion_index_medium));
        }else if (indexEngScore>=800){
            index_eng_image.setImageResource(R.drawable.smile_green_big);
            tv_index_eng_number.setText(showEngValue+"");
            tv_index_eng_number.setTextColor(getResources().getColor(R.color.motion_index_high));
        }
        ValueAnimator valueAnimator= ValueAnimator.ofFloat(150,600.0f);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv_index_eng_number.setText(showEngValue+"");
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
    /********************* hide keyboard outside click ******************/
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(BasicHomeActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    /******************** show Happy Index sentiment ***************/
    private void getHappyIndexBlink(boolean dailyNotiStatus,Integer leaveStatus){
        if (leaveStatus!= 1) {
            if (!dailyNotiStatus && leaveStatus != 1) {
                if (utility.checkTime()) {
                    card_happy_index_ll.setVisibility(View.VISIBLE);
                    happyEmoji.setOnClickListener(v -> api_addHappyIndex("3"));
                    neutralEmoji.setOnClickListener(v -> api_addHappyIndex("2"));
                    sadEmoji.setOnClickListener(v -> api_addHappyIndex("3"));
                } else {
                    card_happy_index_ll.setVisibility(View.GONE);
                }
            } else {
                card_happy_index_ll.setVisibility(View.GONE);
            }
        }else {
            card_happy_index_ll.setVisibility(View.GONE);
        }
    }

    /***************************************** Api Calling Section *********************/
    public void getHomePageDetail(){
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    Gson gson=new Gson();
                    iv_top_back.setVisibility(View.GONE);
                    sentiment_index_title.setVisibility(View.VISIBLE);
                    calender_card_view.setVisibility(View.VISIBLE);
                    card_amazing_ll.setVisibility(View.VISIBLE);
                    award_list_card.setVisibility(View.VISIBLE);
                    title_award_list.setVisibility(View.VISIBLE);
                    user_frame_layout.setVisibility(View.GONE);

                    homeDetail = gson.fromJson(object.toString(), BasicHomeDetail.class);
                    try{
                        dailyNotiStatus = homeDetail.isUserGivenfeedback();
                        if (homeDetail.getAppPaymentVersion()==1){
                            Intent intent = new Intent(mContext, Act_FreeVersionHome.class);
                            startActivity(intent);
                            finish();
                        }else if (homeDetail.getAppPaymentVersion() ==2){
                            Intent intent = new Intent(mContext, Act_Home.class);
                            startActivity(intent);
                            finish();
                        }
                        sessionParam.saveFreeVersion(mContext,homeDetail.getAppPaymentVersion());
                        getHappyIndexBlink(dailyNotiStatus,homeDetail.getLeaveStatus());
                        try {
                            amazingValue=homeDetail.getKudoAwardValue();
                            String awardName=homeDetail.getKudoAwardKey();
                            amazing_dot_value.setText(amazingValue+"");
                           getAmazingCard(awardName,amazingValue);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            indexEngScoreData( homeDetail.getTodayEIScore());

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


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        List<HappyIndexMonthly> happyIndexMonthly= homeDetail.getHappyIndexMonthly();
                        //String monthStartDay= homeDetail.getFirstDayOfMonth();
                        if (happyIndexMonthly.size()>0) {
                            calender_recycler_view.setVisibility(View.VISIBLE);
                            show_empty_msg.setVisibility(View.GONE);
                            calenderList.clear();
                            setCalenderList(homeDetail.getHappyIndexMonthly(), homeDetail.getFirstDayOfMonth(),homeDetail.getNotWorkingDays());
                        }else {
                            calender_recycler_view.setVisibility(View.GONE);
                            show_empty_msg.setVisibility(View.VISIBLE);

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        List<LatestKudoAward> newKudos= homeDetail.getLatestKAward();
                        if (newKudos.size()>0){
                           // checkAwardBottom(newKudos.get(0));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                       kudosAwardList =homeDetail.getTopKudosAward();
                       if (kudosAwardList.size()>0){
                           setAwardList();
                           tv_no_record_found_awards.setVisibility(View.GONE);
                           rv_kudos_awards.setVisibility(View.VISIBLE);
                       }else {
                           tv_no_record_found_awards.setVisibility(View.VISIBLE);
                           rv_kudos_awards.setVisibility(View.GONE);
                       }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        int status = homeDetail.getLeaveStatus();
                        if (status == 1){
                           // dialogEnableAbsent();
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

                }catch (Exception e){e.printStackTrace();}

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
               // utility.showToast(mContext,message);
            }
        });
        JsonObject object=Functions.getClient().getJsonMapObject("orgId",orgId,
                "deviceType",1+"");
        baseRequest.callAPIPost(1,object,ConstantAPI.api_getBasicVersionHomeDetails);
    }

    public void getUserList(){
        userList.clear();
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                    modelDepartmentDialog=gson.fromJson(object.toString(),ModelDepartmentDialog.class);
                    resetDepartmentsList=modelDepartmentDialog.getDepartments();
                    userList=modelDepartmentDialog.getUsers();
                    resetUserlist=modelDepartmentDialog.getUsers();
                    for (int i=0;i<userList.size();i++){
                        String id=userList.get(i).getId()+"";
                        if (id.equals(sessionParam.id+"")){
                            userList.remove(i);
                        }
                    }
                    resetUserList(clickableFlag);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
               // utility.showToast(mContext,message);

            }
        });
        JsonObject object= Functions.getClient().getJsonMapObject("orgId",orgId);
        baseRequest.callAPIPost(1,object, ConstantAPI.getDepartmentuserList);
    }
    public void api_getNotificationCount(){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    if (jsonObject.optInt("notificationCount")>0){
                        int countValue=jsonObject.optInt("notificationCount");
                        circle_count_tv.setVisibility(View.VISIBLE);
                        if (countValue>99){
                            circle_count_tv.setText("99"+"");
                        }else {
                            circle_count_tv.setText(countValue+"");
                        }
                    }else {
                        circle_count_tv.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //utility.showToast(mContext,message);
            }
        });
        JsonObject object=Functions.getClient().getJsonMapObject("userId",sessionParam.id);
        baseRequest.callAPIPostWOLoader(1,object,ConstantAPI.api_getBubbleUnReadNotifications);

    }
    public void sendAmazingAward(String msgText){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    try {
                        submit_msg_button.setEnabled(true);
                        String msg = "";
                        JSONObject obj = new JSONObject(Json);
                        msg = obj.optString("message");
                        utility.showToast(mContext, msg);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    et_comment.setText("");
                    comment="";

                    individualCount=0;
                    userList.clear();

                    getHomePageDetail();
                    getUserList();
                    JSONObject jsonObject= new JSONObject(object.toString());
                    String todayEIScore = jsonObject.getString("todayEIScore");
                    indexEngScoreData(todayEIScore);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
                submit_msg_button.setEnabled(true);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);
                submit_msg_button.setEnabled(true);
            }
        });
        JsonObject object=Functions.getClient().getJsonMapObject();
        JsonArray user_id_array=new JsonArray();
        for (int i=0;i<userList.size();i++){
            if (userList.get(i).isSelected())
                user_id_array.add(userList.get(i).getId()+"");
        }
        object.add("toUserId",user_id_array);
        object.addProperty("description",msgText);
        baseRequest.callAPIPost(1,object,ConstantAPI.addKudosAward);
    }

    public void api_addHappyIndex(String status){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    card_happy_index_ll.setVisibility(View.GONE);
                    String timeDate=utility.getCurrentDate();
                    sessionParam.happyIndexDialogShow(mContext,timeDate,true);
                    JSONObject jsonObject=new JSONObject(object.toString());
                    String todayEIScore=jsonObject.getString("todayEIScore");
                    indexEngScoreData(todayEIScore);
                    if (status.equals("1")){
                        dialogGetFeedback("happyIndexSend",todayEIScore);
                    }else {
                        dialogKudosSendResponse("happyIndexSend",todayEIScore);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
                card_happy_index_ll.setVisibility(View.GONE);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);
            }
        });
        JsonObject object=Functions.getClient().getJsonMapObject("userId", sessionParam.id, "moodStatus",status);
        baseRequest.callAPIPostWOLoader(1,object,ConstantAPI.addHappyIndex);
    }

    public void absentStatusChange(){
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
                   getHomePageDetail();
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
        JsonObject object= Functions.getClient().getJsonMapObject("userId",sessionParam.id);
        baseRequest.callAPIPost(1,object,ConstantAPI.api_userChangeLeaveStatus);
    }
    /************* absent Mode enable Api *****************/
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
                    getHomePageDetail();

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
    /************************** APi Calling Close *******************/


    /***************************** Dialog to get Feedback when user not happy *******************/
    public String formatdate(String fdate)
    {
        String datetime=null;
        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat d= new SimpleDateFormat("MMM, yyyy");
        try {
            Date convertedDate = inputFormat.parse(fdate);
            datetime = d.format(convertedDate);

        }catch (ParseException e)
        {
            e.printStackTrace();
        }
        return  datetime;


    }
     /********** set calender monthly List data **************/
    private void setCalenderList(List<HappyIndexMonthly> monthlyList,String monthStart, List<String> notWorkingDays){
        if (monthStart.contains("Monday")) {
            HappyIndexMonthly firstValue= new HappyIndexMonthly();
            firstValue.setDate("");
            firstValue.setScore(null);
            calenderList.add(firstValue);
        }else if (monthStart.contains("Tuesday")){
            for (int i=0;i<2;i++){
                HappyIndexMonthly secondValue = new HappyIndexMonthly();
                secondValue.setDate("");
                secondValue.setScore(null);
                calenderList.add(secondValue);
            }
        }else if (monthStart.contains("Wednesday")){
            for (int i=0;i<3;i++){
                HappyIndexMonthly thirdValue=new HappyIndexMonthly();
                thirdValue.setDate("");
                thirdValue.setScore(null);
                calenderList.add(thirdValue);
            }

        }else if (monthStart.contains("Thursday")){
            for (int i=0;i<4;i++) {
                HappyIndexMonthly fourthValue = new HappyIndexMonthly();
                fourthValue.setDate("");
                fourthValue.setScore(null);
                calenderList.add(fourthValue);
            }

        }else if (monthStart.contains("Friday")){
            for (int i=0;i<5;i++){
                HappyIndexMonthly fifthValue= new HappyIndexMonthly();
                fifthValue.setDate("");
                fifthValue.setScore(null);
                calenderList.add(fifthValue);
            }

        }else if (monthStart.contains("Saturday")){
            for (int i=0;i<6;i++){
                HappyIndexMonthly sixthValue= new HappyIndexMonthly();
                sixthValue.setDate("");
                sixthValue.setScore(null);
                calenderList.add(sixthValue);
            }

        }
        calenderList.addAll(monthlyList);
        ad_calenderView=new Ad_HICalender(notWorkingDays,calenderList, monthStart, mContext, new Ad_HICalender.onListenerClick() {
            @Override
            public void onHIClick(int position, HappyIndexMonthly value) {
                String weekIs="";
                int posValue= position % 7;
                if (posValue==1){
                    weekIs="Mon";
                }else if (posValue==2){
                    weekIs="Tue";
                }else if(posValue==3){
                    weekIs="Wed";
                }else if (posValue==4){
                    weekIs="Thur";
                }else if (posValue ==5){
                    weekIs="Fri";
                }
                // dialogShowData(weekIs+ ", "+value.getDate()+" "+ selectedMonth +" "+selectedyear,value.getScore());
                String monthYear="";
                try{
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy ");
                    Date date = new Date();
                    String dateIs = formatter.format(date);
                    monthYear= formatdate(dateIs);
                }catch (Exception e){
                    e.printStackTrace();
                }
                dialogShowData(weekIs+ ", "+value.getDate()+ " "+monthYear,value.getScore());
            }
        });
    calender_recycler_view.setAdapter(ad_calenderView);
    }
    /************** handle onClick on monthlyList cell ************/


    /*********** show Popup data on selected lis item ************/
    private void dialogShowData(String dateSelection, Double todayEIScore) {
        try {
            Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_calender_kudos);
            dialog.setCanceledOnTouchOutside(true);

            final TextView tv_title=dialog.findViewById(R.id.dialog_title_text);
            final TextView tvDate=dialog.findViewById(R.id.tv_date);
            final ImageView imageFace= dialog.findViewById(R.id.image_face);
            final TextView  hiValueTxt=dialog.findViewById(R.id.hi_value_txt);



            hiValueTxt.setText(todayEIScore.toString()+"%");
            tvDate.setText(dateSelection);
            if (todayEIScore >=0 && todayEIScore <= 30){
                imageFace.setImageResource(R.drawable.low);
                hiValueTxt.setTextColor(getResources().getColor(R.color.motion_index_low));
            }else if (todayEIScore >= 31 && todayEIScore <= 60){
                imageFace.setImageResource(R.drawable.medium);
                hiValueTxt.setTextColor(getResources().getColor(R.color.motion_index_medium));
            }else if (todayEIScore >=60 ){
                imageFace.setImageResource(R.drawable.high);
                hiValueTxt.setTextColor(getResources().getColor(R.color.motion_index_high));
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void dialogGetFeedback(String title,String todayEIScore){
        try{
            Dialog mDialog= new Dialog(BasicHomeActivity.this);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.dialog_get_feedback_sentiment);
            mDialog.setCancelable(true);

            flag=true;
            TextView dialogTitleTv=mDialog.findViewById(R.id.dialog_title_tv);
            TextView engagmentIndex=mDialog.findViewById(R.id.engagment_index);
            LinearLayout feedbackScroll=mDialog.findViewById(R.id.feedback_score_ll);
            ImageView engImage=mDialog.findViewById(R.id.eng_image);
            TextView engScoreTv=mDialog.findViewById(R.id.eng_score_tv);
            EditText etComment=mDialog.findViewById(R.id.et_comment);
            TextView tvSave=mDialog.findViewById(R.id.tv_save);
            etComment.setOnEditorActionListener(new DoneOnEditorActionListener());

            if (title.equals("")){
                dialogTitleTv.setText("Offloading");
                engagmentIndex.setVisibility(View.GONE);
                feedbackScroll.setVisibility(View.GONE);
            }else {
                dialogTitleTv.setText(getString(R.string.sentiment_submit));
            }
            tvSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etComment.getText().toString().trim().isEmpty()){
                        utility.showToast(mContext,getString(R.string.please_enter_offloading));
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
                        if (mDialog!=null){
                            mDialog.dismiss();
                        }
                        etComment.setText("");
                    }else {
                        utility.showToast(mContext,getString(R.string.please_enter_offloading));
                    }
                }
            });
            WindowManager.LayoutParams lp= new WindowManager.LayoutParams();
            Window window= mDialog.getWindow();
            lp.copyFrom(window.getAttributes());
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
            lp.width= WindowManager.LayoutParams.MATCH_PARENT;
            lp.height= WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            if (mDialog!=null){
                mDialog.dismiss();
            }
            mDialog.show();
            try {
                Float engScore=Float.valueOf(todayEIScore);
                try {
                    int engScoreInt=Math.round(engScore);
                    int startValue=0;
                    if (engScoreInt > 800){
                        startValue =400;
                    }else if (engScoreInt > 600){
                        startValue =200;
                    }else if (engScore > 400){
                        startValue =100;
                    }else {
                        startValue =0;
                    }
                    animateTextView(0, engScoreInt, engScoreTv,engImage,todayEIScore);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /********************************* dialog to show Engagement score ***************/
    private void dialogKudosSendResponse(String title,String todayEIScore){
        try {
            Dialog dialog= new Dialog(BasicHomeActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_kudos_send_response);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCanceledOnTouchOutside(true);
            TextView dialogTitleTv=dialog.findViewById(R.id.dialog_title_tv);
            ImageView engImage=dialog.findViewById(R.id.eng_image);
            TextView engScoreTv=dialog.findViewById(R.id.eng_score_tv);
            dialogTitleTv.setText(getString(R.string.sentiment_submit));

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
            try {
                int engScoreInt= Math.round(engScore);
                int startValue=0;
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
                animateTextView(0, engScoreInt,engScoreTv,engImage,todayEIScore);
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /******************************** Dialog to Amazing Circle Click **************/
    public void getAmazingCard(String name, int awardValue){

        amazing_tv_name.setText(name.trim());
        amazing_bottom_value.setText(awardValue+"");
        amazing_tv_name.setBackground(this.getResources().getDrawable(R.drawable.circle_red_row));
        card_amazing_ll.setBackground(this.getResources().getDrawable(R.drawable.circle_red_row));
        amazing_value_card_ll.setBackground(this.getResources().getDrawable(R.drawable.bottom_edge_white));
        amazing_tv_name.setTextColor(this.getResources().getColor(R.color.white));
        amazing_bottom_value.setTextColor(this.getResources().getColor(R.color.red));
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
               /* if(kudosList.size()>0){
                    Object object= new Object();

                    showChangeDialog(kudosList,object , 1);

                }*/
                Intent intent=new Intent(mContext, Act_AwardDescriptions.class);
                intent.putExtra("kudosId","0");
                intent.putExtra("kudosName","Amazing Awards");
                startActivity(intent);
            }
        });

        amazing_card_view_ll.setVisibility(View.VISIBLE);
        card_amazing_value_ll.setBackground(mContext.getResources().getDrawable(R.drawable.bottom_edge_white));
        amazing_award_count_tv.setText(awardValue+"");
        amazing_name_image.setOnClickListener(view -> {
            dialogAmazingShow(name);
            amazingFlag=true;
        });
        award_value_ll.setOnClickListener(view -> {
            Intent intent=new Intent(mContext, Act_AwardDescriptions.class);
            intent.putExtra("kudosId","0");
            intent.putExtra("kudosName","Amazing Awards");
            startActivity(intent);
        });

    }
    /************************ absentDialog enable ******************/
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

    /***************** dialog For Enable Absent Module ******************/
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
    /***************************** Amazing Dailog *********************/
    private void dialogAmazingShow(String nameTitle){
        final Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_amazing_layout);

        TextView amazingTitleTv= dialog.findViewById(R.id.amazing_title_tv);
        EditText diaEtComment=dialog.findViewById(R.id.et_comment);
        Button submitButton=dialog.findViewById(R.id.submit_button);
        dialog.setCanceledOnTouchOutside(true);


        amazingTitleTv.setText( nameTitle+" Award");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 comment= diaEtComment.getText().toString().trim();
                if (!comment.isEmpty()){
                    dialog.cancel();
                    //amazingComment=comment;
                    user_frame_layout.setVisibility(View.VISIBLE);
                    // individual_card_view_ll.setVisibility(View.GONE);

                    iv_top_back.setVisibility(View.VISIBLE);
                    sentiment_index_title.setVisibility(View.GONE);
                    calender_card_view.setVisibility(View.GONE);
                    award_card_ll.setVisibility(View.GONE);
                    card_amazing_ll.setVisibility(View.GONE);
                    award_list_card.setVisibility(View.GONE);
                    title_award_list.setVisibility(View.GONE);
                    /*if(!amazingFlag) {
                        kudosSelectionList = new ArrayList<>();
                        for (int i = 0; i < kudosList.size(); i++) {
                            if (kudosList.get(i).isSelected()) {
                                kudosSelectionList.add(kudosList.get(i));
                            }
                        }
                    }*/

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
    /*************************** show Animation for  Increse Score *************/
    public void animateTextView(int initialValue, int finalValue, final TextView eng_tv_score, ImageView eng_image, String todayEIScore){
        DecelerateInterpolator decelerateInterpolator= new DecelerateInterpolator(0.8f);
        int start= Math.min(initialValue, finalValue);
        int end= Math.max(initialValue, finalValue);
        int difference= Math.abs(finalValue-initialValue);
        Handler handler = new Handler();
        Float engScore= Float.valueOf(todayEIScore);
        int newValue=Math.round(engScore);
        for (int count=start; count<=end;count++){
            int time = Math.round(decelerateInterpolator.getInterpolation(((float) count  / difference)) * 100) * (count/10);
            int maxTime=(time-count);

            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if ( finalCount <= 299) {
                        eng_image.setImageResource(R.drawable.low);
                        //eng_tv_score.setText(indexEngScore+"");
                        //eng_tv_score.setText(todayEIScore + "");
                        eng_tv_score.setTextColor(getResources().getColor(R.color.motion_index_low));
                    } else if (finalCount >= 300 && finalCount < 800) {
                        eng_image.setImageResource(R.drawable.medium);
                        //tv_index_eng_number.setText(indexEngScore+"");
                        //eng_tv_score.setText(todayEIScore + "");
                        eng_tv_score.setTextColor(getResources().getColor(R.color.motion_index_medium));
                    } else if (finalCount >= 800) {
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
    /****************************** set Award at bottom navigation ****************************/
    public void checkAwardBottom(LatestKudoAward latestKudoAward){
        try {
            if (latestKudoAward!=null)
                if (!latestKudoAward.getAwardValue().isEmpty() && latestKudoAward.getAwardValue()!=null) {
                    award_card_ll.setVisibility(View.VISIBLE);

                    kudos_description_tv.setText(Html.fromHtml(latestKudoAward.getAwardDescription()));
                    // kudos_description_tv.setText(latestKudoAward.getAwardDescription());
                    if (latestKudoAward.getKudoAwardCount() > 0) {
                        kudosValueTv.setText(latestKudoAward.getAwardValue() + " - " + latestKudoAward.getUserName() + " & " + latestKudoAward.getKudoAwardCount() + " more");
                        Glide.with(mContext)
                                .load(R.drawable.group_award)
                                .placeholder(R.drawable.group_award)
                                .into(user_image_kudos);

                    } else {
                        kudosValueTv.setText(latestKudoAward.getAwardValue() + " - " + latestKudoAward.getUserName());
                        Glide.with(mContext)
                                .load(latestKudoAward.getUserImage())
                                .placeholder(R.drawable.user_circle)
                                .into(user_image_kudos);

                    }

                    String newDate = latestKudoAward.getAwardDate();
                    kudos_award_date.setText(utility.getDate(newDate));

                    //if(countValue)
                    //String image_url="https://console.tribe365.co/public/uploads/user_profile/user_1610001900.png";
                    //Picasso.get().load(latestKudoAward.getUserImage()).into(user_image_kudos);

                } else {
                    award_card_ll.setVisibility(View.GONE);
                }
        }catch (Exception e){
            award_card_ll.setVisibility(View.GONE);
            e.printStackTrace();

        }
    }



    /********************************* Call App Tour Method ****************************/
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
        //.setTitle(title)
        // .setContentSpan((Spannable) Html.fromHtml("<font color='white' ,background color='red'>text</p>"))
        //optional
        //optional
        //optional - default dismissible by TargetView
        // .setButtonBackground(ContextCompat.getDrawable(this, R.drawable.rounded))
        //.setButtonTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        // .setPaddingTitle(50,10,40,10)
        //.setPaddingMessage(50,10,40,10)
        GuideView.Builder builder = new GuideView.Builder(BasicHomeActivity.this)
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
                getHomePageDetail();
            }
        });

        mGuideView.mMessageView.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type < 15)
                    mGuideView.dismiss();
               if(type == 1){
                    ShowIntro(getString(R.string.daily_enga_ppt_basic), R.id.cv_index_eng, 2);
                } else if (type == 2) {
                    ShowIntro(getString(R.string.notification_ppt), R.id.iv_notic, 5);

                }else if (type == 5){
                   ShowIntro(getString(R.string.kudos_award_ppt), R.id.card_amazing_ll, 7);

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
                    ShowIntro(getString(R.string.thanks_ppt), R.id.well_app_tour, 13);
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
        amazing_dot_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mGuideView.updateGuideViewLocation();
            }
        });
    }


    /************************** set view on BaseActivity layout *********************/
    @Override
    public int getLayoutId() {
        return R.layout.activity_basic_home;
    }
    /*********************** set Navigation view *************************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}