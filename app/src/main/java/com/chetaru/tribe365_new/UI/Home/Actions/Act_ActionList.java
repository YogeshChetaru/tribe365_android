package com.chetaru.tribe365_new.UI.Home.Actions;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.Ad_ActionList;
import com.chetaru.tribe365_new.API.Models.ModelACtionList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_ActionList extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_error_msg)
    TextView tv_error_msg;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.spinner_parent_ll)
    LinearLayout spinner_parent_ll;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fab)
    FloatingActionButton fab;


    ArrayList<ModelACtionList> list;
    ArrayList<ModelACtionList> list2;
    Utility utility;
    SessionParam sessionParam;
    String[] type = {"All Tier", "Primary", "Secondary", "Tertiary", "Office", "Department", "Individual"};
    ArrayAdapter aa;
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_action_list);
        ButterKnife.bind(this);
        init();
        tribe365.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
    }

    /* is used to initialise all the views
     */
    public void init() {
        utility = new Utility();
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        spinner.setOnItemSelectedListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        rv_list.setNestedScrollingEnabled(false);
        //Creating the ArrayAdapter instance having the country list
        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        /* rv_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));*/
        getActionList();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, Act_AddAction_new.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActionList();
    }



    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        //Toast.makeText(getApplicationContext(),type[position] , Toast.LENGTH_LONG).show();
        if (list.size() > 0) {
            if (type[position].equalsIgnoreCase("primary")) {
                list2.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTier().equalsIgnoreCase("primary")) {
                        list2.add(list.get(i));
                    }
                }

            } else if (type[position].equalsIgnoreCase("secondary")) {
                list2.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTier().equalsIgnoreCase("secondary")) {
                        list2.add(list.get(i));
                    }
                }

            } else if (type[position].equalsIgnoreCase("tertiary")) {
                list2.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTier().equalsIgnoreCase("tertiary")) {
                        list2.add(list.get(i));
                    }
                }

            } else if (type[position].equalsIgnoreCase("office")) {
                list2.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTier().equalsIgnoreCase("office")) {
                        list2.add(list.get(i));
                    }
                }

            } else if (type[position].equalsIgnoreCase("department")) {
                list2.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTier().equalsIgnoreCase("department")) {
                        list2.add(list.get(i));
                    }
                }

            } else if (type[position].equalsIgnoreCase("individual")) {
                list2.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTier().equalsIgnoreCase("individual")) {
                        list2.add(list.get(i));
                    }
                }

            } else if (type[position].equalsIgnoreCase("all tier")) {
                rv_list.setAdapter(new Ad_ActionList(list, mContext));
                rv_list.setVisibility(View.VISIBLE);
                tv_error_msg.setVisibility(View.GONE);
                //list2 = list;
                return;
            }/*else{

            }*/
            rv_list.setAdapter(new Ad_ActionList(list2, mContext));
            if (list2.size() < 1) {
                rv_list.setVisibility(View.GONE);
                tv_error_msg.setVisibility(View.VISIBLE);
                //utility.showToast(mContext, "No Actions found");
            } else {
                rv_list.setVisibility(View.VISIBLE);
                tv_error_msg.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    /*API call to get all the action list added by the user
     */
    public void getActionList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                list2 = baseRequest.getDataList(jsonArray, ModelACtionList.class);
                list = baseRequest.getDataList(jsonArray, ModelACtionList.class);
                rv_list.setAdapter(new Ad_ActionList(list2, mContext));
                if (list2.size() < 1) {
                    rv_list.setVisibility(View.GONE);
                    tv_error_msg.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    spinner_parent_ll.setVisibility(View.GONE);
                    // utility.showToast(mContext, "No Actions found");
                }else{
                    spinner.setVisibility(View.VISIBLE);
                    spinner_parent_ll.setVisibility(View.VISIBLE);
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
        String orgId = "";
        if (sessionParam.role.equalsIgnoreCase("1")) {
            orgId = sessionParam.companyOrgId;
        } else {
            orgId = sessionParam.orgId;
        }
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", orgId
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getActionList);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_action_list;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}
