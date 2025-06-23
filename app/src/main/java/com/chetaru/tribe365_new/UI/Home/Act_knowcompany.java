package com.chetaru.tribe365_new.UI.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Admin.ModelAdminReportDOT;
import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelDiagnosticReport;
import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelTribeometerGraph;
import com.chetaru.tribe365_new.API.Models.KnowCompany.ModelCultureIndex;
import com.chetaru.tribe365_new.API.Models.ModelDepartmentList;
import com.chetaru.tribe365_new.API.Models.ModelHappyIndex;
import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelMotivationGraph;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.AdminReportAdapter.Adapter_Admin_Report_DOT_Horizontal;
import com.chetaru.tribe365_new.Adapter.AdminReportAdapter.Adapter_Admin_Report_Diagnostics_graph_list;
import com.chetaru.tribe365_new.Adapter.AdminReportAdapter.Adapter_Admin_Report_Tribeometer_graph_list;
import com.chetaru.tribe365_new.Adapter.AdminReportAdapter.Adapter_Admin_SOT_Motivation_graph_list;
import com.chetaru.tribe365_new.Adapter.Diagnostics.Adapter_Diagnostics_graph_list;
import com.chetaru.tribe365_new.Adapter.KnowCompany.Ad_CultureIndex;
import com.chetaru.tribe365_new.Adapter.KnowCompany.Ad_EngagementIndex;
import com.chetaru.tribe365_new.Adapter.KnowCompany.Adapter_kc_happyindex;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Know.COT.ModelOfficeDepartment;
import com.chetaru.tribe365_new.UI.Know.Diagnostics.Act_Diagnostics_list;
import com.chetaru.tribe365_new.UI.Know.Diagnostics.Act_Tribeometer_list;
import com.chetaru.tribe365_new.UI.Know.Diagnostics.Act_Update_Diagnostics_list;
import com.chetaru.tribe365_new.UI.Know.Diagnostics.Act_Update_Tribeometer_list;
import com.chetaru.tribe365_new.UI.Know.SOT.Act_SOT_Questionlist;
import com.chetaru.tribe365_new.UI.Know.SOT.Act_SOT_Update_Questionlist;
import com.chetaru.tribe365_new.UI.UserInfo.Act_ProfileUser;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
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


public class Act_knowcompany extends BaseActivity implements View.OnClickListener {


    /************ initialize variable *********************/
    public static String year = "year";
    public static String month = "month";
    public static String week = "week";
    public static String day = "day";
    //nav_views
    ImageView iv_top_companylogo;
    SessionParam sessionParam;
    Utility utility;
    BaseRequest baseRequest;
    LinearLayout expandableView;
    LinearLayout ll_valueAndBelief;
    RecyclerView rv_admin_report_dot;
    ImageView iv_teamrole_downarrow, iv_personality_type_downarrow, iv_cultureStructure_downarrow, iv_motivation_downarrow, iv_diagnostic_downarrow;
    LinearLayout ll_teamRolsMain, ll_personality_typeMain, ll_sot_culture_main, ll_motivationMain, ll_diagnostic_graph_main;
    List<ModelAdminReportDOT> modelAdminReportList;
    Spinner sp_depart, sp_office;
    String path = "";
    ScrollView scrollView;

    ArrayList<String> list_office = new ArrayList<>();
    ArrayList<String> list_office_id = new ArrayList<>();
    ArrayList<String> list_depart = new ArrayList<>();
    ArrayList<String> list_depart_id = new ArrayList<>();
    String officeId = "0", depart_id = "0";
    boolean flag = true;
    int iCurrentSelection;
    ArrayList<ModelDepartmentList> li_ModelDepartmentList = new ArrayList<>();
    ArrayList<ModelOfficeDepartment> list_officedepartment = new ArrayList<>();

    //Team role block view
    TextView tv_maperkey_shape, tv_maperkey_coordinator, tv_maperkey_Completer, tv_maperkey_teamwork, tv_maperkey_Implementer, tv_maperkey_Monitor, tv_maperkey_plant, tv_maperkey_resource, tv_txt_shaper, tv_txt_coordinator, tv_txt_implementer, tv_txt_complete, tv_txt_Monitor, tv_txt_Team, tv_txt_plant, tv_txt_resource;

    //Personality type views
    TextView tv_adm_cot_funt_NF_rating, tv_adm_cot_funt_ST_rating, tv_adm_cot_funt_SF_rating, tv_adm_cot_funt_NT_rating;
    TextView tv_box_one_st, tv_box_two_sf, tv_box_three_nt, tv_box_four_nf;

    //culture structure
    TextView tv_PERSON, tv_PERSON_point, tv_POWER, tv_POWER_point, tv_ROLE, tv_ROLE_point, tv_TRIBE, tv_TRIBE_point, tv_summylist, tv_org_name;
    ImageView img_org;
    Button btn_sot_review, btn_sot_review_nrf;
    TextView tv_nrf_cultureStructure;


    //motivation graph
    ArrayList<ModelMotivationGraph> list_motivation_graph = new ArrayList<>();
    ArrayList<Float> li_score_short = new ArrayList<>();
    RecyclerView rv_sot_motivation_graph;
    BarChart chart;

    //Diagnostic graph
    ArrayList<ModelDiagnosticReport> li_Diagnostic_report = new ArrayList<>();
    BarChart chartD;
    RecyclerView rv_diagnostic_graph_list;
    LinearLayout ll_diagnostic_msg, lyt_diagnostics;
    TextView tv_diagnosticsBack, tv_undo, tv_personalityBack;
    Button btn_undo_diagnostics_nrf;
    boolean subgraph = false;
    boolean perltySubgraph = false;


    JSONArray mainDiagnosticArray;
    JSONArray mainPersonalityArray;
    JSONArray mainTribographArray;
    JSONArray mainTriboSubgraphArray;

    //Tribometer graph
    ImageView iv_tribometer_downarrow;
    LinearLayout ll_tribeometer_main;
    TextView tv_tribeometerBack;
    TextView tv_tribometer_subgraph_back;
    BarChart chartT;
    int triboCategoryId = -1;
    boolean tribometerSubgraph = false;
    boolean tribometerSubsubgraph = false;
    RecyclerView rv_tribometer_graphlist;
    LinearLayout ll_tribeometer_msg;
    ArrayList<ModelTribeometerGraph> li_tribeometer = new ArrayList<>();
    TextView tv_undo_tribometer;
    Button btn_undo_tribometer_nrf;


    //Happy index
    ArrayList<ModelHappyIndex> li_happIndex = new ArrayList<>();
    LinearLayout ll_happyindex_msg, ll_happyindex_graph_main, lyt_happyindex;
    ImageView iv_happy_index_downarrow;
    TextView tv_nrf_happyIndex, tv_happyIndexBack;
    BarChart chartHi;
    RecyclerView rv_happyindex_graph_list;
    JSONArray yearJson;
    JSONArray monthJson;
    JSONArray weekJson;
    JSONArray dayJson;

    //culture Index
    ImageView iv_culture_indexDownArrow;
    LinearLayout ll_culture_index, ll_cultureindex_graph_main, lyt_cultureindex, ll_cultureindex_msg;
    LineChart chart_culture_index;
    TextView tv_cultureindex, tv_nrf_value_culture_index;
    RecyclerView rv_cultureindex_graph_list;
    ArrayList<ModelCultureIndex> list_culture_index = new ArrayList<>();

    //engagement Index
    ImageView iv_engagement_indexDownArrow;
    LinearLayout ll_engagement_index_graph_main, lyt_engagement_index, ll_engagement_index_msg;
    LineChart chart_engagement_index;
    TextView tv_engagement_index, tv_nrf_engagement_index;
    RecyclerView rv_engagement_index_graph_list;
    ArrayList<ModelCultureIndex> list_engagment_index = new ArrayList<>();

    boolean dotStatus = false;
    boolean cotTeamRoleStatus = false;
    boolean cotPersonalityStatus = false;
    boolean diagnosticStatus = false;
    boolean cultureStatus = false;
    boolean sotMotivationStatus = false;
    boolean tribometerStatus = false;
    Boolean IsUserFilledAnswer = false;
    boolean subCategoryTrue = false;

    Boolean isTribeometerAnsDone = false;
    Boolean isDiagnosticAnsDone = false;
    Boolean personalityTypeStatus = false;
    String hi_type = month;

    String selectedYear = "";
    String selectedyear = "";
    String selectedmonth = "";
    String selectedWeek = "";

    TextView tv_nrf_value, tv_nrf_motivation, tv_nrf_diagnostic, tv_nrf_teamrole, tv_nrf_tribometer, tv_nrf_thumsUp, tv_nrf_personalityType;

    //clone with Diagnostics for Personality type
    BarChart Person_chart;
    // @BindView(R.id.ll_diagno_help)
    LinearLayout tv_diagno_help;
    String org_name = "";
    ArrayList<ModelDiagnosticReport> li_Person_report = new ArrayList<>();
    ArrayList<Float> li_score_short_person = new ArrayList<>();
    /*@BindView(R.id.rv_person_graph_list)*/ RecyclerView rv_diagnostic_graph_list_person;
    @BindView(R.id.ll_diagnostic_graph)
    LinearLayout ll_diagnostic_graph;

