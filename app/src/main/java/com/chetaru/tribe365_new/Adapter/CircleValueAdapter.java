package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.HomeBelief;
import com.chetaru.tribe365_new.CallBack.OnChildItemsClick;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.CircleDotValueListBinding;

import java.util.List;

public class CircleValueAdapter extends RecyclerView.Adapter<CircleValueAdapter.ViewHolder> {

    List<HomeBelief> listValue;
    Context context;
    OnChildItemsClick onItemClickCustom;

    public CircleValueAdapter(List<HomeBelief> listValue, Context context, OnChildItemsClick onItemClickCustom) {

        this.listValue = listValue;
        this.context = context;
        this.onItemClickCustom = onItemClickCustom;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        CircleDotValueListBinding binding=CircleDotValueListBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_dot_value_list,parent,false);
        return new ViewHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemBinding.listValueName.setText(listValue.get(position).getName());
        //holder.tv_value.setText(listValue.get(position).getTodayKudosCount()+"");
        try {
            if (listValue.get(position).getTodayDotValueKudoAwardCount()>99){
                holder.itemBinding.listDotValue.setText(99+"+");
            }else{
                holder.itemBinding.listDotValue.setText(listValue.get(position).getTodayDotValueKudoAwardCount()+"");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if (listValue.get(position).getTodayKudosCount()>99){
                holder.itemBinding.bottomDotValue.setText(99+"+");
            }else {
                holder.itemBinding.bottomDotValue.setText(listValue.get(position).getTodayKudosCount()+"");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //holder.ll_main.setBackgroundResource(R.drawable.card_view_half_circle);
         {
            holder.itemBinding.llMain.setVisibility(View.VISIBLE);
            if (listValue.get(position).isSelected()) {
                holder.itemBinding.listValueName.setBackground(context.getResources().getDrawable(R.drawable.circle_red));
                holder.itemBinding.cardMainLl.setBackground(context.getResources().getDrawable(R.drawable.circle_red));
               // holder.tv_value.setBackground(context.getResources().getDrawable(R.drawable.bottom_edge_white));
               // holder.value_card_ll.setBackground(context.getResources().getDrawable(R.drawable.bottom_edge_white));
               // holder.image_explanation.setBackgroundResource(R.drawable.explanation_wihite);
                holder.itemBinding.valueCardLl.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_white_red));
                holder.itemBinding.listValueName.setTextColor(context.getResources().getColor(R.color.white));
                holder.itemBinding.listDotValue.setTextColor(context.getResources().getColor(R.color.red));
                holder.itemBinding.bottomDotValue.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                holder.itemBinding.listValueName.setBackground(context.getResources().getDrawable(R.drawable.white_circle_big));
                holder.itemBinding.cardMainLl.setBackground(context.getResources().getDrawable(R.drawable.white_circle_big));
               // holder.tv_value.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_red));
                //holder.value_card_ll.setBackground(context.getResources().getDrawable(R.drawable.bottom_edge_red));
               // holder.image_explanation.setBackgroundResource(R.drawable.explanation_red);
                holder.itemBinding.valueCardLl.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_red_white));
                holder.itemBinding.listValueName.setTextColor(context.getResources().getColor(R.color.red));
                holder.itemBinding.listDotValue.setTextColor(context.getResources().getColor(R.color.white));
                holder.itemBinding.bottomDotValue.setTextColor(context.getResources().getColor(R.color.red));
            }
            holder.itemBinding.cardMainLl.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onClick(View v) {

                    if (listValue.get(position).isSelected()) {
                        holder.itemBinding.listValueName.setBackground(context.getResources().getDrawable(R.drawable.white_circle_big));
                        holder.itemBinding.cardMainLl.setBackground(context.getResources().getDrawable(R.drawable.white_circle_big));
                      //  holder.tv_value.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_red));
                        //holder.value_card_ll.setBackground(context.getResources().getDrawable(R.drawable.bottom_edge_red));
                       // holder.image_explanation.setBackgroundResource(R.drawable.explanation_red);
                        holder.itemBinding.valueCardLl.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_red_white));
                        holder.itemBinding.listValueName.setTextColor(context.getResources().getColor(R.color.red));
                        holder.itemBinding.listDotValue.setTextColor(context.getResources().getColor(R.color.white));
                        holder.itemBinding.bottomDotValue.setTextColor(context.getResources().getColor(R.color.red));

                        listValue.get(position).setSelected(false);
                    } else {
                        listValue.get(position).setSelected(true);
                        holder.itemBinding.listValueName.setBackground(context.getResources().getDrawable(R.drawable.circle_red));
                        holder.itemBinding.cardMainLl.setBackground(context.getResources().getDrawable(R.drawable.circle_red));
                        //holder.tv_value.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_white));
                        //holder.value_card_ll.setBackground(context.getResources().getDrawable(R.drawable.bottom_edge_white));
                        //holder.image_explanation.setBackgroundResource(R.drawable.explanation_wihite);
                        holder.itemBinding.valueCardLl.setBackground(context.getResources().getDrawable(R.drawable.bottom_half_white_red));
                        holder.itemBinding.listValueName.setTextColor(context.getResources().getColor(R.color.white));
                        holder.itemBinding.listDotValue.setTextColor(context.getResources().getColor(R.color.red));
                        holder.itemBinding.bottomDotValue.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    onItemClickCustom.onChildClick(R.id.tv_value, position, listValue);
                }
            });
            holder.itemBinding.valueCardLl.setOnClickListener(v -> {
                onItemClickCustom.onSubChildClick(R.id.tv_value, position, listValue);
            });

            holder.itemBinding.cardMainLl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickCustom.onLongClick(R.id.main_ll,position,listValue.get(position));
                    return true;
                }
            });
            holder.itemBinding.imageExplanation.setOnClickListener(v->{
               // onItemClickCustom.onParentClick(R.id.main_ll,position,listValue.get(position));
            });
        }
    }

    @Override
    public int getItemCount() {
        try {
            return listValue.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout ll_main;
        TextView tv_value;
        TextView tv_name;
        TextView bottom_dot_value;
        CardView value_card_ll;
        CardView main_card_ll;
        ImageView image_explanation;

        CircleDotValueListBinding itemBinding;
        public ViewHolder(@NonNull CircleDotValueListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* tv_name = view.findViewById(R.id.list_value_name);
            tv_value = view.findViewById(R.id.list_dot_value);
            ll_main = view.findViewById(R.id.ll_main);
            main_card_ll = view.findViewById(R.id.card_main_ll);
            value_card_ll = view.findViewById(R.id.value_card_ll);
            bottom_dot_value=view.findViewById(R.id.bottom_dot_value);
            image_explanation=view.findViewById(R.id.image_explanation);*/

        }
    }

}
