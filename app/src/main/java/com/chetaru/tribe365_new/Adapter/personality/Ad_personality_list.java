package com.chetaru.tribe365_new.Adapter.personality;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelDiagnosticsQusList;
import com.chetaru.tribe365_new.R;

import java.util.ArrayList;
import java.util.List;

public class Ad_personality_list extends RecyclerView.Adapter<Ad_personality_list.ViewHolder> {

    List<ModelDiagnosticsQusList> list = new ArrayList<>();
    private Context context;


    public Ad_personality_list(List<ModelDiagnosticsQusList> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
    }

    @Override
    public Ad_personality_list.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_personality_list, parent, false);
        return new Ad_personality_list.ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(final Ad_personality_list.ViewHolder holder, final int position) {
        holder.tv_diagnostics_qus.setText(list.get(position).getQuestion());
        holder.tv_diagnostics_qus_no.setText(list.get(position).getQuestionId().toString());
        for (int i = 0; i < list.get(position).getOptions().size(); i++) ;
        {


            holder.tv_diagnostics_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
            holder.tv_diagnostics_opt_1.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_diagnostics_opt_1.setText(list.get(position).getOptions().get(0).getOptionName());

            holder.tv_diagnostics_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
            holder.tv_diagnostics_opt_2.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_diagnostics_opt_2.setText(list.get(position).getOptions().get(1).getOptionName());

            holder.tv_diagnostics_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
            holder.tv_diagnostics_opt_3.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_diagnostics_opt_3.setText(list.get(position).getOptions().get(2).getOptionName());

            holder.tv_diagnostics_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
            holder.tv_diagnostics_opt_4.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_diagnostics_opt_4.setText(list.get(position).getOptions().get(3).getOptionName());

            holder.tv_diagnostics_opt_5.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
            holder.tv_diagnostics_opt_5.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.tv_diagnostics_opt_5.setText(list.get(position).getOptions().get(4).getOptionName());

            //show select value--------------------
            if (list.get(position).getmAnswer().equals("1")) {
                holder.tv_diagnostics_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_diagnostics_opt_1.setTextColor(context.getResources().getColor(R.color.white));
            }
            if (list.get(position).getmAnswer().equals("2")) {
                holder.tv_diagnostics_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_diagnostics_opt_2.setTextColor(context.getResources().getColor(R.color.white));
            }
            if (list.get(position).getmAnswer().equals("3")) {
                holder.tv_diagnostics_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_diagnostics_opt_3.setTextColor(context.getResources().getColor(R.color.white));
            }
            if (list.get(position).getmAnswer().equals("4")) {
                holder.tv_diagnostics_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_diagnostics_opt_4.setTextColor(context.getResources().getColor(R.color.white));
            }
            if (list.get(position).getmAnswer().equals("5")) {
                holder.tv_diagnostics_opt_5.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_diagnostics_opt_5.setTextColor(context.getResources().getColor(R.color.white));
            }

            //--------------------------------------------
        }
        holder.tv_diagnostics_opt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------setAnswer
                list.get(position).setmAnswer("1");
                list.get(position).setFlag(true);

                holder.tv_diagnostics_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_diagnostics_opt_1.setTextColor(context.getResources().getColor(R.color.white));

                holder.tv_diagnostics_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_5.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                //Text Color------------------------------
                holder.tv_diagnostics_opt_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_5.setTextColor(context.getResources().getColor(R.color.textcolor));

            }
        });
        holder.tv_diagnostics_opt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------setAnswer
                list.get(position).setmAnswer("2");
                list.get(position).setFlag(true);
                holder.tv_diagnostics_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_diagnostics_opt_2.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_diagnostics_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_5.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                //Text Color------------------------------
                holder.tv_diagnostics_opt_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_5.setTextColor(context.getResources().getColor(R.color.textcolor));



            }
        });
        holder.tv_diagnostics_opt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------setAnswer
                list.get(position).setmAnswer("3");
                list.get(position).setFlag(true);
                holder.tv_diagnostics_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_diagnostics_opt_3.setTextColor(context.getResources().getColor(R.color.white));

                holder.tv_diagnostics_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_5.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));

                holder.tv_diagnostics_opt_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_5.setTextColor(context.getResources().getColor(R.color.textcolor));


            }
        });
        holder.tv_diagnostics_opt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------setAnswer
                list.get(position).setmAnswer("4");
                list.get(position).setFlag(true);
                holder.tv_diagnostics_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_diagnostics_opt_4.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_diagnostics_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_5.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));

                //Text Color------------------------------
                holder.tv_diagnostics_opt_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_1.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_5.setTextColor(context.getResources().getColor(R.color.textcolor));


            }
        });
        holder.tv_diagnostics_opt_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------setAnswer
                list.get(position).setmAnswer("5");
                list.get(position).setFlag(true);
                holder.tv_diagnostics_opt_5.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_diagnostics_opt_5.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_diagnostics_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_diagnostics_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                //Text Color------------------------------
                holder.tv_diagnostics_opt_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_diagnostics_opt_1.setTextColor(context.getResources().getColor(R.color.textcolor));


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
        TextView tv_diagnostics_qus, tv_diagnostics_qus_no;
        TextView tv_diagnostics_opt_1, tv_diagnostics_opt_2, tv_diagnostics_opt_3, tv_diagnostics_opt_4, tv_diagnostics_opt_5;

        public ViewHolder(View v) {
            super(v);
            tv_diagnostics_qus = v.findViewById(R.id.tv_diagnostics_qus);
            tv_diagnostics_qus_no = v.findViewById(R.id.tv_diagnostics_qus_no);
            tv_diagnostics_opt_1 = v.findViewById(R.id.tv_diagnostics_opt_1);
            tv_diagnostics_opt_2 = v.findViewById(R.id.tv_diagnostics_opt_2);
            tv_diagnostics_opt_3 = v.findViewById(R.id.tv_diagnostics_opt_3);
            tv_diagnostics_opt_4 = v.findViewById(R.id.tv_diagnostics_opt_4);
            tv_diagnostics_opt_5 = v.findViewById(R.id.tv_diagnostics_opt_5);
        }
    }
}
