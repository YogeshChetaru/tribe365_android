package com.chetaru.tribe365_new.UI.Home.Actions;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.Ad_Departmentdialog;
import com.chetaru.tribe365_new.Adapter.Ad_OfficeDialog;
import com.chetaru.tribe365_new.Adapter.Ad_TierList;
import com.chetaru.tribe365_new.Adapter.Ad_UserListDialog;
import com.chetaru.tribe365_new.API.Models.Department;
import com.chetaru.tribe365_new.API.Models.ModelAddActionUser;
import com.chetaru.tribe365_new.API.Models.ModelDepartmentDialog;
import com.chetaru.tribe365_new.API.Models.ModelOrganisationList;
import com.chetaru.tribe365_new.API.Models.ModelTheme;
import com.chetaru.tribe365_new.API.Models.ModelTierList;
import com.chetaru.tribe365_new.API.Models.Office;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.databinding.DialogTierListBinding;
import com.chetaru.tribe365_new.databinding.RowTierListBinding;
import com.chetaru.tribe365_new.utility.RecyclerItemClickListener;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Act_AddAction_new extends BaseActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_startdate)
    TextView tv_startdate;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_duedate)
    TextView tv_duedate;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_selecttier)
    TextView tv_selecttier;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_responsible)
    TextView tv_responsible;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_responsible_value)
    TextView tv_responsible_value;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_theme_value)
    TextView tv_theme_value;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_department)
    TextView tv_department;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_department_value)
    TextView tv_department_value;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_description)
    EditText et_description;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bt_submit)
    Button bt_submit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rb_complete)
    RadioButton rb_complete;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rb_started)
    RadioButton rb_started;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rb_notstarted)
    RadioButton rb_notstarted;


    int mYear, mMonth, mDay;
    String startdate = "";
    Utility utility;
    SessionParam sessionParam;
    ArrayList<ModelTierList> tierList;
    String companyString = "";
    ArrayList<ModelOrganisationList> organisationLists;
    ArrayList<Office> officeList;
    List<Department> departmentList;
    List<ModelTheme> themeList;
    List<ModelAddActionUser> userList;
    ArrayList<ModelAddActionUser> modelUserList;
    String status = "";
    String tierId = "";
    String departmentId = "";
    String officeId = "";
    String individualUserId = "";
    int themeCount = 0;
    ModelDepartmentDialog modelDepartmentDialog;
    FloatingActionButton fab;
    DialogTierListBinding binding;
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_add_action_new);
        init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_add_action_new;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }

    public void init() {
        ButterKnife.bind(this);
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        companyString = sessionParam.companyList;
        organisationLists = new ArrayList<>();
        modelUserList = new ArrayList<>();
        officeList = new ArrayList<>();
        departmentList = new ArrayList<>();
        userList = new ArrayList<>();
        modelDepartmentDialog = new ModelDepartmentDialog();
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        baseRequest = new BaseRequest(mContext);
        JSONArray jsonArray = null;
        tierList = new ArrayList<>();
        getTierList();
        api_getTheme();
        try {
            jsonArray = new JSONArray(companyString);
            organisationLists = baseRequest.getDataList(jsonArray, ModelOrganisationList.class);
            //let me explain what we did there, else after a month even I'll not be able to understand this line of code
            for (int i = 0; i < organisationLists.size(); i++) {
                if (sessionParam.orgId.equalsIgnoreCase(organisationLists.get(i).getOrganisationId() + "")) {
                    officeList = organisationLists.get(i).getOffices();
                    return;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getTierList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                tierList = baseRequest.getDataList(jsonArray, ModelTierList.class);
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
        baseRequest.callAPIGET(1, map, ConstantAPI.api_actionTierList);
    }

    public void getDate(final TextView tv) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tv.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        startdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @OnClick({R.id.tribe365, R.id.tv_startdate, R.id.tv_duedate, R.id.tv_theme_value, R.id.tv_selecttier, R.id.tv_responsible_value, R.id.bt_submit, R.id.tv_department_value})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_theme_value:
                dialogTheme();
                break;
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.tv_startdate:
                getDate(tv_startdate);
                break;

            case R.id.tv_duedate:
                getDate(tv_duedate);
                break;

            case R.id.tv_selecttier:
                dialogTier();
                tv_responsible_value.setText("");
                break;

            case R.id.bt_submit:
                if (rb_complete.isChecked()) {
                    status = "Completed";
                    //return;
                }
                if (rb_notstarted.isChecked()) {
                    status = "Not Started";
                    // return;
                }
                if (rb_started.isChecked()) {
                    status = "Started";
                    //return;
                }
                if (status.isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_select_Status));
                    return;
                }
                if (tv_selecttier.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_select_one_tier));
                    return;
                } else if (tv_selecttier.getText().toString().contains("OFFICE") && tv_responsible_value.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_select_responsible));
                    return;
                } else if (tv_selecttier.getText().toString().contains("DEPARTMENT") && tv_responsible_value.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_select_responsible));
                    return;
                } else if (tv_selecttier.getText().toString().contains("INDIVIDUAL") && tv_responsible_value.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_select_responsible));
                    return;
                } else if (tv_responsible_value.getText().toString().isEmpty()) {
                    utility.showToast(mContext,getString(R.string.please_select_responsible));
                    return;
                }

               /* else if(tv_selecttier.getText().toString().contains("OFFICE")||
                        tv_selecttier.getText().toString().equalsIgnoreCase("DEPARTMENT")||
                        tv_selecttier.getText().toString().equalsIgnoreCase("INDIVIDUAL")){
                    if(tv_responsible_value.getText().toString().isEmpty()){
                        utility.showToast(mContext,"Please select Responsible");
                       // return;
                    }

                }*/
                else if (tv_startdate.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_enter_start_date));
                    return;
                } else if (tv_duedate.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_enter_due_date));
                    return;
                } else if (status.isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_select_Status));
                    return;
                } else if (et_description.getText().toString().isEmpty()) {
                    utility.showToast(mContext, getString(R.string.please_enter_description));
                    return;
                } else {
                    apiAddAction();
                }

                break;

            case R.id.tv_responsible_value:
                //If primary or tertiary show all organisation user
                //if office show all office staff
                //if department show all department user list
                //if individual show user department

                if (userList.size() == 0) {
                    utility.showToast(mContext, getString(R.string.no_staff_found));
                } else {
                    dialogResponsiblePerson(tv_selecttier.getText().toString());
                }

                break;
            case R.id.tv_department_value:
                //If primary or tertiary show all organisation user
                //if office show all office staff
                //if department show all department user list
                //if individual show user department
                if (tv_selecttier.getText().toString().equalsIgnoreCase("OFFICE")) {
                    dialogResponsibleDepartment("OFFICE");
                }
                if (tv_selecttier.getText().toString().equalsIgnoreCase("DEPARTMENT")) {
                    dialogResponsibleDepartment("DEPARTMENT");
                }
                //dialogResponsible("DEPARTMENT");
                //api_getUserByType(tv_selecttier.getText().toString());
                break;
        }
    }

    public void dialogTier() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tier_list);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final ImageView iv_close = dialog.findViewById(R.id.iv_close);
        final TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText("SELECT TIER");
        final RecyclerView rv_list = dialog.findViewById(R.id.rv_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        rv_list.setAdapter(new Ad_TierList(tierList, mContext));
        rv_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                tv_selecttier.setText(tierList.get(position).getName());
                tv_department_value.setText("");
                userList.clear();
                tierId = tierList.get(position).getId() + "";
                dialog.dismiss();
                if (tierList.get(position).getName().contains("DEPARTMENT")) {
                    tv_department.setVisibility(View.VISIBLE);
                    tv_department_value.setVisibility(View.VISIBLE);
                    tv_department.setText("RESPONSIBLE DEPARTMENT");
                    if (departmentList.size() == 0) {
                        getDepartment();
                    } else {

                    }

                } else if (tierList.get(position).getName().equalsIgnoreCase("OFFICE")) {
                    tv_department.setText("RESPONSIBLE OFFICE");
                    tv_department.setVisibility(View.VISIBLE);
                    tv_department_value.setVisibility(View.VISIBLE);


                } else {
                    tv_department.setVisibility(View.GONE);
                    tv_department_value.setVisibility(View.GONE);
                    api_getUserByType(tv_selecttier.getText().toString());
                }
                //getDotDetail();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        iv_close.setOnClickListener(new View.OnClickListener() {
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

    public void dialogResponsibleDepartment(final String title) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tier_list);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final ImageView iv_close = dialog.findViewById(R.id.iv_close);
        final RecyclerView rv_list = dialog.findViewById(R.id.rv_list);
        final TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText(title);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        if (title.contains("OFFICE")) {
            rv_list.setAdapter(new Ad_OfficeDialog(officeList, mContext));
        } else if (title.contains("DEPARTMENT")) {
            rv_list.setAdapter(new Ad_Departmentdialog(departmentList, mContext));
        } else {
            rv_list.setAdapter(new Ad_UserListDialog(userList, mContext));
        }

        rv_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                /*if (sessionParam.role.equalsIgnoreCase("1")) {
                    departmentId = "";
                    officeId = "";
                    individualUserId = "";
                }*/

                if (title.contains("OFFICE")) {
                    officeId = officeList.get(position).getOfficeId() + "";
                    tv_department_value.setText(officeList.get(position).getOffice());
                    tv_responsible_value.setText("");
                    api_getUserByType("office");
                } else if (title.contains("DEPARTMENT")) {
                    tv_department_value.setText(departmentList.get(position).getName());
                    departmentId = departmentList.get(position).getDepartmentId() + "";
                    tv_responsible_value.setText("");
                    api_getUserByType("department");
                } else {
                    tv_responsible_value.setText(userList.get(position).getName());

                }
                dialog.dismiss();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(lp);
        dialog.show();
    }


    public void dialogResponsiblePerson(final String title) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tier_list);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final ImageView iv_close = dialog.findViewById(R.id.iv_close);
        final RecyclerView rv_list = dialog.findViewById(R.id.rv_list);
        final TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText("STAFF");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        rv_list.setAdapter(new Ad_UserListDialog(userList, mContext));
        /*if (title.contains("OFFICE")) {
            rv_list.setAdapter(new Ad_OfficeDialog(officeList, mContext));
        } else if (title.contains("DEPARTMENT")) {
            rv_list.setAdapter(new Ad_Departmentdialog(departmentList, mContext));
        } else {

        }*/

        rv_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

              /*  if (sessionParam.role.equalsIgnoreCase("1")) {
                    departmentId = "";
                    officeId = "";
                    individualUserId = "";
                }*/
                tv_responsible_value.setText(userList.get(position).getName());
                individualUserId = userList.get(position).getId() + "";

               /* if (title.contains("OFFICE")) {
                    officeId = officeList.get(position).getOfficeId() + "";
                    tv_department_value.setText(officeList.get(position).getOffice());
                    api_getUserByType("office");
                } else if (title.contains("DEPARTMENT")) {
                    tv_department_value.setText(departmentList.get(position).getName());
                    departmentId = departmentList.get(position).getDepartmentId() + "";
                    api_getUserByType("department");
                } else {
                    tv_responsible_value.setText(userList.get(position).getName());
                    individualUserId = userList.get(position).getRoleId() + "";
                }*/
                dialog.dismiss();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        iv_close.setOnClickListener(new View.OnClickListener() {
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

    public void api_getUserByType(final String type) {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    userList = baseRequest.getDataList(jsonArray, ModelAddActionUser.class);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (type.equalsIgnoreCase("")) {
                    // dialogResponsible("SELECT STAFF");
                } else {
                    //  dialogResponsible("SELECT DEPARTMENT");
                }

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
        String orgId = "";
        if (sessionParam.role.equalsIgnoreCase("1")) {
            orgId = sessionParam.companyOrgId;
        } else {
            orgId = sessionParam.orgId;
        }

        String type1 = "";
        String typeid = "";
        if (type.equalsIgnoreCase("primary") || type.equalsIgnoreCase("tertiary") || type.equalsIgnoreCase("secondary") || type.equalsIgnoreCase("individual")) {
            type1 = "organisation";
            if (sessionParam.role.equalsIgnoreCase("1")) {
                typeid = sessionParam.companyOrgId;
            } else {
                typeid = sessionParam.orgId;
            }

        } else if (type.equalsIgnoreCase("office")) {
            type1 = "office";
            typeid = officeId;
        } else {
            type1 = "department";
            typeid = departmentId;
        }
        //JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.companyOrgId
        JsonObject object = Functions.getClient().getJsonMapObject("type", type1,
                "typeId", typeid

        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getUserByType);
    }

    public void getDepartment() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                modelDepartmentDialog = gson.fromJson(object.toString(), ModelDepartmentDialog.class);
                departmentList = modelDepartmentDialog.getDepartments();


            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                //utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                //utility.showToast(mContext, message);
            }
        });
        String orgId = "";
        if (sessionParam.role.equalsIgnoreCase("1")) {
            orgId = sessionParam.companyOrgId;
        } else {
            orgId = sessionParam.orgId;
        }

        JsonObject object = Functions.getClient().getJsonMapObject("orgId", orgId
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getDepartmentuserList);
    }

    public void apiAddAction() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                finish();
                startActivity(new Intent(mContext, Act_ActionList.class));
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
        String orgId = "";
        if (sessionParam.role.equalsIgnoreCase("1")) {
            orgId = sessionParam.companyOrgId;
        } else {
            orgId = sessionParam.orgId;
        }
        //companyorgId is used in action only

        if (tv_selecttier.getText().toString().equalsIgnoreCase("primary") ||
                tv_selecttier.getText().toString().equalsIgnoreCase("tertiary") ||
                tv_selecttier.getText().toString().equalsIgnoreCase("secondary") ||
                tv_selecttier.getText().toString().equalsIgnoreCase("individual")) {
            officeId = "";
            departmentId = "";
        }


        JsonArray themeIdArray = new JsonArray();
        JsonObject main_object = new JsonObject();
        for (int ui = 0; ui < themeList.size(); ui++) {
            if (themeList.get(ui).isSelected())
                themeIdArray.add(themeList.get(ui).getId() + "");
        }


        main_object.addProperty("userId", sessionParam.id);
        main_object.addProperty("startedDate", tv_startdate.getText().toString());
        main_object.addProperty("dueDate", tv_duedate.getText().toString());
        main_object.addProperty("tierId", tierId);
        main_object.addProperty("departmentId", departmentId);
        main_object.addProperty("officeId", officeId);
        main_object.addProperty("IndividualUserId", "");
        main_object.addProperty("orgStatus", status);
        main_object.addProperty("orgId", orgId);
        main_object.addProperty("description", et_description.getText().toString());
        main_object.addProperty("responsibleUserId", individualUserId);
        main_object.add("themeId", themeIdArray);

