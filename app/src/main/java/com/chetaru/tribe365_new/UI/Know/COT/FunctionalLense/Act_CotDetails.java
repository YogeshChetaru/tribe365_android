package com.chetaru.tribe365_new.UI.Know.COT.FunctionalLense;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.COTAdapters.Ad_CotfuncLensDetails;
import com.chetaru.tribe365_new.Adapter.Diagnostics.Adapter_Diagnostics_graph_list;
import com.chetaru.tribe365_new.API.Models.Admin.ModelAdminReportDOT;
import com.chetaru.tribe365_new.API.Models.COTBeans.FuncLensKeyDetail;
import com.chetaru.tribe365_new.API.Models.COTBeans.InitialValueList;
import com.chetaru.tribe365_new.API.Models.COTBeans.ModelForRecyclerView;
import com.chetaru.tribe365_new.API.Models.COTBeans.ModelFutureLenseDetails;
import com.chetaru.tribe365_new.API.Models.COTBeans.PersuadeArray;
import com.chetaru.tribe365_new.API.Models.COTBeans.SeekArray;
import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelDiagnosticReport;
import com.chetaru.tribe365_new.UI.Know.PersonalityType.Act_Update_Personality_list;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Act_CotDetails extends BaseActivity implements View.OnClickListener {
    Utility utility;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    ModelFutureLenseDetails modelFutureLenseDetails;
    ModelForRecyclerView modelForRecyclerView;
    List<ModelAdminReportDOT> modelUserReportList;
    boolean subgraph = false;
    JSONArray mainPersonalityArray;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
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
    @BindView(R.id.tv_name)
    TextView tv_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_view_team)
    TextView tv_view_team;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_cot_summary)
    TextView tv_cot_summary;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_undo)
    TextView tv_undo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_function_help)
    LinearLayout ll_function_help;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cot_detail_ll)
    LinearLayout cot_detail_ll;

    List<InitialValueList> initialValueLists;
    List<ModelForRecyclerView> listForRv;
    List<ModelForRecyclerView> listForENTP;
    List<ModelForRecyclerView> listForscrore;
    List<ModelForRecyclerView> listForENTPSummery;
    List<FuncLensKeyDetail> funcLensKeyDetailList;
    List<SeekArray> seekArrayList;
    List<PersuadeArray> persuadeArrayList;


    //new personality Type
    //clone with member to user for Personality type
    BarChart Person_chart;

    String org_name = "";
    ArrayList<ModelDiagnosticReport> li_Person_report = new ArrayList<>();
    ArrayList<Float> li_score_short_person = new ArrayList<>();
    /*@BindView(R.id.rv_person_graph_list)*/
    RecyclerView rv_diagnostic_graph_list_person;
    @BindView(R.id.ll_diagnostic_graph)
    LinearLayout ll_diagnostic_graph;


    TextView tv_personalityBack;
    @BindView(R.id.tv_view_Full_result)
    TextView viewFullResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_cot_details);
        init();
    }

    /* used to initialise all the views
     */
    public void init() {
        ButterKnife.bind(this);
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        listForRv = new ArrayList<>();

        //for member personality chart
        ////////personality Type
        Person_chart = findViewById(R.id.user_personality_chart);
        ll_diagnostic_graph = findViewById(R.id.ll_diagnostic_graph);
        rv_diagnostic_graph_list_person = findViewById(R.id.rv_user_personality_graph_list);
        tv_personalityBack = findViewById(R.id.tv_user_personality_back);
        tv_personalityBack.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        tv_initial_result.setVisibility(View.GONE);
        tv_cot_summary.setVisibility(View.GONE);
        tv_name.setText(sessionParam.name + " " + sessionParam.lastName);
        api_getDetails();
        ll_diagnostic_graph.setVisibility(View.GONE);
        cot_detail_ll.setVisibility(View.VISIBLE);
        viewFullResult.setVisibility(View.VISIBLE);
        //call sub Graph for personalityType
        Person_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // api_diagnosticSubGraph()
                if (!subgraph) {
                    subgraph = true;
                    api_personalityTypeSubgraph(sessionParam.id, li_Person_report.get(e.getXIndex()).getCategoryId());
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        viewFullResult.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ll_diagnostic_graph.setVisibility(View.GONE);
        cot_detail_ll.setVisibility(View.VISIBLE);
        viewFullResult.setVisibility(View.VISIBLE);
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.tv_d1, R.id.tv_d2, R.id.tv_d3, R.id.tv_d4, R.id.tv_s1, R.id.tv_s2, R.id.tv_s3,
            R.id.tv_s4, R.id.tv_initial_result, R.id.tv_view_team, R.id.tv_undo, R.id.tv_view_Full_result, R.id.ll_function_help, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.tv_d1:

                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                tv_cot_summary.setVisibility(View.VISIBLE);
                listForENTP = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(funcLensKeyDetailList.get(0).getTitle());
                modelForRecyclerView.setDescription(funcLensKeyDetailList.get(0).getDescription());
                modelForRecyclerView.setType("funclens");
                listForENTP.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForENTP, mContext));
                // Changing border color
                tv_d1.setBackground(getResources().getDrawable(R.drawable.bg_option_red_round));
                tv_d2.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d3.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d4.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));

                tv_d1.setTextColor(getResources().getColor(R.color.white));
                tv_d2.setTextColor(getResources().getColor(R.color.textcolor));
                tv_d3.setTextColor(getResources().getColor(R.color.textcolor));
                tv_d4.setTextColor(getResources().getColor(R.color.textcolor));
                //tv_s3.setTextColor(getResources().getColor(R.color.non_Clickable_textcolor));
                setScoreblockdefaultColor();

                break;
            case R.id.tv_d2:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                tv_cot_summary.setVisibility(View.VISIBLE);
                listForENTP = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(funcLensKeyDetailList.get(1).getTitle());
                modelForRecyclerView.setDescription(funcLensKeyDetailList.get(1).getDescription());
                modelForRecyclerView.setType("funclens");
                listForENTP.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForENTP, mContext));

                // Changing border color
                tv_d1.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d2.setBackground(getResources().getDrawable(R.drawable.bg_option_red_round));
                tv_d3.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d4.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));

                tv_d1.setTextColor(getResources().getColor(R.color.textcolor));
                tv_d2.setTextColor(getResources().getColor(R.color.white));
                tv_d3.setTextColor(getResources().getColor(R.color.textcolor));
                tv_d4.setTextColor(getResources().getColor(R.color.textcolor));

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
                tv_d3.setBackground(getResources().getDrawable(R.drawable.bg_option_red_round));
                tv_d4.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));

                tv_d1.setTextColor(getResources().getColor(R.color.textcolor));
                tv_d2.setTextColor(getResources().getColor(R.color.textcolor));
                tv_d3.setTextColor(getResources().getColor(R.color.white));
                tv_d4.setTextColor(getResources().getColor(R.color.textcolor));
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
                tv_d4.setBackground(getResources().getDrawable(R.drawable.bg_option_red_round));

                tv_d1.setTextColor(getResources().getColor(R.color.textcolor));
                tv_d2.setTextColor(getResources().getColor(R.color.textcolor));
                tv_d3.setTextColor(getResources().getColor(R.color.textcolor));
                tv_d4.setTextColor(getResources().getColor(R.color.white));
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

            case R.id.tv_view_team:
                startActivity(new Intent(mContext, Act_CotFTeamList.class));
                break;

            case R.id.tv_initial_result:
                tv_initial_result.setVisibility(View.GONE);
                tv_cot_summary.setVisibility(View.GONE);
                rv_list.setVisibility(View.GONE);
                ll_allDataBlock.setVisibility(View.VISIBLE);
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
                break;
            case R.id.tv_undo:
                Intent in = new Intent(Act_CotDetails.this, Act_Update_Personality_list.class);
                in.putExtra("checklist", "");
                startActivity(in);
                break;
            case R.id.ll_function_help:

                break;

            case R.id.tv_user_personality_back:
                if (subgraph) {
                    subgraph = false;
                    tv_personalityBack.setVisibility(View.GONE);
                    setPersonalityTypeData(mainPersonalityArray);
                }
                break;
            case R.id.tv_view_Full_result:
                api_getPersonalityReport();

                break;
        }
    }

    /*API call to get functional lense details
     */
    public void api_getDetails() {

        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                modelFutureLenseDetails = gson.fromJson(object.toString(), ModelFutureLenseDetails.class);
                try {
                    if (modelFutureLenseDetails.getFuncLensKeyDetail().size() == 0) {
                        ll_topBlock.setVisibility(View.GONE);
                        ll_bottom_block.setVisibility(View.GONE);
                        tv_nodata.setVisibility(View.VISIBLE);
                        tv_name.setVisibility(View.GONE);
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
                    String seekValue = "";
                    if (seekArrayList != null) {
                        if (seekArrayList.size() > 0) {
                            for (int l = 0; l < seekArrayList.size(); l++) {
                                modelForRecyclerView = new ModelForRecyclerView();
                                modelForRecyclerView.setHeading("Seek");
                                if (l == 0) {
                                    seekValue = seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                                } else {
                                    seekValue = seekValue + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                                }
                            }
                            tv_allseek_data.setText(seekValue);
                        } else {
                            tv_allseek.setVisibility(View.GONE);
                            tv_allseek_data.setVisibility(View.GONE);
                        }
                    } else {
                        tv_allseek.setVisibility(View.GONE);
                        tv_allseek_data.setVisibility(View.GONE);
                    }

                    if (persuadeArrayList != null) {
                        if (persuadeArrayList.size() > 0) {
                            String persuadeValue = "";
                            for (int l = 0; l < persuadeArrayList.size(); l++) {
                                modelForRecyclerView = new ModelForRecyclerView();
                                modelForRecyclerView.setHeading("Persuade");
                                if (l == 0) {
                                    persuadeValue = persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                                } else {
                                    persuadeValue = persuadeValue + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                                }
                            }
                            tv_allPersuadedata.setText(persuadeValue);
                        } else {
                            tv_allPersuade.setVisibility(View.GONE);
                            tv_allPersuadedata.setVisibility(View.GONE);
                        }

                    } else {
                        tv_allPersuade.setVisibility(View.GONE);
                        tv_allPersuadedata.setVisibility(View.GONE);
                    }

                    rv_list.setAdapter(new Ad_CotfuncLensDetails(listForRv, mContext));
                } catch (Exception e) {

                }
                setAdapter(modelFutureLenseDetails);
                // utility.showToast(mContext, "Success");
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext, message);

            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext, message);
                // errorLayout.showError(message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id,
                "orgId", sessionParam.orgId
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.api_functionallensdetail);
    }

    //API call to get Actions personality Type
    public void api_getPersonalityReport() {

        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                    JSONArray personalityArray;
                    personalityArray = (JSONArray) object;
                    // li_Person_report = baseRequest.getDataList(jsonArray, ModelAdminReportDOT.class);
                    setPersonalityTypeData(personalityArray);
                    mainPersonalityArray = personalityArray;


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
                utility.showToast(mContext, message);
                // errorLayout.showError(message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject(
                "userId", sessionParam.id
        );
        baseRequest.callAPIPost(1, object,ConstantAPI.api_getPersonalityTypeReport );
    }

    public void setAdapter(ModelFutureLenseDetails modelFutureLenseDetails) {
        if (modelFutureLenseDetails.getInitialValueList().size() > 0) {
            initialValueLists = modelFutureLenseDetails.getInitialValueList();
        }
        if (modelFutureLenseDetails.getFuncLensKeyDetail().size() > 0) {
            funcLensKeyDetailList = modelFutureLenseDetails.getFuncLensKeyDetail();
            //here we are checking how many combination are there and showing them accordingly
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
       /* String seekValue = "";
        if (seekArrayList != null) {


            if (seekArrayList != null) {
                for (int l = 0; l < seekArrayList.size(); l++) {
                    modelForRecyclerView = new ModelForRecyclerView();
                    modelForRecyclerView.setHeading("Seek");
                    if (l == 0) {
                        seekValue = seekArrayList.get(l).getValue() + ",";
                    } else {
                        seekValue = seekValue + seekArrayList.get(l).getValue() + ",";
                    }
                    modelForRecyclerView.setDescription(seekValue);
                    modelForRecyclerView.setType("seek");

                }
                listForRv.add(modelForRecyclerView);
            }
        }


        if (persuadeArrayList != null) {
            String persuadeValue = "";
            for (int l = 0; l < persuadeArrayList.size(); l++) {
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading("Persuade");
                if (l == 0) {
                    persuadeValue = persuadeArrayList.get(l).getValue() + ",";
                } else {
                    persuadeValue = seekValue + persuadeArrayList.get(l).getValue() + ",";
                }
                modelForRecyclerView.setDescription(persuadeValue);
                modelForRecyclerView.setType("Persuade");

            }
            listForRv.add(modelForRecyclerView);
        }
*/

        /*try {
            modelForRecyclerView = new ModelForRecyclerView();
            modelForRecyclerView.setHeading(modelFutureLenseDetails.getTribeTipsArray().get(0).getSummary());
            modelForRecyclerView.setType("summery");
            listForRv.add(modelForRecyclerView);
        } catch (Exception e) {

        }*/

        // rv_list.setAdapter(new Ad_CotfuncLensDetails(listForRv, mContext));
    }

    /*this method is used to set color according to score.*/
    @SuppressLint("UseCompatLoadingForDrawables")
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
        //for third block
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

    @SuppressLint("UseCompatLoadingForDrawables")
    public void DallBloackdefaultColor() {
        tv_d1.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
        tv_d2.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
        tv_d3.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
        tv_d4.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
        tv_d1.setTextColor(getResources().getColor(R.color.textcolor));
        tv_d2.setTextColor(getResources().getColor(R.color.textcolor));
        tv_d3.setTextColor(getResources().getColor(R.color.textcolor));
        tv_d4.setTextColor(getResources().getColor(R.color.textcolor));
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        api_getDetails();
    }

    /* this method will be responsible if scoreBlock1 is selected*/
    @SuppressLint("UseCompatLoadingForDrawables")
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

    /*if user unselect scoreBlock1 then this method will handle all the
     * functionality. The same thing will be repeated for block 2 3 4.*/
    @SuppressLint("UseCompatLoadingForDrawables")
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

    @SuppressLint("UseCompatLoadingForDrawables")
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

    @SuppressLint("UseCompatLoadingForDrawables")
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

    @SuppressLint("UseCompatLoadingForDrawables")
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

    @SuppressLint("UseCompatLoadingForDrawables")
    public void scoreBlock3_NOT_SELECTED() {

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
    }

    @SuppressLint("UseCompatLoadingForDrawables")
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

    @SuppressLint("UseCompatLoadingForDrawables")
    public void scoreBlock4_NOT_SELECTED() {
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

    public void setPersonalityTypeData(JSONArray report) {
        Gson gson = new Gson();
        try {
            JSONArray jsonArray = new JSONArray(report.toString());
            // Log.d("Diagnostics_graph", jsonArray.toString());
            li_Person_report = baseRequest.getDataList(jsonArray, ModelDiagnosticReport.class);

            ArraystoreValuePerson();
            ll_diagnostic_graph.setVisibility(View.VISIBLE);
            cot_detail_ll.setVisibility(View.GONE);
            viewFullResult.setVisibility(View.GONE);
            if (sessionParam.role.equals("3")) {
                tv_undo.setVisibility(View.VISIBLE);
                // viewFullResult.setVisibility(View.VISIBLE);
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

    ///////////////////////for user Personality Type////////////
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

    @Override
    public int getLayoutId() {
        return R.layout.act_cot_details;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}
