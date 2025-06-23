package com.chetaru.tribe365_new.Adapter.COTAdapters;


import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelCOTQuestion;
import com.chetaru.tribe365_new.API.Models.Option;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;

import static com.chetaru.tribe365_new.R.id;
import static com.chetaru.tribe365_new.R.layout;


public class Adap_New_COT_Questionlist extends RecyclerView.Adapter<Adap_New_COT_Questionlist.ViewHolder> {
    private static String TAG = "NumberPicker";
    public String[] etText = new String[10];
    public String[] tvpoint = new String[10];
    ArrayList<Option> list = new ArrayList<>();
    TextView tv;
    int count = 0;
    Utility utility;
    NumberPicker numberPicker;
    int curnt_point = 0;
    TextView tv_done;
    BottomSheetDialog dialog;
    int maxcot = 10, cut_val = 0;
    ArrayList<ModelCOTQuestion> list_COTQuestions;
    int index = 0;
    int tot_point = 0;
    int tot_count = 0;
    private Context context;
    //New--------------------------------------------------------------------
    //OnDataChangeListener mOnDataChangeListener;
    private AdapterCallback mAdapterCallback;


    public Adap_New_COT_Questionlist() {

    }

    public Adap_New_COT_Questionlist(int index, ArrayList<ModelCOTQuestion> list_modelCOTQuestions, ArrayList<Option> list, Context context) {
        this.list = list;
        notifyDataSetChanged();
        utility = new Utility();
        this.context = context;
        this.list_COTQuestions = list_modelCOTQuestions;
        this.index = index;
        this.mAdapterCallback = ((AdapterCallback) context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.row_new_cot_question, parent, false);
        return new ViewHolder(view);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_option.setText(list.get(position).getOption().toString());
        holder.tv_alphabet.setText(list.get(position).getmAlphabate());
        holder.tv_points.setText(list.get(position).getmAnswer());
        picker_Logic(holder);
        holder.et_score.setTag(position);
        holder.et_score.requestFocus();
        holder.tv_points.setTag(position);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.et_score.setShowSoftInputOnFocus(false);
            holder.tv_points.setShowSoftInputOnFocus(false);
        }
        //binding data from array

//        holder.et_score.setText(etText[position]);
        //  holder.tv_points.setText(tvpoint[position]);

        if (etText.length == 0) {
            holder.tv_points.setText(0 + "");
        }
//        holder.tv_points.setText("0");
//        holder.tv_points.addTextChangedListener(new TextWatcher() {
//
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                tvpoint[position] = s.toString();
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                tvpoint[position] = s.toString();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        holder.et_score.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                etText[position] = s.toString();
//               list_COTQuestions.get(index).getOption().get(position).setmAnswer(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etText[position] = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("S =>", s.toString());

            }
        });

//----------NEW-------------------------------------------------------------
//        holder.tv_points.setText("0");


//----OLD---------------------------------------------------------------

        holder.tv_increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.et_score.setText(curnt_point + "");
                int etscore = 0;

//                if (tot_point == 10) {
//                    holder.tv_increment.setClickable(false);
//                    holder.tv_decrement.setClickable(true);
//                } else {
//                    holder.tv_increment.setClickable(true);
//                    holder.tv_decrement.setClickable(true);
//                }
                if (tot_point != 10) {
                    if (holder.tv_points.getText().toString().trim().equals("")) {
                        etscore = 0;

                    } else {
                        etscore = Integer.parseInt(holder.tv_points.getText().toString().trim());
                    }
                    if (tot_point < 10) {
                        curnt_point = etscore + 1;
                    }
                    if (tot_point == 10) {
                        curnt_point = etscore;
                    }

                    holder.tv_points.setText(curnt_point + "");
                    list.get(position).setmAnswer(curnt_point + "");
                    list.get(position).setFlag(true);
                    list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");


//                    holder.tv_increment.setClickable(true);
//                    holder.tv_decrement.setClickable(true);
                } else {
//                    holder.tv_increment.setClickable(false);
//                    holder.tv_decrement.setClickable(true);
                }

                // int old=Integer.parseInt(list.get(position).getOldValue().toString())+1;
                String oldValue = list.get(position).getOldValue().toString();
                int difference;
                //here we are checking if this answered before or not
//                if (oldValue.isEmpty()) {
//                    list.get(position).setOldValue(curnt_point + "");
//                    list.get(position).setmAnswer(curnt_point + "");
//                    list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");
//                    maxcot = maxcot - curnt_point;
//
//                } else {
//
////                    if (Integer.parseInt(oldValue) > curnt_point) {
////                        difference = Integer.parseInt(oldValue) - curnt_point;
////                        maxcot = maxcot + difference;
////                    } else {
////                        difference = curnt_point - Integer.parseInt(oldValue);
//////                        maxcot = maxcot - difference;
////                    }
//                    list.get(position).setOldValue(curnt_point + "");
//                    list.get(position).setmAnswer(curnt_point + "");
//                    //Question list set Answer
//                    list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");
//
//                }

                picker_Logic(holder);
            }
        });
        holder.tv_decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int etscore = 0;
