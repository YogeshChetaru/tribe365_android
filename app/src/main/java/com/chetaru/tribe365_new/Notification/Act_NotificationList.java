package com.chetaru.tribe365_new.Notification;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.Home.HomeBelief;
import com.chetaru.tribe365_new.API.Models.Home.HomeKudosResponse;
import com.chetaru.tribe365_new.API.Models.ModelNotificationList;
import com.chetaru.tribe365_new.API.Models.NotificationAction;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.Ad_NotificationList;
import com.chetaru.tribe365_new.Adapter.Ad_NotificationList_pagination;
import com.chetaru.tribe365_new.Adapter.Ad_UnreadNotification;
import com.chetaru.tribe365_new.Adapter.Ad_home_belief_filter;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Home.Act_Award;
import com.chetaru.tribe365_new.UI.Home.Act_AwardDescriptions;
import com.chetaru.tribe365_new.utility.ItemOffsetDecoration;
import com.chetaru.tribe365_new.utility.PaginationAdapterCallback;
import com.chetaru.tribe365_new.utility.PaginationAdapterURCallback;
import com.chetaru.tribe365_new.utility.PaginationScrollListener;
import com.chetaru.tribe365_new.utility.PaginationScrollListenerUR;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_NotificationList extends BaseActivity implements PaginationAdapterCallback, PaginationAdapterURCallback {
    private static final int PAGE_START = 1;
    private static int TOTAL_PAGES = 1;

    private static final int UNREAD_PAGE_START=1;
    private static int UNREAD_TOTAL_PAGES=1;

    Utility utility;
    BaseRequest baseRequest;
    SessionParam sessionParam;

    ArrayList<ModelNotificationList> list = new ArrayList<>();
    ArrayList<ModelNotificationList> listUnread = new ArrayList<>();
    Ad_NotificationList ad_notificationList;
    Ad_NotificationList_pagination ad_notificationListP;
    Ad_UnreadNotification ad_unreadNotification;
    RecyclerView rv_list_archive, rv_list_unreadNoti;
    SearchView search_bar;

    TextView tv_tab_archive, tv_tab_noti, tv_sendToArchive;
    ImageView tribe365;
    boolean noti_selected = true;
    int pageNo = 1000;
    ProgressBar progressBar;
    LinearLayout ll_archive;
    LinearLayoutManager linearLayoutManager, linearLayoutManager2;
    //archiveAll will check is there any data in "data tag" in unread notification api if not then we will set archive all button visibility gone
    boolean archiveAll = false;
    JSONObject reminderList;
    private boolean isLoading = false;
    public int currentPage = PAGE_START;
    private boolean isLastPage = false;

    private boolean isLoadingUR = false;
    public int currentPageUR = UNREAD_PAGE_START;
    private boolean isLastPageUR = false;

    HomeKudosResponse kudosResponse = new HomeKudosResponse();
    List<HomeBelief> totalKudosList;
    int amazingValue = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_notification_list);
        rv_list_archive = findViewById(R.id.rv_list_archive);
        rv_list_unreadNoti = findViewById(R.id.rv_list_unreadNoti);
        search_bar = findViewById(R.id.search_bar);
        tv_tab_archive = findViewById(R.id.tv_tab_archive);
        tv_tab_noti = findViewById(R.id.tv_tab_noti);
        tv_sendToArchive = findViewById(R.id.tv_sendToArchive);
        progressBar = findViewById(R.id.main_progress);
        ll_archive = findViewById(R.id.ll_archive);
        tribe365 = findViewById(R.id.tribe365);
        sessionParam = new SessionParam(mContext);
        totalKudosList= new ArrayList<>();

        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.isEmpty()) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        reminderList = new JSONObject();
        utility = new Utility();
        ad_notificationList = new Ad_NotificationList(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list_archive.setLayoutManager(linearLayoutManager);
        rv_list_unreadNoti.setLayoutManager(linearLayoutManager2);
        rv_list_archive.setItemAnimator(new DefaultItemAnimator());
        rv_list_unreadNoti.setItemAnimator(new DefaultItemAnimator());
        rv_list_archive.setAdapter(ad_notificationList);
        rv_list_unreadNoti.addOnScrollListener(new PaginationScrollListenerUR(linearLayoutManager2) {
            @Override
            protected void loadMoreItems() {
                isLoadingUR = true;
                currentPageUR += 1;
                api_loadnext_page_unread();

            }

            @Override
            public int getTotalPageCount() {
                return UNREAD_TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPageUR;
            }

            @Override
            public boolean isLoading() {
                return isLoadingUR;
            }
        });
        rv_list_archive.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                api_loadnextPage();
            }

            @Override
            public int getTotalPageCount() {
                //Toast.makeText(mContext, "getTotalPageCount", Toast.LENGTH_SHORT).show();
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                //Toast.makeText(mContext, "isLastPage", Toast.LENGTH_SHORT).show();
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                //Toast.makeText(mContext, "isLoading", Toast.LENGTH_SHORT).show();
                return isLoading;
            }
        });



        if (!noti_selected) {

        } else {
            progressBar.setVisibility(View.GONE);
        }
        search_bar.setIconifiedByDefault(false);
        search(search_bar);

        tv_tab_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!noti_selected)
                    switch_tab();
            }
        });
        tv_tab_archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noti_selected)
                    switch_tab();
            }
        });
        tv_sendToArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api_readAllNoti();
            }
        });
        tribe365.setOnClickListener(v->{
            callHomeAct(mContext);
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            rv_list_archive.setNestedScrollingEnabled(false);
            rv_list_unreadNoti.setNestedScrollingEnabled(false);
        }else {
            rv_list_archive.setNestedScrollingEnabled(true);
            rv_list_unreadNoti.setNestedScrollingEnabled(true);
        }
    }

    /*method will help to switch between “Notification” and “Archived*/
    public void switch_tab() {
        if (noti_selected) {
            //Toast.makeText(mContext, "archive selected", Toast.LENGTH_SHORT).show();
            tv_tab_noti.setTextColor(getResources().getColor(R.color.red));
            tv_tab_noti.setBackgroundColor(getResources().getColor(R.color.white));
            tv_tab_archive.setTextColor(getResources().getColor(R.color.white));
            tv_tab_archive.setBackgroundColor(getResources().getColor(R.color.red));
            list.clear();
            noti_selected = false;
            ad_notificationList = new Ad_NotificationList(list, mContext, new Ad_NotificationList.kudosClickListener() {
                @Override
                public void kudosClick() {
                    kudosNotiClick();

                }
            });
            rv_list_archive.setAdapter(ad_notificationList);
            ll_archive.setVisibility(View.VISIBLE);
            rv_list_unreadNoti.setVisibility(View.GONE);
            tv_sendToArchive.setVisibility(View.GONE);
            api_notificationList();


        } else {
            //Toast.makeText(mContext, "notification selected", Toast.LENGTH_SHORT).show();
            tv_tab_noti.setTextColor(getResources().getColor(R.color.white));
            tv_tab_noti.setBackgroundColor(getResources().getColor(R.color.red));
            tv_tab_archive.setTextColor(getResources().getColor(R.color.red));
            tv_tab_archive.setBackgroundColor(getResources().getColor(R.color.white));
            noti_selected = true;
            listUnread.clear();
            ad_unreadNotification = new Ad_UnreadNotification(listUnread, reminderList, this, new Ad_UnreadNotification.kudosListener() {
                @Override
                public void KudosClick() {
                    kudosNotiClick();
                }
            });
            rv_list_archive.setAdapter(ad_unreadNotification);
            ll_archive.setVisibility(View.GONE);
            rv_list_unreadNoti.setVisibility(View.VISIBLE);
            api_notificationListUnread();
        }
    }
    /*********** call kudos dialog or Awards list *************************/
    private void kudosNotiClick() {
        if (sessionParam.loginVersion == 3){
            Intent intent=new Intent(mContext, Act_AwardDescriptions.class);
            intent.putExtra("kudosId","0");
            intent.putExtra("kudosName","Amazing Awards");
            startActivity(intent);
        }else {
            getKudos();
        }
    }


    public void api_notificationList() {
        //hideErrorView();
        currentPage = PAGE_START;
        isLastPage = false;
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                list.clear();
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    TOTAL_PAGES = jsonObject.optInt("totalPageCount");
                    JSONArray jsonArray = new JSONArray(object.toString());
                    list = baseRequest.getDataList(jsonArray, ModelNotificationList.class);
                    //ad_notificationList.addAll(listUnread);
                    ad_notificationList.addAll(list);
                    if (currentPage <= TOTAL_PAGES) ad_notificationList.addLoadingFooter();
                    else isLastPage = true;

                    isLastPage= currentPage == TOTAL_PAGES;
                    //rv_list_archive.setAdapter(ad_notificationListP);
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
       JsonObject object = Functions.getClient().getJsonMapObject(
                "userId", sessionParam.id,
                "page", currentPage + ""
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getBubbleRatingNotificationList);
    }

    /*API call to get all unread notifications*/
    public void api_notificationListUnread() {
        currentPageUR= UNREAD_PAGE_START;
        isLastPageUR=false;
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(object.toString());
                    Log.d("unreadNotification", "ntoi:-" + jsonArray);
                    Log.d("unreadNotification", "ntoi:-" + object);
                    archiveAll = jsonArray.length() == 0;
                    if (archiveAll) {
                        tv_sendToArchive.setVisibility(View.GONE);
                    } else {
                        tv_sendToArchive.setVisibility(View.VISIBLE);
                    }

                    ArrayList<ModelNotificationList> listUnread_temp = new ArrayList<>();
                    listUnread_temp = baseRequest.getDataList(jsonArray, ModelNotificationList.class);
                    JSONObject joMain = new JSONObject(Json);
                    JSONObject joAnsStatus = joMain.optJSONObject("ansStatus");
                    JSONObject joToDoList = joMain.optJSONObject("toDoList");
                    JSONObject reminderList = joMain.optJSONObject("reminderList");
                    UNREAD_TOTAL_PAGES= joMain.optInt("totalPageCount");
                    JSONArray actionArray=joMain.optJSONArray("actions");

                    List<NotificationAction> actionList=baseRequest.getDataList(actionArray,NotificationAction.class);
                    try {

                        if (actionList.size()>0) {
                            for (int i = 0; i < actionList.size(); i++) {
                                ModelNotificationList actionValue = new ModelNotificationList();
                                actionValue.setmId(actionList.get(i).getId()+"");
                                actionValue.setTitle(actionList.get(i).getName());
                                actionValue.setDescription(actionList.get(i).getDescription());
                                actionValue.setCreatedAt(actionList.get(i).getDueDate());
                                actionValue.setNotificationType("actionList");
                                listUnread.add(actionValue);
                            }

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                     ModelNotificationList modelNotificationList = new ModelNotificationList();
                    //---------------------------ansStatus"----------------------------------
                    Boolean tribeValue = joAnsStatus.optBoolean("tribeValue");
                    Boolean functionalLens = joAnsStatus.optBoolean("functionalLens");
                    Boolean teamRoleAnswers = joAnsStatus.optBoolean("teamRoleAnswers");
                    Boolean cultureStructure = joAnsStatus.optBoolean("cultureStructure");
                    Boolean motivation = joAnsStatus.optBoolean("motivation");
                    Boolean tribeometer = joAnsStatus.optBoolean("tribeometer");
                    Boolean diagnostic = joAnsStatus.optBoolean("diagnostic");


                    Boolean rem_tribeValue = reminderList.optBoolean("tribeValue");
                    Boolean rem_functionalLens = reminderList.optBoolean("functionalLens");
                    Boolean rem_teamRoleAnswers = reminderList.optBoolean("teamRoleAnswers");
                    Boolean rem_cultureStructure = reminderList.optBoolean("cultureStructure");
                    Boolean rem_motivation = reminderList.optBoolean("motivation");
                    Boolean rem_tribeometer = reminderList.optBoolean("tribeometer");
                    Boolean rem_diagnostic = reminderList.optBoolean("diagnostic");
                    Boolean rem_bubbleRatings = reminderList.optBoolean("bubbleRatings");
                    if (!tribeValue) {
                        if (rem_tribeValue) {
                            modelNotificationList.setTitle("Reminder!");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_evaluate_yourself_against_tribe_value));
                            listUnread.add(modelNotificationList);
                        } else {
                            modelNotificationList.setTitle("First Use Checklist");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_evaluate_yourself_against_tribe_value));
                            listUnread.add(modelNotificationList);
                        }
                    }
                    modelNotificationList = new ModelNotificationList();
                    if (!functionalLens) {
                        if (rem_functionalLens) {
                            modelNotificationList.setTitle("Reminder!");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_complete_personality_Type_Questionnaire));
                            listUnread.add(modelNotificationList);
                        } else {
                            modelNotificationList.setTitle("First Use Checklist");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_complete_personality_Type_Questionnaire));
                            listUnread.add(modelNotificationList);
                        }
                    }
                    modelNotificationList = new ModelNotificationList();
                    if (!teamRoleAnswers) {
                        if (rem_teamRoleAnswers) {
                            modelNotificationList.setTitle("Reminder!");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_Complete_Team_Role_Questionnaire));
                            listUnread.add(modelNotificationList);
                        } else {
                            modelNotificationList.setTitle("First Use Checklist");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_Complete_Team_Role_Questionnaire));
                            listUnread.add(modelNotificationList);
                        }

                    }
                    modelNotificationList = new ModelNotificationList();
                    if (!cultureStructure) {
                        if (rem_cultureStructure) {
                            modelNotificationList.setTitle("Reminder!");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_Complete_Culture_Structure_Questionnaire));
                            listUnread.add(modelNotificationList);
                        } else {
                            modelNotificationList.setTitle("First Use Checklist");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_Complete_Culture_Structure_Questionnaire));
                            listUnread.add(modelNotificationList);
                        }

                    }
                    modelNotificationList = new ModelNotificationList();
                    if (!motivation) {
                        if (rem_motivation) {
                            modelNotificationList.setTitle("Reminder!");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_Complete_Motivation_Questionnaire));
                            listUnread.add(modelNotificationList);
                        } else {
                            modelNotificationList.setTitle("First Use Checklist");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_Complete_Motivation_Questionnaire));
                            listUnread.add(modelNotificationList);
                        }

                    }
                    modelNotificationList = new ModelNotificationList();
                    if (!tribeometer) {
                        if (rem_tribeometer) {
                            modelNotificationList.setTitle("Reminder!");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_Complete_Tribeometer_Survey));
                            listUnread.add(modelNotificationList);
                        } else {
                            modelNotificationList.setTitle("First Use Checklist");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_Complete_Tribeometer_Survey));
                            listUnread.add(modelNotificationList);
                        }

                    }
                    modelNotificationList = new ModelNotificationList();
                    if (!diagnostic) {
                        if (rem_diagnostic) {
                            modelNotificationList.setTitle("Reminder!");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_Complete_Diagnostic_Survey));
                            listUnread.add(modelNotificationList);
                        } else {
                            modelNotificationList.setTitle("First Use Checklist");
                            modelNotificationList.setDescription(getResources().getString(R.string.checklist_Complete_Diagnostic_Survey));
                            listUnread.add(modelNotificationList);
                        }
                    }

                    if (tribeValue &&
                            functionalLens &&
                            teamRoleAnswers &&
                            cultureStructure
                            && motivation
                            && tribeometer
                            && diagnostic)
                    {
                        Boolean TribeValue_todo = joToDoList.optBoolean("tribeValue");
                        Boolean functionalLens_todo = joToDoList.optBoolean("functionalLens");
                        Boolean teamRoleAnswers_todo = joToDoList.optBoolean("teamRoleAnswers");
                        Boolean cultureStructure_todo = joToDoList.optBoolean("cultureStructure");
                        Boolean motivation_todo = joToDoList.optBoolean("motivation");
                        Boolean tribeometer_todo = joToDoList.optBoolean("tribeometer");
                        Boolean diagnostic_todo = joToDoList.optBoolean("diagnostic");
                        Boolean bubbleRatings_todo = joToDoList.optBoolean("bubbleRatings");


                        if (!TribeValue_todo) {

                            if (rem_tribeValue) {
                                modelNotificationList.setTitle("Reminder To Do List");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_your_values_Monthly));
                                listUnread.add(modelNotificationList);
                            } else {
                                modelNotificationList.setTitle("To Do List");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_your_values_Monthly));
                                listUnread.add(modelNotificationList);
                            }

                        }
                        modelNotificationList = new ModelNotificationList();
                        if (!functionalLens_todo) {

                            if (rem_functionalLens) {
                                modelNotificationList.setTitle("Reminder To Do List!");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_Personality_Type_Questionnaire));
                                listUnread.add(modelNotificationList);
                            } else {
                                modelNotificationList.setTitle("To Do List");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_Personality_Type_Questionnaire));
                                listUnread.add(modelNotificationList);
                            }


                        }
                        modelNotificationList = new ModelNotificationList();
                        if (!teamRoleAnswers_todo) {

                            if (rem_teamRoleAnswers) {
                                modelNotificationList.setTitle("Reminder To Do List!");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_TeamRoleQuestionnaire));
                                listUnread.add(modelNotificationList);
                            } else {
                                modelNotificationList.setTitle("To Do List");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_TeamRoleQuestionnaire));
                                listUnread.add(modelNotificationList);
                            }

                        }
                        modelNotificationList = new ModelNotificationList();
                        if (!cultureStructure_todo) {

                            if (rem_cultureStructure) {
                                modelNotificationList.setTitle("Reminder To Do List!");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_CultureStructureQuestionnaire));
                                listUnread.add(modelNotificationList);
                            } else {
                                modelNotificationList.setTitle("To Do List");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_CultureStructureQuestionnaire));
                                listUnread.add(modelNotificationList);
                            }


                        }
                        modelNotificationList = new ModelNotificationList();
                        if (!motivation_todo) {
                            if (rem_motivation) {
                                modelNotificationList.setTitle("Reminder To Do List!");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_MotivationQuestionnaire));
                                listUnread.add(modelNotificationList);
                            } else {
                                modelNotificationList.setTitle("To Do List");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_MotivationQuestionnaire));
                                listUnread.add(modelNotificationList);
                            }


                        }
                        modelNotificationList = new ModelNotificationList();
                        if (!tribeometer_todo) {
                            if (rem_tribeometer) {
                                modelNotificationList.setTitle("Reminder To Do List!");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_TribeometerSurvey));
                                listUnread.add(modelNotificationList);
                            } else {
                                modelNotificationList.setTitle("To Do List");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_TribeometerSurvey));
                                listUnread.add(modelNotificationList);
                            }



                        }
                        modelNotificationList = new ModelNotificationList();
                        if (!diagnostic_todo) {
                            if (rem_diagnostic) {
                                modelNotificationList.setTitle("Reminder To Do List!");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_DiagnosticSurvey));
                                listUnread.add(modelNotificationList);
                            } else {
                                modelNotificationList.setTitle("To Do List");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_Review_DiagnosticSurvey));
                                listUnread.add(modelNotificationList);
                            }


                        }
                        modelNotificationList = new ModelNotificationList();
                        if (!bubbleRatings_todo) {
                            if (rem_bubbleRatings) {
                                modelNotificationList.setTitle("Reminder To Do List!");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_AwardGreenBubblestoyourcolleaguesDaily));
                                listUnread.add(modelNotificationList);
                            } else {
                                modelNotificationList.setTitle("To Do List");
                                modelNotificationList.setDescription(getResources().getString(R.string.todolist_AwardGreenBubblestoyourcolleaguesDaily));
                                listUnread.add(modelNotificationList);
                            }

                        }

                    }



                    listUnread.addAll(listUnread_temp);

                    ad_unreadNotification = new Ad_UnreadNotification(listUnread, reminderList, mContext, Act_NotificationList.this::kudosNotiClick);
                    rv_list_unreadNoti.setAdapter(ad_unreadNotification);
                    if (currentPageUR<=UNREAD_TOTAL_PAGES)
                        ad_unreadNotification.addLoadingFooter();
                    else
                        isLastPageUR=true;
                    if (currentPageUR== UNREAD_TOTAL_PAGES){
                        isLastPageUR=true;
                    }

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
        JsonObject object = Functions.getClient().getJsonMapObject(
                "userId", sessionParam.id,
                "page", currentPageUR + ""
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getBubbleRatingUnReadNotificationList);
    }

    /*is used for searching in list.
     */
    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ad_notificationList.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ll_archive.setVisibility(View.GONE);
        rv_list_unreadNoti.setVisibility(View.GONE);
        try {

            if (noti_selected) {
                listUnread.clear();
                ad_unreadNotification = new Ad_UnreadNotification(listUnread, reminderList, this, new Ad_UnreadNotification.kudosListener() {
                    @Override
                    public void KudosClick() {
                        kudosNotiClick();
                    }
                });
                ad_unreadNotification.notifyDataSetChanged();
                ll_archive.setVisibility(View.GONE);
                rv_list_unreadNoti.setVisibility(View.VISIBLE);
                api_notificationListUnread();
            } else {
                list.clear();
                noti_selected = false;
                ad_notificationList = new Ad_NotificationList(list, mContext, new Ad_NotificationList.kudosClickListener() {
                    @Override
                    public void kudosClick() {
                        kudosNotiClick();
                    }
                });
                ll_archive.setVisibility(View.VISIBLE);
                rv_list_unreadNoti.setVisibility(View.GONE);
                api_notificationList();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void api_loadnext_page_unread(){
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gone=new Gson();
                try{
                    isLoadingUR = false;
                    ad_unreadNotification.removeLoadingFooter();
                    try {
                        JSONObject joMain = new JSONObject(Json);
                        UNREAD_TOTAL_PAGES= joMain.optInt("totalPageCount");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    JSONArray jsonArray = new JSONArray(object.toString());
                    ArrayList<ModelNotificationList> listUnread  = baseRequest.getDataList(jsonArray, ModelNotificationList.class);
                    ad_unreadNotification.addAll(listUnread);

                    if (currentPageUR != UNREAD_TOTAL_PAGES)
                        ad_unreadNotification.addLoadingFooter();

                    else isLastPageUR = true;
                    if (currentPage == UNREAD_TOTAL_PAGES)
                        isLastPageUR= true;
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
                api_notificationListUnread();
            }
        });
         JsonObject object=Functions.getClient().getJsonMapObject(
                 "userId",sessionParam.id,
                "page", currentPageUR+"");
        baseRequest.callAPIPost(1,object, ConstantAPI.getBubbleRatingUnReadNotificationList);

    }

    //this method is used to paging in our notification list
    public void api_loadnextPage() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    TOTAL_PAGES = jsonObject.optInt("totalPageCount");
                    JSONArray jsonArray = new JSONArray(object.toString());
                    list = baseRequest.getDataList(jsonArray, ModelNotificationList.class);

                    //ad_notificationListP = new Ad_NotificationList_pagination(rv_list,list, mContext);
                    ad_notificationList.removeLoadingFooter();
                    isLoading = false;
                    ad_notificationList.addAll(list);
                    //rv_list_archive.setAdapter(ad_notificationList);
                    if (currentPage != TOTAL_PAGES) ad_notificationList.addLoadingFooter();
                    else isLastPage = true;
                    //rv_list_archive.setAdapter(ad_notificationListP);
                    if (currentPage == TOTAL_PAGES){
                        isLastPage= true;
                    }
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
                api_notificationList();
            }
        });
        //JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.companyOrgId
        JsonObject object = Functions.getClient().getJsonMapObject("userId", sessionParam.id,
                "page", currentPage + ""
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.getBubbleRatingNotificationList);
    }

    /*will retry page load if failed on notification read*/
    @Override
    public void retryPageLoad() {
        api_loadnextPage();
    }


    @Override
    public void retryPageLoadUR() {
        api_loadnext_page_unread();
    }

    /*************** retry pageLoad failed on notification ******/



    /*API call to mark all unread notification read.
     */
    public void api_readAllNoti() {
        //hideErrorView();
        currentPage = PAGE_START;
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                onResume();
                //api_getnotificationCount();
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
        JsonObject object = Functions.getClient().getJsonMapObject(
                "userId", sessionParam.id);
        baseRequest.callAPIPost(1, object, ConstantAPI.readAllNotification);
    }
    private void getKudos() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    Gson gson = new Gson();
                    //JSONArray jsonArray = new JSONArray(object.toString());
                    JSONObject jsonObject= new JSONObject(object.toString());
                    kudosResponse = gson.fromJson(jsonObject.toString(), HomeKudosResponse.class);
                    totalKudosList=kudosResponse.getBelief();
                    String amazingValueKey=kudosResponse.getKudoAwardKey();
                    int todayAwardCount=kudosResponse.getTodayKudosAwardCount();
                    int yesterdayAwardCount=kudosResponse.getYesterdayKudosAwardCount();
                    int thisWeekAwardCount=kudosResponse.getThisWeekKudosAwardCount();
                    int lastWeekAwardCount=kudosResponse.getLastWeekKudosAwardCount();
                    int thisMonthAwardCount=kudosResponse.getThisMonthKudosAwardCount();
                    int lastMonthAwardCount=kudosResponse.getLastMonthKudosAwardCount();
                    int totalAwardCount=kudosResponse.getTotalKudosAwardCount();
                    /******* show popup on kudos count ****************/
                    showChangeDialog(totalKudosList,amazingValueKey,todayAwardCount,yesterdayAwardCount
                    ,thisWeekAwardCount,lastWeekAwardCount,thisMonthAwardCount,lastMonthAwardCount,totalAwardCount);
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
        JsonObject object=Functions.getClient().getJsonMapObject("orgId",sessionParam.orgId);
        baseRequest.callAPIPostWOLoader(1, object, ConstantAPI.getHomePageKudosCount);
    }


    private void showChangeDialog(List<HomeBelief> kudosList, String amazingValueKey, int todayAwardCount,
                                  int yesterdayAwardCount, int thisWeekAwardCount, int lastWeekAwardCount,
                                  int thisMonthAwardCount, int lastMonthAwardCount, int totalAwardCount) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_kudos_filter);
        dialog.setCanceledOnTouchOutside(true);
        ArrayList<String> titleList= new ArrayList<>();
        titleList.add("Total");
        titleList.add("Today");
        titleList.add("Yesterday");
        titleList.add("Last week");
        titleList.add("This week");
        titleList.add("Last month");
        titleList.add("This month");
        ArrayList<String> titleTopList= new ArrayList<>();
        titleTopList.add("Kudos Values");
        titleTopList.add("Kudos Awards");

        final ImageView iv_top_previous= dialog.findViewById(R.id.top_left_image_click);
        final ImageView iv_top_next=dialog.findViewById(R.id.top_next_image_click);
        final TextView top_tv_title=dialog.findViewById(R.id.kudos_top_title_tv);

        final ImageView iv_previous = dialog.findViewById(R.id.previous_image_click);
        final ImageView iv_next = dialog.findViewById(R.id.next_image_click);
        final TextView tv_title = dialog.findViewById(R.id.kudos_title_tv);

        final LinearLayout amazing_ll=dialog.findViewById(R.id.bottom_ll);
        final TextView amazing_name=dialog.findViewById(R.id.tv_Amazing_name);
         TextView amazing_value=dialog.findViewById(R.id.tv_amazing_value);
        final TextView award_image=dialog.findViewById(R.id.award_image);

        setAmazingValue(amazing_value,"Total",   todayAwardCount,
                yesterdayAwardCount,  thisWeekAwardCount,  lastWeekAwardCount,  thisMonthAwardCount,
         lastMonthAwardCount,  totalAwardCount);

        amazing_name.setText(amazingValueKey);

        RecyclerView rv_list = dialog.findViewById(R.id.rv_kudos_filter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_small);
        rv_list.addItemDecoration(itemDecoration);
        rv_list.addItemDecoration(new ItemOffsetDecoration(mContext,R.dimen.item_offset_small));
        //rv_list.getRecycledViewPool().setMaxRecycledViews(TYPE_CAROUSEL, 0);
        tv_title.setText(titleList.get(0));
        top_tv_title.setText(titleTopList.get(0));
        award_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, Act_Award.class);
                startActivity(intent);
            }
        });
        iv_top_next.setOnClickListener(view ->{
            try {
                String titleTop=top_tv_title.getText().toString().trim();
                for (int i=0; i<titleTopList.size();i++){

                    if (titleTop.equals(titleTopList.get(i))){

                        if (i == (titleTopList.size()-1)) {
                            top_tv_title.setText(titleTopList.get(0));
                        } else {
                            top_tv_title.setText(titleTopList.get(i + 1));
                        }
                    }
                }

                String newTitle=tv_title.getText().toString().trim();
                String topTitleString= top_tv_title.getText().toString().trim();
                if (topTitleString.equals("Kudos Awards")){
                    amazing_ll.setVisibility(View.VISIBLE);
                    award_image.setVisibility(View.VISIBLE);
                    setAmazingValue(amazing_value, newTitle,   todayAwardCount,
                            yesterdayAwardCount,  thisWeekAwardCount,  lastWeekAwardCount,  thisMonthAwardCount,
                            lastMonthAwardCount,  totalAwardCount);
                }else{
                    amazing_ll.setVisibility(View.GONE);
                    award_image.setVisibility(View.GONE);
                }
                Ad_home_belief_filter belief_filter = new Ad_home_belief_filter(kudosList, mContext,newTitle,topTitleString);
                rv_list.setAdapter(belief_filter);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        iv_top_previous.setOnClickListener(v -> {
            try {

                String titleTop=top_tv_title.getText().toString().trim();
                for (int i=0; i<titleTopList.size();i++){
                    if (titleTop.equals(titleTopList.get(i))) {
                        if (i == 0) {
                            top_tv_title.setText(titleTopList.get(titleTopList.size()-1));
                        } else {
                            top_tv_title.setText(titleTopList.get(i - 1));
                        }
                    }

                }
            String newTitle=tv_title.getText().toString().trim();
            String topTitleString= top_tv_title.getText().toString().trim();
            if (topTitleString.equals("Kudos Awards")){
                amazing_ll.setVisibility(View.VISIBLE);
                award_image.setVisibility(View.VISIBLE);

                setAmazingValue(amazing_value, newTitle,   todayAwardCount,
                        yesterdayAwardCount,  thisWeekAwardCount,  lastWeekAwardCount,  thisMonthAwardCount,
                        lastMonthAwardCount,  totalAwardCount);
            }else {
                amazing_ll.setVisibility(View.GONE);
                award_image.setVisibility(View.GONE);
            }
                Ad_home_belief_filter belief_filter = new Ad_home_belief_filter(kudosList, mContext,newTitle,topTitleString);
                rv_list.setAdapter(belief_filter);

            }catch (Exception e){
                e.printStackTrace();
            }
        });

        iv_previous.setOnClickListener(view -> {
            String getTitle=tv_title.getText().toString().trim();
            try {
                for (int i = 0; i < titleList.size(); i++) {
                    if (getTitle.equals(titleList.get(i))) {
                        if (i == 0) {
                            tv_title.setText(titleList.get(titleList.size()-1));
                        } else {
                            tv_title.setText(titleList.get(i - 1));
                        }
                    }
                }
                String newTitle=tv_title.getText().toString().trim();
                String topTitleString=top_tv_title.getText().toString().trim();
                if (topTitleString.equals("Kudos Awards")){
                    amazing_ll.setVisibility(View.VISIBLE);
                    award_image.setVisibility(View.VISIBLE);
                    setAmazingValue(amazing_value, newTitle,   todayAwardCount,
                            yesterdayAwardCount,  thisWeekAwardCount,  lastWeekAwardCount,  thisMonthAwardCount,
                            lastMonthAwardCount,  totalAwardCount);
                }else {
                    amazing_ll.setVisibility(View.GONE);
                    award_image.setVisibility(View.GONE);
                }
                Ad_home_belief_filter belief_filter = new Ad_home_belief_filter(kudosList, mContext,newTitle,topTitleString);
                rv_list.setAdapter(belief_filter);
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        iv_next.setOnClickListener(v->{
            String getTitle=tv_title.getText().toString().trim();
            try {
                for (int i = 0; i < titleList.size(); i++) {
                    if (getTitle.equals(titleList.get(i))) {
                        if (i == (titleList.size()-1)) {
                            tv_title.setText(titleList.get(0));
                        } else {
                            tv_title.setText(titleList.get(i + 1));
                        }
                    }
                }
                String newTitle=tv_title.getText().toString().trim();
                String topTitleString=top_tv_title.getText().toString().trim();
                Ad_home_belief_filter belief_filter = new Ad_home_belief_filter(kudosList, mContext,newTitle,topTitleString);
                rv_list.setAdapter(belief_filter);
                if (topTitleString.equals("Kudos Awards")){
                    amazing_ll.setVisibility(View.VISIBLE);
                    award_image.setVisibility(View.VISIBLE);


                    setAmazingValue(amazing_value, newTitle,   todayAwardCount,
                            yesterdayAwardCount,  thisWeekAwardCount,  lastWeekAwardCount,  thisMonthAwardCount,
                            lastMonthAwardCount,  totalAwardCount);
                }else {
                    amazing_ll.setVisibility(View.GONE);
                    award_image.setVisibility(View.GONE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        String getTitle=tv_title.getText().toString().trim();
        String topTitleString="Kudos Values";
        Ad_home_belief_filter belief_filter = new Ad_home_belief_filter(kudosList, mContext,getTitle,topTitleString);
        rv_list.setAdapter(belief_filter);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        window.setGravity(Gravity.CENTER);
        lp.width=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    private void setAmazingValue(final TextView amazing_value_TV, String showText,
                                 int todayAwardCount, int yesterdayAwardCount, int thisWeekAwardCount,
                                 int lastWeekAwardCount, int thisMonthAwardCount, int lastMonthAwardCount,
                                 int totalAwardCount) {
        try{
            int amazingValue=0;
            if (showText.equals("Today")) {
                amazingValue=todayAwardCount;
            } else if (showText.equals("Yesterday")) {
              // amazing_value_TV.setText(yesterdayAwardCount + "");
               amazingValue=yesterdayAwardCount;
            } else if (showText.equals("Last week")) {
                //amazing_value_TV.setText(lastWeekAwardCount + "");
                amazingValue=lastWeekAwardCount;
            } else if (showText.equals("This week")) {
                //amazing_value_TV.setText(thisWeekAwardCount + "");
                amazingValue=thisWeekAwardCount;
            } else if (showText.equals("Last month")) {
               // amazing_value_TV.setText(lastMonthAwardCount + "");
                amazingValue=lastMonthAwardCount;
            } else if (showText.equals("This month")) {
               // amazing_value_TV.setText(thisMonthAwardCount + "");
                amazingValue=thisMonthAwardCount;
            } else if (showText.equals("Total")) {
                amazingValue=totalAwardCount;
            }
            amazing_value_TV.setText(String.valueOf(amazingValue) );

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /******************* set Layout on BaseActivity ***************/
    @Override
    public int getLayoutId() {
        return R.layout.act_notification_list;
    }

    /****************** set Navigation id for selection ********************/
    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
