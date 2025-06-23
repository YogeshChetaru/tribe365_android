package com.chetaru.tribe365_new.Adapter.KnowHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.KnowHome.KudosCampList;
import com.chetaru.tribe365_new.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class Ad_Multi_champions extends RecyclerView.Adapter<Ad_Multi_champions.ViewHolder> {

    List<KudosCampList> list;
    Context mContext;

    public Ad_Multi_champions(List<KudosCampList> lastMonthKudosChamp, Context mContext) {
        this.list=lastMonthKudosChamp;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.kudos_champ_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (list.get(position).getUserName()!=null) {
                holder.username.setText(list.get(position).getUserName().toString());
                holder.userValue.setText(list.get(position).getCount().toString());
                Glide.with(mContext)
                        .load(list.get(position).getUserImage())
                        .placeholder(R.drawable.user_circle)
                        .into(holder.imageView);
                if (position == 0) {
                    holder.username.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.userValue.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.cronImage.setVisibility(View.VISIBLE);
                    holder.imageView.setBorderWidth(1f);
                    holder.imageView.setBorderColor(mContext.getResources().getColor(R.color.red));
                    holder.bottomView.setVisibility(View.VISIBLE);
                } else {
                    holder.username.setTextColor(mContext.getResources().getColor(R.color.black));
                    holder.userValue.setTextColor(mContext.getResources().getColor(R.color.black));
                    holder.cronImage.setVisibility(View.GONE);
                    holder.bottomView.setVisibility(View.VISIBLE);
                    //holder.imageView.setBorderWidth(1f);
                    //holder.imageView.setBorderColor(mContext.getResources().getColor(R.color.white));
                }
            }
            if (position == list.size() - 1) {
                holder.bottomView.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        try {
            return list.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircularImageView imageView;
        ImageView cronImage;
        TextView username,userValue;
        View bottomView;
        public ViewHolder(@NonNull View v) {
            super(v);
            imageView=v.findViewById(R.id.user_image_cmp);
            cronImage=v.findViewById(R.id.cron_image);
            username=v.findViewById(R.id.user_name_cmp);
            userValue=v.findViewById(R.id.tv_value_cmp);
            bottomView=v.findViewById(R.id.bottom_view);
        }
    }
}
