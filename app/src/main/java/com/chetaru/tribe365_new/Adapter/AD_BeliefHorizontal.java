package com.chetaru.tribe365_new.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.BeliefValue;
import com.chetaru.tribe365_new.API.Models.Model_AddBeliefs;
import com.chetaru.tribe365_new.databinding.RowBeliefNameBinding;
import com.chetaru.tribe365_new.utility.SessionParam;

import java.util.ArrayList;
import java.util.List;

public class AD_BeliefHorizontal extends RecyclerView.Adapter<AD_BeliefHorizontal.ViewHolder> {

    List<Model_AddBeliefs> beliefsList = new ArrayList<>();
    ArrayList<String> valueList;
    SessionParam sessionParam;
    ArrayList<Integer> li_size = new ArrayList<>();
    String EditDot = "";
    AD_Belief_Vertical ad_belief_vertical;
    ScrollView sv_view;
    ClickBeliefListener mListener;
    private Context context;

    public AD_BeliefHorizontal(List<Model_AddBeliefs> list, Context context) {
        this.beliefsList = list;
        notifyDataSetChanged();
        this.context = context;
        sessionParam = new SessionParam(context);
    }

    public AD_BeliefHorizontal(List<Model_AddBeliefs> list, Context context, String EditDot, ClickBeliefListener mListener) {
        this.beliefsList = list;
        notifyDataSetChanged();
        this.context = context;
        sessionParam = new SessionParam(context);
        this.EditDot = EditDot;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        RowBeliefNameBinding binding=RowBeliefNameBinding.inflate(inflater,parent,false);
       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_belief_name, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 15, 10);
        holder.itemBinding.tvBelief.setText(beliefsList.get(position).getBeliefName());
        //holder.tv_belief.setText(beliefsList.get(position).getBeliefName());
        if (beliefsList.get(position).getIsSelected().equals("t")) {
            holder.itemBinding.llIndicator.setVisibility(View.GONE);
        } else {
            holder.itemBinding.llIndicator.setVisibility(View.GONE);
        }
        holder.itemBinding.rowClickLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.beliefListener(beliefsList.get(position));
            }
        });
        ad_belief_vertical = new AD_Belief_Vertical(beliefsList.get(position).getValueList(), context, EditDot, new AD_Belief_Vertical.BeliefVerticalListener() {
            @Override
            public void VerticalListener(BeliefValue beliefValue) {
                mListener.beliefValueListener(beliefValue);
            }
        });
        holder.itemBinding.rvBeliefList.setHasFixedSize(true);
        holder.itemBinding.rvBeliefList.setAdapter(ad_belief_vertical);
        holder.itemBinding.rvBeliefList.setLayoutManager(new LinearLayoutManager(context));
//        holder.rv_belief_list.scrollToPosition(0);

    }

    @Override
    public int getItemCount() {
        try {
            return beliefsList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public interface ClickBeliefListener {
        public void beliefListener(Model_AddBeliefs modelAddBeliefs);

        public void beliefValueListener(BeliefValue beliefValue);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_belief;
        LinearLayout ll_indicator, lyt_brief;
        RecyclerView rv_belief_list;
        LinearLayout row_click_ll;
        RowBeliefNameBinding itemBinding;
        public ViewHolder(RowBeliefNameBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*tv_belief = v.findViewById(R.id.tv_belief);
            ll_indicator = v.findViewById(R.id.ll_indicator);
            rv_belief_list = v.findViewById(R.id.rv_belief_list);
            lyt_brief = v.findViewById(R.id.lyt_brief);
            row_click_ll = v.findViewById(R.id.row_click_ll);*/
        }
    }

}
