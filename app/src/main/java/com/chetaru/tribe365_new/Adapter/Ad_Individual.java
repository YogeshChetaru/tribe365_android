package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.User;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.RowUserListBinding;

import java.util.ArrayList;
import java.util.List;

public class Ad_Individual extends RecyclerView.Adapter<Ad_Individual.ViewHolder> {


    List<User> list;
    List<User> filteredUserList;
    Context context;
    OnItemClickCustom onItemClickCustom;
    Boolean clickableFlag=false;
    public Ad_Individual(boolean clickableFlag,List<User> list, Context context, OnItemClickCustom onItemClickCustom) {
        this.list = list;
        this.clickableFlag=clickableFlag;
        filteredUserList = list;
        notifyDataSetChanged();
        this.context = context;
        this.onItemClickCustom = onItemClickCustom;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        RowUserListBinding binding=RowUserListBinding.inflate(inflater,parent,false);
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_list,parent ,false);
        return new ViewHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.setIsRecyclable(false);
            holder.itemBinding.titleNameIndividual.setText(filteredUserList.get(position).getName());
            if (filteredUserList.get(position).isSelected()) {
                //holder.radioButton.setTextColor(context.getResources().getColor(R.color.red));
                holder.itemBinding.titleNameIndividual.setTextColor(context.getResources().getColor(R.color.red));
                //holder.checkButton.setBackground(context.getResources().getDrawable(R.drawable.circle_checked));
                holder.itemBinding.imageOption.setImageResource(R.drawable.circle_checked);

                //list.get(position).setSelected(false);
            } else {
                holder.itemBinding.titleNameIndividual.setTextColor(context.getResources().getColor(R.color.black));
                //holder.checkButton.setBackground(context.getResources().getDrawable(R.drawable.circle_unchecked));
                holder.itemBinding.imageOption.setImageResource(R.drawable.circle_unchecked);
                //holder.radioButton.setBackgroundColor(Color.WHITE);
            }
            holder.itemBinding.imageOption.setOnClickListener(v -> {
                if (clickableFlag) {
                    if (filteredUserList.get(position).isSelected()) {
                        holder.itemBinding.titleNameIndividual.setTextColor(context.getResources().getColor(R.color.black));
                        //holder.radioButton.setBackgroundColor(Color.WHITE);
                        // holder.checkButton.setBackground(context.getResources().getDrawable(R.drawable.circle_unchecked));
                        holder.itemBinding.imageOption.setImageResource(R.drawable.circle_unchecked);
                        filteredUserList.get(position).setSelected(false);
                    } else {
                        filteredUserList.get(position).setSelected(true);
                        ///holder.radioButton.setBackgroundColor(Color.RED);
                        holder.itemBinding.titleNameIndividual.setTextColor(context.getResources().getColor(R.color.red));
                        //holder.checkButton.setBackground(context.getResources().getDrawable(R.drawable.circle_checked));
                        holder.itemBinding.imageOption.setImageResource(R.drawable.circle_checked);
               /* if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.your_drawable));
                } else {
                    view.setBackground(ContextCompat.getDrawable(this, R.drawable.your_drawable));
                }*/
                    }
                    onItemClickCustom.onClick(R.id.tv_value, position, filteredUserList.get(position));
                }
            });
            holder.itemBinding.mainLl.setOnClickListener(v -> {
                try {
                    if (clickableFlag) {
                        if (filteredUserList.get(position).isSelected()) {
                            holder.itemBinding.titleNameIndividual.setTextColor(context.getResources().getColor(R.color.black));
                            //holder.radioButton.setBackgroundColor(Color.WHITE);
                            //holder.checkButton.setBackground(context.getResources().getDrawable(R.drawable.circle_unchecked));
                            holder.itemBinding.imageOption.setImageResource(R.drawable.circle_unchecked);
                            filteredUserList.get(position).setSelected(false);
                        } else {
                            filteredUserList.get(position).setSelected(true);
                            ///holder.radioButton.setBackgroundColor(Color.RED);
                            holder.itemBinding.titleNameIndividual.setTextColor(context.getResources().getColor(R.color.red));
                            //holder.checkButton.setBackground(context.getResources().getDrawable(R.drawable.circle_checked));
                            holder.itemBinding.imageOption.setImageResource(R.drawable.circle_checked);
                        }
                        onItemClickCustom.onClick(R.id.tv_value, position, filteredUserList.get(position));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        try {
            return filteredUserList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_main;
        TextView tv_name;
        ImageView checkButton;
        RowUserListBinding itemBinding;
        public ViewHolder(RowUserListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*ll_main=v.findViewById(R.id.main_ll);
            tv_name=v.findViewById(R.id.title_name_individual);
            checkButton=v.findViewById(R.id.image_option);*/
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().trim();
                if (charString.isEmpty()) {
                    filteredUserList = list;
                } else {
                    ArrayList<User> userList = new ArrayList<>();
                    for (User department : list) {
                        if (department.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                department.getName().toUpperCase().contains(charString.toUpperCase())) {
                            userList.add(department);
                        }
                    }
                    filteredUserList = userList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
