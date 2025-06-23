package com.chetaru.tribe365_new.UI.Home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.DOT.Modelreport;
import com.chetaru.tribe365_new.API.Models.ModelDepartmentDialog;
import com.chetaru.tribe365_new.API.Models.User;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.SOTAdapters.Ad_reportList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.RecyclerItemClickListener;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_Report extends BaseActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_reportlist)
    RecyclerView rv_report;
    Utility utility;
    SessionParam sessionParam;
    BaseRequest baseRequest;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_date)
    ImageView iv_date;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_report_month)
    TextView tv_report_month;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_report_year)
    TextView tv_report_year;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_add_user)
    TextView tv_add_user;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll_select_user)
    LinearLayout ll_select_user;
    ArrayList<Modelreport> li_report = new ArrayList<>();
    Ad_reportList ad_reportList;
    ArrayList<String> li_month = new ArrayList<>();
    String dateFromNotification = "";

    ModelDepartmentDialog modelDepartmentDialog;
    List<User> userList;
    List<User> filteredUserList;
    List<User> resetUserlist;
    Ad_dot_home_individual ad_dot_home_individual;
    String orgId = "";
    String name = "";
    String userId = "1";
    String Gmonth = "";
    String Gyear = "";
    String backHandel="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_user_report);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_user_report;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }

    private void init() {
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        if (getIntent().getStringExtra("date") != null) {
            dateFromNotification = getIntent().getStringExtra("date");
            //2019-08-23 11:54:18
            datemotnArray();
            String[] splitDate = dateFromNotification.split("-");
            tv_report_month.setText(li_month.get(Integer.parseInt(splitDate[1])));
            tv_report_year.setText(splitDate[0]);

            getOrganisationList(splitDate[1], splitDate[0]);
        } else {
            datepick();
        }
        /*************** send notification read Status ***************/
        if (getIntent().getStringExtra("readNotificationId") != null) {
            String readNotificationId = getIntent().getStringExtra("readNotificationId");
            api_notificationRead(readNotificationId);
        }

        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepick();
            }
        });

        try{
            backHandel = getIntent().getStringExtra("backHandel");
        }catch (Exception e){
            e.printStackTrace();
        }
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe);
            }
        }

        if (getIntent().getStringExtra("orgId") != null) {
            orgId = getIntent().getStringExtra("orgId");
            name = getIntent().getStringExtra("name");
        }

        tribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });

        tv_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUser();
            }
        });

        if (sessionParam.role.equals("1")) {
            ll_select_user.setVisibility(View.VISIBLE);
            api_getUser();
        } else {
            ll_select_user.setVisibility(View.GONE);
        }

        ll_select_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUser();
            }
        });
    }

    private void datepick() {
        final Calendar c = Calendar.getInstance();
        final Dialog mDialog;
        mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.report_date_dialog);
        mDialog.setCancelable(false);
        TextView tv_date_done;
        ImageView iv_close;
        li_month.clear();
        final DatePicker datePicker = mDialog.findViewById(R.id.pickerdate);
        datePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        tv_date_done = mDialog.findViewById(R.id.tv_date_done);
        int yr = (c.get(Calendar.YEAR));
        final int month = (c.get(Calendar.MONTH));
        int day = (c.get(Calendar.DAY_OF_MONTH));

        //----------Current Date----------------------------------
        String myDate = yr + "/" + month + "/" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = 0;
        if (date != null) {
            millis = date.getTime();
        }
        datePicker.setMaxDate(millis);
        //--------------------
        //--------Previous Date-------------------------
        int prv_yr = (c.get(Calendar.YEAR) - 3);
        int prv_month = (c.get(Calendar.JANUARY));
        int prv_day = (c.get(Calendar.DAY_OF_MONTH));
        //----------Current Date----------------------------------
        String prv_myDate = prv_yr + "/" + prv_month + "/" + prv_day;
        Date prvdate = null;
        try {
            prvdate = sdf.parse(prv_myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long prv_date = 0;
        if (prvdate != null) {
            prv_date = prvdate.getTime();
        }
        datePicker.setMinDate(prv_date);
        //--------------------------------------------------------
        datemotnArray();
        datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c.get(Calendar.DAY_OF_MONTH), null);
        LinearLayout ll = (LinearLayout) datePicker.getChildAt(0);
        LinearLayout ll2 = (LinearLayout) ll.getChildAt(0);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ll2.getChildAt(1).setVisibility(View.GONE);
        }
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            ll2.getChildAt(1).setVisibility(View.GONE);
        }
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            ll2.getChildAt(0).setVisibility(View.GONE);
        }
        iv_close = mDialog.findViewById(R.id.iv_close);
        if (!tv_report_month.getText().toString().isEmpty()) {
            String st_yy = tv_report_year.getText().toString();
            String st_mm = tv_report_month.getText().toString();
            int mmm = 0;
            for (int i = 0; i < li_month.size(); i++) {
                if (st_mm.equals(li_month.get(i))) {
                    mmm = i - 1;
                }
            }
            int yyy = Integer.parseInt(st_yy);
            datePicker.init(yyy, mmm, 1, null);
        }
        tv_date_done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int cut_month = (datePicker.getMonth()) + 1;
                for (int i = 0; i < li_month.size(); i++) {
                    tv_report_month.setText(li_month.get(cut_month));
                    tv_report_year.setText(datePicker.getYear() + "");
                }
                Gmonth = cut_month + "";
                Gyear = datePicker.getYear() + "";
                getOrganisationList(cut_month + "", datePicker.getYear() + "");
                li_month.clear();
                mDialog.cancel();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.cancel();
                finish();
            }
        });
        mDialog.show();
    }

    private void datemotnArray() {
        li_month.add("");
        li_month.add("January");
        li_month.add("February");
        li_month.add("March");
        li_month.add("April");
        li_month.add("May");
        li_month.add("June");
        li_month.add("July");
        li_month.add("August");
        li_month.add("September");
        li_month.add("October");
        li_month.add("November");
        li_month.add("December");
    }

    public void getOrganisationList(String cut_month, String year) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                li_report = baseRequest.getDataList(jsonArray, Modelreport.class);
                ad_reportList = new Ad_reportList(li_report, mContext);
                rv_report.setAdapter(ad_reportList);
                rv_report.setHasFixedSize(true);
                rv_report.setAdapter(ad_reportList);
                rv_report.setLayoutManager(new LinearLayoutManager(mContext));
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
        JsonObject object;
        if (sessionParam.role.equals("1")) {
            object = Functions.getClient().getJsonMapObject("year", year,
                    "month", cut_month,
                    "userId", userId,
                    "orgId", orgId
            );
            //JsonObject object = Functions.getClient().getJsonMapObject("officeId", "10"
        } else {
            object = Functions.getClient().getJsonMapObject("year", "",
                    "month", "",
                    "userId", sessionParam.id,
                    "orgId", sessionParam.orgId
                    //JsonObject object = Functions.getClient().getJsonMapObject("officeId", "10"
            );
        }
        baseRequest.callAPIPost(1, object,ConstantAPI.api_getBubbleRatingList);
    }

    public void api_getUser() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                modelDepartmentDialog = gson.fromJson(object.toString(), ModelDepartmentDialog.class);
                //userModel = gson.fromJson(object.toString(),Actions.class);
                userList = modelDepartmentDialog.getUsers();
                resetUserlist = modelDepartmentDialog.getUsers();
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
        //companyorgId is used in action only
        //String orgId = "";
        if (!sessionParam.role.equalsIgnoreCase("1")) {
            orgId = sessionParam.orgId;
        } else {

        }
        //JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.companyOrgId
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", orgId
                //at orgid 29 i found department so m using it temporarily
        );
        baseRequest.callAPIPostWOLoader(1, object, ConstantAPI.getDepartmentuserList);
    }

    public void dialogUser() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dot_home_department);

        //DialogDotHomeDepartmentBinding binding=DialogDotHomeDepartmentBinding.inflate(LayoutInflater.from(mContext));
        //dialog.setContentView(binding.getRoot());
        dialog.setCanceledOnTouchOutside(true);

        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final SearchView searchBar = dialog.findViewById(R.id.search_bar);

        //search(search_bar,"t");
        searchBar.setIconifiedByDefault(false);

        final RecyclerView rv_list = dialog.findViewById(R.id.rv_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);

        search(searchBar, "u");
        tvTitle.setText("Select Actions");
        ad_dot_home_individual = new Ad_dot_home_individual(userList, mContext);
        rv_list.setAdapter(ad_dot_home_individual);


        rv_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                tv_add_user.setText(filteredUserList.get(position).getName());
                userId = filteredUserList.get(position).getId() + "";
                getOrganisationList(Gmonth, Gyear);
                dialog.dismiss();
                //getDotDetail();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    private void search(SearchView searchView, final String type) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ad_dot_home_individual.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (backHandel.equals("homeBack")) {
                startActivity(new Intent(mContext, Act_Home.class));
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }

    public class Ad_dot_home_individual extends RecyclerView.Adapter<Ad_dot_home_individual.ViewHolder> {
        List<User> list;
        Context context;


        public Ad_dot_home_individual(List<User> list, Context context) {
            this.list = list;
            filteredUserList = list;
            notifyDataSetChanged();
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_department_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.setIsRecyclable(false);
            holder.tv_department.setText(filteredUserList.get(position).getName());

//            holder.tv_department.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                }
//            });
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

        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        filteredUserList = list;
                    } else {
                        ArrayList<User> userList = new ArrayList<>();
                        for (User user : list) {
                            if (user.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                    user.getName().toUpperCase().contains(charString.toUpperCase())) {
                                userList.add(user);
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

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_department;

            public ViewHolder(View v) {
                super(v);
                tv_department = v.findViewById(R.id.tv_department);
            }
        }
    }
}
