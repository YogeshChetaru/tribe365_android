package com.chetaru.tribe365_new.Adapter.SOTAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.SOTBeans.UpdateBean.ModelSOtMotivationIndividual;
import com.chetaru.tribe365_new.R;

import java.util.ArrayList;
import java.util.List;


public class Ad_Sot_Update_Motivation_Question extends RecyclerView.Adapter<Ad_Sot_Update_Motivation_Question.ViewHolder> {

    List<ModelSOtMotivationIndividual> list = new ArrayList<>();
    private Context context;


    public Ad_Sot_Update_Motivation_Question(List<ModelSOtMotivationIndividual> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_act_sot_motivation_question, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String QuesString = "<b>" + "Question " + (position + 1) + " : " + "</b> " + list.get(position).getQuestionName();
        holder.tv_Question.setText(Html.fromHtml(QuesString));

        holder.tv_opt1.setText(list.get(position).getOption().get(0).getOption());
        holder.tv_opt2.setText(list.get(position).getOption().get(1).getOption());
        if (list.get(position).getOption().get(0).getPoints() == 5) {
            holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));

            holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.white));
            list.get(position).getOption().get(0).setRating("5");
            list.get(position).getOption().get(1).setRating("0");
            //option 1
            holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            //option 2
            holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
        }
        if (list.get(position).getOption().get(0).getPoints() == 0) {
            holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.white));
            list.get(position).getOption().get(0).setRating("0");
            list.get(position).getOption().get(1).setRating("5");
            //option 1
            holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            //option 2
            holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));

        }
        if (list.get(position).getOption().get(0).getPoints() == 1) {
            holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.white));
            list.get(position).getOption().get(0).setRating("1");
            list.get(position).getOption().get(1).setRating("4");

            //option 1
            holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            //option 2
            holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));

        }
        if (list.get(position).getOption().get(0).getPoints() == 4) {
            holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.white));
            list.get(position).getOption().get(0).setRating("4");
            list.get(position).getOption().get(1).setRating("1");
            //option 1
            holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            //option 2
            holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
        }
        if (list.get(position).getOption().get(0).getPoints() == 2) {
            holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.white));
            list.get(position).getOption().get(0).setRating("2");
            list.get(position).getOption().get(1).setRating("3");
            //option 1
            holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            //option 2
            holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
        }
        if (list.get(position).getOption().get(0).getPoints() == 3) {
            holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
            holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.white));
            list.get(position).getOption().get(0).setRating("3");
            list.get(position).getOption().get(1).setRating("2");
            //option 1
            holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            //option 2
            holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
        }
        holder.tv_sot_opt1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.white));
                list.get(position).getOption().get(0).setRating("0");
                list.get(position).getOption().get(1).setRating("5");
                //option 1
                holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
                //option 2
                holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));

            }
        });
        holder.tv_sot_opt1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.white));
                list.get(position).getOption().get(0).setRating("1");
                list.get(position).getOption().get(1).setRating("4");

                //option 1
                holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
                //option 2
                holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            }
        });
        holder.tv_sot_opt1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.white));
                list.get(position).getOption().get(0).setRating("2");
                list.get(position).getOption().get(1).setRating("3");
                //option 1
                holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
                //option 2
                holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            }
        });
        holder.tv_sot_opt1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.white));
                list.get(position).getOption().get(0).setRating("3");
                list.get(position).getOption().get(1).setRating("2");
                //option 1
                holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
                //option 2
                holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            }
        });
        holder.tv_sot_opt1_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.white));
                list.get(position).getOption().get(0).setRating("4");
                list.get(position).getOption().get(1).setRating("1");
                //option 1
                holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
                //option 2
                holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            }
        });
        holder.tv_sot_opt1_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
                holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.white));
                list.get(position).getOption().get(0).setRating("5");
                list.get(position).getOption().get(1).setRating("0");
                //option 1
                holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                //option 2
                holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
                holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            }
        });
    }

    @Override
    public int getItemCount() {

        try {
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Question;
        TextView tv_sot_opt1_0, tv_sot_opt1_1, tv_sot_opt1_2, tv_sot_opt1_3, tv_sot_opt1_4, tv_sot_opt1_5;
        TextView tv_sot_opt2_0, tv_sot_opt2_1, tv_sot_opt2_2, tv_sot_opt2_3, tv_sot_opt2_4, tv_sot_opt2_5;
        TextView tv_opt1, tv_opt2;

        public ViewHolder(View v) {
            super(v);
            tv_Question = v.findViewById(R.id.tv_Question);
            //option 1
            tv_sot_opt1_0 = v.findViewById(R.id.tv_sot_opt1_0);
            tv_sot_opt1_1 = v.findViewById(R.id.tv_sot_opt1_1);
            tv_sot_opt1_2 = v.findViewById(R.id.tv_sot_opt1_2);
            tv_sot_opt1_3 = v.findViewById(R.id.tv_sot_opt1_3);
            tv_sot_opt1_4 = v.findViewById(R.id.tv_sot_opt1_4);
            tv_sot_opt1_5 = v.findViewById(R.id.tv_sot_opt1_5);
//option 2
            tv_sot_opt2_0 = v.findViewById(R.id.tv_sot_opt2_0);
            tv_sot_opt2_1 = v.findViewById(R.id.tv_sot_opt2_1);
            tv_sot_opt2_2 = v.findViewById(R.id.tv_sot_opt2_2);
            tv_sot_opt2_3 = v.findViewById(R.id.tv_sot_opt2_3);
            tv_sot_opt2_4 = v.findViewById(R.id.tv_sot_opt2_4);
            tv_sot_opt2_5 = v.findViewById(R.id.tv_sot_opt2_5);

            tv_opt1 = v.findViewById(R.id.tv_opt1);
            tv_opt2 = v.findViewById(R.id.tv_opt2);

            tv_sot_opt2_0.setClickable(false);
            tv_sot_opt2_1.setClickable(false);
            tv_sot_opt2_2.setClickable(false);
            tv_sot_opt2_3.setClickable(false);
            tv_sot_opt2_4.setClickable(false);
            tv_sot_opt2_5.setClickable(false);
        }
    }
}