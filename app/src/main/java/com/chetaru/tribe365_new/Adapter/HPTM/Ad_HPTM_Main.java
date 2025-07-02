package com.chetaru.tribe365_new.Adapter.HPTM;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.HPTM.HPTM_Principle;
import com.chetaru.tribe365_new.databinding.HptmMainListBinding;
import com.chetaru.tribe365_new.utility.customView.AutoResizeTextView;

import java.util.List;

public class Ad_HPTM_Main extends RecyclerView.Adapter<Ad_HPTM_Main.ViewHolder> {
    List<HPTM_Principle> list;
    Context mContext;
    String freeVersion="back";

    public Ad_HPTM_Main(List<HPTM_Principle> list, Context mContext,String freeVersion) {
        this.list = list;
        this.mContext = mContext;
        this.freeVersion=freeVersion;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        HptmMainListBinding binding= HptmMainListBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hptm_main_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemBinding.tvTitleHptm.setText(list.get(position).getTitle());
        holder.itemBinding.tvDescHptm.setText(list.get(position).getDescription());

        holder.itemBinding.tvCompletionHptm.setText("Completion - "+list.get(position).getCompletionPercent()+"%" );
        try {

            if (!freeVersion.equals("freeVersion")) {
                holder.itemBinding.tvTeamHptm.setVisibility(View.VISIBLE);
                holder.itemBinding.tvTeamHptm.setText("Team Feedback - " + list.get(position).getTeamFeedbackScorePercent() + "%");
            }else {
                holder.itemBinding.tvTeamHptm.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
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
        TextView tv_title_hptm,tv_desc_hptm;
        AutoResizeTextView tv_completion_hptm,tv_team_hptm;
        //TextView tv_completion_hptm,tv_team_hptm;
        HptmMainListBinding itemBinding;
        public ViewHolder(HptmMainListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* tv_title_hptm=view.findViewById(R.id.tv_title_hptm);
            tv_desc_hptm=view.findViewById(R.id.tv_desc_hptm);
            tv_completion_hptm=view.findViewById(R.id.tv_completion_hptm);
            tv_team_hptm=view.findViewById(R.id.tv_team_hptm);*/

        }
    }
}
