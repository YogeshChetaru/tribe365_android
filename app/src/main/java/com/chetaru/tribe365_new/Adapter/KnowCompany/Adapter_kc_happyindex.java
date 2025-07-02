package com.chetaru.tribe365_new.Adapter.KnowCompany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelHappyIndex;
import com.chetaru.tribe365_new.databinding.RowSotMotivationGraphListBinding;

import java.util.ArrayList;
import java.util.List;

public class Adapter_kc_happyindex extends RecyclerView.Adapter<Adapter_kc_happyindex.ViewHolder> {


    List<ModelHappyIndex> list = new ArrayList<>();
    List<Float> li_score_sort = new ArrayList<>();
    String type = "";
    private Context context;


    public Adapter_kc_happyindex(List<ModelHappyIndex> list, List<Float> li_score_sort, Context context, String type) {
        this.list = list;
        this.type = type;
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

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.itemBinding.tvGraphNumber.setText((position + 1) + " - ");

      /*  if(type.equals("year")){
            holder.tv_motivation_graph_name.setText(list.get(position).getYear()+"");
        }*/
        if (type.equals("month")) {
            holder.itemBinding.tvMotivationGraphName.setText(list.get(position).getmMonthName() + "");
        } else if (type.equals("week")) {
            holder.itemBinding.tvMotivationGraphName.setText(list.get(position).getWeek() + "");
        } else if (type.equals("day")) {
            holder.itemBinding.tvMotivationGraphName.setText(list.get(position).getmDayName() + "");
        }

        holder.itemBinding.tvBgMotivationGraphScore.setText(list.get(position).getHappy() + "" + "%");

        float setalpha = (float) 0.1;
        for (int i = 0; i < li_score_sort.size(); i++) {
            setalpha = (float) (setalpha + 0.3);

           /* if (li_score_sort.get(i).toString().equals((Float.valueOf(list.get(position).getHappy() + "")).toString())) {
                holder.itemBinding.tvBgMotivationGraphScore.setAlpha(setalpha);
            }*/

        }
        }catch (Exception e){
            e.printStackTrace();
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
