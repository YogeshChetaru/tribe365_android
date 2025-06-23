package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.HomeBelief;
import com.chetaru.tribe365_new.CallBack.OnChildItemsClick;
import com.chetaru.tribe365_new.R;

import java.util.List;

public class Ad_HomeKudosList extends RecyclerView.Adapter<Ad_HomeKudosList.ViewHolder> {
    List<HomeBelief> listValue;
    Context context;
    OnChildItemsClick onItemClickCustom;

    public Ad_HomeKudosList(List<HomeBelief> kudosList, Context mContext, OnChildItemsClick onChildItemsClick) {
        this.listValue=kudosList;
        this.context=mContext;
        this.onItemClickCustom=onChildItemsClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.home_kudos_list,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try{
            holder.kudos_name_tv.setText(listValue.get(position).getName());
            try {
                if (listValue.get(position).getTodayKudosCount()>99){
                    holder.kudos_value_tv.setText(99+"+");
                }else {
                    holder.kudos_value_tv.setText(listValue.get(position).getTodayKudosCount()+"");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                if (listValue.get(position).getTodayDotValueKudoAwardCount()>99){
                    holder.amazing_value_tv.setText(99+"+");
                }else {
                    holder.amazing_value_tv.setText(listValue.get(position).getTodayDotValueKudoAwardCount()+"");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if (listValue.get(position).isSelected()){
                holder.main_card_ll.setBackground(context.getResources().getDrawable(R.drawable.shape_color_5_green));
                holder.kudos_name_tv.setTextColor(context.getResources().getColor(R.color.white));

                holder.value_card_ll.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_white_red));
                holder.kudos_value_tv.setTextColor(context.getResources().getColor(R.color.red));
                holder.amazing_value_tv.setTextColor(context.getResources().getColor(R.color.white));


            }else {
                holder.main_card_ll.setBackground(context.getResources().getDrawable(R.drawable.card_bg_corner_white));
                holder.kudos_name_tv.setTextColor(context.getResources().getColor(R.color.red));

                holder.value_card_ll.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_red_white));
                holder.kudos_value_tv.setTextColor(context.getResources().getColor(R.color.white));
                holder.amazing_value_tv.setTextColor(context.getResources().getColor(R.color.red));


            }
            holder.main_card_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listValue.get(position).isSelected()){
                        holder.main_card_ll.setBackground(context.getResources().getDrawable(R.drawable.card_bg_corner_white));
                        holder.kudos_name_tv.setTextColor(context.getResources().getColor(R.color.red));

                        holder.value_card_ll.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_red_white));
                        holder.kudos_value_tv.setTextColor(context.getResources().getColor(R.color.white));
                        holder.amazing_value_tv.setTextColor(context.getResources().getColor(R.color.red));

                        listValue.get(position).setSelected(false);
                    }else {
                        holder.main_card_ll.setBackground(context.getResources().getDrawable(R.drawable.shape_color_5_green));
                        holder.kudos_name_tv.setTextColor(context.getResources().getColor(R.color.white));

                        holder.value_card_ll.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_white_red));
                        holder.kudos_value_tv.setTextColor(context.getResources().getColor(R.color.red));
                        holder.amazing_value_tv.setTextColor(context.getResources().getColor(R.color.white));

                        listValue.get(position).setSelected(true);

                    }
                    onItemClickCustom.onChildClick(R.id.top_ll,position,listValue);
                }
            });
            holder.top_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listValue.get(position).isSelected()){
                        holder.main_card_ll.setBackground(context.getResources().getDrawable(R.drawable.card_bg_corner_white));
                        holder.kudos_name_tv.setTextColor(context.getResources().getColor(R.color.red));

                        holder.value_card_ll.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_red_white));
                        holder.kudos_value_tv.setTextColor(context.getResources().getColor(R.color.white));
                        holder.amazing_value_tv.setTextColor(context.getResources().getColor(R.color.red));

                        listValue.get(position).setSelected(false);
                    }else {

                        holder.main_card_ll.setBackground(context.getResources().getDrawable(R.drawable.shape_color_5_green));
                        holder.kudos_name_tv.setTextColor(context.getResources().getColor(R.color.white));

                        holder.value_card_ll.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_white_red));
                        holder.kudos_value_tv.setTextColor(context.getResources().getColor(R.color.red));
                        holder.amazing_value_tv.setTextColor(context.getResources().getColor(R.color.white));

                        listValue.get(position).setSelected(true);

                    }
                    onItemClickCustom.onChildClick(R.id.top_ll,position,listValue);

                }
            });

            holder.kudos_value_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCustom.onSubChildClick(R.id.kudos_value_tv,position,listValue);
                }
            });
            holder.amazing_value_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCustom.onSubChildClick(R.id.amazing_value_tv,position,listValue);
                }
            });
            holder.main_card_ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickCustom.onLongClick(R.id.main_card_ll,position,listValue.get(position));
                    return true;
                }
            });
            holder.top_ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickCustom.onLongClick(R.id.top_ll,position,listValue.get(position));
                    return true;
                }
            });
        }catch (Exception e){e.printStackTrace();}

    }

    @Override
    public int getItemCount() {
        try {
            return listValue.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kudos_name_tv;
        TextView kudos_value_tv,amazing_value_tv;
        CardView main_card_ll,value_card_ll;
        LinearLayout top_ll;
        public ViewHolder(@NonNull View view) {
            super(view);
            value_card_ll= view.findViewById(R.id.value_card_ll);
            kudos_name_tv= view.findViewById(R.id.kudos_name_tv);
            kudos_value_tv=view.findViewById(R.id.kudos_value_tv);
            amazing_value_tv=view.findViewById(R.id.amazing_value_tv);
            main_card_ll=view.findViewById(R.id.main_card_ll);
            top_ll=view.findViewById(R.id.top_ll);
        }
    }
}
