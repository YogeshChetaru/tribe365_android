package com.chetaru.tribe365_new.Adapter.SOTAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelSOTQusList;
import com.chetaru.tribe365_new.API.Models.SOTBeans.ModelSotans;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.ArrayList;


public class Adap_SOT_Update_Questionlist extends RecyclerView.Adapter<Adap_SOT_Update_Questionlist.ViewHolder> {

    ArrayList<ModelSOTQusList> list;
    Utility utility;
    ModelSotans modelSotans;
    int count = 0;
    private Context context;

    public Adap_SOT_Update_Questionlist(ArrayList<ModelSOTQusList> list_modelSOTQusListQuestions, Context mContext) {
        this.list = list_modelSOTQusListQuestions;
        utility = new Utility();
        this.context = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sot_row_update_questionlist, parent, false);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //if true, your checkbox will be selected, else unselected
       /* holder.chk_sot_ques1.setChecked(list.get(position).getmSotQusDetails().get(0).isCheck());
        holder.chk_sot_ques2.setChecked(list.get(position).getmSotQusDetails().get(1).isCheck());
        holder.chk_sot_ques3.setChecked(list.get(position).getmSotQusDetails().get(2).isCheck());
        holder.chk_sot_ques4.setChecked(list.get(position).getmSotQusDetails().get(3).isCheck());*/

