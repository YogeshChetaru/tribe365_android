package com.chetaru.tribe365_new.UI.Home;

import static com.chetaru.tribe365_new.API.retrofit.ConstantAPI.freeVersionHomeDetails;
import static com.chetaru.tribe365_new.Adapter.freeVersion.Ad_HICalender.onListenerClick;
import static com.chetaru.tribe365_new.R.color.transparent;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.ModelDepartmentList;
import com.chetaru.tribe365_new.API.Models.freeVersion.FreeVersionHome;
import com.chetaru.tribe365_new.API.Models.freeVersion.HappyIndexMonthly;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.freeVersion.Ad_HICalender;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.BasicVersion.BasicHomeActivity;
import com.chetaru.tribe365_new.UI.HPTM.Act_HPTM_main;
import com.chetaru.tribe365_new.UI.Know.COT.ModelOfficeDepartment;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_FreeVersionHome extends BaseActivity implements View.OnClickListener, onListenerClick {


    BaseRequest baseRequest;
    Utility utility;
    SessionParam sessionParam;
    String orgId="";
    String departId="",officeId="";
    List<String> month_list = new ArrayList<>();
    FreeVersionHome homeDetail;
    ArrayList<String> list_depart = new ArrayList<>();
    ArrayList<String> list_depart_id = new ArrayList<>();
    ArrayList<String> list_office=new ArrayList<>();
    ArrayList<String> list_office_id=new ArrayList<>();
    List<ModelDepartmentList> li_ModelDepartmentList=new ArrayList<>();
    List<ModelOfficeDepartment> list_officedepartment= new ArrayList<>();

    ArrayList<String> years= new ArrayList<String>();
    List<Integer> yearList =new ArrayList<>();
    int newYear=0;
    ArrayList<HappyIndexMonthly> calenderList;
    int selectedyear;
    int indexofmonth;
    String selectedMonth;
    List<String> monthsList=new ArrayList<>();
    String[] months;
    int iCurrentSelection;
    boolean flag=true,firstFlag=false;

    Ad_HICalender ad_calenderView;



    /****************** initialize view ************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_companylogo)
    ImageView iv_top_companylogo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_user_name_txt)
    TextView tv_user_name_txt;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_designation)
    TextView tv_designation;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.menu_option)
    LinearLayout menu_Option;
    /******************* card Layout ************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.absent_card_view)
    CardView absent_card_view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.card_happy_index_ll)
    CardView card_happy_index_ll;
    /*@SuppressLint("NonConstantResourceId")
    @BindView(R.id.msg_title_text)
    TextView msg_title_text;*/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.happy_image)
    ImageView happy_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.neutral_image)
    ImageView neutral_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sad_image)
    ImageView sad_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.absent_Txt)
    TextView absent_Txt;

    /**************** center layout view ***********/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.calender_recycler_view)
    RecyclerView calender_recycler_view;

    /*************** month and year selection **********/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sp_office)
    Spinner sp_office;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sp_department)
    Spinner sp_department;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sp_month)
    Spinner sp_month;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sp_year)
    Spinner sp_year;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_calender_ll)
    LinearLayout show_calender_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_empty_msg)
    TextView show_empty_msg;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_hptm_button)
    Button show_hptm_button;


   /* static final String[] Months = new String[] { "January", "February",
            "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_version_home);
        ButterKnife.bind(this);
        sessionParam=new SessionParam(mContext);
        utility=new Utility();
        menu_Option.setOnClickListener(this);
        show_hptm_button.setOnClickListener(this);
        list_office.add("All Offices");
        list_office_id.add("");
        selectedyear  = Calendar.getInstance().get(Calendar.YEAR);
        indexofmonth = Calendar.getInstance().get(Calendar.MONTH);

        monthsList=new ArrayList<>();
        months=new DateFormatSymbols().getMonths();
        /****** set session param *****/
        sessionParamValue();
        getArrayCalender();
        getDepartmentList();
        call_Office_DepartmentApi();
        updateNotificationStatus();

        int year  = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth  = Calendar.getInstance().get(Calendar.MONTH);

        try {
            if (selectedyear == year) {
                for (int i = 0; i <= currentMonth; i++) {
                    String month = months[i];
                    monthsList.add(months[i]);
                }

            } else {
                for (int i = 0; i < months.length; i++) {
                    String month = months[i];
                    monthsList.add(months[i]);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        setAllMonthSpinner();


        sp_month.setSelection(indexofmonth);

        /*************** get Api ****************/
        //getHomePageDetails();
        sp_year.setSelection(getIndex(sp_year));

        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // sp_month.setSelection(indexofmonth);
                indexofmonth = position+1;
                selectedMonth = monthsList.get(position);
                if(firstFlag) {
                    getHomePageDetails();
                    firstFlag=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        absent_Txt.setOnClickListener(v -> {
            dialogAbsent();
        });
        absent_card_view.setOnClickListener(v -> {
            dialogEnableAbsent();
        });





    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.freeversion_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.nav_change_password:
                return true;
            case R.id.nav_logout:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
    /******************** Deparmtemt Spinner ******************/
    private void setAllDepartmentSpinner(){
        if (list_depart!=null){
            ArrayAdapter<String> adDepartment = new ArrayAdapter<>(this, R.layout.spinner_month_item,list_depart);
            adDepartment.setDropDownViewResource(R.layout.spinner_month_item);
            sp_department.setAdapter(adDepartment);
            sp_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                   if (flag) {
                     String newDerpartId = list_depart_id.get(i);
                       Log.e("Select department list", departId);
                       if (!departId.equalsIgnoreCase(newDerpartId)) {
                           departId=newDerpartId;
                           getHomePageDetails();
                       }
                   }
                   iCurrentSelection = i;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    departId="0";
                }
            });
        }
    }
    public void get_all_deparmant(){
        list_depart.clear();
        list_depart_id.clear();
        list_depart.add("All Department");
        list_depart_id.add("");
        for (int k=0;k<li_ModelDepartmentList.size();k++){
            list_depart.add(li_ModelDepartmentList.get(k).getDepartment());
            list_depart_id.add(li_ModelDepartmentList.get(k).getId().toString());
        }
        setAllDepartmentSpinner();
    }


    /************ get Index value for selected Year *******/
    private int getIndex(Spinner spinner ){
        int index = 0;
        for (int i=0 ; i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(selectedyear)){
                index=i;
            }
        }
        return index;
    }

    /********** get Index Value for selected month *************/
        private int getMonthIndex(Spinner spinner){
            for (int i=0 ; i<spinner.getCount();i++){
                if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(selectedMonth)){
                    return i;
                }
            }
            return 0;
        }

        /********* spinner for Selected year list********/
        private void setYearSpinner(List<Integer> yearList){
       // int thisYear= Calendar.getInstance().get(Calendar.YEAR);
        //sp_year.setSelection(getIndex(sp_year));
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<>(this ,R.layout.spinner_month_item,yearList);
        adapter1.setDropDownViewResource(R.layout.spinner_month_item);
        sp_year.setAdapter(adapter1);
        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                //sp_year.setSelection(position);

                newYear =yearList.get(i);

                if (selectedyear != newYear && newYear!=0){
                    selectedyear=newYear;
                    setAllMonthSpinner();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                newYear = 0;
            }
        });

    }
    /************** spinner for selected month list *************/
   private void setAllMonthSpinner(){
      int year  = Calendar.getInstance().get(Calendar.YEAR);
      int currentMonth  = Calendar.getInstance().get(Calendar.MONTH);

       try {
           if (selectedyear == year) {
               monthsList.clear();
               for (int i = 0; i <= currentMonth; i++) {
                   String month = months[i];
                   monthsList.add(months[i]);
               }

           } else {
               monthsList.clear();
               for (int i = 0; i < months.length; i++) {
                   String month = months[i];
                   monthsList.add(months[i]);
               }
           }

       }catch (Exception e){
           e.printStackTrace();
       }

      // month_list.addAll(Months);
        ArrayAdapter<String> ad= new ArrayAdapter<>(this,R.layout.spinner_month_item,monthsList);
        ad.setDropDownViewResource(R.layout.spinner_month_item);
        sp_month.setAdapter(ad);
       ad.notifyDataSetChanged();

       try {
           if (getMonthIndex(sp_month)==0 && selectedyear== year && !selectedMonth.equals("January")){
               sp_month.setSelection(currentMonth);
           }else {
               sp_month.setSelection(getMonthIndex(sp_month));
           }
       }catch (Exception e){
           e.printStackTrace();
           sp_month.setSelection(currentMonth);
       }
   }
   /****** set Happy Index feedback Status **************/
    private void showHappyIndex(Boolean feedbackStatus, Integer leaveStatus){
        if (leaveStatus != 1) {
            if (!feedbackStatus && leaveStatus != 1) {
                if (checkTime()) {
                    card_happy_index_ll.setVisibility(View.VISIBLE);
                    happy_image.setOnClickListener(v -> {
                        api_addHappyIndex("3");
                    });
                    neutral_image.setOnClickListener(v -> {
                        api_addHappyIndex("2");
                    });
                    sad_image.setOnClickListener(v -> {
                        api_addHappyIndex("1");
                    });
                } else {
                    card_happy_index_ll.setVisibility(View.GONE);
                }
            } else {
                card_happy_index_ll.setVisibility(View.GONE);
            }
        }else{
            card_happy_index_ll.setVisibility(View.GONE);
        }
    }
    /********* set user Data on Session Param **************/
    private void sessionParamValue(){
        try {
            if (orgId.equals("")){
                orgId=sessionParam.orgId;
            }
            Picasso.get().load(sessionParam.organisation_logo).placeholder(R.drawable.icon_tribe365).into(iv_top_companylogo);
            tv_user_name_txt.setText(sessionParam.name+" "+sessionParam.lastName);
            tv_company_name.setText(sessionParam.email);
            tv_designation.setText(sessionParam.department+ ", "+sessionParam.office);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
/*
    private DatePickerDialog createDialogWithoutDateField() {
        DatePickerDialog dpd = new DatePickerDialog(this, null, 2014, 1, 24);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
        }
        return dpd;
    }
*/
    /******************* initialize  calender Month list **************/
    private  void  getArrayCalender(){
        calenderList=new ArrayList<>();
       // ArrayList<CalenderValue> drawerList = new ArrayList<>();
       /* for (int i=1;i<=30;i++){
            if ((i & 1 )==0){
                calenderList.add(new CalenderValue(i,R.drawable.high));
            }else{
                calenderList.add(new CalenderValue(i,R.drawable.low));
            }
        }*/
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 7);
        calender_recycler_view.setLayoutManager(layoutManager);
        calender_recycler_view.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_small);
        calender_recycler_view.addItemDecoration(itemDecoration);
        calender_recycler_view.setNestedScrollingEnabled(true);
    }
    /********** set calender monthly List data **************/
    private void setCalenderList(List<HappyIndexMonthly> monthlyList,String monthStart, List<String> notWorkingDays){
        if (monthStart.contains("Monday")) {
            HappyIndexMonthly fisrtValue= new HappyIndexMonthly();
            fisrtValue.setDate("");
            fisrtValue.setScore(null);
            calenderList.add(fisrtValue);
        }else if (monthStart.contains("Tuesday")){
            for (int i=0;i<2;i++){
                HappyIndexMonthly sceondValue = new HappyIndexMonthly();
                sceondValue.setDate("");
                sceondValue.setScore(null);
                calenderList.add(sceondValue);
            }
        }else if (monthStart.contains("Wednesday")){
            for (int i=0;i<3;i++){
                HappyIndexMonthly thirdValue=new HappyIndexMonthly();
                thirdValue.setDate("");
                thirdValue.setScore(null);
                calenderList.add(thirdValue);
            }

        }else if (monthStart.contains("Thursday")){
            for (int i=0;i<4;i++) {
                HappyIndexMonthly fourthValue = new HappyIndexMonthly();
                fourthValue.setDate("");
                fourthValue.setScore(null);
                calenderList.add(fourthValue);
            }

        }else if (monthStart.contains("Friday")){
            for (int i=0;i<5;i++){
                HappyIndexMonthly fifthValue= new HappyIndexMonthly();
                fifthValue.setDate("");
                fifthValue.setScore(null);
                calenderList.add(fifthValue);
            }

        }else if (monthStart.contains("Saturday")){
            for (int i=0;i<6;i++){
                HappyIndexMonthly sixthValue= new HappyIndexMonthly();
                sixthValue.setDate("");
                sixthValue.setScore(null);
                calenderList.add(sixthValue);
            }

        }
        calenderList.addAll(monthlyList);
        ad_calenderView=new Ad_HICalender(notWorkingDays,calenderList,monthStart,mContext,this);
    calender_recycler_view.setAdapter(ad_calenderView);

    }


    /************** handle onClick on monthlyList cell ************/
        @Override
        public void onHIClick(int position, HappyIndexMonthly value) {
            String weekIs="";
            int posValue= position % 7;
            if (posValue==1){
                weekIs="Mon";
            }else if (posValue==2){
                weekIs="Tue";
            }else if(posValue==3){
                weekIs="Wed";
            }else if (posValue==4){
                weekIs="Thur";
            }else if (posValue ==5){
                weekIs="Fri";
            }
            dialogShowData(weekIs+ ", "+value.getDate()+" "+selectedMonth +" "+selectedyear,value.getScore());
        }

        /*********** show Popup data on selected lis item ************/
        private void dialogShowData(String dateSelection, Double todayEIScore) {
        try {
            Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_calender_kudos);
            //DialogCalenderKudosBinding binding=DialogCalenderKudosBinding.inflate(LayoutInflater.from(mContext));
            //dialog.setContentView(binding.getRoot());
            dialog.setCanceledOnTouchOutside(true);

            final TextView tv_title=dialog.findViewById(R.id.dialog_title_text);
            final TextView tvDate=dialog.findViewById(R.id.tv_date);
            final ImageView imageFace= dialog.findViewById(R.id.image_face);
            final TextView  hiValueTxt=dialog.findViewById(R.id.hi_value_txt);



           // Double indexEngScore = Double.valueOf(todayEIScore);
            hiValueTxt.setText(todayEIScore.toString()+"%");
            tvDate.setText(dateSelection);
            if (todayEIScore >=0 && todayEIScore <= 30){
                imageFace.setImageResource(R.drawable.low);
                hiValueTxt.setTextColor(getResources().getColor(R.color.motion_index_low));
            }else if (todayEIScore >= 31 && todayEIScore <= 60){
                imageFace.setImageResource(R.drawable.medium);
                hiValueTxt.setTextColor(getResources().getColor(R.color.motion_index_medium));
            }else if (todayEIScore >=60 ){
                imageFace.setImageResource(R.drawable.high);
                hiValueTxt.setTextColor(getResources().getColor(R.color.motion_index_high));
            }


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            dialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /************************ absentDialog enable ******************/
    private void dialogEnableAbsent(){
        Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_enble_absent);
        dialog.setCanceledOnTouchOutside(true);

        TextView tv_enable= dialog.findViewById(R.id.tv_enable);
        tv_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                absentStatusChange();
                dialog.dismiss();
            }
        });


        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        Window window=dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(transparent);
        window.setGravity(Gravity.CENTER);
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();

    }

    /***************** dialog For Enable Absent Module ******************/
    private  void dialogAbsent(){
        Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_absent);
        dialog.setCanceledOnTouchOutside(true);
        TextView starDateET= dialog.findViewById(R.id.start_date_et);
        TextView endDateET= dialog.findViewById(R.id.end_date_et);
        TextView submitTV= dialog.findViewById(R.id.tv_submit);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        starDateET.setText(formattedDate);

        /*starDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starDateET.setText(start);
                showDateCalendar(starDateET);
            }
        });*/
        endDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDate(endDateET);
            }
        });
        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!endDateET.getText().toString().trim().equals(null) ){
                    String startDate=starDateET.getText().toString().trim();
                    String endDate= endDateET.getText().toString().trim();

                    String start = utility.changeDateDMYtoYMD(startDate);
                    String end = utility.changeDateDMYtoYMD(endDate);
                    callAbsentApi(start,end);
                    dialog.dismiss();
                }
            }
        });


        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        Window window=dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(transparent);
        window.setGravity(Gravity.CENTER);
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }


    public void showEndDate(TextView endDate){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                String dateString = format.format(calendar.getTime());
                endDate.setText(dateString);
            }
        },mYear,mMonth,mDay);
        long now = c.getTimeInMillis();
        datePickerDialog.getDatePicker().setMinDate(now);
        datePickerDialog.show();
    }

    /************ handle onClick **********/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_option:
                showBottomSheetDailog();
                break;
            case R.id.card_happy_index_ll:
                break;
            case R.id.show_hptm_button:
                Intent intent=new Intent(mContext, Act_HPTM_main.class);
                intent.putExtra("backHandel","freeVersion");
                startActivity(intent);
                break;
        }

    }

    /*method to check the time span between 4 pm to 11 pm
     */
    public boolean checkTime() {
        try {
            String string1 = "16:00:00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            //calendar1.add(Calendar.DATE, 1);
            String string2 = "23:59:00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            //calendar2.add(Calendar.DATE, 1);
            String someRandomTime = "01:00:00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            //calendar3.add(Calendar.DATE, 1);
            //Date x = calendar3.getTime();
            Calendar currTime = Calendar.getInstance();
            int hour = currTime.get(Calendar.HOUR_OF_DAY);
            int minut = currTime.get(Calendar.MINUTE);
            Float time = Float.parseFloat(hour + "." + minut);
            // Float time = Float.parseFloat(currTime.get(Calendar.HOUR_OF_DAY)+"."+currTime.get(Calendar.MINUTE));

            Date x = Calendar.getInstance().getTime();
//            int starttime= hour-1;
//            int endtime=hour+1;
            // Toast.makeText(activity, "true in time", Toast.LENGTH_SHORT).show();
            //Toast.makeText(activity, "not in time \nstarttime "+time +"endtime "+time, Toast.LENGTH_SHORT).show();
            return (time >= 16) && (time < 23.59);
//            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
//                //checkes whether the current time is between 14:49:00 and 20:11:13.
//                //Toast.makeText(activity, "true in time", Toast.LENGTH_SHORT).show();
//                return true;
//            }else{
//                //Toast.makeText(activity, "not in time", Toast.LENGTH_SHORT).show();
//                return false;
//            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /***************************** set Mood Status Feedback *****************/
    public void api_addHappyIndex(String status) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    card_happy_index_ll.setVisibility(View.GONE);
                    String timeDate =utility.getCurrentDate();
                    sessionParam.happyIndexDialogShow(mContext,timeDate,true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                try {
                    utility.showToast(mContext,message);
                    card_happy_index_ll.setVisibility(View.GONE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id, "moodStatus", status);
        baseRequest.callAPIPostWOLoader(1, object, ConstantAPI.addHappyIndex);
    }

    private void getDepartmentList(){
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONArray jsonArray;
                    li_ModelDepartmentList.clear();
                    jsonArray = (JSONArray) object;
                    li_ModelDepartmentList= baseRequest.getDataList(jsonArray, ModelDepartmentList.class);
                    get_all_deparmant();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //utility.showToast(mContext,message);
            }
        });
        HashMap<String,String> mapValue=new HashMap<>();
        mapValue.put("","");
        baseRequest.callAPIGET(1,mapValue,ConstantAPI.getDepartmentList);
    }

    public void call_Office_DepartmentApi(){
        baseRequest=new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    JSONObject jsonObject=(JSONObject) object;
                    JSONArray jsonArray=jsonObject.optJSONArray("offices");
                    list_officedepartment= baseRequest.getDataList(jsonArray, ModelOfficeDepartment.class);
                    getDepartmentList();
                    call_listoffice();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);

            }
        });
        JsonObject object=Functions.getClient().getJsonMapObject("orgId",sessionParam.orgId);
        baseRequest.callAPIPost(1,object,ConstantAPI.getAllOfficenDepartments);
    }
    public void call_listoffice(){
        for (int i=0;i< list_officedepartment.size();i++){
            list_office.add(list_officedepartment.get(i).getOffice());
            list_office_id.add(String.valueOf(list_officedepartment.get(i).getOfficeId()));
        }
        setAllOfficeSpinner();
    }


    public void setAllOfficeSpinner(){
        if (list_office!=null){
            ArrayAdapter<String> ad=new ArrayAdapter<>(this,R.layout.spinner_month_item,list_office);
            ad.setDropDownViewResource(R.layout.spinner_month_item);
            sp_office.setAdapter(ad);
            sp_office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    if (flag=true) {
                        if (iCurrentSelection != -1) {
                            officeId = list_office_id.get(i);
                            list_depart_id.clear();
                            list_depart.clear();
                            list_depart_id.add("");
                            list_depart.add("All Departments");
                            setAllDepartmentSpinner();
                            if (departId.equals("")) {
                                getHomePageDetails();
                            }
                            if (officeId.equals("")) {
                                li_ModelDepartmentList.clear();
                                getDepartmentList();
                            } else {
                                for (int j = 0; j < list_officedepartment.size(); j++) {
                                    if (officeId.equals(list_officedepartment.get(j).getOfficeId().toString())) {
                                        for (int k = 0; k < list_officedepartment.get(j).getDepartment().size(); k++) {
                                            try {
                                                list_depart.add(list_officedepartment.get(j).getDepartment().get(k).getDepartment());
                                                list_depart_id.add(list_officedepartment.get(j).getDepartment().get(k).getId().toString());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    /******************************* api Home ************************/
    private void getHomePageDetails() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    calenderList.clear();
                    Gson gson = new Gson();
                    homeDetail = gson.fromJson(object.toString(), FreeVersionHome.class);
                    try {
                        if (homeDetail.getAppPaymentVersion() ==2){
                            Intent intent=new Intent(mContext,Act_Home.class);
                            startActivity(intent);
                            finish();
                        }else if (homeDetail.getAppPaymentVersion() == 3){
                            Intent intent= new Intent(mContext, BasicHomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        sessionParam.saveFreeVersion(mContext,homeDetail.getAppPaymentVersion());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    /********* check happy index popup show***********/
                    showHappyIndex(homeDetail.getUserGivenfeedback(),homeDetail.getLeaveStatus());
                    /******** set calender Data *******/
                    if (homeDetail.getHappyIndexMonthly().size()>0) {
                        show_empty_msg.setVisibility(View.GONE);
                        show_calender_ll.setVisibility(View.VISIBLE);
                        try {
                            setCalenderList(homeDetail.getHappyIndexMonthly(), homeDetail.getFirstDayOfMonth(), homeDetail.getNotWorkingDays());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        show_empty_msg.setVisibility(View.VISIBLE);
                        show_calender_ll.setVisibility(View.GONE);
                    }


                    try {
                        if(yearList.size()<=0) {
                            yearList = homeDetail.getOrgYearList();
                            Collections.reverse(yearList);
                            setYearSpinner(yearList);
                            sp_year.setSelection(getIndex(sp_year));
                            firstFlag=true;
                        }
                        //sp_year.setSelection(selectedyear);

                    }catch (Exception e ){
                        e.printStackTrace();
                    }
                    try{
                        int status= homeDetail.getLeaveStatus();
                        if (status ==1) {
                            absent_card_view.setVisibility(View.VISIBLE);
                        }else{
                            absent_card_view.setVisibility(View.GONE);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext, message);
                show_calender_ll.setVisibility(View.GONE);
                show_empty_msg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", orgId,
                "month",+indexofmonth+"",
                "year",+selectedyear+"",
                "deviceType", 1 + "",
                "officeId",officeId,
                "departmentId",departId
        );
        baseRequest.callAPIPost(1, object, freeVersionHomeDetails);
    }

    public void absentStatusChange(){
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    String message = jsonObject.getString("message");
                    utility.showToast(mContext,message);
                    getHomePageDetails();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);

            }
        });
        JsonObject object= Functions.getClient().getJsonMapObject("userId",sessionParam.id);
        baseRequest.callAPIPost(1,object,ConstantAPI.api_userChangeLeaveStatus);
    }

    /************* absent Mode enable Api *****************/
    private void callAbsentApi(String startDate, String endDate) {
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    String leaveStatus = jsonObject.getString("leaveStatus");
                    card_happy_index_ll.setVisibility(View.GONE);
                    absent_card_view.setVisibility(View.VISIBLE);
                    JSONObject jsonData = new JSONObject(Json);
                    String msg = jsonData.getString("message");
                    utility.showToast(mContext,msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext,message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id
                , "startDate",startDate, "endDate", endDate);
        baseRequest.callAPIPost(1, object, ConstantAPI.api_userApplyLeave);
    }

    /**************** handle Back Press button **********/
    @Override
    public void onBackPressed() {
        super.onBackPressed();


        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.Are_you_sure_you_want_to_exit))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAllActivities();
                            }
                        })
                .setNegativeButton(getString(R.string.No),null)
                .show();

    }

    private void showBottomSheetDailog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        bottomSheetDialog.setContentView(sheetView);

        TextView changePassword = sheetView.findViewById(R.id.changePassword);
        TextView logout = sheetView.findViewById(R.id.logout);
        TextView tvcancel = sheetView.findViewById(R.id.tvcancel);

        changePassword.setOnClickListener(v -> {
            dialogChangePassword();
            bottomSheetDialog.dismiss();
        });

        logout.setOnClickListener(v -> {
            logoutDialog();
            bottomSheetDialog.dismiss();
        });
        tvcancel.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();

 /* new ActionSheet(this,getSupportFragmentManager())
           .setCancelButtonTitle("Cancel")
           .setOtherButtonTitles("Change  Password","Logout")
           .setCancelableOnTouchOutside(true)
           .setListener(new ActionSheetCallBack() {

               onListenerClick
               @Override
               public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

               }

               @Override
               public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                   if (index == 0){
                       dialogChangePassword();
                   }else if (index == 1){
                       logoutDialog();
                   }

               }
           })
           .show();*/
    }


    /*popup to change password
     */
    public void dialogChangePassword() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_password);
        //DialogChangePasswordBinding binding=DialogChangePasswordBinding.inflate(LayoutInflater.from(mContext));
        //dialog.setContentView(binding.getRoot());
        dialog.setCanceledOnTouchOutside(true);

        final EditText etCurrentPass = dialog.findViewById(R.id.et_current_pass);
        final EditText etNewPassword = dialog.findViewById(R.id.et_new_password);
        final EditText etConfirm = dialog.findViewById(R.id.et_confirm);

        final TextView btClose = dialog.findViewById(R.id.bt_close);
        final Button btDone = dialog.findViewById(R.id.bt_done);



        etNewPassword.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z0-9$&+,:;=?@#|'<>.^*()%!]+")) { // here no space character
                            return cs;
                        }
                        return "";
                    }
                }
        });
        etConfirm.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z0-9$&+,:;=?@#|'<>.^*()%!]+")) { // here no space character
                            return cs;
                        }
                        return "";
                    }
                }
        });
        etCurrentPass.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z0-9$&+,:;=?@#|'<>.^*()%!]+")) { // here no space character
                            return cs;
                        }
                        return "";
                    }
                }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCurrentPass.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.enter_your_current_password));
                    return;
                }
                if (etNewPassword.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_enter_password));
                    return;
                }
                if (etConfirm.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.enter_confirm_password));
                    return;
                }
                if (!etNewPassword.getText().toString().equals(etConfirm.getText().toString())) {
                    utility.showToast(mContext, getString(R.string.password_and_confirm_pass_validation));
                } else {
                    baseRequest = new BaseRequest(mContext);
                    baseRequest.setBaseRequestListner(new RequestReciever() {
                        @Override
                        public void onSuccess(int requestCode, String Json, Object object) {
                           // utility.showToast(mContext, "Password changed");
                            try {
                                String msg = "";
                                JSONObject obj = new JSONObject(Json);
                                msg = obj.optString("message");
                                utility.showToast(mContext, msg);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(int requestCode, String errorCode, String message) {
                            //errorLayout.showError(message);
                            utility.showToast(mContext, message);

                        }

                        @Override
                        public void onNetworkFailure(int requestCode, String message) {
                            //errorLayout.showError(message);
                            utility.showToast(mContext, message);
                        }
                    });
                    JsonObject object = Functions.getClient().getJsonMapObject("currentPassword", etCurrentPass.getText().toString(),
                            "newPassword", etNewPassword.getText().toString()
                            //JsonObject object = Functions.getClient().getJsonMapObject("orgId", "9"
                    );
                    baseRequest.callAPIPost(1, object, ConstantAPI.updatePasswordWithCurrentPassword);
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }
     /********************** logout Dialog handel *****************/
    public void logoutDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        //DialogLogoutBinding binding=DialogLogoutBinding.inflate(LayoutInflater.from(mContext));
        //dialog.setContentView(binding.getRoot());
        dialog.setCanceledOnTouchOutside(true);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvLogout = dialog.findViewById(R.id.tv_logout);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                //apiTellSidLogout();
                api_logout();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }


    /******************* initialize layout ***********************/
    @Override
    public int getLayoutId() {
        return R.layout.free_version_home;
    }

    /**************** initialize id ************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }


}