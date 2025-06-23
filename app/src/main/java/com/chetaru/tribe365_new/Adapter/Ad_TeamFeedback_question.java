package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.TeamFeedback.QuestionArr;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.RowTeamQuestionsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Ad_TeamFeedback_question extends RecyclerView.Adapter<Ad_TeamFeedback_question.ViewHolder> {
    List<QuestionArr> list=new ArrayList<>();
    public Context mContext;
    public String otherUserName;

    public Ad_TeamFeedback_question(List<QuestionArr> list, Context mContext,String userName) {
        this.list = list;
        this.mContext = mContext;
        this.otherUserName=userName;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        RowTeamQuestionsBinding binding=RowTeamQuestionsBinding.inflate(inflater,parent,false);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_team_questions,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try{
            holder.itemBinding.tvQuestionNum.setText(list.get(position).getQuestionId().toString());
            holder.itemBinding.rvOptionsAnswer.setLayoutManager(new LinearLayoutManager(mContext));
            String question=list.get(position).getQuestion();
            String replace=question.replace("<Name>",otherUserName);
            holder.itemBinding.tvTeamQuestion.setText(replace);

            Ad_option_team optionAdapter= new Ad_option_team(mContext, list.get(position).getOptions(), new Ad_option_team.OptionClickListener() {
                @Override
                public void optionClick(int ansPosition) {

                    list.get(position).setmAnswer(String.valueOf(ansPosition));
                    notifyDataSetChanged();
                }
            });
            optionAdapter.notifyDataSetChanged();

            holder.itemBinding.rvOptionsAnswer.setAdapter(optionAdapter);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_question_num,tv_question_name;
        RecyclerView rv_option_answer;
        RowTeamQuestionsBinding itemBinding;
        public ViewHolder(RowTeamQuestionsBinding itemBinding){
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*tv_question_num=v.findViewById(R.id.tv_question_num);
            tv_question_name=v.findViewById(R.id.tv_team_question);
            rv_option_answer=v.findViewById(R.id.rv_options_answer);*/

        }

    }

}
