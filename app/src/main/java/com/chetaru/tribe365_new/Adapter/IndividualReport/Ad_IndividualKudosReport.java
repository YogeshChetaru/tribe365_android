package com.chetaru.tribe365_new.Adapter.IndividualReport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.report.BeliefCount;
import com.chetaru.tribe365_new.R;

import java.util.List;

public class Ad_IndividualKudosReport extends RecyclerView.Adapter<Ad_IndividualKudosReport.ViewHolder> {

    List<BeliefCount> beliefList;

    List<BeliefCount> departKudosList;
    List<BeliefCount> userKudosList;
    Context context;

    public Ad_IndividualKudosReport(List<BeliefCount> beliefList, Context context) {

    }

    public Ad_IndividualKudosReport(List<BeliefCount> orgDotKudos, List<BeliefCount> departDotKudos, List<BeliefCount> userDotKudos, Context mContext) {
        this.beliefList = orgDotKudos;
        this.departKudosList = departDotKudos;
        this.userKudosList = userDotKudos;
        this.context = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater= LayoutInflater.from(context);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.kudos_report_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            holder.kudos_name_tv.setText(beliefList.get(position).getName());
            try {
                /******************************** Set Organization Kudos **********************/
                int todayOrgValue = beliefList.get(position).getKudosCount();
                int lastOrgValue = beliefList.get(position).getLastKudosCount();
                holder.org_value_current_tv.setText(todayOrgValue+"");
                int different = todayOrgValue - lastOrgValue;
                holder.org_value_difference_tv.setText(" ("+different+")");
               if (different==0){
                   holder.org_value_difference_tv.setTextColor(context.getResources().getColor(R.color.black));
               }else if (todayOrgValue > lastOrgValue) {
                    holder.org_value_difference_tv.setTextColor(context.getResources().getColor(R.color.color_green));
                } else {
                    holder.org_value_difference_tv.setTextColor(context.getResources().getColor(R.color.red));
                }
                /************************set Award count***************************/
                int todayOrgAward = beliefList.get(position).getKudosAwardCount();
                int lastOrgAward = beliefList.get(position).getLastKudosAwardCount();
                holder.org_awards_current_tv.setText(todayOrgAward+"");
                int differentAward = todayOrgAward - lastOrgAward;
                holder.org_awards_difference_tv.setText(" ("+differentAward+")");
                if (differentAward==0){
                    holder.org_awards_difference_tv.setTextColor(context.getResources().getColor(R.color.black));
                }else if (todayOrgAward > lastOrgAward) {
                    holder.org_awards_difference_tv.setTextColor(context.getResources().getColor(R.color.color_green));
                } else {
                    holder.org_awards_difference_tv.setTextColor(context.getResources().getColor(R.color.red));
                }
                /******************************** Finish Organization Kudos **********************/
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                /******************************** Set Team Kudos **********************/
                int todayDepartValue = departKudosList.get(position).getKudosCount();
                int lastDepartValue = departKudosList.get(position).getLastKudosCount();

                holder.team_value_current_tv.setText(todayDepartValue+"");
                int different = todayDepartValue - lastDepartValue;
                holder.team_value_difference_tv.setText(" ("+different+")");
                if (different==0){
                    holder.team_value_difference_tv.setTextColor(context.getResources().getColor(R.color.black));
                }else if (todayDepartValue > lastDepartValue) {
                    holder.team_value_difference_tv.setTextColor(context.getResources().getColor(R.color.color_green));
                } else {
                    holder.team_value_difference_tv.setTextColor(context.getResources().getColor(R.color.red));
                }

                /************************set Award count***************************/
                int todayDepartAward = departKudosList.get(position).getKudosAwardCount();
                int lastDepartAward = departKudosList.get(position).getLastKudosAwardCount();
                holder.team_awards_current_tv.setText(todayDepartAward+"");
                int differentAward = todayDepartAward - lastDepartAward;
                holder.team_awards_difference_tv.setText(" ("+differentAward+")");
                if (differentAward==0){
                    holder.team_awards_difference_tv.setTextColor(context.getResources().getColor(R.color.black));
                }else if (todayDepartAward > lastDepartAward) {
                    holder.team_awards_difference_tv.setTextColor(context.getResources().getColor(R.color.color_green));
                } else {
                    holder.team_awards_difference_tv.setTextColor(context.getResources().getColor(R.color.red));
                }
                /******************************** Finish team Kudos **********************/
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                /******************************** Set User Kudos **********************/
                int todayUserValue = userKudosList.get(position).getKudosCount();
                int lastUserValue = userKudosList.get(position).getLastKudosCount();

                holder.your_current_value_tv.setText(todayUserValue+"");
                int different = todayUserValue - lastUserValue;
                holder.your_difference_value_tv.setText(" ("+different+")");
                if (different == 0){
                    holder.your_difference_value_tv.setTextColor(context.getResources().getColor(R.color.black));
                }else if (todayUserValue > lastUserValue) {
                    holder.your_difference_value_tv.setTextColor(context.getResources().getColor(R.color.color_green));
                } else {
                    holder.your_difference_value_tv.setTextColor(context.getResources().getColor(R.color.red));
                }

                /************************set Award count***************************/
                int todayUserAward = userKudosList.get(position).getKudosAwardCount();
                int lastUserAward = userKudosList.get(position).getLastKudosAwardCount();
                holder.your_current_awards_tv.setText(todayUserAward+"");
                int differentAward = todayUserAward - lastUserAward;
                holder.your_difference_awards_tv.setText(" ("+differentAward+")");
                if (differentAward ==0){
                    holder.your_difference_awards_tv.setTextColor(context.getResources().getColor(R.color.black));
                }else if (todayUserAward > lastUserAward) {
                    holder.your_difference_awards_tv.setTextColor(context.getResources().getColor(R.color.color_green));
                } else {
                    holder.your_difference_awards_tv.setTextColor(context.getResources().getColor(R.color.red));
                }
                /******************************** Finish User Kudos **********************/
                if (position == beliefList.size() - 1) {
                    holder.bottom_view.setVisibility(View.GONE);
                }else {
                    holder.bottom_view.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        try {
            return beliefList.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView kudos_name_tv;
        TextView org_value_current_tv,org_value_difference_tv,team_value_current_tv,team_value_difference_tv,your_current_value_tv,your_difference_value_tv;
        TextView org_awards_current_tv,org_awards_difference_tv,team_awards_current_tv,team_awards_difference_tv,your_current_awards_tv,your_difference_awards_tv;
        View bottom_view;

        public ViewHolder(@NonNull View view) {
            super(view);
            kudos_name_tv=view.findViewById(R.id.kudos_belief_name_tv);
            bottom_view= view.findViewById(R.id.bottom_view);
            org_value_current_tv=view.findViewById(R.id.org_value_current_tv);
            org_value_difference_tv=view.findViewById(R.id.org_value_difference_tv);
            team_value_current_tv=view.findViewById(R.id.team_value_current_tv);
            team_value_difference_tv=view.findViewById(R.id.team_value_difference_tv);
            your_current_value_tv=view.findViewById(R.id.your_current_value_tv);
            your_difference_value_tv=view.findViewById(R.id.your_difference_value_tv);

            org_awards_current_tv=view.findViewById(R.id.org_awards_current_tv);
            org_awards_difference_tv=view.findViewById(R.id.org_awards_difference_tv);
            team_awards_current_tv=view.findViewById(R.id.team_awards_current_tv);
            team_awards_difference_tv=view.findViewById(R.id.team_awards_difference_tv);
            your_current_awards_tv=view.findViewById(R.id.your_current_awards_tv);
            your_difference_awards_tv=view.findViewById(R.id.your_difference_awards_tv);
        }
    }
}
