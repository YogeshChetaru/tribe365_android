package com.chetaru.tribe365_new.Adapter.SOTAdapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelSOTMotivationQues;
import com.chetaru.tribe365_new.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class Ad_Sot_Motivation_Question extends RecyclerView.Adapter<Ad_Sot_Motivation_Question.ViewHolder> {

    List<ModelSOTMotivationQues> list = new ArrayList<>();
    Gson gson = new Gson();
    String Savelist = "Savelist";
    String shred_list = "";
    private Context context;

    public Ad_Sot_Motivation_Question(List<ModelSOTMotivationQues> list, String shred_list, Context context) {
        this.list = list;
        this.shred_list = shred_list;
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

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        if (!(shred_list ==null)) {
//            Log.e("list json", shred_list);
//            try {
//                JSONArray jr = new JSONArray(shred_list);
//                for (int i = 0; i < jr.length(); i++) {
//                    JSONObject jsonObject = jr.getJSONObject(i);
//                    String sh_questionId = jsonObject.optString("questionId");
//                    String sh_questionName = jsonObject.optString("questionName");
//                    JSONArray opt_array = jsonObject.getJSONArray("option");
//                    String QuesString = "<b>" + "Question " + (position + 1) + " : " + "</b> " + sh_questionName;
//                    holder.tv_Question.setText(Html.fromHtml(QuesString));
//                    for (int j = 0; j < opt_array.length(); j++) {
//                        JSONObject opt_object = opt_array.getJSONObject(j);
//                        String categoryId = opt_object.optString("categoryId");
//                        String option = opt_object.optString("option");
//                        String OptionId = opt_object.optString("OptionId");
//                        String rating = opt_object.getString("rating");
//                        list.get(i).getOption().get(j).setRating(rating);
//
//                        if (j == 0) {
//                            holder.tv_opt1.setText(option);
//                        }
//                        if (j == 1) {
//                            holder.tv_opt2.setText(option);
//                        }
//
////                        if(rating.equals("")) {
////                            if (list.get(i).getOption().get(0).getRating().equals("")) {
////                                holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.white));
////                                holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////
////                                holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.white));
////                                list.get(i).getOption().get(0).setRating("");
////                                list.get(i).getOption().get(1).setRating("");
////                                //option 1
////                                holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                //option 2
////                                holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                return;
////                            }
////                        }
////                            if (rating.equals("5")) {
////                                if (list.get(i).getOption().get(0).getRating().equals("")) {
////                                    holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.white));
////                                    holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////
////                                    holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.white));
////                                    list.get(i).getOption().get(0).setRating("5");
////                                    list.get(i).getOption().get(1).setRating("0");
////                                    //option 1
////                                    holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    //option 2
////                                    holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    return;
////                                }
////                            }
////                            if (rating.equals("0")) {
////                                if (list.get(i).getOption().get(0).getRating().equals("")) {
////                                    holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.white));
////                                    holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.white));
////
////                                    list.get(i).getOption().get(0).setRating("0");
////                                    list.get(i).getOption().get(1).setRating("5");
////                                    //option 1
////                                    holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    //option 2
////                                    holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    return;
////                                }
////
////                            }
////                            if (rating.equals("1")) {
////                                if (list.get(i).getOption().get(0).getRating().equals("")) {
////                                    holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.white));
////                                    holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.white));
////                                    list.get(i).getOption().get(0).setRating("1");
////                                    list.get(i).getOption().get(1).setRating("4");
////
////                                    //option 1
////                                    holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    //option 2
////                                    holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    return;
////                                }
////                            }
////                            if (rating.equals("4")) {
////                                if (list.get(i).getOption().get(0).getRating().equals("")) {
////                                    holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.white));
////                                    holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.white));
////                                    list.get(i).getOption().get(0).setRating("4");
////                                    list.get(i).getOption().get(1).setRating("1");
////                                    //option 1
////                                    holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    //option 2
////                                    holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    return;
////                                }
////                            }
////                            if (rating.equals("2")) {
////                                if (list.get(i).getOption().get(0).getRating().equals("")) {
////                                    holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.white));
////                                    holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.white));
////                                    list.get(i).getOption().get(0).setRating("2");
////                                    list.get(i).getOption().get(1).setRating("3");
////                                    //option 1
////                                    holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    //option 2
////                                    holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    return;
////                                }
////                            }
////                            if (rating.equals("3")) {
////                                if (list.get(i).getOption().get(0).getRating().equals("")) {
////                                    holder.tv_sot_opt1_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt1_3.setTextColor(context.getResources().getColor(R.color.white));
////                                    holder.tv_sot_opt2_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_red_bg));
////                                    holder.tv_sot_opt2_2.setTextColor(context.getResources().getColor(R.color.white));
////                                    list.get(i).getOption().get(0).setRating("3");
////                                    list.get(i).getOption().get(1).setRating("2");
////                                    //option 1
////                                    holder.tv_sot_opt1_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_2.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_2.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt1_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt1_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    //option 2
////                                    holder.tv_sot_opt2_0.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_0.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_1.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_1.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_4.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_4.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_3.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_3.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    holder.tv_sot_opt2_5.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
////                                    holder.tv_sot_opt2_5.setTextColor(context.getResources().getColor(R.color.textcolor));
////                                    return;
////                                }
////
////
////                        }
// }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }else{
        String QuesString = "<b>" + "Question " + (position + 1) + " : " + "</b> " + list.get(position).getQuestionName();
        holder.tv_Question.setText(Html.fromHtml(QuesString));

        holder.tv_opt1.setText(list.get(position).getOption().get(0).getOption());
        holder.tv_opt2.setText(list.get(position).getOption().get(1).getOption());
//            }
        if (list.get(position).getOption().get(0).getRating().equals("5")) {
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
        if (list.get(position).getOption().get(0).getRating().equals("0")) {
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
        if (list.get(position).getOption().get(0).getRating().equals("1")) {
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
        if (list.get(position).getOption().get(0).getRating().equals("4")) {
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
        if (list.get(position).getOption().get(0).getRating().equals("2")) {
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
        if (list.get(position).getOption().get(0).getRating().equals("3")) {
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
//Save Shared Pref.-----------------------
//                    saveArrayList(list, Savelist);
                //-------------------------------
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
                list.get(position).setFalg(true);

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
                //Save Shared Pref.-----------------------
//                    saveArrayList(list, Savelist);
                //-------------------------------
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
                list.get(position).setFalg(true);
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
//Save Shared Pref.-----------------------
//                    saveArrayList(list, Savelist);
                //-------------------------------
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
                list.get(position).setFalg(true);
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
                //Save Shared Pref.-----------------------
//                    saveArrayList(list, Savelist);
                //-------------------------------
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
                list.get(position).setFalg(true);
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
//Save Shared Pref.-----------------------
//                    saveArrayList(list, Savelist);
                //-------------------------------
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
                list.get(position).setFalg(true);
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
                //Save Shared Pref.-----------------------
//                    saveArrayList(list, Savelist);
                //-------------------------------
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

//        public void saveArrayList(List<ModelSOTMotivationQues> list, String key) {
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = prefs.edit();
//            Gson gson = new Gson();
//            String json = gson.toJson(list);
//            editor.putString(key, json);
//            editor.apply();     // This line is IMPORTANT !!!
//        }
}