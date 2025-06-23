package com.chetaru.tribe365_new.UI.Know.COT;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.COTAdapters.Ad_Cot_Description;
import com.chetaru.tribe365_new.API.Models.COTBeans.ModelCotDescription;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_Cot_Team_Description extends BaseActivity {
    @BindView(R.id.tribe365)
    ImageView title_tribe365;
    @BindView(R.id.rv_description_list)
    RecyclerView rv_description_list;
    BaseRequest baseRequest;
    Utility utility;
    SessionParam sessionParam;
    ArrayList<ModelCotDescription> list_description = new ArrayList<>();
    Ad_Cot_Description ad_cot_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_category_description_list);
        init();
        title_tribe365.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
    }

    private void init() {
        ButterKnife.bind(this);
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(title_tribe365);
            }
        }
        getOrganisationList();
    }

    public void getOrganisationList() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                list_description = baseRequest.getDataList(jsonArray, ModelCotDescription.class);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                rv_description_list.setLayoutManager(layoutManager);
                rv_description_list.setAdapter(new Ad_Cot_Description(list_description, mContext));
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
        baseRequest.callAPIGET(1, map, ConstantAPI.api_getCOTMapperSummary);
    }


    /****************** layout initialize ************/
    @Override
    public int getLayoutId() {
        return R.layout.act_category_description_list;
    }

    /**************** navigation id *******************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
