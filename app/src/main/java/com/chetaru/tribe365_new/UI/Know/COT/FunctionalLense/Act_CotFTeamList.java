package com.chetaru.tribe365_new.UI.Know.COT.FunctionalLense;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.COTAdapters.Ad_UserList;
import com.chetaru.tribe365_new.API.Models.ModelAddActionUser;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_CotFTeamList extends BaseActivity {
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.search_bar)
    SearchView search_bar;
    Utility utility;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    List<ModelAddActionUser> userList = new ArrayList<>();

    Ad_UserList ad_userList;
    Activity activity;

    private static void showKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        InputMethodManager methodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert methodManager != null && view != null;
        methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cot_fteam_list);
        init();
        search_bar.setIconifiedByDefault(false);
        search(search_bar);
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
        rv_list.setLayoutManager(layoutManager);
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
                    ad_userList = new Ad_UserList(userList, mContext);
                    rv_list.setAdapter(ad_userList);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_home_land;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