//                if (tot_point == 0) {
//                    holder.tv_increment.setClickable(true);
//                    holder.tv_decrement.setClickable(false);
//                } else {
//                    holder.tv_increment.setClickable(true);
//                    holder.tv_decrement.setClickable(true);
//                }
                if (curnt_point >= 0) {

//                holder.et_score.setText(curnt_point + "");
//                    curnt_point = curnt_point - 1;
                    if (holder.tv_points.getText().toString().trim().equals("")) {
                        etscore = 0;

                    } else {
                        etscore = Integer.parseInt(holder.tv_points.getText().toString().trim());
                    }
                    if (etscore != 0) {
                        curnt_point = etscore - 1;
                        holder.tv_points.setText(curnt_point + "");
                        list.get(position).setmAnswer(curnt_point + "");
                        list.get(position).setFlag(true);
                        list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");
                    }
                    if (etscore == 0) {
                        curnt_point = etscore;
                        holder.tv_points.setText(curnt_point + "");
                        list.get(position).setmAnswer(curnt_point + "");
                        list.get(position).setFlag(true);
                        list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");
                    }


//                    holder.tv_points.setText(curnt_point + "");
                }
//                String oldValue = list.get(position).getOldValue();
//                int difference;
                //here we are checking if this answered before or not
//                if (oldValue.isEmpty()) {
//                    list.get(position).setOldValue(curnt_point + "");
//                    list.get(position).setmAnswer(curnt_point + "");
//                    list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");
////                    maxcot = maxcot - curnt_point;
//
//                } else {
//
////                    if (Integer.parseInt(oldValue) > curnt_point) {
//////                        difference = Integer.parseInt(oldValue) - curnt_point;
//////                        maxcot = maxcot + difference;
////                    } else {
////                        difference = curnt_point - Integer.parseInt(oldValue);
//////                        maxcot = maxcot - difference;
////                    }
//                    list.get(position).setOldValue(curnt_point + "");
//                    list.get(position).setmAnswer(curnt_point + "");
//                    //Question list set Answer
//                    list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");
//
//                }

                picker_Logic(holder);

            }
        });


//        holder.et_score.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showBottomListDialogFragment();
//                tv_done.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        // if (list.get(position).getmAnswer().equals("")) {
//                        holder.et_score.setText(curnt_point + "");
//                        String oldValue = list.get(position).getOldValue();
//                        int difference;
//                        //here we are checking if this answered before or not
//                        if (oldValue.equals("")) {
//                            list.get(position).setOldValue(curnt_point + "");
//                            list.get(position).setmAnswer(curnt_point + "");
//                            list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");
////                            maxcot = maxcot - curnt_point;
//
//                        } else {
//
//                            if (Integer.parseInt(oldValue) > curnt_point) {
//                                difference = Integer.parseInt(oldValue) - curnt_point;
//                                maxcot = maxcot + difference;
//                            } else {
//                                difference = curnt_point - Integer.parseInt(oldValue);
//                                maxcot = maxcot - difference;
//                            }
//                            list.get(position).setOldValue(curnt_point + "");
//                            list.get(position).setmAnswer(curnt_point + "");
//                            //Question list set Answer
//                            list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");
//
//                        }
//
//                        picker_Logic(holder);
//                        dialog.dismiss();
//                       /* }else{
//
//                        }*/
//                    }
//
//
//                });
//            }
//        });
    }

    private void picker_Logic(ViewHolder viewHolder) {
        // maxcot = maxcot - curnt_point;
        Log.e(TAG, "maxcot=>" + maxcot + " cut_val=>" + curnt_point + "");

        if (count == 10) {
            maxcot = 0;
            curnt_point = 0;
        } else if (count < 10) {
            tot_point = 0;
            final int tot_point1 = 0;
            for (int i = 0; i < list.size(); i++) {
                try {
                    tot_point = tot_point + Integer.parseInt(list.get(i).getmAnswer().toString());

                } catch (Exception e) {
                    Log.e("NOP==>", e.toString());
                }

            }
////            maxcot = maxcot - tot_point;
////            utility.showToast(context, tot_point + "");
//
//          /*  public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
//                mOnDataChangeListener = onDataChangeListener;
//            }*/
////          if(tot_point==10)
////          {
////              viewHolder.tv_increment.setClickable(false);
////              viewHolder.tv_decrement.setClickable(true);
////          }else{
////              viewHolder.tv_increment.setClickable(true);
////              viewHolder.tv_decrement.setClickable(true);
////          }
////            if(tot_point==0)
////            {
////                viewHolder.tv_increment.setClickable(true);
////                viewHolder.tv_decrement.setClickable(false);
////            }else{
////                viewHolder.tv_increment.setClickable(true);
////                viewHolder.tv_decrement.setClickable(true);
////            }
            mAdapterCallback.onMethodCallback(tot_point);

        }
    }

    private void showBottomListDialogFragment() {
        View view = LayoutInflater.from(context).inflate(R.layout.adap_list_bottom, null);
        numberPicker = view.findViewById(R.id.number_picker);
        tv_done = view.findViewById(R.id.tv_done);
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // picker_Logic();
            }
        });
