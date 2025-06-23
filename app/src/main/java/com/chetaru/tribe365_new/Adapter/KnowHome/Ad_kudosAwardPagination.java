package com.chetaru.tribe365_new.Adapter.KnowHome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
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

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.KnowHome.KudosAwardList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.utility.PaginationAdapterCallback;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ad_kudosAwardPagination extends RecyclerView.Adapter<Ad_kudosAwardPagination.ViewHolder> {


    private static final int ITEM= 0;
    private static final int LOADING= 1;
    List<KudosAwardList> list= new ArrayList<>();
    List<KudosAwardList> mFilteredList= new ArrayList<>();
    Utility utility;
    SessionParam sessionParam;
    private  Context mContext;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;
    private String userId;
    Map<String, List<KudosAwardList>> listMap;

    public Ad_kudosAwardPagination(Context context){
        this.list= new ArrayList<>();
        this.mFilteredList= list;
        this.mCallback=(PaginationAdapterCallback) context;
        this.mContext= context;
        sessionParam= new SessionParam(context);
        utility= new Utility();
    }

    public Ad_kudosAwardPagination(String userId,List<KudosAwardList> kudosAwardList, Context context) {
        this.list= kudosAwardList;
        this.mFilteredList= kudosAwardList;
        this.mContext= context;
        sessionParam= new SessionParam(context);
        utility= new Utility();
        this.userId= userId;
        notifyDataSetChanged();
    }

    public Ad_kudosAwardPagination(Map<String, List<KudosAwardList>> map, String userId, List<KudosAwardList> kudosAwardList, Context context) {
        this.list= kudosAwardList;
        this.mFilteredList= kudosAwardList;
        this.mContext= context;
        sessionParam= new SessionParam(context);
        utility= new Utility();
        this.userId= userId;
        this.listMap= map;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFilteredList== null ? 0 : mFilteredList.size();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected android.widget.Filter.FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = list;
                } else {
                    ArrayList<KudosAwardList> filteredList = new ArrayList<>();

                    for (KudosAwardList addActionUser : list) {

                        if (addActionUser.getUserName().toLowerCase().contains(charString.toLowerCase()) ||
                                addActionUser.getUserName().toUpperCase().contains(charString.toUpperCase())) {
                            filteredList.add(addActionUser);
                        }
                    }
                    mFilteredList = filteredList;

                }

                android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {
                mFilteredList = (ArrayList<KudosAwardList>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }

    public void add(KudosAwardList r) {
        mFilteredList.add(r);
        notifyItemInserted(mFilteredList.size() - 1);
    }

    public void addAll(List<KudosAwardList> noti_Results) {
        for (KudosAwardList result : noti_Results) {
            add(result);
        }
    }

    public void remove(KudosAwardList r) {
        int position = mFilteredList.indexOf(r);
        if (position > -1) {
            mFilteredList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new KudosAwardList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mFilteredList.size() - 1;
        KudosAwardList result = getItem(position);

        if (result != null) {
            mFilteredList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private KudosAwardList getItem(int position) {
        return mFilteredList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewHolder viewHolder= null;
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        switch (viewType){
            case ITEM:
                View viewItem= inflater.inflate(R.layout.kudos_award_des_list,parent,false);
                viewHolder=new ViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading= inflater.inflate(R.layout.item_progress,parent,false);
                viewHolder= new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ITEM:
                try {
                    final ViewHolder viewHolder = holder;

                    if (!userId.equals("")) {
                        try {
                            viewHolder.person_image.setVisibility(View.VISIBLE);
                            viewHolder.desc_tv.setText(Html.fromHtml(mFilteredList.get(position).getAwardDescription()));
                            viewHolder.name_tv.setText(mFilteredList.get(position).getAwardValue() );
                            viewHolder.award_person_name_tv.setText(mFilteredList.get(position).getUserName());
                            //viewHolder.name_tv.setText(mFilteredList.get(position).getAwardValue() );
                            Glide.with(mContext)
                                    .load(mFilteredList.get(position).getUserImage())
                                    .placeholder(R.drawable.user_circle)
                                    .into(viewHolder.person_image);
                            String newDate = mFilteredList.get(position).getAwardDate();
                            viewHolder.date_tv.setText(utility.getDate(newDate));
                            try {
                                if (position == mFilteredList.size() - 1) {
                                    viewHolder.bottomView.setVisibility(View.GONE);
                                } else {
                                    viewHolder.bottomView.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        viewHolder.person_image.setVisibility(View.GONE);

                        viewHolder.desc_tv.setText(Html.fromHtml(mFilteredList.get(position).getAwardDescription()));
                        String newDate = mFilteredList.get(position).getAwardDate();
                        viewHolder.date_tv.setText(utility.getDate(newDate));

                        String desc=mFilteredList.get(position).getAwardDescription();
                        List<KudosAwardList> userList= listMap.get(desc);
                        List<String> personList = new ArrayList<>();
                        if (userList.size()>0) {
                           for (int k=0; k< userList.size();k++) {
                               personList.add(userList.get(k).getUserName());
                           }
                        }
                       /* String awardValue = mFilteredList.get(position).getAwardValue();
                        Ad_AwardUserList adAwardUserList = new Ad_AwardUserList(awardValue, list, personList, mContext);
                        holder.person_rv.hasFixedSize();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        holder.person_rv.setLayoutManager(layoutManager);
                        holder.person_rv.addItemDecoration(new ItemOffsetDecoration(mContext, R.dimen.item_offset_small));
                        holder.person_rv.setAdapter(adAwardUserList);*/
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            case LOADING:
                final LoadingVH loadingVH= (LoadingVH) holder;
                if (retryPageLoad){
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);
                    loadingVH.mErrorTxt.setText(errorMsg != null? errorMsg: mContext.getString(R.string.error_msg_unknown));
                }
                else {
                    if (mFilteredList.get(mFilteredList.size() - 1).getUserName() == null) {
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
    private String getResponseString(InputStream stream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.ISO_8859_1));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        finally {
            stream.close();
        }
        return sb.toString();
    }

    @Override
    public int getItemViewType(int position) {
        return (position== mFilteredList.size()-1 && isLoadingAdded)? LOADING : ITEM;
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mFilteredList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView person_image;
        TextView name_tv,desc_tv,date_tv,award_person_name_tv;
        View bottomView;
        public ViewHolder(@NonNull View view) {
            super(view);
            person_image= view.findViewById(R.id.person_image);
            name_tv= view.findViewById(R.id.award_name_tv);
            desc_tv= view.findViewById(R.id.award_desc_tv);
            date_tv= view.findViewById(R.id.award_date_tv);
            bottomView= view.findViewById(R.id.bottom_view);

            award_person_name_tv= view.findViewById(R.id.award_person_name_tv);

        }
    }
    public  class LoadingVH extends ViewHolder implements View.OnClickListener {
        private final ProgressBar mProgressBar;
        private final ImageButton mRetryBtn;
        private final TextView mErrorTxt;
        private final LinearLayout mErrorLayout;

        public LoadingVH(@NonNull View itemView) {
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
   
}
