package com.chetaru.tribe365_new.Adapter.KnowHome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.KnowHome.KudosAwardList;
import com.chetaru.tribe365_new.API.Models.MemberHome.LatestKudosAward;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.KudosAwardListBinding;
import com.chetaru.tribe365_new.utility.MyCircleImageView;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.List;

public class Ad_kudos_awards_list extends RecyclerView.Adapter<Ad_kudos_awards_list.MyViewHolder> {

    List<LatestKudosAward> list;
    Context mContext;
    Utility utility;
    groupUsersListener mListener;
    public Ad_kudos_awards_list(List<LatestKudosAward> list, Context mContext, groupUsersListener mListener) {
        this.list = list;
        this.mContext = mContext;
        utility= new Utility();
        this.mListener= mListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        KudosAwardListBinding binding=KudosAwardListBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.kudos_award_list,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.itemBinding.awardDescTv.setText(Html.fromHtml(list.get(position).getAwardDescription()));
            //award_value_tv= view.findViewById(R.id.award_name_tv);
            holder.itemBinding.awardNameTv.setText(list.get(position).getAwardValue());
            if (list.get(position).getKudoAwardCount()>0){
                Glide.with(mContext).load(R.drawable.group_award).placeholder(R.drawable.group_award).into(holder.itemBinding.personImage);
                holder.itemBinding.awardPersonNameTv.setText(list.get(position).getUserName()+ " & "+ list.get(position).getKudoAwardCount() +" more");
            }else {
                holder.itemBinding.awardPersonNameTv.setText(list.get(position).getUserName());
                Glide.with(mContext).load(list.get(position).getUserImage()).placeholder(R.drawable.user_circle).into(holder.itemBinding.personImage);
            }



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position).getViewMoreUsers().size()>0){
                        mListener.groupUser(list.get(position).getAwardValue(),list.get(position).getAwardDescription(), list.get(position).getViewMoreUsers());
                    }
                }
            });
            if(position==list.size()-1){
                holder.itemBinding.bottomView.setVisibility(View.GONE);
            }else {
                holder.itemBinding.bottomView.setVisibility(View.VISIBLE);
            }
            String newDate= list.get(position).getAwardDate();
            holder.itemBinding.awardDateTv.setText(utility.getDate(newDate));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        try{
            return list.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        KudosAwardListBinding itemBinding;
        public MyViewHolder(@NonNull KudosAwardListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
        }
    }

    public interface groupUsersListener{
        public void groupUser(String awardValue,String desc,List<KudosAwardList> userList);
    }
}
