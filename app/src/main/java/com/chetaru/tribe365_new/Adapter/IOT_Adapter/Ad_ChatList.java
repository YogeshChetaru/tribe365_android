package com.chetaru.tribe365_new.Adapter.IOT_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.ModelChat;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.RowChatMsgBinding;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class Ad_ChatList extends RecyclerView.Adapter<Ad_ChatList.ViewHolder> {

    List<ModelChat> list = new ArrayList<>();
    Utility utility;
    chatImageViewListener mListener;
    private Context context;


    public Ad_ChatList(List<ModelChat> list, Context context, chatImageViewListener mListener) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
        this.utility = new Utility();
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        RowChatMsgBinding binding=RowChatMsgBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (list.get(position).getUserType().equals("Admin")) {
            holder.itemBinding.rlYou.setVisibility(View.VISIBLE);
            holder.itemBinding.rlMe.setVisibility(View.GONE);
            holder.itemBinding.tvChatTitleNameL.setText(list.get(position).getmName());
            holder.itemBinding.tvChatTimeL.setText(utility.getDate(list.get(position).getmCreated_at()));

            if (list.get(position).getmMsgImageUrl().equals("")) {
                holder.itemBinding.tvMsgL.setText(list.get(position).getMessage());
                holder.itemBinding.tvMsgL.setVisibility(View.VISIBLE);
                holder.itemBinding.ivMsgImgL.setVisibility(View.GONE);
            } else {
                holder.itemBinding.tvMsgL.setVisibility(View.GONE);
                holder.itemBinding.ivMsgImgL.setVisibility(View.VISIBLE);

                //demo--------------------------------------------

                Glide.with(context)
                        .load(list.get(position).getmMsgImageUrl())
                        .placeholder(R.drawable.noimage)
                        .into(holder.itemBinding.ivMsgImgL);
//
                //live-----------------------------------------------------

            }
            holder.itemBinding.ivMsgImgL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.chatImageClick(list.get(position));
                }
            });
        } else {
            holder.itemBinding.rlYou.setVisibility(View.GONE);
            holder.itemBinding.rlMe.setVisibility(View.VISIBLE);
            holder.itemBinding.tvChatTime.setText(utility.getDate(list.get(position).getmCreated_at()));
            if (list.get(position).getmMsgImageUrl().equals("")) {
                holder.itemBinding.tvMsg.setText(list.get(position).getMessage());
                holder.itemBinding.tvMsg.setVisibility(View.VISIBLE);
                holder.itemBinding.ivMsgImg.setVisibility(View.GONE);
            } else {
                holder.itemBinding.tvMsg.setVisibility(View.GONE);
                holder.itemBinding.ivMsgImg.setVisibility(View.VISIBLE);

                //demo----------------------------------------------------

                Glide.with(context)
                        .load(list.get(position).getmMsgImageUrl())
                        .placeholder(R.drawable.noimage)
                        .into(holder.itemBinding.ivMsgImg);
                //live----------------------------------------------
            }
            holder.itemBinding.ivMsgImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.chatImageClick(list.get(position));
                }
            });
        }

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

    public interface chatImageViewListener {
        public void chatImageClick(ModelChat chat);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowChatMsgBinding itemBinding;
        public ViewHolder(RowChatMsgBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
        }
    }
}
