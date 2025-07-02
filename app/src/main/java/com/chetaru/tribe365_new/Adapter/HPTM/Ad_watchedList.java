package com.chetaru.tribe365_new.Adapter.HPTM;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.HPTM.HPTMLearningCheckList;
import com.chetaru.tribe365_new.API.Models.HPTM.PrincipleFirst;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.WatchListBinding;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;

import java.util.List;

public class Ad_watchedList extends RecyclerView.Adapter<Ad_watchedList.ViewHolder> {

    //List<PrincipleFirst> list;
    //Map<String,List<PrincipleFirst>> mapList;
    List<HPTMLearningCheckList> list;
    Context mContext;
    boolean flag=false,flagExpand=false;
    boolean radioFlag=false;
   public watchListListener mListener;
   public LinearLayoutManager linearLayoutManager;

    public Ad_watchedList(List<HPTMLearningCheckList> list, Context mContext, watchListListener mListener) {
        this.list = list;
        this.mContext = mContext;
        this.mListener=mListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        WatchListBinding binding=WatchListBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HPTMLearningCheckList firstValue = list.get(position);
        try {
            holder.itemBinding.tvTitleWatch.setText(list.get(position).getHeadingTitle());
            if (firstValue.getLearningList().size()>0) {
                for (int i = 0; i < firstValue.getLearningList().size(); i++) {
                    if (firstValue.getLearningList().get(i).getUserReadChecklist()){
                        radioFlag=true;
                    }else {
                        radioFlag=false;
                        break;
                    }
                }
            }else{
                radioFlag = false;
            }
            if (radioFlag){
                list.get(position).setSelected(true);
            }else{
                list.get(position).setSelected(false);
            }
            if(list.get(position).isSelected()){
                holder.itemBinding.imageOption.setVisibility(View.VISIBLE);
                holder.itemBinding.imageOption.setImageResource(R.drawable.green_check);
            }else{
                holder.itemBinding.imageOption.setVisibility(View.GONE);
                holder.itemBinding.imageOption.setImageResource(R.drawable.circle_unchecked);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if (firstValue.getLearningList().size()>0){
                LinearLayoutManager layout_manager=new LinearLayoutManager(mContext);
                holder.itemBinding.showChildLl.setVisibility(View.VISIBLE);
                holder.itemBinding.rvChild.setLayoutManager(layout_manager);
                holder.itemBinding.rvChild.setHasFixedSize(true);
                ItemOffsetDecoration itemDecoration= new ItemOffsetDecoration(mContext,R.dimen.item_small);
                holder.itemBinding.rvChild.addItemDecoration(itemDecoration);
                Ad_watched_childList ad_chiltList= new Ad_watched_childList(firstValue.getLearningList(), mContext, new Ad_watched_childList.watchInnerListener() {
                    @Override
                    public void radioInnerOptionClick(PrincipleFirst list, boolean flag) {
                        mListener.radioOptionClick(list);

                            if (firstValue.getLearningList().size() > 0) {
                                for (int i = 0; i < firstValue.getLearningList().size(); i++) {
                                    if (firstValue.getLearningList().get(i).getUserReadChecklist()) {
                                        radioFlag = true;
                                    } else {
                                        radioFlag = false;
                                        break;
                                    }
                                }
                            }

                        setRadioSelection(holder,radioFlag,position);

                    }
                });
                holder.itemBinding.rvChild.setAdapter(ad_chiltList);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        holder.itemBinding.expandImage.setOnClickListener(v->{
            if (!flagExpand){
                holder.itemBinding.expandImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_more_24));
                //holder.title_ll.setBackground(mContext.getResources().getDrawable(R.drawable.card_bg_light_white));
                holder.itemBinding.rvChild.setVisibility(View.VISIBLE);
                flagExpand=true;
            }else {
               // holder.expand_image.setBackground(mContext.getResources().getDrawable(R.drawable.ic_expand_less_24));
                holder.itemBinding.expandImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_arrow_right_24));
                //holder.title_ll.setBackground(mContext.getResources().getDrawable(R.drawable.edit_text_card_rounded));
                holder.itemBinding.rvChild.setVisibility(View.GONE);
                flagExpand=false;
            }
        });
        holder.itemBinding.tvTitleWatch.setOnClickListener(v->{
            if(!flag){
                holder.itemBinding.expandImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_more_24));
                //holder.title_ll.setBackground(mContext.getResources().getDrawable(R.drawable.card_bg_light_white));
                flag=true;
                holder.itemBinding.rvChild.setVisibility(View.VISIBLE);


            }else{
                holder.itemBinding.expandImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_arrow_right_24));
                //holder.title_ll.setBackground(mContext.getResources().getDrawable(R.drawable.edit_text_card_rounded));
                holder.itemBinding.rvChild.setVisibility(View.GONE);
                flag=false;
            }
        });
        /*holder.image_option.setOnClickListener(v->{
            radioFlag=list.get(position).isSelected();
            if (!radioFlag){
                radioFlag=true;
                holder.image_option.setImageResource(R.drawable.green_check);
            }else {
                radioFlag=false;
                holder.image_option.setImageResource(R.drawable.circle_unchecked);
            }
           // mListener.radioOptionClick(list.get(position));

        });*/


    }
    public void setRadioSelection(ViewHolder holder,boolean flag, int position){
        if (flag) {
            list.get(position).setSelected(true);
            holder.itemBinding.imageOption.setVisibility(View.VISIBLE);
            holder.itemBinding.imageOption.setImageResource(R.drawable.green_check);

        }else {
            list.get(position).setSelected(false);
            holder.itemBinding.imageOption.setVisibility(View.GONE);
            holder.itemBinding.imageOption.setImageResource(R.drawable.circle_unchecked);
        }
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image_option;
        TextView tv_title_watch;
        ImageView expand_image;
        LinearLayout title_ll;
        LinearLayout show_child_ll;
        RecyclerView rv_child;

        WatchListBinding itemBinding;
        public ViewHolder(WatchListBinding itemBinding){
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* image_option = view.findViewById(R.id.image_option);
            tv_title_watch= view.findViewById(R.id.tv_title_watch);
            expand_image=view.findViewById(R.id.expand_image);
            title_ll=view.findViewById(R.id.title_ll);
            show_child_ll=view.findViewById(R.id.show_child_ll);
            rv_child=view.findViewById(R.id.rv_child);*/
        }

    }

    public interface watchListListener{
        void radioOptionClick(PrincipleFirst list);
    }
}
