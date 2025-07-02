package com.chetaru.tribe365_new.Adapter.KnowMember;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.MemberHome.PersonalityType;
import com.chetaru.tribe365_new.databinding.PersRowOptionsListBinding;

import java.util.List;

public class Ad_personalityType extends RecyclerView.Adapter<Ad_personalityType.ViewHolder> {

    List<PersonalityType> personalityList;
    Context mContext;

    public Ad_personalityType(List<PersonalityType> personalityList, Context mContext) {
        this.personalityList = personalityList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        PersRowOptionsListBinding binding=PersRowOptionsListBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pers_row_options_list,parent,false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       /* if(personalityList.get(position).contains("(")){
            String abc=personalityList.get(position).replace("(","\n");
            String newValue=abc.replace(")","");
            holder.row_name_tv.setText(newValue);
        }else {
            holder.row_name_tv.setText(personalityList.get(position));
        }*/

        holder.itemBinding.rowNameTv.setText(personalityList.get(position).getCateName()+"\n" +personalityList.get(position).getScore());
        holder.itemBinding.cardMainLl.setOnClickListener(v->{
           // onClickListener.onClick(R.id.row_name_tv,position,listName.get(position));
        });
    }

    @Override
    public int getItemCount() {
        try {
            return personalityList.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView main_card;
        TextView row_name_tv;
        PersRowOptionsListBinding itemBinding;
        public ViewHolder( PersRowOptionsListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*main_card=v.findViewById(R.id.card_main_ll);
            row_name_tv=v.findViewById(R.id.row_name_tv);*/

        }
    }
}
