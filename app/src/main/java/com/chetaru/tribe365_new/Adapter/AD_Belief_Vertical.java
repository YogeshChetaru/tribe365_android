package com.chetaru.tribe365_new.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.BeliefValue;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.UI.Know.DOT.Act_DOT_Details;
import com.chetaru.tribe365_new.UI.Know.DOT.Act_DOT_staff_List;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AD_Belief_Vertical extends RecyclerView.Adapter<AD_Belief_Vertical.ViewHolder> {

    List<BeliefValue> beliefsList = new ArrayList<>();
    ArrayList<String> valueList;
    SessionParam sessionParam;
    String Edit = "";
    Dialog Dialog;
    String dotId = "";
    String beliefId = "";
    String valueId = "";
    String valueName = "";
    String rating = "";
    TextView tv_value, tv_done, tv_seekbarProg;
    SeekBar seekbar;
    TextView tv_rating;
    Utility utility = new Utility();
    ImageView iv_dismiss;
    BeliefVerticalListener mListener;
    private final Context context;
    private BaseRequest baseRequest;


    public AD_Belief_Vertical(List<BeliefValue> list, Context context, String Edit, BeliefVerticalListener mListener) {
        this.beliefsList = list;
        notifyDataSetChanged();
        this.context = context;
        sessionParam = new SessionParam(context);
        this.Edit = Edit;
        this.mListener = mListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_belieflist, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (!beliefsList.get(position).getRatings().equals("")) {
            holder.tv_belief_item.setText(beliefsList.get(position).getName() + "\n" + beliefsList.get(position).getRatings());
        } else {
            holder.tv_belief_item.setText(beliefsList.get(position).getName());
        }

        //holder.tv_belief_itemscore.setText(beliefsList.get(position).getRatings());

        if (sessionParam.role.equals("1")) {
            holder.bubble_view.setVisibility(View.GONE);
            holder.iv_BubbleRating.setVisibility(View.GONE);
            holder.tv_belief_itemscore.setVisibility(View.GONE);

            holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_rect_white_blackborder));
            holder.tv_belief_item.setTextColor(context.getResources().getColor(R.color.textcolor));
        } else {
            if (beliefsList.get(position).getRatings().equals("")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_gry));
                holder.tv_belief_itemscore.setText("-");
            }
            if (beliefsList.get(position).getRatings().equals("0")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_0_red));
            } else if (beliefsList.get(position).getRatings().equals("1")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_1_mid_red));
            } else if (beliefsList.get(position).getRatings().equals("2")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_2_light_red));
            } else if (beliefsList.get(position).getRatings().equals("3")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_3_light_green));
            } else if (beliefsList.get(position).getRatings().equals("4")) {
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_4_mid_green));
            } else if (beliefsList.get(position).getRatings().equals("5")) {
//            holder.tv_belief_itemscore.setBackground(context.getResources().getDrawable(R.drawable.shape_color_5_green));
                holder.tv_belief_item.setBackground(context.getResources().getDrawable(R.drawable.shape_color_5_green));
            }
        }


        //--------------------------------------On Click Demo---------------------------------
////Open Bottom dialog box and select bubble list and evidence running
//        holder.tv_belief_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (sessionParam.role.equals("1")) {
//
//                    if (Edit.equals("")) {
//                        SelectDailogBox(beliefsList.get(position).getId().toString(), beliefsList.get(position).getDotId(), beliefsList.get(position).getBeliefId().toString(), beliefsList.get(position).getName().toString(), beliefsList.get(position).getValueId().toString());
//
//                    }
//
//                }
//
//                if (sessionParam.role.equals("3")) {
//                    SelectDailogBox(beliefsList.get(position).getId().toString(), beliefsList.get(position).getDotId(), beliefsList.get(position).getBeliefId().toString(), beliefsList.get(position).getName().toString(), beliefsList.get(position).getValueId().toString());
//                }
//
//            }
//        });
//        //----------------------------------------- changes
        holder.iv_BubbleRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, Act_DOT_staff_List.class);
                in.putExtra("dotId", beliefsList.get(position).getDotId())
                        .putExtra("dotBeliefId", beliefsList.get(position).getBeliefId().toString())
                        .putExtra("dotValueId", beliefsList.get(position).getId().toString())
                        .putExtra("dotValueNameId", beliefsList.get(position).getValueId())
                        .putExtra("dotvalueName", beliefsList.get(position).getName());
                context.startActivity(in);
            }
        });

        holder.iv_dot_add_evidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        holder.iv_dot_view_evidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //-----------------Working on setOnTouchListener

//
//        holder.tv_belief_item.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (sessionParam.role.equals("1")) {
////                    Intent in = new Intent(context, Act_DOT_staff_List.class);
////                    context.startActivity(in);
//                    if(Edit.equals("")) {
//                        SelectDailogBox(beliefsList.get(position).getDotId(), beliefsList.get(position).getBeliefId().toString(), beliefsList.get(position).getName().toString());
//                    }
////
//                }
//
//                if (sessionParam.role.equals("3")) {
//                    SelectDailogBox(beliefsList.get(position).getDotId(), beliefsList.get(position).getBeliefId().toString(), beliefsList.get(position).getName().toString());
//                }
//                return false;
//            }
//        });

        //--------------------------------->>......................
