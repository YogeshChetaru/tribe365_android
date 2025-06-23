package com.chetaru.tribe365_new.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelAddActionUser;
import com.chetaru.tribe365_new.databinding.RowTierListBinding;

import java.util.ArrayList;
import java.util.List;

public class Ad_UserListDialog extends RecyclerView.Adapter<Ad_UserListDialog.ViewHolder> {

    List<ModelAddActionUser> list = new ArrayList<>();
    private Context context;


    public Ad_UserListDialog(List<ModelAddActionUser> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        RowTierListBinding binding=RowTierListBinding.inflate(inflater);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tier_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.itemBinding.tvTier.setText(list.get(position).getName() + " " + list.get(position).getLastName().toString().trim());
        }catch (Exception e){
            e.printStackTrace();
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
