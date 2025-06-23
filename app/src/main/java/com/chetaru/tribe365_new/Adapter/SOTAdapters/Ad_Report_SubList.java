package com.chetaru.tribe365_new.Adapter.SOTAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.DOT.BeliefValueArr;
import com.chetaru.tribe365_new.databinding.RowSubreportBinding;

import java.util.ArrayList;
import java.util.List;

public class Ad_Report_SubList extends RecyclerView.Adapter<Ad_Report_SubList.ViewHolder> {

    ArrayList<BeliefValueArr> list = new ArrayList<>();
    private Context context;


    public Ad_Report_SubList(List<BeliefValueArr> list, Context context) {
        this.list = (ArrayList<BeliefValueArr>) list;
        notifyDataSetChanged();
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        RowSubreportBinding binding=RowSubreportBinding.inflate(inflater,parent,false);
       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_subreport, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemBinding.tvReportName.setText(list.get(position).getName());
        holder.itemBinding.tvLikeScore.setText(list.get(position).getUpVotes().toString());
        // holder.tv_unlike_score.setText(list.get(position).getDownVotes().toString());
        holder.itemBinding.tvSubrating.setText(list.get(position).getRatings());
        if (list.get(position).getRatings().equals("")) {
            holder.itemBinding.llRating.setVisibility(View.GONE);
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
        TextView tv_report_name, tv_like_score, tv_unlike_score, tv_subrating;
        LinearLayout ll_rating;
        RowSubreportBinding itemBinding;
        public ViewHolder(RowSubreportBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* tv_report_name = v.findViewById(R.id.tv_report_name);
            tv_like_score = v.findViewById(R.id.tv_like_score);
            tv_unlike_score = v.findViewById(R.id.tv_unlike_score);
            tv_subrating = v.findViewById(R.id.tv_subrating);
            ll_rating = v.findViewById(R.id.ll_rating);*/
        }
    }
}
