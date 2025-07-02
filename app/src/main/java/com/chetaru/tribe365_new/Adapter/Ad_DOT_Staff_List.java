package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.apachat.swipereveallayout.core.ViewBinder;
import com.chetaru.tribe365_new.API.Models.ModelAddActionUser;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.databinding.RowCotUserlistBinding;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ad_DOT_Staff_List extends RecyclerView.Adapter<Ad_DOT_Staff_List.ViewHolder> implements Filterable {

    private final ViewBinder binderHelper = new ViewBinder();
    List<ModelAddActionUser> list = new ArrayList<>();
    int index;
    Utility utility;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    String toUserId = "", dotId = "", dotBeliefId = "", dotValueId = "", dotValueNameId = "", bubbleFlag = "1", dotvalueName = "";
    String upVotes, downVotes;
    ImageView iv_image_like, iv_image_unlike, iv_close;
    TextView tv_staffname, tv_upVote_rating, tv_downVote_rating, tv_staff_valuename;
    FloatingActionButton fb_select_staff;
    Dialog mDialog;
    ArrayList<String> li_selectedStaff = new ArrayList<>();
    int count = 0;
    private List<ModelAddActionUser> mFilteredList;
    private final Context context;

    public Ad_DOT_Staff_List(List<ModelAddActionUser> list, Context context, String dotId, String dotBeliefId, String dotValueId, String dotValueNameId, String dotvalueName, FloatingActionButton fb_select_staff) {
        this.list = list;
        this.mFilteredList = list;
        notifyDataSetChanged();
        this.context = context;
        utility = new Utility();
        sessionParam = new SessionParam(context);
        this.dotId = dotId;
        this.dotBeliefId = dotBeliefId;
        this.dotValueId = dotValueId;
        this.dotValueNameId = dotValueNameId;
        this.dotvalueName = dotvalueName;
        this.fb_select_staff = fb_select_staff;

    }

    public Ad_DOT_Staff_List(List<ModelAddActionUser> list, Context context, String dotId, String dotBeliefId, String dotValueId, String dotValueNameId, String dotvalueName) {
        this.list = list;
        this.mFilteredList = list;
        notifyDataSetChanged();
        this.context = context;
        utility = new Utility();
        sessionParam = new SessionParam(context);
        this.dotId = dotId;
        this.dotBeliefId = dotBeliefId;
        this.dotValueId = dotValueId;
        this.dotValueNameId = dotValueNameId;
        this.dotvalueName = dotvalueName;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        RowCotUserlistBinding binding= RowCotUserlistBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cot_userlist, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.itemBinding.tvName.setText(mFilteredList.get(position).getName());
        //holder.tv_department.setText(list.get(position).getDepartment());
        holder.itemBinding.tvEmail.setText(mFilteredList.get(position).getEmail());
        toUserId = mFilteredList.get(position).getId().toString();
        if (mFilteredList.get(position).isSelected()) {
            holder.itemBinding.llBlock.setBackground(context.getResources().getDrawable(R.drawable.rect_solid_green_black_border));
        } else {
            holder.itemBinding.llBlock.setBackground(context.getResources().getDrawable(R.drawable.rect_solid_black_border));

        }
        holder.itemBinding.llBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "onclick", Toast.LENGTH_SHORT).show();

                if (!mFilteredList.get(position).isSelected()) {
                    mFilteredList.get(position).setSelected(true);
                    holder.itemBinding.llBlock.setBackground(context.getResources().getDrawable(R.drawable.rect_solid_green_black_border));
                    count++;
                } else {
                    mFilteredList.get(position).setSelected(false);
                    holder.itemBinding.llBlock.setBackground(context.getResources().getDrawable(R.drawable.rect_solid_black_border));
                    count--;
                }

                if (count != 0) {
                    fb_select_staff.setVisibility(View.VISIBLE);
                } else if (count == 0) {
                    fb_select_staff.setVisibility(View.GONE);
                }


//                for(int i=0;i<=li_selectedStaff.size();i++) {
//                    try {
//
//
//                        if (li_selectedStaff.size() == 0) {
//                            li_selectedStaff.add(mFilteredList.get(position).getId().toString());
//                            break;
//                        }
////                        if (li_selectedStaff.get(i).equals(mFilteredList.get(position).getId().toString())) {
////                            li_selectedStaff.remove(i);
////                        }
//                        if (!li_selectedStaff.get(i).equals(mFilteredList.get(position).getId().toString())) {
//                            li_selectedStaff.add(mFilteredList.get(position).getId().toString());
//                        }
//
//
//                    }catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }

//                }
            }
        });


