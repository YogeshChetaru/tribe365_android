package com.chetaru.tribe365_new.UI.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.AwardList;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.award.Ad_AwardDescriptionList;
import com.chetaru.tribe365_new.Notification.Act_NotificationList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.PaginationAdapterCallback;
import com.chetaru.tribe365_new.utility.PaginationScrollListener;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_AwardDescriptions extends BaseActivity implements PaginationAdapterCallback {


    /**************** create variable to access Award data ***************/
    private static final int PAGE_START=1;
    private static int TOTAL_PAGES = 1;
    SessionParam sessionParam;
    Utility utility;
    BaseRequest baseRequest;
    List<AwardList> awardLists;
    String orgId="";
    String kudosId="", kudosName="";
    private boolean isLoading = false;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    Ad_AwardDescriptionList descriptionAdapter;
    LinearLayoutManager layoutManager;

    /************************** initialize Bind view *************/
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_top_companylogo)
    ImageView iv_top_companylogo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.index_eng_image)
    ImageView index_eng_image;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_index_eng_number)
    TextView tv_index_eng_number;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_notic)
    ImageView iv_notic;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_name_tv)
    TextView title_name_tv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_award_desc_rv)
    RecyclerView show_award_desc_rv;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.show_award_tv)
    TextView show_award_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_award_descriptions);

        ButterKnife.bind(this);
        utility= new Utility();
        sessionParam= new SessionParam(mContext);
        setSessionParam();
        awardLists= new ArrayList<>();

        //setContentView(R.layout.activity_act_award);
        layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        show_award_desc_rv.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        show_award_desc_rv.addItemDecoration(itemDecoration);
        show_award_desc_rv.addItemDecoration(new ItemOffsetDecoration(mContext,R.dimen.item_offset_small));
        descriptionAdapter= new Ad_AwardDescriptionList(awardLists,mContext);
        show_award_desc_rv.setAdapter(descriptionAdapter);
        try {
            kudosId = getIntent().getStringExtra("kudosId");
            kudosName=getIntent().getStringExtra("kudosName");
            title_name_tv.setText(kudosName.trim());
        }catch (Exception e){
            e.printStackTrace();
        }


        iv_notic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Act_NotificationList.class));
            }
        });
        show_award_desc_rv.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading= true;
                currentPage +=1;
                api_LoadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        /**************** calling Api ***************/
        getKudosAwardDetail();
    }

    /****************** set session param **************/
    public void setSessionParam(){
        if(orgId.equals("")){
            orgId= sessionParam.orgId;
            Picasso.get().load(sessionParam.organisation_logo).into(iv_top_companylogo);
        }
    }



   /***************** get Api response ***********/
    public void getKudosAwardDetail(){
        currentPage=PAGE_START;
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    awardLists.clear();
                    JSONObject jsonObject = new JSONObject(Json);
                    TOTAL_PAGES = jsonObject.optInt("totalPageCount");
                    JSONArray jsonArray = new JSONArray(object.toString());
                    //JSONArray jsonArray= new JSONArray(object.toString());
                    awardLists= baseRequest.getDataList(jsonArray,AwardList.class);
                    descriptionAdapter.addAll(awardLists);
                    if (currentPage<=TOTAL_PAGES) descriptionAdapter.addLoadingFooter();
                    else isLastPage=true;

                    if (awardLists.size()>0){
                        show_award_tv.setVisibility(View.GONE);
                        show_award_desc_rv.setVisibility(View.VISIBLE);
                    }else {
                        show_award_tv.setVisibility(View.VISIBLE);
                        show_award_desc_rv.setVisibility(View.GONE);
                    }
                }catch (Exception e){
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
        JsonObject jsonObject= Functions.getClient().getJsonMapObject("dotValueId", kudosId,"page",currentPage+"");
        baseRequest.callAPIPost(1,jsonObject,ConstantAPI.kudoAwardDetail);
    }

    /**************** call load more Api *************************/
    public void api_LoadNextPage(){
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    awardLists = baseRequest.getDataList(jsonArray, AwardList.class);
                    descriptionAdapter.removeLoadingFooter();
                    isLoading=false;
                    descriptionAdapter.addAll(awardLists);
                    if (currentPage != TOTAL_PAGES) descriptionAdapter.addLoadingFooter();
                    else isLastPage= true;

                }catch (Exception e){
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
        JsonObject jsonObject= Functions.getClient().getJsonMapObject("dotValueId", kudosId,"page", currentPage+"");
        baseRequest.callAPIPost(1,jsonObject,ConstantAPI.kudoAwardDetail);
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_award_descriptions;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }

    /********************* handel callback Listener ***************/
    @Override
    public void retryPageLoad() {
        api_LoadNextPage();
    }
}