package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.Option;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.RowTribeometerOptionBinding;

import java.util.List;

/* created by ritesh 11 non 2020 call for Tribometer question Options*/

public class Option_Adapter extends RecyclerView.Adapter<Option_Adapter.ViewHolder> {
    public Context mContext;
    public List<Option> optionList;
    optionClickListener mListener;
    int AnswerOpt;

    public Option_Adapter(Context context, List<Option> options, int Answer, optionClickListener listener) {
        this.mContext = context;
        this.optionList = options;
        this.mListener = listener;
        this.AnswerOpt = Answer;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        RowTribeometerOptionBinding binding=RowTribeometerOptionBinding.inflate(inflater,parent,false);
       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tribeometer_option, parent, false);
        return new Option_Adapter.ViewHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemBinding.tvTribeometerOpt.setText(optionList.get(position).getOptionName());


        if (optionList.get(position).isAnswerFlag()) {
            holder.itemBinding.tvTribeometerOpt.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_orange));
            holder.itemBinding.mainBlockLl.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_orange));
            holder.itemBinding.tvTribeometerOpt.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.itemBinding.tvTribeometerOpt.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_normal));
            holder.itemBinding.mainBlockLl.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_normal));
            holder.itemBinding.tvTribeometerOpt.setTextColor(mContext.getResources().getColor(R.color.textcolor));
        }

        holder.itemBinding.tvTribeometerOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //optionList.get(position).setAnswerFlag(true);

                mListener.optionClick(position);

                optionList.get(position).setAnswerFlag(true);
                for (int i = 0; i < optionList.size(); i++) {
                    if (i == position) {
                        holder.itemBinding.tvTribeometerOpt.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_orange));
                        holder.itemBinding.mainBlockLl.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_orange));
                        holder.itemBinding.tvTribeometerOpt.setTextColor(mContext.getResources().getColor(R.color.white));
                    } else {
                        optionList.get(i).setAnswerFlag(false);
                        holder.itemBinding.tvTribeometerOpt.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_normal));
                        holder.itemBinding.mainBlockLl.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_normal));
                        holder.itemBinding.tvTribeometerOpt.setTextColor(mContext.getResources().getColor(R.color.textcolor));

                    }
                }
                notifyDataSetChanged();
            }
        });
       /* if (AnswerOpt==(position)){
            mListener.optionClick(AnswerOpt+1);
            holder.optionTextView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_orange));
            holder.optionTextView.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.optionTextView.setTypeface(null,Typeface.BOLD);
        }else {
            holder.optionTextView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_normal));
            holder.optionTextView.setTextColor(mContext.getResources().getColor(R.color.textcolor));
            holder.optionTextView.setTypeface(null,Typeface.NORMAL);
        }*/

    }

    @Override
    public int getItemCount() {
        try {
            return optionList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public interface optionClickListener {
        void optionClick(int name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView optionTextView;
        LinearLayout main_block_ll;
        RowTribeometerOptionBinding itemBinding;
        public ViewHolder(RowTribeometerOptionBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*optionTextView = view.findViewById(R.id.tv_tribeometer_opt);
            main_block_ll= view.findViewById(R.id.main_block_ll);*/
        }
    }
}
