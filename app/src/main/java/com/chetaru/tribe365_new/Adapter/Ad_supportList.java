package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelSupportHistory;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.CustomerSupport.Act_SupportChat;
import com.chetaru.tribe365_new.UI.CustomerSupport.Act_SupportListDetails;
import com.chetaru.tribe365_new.utility.PaginationAdapterCallback;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Ad_supportList extends RecyclerView.Adapter<Ad_supportList.ViewHolder> {

    private static final int ITEM= 0;
    private static final int LOADING= 1;
    List<ModelSupportHistory> list = new ArrayList<>();
    List<ModelSupportHistory> mFilteredList;
    AlertDialog.Builder builder;
    Utility utility;
    SessionParam sessionParam;
    String jsonData = "";
    private Context mContext;
    private BaseRequest baseRequest;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String errorMsg;
    private PaginationAdapterCallback mCallback;


    public Ad_supportList(List<ModelSupportHistory> list, Context mContext, String json) {
        this.list = list;
        this.mFilteredList= list;
        this.mContext = mContext;
        notifyDataSetChanged();
        this.jsonData = json;
        builder = new AlertDialog.Builder(mContext);
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder= null;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        switch (viewType){
            case ITEM:
                View viewItem=inflater.inflate(R.layout.row_history,parent,false);
                viewHolder= new ViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading=inflater.inflate(R.layout.item_progress,parent,false);
                viewHolder= new LoadingVH(viewLoading);
                break;
        }
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history, parent, false);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                    // holder.tv_location.setText(list.get(position).getMessagesList());
                ViewHolder mHolder= (ViewHolder) holder;
                if (mFilteredList.get(position).getId()>0) {
                    try {
                        mHolder.tv_date.setText(utility.getDate(mFilteredList.get(position).getCreatedAt()));
                        mHolder.tv_content.setText(mFilteredList.get(position).getMessage());
                        if (!mFilteredList.get(position).getImage().equals("")) {
                            Picasso.get().load(mFilteredList.get(position).getImage()).placeholder(R.drawable.noimage_big).into(holder.iv_image); //live
                        } else {
                            mHolder.iv_image.setImageResource(R.drawable.noimage_big);
                        }
                        mHolder.tv_sendQuery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mContext.startActivity(new Intent(mContext, Act_SupportChat.class)
                                        .putExtra("changeItId", mFilteredList.get(position).getId() + ""));
                            }
                        });

                        mHolder.ll_block.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mContext.startActivity(new Intent(mContext, Act_SupportListDetails.class)
                                        .putExtra("index", position + "")
                                        .putExtra("changeItId", mFilteredList.get(position).getId() + "")
                                );
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (mFilteredList.get(position).getStatus().equals("Completed")) {
                            mHolder.card_status_ll.setBackground(mContext.getResources().getDrawable(R.drawable.bg_green_light_card));
                            mHolder.status_tv.setTextColor(mContext.getResources().getColor(R.color.green_dark_text));
                            //holder.status_tv.setText(list.get(position).getStatus());

                            mHolder.status_tv.setText("Complete");
                        } else {
                            mHolder.card_status_ll.setBackground(mContext.getResources().getDrawable(R.drawable.bg_yellow_light_card));
                            mHolder.status_tv.setTextColor(mContext.getResources().getColor(R.color.yellow_dark_text));
                            //holder.status_tv.setText(list.get(position).getStatus());
                            mHolder.status_tv.setText("Active");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            break;
            case LOADING:
                        LoadingVH loadingVH= (LoadingVH) holder;
                            if (retryPageLoad){
                                loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                                loadingVH.mProgressBar.setVisibility(View.GONE);
                                loadingVH.mErrorTxt.setText( errorMsg !=null? errorMsg : mContext.getString(R.string.error_msg_unknown));
                            }else {
                                if (mFilteredList.get(mFilteredList.size()-1).getMessage()==null){
                                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                                    loadingVH.mProgressBar.setVisibility(View.GONE);
                                }
                                else {
                                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                                }
                            }
                break;
        }
    }

    @Override
    public int getItemCount() {
        try {
            return mFilteredList== null ?  0: mFilteredList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = list;
                } else {
                    ArrayList<ModelSupportHistory> filteredList = new ArrayList<>();

                    for (ModelSupportHistory addActionUser : list) {

                        if (addActionUser.getMessage().toLowerCase().contains(charString.toLowerCase()) ||
                                addActionUser.getMessage().toUpperCase().contains(charString.toUpperCase())) {
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
                mFilteredList = (ArrayList<ModelSupportHistory>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }

    public void add(ModelSupportHistory r) {
        mFilteredList.add(r);
        notifyItemInserted(mFilteredList.size() - 1);
    }

    public void addAll(List<ModelSupportHistory> noti_Results) {
        for (ModelSupportHistory result : noti_Results) {
            add(result);
        }
    }

    public void remove(ModelSupportHistory r) {
        int position = mFilteredList.indexOf(r);
        if (position > -1) {
            mFilteredList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ModelSupportHistory());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mFilteredList.size() - 1;
        ModelSupportHistory result = getItem(position);

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

    public ModelSupportHistory getItem(int position) {
        return mFilteredList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_location, tv_date, tv_content, tv_message, tv_sendQuery;
        ImageView iv_image;
        LinearLayout ll_block;
        LinearLayout card_status_ll;
        TextView status_tv;
        public ViewHolder(View v) {
            super(v);
            tv_location = v.findViewById(R.id.tv_location);
            iv_image = v.findViewById(R.id.iv_image);
            tv_date = v.findViewById(R.id.tv_date);
            tv_content = v.findViewById(R.id.tv_content);
            tv_message = v.findViewById(R.id.tv_message);
            tv_sendQuery = v.findViewById(R.id.tv_sendQuery);
            ll_block = v.findViewById(R.id.ll_block);
            card_status_ll= v.findViewById(R.id.card_status_ll);
            status_tv= v.findViewById(R.id.status_tv);
        }
    }
    public class LoadingVH extends ViewHolder implements View.OnClickListener{
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
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:
                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }
    }
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mFilteredList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


}
