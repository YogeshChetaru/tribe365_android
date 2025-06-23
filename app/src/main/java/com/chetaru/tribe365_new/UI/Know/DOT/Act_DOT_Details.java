package com.chetaru.tribe365_new.UI.Know.DOT;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chetaru.tribe365_new.API.Models.BeliefValue;
import com.chetaru.tribe365_new.API.Models.ModelDotDetail;
import com.chetaru.tribe365_new.API.Models.ModelValueList;
import com.chetaru.tribe365_new.API.Models.Model_AddBeliefs;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.Adapter.AD_BeliefHorizontal;
import com.chetaru.tribe365_new.Adapter.Ad_ValueList;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.AppTour.Link_click_Activity;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.DismissType;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.orientation;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.GuideView;
import com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Listener.GuideListener;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Home.Act_Help;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.UI.Home.Act_Splash;
import com.chetaru.tribe365_new.utility.RecyclerItemClickListener;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class Act_DOT_Details extends BaseActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_belief_detailslist)
    RecyclerView rv_belief_list;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_value_list)
    RecyclerView rv_value_list;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_vision)
    TextView tv_vision;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_mission)
    TextView tv_mission;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_focus)
    TextView tv_focus;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_notfound)
    TextView tv_notfound;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_evidence_vission)
    ImageView iv_evidence_vission;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.iv_add_evidence_vission)
    ImageView iv_add_evidence_vission;
    @BindView(R.id.iv_mission_evidence)
    ImageView iv_mission_evidence;
    @BindView(R.id.iv_add_mission_evidence)
    ImageView iv_add_mission_evidence;
    @BindView(R.id.iv_focus_evidence)
    ImageView iv_focus_evidence;
    @BindView(R.id.iv_add_focus_evidence)
    ImageView iv_add_focus_evidence;
    @BindView(R.id.iv_belief_evidence)
    ImageView iv_belief_evidence;
    @BindView(R.id.tv_value_evidence)
    TextView tv_value_evidence;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.tribe365)
    ImageView tribe365;
    @BindView(R.id.rl_value)
    RelativeLayout rl_value;
    @BindView(R.id.rl_belief)
    RelativeLayout rl_belief;
    @BindView(R.id.ll_dot_help)
    LinearLayout ll_dot_help;
    @BindView(R.id.iv_add_belief_evidence)
    ImageView iv_add_belief_evidence;
    @BindView(R.id.sv_add_organisation)
    NestedScrollView sv_add_organisation;
    @BindView(R.id.ll_dot_parent)
    LinearLayout ll_dot_parent;
    List<Model_AddBeliefs> beliefList;
    Model_AddBeliefs model_addBeliefs;
    AD_BeliefHorizontal adapter_beliefsList;
    SessionParam sessionParam;
    BaseRequest baseRequest;
    Utility utility;
    String data = "";
    ModelDotDetail modelDotDetail;
    int index = 0;
    String beliefId = "";
    String path = "";
    ArrayList<Integer> li_size = new ArrayList<>();
    String checklist = "";
    SharedPreferences prefs;
    Dialog dialog;
    RecyclerView rv_dailog_belief_list;
    ImageView iv_beliefs_cancel;
    int introType = 0;
    boolean handelBackPress = false;
    private GuideView mGuideView;
    private GuideView.Builder builder;
    boolean isTablet;
    String SHOWCASE_ID= "SHOWCASE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_dot_details_new);

        try {
            prefs = getSharedPreferences("Checklist", MODE_PRIVATE);
            checklist = prefs.getString("checklist", "");
            handelBackPress = getIntent().getBooleanExtra("backHandel", false);
            path = getIntent().getStringExtra("path");
        } catch (Exception e) {
            e.printStackTrace();
        }
        isTablet=getResources().getBoolean(R.bool.isTablet);
        int getIntroType = getIntent().getIntExtra("introType", 0);
        init();
       /* try {
            if (getIntroType != 0)
                sv_add_organisation.post(new Runnable() {
                    @Override
                    public void run() {
                        sv_add_organisation.fullScroll(NestedScrollView.FOCUS_UP);
                        // sv_add_organisation.scrollTo(0, sv_add_organisation.getBottom());

                    }
                });
        }catch (Exception e){
            e.printStackTrace();
        }*/


        if (getIntroType != 0) {
            Handler h = new Handler();
            long delayInMilliseconds = 1000;
            h.postDelayed(new Runnable() {
                public void run() {

                    ShowIntro("Tribe365", getString(R.string.comp_logo_ppt), R.id.tribe365, 8);
                   // MaterialSHowCase(R.id.tribe365,getString(R.string.comp_logo_ppt));
                    //showCaseSequence();

                }
            }, delayInMilliseconds);

        }
