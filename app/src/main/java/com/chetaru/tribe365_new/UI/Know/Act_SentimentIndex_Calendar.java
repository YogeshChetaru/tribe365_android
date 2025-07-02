package com.chetaru.tribe365_new.UI.Know;

import static com.chetaru.tribe365_new.API.retrofit.ConstantAPI.freeVersionHomeDetails;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.chetaru.tribe365_new.UI.Know.COT.ModelOfficeDepartment;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_SentimentIndex_Calendar extends BaseActivity implements View.OnClickListener{

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

    /******************** header Image *********/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView iv_top_companylogo;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_sentiment_index_calendar);
        ButterKnife.bind(this);
        sessionParam=new SessionParam(mContext);
        utility=new Utility();
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
    }

    private void sessionParamValue() {
        try {
            if (orgId.equals("")) {
                orgId = sessionParam.orgId;
            }
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /******************* initialize  calender Month list **************/
    private  void  getArrayCalender(){
        calenderList=new ArrayList<>();
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
            HappyIndexMonthly firstValue= new HappyIndexMonthly();
            firstValue.setDate("");
            firstValue.setScore(null);
            calenderList.add(firstValue);
        }else if (monthStart.contains("Tuesday")){
            for (int i=0;i<2;i++){
                HappyIndexMonthly secondValue = new HappyIndexMonthly();
                secondValue.setDate("");
                secondValue.setScore(null);
                calenderList.add(secondValue);
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
        ad_calenderView=new Ad_HICalender(notWorkingDays,calenderList, monthStart, mContext, new Ad_HICalender.onListenerClick() {
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
                // dialogShowData(weekIs+ ", "+value.getDate()+" "+ selectedMonth +" "+selectedyear,value.getScore());
                String monthYear="";
                try{
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy ");
                    Date date = new Date();
                    String dateIs = formatter.format(date);
                    monthYear= formatdate(dateIs);
                }catch (Exception e){
                    e.printStackTrace();
                }
                dialogShowData(weekIs+ ", "+value.getDate()+ " "+monthYear,value.getScore());
            }
        });
        calender_recycler_view.setAdapter(ad_calenderView);
    }
    /***************************** Dialog to get Feedback when user not happy *******************/
    public String formatdate(String fdate)
    {
        String datetime=null;
        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat d= new SimpleDateFormat("MMM, yyyy");
        try {
            Date convertedDate = inputFormat.parse(fdate);
            datetime = d.format(convertedDate);

        }catch (ParseException e)
        {
            e.printStackTrace();
        }
        return  datetime;


    }
    /************** handle onClick on monthlyList cell ************/


    /*********** show Popup data on selected lis item ************/
    private void dialogShowData(String dateSelection, Double todayEIScore) {
        try {
            Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_calender_kudos);
            dialog.setCanceledOnTouchOutside(true);

            final TextView tv_title=dialog.findViewById(R.id.dialog_title_text);
            final TextView tvDate=dialog.findViewById(R.id.tv_date);
            final ImageView imageFace= dialog.findViewById(R.id.image_face);
            final TextView  hiValueTxt=dialog.findViewById(R.id.hi_value_txt);



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



    /********************************** APi Calling Section **********************/
    private void getCalenderDetails() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    calenderList.clear();
                    Gson gson = new Gson();
                    homeDetail = gson.fromJson(object.toString(), FreeVersionHome.class);

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
                    }catch (Exception e ){
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
               // utility.showToast(mContext,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //utility.showToast(mContext,message);
            }
        });
        HashMap<String,String> mapValue=new HashMap<>();
        mapValue.put("","");
        baseRequest.callAPIGET(1,mapValue, ConstantAPI.getDepartmentList);
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
        JsonObject object= Functions.getClient().getJsonMapObject("orgId",sessionParam.orgId);
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
                                getCalenderDetails();
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


    /******************************************* Set Spinner Data *****************************/
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
                            getCalenderDetails();
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
    @Override
    public int getLayoutId() {
        return R.layout.act_sentiment_index_calendar;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_know;
    }

    @Override
    public void onClick(View v) {

    }
}