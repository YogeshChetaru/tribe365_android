package com.chetaru.tribe365_new.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelTheme;
import com.chetaru.tribe365_new.databinding.RowTierListBinding;

import java.util.ArrayList;
import java.util.List;

public class Ad_Theme extends RecyclerView.Adapter<Ad_Theme.ViewHolder> {

    List<ModelTheme> list = new ArrayList<>();
    Context context;

    public Ad_Theme(List<ModelTheme> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
    }

    @NonNull
    @Override
    public Ad_Theme.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        RowTierListBinding binding= RowTierListBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tier_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Ad_Theme.ViewHolder holder, int position) {
        holder.itemBinding.tvTier.setText(list.get(position).getTitle());

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tier;
        RowTierListBinding itemBinding;
        public ViewHolder(RowTierListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            //tv_tier = itemView.findViewById(R.id.tv_tier);
        }
    }
}
