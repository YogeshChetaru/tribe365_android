package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.BeliefValue;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.RowDotValueListHomeBinding;

import java.util.ArrayList;

public class Ad_dot_home_values extends RecyclerView.Adapter<Ad_dot_home_values.ViewHolder> {

    ArrayList<BeliefValue> list;
    Context context;
    OnItemClickCustom onItemClickCustom;
    private Object[] mList;

    public  Ad_dot_home_values(ArrayList<BeliefValue> list, Context context, OnItemClickCustom onItemClickCustom) {
        this.context = context;
        this.list = list;
        this.onItemClickCustom = onItemClickCustom;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        RowDotValueListHomeBinding binding=RowDotValueListHomeBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dot_value_list_home, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.itemBinding.tvValue.setText(list.get(position).getName());

        if (list.get(position).isSelected()) {
            holder.itemBinding.llMain.setBackground(context.getResources().getDrawable(R.drawable.roundedcorner_border_red_solid));
            holder.itemBinding.tvValue.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.itemBinding.llMain.setBackground(context.getResources().getDrawable(R.drawable.roundedcorner_border_black_solid));
            holder.itemBinding.tvValue.setTextColor(context.getResources().getColor(R.color.textcolor));
        }

        holder.itemBinding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isSelected()) {
                    holder.itemBinding.llMain.setBackground(context.getResources().getDrawable(R.drawable.roundedcorner_border_black_solid));
                    holder.itemBinding.tvValue.setTextColor(context.getResources().getColor(R.color.textcolor));
                    list.get(position).setSelected(false);
                } else {
                    holder.itemBinding.llMain.setBackground(context.getResources().getDrawable(R.drawable.roundedcorner_border_red_solid));
                    holder.itemBinding.tvValue.setTextColor(context.getResources().getColor(R.color.white));
                    list.get(position).setSelected(true);
                }
                onItemClickCustom.onClick(R.id.tv_value, position, list);
                //notifyDataSetChanged();
            }
        });
        holder.itemBinding.llMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickCustom.dotValueLongClick(list.get(position));
                return true;
            }
        });
//        holder.tv_value.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCustom.onClick(R.id.tv_value, position, list);
//            }
//        });
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
        TextView tv_value;
        LinearLayout ll_main;
        RowDotValueListHomeBinding itemBinding;
        public ViewHolder(RowDotValueListHomeBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*tv_value = view.findViewById(R.id.tv_value);
            ll_main = view.findViewById(R.id.ll_main);*/
        }
    }

}
