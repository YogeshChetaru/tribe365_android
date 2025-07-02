package com.chetaru.tribe365_new.Adapter.KnowMember;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.MemberHome.KudosCount;
import com.chetaru.tribe365_new.R;

import java.util.List;

public class Ad_belief_member extends RecyclerView.Adapter<Ad_belief_member.ViewHolder> {

    List<KudosCount> countList;
    Context mContext;

    public Ad_belief_member(List<KudosCount> countList, Context mContext) {
        this.countList = countList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        //RowBeliefListBinding binding= RowBeliefListBinding.inflate(inflater,parent,false);

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowbelief_listitem,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name.setText(countList.get(position).getName());
        holder.tv_value.setText(countList.get(position).getKudosCount().toString());

    }

    @Override
    public int getItemCount() {
        try {
            return countList.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }


    }

     class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_value;
        LinearLayout ll_main;
       // RowBeliefListBinding itemBinding;
         ViewHolder(View v) {
            super(v);
            //this.itemBinding=itemBinding;
            tv_name = v.findViewById(R.id.tv_belief_name);
            tv_value = v.findViewById(R.id.tv_today_value);
            ll_main = v.findViewById(R.id.ll_main);
        }
    }
}
