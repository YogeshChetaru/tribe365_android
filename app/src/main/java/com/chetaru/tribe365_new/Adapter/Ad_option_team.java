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

import com.chetaru.tribe365_new.API.Models.TeamFeedback.Option;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.RowOptionTeamBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Ad_option_team extends RecyclerView.Adapter<Ad_option_team.ViewHolder> {
    public Context mContext;
    public List<Option> optionList = new ArrayList<>();
   public OptionClickListener mListener;

    public Ad_option_team(Context mContext, List<Option> optionList, OptionClickListener mListener) {
        this.mContext = mContext;
        this.optionList = optionList;
        this.mListener = mListener;
    }

    @NonNull
    @NotNull
    @Override
    public Ad_option_team.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        RowOptionTeamBinding binding=RowOptionTeamBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_option_team,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Ad_option_team.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try{

            if (optionList.get(position).isAnswerFlag()){
                holder.itemBinding.tvOptionName.setBackground(mContext.getResources().getDrawable(R.drawable.bg_option_red_round));
                holder.itemBinding.tvOptionName.setTextColor(mContext.getResources().getColor(R.color.white));
            }else {
                holder.itemBinding.tvOptionName.setBackground(mContext.getResources().getDrawable(R.drawable.bg_option_white));
                holder.itemBinding.tvOptionName.setTextColor(mContext.getResources().getColor(R.color.black));/* main_option_ll=view.findViewById(R.id.main_option_ll);
            tv_option_name=view.findViewById(R.id.tv_option_name);*/
            }
            holder.itemBinding.tvOptionName.setText(optionList.get(position).getOptionName());
            holder.itemBinding.mainOptionLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.optionClick(position);
                    optionList.get(position).setAnswerFlag(true);
                    for (int i=0;i<optionList.size();i++){
                        if (i==position){
                            holder.itemBinding.tvOptionName.setBackground(mContext.getResources().getDrawable(R.drawable.bg_option_red_round));
                            holder.itemBinding.tvOptionName.setTextColor(mContext.getResources().getColor(R.color.white));

                        }else {
                            optionList.get(i).setAnswerFlag(false);
                            holder.itemBinding.tvOptionName.setBackground(mContext.getResources().getDrawable(R.drawable.bg_option_white));
                            holder.itemBinding.tvOptionName.setTextColor(mContext.getResources().getColor(R.color.black));
                        }
                    }
                   // notifyDataSetChanged();
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return optionList.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout main_option_ll;
        TextView tv_option_name;
        RowOptionTeamBinding itemBinding;
        public ViewHolder(RowOptionTeamBinding itemBinding){
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* main_option_ll=view.findViewById(R.id.main_option_ll);
            tv_option_name=view.findViewById(R.id.tv_option_name);*/
        }
    }

    public interface OptionClickListener{
        void optionClick(int name);
    }
}
