package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.DiagnosticsBeans.Update.Option;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.RowTribeometerOptionBinding;

import java.util.List;

/* tribometer update question option*/

public class Update_opt_Adapter extends RecyclerView.Adapter<Update_opt_Adapter.ViewHolder> {
    public Context mContext;
    public List<Option> optionList;
    updateOptionListener mListener;


    public Update_opt_Adapter(Context mContext, List<Option> optionList,  updateOptionListener mListener) {
        this.mContext = mContext;
        this.optionList = optionList;
        this.mListener = mListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        RowTribeometerOptionBinding binding=RowTribeometerOptionBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tribeometer_option, parent, false);
        return new Update_opt_Adapter.ViewHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemBinding.tvTribeometerOpt.setText(optionList.get(position).getOptionName());
        holder.itemBinding.tvTribeometerOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.updateOptionClick(position + 1);
                notifyDataSetChanged();
                for (int i = 0; i < optionList.size(); i++) {
                    optionList.get(i).setIsChecked(false);
                   /* if (optionList.get(i).getIsChecked().equals(optionList.get(position).getIsChecked())){
                        optionList.get(position).setIsChecked(true);
                        //break;
                    }else{

                    }*/
                    optionList.get(i).setIsChecked(false);
                }
                optionList.get(position).setIsChecked(true);

            }
        });
        if (optionList.get(position).getIsChecked()) {
            // mListener.updateOptionClick(AnswerOpt+1);
            holder.itemBinding.tvTribeometerOpt.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_orange));
            holder.itemBinding.mainBlockLl.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_orange));
            holder.itemBinding.tvTribeometerOpt.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.itemBinding.tvTribeometerOpt.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_normal));
            holder.itemBinding.mainBlockLl.setBackground(mContext.getResources().getDrawable(R.drawable.shape_color_normal));
            holder.itemBinding.tvTribeometerOpt.setTextColor(mContext.getResources().getColor(R.color.textcolor));
        }


    }

    @Override
    public int getItemCount() {
        try {
            return optionList.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public interface updateOptionListener {
        void updateOptionClick(int name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowTribeometerOptionBinding itemBinding;
        public ViewHolder(RowTribeometerOptionBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;

        }
    }
}
