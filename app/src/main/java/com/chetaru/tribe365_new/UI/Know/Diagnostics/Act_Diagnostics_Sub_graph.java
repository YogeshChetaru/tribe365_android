package com.chetaru.tribe365_new.UI.Know.Diagnostics;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelDeagnosticsSubGraph;
import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelDiagnosticReport;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Diagnostics.Adapter_Diagnostics_Sub_graph_list;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Act_Diagnostics_Sub_graph extends BaseActivity {
    @BindView(R.id.tribe365)
    ImageView tribe365;
    BaseRequest baseRequest;
    Utility utility;
    SessionParam sessionParam;
    BarChart chart;
    @BindView(R.id.ll_diagnostic_msg)
    LinearLayout ll_diagnostic_msg;
    @BindView(R.id.ll_diagnostic_graph)
    LinearLayout ll_diagnostic_graph;
    @BindView(R.id.tv_org_name)
    TextView tv_org_name;

    String org_name = "", categoryId = "";
    ArrayList<ModelDeagnosticsSubGraph> li_Sub_Diagnostic_report = new ArrayList<>();
    ArrayList<ModelDiagnosticReport> li_Diagnostic_report = new ArrayList<>();
    ArrayList<Float> li_score_short = new ArrayList<>();
    @BindView(R.id.rv_diagnostic_graph_list)
    RecyclerView rv_diagnostic_graph_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.act_diagnostics_sub_graph);
        ButterKnife.bind(this);
        chart = (BarChart) findViewById(R.id.chart);
        inti();
        tribe365.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
//Test------------------------
    }

    private void inti() {
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        categoryId = getIntent().getStringExtra("categoryId");
        org_name = getIntent().getStringExtra("graphname");
//        tv_org_name.setText(sessionParam.name);
        tv_org_name.setText(org_name);

    }

    @Override
    protected void onResume() {
        super.onResume();
        api_getReport();
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int j = 0; j < li_Sub_Diagnostic_report.size(); j++) {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(li_Sub_Diagnostic_report.get(j).getPercentage().toString()), j); // Jan
            valueSet1.add(v1e1);
        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Percentage");
//        barDataSet1.setColor(Color.rgb(51, 111, 179));
        barDataSet1.setColor(getResources().getColor(R.color.graph_color));
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < li_Sub_Diagnostic_report.size(); i++) {
            xAxis.add((i + 1) + "");
        }
        return xAxis;
    }


    public void api_getReport() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    li_Sub_Diagnostic_report = baseRequest.getDataList(jsonArray, ModelDeagnosticsSubGraph.class);
                    //ModelDiagnosticReport
                    //  list_motivation_graph = baseRequest.getDataList(jsonArray, ModelMotivationGraph.class);
                    ArraystoreValue();
                    ll_diagnostic_graph.setVisibility(View.VISIBLE);
                    ll_diagnostic_msg.setVisibility(View.GONE);
                    Adapter_Diagnostics_Sub_graph_list adap_diag_graphlist = new Adapter_Diagnostics_Sub_graph_list(li_Sub_Diagnostic_report, li_score_short, mContext);
                    rv_diagnostic_graph_list.setHasFixedSize(true);
                    rv_diagnostic_graph_list.setAdapter(adap_diag_graphlist);
                    rv_diagnostic_graph_list.setLayoutManager(new LinearLayoutManager(mContext));
//Size of recycler view
                    ViewGroup.LayoutParams params = rv_diagnostic_graph_list.getLayoutParams();
                    params.height = 90 * li_Sub_Diagnostic_report.size();
                    rv_diagnostic_graph_list.requestLayout();
                    BarData data = new BarData(getXAxisValues(), getDataSet());
                    YAxis yLeft = chart.getAxisLeft();
                    //Set the minimum and maximum bar lengths as per the values that they represent
                    yLeft.setAxisMaxValue(100f);
                    yLeft.setAxisMinValue(0f);
                    chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    chart.getRendererXAxis();
                    chart.getAxisRight().setEnabled(false);
//                    chart.getAxisLeft().setEnabled(false);
                    chart.setData(data);
                    chart.setScaleEnabled(false);
                    chart.setDoubleTapToZoomEnabled(false);
                    chart.setDescription(" ");
                    chart.animateXY(2000, 2000);
                    chart.invalidate();
//                    setPercentage();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
                if (message.equals("Diagnostic answers not done yet")) {
                    ll_diagnostic_graph.setVisibility(View.GONE);
                    ll_diagnostic_msg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId, "officeId", "",
                "departmentId", "",
                "categoryId", categoryId
        );
        baseRequest.callAPIPost(1, object, getString(R.string.api_getDiagnsticReportSubGraph));
    }

    private void ArraystoreValue() {
        li_score_short.clear();
        for (int j = 0; j < li_Sub_Diagnostic_report.size(); j++) {
            li_score_short.add(Float.valueOf(li_Sub_Diagnostic_report.get(j).getPercentage()));
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
    @Override
    public int getLayoutId() {
        return R.layout.act_function_lens_qustion;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
