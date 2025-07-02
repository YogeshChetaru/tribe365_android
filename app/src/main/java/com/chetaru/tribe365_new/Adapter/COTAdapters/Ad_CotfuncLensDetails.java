package com.chetaru.tribe365_new.Adapter.COTAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.COTBeans.ModelForRecyclerView;
import com.chetaru.tribe365_new.databinding.RowCotFunclensDetailsBinding;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class Ad_CotfuncLensDetails extends RecyclerView.Adapter<Ad_CotfuncLensDetails.ViewHolder> {

    List<ModelForRecyclerView> list = new ArrayList<>();
    Utility utility;
    private Context context;


    public Ad_CotfuncLensDetails(List<ModelForRecyclerView> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
        utility = new Utility();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        RowCotFunclensDetailsBinding binding=RowCotFunclensDetailsBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cot_funclens_details, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //holder.tv_heading.setText(list.get(position).getHeading());

        if (list.get(position).getType().equalsIgnoreCase("funclens")) {
            holder.itemBinding.tvPositives.setVisibility(View.GONE);
            holder.itemBinding.tvAllowableWeeknes.setVisibility(View.GONE);
            holder.itemBinding.tvDesc.setVisibility(View.VISIBLE);
            holder.itemBinding.tvHeading.setText(list.get(position).getHeading());
            holder.itemBinding.tvDesc.setText(list.get(position).getDescription());

        }
        if (list.get(position).getType().equalsIgnoreCase("initialValue")) {
            holder.itemBinding.tvPositives.setVisibility(View.VISIBLE);
            holder.itemBinding.tvAllowableWeeknes.setVisibility(View.VISIBLE);
            holder.itemBinding.tvDesc.setVisibility(View.GONE);

            holder.itemBinding.tvHeading.setText(list.get(position).getHeading());
            holder.itemBinding.tvPositives.setText("Positives: " + list.get(position).getPositives());
            holder.itemBinding.tvAllowableWeeknes.setText("Allowable weakness :" + list.get(position).getAllowableWeakness());
        }
        if (list.get(position).getType().equalsIgnoreCase("seek")) {
            holder.itemBinding.tvPositives.setVisibility(View.GONE);
            holder.itemBinding.tvAllowableWeeknes.setVisibility(View.GONE);
            holder.itemBinding.tvDesc.setVisibility(View.VISIBLE);

            holder.itemBinding.tvHeading.setText(list.get(position).getHeading());
            String[] splitStringarray = list.get(position).getDescription().split(",");
            String splitedString = "";
            for (int i = 0; i < splitStringarray.length; i++) {
                if (i == 0) {
                    splitedString = splitStringarray[i] + System.getProperty("line.separator");
                } else {
                    splitedString = splitedString + splitStringarray[i] + System.getProperty("line.separator");
                }

            }
            holder.itemBinding.tvDesc.setText(splitedString);
        }
        if (list.get(position).getType().equalsIgnoreCase("Persuade")) {
            holder.itemBinding.tvPositives.setVisibility(View.GONE);
            holder.itemBinding.tvAllowableWeeknes.setVisibility(View.GONE);
            holder.itemBinding.tvDesc.setVisibility(View.VISIBLE);
            holder.itemBinding.tvHeading.setText(list.get(position).getHeading());
            String[] splitStringarray = list.get(position).getDescription().split(",");
            String splitedString = "";
            for (int i = 0; i < splitStringarray.length; i++) {
                if (i == 0) {
                    splitedString = splitStringarray[i] + System.getProperty("line.separator");
                } else {
                    splitedString = splitedString + splitStringarray[i] + System.getProperty("line.separator");
                }

            }
            holder.itemBinding.tvDesc.setText(splitedString);
        }
        if (list.get(position).getType().equalsIgnoreCase("summery")) {
            holder.itemBinding.tvPositives.setVisibility(View.GONE);
            holder.itemBinding.tvAllowableWeeknes.setVisibility(View.GONE);
            holder.itemBinding.tvDesc.setVisibility(View.GONE);
            holder.itemBinding.tvHeading.setText("Summery: \n" + list.get(position).getHeading());
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
        TextView tv_heading, tv_desc, tv_positives, tv_allowable_weeknes;
        RowCotFunclensDetailsBinding itemBinding;
        public ViewHolder(RowCotFunclensDetailsBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
          /*  tv_heading = v.findViewById(R.id.tv_heading);
            tv_desc = v.findViewById(R.id.tv_desc);
            tv_positives = v.findViewById(R.id.tv_positives);
            tv_allowable_weeknes = v.findViewById(R.id.tv_allowable_weeknes);*/
        }
    }
}
