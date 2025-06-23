package com.chetaru.tribe365_new.Adapter.SOTAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelMotivationGraph;
import com.chetaru.tribe365_new.databinding.RowSotMotivationGraphListBinding;

import java.util.ArrayList;
import java.util.List;

public class Adapter_SOT_Motivation_graph_list extends RecyclerView.Adapter<Adapter_SOT_Motivation_graph_list.ViewHolder> {


    List<ModelMotivationGraph> list = new ArrayList<>();
    List<Float> li_score_sort = new ArrayList<>();
    private Context context;


    public Adapter_SOT_Motivation_graph_list(List<ModelMotivationGraph> list, List<Float> li_score_sort, Context context) {
        this.list = list;
        this.li_score_sort = li_score_sort;
        notifyDataSetChanged();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        RowSotMotivationGraphListBinding binding=RowSotMotivationGraphListBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sot_motivation_graph_list, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemBinding.tvGraphNumber.setText((position + 1) + " - ");
        holder.itemBinding.tvMotivationGraphName.setText(list.get(position).getTitle());
        holder.itemBinding.tvBgMotivationGraphScore.setText((Float.valueOf(list.get(position).getScore())).toString());

        float setalpha = (float) 0.0;
        for (int i = 0; i < li_score_sort.size(); i++) {
            setalpha = (float) (setalpha + 0.3);

            if (li_score_sort.get(i).toString().equals(Float.valueOf(list.get(position).getScore()).toString())) {
               // holder.itemBinding.tvBgMotivationGraphScore.setAlpha(setalpha);
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

        RowSotMotivationGraphListBinding itemBinding;
        public ViewHolder(RowSotMotivationGraphListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;

        }
    }

}
