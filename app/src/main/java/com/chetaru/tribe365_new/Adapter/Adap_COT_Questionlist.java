package com.chetaru.tribe365_new.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelCOTQuestion;
import com.chetaru.tribe365_new.API.Models.ModelCOTQuestion;
import com.chetaru.tribe365_new.API.Models.Option;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.databinding.RowCotQuestionBinding;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Locale;


public class Adap_COT_Questionlist extends RecyclerView.Adapter<Adap_COT_Questionlist.ViewHolder> {

    private static String TAG = "NumberPicker";
    public String[] etText = new String[10];
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
    private Context context;
    //OnDataChangeListener mOnDataChangeListener;
    private AdapterCallback mAdapterCallback;

    public Adap_COT_Questionlist() {

    }

    public Adap_COT_Questionlist(int index, ArrayList<ModelCOTQuestion> list_modelCOTQuestions, ArrayList<Option> list, Context context) {
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
        LayoutInflater inflater= LayoutInflater.from(context);
        RowCotQuestionBinding binding= RowCotQuestionBinding.inflate(inflater);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cot_question, parent, false);
        return new ViewHolder(binding);
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
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.itemBinding.tvOptionItem.setText(list.get(position).getOption().toString());
        holder.itemBinding.tvAlphabet.setText(list.get(position).getmAlphabate());
        holder.itemBinding.etScore.setTag(position);
        holder.itemBinding.etScore.requestFocus();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.itemBinding.etScore.setShowSoftInputOnFocus(false);
        }
        //binding data from array

        holder.itemBinding.etScore.setText(etText[position]);

        holder.itemBinding.etScore.addTextChangedListener(new TextWatcher() {
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
//                list.get(position).setmAnswer(s.toString());
//                list.get(position).setmRoleMapId(list_COTQuestions.get(index).getOption().get(position).getRoleMapId());
                // list.get(position).setmQusId(list_COTQuestions.get(index).getQuestionId().toString());
//                for(int i=0;i<list_COTQuestions.get(index).getOption().size();i++)
//                {
//                        list_COTQuestions.get(index).getOption().get(position).setmAnswer(s.toString().trim());
//                }


               /* try {
                    count = count + Integer.parseInt(s.toString() + "");
                    if (count == 10) {
                        utility.showToast(context, "Count ==" + count);
                    }

                } catch (Exception e) {

                }*/
            }
        });
        holder.itemBinding.etScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomListDialogFragment();
                tv_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // if (list.get(position).getmAnswer().equals("")) {
                        holder.itemBinding.etScore.setText(curnt_point + "");
                        String oldValue = list.get(position).getOldValue();
                        int difference;
                        //here we are checking if this answered before or not
                        if (oldValue.isEmpty()) {
                            list.get(position).setOldValue(curnt_point + "");
                            list.get(position).setmAnswer(curnt_point + "");
                            list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");
                            maxcot = maxcot - curnt_point;

                        } else {

                            if (Integer.parseInt(oldValue) > curnt_point) {
                                difference = Integer.parseInt(oldValue) - curnt_point;
                                maxcot = maxcot + difference;
                            } else {
                                difference = curnt_point - Integer.parseInt(oldValue);
                                maxcot = maxcot - difference;
                            }
                            list.get(position).setOldValue(curnt_point + "");
                            list.get(position).setmAnswer(curnt_point + "");
                            //Question list set Answer
                            list_COTQuestions.get(index).getOption().get(position).setmAnswer(curnt_point + "");

                        }

                        picker_Logic(holder);
                        dialog.dismiss();
                       /* }else{

                        }*/
                    }


                });
            }
        });
    }

    private void picker_Logic(ViewHolder viewHolder) {
        // maxcot = maxcot - curnt_point;
        Log.e(TAG, "maxcot=>" + maxcot + " cut_val=>" + curnt_point + "");

        if (count == 10) {
            maxcot = 0;
            curnt_point = 0;
        } else if (count < 10) {
            int tot_point = 0;
            final int tot_point1 = 0;
            for (int i = 0; i < list.size(); i++) {
                try {
                    tot_point = tot_point + Integer.parseInt(list.get(i).getmAnswer().toString());

                } catch (Exception e) {
                    Log.e("NOP==>", e.toString());
                }

            }

//            utility.showToast(context, tot_point + "");

          /*  public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
                mOnDataChangeListener = onDataChangeListener;
            }*/
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
        picker();
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

    private void picker() {

        numberPicker.setDividerColor(ContextCompat.getColor(context, R.color.background_dialog));
        numberPicker.setDividerColorResource(R.color.background_dialog);

        // Set formatter
        numberPicker.setFormatter(context.getString(R.string.number_picker_formatter));
        numberPicker.setFormatter(R.string.number_picker_formatter);

        // Set selected text color
        numberPicker.setSelectedTextColor(ContextCompat.getColor(context, R.color.black));
        numberPicker.setSelectedTextColorResource(R.color.black);

        // Set selected text size
        numberPicker.setSelectedTextSize(context.getResources().getDimension(R.dimen.selected_text_size));
        numberPicker.setSelectedTextSize(R.dimen.selected_text_size);

        // Set text color
        numberPicker.setTextColor(ContextCompat.getColor(context, R.color.dark_grey));
        numberPicker.setTextColorResource(R.color.dark_grey);

        // Set text size
        numberPicker.setTextSize(context.getResources().getDimension(R.dimen.text_size));
        numberPicker.setTextSize(R.dimen.text_size);

        // Set typeface
        numberPicker.setTypeface(Typeface.create(context.getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setTypeface(context.getString(R.string.roboto_light), Typeface.NORMAL);
        numberPicker.setTypeface(context.getString(R.string.roboto_light));
        numberPicker.setTypeface(R.string.roboto_light, Typeface.NORMAL);
        numberPicker.setTypeface(R.string.roboto_light);

        // Set value set max =>end point, min is stating point
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(maxcot);
        numberPicker.setValue(0);
        numberPicker.computeScroll();

        // Set fading edge enabled
        numberPicker.setFadingEdgeEnabled(true);

        // Set scroller enabled
        numberPicker.setScrollerEnabled(true);

        // Set wrap selector wheel
        numberPicker.setWrapSelectorWheel(true);

        // OnClickListener
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on current value");
            }
        });

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
                curnt_point = newVal;

            }
        });

        curnt_point = numberPicker.getValue();
    }

    public static interface AdapterCallback {
        void onMethodCallback(int total);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_option, tv_alphabet;
        EditText et_score;
        RowCotQuestionBinding itemBinding;
        public ViewHolder(RowCotQuestionBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
            /*tv_option = v.findViewById(R.id.tv_option_item);
            tv_alphabet = v.findViewById(R.id.tv_alphabet);
            et_score = v.findViewById(R.id.et_score);*/
            MyTextWatcher textWatcher = new MyTextWatcher(itemBinding.etScore);
            itemBinding.etScore.addTextChangedListener(textWatcher);
            this.setIsRecyclable(false);

        }
    }

    /*public interface OnDataChangeListener {
        public void onDataChanged(int size);
    }*/

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