//        holder.ll_block.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String stff_id = "", staff_name = "";
//                staff_name = mFilteredList.get(position).getName().toString();
//                stff_id = mFilteredList.get(position).getId().toString();
//                dialog(context, staff_name, stff_id);
//            }
//        });
        fb_select_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                li_selectedStaff.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelected()) {
                        li_selectedStaff.add(list.get(i).getId().toString());
                    }

                }
                api_getAddBubble();

            }
        });
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

    public void api_getAddBubble() {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {


                    //         mDialog.cancel();
                    try {
                        String msg = "";
                        JSONObject obj = new JSONObject(Json);
                        msg = obj.optString("message");
                        utility.showToast(context, msg);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    JSONArray jsonArray = new JSONArray(object.toString());
                    ((Activity) context).finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(context, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(context, message);
            }
        });
        //JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.companyOrgId

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < li_selectedStaff.size(); i++) {
            jsonArray.add(li_selectedStaff.get(i));
        }
        jsonObject.add("toUserId", jsonArray);
        jsonObject.addProperty("dotId", dotId);
        jsonObject.addProperty("dotBeliefId", dotBeliefId);
        jsonObject.addProperty("dotValueId", dotValueId);
        jsonObject.addProperty("dotValueNameId", dotValueNameId);
        jsonObject.addProperty("bubbleFlag", bubbleFlag);//hard code pass value 1
//        JsonObject object = Functions.getClient().getJsonMapObject(jsonObject
//        );
        baseRequest.callAPIPost(1, jsonObject, ConstantAPI.api_addbubbleRatings );
    }

    public void dialog(Context context, String stff_name, String staff_id) {
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.bubble_dialog);
        mDialog.setCancelable(false);
        api_set_BubbleRating(staff_id);
        tv_staffname = mDialog.findViewById(R.id.tv_staffname);
        iv_image_like = mDialog.findViewById(R.id.iv_image_like);
        iv_image_unlike = mDialog.findViewById(R.id.iv_image_unlike);
        iv_close = mDialog.findViewById(R.id.iv_close);
        tv_upVote_rating = mDialog.findViewById(R.id.tv_upVote_rating);
        tv_downVote_rating = mDialog.findViewById(R.id.tv_downVote_rating);
        tv_staff_valuename = mDialog.findViewById(R.id.tv_staff_valuename);
        tv_staffname.setText(stff_name);
        tv_staff_valuename.setText(dotvalueName);
        toUserId = staff_id;
        mDialog.show();
        iv_image_like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bubbleFlag = "0";
                api_getAddBubble();
            }
        });
        iv_image_unlike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bubbleFlag = "1";
                api_getAddBubble();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });
    }

    public void api_set_BubbleRating(String staff_id) {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = (JSONObject) object;

                    upVotes = jsonObject.optString("upVotes");
                    downVotes = jsonObject.optString("downVotes");
                    tv_upVote_rating.setText(upVotes);
                    tv_downVote_rating.setText(downVotes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(context, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(context, message);
            }
        });


        //JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.companyOrgId
        JsonObject object = Functions.getClient().getJsonMapObject(
                "toUserId", staff_id,
                "dotBeliefId", dotBeliefId,
                "dotValueNameId", dotValueNameId

        );
        baseRequest.callAPIPost(1, object,ConstantAPI.api_getBubbleFromUserRating);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_email;
        LinearLayout ll_block;
        RowCotUserlistBinding itemBinding;
        public ViewHolder(RowCotUserlistBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
          /*  tv_name = v.findViewById(R.id.tv_name);
            tv_email = v.findViewById(R.id.tv_email);
            ll_block = v.findViewById(R.id.ll_block);*/
            this.setIsRecyclable(false);
        }
    }


}
