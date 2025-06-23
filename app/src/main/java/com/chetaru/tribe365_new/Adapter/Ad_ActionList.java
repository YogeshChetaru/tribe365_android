package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.apachat.swipereveallayout.core.SwipeLayout;
import com.apachat.swipereveallayout.core.ViewBinder;
import com.chetaru.tribe365_new.API.Models.ModelACtionList;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.UI.Home.Actions.Act_ActionComment;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Home.Actions.Act_Edit_Action;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.databinding.RowActionlistBinding;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

public class Ad_ActionList extends RecyclerView.Adapter<Ad_ActionList.ViewHolder> {

    private final ViewBinder binderHelper = new ViewBinder();

    ArrayList<ModelACtionList> list = new ArrayList<>();
    Utility utility;
    SessionParam sessionParam;
    int index;
    String finalstatus = "";
    ViewHolder globalholder;
    private final Context context;
    private BaseRequest baseRequest;

    public Ad_ActionList(ArrayList<ModelACtionList> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
        sessionParam = new SessionParam(context);
        utility = new Utility();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        RowActionlistBinding binding= RowActionlistBinding.inflate(inflater,parent,false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_actionlist, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        globalholder = holder;
        binderHelper.bind(holder.itemBinding.swipeLayout,position+"");
        //holder.itemBinding.swipeLayout.close(true);
        holder.itemBinding.tvName.setText(list.get(position).getName());
        holder.itemBinding.tvDescription.setText(list.get(position).getDescription());
        holder.itemBinding.tvStartdate.setText(list.get(position).getStartedDate());
        holder.itemBinding.tvDuedate.setText(list.get(position).getDueDate());
        holder.itemBinding.tvTier.setText(list.get(position).getTier());
        holder.itemBinding.tvResponsiblePerson.setText(list.get(position).getResponsibleName());
       // holder.itemBinding.swipeLayout.setLockDrag(false);
        //binderHelper.restoreStates(inState);


        try {
            if (list.get(position).getLinkedActionOffloads()!=null) {
                holder.itemBinding.tvLinkedOffloads.setText(list.get(position).getLinkedActionOffloads() + "");
                holder.itemBinding.llOffloads.setVisibility(View.VISIBLE);
            }else {
                holder.itemBinding.llOffloads.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
            holder.itemBinding.llOffloads.setVisibility(View.GONE);
        }

        /*binderHelper.bind(holder.swipe_layout, position + "");
        holder.swipe_layout.close(true);
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_description.setText(list.get(position).getDescription());
        holder.tv_startdate.setText(list.get(position).getStartedDate());
        holder.tv_duedate.setText(list.get(position).getDueDate());
        holder.tv_tier.setText(list.get(position).getTier());
        holder.tv_responsiblePerson.setText(list.get(position).getResponsibleName());
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (sessionParam.id.equals(list.get(position).getUserId())) {
                holder.swipe_layout.setLockDrag(false);
            } else {
                holder.swipe_layout.setLockDrag(true);
            }
        }*/

        if (list.get(position).getTier().equalsIgnoreCase("primary")) {
           // holder.tv_tier.setBackgroundColor(context.getResources().getColor(R.color.action_primary));
            holder.itemBinding.tvTier.setBackgroundColor(context.getResources().getColor(R.color.action_primary));
        } else if (list.get(position).getTier().equalsIgnoreCase("secondary")) {
            //holder.tv_tier.setBackgroundColor(context.getResources().getColor(R.color.action_secondary));
            holder.itemBinding.tvTier.setBackgroundColor(context.getResources().getColor(R.color.action_secondary));
        } else {
           // holder.tv_tier.setBackgroundColor(context.getResources().getColor(R.color.action_tertiary));
            holder.itemBinding.tvTier.setBackgroundColor(context.getResources().getColor(R.color.action_tertiary));
        }

        if (list.get(position).getTier().equalsIgnoreCase("department") ||
                list.get(position).getTier().equalsIgnoreCase("office") ||
                list.get(position).getTier().equalsIgnoreCase("individual")) {
           // holder.tv_tier.setVisibility(View.GONE);
            holder.itemBinding.tvTier.setVisibility(View.GONE);
        } else {
            // holder.tv_tier.setVisibility(View.VISIBLE);
        }

        if (sessionParam.id.equalsIgnoreCase(list.get(position).getUserId())) {
            //holder.iv_edit.setVisibility(View.VISIBLE);
            holder.itemBinding.ivEdit.setVisibility(View.VISIBLE);
        } else {
            //holder.iv_edit.setVisibility(View.GONE);
            holder.itemBinding.ivEdit.setVisibility(View.GONE);
        }
        if (list.get(position).getOrgStatus().equalsIgnoreCase("STARTED")) {
            holder.itemBinding.ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.started));
            holder.itemBinding.tvStarted.setBackgroundColor(context.getResources().getColor(R.color.segmentblock));
            holder.itemBinding.tvStarted.setTextColor(context.getResources().getColor(R.color.white));
            holder.itemBinding.tvComplete.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.itemBinding.tvComplete.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.itemBinding.tvNotstarted.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.itemBinding.tvNotstarted.setTextColor(context.getResources().getColor(R.color.textcolor));

            if (holder.itemBinding.llStatus.isShown()) {
                holder.itemBinding.llStatus.setVisibility(View.GONE);
            }
        }
        if (list.get(position).getOrgStatus().equalsIgnoreCase("COMPLETED")) {
            holder.itemBinding.ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.complete));
            holder.itemBinding.tvComplete.setBackgroundColor(context.getResources().getColor(R.color.segmentblock));
            holder.itemBinding.tvComplete.setTextColor(context.getResources().getColor(R.color.white));

