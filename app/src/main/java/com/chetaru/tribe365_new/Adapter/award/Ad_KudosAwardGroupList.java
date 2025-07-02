package com.chetaru.tribe365_new.Adapter.award;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.API.Models.KnowHome.KudosAwardList;
import com.chetaru.tribe365_new.API.Models.Kudos.GroupKudosList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.PaginationAdapterCallback;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class Ad_KudosAwardGroupList extends RecyclerView.Adapter<Ad_KudosAwardGroupList.ViewHolder> {

    private static final int ITEM= 0;
    private static final int LOADING= 1;
    Utility utility;
    SessionParam sessionParam;
    private Context mContext;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;
    private String userId;
    List<GroupKudosList> list;
    groupUsersListener mListener;

    public Ad_KudosAwardGroupList(List<GroupKudosList> groupList, Context context, groupUsersListener mListener) {
        this.list= groupList;
        this.mContext=context;
        utility= new Utility();
        sessionParam= new SessionParam(context);
        this.mListener= mListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.kudos_award_group_user_list,parent,false);
        return  new ViewHolder(view);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            holder.person_image.setVisibility(View.GONE);
            //listMap.get(123).get(position).getAwardDate();


                String awardValue="";
                try {
                    holder.desc_tv.setText(Html.fromHtml(list.get(position).getKudosAwardLists().get(0).getAwardDescription()));
                    String newDate = list.get(position).getKudosAwardLists().get(0).getAwardDate();
                     awardValue = list.get(position).getKudosAwardLists().get(0).getAwardValue();
                    holder.date_tv.setText(utility.getDate(newDate));
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (list.get(position).getKudosAwardLists().size()>0) {
                    holder.person_rv.setVisibility(View.GONE);
                    holder.person_image.setVisibility(View.VISIBLE);
                    holder.name_tv.setVisibility(View.VISIBLE);
                    holder.name_tv.setText(awardValue);
                    String name = list.get(position).getKudosAwardLists().get(0).getUserName();

                    if (list.get(position).getKudosAwardLists().size()>1) {
                        int count = list.get(position).getKudosAwardLists().size()-1;
                        holder.award_person_name_tv.setText(name +" & "+ count+ " more");
                    }else {
                        holder.award_person_name_tv.setText(name);
                        Glide.with(mContext)
                                .load(list.get(position).getKudosAwardLists().get(0).getUserImage())
                                .placeholder(R.drawable.user_circle)
                                .into(holder.person_image);
                    }

                    List<String> personList = new ArrayList<>();
                    Ad_AwardUserList adAwardUserList = new Ad_AwardUserList(awardValue, list.get(position).getKudosAwardLists(), personList, mContext);
                   /* GridLayoutManager gridLayoutManager= new GridLayoutManager(mContext,2);
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                    holder.person_rv.setLayoutManager(layoutManager);
                    holder.person_rv.hasFixedSize();

                    holder.person_rv.setNestedScrollingEnabled(false);
                    holder.person_rv.addItemDecoration(new ItemOffsetDecoration(mContext, R.dimen.item_offset_very_small));
                    adAwardUserList.setHasStableIds(true);
                    holder.person_rv.setAdapter(adAwardUserList);
                    holder.main_ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (list.get(position).getKudosAwardLists().size()>1) {
                                List<KudosAwardList> userList = list.get(position).getKudosAwardLists();
                                String awardValue = list.get(position).getKudosAwardLists().get(0).getAwardValue();
                                mListener.groupUser(awardValue, userList);
                            }
                        }
                    });

                }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return  list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView person_image;
        TextView name_tv,desc_tv,date_tv,award_person_name_tv;
        View bottomView;
        RecyclerView person_rv;
        LinearLayout main_ll;
        public ViewHolder(@NonNull View view) {
            super(view);
            person_image= view.findViewById(R.id.person_image);
            name_tv= view.findViewById(R.id.award_name_tv);
            award_person_name_tv=view.findViewById(R.id.award_person_name_tv);
            desc_tv= view.findViewById(R.id.award_desc_tv);
            date_tv= view.findViewById(R.id.award_date_tv);
            bottomView= view.findViewById(R.id.bottom_view);
            person_rv= view.findViewById(R.id.person_rv);
            main_ll= view.findViewById(R.id.main_ll);
        }
    }

    public interface groupUsersListener{
        public void groupUser(String awardValue,List<KudosAwardList> userList);
    }

}
