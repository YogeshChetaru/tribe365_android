package com.chetaru.tribe365_new.Adapter.Diagnostics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.ModelDeagnosticsSubGraph;
import com.chetaru.tribe365_new.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Diagnostics_Sub_graph_list extends RecyclerView.Adapter<Adapter_Diagnostics_Sub_graph_list.ViewHolder> {


    List<ModelDeagnosticsSubGraph> list = new ArrayList<>();
    List<Float> li_score_sort = new ArrayList<>();
    private Context context;


    public Adapter_Diagnostics_Sub_graph_list(List<ModelDeagnosticsSubGraph> list, List<Float> li_score_sort, Context context) {
        this.list = list;
        this.li_score_sort = li_score_sort;
        notifyDataSetChanged();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sot_motivation_graph_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_graph_number.setText((position + 1) + " - ");
        holder.tv_motivation_graph_name.setText(list.get(position).getTitle());
        holder.tv_motivation_graph_score.setText(list.get(position).getPercentage() + "%");

        float setalpha = (float) 0.0;
        for (int i = 0; i < li_score_sort.size(); i++) {
            setalpha = (float) (setalpha + 0.1);

            if (li_score_sort.get(i).toString().equals((Float.valueOf(list.get(position).getPercentage())).toString())) {
                holder.tv_bg_motivation_graph_score.setAlpha(setalpha);
            }

        }
//       if (li_score_sort.size()==position)
//       {
//
//           holder.v_line.setVisibility(View.GONE);
//       }
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
        TextView tv_graph_number, tv_motivation_graph_name, tv_motivation_graph_score, tv_bg_motivation_graph_score;
        View v_line;

        public ViewHolder(View v) {
            super(v);
            tv_graph_number = v.findViewById(R.id.tv_graph_number);
            tv_motivation_graph_name = v.findViewById(R.id.tv_motivation_graph_name);
            tv_motivation_graph_score = v.findViewById(R.id.tv_motivation_graph_score);
            tv_bg_motivation_graph_score = v.findViewById(R.id.tv_bg_motivation_graph_score);
            v_line = v.findViewById(R.id.v_line);
        }
    }

}
