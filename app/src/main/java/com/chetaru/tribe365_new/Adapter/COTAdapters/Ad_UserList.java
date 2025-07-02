package com.chetaru.tribe365_new.Adapter.COTAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.apachat.swipereveallayout.core.ViewBinder;
import com.chetaru.tribe365_new.API.Models.ModelAddActionUser;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.UI.Know.COT.FunctionalLense.Act_CotTeamMemDetails;
import com.chetaru.tribe365_new.databinding.RowCotUserlistBinding;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class Ad_UserList extends RecyclerView.Adapter<Ad_UserList.ViewHolder> implements Filterable {

    private final ViewBinder binderHelper = new ViewBinder();
    List<ModelAddActionUser> list = new ArrayList<>();
    int index;
    Utility utility;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    private List<ModelAddActionUser> mFilteredList;
    private final Context context;

    public Ad_UserList(List<ModelAddActionUser> list, Context context) {
        this.list = list;
        this.mFilteredList = list;
        notifyDataSetChanged();
        this.context = context;
        utility = new Utility();
        sessionParam = new SessionParam(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        RowCotUserlistBinding binding=RowCotUserlistBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cot_userlist, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.itemBinding.tvName.setText(mFilteredList.get(position).getName());
        //holder.tv_department.setText(list.get(position).getDepartment());
        holder.itemBinding.tvEmail.setText(mFilteredList.get(position).getEmail());
        holder.itemBinding.llBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, Act_CotTeamMemDetails.class)
                        .putExtra("userId", mFilteredList.get(position).getId().toString())
                        .putExtra("orgId", mFilteredList.get(position).getOrgId().toString())
                        .putExtra("name", mFilteredList.get(position).getName()));

            }
        });
        // binderHelper.bind(holder.swipe_layout, position+"");

    }

    @Override
    public int getItemCount() {

        try {
            return mFilteredList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = list;
                } else {
                    ArrayList<ModelAddActionUser> filteredList = new ArrayList<>();

                    for (ModelAddActionUser addActionUser : list) {

                        if (addActionUser.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                addActionUser.getName().toUpperCase().contains(charString.toUpperCase())) {
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
                mFilteredList = (ArrayList<ModelAddActionUser>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_email;
        LinearLayout ll_block;
        RowCotUserlistBinding itemBinding;
        public ViewHolder(RowCotUserlistBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* tv_name = v.findViewById(R.id.tv_name);
            tv_email = v.findViewById(R.id.tv_email);
            ll_block = v.findViewById(R.id.ll_block);*/
        }
    }

}
