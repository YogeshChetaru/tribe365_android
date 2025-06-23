package com.chetaru.tribe365_new.Adapter.AdminReportAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Admin.ModelAdminReportDOT;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.utility.SessionParam;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Admin_Report_DOT_Horizontal extends RecyclerView.Adapter<Adapter_Admin_Report_DOT_Horizontal.ViewHolder> {

    List<ModelAdminReportDOT> beliefsList = new ArrayList<>();
    ArrayList<String> valueList;
    SessionParam sessionParam;
    ArrayList<Integer> li_size = new ArrayList<>();
    String EditDot = "";
    Adapter_Admin_Belief_Vertical ad_belief_vertical;
    private Context context;

    public Adapter_Admin_Report_DOT_Horizontal(List<ModelAdminReportDOT> list, Context context) {
        this.beliefsList = list;
        notifyDataSetChanged();
        this.context = context;
        sessionParam = new SessionParam(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adap_admin_report_dot_horizontal_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 15, 10);
        holder.tv_horiz_brlifname.setText(beliefsList.get(position).getBeliefName());
        holder.tv_horiz_brlifrating.setText(beliefsList.get(position).getBeliefRatings());
        int rat = Math.round(Float.parseFloat(beliefsList.get(position).getBeliefRatings()));

        //--------------------------------------------Color commented old and new color apply
        if ((rat + "").equals("")) {
            holder.fl_beilfname.setBackground(context.getResources().getDrawable(R.drawable.admin_white_border_gry));
            holder.tv_horiz_brlifrating.setText("-");
            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.dot_0)));
            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.dot_0)));
        }
        if ((rat + "").equals("0")) {
            holder.fl_beilfname.setBackground(context.getResources().getDrawable(R.drawable.admin_white_border_red));
//            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.color_0_red)));
//            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.color_0_red)));
            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.dot_0)));
            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.dot_0)));

        } else if ((rat + "").equals("1")) {
            holder.fl_beilfname.setBackground(context.getResources().getDrawable(R.drawable.admin_red_border_mid_red));
//            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.color_1_mid_red)));
//            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.color_1_mid_red)));

            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.dot_0)));
            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.dot_0)));
        } else if ((rat + "").equals("2")) {
            holder.fl_beilfname.setBackground(context.getResources().getDrawable(R.drawable.admin_red_border_light_red));
//            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.color_2_light_red)));
//            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.color_2_light_red)));
            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.dot_1)));
            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.dot_1)));
        } else if ((rat + "").equals("3")) {
            holder.fl_beilfname.setBackground(context.getResources().getDrawable(R.drawable.admin_border_light_green));
//            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.dot_3)));
//            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.dot_3)));
            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.dot_2)));
            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.dot_2)));
        } else if ((rat + "").equals("4")) {
            holder.fl_beilfname.setBackground(context.getResources().getDrawable(R.drawable.admin_red_border_square));
            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.dot_3)));
            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.dot_3)));
        } else if ((rat + "").equals("5")) {
            holder.fl_beilfname.setBackground(context.getResources().getDrawable(R.drawable.admin_whit_border_green));
            holder.tv_horiz_brlifrating.setTextColor(context.getResources().getColor((R.color.dot_4)));
            holder.vv_color.setBackgroundColor(context.getResources().getColor((R.color.dot_4)));
        }
        //------------------------------------------------

        ad_belief_vertical = new Adapter_Admin_Belief_Vertical(beliefsList.get(position).getBeliefValues(), context, EditDot);
        holder.rv_belief_list.setHasFixedSize(true);
        holder.rv_belief_list.setAdapter(ad_belief_vertical);
        holder.rv_belief_list.setLayoutManager(new LinearLayoutManager(context));


    }

    @Override
    public int getItemCount() {
        try {
            return beliefsList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_horiz_brlifname, tv_horiz_brlifrating;
        RecyclerView rv_belief_list;
        View vv_color;
        FrameLayout fl_beilfname;

        public ViewHolder(View v) {
            super(v);
            tv_horiz_brlifname = v.findViewById(R.id.tv_horiz_brlifname);
            tv_horiz_brlifrating = v.findViewById(R.id.tv_horiz_brlifrating);
            rv_belief_list = v.findViewById(R.id.rv_belief_list);
            vv_color = v.findViewById(R.id.vv_color);
            fl_beilfname = v.findViewById(R.id.fl_beilfname);
        }
    }

}