//        holder.tv_belief_itemscore.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return false;
//            }
//        });

//        holder.tv_belief_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SelectDailogBox(beliefsList.get(position).getDotId(), beliefsList.get(position).getBeliefId().toString(), beliefsList.get(position).getName().toString());
//
////                Intent in = new Intent(context, Act_DOT_staff_List.class);
////                in.putExtra("dotId", beliefsList.get(position).getDotId().toString());
////                in.putExtra("dotBeliefId", beliefsList.get(position).getBeliefId().toString());
////                in.putExtra("dotValueId", beliefsList.get(position).getValueId().toString());
////                in.putExtra("dotValueNameId", beliefsList.get(position).getId().toString());
////                context.startActivity(in);
//            }
//        });

//        holder.tv_belief_itemscore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////
////
//                Ratingdialog(beliefsList.get(position).getDotId(), beliefsList.get(position).getBeliefId() + "", beliefsList.get(position).getId() + "", beliefsList.get(position).getName(), rating);
//            }
//        });
        holder.lyt_dot_vertical_detailslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionParam.role.equals("3")) {
                    if (beliefsList.get(position).getRatings().isEmpty()) {
                        rating = 0 + "";
                    } else {
                        // intent.putExtra("rating", beliefsList.get(position).getRatings());
                        rating = beliefsList.get(position).getRatings();
                    }

//                ((Activity) context).finish();
//                context.startActivity(intent);

                    Ratingdialog(beliefsList.get(position).getDotId(), beliefsList.get(position).getBeliefId() + "", beliefsList.get(position).getId() + "", beliefsList.get(position).getName(), rating);

                }

            }
        });
        holder.lyt_dot_vertical_detailslist.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.VerticalListener(beliefsList.get(position));
                return true;
            }
        });
    }

    private void SelectDailogBox(final String id, final String dot_Id, final String beliefId, final String beliefName, final String ValueId) {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.dialog_dot_list_select);
        TextView tv_bubbleRating = dialog.findViewById(R.id.tv_bubbleRating);
        TextView tv_evidence = dialog.findViewById(R.id.tv_evidence);
        LinearLayout ll_bubble_rating = dialog.findViewById(R.id.ll_bubble_rating);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        if (sessionParam.role.equals("1")) {
            ll_bubble_rating.setVisibility(View.GONE);
        }
        tv_bubbleRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent in = new Intent(context, Act_DOT_staff_List.class);
                in.putExtra("dotId", dot_Id)
                        .putExtra("dotBeliefId", beliefId)
                        .putExtra("dotValueId", id)
                        .putExtra("dotValueNameId", ValueId)
                        .putExtra("dotvalueName", beliefName);
                context.startActivity(in);
                dialog.dismiss();
            }
        });

        tv_evidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public int getItemCount() {

        try {
            return beliefsList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void Ratingdialog(final String dotId, final String beliefId, final String valueId, final String valueName, final String rating) {

        Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dailog_values_rating);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        mDialog.getWindow().setAttributes(lp);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        seekbar =  mDialog.findViewById(R.id.seekbar);
        tv_seekbarProg = mDialog.findViewById(R.id.tv_seekbarProg);
        tv_rating = mDialog.findViewById(R.id.tv_rating);
        iv_dismiss = mDialog.findViewById(R.id.iv_dismiss);
//        tv_6 = (TextView) mDialog.findViewById(R.id.tv_6);
        tv_done = mDialog.findViewById(R.id.tv_done);
        tv_value = mDialog.findViewById(R.id.tv_value);
        tv_seekbarProg.setText(rating);
        tv_value.setText(valueName);
        callRatingSetDetails(context, dotId, beliefId, valueId, valueName, rating);
        mDialog.show();
        iv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

    }

    private void callRatingSetDetails(final Context context, final String dotId, final String beliefId, final String valueId, final String valueName, final String rating) {
        try {
            seekbar.setProgress((int) Float.parseFloat(rating));

            if (rating.equals("0")) {
                tv_rating.setText(rating + " " + context.getString(R.string.zero_rating_text)  + valueName);
            } else if (rating.equals("1")) {
                tv_rating.setText(rating + " " + context.getString(R.string.first_rating) + valueName + ", I am never/rarely " + valueName);
            } else if (rating.equals("2")) {
                tv_rating.setText(rating + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am) + valueName + " when I have to be");
            } else if (rating.equals("3")) {
                tv_rating.setText(rating + " " + context.getString(R.string.first_rating) + valueName +context.getString(R.string.i_am) + valueName + "  every day at work");
            } else if (rating.equals("4")) {
                tv_rating.setText(rating + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am) + valueName + "  every day at work, colleagues and clients would describe me as " + valueName);
            } else if (rating.equals("5")) {
                tv_rating.setText(rating + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am) + valueName + "  every day at work, colleagues and clients would describe me as " + valueName + ", I help others become " + valueName);
            }

        } catch (
                NullPointerException npe) {

        }
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_seekbarProg.setText(seekBar.getProgress() + "");
                String progress = seekBar.getProgress() + "";
                tv_seekbarProg.setText(progress);
