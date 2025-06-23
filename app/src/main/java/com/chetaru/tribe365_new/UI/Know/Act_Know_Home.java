package com.chetaru.tribe365_new.UI.Know;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.KnowHome.KnowOrgDetails;
import com.chetaru.tribe365_new.API.Models.KnowHome.KudosAwardList;
import com.chetaru.tribe365_new.API.Models.KnowHome.KudosCampList;
import com.chetaru.tribe365_new.API.Models.MemberHome.LatestKudosAward;
import com.chetaru.tribe365_new.API.Models.ModelDepartmentList;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.KnowHome.Ad_Multi_champions;
import com.chetaru.tribe365_new.Adapter.KnowHome.Ad_kudos_awards_list;
import com.chetaru.tribe365_new.Adapter.award.Ad_AwardUserList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.HPTM.Act_HPTM_main;
import com.chetaru.tribe365_new.UI.Know.COT.ModelOfficeDepartment;
import com.chetaru.tribe365_new.UI.Know.Member.Act_Member_Home;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class Act_Know_Home extends BaseActivity  {

    ArrayList<String> list_office = new ArrayList<>();
    ArrayList<String> list_office_id = new ArrayList<>();
    ArrayList<String> list_depart = new ArrayList<>();
    ArrayList<String> list_depart_id = new ArrayList<>();
    String officeId = "0", depart_id = "0";
    boolean flag = true, flagSecond= true;
    int iCurrentSelection;
    ArrayList<ModelDepartmentList> li_ModelDepartmentList = new ArrayList<>();
    ArrayList<ModelOfficeDepartment> list_officedepartment = new ArrayList<>();
    Utility utility;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    String orgId="";
    String lastDate="";
    KnowOrgDetails homeDetail;
    Ad_Multi_champions ad_Multi_champions;
    List<KudosCampList> kudosList=new ArrayList<>();
    Ad_kudos_awards_list ad_kudosAward;
    List<LatestKudosAward> kudosAwardList= new ArrayList<>();
    // create array of Strings
    // and store name of courses


    /*********************** initialize layout ***************/
    /*********************** initialize Top layout ***************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView iv_top_companylogo;
    /******************** initialize Spinner ****************/
    @BindView(R.id.tv_hptm_action)
    TextView tv_hptm_action;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sp_depart)
    Spinner sp_depart;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sp_office)
    Spinner sp_office;
    /******************** initialize first card ****************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sp_daily_filter)
    Spinner sp_daily_filter;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_today_title)
    TextView tv_today_title;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.today_image_face)
    ImageView today_image_face;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_today_value)
    TextView tv_today_Value;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_yesterday_title)
    TextView tv_yesterday_title;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.yesterday_image_face)
    ImageView yesterday_image_face;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_yesterday_value)
    TextView tv_yesterday_value;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_day_after_title)
    TextView tv_day_after_title;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.day_after_face)
    ImageView day_after_face;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_day_after_value)
    TextView tv_day_after_value;

    /***************** initialize Culture index **************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.culture_face_image)
    ImageView culture_face_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_culture_rank)
    TextView tv_culture_rank;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_culture_value)
    TextView tv_culture_value;

    /*********************** initialize Engagement Index **************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.engagement_face_image)
    ImageView engagement_face_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_engagement_rank)
    TextView tv_engagement_rank;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_engagement_value)
    TextView tv_engagement_value;


    /************************** initialize bottom view *************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_kudos_champ_tv)
    TextView show_kudos_champ_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_kudos_list_card)
    CardView show_kudos_list_card;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_kudos_champ)
    RecyclerView rv_kudos_champ;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_no_record_found)
    TextView tv_no_record_found;
    /******************* initialize bottom view *******************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_kudos_awards)
    RecyclerView rv_kudos_awards;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_no_record_found_awards)
    TextView no_awards_found;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_view_more_award)
    TextView tv_view_more_award;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_know_members)
    TextView tv_know_members;
    @BindView(R.id.view_more_card)
    CardView view_more_card;
    /************** Your Report view Initialize *************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_your_status)
    TextView tv_your_status;


    /************* get Default array for selection *************/
    String[] courses = { "Day Wise" ,"Week Wise","Month Wise" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_know_home);
        utility=new Utility();
        sessionParam=new SessionParam(mContext);
        ButterKnife.bind(this);
        /********** Assign First value for department and Office ********/
        list_office.add("All Offices");
        list_depart.add("All Departments");
        list_office_id.add("0");
        list_depart_id.add("0");
        /************** get session manager **************/
        getSessionParam();

        /********************* get spinner Initialization and handle Selection ***********/
        try {
            setFilterDaySpinner();
            setAllOfficeSpinner();
            setAllDepartmentSpinner();
        }catch (Exception e){
            Timber.tag("Act_Home").e(e.toString());
        }

        /**************************** getApi for Home**************************/
        //getOrganisationApi();
        try {
            call_Office_DepartmentApi();
        }catch (Exception e){
            Timber.tag("Act_Home").e(e.toString());
        }

        tv_know_members.setOnClickListener(v ->{
            Intent intent=new Intent(this, Act_Member_Home.class);
            startActivity(intent);
        });
        tv_hptm_action.setOnClickListener(v ->{
            Intent hptm_intent=new Intent(mContext, Act_HPTM_main.class);
            hptm_intent.putExtra("backHandel","know");
            startActivity(hptm_intent);
        });

        iv_top_companylogo.setOnClickListener(v->{
            callHomeAct(mContext);
        });
        tv_view_more_award.setOnClickListener(v->{
            Intent intent= new Intent(this,Act_Award_List.class);
            startActivity(intent);
        });
        tv_your_status.setOnClickListener( v -> {
            Intent intent= new Intent(this, Act_Individual_Report.class);
            startActivity(intent);
        });

    }
    /*********** getSessionParam **************/
    private void getSessionParam() {
        try {
            if (orgId.equals("")) {
                orgId = sessionParam.orgId;
            }
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (sessionParam.loginVersion==3){
            show_kudos_champ_tv.setVisibility(View.GONE);
            show_kudos_list_card.setVisibility(View.GONE);
        }else {
            show_kudos_list_card.setVisibility(View.VISIBLE);
            show_kudos_champ_tv.setVisibility(View.VISIBLE);
        }
    }

    /******************* get spinner for day,Week,Month selection **************/
    private void setFilterDaySpinner() {
        if (courses!=null) {
            
            ArrayAdapter<String> adDayWaise = new ArrayAdapter<String>(this, R.layout.spinner_item_list, courses);
            adDayWaise.setDropDownViewResource(R.layout.spinner_item_list);
            sp_daily_filter.setAdapter(adDayWaise);
            sp_daily_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   // Toast.makeText(getApplicationContext(), courses[position], Toast.LENGTH_LONG).show();
                    try {
                        if (homeDetail != null) {
                            setDataonSelection(courses[position], homeDetail);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    /******** get Filter for Selection **************/
    private void setDataonSelection(String spinnerValue, KnowOrgDetails homeDetail) {
        if (spinnerValue.contains("Week")){
            setWeeklyData(homeDetail);
        }else if (spinnerValue.contains("Month")){
            setMonthlyData(homeDetail);
        } else {
            setDayWiseData(homeDetail);
        }
    }

    /******************* set data on Day wise Selection ******************/
    @SuppressLint("SetTextI18n")
    private void setDayWiseData(KnowOrgDetails homeDetail) {

        if(homeDetail.getTodayHIPercent()!=null){
           // tv_today_title.setText("Today");
            tv_today_title.setText(homeDetail.getTodayDay());
            setFirstColumnData(homeDetail.getTodayHIPercent(),tv_today_Value,today_image_face);
        }
        if (homeDetail.getYesterdayHIPercent()!=null){
            //tv_yesterday_title.setText("Yesterday");
            tv_yesterday_title.setText(homeDetail.getYesterdayDay());
            setSecondColumnData(Double.valueOf(homeDetail.getYesterdayHIPercent()),tv_yesterday_value,yesterday_image_face);
        }
        if (homeDetail.getDayBeforeYesterdayHIPercent()!=null){
           // tv_day_after_title.setText(lastDate);
            tv_day_after_title.setText(homeDetail.getDayBeforeYesterdayDay());
            setThirdColumnData(Double.valueOf(homeDetail.getDayBeforeYesterdayHIPercent()),tv_day_after_value,day_after_face);
        }
        
    }
    @SuppressLint("SetTextI18n")
    private void setWeeklyData(KnowOrgDetails homeDetail) {
        if(homeDetail.getThisWeekHIPercent()!=null){
            tv_today_title.setText("This Week");
            setFirstColumnData(Double.valueOf(homeDetail.getThisWeekHIPercent()),tv_today_Value,today_image_face);
        }
        if (homeDetail.getLastWeekHIPercent()!=null){
            tv_yesterday_title.setText("Last Week");
            setSecondColumnData(Double.valueOf(homeDetail.getLastWeekHIPercent()),tv_yesterday_value,yesterday_image_face);
        }
        if (homeDetail.getLastToLastWeekHIPercent()!=null){
            tv_day_after_title.setText("Last 3rd Week");
            setThirdColumnData(Double.valueOf(homeDetail.getLastToLastWeekHIPercent()),tv_day_after_value,day_after_face);
        }
    }
    @SuppressLint("SetTextI18n")
    private void setMonthlyData(KnowOrgDetails homeDetail) {
        Calendar c= Calendar.getInstance();

        if(homeDetail.getThisMonthHIPercent()!=null){
            int month=c.get(Calendar.MONTH);
            String monthName=getMonthForInt(month);
           // tv_today_title.setText("This Month");
           // tv_today_title.setText(monthName+"");
            tv_today_title.setText(homeDetail.getThisMonthName());
            setFirstColumnData(Double.valueOf(homeDetail.getThisMonthHIPercent()),tv_today_Value,today_image_face);
        }
        if (homeDetail.getLastMonthHIPercent()!=null){
            int lastMonth=0;
            try {
                 lastMonth=c.get(Calendar.MONTH)-1;
                 if (lastMonth ==-1){
                     lastMonth= 11;
                 }
            }catch (Exception e){
                e.printStackTrace();
              int  month=c.get(Calendar.MONTH);
                if (month==0) {
                    lastMonth = 12;
                }
            }

           // String lastMonthName=getMonthForInt(lastMonth);
            String lastMonthName=homeDetail.getLastMonthName();

            tv_yesterday_title.setText("Last Month");
            tv_yesterday_title.setText(lastMonthName);
            setSecondColumnData(Double.valueOf(homeDetail.getLastMonthHIPercent()),tv_yesterday_value,yesterday_image_face);
        }
        if (homeDetail.getLastToLastMonthHIPercent()!=null){
            int month = 0;
            try {
                month=c.get(Calendar.MONTH)-2;
                int checkMonth =c.get(Calendar.MONTH);
                if (month==-1){
                    month=11;
                }else if (month==-2){
                    month=10;
                }
            }catch (Exception e){
                int checkMonth =c.get(Calendar.MONTH);
                if (checkMonth==0){
                    month=11;
                }else if (checkMonth==-1){
                    month=12;
                }
            }
            //String beforeMonth=getMonthForInt(month);
            String beforeMonth=homeDetail.getLastToLastMonthName();
            tv_day_after_title.setText(beforeMonth);
            setThirdColumnData(Double.valueOf(homeDetail.getLastToLastMonthHIPercent()),tv_day_after_value,day_after_face);
        }
    }
    String getMonthForInt(int num) {
        String month = "wrong";
        try {
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getMonths();
            if (num >= 0 && num <= 11 ) {
                month = months[num];
            }
            return month;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /************** for first column check condition according to set face High low Medium ***************/
    @SuppressLint("SetTextI18n")
    private void setFirstColumnData(Double finalCount, TextView valueText, ImageView imageFace){
        valueText.setText(finalCount + "%");
        if (finalCount >= 0 && finalCount <= 30) {
            imageFace.setImageResource(R.drawable.low);
            //eng_tv_score.setText(indexEngScore+"");
            valueText.setTextColor(getResources().getColor(R.color.motion_index_low));
        }
        else if (finalCount >= 31 && finalCount <= 60) {
            imageFace.setImageResource(R.drawable.medium);
            //tv_index_eng_number.setText(indexEngScore+"");
            //eng_tv_score.setText(todayEIScore + "");
            valueText.setTextColor(getResources().getColor(R.color.motion_index_medium));
        }
        else if (finalCount >= 60) {
            imageFace.setImageResource(R.drawable.smile_green_big);
            //tv_index_eng_number.setText(indexEngScore+"");
            //eng_tv_score.setText(todayEIScore + "");
            valueText.setTextColor(getResources().getColor(R.color.motion_index_high));
        }
    }
    @SuppressLint("SetTextI18n")
    private void setSecondColumnData(Double finalCount, TextView valueText, ImageView imageFace){
        valueText.setText(finalCount + "%");
        if (finalCount >= 0 && finalCount <= 30) {
            imageFace.setImageResource(R.drawable.low);
            //eng_tv_score.setText(indexEngScore+"");
            //eng_tv_score.setText(todayEIScore + "");
            valueText.setTextColor(getResources().getColor(R.color.motion_index_low));
        } else if (finalCount >= 31 && finalCount <= 60) {
            imageFace.setImageResource(R.drawable.medium);
            //tv_index_eng_number.setText(indexEngScore+"");
            //eng_tv_score.setText(todayEIScore + "");
            valueText.setTextColor(getResources().getColor(R.color.motion_index_medium));
        } else if (finalCount >= 60) {
            imageFace.setImageResource(R.drawable.smile_green_big);
            //tv_index_eng_number.setText(indexEngScore+"");
            //eng_tv_score.setText(todayEIScore + "");
            valueText.setTextColor(getResources().getColor(R.color.motion_index_high));
        }
    }
    private void setThirdColumnData(Double finalCount,TextView valueText,ImageView imageFace){
        valueText.setText(finalCount + "%");
        if (finalCount >= 0 && finalCount <= 30) {
            imageFace.setImageResource(R.drawable.low);
            //eng_tv_score.setText(indexEngScore+"");
            //eng_tv_score.setText(todayEIScore + "");
            valueText.setTextColor(getResources().getColor(R.color.motion_index_low));
        } else if (finalCount >= 31 && finalCount <= 60) {
            imageFace.setImageResource(R.drawable.medium);
            //tv_index_eng_number.setText(indexEngScore+"");
            //eng_tv_score.setText(todayEIScore + "");
            valueText.setTextColor(getResources().getColor(R.color.motion_index_medium));
        } else if (finalCount >= 60) {
            imageFace.setImageResource(R.drawable.smile_green_big);
            //tv_index_eng_number.setText(indexEngScore+"");
            //eng_tv_score.setText(todayEIScore + "");
            valueText.setTextColor(getResources().getColor(R.color.motion_index_high));
        }
    }



    /******************* spinner For Department initialize and set selection ****************/
    private void setAllDepartmentSpinner() {
        if (list_depart!=null) {
            ArrayAdapter<String> adDepartment = new ArrayAdapter<String>(this, R.layout.spinner_item, list_depart);
            adDepartment.setDropDownViewResource(R.layout.spinner_item);
            sp_depart.setAdapter(adDepartment);
            sp_depart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                   // Toast.makeText(getApplicationContext(), list_depart.get(i), Toast.LENGTH_LONG).show();
                    if (flag){
                        depart_id= list_depart_id.get(i);
                        Log.e("Select list=> ",depart_id);
                        getOrganisationApi();
                    }
                    iCurrentSelection = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    depart_id="0";
                }
            });
        }
    }
    /***************** spinner for initialize and set selection for Office ***************/
    private void setAllOfficeSpinner() {
        if (list_office!=null) {
            ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.spinner_item, list_office);
            ad.setDropDownViewResource(R.layout.spinner_item);
            sp_office.setAdapter(ad);
            sp_office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    if (flag=true){
                        if (iCurrentSelection!=-1){
                            officeId= list_office_id.get(i);
                            //int offID=Integer.parseInt(officeId);
                            list_depart_id.clear();
                            list_depart.clear();
                            list_depart_id.add("0");
                            list_depart.add("All Departments");

                            if (depart_id.equals("0")){

                                if (flagSecond) {
                                    getOrganisationApi();
                                    flagSecond=false;
                                }
                            }
                            setAllDepartmentSpinner();
                            if (officeId.equals("0")){
                                li_ModelDepartmentList.clear();
                                getDepartmentList();
                            }else {
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

    /****************** set CultureData From Organisation
     * @param cultureData
     * @param cultureIndexRank********/
    @SuppressLint("SetTextI18n")
    private void setCultureData(Double cultureData, Integer cultureIndexRank) {
        Double finalCount;

        String showEngValue=trimTrailingZeros(cultureData.toString());
        tv_culture_rank.setText("Rank: "+cultureIndexRank);
        tv_culture_value.setText(showEngValue);

            if (cultureData!=null){
                if ( cultureData <= 499) {
                    culture_face_image.setImageResource(R.drawable.low);
                    //eng_tv_score.setText(indexEngScore+"");
                    tv_culture_value.setTextColor(getResources().getColor(R.color.motion_index_low));
                } else if (cultureData >= 500 && cultureData <= 1099) {
                    culture_face_image.setImageResource(R.drawable.medium);
                    //tv_index_eng_number.setText(indexEngScore+"");
                    //eng_tv_score.setText(todayEIScore + "");
                    tv_culture_value.setTextColor(getResources().getColor(R.color.motion_index_medium));
                } else if (cultureData >= 1100) {
                    culture_face_image.setImageResource(R.drawable.smile_green_big);
                    //tv_index_eng_number.setText(indexEngScore+"");
                    //eng_tv_score.setText(todayEIScore + "");
                    tv_culture_value.setTextColor(getResources().getColor(R.color.motion_index_high));
                }
            }

    }
    private void setCultureDataBasicVersion(Double cultureData, Integer cultureIndexRank) {


        String showEngValue=trimTrailingZeros(cultureData.toString());
        tv_culture_rank.setText("Rank: "+cultureIndexRank);
        tv_culture_value.setText(showEngValue);

        if (cultureData!=null){
            if ( cultureData <= 299) {
                culture_face_image.setImageResource(R.drawable.low);
                tv_culture_value.setTextColor(getResources().getColor(R.color.motion_index_low));
            } else if (cultureData >= 300 && cultureData < 800) {
                culture_face_image.setImageResource(R.drawable.medium);
                tv_culture_value.setTextColor(getResources().getColor(R.color.motion_index_medium));
            } else if (cultureData >= 800) {
                culture_face_image.setImageResource(R.drawable.smile_green_big);
                tv_culture_value.setTextColor(getResources().getColor(R.color.motion_index_high));
            }
        }

    }
    private static String trimTrailingZeros(String number) {
        if(!number.contains(".")) {
            return number;
        }

        return number.replaceAll("\\.?0*$", "");
    }
    public static String removeZero(double number) {
        DecimalFormat format = new DecimalFormat("#.###########");
        return format.format(number);
    }

    @SuppressLint("SetTextI18n")
    private void setEngagementData(Double engagementData, Integer engagementIndexRank) {
        Double finalCount=engagementData;

        tv_engagement_rank.setText("Rank: "+engagementIndexRank);

        String showEngValue=trimTrailingZeros(String.valueOf(engagementData));
        tv_engagement_value.setText(showEngValue);
        if (engagementData!=null) {
            if (finalCount <= 499) {
                engagement_face_image.setImageResource(R.drawable.low);
                tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_low));
            } else if (finalCount >= 500 && finalCount <= 1099) {
                engagement_face_image.setImageResource(R.drawable.medium);

                tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_medium));
            } else if (finalCount >= 1100) {
                engagement_face_image.setImageResource(R.drawable.smile_green_big);
                tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_high));
            }
        }

    }
    private void setEngagementDataBasicVersion(Double engagementData, Integer engagementIndexRank) {
        Double finalCount=engagementData;
        tv_engagement_rank.setText("Rank: "+engagementIndexRank);

        String showEngValue=trimTrailingZeros(String.valueOf(engagementData));
        tv_engagement_value.setText(showEngValue);
        if (engagementData!=null) {
            if (finalCount <= 299) {
                engagement_face_image.setImageResource(R.drawable.low);
                tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_low));
            } else if (finalCount >= 300 && finalCount < 800) {
                engagement_face_image.setImageResource(R.drawable.medium);
                tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_medium));
            } else if (finalCount >= 800) {
                engagement_face_image.setImageResource(R.drawable.smile_green_big);
                tv_engagement_value.setTextColor(getResources().getColor(R.color.motion_index_high));
            }
        }

    }
    private void setKudosAwardList(List<LatestKudosAward> kudosAwardList){

        LinearLayoutManager layoutManager= new LinearLayoutManager(mContext);
        rv_kudos_awards.setNestedScrollingEnabled(false);
        rv_kudos_awards.setLayoutManager(layoutManager);

        ad_kudosAward =new Ad_kudos_awards_list(kudosAwardList, mContext, new Ad_kudos_awards_list.groupUsersListener() {
            @Override
            public void groupUser(String awardValue,String desc, List<KudosAwardList> userList) {
                groupUserDialog(awardValue,desc,userList);
            }
        });
        rv_kudos_awards.setAdapter(ad_kudosAward);


    }

    private void setKudosChamp(List<KudosCampList> lastMonthKudosChamp) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_kudos_champ.setLayoutManager(layoutManager);
        rv_kudos_champ.setHasFixedSize(true);
        Drawable mDivider = ContextCompat.getDrawable(this, R.drawable.divider);
        DividerItemDecoration hItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        assert mDivider != null;
        hItemDecoration.setDrawable(mDivider);
        rv_kudos_champ.setNestedScrollingEnabled(false);

        ad_Multi_champions =new Ad_Multi_champions(lastMonthKudosChamp,mContext);
        rv_kudos_champ.setAdapter(ad_Multi_champions);
    }

    /************************* Api Calling **********************************/

    /************ get All Org data ********************/
    private void getOrganisationApi() {
            baseRequest = new BaseRequest(mContext);
            baseRequest.setBaseRequestListner(new RequestReciever() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onSuccess(int requestCode, String Json, Object object) {
                    try {
                        flag= true;
                        Gson gson = new Gson();
                        try {
                            homeDetail = gson.fromJson(object.toString(), KnowOrgDetails.class);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if(sessionParam.loginVersion ==3){
                            setCultureDataBasicVersion(homeDetail.getCultureIndex(),homeDetail.getCultureIndexRank());
                        }else {
                            setCultureData(homeDetail.getCultureIndex(), homeDetail.getCultureIndexRank());
                        }
                        if (sessionParam.loginVersion ==3){
                            setEngagementDataBasicVersion(homeDetail.getEngagementIndex(), homeDetail.getEngagementIndexRank());
                        }else {
                            setEngagementData(homeDetail.getEngagementIndex(), homeDetail.getEngagementIndexRank());
                        }
                         kudosList=homeDetail.getLastMonthKudosChamp();

                         if (kudosList.size()>0) {
                             rv_kudos_champ.setVisibility(View.VISIBLE);
                             tv_no_record_found.setVisibility(View.GONE);

                             try {
                                 if(kudosList.get(0).getUserName().isEmpty()){
                                     rv_kudos_champ.setVisibility(View.GONE);
                                     tv_no_record_found.setVisibility(View.VISIBLE);
                                 }else{
                                     setKudosChamp(kudosList);
                                 }
                             }catch (Exception e){
                                 e.printStackTrace();
                             }

                         }else {
                             rv_kudos_champ.setVisibility(View.GONE);
                             tv_no_record_found.setVisibility(View.VISIBLE);
                         }



                        kudosAwardList=homeDetail.getLatestKudosAward();
                         if (kudosAwardList.size()>0){
                             rv_kudos_awards.setVisibility(View.VISIBLE);
                             no_awards_found.setVisibility(View.GONE);
                             setKudosAwardList(kudosAwardList);
                             try{
                                 if (kudosAwardList.size() >= 3){
                                     tv_view_more_award.setVisibility(View.VISIBLE);
                                     view_more_card.setVisibility(View.VISIBLE);
                                 }else{
                                     tv_view_more_award.setVisibility(View.GONE);
                                     view_more_card.setVisibility(View.GONE);
                                 }

                             }catch (Exception e){
                                 e.printStackTrace();
                             }
                         }else{
                             rv_kudos_champ.setVisibility(View.GONE);
                             no_awards_found.setVisibility(View.VISIBLE);
                             tv_view_more_award.setVisibility(View.GONE);
                             view_more_card.setVisibility(View.GONE);
                         }
                        setFilterDaySpinner();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int requestCode, String errorCode, String message) {
                    utility.showToast(mContext, message);
                }

                @Override
                public void onNetworkFailure(int requestCode, String message) {
                    //errorLayout.showError(message);
                    utility.showToast(mContext, message);
                }
            });
            JsonObject object = Functions.getClient().getJsonMapObject(
                    "orgId", orgId,
                    "officeId", officeId,
                    "departmentId", depart_id
            );
            baseRequest.callAPIPost(1, object, ConstantAPI.getKnowOrganisationDetails);


    }




    /******************** get DepartmentList ***********/
    public void getDepartmentList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                li_ModelDepartmentList = baseRequest.getDataList(jsonArray, ModelDepartmentList.class);
                call_li_allDepart();
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
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        baseRequest.callAPIGET(1, map, ConstantAPI.getDepartmentList);
    }

    /************** filter department list and remove office id ***************/
    private void call_li_allDepart() {
        list_depart_id.clear();
        list_depart.clear();
        list_depart_id.add("0");
        list_depart.add("All Department");
//                list_office.add("All Office");
//                call_fiterteamApi();
        Log.e("Select list=>", officeId);

        for (int k = 0; k < li_ModelDepartmentList.size(); k++) {
            list_depart.add(li_ModelDepartmentList.get(k).getDepartment());
            list_depart_id.add(li_ModelDepartmentList.get(k).getId().toString());
        }
    }

    /* API call to get office list and department list
     */
    /********************* get office List Api ******************/
    public void call_Office_DepartmentApi() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONObject jsonObject = (JSONObject) object;
                JSONArray jsonArray = jsonObject.optJSONArray("offices");
                list_officedepartment = baseRequest.getDataList(jsonArray, ModelOfficeDepartment.class);
                getDepartmentList();
                call_listoffice();
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getAllOfficenDepartments);
    }


    /*********** filter office list id ****************/
    private void call_listoffice() {
        for (int i = 0; i < list_officedepartment.size(); i++) {
            list_office.add(list_officedepartment.get(i).getOffice());
            list_office_id.add(String.valueOf(list_officedepartment.get(i).getOfficeId()));
        }
        setAllOfficeSpinner();
    }

    private void groupUserDialog(String awardValue,String description, List<KudosAwardList> userList) {
        final Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_award_user_list);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        final TextView iv_top_previous= dialog.findViewById(R.id.award_name_tv);
        final TextView description_tv= dialog.findViewById(R.id.award_desc_tv);
        RecyclerView award_list= dialog.findViewById(R.id.award_rv);

        iv_top_previous.setText(awardValue);
        try {
            description_tv.setText(description);
        }catch (Exception e){
            e.printStackTrace();
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        award_list.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        award_list.addItemDecoration(itemDecoration);
        award_list.addItemDecoration(new ItemOffsetDecoration(mContext,R.dimen.item_offset_small));
        List<String> personList = new ArrayList<>();
        Ad_AwardUserList adAwardUserList = new Ad_AwardUserList(awardValue, userList, personList, mContext);
        award_list.setNestedScrollingEnabled(false);

        award_list.setAdapter(adAwardUserList);
        WindowManager.LayoutParams lp= new WindowManager.LayoutParams();
        Window window=dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_know_home;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_know;
    }


}