        // int qusNo = Integer.parseInt(list.get(position).getmQusNo()) + 1;
        holder.tv_sot_qus_no.setText(list.get(position).getmQusNo());
        for (int i = 0; i < list.get(position).getmSotQusDetails().size(); i++) {
            holder.tv_sot_ques1.setText(list.get(position).getmSotQusDetails().get(0).getQuestion());
            holder.tv_sot_ques2.setText(list.get(position).getmSotQusDetails().get(1).getQuestion());
            holder.tv_sot_ques3.setText(list.get(position).getmSotQusDetails().get(2).getQuestion());
            holder.tv_sot_ques4.setText(list.get(position).getmSotQusDetails().get(3).getQuestion());
            if (list.get(position).getmSotQusDetails().get(0).isCheck() == true)
                holder.chk_sot_ques1.setChecked(true);
            if (holder.chk_sot_ques1.isChecked()) {
                holder.chk_sot_ques1.setChecked(true);
                holder.chk_sot_ques1.setClickable(false);
                holder.chk_sot_ques3.setClickable(true);
                holder.chk_sot_ques4.setClickable(true);
                holder.chk_sot_ques2.setClickable(true);
            }
//            holder.chk_sot_ques1.setClickable(false);
            if (list.get(position).getmSotQusDetails().get(1).isCheck() == true)
                holder.chk_sot_ques2.setChecked(true);

            if (holder.chk_sot_ques2.isChecked()) {
                holder.chk_sot_ques2.setChecked(true);

                holder.chk_sot_ques2.setClickable(false);
                holder.chk_sot_ques3.setClickable(true);
                holder.chk_sot_ques4.setClickable(true);
                holder.chk_sot_ques1.setClickable(true);
            }
//            holder.chk_sot_ques2.setClickable(false);
            if (list.get(position).getmSotQusDetails().get(2).isCheck() == true)
                holder.chk_sot_ques3.setChecked(true);

            if (holder.chk_sot_ques3.isChecked()) {
                holder.chk_sot_ques3.setChecked(true);

                holder.chk_sot_ques3.setClickable(false);
                holder.chk_sot_ques2.setClickable(true);
                holder.chk_sot_ques4.setClickable(true);
                holder.chk_sot_ques1.setClickable(true);
            }
//            holder.chk_sot_ques3.setClickable(false);
            if (list.get(position).getmSotQusDetails().get(3).isCheck() == true)
                holder.chk_sot_ques4.setChecked(true);
            if (holder.chk_sot_ques4.isChecked()) {
                holder.chk_sot_ques4.setChecked(true);

                holder.chk_sot_ques4.setClickable(false);
                holder.chk_sot_ques2.setClickable(true);
                holder.chk_sot_ques3.setClickable(true);
                holder.chk_sot_ques1.setClickable(true);
            }
//            holder.chk_sot_ques4.setClickable(false);
        }

//        holder.ll_option_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (holder.chk_sot_ques1.isChecked()) {
//                    holder.chk_sot_ques1.setChecked(true);
//                } else {
//                    holder.chk_sot_ques1.setChecked(false);
//                }
//            }
//        });
        holder.chk_sot_ques1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.chk_sot_ques1.isChecked()) {
                    holder.chk_sot_ques1.setChecked(true);

                    holder.chk_sot_ques1.setClickable(false);
                    holder.chk_sot_ques3.setClickable(true);
                    holder.chk_sot_ques4.setClickable(true);
                    holder.chk_sot_ques2.setClickable(true);
                }
                if (isChecked) {

                    list.get(position).getmSotQusDetails().get(0).setCheck(true);
                    list.get(position).getmSotQusDetails().get(1).setCheck(false);
                    list.get(position).getmSotQusDetails().get(2).setCheck(false);
                    list.get(position).getmSotQusDetails().get(3).setCheck(false);
                    holder.chk_sot_ques1.setChecked(true);
                    holder.chk_sot_ques2.setChecked(false);
                    holder.chk_sot_ques3.setChecked(false);
                    holder.chk_sot_ques4.setChecked(false);
                    modelSotans = new ModelSotans(list.get(position).getmSotQusDetails().get(0).getId());
                    list.get(position).getmSotQusDetails().get(0).setCheck(true);
                } else {
                    holder.chk_sot_ques1.setChecked(false);
                    modelSotans = new ModelSotans(list.get(position).getmSotQusDetails().get(0).setId(0 + ""));
                    list.get(position).getmSotQusDetails().get(0).setCheck(false);

                }


            }
        });

        holder.chk_sot_ques2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.chk_sot_ques2.isChecked()) {
                    holder.chk_sot_ques2.setChecked(true);

                    holder.chk_sot_ques2.setClickable(false);
                    holder.chk_sot_ques3.setClickable(true);
                    holder.chk_sot_ques4.setClickable(true);
                    holder.chk_sot_ques1.setClickable(true);
                }
                if (isChecked == true) {
                    list.get(position).getmSotQusDetails().get(0).setCheck(false);
                    list.get(position).getmSotQusDetails().get(1).setCheck(true);
                    list.get(position).getmSotQusDetails().get(2).setCheck(false);
                    list.get(position).getmSotQusDetails().get(3).setCheck(false);
                    holder.chk_sot_ques2.setChecked(true);
                    holder.chk_sot_ques1.setChecked(false);
                    holder.chk_sot_ques3.setChecked(false);
                    holder.chk_sot_ques4.setChecked(false);
                    modelSotans = new ModelSotans(list.get(position).getmSotQusDetails().get(1).getId());
                    list.get(position).getmSotQusDetails().get(1).setCheck(true);

                } else {
                    holder.chk_sot_ques2.setChecked(false);
                    modelSotans = new ModelSotans(list.get(position).getmSotQusDetails().get(1).setId(0 + ""));
                    list.get(position).getmSotQusDetails().get(1).setCheck(false);
                }

            }
        });
        holder.chk_sot_ques3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.chk_sot_ques3.isChecked()) {
                    holder.chk_sot_ques3.setChecked(true);

                    holder.chk_sot_ques3.setClickable(false);
                    holder.chk_sot_ques2.setClickable(true);
                    holder.chk_sot_ques4.setClickable(true);
                    holder.chk_sot_ques1.setClickable(true);
                }
                if (isChecked == true) {
                    list.get(position).getmSotQusDetails().get(0).setCheck(false);
                    list.get(position).getmSotQusDetails().get(1).setCheck(false);
                    list.get(position).getmSotQusDetails().get(2).setCheck(true);
                    list.get(position).getmSotQusDetails().get(3).setCheck(false);
                    holder.chk_sot_ques1.setChecked(false);
                    holder.chk_sot_ques2.setChecked(false);
                    holder.chk_sot_ques3.setChecked(true);
                    holder.chk_sot_ques4.setChecked(false);
                    modelSotans = new ModelSotans(list.get(position).getmSotQusDetails().get(2).getId());
                    list.get(position).getmSotQusDetails().get(2).setCheck(true);
                } else {
                    holder.chk_sot_ques3.setChecked(false);
                    modelSotans = new ModelSotans(list.get(position).getmSotQusDetails().get(2).setId(0 + ""));
                    list.get(position).getmSotQusDetails().get(2).setCheck(false);
                }
            }
        });
        holder.chk_sot_ques4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.chk_sot_ques4.isChecked()) {
                    holder.chk_sot_ques4.setChecked(true);

                    holder.chk_sot_ques4.setClickable(false);
                    holder.chk_sot_ques2.setClickable(true);
                    holder.chk_sot_ques3.setClickable(true);
                    holder.chk_sot_ques1.setClickable(true);
                }
                if (isChecked == true) {
                    list.get(position).getmSotQusDetails().get(0).setCheck(false);
                    list.get(position).getmSotQusDetails().get(1).setCheck(false);
                    list.get(position).getmSotQusDetails().get(2).setCheck(false);
                    list.get(position).getmSotQusDetails().get(3).setCheck(true);
                    holder.chk_sot_ques1.setChecked(false);
                    holder.chk_sot_ques2.setChecked(false);
                    holder.chk_sot_ques3.setChecked(false);
                    holder.chk_sot_ques4.setChecked(true);
                    modelSotans = new ModelSotans(list.get(position).getmSotQusDetails().get(3).getId());
                    list.get(position).getmSotQusDetails().get(3).setCheck(true);
                } else {
                    holder.chk_sot_ques4.setChecked(false);
                    modelSotans = new ModelSotans(list.get(position).getmSotQusDetails().get(3).setId(0 + ""));
                    list.get(position).getmSotQusDetails().get(3).setCheck(false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sot_ques1, tv_sot_ques2, tv_sot_ques3, tv_sot_ques4, tv_sot_qus_no;
        CheckBox chk_sot_ques1, chk_sot_ques2, chk_sot_ques3, chk_sot_ques4;
        //        RadioButton ;
        LinearLayout ll_option_one;

        public ViewHolder(View v) {
            super(v);
            tv_sot_qus_no = v.findViewById(R.id.tv_sot_qus_no);
            tv_sot_ques1 = v.findViewById(R.id.tv_sot_ques1);
            tv_sot_ques2 = v.findViewById(R.id.tv_sot_ques2);
            tv_sot_ques3 = v.findViewById(R.id.tv_sot_ques3);
            tv_sot_ques4 = v.findViewById(R.id.tv_sot_ques4);
            chk_sot_ques1 = v.findViewById(R.id.chk_sot_ques1);
            chk_sot_ques2 = v.findViewById(R.id.chk_sot_ques2);
            chk_sot_ques3 = v.findViewById(R.id.chk_sot_ques3);
            chk_sot_ques4 = v.findViewById(R.id.chk_sot_ques4);
            ll_option_one = v.findViewById(R.id.ll_option_one);
            this.setIsRecyclable(false);
        }
    }

}