//                                                        tv_rating.setText(progress+"- " + "I do not understand what it means to be " + valueName);
                if (progress.equals("0")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.zero_rating_text) + valueName);

                } else if (progress.equals("1")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating)+ valueName + ", I am never/rarely " + valueName);
                } else if (progress.equals("2")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am) + valueName + " when I have to be");
                } else if (progress.equals("3")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName +context.getString(R.string.i_am) + valueName + "  every day at work");
                } else if (progress.equals("4")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName +context.getString(R.string.i_am) + valueName + "  every day at work, colleagues and clients would describe me as " + valueName);
                } else if (progress.equals("5")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating)+ valueName + context.getString(R.string.i_am) + valueName + "  every day at work, colleagues and clients would describe me as " + valueName + ", I help others become " + valueName);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("seekbar TrackingTouch", seekBar.getProgress() + "");
                tv_seekbarProg.setText(seekBar.getProgress() + "");
                String progress = seekBar.getProgress() + "";
                tv_seekbarProg.setText(progress);
//                                                        tv_rating.setText(progress + "I do not understand what it means to be " + valueName);
                if (progress.equals("0")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.zero_rating_text) + valueName);
                } else if (progress.equals("1")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName + ", I am never/rarely " + valueName);
                } else if (progress.equals("2")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am) + valueName + " when I have to be");
                } else if (progress.equals("3")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am)+ valueName + "  every day at work");
                } else if (progress.equals("4")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am) + valueName + "  every day at work, colleagues and clients would describe me as " + valueName);
                } else if (progress.equals("5")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am) + valueName + "  every day at work, colleagues and clients would describe me as " + valueName + ", I help others become " + valueName);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("seekbar stop", seekBar.getProgress() + "");
                tv_seekbarProg.setText(seekBar.getProgress() + "");
                String progress = seekBar.getProgress() + "";
                tv_seekbarProg.setText(progress);
//                                                        tv_rating.setText(progress + "I do not understand what it means to be " + valueName);
                if (progress.equals("0")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.zero_rating_text) + valueName);
                } else if (progress.equals("1")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating)+ valueName + ", I am never/rarely " + valueName);
                } else if (progress.equals("2")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am)+ valueName + " when I have to be");
                } else if (progress.equals("3")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am)+ valueName + "  every day at work");
                } else if (progress.equals("4")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am) + valueName + "  every day at work, colleagues and clients would describe me as " + valueName);
                } else if (progress.equals("5")) {
                    tv_rating.setText(progress + " " + context.getString(R.string.first_rating) + valueName + context.getString(R.string.i_am) + valueName + "  every day at work, colleagues and clients would describe me as " + valueName + ", I help others become " + valueName);
                }
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giveRating(dotId, beliefId, valueId, valueName, rating);
            }
        });
    }

    public void giveRating(String dotId, String beliefId, String valueId, String valueName, String rating) {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                //finish();
                Log.e("api_get_COTQuestions=>", Json);
               // utility.showToast(context, "Ratings updated successfully.");
                //add this after dotsotcot screen
                try {
                    String msg = "";
                    JSONObject obj = new JSONObject(Json);
                    msg = obj.optString("message");
                    utility.showToast(context, msg);
                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(context, Act_DOT_Details.class);
                intent.putExtra("path", "rating");
                intent.putExtra("checklist", "");
                context.startActivity(intent);
                ((Activity) context).finish();

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
        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id,
                "dotId", dotId,
                "beliefId", beliefId,
                "valueId", valueId,
                "ratings", tv_seekbarProg.getText().toString()
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.api_add_rating);
    }

    public interface BeliefVerticalListener {
        void VerticalListener(BeliefValue beliefValue);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_belief_item;
        TextView tv_belief_itemscore;
        LinearLayout lyt_dot_vertical_detailslist;
        ImageView iv_BubbleRating, iv_dot_add_evidence, iv_dot_view_evidence;
        View bubble_view;
        CardView cv_block;

        public ViewHolder(View v) {
            super(v);
            tv_belief_item = v.findViewById(R.id.tv_belief_item);
            tv_belief_itemscore = v.findViewById(R.id.tv_belief_item_score);
            lyt_dot_vertical_detailslist = v.findViewById(R.id.lyt_dot_vertical_detailslist);
            iv_BubbleRating = v.findViewById(R.id.iv_bubble_rating);
            iv_dot_add_evidence = v.findViewById(R.id.iv_dot_add_evidence);
            iv_dot_view_evidence = v.findViewById(R.id.iv_dot_view_evidence);
            bubble_view = v.findViewById(R.id.bubble_view);
        }
    }
}
