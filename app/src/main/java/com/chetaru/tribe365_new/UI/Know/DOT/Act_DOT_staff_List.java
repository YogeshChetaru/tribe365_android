package com.chetaru.tribe365_new.UI.Know.DOT;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.Ad_DOT_Staff_List;
import com.chetaru.tribe365_new.API.Models.ModelAddActionUser;
import com.chetaru.tribe365_new.API.Models.ModelUser;
import com.chetaru.tribe365_new.API.Models.Office;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_DOT_staff_List extends BaseActivity {
    @BindView(R.id.tribe365)
    ImageView tribe365;
    Utility utility;
    @BindView(R.id.rv_staff_list)
    RecyclerView rv_staff_list;
    @BindView(R.id.search_bar)
    SearchView search_bar;
    @BindView(R.id.fb_select_staff)
    FloatingActionButton fb_select_staff;
    ArrayList<ModelUser> userArrayList;
    String officeId;
    ArrayList<Office> officesList;
    String officeName = "";
    String orgId = "";
    Activity activity;
    SessionParam sessionParam;
    Ad_DOT_Staff_List ad_userList;
    List<ModelAddActionUser> userList = new ArrayList<>();
    String toUserId = "", dotId = "", dotBeliefId = "", dotValueId = "", dotValueNameId = "", bubbleFlag = "", dotvalueName = "";
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_motivation_stafflist);
        init();
        search_bar.setIconifiedByDefault(false);
        search(search_bar);
        toUserId = sessionParam.id;
        dotId = getIntent().getStringExtra("dotId");
        dotBeliefId = getIntent().getStringExtra("dotBeliefId");
        dotValueId = getIntent().getStringExtra("dotValueId");
        dotValueNameId = getIntent().getStringExtra("dotValueNameId");
        dotvalueName = getIntent().getStringExtra("dotvalueName");

        Log.e("Passing=>", "toUserId=>" + toUserId + " dotId=>" + dotId + " dotBeliefId=>" + dotBeliefId + " dotValueId=>" + dotValueId + " dotValueNameId=>" + dotValueNameId);
        tribe365.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
    }

    public void init() {
        ButterKnife.bind(this);
        utility = new Utility();
        activity = this;
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_staff_list.setLayoutManager(layoutManager);
        api_getUserByType();

    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ad_userList.getFilter().filter(newText);
                return true;
            }
        });
    }

    public void api_getUserByType() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    userList = baseRequest.getDataList(jsonArray, ModelAddActionUser.class);

                    for (int i = 0; i < userList.size(); i++) {
                        if (sessionParam.id.equalsIgnoreCase(userList.get(i).getId().toString())) {
                            userList.remove(i);
                        }
                    }
                    ad_userList = new Ad_DOT_Staff_List(userList, mContext, dotId, dotBeliefId, dotValueId, dotValueNameId, dotvalueName, fb_select_staff);
                    rv_staff_list.setAdapter(ad_userList);
                } catch (JSONException e) {
                    e.printStackTrace();
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

        String typeid = "";
        if (sessionParam.role.equalsIgnoreCase("1")) {
            typeid = sessionParam.companyOrgId;
        } else {
            typeid = sessionParam.orgId;
        }
        //JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.companyOrgId
        JsonObject object = Functions.getClient().getJsonMapObject("type", "organisation",
                //"typeId", ""
                "typeId", typeid

        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getUserByType);
    }

    /*************** layout initialize **********/
    @Override
    public int getLayoutId() {
        return R.layout.act_admin_motivation_stafflist;
    }

    /************* navigation id *********/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }

}
