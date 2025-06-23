package com.chetaru.tribe365_new.Adapter.Diagnostics;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.Adapter.Option_Adapter;
import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelTribeometerQusList;
import com.chetaru.tribe365_new.R;

import java.util.ArrayList;
import java.util.List;

public class Ad_Tribeometer_list extends RecyclerView.Adapter<Ad_Tribeometer_list.ViewHolder> {

    List<ModelTribeometerQusList> list = new ArrayList<>();
    private Context context;


    public Ad_Tribeometer_list(List<ModelTribeometerQusList> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
    }

    @Override
    public Ad_Tribeometer_list.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tribeometer_list, parent, false);
        return new Ad_Tribeometer_list.ViewHolder(view);
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
    public void onBindViewHolder(final Ad_Tribeometer_list.ViewHolder holder, final int position) {
        holder.tv_tribeometer_qus.setText(list.get(position).getQuestion());
        holder.tv_tribeometer_qus_no.setText(list.get(position).getQuestionId().toString());

        int optAnswer = 100;
        if (!list.get(position).getmAnswer().equals("")) {
            optAnswer = Integer.parseInt((list.get(position).getmAnswer()));
        }
        Option_Adapter optionAdapter = new Option_Adapter(context, list.get(position).getOptions(), optAnswer, new Option_Adapter.optionClickListener() {
            @Override
            public void optionClick(int optionPosition) {
                // list.get(position).setFlag(true);
                list.get(position).setmAnswer(String.valueOf(optionPosition));
                //changeAnswer(optionPosition);
                notifyDataSetChanged();
            }
        });
        optionAdapter.notifyDataSetChanged();
        holder.optionRecyclerView.setHasFixedSize(true);
        // holder.optionRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        holder.optionRecyclerView.setLayoutManager(gridLayoutManager);
        holder.optionRecyclerView.setAdapter(optionAdapter);
        /*try {
            for (int i = 0; i < list.get(position).getOptions().size(); i++) {

                LinearLayout a = null;
                if (i % 3 == 0) {
                    a = new LinearLayout(context);
                    a.setOrientation(LinearLayout.HORIZONTAL);
                }
           *//* LinearLayout a = new LinearLayout(context);
            a.setOrientation(LinearLayout.HORIZONTAL);*//*
                TextView view1 = new TextView(context);
                TextView view2 = new TextView(context);
                TextView view3 = new TextView(context);
                view1.setText("good");
                view2.setText("sad");
                view3.setText("bad");
                a.addView(view1);
                a.addView(view2);
                a.addView(view3);

                myRoot.addView(a);

            }
        }catch (Exception e){
            e.printStackTrace();
        }*/


       /* for (int i = 0; i < list.get(position).getOptions().size(); i++) {
            holder.tv_tribeometer_opt_1.setText(list.get(position).getOptions().get(0).getOptionName());
            holder.tv_tribeometer_opt_2.setText(list.get(position).getOptions().get(1).getOptionName());
            holder.tv_tribeometer_opt_3.setText(list.get(position).getOptions().get(2).getOptionName());
            holder.tv_tribeometer_opt_4.setText(list.get(position).getOptions().get(3).getOptionName());

           *//* try {
                if (list.get(position).getOptions().size()>3) {
                    holder.tv_tribeometer_opt_4.setVisibility(View.GONE);
                    holder.secondRowLayout.setVisibility(View.VISIBLE);
                    if (list.get(position).getOptions().size() >= 3 && !(list.get(position).getOptions().get(3).getOptionName()).equals("")) {
                        holder.tv_tribeometer_opt_4.setText(list.get(position).getOptions().get(3).getOptionName());
                    } else {
                        holder.tv_tribeometer_opt_4.setVisibility(View.GONE);
                    }
                }else {
                    holder.tv_tribeometer_opt_4.setVisibility(View.GONE);
                }

            }catch (Exception e){
                e.printStackTrace();
            }*//*
            if (list.get(position).getmAnswer().equals("1")){
                holder.tv_tribeometer_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_tribeometer_opt_1.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_tribeometer_opt_1.setTypeface(null, Typeface.BOLD);

            }
            if (list.get(position).getmAnswer().equals("2")){
                holder.tv_tribeometer_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_tribeometer_opt_2.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_tribeometer_opt_2.setTypeface(null, Typeface.BOLD);
            }
            if (list.get(position).getmAnswer().equals("3")){
                holder.tv_tribeometer_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_tribeometer_opt_3.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_tribeometer_opt_3.setTypeface(null, Typeface.BOLD);
            }
        if (list.get(position).getmAnswer().equals("4")){
                holder.tv_tribeometer_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_tribeometer_opt_4.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_tribeometer_opt_4.setTypeface(null, Typeface.BOLD);
            }



        }*/


        holder.tv_tribeometer_opt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.get(position).setFlag(true);
                list.get(position).setmAnswer("1");
                holder.tv_tribeometer_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_tribeometer_opt_1.setTextColor(context.getResources().getColor(R.color.white));

                holder.tv_tribeometer_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                //Text Color------------------------------
                holder.tv_tribeometer_opt_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_4.setTextColor(context.getResources().getColor(R.color.textcolor));



            }
        });
        holder.tv_tribeometer_opt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setmAnswer("2");
                list.get(position).setFlag(true);
                holder.tv_tribeometer_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_tribeometer_opt_2.setTextColor(context.getResources().getColor(R.color.white));

                holder.tv_tribeometer_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                //Text Color------------------------------
                holder.tv_tribeometer_opt_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_4.setTextColor(context.getResources().getColor(R.color.textcolor));


            }
        });
        holder.tv_tribeometer_opt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setmAnswer("3");
                list.get(position).setFlag(true);
                holder.tv_tribeometer_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_tribeometer_opt_3.setTextColor(context.getResources().getColor(R.color.white));

                holder.tv_tribeometer_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                //Text Color------------------------------
                holder.tv_tribeometer_opt_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_4.setTextColor(context.getResources().getColor(R.color.textcolor));


            }
        });
        holder.tv_tribeometer_opt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setmAnswer("4");
                list.get(position).setFlag(true);
                holder.tv_tribeometer_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_tribeometer_opt_4.setTextColor(context.getResources().getColor(R.color.white));

                holder.tv_tribeometer_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                //Text Color------------------------------
                holder.tv_tribeometer_opt_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_1.setTextColor(context.getResources().getColor(R.color.textcolor));


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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tribeometer_qus, tv_tribeometer_qus_no;
        TextView tv_tribeometer_opt_1, tv_tribeometer_opt_2, tv_tribeometer_opt_3,
                tv_tribeometer_opt_4, tv_tribeometer_opt_5, tv_tribeometer_opt_6,
                tv_tribeometer_opt_7, tv_tribeometer_opt_8, tv_tribeometer_opt_9;
        LinearLayout secondRowLayout, thirdRowLayout;
        RecyclerView optionRecyclerView;

        public ViewHolder(View v) {
            super(v);
            tv_tribeometer_qus = v.findViewById(R.id.tv_tribeometer_qus);
            tv_tribeometer_qus_no = v.findViewById(R.id.tv_tribeometer_qus_no);
            tv_tribeometer_opt_1 = v.findViewById(R.id.tv_tribeometer_opt_1);
            tv_tribeometer_opt_2 = v.findViewById(R.id.tv_tribeometer_opt_2);
            tv_tribeometer_opt_3 = v.findViewById(R.id.tv_tribeometer_opt_3);
            tv_tribeometer_opt_4 = v.findViewById(R.id.tv_tribeometer_opt_4);

            optionRecyclerView = v.findViewById(R.id.option_recyclerview);

        }
    }
}
