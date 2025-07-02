package com.chetaru.tribe365_new.UI.Home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.Admin.ModelAdminReportDOT;
import com.chetaru.tribe365_new.API.Models.COTBeans.FuncLensKeyDetail;
import com.chetaru.tribe365_new.API.Models.COTBeans.InitialValueList;
import com.chetaru.tribe365_new.API.Models.COTBeans.ModelForRecyclerView;
import com.chetaru.tribe365_new.API.Models.COTBeans.ModelFutureLenseDetails;
import com.chetaru.tribe365_new.API.Models.COTBeans.PersuadeArray;
import com.chetaru.tribe365_new.API.Models.COTBeans.SeekArray;
import com.chetaru.tribe365_new.API.Models.DOT.Modelreport;
import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelDiagnosticReport;
import com.chetaru.tribe365_new.API.Models.KnowCompany.ModelCultureIndex;
import com.chetaru.tribe365_new.API.Models.ModelAddActionUser;
import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelMotivationGraph;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_UserListDialog;
import com.chetaru.tribe365_new.Adapter.AdminReportAdapter.Adapter_Admin_Report_DOT_Horizontal;
import com.chetaru.tribe365_new.Adapter.AdminReportAdapter.Adapter_Admin_SOT_Motivation_graph_list;
import com.chetaru.tribe365_new.Adapter.COTAdapters.Ad_CotfuncLensDetails;
import com.chetaru.tribe365_new.Adapter.Diagnostics.Adapter_Diagnostics_graph_list;
import com.chetaru.tribe365_new.Adapter.KnowCompany.Ad_CultureIndex;
import com.chetaru.tribe365_new.Adapter.SOTAdapters.Ad_reportList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.CustomerSupport.Act_CustomerSupport;
import com.chetaru.tribe365_new.UI.Home.Actions.Act_ActionList;
import com.chetaru.tribe365_new.UI.Know.COT.Cot_Individual;
import com.chetaru.tribe365_new.UI.Know.COT.Cot_New_Question;
import com.chetaru.tribe365_new.UI.Know.COT.Update_Cot_Question;
import com.chetaru.tribe365_new.UI.Know.PersonalityType.Act_Personality_type_list;
import com.chetaru.tribe365_new.UI.Know.PersonalityType.Act_Update_Personality_list;
import com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation.Act_SOT_Motivation_Question;
import com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation.Act_SOT_Update_Motivation_Question;
import com.chetaru.tribe365_new.UI.Offloading.Act_IOTHome;
import com.chetaru.tribe365_new.UI.UserInfo.Act_ProfileUser;
import com.chetaru.tribe365_new.utility.RecyclerItemClickListener;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_knowMember extends BaseActivity implements View.OnClickListener {

    //nav_views
    /***************** initialize variable ************************/
    DrawerLayout drawerLayout;
    TextView tv_nav_name, tv_nav_email, tv_nav_knowCompanyname;
    ImageView iv_nav_dp, iv_nav_company_logo;
    LinearLayout ll_nav_feedback, ll_nav_customerSupport, ll_nav_home, ll_nav_app_tour, ll_nav_know_member, ll_nav_knowCompany, ll_nav_logout;
    ImageView iv_home;

    ImageView iv_top_companylogo;
    SessionParam sessionParam;
    Utility utility;
    BaseRequest baseRequest;
    ArrayList<ModelAddActionUser> modelUserList;
    boolean subgraph = false;

    //all field down arrow click
    ImageView iv_valueBeliefDownArrow,
            iv_personality_type_downarrow,
            iv_cultureStructure_downarrow,
            iv_teamrole_downarrow,
            iv_motivation_downarrow,
            iv_thumbsup_downarrow;

    LinearLayout ll_teamRolsMain,
            ll_personality_typeMain,
            ll_motivationMain,
            ll_thumbsup_main,
            expandableView;

    //value and belief
    List<ModelAdminReportDOT> modelAdminReportList;
    RecyclerView rv_admin_report_dot;


    //team role
    /*********************** initialize bind View ***************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_shape)
    TextView tv_shape;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_coordinator)
    TextView tv_coordinator;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_implementer)
    TextView tv_implementer;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_completer)
    TextView tv_completer;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_monitor_Evat)
    TextView tv_monitor_Evat;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_team_work)
    TextView tv_team_work;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_plant)
    TextView tv_plant;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_resource)
    TextView tv_resource;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_shaper_totkey)
    TextView tv_shaper_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_coordinator_totkey)
    TextView tv_coordinator_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_implementer_totkey)
    TextView tv_implementer_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_completer_totkey)
    TextView tv_completer_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_monitor_Evat_totkey)
    TextView tv_monitor_Evat_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_team_work_totkey)
    TextView tv_team_work_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_plant_totkey)
    TextView tv_plant_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_resource_totkey)
    TextView tv_resource_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_undo)
    TextView tv_undo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_teamrole_nrf)
    Button btn_teamrole_nrf;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_shaper)
    TextView tv_txt_shaper;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_coordinator)
    TextView tv_txt_coordinator;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_implementer)
    TextView tv_txt_implementer;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_complete)
    TextView tv_txt_complete;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_monitor_evaluator)
    TextView tv_txt_monitor_evaluator;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_team_worker)
    TextView tv_txt_team_worker;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_plant)
    TextView tv_txt_plant;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_resource)
    TextView tv_txt_resource;


    //Personality type
    ModelFutureLenseDetails modelFutureLenseDetails;
    ModelForRecyclerView modelForRecyclerView;
    List<InitialValueList> initialValueLists;
    List<ModelForRecyclerView> listForRv;
    List<ModelForRecyclerView> listForENTP;
    List<ModelForRecyclerView> listForscrore;
    List<ModelForRecyclerView> listForENTPSummery;
    List<FuncLensKeyDetail> funcLensKeyDetailList;
    List<SeekArray> seekArrayList;
    List<PersuadeArray> persuadeArrayList;
    JSONArray mainPersonalityArray;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_bottom_block)
    LinearLayout ll_bottom_block;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_allheading)
    TextView tv_allheading;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_alldesc)
    TextView tv_alldesc;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_allseek)
    TextView tv_allseek;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_allseek_data)
    TextView tv_allseek_data;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_allPersuade)
    TextView tv_allPersuade;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_allPersuadedata)
    TextView tv_allPersuadedata;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_nodata)
    TextView tv_nodata;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_initial_result)
    TextView tv_initial_result;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_cot_summary)
    TextView tv_cot_summary;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_undo_personalityType)
    TextView tv_undo_personalityType;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_d1)
    TextView tv_d1;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_d2)
    TextView tv_d2;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_d3)
    TextView tv_d3;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_d4)
    TextView tv_d4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_s1)
    TextView tv_s1;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_s2)
    TextView tv_s2;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_s3)
    TextView tv_s3;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_s4)
    TextView tv_s4;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_allDataBlock)
    LinearLayout ll_allDataBlock;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_topBlock)
    LinearLayout ll_topBlock;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_personalityType_nrf)
    Button btn_personalityType_nrf;
    TextView tv_action_right;
    TextView tv_userName;

    //new personality Type
    //clone with Diagnostics for Personality type
    BarChart Person_chart;

    String org_name = "";
    ArrayList<ModelDiagnosticReport> li_Person_report = new ArrayList<>();
    ArrayList<Float> li_score_short_person = new ArrayList<>();
    /*@BindView(R.id.rv_person_graph_list)*/
    RecyclerView rv_diagnostic_graph_list_person;
    @BindView(R.id.ll_diagnostic_graph)
    LinearLayout ll_diagnostic_graph;
    TextView tv_personalityBack;


    //motivation graph
    ArrayList<ModelMotivationGraph> list_motivation_graph = new ArrayList<>();
    ArrayList<Float> li_score_short = new ArrayList<>();
    RecyclerView rv_sot_motivation_graph;
    BarChart chart;
    TextView tv_undoMoti;

    //thumbsup
    ArrayList<Modelreport> li_report = new ArrayList<>();
    Ad_reportList ad_reportList;
    RecyclerView rv_reportlist;

    String userId = "";

    TextView tv_nrf_thumsUp, tv_nrf_motivation, tv_nrf_personalityType, tv_nrf_teamrole, tv_nrf_value;
    Button btn_motivation_nrf;

    boolean dotStatus = false;
    boolean cotTeamRoleStatus = false;
    boolean cotPersonalityStatus = false;
    boolean sotStatus = false;
    boolean sotMotivationStatus = false;
    boolean bubbleRatingStatus = false;
    boolean personaliseDataStatus = false;

    RelativeLayout rv_profile;

    //engagement Index
    ImageView iv_engagement_indexDownArrow;
    LinearLayout ll_engagement_index_graph_main, lyt_engagement_index, ll_engagement_index_msg;
    LineChart chart_engagement_index;
    TextView tv_engagement_index, tv_nrf_engagement_index;
    RecyclerView rv_engagement_index_graph_list;
    ArrayList<ModelCultureIndex> list_engagment_index = new ArrayList<>();

    //Add by Ritesh for show personality graph layout
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_view_full_result)
    TextView tv_ViewFullResult;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_view_back_result)
    TextView tv_ViewBackResult;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_personalise_data_msg)
    TextView personaliseDataMsg;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.all_know_member_ll)
    LinearLayout allKnowMemberLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_know_member);

        //for member personality chart
        ////////personality Type create by Ritesh
        Person_chart = findViewById(R.id.member_person_chart);
        ll_diagnostic_graph = findViewById(R.id.ll_diagnostic_graph);

        rv_diagnostic_graph_list_person = findViewById(R.id.rv_member_person_graph_list);
        tv_personalityBack = findViewById(R.id.tv_member_personality_back);
        tv_ViewFullResult = findViewById(R.id.tv_view_full_result);
        tv_ViewBackResult = findViewById(R.id.tv_view_back_result);
        tv_personalityBack.setOnClickListener(this);
        tv_ViewFullResult.setOnClickListener(this);
        tv_ViewFullResult.setVisibility(View.VISIBLE);
        tv_ViewBackResult.setOnClickListener(this);
        tv_ViewBackResult.setVisibility(View.GONE);
        /**************** initialize all view **************/
        init();
        /***************** call getData Api ********************/
        getAllData(userId, sessionParam.name, sessionParam.lastName);
    }

    /*used to initialise all the views*/
    public void init() {

        //initialization
        ButterKnife.bind(this);
        sessionParam = new SessionParam(mContext);
        utility = new Utility();
        userId = sessionParam.id;

        tv_nrf_thumsUp = findViewById(R.id.tv_nrf_thumsUp);
        tv_nrf_motivation = findViewById(R.id.tv_nrf_motivation);
        btn_motivation_nrf = findViewById(R.id.btn_motivation_nrf);
        tv_nrf_personalityType = findViewById(R.id.tv_nrf_personalityType);
        tv_nrf_teamrole = findViewById(R.id.tv_nrf_teamrole);
        tv_nrf_value = findViewById(R.id.tv_nrf_value);

        iv_home = findViewById(R.id.iv_home);
        drawerLayout = findViewById(R.id.drawer_layout);
        ll_nav_feedback = findViewById(R.id.ll_nav_feedback);
        ll_nav_customerSupport = findViewById(R.id.ll_nav_customer_support);
        ll_nav_home = findViewById(R.id.ll_nav_home);
        ll_nav_know_member = findViewById(R.id.ll_nav_know_member);
        ll_nav_app_tour = findViewById(R.id.ll_nav_app_tour);
        ll_nav_knowCompany = findViewById(R.id.ll_nav_knowCompany);
        ll_nav_logout = findViewById(R.id.ll_nav_logout);
        iv_top_companylogo = findViewById(R.id.iv_top_companylogo);
        iv_nav_dp = findViewById(R.id.iv_nav_dp);
        iv_nav_company_logo = findViewById(R.id.iv_nav_company_logo);
        tv_nav_name = findViewById(R.id.tv_nav_name);
        tv_nav_email = findViewById(R.id.tv_nav_email);
        tv_nav_knowCompanyname = findViewById(R.id.tv_nav_knowCompanyname);
        tv_action_right = findViewById(R.id.tv_action_right);
        rv_profile = findViewById(R.id.rv_profile);

        //Value and belief
        rv_admin_report_dot = findViewById(R.id.rv_admin_report_dot);
        tv_userName = findViewById(R.id.tv_userName);


        //all field down arrow click
        iv_valueBeliefDownArrow = findViewById(R.id.iv_valueBeliefDownArrow);
        iv_personality_type_downarrow = findViewById(R.id.iv_personality_type_downarrow);
        iv_cultureStructure_downarrow = findViewById(R.id.iv_cultureStructure_downarrow);
        iv_motivation_downarrow = findViewById(R.id.iv_motivation_downarrow);
        ll_thumbsup_main = findViewById(R.id.ll_thumbsup_main);
        iv_teamrole_downarrow = findViewById(R.id.iv_teamrole_downarrow);
        expandableView = findViewById(R.id.expandableView);

        ll_personality_typeMain = findViewById(R.id.ll_personality_typeMain);
        ll_teamRolsMain = findViewById(R.id.ll_teamrole_main);
        ll_motivationMain = findViewById(R.id.ll_motivationMain);
        iv_thumbsup_downarrow = findViewById(R.id.iv_thumbsup_downarrow);

        rv_reportlist = findViewById(R.id.rv_reportlist);

        iv_engagement_indexDownArrow = findViewById(R.id.iv_engagement_indexDownArrow);
        //ll_culture_index = findViewById(R.id.ll_culture_index);
        ll_engagement_index_msg = findViewById(R.id.ll_engagement_index_msg);
        ll_engagement_index_graph_main = findViewById(R.id.ll_engagement_index_graph_main);
        lyt_engagement_index = findViewById(R.id.lyt_engagement_index);
        chart_engagement_index = findViewById(R.id.chart_engagement_index);
        rv_engagement_index_graph_list = findViewById(R.id.rv_engagement_index_graph_list);
        tv_nrf_engagement_index = findViewById(R.id.tv_nrf_engagement_index);
        iv_engagement_indexDownArrow.setOnClickListener(this);

        //click event
        iv_home.setOnClickListener(this);
        ll_nav_feedback.setOnClickListener(this);
        ll_nav_customerSupport.setOnClickListener(this);
        ll_nav_home.setOnClickListener(this);
        ll_nav_know_member.setOnClickListener(this);
        ll_nav_app_tour.setOnClickListener(this);
        ll_nav_knowCompany.setOnClickListener(this);
        ll_nav_logout.setOnClickListener(this);
        tv_action_right.setOnClickListener(this);

        iv_valueBeliefDownArrow.setOnClickListener(this);
        iv_personality_type_downarrow.setOnClickListener(this);

        iv_teamrole_downarrow.setOnClickListener(this);
        iv_home.setOnClickListener(this);
        iv_motivation_downarrow.setOnClickListener(this);
        iv_thumbsup_downarrow.setOnClickListener(this);
        tv_undo.setOnClickListener(this);
        btn_teamrole_nrf.setOnClickListener(this);

        //personality type
        listForRv = new ArrayList<>();
        tv_d1.setOnClickListener(this);
        tv_d2.setOnClickListener(this);
        tv_d3.setOnClickListener(this);
        tv_d4.setOnClickListener(this);
        tv_s1.setOnClickListener(this);
        tv_s2.setOnClickListener(this);
        tv_s3.setOnClickListener(this);
        tv_s4.setOnClickListener(this);
        tv_undo_personalityType.setOnClickListener(this);
        btn_personalityType_nrf.setOnClickListener(this);
        tv_initial_result.setOnClickListener(this);
        tv_userName.setOnClickListener(this);
        tv_userName.setText(sessionParam.name + " " + sessionParam.lastName);
        rv_profile.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);

        //motivation
        rv_sot_motivation_graph = findViewById(R.id.rv_sot_motivation_graph);
        tv_undoMoti = findViewById(R.id.tv_undoMoti);

        chart = findViewById(R.id.chart);
        tv_undoMoti.setOnClickListener(this);
        btn_motivation_nrf.setOnClickListener(this);

        if (!sessionParam.organisation_logo.equals("")) {
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);
            Picasso.get().load(sessionParam.organisation_logo).into(iv_nav_company_logo);
        }
        if (!sessionParam.profileImage.equals("")) {
            //Picasso.get().load(sessionParam.profileImage).into(iv_nav_dp);
           /* Picasso.get().load(sessionParam.profileImage)
                    .placeholder(getResources().getDrawable(R.drawable.user_circle))
                    .error(R.drawable.user_circle)
                    .into(iv_nav_dp);*/
            Glide.with(this)
                    .load(sessionParam.profileImage)
                    .centerCrop()
                    .placeholder(R.drawable.user_circle)
                    .into(iv_nav_dp);
        }

        tv_nav_name.setText(sessionParam.name + " " + sessionParam.lastName);
        tv_nav_email.setText(sessionParam.email);
        tv_nav_knowCompanyname.setText("Know " + sessionParam.orgname);

        /************ call sub Graph for personalityType *****/
        Person_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // api_diagnosticSubGraph()
                if (!subgraph) {
                    subgraph = true;
                    api_personalityTypeSubgraph(userId, li_Person_report.get(e.getXIndex()).getCategoryId());
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    /************** handle click event *****************/
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent in;
        switch (v.getId()) {
            case R.id.iv_home:

                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START);
                } else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.ll_nav_feedback:
                finish();
                startActivity(new Intent(mContext, Act_IOTHome.class));
                drawerLayout.closeDrawers();
                break;
            case R.id.ll_nav_customer_support:
                finish();
                startActivity(new Intent(mContext, Act_CustomerSupport.class));
                drawerLayout.closeDrawers();
                break;
            case R.id.ll_nav_home:
                finishAllActivities();
                startActivity(new Intent(mContext, Act_Home.class));
                break;
            case R.id.ll_nav_know_member:
                drawerLayout.closeDrawers();
                break;
            case R.id.ll_nav_app_tour:
                Intent intent = new Intent(mContext, Act_Home.class);
                intent.putExtra("introType", 1);
                startActivity(intent);
                drawerLayout.closeDrawers();
                break;
            case R.id.ll_nav_knowCompany:
                startActivity(new Intent(mContext, Act_knowcompany.class));
                finish();
                break;

            case R.id.rv_profile:
                drawerLayout.closeDrawers();
                startActivity(new Intent(mContext, Act_ProfileUser.class));
                break;

            case R.id.ll_nav_logout:
                drawerLayout.closeDrawers();
                logoutDialog();
                break;

            case R.id.iv_valueBeliefDownArrow:
                if (sotStatus) {
                    if (expandableView.getVisibility() == View.VISIBLE) {
                        expandableView.setVisibility(View.GONE);
                    } else {
                        expandableView.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_nrf_value.getVisibility() == View.VISIBLE) {
                        tv_nrf_value.setVisibility(View.GONE);
                    } else {
                        tv_nrf_value.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case R.id.iv_personality_type_downarrow:


                if (cotPersonalityStatus) {
                    if (ll_personality_typeMain.getVisibility() == View.VISIBLE) {
                        ll_personality_typeMain.setVisibility(View.GONE);
                        btn_personalityType_nrf.setVisibility(View.GONE);
                        tv_ViewFullResult.setVisibility(View.GONE);


                    } else {
                        ll_personality_typeMain.setVisibility(View.VISIBLE);
                        ll_topBlock.setVisibility(View.VISIBLE);
                        ll_bottom_block.setVisibility(View.VISIBLE);
                        tv_nodata.setVisibility(View.GONE);
                        tv_ViewBackResult.setVisibility(View.GONE);
                        ll_diagnostic_graph.setVisibility(View.GONE);
                        if (userId.equals(sessionParam.id)) {
                            tv_ViewFullResult.setVisibility(View.VISIBLE);
                        } else {
                            tv_ViewFullResult.setVisibility(View.GONE);
                        }
                    }
                } else {
                    if (tv_nrf_personalityType.getVisibility() == View.VISIBLE) {
                        tv_nrf_personalityType.setVisibility(View.GONE);
                        btn_personalityType_nrf.setVisibility(View.GONE);
                        tv_ViewFullResult.setVisibility(View.GONE);
                    } else {
                        tv_nrf_personalityType.setVisibility(View.VISIBLE);
                        btn_personalityType_nrf.setVisibility(View.VISIBLE);
                        if (userId.equals(sessionParam.id)) {
                            tv_ViewFullResult.setVisibility(View.VISIBLE);
                        } else {
                            tv_ViewFullResult.setVisibility(View.GONE);
                        }

                    }
                }

                break;
            case R.id.tv_member_personality_back:
                if (subgraph) {
                    subgraph = false;
                    tv_personalityBack.setVisibility(View.GONE);
                    setPersonalityTypeData(mainPersonalityArray);
                }
                break;
            case R.id.iv_motivation_downarrow:
                if (sotMotivationStatus) {
                    if (ll_motivationMain.getVisibility() == View.VISIBLE) {
                        ll_motivationMain.setVisibility(View.GONE);
                    } else {
                        ll_motivationMain.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_nrf_motivation.getVisibility() == View.VISIBLE) {
                        tv_nrf_motivation.setVisibility(View.GONE);
                        btn_motivation_nrf.setVisibility(View.GONE);
                    } else {
                        tv_nrf_motivation.setVisibility(View.VISIBLE);
                        if (userId.equals(sessionParam.id)) {
                            btn_motivation_nrf.setVisibility(View.VISIBLE);
                        } else {
                            btn_motivation_nrf.setVisibility(View.GONE);
                        }
                    }
                }

                break;


            case R.id.iv_thumbsup_downarrow:
                if (bubbleRatingStatus) {
                    if (ll_thumbsup_main.getVisibility() == View.VISIBLE) {
                        ll_thumbsup_main.setVisibility(View.GONE);
                    } else {
                        ll_thumbsup_main.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_nrf_thumsUp.getVisibility() == View.VISIBLE) {
                        tv_nrf_thumsUp.setVisibility(View.GONE);
                    } else {
                        tv_nrf_thumsUp.setVisibility(View.VISIBLE);
                    }
                }

                break;
            case R.id.iv_teamrole_downarrow:
                if (cotTeamRoleStatus) {
                    if (ll_teamRolsMain.getVisibility() == View.VISIBLE) {
                        ll_teamRolsMain.setVisibility(View.GONE);
                    } else {
                        ll_teamRolsMain.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_nrf_teamrole.getVisibility() == View.VISIBLE) {
                        tv_nrf_teamrole.setVisibility(View.GONE);
                        btn_teamrole_nrf.setVisibility(View.GONE);
                    } else {
                        tv_nrf_teamrole.setVisibility(View.VISIBLE);
                        if (sessionParam.id.equals(userId)) {
                            btn_teamrole_nrf.setVisibility(View.VISIBLE);
                        }

                    }
                }
                break;

            case R.id.tv_undo:
                in = new Intent(mContext, Update_Cot_Question.class);
                startActivity(in);
                break;

            case R.id.btn_teamrole_nrf:

                api_checkAnswerDistributedOrNot();

                break;

            case R.id.tv_undoMoti:
                in = new Intent(mContext, Act_SOT_Update_Motivation_Question.class);
                startActivity(in);
                break;
            case R.id.btn_motivation_nrf:
                in = new Intent(mContext, Act_SOT_Motivation_Question.class);
                startActivity(in);
                break;

            case R.id.tv_action_right:
                startActivity(new Intent(mContext, Act_ActionList.class));
                break;

            case R.id.tv_d1:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                tv_cot_summary.setVisibility(View.VISIBLE);
                listForENTP = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                if (userId.equals(sessionParam.id)) {
                    modelForRecyclerView.setHeading(funcLensKeyDetailList.get(0).getTitle());
                    modelForRecyclerView.setDescription(funcLensKeyDetailList.get(0).getDescription());
                } else {
                    modelForRecyclerView.setHeading(funcLensKeyDetailList.get(1).getTitle());
                    modelForRecyclerView.setDescription(funcLensKeyDetailList.get(1).getDescription());
                }

                modelForRecyclerView.setType("funclens");
                listForENTP.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForENTP, mContext));
                // Changing border color
                tv_d1.setBackground(getResources().getDrawable(R.drawable.rect_solid_red_border));
                tv_d2.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d3.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d4.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                setScoreblockdefaultColor();

                break;
            case R.id.tv_d2:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                tv_cot_summary.setVisibility(View.VISIBLE);
                listForENTP = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                if (userId.equals(sessionParam.id)) {
                    modelForRecyclerView.setHeading(funcLensKeyDetailList.get(1).getTitle());
                    modelForRecyclerView.setDescription(funcLensKeyDetailList.get(1).getDescription());
                } else {
                    modelForRecyclerView.setHeading(funcLensKeyDetailList.get(2).getTitle());
                    modelForRecyclerView.setDescription(funcLensKeyDetailList.get(2).getDescription());
                }
                modelForRecyclerView.setType("funclens");
                listForENTP.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForENTP, mContext));

                // Changing border color
                tv_d1.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d2.setBackground(getResources().getDrawable(R.drawable.rect_solid_red_border));
                tv_d3.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d4.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                setScoreblockdefaultColor();
                break;
            case R.id.tv_d3:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                tv_cot_summary.setVisibility(View.VISIBLE);
                listForENTP = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(funcLensKeyDetailList.get(2).getTitle());
                modelForRecyclerView.setDescription(funcLensKeyDetailList.get(2).getDescription());
                modelForRecyclerView.setType("funclens");
                listForENTP.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForENTP, mContext));
                // Changing border color
                tv_d1.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d2.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d3.setBackground(getResources().getDrawable(R.drawable.rect_solid_red_border));
                tv_d4.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                setScoreblockdefaultColor();
                break;
            case R.id.tv_d4:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                tv_cot_summary.setVisibility(View.VISIBLE);
                listForENTP = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(funcLensKeyDetailList.get(3).getTitle());
                modelForRecyclerView.setDescription(funcLensKeyDetailList.get(3).getDescription());
                modelForRecyclerView.setType("funclens");
                listForENTP.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForENTP, mContext));
                // Changing border color
                tv_d1.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d2.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d3.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d4.setBackground(getResources().getDrawable(R.drawable.rect_solid_red_border));
                setScoreblockdefaultColor();
                break;
            case R.id.tv_s1:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                tv_cot_summary.setVisibility(View.VISIBLE);
                listForscrore = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(initialValueLists.get(0).getTitle());
                modelForRecyclerView.setPositives(initialValueLists.get(0).getPositives());
                modelForRecyclerView.setAllowableWeakness(initialValueLists.get(0).getAllowableWeaknesses());
                modelForRecyclerView.setType("initialValue");
                listForscrore.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForscrore, mContext));
                //changing color of blocks
                //here we are clicking on score block one that meant only block one will show high lighted with red border rest blocks with black border
                DallBloackdefaultColor();
                scoreBlock1_SELECTED();
                scoreBlock2_NOT_SELECTED();
                scoreBlock3_NOT_SELECTED();
                scoreBlock4_NOT_SELECTED();

                break;
            case R.id.tv_s2:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                tv_cot_summary.setVisibility(View.VISIBLE);
                listForscrore = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(initialValueLists.get(1).getTitle());
                modelForRecyclerView.setPositives(initialValueLists.get(1).getPositives());
                modelForRecyclerView.setAllowableWeakness(initialValueLists.get(1).getAllowableWeaknesses());
                modelForRecyclerView.setType("initialValue");
                listForscrore.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForscrore, mContext));
                DallBloackdefaultColor();
                scoreBlock1_NOT_SELECTED();
                scoreBlock2_SELECTED();
                scoreBlock3_NOT_SELECTED();
                scoreBlock4_NOT_SELECTED();

                break;
            case R.id.tv_s3:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                tv_cot_summary.setVisibility(View.VISIBLE);
                listForscrore = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(initialValueLists.get(2).getTitle());
                modelForRecyclerView.setPositives(initialValueLists.get(2).getPositives());
                modelForRecyclerView.setAllowableWeakness(initialValueLists.get(2).getAllowableWeaknesses());
                modelForRecyclerView.setType("initialValue");
                listForscrore.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForscrore, mContext));
                if (initialValueLists.get(2).getTitle().toLowerCase().contains("very clear")) {
                    tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_red_border_red));
                }
                if (initialValueLists.get(2).getTitle().toLowerCase().contains("clear")) {
                    tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_red_border_orange));
                }
                if (initialValueLists.get(2).getTitle().toLowerCase().contains("moderate")) {
                    tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_red_border_lightgreen));
                }
                if (initialValueLists.get(2).getTitle().toLowerCase().contains("slight")) {
                    tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_red_border_green));
                }
                DallBloackdefaultColor();
                scoreBlock1_NOT_SELECTED();
                scoreBlock2_NOT_SELECTED();
                scoreBlock3_SELECTED();
                scoreBlock4_NOT_SELECTED();

                break;
            case R.id.tv_s4:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                tv_cot_summary.setVisibility(View.VISIBLE);
                listForscrore = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(initialValueLists.get(3).getTitle());
                modelForRecyclerView.setPositives(initialValueLists.get(3).getPositives());
                modelForRecyclerView.setAllowableWeakness(initialValueLists.get(3).getAllowableWeaknesses());
                modelForRecyclerView.setType("initialValue");
                listForscrore.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForscrore, mContext));
                DallBloackdefaultColor();
                scoreBlock1_NOT_SELECTED();
                scoreBlock2_NOT_SELECTED();
                scoreBlock3_NOT_SELECTED();
                scoreBlock4_SELECTED();


                break;

            case R.id.tv_initial_result:
                tv_initial_result.setVisibility(View.GONE);
                tv_cot_summary.setVisibility(View.GONE);
                rv_list.setVisibility(View.GONE);
                ll_allDataBlock.setVisibility(View.VISIBLE);

                if (!userId.equals(sessionParam.id)) {
                    try {
                        tv_allheading.setText(modelFutureLenseDetails.getTribeTipsArray().get(0).getTitle());
                        tv_alldesc.setText(modelFutureLenseDetails.getTribeTipsArray().get(0).getSummary());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tv_allPersuade.setVisibility(View.GONE);
                    tv_allPersuadedata.setVisibility(View.GONE);
                    DallBloackdefaultColor();
                    setScoreblockdefaultColor();
                    String seekValue = "";
                    if (seekArrayList != null) {
                        for (int l = 0; l < seekArrayList.size(); l++) {
                            modelForRecyclerView = new ModelForRecyclerView();
                            modelForRecyclerView.setHeading("Seek");
                            if (l == 0) {
                                seekValue = "\u25CF " + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                            } else {
                                seekValue = seekValue + "\u25CF " + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                            }
                        }
                        tv_allseek_data.setText(seekValue);
                        tv_allseek.setVisibility(View.VISIBLE);
                        tv_allseek_data.setVisibility(View.VISIBLE);

                    }
                    if (persuadeArrayList != null) {
                        String persuadeValue = "";
                        for (int l = 0; l < persuadeArrayList.size(); l++) {
                            modelForRecyclerView = new ModelForRecyclerView();
                            modelForRecyclerView.setHeading("Persuade");
                            if (l == 0) {
                                persuadeValue = "\u25CF " + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                            } else {
                                persuadeValue = persuadeValue + "\u25CF " + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                            }
                        }
                        tv_allPersuadedata.setText(persuadeValue);
                        tv_allPersuadedata.setVisibility(View.VISIBLE);
                        tv_allPersuade.setVisibility(View.VISIBLE);
                    }


                } else {
                    if (modelFutureLenseDetails.getTribeTipsArray().size() > 0) {
                        tv_allheading.setText(modelFutureLenseDetails.getTribeTipsArray().get(0).getTitle());
                        tv_alldesc.setText(modelFutureLenseDetails.getTribeTipsArray().get(0).getSummary());
                    }
                    if (modelFutureLenseDetails.getValueCombination().size() > 0) {
                        tv_allheading.setText(modelFutureLenseDetails.getValueCombination().get(0).getTitle());
                        tv_alldesc.setText(modelFutureLenseDetails.getValueCombination().get(0).getSummary());
                    }

                    tv_allPersuade.setVisibility(View.GONE);
                    tv_allPersuadedata.setVisibility(View.GONE);


                    DallBloackdefaultColor();
                    setScoreblockdefaultColor();
                }


                break;

            case R.id.tv_undo_personalityType:
            case R.id.btn_personalityType_nrf:

                /* in = new Intent(mContext, Act_Update_Fun_QuestionList.class);
                in.putExtra("checklist", "");
                startActivity(in);*/
                if (cotPersonalityStatus) {
                    startActivity(new Intent(mContext, Act_Update_Personality_list.class));
                } else {
                    startActivity(new Intent(mContext, Act_Personality_type_list.class));
                }
                break;

                /*in = new Intent(mContext, Act_Fun_QuestionList.class);
                in.putExtra("checklist", "");
                startActivity(in);*/
            case R.id.tv_userName:
                dialogResponsible();
                break;
            //created by Ritesh to view Personality Graph
            case R.id.tv_view_full_result:
                tv_ViewBackResult.setVisibility(View.VISIBLE);
                tv_ViewFullResult.setVisibility(View.GONE);
                ll_topBlock.setVisibility(View.GONE);
                ll_bottom_block.setVisibility(View.GONE);
                tv_nodata.setVisibility(View.GONE);

                ll_diagnostic_graph.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_view_back_result:
                tv_ViewFullResult.setVisibility(View.VISIBLE);
                ll_topBlock.setVisibility(View.VISIBLE);
                ll_bottom_block.setVisibility(View.VISIBLE);
                tv_nodata.setVisibility(View.GONE);
                tv_ViewBackResult.setVisibility(View.GONE);
                ll_diagnostic_graph.setVisibility(View.GONE);
                break;

            case R.id.iv_engagement_indexDownArrow:
                if (list_engagment_index.size() > 0) {
                    if (ll_engagement_index_graph_main.getVisibility() == View.VISIBLE) {
                        ll_engagement_index_graph_main.setVisibility(View.GONE);
                    } else {
                        ll_engagement_index_graph_main.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_nrf_engagement_index.getVisibility() == View.VISIBLE) {
                        tv_nrf_engagement_index.setVisibility(View.GONE);
                    } else {
                        tv_nrf_engagement_index.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

    /*API call to get all data of the organisation users
     */
    public void getAllData(final String userId, final String firstName, final String lastName) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

                try {
                    Gson gson = new Gson();
                    JSONObject jsonObjectMain = new JSONObject(object.toString());
                    personaliseDataStatus = jsonObjectMain.getBoolean("personaliseData");

                    JSONArray jsonArrayDotGraph = jsonObjectMain.getJSONArray("getDOTreportGraph");
                    JSONObject jo_Teamrole = jsonObjectMain.getJSONObject("getCOTindividualSummary");
                    JSONArray ja_motivationgraph = jsonObjectMain.getJSONArray("getSOTmotivationUserList");
                    JSONArray ja_engagementIndex = jsonObjectMain.getJSONArray("userEngagementReport");
                    JSONArray ja_thumbsup = jsonObjectMain.getJSONArray("getBubbleRatingList");
                    JSONObject jo_userStatus = jsonObjectMain.getJSONObject("userStatus");
                    // JSONObject jo_PersonalityType = jsonObjectMain.getJSONObject("getCOTpersonalityType");
                    JSONObject jo_PersonalityTypeInitial = jsonObjectMain.getJSONObject("getCOTpersonalityTypeInitial");
                    JSONArray jo_PersonalityType = jsonObjectMain.getJSONArray("getCOTpersonalityType");

                    modelAdminReportList = baseRequest.getDataList(jsonArrayDotGraph, ModelAdminReportDOT.class);
                    dotStatus = jo_userStatus.getBoolean("dotStatus");
                    cotTeamRoleStatus = jo_userStatus.getBoolean("cotTeamRoleStatus");
                    cotPersonalityStatus = jo_userStatus.getBoolean("cotPersonalityStatus");
                    sotStatus = jo_userStatus.getBoolean("sotStatus");
                    sotMotivationStatus = jo_userStatus.getBoolean("sotMotivationStatus");
                    bubbleRatingStatus = jo_userStatus.getBoolean("bubbleRatingStatus");

                    if (!personaliseDataStatus) {
                        allKnowMemberLayout.setVisibility(View.VISIBLE);
                        personaliseDataMsg.setVisibility(View.GONE);
                    } else {
                        if (!userId.equals(sessionParam.id)) {
                            personaliseDataMsg.setText(firstName + " " + lastName + " data is set to Private");
                            allKnowMemberLayout.setVisibility(View.GONE);
                            personaliseDataMsg.setVisibility(View.VISIBLE);
                        } else {
                            allKnowMemberLayout.setVisibility(View.VISIBLE);
                            personaliseDataMsg.setVisibility(View.GONE);
                        }
                    }

                    setValueAndBeliefData(modelAdminReportList);
                    setTeamroledata(jo_Teamrole);
                    setPersonalityTypeData(jo_PersonalityType);
                    setPersonalityTypeInitial(jo_PersonalityTypeInitial);
                    setMotivationgraph(ja_motivationgraph);
                    setEngagmentIndex(ja_engagementIndex);
                    setThumsupdata(ja_thumbsup);
                    mainPersonalityArray = jo_PersonalityType;

                    if (sessionParam.id.equals(userId)) {
                        tv_undoMoti.setVisibility(View.VISIBLE);
                        tv_undo.setVisibility(View.VISIBLE);
                        tv_undo_personalityType.setVisibility(View.VISIBLE);


                    } else {
                        tv_undoMoti.setVisibility(View.GONE);
                        tv_undo.setVisibility(View.GONE);
                        tv_undo_personalityType.setVisibility(View.GONE);

                    }
                    //for graph back button Data set


                    expandableView.setVisibility(View.GONE);
                    ll_motivationMain.setVisibility(View.GONE);
                    ll_teamRolsMain.setVisibility(View.GONE);
                    ll_thumbsup_main.setVisibility(View.GONE);
                    ll_personality_typeMain.setVisibility(View.GONE);

                    tv_nrf_value.setVisibility(View.GONE);
                    tv_nrf_motivation.setVisibility(View.GONE);
                    tv_nrf_teamrole.setVisibility(View.GONE);
                    btn_teamrole_nrf.setVisibility(View.GONE);
                    btn_motivation_nrf.setVisibility(View.GONE);

                    tv_ViewFullResult.setVisibility(View.GONE);
                    tv_nrf_thumsUp.setVisibility(View.GONE);
                    tv_nrf_personalityType.setVisibility(View.GONE);
                    subgraph = false;
                    tv_personalityBack.setVisibility(View.GONE);

                } catch (JSONException e) {
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


        JsonObject object = Functions.getClient().getJsonMapObject(
                "orgId", sessionParam.orgId,
                "userId", userId
        );
        baseRequest.callAPIPost(1, object,ConstantAPI.api_getUserDashboardReport );
    }

    /*this method is used to set belief data
     */
    public void setValueAndBeliefData(List<ModelAdminReportDOT> list) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rv_admin_report_dot.setLayoutManager(layoutManager);
        Adapter_Admin_Report_DOT_Horizontal adapter_admin_report_dot_horizontal =
                new Adapter_Admin_Report_DOT_Horizontal(list, mContext);
        rv_admin_report_dot.setAdapter(adapter_admin_report_dot_horizontal);


    }

    /*this method is used to set Teamrole data
     */
    public void setTeamroledata(JSONObject object) {
        JSONObject jsonObject = object;
        tv_shape.setText(jsonObject.optString("shaper"));
        tv_coordinator.setText(jsonObject.optString("coordinator"));
        tv_implementer.setText(jsonObject.optString("implementer"));
        tv_completer.setText(jsonObject.optString("completerFinisher"));
        tv_monitor_Evat.setText(jsonObject.optString("monitorEvaluator"));
        tv_team_work.setText(jsonObject.optString("teamworker"));
        tv_plant.setText(jsonObject.optString("plant"));
        tv_resource.setText(jsonObject.optString("resourceInvestigator"));
        CheckColor(jsonObject);
        JSONObject jsonObject1 = jsonObject.optJSONObject("totalKeyCount");

        tv_shaper_totkey.setText(jsonObject1.optString("shaper"));
        tv_coordinator_totkey.setText(jsonObject1.optString("coordinator"));
        tv_implementer_totkey.setText(jsonObject1.optString("implementer"));
        tv_completer_totkey.setText(jsonObject1.optString("completerFinisher"));
        tv_monitor_Evat_totkey.setText(jsonObject1.optString("monitorEvaluator"));
        tv_team_work_totkey.setText(jsonObject1.optString("teamworker"));
        tv_plant_totkey.setText(jsonObject1.optString("plant"));
        tv_resource_totkey.setText(jsonObject1.optString("resourceInvestigator"));
//--------set Text dynamic ----------------------------------------------------------------------------------------------------------------------

        JSONObject jsonObject2 = jsonObject.optJSONObject("mapersArray");
        if (tv_txt_shaper.getText().equals("Shaper")) {
            tv_txt_shaper.setText(jsonObject2.optString("shaper"));
        }
        if (tv_txt_coordinator.getText().equals("Coordinator")) {
            String upperString = jsonObject2.optString("coordinator").substring(0, 1).toUpperCase() + jsonObject2.optString("coordinator").substring(1);
            tv_txt_coordinator.setText(upperString);
        }
        if (tv_txt_implementer.getText().equals("Implementer")) {
            String upperString = jsonObject2.optString("implementer").substring(0, 1).toUpperCase() + jsonObject2.optString("implementer").substring(1);
            tv_txt_implementer.setText(upperString);
        }
        if (tv_txt_complete.getText().equals("Completer")) {
            String upperString = jsonObject2.optString("completerFinisher").substring(0, 1).toUpperCase() + jsonObject2.optString("completerFinisher").substring(1);
            tv_txt_complete.setText(upperString);
        }
        if (tv_txt_monitor_evaluator.getText().equals("Monitor Evaluator")) {
            String upperString = jsonObject2.optString("monitorEvaluator").substring(0, 1).toUpperCase() + jsonObject2.optString("monitorEvaluator").substring(1);
            tv_txt_monitor_evaluator.setText(upperString);
        }
        if (tv_txt_team_worker.getText().equals("Team Worker")) {
            String upperString = jsonObject2.optString("teamworker").substring(0, 1).toUpperCase() + jsonObject2.optString("teamworker").substring(1);

            tv_txt_team_worker.setText(upperString);
        }
        if (tv_txt_plant.getText().equals("Plant")) {
            String upperString = jsonObject2.optString("plant").substring(0, 1).toUpperCase() + jsonObject2.optString("plant").substring(1);

            tv_txt_plant.setText(upperString);
        }
        if (tv_txt_resource.getText().equals("Resource Investigator")) {
            String upperString = jsonObject2.optString("resourceInvestigator").substring(0, 1).toUpperCase() + jsonObject2.optString("resourceInvestigator").substring(1);

            tv_txt_resource.setText(upperString);
        }
    }

    private void CheckColor(JSONObject jsonObject) {
//----------------shaper------------------------------
        if (jsonObject.optString("shaper").equals("0")) {

            tv_shape.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_shape.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("shaper").equals("1")) {
            tv_shape.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("shaper").equals("2")) {
            tv_shape.setTextColor(ContextCompat.getColor(this, R.color.black));
            tv_shape.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));

        } else if (jsonObject.optString("shaper").equals("3")) {
            tv_shape.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_shape.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }
//----------------coordinator------------------------------
        if (jsonObject.optString("coordinator").equals("0")) {
            tv_coordinator.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            ;
            tv_coordinator.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("coordinator").equals("1")) {
            tv_coordinator.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("coordinator").equals("2")) {
            tv_coordinator.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_coordinator.setTextColor(ContextCompat.getColor(this, R.color.black));

        } else if (jsonObject.optString("coordinator").equals("3")) {
            tv_coordinator.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_coordinator.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }
        //----------------implementer------------------------------
        if (jsonObject.optString("implementer").equals("0")) {
            tv_implementer.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_implementer.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("implementer").equals("1")) {
            tv_implementer.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("implementer").equals("2")) {
            tv_implementer.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_implementer.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("implementer").equals("3")) {
            tv_implementer.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_implementer.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }


//----------------completerFinisher------------------------------
        if (jsonObject.optString("completerFinisher").equals("0")) {
            tv_completer.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_completer.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("completerFinisher").equals("1")) {
            tv_completer.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("completerFinisher").equals("2")) {
            tv_completer.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_completer.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("completerFinisher").equals("3")) {
            tv_completer.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_completer.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }

//----------------monitorEvaluator------------------------------
        if (jsonObject.optString("monitorEvaluator").equals("0")) {
            tv_monitor_Evat.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_monitor_Evat.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("monitorEvaluator").equals("1")) {
            tv_monitor_Evat.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("monitorEvaluator").equals("2")) {
            tv_monitor_Evat.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_monitor_Evat.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("monitorEvaluator").equals("3")) {
            tv_monitor_Evat.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_monitor_Evat.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }

//----------------teamworker------------------------------
        if (jsonObject.optString("teamworker").equals("0")) {
            tv_team_work.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_team_work.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("teamworker").equals("1")) {
            tv_team_work.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("teamworker").equals("2")) {
            tv_team_work.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_team_work.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("teamworker").equals("3")) {
            tv_team_work.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_team_work.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }

//----------------plant------------------------------
        if (jsonObject.optString("plant").equals("0")) {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_plant.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("plant").equals("1")) {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("plant").equals("2")) {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_plant.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("plant").equals("3")) {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }

        //----------------resourceInvestigator------------------------------
        if (jsonObject.optString("resourceInvestigator").equals("0")) {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_plant.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("resourceInvestigator").equals("1")) {
            tv_resource.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("resourceInvestigator").equals("2")) {
            tv_resource.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_resource.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("resourceInvestigator").equals("3")) {
            tv_resource.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_resource.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }
    }

    /*this method is used to set personality type data
     */
    public void setPersonalityTypeData(JSONObject jsonObject) {
        Gson gson = new Gson();
        modelFutureLenseDetails = gson.fromJson(jsonObject.toString(), ModelFutureLenseDetails.class);
        // try {
        if (modelFutureLenseDetails.getFuncLensKeyDetail().size() == 0) {
            ll_topBlock.setVisibility(View.GONE);
            ll_bottom_block.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
            return;
        } else {
            ll_topBlock.setVisibility(View.VISIBLE);
            ll_bottom_block.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
        }
        modelForRecyclerView = new ModelForRecyclerView();
        modelForRecyclerView.setHeading(modelFutureLenseDetails.getTribeTipsArray().get(0).getSummary());
        modelForRecyclerView.setType("summery");
        listForRv.add(modelForRecyclerView);

        tv_allheading.setText(modelFutureLenseDetails.getValueCombination().get(0).getTitle());
        tv_alldesc.setText(modelFutureLenseDetails.getValueCombination().get(0).getSummary());
        tv_allPersuade.setVisibility(View.GONE);
        tv_allPersuadedata.setVisibility(View.GONE);

        if (!userId.equals(sessionParam.id)) {
            tv_allseek.setVisibility(View.VISIBLE);

            tv_undo_personalityType.setVisibility(View.GONE);
            seekArrayList = modelFutureLenseDetails.getTribeTipsArray().get(0).getSeekArray();
            persuadeArrayList = modelFutureLenseDetails.getTribeTipsArray().get(0).getPersuadeArray();
            String seekValue = "";
            if (seekArrayList != null) {
                for (int l = 0; l < seekArrayList.size(); l++) {
                    modelForRecyclerView = new ModelForRecyclerView();
                    modelForRecyclerView.setHeading("Seek");
                    if (l == 0) {
                        seekValue = "\u25CF " + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                    } else {
                        seekValue = seekValue + "\u25CF " + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                    }
                }
                tv_allseek_data.setText(seekValue);
                tv_allseek.setVisibility(View.VISIBLE);
                tv_allseek_data.setVisibility(View.VISIBLE);

            }

            if (persuadeArrayList != null) {
                String persuadeValue = "";
                for (int l = 0; l < persuadeArrayList.size(); l++) {
                    modelForRecyclerView = new ModelForRecyclerView();
                    modelForRecyclerView.setHeading("Persuade");
                    if (l == 0) {
                        persuadeValue = "\u25CF " + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                    } else {
                        persuadeValue = persuadeValue + "\u25CF " + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                    }
                }
                tv_allPersuadedata.setText(persuadeValue);
                tv_allPersuadedata.setVisibility(View.VISIBLE);
                tv_allPersuade.setVisibility(View.VISIBLE);

            }
        } else {
            tv_allseek.setVisibility(View.GONE);
            tv_allseek_data.setVisibility(View.GONE);
            tv_d3.setVisibility(View.VISIBLE);
            tv_d4.setVisibility(View.VISIBLE);
            tv_s3.setVisibility(View.VISIBLE);
            tv_s4.setVisibility(View.VISIBLE);
            tv_allPersuadedata.setVisibility(View.GONE);
            tv_undo_personalityType.setVisibility(View.GONE);
        }


//        } catch (Exception e) {
//            Toast.makeText(currentAct, e.toString(), Toast.LENGTH_SHORT).show();
//
//        }


        setAdapter(modelFutureLenseDetails);
    }

    /*this method is used to set personality type Initial data
     */
    public void setPersonalityTypeInitial(JSONObject jsonObject) {
        Gson gson = new Gson();
        ll_diagnostic_graph.setVisibility(View.GONE);
        tv_ViewFullResult.setVisibility(View.VISIBLE);
        modelFutureLenseDetails = gson.fromJson(jsonObject.toString(), ModelFutureLenseDetails.class);
        // try {
        if (modelFutureLenseDetails.getFuncLensKeyDetail().size() == 0) {
            ll_topBlock.setVisibility(View.GONE);
            ll_bottom_block.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
            return;
        } else {
            ll_topBlock.setVisibility(View.VISIBLE);
            ll_bottom_block.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
        }
        modelForRecyclerView = new ModelForRecyclerView();
        modelForRecyclerView.setHeading(modelFutureLenseDetails.getTribeTipsArray().get(0).getSummary());
        modelForRecyclerView.setType("summery");
        listForRv.add(modelForRecyclerView);

        tv_allheading.setText(modelFutureLenseDetails.getValueCombination().get(0).getTitle());
        tv_alldesc.setText(modelFutureLenseDetails.getValueCombination().get(0).getSummary());
        tv_allPersuade.setVisibility(View.GONE);
        tv_allPersuadedata.setVisibility(View.GONE);

        if (!userId.equals(sessionParam.id)) {
            tv_allseek.setVisibility(View.VISIBLE);

            tv_undo_personalityType.setVisibility(View.GONE);
            seekArrayList = modelFutureLenseDetails.getTribeTipsArray().get(0).getSeekArray();
            persuadeArrayList = modelFutureLenseDetails.getTribeTipsArray().get(0).getPersuadeArray();
            String seekValue = "";
            if (seekArrayList != null) {
                for (int l = 0; l < seekArrayList.size(); l++) {
                    modelForRecyclerView = new ModelForRecyclerView();
                    modelForRecyclerView.setHeading("Seek");
                    if (l == 0) {
                        seekValue = "\u25CF " + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                    } else {
                        seekValue = seekValue + "\u25CF " + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                    }
                }
                tv_allseek_data.setText(seekValue);
                tv_allseek.setVisibility(View.VISIBLE);
                tv_allseek_data.setVisibility(View.VISIBLE);

            }

            if (persuadeArrayList != null) {
                String persuadeValue = "";
                for (int l = 0; l < persuadeArrayList.size(); l++) {
                    modelForRecyclerView = new ModelForRecyclerView();
                    modelForRecyclerView.setHeading("Persuade");
                    if (l == 0) {
                        persuadeValue = "\u25CF " + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                    } else {
                        persuadeValue = persuadeValue + "\u25CF " + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                    }
                }
                tv_allPersuadedata.setText(persuadeValue);
                tv_allPersuadedata.setVisibility(View.VISIBLE);
                tv_allPersuade.setVisibility(View.VISIBLE);

            }
        } else {
            tv_allseek.setVisibility(View.GONE);
            tv_allseek_data.setVisibility(View.GONE);
            tv_d3.setVisibility(View.VISIBLE);
            tv_d4.setVisibility(View.VISIBLE);
            tv_s3.setVisibility(View.VISIBLE);
            tv_s4.setVisibility(View.VISIBLE);
            tv_allPersuadedata.setVisibility(View.GONE);
            tv_undo_personalityType.setVisibility(View.GONE);
        }


//        } catch (Exception e) {
//            Toast.makeText(currentAct, e.toString(), Toast.LENGTH_SHORT).show();
//
//        }


        setAdapter(modelFutureLenseDetails);
    }

    public void setPersonalityTypeData(JSONArray jsonArray) {
        Gson gson = new Gson();
        try {
            // JSONArray jsonArray = new JSONArray(jsonArray.toString());
            Log.d("Diagnostics_graph", jsonArray.toString());
            li_Person_report = baseRequest.getDataList(jsonArray, ModelDiagnosticReport.class);
            //ModelDiagnosticReport
            //  list_motivation_graph = baseRequest.getDataList(jsonArray, ModelMotivationGraph.class);
            ArraystoreValuePerson();
            //ll_diagnostic_graph.setVisibility(View.VISIBLE);


            if (sessionParam.role.equals("3")) {
                tv_undo.setVisibility(View.VISIBLE);
            }
            Adapter_Diagnostics_graph_list adap_diag_graphlist = new Adapter_Diagnostics_graph_list(li_Person_report, li_score_short_person, mContext);
            rv_diagnostic_graph_list_person.setHasFixedSize(true);
            rv_diagnostic_graph_list_person.setAdapter(adap_diag_graphlist);
            rv_diagnostic_graph_list_person.setLayoutManager(new LinearLayoutManager(mContext));
//Size of recycler view
            ViewGroup.LayoutParams params = rv_diagnostic_graph_list_person.getLayoutParams();
            params.height = 90 * li_Person_report.size();
            rv_diagnostic_graph_list_person.requestLayout();

            BarData data = new BarData(getXAxisValuesPerson(), getDataSetPerson());
            YAxis yLeft = Person_chart.getAxisLeft();
            //Set the minimum and maximum bar lengths as per the values that they represent
            yLeft.setAxisMaxValue(100f);
            yLeft.setAxisMinValue(0f);
            Log.d("person_graph_data", data.toString());
            Person_chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            Person_chart.getRendererXAxis();
            Person_chart.getAxisRight().setEnabled(false);
//                    chart.getAxisLeft().setEnabled(false);
            Person_chart.setData(data);
            Person_chart.setScaleEnabled(false);
            Person_chart.setDoubleTapToZoomEnabled(false);
            Person_chart.setDescription(" ");
            Person_chart.animateXY(2000, 2000);
            Person_chart.invalidate();
//                    setPercentage();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*API to get Personality data when the user clicked on the graph*/
    public void api_personalityTypeSubgraph(String userId, String categoryId) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    setPersonalityTypeData(jsonArray);
                    tv_personalityBack.setVisibility(View.VISIBLE);
                    //li_Diagnostic_report = baseRequest.getDataList(jsonArray, ModelDiagnosticReport.class);
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
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId,
                "userId", userId,
                "categoryId", categoryId
        );
        baseRequest.callAPIPost(1, object,ConstantAPI.api_getPersonalityTypeReportSubGraph );
    }

    public void setAdapter(ModelFutureLenseDetails modelFutureLenseDetails) {
        if (modelFutureLenseDetails.getInitialValueList().size() > 0) {
            initialValueLists = modelFutureLenseDetails.getInitialValueList();
        }
        if (modelFutureLenseDetails.getFuncLensKeyDetail().size() > 0) {
            funcLensKeyDetailList = modelFutureLenseDetails.getFuncLensKeyDetail();
            //here we are checking how many combination are there and showing them accordingly

            if (!userId.equals(sessionParam.id)) {
                initialValueLists.remove(0);
                initialValueLists.remove(2);
            }
            if (initialValueLists.size() == 1) {
                if (initialValueLists.get(0).getScore() == 0) {
                    tv_s1.setClickable(false);
                    tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_gry));
                    tv_s1.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));
                }
                tv_d1.setText(initialValueLists.get(0).getValue());

                tv_d2.setVisibility(View.GONE);
                tv_d3.setVisibility(View.GONE);
                tv_d4.setVisibility(View.GONE);
                try {
                    if (initialValueLists.get(0).getValue().equalsIgnoreCase(initialValueLists.get(0).getValue())) {
                        tv_s1.setText(initialValueLists.get(0).getScore().toString());
                        tv_s2.setVisibility(View.GONE);
                        tv_s3.setVisibility(View.GONE);
                        tv_s4.setVisibility(View.GONE);
                        if (initialValueLists.get(0).getTitle().toLowerCase().contains("very clear")) {
                            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
                        }
                        if (initialValueLists.get(0).getTitle().toLowerCase().contains("clear")) {
                            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
                        }
                        if (initialValueLists.get(0).getTitle().toLowerCase().contains("moderate")) {
                            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
                        }
                        if (initialValueLists.get(0).getTitle().toLowerCase().contains("slight")) {
                            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
                        }
                    }
                } catch (IndexOutOfBoundsException iobe) {

                }
            }
            if (initialValueLists.size() == 2) {

                if (userId.equals(sessionParam)) {
                    if (initialValueLists.get(0).getScore() == 0) {
                        tv_s1.setClickable(false);
                        tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_gry));
                        tv_s1.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));
                    }
                    if (initialValueLists.get(1).getScore() == 0) {
                        tv_s2.setClickable(false);
                        tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_gry));
                        tv_s2.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));
                    }
                }

                tv_d1.setText(initialValueLists.get(0).getValue());
                tv_d2.setText(initialValueLists.get(1).getValue());
                tv_d3.setVisibility(View.GONE);
                tv_d4.setVisibility(View.GONE);
                if (initialValueLists.get(1).getValue().equalsIgnoreCase(initialValueLists.get(1).getValue())) {
                    tv_s1.setText(initialValueLists.get(0).getScore().toString());
                    tv_s2.setText(initialValueLists.get(1).getScore().toString());
                    tv_s3.setVisibility(View.GONE);
                    tv_s4.setVisibility(View.GONE);
                }

            }
            if (initialValueLists.size() == 3) {
                if (initialValueLists.get(0).getScore() == 0) {
                    tv_s1.setClickable(false);
                    tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_gry));
                    tv_s1.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));

                }
                if (initialValueLists.get(1).getScore() == 0) {
                    tv_s2.setClickable(false);
                    tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_gry));
                    tv_s2.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));
                }

                if (initialValueLists.get(2).getScore() == 0) {
                    tv_s3.setClickable(false);
                    tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_red_border_gry));
                    tv_s3.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));

                }
                tv_d1.setText(initialValueLists.get(0).getValue());
                tv_d2.setText(initialValueLists.get(1).getValue());
                tv_d3.setText(initialValueLists.get(2).getValue());
                tv_d4.setVisibility(View.GONE);

                tv_s1.setText(initialValueLists.get(0).getScore().toString());
                tv_s2.setText(initialValueLists.get(1).getScore().toString());
                tv_s3.setText(initialValueLists.get(2).getScore().toString());
                tv_s4.setVisibility(View.GONE);
            }
            if (initialValueLists.size() == 4) {
                if (initialValueLists.get(0).getScore() == 0) {
                    tv_s1.setClickable(false);
                    tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_gry));
                    tv_s1.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));
                }
                if (initialValueLists.get(1).getScore() == 0) {
                    tv_s2.setClickable(false);
                    tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_gry));
                    tv_s2.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));
                }
                if (initialValueLists.get(2).getScore() == 0) {
                    tv_s3.setClickable(false);
                    tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_red_border_gry));
                    tv_s3.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));
                }
                if (initialValueLists.get(3).getScore() == 0) {
                    tv_s4.setClickable(false);
                    tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_red_border_gry));
                    tv_s4.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));
                }
                tv_d1.setText(initialValueLists.get(0).getValue());
                tv_d2.setText(initialValueLists.get(1).getValue());
                tv_d3.setText(initialValueLists.get(2).getValue());
                tv_d4.setText(initialValueLists.get(3).getValue());
                tv_s1.setText(initialValueLists.get(0).getScore().toString());
                tv_s2.setText(initialValueLists.get(1).getScore().toString());
                tv_s3.setText(initialValueLists.get(2).getScore().toString());
                tv_s4.setText(initialValueLists.get(3).getScore().toString());
                //setting score block color
                //for first block
                setScoreblockdefaultColor();
            }
        }

        if (modelFutureLenseDetails.getTribeTipsArray().size() > 0) {
            seekArrayList = modelFutureLenseDetails.getTribeTipsArray().get(0).getSeekArray();
            persuadeArrayList = modelFutureLenseDetails.getTribeTipsArray().get(0).getPersuadeArray();
        }

        //here we are creating list for recyclerView
        if (funcLensKeyDetailList != null) {
            for (int j = 0; j < funcLensKeyDetailList.size(); j++) {
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(funcLensKeyDetailList.get(j).getTitle());
                modelForRecyclerView.setDescription(funcLensKeyDetailList.get(j).getDescription());
                modelForRecyclerView.setType("funclens");
                listForRv.add(modelForRecyclerView);
            }
        }

        if (initialValueLists != null) {
            for (int k = 0; k < initialValueLists.size(); k++) {
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(initialValueLists.get(k).getTitle());
                modelForRecyclerView.setPositives(initialValueLists.get(k).getPositives());
                modelForRecyclerView.setAllowableWeakness(initialValueLists.get(k).getAllowableWeaknesses());
                modelForRecyclerView.setType("initialValue");
                listForRv.add(modelForRecyclerView);
            }
        }

    }

    public void setScoreblockdefaultColor() {
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("very clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
        }

        if (initialValueLists.get(0).getTitle().toLowerCase().contains("moderate")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("slight")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
        }
        //for second block
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("very clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
        }

        if (initialValueLists.get(1).getTitle().toLowerCase().contains("moderate")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("slight")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
        }

        //add condition here for user list
        //for third block
        if (userId.equals(sessionParam.id)) {
            if (initialValueLists.get(2).getTitle().toLowerCase().contains("clear")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
            }
            if (initialValueLists.get(2).getTitle().toLowerCase().contains("very clear")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
            }

            if (initialValueLists.get(2).getTitle().toLowerCase().contains("moderate")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
            }
            if (initialValueLists.get(2).getTitle().toLowerCase().contains("slight")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
            }
            //for fourth block
            if (initialValueLists.get(3).getTitle().toLowerCase().contains("clear")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
            }
            if (initialValueLists.get(3).getTitle().toLowerCase().contains("very clear")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
            }

            if (initialValueLists.get(3).getTitle().toLowerCase().contains("moderate")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
            }
            if (initialValueLists.get(3).getTitle().toLowerCase().contains("slight")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
            }
        }

    }

    public void DallBloackdefaultColor() {
        tv_d1.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
        tv_d2.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
        tv_d3.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
        tv_d4.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
    }

    public void scoreBlock1_SELECTED() {

        if (initialValueLists.get(0).getTitle().toLowerCase().contains("clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_orange));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("very clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_red));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("moderate")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_lightgreen));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("slight")) {
            //slight sensing
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_green));
        }
    }

    public void scoreBlock1_NOT_SELECTED() {
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("very clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
        }

        if (initialValueLists.get(0).getTitle().toLowerCase().contains("moderate")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("slight")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
        }
    }

    public void scoreBlock2_SELECTED() {
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_orange));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("very clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_red));
        }

        if (initialValueLists.get(1).getTitle().toLowerCase().contains("moderate")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_lightgreen));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("slight")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_green));
        }
    }

    public void scoreBlock2_NOT_SELECTED() {
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("very clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
        }

        if (initialValueLists.get(1).getTitle().toLowerCase().contains("moderate")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("slight")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
        }
    }

    public void scoreBlock3_SELECTED() {
        if (initialValueLists.get(2).getTitle().toLowerCase().contains("clear")) {
            tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_red_border_orange));
        }
        if (initialValueLists.get(2).getTitle().toLowerCase().contains("very clear")) {
            tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_red_border_red));
        }

        if (initialValueLists.get(2).getTitle().toLowerCase().contains("moderate")) {
            tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_red_border_lightgreen));
        }
        if (initialValueLists.get(2).getTitle().toLowerCase().contains("slight")) {
            tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_red_border_green));
        }
    }

    public void scoreBlock3_NOT_SELECTED() {
//here we are handling user and team member
        if (userId.equals(sessionParam.id)) {
            if (initialValueLists.get(2).getTitle().toLowerCase().contains("clear")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
            }
            if (initialValueLists.get(2).getTitle().toLowerCase().contains("very clear")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
            }

            if (initialValueLists.get(2).getTitle().toLowerCase().contains("moderate")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
            }
            if (initialValueLists.get(2).getTitle().toLowerCase().contains("slight")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
            }
        } else {
            if (initialValueLists.get(0).getTitle().toLowerCase().contains("clear")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
            }
            if (initialValueLists.get(0).getTitle().toLowerCase().contains("very clear")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
            }

            if (initialValueLists.get(0).getTitle().toLowerCase().contains("moderate")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
            }
            if (initialValueLists.get(0).getTitle().toLowerCase().contains("slight")) {
                tv_s3.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
            }
        }


    }

    public void scoreBlock4_SELECTED() {
        if (initialValueLists.get(3).getTitle().toLowerCase().contains("clear")) {
            tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_red_border_orange));
        }
        if (initialValueLists.get(3).getTitle().toLowerCase().contains("very clear")) {
            tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_red_border_red));
        }

        if (initialValueLists.get(3).getTitle().toLowerCase().contains("moderate")) {
            tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_red_border_lightgreen));
        }
        if (initialValueLists.get(3).getTitle().toLowerCase().contains("slight")) {
            tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_red_border_green));
        }
    }

    public void scoreBlock4_NOT_SELECTED() {

        if (userId.equals(sessionParam.id)) {

            if (initialValueLists.get(3).getTitle().toLowerCase().contains("clear")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
            }
            if (initialValueLists.get(3).getTitle().toLowerCase().contains("very clear")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
            }

            if (initialValueLists.get(3).getTitle().toLowerCase().contains("moderate")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
            }
            if (initialValueLists.get(3).getTitle().toLowerCase().contains("slight")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
            }

        } else {
            if (initialValueLists.get(1).getTitle().toLowerCase().contains("clear")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
            }
            if (initialValueLists.get(1).getTitle().toLowerCase().contains("very clear")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
            }

            if (initialValueLists.get(1).getTitle().toLowerCase().contains("moderate")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
            }
            if (initialValueLists.get(1).getTitle().toLowerCase().contains("slight")) {
                tv_s4.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
            }
        }

    }

    /* this method is used to set motivation graph and list.
     */
    public void setMotivationgraph(JSONArray jsonArrayD) {
        JSONArray jsonArray = jsonArrayD;
        list_motivation_graph = baseRequest.getDataList(jsonArray, ModelMotivationGraph.class);
        ArraystoreValue();
        // lyt_motivation.setVisibility(View.VISIBLE);
        //lyt_indi_graph.setVisibility(View.VISIBLE);
        Adapter_Admin_SOT_Motivation_graph_list adap_sot_graphlist = new Adapter_Admin_SOT_Motivation_graph_list(list_motivation_graph, mContext);
        rv_sot_motivation_graph.setHasFixedSize(true);
        rv_sot_motivation_graph.setAdapter(adap_sot_graphlist);
        rv_sot_motivation_graph.setLayoutManager(new LinearLayoutManager(mContext));

        if (userId.equals(sessionParam.id)) {
            tv_undoMoti.setVisibility(View.VISIBLE);
        } else {
            tv_undoMoti.setVisibility(View.GONE);
        }
//Size of recycler view
        ViewGroup.LayoutParams params = rv_sot_motivation_graph.getLayoutParams();
        params.height = 90 * list_motivation_graph.size();
        rv_sot_motivation_graph.requestLayout();
//--------------------------------------------
        BarData data = new BarData(getXAxisValues(), getDataSet());
        YAxis yLeft = chart.getAxisLeft();
        //Set the minimum and maximum bar lengths as per the values that they represent
        yLeft.setAxisMaxValue(100f);
        yLeft.setAxisMinValue(0f);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getRendererXAxis();
        chart.getAxisRight().setEnabled(false);
        chart.setData(data);
        chart.setScaleEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDescription(" ");
        chart.animateXY(2000, 2000);
        chart.invalidate();

    }

    @SuppressLint("Range")
    private void ArraystoreValue() {
        li_score_short.clear();
        for (int j = 0; j < list_motivation_graph.size(); j++) {
            li_score_short.add(Float.valueOf(list_motivation_graph.get(j).getScore()));
        }


        Object[] st = li_score_short.toArray();
        for (Object s : st) {
            if (li_score_short.indexOf(s) != li_score_short.lastIndexOf(s)) {
                li_score_short.remove(li_score_short.lastIndexOf(s));
            }
        }
        Collections.sort(li_score_short, new Comparator<Float>() {
            @Override
            public int compare(Float lhs, Float rhs) {
                return lhs.compareTo(rhs);
            }
        });

        li_score_short.size();
    }

    /*this method is used to set x-axis values
     */
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < list_motivation_graph.size(); i++) {
            xAxis.add((i + 1) + "");
        }
        return xAxis;
    }

    /*will set data on bar chart
     */
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int j = 0; j < list_motivation_graph.size(); j++) {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(list_motivation_graph.get(j).getScore()), j); // Jan
            valueSet1.add(v1e1);
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Score");
//        barDataSet1.setColor(Color.rgb(51, 111, 179));
        barDataSet1.setColor(getResources().getColor(R.color.graph_color));
        barDataSet1.setBarSpacePercent(50);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    /* this method is used to set data on engagement index
     */
    public void setEngagmentIndex(JSONArray jsonArrayEI) {

        JSONArray jsonArray = jsonArrayEI;
        list_engagment_index = baseRequest.getDataList(jsonArray, ModelCultureIndex.class);
        chart_engagement_index.setDescription("");
        chart_engagement_index.setNoDataTextDescription("You need to provide data for the chart.");
        ArraystoreValueEI();
        Ad_CultureIndex ad_cultureIndex = new Ad_CultureIndex(list_engagment_index, mContext);
        rv_engagement_index_graph_list.setHasFixedSize(true);
        rv_engagement_index_graph_list.setAdapter(ad_cultureIndex);
        rv_engagement_index_graph_list.setLayoutManager(new LinearLayoutManager(mContext));
//Size of recycler view
        ViewGroup.LayoutParams params = rv_engagement_index_graph_list.getLayoutParams();
        params.height = 90 * list_engagment_index.size();
        rv_engagement_index_graph_list.requestLayout();

        chart_engagement_index.setHighlightEnabled(false);
        chart_engagement_index.setTouchEnabled(false);
        chart_engagement_index.setDragEnabled(true);
        chart_engagement_index.setScaleEnabled(true);
        chart_engagement_index.setPinchZoom(false);
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        chart_engagement_index.setMarkerView(mv);
        chart_engagement_index.setHighlightIndicatorEnabled(false);
        XAxis xAxis = chart_engagement_index.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setSpaceBetweenLabels(1);
        YAxis leftAxis = chart_engagement_index.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMinValue(1f);
        leftAxis.setStartAtZero(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawLimitLinesBehindData(true);
        chart_engagement_index.getAxisRight().setEnabled(false);
        setDataEI(list_engagment_index);
        chart_engagement_index.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = chart_engagement_index.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        if (list_engagment_index.size() > 0) {
            ll_engagement_index_graph_main.setVisibility(View.VISIBLE);
            ll_engagement_index_msg.setVisibility(View.GONE);
        } else {
            ll_engagement_index_graph_main.setVisibility(View.VISIBLE);
            lyt_engagement_index.setVisibility(View.GONE);
            ll_engagement_index_msg.setVisibility(View.VISIBLE);
        }
    }

    private void setDataEI(ArrayList<ModelCultureIndex> list) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 0f, 0f);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(1f);
        set1.setCircleSize(5f);
        set1.setDrawCircleHole(true);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);
        // set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
        // Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        //LineData data = new LineData(xVals, dataSets);

        LineData data = new LineData(getXAxisValuesEI(), getDataSetEI());

        // set data
        chart_engagement_index.setData(data);
    }

    private void ArraystoreValueEI() {
        li_score_short.clear();
        for (int j = 0; j < list_engagment_index.size(); j++) {
            li_score_short.add(Float.valueOf(list_engagment_index.get(j).getIndexCount()));
        }
        Object[] st = li_score_short.toArray();
        for (Object s : st) {
            if (li_score_short.indexOf(s) != li_score_short.lastIndexOf(s)) {
                li_score_short.remove(li_score_short.lastIndexOf(s));
            }
        }
        Collections.sort(li_score_short, new Comparator<Float>() {
            @Override
            public int compare(Float lhs, Float rhs) {
                return lhs.compareTo(rhs);
            }
        });
        li_score_short.size();
    }

    /*** this will get dataset for engagmentIndex **/
    private ArrayList<LineDataSet> getDataSetEI() {
        ArrayList<LineDataSet> dataSets = null;
        ArrayList<LineData> valueSet1 = new ArrayList<>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<String> xaxisData = new ArrayList<>();
        for (int i = 0; i < list_engagment_index.size(); i++) {
            float val = Float.valueOf(list_engagment_index.get(i).getIndexCount());
            yVals.add(new Entry(val, i));
            xaxisData.add(list_engagment_index.get(i).getTitle());
        }
        LineDataSet set1 = new LineDataSet(yVals, "Month");
        for (int j = 0; j < list_engagment_index.size(); j++) {
            LineData v1e1 = new LineData(xaxisData, set1); // Jan
            valueSet1.add(v1e1);
        }

        dataSets = new ArrayList<>();
        set1.enableDashedLine(10f, 0f, 0f);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(1f);
        set1.setCircleSize(5f);
        set1.setDrawCircleHole(true);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);
        dataSets.add(set1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValuesEI() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < list_engagment_index.size(); i++) {
            //xAxis.add((i + 1) + "");
            xAxis.add(list_engagment_index.get(i).getTitle());
        }
        return xAxis;
    }

    /*this method is used to set thumbsup data
     */
    public void setThumsupdata(JSONArray Object) {
        JSONArray jsonArray = Object;
        rv_reportlist.setVisibility(View.VISIBLE);
        li_report = baseRequest.getDataList(jsonArray, Modelreport.class);
        ad_reportList = new Ad_reportList(li_report, mContext);
        rv_reportlist.setAdapter(ad_reportList);
        rv_reportlist.setHasFixedSize(true);
        rv_reportlist.setAdapter(ad_reportList);
        rv_reportlist.setLayoutManager(new LinearLayoutManager(mContext));
    }

    public void api_getUserByType() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    modelUserList = baseRequest.getDataList(jsonArray, ModelAddActionUser.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // dialogResponsible();
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
        JsonObject object = Functions.getClient().getJsonMapObject("type", "organisation",
                "typeId", sessionParam.orgId

        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getUserByType);
    }

    /**** pop to select user ***/
    public void dialogResponsible() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tier_list);
        //DialogTierListBinding binding= DialogTierListBinding.inflate(LayoutInflater.from(mContext));
        //dialog.setContentView(binding.getRoot());
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        final RecyclerView rvList = dialog.findViewById(R.id.rv_list);
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);

        tvTitle.setText("Select User");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(new Ad_UserListDialog(modelUserList, mContext));

        rvList.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rvList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                tv_userName.setText(modelUserList.get(position).getName() + " " + modelUserList.get(position).getLastName());
                    userId = modelUserList.get(position).getId() + "";
                if (sessionParam.id.equals(userId)) {
                    tv_undoMoti.setVisibility(View.VISIBLE);

                } else {
                    tv_undoMoti.setVisibility(View.GONE);
                    tv_undo.setVisibility(View.GONE);
                    tv_undo_personalityType.setVisibility(View.GONE);
                }
                btn_personalityType_nrf.setVisibility(View.GONE);
                allKnowMemberLayout.setVisibility(View.VISIBLE);
                personaliseDataMsg.setVisibility(View.GONE);
                personaliseDataMsg.setText(modelUserList.get(position).getName() + " " + modelUserList.get(position).getLastName() + " data is personalised");
                getAllData(userId, modelUserList.get(position).getName(), modelUserList.get(position).getLastName());
                dialog.dismiss();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        ivClose.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onResume() {
        super.onResume();

        api_getUserByType();
       // userId = sessionParam.id;
        try {
            tv_nav_name.setText(sessionParam.name + " " + sessionParam.lastName);
            if (!sessionParam.profileImage.equals("")) {
                Picasso.get().load(sessionParam.profileImage).into(iv_nav_dp);
            }


        } catch (Exception e) {
            e.printStackTrace();
            getAllData(userId, " ", " ");
        }
    }

    /*API to check if the answer is distributed or not
     */
    private void api_checkAnswerDistributedOrNot() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONObject jsonObject = (JSONObject) object;
                String isCOTanswered = jsonObject.optString("isCOTanswered");
                String isQuestionDestributed = jsonObject.optString("isQuestionDestributed");
                if (isCOTanswered.equals("true")) {
                    startActivity(new Intent(mContext, Cot_Individual.class));
                } else if (isCOTanswered.equals("false") && isQuestionDestributed.equals("true")) {
                    //startActivity(new Intent(mContext, Cot_Question.class));
                    startActivity(new Intent(mContext, Cot_New_Question.class).putExtra("checklist", ""));
                } else if (isCOTanswered.equals("false") && isQuestionDestributed.equals("false")) {
                    utility.showToast(mContext, getString(R.string.ques_not_distri));
                } else if (isCOTanswered.equals("true") && isQuestionDestributed.equals("true")) {

                }
