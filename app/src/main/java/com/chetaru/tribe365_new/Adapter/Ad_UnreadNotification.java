package com.chetaru.tribe365_new.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.apachat.swipereveallayout.core.SwipeLayout;
import com.chetaru.tribe365_new.API.Models.ModelNotificationList;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.CustomerSupport.Act_SupportListDetails;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.UI.Home.Actions.Act_ActionComment;
import com.chetaru.tribe365_new.UI.Know.COT.Cot_New_Question;
import com.chetaru.tribe365_new.UI.Know.COT.Update_Cot_Question;
import com.chetaru.tribe365_new.UI.Know.DOT.Act_DOT_Details;
import com.chetaru.tribe365_new.UI.Know.Diagnostics.Act_Diagnostics_list;
import com.chetaru.tribe365_new.UI.Know.Diagnostics.Act_Tribeometer_list;
import com.chetaru.tribe365_new.UI.Know.Diagnostics.Act_Update_Diagnostics_list;
import com.chetaru.tribe365_new.UI.Know.Diagnostics.Act_Update_Tribeometer_list;
import com.chetaru.tribe365_new.UI.Know.PersonalityType.Act_Personality_type_list;
import com.chetaru.tribe365_new.UI.Know.PersonalityType.Act_Update_Personality_list;
import com.chetaru.tribe365_new.UI.Know.SOT.Act_SOT_Questionlist;
import com.chetaru.tribe365_new.UI.Know.SOT.Act_SOT_Update_Questionlist;
import com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation.Act_SOT_Motivation_Question;
import com.chetaru.tribe365_new.UI.Know.SOT.SOT_Motivation.Act_SOT_Update_Motivation_Question;
import com.chetaru.tribe365_new.UI.Offloading.Act_HistoryDetail;
import com.chetaru.tribe365_new.UI.Offloading.Act_Ref_HistoryDetail;
import com.chetaru.tribe365_new.UI.UserInfo.Act_TeamFeedback_Question;
import com.chetaru.tribe365_new.utility.PaginationAdapterURCallback;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ad_UnreadNotification extends RecyclerView.Adapter<Ad_UnreadNotification.ViewHolder> {

    private static final int ITEM=0;
    private static final int LOADING=1;

    List<ModelNotificationList> list = new ArrayList<>();
    private final List<ModelNotificationList> mFilteredList;

    SharedPreferences.Editor editor;
    Utility utility;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    JSONObject reminderList;
    private final Context context;
    private boolean isLoadingAdded= false;
    private boolean retryPageLoad= false;
    private String errorMsg;
    private final PaginationAdapterURCallback mCallback;
    public kudosListener mListener;

    public Ad_UnreadNotification(List<ModelNotificationList> list, JSONObject reminderList, Context context,kudosListener mListener) {
        this.list = list;
        this.mFilteredList= list;
        this.context = context;
        this.reminderList = reminderList;
        this.mCallback = (PaginationAdapterURCallback) context;
        utility = new Utility();
        this.mListener= mListener;
        sessionParam = new SessionParam(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder=null;
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        switch (viewType){
            case ITEM:
                View viewItem = inflater.inflate(R.layout.row_notification_list, parent, false);
                viewHolder= new ViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading=inflater.inflate(R.layout.item_progress,parent,false);
                viewHolder =new LoadingVH(viewLoading);
                break;
        }
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder mainHolder, final int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                try {
                    final ViewHolder holder = mainHolder;
                    ModelNotificationList listItem = mFilteredList.get(position);
                    if (listItem.getTitle() != null) {


                        try {
                            if (mFilteredList.get(position).getNotificationType().equals("actionList")) {
                                holder.tv_title_by.setVisibility(View.VISIBLE);
                                holder.tv_notiName.setText(mFilteredList.get(position).getTitle());
                            } else {
                                holder.tv_title_by.setVisibility(View.GONE);
                                holder.tv_notiName.setText(mFilteredList.get(position).getTitle());
                            }
                            holder.tv_desc.setText(mFilteredList.get(position).getDescription());
                            } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            if (!mFilteredList.get(position).getDescription().equals("")) {
                                holder.des_ll.setVisibility(View.VISIBLE);
                            } else {
                                holder.des_ll.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            if (mFilteredList.get(position).getCreatedAt() != null) {

                                holder.tv_date.setVisibility(View.VISIBLE);

                                // holder.tv_date.setText(mFilteredList.get(position).getCreatedAt());
                                if (mFilteredList.get(position).getNotificationType().equals("actionList")) {
                                    holder.date_ll.setVisibility(View.GONE);
                                    holder.action_ll.setVisibility(View.VISIBLE);
                                    holder.tv_date_title.setText(" Due Date:");
                                    holder.tv_date_action.setText(utility.getDateWOTime(mFilteredList.get(position).getCreatedAt()));
                                    holder.tv_date.setVisibility(View.GONE);
                                    holder.tv_date_title.setVisibility(View.VISIBLE);
                                    holder.tv_date_action.setVisibility(View.VISIBLE);
                                } else {
                                    holder.date_ll.setVisibility(View.VISIBLE);
                                    holder.action_ll.setVisibility(View.GONE);
                                    holder.tv_date.setVisibility(View.VISIBLE);
                                    holder.tv_date_title.setVisibility(View.GONE);
                                    holder.tv_date_action.setVisibility(View.GONE);
                                    if (mFilteredList.get(position).getNotificationType().equals("teamFeedback")){
                                        holder.tv_date.setText(utility.getFeedbackDate(mFilteredList.get(position).getCreatedAt()));
                                    }else {
                                        holder.tv_date.setText(utility.getDate(mFilteredList.get(position).getCreatedAt()));
                                    }
                                }
                            } else {
                                String date= utility.getDate(mFilteredList.get(position).getCreatedAt());
                                if (date.equals("00-00-0000 00:00")){
                                    holder.tv_date.setText("");
                                }else {
                                    holder.tv_date.setText(date);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {

                            if (mFilteredList.get(position).getTitle().contains("Checklist") || mFilteredList.get(position).getTitle().equals("To Do List")) {
                                holder.iv_logo.setImageResource(R.drawable.ic_checklist_uncheck);

                            } else if (mFilteredList.get(position).getNotificationType().equals("actionList")) {
                                holder.iv_logo.setImageResource(R.drawable.action_image_noti);
                            } else if (mFilteredList.get(position).getTitle().contains("Reminder To Do List!")) {
                                holder.tv_notiName.setTextColor(context.getResources().getColor(R.color.seek_bar));
                                holder.iv_logo.setImageResource(R.drawable.ic_reminder);
                            } else if (mFilteredList.get(position).getTitle().contains("Reminder!")) {
                                holder.tv_notiName.setTextColor(context.getResources().getColor(R.color.seek_bar));
                                holder.iv_logo.setImageResource(R.drawable.ic_reminder);
                            } else if (mFilteredList.get(position).getNotificationType().equals("chat")) {
                                holder.iv_logo.setImageResource(R.drawable.ic_chat_hollow_blue);
                            } else if (mFilteredList.get(position).getNotificationType().contains("support")) {
                                holder.iv_logo.setImageResource(R.drawable.ic_chat_hollow_blue);
                            } else if (mFilteredList.get(position).getNotificationType().equals("reflectionChat")) {
                                holder.iv_logo.setImageResource(R.drawable.ic_chat_hollow_blue);
                            } else if (mFilteredList.get(position).getTitle().contains("Kudos Champion")) {
                                holder.iv_logo.setImageResource(R.drawable.cup);
                                holder.tv_desc.setText(mFilteredList.get(position).getmUserName());
                            } else if (mFilteredList.get(position).getNotificationType().contains("kudoAward")) {
                                holder.iv_logo.setImageResource(R.drawable.awards___kudos_orange);

                            } else if (mFilteredList.get(position).getNotificationType().contains("custom notification")) {
                                holder.iv_logo.setImageResource(R.drawable.direct);
                                holder.tv_desc.setText(mFilteredList.get(position).getDescription());

                            } else if (mFilteredList.get(position).getNotificationType().contains("teamFeedback")) {
                                holder.iv_logo.setImageResource(R.drawable.team_feedback);
                            } else {
                                holder.iv_logo.setImageResource(R.drawable.ic_like_hollow_green);
                            }
                            try {
                                if (mFilteredList.get(position).getmLastMessage() != null && !mFilteredList.get(position).getmLastMessage().equals("")) {
                                    holder.link_button.setVisibility(View.VISIBLE);

                                } else {
                                    holder.link_button.setVisibility(View.GONE);
                                }
                                holder.link_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mFilteredList.get(position).getmLastMessage()));
                                        context.startActivity(browserIntent);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                holder.link_button.setVisibility(View.GONE);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        holder.swipe_layout.setLockDrag(true);
                        holder.tv_desc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (mFilteredList.get(position).getNotificationType().equals("custom notification")) {
                                    api_notificationList(mFilteredList.get(position).getmId());
                                    ShowDiscDialog(mFilteredList.get(position));
                                } else {
                                    if (mFilteredList.get(position).getNotificationType().equals("actionList")) {
                                        context.startActivity(new Intent(context, Act_ActionComment.class)
                                                .putExtra("actionId", mFilteredList.get(position).getmId())
                                                .putExtra("backHandel", "notiBack"));
                                        return;
                                    } else {
                                        api_notificationList(mFilteredList.get(position).getmId());
                                    }
                                    if (mFilteredList.get(position).getNotificationType().equals("chat")) {
                                        context.startActivity(new Intent(context, Act_HistoryDetail.class)
                                                .putExtra("backHandel", "notiBack")
                                                .putExtra("changeItId", mFilteredList.get(position).getmFeedbackId()));
                                    } else if (mFilteredList.get(position).getNotificationType().equals("support")) {
                                        context.startActivity(new Intent(context, Act_SupportListDetails.class)
                                                .putExtra("changeItId", mFilteredList.get(position).getmSupportId()));
                                    } else if (mFilteredList.get(position).getNotificationType().equals("reflectionChat")) {
                                        context.startActivity(new Intent(context, Act_Ref_HistoryDetail.class)
                                                .putExtra("changeItId", mFilteredList.get(position).getmReflectionId())
                                                .putExtra("backHandel", "notiBack"));

                                    } else if (mFilteredList.get(position).getNotificationType().equals("teamFeedback")) {
                                        context.startActivity(new Intent(context, Act_TeamFeedback_Question.class)
                                                .putExtra("userId", mFilteredList.get(position).getFromUserId())
                                                .putExtra("date", mFilteredList.get(position).getmCreatedAt())
                                                .putExtra("teamId", mFilteredList.get(position).getmTeamFeedbackId())
                                                .putExtra("backHandel", "notiBack"));
                                    } else if (mFilteredList.get(position).getNotificationType().equals("kudoschamp")) {
                                        if (mFilteredList.get(position).getMultiple()) {
                                            dialog_Multipule_Champion(mFilteredList.get(position));
                                        } else {
                                            dialog_champion(mFilteredList.get(position));
                                        }
                                    } else if (mFilteredList.get(position).getNotificationType().equals("custom notification")) {
                                        // Toast.makeText(context, "custom check", Toast.LENGTH_SHORT).show();
                                        ShowDiscDialog(mFilteredList.get(position));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_evaluate_yourself_against_tribe_value))) {
                                        context.startActivity(new Intent(context, Act_DOT_Details.class).putExtra("path", "noti"));
                                        editor = context.getSharedPreferences("Checklist", Context.MODE_PRIVATE).edit();
                                        editor.putString("checklist", "checklist");
                                        editor.apply();
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_complete_personality_Type_Questionnaire))) {
                                        context.startActivity(new Intent(context, Act_Personality_type_list.class).putExtra("checklist", "checklist"));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_Complete_Team_Role_Questionnaire))) {
                                        context.startActivity(new Intent(context, Cot_New_Question.class).putExtra("checklist", "checklist"));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_Complete_Culture_Structure_Questionnaire))) {
                                        context.startActivity(new Intent(context, Act_SOT_Questionlist.class).putExtra("checklist", "checklist"));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_Complete_Motivation_Questionnaire))) {
                                        context.startActivity(new Intent(context, Act_SOT_Motivation_Question.class).putExtra("checklist", "checklist"));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_Complete_Tribeometer_Survey))) {
                                        context.startActivity(new Intent(context, Act_Tribeometer_list.class).putExtra("checklist", "checklist"));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_Complete_Diagnostic_Survey))) {
                                        context.startActivity(new Intent(context, Act_Diagnostics_list.class).putExtra("checklist", "checklist"));
                                    }

                                    //to do list
                                    else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_AwardGreenBubblestoyourcolleaguesDaily))) {
                                        context.startActivity(new Intent(context, Act_Home.class).putExtra("checklist", "noti"));
                                        editor = context.getSharedPreferences("Checklist", Context.MODE_PRIVATE).edit();
                                        editor.putString("checklist", "checklist");
                                        editor.apply();
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_your_values_Monthly))) {
                                        editor = context.getSharedPreferences("Checklist", Context.MODE_PRIVATE).edit();
                                        editor.putString("checklist", "checklist");
                                        editor.apply();
                                        context.startActivity(new Intent(context, Act_DOT_Details.class).putExtra("checklist", "noti"));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_Personality_Type_Questionnaire))) {
                                        context.startActivity(new Intent(context, Act_Update_Personality_list.class).putExtra("checklist", ""));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_TeamRoleQuestionnaire))) {
                                        context.startActivity(new Intent(context, Update_Cot_Question.class).putExtra("checklist", "checklist"));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_CultureStructureQuestionnaire))) {
                                        context.startActivity(new Intent(context, Act_SOT_Update_Questionlist.class).putExtra("checklist", "checklist")); //it need checklist to finish
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_MotivationQuestionnaire))) {
                                        context.startActivity(new Intent(context, Act_SOT_Update_Motivation_Question.class).putExtra("checklist", "checklist"));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_TribeometerSurvey))) {
                                        context.startActivity(new Intent(context, Act_Update_Tribeometer_list.class).putExtra("checklist", "checklist"));
                                    } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_DiagnosticSurvey))) {
                                        context.startActivity(new Intent(context, Act_Update_Diagnostics_list.class).putExtra("checklist", "checklist"));
                                    } else {
                                        mListener.KudosClick();
                                    }
                                }
                            }
                        });
                        holder.ll_mainBlock.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mFilteredList.get(position).getNotificationType().equals("actionList")) {
                                    context.startActivity(new Intent(context, Act_ActionComment.class)
                                            .putExtra("actionId", mFilteredList.get(position).getmId())
                                            .putExtra("backHandel", "notiBack"));
                                    return;
                                } else {
                                    api_notificationList(mFilteredList.get(position).getmId());
                                }
                                if (mFilteredList.get(position).getNotificationType().equals("chat")) {
                                    context.startActivity(new Intent(context, Act_HistoryDetail.class)
                                            .putExtra("backHandel", "notiBack")
                                            .putExtra("changeItId", mFilteredList.get(position).getmFeedbackId()));
                                } else if (mFilteredList.get(position).getNotificationType().equals("support")) {
                                    context.startActivity(new Intent(context, Act_SupportListDetails.class)
                                            .putExtra("changeItId", mFilteredList.get(position).getmSupportId()));
                                } else if (mFilteredList.get(position).getNotificationType().equals("reflectionChat")) {
                                    context.startActivity(new Intent(context, Act_Ref_HistoryDetail.class)
                                            .putExtra("changeItId", mFilteredList.get(position).getmReflectionId())
                                            .putExtra("backHandel", "notiBack"));

                                } else if (mFilteredList.get(position).getNotificationType().equals("teamFeedback")) {
                                    context.startActivity(new Intent(context, Act_TeamFeedback_Question.class)
                                            .putExtra("userId", mFilteredList.get(position).getFromUserId())
                                            .putExtra("date", mFilteredList.get(position).getmCreatedAt())
                                            .putExtra("teamId", mFilteredList.get(position).getmTeamFeedbackId())
                                            .putExtra("backHandel", "notiBack"));
                                } else if (mFilteredList.get(position).getNotificationType().equals("kudoschamp")) {
                                    if (mFilteredList.get(position).getMultiple()) {
                                        dialog_Multipule_Champion(mFilteredList.get(position));
                                    } else {
                                        dialog_champion(mFilteredList.get(position));
                                    }
                                } else if (mFilteredList.get(position).getNotificationType().equals("custom notification")) {
                                    // Toast.makeText(context, "custom check", Toast.LENGTH_SHORT).show();
                                    ShowDiscDialog(mFilteredList.get(position));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_evaluate_yourself_against_tribe_value))) {
                                    context.startActivity(new Intent(context, Act_DOT_Details.class).putExtra("path", "noti"));
                                    editor = context.getSharedPreferences("Checklist", Context.MODE_PRIVATE).edit();
                                    editor.putString("checklist", "checklist");
                                    editor.apply();
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_complete_personality_Type_Questionnaire))) {
                                    context.startActivity(new Intent(context, Act_Personality_type_list.class).putExtra("checklist", "checklist"));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_Complete_Team_Role_Questionnaire))) {
                                    context.startActivity(new Intent(context, Cot_New_Question.class).putExtra("checklist", "checklist"));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_Complete_Culture_Structure_Questionnaire))) {
                                    context.startActivity(new Intent(context, Act_SOT_Questionlist.class).putExtra("checklist", "checklist"));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_Complete_Motivation_Questionnaire))) {
                                    context.startActivity(new Intent(context, Act_SOT_Motivation_Question.class).putExtra("checklist", "checklist"));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_Complete_Tribeometer_Survey))) {
                                    context.startActivity(new Intent(context, Act_Tribeometer_list.class).putExtra("checklist", "checklist"));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.checklist_Complete_Diagnostic_Survey))) {
                                    context.startActivity(new Intent(context, Act_Diagnostics_list.class).putExtra("checklist", "checklist"));
                                }

                                //to do list
                                else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_AwardGreenBubblestoyourcolleaguesDaily))) {
                                    context.startActivity(new Intent(context, Act_Home.class).putExtra("checklist", "noti"));
                                    editor = context.getSharedPreferences("Checklist", Context.MODE_PRIVATE).edit();
                                    editor.putString("checklist", "checklist");
                                    editor.apply();
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_your_values_Monthly))) {
                                    editor = context.getSharedPreferences("Checklist", Context.MODE_PRIVATE).edit();
                                    editor.putString("checklist", "checklist");
                                    editor.apply();
                                    context.startActivity(new Intent(context, Act_DOT_Details.class).putExtra("checklist", "noti"));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_Personality_Type_Questionnaire))) {
                                    context.startActivity(new Intent(context, Act_Update_Personality_list.class).putExtra("checklist", ""));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_TeamRoleQuestionnaire))) {
                                    context.startActivity(new Intent(context, Update_Cot_Question.class).putExtra("checklist", "checklist"));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_CultureStructureQuestionnaire))) {
                                    context.startActivity(new Intent(context, Act_SOT_Update_Questionlist.class).putExtra("checklist", "checklist")); //it need checklist to finish
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_MotivationQuestionnaire))) {
                                    context.startActivity(new Intent(context, Act_SOT_Update_Motivation_Question.class).putExtra("checklist", "checklist"));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_TribeometerSurvey))) {
                                    context.startActivity(new Intent(context, Act_Update_Tribeometer_list.class).putExtra("checklist", "checklist"));
                                } else if (mFilteredList.get(position).getDescription().equals(context.getString(R.string.todolist_Review_DiagnosticSurvey))) {
                                    context.startActivity(new Intent(context, Act_Update_Diagnostics_list.class).putExtra("checklist", "checklist"));
                                } else {

                                    mListener.KudosClick();
                                }
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            break;
            case LOADING:
                LoadingVH loadingVH= (LoadingVH) mainHolder;
                try {
                if (retryPageLoad){
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);
                    loadingVH.mErrorTxt.setText(errorMsg !=null ? errorMsg : context.getString(R.string.error_msg_unknown));
                }else{
                        if (mFilteredList.get(mFilteredList.size() - 1).getTitle() == null) {
                            loadingVH.mErrorLayout.setVisibility(View.GONE);
                            loadingVH.mProgressBar.setVisibility(View.GONE);
                        } else {
                            loadingVH.mErrorLayout.setVisibility(View.GONE);
                            loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                        }
                                        }
                      }catch (Exception e){
                        e.printStackTrace();
                    }
                break;
        }
    }

    public void add(ModelNotificationList r){
        mFilteredList.add(r);
        notifyItemInserted(mFilteredList.size()-1);
    }
    public void addAll(List<ModelNotificationList> noti_result){
        for (ModelNotificationList result: noti_result){
            add(result);
        }
    }
    public void remove(ModelNotificationList r){
        int position =mFilteredList.indexOf(r);
        if (position>-1){
            mFilteredList.remove(position);
            notifyItemRemoved(position);
        }
    }
    @Override
    public int getItemCount() {
        try {
           // return mFilteredList.size();
            return mFilteredList == null ? 0 : mFilteredList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void addLoadingFooter(){
        isLoadingAdded=true;
        add(new ModelNotificationList());
    }
    public void removeLoadingFooter(){
        isLoadingAdded=false;
        int position =mFilteredList.size()-1;
        ModelNotificationList result=getItem(position);
        if (result!=null){
            mFilteredList.remove(position);
            notifyItemRemoved(position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return (position== mFilteredList.size() -1 && isLoadingAdded)? LOADING :ITEM;
        //return super.getItemViewType(position);
    }
    public ModelNotificationList getItem(int position){
        return mFilteredList.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg){
        retryPageLoad = show;
        notifyItemChanged(mFilteredList.size()-1);
        if (errorMsg !=null) this.errorMsg =errorMsg;
    }

    public void api_notificationList(String id) {
        //hideErrorView();
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(context, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(context, message);
            }
        });
        //JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.companyOrgId
        JsonObject object = Functions.getClient().getJsonMapObject(
                "notificationId", id
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.changeNotificationStatus);
    }

    public void dialog_champion(ModelNotificationList NotiList) {
        final Dialog dialog = new Dialog(context, R.style.cust_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_champion);


        final TextView tv_des = dialog.findViewById(R.id.dia_des);
        final TextView tv_user_msg = dialog.findViewById(R.id.dia_user_name);
        final TextView tv_user_email = dialog.findViewById(R.id.dia_user_email);
        final ImageView img_user_image = dialog.findViewById(R.id.dia_user_image);
        final LinearLayout img_dia_ll = dialog.findViewById(R.id.dia_Cancle);
        final Button valueButton = dialog.findViewById(R.id.dia_value_button);
        tv_user_msg.setText(NotiList.getmUserName());
        tv_user_email.setText(NotiList.getmUserEmail());
        valueButton.setText(NotiList.getDescription());
        Picasso.get().load(NotiList.getmUserImage()).into(img_user_image);

        img_dia_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

    private void dialog_Multipule_Champion(ModelNotificationList notiList) {
        Ad_Multi_Champions cham_adapter;
        List<ModelNotificationList> mylist = new ArrayList<>();
        List<String> selectedValueLsit = new ArrayList<>();

        try {
            String userName = notiList.getmUserName();
            String userEmail = notiList.getmUserEmail();
            String userImage = notiList.getmUserImage();
            if (!userName.isEmpty()) {
                String[] splitValue = userName.split(",");
                String[] splitEmail = userEmail.split(",");
                String[] splitImage = userImage.split(",");
                for (int i = 0; i < splitValue.length; i++) {
                    selectedValueLsit.add(splitValue[i]);
                    mylist.get(i).setmUserName(splitValue[i]);
                    mylist.get(i).setmUserEmail(splitEmail[i]);
                    mylist.get(i).setmUserEmail(splitImage[i]);
                    mylist.get(i).setDescription(notiList.getDescription());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Dialog dialog = new Dialog(context, R.style.cust_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_champion_multipule);
        //adapter
        cham_adapter = new Ad_Multi_Champions(context, R.layout.dailog_component, mylist);
        ListView list = dialog.findViewById(R.id.dai_list_champion);
        list.setAdapter(cham_adapter);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();


    }

    private void ShowDiscDialog(final ModelNotificationList NotiList) {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_descfull);

        final TextView tv_notiName = dialog.findViewById(R.id.tv_dia_notiName);
        final TextView tv_desc = dialog.findViewById(R.id.tv_dia_desc);
        final TextView tv_date = dialog.findViewById(R.id.tv_dia_date);
        final LinearLayout date_dia_ll = dialog.findViewById(R.id.ll_dia_date);
        final Button valueButton = dialog.findViewById(R.id.link_dia_button);
        final ImageView diaIcon = dialog.findViewById(R.id.iv_dia_logo);
        final ImageView tribe = dialog.findViewById(R.id.tribe365);
        tv_notiName.setText(NotiList.getTitle());
        tv_desc.setText(NotiList.getDescription());
        tv_date.setText(utility.getDate(NotiList.getmCreatedAt()));

        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).placeholder(R.drawable.icon_tribe365).into(tribe);
            }
        }
        try {
            if (!NotiList.getmLastMessage().equals("")) {
                valueButton.setVisibility(View.VISIBLE);
            } else {
                valueButton.setVisibility(View.GONE);
            }
            valueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // dialog.dismiss();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NotiList.getmLastMessage()));
                    context.startActivity(browserIntent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_notiName, tv_desc, tv_date;
        ImageView iv_logo;
        TextView link_button;
        private final SwipeLayout swipe_layout;
        private final LinearLayout ll_mainBlock;
        private LinearLayout ll_date;
        private final LinearLayout des_ll;
        TextView tv_date_title,tv_date_action,tv_title_by;
        LinearLayout date_ll,action_ll;

        public ViewHolder(View v) {
            super(v);
            tv_notiName = v.findViewById(R.id.tv_notiName);
            tv_desc = v.findViewById(R.id.tv_desc);
            tv_date = v.findViewById(R.id.tv_date);
            link_button = v.findViewById(R.id.link_button);
            swipe_layout = v.findViewById(R.id.swipe_layout);
            ll_mainBlock = v.findViewById(R.id.ll_mainBlock);
            iv_logo = v.findViewById(R.id.iv_logo);
            des_ll = v.findViewById(R.id.des_ll);
            tv_date_title=v.findViewById(R.id.tv_date_title);
            tv_date_action=v.findViewById(R.id.tv_date_action);
            date_ll=v.findViewById(R.id.date_ll);
            action_ll=v.findViewById(R.id.action_ll);
            tv_title_by=v.findViewById(R.id.tv_title_by);
        }
    }

    protected class LoadingVH extends  ViewHolder implements View.OnClickListener{
        private final ProgressBar mProgressBar;
        private final ImageButton mRetryBtn;
        private final TextView mErrorTxt;
        private final LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);
            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:
                    showRetry(false, null);
                    mCallback.retryPageLoadUR();
                    break;
            }
        }
    }

    private class Ad_Multi_Champions extends ArrayAdapter<ModelNotificationList> {

        public Ad_Multi_Champions(Context context, int textViewResourceId, List<ModelNotificationList> objects) {
            super(context, textViewResourceId, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View curView = convertView;
            if (curView == null) {
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                curView = vi.inflate(R.layout.dailog_component, null);
            }

            ModelNotificationList cp = getItem(position);
            TextView userName = curView.findViewById(R.id.dia_user_name_cmp);
            TextView userEmail = curView.findViewById(R.id.dia_user_email_cmp);
            TextView userValue = curView.findViewById(R.id.dia_value_tv_cmp);


            userName.setText(cp.getmUserName());
            userEmail.setText(cp.getmUserEmail());
            userValue.setText(cp.getDescription());

            return curView;

        }
    }

    public interface kudosListener{
        void KudosClick();
    }
}