    @BindView(R.id.belief_card_ll)
    CardView belief_card_ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.act_knowcompany);
        ButterKnife.bind(this);
        ll_tribeometer_msg = findViewById(R.id.ll_tribeometer_msg);
        ll_tribeometer_main = findViewById(R.id.ll_tribeometer_main);
        tv_tribeometerBack = findViewById(R.id.tv_tribometer_back);
        tv_tribeometerBack.setOnClickListener(this);
        tv_tribometer_subgraph_back = findViewById(R.id.tv_tribometer_subgraph_back);
        tv_tribometer_subgraph_back.setOnClickListener(this);

        rv_tribometer_graphlist = findViewById(R.id.rv_tribometer_graphlist);
        ll_diagnostic_graph = findViewById(R.id.ll_diagnostic_graph);
        rv_diagnostic_graph_list_person = findViewById(R.id.rv_person_graph_list);
        tv_diagno_help = findViewById(R.id.ll_diagno_help);

        chartT = findViewById(R.id.chartT);
        ////////personality Type
        Person_chart = findViewById(R.id.person_chart);
        tv_personalityBack = findViewById(R.id.tv_personality_back);
        tv_personalityBack.setOnClickListener(this);


        init();
        serSessionParam();
    }

    private void serSessionParam() {
        if (sessionParam.loginVersion == 3) {
            belief_card_ll.setVisibility(View.GONE);
        } else {
            belief_card_ll.setVisibility(View.VISIBLE);
        }
    }

    /*used to initialise all the views*/
    public void init() {
        sessionParam = new SessionParam(mContext);

        tv_undo = findViewById(R.id.tv_undo);
        btn_undo_diagnostics_nrf = findViewById(R.id.btn_undo_diagnostics_nrf);
        tv_undo_tribometer = findViewById(R.id.tv_undo_tribometer);

        iv_top_companylogo = findViewById(R.id.iv_top_companylogo);

        sp_office = findViewById(R.id.sp_office);
        sp_depart = findViewById(R.id.sp_depart);

        rv_admin_report_dot = findViewById(R.id.rv_admin_report_dot);

        tv_nrf_value = findViewById(R.id.tv_nrf_value);
        tv_nrf_motivation = findViewById(R.id.tv_nrf_motivation);
        tv_nrf_diagnostic = findViewById(R.id.tv_nrf_diagnostic);

        tv_nrf_thumsUp = findViewById(R.id.tv_nrf_thumsUp);
        tv_nrf_personalityType = findViewById(R.id.tv_nrf_personalityType);
        tv_nrf_tribometer = findViewById(R.id.tv_nrf_tribometer);
        btn_undo_tribometer_nrf = findViewById(R.id.btn_undo_tribometer_nrf);

        expandableView = findViewById(R.id.expandableView);
        ll_valueAndBelief = findViewById(R.id.ll_valueAndBelief);


        scrollView = findViewById(R.id.scrollView);

        //when user clicks on happy index we will scroll to last index
        if (getIntent().getStringExtra("path") != null) {
            path = getIntent().getStringExtra("path");

        }


        iv_teamrole_downarrow = findViewById(R.id.iv_teamrole_downarrow);
        iv_personality_type_downarrow = findViewById(R.id.iv_personality_type_downarrow);
        ll_personality_typeMain = findViewById(R.id.ll_personality_typeMain);
        ll_teamRolsMain = findViewById(R.id.ll_teamRolsMain);
        iv_cultureStructure_downarrow = findViewById(R.id.iv_cultureStructure_downarrow);
        ll_sot_culture_main = findViewById(R.id.ll_sot_culture_main);
        iv_motivation_downarrow = findViewById(R.id.iv_motivation_downarrow);
        ll_motivationMain = findViewById(R.id.ll_motivationMain);
        iv_diagnostic_downarrow = findViewById(R.id.iv_diagnostic_downarrow);
        ll_diagnostic_graph_main = findViewById(R.id.ll_diagnostic_graph_main);
        iv_tribometer_downarrow = findViewById(R.id.iv_tribometer_downarrow);
        iv_happy_index_downarrow = findViewById(R.id.iv_happy_index_downarrow);
        ll_tribeometer_main = findViewById(R.id.ll_tribeometer_main);

        iv_culture_indexDownArrow = findViewById(R.id.iv_culture_indexDownArrow);
        ll_culture_index = findViewById(R.id.ll_culture_index);
        ll_cultureindex_msg = findViewById(R.id.ll_cultureindex_msg);
        ll_cultureindex_graph_main = findViewById(R.id.ll_cultureindex_graph_main);
        lyt_cultureindex = findViewById(R.id.lyt_cultureindex);
        chart_culture_index = findViewById(R.id.chart_culture_index);
        rv_cultureindex_graph_list = findViewById(R.id.rv_cultureindex_graph_list);
        tv_nrf_value_culture_index = findViewById(R.id.tv_nrf_value_culture_index);

        iv_engagement_indexDownArrow = findViewById(R.id.iv_engagement_indexDownArrow);
        //ll_culture_index = findViewById(R.id.ll_culture_index);
        ll_engagement_index_msg = findViewById(R.id.ll_engagement_index_msg);
        ll_engagement_index_graph_main = findViewById(R.id.ll_engagement_index_graph_main);
        lyt_engagement_index = findViewById(R.id.lyt_engagement_index);
        chart_engagement_index = findViewById(R.id.chart_engagement_index);
        rv_engagement_index_graph_list = findViewById(R.id.rv_engagement_index_graph_list);
        tv_nrf_engagement_index = findViewById(R.id.tv_nrf_engagement_index);

        iv_personality_type_downarrow.setOnClickListener(this);
        iv_cultureStructure_downarrow.setOnClickListener(this);
        iv_teamrole_downarrow.setOnClickListener(this);
        iv_motivation_downarrow.setOnClickListener(this);
        iv_diagnostic_downarrow.setOnClickListener(this);
        iv_tribometer_downarrow.setOnClickListener(this);
        iv_happy_index_downarrow.setOnClickListener(this);
        iv_engagement_indexDownArrow.setOnClickListener(this);
        ll_culture_index.setOnClickListener(this);
        tv_undo.setOnClickListener(this);
        btn_undo_diagnostics_nrf.setOnClickListener(this);
        tv_undo_tribometer.setOnClickListener(this);
        btn_undo_tribometer_nrf.setOnClickListener(this);

        //team role view initialization
        tv_maperkey_shape = findViewById(R.id.tv_maperkey_shape);
        tv_maperkey_coordinator = findViewById(R.id.tv_maperkey_coordinator);
        tv_maperkey_Completer = findViewById(R.id.tv_maperkey_Completer);
        tv_maperkey_teamwork = findViewById(R.id.tv_maperkey_teamwork);
        tv_maperkey_Implementer = findViewById(R.id.tv_maperkey_Implementer);
        tv_maperkey_Monitor = findViewById(R.id.tv_maperkey_Monitor);
        tv_maperkey_plant = findViewById(R.id.tv_maperkey_plant);
        tv_maperkey_resource = findViewById(R.id.tv_maperkey_resource);
        tv_txt_shaper = findViewById(R.id.txt_Shaper);
        tv_txt_coordinator = findViewById(R.id.txt_Coordinator);
        tv_txt_implementer = findViewById(R.id.txt_Implementer);
        tv_txt_complete = findViewById(R.id.txt_Completer);
        tv_txt_Monitor = findViewById(R.id.txt_Monitor);
        tv_txt_Team = findViewById(R.id.txt_Team);
        tv_txt_plant = findViewById(R.id.txt_Plant);
        tv_txt_resource = findViewById(R.id.txt_Resource);

        //personlaityType views initialization
        //  tv_adm_cot_funt_NF_rating = findViewById(R.id.tv_adm_cot_funt_NF_rating);
        //  tv_adm_cot_funt_ST_rating = findViewById(R.id.tv_adm_cot_funt_ST_rating);
        // tv_adm_cot_funt_SF_rating = findViewById(R.id.tv_adm_cot_funt_SF_rating);
        //  tv_adm_cot_funt_NT_rating = findViewById(R.id.tv_adm_cot_funt_NT_rating);

        //for personality part
        //tv_box_one_st = findViewById(R.id.tv_box_one_st);
        //tv_box_two_sf = findViewById(R.id.tv_box_two_sf);
        //tv_box_three_nt = findViewById(R.id.tv_box_three_nt);
        //tv_box_four_nf = findViewById(R.id.tv_box_four_nf);

        //culture structure view
        tv_PERSON = findViewById(R.id.tv_PERSON);
        tv_PERSON_point = findViewById(R.id.tv_PERSON_point);
        tv_POWER = findViewById(R.id.tv_POWER);
        tv_POWER_point = findViewById(R.id.tv_POWER_point);
        tv_ROLE = findViewById(R.id.tv_ROLE);
        tv_ROLE_point = findViewById(R.id.tv_ROLE_point);
        tv_TRIBE = findViewById(R.id.tv_TRIBE);
        tv_TRIBE_point = findViewById(R.id.tv_TRIBE_point);
        tv_summylist = findViewById(R.id.tv_summylist);
        tv_org_name = findViewById(R.id.tv_org_name);
        tv_nrf_cultureStructure = findViewById(R.id.tv_nrf_cultureStructure);
        img_org = findViewById(R.id.img_org);
        btn_sot_review = findViewById(R.id.btn_sot_review);
        btn_sot_review.setOnClickListener(this);
        btn_sot_review_nrf = findViewById(R.id.btn_sot_review_nrf);
        btn_sot_review_nrf.setOnClickListener(this);

        //motivation diagnostic view

        rv_sot_motivation_graph = findViewById(R.id.rv_sot_motivation_graph);
        chart = findViewById(R.id.chart);

        //diagnostic views
        chartD = findViewById(R.id.chartD);
        rv_diagnostic_graph_list = findViewById(R.id.rv_diagnostic_graph_list);
        ll_diagnostic_msg = findViewById(R.id.ll_diagnostic_msg);
        lyt_diagnostics = findViewById(R.id.lyt_diagnostics);
        tv_diagnosticsBack = findViewById(R.id.tv_diagnosticsBack);
        tv_diagnosticsBack.setOnClickListener(this);
        chartD.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // api_diagnosticSubGraph()
                if (!subgraph) {
                    subgraph = true;
                    api_diagnosticSubGraph(officeId, depart_id, li_Diagnostic_report.get(e.getXIndex()).getCategoryId());
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //happyindex
        chartHi = findViewById(R.id.chart_hi);
        iv_happy_index_downarrow = findViewById(R.id.iv_happy_index_downarrow);
        tv_nrf_happyIndex = findViewById(R.id.tv_nrf_happyIndex);
        ll_happyindex_graph_main = findViewById(R.id.ll_happyindex_graph_main);
        tv_happyIndexBack = findViewById(R.id.tv_happyIndexBack);
        rv_happyindex_graph_list = findViewById(R.id.rv_happyindex_graph_list);
        ll_happyindex_msg = findViewById(R.id.ll_happyindex_msg);
        lyt_happyindex = findViewById(R.id.lyt_happyindex);
        tv_happyIndexBack.setOnClickListener(this);
        chartHi.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            Entry enothingSelected;

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // api_diagnosticSubGraph()
                chartHi.setHighlightEnabled(false);
                enothingSelected = e;

                if (hi_type.equals(year)) {
                    // Toast.makeText(currentAct, "month api", Toast.LENGTH_SHORT).show();
                    hi_type = month;
                    api_month(officeId, depart_id, li_happIndex.get(e.getXIndex()).getYear());
                    selectedyear = li_happIndex.get(e.getXIndex()).getYear();

                } else if (hi_type.equals(month)) {
                    // Toast.makeText(currentAct, "week api", Toast.LENGTH_SHORT).show();
                   /* String month = null,year=null;
                   String  monthName= li_happIndex.get(e.getXIndex()).getmMonthName();
                    String splitValue[] = monthName.split(" ");
                    for (int i = 0; i < splitValue.length; i++) {
                        if (i==0) {
                            month = splitValue[i];
                        }else{
                            year=splitValue[i];
                        }
                    }*/

                    hi_type = week;
                    // selectedmonth = e.getXIndex() + 1 + "";
                    selectedmonth = li_happIndex.get(e.getXIndex()).getmMonth();
                    selectedyear = li_happIndex.get(e.getXIndex()).getYear();
                    if (selectedmonth != null) {
                        api_week(officeId, depart_id, li_happIndex.get(e.getXIndex()).getYear(), li_happIndex.get(e.getXIndex()).getmMonth());
                    } else {
                        Toast.makeText(currentAct, "Month not be Null", Toast.LENGTH_SHORT).show();
                    }

                } else if (hi_type.equals(week)) {
                    //Toast.makeText(currentAct, "day api", Toast.LENGTH_SHORT).show();
                    hi_type = day;
                    selectedWeek = e.getXIndex() + 1 + "";
                    if (selectedmonth != null && selectedWeek != null) {
                        api_day(officeId, depart_id, li_happIndex.get(e.getXIndex()).getYear(), li_happIndex.get(e.getXIndex()).getmMonthName());
                    } else {
                        Toast.makeText(currentAct, "Month not be Null", Toast.LENGTH_SHORT).show();
                    }
                } else if (hi_type.equals("day")) {
                    //Toast.makeText(currentAct, "do nothing", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected() {
               /* if (hi_type.equals(year)) {
                    //Toast.makeText(currentAct, "month api", Toast.LENGTH_SHORT).show();
                    hi_type = month;
                    api_month(officeId, depart_id, li_happIndex.get(enothingSelected.getXIndex()).getYear());
                } else */
                if (hi_type.equals("month")) {
                    //Toast.makeText(currentAct, "week api", Toast.LENGTH_SHORT).show();
                    hi_type = week;
                    if (selectedmonth != null) {
                        api_week(officeId, depart_id, li_happIndex.get(enothingSelected.getXIndex()).getYear(), li_happIndex.get(enothingSelected.getXIndex()).getmMonthName());
                    } else {
                        Toast.makeText(currentAct, "Month not be Null", Toast.LENGTH_SHORT).show();
                    }
                } else if (hi_type.equals(week)) {
                    // Toast.makeText(currentAct, "day api", Toast.LENGTH_SHORT).show();
                    hi_type = day;
                } else if (hi_type.equals(day)) {
                    // Toast.makeText(currentAct, "do nothing", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ll_valueAndBelief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dotStatus) {
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
            }
        });

        utility = new Utility();
        sessionParam = new SessionParam(mContext);

        if (!sessionParam.organisation_logo.equals("")) {
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rv_admin_report_dot.setLayoutManager(layoutManager);


        list_office.add("All Offices");
        list_depart.add("All Departments");
        list_office_id.add("0");
        list_depart_id.add("0");

        call_Office_DepartmentApi();
        //getAllData();
        callSpinnerDepartment();

        //get personality sub graph
        Person_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // api_diagnosticSubGraph()
                if (!perltySubgraph) {
                    perltySubgraph = true;
                    api_personalityTypeSubgraph(officeId, depart_id, li_Person_report.get(e.getXIndex()).getCategoryId());
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        chartT.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (!tribometerSubgraph) {
                    tribometerSubgraph = true;

                    api_trireoMeterSubdgraph(officeId, depart_id, li_tribeometer.get(e.getXIndex()).getCategoryId());
                    triboCategoryId = li_tribeometer.get(e.getXIndex()).getCategoryId();
                } else {

                    if (!tribometerSubsubgraph && tribometerSubgraph) {
                        tribometerSubsubgraph = true;
                        //  api_tribeoMeterSubSubgarph(officeId, depart_id,  li_tribeometer.get(e.getXIndex()).getCategoryId(), li_tribeometer.get(e.getXIndex()).getSubCategoryId());

                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }


    /*API call to get all data of the organisation users
     */
    public void getAllData() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

                try {
                    Gson gson = new Gson();
                    JSONObject jsonObjectMain = new JSONObject(object.toString());
                    JSONArray jsonArrayDotGraph = jsonObjectMain.getJSONArray("getDOTreportGraph");
                    JSONObject jo_TeamRole = jsonObjectMain.getJSONObject("getCOTteamRoleMapReport");

                    JSONObject jo_CultureStructure = jsonObjectMain.getJSONObject("getSOTcultureStructureReport");
                    JSONObject jo_status = jsonObjectMain.getJSONObject("orgStatus");

                    JSONArray ja_CultureIndexgraph = jsonObjectMain.getJSONArray("cultureIndex");
                    JSONArray ja_EngagmentIndexgraph = jsonObjectMain.getJSONArray("engagementIndex");
                    JSONArray ja_Motivationgraph = jsonObjectMain.getJSONArray("getSOTmotivationReport");
                    JSONArray ja_Diagnosticgraph = jsonObjectMain.getJSONArray("getDiagnosticReportForGraph");
                    JSONArray ja_Tribometer = jsonObjectMain.getJSONArray("getTribeometerReportForGraph");
                    JSONArray jo_PersonalityType = jsonObjectMain.getJSONArray("getCOTpersonalityType");
                    // JSONObject jo_PersonalityType = jsonObjectMain.getJSONObject("getCOTpersonalityType");
                    // yearJson = jsonObjectMain.getJSONArray("getHappyIndexYearGraphCount");
                    monthJson = jsonObjectMain.getJSONArray("getHappyIndexMonthGraphCount");

                    mainDiagnosticArray = ja_Diagnosticgraph;
                    mainPersonalityArray = jo_PersonalityType;
                    mainTribographArray = ja_Tribometer;
                    modelAdminReportList = baseRequest.getDataList(jsonArrayDotGraph, ModelAdminReportDOT.class);
                    //modelAdminReportDOT = gson.fromJson(jsonArrayDotGraph.toString(),ModelAdminReportDOT.class);
                    //Toast.makeText(currentAct, "check data", Toast.LENGTH_SHORT).show();

                    Adapter_Admin_Report_DOT_Horizontal adapter_admin_report_dot_horizontal = new Adapter_Admin_Report_DOT_Horizontal(modelAdminReportList, mContext);
                    rv_admin_report_dot.setAdapter(adapter_admin_report_dot_horizontal);
                    //expandableView.setVisibility(View.VISIBLE);

                    //expandableView.setVisibility(View.GONE);
                    ll_motivationMain.setVisibility(View.GONE);
                    ll_teamRolsMain.setVisibility(View.GONE);
                    ll_sot_culture_main.setVisibility(View.GONE);
                    ll_diagnostic_graph_main.setVisibility(View.GONE);
                    ll_personality_typeMain.setVisibility(View.GONE);
                    ll_tribeometer_main.setVisibility(View.GONE);

                    btn_sot_review_nrf.setVisibility(View.GONE);
                    tv_nrf_cultureStructure.setVisibility(View.GONE);
                    perltySubgraph = false;
                    tribometerSubgraph = false;
                    tribometerSubsubgraph = false;
                    tv_personalityBack.setVisibility(View.GONE);
                    tv_tribeometerBack.setVisibility(View.GONE);
                    tv_tribometer_subgraph_back.setVisibility(View.GONE);
                    //getting array to show no record in culture structure
//                    JSONObject jsonObject = jo_CultureStructure;
//                    JSONArray jsonArray1 = jsonObject.optJSONArray("sotSummaryDetailArray");

                    cultureStatus = ja_Tribometer.length() > 0;
                    if (modelAdminReportList.size() > 0) {
                        dotStatus = true;
                        expandableView.setVisibility(View.VISIBLE);
                    } else {
                        dotStatus = false;
                    }
                    sotMotivationStatus = ja_Motivationgraph.length() > 0;
                    diagnosticStatus = jo_status.optBoolean("diagnosticStatus");
                    tribometerStatus = jo_status.optBoolean("tribeometerStatus");
                    isTribeometerAnsDone = jo_status.optBoolean("isTribeometerAnsDone");
                    isDiagnosticAnsDone = jo_status.optBoolean("isDiagnosticAnsDone");
                    personalityTypeStatus = jo_status.optBoolean("personalityTypeStatus");
                    tv_nrf_value.setVisibility(View.GONE);
                    tv_nrf_motivation.setVisibility(View.GONE);
                    //tv_nrf_teamrole.setVisibility(View.GONE);
                    tv_nrf_diagnostic.setVisibility(View.GONE);
                    tv_nrf_tribometer.setVisibility(View.GONE);
                    btn_undo_tribometer_nrf.setVisibility(View.GONE);
                    btn_undo_diagnostics_nrf.setVisibility(View.GONE);

                    tv_nrf_personalityType.setVisibility(View.GONE);


                    setCultureIndex(ja_CultureIndexgraph);
                    setEngagmentIndex(ja_EngagmentIndexgraph);
                    setTeamRoledata(jo_TeamRole);
                    setPersonalityTypeData(jo_PersonalityType);
                    setCultureStructureData(jo_CultureStructure);
                    setMotivationgraph(ja_Motivationgraph);
                    setDiagnosticData(ja_Diagnosticgraph);
                    setTribometerData(ja_Tribometer);
                    setHappyIndex(monthJson);


                    if (path.contains("happyIndex")) {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                    if (ja_CultureIndexgraph.length() > 0) {
                        ll_cultureindex_graph_main.setVisibility(View.VISIBLE);
                    }
                    if (ja_EngagmentIndexgraph.length() > 0) {
                        ll_engagement_index_graph_main.setVisibility(View.VISIBLE);
                    }
                    if (jo_TeamRole.length() > 0) {
                        ll_teamRolsMain.setVisibility(View.VISIBLE);
                    }
                    if (jo_PersonalityType.length() > 0) {
                        ll_personality_typeMain.setVisibility(View.VISIBLE);
                    }
                    if (jo_CultureStructure.length() > 0) {
                        ll_sot_culture_main.setVisibility(View.VISIBLE);
                    }
                    if (ja_Motivationgraph.length() > 0) {
                        ll_motivationMain.setVisibility(View.VISIBLE);
                    }
                    if (ja_Diagnosticgraph.length() > 0) {
                        ll_diagnostic_graph_main.setVisibility(View.VISIBLE);
                    }
                    if (ja_Tribometer.length() > 0) {
                        ll_tribeometer_main.setVisibility(View.VISIBLE);
                    }
                    if (monthJson.length() > 0) {
                        ll_happyindex_graph_main.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //ModelMainKnowCompany modelMainKnowCompany = gson.fromJson(object.toString(),ModelMainKnowCompany.class);
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
        if (depart_id.isEmpty()) {
            depart_id = sessionParam.departmentId;
        }
        if (officeId.isEmpty()) {
            officeId = sessionParam.officeId;
        }
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId, "officeId", officeId, "departmentId", depart_id
                //at orgid 29 i found department so m using it temporarily
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getOrgDashboardReportWithFilter);
    }

    /*this method is used to set Teamrole data
     */
    public void setTeamRoledata(JSONObject jsonObject) {
        JSONObject object = jsonObject;
        String shaper = " ";
        String coordinator = " ";
        String completerFinisher = " ";
        String teamworker = " ";
        String implementer = " ";
        String monitorEvaluator = " ";
        String plant = " ";
        String resourceInvestigator = " ";
        shaper = jsonObject.optString("shaper");
        coordinator = jsonObject.optString("coordinator");
        completerFinisher = jsonObject.optString("completerFinisher");
        teamworker = jsonObject.optString("teamworker");
        implementer = jsonObject.optString("implementer");
        monitorEvaluator = jsonObject.optString("monitorEvaluator");
        plant = jsonObject.optString("plant");
        resourceInvestigator = jsonObject.optString("resourceInvestigator");
        tv_maperkey_shape.setText(shaper + "%");
        tv_maperkey_coordinator.setText(coordinator + "%");
        tv_maperkey_Completer.setText(completerFinisher + "%");
        tv_maperkey_teamwork.setText(teamworker + "%");
        tv_maperkey_Implementer.setText(implementer + "%");
        tv_maperkey_Monitor.setText(monitorEvaluator + "%");
        tv_maperkey_plant.setText(plant + "%");
        tv_maperkey_resource.setText(resourceInvestigator + "%");
        JSONObject jsonObject1 = jsonObject.optJSONObject("mapersArray");

        tv_txt_shaper.setText(jsonObject1.optString("shaper"));
        tv_txt_coordinator.setText(jsonObject1.optString("coordinator"));
        tv_txt_implementer.setText(jsonObject1.optString("implementer"));
        tv_txt_complete.setText(jsonObject1.optString("completerFinisher"));
        tv_txt_Monitor.setText(jsonObject1.optString("monitorEvaluator"));
        tv_txt_Team.setText(jsonObject1.optString("teamworker"));
        tv_txt_plant.setText(jsonObject1.optString("plant"));
        tv_txt_resource.setText(jsonObject1.optString("resourceInvestigator"));
        //ll_teamRolsMain.setVisibility(View.VISIBLE);

    }

    /*this method is used to set personality type data*/
    public void setPersonalityTypeData(JSONObject jsonObjectD) {
        JSONObject jsonObject = jsonObjectD;
        //ll_parent.setVisibility(View.VISIBLE);
        String st = jsonObject.optString("st");
        String sf = jsonObject.optString("sf");
        String nf = jsonObject.optString("nf");
        String nt = jsonObject.optString("nt");
        tv_adm_cot_funt_NF_rating.setText(nf + "%");
        tv_adm_cot_funt_ST_rating.setText(st + "%");
        tv_adm_cot_funt_SF_rating.setText(sf + "%");
        tv_adm_cot_funt_NT_rating.setText(nt + "%");
       /* tv_box_one_st.setText(jsonObject.optString("stValue"));
        tv_box_two_sf.setText(jsonObject.optString("sfValue"));
        tv_box_three_nt.setText(jsonObject.optString("ntValue"));
        tv_box_four_nf.setText(jsonObject.optString("nfValue"));*/
        //ll_personality_typeMain.setVisibility(View.VISIBLE);
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
            ll_diagnostic_graph.setVisibility(View.VISIBLE);

            if (sessionParam.role.equals("3")) {
                tv_undo.setVisibility(View.VISIBLE);
            }
            Adapter_Diagnostics_graph_list adap_diag_graphlist = new Adapter_Diagnostics_graph_list(li_Person_report, li_score_short_person, mContext);
            rv_diagnostic_graph_list_person.setHasFixedSize(true);
            rv_diagnostic_graph_list_person.setAdapter(adap_diag_graphlist);
            rv_diagnostic_graph_list_person.setLayoutManager(new LinearLayoutManager(mContext));
            rv_diagnostic_graph_list_person.setNestedScrollingEnabled(true);
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

    /* this method is used to set cultureStructure data*/
    public void setCultureStructureData(JSONObject jsonObjectD) {
        JSONObject jsonObject = jsonObjectD;
        ArrayList<String> li_count = new ArrayList<>();
        ArrayList<String> li_title = new ArrayList<>();
        ArrayList<String> li_summary = new ArrayList<>();
        String IsQuestionnaireAnswerFilled = jsonObject.optString("IsQuestionnaireAnswerFilled");
        IsUserFilledAnswer = jsonObject.optBoolean("IsUserFilledAnswer");
        if (IsQuestionnaireAnswerFilled.equals("false")) {
            //  Warinig_Dailogbox();
            // ll_sot_culture.setVisibility(View.GONE);
            ll_sot_culture_main.setVisibility(View.GONE);
        }
        JSONArray jsonArray = jsonObject.optJSONArray("sotDetailArray");
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object1 = jsonArray.optJSONObject(i);
                String id = object1.optString("id");
                String type = object1.optString("type");
                String title = object1.optString("title");
                String imgUrl = object1.optString("imgUrl");
                String SOTCount = object1.optString("SOTCount");
                li_count.add(SOTCount);
                li_title.add(title);
//              li_culture_list.get(i).setTitle(title);
//              li_culture_list.get(i).setCount(SOTCount);
            }
            for (int i = 0; i < li_count.size(); i++) {
                tv_PERSON.setText(li_title.get(0));
                tv_PERSON_point.setText(li_count.get(0));

                tv_POWER.setText(li_title.get(1));
                tv_POWER_point.setText(li_count.get(1));

                tv_ROLE.setText(li_title.get(2));
                tv_ROLE_point.setText(li_count.get(2));

                tv_TRIBE.setText(li_title.get(3));
                tv_TRIBE_point.setText(li_count.get(3));
            }
        } else {
            tv_PERSON_point.setText("0");
            tv_POWER_point.setText("0");
            tv_ROLE_point.setText("0");
            tv_TRIBE_point.setText("0");
        }
        String id = " ";
        String title = " ";
        String imgUrl = " ";
        String SOTCount = " ";
        li_summary.clear();
//      TextView text;
        JSONArray jsonArray1 = jsonObject.optJSONArray("sotSummaryDetailArray");
        if (jsonArray1.length() == 0) {

        }
        for (int i = 0; i < jsonArray1.length(); i++) {
            JSONObject object1 = jsonArray1.optJSONObject(i);

            id = object1.optString("id");
            String type = object1.optString("type");
            title = object1.optString("title");
            imgUrl = object1.optString("imgUrl");
            SOTCount = object1.optString("SOTCount");
            JSONArray jsonArray2 = object1.optJSONArray("summary");
            for (int j = 0; j < jsonArray2.length(); j++) {
                JSONObject object2 = jsonArray2.optJSONObject(j);
                String summary = object2.optString("summary");
                li_summary.add(summary);
            }
            String st_sum = "", stsmy = "";
            TextView text = null;
            for (int k = 0; k < li_summary.size(); k++) {
                text = new TextView(mContext);
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                        tv_summylist.setText("\u2022" + " " + li_summary.get(k).toString() + "\n");
                text.setTextSize(14);
//                        lyt_summy.addView(text);
                stsmy = stsmy + " " + "\u2022" + "  " + li_summary.get(k) + "\n\n";
            }
            li_summary.clear();
            tv_summylist.setText(stsmy);
            tv_summylist.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            tv_org_name.setText(title);
            if (!imgUrl.equals("")) {
                Picasso.get().load(imgUrl).into(img_org);
            }
        }
        //to keep it open
//        if (tv_PERSON_point.getText().toString().equals("0") &&
//                tv_POWER_point.getText().toString().equals("0") &&
//                tv_ROLE_point.getText().toString().equals("0") &&
//                tv_TRIBE_point.getText().toString().equals("0")) {
//            if (tv_nrf_cultureStructure.getVisibility() == View.VISIBLE) {
//                ll_cultureindex_graph_main.setVisibility(View.VISIBLE);
//                tv_nrf_cultureStructure.setVisibility(View.GONE);
//                btn_sot_review_nrf.setVisibility(View.GONE);
//            } else {
//                tv_nrf_cultureStructure.setVisibility(View.VISIBLE);
//                btn_sot_review_nrf.setVisibility(View.VISIBLE);
//            }
//        } else {
//            ll_sot_culture_main.setVisibility(View.VISIBLE);
//        }

    }

    /*this method is used to set motivation graph and list.*/
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
        //ll_motivationMain.setVisibility(View.VISIBLE);
//        if (sotMotivationStatus) {
//            if (ll_motivationMain.getVisibility() == View.VISIBLE) {
//                ll_motivationMain.setVisibility(View.GONE);
//            } else {
//                ll_motivationMain.setVisibility(View.VISIBLE);
//            }
//        } else {
//            if (tv_nrf_motivation.getVisibility() == View.VISIBLE) {
//                tv_nrf_motivation.setVisibility(View.GONE);
//            } else {
//                tv_nrf_motivation.setVisibility(View.VISIBLE);
//            }
//        }

    }

    /*this method is used to set diagnostic data*/
    public void setDiagnosticData(JSONArray jsonArrayD) {
        JSONArray jsonArray = jsonArrayD;
        li_Diagnostic_report = baseRequest.getDataList(jsonArray, ModelDiagnosticReport.class);
        //ModelDiagnosticReport
        //  list_motivation_graph = baseRequest.getDataList(jsonArray, ModelMotivationGraph.class);
        // ll_diagnostic_graph.setVisibility(View.VISIBLE);
        //ArraystoreValueD();
        ll_diagnostic_msg.setVisibility(View.GONE);
        lyt_diagnostics.setVisibility(View.VISIBLE);
        Adapter_Admin_Report_Diagnostics_graph_list adap_diag_graphlist = new Adapter_Admin_Report_Diagnostics_graph_list(li_Diagnostic_report, mContext);
        rv_diagnostic_graph_list.setHasFixedSize(true);
        rv_diagnostic_graph_list.setAdapter(adap_diag_graphlist);
        rv_diagnostic_graph_list.setLayoutManager(new LinearLayoutManager(mContext));
//Size of recycler view
        ViewGroup.LayoutParams params = rv_diagnostic_graph_list.getLayoutParams();
        params.height = 90 * li_Diagnostic_report.size();
        rv_diagnostic_graph_list.requestLayout();

        BarData data = new BarData(getXAxisValuesD(), getDataSetD());

        YAxis yLeft = chartD.getAxisLeft();
        //Set the minimum and maximum bar lengths as per the values that they represent
        yLeft.setAxisMaxValue(100f);
        yLeft.setAxisMinValue(0f);

        chartD.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartD.getRendererXAxis();
        chartD.getAxisRight().setEnabled(false);
//      chart.getAxisLeft().setEnabled(false);
        chartD.setData(data);
        chartD.setScaleEnabled(false);
        chartD.setDoubleTapToZoomEnabled(false);
        chartD.setDescription(" ");
        chartD.animateXY(2000, 2000);
        chartD.invalidate();

        //ll_diagnostic_graph_main.setVisibility(View.VISIBLE);
//        if (diagnosticStatus) {
//            if (ll_diagnostic_graph_main.getVisibility() == View.VISIBLE) {
//                if (li_Diagnostic_report.size() > 0) {
//
//                } else {
//                    ll_diagnostic_graph_main.setVisibility(View.GONE);
//                }
//
//
//            } else {
//                ll_diagnostic_graph_main.setVisibility(View.VISIBLE);
//            }
//        } else {
//            if (tv_nrf_diagnostic.getVisibility() == View.VISIBLE) {
//                tv_nrf_diagnostic.setVisibility(View.GONE);
//                btn_undo_diagnostics_nrf.setVisibility(View.GONE);
//            } else {
//                tv_nrf_diagnostic.setVisibility(View.VISIBLE);
//                btn_undo_diagnostics_nrf.setVisibility(View.VISIBLE);
//            }
//        }
//       setPercentage();
    }

    /* this method is used to set happyIndex data.*/
    public void setHappyIndex(JSONArray jsonArray) {
        JSONArray jsonAr = jsonArray;
        li_happIndex = baseRequest.getDataList(jsonArray, ModelHappyIndex.class);
        ArraystoreValueH();
        ll_happyindex_msg.setVisibility(View.GONE);
        lyt_happyindex.setVisibility(View.VISIBLE);
        Adapter_kc_happyindex ad_happyIndex = new Adapter_kc_happyindex(li_happIndex, li_score_short, mContext, hi_type);
        rv_happyindex_graph_list.setHasFixedSize(true);
        rv_happyindex_graph_list.setAdapter(ad_happyIndex);
        rv_happyindex_graph_list.setLayoutManager(new LinearLayoutManager(mContext));
        //Size of recycler view
        ViewGroup.LayoutParams params = rv_happyindex_graph_list.getLayoutParams();
        params.height = 90 * li_happIndex.size();
        rv_happyindex_graph_list.requestLayout();

        BarData data = new BarData(getXAxisValuesH(), getDataSetH());
        YAxis yLeft = chartHi.getAxisLeft();
        //Set the minimum and maximum bar lengths as per the values that they represent
        yLeft.setAxisMaxValue(100f);
        yLeft.setAxisMinValue(0f);

        chartHi.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartHi.getRendererXAxis();
        chartHi.getAxisRight().setEnabled(false);
//      chart.getAxisLeft().setEnabled(false);
        chartHi.setData(data);
        chartHi.setScaleEnabled(false);
        chartHi.setDoubleTapToZoomEnabled(false);
        chartHi.setDescription(" ");
        chartHi.animateXY(2000, 2000);
        chartHi.invalidate();
        //ll_happyindex_graph_main.setVisibility(View.VISIBLE);

        if (path.contains("happyIndex")) {

            if (diagnosticStatus) {
                if (ll_happyindex_graph_main.getVisibility() == View.VISIBLE) {
                    if (li_happIndex.size() == 0) {
                        ll_happyindex_graph_main.setVisibility(View.GONE);
                    }

                } else {
                    ll_happyindex_graph_main.setVisibility(View.VISIBLE);
                }
            } else {
                if (tv_nrf_happyIndex.getVisibility() == View.VISIBLE) {
                    tv_nrf_happyIndex.setVisibility(View.GONE);
                    //btn_undo_diagnostics_nrf.setVisibility(View.GONE);
                } else {
                    tv_nrf_happyIndex.setVisibility(View.VISIBLE);
                    //btn_undo_diagnostics_nrf.setVisibility(View.VISIBLE);
                }
            }


        }

    }

    /*this method is used to set Tribeometer data
     */
    public void setTribometerData(JSONArray jsonArrayT) {
        JSONArray jsonArray = jsonArrayT;
        li_tribeometer = baseRequest.getDataList(jsonArray, ModelTribeometerGraph.class);
        //lyt_tribeometer.setVisibility(View.VISIBLE);
        ll_tribeometer_msg.setVisibility(View.GONE);
        //ArraystoreValueT();
        //ll_tribeometer_main.setVisibility(View.VISIBLE);
        ll_tribeometer_msg.setVisibility(View.GONE);

        try {
            Adapter_Admin_Report_Tribeometer_graph_list adap_tribo_graphlist = new Adapter_Admin_Report_Tribeometer_graph_list(li_tribeometer, mContext);
            rv_tribometer_graphlist.setHasFixedSize(true);
            rv_tribometer_graphlist.setAdapter(adap_tribo_graphlist);
            rv_tribometer_graphlist.setLayoutManager(new LinearLayoutManager(mContext));
            //Size of recycler view
            ViewGroup.LayoutParams params = rv_tribometer_graphlist.getLayoutParams();
            params.height = 90 * li_tribeometer.size();
            rv_tribometer_graphlist.requestLayout();
            BarData data = new BarData(getXAxisValuesT(), getDataSetT());
            YAxis yLeft = chartT.getAxisLeft();
            //Set the minimum and maximum bar lengths as per the values that they represent
            yLeft.setAxisMaxValue(100f);
            yLeft.setAxisMinValue(0f);
            chartT.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chartT.getRendererXAxis();
            chartT.getAxisRight().setEnabled(false);
            chartT.setData(data);
            chartT.setScaleEnabled(false);
            chartT.setDoubleTapToZoomEnabled(false);
            chartT.setDescription(" ");
//          setValue();
            chartT.animateXY(2000, 2000);
            chartT.invalidate();
            //ll_tribeometer_main.setVisibility(View.VISIBLE);
//            if (tribometerStatus) {
//                if (ll_tribeometer_main.getVisibility() == View.VISIBLE) {
//                    ll_tribeometer_main.setVisibility(View.GONE);
//                } else {
//                    ll_tribeometer_main.setVisibility(View.VISIBLE);
//                }
//            } else {
//                if (tv_nrf_tribometer.getVisibility() == View.VISIBLE) {
//                    tv_nrf_tribometer.setVisibility(View.GONE);
//                    btn_undo_tribometer_nrf.setVisibility(View.GONE);
//                } else {
//                    tv_nrf_tribometer.setVisibility(View.VISIBLE);
//                    btn_undo_tribometer_nrf.setVisibility(View.VISIBLE);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*this method is used to set culture index data.
     */
    public void setCultureIndex(JSONArray jsonArrayCI) {

        JSONArray jsonArray = jsonArrayCI;
        list_culture_index = baseRequest.getDataList(jsonArray, ModelCultureIndex.class);
        chart_culture_index.setDescription("");
        chart_culture_index.setNoDataTextDescription("You need to provide data for the chart.");
        //ArraystoreValueCI();
        Ad_CultureIndex ad_cultureIndex = new Ad_CultureIndex(list_culture_index, mContext);
        rv_cultureindex_graph_list.setHasFixedSize(true);
        rv_cultureindex_graph_list.setAdapter(ad_cultureIndex);
        rv_cultureindex_graph_list.setLayoutManager(new LinearLayoutManager(mContext));
//Size of recycler view
        ViewGroup.LayoutParams params = rv_cultureindex_graph_list.getLayoutParams();
        params.height = 90 * list_culture_index.size();
        rv_cultureindex_graph_list.requestLayout();

        chart_culture_index.setHighlightEnabled(false);
        chart_culture_index.setTouchEnabled(false);
        chart_culture_index.setDragEnabled(true);
        chart_culture_index.setScaleEnabled(true);
        chart_culture_index.setPinchZoom(false);
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        chart_culture_index.setMarkerView(mv);
        chart_culture_index.setHighlightIndicatorEnabled(false);
        XAxis xAxis = chart_culture_index.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setSpaceBetweenLabels(1);
        YAxis leftAxis = chart_culture_index.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        // leftAxis.setAxisMinValue(1f);
        // leftAxis.setAxisMinValue(-2000);
        leftAxis.setStartAtZero(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawLimitLinesBehindData(true);
        chart_culture_index.getAxisRight().setEnabled(false);
        setDataCI(list_culture_index);
        chart_culture_index.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = chart_culture_index.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        if (list_culture_index.size() > 0) {
            ll_cultureindex_graph_main.setVisibility(View.VISIBLE);
            ll_cultureindex_msg.setVisibility(View.GONE);
        } else {
            ll_cultureindex_graph_main.setVisibility(View.VISIBLE);
            lyt_cultureindex.setVisibility(View.GONE);
            ll_cultureindex_msg.setVisibility(View.VISIBLE);
        }

        if (path.contains("happyIndex")) {
            ll_cultureindex_graph_main.setVisibility(View.GONE);
        }


    }

    /*will set graph data of culture index
     */
    private void setDataCI(ArrayList<ModelCultureIndex> list) {

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

        LineData data = new LineData(getXAxisValuesCI(), getDataSetCI());

        // set data
        chart_culture_index.setData(data);
    }

    /**/
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

    /**/
    private ArrayList<String> getXAxisValuesCI() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < list_culture_index.size(); i++) {
            //xAxis.add((i + 1) + "");
            xAxis.add(list_culture_index.get(i).getTitle());
        }
        return xAxis;
    }

    /**/
    private ArrayList<String> getXAxisValuesEI() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < list_engagment_index.size(); i++) {
            //xAxis.add((i + 1) + "");
            xAxis.add(list_engagment_index.get(i).getTitle());
        }
        return xAxis;
    }

    /* this will get dataset for cultur index*/
    private ArrayList<LineDataSet> getDataSetCI() {
        ArrayList<LineDataSet> dataSets = null;
        ArrayList<LineData> valueSet1 = new ArrayList<>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<String> xaxisData = new ArrayList<>();
        for (int i = 0; i < list_culture_index.size(); i++) {
            float val = Float.valueOf(list_culture_index.get(i).getIndexCount());
            yVals.add(new Entry(val, i));
            xaxisData.add(list_culture_index.get(i).getTitle());
        }
        LineDataSet set1 = new LineDataSet(yVals, "Month");
        for (int j = 0; j < list_culture_index.size(); j++) {
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

    /*this will get dataset for engagmentIndex*/
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

    /*this method is used to set engagmentIndex data
     */
    public void setEngagmentIndex(JSONArray jsonArrayEI) {

        JSONArray jsonArray = jsonArrayEI;
        list_engagment_index = baseRequest.getDataList(jsonArray, ModelCultureIndex.class);
        chart_engagement_index.setDescription("");
        chart_engagement_index.setNoDataTextDescription("You need to provide data for the chart.");
        //ArraystoreValueEI();
        Ad_EngagementIndex ad_engagementIndex = new Ad_EngagementIndex(list_engagment_index, li_score_short, mContext);
        rv_engagement_index_graph_list.setHasFixedSize(true);
        rv_engagement_index_graph_list.setAdapter(ad_engagementIndex);
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
        //  float min = Collections.min(arrayY);
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        //leftAxis.setAxisMinValue(1f);
        //leftAxis.setAxisMinValue(-2000);
        leftAxis.setStartAtZero(false);
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
//        if (list_engagment_index.size() > 0) {
//            ll_engagement_index_graph_main.setVisibility(View.VISIBLE);
//            ll_engagement_index_msg.setVisibility(View.GONE);
//        } else {
//            ll_engagement_index_graph_main.setVisibility(View.VISIBLE);
//            lyt_engagement_index.setVisibility(View.GONE);
//            ll_engagement_index_msg.setVisibility(View.VISIBLE);
//        }
    }

    /**/
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < list_motivation_graph.size(); i++) {
            xAxis.add((i + 1) + "");
        }
        return xAxis;
    }

    /*this will get dataset for motivation*/
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int j = 0; j < list_motivation_graph.size(); j++) {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(list_motivation_graph.get(j).getScore()), j); // Jan
            valueSet1.add(v1e1);
        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Score");
//      barDataSet1.setColor(Color.rgb(51, 111, 179));
        barDataSet1.setColor(getResources().getColor(R.color.graph_color));
        barDataSet1.setBarSpacePercent(50);
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    /**/
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

    /*not in use*/
    private void ArraystoreValueCI() {
        li_score_short.clear();
        for (int j = 0; j < list_culture_index.size(); j++) {
            li_score_short.add(Float.valueOf(list_culture_index.get(j).getIndexCount()));
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

    /*not in use*/
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

    /*not in use*/
    private void ArraystoreValueD() {
        li_score_short.clear();
        for (int j = 0; j < li_Diagnostic_report.size(); j++) {
            li_score_short.add(Float.valueOf(li_Diagnostic_report.get(j).getPercentage()));
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

    /*this will get dataset for diagnostic*/
    private ArrayList<BarDataSet> getDataSetD() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int j = 0; j < li_Diagnostic_report.size(); j++) {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(li_Diagnostic_report.get(j).getPercentage()), j); // Jan
            valueSet1.add(v1e1);
        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Percentage");
//      barDataSet1.setColor(Color.rgb(51, 111, 179));
        barDataSet1.setColor(getResources().getColor(R.color.graph_color));
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    /**/
    private ArrayList<String> getXAxisValuesD() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < li_Diagnostic_report.size(); i++) {
            xAxis.add((i + 1) + "");
        }
        return xAxis;
    }

    /*API to get diagnostic data when the user clicked on the graph*/
    public void api_diagnosticSubGraph(String officeId, String departmentId, String categoryId) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    setDiagnosticData(jsonArray);
                    tv_diagnosticsBack.setVisibility(View.VISIBLE);
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
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId, "officeId", officeId, "departmentId", departmentId, "categoryId", categoryId);
        baseRequest.callAPIPost(1, object, getString(R.string.api_getDiagnsticReportSubGraph));
    }

    /*API to get tibreoMetersubgraph data when the user clicked on the graph*/
    private void api_trireoMeterSubdgraph(String officeId, String depart_id, Integer categoryId) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    // setPersonalityTypeData(jsonArray);
                    setTribometerData(jsonArray);
                    mainTriboSubgraphArray = jsonArray;
                    tv_tribeometerBack.setVisibility(View.VISIBLE);
                    subCategoryTrue = true;
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
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId, "officeId", officeId, "departmentId", depart_id, "categoryId", categoryId + "");
        baseRequest.callAPIPost(1, object, ConstantAPI.api_getTribeometerMainSubGraph);
    }

    /*API to get tibreoMetersubgraph data when the user clicked on the graph*/
    private void api_tribeoMeterSubSubgarph(String officeId, String depart_id, int triboCategoryId, Integer subCategoryId) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    // setPersonalityTypeData(jsonArray);
                    setTribometerData(jsonArray);
                    tv_tribeometerBack.setVisibility(View.GONE);
                    tv_tribometer_subgraph_back.setVisibility(View.VISIBLE);
                    tribometerSubsubgraph = jsonArray != null;
                    //li_Diagnostic_report = baseRequest.getDataList(jsonArray, ModelDiagnosticReport.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext, message);
                tribometerSubsubgraph = false;
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId, "officeId", officeId, "departmentId", depart_id, "categoryId", triboCategoryId + "", "subCategoryId", subCategoryId + "");
        baseRequest.callAPIPost(1, object, ConstantAPI.api_getTribeometerSubGraph);

    }

    /*API to get Personality data when the user clicked on the graph*/
    public void api_personalityTypeSubgraph(String officeId, String departmentId, String categoryId) {
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
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId, "officeId", officeId, "departmentId", departmentId, "categoryId", categoryId);
        baseRequest.callAPIPost(1, object, ConstantAPI.api_getPersonalityTypeReportSubGraph);
    }

    /**/
    //happyIndex graph
    private void ArraystoreValueH() {
        li_score_short.clear();
        for (int j = 0; j < li_happIndex.size(); j++) {
            //Float score = li_happIndex.get(j).getHappy();
            li_score_short.add(Float.valueOf(Float.parseFloat(li_happIndex.get(j).getHappy() + "")));
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

    /*this will get dataset for happyIndex*/
    private ArrayList<BarDataSet> getDataSetH() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int j = 0; j < li_happIndex.size(); j++) {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(li_happIndex.get(j).getHappy()), j); // Jan
            valueSet1.add(v1e1);
        }
        BarDataSet barDataSet1;
        if (hi_type.equalsIgnoreCase("year")) {
            barDataSet1 = new BarDataSet(valueSet1, "Year");
        } else if (hi_type.equalsIgnoreCase("month")) {
            barDataSet1 = new BarDataSet(valueSet1, "Month");
        } else if (hi_type.equalsIgnoreCase("week")) {
            barDataSet1 = new BarDataSet(valueSet1, "Week");
        } else {
            barDataSet1 = new BarDataSet(valueSet1, "Day");
        }
