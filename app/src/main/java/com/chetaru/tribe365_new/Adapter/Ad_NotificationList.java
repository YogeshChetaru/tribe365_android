package com.chetaru.tribe365_new.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.apachat.swipereveallayout.core.SwipeLayout;
import com.chetaru.tribe365_new.API.Models.ModelNotificationList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.CustomerSupport.Act_SupportListDetails;
import com.chetaru.tribe365_new.UI.Offloading.Act_HistoryDetail;
import com.chetaru.tribe365_new.UI.Offloading.Act_Ref_HistoryDetail;
import com.chetaru.tribe365_new.UI.UserInfo.Act_TeamFeedback_Question;
import com.chetaru.tribe365_new.utility.PaginationAdapterCallback;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Ad_NotificationList extends RecyclerView.Adapter<Ad_NotificationList.ViewHolder> {

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    List<ModelNotificationList> list = new ArrayList<>();
    Utility utility;
    SessionParam sessionParam;
    private List<ModelNotificationList> mFilteredList;
    private final Context context;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String errorMsg;
    private PaginationAdapterCallback mCallback;
    public kudosClickListener mListener;

    public Ad_NotificationList(List<ModelNotificationList> list, Context context,kudosClickListener mListener) {
        this.list = list;
        this.mFilteredList = list;
        notifyDataSetChanged();
        this.context = context;
        utility = new Utility();
        this.mListener=mListener;
        sessionParam = new SessionParam(context);
    }

    public Ad_NotificationList(Context context) {
        this.list = new ArrayList<>();
        this.mFilteredList = list;
        this.mCallback = (PaginationAdapterCallback) context;
        this.context = context;
        sessionParam = new SessionParam(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification_list, parent, false);
        //        return new ViewHolder(view);

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.row_notification_list, parent, false);
                viewHolder = new ViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {

            case ITEM:
                try {
                    ModelNotificationList modelNotificationList = mFilteredList.get(position); //data

                    if (modelNotificationList.getDescription()!=null) {
                        final ViewHolder mholder = holder;


                        try {
                            mholder.tv_notiName.setText(mFilteredList.get(position).getTitle());
                            mholder.tv_desc.setText(mFilteredList.get(position).getDescription());
                            // mholder.tv_date.setText(mFilteredList.get(position).getCreatedAt());
                            //mholder.tv_date.setText(mFilteredList.get(position).getCreatedAt());
                            // mholder.tv_date.setText(mFilteredList.get(position).getCreatedAt());
                            if (mFilteredList.get(position).getNotificationType().equals("teamFeedback")){
                                holder.tv_date.setText(utility.getFeedbackDate(mFilteredList.get(position).getCreatedAt()));
                            }else {
                                holder.tv_date.setText(utility.getDate(mFilteredList.get(position).getCreatedAt()));
                            }
                            mholder.swipe_layout.setLockDrag(true);

                            if (!list.get(position).getDescription().equals("")) {
                                holder.des_ll.setVisibility(View.VISIBLE);
                            } else {
                                holder.des_ll.setVisibility(View.GONE);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            if (list.get(position).getmLastMessage() != null && !mFilteredList.get(position).getmLastMessage().equals("")) {
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
                        }
                        try {
                            mholder.tv_desc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mFilteredList.get(position).getNotificationType().equals("custom notification")) {
                                        ShowDiscDialog(mFilteredList.get(position));
                                    } else {
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
                                                    .putExtra("date", mFilteredList.get(position).getCreatedAt())
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
                                        } else {
                                /*context.startActivity(new Intent(context, Act_Report.class)
                                        .putExtra("backHandel","notiBack")
                                        .putExtra("date", mFilteredList.get(position).getCreatedAt()));*/
                                /*context.startActivity(new Intent(context, Act_Home.class)
                                        .putExtra("kudosClick",true)
                                        .putExtra("handelClick","notiBack"));*/
                                            mListener.kudosClick();
                                        }
                                    }
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            mholder.ll_mainBlock.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
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
                                    } else {
                           /* context.startActivity(new Intent(context, Act_Report.class)
                                    .putExtra("backHandel","notiBack")
                                    .putExtra("date", mFilteredList.get(position).getCreatedAt()));*/
                            /*context.startActivity(new Intent(context, Act_Home.class)
                                    .putExtra("kudosClick",true)
                                    .putExtra("backHandel","notiBack")
                                );*/
                                        mListener.kudosClick();
                                    }
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                                    if (mFilteredList.get(position).getNotificationType().equals("chat")) {
                                        holder.iv_logo.setImageResource(R.drawable.ic_chat_hollow_blue);
                                    } else if (mFilteredList.get(position).getNotificationType().contains("support")) {
                                        holder.iv_logo.setImageResource(R.drawable.ic_chat_hollow_blue);
                                    } else if (mFilteredList.get(position).getNotificationType().equals("reflectionChat")) {
                                        holder.iv_logo.setImageResource(R.drawable.ic_chat_hollow_blue);
                                    }else if (mFilteredList.get(position).getNotificationType().equals("kudoAward")){
                                        holder.iv_logo.setImageResource(R.drawable.awards___kudos_orange);
                                    }else if (mFilteredList.get(position).getTitle().contains("Kudos Champion")) {
                                        holder.iv_logo.setImageResource(R.drawable.cup);
                                        holder.tv_desc.setText(mFilteredList.get(position).getmUserName());
                                    } else if (mFilteredList.get(position).getNotificationType().contains("custom notification")) {
                                        holder.iv_logo.setImageResource(R.drawable.direct);
                                        holder.tv_desc.setText(mFilteredList.get(position).getDescription());
                                    }else if(mFilteredList.get(position).getNotificationType().contains("teamFeedback")) {
                                        holder.iv_logo.setImageResource(R.drawable.team_feedback);
                                    } else {
                                        holder.iv_logo.setImageResource(R.drawable.ic_like_hollow_green);
                                        }
                                } catch (NullPointerException npe) {

                                }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);
                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));
                } else {
                    if (mFilteredList.get(mFilteredList.size() - 1).getTitle() == null) {
                        loadingVH.mErrorLayout.setVisibility(View.GONE);
                        loadingVH.mProgressBar.setVisibility(View.GONE);
                    } else {
                        loadingVH.mErrorLayout.setVisibility(View.GONE);
                        loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }


    }

    private void ShowDiscDialog(final ModelNotificationList NotiList) {
        final Dialog dialog = new Dialog(context);
        //final Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_descfull);

        //dialog.setCanceledOnTouchOutside(true);

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
        // Picasso.get().load(NotiList.getmUserImage()).into(diaIcon);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    @Override
    public int getItemCount() {

        return mFilteredList == null ? 0 : mFilteredList.size();
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = list;
                } else {
                    ArrayList<ModelNotificationList> filteredList = new ArrayList<>();

                    for (ModelNotificationList addActionUser : list) {

                        if (addActionUser.getTitle().toLowerCase().contains(charString.toLowerCase()) ||
                                addActionUser.getTitle().toUpperCase().contains(charString.toUpperCase())) {
                            filteredList.add(addActionUser);
                        }
                    }
                    mFilteredList = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ModelNotificationList>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }

    public void add(ModelNotificationList r) {
        mFilteredList.add(r);
        notifyItemInserted(mFilteredList.size() - 1);
    }

    public void addAll(List<ModelNotificationList> noti_Results) {
        for (ModelNotificationList result : noti_Results) {
            add(result);
        }
    }

    public void remove(ModelNotificationList r) {
        int position = mFilteredList.indexOf(r);
        if (position > -1) {
            mFilteredList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ModelNotificationList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mFilteredList.size() - 1;
        ModelNotificationList result = getItem(position);

        if (result != null) {
            mFilteredList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mFilteredList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
//        if(mFilteredList.size()<5){
//            return mFilteredList.size() - 1;
//        }else{
//            return (position == mFilteredList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
//        }
    }

    public ModelNotificationList getItem(int position) {
        return mFilteredList.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mFilteredList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public void dialog_champion(ModelNotificationList NotiList) {

        //clearSelection();
        //AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.cust_dialog));


        final Dialog dialog = new Dialog(context, R.style.cust_dialog);
        //final Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_champion);

        //dialog.setCanceledOnTouchOutside(true);

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
       /* valueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
       /* WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/

        //window.setBackgroundDrawableResource(R.color.red);
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
        List<String> selecteduserName = new ArrayList<>();
        List<String> selecteduserEmail = new ArrayList<>();
        List<String> selecteduserImage = new ArrayList<>();

        try {
            String userName = notiList.getmUserName();
            String userEmail = notiList.getmUserEmail();
            String userImage = notiList.getmUserImage();
            if (!userName.isEmpty()) {
                String[] splitValue = userName.split(",");
                String[] splitEmail = userEmail.split(",");
                String[] splitImage = userImage.split(",");
                for (int i = 0; i < splitValue.length; i++) {
                    selecteduserName.add(splitValue[i]);
                    selecteduserEmail.add(splitEmail[i]);
                    selecteduserImage.add(splitImage[i]);

                }
            }
            for (int j = 0; j < selecteduserName.size(); j++) {
                ModelNotificationList notificationList = new ModelNotificationList();
                notificationList.setmUserName(selecteduserName.get(j));
                notificationList.setmUserEmail(selecteduserEmail.get(j));
                notificationList.setmUserImage(selecteduserImage.get(j));
                notificationList.setDescription(notiList.getDescription());
                mylist.add(notificationList);
            }
           /* for (int k=0;k<10;k++){
                ModelNotificationList data=new ModelNotificationList();

                if (k<2){
                    data.setmUserName(selecteduserName.get(k));
                    data.setmUserEmail(selecteduserEmail.get(k));
                    data.setmUserImage(selecteduserImage.get(1));
                    data.setDescription(notiList.getDescription());
                }else{
                    data.setmUserName("Ritesh");
                    data.setmUserEmail("Ritesh@chetaru.com");
                    data.setmUserImage(selecteduserImage.get(1));
                    data.setDescription(notiList.getDescription());
                }
                mylist.add(data);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
       /* String userImage=notiList.getmUserName();
        if(!userImage.isEmpty()){
            String splitValue[] = userImage.split(",");
            for(int i=0;i<splitValue.length;i++){
                selectedValueLsit.add(splitValue[i]);
                mylist.setmUserImage(splitValue[i]);
            }
        }*/

        final Dialog dialog = new Dialog(context, R.style.cust_dialog);
        //final Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_champion_multipule);
        final LinearLayout img_dia_ll = dialog.findViewById(R.id.dia_Cancle);
        img_dia_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_notiName, tv_desc, tv_date;
        SwipeLayout swipe_layout;
        LinearLayout ll_mainBlock, des_ll, ll_date;
        ImageView iv_logo;
        TextView link_button;

        public ViewHolder(View v) {
            super(v);
            tv_desc = v.findViewById(R.id.tv_desc);
            tv_notiName = v.findViewById(R.id.tv_notiName);
            link_button = v.findViewById(R.id.link_button);
            tv_date = v.findViewById(R.id.tv_date);
            swipe_layout = v.findViewById(R.id.swipe_layout);
            ll_mainBlock = v.findViewById(R.id.ll_mainBlock);
            iv_logo = v.findViewById(R.id.iv_logo);
            des_ll = v.findViewById(R.id.des_ll);

        }
    }

    protected class LoadingVH extends ViewHolder implements View.OnClickListener {
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
                    mCallback.retryPageLoad();
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
            ImageView userImage = curView.findViewById(R.id.dia_user_image_cmp);

            userName.setText(cp.getmUserName());
            userEmail.setText(cp.getmUserEmail());
            userValue.setText(cp.getDescription());
            Picasso.get().load(cp.getmUserImage()).into(userImage);
            return curView;

        }
    }

     public interface kudosClickListener{
        void kudosClick();
     }

}