//tribe365.performClick();

    }

    /*is used to initialise all the views
     */
    public void init() {
        ButterKnife.bind(this);
        utility = new Utility();
        sessionParam = new SessionParam(mContext);


        rv_belief_list.setVisibility(View.VISIBLE);
        try {
            data = getIntent().getStringExtra("data");
        } catch (NullPointerException npe) {
            finishAllActivities();

        }
        if (sessionParam.role.equalsIgnoreCase("3")) {
            iv_edit.setVisibility(View.GONE);
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
            //ll_addBlock.setVisibility(View.GONE);
        }
        Gson gson = new Gson();
        try {
            modelDotDetail = gson.fromJson(data, ModelDotDetail.class);
            tv_vision.setText(modelDotDetail.getVision());
            tv_mission.setText(modelDotDetail.getMission());
            tv_focus.setText(modelDotDetail.getFocus());

            model_addBeliefs = new Model_AddBeliefs();
            beliefList = new ArrayList<>();
        } catch (NullPointerException npe) {

        }

        getDotDetail();
//

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 2);
                rv_belief_list.setLayoutManager(layoutManager1);
            }else {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                rv_belief_list.setLayoutManager(layoutManager);
            }
        rv_belief_list.setNestedScrollingEnabled(false);
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                sv_add_organisation.fullScroll(ScrollView.FOCUS_UP);
//                ll_dot_parent.setVisibility(View.VISIBLE);
//
//            }
//        }, 2000);
    }
