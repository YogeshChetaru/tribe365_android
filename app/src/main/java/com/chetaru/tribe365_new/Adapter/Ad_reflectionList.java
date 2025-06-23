package com.chetaru.tribe365_new.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.reflection.Reflection;
import com.chetaru.tribe365_new.UI.Offloading.Act_Ref_HistoryDetail;
import com.chetaru.tribe365_new.databinding.ReflectoinListBinding;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class Ad_reflectionList extends RecyclerView.Adapter<Ad_reflectionList.ViewHolder> {

    List<Reflection> list= new ArrayList<>();
    Utility utility;
    SessionParam sessionParam;
    Context mContext;
    String jsonData="";

    public Ad_reflectionList(List<Reflection> list,Context mContext){
        this.list=list;
        this.mContext=mContext;
        utility=new Utility();
        sessionParam=new SessionParam(mContext);
    }


    @NonNull
    @Override
    public Ad_reflectionList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        ReflectoinListBinding binding=ReflectoinListBinding.inflate(inflater,parent,false);

       // View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reflectoin_list,parent,false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull Ad_reflectionList.ViewHolder holder, int position) {
        holder.itemBinding.tvDescTxt.setText(list.get(position).getmMessage());
        String dateString=list.get(position).getmCreatedAt();
        holder.itemBinding.tvDateTxt.setText(utility.getDate(dateString));
        holder.itemBinding.mainBlockLl.setOnClickListener(v ->{
            mContext.startActivity(new Intent(mContext, Act_Ref_HistoryDetail.class)
                    .putExtra("index", position + "")
                    .putExtra("changeItId", list.get(position).getmId() + "")
                    );
        });

    }

    @Override
    public int getItemCount() {
        try {
            return list.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView desText,dateText;
        LinearLayout main_block_ll;
        ReflectoinListBinding itemBinding;
        public ViewHolder(ReflectoinListBinding itemBinding){
            super(itemBinding.getRoot());
            this.itemBinding= itemBinding;
           /* desText=v.findViewById(R.id.tv_desc_txt);
            dateText=v.findViewById(R.id.tv_date_txt);
            main_block_ll= v.findViewById(R.id.main_block_ll);*/

        }
    }
}
