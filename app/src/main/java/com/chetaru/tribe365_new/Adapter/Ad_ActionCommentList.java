package com.chetaru.tribe365_new.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelActionCommentList;
import com.chetaru.tribe365_new.databinding.RowCommentBinding;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.ArrayList;

public class Ad_ActionCommentList extends RecyclerView.Adapter<Ad_ActionCommentList.ViewHolder> {

    ArrayList<ModelActionCommentList> list = new ArrayList<>();
    Utility utility;
    private final Context mContext;


    public Ad_ActionCommentList(ArrayList<ModelActionCommentList> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.mContext = context;
        utility = new Utility();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        RowCommentBinding binding= RowCommentBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemBinding.tvName.setText(list.get(position).getName());
        holder.itemBinding.tvDescription.setText(list.get(position).getComment());
        holder.itemBinding.tvDate.setText(utility.getDate(list.get(position).getCreatedAt()));
        if (position ==(list.size()-1)){
            holder.itemBinding.bottomView.setVisibility(View.GONE);
        }else{
            holder.itemBinding.bottomView.setVisibility(View.VISIBLE);
        }
        /*holder.tv_name.setText(list.get(position).getName());
        holder.tv_description.setText(list.get(position).getComment());
        holder.tv_date.setText(utility.getDate(list.get(position).getCreatedAt()));
        if (position== (list.size()-1)){
            holder.bottom_view.setVisibility(View.GONE);
        }else {
            holder.bottom_view.setVisibility(View.VISIBLE);
        }*/
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
        //TextView tv_name, tv_description, tv_date;
        //View bottom_view;
        RowCommentBinding itemBinding;
        public ViewHolder(RowCommentBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*tv_name = v.findViewById(R.id.tv_name);
            tv_description = v.findViewById(R.id.tv_description);
            tv_date = v.findViewById(R.id.tv_date);
            bottom_view= v.findViewById(R.id.bottom_view);*/
        }
    }
}
