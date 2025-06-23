package com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.SOTAdapters.Adapter_SOT_Motivation_graph_list;
import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelMotivationGraph;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_SOT_Motivation_Individual_graph extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_sot_motivation_graph)
    RecyclerView rv_sot_motivation_graph;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chart)
    BarChart chart;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lyt_indi_graph)
    LinearLayout lyt_indi_graph;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_sot_graph_name)
    TextView tv_sot_graph_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_undo)
    TextView tv_undo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_motivation_help)
    LinearLayout ll_motivation_help;

    Utility utility;
    SessionParam sessionParam;
    Activity activity;
    BaseRequest baseRequest;
    ArrayList<Float> li_score_short = new ArrayList<>();
    ArrayList<ModelMotivationGraph> list_motivation_graph = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_sot_motivation_graph);
        init();
        tribe.setOnClickListener(v -> callHomeAct(mContext));
    }

    /*used to initialise all the views
     */
    private void init() {
        ButterKnife.bind(this);
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe);
            }
        }
        utility = new Utility();
//      ll_motivation_help.setVisibility(View.VISIBLE);
        list_motivation_graph.clear();
        api_getUserByType();
        tv_sot_graph_name.setText(sessionParam.name);
        tv_undo.setVisibility(View.VISIBLE);
        tv_undo.setOnClickListener(v -> {
            Intent in = new Intent(Act_SOT_Motivation_Individual_graph.this, Act_SOT_Update_Motivation_Question.class);
            startActivity(in);
        });
        ll_motivation_help.setOnClickListener(v -> {

        });
    }

    /*API call to get motivation data
     */
    public void api_getUserByType() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray;
                    jsonArray = (JSONArray) object;
                    list_motivation_graph = baseRequest.getDataList(jsonArray, ModelMotivationGraph.class);
                    ArraystoreValue();
                    lyt_indi_graph.setVisibility(View.VISIBLE);
                    Adapter_SOT_Motivation_graph_list adap_sot_graphlist = new Adapter_SOT_Motivation_graph_list(list_motivation_graph, li_score_short, mContext);
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

                } catch (Exception e) {
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
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId, "userId", sessionParam.id
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.api_getSOTmotivationUserList);
    }

    //------------------__Graph Data------------------------------
    /*is a method used to set data on x-axis.
     */
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < list_motivation_graph.size(); i++) {
            xAxis.add((i + 1) + "");
        }
        return xAxis;
    }

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

    /*is used to set the alpha background on recyclerview list
     */
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

    @Override
    protected void onResume() {
        super.onResume();
        api_getUserByType();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent in = new Intent(Act_SOT_Motivation_Individual_graph.this, Act_SOT_Home.class);
//        startActivity(in);
        finish();
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_sot_motivation_graph;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}
