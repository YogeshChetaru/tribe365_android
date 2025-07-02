package com.chetaru.tribe365_new.Adapter.HPTM;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.HPTM.PrincipleFirst;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.WatchChildListBinding;

import java.util.List;

public class Ad_watched_childList extends RecyclerView.Adapter<Ad_watched_childList.ViewHolder> {

    List<PrincipleFirst> list;
    Context mContext;
    boolean flag=false;
    boolean radioFlag=false;
    public watchInnerListener mListener;

    public  Ad_watched_childList(List<PrincipleFirst> list, Context mContext, watchInnerListener mListener) {
        this.list = list;
        this.mContext = mContext;
        this.mListener= mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        WatchChildListBinding binding= WatchChildListBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_child_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            PrincipleFirst firstValue = list.get(position);
            holder.itemBinding.tvNameWatch.setText(list.get(position).getChecklistTitle());
            if(list.get(position).getUserReadChecklist()){
                holder.itemBinding.imageOption.setImageResource(R.drawable.circle_checked);
            }else{
                holder.itemBinding.imageOption.setImageResource(R.drawable.circle_unchecked);
            }
            if (list.get(position).getChecklistTitle().equals("")){
                holder.itemBinding.tvNameWatch.setVisibility(View.GONE);
            }else {
                holder.itemBinding.tvNameWatch.setVisibility(View.VISIBLE);
            }
            if (list.get(position).getDescription().equals("")){
                holder.itemBinding.tvDescWatch.setVisibility(View.GONE);
            }else{
                holder.itemBinding.tvDescWatch.setText(list.get(position).getDescription());
                holder.itemBinding.tvDescWatch.setVisibility(View.VISIBLE);
            }
            if(!list.get(position).getDocument().equals("")){
                holder.itemBinding.watchDocumentLl.setVisibility(View.VISIBLE);
            }else {
                holder.itemBinding.watchDocumentLl.setVisibility(View.GONE);
            }
            if (!list.get(position).getLink().equals("")){
                holder.itemBinding.watchVideoLl.setVisibility(View.VISIBLE);
            }else {
                holder.itemBinding.watchVideoLl.setVisibility(View.GONE);
            }
            if (position==(list.size()-1)){
                holder.itemBinding.bottomView.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        holder.itemBinding.imageOption.setOnClickListener(v->{
            radioFlag=list.get(position).getUserReadChecklist();
            if (!radioFlag){
                radioFlag=true;
                list.get(position).setUserReadChecklist(true);
                holder.itemBinding.imageOption.setImageResource(R.drawable.circle_checked);
            }else {
                radioFlag=false;
                list.get(position).setUserReadChecklist(false);
                holder.itemBinding.imageOption.setImageResource(R.drawable.circle_unchecked);
            }
            mListener.radioInnerOptionClick(list.get(position),radioFlag);

        });
        holder.itemBinding.watchVideoLl.setOnClickListener(v->{
            String link = list.get(position).getLink();
            if(link!=null) {
                Intent browserIntent= new Intent(Intent.ACTION_VIEW,Uri.parse(link));
                mContext.startActivity(browserIntent);

            }
        });
        holder.itemBinding.watchDocumentLl.setOnClickListener(v->{
            String link=list.get(position).getDocument();
            if (!link.equals("")){
                Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(link));
                mContext.startActivity(intent);
            }
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image_option;
        TextView tv_name_watch,tv_desc_watch;
        LinearLayout watch_video_ll;
        LinearLayout show_main_ll,watch_document_ll;
        LinearLayout show_child_ll;
        RecyclerView rv_child;
        View bottomView;
        WatchChildListBinding itemBinding;
        public ViewHolder(WatchChildListBinding itemBinding){
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* image_option = view.findViewById(R.id.image_option);
            tv_name_watch=view.findViewById(R.id.tv_name_watch);
            tv_desc_watch=view.findViewById(R.id.tv_desc_watch);
            watch_video_ll=view.findViewById(R.id.watch_video_ll);
            show_main_ll=view.findViewById(R.id.show_main_ll);
            watch_document_ll=view.findViewById(R.id.watch_document_ll);
            show_child_ll=view.findViewById(R.id.show_child_ll);
            rv_child=view.findViewById(R.id.rv_child);
            bottomView=view.findViewById(R.id.bottom_view);*/
        }

    }

    public interface watchInnerListener{
        void radioInnerOptionClick(PrincipleFirst list ,boolean flag);
    }

}
