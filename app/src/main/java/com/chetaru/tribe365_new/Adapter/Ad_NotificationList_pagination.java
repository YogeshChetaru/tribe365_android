package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apachat.swipereveallayout.core.SwipeLayout;
import com.chetaru.tribe365_new.UI.Home.Act_Report;
import com.chetaru.tribe365_new.API.Models.ModelNotificationList;
import com.chetaru.tribe365_new.UI.Offloading.Act_Chat;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Offloading.Act_HistoryDetail;
import com.chetaru.tribe365_new.utility.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class Ad_NotificationList_pagination extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    List<ModelNotificationList> list = new ArrayList<>();
    private List<ModelNotificationList> mFilteredList;
    private final Context context;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;


//

    public Ad_NotificationList_pagination(RecyclerView recyclerView, ArrayList<ModelNotificationList> list_modelTeamCots, final Context context) {
        this.mFilteredList = list_modelTeamCots;
        this.context = context;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }


    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mFilteredList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_notification_list, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tv_notiName.setText(mFilteredList.get(position).getTitle());
            viewHolder.tv_desc.setText(mFilteredList.get(position).getDescription());
            viewHolder.tv_date.setText(mFilteredList.get(position).getCreatedAt());
            viewHolder.swipe_layout.setLockDrag(true);
            viewHolder.ll_mainBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFilteredList.get(position).getNotificationType().equals("chat")) {
                        context.startActivity(new Intent(context, Act_HistoryDetail.class)
                                .putExtra("backHandel","notiBack")
                                .putExtra("changeItId", mFilteredList.get(position).getmFeedbackId()));
                    } else {
                        context.startActivity(new Intent(context, Act_Report.class)
                                .putExtra("backHandel","notiBack")
                                .putExtra("date", mFilteredList.get(position).getCreatedAt()));
                    }
                }
            });
            if (mFilteredList.get(position).getNotificationType().equals("chat")) {
                viewHolder.iv_logo.setImageResource(R.drawable.ic_chat_hollow_blue);
            } else {
                viewHolder.iv_logo.setImageResource(R.drawable.ic_like_hollow_green);

            }
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }


    }

    @Override
    public int getItemCount() {

        return mFilteredList == null ? 0 : mFilteredList.size();
    }

    public void setLoaded() {
        isLoading = false;

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_notiName, tv_desc, tv_date;
        SwipeLayout swipe_layout;
        LinearLayout ll_mainBlock;
        ImageView iv_logo;

        public ViewHolder(View v) {
            super(v);
            tv_desc = v.findViewById(R.id.tv_desc);
            tv_notiName = v.findViewById(R.id.tv_notiName);
            tv_date = v.findViewById(R.id.tv_date);
            swipe_layout = v.findViewById(R.id.swipe_layout);
            ll_mainBlock = v.findViewById(R.id.ll_mainBlock);
            iv_logo = v.findViewById(R.id.iv_logo);

        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar1);
        }
    }
}
