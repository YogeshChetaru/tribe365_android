package com.chetaru.tribe365_new.Adapter.award;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.AwardList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.utility.PaginationAdapterCallback;
import com.chetaru.tribe365_new.utility.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Ad_AwardDescriptionList extends RecyclerView.Adapter<Ad_AwardDescriptionList.ViewHolder> {
    private static final int ITEM=0;
    private static final int LOADING= 1;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String errorMsg;
    List<AwardList> list;
    List<AwardList> mFilteredList;
    Context context;
    Utility utility;
    private final PaginationAdapterCallback mCallback;

    public Ad_AwardDescriptionList(List<AwardList> list, Context context) {
        this.list = list;
        this.mFilteredList=list;
        this.context = context;
        utility= new Utility();
        this.mCallback = (PaginationAdapterCallback) context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.description_list,parent,false);
        ViewHolder viewHolder= null;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        switch (viewType){
            case ITEM:
                View viewItem=inflater.inflate(R.layout.description_list,parent,false);
                viewHolder= new ViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading=inflater.inflate(R.layout.item_progress,parent,false);
                viewHolder= new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ITEM:
                final ViewHolder mHolder= holder;
                try {
                    String desc= mFilteredList.get(position).getDescription().trim();
                    String dateString= mFilteredList.get(position).getCreatedAt();
                    mHolder.tv_description.setText(desc);
                    mHolder.text_date.setText(utility.getDate(dateString));
                    if (position==(mFilteredList.size()-2)){
                        mHolder.line_divider.setVisibility(View.GONE);
                    }else{
                        mHolder.line_divider.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case LOADING:
                LoadingVH loadingVH= (LoadingVH) holder;
                try {
                    if (retryPageLoad) {
                        loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                        loadingVH.mProgressBar.setVisibility(View.GONE);

                        loadingVH.mErrorTxt.setText(
                                errorMsg != null ?
                                        errorMsg :
                                        context.getString(R.string.error_msg_unknown));
                    } else {
                        if (mFilteredList.get(mFilteredList.size() - 1).getCreatedAt() == null) {
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


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_description,text_date;
        View line_divider;
        public ViewHolder(View view){
            super(view);
            tv_description=view.findViewById(R.id.text_description);
            text_date=view.findViewById(R.id.text_date);
            line_divider= view.findViewById(R.id.line_divider_view);
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
                    ArrayList<AwardList> filteredList = new ArrayList<>();

                    for (AwardList addActionUser : list) {

                        if (addActionUser.getDescription().toLowerCase().contains(charString.toLowerCase()) ||
                                addActionUser.getDescription().toUpperCase().contains(charString.toUpperCase())) {
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
                mFilteredList = (ArrayList<AwardList>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }

    public void add(AwardList r) {
        try {
            mFilteredList.add(r);
            notifyItemInserted(mFilteredList.size() - 1);
        }catch (Exception e){e.printStackTrace();}

    }

    public void addAll(List<AwardList> noti_Results) {
        for (AwardList result : noti_Results) {
            add(result);
        }
    }

    public void remove(AwardList r) {
        int position = mFilteredList.indexOf(r);
        if (position > -1) {
            mFilteredList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new AwardList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mFilteredList.size() - 1;
        AwardList result = getItem(position);

        if (result != null) {
            mFilteredList.remove(position);
            notifyItemRemoved(position);
        }
    }
    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        return (position == mFilteredList.size()-1 && isLoadingAdded)? LOADING : ITEM;
    }

    public AwardList getItem(int position) {
        return mFilteredList.get(position);
    }

    @Override
    public int getItemCount() {
        try{
           return mFilteredList==null ? 0: mFilteredList.size();
        }catch (Exception e) {
            return 0;
        }
    }



    protected class LoadingVH extends ViewHolder implements View.OnClickListener{
        private final ProgressBar mProgressBar;
        private final TextView mErrorTxt;
        private final LinearLayout mErrorLayout;

        public LoadingVH(@NonNull View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            ImageButton mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mErrorLayout.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:
                    showRetry(false,null);
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
