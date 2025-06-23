package com.chetaru.tribe365_new.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.AwardList;
import com.chetaru.tribe365_new.databinding.DescriptionListBinding;
import com.chetaru.tribe365_new.utility.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Ad_award_list  extends RecyclerView.Adapter<Ad_award_list.ViewHolder> {
    List<AwardList> list;
    Context context;
    Utility utility;

    public Ad_award_list(List<AwardList> list, Context context) {
        this.list = list;
        this.context = context;
        utility= new Utility();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        DescriptionListBinding binding= DescriptionListBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.description_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        try {
            String desc= list.get(position).getDescription().trim();
            String dateString= list.get(position).getCreatedAt();
            holder.itemBinding.textDescription.setText(desc);
            holder.itemBinding.textDate.setText(utility.getDate(dateString));

            //holder.tv_description.setText(desc);
           //holder.text_date.setText(utility.getDate(dateString));
            if (position==(list.size()-1)){
                //holder.line_divider.setVisibility(View.GONE);
                holder.itemBinding.lineDividerView.setVisibility(View.GONE);
            }else{
                //holder.line_divider.setVisibility(View.VISIBLE);
                holder.itemBinding.lineDividerView.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_description,text_date;
        View line_divider;
        private DescriptionListBinding itemBinding;
        public ViewHolder(DescriptionListBinding  itemBinding){
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*tv_description=view.findViewById(R.id.text_description);
            text_date=view.findViewById(R.id.text_date);
            line_divider= view.findViewById(R.id.line_divider_view);*/
        }
    }

    @Override
    public int getItemCount() {
        try{
           return list.size();
        }catch (Exception e) {
            return 0;
        }
    }
}
