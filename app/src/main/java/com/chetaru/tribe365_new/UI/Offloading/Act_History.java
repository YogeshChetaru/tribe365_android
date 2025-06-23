package com.chetaru.tribe365_new.UI.Offloading;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.IOT_Adapter.Ad_historyList;
import com.chetaru.tribe365_new.API.Models.ModelHistory;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_History extends BaseActivity {

    SessionParam sessionParam;
    Utility utility;
    MarshMallowPermission marshMallowPermission;
    String deviceId = "";
    ArrayList<ModelHistory> list;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.tribe365)
    ImageView tribe365;
    String json = "";
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_history);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        //activity = this;
        marshMallowPermission = new MarshMallowPermission(this);
        list = new ArrayList<>();
        sessionParam = new SessionParam(mContext);

        tribe365.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
    }

    public void apihistory() {
        utility = new Utility();
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                JSONArray jsonArray;
                jsonArray = (JSONArray) object;
                list = baseRequest.getDataList(jsonArray, ModelHistory.class);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                rv_list.setLayoutManager(linearLayoutManager);
                rv_list.setAdapter(new Ad_historyList(list, mContext, object.toString()));
                rv_list.setVisibility(View.VISIBLE);
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
                "userId", sessionParam.id
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getFeedbackDetail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessionParam = new SessionParam(mContext);
        rv_list = findViewById(R.id.rv_list);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
//        rv_list.setLayoutManager(layoutManager);
       /* TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        deviceId = TelephonyMgr.getDeviceId();*/
        deviceId = utility.getAndroidID(this);
        sessionParam.saveDeviceId(mContext, deviceId);


        //apihistory();
    }
    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_history;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_offloading;
    }
}
