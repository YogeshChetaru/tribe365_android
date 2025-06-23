package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelValueList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.RowValuelistcheckboxBinding;
import com.chetaru.tribe365_new.utility.SessionParam;

import java.util.ArrayList;
import java.util.List;

public class Ad_ValueList extends RecyclerView.Adapter<Ad_ValueList.ViewHolder> {

    List<ModelValueList> list = new ArrayList<>();
    List<String> selectedValueLsit = new ArrayList<>();
    SessionParam sessionParam;
    String path = "";
    private Context context;


    public Ad_ValueList(List<ModelValueList> list, Context context, String selectedValues, String path) {
        this.list = list;
        this.path = path;
        notifyDataSetChanged();
        this.context = context;
        sessionParam = new SessionParam(context);
        if (!selectedValues.isEmpty()) {
            String splitValue[] = selectedValues.split(",");
            for (int i = 0; i < splitValue.length; i++) {
                selectedValueLsit.add(splitValue[i]);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        RowValuelistcheckboxBinding binding= RowValuelistcheckboxBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_valuelistcheckbox, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.itemBinding.tvValue.setText(list.get(position).getName());
        if (path.equals("evidence"))
            holder.itemBinding.ivCb.setVisibility(View.GONE);
        holder.itemBinding.ivCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getChecked()) {
                    holder.itemBinding.ivCb.setImageDrawable(context.getResources().getDrawable(R.drawable.uncheck));
                    list.get(position).setChecked(false);
                } else {
                    holder.itemBinding.ivCb.setImageDrawable(context.getResources().getDrawable(R.drawable.check));
                    list.get(position).setChecked(true);
                }
            }
        });

        if (sessionParam.role.equalsIgnoreCase("1")) {
            if (selectedValueLsit.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < selectedValueLsit.size(); j++) {
                        if (list.get(position).getName().equalsIgnoreCase(selectedValueLsit.get(j))) {
                            holder.itemBinding.ivCb.setImageDrawable(context.getResources().getDrawable(R.drawable.check));
                            //holder.iv_cb.setEnabled(false);
                            list.get(position).setChecked(true);
                        }
                    }
                }
            }
        }

      /*holder.tv_content.setText(list.get(position).getDescription());
        holder.tv_date.setText(list.get(position).getCreatedAt());
        try{
            if(!list.get(position).getFileURL().isEmpty()||list.get(position).getFileURL()!=null){
                Picasso.get().load(list.get(position).getFileURL()).into(holder.iv_logo);
            }
        }catch (IllegalArgumentException iae){

        }*/
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

    public List<ModelValueList> getList() {
        return list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_value, tv_date;
        ImageView iv_cb;

        RowValuelistcheckboxBinding itemBinding;
        public ViewHolder(RowValuelistcheckboxBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*tv_value = v.findViewById(R.id.tv_value);
            iv_cb = v.findViewById(R.id.iv_cb);*/

        }
    }
}
