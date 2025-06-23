package com.chetaru.tribe365_new.Adapter.COTAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.COTBeans.UpdateBean.UpdateFunctionBean.ModelFuctionLens;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.utility.SessionParam;

import java.util.ArrayList;
import java.util.List;

public class AD_update_Fun_Question extends RecyclerView.Adapter<AD_update_Fun_Question.ViewHolder> {

    List<ModelFuctionLens> list = new ArrayList<>();
    SessionParam sessionParam;
    private Context context;

    public AD_update_Fun_Question(List<ModelFuctionLens> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
        sessionParam = new SessionParam(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_functional_lens_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tv_tv_fun_lens_qus.setText(list.get(position).getQuestion());
        holder.tv_qus_no.setText(list.get(position).getQuestionId().toString());

        holder.chk_sot_ques1.setChecked(list.get(position).getOptions().get(0).getIsChecked());
        holder.chk_sot_ques2.setChecked(list.get(position).getOptions().get(1).getIsChecked());

        for (int i = 0; i < list.get(position).getOptions().size(); i++) {
            holder.tv_sot_ques1.setText(list.get(position).getOptions().get(0).getOptionName());

            holder.tv_sot_ques2.setText(list.get(position).getOptions().get(1).getOptionName());
            if (list.get(position).getOptions().get(0).getIsChecked() == true) {
                holder.chk_sot_ques1.setChecked(true);
                holder.chk_sot_ques2.setChecked(false);
                list.get(position).setOptionId(list.get(position).getOptions().get(0).getOptionId().toString());

            }
            if (list.get(position).getOptions().get(1).getIsChecked() == true) {
                holder.chk_sot_ques1.setChecked(false);
                holder.chk_sot_ques2.setChecked(true);
                list.get(position).setOptionId(list.get(position).getOptions().get(1).getOptionId().toString());

            }
        }
        holder.chk_sot_ques1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.chk_sot_ques1.isChecked()) {
                    holder.chk_sot_ques1.setChecked(true);
                    holder.chk_sot_ques1.setClickable(false);
                    holder.chk_sot_ques2.setClickable(true);
                }
                if (isChecked == true) {
                    list.get(position).getOptions().get(0).getIsChecked();
                    list.get(position).getOptions().get(1).getIsChecked();
                    holder.chk_sot_ques1.setChecked(true);
                    holder.chk_sot_ques2.setChecked(false);
                    list.get(position).setOptionId(list.get(position).getOptions().get(0).getOptionId().toString());
                    list.get(position).getOptions().get(0).setIsChecked(true);
                    list.get(position).getOptions().get(1).setIsChecked(false);
                    list.get(position).getOptions().get(0).getIsChecked();
                    notifyDataSetChanged();

                } else {
                    holder.chk_sot_ques1.setChecked(false);
                    list.get(position).getOptions().get(0).getIsChecked();
                    notifyDataSetChanged();
                }

            }
        });

        holder.chk_sot_ques2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.chk_sot_ques2.isChecked()) {
                    holder.chk_sot_ques2.setChecked(true);
                    holder.chk_sot_ques2.setClickable(false);
                    holder.chk_sot_ques1.setClickable(true);
                }
                if (isChecked == true) {
                    list.get(position).getOptions().get(0).getIsChecked();
                    list.get(position).getOptions().get(1).getIsChecked();
                    holder.chk_sot_ques2.setChecked(true);
                    holder.chk_sot_ques1.setChecked(false);
                    list.get(position).getOptions().get(1).getIsChecked();
                    list.get(position).setOptionId(list.get(position).getOptions().get(1).getOptionId().toString());
                    list.get(position).getOptions().get(0).setIsChecked(false);
                    list.get(position).getOptions().get(1).setIsChecked(true);
                    notifyDataSetChanged();
                } else {
                    list.get(position).getOptions().get(1).getIsChecked();
                    holder.chk_sot_ques2.setChecked(false);
                    notifyDataSetChanged();
                }

            }
        });


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
        TextView tv_tv_fun_lens_qus, tv_qus_no;
        TextView tv_sot_ques1, tv_sot_ques2;
        CheckBox chk_sot_ques1, chk_sot_ques2;


        public ViewHolder(View v) {
            super(v);
            tv_tv_fun_lens_qus = v.findViewById(R.id.tv_tv_fun_lens_qus);
            tv_sot_ques1 = v.findViewById(R.id.tv_sot_ques1);
            tv_sot_ques2 = v.findViewById(R.id.tv_sot_ques2);
            chk_sot_ques1 = v.findViewById(R.id.chk_sot_ques1);
            chk_sot_ques2 = v.findViewById(R.id.chk_sot_ques2);
            tv_qus_no = v.findViewById(R.id.tv_qus_no);
            this.setIsRecyclable(false);
        }
    }


}
