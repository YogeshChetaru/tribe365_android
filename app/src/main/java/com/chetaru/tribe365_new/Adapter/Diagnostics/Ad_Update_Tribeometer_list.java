package com.chetaru.tribe365_new.Adapter.Diagnostics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.Update.ModelUpdateDiagnostics;
import com.chetaru.tribe365_new.Adapter.Update_opt_Adapter;
import com.chetaru.tribe365_new.R;

import java.util.ArrayList;
import java.util.List;

public class Ad_Update_Tribeometer_list extends RecyclerView.Adapter<Ad_Update_Tribeometer_list.ViewHolder> {

    List<ModelUpdateDiagnostics> list = new ArrayList<>();
    private Context context;


    public Ad_Update_Tribeometer_list(List<ModelUpdateDiagnostics> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
    }

    @Override
    public Ad_Update_Tribeometer_list.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tribeometer_list, parent, false);
        return new Ad_Update_Tribeometer_list.ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    @Override
    public void onBindViewHolder(final Ad_Update_Tribeometer_list.ViewHolder holder, final int position) {
        holder.tv_tribeometer_qus.setText(list.get(position).getQuestion());
        holder.tv_tribeometer_qus_no.setText(list.get(position).getQuestionId() + "");

        Update_opt_Adapter optionAdapter = new Update_opt_Adapter(context, list.get(position).getOptions(), new Update_opt_Adapter.updateOptionListener() {
            @Override
            public void updateOptionClick(int optionPosition) {
                list.get(position).setmAnswer(String.valueOf(optionPosition));
            }
        });
        optionAdapter.notifyDataSetChanged();
        holder.optionRecyclerView.hasFixedSize();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        holder.optionRecyclerView.setLayoutManager(gridLayoutManager);
        holder.optionRecyclerView.setAdapter(optionAdapter);

        /*try {
            for (int i = 0; i < list.get(position).getOptions().size(); i++) {
                holder.tv_tribeometer_opt_1.setText(list.get(position).getOptions().get(0).getOptionName());
                holder.tv_tribeometer_opt_2.setText(list.get(position).getOptions().get(1).getOptionName());
                holder.tv_tribeometer_opt_3.setText(list.get(position).getOptions().get(2).getOptionName());
                holder.tv_tribeometer_opt_4.setText(list.get(position).getOptions().get(3).getOptionName());


                if (list.get(position).getOptions().get(0).getIsChecked().equals(true)) {
                    list.get(position).setmAnswer("1");
                    holder.tv_tribeometer_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                    holder.tv_tribeometer_opt_1.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tv_tribeometer_opt_1.setTypeface(null, Typeface.BOLD);

                }
                if (list.get(position).getOptions().get(1).getIsChecked().equals(true)) {
                    list.get(position).setmAnswer("2");
                    holder.tv_tribeometer_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                    holder.tv_tribeometer_opt_2.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tv_tribeometer_opt_2.setTypeface(null, Typeface.BOLD);
                }
                if (list.get(position).getOptions().get(2).getIsChecked().equals(true)) {
                    list.get(position).setmAnswer("3");
                    holder.tv_tribeometer_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                    holder.tv_tribeometer_opt_3.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tv_tribeometer_opt_3.setTypeface(null, Typeface.BOLD);
                }
                if (list.get(position).getOptions().get(3).getIsChecked().equals(true)) {
                    list.get(position).setmAnswer("4");
                    holder.tv_tribeometer_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                    holder.tv_tribeometer_opt_4.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tv_tribeometer_opt_4.setTypeface(null, Typeface.BOLD);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }*/


        holder.tv_tribeometer_opt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.get(position).setmAnswer("1");
                list.get(position).getOptions().get(0).setIsChecked(true);
                list.get(position).getOptions().get(1).setIsChecked(false);
                list.get(position).getOptions().get(2).setIsChecked(false);
                list.get(position).getOptions().get(3).setIsChecked(false);


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
                list.get(position).getOptions().get(1).setIsChecked(true);
                list.get(position).getOptions().get(0).setIsChecked(false);
                list.get(position).getOptions().get(2).setIsChecked(false);
                list.get(position).getOptions().get(3).setIsChecked(false);

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
                list.get(position).getOptions().get(2).setIsChecked(true);
                list.get(position).getOptions().get(0).setIsChecked(false);
                list.get(position).getOptions().get(1).setIsChecked(false);
                list.get(position).getOptions().get(3).setIsChecked(false);

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
                list.get(position).getOptions().get(3).setIsChecked(true);
                list.get(position).getOptions().get(0).setIsChecked(false);
                list.get(position).getOptions().get(2).setIsChecked(false);
                list.get(position).getOptions().get(1).setIsChecked(false);
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
       /* holder.tv_tribeometer_opt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_tribeometer_opt_1.setBackground(context.getResources().getDrawable(R.drawable.shape_color_orange));
                holder.tv_tribeometer_opt_1.setTextColor(context.getResources().getColor(R.color.white));
                holder.tv_tribeometer_opt_1.setTypeface(null,Typeface.BOLD);

                holder.tv_tribeometer_opt_2.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_3.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_4.setBackground(context.getResources().getDrawable(R.drawable.shape_color_normal));
                holder.tv_tribeometer_opt_2.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_3.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_4.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.tv_tribeometer_opt_2.setTypeface(null,Typeface.NORMAL);
                holder.tv_tribeometer_opt_3.setTypeface(null,Typeface.NORMAL);
                holder.tv_tribeometer_opt_4.setTypeface(null,Typeface.NORMAL);

            }
        });*/
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
        TextView tv_tribeometer_qus, tv_tribeometer_qus_no;
        TextView tv_tribeometer_opt_1, tv_tribeometer_opt_2, tv_tribeometer_opt_3, tv_tribeometer_opt_4;

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
//            this.setIsRecyclable(true);
        }
    }
}
