package com.chetaru.tribe365_new.Adapter.KnowMember;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.databinding.RowOptionsListBinding;

import java.util.List;

public class Ad_Motivational extends RecyclerView.Adapter<Ad_Motivational.ViewHolder> {

    List<String> motivationalList;
    Context mContext;

    public Ad_Motivational(List<String> motivationalList, Context mContext) {
        this.motivationalList = motivationalList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        RowOptionsListBinding binding=RowOptionsListBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_options_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemBinding.rowNameTv.setText(motivationalList.get(position));
        holder.itemBinding.cardMainLl.setOnClickListener(v->{
           // onclickListener.onClick(R.id.row_name_tv,position,listName.get(position));
        });
    }

    @Override
    public int getItemCount() {
        try {
            return motivationalList.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView row_name_tv;
        CardView main_card;
        RowOptionsListBinding itemBinding;
        public ViewHolder(@NonNull RowOptionsListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*main_card=v.findViewById(R.id.card_main_ll);
            row_name_tv=v.findViewById(R.id.row_name_tv);*/
        }
    }
}
