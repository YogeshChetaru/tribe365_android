package com.chetaru.tribe365_new.UI.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.KudosAwardTitleList;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.award.Ad_kudosAward_list;
import com.chetaru.tribe365_new.Notification.Act_NotificationList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_Award extends BaseActivity {

    /**************** create variable to access Award data ***************/
    SessionParam sessionParam;
    Utility utility;
    BaseRequest baseRequest;
    List<KudosAwardTitleList> kudosAwardTitleLists;
    String orgId="";
    /************************** initialize Bind view *************/
    @BindView(R.id.iv_top_companylogo)
    ImageView iv_top_companylogo;
    @BindView(R.id.index_eng_image)
    ImageView index_eng_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_index_eng_number)
    TextView tv_index_eng_number;
    @BindView(R.id.show_award_rv)
    RecyclerView show_award_rv;
    @BindView(R.id.iv_notic)
    ImageView iv_notic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        utility= new Utility();
        sessionParam= new SessionParam(mContext);
        setSessionParam();
        kudosAwardTitleLists= new ArrayList<>();

        //setContentView(R.layout.activity_act_award);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        show_award_rv.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        show_award_rv.addItemDecoration(itemDecoration);
        show_award_rv.addItemDecoration(new ItemOffsetDecoration(mContext,R.dimen.item_offset_small));

        iv_notic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Act_NotificationList.class));
            }
        });
        /**************************** calling Api ****************/

        getKudosAwardList();
       // Ad_award_list adAwardList= new Ad_award_list(kudosAwardTitleLists,mContext);
    }
    /******* set session data **********/
    public void setSessionParam(){
        if(orgId.equals("")){
            orgId= sessionParam.orgId;
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);
        }
    }




  /***************** handel api response **************************/
    public void getKudosAwardList(){
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                    //JSONArray jsonArray = new JSONArray(object.toString());
                    //JSONObject jsonObject= new JSONObject(object.toString());
                    JSONArray jsonArray = new JSONArray(object.toString());
                    kudosAwardTitleLists = baseRequest.getDataList(jsonArray, KudosAwardTitleList.class);
                  //  kudosAwardTitleLists= gson.fromJson(jsonObject.toString(),KudosAwardTitleList.class);
                    Ad_kudosAward_list adKudosAwardList= new Ad_kudosAward_list(kudosAwardTitleLists, mContext, new Ad_kudosAward_list.MoreKudosListener() {
                        @Override
                        public void loadMoreClick(KudosAwardTitleList awardTitleList) {
                            Intent intent=new Intent(mContext,Act_AwardDescriptions.class);
                            intent.putExtra("kudosId",awardTitleList.getId());
                            intent.putExtra("kudosName",awardTitleList.getDotName());
                            startActivity(intent);
                        }
                    });
                    show_award_rv.setAdapter(adKudosAwardList);

                }catch ( Exception e){
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
        JsonObject object= Functions.getClient().getJsonMapObject("","");
        baseRequest.callAPIPostWOLoader(1, object, ConstantAPI.dotValuekudoAwardDetail);

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_act_award;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}