//      barDataSet1.setColor(Color.rgb(51, 111, 179));
        barDataSet1.setColor(getResources().getColor(R.color.graph_color));
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    /**/
    private ArrayList<String> getXAxisValuesH() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < li_happIndex.size(); i++) {
            xAxis.add((i + 1) + "");
        }
        return xAxis;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_personality_type_downarrow:
               /* if (ll_personality_typeMain.getVisibility() == View.VISIBLE) {
                    ll_personality_typeMain.setVisibility(View.GONE);
                } else {
                    ll_personality_typeMain.setVisibility(View.VISIBLE);
                }*/

                if (personalityTypeStatus) {

                    if (ll_personality_typeMain.getVisibility() == View.VISIBLE) {
                        ll_personality_typeMain.setVisibility(View.GONE);
                    } else {
                        ll_personality_typeMain.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_nrf_personalityType.getVisibility() == View.VISIBLE) {
                        tv_nrf_personalityType.setVisibility(View.GONE);
                    } else {
                        tv_nrf_personalityType.setVisibility(View.VISIBLE);
                    }

                }
                break;
            case R.id.iv_cultureStructure_downarrow:
                if (tv_PERSON_point.getText().toString().equals("0") && tv_POWER_point.getText().toString().equals("0") && tv_ROLE_point.getText().toString().equals("0") && tv_TRIBE_point.getText().toString().equals("0")) {
                    if (tv_nrf_cultureStructure.getVisibility() == View.VISIBLE) {
                        tv_nrf_cultureStructure.setVisibility(View.GONE);
                        btn_sot_review_nrf.setVisibility(View.GONE);
                    } else {
                        tv_nrf_cultureStructure.setVisibility(View.VISIBLE);
                        btn_sot_review_nrf.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (ll_sot_culture_main.getVisibility() == View.VISIBLE) {
                        ll_sot_culture_main.setVisibility(View.GONE);
                    } else {
                        ll_sot_culture_main.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.ll_culture_index:
                if (list_culture_index.size() > 0) {
                   /* if (ll_cultureindex_graph_main.getVisibility() == View.VISIBLE) {
                        ll_cultureindex_graph_main.setVisibility(View.GONE);
                    } else {
                        ll_cultureindex_graph_main.setVisibility(View.VISIBLE);
                    }*/
                    ll_cultureindex_graph_main.setVisibility(View.VISIBLE);
                } else {
                    if (tv_nrf_value_culture_index.getVisibility() == View.VISIBLE) {
                        tv_nrf_value_culture_index.setVisibility(View.GONE);
                    } else {
                        tv_nrf_value_culture_index.setVisibility(View.VISIBLE);
                    }
                }
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
            case R.id.iv_teamrole_downarrow:
                if (ll_teamRolsMain.getVisibility() == View.VISIBLE) {
                    ll_teamRolsMain.setVisibility(View.GONE);
                } else {
                    ll_teamRolsMain.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.iv_motivation_downarrow:
//                if (ll_motivationMain.getVisibility() == View.VISIBLE) {
//                    ll_motivationMain.setVisibility(View.GONE);
//                } else {
//                    ll_motivationMain.setVisibility(View.VISIBLE);
//                }
                if (sotMotivationStatus) {
                    if (ll_motivationMain.getVisibility() == View.VISIBLE) {
                        ll_motivationMain.setVisibility(View.GONE);
                    } else {
                        ll_motivationMain.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_nrf_motivation.getVisibility() == View.VISIBLE) {
                        tv_nrf_motivation.setVisibility(View.GONE);
                    } else {
                        tv_nrf_motivation.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.iv_diagnostic_downarrow:
//                if (ll_diagnostic_graph_main.getVisibility() == View.VISIBLE) {
//                    ll_diagnostic_graph_main.setVisibility(View.GONE);
//                } else {
//                    ll_diagnostic_graph_main.setVisibility(View.VISIBLE);
//                }
                if (diagnosticStatus) {
                    if (ll_diagnostic_graph_main.getVisibility() == View.VISIBLE) {
                        ll_diagnostic_graph_main.setVisibility(View.GONE);
                    } else {
                        ll_diagnostic_graph_main.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_nrf_diagnostic.getVisibility() == View.VISIBLE) {
                        tv_nrf_diagnostic.setVisibility(View.GONE);
                        btn_undo_diagnostics_nrf.setVisibility(View.GONE);
                    } else {
                        tv_nrf_diagnostic.setVisibility(View.VISIBLE);
                        btn_undo_diagnostics_nrf.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.iv_tribometer_downarrow:
//                if (ll_tribeometer_main.getVisibility() == View.VISIBLE) {
//                    ll_tribeometer_main.setVisibility(View.GONE);
//                } else {
//                    ll_tribeometer_main.setVisibility(View.VISIBLE);
//                }
                if (tribometerStatus) {
                    if (ll_tribeometer_main.getVisibility() == View.VISIBLE) {
                        ll_tribeometer_main.setVisibility(View.GONE);
                    } else {
                        ll_tribeometer_main.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_nrf_tribometer.getVisibility() == View.VISIBLE) {
                        tv_nrf_tribometer.setVisibility(View.GONE);
                        btn_undo_tribometer_nrf.setVisibility(View.GONE);
                    } else {
                        tv_nrf_tribometer.setVisibility(View.VISIBLE);
                        btn_undo_tribometer_nrf.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.iv_happy_index_downarrow:
                if (diagnosticStatus) {
                    if (ll_happyindex_graph_main.getVisibility() == View.VISIBLE) {
                        ll_happyindex_graph_main.setVisibility(View.GONE);
                    } else {
                        ll_happyindex_graph_main.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_nrf_happyIndex.getVisibility() == View.VISIBLE) {
                        tv_nrf_happyIndex.setVisibility(View.GONE);
                        //btn_undo_diagnostics_nrf.setVisibility(View.GONE);
                    } else {
                        tv_nrf_happyIndex.setVisibility(View.VISIBLE);
                        //btn_undo_diagnostics_nrf.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.tv_diagnosticsBack:
                if (subgraph) {
                    subgraph = false;
                    tv_diagnosticsBack.setVisibility(View.GONE);
                    setDiagnosticData(mainDiagnosticArray);
                }
                break;
            case R.id.tv_personality_back:
                if (perltySubgraph) {
                    perltySubgraph = false;
                    tv_personalityBack.setVisibility(View.GONE);
                    setPersonalityTypeData(mainPersonalityArray);
                }
                break;
            case R.id.tv_tribometer_back:
                if (tribometerSubgraph) {
                    tribometerSubgraph = false;
                    tv_tribeometerBack.setVisibility(View.GONE);
                    tv_tribometer_subgraph_back.setVisibility(View.GONE);
                    setTribometerData(mainTribographArray);
                }
                break;
            case R.id.tv_tribometer_subgraph_back:
                if (tribometerSubsubgraph) {
                    tribometerSubsubgraph = false;
                    tv_tribometer_subgraph_back.setVisibility(View.GONE);
                    tv_tribeometerBack.setVisibility(View.VISIBLE);
                    setTribometerData(mainTriboSubgraphArray);
                }
                break;


            case R.id.tv_undo:
            case R.id.btn_undo_diagnostics_nrf:

                if (isDiagnosticAnsDone) {
                    startActivity(new Intent(mContext, Act_Update_Diagnostics_list.class));
                } else {
                    startActivity(new Intent(mContext, Act_Diagnostics_list.class));
                }
                break;

            case R.id.tv_undo_tribometer:
            case R.id.btn_undo_tribometer_nrf:
                if (isTribeometerAnsDone) {
                    startActivity(new Intent(mContext, Act_Update_Tribeometer_list.class));
                } else {
                    startActivity(new Intent(mContext, Act_Tribeometer_list.class));
                }
                break;

            case R.id.btn_sot_review:

            case R.id.btn_sot_review_nrf:
                if (IsUserFilledAnswer) {
                    startActivity(new Intent(mContext, Act_SOT_Update_Questionlist.class));
                } else {
                    startActivity(new Intent(mContext, Act_SOT_Questionlist.class).putExtra("checklist", "checklist"));
                }
                break;


            case R.id.tv_happyIndexBack:
                /*if (hi_type.equals(month)) {
                    tv_happyIndexBack.setVisibility(View.GONE);
                    hi_type = year;
                    setHappyIndex(yearJson);
                } */
                if (hi_type.equals(week)) {
                    tv_happyIndexBack.setVisibility(View.GONE);
                    hi_type = month;
                    setHappyIndex(monthJson);
                } else if (hi_type.equals(day)) {
                    hi_type = week;
                    setHappyIndex(weekJson);
                }
                break;
        }
    }

    /*will set data on department spinner
     */
    private void callSpinnerDepartment() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, list_depart);
        sp_depart.setAdapter(adapter);
        sp_depart.setVisibility(View.VISIBLE);
        sp_depart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (flag == true) {
                    depart_id = list_depart_id.get(i);
                    Log.e("Select list=>", depart_id);
                    //api_getFilterReport();
                    getAllData();
                }
                iCurrentSelection = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                depart_id = "0";
            }
        });
    }

    /*will set data on office spinner
     */
    private void callSpinnerOffice() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, list_office);
        sp_office.setAdapter(adapter);
        sp_office.setVisibility(View.VISIBLE);

        sp_office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (flag = true) {
                    if (iCurrentSelection != -1) {
                        officeId = list_office_id.get(i);
                        int offID = Integer.parseInt(officeId);
                        list_depart_id.clear();
                        list_depart.clear();
                        list_depart_id.add("0");
                        list_depart.add("All Departments");
//                list_office.add("All Office");
//                call_getReportFilterFunctionalLensGraph();
                        getAllData();
                        //getDepartmentList();
                        callSpinnerDepartment();
                        if (depart_id.equals("0")) {
                            getAllData();
                        }
                        Log.e("Select list=>", officeId);
                        if (officeId.equals("0")) {
                            li_ModelDepartmentList.clear();
                            getDepartmentList();
                        } else for (int j = 0; j < list_officedepartment.size(); j++) {
                            if (officeId.equals(list_officedepartment.get(j).getOfficeId().toString())) {
                                for (int k = 0; k < list_officedepartment.get(j).getDepartment().size(); k++) {
                                    try {
                                        list_depart.add(list_officedepartment.get(j).getDepartment().get(k).getDepartment());
                                        list_depart_id.add(list_officedepartment.get(j).getDepartment().get(k).getId().toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                officeId = "0";
            }
        });
    }

    /*API call to get department list
     */
    public void getDepartmentList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                li_ModelDepartmentList = baseRequest.getDataList(jsonArray, ModelDepartmentList.class);
                call_li_allDepart();
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
        baseRequest.callAPIGET(1, map, ConstantAPI.getDepartmentList);
    }

    /**/
    private void call_li_allDepart() {
        list_depart_id.clear();
        list_depart.clear();
        list_depart_id.add("0");
        list_depart.add("All Department");
//                list_office.add("All Office");
//                call_fiterteamApi();
        Log.e("Select list=>", officeId);

        for (int k = 0; k < li_ModelDepartmentList.size(); k++) {
            list_depart.add(li_ModelDepartmentList.get(k).getDepartment());
            list_depart_id.add(li_ModelDepartmentList.get(k).getId().toString());
        }
    }

    /* API call to get office list and department list
     */
    public void call_Office_DepartmentApi() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
//              JSONArray jsonArray = (JSONArray) object;
                JSONObject jsonObject = (JSONObject) object;
                JSONArray jsonArray = jsonObject.optJSONArray("offices");
                list_officedepartment = baseRequest.getDataList(jsonArray, ModelOfficeDepartment.class);
                getDepartmentList();
                call_listoffice();
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
//                errorLayout.showError(message);
                utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
//                errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId);
        baseRequest.callAPIPost(1, object, ConstantAPI.getAllOfficenDepartments);
    }

    /**/
    private void call_listoffice() {
        for (int i = 0; i < list_officedepartment.size(); i++) {
            list_office.add(list_officedepartment.get(i).getOffice());
            list_office_id.add(String.valueOf(list_officedepartment.get(i).getOfficeId()));
        }
        callSpinnerOffice();
    }

    /*not in use*/
    private void ArraystoreValueT() {
        li_score_short.clear();
        for (int j = 0; j < li_tribeometer.size(); j++) {
            li_score_short.add(Float.valueOf(li_tribeometer.get(j).getPercentage()));
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

    /**/
    private ArrayList<String> getXAxisValuesT() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < li_tribeometer.size(); i++) {
            xAxis.add((i + 1) + "");
        }
        return xAxis;
    }

    /**/
    private ArrayList<BarDataSet> getDataSetT() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int j = 0; j < li_tribeometer.size(); j++) {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(li_tribeometer.get(j).getPercentage()), j); // Jan
            valueSet1.add(v1e1);
        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Percentage");
        barDataSet1.setBarSpacePercent(60f);
//      barDataSet1.setColor(Color.rgb(51, 111, 179));
        barDataSet1.setColor(getResources().getColor(R.color.graph_color));
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    /*happy index graph initially showing year’s data, when user click on that he/she will get data in months of that corresponding year.
     */
    public void api_month(String officeId, String departmentId, String year) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    monthJson = new JSONArray(object.toString());
                    setHappyIndex(monthJson);
                    //  tv_happyIndexBack.setVisibility(View.VISIBLE);
                    // li_Diagnostic_report = baseRequest.getDataList(jsonArray, ModelDiagnosticReport.class);
                    //  LoadSpinnercatelgory();
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
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId, "officeId", officeId, "departmentId", departmentId, "year", year);
        baseRequest.callAPIPost(1, object, ConstantAPI.api_getHappyIndexMonthCount);
    }

    /*if again user clicks on month graph then he/she will get data in a week
     */
    public void api_week(String officeId, String departmentId, String year, String month) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    weekJson = new JSONArray(object.toString());
                    setHappyIndex(weekJson);
                    tv_happyIndexBack.setVisibility(View.VISIBLE);
                    // li_Diagnostic_report = baseRequest.getDataList(jsonArray, ModelDiagnosticReport.class);
                    //  LoadSpinnercatelgory();
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
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId, "officeId", officeId, "departmentId", departmentId, "year", selectedyear, "month", selectedmonth);
        baseRequest.callAPIPost(1, object, ConstantAPI.api_getHappyIndexWeeksCount);
    }

    /* if the user clicks on week graph then he/she will get data in days.
     */
    public void api_day(String officeId, String departmentId, String year, String month) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    dayJson = new JSONArray(object.toString());
                    setHappyIndex(dayJson);
                    tv_happyIndexBack.setVisibility(View.VISIBLE);
                    // li_Diagnostic_report = baseRequest.getDataList(jsonArray, ModelDiagnosticReport.class);
                    // LoadSpinnercatelgory();
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
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId, "officeId", officeId, "departmentId", departmentId, "year", selectedyear, "month", selectedmonth, "week", selectedWeek);
        baseRequest.callAPIPost(1, object, ConstantAPI.api_getHappyIndexDaysCount);
    }

    //clone as Diagnostics for PersonType

    @Override
    protected void onResume() {
        super.onResume();
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
        getAllData();
    }

    private ArrayList<BarDataSet> getDataSetPerson() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int j = 0; j < li_Person_report.size(); j++) {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(li_Person_report.get(j).getPercentage()), j); // Jan
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
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.nav_profile != selectedItemId) {
            mContext.startActivity(new Intent(mContext, Act_ProfileUser.class));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_knowcompany;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}