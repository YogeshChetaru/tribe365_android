package com.chetaru.tribe365_new.Adapter.Risk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.TestModel;
import com.chetaru.tribe365_new.API.Models.Risk.RiskArr;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.RiskListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Ad_RiskHome  extends RecyclerView.Adapter<Ad_RiskHome.ViewHolder> {

    Context mContext;
    List<RiskArr> list;
    public ActionListener mListener;

    public Ad_RiskHome(Context mContext, List<RiskArr> list,ActionListener mListener) {
        this.mContext = mContext;
        this.list = list;
        this.mListener= mListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.risk_list);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        RiskListBinding binding=RiskListBinding.inflate(inflater,parent,false);
        //View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.risk_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {

            holder.itemBinding.numberTv.setText((position+1)+"");
            holder.itemBinding.nameTv.setText(list.get(position).getTitle());
            holder.itemBinding.sceondNameTv.setText(list.get(position).getPriority());
            if (list.get(position).getPriority().equals("High")){
                holder.itemBinding.numberTv.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.itemBinding.nameTv.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.itemBinding.sceondNameTv.setTextColor(mContext.getResources().getColor(R.color.red));
            }else {
                holder.itemBinding.numberTv.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.itemBinding.nameTv.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.itemBinding.sceondNameTv.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            holder.itemBinding.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* holder.number_tv.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.name_tv.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.sceond_name_tv.setTextColor(mContext.getResources().getColor(R.color.red));*/
                    mListener.riskAction(list.get(position));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView ll_main;
        TextView number_tv,name_tv,sceond_name_tv;
        RiskListBinding itemBinding;
        public ViewHolder( RiskListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* number_tv=item.findViewById(R.id.number_tv);
            name_tv=item.findViewById(R.id.name_tv);
            sceond_name_tv=item.findViewById(R.id.sceond_name_tv);
            ll_main=item.findViewById(R.id.ll_main);*/

        }
    }

    public interface ActionListener{
        void riskAction(RiskArr testModel);
    }
}