//        JsonObject object = Functions.getClient().getJsonMapObject(
//                "userId", sessionParam.id,
//                "startedDate", tv_startdate.getText().toString(),
//                "dueDate", tv_duedate.getText().toString(),
//                "tierId", tierId,
//                "departmentId", departmentId,
//                "officeId", officeId,
//                "IndividualUserId", "",
//                "orgStatus", status,
//                "orgId", orgId,
//                "description", et_description.getText().toString(),
//                "responsibleUserId", individualUserId
//
//        );

        baseRequest.callAPIPost(1, main_object, ConstantAPI.addAction);
    }

    public void api_getTheme() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

                try {
                    JSONObject jsonObject = (JSONObject) object;
                    JSONArray jsonArray = jsonObject.optJSONArray("themeList");
                    themeList = baseRequest.getDataList(jsonArray, ModelTheme.class);

                } catch (Exception e) {

                    e.printStackTrace();
                }

                //userList = modelDepartmentDialog.getUsers();
                /* if(type.equalsIgnoreCase("user")){
                    dialogResponsible("SELECT STAFF");
                }else{
                    dialogResponsible("SELECT DEPARTMENT");
                }*/
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
        String orgId = "";
        if (sessionParam.role.equalsIgnoreCase("1")) {
            orgId = sessionParam.companyOrgId;
        } else {
            orgId = sessionParam.orgId;
        }

        JsonObject object = Functions.getClient().getJsonMapObject("orgId", orgId
        );
        baseRequest.callAPIPost(1, object,ConstantAPI.getThemeList);
    }

    public void dialogTheme() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tier_list);
        // binding=DialogTierListBinding.inflate(LayoutInflater.from(mContext));
        //dialog.setContentView(binding.getRoot());
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        final RecyclerView rvList = dialog.findViewById(R.id.rv_list);
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        fab = dialog.findViewById(R.id.fab);

        tvTitle.setText("Select Risks");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(new Ad_Theme(themeList, mContext));

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (themeCount > 1) {
                    tv_theme_value.setText(themeCount + " selected");
                } else {
                    for (int i = 0; i < themeList.size(); i++) {
                        if (themeList.get(i).isSelected()) {
                            tv_theme_value.setText(themeList.get(i).getTitle());
                        }
                    }
                }

                if (themeCount == 0) {
                    tv_theme_value.setText("");
                }
                dialog.dismiss();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (themeCount > 1) {
                    tv_theme_value.setText(themeCount + " selected");
                } else {
                    for (int i = 0; i < themeList.size(); i++) {
                        if (themeList.get(i).isSelected()) {
                            tv_theme_value.setText(themeList.get(i).getTitle());
                        }
                    }
                }

                if (themeCount == 0) {
                    tv_theme_value.setText("");
                }
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class Ad_Theme extends RecyclerView.Adapter<Ad_Theme.ViewHolder> {

        List<ModelTheme> list = new ArrayList<>();
        Context context;

        public Ad_Theme(List<ModelTheme> list, Context context) {
            this.list = list;
            notifyDataSetChanged();
            this.context = context;
        }

        @NonNull
        @Override
        public Ad_Theme.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(mContext);
            RowTierListBinding binding=RowTierListBinding.inflate(inflater,parent,false);
            //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tier_list, parent, false);
            return new Ad_Theme.ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.itemBinding.tvTier.setText(list.get(position).getTitle());
            if (list.get(position).isSelected()) {
                holder.itemBinding.tvTier.setTextColor(context.getResources().getColor(R.color.white));
                holder.itemBinding.llMain.setBackgroundColor(context.getResources().getColor(R.color.red));
                fab.setVisibility(View.VISIBLE);
                //list.get(position).setSelected(false);
            } else {
                holder.itemBinding.tvTier.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.itemBinding.llMain.setBackgroundColor(context.getResources().getColor(R.color.bg_light_grey));
            }
            holder.itemBinding.tvTier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position).isSelected()) {
                        holder.itemBinding.tvTier.setTextColor(context.getResources().getColor(R.color.textcolor));
                        holder.itemBinding.llMain.setBackgroundColor(context.getResources().getColor(R.color.bg_light_grey));
                        list.get(position).setSelected(false);
                        themeCount--;
                    } else {
                        holder.itemBinding.tvTier.setTextColor(context.getResources().getColor(R.color.white));
                        holder.itemBinding.llMain.setBackgroundColor(context.getResources().getColor(R.color.red));
                        list.get(position).setSelected(true);
                        themeCount++;
                    }

                    if (themeCount == 0) {
                        fab.setVisibility(View.GONE);
                    } else {
                        fab.setVisibility(View.VISIBLE
                        );
                    }

                }
            });

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

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_tier;
            LinearLayout ll_main;
            RowTierListBinding itemBinding;
            public ViewHolder(RowTierListBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding=itemBinding;
                /*tv_tier = itemView.findViewById(R.id.tv_tier);
                ll_main = itemView.findViewById(R.id.ll_main);*/
            }
        }
    }


}
