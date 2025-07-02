package com.chetaru.tribe365_new.UI.Know;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.KnowHome.KudosAwardList;
import com.chetaru.tribe365_new.API.Models.KnowHome.KudosAwardResponse;
import com.chetaru.tribe365_new.API.Models.Kudos.GroupKudosList;
import com.chetaru.tribe365_new.API.Models.ModelAddActionUser;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_UserListDialog;
import com.chetaru.tribe365_new.Adapter.KnowHome.Ad_kudosAwardPagination;
import com.chetaru.tribe365_new.Adapter.award.Ad_AwardUserList;
import com.chetaru.tribe365_new.Adapter.award.Ad_KudosAwardGroupList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.PaginationAdapterCallback;
import com.chetaru.tribe365_new.utility.PaginationScrollListener;
import com.chetaru.tribe365_new.utility.RecyclerItemClickListener;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_Award_List extends BaseActivity implements PaginationAdapterCallback {
    private static final int PAGE_START= 1;
    private static final int TOTAL_PAGES = 1;
    private boolean isLoading = false;
    private int currentPage = PAGE_START;
    private boolean isLastPage= false;
    ArrayList<ModelAddActionUser> modelUserList;
    String userId = "";
    String firstName="",lastName="";


    Utility utility;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    String orgId="";


    @BindView(R.id.tribe365)
    ImageView imageLogo;
    @BindView(R.id.rv_kudos_award)
    RecyclerView rv_kudos_award;
    Ad_kudosAwardPagination ad_KudosAward;
    @BindView(R.id.sp_office)
    Spinner sp_office;
    @BindView(R.id.sp_depart)
    Spinner sp_depart;
    @BindView(R.id.tv_userName)
    TextView tv_userName;
    @BindView(R.id.no_award_tv)
    TextView no_award_tv;


    KudosAwardResponse awardResponse;
    List<KudosAwardList> kudosAwardList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_award_list);
        utility= new Utility();
        sessionParam= new SessionParam(mContext);
        ButterKnife.bind(this);
        setSessionData();


        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        rv_kudos_award.setLayoutManager(layoutManager);
        rv_kudos_award.setHasFixedSize(true);
        Drawable mDivider= ContextCompat.getDrawable(this,R.drawable.divider);
        DividerItemDecoration htDecoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        assert  mDivider !=null;
        htDecoration.setDrawable(mDivider);
        ad_KudosAward= new Ad_kudosAwardPagination(this);
        //rv_kudos_award.setAdapter(ad_KudosAward);
        rv_kudos_award.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage +=1;
               // api_loadNextPage();

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
        /***************** get User Selection List***************************/
        api_getUserByType();
        /************ get Award List Api ***********************/
        getAward("");
        tv_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogResponsible();
            }
        });
        tv_userName.setText("All User");
        //setAwardData();


    }



    public void setAwardData(){
       /* KudosAwardList awardList= new KudosAwardList();
        awardList.setId(1);
        awardList.setDescription("Fantastic energy and positivity as always Oliver");
        awardList.setDate("09 Sep 2021,06:12pm");
        awardList.setUserName("Oliver Randall");
        kudosAwardList.add(awardList);

        KudosAwardList awardList1= new KudosAwardList();
        awardList1.setId(2);
        awardList1.setUserName("Dan Webber");
        awardList1.setDate("09 sep 2021,06:12pm");
        awardList1.setDescription("Fantastic energy and positivity as always Dan");
        kudosAwardList.add(awardList1);

        KudosAwardList awardList2= new KudosAwardList();
        awardList2.setId(3);
        awardList2.setUserName("Holly Dickinsin");
        awardList2.setDate("09 sep 2021,06:12pm");
        awardList2.setDescription("Fantastic energy and positivity as always Holly");
        kudosAwardList.add(awardList2);

        KudosAwardList awardList3= new KudosAwardList();
        awardList3.setId(4);
        awardList3.setUserName("Oliver Randall");
        awardList3.setDate("09 sep 2021,06:12pm");
        awardList3.setDescription("Fantastic energy and positivity as always Oliver");
        kudosAwardList.add(awardList3);

        KudosAwardList awardList4= new KudosAwardList();
        awardList4.setId(5);
        awardList4.setUserName("Dan Webber");
        awardList4.setDate("09 sep 2021,06:12pm");
        awardList4.setDescription("Fantastic energy and positivity as always Dan");
        kudosAwardList.add(awardList4);

        KudosAwardList awardList5= new KudosAwardList();
        awardList5.setId(6);
        awardList5.setUserName("Holly Dickinsin");
        awardList5.setDate("09 sep 2021,06:12pm");
        awardList5.setDescription("Fantastic energy and positivity as always Holly");
        kudosAwardList.add(awardList5);*/


       /* ad_KudosAward= new Ad_kudosAwardPagination(kudosAwardList,this);
        rv_kudos_award.setAdapter(ad_KudosAward);*/
       // ad_KudosAward.addAll(kudosAwardList);
        ad_KudosAward.notifyDataSetChanged();
    }
    public void setSessionData(){
        try{
            if(orgId.equals("")){
                orgId= sessionParam.orgId;
            }
           // tv_userName.setText(sessionParam.name+ " " + sessionParam.lastName);
            Picasso.get().load(sessionParam.organisation_logo).into(imageLogo);
            imageLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callHomeAct(mContext);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void api_loadNextPage() {
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{

                    /*
                    *   Gson gson = new Gson();
                        homeDetail = gson.fromJson(object.toString(), KnowOrgDetails.class);
                    *  JSONObject jsonObject = new JSONObject(Json);
                    TOTAL_PAGES = jsonObject.optInt("totalPageCount");
                    JSONArray jsonArray = new JSONArray(object.toString());*/

                    ad_KudosAward.removeLoadingFooter();
                    isLoading= false;
                    //ad_KudosAward.addAll();
                    if (currentPage != TOTAL_PAGES)
                        ad_KudosAward.addLoadingFooter();
                    else
                        isLastPage= true;
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

        JsonObject object= Functions.getClient().getJsonMapObject("pageId",currentPage+"");
        baseRequest.callAPIPost(1,object, ConstantAPI.getKudosAward);
    }

    private void getAward(String userId) {
        currentPage= PAGE_START;
        baseRequest= new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{

                    kudosAwardList.clear();
                      Gson gson = new Gson();
                   // JSONObject jsonObject= new JSONObject(object.toString());
                    JSONArray jsonArray = new JSONArray(object.toString());
                    kudosAwardList = baseRequest.getDataList(jsonArray, KudosAwardList.class);


                   // kudosAwardList= awardResponse.getKudosAwardLists();

                        /*ad_KudosAward.addAll(kudosAwardList);
                        ad_KudosAward.notifyDataSetChanged();*/
                        if (userId.equals("")) {
                           /* List<String> userList = new ArrayList<>();
                            for (int j = 0; j < kudosAwardList.size(); j++) {
                                for (int k = j; k < kudosAwardList.size(); k++) {
                                    if (kudosAwardList.get(j).getAwardValue().equals(kudosAwardList.get(k).getAwardValue()) && (kudosAwardList.get(j).getAwardDescription().equals(kudosAwardList.get(k).getAwardDescription()))) {

                                        userList.add(kudosAwardList.get(j).getUserName());
                                        kudosAwardList.remove(j);
                                    }
                                }

                            }*/
                            Map<String, List<KudosAwardList>> map= new HashMap<String, List<KudosAwardList>>();
                            try {
                               // for (int i=0; i<kudosAwardList.size(); i++) {

                                    for (KudosAwardList awardList : kudosAwardList) {

                                        String key = "#"+utility.getDate(awardList.getAwardDate())+" # "+ awardList.getAwardDescription();
                                        if (map.get(key) == null) {
                                            map.put(key, new ArrayList<KudosAwardList>());

                                        }
                                        map.get(key).add(awardList);
                                    }
                                   /* for(KudosAwardList awardList : kudosAwardList){
                                        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm a");
                                        Date d1Key= sdf.parse(awardList.getAwardDate());
                                        String key= String.valueOf(d1Key);
                                        if (map.get(key)==null){
                                            map.put(key,new ArrayList<>());
                                        }
                                        map.get(key).add(awardList);
                                    }*/
                              //  }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            Map<String, List<KudosAwardList>> newMapList= sortMap(map);
                           List <GroupKudosList> groupKudosList= new ArrayList<>();
                            for (Map.Entry<String,List<KudosAwardList>> stringListMap: newMapList.entrySet()) {
                                String myKey = stringListMap.getKey();
                                List<KudosAwardList> myList = stringListMap.getValue();
                                GroupKudosList list= new GroupKudosList();
                                list.setKeyDescription(myKey);
                                list.setKudosAwardLists(myList);
                                groupKudosList.add(list);
                            }
                            rv_kudos_award.setVisibility(View.VISIBLE);
                            no_award_tv.setVisibility(View.GONE);
                            //ad_KudosAward = new Ad_kudosAwardPagination(map,userId, kudosAwardList, mContext);
                            Ad_KudosAwardGroupList adapterGroupList= new Ad_KudosAwardGroupList(groupKudosList,mContext, new Ad_KudosAwardGroupList.groupUsersListener() {
                                @Override
                                public void groupUser(String awardValue,List<KudosAwardList> userList) {
                                    groupUserDialog(awardValue,userList);
                                }
                            });
                            rv_kudos_award.setAdapter(adapterGroupList);


                        } else {


                            if (kudosAwardList.size()>0){
                                no_award_tv.setVisibility(View.GONE);
                                rv_kudos_award.setVisibility(View.VISIBLE);
                                ad_KudosAward = new Ad_kudosAwardPagination(userId, kudosAwardList, mContext);
                                rv_kudos_award.setAdapter(ad_KudosAward);
                            }else {
                                no_award_tv.setVisibility(View.VISIBLE);
                                rv_kudos_award.setVisibility(View.GONE);
                            }
                        }


                    //  JSONObject jsonObject = new JSONObject(Json);
                   // TOTAL_PAGES = jsonObject.optInt("totalPageCount");
                    //JSONArray jsonArray = new JSONArray(object.toString());

                    if (currentPage <= TOTAL_PAGES)
                        ad_KudosAward.addLoadingFooter();
                    else
                        isLastPage= true;
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

        JsonObject object= Functions.getClient().getJsonMapObject("userId",userId);
        baseRequest.callAPIPost(1,object, ConstantAPI.getKudosAward);
    }




    public Map<String, List<KudosAwardList>> sortMap(Map<String, List<KudosAwardList>> unsortMap) {

        List<Map.Entry<String, List<KudosAwardList>>> list = new LinkedList<Map.Entry<String, List<KudosAwardList>>>(
                unsortMap.entrySet());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm aa");
        // sort list based on comparator
        Collections.sort(list, new Comparator<Object>() {
            @SuppressWarnings("unchecked")
            public int compare(Object o1, Object o2) {
                String keyString= ((Map.Entry<String,List<KudosAwardList>>) o1).getKey();
                String[] parts = keyString.split("#");
                String firstKey= parts[1].trim();

                String secondKeyString= ((Map.Entry<String,List<KudosAwardList>>) o2).getKey();
                String[] secondParts = secondKeyString.split("#");
                String secondKey= secondParts[1].trim();
                Date d1= null;
                Date d2= null;
                try{
                    d1= sdf.parse(firstKey);
                    d2= sdf.parse(secondKey);
                }catch (ParseException e){
                    e.printStackTrace();

                }
                return (d1.getTime()> d2.getTime() ? -1 : 1);
                //return  (firstKey.compareTo(secondKey));
               /* return ((Map.Entry<String, List<KudosAwardList>>) o1).getKey().compareTo(
                        ((Map.Entry<String, List<KudosAwardList>>) o2).getKey());*/
            }
        });

        // put sorted list into map again
        Map<String, List<KudosAwardList>> sortedMap = new LinkedHashMap<String, List<KudosAwardList>>();
        for (Iterator<Map.Entry<String, List<KudosAwardList>>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, List<KudosAwardList>> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }


    /****************** user Selection dialog **********/
    public void dialogResponsible() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tier_list);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final ImageView iv_close = dialog.findViewById(R.id.iv_close);
        final RecyclerView rv_list = dialog.findViewById(R.id.rv_list);
        final TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText("Select User");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        rv_list.setAdapter(new Ad_UserListDialog(modelUserList, mContext));

        rv_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_list, new RecyclerItemClickListener.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(View view, int position) {
                if (modelUserList.get(position).getName().equals("All User")){
                    tv_userName.setText(modelUserList.get(position).getName().trim());
                    getAward("");
                }else {
                    tv_userName.setText(modelUserList.get(position).getName() + " " + modelUserList.get(position).getLastName());
                    userId = modelUserList.get(position).getId() + "";
                    firstName = modelUserList.get(position).getName();
                    lastName = modelUserList.get(position).getLastName();
                    getAward(userId);
                }
               /* if (sessionParam.id.equals(userId)) {
                    tv_undoMoti.setVisibility(View.VISIBLE);

                } else {
                    tv_undoMoti.setVisibility(View.GONE);
                    tv_undo.setVisibility(View.GONE);
                    tv_undo_personalityType.setVisibility(View.GONE);
                }
                btn_personalityType_nrf.setVisibility(View.GONE);
                allKnowMemberLayout.setVisibility(View.VISIBLE);*/

                //  getAllData(userId, modelUserList.get(position).getName(), modelUserList.get(position).getLastName());
                /*********** call Api for get user Report data ********/

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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }
    /************************** call Api For get user List ************/
    public void api_getUserByType() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    modelUserList = baseRequest.getDataList(jsonArray, ModelAddActionUser.class);
                    ModelAddActionUser newUser= new ModelAddActionUser();
                    newUser.setName("All User");
                    newUser.setLastName("");
                    modelUserList.add(0,newUser);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // dialogResponsible();
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


        //JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.companyOrgId
        JsonObject object = Functions.getClient().getJsonMapObject("type", "organisation",
                "typeId", sessionParam.orgId

        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getUserByType);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            rv_kudos_award.setNestedScrollingEnabled(false);
        }else {
            rv_kudos_award.setNestedScrollingEnabled(true);

        }

    }

    private void groupUserDialog(String awardValue, List<KudosAwardList> userList) {
        final  Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_award_user_list);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        final TextView iv_top_previous= dialog.findViewById(R.id.award_name_tv);
        final TextView description_tv= dialog.findViewById(R.id.award_desc_tv);
        RecyclerView award_list= dialog.findViewById(R.id.award_rv);

        iv_top_previous.setText(awardValue);
        try {
            description_tv.setText(userList.get(0).getAwardDescription());
            //java.util.Collections.sort(userList, Collator.getInstance());
            Collections.sort(userList, new Comparator<KudosAwardList>(){
                public int compare(KudosAwardList d1, KudosAwardList d2){
                    return d1.getUserName().compareTo(d2.getUserName());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        award_list.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        award_list.addItemDecoration(itemDecoration);
        award_list.addItemDecoration(new ItemOffsetDecoration(mContext,R.dimen.item_offset_small));
        List<String> personList = new ArrayList<>();
        Ad_AwardUserList adAwardUserList = new Ad_AwardUserList(awardValue, userList, personList, mContext);
        award_list.setNestedScrollingEnabled(true);
        award_list.setAdapter(adAwardUserList);
        WindowManager.LayoutParams lp= new WindowManager.LayoutParams();
        Window window=dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_award_list;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_know;
    }


    @Override
    public void retryPageLoad() {
        api_loadNextPage();
    }
}