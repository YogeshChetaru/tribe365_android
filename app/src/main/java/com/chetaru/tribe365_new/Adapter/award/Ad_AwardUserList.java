package com.chetaru.tribe365_new.Adapter.award;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.KnowHome.KudosAwardList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.AwardUserListBinding;

import java.util.ArrayList;
import java.util.List;

public class Ad_AwardUserList extends RecyclerView.Adapter<Ad_AwardUserList.ViewHolder> {
    List<String> awardUserList= new ArrayList<>();
    List<KudosAwardList> list= new ArrayList<>();
    private  Context mContext;
    String awardValue;

    public Ad_AwardUserList(String awardValueString,List<KudosAwardList> list,List<String> awardUserList, Context mContext) {
        this.awardUserList = awardUserList;
        this.list= list;
        this.mContext = mContext;
        this.awardValue= awardValueString;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        AwardUserListBinding binding= AwardUserListBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.award_user_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            Glide.with(mContext)
                    .load(list.get(position).getUserImage())
                    .placeholder(R.drawable.user_circle)
                    .into(holder.itemBinding.personImage);
            //holder.person_name.setText(awardUserList.get(position).toString());
            //holder.person_name.setText("(" + awardUserList.get(position) + " - " + awardValue+ ")");
            holder.itemBinding.personNameTv.setText( list.get(position).getUserName());
            holder.setIsRecyclable(false);

        }catch (Exception e){
            e.printStackTrace();
        }

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
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView person_image;
        TextView person_name;
        AwardUserListBinding itemBinding;
        public ViewHolder(@NonNull AwardUserListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* person_image= item.findViewById(R.id.person_image);
            person_name= item.findViewById(R.id.person_name_tv);*/

        }
    }
}
