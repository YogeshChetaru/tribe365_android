package com.chetaru.tribe365_new.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.HomeBelief;
import com.chetaru.tribe365_new.databinding.RowBeliefFilterListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Ad_home_belief_filter extends RecyclerView.Adapter<Ad_home_belief_filter.ViewHolder> {
    List<HomeBelief> list;
    Context context;
    String showText;
    String topTitle;

    public Ad_home_belief_filter(List<HomeBelief> list, Context context, String showText,String topTitle) {
        this.list = list;
        //list
        this.context = context;
        this.showText = showText;
        this.topTitle=topTitle;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        //holder.tv_name.setText(list.get(position).getName());
        holder.itemBinding.tvBeliefName.setText(list.get(position).getName());
        if (topTitle.equals("Kudos Values")) {
            try{
                holder.itemBinding.tvBeliefName.setVisibility(View.VISIBLE);
                holder.itemBinding.tvTodayValue.setVisibility(View.VISIBLE);
            if (showText.equals("Today")) {
                holder.itemBinding.tvTodayValue.setText(list.get(position).getTodayKudosCount() + "");
            } else if (showText.equals("Yesterday")) {
                holder.itemBinding.tvTodayValue.setText(list.get(position).getYesterdayKudosCount() + "");
            } else if (showText.equals("Last week")) {
                holder.itemBinding.tvTodayValue.setText(list.get(position).getLastWeekKudosCount() + "");
            } else if (showText.equals("This week")) {
                holder.itemBinding.tvTodayValue.setText(list.get(position).getThisWeekKudosCount() + "");
            } else if (showText.equals("Last month")) {
                holder.itemBinding.tvTodayValue.setText(list.get(position).getLastMonthKudosCount() + "");
            } else if (showText.equals("This month")) {
                holder.itemBinding.tvTodayValue.setText(list.get(position).getThisMonthKudosCount() + "");
            } else if (showText.equals("Total")) {
                holder.itemBinding.tvTodayValue.setText(list.get(position).getTotalKudosCount().toString());
            }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try{
                holder.itemBinding.tvBeliefName.setVisibility(View.VISIBLE);
                holder.itemBinding.tvTodayValue.setVisibility(View.VISIBLE);
                holder.itemBinding.tvBeliefName.setText(list.get(position).getName());
                if (showText.equals("Today")) {
                    holder.itemBinding.tvTodayValue.setText(list.get(position).getTodayDotValueKudoAwardCount() + "");
                } else if (showText.equals("Yesterday")) {
                    holder.itemBinding.tvTodayValue.setText(list.get(position).getYesterdayDotValueKudoAwardCount() + "");
                } else if (showText.equals("Last week")) {
                    holder.itemBinding.tvTodayValue.setText(list.get(position).getLastWeekDotValueKudoAwardCount() + "");
                } else if (showText.equals("This week")) {
                    holder.itemBinding.tvTodayValue.setText(list.get(position).getThisWeekDotValueKudoAwardCount() + "");
                } else if (showText.equals("Last month")) {
                    holder.itemBinding.tvTodayValue.setText(list.get(position).getLastMonthDotValueKudoAwardCount() + "");
                } else if (showText.equals("This month")) {
                    holder.itemBinding.tvTodayValue.setText(list.get(position).getThisMonthDotValueKudoAwardCount() + "");
                } else if (showText.equals("Total")) {
                        holder.itemBinding.tvTodayValue.setText(list.get(position).getTotalDotValueKudoAwardCount().toString());

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        holder.itemBinding.llMain.setOnClickListener(v-> {});
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        RowBeliefFilterListBinding binding=RowBeliefFilterListBinding.inflate(inflater,parent,false);
       // View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_belief_filter_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public int getItemCount() {

        try {
        return list.size();
        }catch (Exception e) {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_value;
        LinearLayout ll_main;
        RowBeliefFilterListBinding itemBinding;
        public ViewHolder(RowBeliefFilterListBinding itemBinding){
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* tv_name = v.findViewById(R.id.tv_belief_name);
            tv_value = v.findViewById(R.id.tv_today_value);
            ll_main = v.findViewById(R.id.ll_main);*/
        }
    }

}
