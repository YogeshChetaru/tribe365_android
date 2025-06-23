package com.chetaru.tribe365_new.Adapter.SOTAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.DOT.BeliefValueArr;
import com.chetaru.tribe365_new.API.Models.DOT.Modelreport;
import com.chetaru.tribe365_new.databinding.RowReportlistBinding;

import java.util.ArrayList;

public class Ad_reportList extends RecyclerView.Adapter<Ad_reportList.ViewHolder> {

    ArrayList<Modelreport> list = new ArrayList<>();
    ArrayList<BeliefValueArr> li_beliefValueArrs = new ArrayList<>();
    private Context context;

    public Ad_reportList(ArrayList<Modelreport> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        RowReportlistBinding binding=RowReportlistBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reportlist, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemBinding.tvReportTitle.setText(list.get(position).getName());
        Ad_Report_SubList ad_report_subList = new Ad_Report_SubList(list.get(position).getBeliefValueArr(), context);
        holder.itemBinding.rvReportSublist.setHasFixedSize(true);
        holder.itemBinding.rvReportSublist.setAdapter(ad_report_subList);
        holder.itemBinding.rvReportSublist.setLayoutManager(new LinearLayoutManager(context));
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
        TextView tv_report_title;
        RecyclerView rv_report_sublist;
        RowReportlistBinding itemBinding;
        public ViewHolder(RowReportlistBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*tv_report_title = v.findViewById(R.id.tv_report_title);
            rv_report_sublist = v.findViewById(R.id.rv_report_sublist);*/
        }
    }
}
