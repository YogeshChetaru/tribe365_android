package com.chetaru.tribe365_new.Adapter.award;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.KudosAwardTitleList;
import com.chetaru.tribe365_new.Adapter.Ad_award_list;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.ShowAwardTitleRowBinding;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

public class Ad_kudosAward_list extends RecyclerView.Adapter<Ad_kudosAward_list.ViewHolder> {
    List<KudosAwardTitleList> awardTitleLists = new ArrayList<>();
    private final Context mContent;
    boolean flag=false;
    public MoreKudosListener mListener;

    public Ad_kudosAward_list(List<KudosAwardTitleList> awardTitleLists, Context mContent,  MoreKudosListener mListener) {
        this.awardTitleLists = awardTitleLists;
        this.mContent = mContent;
        this.mListener=mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mContent);
        ShowAwardTitleRowBinding binding= ShowAwardTitleRowBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_award_title_row,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try{
            holder.itemBinding.awardTitleTv.setText(awardTitleLists.get(position).getDotName());
            holder.itemBinding.awardValueTv.setText(awardTitleLists.get(position).getAwardCount().toString());
            Ad_award_list ad_award_list = new Ad_award_list(awardTitleLists.get(position).dotAwardDes,mContent);
           RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContent);
            ItemOffsetDecoration itemDecoration= new ItemOffsetDecoration(mContent,R.dimen.item_offset_small);
            DividerItemDecoration itemDecor=new DividerItemDecoration(mContent,DividerItemDecoration.VERTICAL);
           //holder.award_desc_rv.setHasFixedSize(true);
            holder.itemBinding.awardDescRv.setLayoutManager(layoutManager);
            //holder.award_desc_rv.addItemDecoration(itemDecor);
            holder.itemBinding.awardDescRv.setAdapter(ad_award_list);

            if (awardTitleLists.get(position).getCountStatus()){
                holder.itemBinding.awardLoadMore.setVisibility(View.VISIBLE);
            }else {
                holder.itemBinding.awardLoadMore.setVisibility(View.GONE);
            }
            try {
                if (awardTitleLists.get(position).dotAwardDes.size() > 0) {
                    holder.itemBinding.expandImage.setImageResource(R.drawable.ic_expand_more_24);
                } else {
                    holder.itemBinding.expandImage.setImageResource(R.drawable.ic_expand_more_gray);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            holder.itemBinding.awardLoadMore.setVisibility(View.GONE);
            holder.itemBinding.awardDescRv.setVisibility(View.GONE);

            holder.itemBinding.expandImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (awardTitleLists.get(position).getDotAwardDes().size()>0) {
                        if (!flag) {
                            holder.itemBinding.awardDescRv.setVisibility(View.VISIBLE);
                            holder.itemBinding.expandImage.setImageResource(R.drawable.ic_expand_less_24);
                            if (awardTitleLists.get(position).getCountStatus()) {
                                holder.itemBinding.awardLoadMore.setVisibility(View.VISIBLE);
                            } else {
                                holder.itemBinding.awardLoadMore.setVisibility(View.GONE);
                            }

                            flag = true;
                        } else {
                            holder.itemBinding.awardDescRv.setVisibility(View.GONE);
                            holder.itemBinding.expandImage.setImageResource(R.drawable.ic_expand_more_24);
                            holder.itemBinding.awardLoadMore.setVisibility(View.GONE);
                            flag = false;

                        }
                    }
                }
            });
            holder.itemBinding.awardLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.loadMoreClick(awardTitleLists.get(position));
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        try{
            return awardTitleLists.size();
        }catch ( Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView award_title_tv,award_value_tv;
        ImageView expand_image;
        RecyclerView award_desc_rv;
        TextView load_more_tv;
        ShowAwardTitleRowBinding itemBinding;
        public ViewHolder( ShowAwardTitleRowBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* award_title_tv= view.findViewById(R.id.award_title_tv);
            award_value_tv= view.findViewById(R.id.award_value_tv);
            expand_image=view.findViewById(R.id.expand_image);
            load_more_tv= view.findViewById(R.id.award_load_more);
            award_desc_rv= view.findViewById(R.id.award_desc_rv);*/
        }
    }
    public interface MoreKudosListener{
        void loadMoreClick(KudosAwardTitleList awardTitleList);
    }



}
