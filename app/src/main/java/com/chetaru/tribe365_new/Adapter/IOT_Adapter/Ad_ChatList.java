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
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_msg, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (list.get(position).getUserType().equals("Admin")) {
            holder.itemBinding.rlYou.setVisibility(View.VISIBLE);
            holder.itemBinding.rlMe.setVisibility(View.GONE);
            //holder.tv_msg_L.setText(list.get(position).getMessage());
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

                //load("http://live.thechangeconsultancy.co/demo_tellsid/" + list.get(position).getMessage()) //test
               /* Picasso.get(). load(list.get(position).getmMsgImageUrl()) //live
                        .placeholder(R.drawable.noimage)
                        .into(holder.iv_msgImg_L);*/
                Glide.with(context)
                        .load(list.get(position).getmMsgImageUrl())
                        .placeholder(R.drawable.noimage)
                        .into(holder.itemBinding.ivMsgImgL);
//
                //live-----------------------------------------------------
//                Picasso.get().
//                        load("http://tellsid.softintelligence.co.uk/assets/upload/chatimg/" + list.get(position).getMessage()) //live
//                        .placeholder(R.drawable.noimage)
//                        .into(holder.iv_msgImg_L);
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
            //holder.tv_msg.setText(list.get(position).getMessage());
            holder.itemBinding.tvChatTime.setText(utility.getDate(list.get(position).getmCreated_at()));
            if (list.get(position).getmMsgImageUrl().equals("")) {
                holder.itemBinding.tvMsg.setText(list.get(position).getMessage());
                holder.itemBinding.tvMsg.setVisibility(View.VISIBLE);
                holder.itemBinding.ivMsgImg.setVisibility(View.GONE);
            } else {
                holder.itemBinding.tvMsg.setVisibility(View.GONE);
                holder.itemBinding.ivMsgImg.setVisibility(View.VISIBLE);
                //Picasso.get().load("http://live.thechangeconsultancy.co/demo_tellsid/assets/upload/chatimg/" + //test

                //demo----------------------------------------------------
               /* Picasso.get().load(list.get(position).getmMsgImageUrl())
                        .placeholder(R.drawable.noimage)
                        .into(holder.iv_msgImg);*/
                Glide.with(context)
                        .load(list.get(position).getmMsgImageUrl())
                        .placeholder(R.drawable.noimage)
                        .into(holder.itemBinding.ivMsgImg);
                //live----------------------------------------------
//                Picasso.get().load("http://tellsid.softintelligence.co.uk/assets/upload/chatimg/" + //live
//                        list.get(position).getMessage())
//                        .placeholder(R.drawable.noimage)
//                        .into(holder.iv_msgImg);
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
        TextView tv_chat_titleName_L,tv_chat_time_L, tv_msg_L, tv_msgstatus_L;
        TextView tv_chat_time, tv_msg, tv_msgstatus;
        RelativeLayout rl_me, rl_you;
        ImageView frag_profile_im_L, iv_msgImg_L;
        ImageView frag_profile_im, iv_msgImg;
        RowChatMsgBinding itemBinding;
        public ViewHolder(RowChatMsgBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* rl_me = v.findViewById(R.id.rl_me);
            rl_you = v.findViewById(R.id.rl_you);
            tv_chat_titleName_L=v.findViewById(R.id.tv_chat_titleName_L);
            tv_chat_time_L = v.findViewById(R.id.tv_chat_time_L);
            frag_profile_im_L = v.findViewById(R.id.frag_profile_im_L);
            tv_msg_L = v.findViewById(R.id.tv_msg_L);
            // tv_msgstatus_L = v.findViewById(R.id.tv_msgstatus_L);
            iv_msgImg_L = v.findViewById(R.id.iv_msgImg_L);

            frag_profile_im = v.findViewById(R.id.frag_profile_im);
            tv_chat_time = v.findViewById(R.id.tv_chat_time);
            tv_msg = v.findViewById(R.id.tv_msg);
            // tv_msgstatus = v.findViewById(R.id.tv_msgstatus);
            iv_msgImg = v.findViewById(R.id.iv_msgImg);*/
        }
    }
}
