package com.chetaru.tribe365_new.Adapter.AdminReportAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Admin.BeliefValue;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.utility.SessionParam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Adapter_Admin_Belief_Vertical extends RecyclerView.Adapter<Adapter_Admin_Belief_Vertical.ViewHolder> {

    List<BeliefValue> beliefsList = new ArrayList<>();
    ArrayList<String> valueList;
    SessionParam sessionParam;
    String Edit = "";
    private Context context;


    public Adapter_Admin_Belief_Vertical(List<BeliefValue> list, Context context, String Edit) {
        this.beliefsList = list;
        notifyDataSetChanged();
        this.context = context;
        sessionParam = new SessionParam(context);
        this.Edit = Edit;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_belieflist, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.ll_new_add_ratingview.setVisibility(View.GONE);
        holder.tv_belief_item.setText(beliefsList.get(position).getValueName() + "\n" + beliefsList.get(position).getValueRatings() + "");
        // holder.tv_belief_itemscore.setText(beliefsList.get(position).getValueRatings() + "");

//        if (sessionParam.role.equals("1")) {
//            holder.tv_belief_itemscore.setVisibility(View.GONE);
//            holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.rect_white_blackborder));
//
//        }

        //--------------------------------
//        if ((beliefsList.get(position).getValueRatings()+"").equals("")) {
//            holder.tv_belief_itemscore.setBackground(context.getResources().getDrawable(R.drawable.shape_color_gry));
//            holder.tv_belief_itemscore.setText("-");
//        }
//        if ((beliefsList.get(position).getValueRatings()+"").equals("0")) {
//            holder.tv_belief_itemscore.setBackground(context.getResources().getDrawable(R.drawable.shape_color_0_red));
//        } else if ((beliefsList.get(position).getValueRatings()+"").equals("1")) {
//            holder.tv_belief_itemscore.setBackground(context.getResources().getDrawable(R.drawable.shape_color_1_mid_red));
//        } else if ((beliefsList.get(position).getValueRatings()+"").equals("2")) {
//            holder.tv_belief_itemscore.setBackground(context.getResources().getDrawable(R.drawable.shape_color_2_light_red));
//        } else if ((beliefsList.get(position).getValueRatings()+"").equals("3")) {
//            holder.tv_belief_itemscore.setBackground(context.getResources().getDrawable(R.drawable.shape_color_3_light_green));
//        } else if ((beliefsList.get(position).getValueRatings()+"").equals("4")) {
//            holder.tv_belief_itemscore.setBackground(context.getResources().getDrawable(R.drawable.shape_color_4_mid_green));
//        } else if ((beliefsList.get(position).getValueRatings()+"").equals("5")) {
//            holder.tv_belief_itemscore.setBackground(context.getResources().getDrawable(R.drawable.shape_color_5_green));
//        }


        //-----------------------------------------------------

        if ((beliefsList.get(position).getValueRatings() + "").equals("")) {
            holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_gry));
            holder.tv_belief_itemscore.setText("-");
        }
        for (int i = 0; i < 10; i++) {
            float f = (float) beliefsList.get(position).getValueRatings();
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            float twoDigitsF = Float.parseFloat(decimalFormat.format(f));
            if ((twoDigitsF + "").equals(("0" + "." + i) + "")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_0_red));
            }
        }
        for (int i = 0; i < 10; i++) {
            float f = (float) beliefsList.get(position).getValueRatings();
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            float twoDigitsF = Float.parseFloat(decimalFormat.format(f));
            if ((twoDigitsF + "").equals(("1" + "." + i) + "")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_1_mid_red));
            }
        }
        for (int i = 0; i < 10; i++) {
            float f = (float) beliefsList.get(position).getValueRatings();
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            float twoDigitsF = Float.parseFloat(decimalFormat.format(f));
            if ((twoDigitsF + "").equals(("2" + "." + i) + "")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_2_light_red));
            }
        }
        for (int i = 0; i < 10; i++) {
            float f = (float) beliefsList.get(position).getValueRatings();
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            float twoDigitsF = Float.parseFloat(decimalFormat.format(f));
            if ((twoDigitsF + "").equals(("3" + "." + i) + "")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_3_light_green));
            }
        }
        for (int i = 0; i < 10; i++) {
            float f = (float) beliefsList.get(position).getValueRatings();
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            float twoDigitsF = Float.parseFloat(decimalFormat.format(f));
            if ((twoDigitsF + "").equals(("4" + "." + i) + "")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_4_mid_green));
            }
        }
        for (int i = 0; i < 10; i++) {
            float f = (float) beliefsList.get(position).getValueRatings();
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            float twoDigitsF = Float.parseFloat(decimalFormat.format(f));
            if ((twoDigitsF + "").equals(("5" + "." + i) + "")) {
//                holder.tv_belief_itemscore.setBackground(context.getResources().getDrawable(R.drawable.shape_color_5_green));
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_5_green));

            }
        }
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


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_belief_item;
        TextView tv_belief_itemscore;
        LinearLayout lyt_dot_vertical_detailslist, ll_new_add_ratingview;


        public ViewHolder(View v) {
            super(v);
            tv_belief_item = v.findViewById(R.id.tv_belief_item);
            tv_belief_itemscore = v.findViewById(R.id.tv_belief_item_score);
            lyt_dot_vertical_detailslist = v.findViewById(R.id.lyt_dot_vertical_detailslist);
            ll_new_add_ratingview = v.findViewById(R.id.ll_new_add_ratingview);
        }
    }


}
