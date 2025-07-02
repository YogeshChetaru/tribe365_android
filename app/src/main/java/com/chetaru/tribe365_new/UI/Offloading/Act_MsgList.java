package com.chetaru.tribe365_new.UI.Offloading;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.IOT_Adapter.Ad_MsgList;
import com.chetaru.tribe365_new.API.Models.ModelMessageList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.MarshMallowPermission;
import com.chetaru.tribe365_new.utility.RecyclerItemClickListener;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_MsgList extends BaseActivity {
    SessionParam sessionParam;
    Utility utility;
    MarshMallowPermission marshMallowPermission;
    String deviceId = "";
    ArrayList<ModelMessageList> list;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe;
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.act_msg_list);
    }

    public void init() {
        ButterKnife.bind(this);
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        //activity = this;
        marshMallowPermission = new MarshMallowPermission(this);
        list = new ArrayList<>();
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe);
            }
        }
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rv_list.setLayoutManager(linearLayoutManager);
        rv_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(mContext, Act_Chat.class)
                        .putExtra("changeItId", list.get(position).getmId()));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        tribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });
        //getInbox();
    }

    public void apihistory() {
        utility = new Utility();
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());

                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("inbox");
                    list = baseRequest.getDataList(jsonArray, ModelMessageList.class);
                    rv_list.setAdapter(new Ad_MsgList(list, mContext));
                    if (list.size() == 0) {
                        utility.showToast(mContext, getString(R.string.no_msg_found));
                    }
                } catch (JSONException e) {
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

        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id
                //JsonObject object = Functions.getClient().getJsonMapObject("orgId", "9"
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getInboxChatList );


//-------------------------OLD code-------------------
//        Map<String, String> map = new HashMap<>();
//        map.put("app_name", "tribe365");
//        map.put("email_id", sessionParam.email);
        //http://live.thechangeconsultancy.co/demo_tellsid/
        //baseRequest.callAPIGETCustomURLTellSid(1, map, "http://tellsid.softintelligence.co.uk/index.php/apitellsid/getinboxitem/"); // live
        //baseRequest.callAPIGETCustomURLTellSid(1, map, "http://live.thechangeconsultancy.co/demo_tellsid/index.php/apitellsid/getinboxitem/"); // test
        //demo---------------------------------
//        baseRequest.callAPIGETCustomURLTellSid(1, map, "http://demo.thechangeconsultancy.co/tellsid/index.php/apitellsid/getinboxitem/"); // live

        //live----------------------------------
//       baseRequest.callAPIGETCustomURLTellSid(1, map, "http://tellsid.softintelligence.co.uk/index.php/apitellsid/getinboxitem/"); // live
    }

    @Override
    protected void onResume() {
        super.onResume();
        {
            init();
            apihistory();
        }
    }
    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_msg_list;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_offloading;
    }
}