            holder.itemBinding.tvStarted.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.itemBinding.tvStarted.setTextColor(context.getResources().getColor(R.color.textcolor));
            holder.itemBinding.tvNotstarted.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.itemBinding.tvNotstarted.setTextColor(context.getResources().getColor(R.color.textcolor));

            if (holder.itemBinding.llStatus.isShown()) {
                holder.itemBinding.llStatus.setVisibility(View.GONE);
            }
        }
        if (list.get(position).getOrgStatus().equalsIgnoreCase("NOT STARTED")) {
            holder.itemBinding.ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.notstarted));
            holder.itemBinding.tvNotstarted.setBackgroundColor(context.getResources().getColor(R.color.segmentblock));
            holder.itemBinding.tvNotstarted.setTextColor(context.getResources().getColor(R.color.white));

            holder.itemBinding.tvComplete.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.itemBinding.tvComplete.setTextColor(context.getResources().getColor(R.color.textcolor));

            holder.itemBinding.tvStarted.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.itemBinding.tvStarted.setTextColor(context.getResources().getColor(R.color.textcolor));

            if (holder.itemBinding.llStatus.isShown()) {
                holder.itemBinding.llStatus.setVisibility(View.GONE);
            }
        }

        holder.itemBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemBinding.llStatus.setVisibility(View.GONE);
                holder.itemBinding.ivClose.setVisibility(View.GONE);
                holder.itemBinding.ivStatus.setVisibility(View.GONE);
                holder.itemBinding.ivEdit.setVisibility(View.GONE);
               /* holder.ll_status.setVisibility(View.GONE);
                holder.iv_close.setVisibility(View.GONE);
                holder.iv_status.setVisibility(View.VISIBLE);
                holder.iv_edit.setVisibility(View.VISIBLE);*/
            }
        });
        holder.itemBinding.ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Act_ActionComment.class)
                        .putExtra("name", list.get(position).getName())
                        .putExtra("description", list.get(position).getDescription())
                        .putExtra("startdate", list.get(position).getStartedDate())
                        .putExtra("duedate", list.get(position).getDueDate())
                        .putExtra("status", list.get(position).getOrgStatus())
                        .putExtra("actionId", list.get(position).getId())
                        .putExtra("respons_person", list.get(position).getResponsibleName())
                        .putExtra("offloads", list.get(position).getLinkedActionOffloads() + "")
                        .putExtra("theme", holder.itemBinding.tvThemes.getText().toString()));
            }
        });

        holder.itemBinding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemBinding.ivStatus.setVisibility(View.GONE);
                holder.itemBinding.ivEdit.setVisibility(View.GONE);
                holder.itemBinding.llStatus.setVisibility(View.VISIBLE);
                holder.itemBinding.ivClose.setVisibility(View.VISIBLE);

               /* holder.iv_status.setVisibility(View.GONE);
                holder.iv_edit.setVisibility(View.GONE);
                holder.ll_status.setVisibility(View.VISIBLE);
                holder.iv_close.setVisibility(View.VISIBLE);*/
            }
        });

        holder.itemBinding.ivEditScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ModelACtionList modelACtionList = new ModelACtionList();

                modelACtionList.setCurrentStatus(list.get(position).getCurrentStatus());
                modelACtionList.setOrgStatus(list.get(position).getOrgStatus());
                modelACtionList.setDescription(list.get(position).getDescription());
                modelACtionList.setDueDate(list.get(position).getDueDate());
                modelACtionList.setId(list.get(position).getId());
                modelACtionList.setName(list.get(position).getName());
                modelACtionList.setOffDeptId(list.get(position).getOffDeptId());
                modelACtionList.setOffDeptName(list.get(position).getOffDeptName());
                modelACtionList.setOrgId(list.get(position).getOrgId());
                modelACtionList.setResponsibleName(list.get(position).getResponsibleName());
                modelACtionList.setResponsibleUserId(list.get(position).getResponsibleUserId());
                modelACtionList.setStartedDate(list.get(position).getStartedDate());
                modelACtionList.setTier(list.get(position).getTier());
                modelACtionList.setTierId(list.get(position).getTierId());
                modelACtionList.setUserId(list.get(position).getUserId());
                modelACtionList.setmThemes(list.get(position).getmThemes());
                context.startActivity(new Intent(context, Act_Edit_Action.class)
                        .putExtra("model", modelACtionList));

            }
        });

        holder.itemBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //deleteAction( list.get(position).getId()+"",position);
                dialog_delete_value(list.get(position).getId(), position);
            }
        });

        holder.itemBinding.tvStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                if (!list.get(position).getOrgStatus().equalsIgnoreCase(holder.itemBinding.tvStarted.getText().toString())) {
                    holder.itemBinding.tvStarted.setBackgroundColor(context.getResources().getColor(R.color.segmentblock));
                    holder.itemBinding.tvStarted.setTextColor(context.getResources().getColor(R.color.white));

                    holder.itemBinding.tvComplete.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.itemBinding.tvComplete.setTextColor(context.getResources().getColor(R.color.textcolor));

                    holder.itemBinding.tvNotstarted.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.itemBinding.tvNotstarted.setTextColor(context.getResources().getColor(R.color.textcolor));

                    holder.itemBinding.ivStatus.setVisibility(View.VISIBLE);
                    holder.itemBinding.ivEdit.setVisibility(View.VISIBLE);
                    holder.itemBinding.llStatus.setVisibility(View.GONE);
                    index = position;
                    apiUpdateStatus(1);

                }
            }
        });
        holder.itemBinding.tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                if (!list.get(position).getOrgStatus().equalsIgnoreCase(holder.itemBinding.tvComplete.getText().toString() + "d")) {
                    holder.itemBinding.tvStarted.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.itemBinding.tvStarted.setTextColor(context.getResources().getColor(R.color.textcolor));

                    holder.itemBinding.tvComplete.setBackgroundColor(context.getResources().getColor(R.color.segmentblock));
                    holder.itemBinding.tvComplete.setTextColor(context.getResources().getColor(R.color.white));

                    holder.itemBinding.tvNotstarted.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.itemBinding.tvNotstarted.setTextColor(context.getResources().getColor(R.color.textcolor));

                    holder.itemBinding.ivStatus.setVisibility(View.VISIBLE);
                    holder.itemBinding.ivEdit.setVisibility(View.VISIBLE);
                    holder.itemBinding.llStatus.setVisibility(View.GONE);
                    index = position;
                    apiUpdateStatus(2);
                }
            }
        });
        holder.itemBinding.tvNotstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!list.get(position).getOrgStatus().equalsIgnoreCase(holder.itemBinding.tvNotstarted.getText().toString())) {
                    holder.itemBinding.tvStarted.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.itemBinding.tvStarted.setTextColor(context.getResources().getColor(R.color.textcolor));

                    holder.itemBinding.tvComplete.setBackgroundColor(context.getResources().getColor(R.color.white));
                    holder.itemBinding.tvComplete.setTextColor(context.getResources().getColor(R.color.textcolor));

                    holder.itemBinding.tvNotstarted.setBackgroundColor(context.getResources().getColor(R.color.segmentblock));
                    holder.itemBinding.tvNotstarted.setTextColor(context.getResources().getColor(R.color.white));

                    holder.itemBinding.ivStatus.setVisibility(View.VISIBLE);
                    holder.itemBinding.ivEdit.setVisibility(View.VISIBLE);
                    holder.itemBinding.llStatus.setVisibility(View.GONE);
                    index = position;
                    apiUpdateStatus(3);

                }

            }
        });

        try {
            holder.itemBinding.llTheme.setVisibility(View.GONE);
            String themesSplited = "";
            if (list.get(position).getmThemes().size() > 0) {
                //holder.ll_theme.setVisibility(View.VISIBLE);
                for (int i = 0; i < list.get(position).getmThemes().size(); i++) {
                    if (i == 0) {
                        if (i == list.get(position).getmThemes().size() - 1) {
                            themesSplited = list.get(position).getmThemes().get(i).getTitle();
                        } else {
                            themesSplited = list.get(position).getmThemes().get(i).getTitle() + ", ";
                        }
                    } else {
                        if (i == list.get(position).getmThemes().size() - 1) {
                            themesSplited = themesSplited + list.get(position).getmThemes().get(i).getTitle();
                        } else {
                            themesSplited = themesSplited + list.get(position).getmThemes().get(i).getTitle() + ", ";
                        }
                    }
                }
                holder.itemBinding.tvThemes.setText(themesSplited);
            } else {
                holder.itemBinding.tvThemes.setText("");
                //holder.ll_theme.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        try {
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //1= started
    //2=completed
    //3=not started
    public void apiUpdateStatus(final int status) {
        if (status == 1) {
            finalstatus = "Started";
        }
        if (status == 2) {
            finalstatus = "Completed";
        }
        if (status == 3) {
            finalstatus = "Not Started";
        }
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONObject jsonObject = (JSONObject) object;
                //String status = jsonObject.optString("orgStatus");
                if (status == 1) {
                    list.get(index).setOrgStatus("Started");
                    list.get(index).setCurrentStatus("Started");
                }
                if (status == 2) {
                    list.get(index).setOrgStatus("Completed");
                    list.get(index).setCurrentStatus("Completed");

                }
                if (status == 3) {
                    list.get(index).setOrgStatus("Not Started");
                    list.get(index).setCurrentStatus("Not Started");

                }
                notifyDataSetChanged();
                //utility.showToast(context,object.toString());
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

        JsonObject object = Functions.getClient().getJsonMapObject("actionId", list.get(index).getId(),
                "orgStatus", finalstatus
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.updateStatus);
    }

    public void deleteAction(String actionId, final int index) {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                list.remove(index);
                notifyDataSetChanged();
                //utility.showToast(context, "Action deleted");
                try {
                    String msg = "";
                    JSONObject obj = new JSONObject(Json);
                    msg = obj.optString("message");
                    utility.showToast(context, msg);
                }catch (Exception e){
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
        String orgId = "";
        if (sessionParam.role.equalsIgnoreCase("1")) {
            orgId = sessionParam.companyOrgId;
        } else {
            orgId = sessionParam.orgId;
        }
        JsonObject object = Functions.getClient().getJsonMapObject("actionId", actionId
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.deleteAction);
    }

    public void dialog_delete_value(final String actionId, final int index) {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Delete Action")
                .setMessage(context.getString(R.string.are_you_sure_you_want))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAction(actionId, index);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_description, tv_startdate, tv_duedate, tv_tier, tv_themes;
        TextView tv_started, tv_complete, tv_notstarted, tv_responsiblePerson;
        ImageView iv_status, iv_chat, iv_edit, iv_close, iv_delete, iv_editScroll;
        LinearLayout ll_status, ll_theme;
        SwipeLayout swipe_layout;
        private final RowActionlistBinding itemBinding;
        public ViewHolder(RowActionlistBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
           /* tv_name = v.findViewById(R.id.tv_name);
            tv_description = v.findViewById(R.id.tv_description);
            tv_startdate = v.findViewById(R.id.tv_startdate);
            tv_duedate = v.findViewById(R.id.tv_duedate);
            iv_status = v.findViewById(R.id.iv_status);
            iv_chat = v.findViewById(R.id.iv_chat);
            ll_status = v.findViewById(R.id.ll_status);
            iv_edit = v.findViewById(R.id.iv_edit);
            tv_started = v.findViewById(R.id.tv_started);
            tv_complete = v.findViewById(R.id.tv_complete);
            tv_notstarted = v.findViewById(R.id.tv_notstarted);
            tv_responsiblePerson = v.findViewById(R.id.tv_responsiblePerson);
            iv_close = v.findViewById(R.id.iv_close);
            tv_tier = v.findViewById(R.id.tv_tier);
            iv_delete = v.findViewById(R.id.iv_delete);
            iv_editScroll = v.findViewById(R.id.iv_editScroll);
            tv_themes = v.findViewById(R.id.tv_themes);
            swipe_layout = v.findViewById(R.id.swipe_layout);
            ll_theme = v.findViewById(R.id.ll_theme);*/
        }
    }


}




