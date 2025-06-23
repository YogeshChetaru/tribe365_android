package com.chetaru.tribe365_new.Adapter.KnowCompany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.KnowCompany.ModelCultureIndex;
import com.chetaru.tribe365_new.databinding.RowSotMotivationGraphListBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ad_EngagementIndex extends RecyclerView.Adapter<Ad_EngagementIndex.ViewHolder> {
    List<ModelCultureIndex> list = new ArrayList<>();
    List<Float> li_score_sort = new ArrayList<>();

    private Context context;

    public Ad_EngagementIndex(List<ModelCultureIndex> list, List<Float> li_score_sort, Context context) {
        this.list = list;
        //this.li_score_sort=li_score_sort;

        this.context = context;


        li_score_sort.clear();
        for (int j = 0; j < list.size(); j++) {
            li_score_sort.add(Float.valueOf(list.get(j).getIndexCount()));
        }
        Object[] st = li_score_sort.toArray();
        for (Object s : st) {
            if (li_score_sort.indexOf(s) != li_score_sort.lastIndexOf(s)) {
                li_score_sort.remove(li_score_sort.lastIndexOf(s));
            }
        }
        Collections.sort(li_score_sort, new Comparator<Float>() {
            @Override
            public int compare(Float lhs, Float rhs) {
                return lhs.compareTo(rhs);
            }
        });

        this.li_score_sort = li_score_sort;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        RowSotMotivationGraphListBinding binding= RowSotMotivationGraphListBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sot_motivation_graph_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemBinding.tvGraphNumber.setText((position + 1) + " - ");
        holder.itemBinding.tvMotivationGraphName.setText(list.get(position).getTitle());
        holder.itemBinding.tvMotivationGraphScore.setText(list.get(position).getIndexCount());
        float setalpha = (float) 0.0;

        for (int i = 0; i < li_score_sort.size(); i++) {
            setalpha = (float) (setalpha + 0.3);
            if (li_score_sort.get(i).toString().equals((Float.valueOf(list.get(position).getIndexCount())).toString())) {
                holder.itemBinding.tvBgMotivationGraphScore.setAlpha(setalpha);
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
        RowSotMotivationGraphListBinding itemBinding;
        public ViewHolder(RowSotMotivationGraphListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*tv_graph_number = v.findViewById(R.id.tv_graph_number);
            tv_motivation_graph_name = v.findViewById(R.id.tv_motivation_graph_name);
            tv_motivation_graph_score = v.findViewById(R.id.tv_motivation_graph_score);
            tv_bg_motivation_graph_score = v.findViewById(R.id.tv_bg_motivation_graph_score);
            v_line = v.findViewById(R.id.v_line);*/
        }
    }
}
