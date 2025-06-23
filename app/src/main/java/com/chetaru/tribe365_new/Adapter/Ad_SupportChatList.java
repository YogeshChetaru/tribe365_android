package com.chetaru.tribe365_new.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.SupportMessage;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.utility.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Ad_SupportChatList extends RecyclerView.Adapter<Ad_SupportChatList.ViewHolder> {

    //same Adapter as Ad_chatList Adapter on call chat detail list
    List<SupportMessage> list = new ArrayList<>();
    Utility utility;
    SupportDetailImageViewListener mListener;
    private Context context;

    public Ad_SupportChatList(List<SupportMessage> list, Context context, SupportDetailImageViewListener mListener) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
        this.utility = new Utility();
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.get(position).getUserType().equals("Admin")) {
            holder.rl_you.setVisibility(View.VISIBLE);
            holder.rl_me.setVisibility(View.GONE);
            //holder.tv_msg_L.setText(list.get(position).getMessage());
            holder.tv_chat_time_L.setText(utility.getDate(list.get(position).getCreatedAt()));

            if (list.get(position).getMsgImageUrl().equals("")) {
                holder.tv_msg_L.setText(list.get(position).getMessage());
                holder.tv_msg_L.setVisibility(View.VISIBLE);
                holder.iv_msgImg_L.setVisibility(View.GONE);
            } else {
                holder.tv_msg_L.setVisibility(View.GONE);
                holder.iv_msgImg_L.setVisibility(View.VISIBLE);

                //demo--------------------------------------------
                Picasso.get().
                        //load("http://live.thechangeconsultancy.co/demo_tellsid/" + list.get(position).getMessage()) //test
                                load(list.get(position).getMsgImageUrl()) //live
                        .placeholder(R.drawable.noimage)
                        .into(holder.iv_msgImg_L);
//
                //live-----------------------------------------------------
//                Picasso.get().
//                        load("http://tellsid.softintelligence.co.uk/assets/upload/chatimg/" + list.get(position).getMessage()) //live
//                        .placeholder(R.drawable.noimage)
//                        .into(holder.iv_msgImg_L);
            }
            holder.iv_msgImg_L.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.detailChatImageClick(list.get(position));
                }
            });
        } else {
            holder.rl_you.setVisibility(View.GONE);
            holder.rl_me.setVisibility(View.VISIBLE);
            //holder.tv_msg.setText(list.get(position).getMessage());
            holder.tv_chat_time.setText(utility.getDate(list.get(position).getCreatedAt()));

            if (list.get(position).getMsgImageUrl().equals("")) {
                holder.tv_msg.setText(list.get(position).getMessage());
                holder.tv_msg.setVisibility(View.VISIBLE);
                holder.iv_msgImg.setVisibility(View.GONE);
            } else {
                holder.tv_msg.setVisibility(View.GONE);
                holder.iv_msgImg.setVisibility(View.VISIBLE);
                //Picasso.get().load("http://live.thechangeconsultancy.co/demo_tellsid/assets/upload/chatimg/" + //test

                //demo----------------------------------------------------
                Picasso.get().load(list.get(position).getMsgImageUrl())
                        .placeholder(R.drawable.noimage_big)
                        .into(holder.iv_msgImg);
                //live----------------------------------------------
//                Picasso.get().load("http://tellsid.softintelligence.co.uk/assets/upload/chatimg/" + //live
//                        list.get(position).getMessage())
//                        .placeholder(R.drawable.noimage)
//                        .into(holder.iv_msgImg);
            }
            holder.iv_msgImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mListener.detailChatImageClick(list.get(position));
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


    public interface SupportDetailImageViewListener {
        public void detailChatImageClick(SupportMessage image);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_chat_time_L, tv_msg_L, tv_msgstatus_L;
        TextView tv_chat_time, tv_msg, tv_msgstatus;
        RelativeLayout rl_me, rl_you;
        ImageView frag_profile_im_L, iv_msgImg_L;
        ImageView frag_profile_im, iv_msgImg;

        public ViewHolder(View v) {
            super(v);
            rl_me = v.findViewById(R.id.rl_me);
            rl_you = v.findViewById(R.id.rl_you);
            tv_chat_time_L = v.findViewById(R.id.tv_chat_time_L);
            frag_profile_im_L = v.findViewById(R.id.frag_profile_im_L);
            tv_msg_L = v.findViewById(R.id.tv_msg_L);
            // tv_msgstatus_L = v.findViewById(R.id.tv_msgstatus_L);
            iv_msgImg_L = v.findViewById(R.id.iv_msgImg_L);

            frag_profile_im = v.findViewById(R.id.frag_profile_im);
            tv_chat_time = v.findViewById(R.id.tv_chat_time);
            tv_msg = v.findViewById(R.id.tv_msg);
            // tv_msgstatus = v.findViewById(R.id.tv_msgstatus);
            iv_msgImg = v.findViewById(R.id.iv_msgImg);
        }
    }
}
