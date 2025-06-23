package com.chetaru.tribe365_new.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelDepartmentList;
import com.chetaru.tribe365_new.databinding.RowTierListBinding;

import java.util.ArrayList;
import java.util.List;

public class Ad_DepartmentList_profile extends RecyclerView.Adapter<Ad_DepartmentList_profile.ViewHolder> {

    List<ModelDepartmentList> list = new ArrayList<>();
    private Context context;

    /*constructor to get data from activity*/
    public Ad_DepartmentList_profile(List<ModelDepartmentList> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        RowTierListBinding binding= RowTierListBinding.inflate(inflater,parent,false);
       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tier_list, parent, false);
        return new ViewHolder(binding);
    }

    /*setting data*/
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemBinding.tvTier.setText(list.get(position).getDepartment());
        //holder.tv_tier.setText(list.get(position).getDepartment());
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

    /*initializing views*/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tier;
        RowTierListBinding itemBinding;
        public ViewHolder(RowTierListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            //tv_tier = v.findViewById(R.id.tv_tier);

        }
    }
}