//        picker();
        dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.show();
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

    public static interface AdapterCallback {
        void onMethodCallback(int total);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_option, tv_alphabet;
        EditText et_score, tv_points;
        TextView tv_increment, tv_decrement;

        public ViewHolder(View v) {
            super(v);
            tv_option = v.findViewById(R.id.tv_option_item);
            tv_alphabet = v.findViewById(R.id.tv_alphabet);
            et_score = v.findViewById(R.id.et_score);
            tv_increment = v.findViewById(id.tv_increment);
            tv_decrement = v.findViewById(id.tv_decrement);
            tv_points = v.findViewById(id.tv_points);
            MyTextWatcher textWatcher = new MyTextWatcher(et_score);
            et_score.addTextChangedListener(textWatcher);
            // MyTextWatcher2 textWatcher1 = new MyTextWatcher2(tv_points);
            //tv_points.addTextChangedListener(textWatcher1);
//
            this.setIsRecyclable(false);

        }
    }
////    public class MyTextWatcher2 implements TextWatcher {
////        private TextView editText;
////
////        public MyTextWatcher2(EditText editText) {
////            this.editText = editText;
////        }
////
////        @Override
////        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////        }
////
////        @Override
////        public void onTextChanged(CharSequence s, int start, int before, int count) {
////            int position = (int) editText.getTag();
////            // Do whatever you want with position
////        }
////
////        @Override
////        public void afterTextChanged(Editable s) {
////
////        }
////    }
//
//
//
//    private void picker() {
//
//        numberPicker.setDividerColor(ContextCompat.getColor(context, R.color.background_dialog));
//        numberPicker.setDividerColorResource(R.color.background_dialog);
//
//        // Set formatter
//        numberPicker.setFormatter(context.getString(R.string.number_picker_formatter));
//        numberPicker.setFormatter(R.string.number_picker_formatter);
//
//        // Set selected text color
//        numberPicker.setSelectedTextColor(ContextCompat.getColor(context, R.color.black));
//        numberPicker.setSelectedTextColorResource(R.color.black);
//
//        // Set selected text size
//        numberPicker.setSelectedTextSize(context.getResources().getDimension(R.dimen.selected_text_size));
//        numberPicker.setSelectedTextSize(R.dimen.selected_text_size);
//
//        // Set text color
//        numberPicker.setTextColor(ContextCompat.getColor(context, R.color.dark_grey));
//        numberPicker.setTextColorResource(R.color.dark_grey);
//
//        // Set text size
//        numberPicker.setTextSize(context.getResources().getDimension(R.dimen.text_size));
//        numberPicker.setTextSize(R.dimen.text_size);
//
//        // Set typeface
//        numberPicker.setTypeface(Typeface.create(context.getString(R.string.roboto_light), Typeface.NORMAL));
//        numberPicker.setTypeface(context.getString(R.string.roboto_light), Typeface.NORMAL);
//        numberPicker.setTypeface(context.getString(R.string.roboto_light));
//        numberPicker.setTypeface(R.string.roboto_light, Typeface.NORMAL);
//        numberPicker.setTypeface(R.string.roboto_light);
//
//        // Set value set max =>end point, min is stating point
//        numberPicker.setMinValue(0);
//        numberPicker.setMaxValue(maxcot);
//        numberPicker.setValue(0);
//        numberPicker.computeScroll();
//
//        // Set fading edge enabled
//        numberPicker.setFadingEdgeEnabled(true);
//
//        // Set scroller enabled
//        numberPicker.setScrollerEnabled(true);
//
//        // Set wrap selector wheel
//        numberPicker.setWrapSelectorWheel(true);
//
//        // OnClickListener
//        numberPicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "Click on current value");
//            }
//        });
//
//        // OnValueChangeListener
//        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
//                curnt_point = newVal;
//
//            }
//        });
//
//        curnt_point = numberPicker.getValue();
//    }
//
//    /*public interface OnDataChangeListener {
//        public void onDataChanged(int size);
//    }*/

    public class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int position = (int) editText.getTag();
            // Do whatever you want with position
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


}


