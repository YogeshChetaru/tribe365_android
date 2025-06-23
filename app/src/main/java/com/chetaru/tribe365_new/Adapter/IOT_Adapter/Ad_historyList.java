package com.chetaru.tribe365_new.Adapter.IOT_Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelHistory;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Offloading.Act_Chat;
import com.chetaru.tribe365_new.UI.Offloading.Act_HistoryDetail;
import com.chetaru.tribe365_new.utility.PaginationAdapterCallback;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ad_historyList extends RecyclerView.Adapter<Ad_historyList.ViewHolder> {

    private static final int ITEM= 0;
    private static final int LOADING= 1;
    List<ModelHistory> list = new ArrayList<>();
    List<ModelHistory> mFilteredList;
    AlertDialog.Builder builder;
    Utility utility;
    SessionParam sessionParam;
    String jsonData = "";
    private Context context;
    private BaseRequest baseRequest;
    boolean statusFlag=true;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String errorMsg;
    private PaginationAdapterCallback mCallback;


    public Ad_historyList(List<ModelHistory> list, Context context, String json) {
        this.list = list;
        this.mFilteredList= list;
        notifyDataSetChanged();
        this.context = context;
        builder = new AlertDialog.Builder(context);
        utility = new Utility();
        sessionParam = new SessionParam(context);
        this.mCallback=(PaginationAdapterCallback) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder= null;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        switch (viewType){
            case ITEM:
                View viewItem= inflater.inflate(R.layout.row_history_offloading,parent,false);
                viewHolder= new ViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading= inflater.inflate(R.layout.item_progress,parent,false);
                viewHolder= new LoadingVH(viewLoading);
                break;

        }
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history_offloading, parent, false);
        return viewHolder;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        switch (getItemViewType(position)){
            case ITEM:
                 ViewHolder mholder=(ViewHolder) holder;
                 if (mFilteredList.get(position).getmId()>0) {

                     mholder.tv_location.setText(mFilteredList.get(position).getMsgDetail());
                     mholder.tv_date.setText(utility.getDate(mFilteredList.get(position).getMCreatedAt()));
                     mholder.tv_content.setText(mFilteredList.get(position).getmMessage());
                     try {
                         if (mFilteredList.get(position).getStatus().equals("Completed")) {
                             mholder.card_status_ll.setBackground(context.getResources().getDrawable(R.drawable.bg_green_light_card));
                             mholder.status_tv.setTextColor(context.getResources().getColor(R.color.green_dark_text));
                             //holder.status_tv.setText(list.get(position).getStatus());
                             mholder.status_tv.setText("Complete");
                         } else {
                             mholder.card_status_ll.setBackground(context.getResources().getDrawable(R.drawable.bg_yellow_light_card));
                             mholder.status_tv.setTextColor(context.getResources().getColor(R.color.yellow_dark_text));
                             // holder.status_tv.setText(list.get(position).getStatus());
                             mholder.status_tv.setText("Active");
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }


                     mholder.tv_message.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             //Uncomment the below code to Set the message and title from the strings.xml file
                             //builder.setMessage("Are you sure to resend") .setTitle(R.string.app_name);
                             //Setting message manually and performing action on button click
                             builder.setMessage("Are you sure to resend ?")
                                     .setCancelable(false)
                                     .setPositiveButton("Resend", new DialogInterface.OnClickListener() {
                                         public void onClick(DialogInterface dialog, int id) {
                                             apiResend(mFilteredList.get(position).getChangeitId());
                                             dialog.dismiss();
                                         }
                                     })
                                     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                         public void onClick(DialogInterface dialog, int id) {
                                             //Action for 'NO' Button
                                             dialog.cancel();
                                         }
                                     });
                             //Creating dialog box
                             AlertDialog alert = builder.create();
                             //Setting the title manually
                             alert.setTitle(R.string.app_name);
                             alert.show();
                         }
                     });

                     mholder.tv_sendQuery.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             context.startActivity(new Intent(context, Act_Chat.class)
                                     .putExtra("changeItId", mFilteredList.get(position).getmId() + ""));
                         }
                     });

                     mholder.ll_block.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             context.startActivity(new Intent(context, Act_HistoryDetail.class)
                                     .putExtra("index", position + "")
                                     .putExtra("changeItId", mFilteredList.get(position).getmId() + "")
                             );
                         }
                     });
                 }
            break;
            case  LOADING:
                         LoadingVH loadingVH= (LoadingVH) holder;
                            if (retryPageLoad){
                                loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                                loadingVH.mProgressBar.setVisibility(View.GONE);
                                loadingVH.mErrorTxt.setText( errorMsg != null? errorMsg : context.getString(R.string.error_msg_unknown));
                            }else {
                                if (mFilteredList.get(mFilteredList.size()-1).getmMessage()==null){
                                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                                    loadingVH.mProgressBar.setVisibility(View.GONE);
                                }else {
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
            return mFilteredList==null? 0 : mFilteredList.size();
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
                    ArrayList<ModelHistory> filteredList = new ArrayList<>();

                    for (ModelHistory addActionUser : list) {

                        if (addActionUser.getMCreatedAt().toLowerCase().contains(charString.toLowerCase()) ||
                                addActionUser.getMCreatedAt().toUpperCase().contains(charString.toUpperCase())) {
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
                mFilteredList = (ArrayList<ModelHistory>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }

    public void add(ModelHistory r) {
        mFilteredList.add(r);
        notifyItemInserted(mFilteredList.size() - 1);
    }

    public void addAll(List<ModelHistory> noti_Results) {
        for (ModelHistory result : noti_Results) {
            add(result);
        }
    }

    public void remove(ModelHistory r) {
        int position = mFilteredList.indexOf(r);
        if (position > -1) {
            mFilteredList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ModelHistory());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mFilteredList.size() - 1;
        ModelHistory result = getItem(position);

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

    public ModelHistory getItem(int position) {
        return mFilteredList.get(position);
    }

    public void apiResend(String changeitId) {
        utility = new Utility();
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                utility.showToast(context, "sent");

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(context, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(context, message);
            }
        });
        Map<String, String> map = new HashMap<>();
        map.put("changeit_id", changeitId);
        map.put("email_id_to", sessionParam.email);

        //baseRequest.callAPIGETCustomURLTellSid(1, map, "http://live.thechangeconsultancy.co/demo_tellsid/index.php/apitellsid/resendemailbyid/"); //test

        //demo---------------------------------------------
        baseRequest.callAPIGETCustomURLTellSid(1, map, "http://demo.thechangeconsultancy.co/tellsid/index.php/apitellsid/resendemailbyid/"); //live

        //live---------------------------------------------
//        baseRequest.callAPIGETCustomURLTellSid(1, map, "http://tellsid.softintelligence.co.uk/index.php/apitellsid/resendemailbyid/"); //live

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
            card_status_ll= v.findViewById(R.id.card_status_ll);
            status_tv= v.findViewById(R.id.status_tv);
            ll_block = v.findViewById(R.id.ll_block);
        }
    }
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mFilteredList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }
    public class LoadingVH extends ViewHolder implements View.OnClickListener{
        private final ProgressBar mProgressBar;
        private final ImageButton mRetryBtn;
        private final TextView mErrorTxt;
        private final LinearLayout mErrorLayout;

        public LoadingVH(View itemView){
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
}
