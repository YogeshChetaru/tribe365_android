package com.chetaru.tribe365_new.Adapter.IOT_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelMessageList;
import com.chetaru.tribe365_new.databinding.RowMsgListBinding;

import java.util.ArrayList;
import java.util.List;

public class Ad_MsgList extends RecyclerView.Adapter<Ad_MsgList.ViewHolder> {

    List<ModelMessageList> list = new ArrayList<>();
    private Context context;

    public Ad_MsgList(List<ModelMessageList> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        RowMsgListBinding binding= RowMsgListBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemBinding.tvHeading.setText(list.get(position).getmFeedback_msg());
        holder.itemBinding.tvDate.setText(list.get(position).getmDate());
        if (!list.get(position).getmImage()) {
            holder.itemBinding.rlTxt.setVisibility(View.VISIBLE);
            holder.itemBinding.rlImg.setVisibility(View.GONE);
            holder.itemBinding.tvContent.setText(list.get(position).getmMessage());
        } else {
            holder.itemBinding.rlTxt.setVisibility(View.GONE);
            holder.itemBinding.rlImg.setVisibility(View.VISIBLE);
        }
//        if(list.get(position).getLastmsg().get(0).getPostType().equals("msg")){
//             holder.rl_txt.setVisibility(View.VISIBLE);
//             holder.rl_img.setVisibility(View.GONE);
//            holder.tv_content.setText(list.get(position).getLastmsg().get(0).getMessage());
//
//        }else{
//            holder.rl_txt.setVisibility(View.GONE);
//            holder.rl_img.setVisibility(View.VISIBLE);
//        }

        // Picasso.get().load("http://live.thechangeconsultancy.co/demo_tellsid/assets/upload/tellsid/"+list.get(position).getImages()).placeholder(R.drawable.noimage).into(holder.iv_image);
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
        TextView tv_heading, tv_date, tv_content;
        RelativeLayout rl_img, rl_txt;
        RowMsgListBinding itemBinding;
        public ViewHolder(RowMsgListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* tv_heading = v.findViewById(R.id.tv_heading);
            tv_date = v.findViewById(R.id.tv_date);
            tv_content = v.findViewById(R.id.tv_content);
            rl_img = v.findViewById(R.id.rl_img);
            rl_txt = v.findViewById(R.id.rl_txt);*/
        }
    }
}