//                in = new Intent(mContext, Cot_Question.class);
//                startActivity(in);
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
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        baseRequest.callAPIGET(1, map, ConstantAPI.isCOTanswerDone);
    }

    ///////////////////////for member Personality Type////////////
    private ArrayList<BarDataSet> getDataSetPerson() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int j = 0; j < li_Person_report.size(); j++) {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(li_Person_report.get(j).getPercentage().toString()), j); // Jan
            valueSet1.add(v1e1);
        }


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Percentage");
//        barDataSet1.setColor(Color.rgb(51, 111, 179));
        barDataSet1.setColor(getResources().getColor(R.color.graph_color));
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValuesPerson() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < li_Person_report.size(); i++) {
            xAxis.add((i + 1) + "");
        }

        return xAxis;
    }


    private void ArraystoreValuePerson() {
        li_score_short_person.clear();
        for (int j = 0; j < li_Person_report.size(); j++) {
            li_score_short_person.add(Float.valueOf(li_Person_report.get(j).getPercentage()));
        }


        Object[] st = li_score_short_person.toArray();
        for (Object s : st) {
            if (li_score_short_person.indexOf(s) != li_score_short_person.lastIndexOf(s)) {
                li_score_short_person.remove(li_score_short_person.lastIndexOf(s));
            }
        }
        Collections.sort(li_score_short_person, new Comparator<Float>() {
            @Override
            public int compare(Float lhs, Float rhs) {
                return lhs.compareTo(rhs);
            }
        });

        li_score_short_person.size();
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_know_member;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
