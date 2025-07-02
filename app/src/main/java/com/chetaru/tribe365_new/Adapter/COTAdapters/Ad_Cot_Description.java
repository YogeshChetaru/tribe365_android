package com.chetaru.tribe365_new.Adapter.COTAdapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.COTBeans.ModelCotDescription;
import com.chetaru.tribe365_new.databinding.ActCategoryDescriptionRowBinding;
import com.chetaru.tribe365_new.utility.Utility;

import java.util.List;

public class Ad_Cot_Description extends RecyclerView.Adapter<Ad_Cot_Description.ViewHolder> {

    List<ModelCotDescription> list;
    Utility utility;
    private Context context;


    public Ad_Cot_Description(List<ModelCotDescription> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
        utility = new Utility();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        ActCategoryDescriptionRowBinding binding=ActCategoryDescriptionRowBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.act_category_description_row, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //holder.tv_heading.setText(list.get(position).getHeading());
        holder.itemBinding.tvDescriptionTitle.setText(list.get(position).getTitle());
        holder.itemBinding.tvDescriptionValue.setText(list.get(position).getShortDescription());

        String sourceString = "<b>" + "Description : " + "</b> " + list.get(position).getLongDescription();
        String replaceString=sourceString.replaceAll("(\\r\\n|\\n)","<br />");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.itemBinding.tvDescription.setText(Html.fromHtml(replaceString,Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.itemBinding.tvDescription.setText(Html.fromHtml(replaceString));
        }
        //holder.tv_description.setText(Html.fromHtml(sourceString));
        // holder.tv_description.setText("Description: "+);
    }

    @Override
    public int getItemCount() {

        try {
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }/* tv_description_title = v.findViewById(R.id.tv_description_title);
            tv_description_value = v.findViewById(R.id.tv_description_value);
            tv_description = v.findViewById(R.id.tv_description);*/
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_description_title, tv_description_value, tv_description;
        ActCategoryDescriptionRowBinding itemBinding;
        public ViewHolder(ActCategoryDescriptionRowBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* tv_description_title = v.findViewById(R.id.tv_description_title);
            tv_description_value = v.findViewById(R.id.tv_description_value);
            tv_description = v.findViewById(R.id.tv_description);*/

        }
    }
}