//        rv_value_list.setLayoutManager(layoutManager1);
//        //rv_belief_list.getRecycledViewPool().setMaxRecycledViews(0,0);
//
//        rv_belief_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_belief_list, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                index = position;
//
//                beliefId = beliefList.get(index).getBeleifId();
//                for (int i = 0; i < beliefList.size(); i++) {
//                    beliefList.get(i).setIsSelected("f");
//                }
//                beliefList.get(position).setIsSelected("t");
//                //rv_belief_list.setAdapter(new AD_BeliefHorizontal(beliefList,mContext));
//                adapter_beliefsList.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        }));
//
//
//
//        //here we are setting up list according to our adapter coz we have splited value then added it in single string
//        //now we are getting data from Model and structure of that is different so we have to handle this accordingly.
//        //so here we are fetching values from new model class and matching it with previous one.
//        //its bit complicated but you can't get things easily ;)
//        //m doing all this to avoid to create a new adapter.
//        //thank me later :P
//
//        try {
//            for (int i = 0; i < modelDotDetail.getBelief().size(); i++) {
//                String values = "";
//                ModelValueList modelValueList = new ModelValueList();
//                ArrayList<ModelValueList> valueList = new ArrayList<>();
//                model_addBeliefs.setBeliefName(modelDotDetail.getBelief().get(i).getName());
//                model_addBeliefs.setBeleifId(modelDotDetail.getBelief().get(i).getId() + "");
//                //model_addBeliefs.setId(modelDotDetail.getBelief().get(i).getId());
//
//
//                //model_addBeliefs.setBeleifId(modelDotDetail.getBeliefId()+"");
//                if (modelDotDetail.getBelief().get(i).getBeliefValue().size() > 0) {
//                    for (int j = 0; j < modelDotDetail.getBelief().get(i).getBeliefValue().size(); j++) {
//                        if (j == 0) {
//                            //values = modelDotDetail.getBelief().get(j).getBeliefValue().get(j).getName() + ",";
//                            values = modelDotDetail.getBelief().get(i).getBeliefValue().get(j).getName() + ",";
//                        } else {
//                            values = values + modelDotDetail.getBelief().get(i).getBeliefValue().get(j).getName() + ",";
//                        }
//                        modelValueList.setName(modelDotDetail.getBelief().get(i).getBeliefValue().get(j).getName());
//                        modelValueList.setId(modelDotDetail.getBelief().get(i).getBeliefValue().get(j).getId());
//                        modelValueList.setIsCheckedString("t");
//                        valueList.add(modelValueList);
//                    }
//                    model_addBeliefs.setValue(values);
//                    model_addBeliefs.setValueList(modelDotDetail.getBelief().get(i).getBeliefValue());
//                    model_addBeliefs.setValueSelectedlsit(valueList);
//                }
//
//                beliefList.add(model_addBeliefs);
//                //                    //Size Set======================
//                int lar_size = 0;
//                for (int k = 0; k < beliefList.size(); k++) {
//                    li_size.add(beliefList.get(k).getValueList().size());
//                }
//                Object[] st = li_size.toArray();
//                for (Object s : st) {
//                    if (li_size.indexOf(s) != li_size.lastIndexOf(s)) {
//                        li_size.remove(li_size.lastIndexOf(s));
//                    }
//                }
//                Collections.sort(li_size, new Comparator<Integer>() {
//                    @Override
//                    public int compare(Integer lhs, Integer rhs) {
//                        return lhs.compareTo(rhs);
//                    }
//                });
//                if (sessionParam.role.equals("1")) {
//                    ViewGroup.LayoutParams params = rv_belief_list.getLayoutParams();
//                    params.height = 150 * li_size.get(li_size.size() - 1);
//                    rv_belief_list.setLayoutParams(params);
//
//                } else {
//                    ViewGroup.LayoutParams params = rv_belief_list.getLayoutParams();
//                    params.height = 230 * li_size.get(li_size.size() - 1);
//                    rv_belief_list.setLayoutParams(params);
//                }
//
//
//                //                    //----------------------------------
//                model_addBeliefs = new Model_AddBeliefs();
//                //now setting that list on adapter let see whether it worked or not :/
//                if (beliefList.size() > 0) {
//
//                    adapter_beliefsList = new AD_BeliefHorizontal(beliefList, mContext);
//                    //oh i forgot to add adapter in recycler view my bad. running again
//
//                    rv_belief_list.setAdapter(adapter_beliefsList);
//                    beliefList.get(0).setIsSelected("t");
//                    beliefId = beliefList.get(0).getBeleifId();
//
//                } else {
//                    rv_belief_list.setVisibility(View.GONE);
//                    rl_value.setVisibility(View.GONE);
//                    rl_belief.setVisibility(View.GONE);
//                    tv_notfound.setVisibility(View.VISIBLE);
//                }
//            }
//        } catch (IndexOutOfBoundsException iobe) {
//            finishAllActivities();
//            startActivity(new Intent(mContext, Act_Splash.class));
//        } catch (NullPointerException npe) {
//
//        }
//
//        try {
//            path = getIntent().getStringExtra("path");
//            if (path.equalsIgnoreCase("rating")) {
//                getDotDetail();
//
//            }
//        } catch (NullPointerException npe) {
//
//        }
//
//
//        //voillaaa it worked as expected
//    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){

        }else{

        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.iv_add_belief_evidence, R.id.tribe365, R.id.ll_dot_help, R.id.iv_edit, R.id.iv_evidence_vission,
            R.id.tv_vision, R.id.tv_mission, R.id.tv_focus,
            R.id.iv_add_evidence_vission, R.id.iv_mission_evidence, R.id.iv_add_mission_evidence, R.id.iv_focus_evidence, R.id.iv_add_focus_evidence, R.id.iv_belief_evidence, R.id.tv_value_evidence})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tribe365:

                finish();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("Checklist", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                callHomeAct(mContext);
                break;

            case R.id.tv_vision:
                try {
                    if (!modelDotDetail.getVisionUrl().trim().equals("") || !modelDotDetail.getVisionDesc().trim().equals("")) {
                        Intent intent = new Intent(this, Link_click_Activity.class);
                        intent.putExtra("TitleName", "Vision");
                        intent.putExtra("subTitleName", modelDotDetail.getVision());
                        intent.putExtra("Description", modelDotDetail.getVisionDesc());
                        intent.putExtra("videoURL", modelDotDetail.getVisionUrl());
                        startActivity(intent);
                        // overridePendingTransition(R.anim.cb_fade_in, R.anim.cb_face_out);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_mission:
                try {


                    if (!modelDotDetail.getMissionUrl().trim().equals("") || !modelDotDetail.getMissionDesc().trim().equals("")) {
                        Intent intent1 = new Intent(this, Link_click_Activity.class);
                        intent1.putExtra("TitleName", "Mission");
                        intent1.putExtra("subTitleName", modelDotDetail.getMission());
                        intent1.putExtra("Description", modelDotDetail.getMissionDesc());
                        intent1.putExtra("videoURL", modelDotDetail.getMissionUrl());
                        startActivity(intent1);
                        //   overridePendingTransition(R.anim.cb_fade_in, R.anim.cb_face_out);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_focus:
                try {
                    if (!modelDotDetail.getFocusUrl().trim().equals("") || !modelDotDetail.getFocusDesc().trim().equals("")) {
                        Intent intent2 = new Intent(this, Link_click_Activity.class);
                        intent2.putExtra("TitleName", "Focus");
                        intent2.putExtra("subTitleName", modelDotDetail.getFocus());
                        intent2.putExtra("Description", modelDotDetail.getFocusDesc());
                        intent2.putExtra("videoURL", modelDotDetail.getFocusUrl());
                        startActivity(intent2);
                        //  overridePendingTransition(R.anim.cb_fade_in, R.anim.cb_face_out);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_edit:
                finish();

                break;


            case R.id.iv_add_belief_evidence:


                break;

            case R.id.iv_belief_evidence:

                break;

            case R.id.tv_value_evidence:
                if (beliefList.size() == 0) {
                    utility.showToast(mContext, "Add belief first");
                    return;
                }
                dialogDepartment();

                break;

            case R.id.ll_dot_help:
                startActivity(new Intent(mContext, Act_Help.class).putExtra("url", "dot.html"));
                break;
        }
    }

    /* is used to show department list
     */
    public void dialogDepartment() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_department_list);
        dialog.setCanceledOnTouchOutside(true);
        final RecyclerView rv_list = dialog.findViewById(R.id.rv_list);
        final Button bt_done = dialog.findViewById(R.id.bt_done);
        final Button bt_closs = dialog.findViewById(R.id.bt_closs);
        final TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText("Values");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_list.setLayoutManager(layoutManager);
        final ArrayList<ModelValueList> test = new ArrayList<>();
        for (int i = 0; i < beliefList.get(index).getValueList().size(); i++) {
            ModelValueList modelValueList = new ModelValueList();
            modelValueList.setName(beliefList.get(index).getValueList().get(i).getName());
            modelValueList.setId(beliefList.get(index).getValueList().get(i).getId());
            //modelValueList.set(beliefList.get(index).getValueList().get(i).getName());
            test.add(modelValueList);
        }
        rv_list.setAdapter(new Ad_ValueList(test, mContext, "", "evidence"));
        rv_list.addOnItemTouchListener(new RecyclerItemClickListener(mContext, rv_list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                dialog.dismiss();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));
        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_closs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    /* is an API call to get data dot details
     */
    public void getDotDetail() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Gson gson = new Gson();
                modelDotDetail = gson.fromJson(object.toString(), ModelDotDetail.class);
                model_addBeliefs = new Model_AddBeliefs();
                beliefList = new ArrayList<>();
                tv_vision.setText(modelDotDetail.getVision());
                tv_mission.setText(modelDotDetail.getMission());
                tv_focus.setText(modelDotDetail.getFocus());

                try {
                    for (int i = 0; i < modelDotDetail.getBelief().size(); i++) {
                        String values = "";
                        ModelValueList modelValueList = new ModelValueList();
                        ArrayList<ModelValueList> valueList = new ArrayList<>();
                        model_addBeliefs.setBeliefName(modelDotDetail.getBelief().get(i).getName());
                        model_addBeliefs.setBeleifId(modelDotDetail.getBelief().get(i).getId() + "");
                        model_addBeliefs.setBeliefUrl(modelDotDetail.getBelief().get(i).getBeliefUrl().trim());
                        model_addBeliefs.setBeliefDesc(modelDotDetail.getBelief().get(i).getBeliefDesc().trim());
                        //model_addBeliefs.setId(modelDotDetail.getBelief().get(i).getId());


                        //model_addBeliefs.setBeleifId(modelDotDetail.getBeliefId()+"");
                        if (modelDotDetail.getBelief().get(i).getBeliefValue().size() > 0) {
                            for (int j = 0; j < modelDotDetail.getBelief().get(i).getBeliefValue().size(); j++) {
                                if (j == 0) {
                                    //values = modelDotDetail.getBelief().get(j).getBeliefValue().get(j).getName() + ",";
                                    values = modelDotDetail.getBelief().get(i).getBeliefValue().get(j).getName() + ",";
                                } else {
                                    values = values + modelDotDetail.getBelief().get(i).getBeliefValue().get(j).getName() + ",";
                                }
                                modelValueList.setName(modelDotDetail.getBelief().get(i).getBeliefValue().get(j).getName());
                                modelValueList.setId(modelDotDetail.getBelief().get(i).getBeliefValue().get(j).getId());
                                modelValueList.setIsCheckedString("t");
                                valueList.add(modelValueList);
                            }
                            model_addBeliefs.setValue(values);
                            model_addBeliefs.setValueList(modelDotDetail.getBelief().get(i).getBeliefValue());
                            model_addBeliefs.setValueSelectedlsit(valueList);
                        }
                        beliefList.add(model_addBeliefs);
                        model_addBeliefs = new Model_AddBeliefs();

                    }

                } catch (IndexOutOfBoundsException iobe) {
                    finishAllActivities();
                    startActivity(new Intent(mContext, Act_Splash.class));
                }

                //now setting that list on adapter let see whether it worked or not :/
                if (beliefList.size() > 0) {
                    //                    //Size Set======================
                    int lar_size = 0;
                    for (int k = 0; k < beliefList.size(); k++) {
                        li_size.add(beliefList.get(k).getValueList().size());
                    }
                    Object[] st = li_size.toArray();
                    for (Object s : st) {
                        if (li_size.indexOf(s) != li_size.lastIndexOf(s)) {
                            li_size.remove(li_size.lastIndexOf(s));
                        }
                    }
                    Collections.sort(li_size, new Comparator<Integer>() {
                        @Override
                        public int compare(Integer lhs, Integer rhs) {
                            return lhs.compareTo(rhs);
                        }
                    });

                   /* if (sessionParam.role.equals("1")) {
                        ViewGroup.LayoutParams params = rv_belief_list.getLayoutParams();
                        params.height = 240 * li_size.get(li_size.size() - 1);
                        rv_belief_list.setLayoutParams(params);

                    } else {

                    }*/
                    /*ViewGroup.LayoutParams params = rv_belief_list.getLayoutParams();
                    params.height = 280 * li_size.get(li_size.size() - 1);
                    rv_belief_list.setLayoutParams(params);*/
                    //----------------------------------
                    adapter_beliefsList = new AD_BeliefHorizontal(beliefList, mContext, checklist, new AD_BeliefHorizontal.ClickBeliefListener() {
                        @Override
                        public void beliefListener(Model_AddBeliefs modelAddBeliefs) {
                            try {
                                if (!modelAddBeliefs.getBeliefUrl().trim().equals("") || !modelAddBeliefs.getBeliefDesc().trim().equals("")) {
                                    Intent intent = new Intent(mContext, Link_click_Activity.class);
                                    intent.putExtra("TitleName", "Belief");
                                    intent.putExtra("subTitleName", modelAddBeliefs.getBeliefName());
                                    intent.putExtra("Description", modelAddBeliefs.getBeliefDesc());
                                    intent.putExtra("videoURL", modelAddBeliefs.getBeliefUrl());
                                    startActivity(intent);
                                    // overridePendingTransition(R.anim.cb_fade_in, R.anim.cb_face_out);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void beliefValueListener(BeliefValue beliefValue) {
                            try {
                                if (!beliefValue.getValueUrl().trim().equals("") || !beliefValue.getValueDesc().trim().equals("")) {
                                    Intent intent = new Intent(mContext, Link_click_Activity.class);
                                    intent.putExtra("TitleName", "Value");
                                    intent.putExtra("subTitleName", beliefValue.getName());
                                    intent.putExtra("Description", beliefValue.getValueDesc());
                                    intent.putExtra("videoURL", beliefValue.getValueUrl());
                                    startActivity(intent);
                                    // overridePendingTransition(R.anim.cb_fade_in, R.anim.cb_face_out);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    //oh i forgot to add adapter in recycler view my bad. running again
                    rv_belief_list.setAdapter(adapter_beliefsList);
//                    beliefList.get(0).setIsSelected("t");
//                    beliefId = beliefList.get(0).getBeleifId();
//                    rv_value_list.setAdapter(new AD_BeliefHorizontal(beliefList.get(0).getValueList(), mContext, ""));

                } else {
                    rv_belief_list.setVisibility(View.GONE);
                    rl_value.setVisibility(View.GONE);
                    rl_belief.setVisibility(View.GONE);
                    tv_notfound.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                if (message.equalsIgnoreCase("DOT Detail not found")) {
                    utility.showToast(mContext, message);
                } else {
                    utility.showToast(mContext, message);
                }
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("orgId", sessionParam.orgId
                //JsonObject object = Functions.getClient().getJsonMapObject("orgId", "9"
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.api_dotDetail);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (path != null) {
            if (!path.equals("noti")) {
                if (checklist != null) {
                    if (checklist.equals("checklist")) {
                        finish();
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("Checklist", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                    } else {
                        if (handelBackPress) {
                            startActivity(new Intent(mContext, Act_Home.class));
                        } else {
                            finish();
                        }
                    }
                } else {
                    if (handelBackPress) {
                        startActivity(new Intent(mContext, Act_Home.class));
                    } else {
                        finish();
                    }
                }
            } else {
                if (handelBackPress) {
                    startActivity(new Intent(mContext, Act_Home.class));
                } else {
                    finish();
                }
            }
        } else {
            finish();
        }

    }

    private void ShowIntro(String title, String text, int viewId, final int type) {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int sizeInPixels = 18;
        if (isTablet){
            sizeInPixels=24;
        }else {
            sizeInPixels=20;
        }
        builder = new GuideView.Builder(this)
                //.setTitle(title)
                .setContentText(text)
                .setTargetView(findViewById(viewId))
                // .setContentSpan((Spannable) Html.fromHtml("<font color='white' ,background color='red'>text</p>"))
                .setContentTextSize(sizeInPixels)//optional
                .setTitleTextSize(sizeInPixels)//optional
                .setDismissType(DismissType.skipe) //optional - default dismissible by TargetView
                .setTitleGravity(Gravity.LEFT)
                .setContentGravity(Gravity.LEFT)
                .setButtonGravity(Gravity.LEFT)

                // .setButtonBackground(ContextCompat.getDrawable(this, R.drawable.rounded))
                //.setButtonTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                // .setPaddingTitle(50,10,40,10)
                //.setPaddingMessage(50,10,40,10)
                .setPaddingButton(10, 0, 10, 0)
                .setButtonText("Skip Tutorial")
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                    }

                });
       /* if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
             builder.setOrientation(orientation.portrait);
        }else if (!isTablet){
            builder.setOrientation(orientation.portrait);
        }else {
            builder.setOrientation(orientation.landscape);
        }*/
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // builder.setOrientation(orientation.landscape);
            if (!isTablet){
                builder.setOrientation(orientation.portrait);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }else {
                builder.setOrientation(orientation.landscape);
            }
        }

        if (type == 9) {
            builder.setGravity(com.chetaru.tribe365_new.UI.AppTour.ShowCaseView.Config.Gravity.auto);
        }

        if(type == 10){
            builder.setButtonText("End").build();
            builder.setNextButtonText("").build();
            mGuideView.dismiss();
        }


        mGuideView = builder.build();
        mGuideView.show();

        updatingForDynamicLocationViews();

        mGuideView.mMessageView.skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGuideView.dismiss();
                callHomeAct(mContext);
            }
        });
        mGuideView.mMessageView.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type < 10)
                    mGuideView.dismiss();
                if (type == 8) {
                    sv_add_organisation.post(new Runnable() {
                        @Override
                        public void run() {
                            sv_add_organisation.fullScroll(NestedScrollView.FOCUS_UP);
                            // sv_add_organisation.scrollTo(0, sv_add_organisation.getBottom());

                        }
                    });
                    Handler h = new Handler();
                    long delayInMilliseconds = 1000;
                    h.postDelayed(new Runnable() {
                        public void run() {

                            // ShowIntro("SetTheme", getString(R.string.belief_ppt), R.id.rv_belief_detailslist, 8);
                            ShowIntro("SetTheme", getString(R.string.belief_ppt), R.id.rv_belief_detailslist, 9);

                        }
                    }, delayInMilliseconds);
                    // ShowIntro("Tribe365", getString(R.string.comp_logo_ppt), R.id.tribe365, 9);
                } else if (type == 9) {
                    //ShowIntro("app tour",getString(R.string.thanks_ppt), R.id.finish_view, 10);


                    Intent intent = new Intent(mContext, Act_Home.class);
                    intent.putExtra("appTourType", 4);
                    startActivity(intent);
                }
                /* } else if (type == 3) {
                ShowIntro("Tribe365", getString(R.string.belief_ppt), R.id.rv_list_values, 4);
            } else if (type == 4) {
                ShowIntro("Tribe365", getString(R.string.comp_logo_ppt), R.id.iv_top_companylogo, 5);

            }*/
            }

        });


    }

    private void updatingForDynamicLocationViews() {
        rv_belief_list.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mGuideView.updateGuideViewLocation();
            }
        });
    }
    public void MaterialSHowCase(int viewId,String title){
        new MaterialShowcaseView.Builder(this)
                .setTarget(findViewById(viewId))
                .setDismissText("Skip")
                .setContentText(title)
                .setDelay(500) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse(SHOWCASE_ID) // provide a unique ID used to ensure it is only shown once
                .show();

    }
    private void showCaseSequence(){
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);
        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                utility.showToast(mContext,"Item :-"+ position);
            }
        });

        sequence.setConfig(config);


        sequence.addSequenceItem(findViewById(R.id.tribe365),
                getString(R.string.comp_logo_ppt), "GOT IT");


        sequence.addSequenceItem(new MaterialShowcaseView.Builder(this)
                .setTarget(findViewById(R.id.rv_belief_detailslist))
                .setDismissText("GOT IT")
                .setContentText(getString(R.string.belief_ppt))
                .withRectangleShape()
                .build());



        sequence.start();
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_dot_details_new;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}