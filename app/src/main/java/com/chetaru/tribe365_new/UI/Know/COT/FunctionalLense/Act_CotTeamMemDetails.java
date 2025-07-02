package com.chetaru.tribe365_new.UI.Know.COT.FunctionalLense;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.Adapter.COTAdapters.Ad_CotfuncLensDetails;
import com.chetaru.tribe365_new.API.Models.COTBeans.FuncLensKeyDetail;
import com.chetaru.tribe365_new.API.Models.COTBeans.InitialValueList;
import com.chetaru.tribe365_new.API.Models.COTBeans.ModelForRecyclerView;
import com.chetaru.tribe365_new.API.Models.COTBeans.ModelFutureLenseDetails;
import com.chetaru.tribe365_new.API.Models.COTBeans.PersuadeArray;
import com.chetaru.tribe365_new.API.Models.COTBeans.SeekArray;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Act_CotTeamMemDetails extends BaseActivity {
    Utility utility;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    ModelFutureLenseDetails modelFutureLenseDetails;
    ModelForRecyclerView modelForRecyclerView;

    @BindView(R.id.tribe365)
    ImageView tribe365;
    @BindView(R.id.tv_d1)
    TextView tv_d1;
    @BindView(R.id.tv_d2)
    TextView tv_d2;

    @BindView(R.id.tv_s1)
    TextView tv_s1;
    @BindView(R.id.tv_s2)
    TextView tv_s2;


    @BindView(R.id.ll_allDataBlock)
    LinearLayout ll_allDataBlock;
    @BindView(R.id.ll_topBlock)
    LinearLayout ll_topBlock;
    @BindView(R.id.ll_bottom_block)
    LinearLayout ll_bottom_block;

    @BindView(R.id.tv_allheading)
    TextView tv_allheading;
    @BindView(R.id.tv_alldesc)
    TextView tv_alldesc;
    @BindView(R.id.tv_allseek)
    TextView tv_allseek;
    @BindView(R.id.tv_allseek_data)
    TextView tv_allseek_data;
    @BindView(R.id.tv_allPersuade)
    TextView tv_allPersuade;
    @BindView(R.id.tv_allPersuadedata)
    TextView tv_allPersuadedata;
    @BindView(R.id.tv_nodata)
    TextView tv_nodata;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_initial_result)
    TextView tv_initial_result;
    List<InitialValueList> initialValueLists;
    List<ModelForRecyclerView> listForRv;
    List<ModelForRecyclerView> listForENTP;
    List<ModelForRecyclerView> listForscrore;
    List<ModelForRecyclerView> listForENTPSummery;
    List<FuncLensKeyDetail> funcLensKeyDetailList;
    List<SeekArray> seekArrayList;
    List<PersuadeArray> persuadeArrayList;
    String userId = "";
    String orgId = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_cot_team_mem_details);
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
        listForRv = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        userId = getIntent().getStringExtra("userId");
        orgId = getIntent().getStringExtra("orgId");
        name = getIntent().getStringExtra("name");
        tv_initial_result.setVisibility(View.GONE);
        tv_name.setText(name);
        api_getDetails();
    }


    @OnClick({R.id.tv_d1, R.id.tv_d2, R.id.tv_s1, R.id.tv_s2, R.id.tv_initial_result, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tribe365:
                callHomeAct(mContext);
                break;
            case R.id.tv_d1:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                listForENTP = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(funcLensKeyDetailList.get(1).getTitle());
                modelForRecyclerView.setDescription(funcLensKeyDetailList.get(1).getDescription());
                modelForRecyclerView.setType("funclens");
                listForENTP.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForENTP, mContext));
                // Changing border color
                tv_d1.setBackground(getResources().getDrawable(R.drawable.rect_solid_red_border));
                tv_d2.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));

                setScoreblockdefaultColor();

                break;
            case R.id.tv_d2:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                listForENTP = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(funcLensKeyDetailList.get(2).getTitle());
                modelForRecyclerView.setDescription(funcLensKeyDetailList.get(2).getDescription());
                modelForRecyclerView.setType("funclens");
                listForENTP.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForENTP, mContext));

                // Changing border color
                tv_d1.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
                tv_d2.setBackground(getResources().getDrawable(R.drawable.rect_solid_red_border));

                setScoreblockdefaultColor();
                break;

            case R.id.tv_s1:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                listForscrore = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(initialValueLists.get(0).getTitle());
                modelForRecyclerView.setPositives(initialValueLists.get(0).getPositives());
                modelForRecyclerView.setAllowableWeakness(initialValueLists.get(0).getAllowableWeaknesses());
                modelForRecyclerView.setType("initialValue");
                listForscrore.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForscrore, mContext));
                //changing color of blocks
                //here we are clicking on score block one that meant only block one will show high lighted with red border rest blocks with black border
                DallBloackdefaultColor();
                scoreBlock1_SELECTED();
                scoreBlock2_NOT_SELECTED();


                break;
            case R.id.tv_s2:
                ll_allDataBlock.setVisibility(View.GONE);
                rv_list.setVisibility(View.VISIBLE);
                tv_initial_result.setVisibility(View.VISIBLE);
                listForscrore = new ArrayList<>();
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(initialValueLists.get(1).getTitle());
                modelForRecyclerView.setPositives(initialValueLists.get(1).getPositives());
                modelForRecyclerView.setAllowableWeakness(initialValueLists.get(1).getAllowableWeaknesses());
                modelForRecyclerView.setType("initialValue");
                listForscrore.add(modelForRecyclerView);
                rv_list.setAdapter(new Ad_CotfuncLensDetails(listForscrore, mContext));
                DallBloackdefaultColor();
                scoreBlock1_NOT_SELECTED();
                scoreBlock2_SELECTED();


                break;


            case R.id.tv_initial_result:
                tv_initial_result.setVisibility(View.GONE);
                rv_list.setVisibility(View.GONE);
                ll_allDataBlock.setVisibility(View.VISIBLE);
                try {
                    tv_allheading.setText(modelFutureLenseDetails.getTribeTipsArray().get(0).getTitle());
                    tv_alldesc.setText(modelFutureLenseDetails.getTribeTipsArray().get(0).getSummary());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tv_allPersuade.setVisibility(View.GONE);
                tv_allPersuadedata.setVisibility(View.GONE);
                DallBloackdefaultColor();
                setScoreblockdefaultColor();
                String seekValue = "";
                if (seekArrayList != null) {
                    for (int l = 0; l < seekArrayList.size(); l++) {
                        modelForRecyclerView = new ModelForRecyclerView();
                        modelForRecyclerView.setHeading("Seek");
                        if (l == 0) {
                            seekValue = "\u25CF " + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                        } else {
                            seekValue = seekValue + "\u25CF " + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                        }
                    }
                    tv_allseek_data.setText(seekValue);
                    tv_allseek.setVisibility(View.VISIBLE);
                    tv_allseek_data.setVisibility(View.VISIBLE);

                }

                if (persuadeArrayList != null) {
                    String persuadeValue = "";
                    for (int l = 0; l < persuadeArrayList.size(); l++) {
                        modelForRecyclerView = new ModelForRecyclerView();
                        modelForRecyclerView.setHeading("Persuade");
                        if (l == 0) {
                            persuadeValue = "\u25CF " + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                        } else {
                            persuadeValue = persuadeValue + "\u25CF " + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                        }
                    }
                    tv_allPersuadedata.setText(persuadeValue);
                    tv_allPersuadedata.setVisibility(View.VISIBLE);
                    tv_allPersuade.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    public void api_getDetails() {

        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                modelFutureLenseDetails = gson.fromJson(object.toString(), ModelFutureLenseDetails.class);

                try {
                    if (!modelFutureLenseDetails.getCOTfunclensAnswersDone()) {
                        ll_topBlock.setVisibility(View.GONE);
                        ll_bottom_block.setVisibility(View.GONE);
                        tv_nodata.setVisibility(View.VISIBLE);
                        tv_name.setVisibility(View.GONE);
                        return;
                    }

                    if (modelFutureLenseDetails.getFuncLensKeyDetail().size() == 0) {
                        ll_topBlock.setVisibility(View.GONE);
                        ll_bottom_block.setVisibility(View.GONE);
                        tv_nodata.setVisibility(View.VISIBLE);
                        tv_name.setVisibility(View.GONE);
                        return;
                    } else {
                        ll_topBlock.setVisibility(View.VISIBLE);
                        ll_bottom_block.setVisibility(View.VISIBLE);
                        tv_nodata.setVisibility(View.GONE);
                    }
                    modelForRecyclerView = new ModelForRecyclerView();
                    modelForRecyclerView.setHeading(modelFutureLenseDetails.getTribeTipsArray().get(0).getSummary());
                    modelForRecyclerView.setType("summery");
                    listForRv.add(modelForRecyclerView);

                    tv_allheading.setText(modelFutureLenseDetails.getTribeTipsArray().get(0).getTitle());
                    tv_alldesc.setText(modelFutureLenseDetails.getTribeTipsArray().get(0).getSummary());
                    tv_allPersuade.setVisibility(View.GONE);
                    tv_allPersuadedata.setVisibility(View.GONE);


                    seekArrayList = modelFutureLenseDetails.getTribeTipsArray().get(0).getSeekArray();
                    persuadeArrayList = modelFutureLenseDetails.getTribeTipsArray().get(0).getPersuadeArray();
                    String seekValue = "";
                    if (seekArrayList != null) {
                        for (int l = 0; l < seekArrayList.size(); l++) {
                            modelForRecyclerView = new ModelForRecyclerView();
                            modelForRecyclerView.setHeading("Seek");
                            if (l == 0) {
                                seekValue = "\u25CF " + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                            } else {
                                seekValue = seekValue + "\u25CF " + seekArrayList.get(l).getValue() + System.getProperty("line.separator");
                            }
                        }
                        tv_allseek_data.setText(seekValue);
                        tv_allseek.setVisibility(View.VISIBLE);
                        tv_allseek_data.setVisibility(View.VISIBLE);

                    }

                    if (persuadeArrayList != null) {
                        String persuadeValue = "";
                        for (int l = 0; l < persuadeArrayList.size(); l++) {
                            modelForRecyclerView = new ModelForRecyclerView();
                            modelForRecyclerView.setHeading("Persuade");
                            if (l == 0) {
                                persuadeValue = "\u25CF " + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                            } else {
                                persuadeValue = persuadeValue + "\u25CF " + persuadeArrayList.get(l).getValue() + System.getProperty("line.separator");
                            }
                        }
                        tv_allPersuadedata.setText(persuadeValue);
                        tv_allPersuadedata.setVisibility(View.VISIBLE);
                        tv_allPersuade.setVisibility(View.VISIBLE);

                    }


                    //rv_list.setAdapter(new Ad_CotfuncLensDetails(listForRv, mContext));
                } catch (Exception e) {

                }
                setAdapter(modelFutureLenseDetails);
                // utility.showToast(mContext, "Success");


            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {

            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                // errorLayout.showError(message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("userId", userId,
                "orgId", orgId

        );
        baseRequest.callAPIPost(1, object, ConstantAPI.api_functionallensdetail);
    }

    public void setAdapter(ModelFutureLenseDetails modelFutureLenseDetails) {
        if (modelFutureLenseDetails.getInitialValueList().size() > 0) {
            initialValueLists = modelFutureLenseDetails.getInitialValueList();
        }
        if (modelFutureLenseDetails.getFuncLensKeyDetail().size() > 0) {
            funcLensKeyDetailList = modelFutureLenseDetails.getFuncLensKeyDetail();
            //here we are checking how many combination are there and showing them accordingly
            initialValueLists.remove(0);
            initialValueLists.remove(2);
            if (initialValueLists.size() == 2) {
                tv_d1.setText(initialValueLists.get(0).getValue());
                tv_d2.setText(initialValueLists.get(1).getValue());
                if (initialValueLists.get(1).getValue().equalsIgnoreCase(initialValueLists.get(1).getValue())) {
                    tv_s1.setText(initialValueLists.get(0).getScore().toString());
                    tv_s2.setText(initialValueLists.get(1).getScore().toString());
                }
                setScoreblockdefaultColor();
            }

        }

        if (modelFutureLenseDetails.getTribeTipsArray().size() > 0) {
            seekArrayList = modelFutureLenseDetails.getTribeTipsArray().get(0).getSeekArray();
            persuadeArrayList = modelFutureLenseDetails.getTribeTipsArray().get(0).getPersuadeArray();
        }

        //here we are creating list for recyclerView
        if (funcLensKeyDetailList != null) {
            for (int j = 0; j < funcLensKeyDetailList.size(); j++) {
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(funcLensKeyDetailList.get(j).getTitle());
                modelForRecyclerView.setDescription(funcLensKeyDetailList.get(j).getDescription());
                modelForRecyclerView.setType("funclens");
                listForRv.add(modelForRecyclerView);
            }
        }

        if (initialValueLists != null) {
            for (int k = 0; k < initialValueLists.size(); k++) {
                modelForRecyclerView = new ModelForRecyclerView();
                modelForRecyclerView.setHeading(initialValueLists.get(k).getTitle());
                modelForRecyclerView.setPositives(initialValueLists.get(k).getPositives());
                modelForRecyclerView.setAllowableWeakness(initialValueLists.get(k).getAllowableWeaknesses());
                modelForRecyclerView.setType("initialValue");
                listForRv.add(modelForRecyclerView);
            }
        }

    }

    public void setScoreblockdefaultColor() {
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("very clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
        }

        if (initialValueLists.get(0).getTitle().toLowerCase().contains("moderate")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("slight")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
        }
        //for second block
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("very clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
        }

        if (initialValueLists.get(1).getTitle().toLowerCase().contains("moderate")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("slight")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
        }

    }

    public void DallBloackdefaultColor() {
        tv_d1.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
        tv_d2.setBackground(getResources().getDrawable(R.drawable.rect_solid_black_border));
    }

    public void scoreBlock1_SELECTED() {
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_orange));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("very clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_red));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("moderate")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_lightgreen));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("slight")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_red_border_green));
        }
    }

    public void scoreBlock1_NOT_SELECTED() {
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("very clear")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
        }

        if (initialValueLists.get(0).getTitle().toLowerCase().contains("moderate")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
        }
        if (initialValueLists.get(0).getTitle().toLowerCase().contains("slight")) {
            tv_s1.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
        }
    }

    public void scoreBlock2_SELECTED() {
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_orange));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("very clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_red));
        }

        if (initialValueLists.get(1).getTitle().toLowerCase().contains("moderate")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_lightgreen));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("slight")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_red_border_green));
        }
    }

    public void scoreBlock2_NOT_SELECTED() {
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_orange));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("very clear")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_red));
        }

        if (initialValueLists.get(1).getTitle().toLowerCase().contains("moderate")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_light_green));
        }
        if (initialValueLists.get(1).getTitle().toLowerCase().contains("slight")) {
            tv_s2.setBackground(getResources().getDrawable(R.drawable.cot_black_border_green));
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_cot_team_mem_details;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }

}

