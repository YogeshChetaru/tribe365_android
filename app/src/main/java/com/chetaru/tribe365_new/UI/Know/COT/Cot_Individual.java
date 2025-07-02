package com.chetaru.tribe365_new.UI.Know.COT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Cot_Individual extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView title_tribe365;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_shape)
    TextView tv_shape;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_coordinator)
    TextView tv_coordinator;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_implementer)
    TextView tv_implementer;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_completer)
    TextView tv_completer;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_monitor_Evat)
    TextView tv_monitor_Evat;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_team_work)
    TextView tv_team_work;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_plant)
    TextView tv_plant;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_resource)
    TextView tv_resource;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_shaper_totkey)
    TextView tv_shaper_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_coordinator_totkey)
    TextView tv_coordinator_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_implementer_totkey)
    TextView tv_implementer_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_completer_totkey)
    TextView tv_completer_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_monitor_Evat_totkey)
    TextView tv_monitor_Evat_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_team_work_totkey)
    TextView tv_team_work_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_plant_totkey)
    TextView tv_plant_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_resource_totkey)
    TextView tv_resource_totkey;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_team_description)
    ImageView img_team_description;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_undo)
    TextView tv_undo;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_shaper)
    TextView tv_txt_shaper;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_coordinator)
    TextView tv_txt_coordinator;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_implementer)
    TextView tv_txt_implementer;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_complete)
    TextView tv_txt_complete;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_monitor_evaluator)
    TextView tv_txt_monitor_evaluator;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_team_worker)
    TextView tv_txt_team_worker;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_plant)
    TextView tv_txt_plant;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_txt_resource)
    TextView tv_txt_resource;
    //  private ErrorLayout errorLayout;
    BaseRequest baseRequest;
    Utility utility;
    Context mContext;
    SessionParam sessionParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.cot_individual);
        init();
    }

    /*used to initialise all the views*/
    private void init() {
        ButterKnife.bind(this);
        utility = new Utility();
        //errorLayout = new ErrorLayout(findViewById(R.id.error_rl));
        this.mContext = this;
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(title_tribe365);
            }
        }
        ButterKnife.bind(this);
        getIndividual();
        //errorLayout.hideError();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.img_team_description, R.id.tv_undo, R.id.tribe365})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_team_description:
                Intent in = new Intent(Cot_Individual.this, Act_Cot_Team_Description.class);
                startActivity(in);
                break;
            case R.id.tv_undo:
                in = new Intent(Cot_Individual.this, Update_Cot_Question.class);
                in.putExtra("checklist", "");
                startActivity(in);
                break;

            case R.id.tribe365:
                callHomeAct(mContext);
                break;
        }
    }

    /*API call to get cot individual summary.
     */
    public void getIndividual() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                Log.e("api_get_COTQuestions=>", Json);
                JSONObject jsonObject = (JSONObject) object;
                tv_shape.setText(jsonObject.optString("shaper"));
                tv_coordinator.setText(jsonObject.optString("coordinator"));
                tv_implementer.setText(jsonObject.optString("implementer"));
                tv_completer.setText(jsonObject.optString("completerFinisher"));
                tv_monitor_Evat.setText(jsonObject.optString("monitorEvaluator"));
                tv_team_work.setText(jsonObject.optString("teamworker"));
                tv_plant.setText(jsonObject.optString("plant"));
                tv_resource.setText(jsonObject.optString("resourceInvestigator"));
                CheckColor(jsonObject);
                JSONObject jsonObject1 = jsonObject.optJSONObject("totalKeyCount");
                try {
                    tv_shaper_totkey.setText(jsonObject1.optString("shaper"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                tv_coordinator_totkey.setText(jsonObject1.optString("coordinator"));
                tv_implementer_totkey.setText(jsonObject1.optString("implementer"));
                tv_completer_totkey.setText(jsonObject1.optString("completerFinisher"));
                tv_monitor_Evat_totkey.setText(jsonObject1.optString("monitorEvaluator"));
                tv_team_work_totkey.setText(jsonObject1.optString("teamworker"));
                tv_plant_totkey.setText(jsonObject1.optString("plant"));
                tv_resource_totkey.setText(jsonObject1.optString("resourceInvestigator"));
//--------set Text dynamic ----------------------------------------------------------------------------------------------------------------------

                JSONObject jsonObject2 = jsonObject.optJSONObject("mapersArray");
                if (tv_txt_shaper.getText().equals("Shaper")) {
                    try {
                        tv_txt_shaper.setText(jsonObject2.optString("shaper"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if (tv_txt_coordinator.getText().equals("Coordinator")) {
                    assert jsonObject2 != null;
                    String upperString = jsonObject2.optString("coordinator").substring(0, 1).toUpperCase() + jsonObject2.optString("coordinator").substring(1);
                    tv_txt_coordinator.setText(upperString);
                }
                if (tv_txt_implementer.getText().equals("Implementer")) {
                    assert jsonObject2 != null;
                    String upperString = jsonObject2.optString("implementer").substring(0, 1).toUpperCase() + jsonObject2.optString("implementer").substring(1);
                    tv_txt_implementer.setText(upperString);
                }
                if (tv_txt_complete.getText().equals("Completer")) {
                    assert jsonObject2 != null;
                    String upperString = jsonObject2.optString("completerFinisher").substring(0, 1).toUpperCase() + jsonObject2.optString("completerFinisher").substring(1);
                    tv_txt_complete.setText(upperString);
                }
                if (tv_txt_monitor_evaluator.getText().equals("Monitor Evaluator")) {
                    assert jsonObject2 != null;
                    String upperString = jsonObject2.optString("monitorEvaluator").substring(0, 1).toUpperCase() + jsonObject2.optString("monitorEvaluator").substring(1);
                    tv_txt_monitor_evaluator.setText(upperString);
                }
                if (tv_txt_team_worker.getText().equals("Team Worker")) {
                    assert jsonObject2 != null;
                    String upperString = jsonObject2.optString("teamworker").substring(0, 1).toUpperCase() + jsonObject2.optString("teamworker").substring(1);

                    tv_txt_team_worker.setText(upperString);
                }
                if (tv_txt_plant.getText().equals("Plant")) {
                    assert jsonObject2 != null;
                    String upperString = jsonObject2.optString("plant").substring(0, 1).toUpperCase() + jsonObject2.optString("plant").substring(1);

                    tv_txt_plant.setText(upperString);
                }
                if (tv_txt_resource.getText().equals("Resource Investigator")) {
                    assert jsonObject2 != null;
                    String upperString = jsonObject2.optString("resourceInvestigator").substring(0, 1).toUpperCase() + jsonObject2.optString("resourceInvestigator").substring(1);

                    tv_txt_resource.setText(upperString);
                }
//----------------------------------------------------------------------------------------------------------------------------------
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //errorLayout.showError(message);
                utility.showToast(mContext, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
//                errorLayout.showError(message);
                utility.showToast(mContext, message);
            }
        });
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        baseRequest.callAPIGET(1, map, ConstantAPI.getCOTindividualSummary);
    }

    /*here we check shaper numbers then set color according to those numbers
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void CheckColor(JSONObject jsonObject) {
//----------------shaper------------------------------
        if (jsonObject.optString("shaper").equals("0")) {

            tv_shape.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_shape.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("shaper").equals("1")) {
            tv_shape.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("shaper").equals("2")) {
            tv_shape.setTextColor(ContextCompat.getColor(this, R.color.black));
            tv_shape.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));

        } else if (jsonObject.optString("shaper").equals("3")) {
            tv_shape.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_shape.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }
//----------------coordinator------------------------------
        if (jsonObject.optString("coordinator").equals("0")) {
            tv_coordinator.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            ;
            tv_coordinator.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("coordinator").equals("1")) {
            tv_coordinator.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("coordinator").equals("2")) {
            tv_coordinator.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_coordinator.setTextColor(ContextCompat.getColor(this, R.color.black));

        } else if (jsonObject.optString("coordinator").equals("3")) {
            tv_coordinator.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_coordinator.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }
        //----------------implementer------------------------------
        if (jsonObject.optString("implementer").equals("0")) {
            tv_implementer.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_implementer.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("implementer").equals("1")) {
            tv_implementer.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("implementer").equals("2")) {
            tv_implementer.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_implementer.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("implementer").equals("3")) {
            tv_implementer.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_implementer.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }


//----------------completerFinisher------------------------------
        if (jsonObject.optString("completerFinisher").equals("0")) {
            tv_completer.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_completer.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("completerFinisher").equals("1")) {
            tv_completer.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("completerFinisher").equals("2")) {
            tv_completer.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_completer.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("completerFinisher").equals("3")) {
            tv_completer.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_completer.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }

//----------------monitorEvaluator------------------------------
        if (jsonObject.optString("monitorEvaluator").equals("0")) {
            tv_monitor_Evat.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_monitor_Evat.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("monitorEvaluator").equals("1")) {
            tv_monitor_Evat.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("monitorEvaluator").equals("2")) {
            tv_monitor_Evat.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_monitor_Evat.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("monitorEvaluator").equals("3")) {
            tv_monitor_Evat.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_monitor_Evat.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }

//----------------teamworker------------------------------
        if (jsonObject.optString("teamworker").equals("0")) {
            tv_team_work.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_team_work.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("teamworker").equals("1")) {
            tv_team_work.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("teamworker").equals("2")) {
            tv_team_work.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_team_work.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("teamworker").equals("3")) {
            tv_team_work.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_team_work.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }

//----------------plant------------------------------
        if (jsonObject.optString("plant").equals("0")) {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_plant.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("plant").equals("1")) {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("plant").equals("2")) {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_plant.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("plant").equals("3")) {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }

        //----------------resourceInvestigator------------------------------
        if (jsonObject.optString("resourceInvestigator").equals("0")) {
            tv_plant.setBackground(getResources().getDrawable(R.drawable.white_colorblock));
            tv_plant.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("resourceInvestigator").equals("1")) {
            tv_resource.setBackground(getResources().getDrawable(R.drawable.green_colorblock));
        } else if (jsonObject.optString("resourceInvestigator").equals("2")) {
            tv_resource.setBackground(getResources().getDrawable(R.drawable.yellow_colorblock));
            tv_resource.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (jsonObject.optString("resourceInvestigator").equals("3")) {
            tv_resource.setBackground(getResources().getDrawable(R.drawable.blue_colorblock));
        } else {
            tv_resource.setBackground(getResources().getDrawable(R.drawable.orange_colorblock));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIndividual();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
    @Override
    public int getLayoutId() {
        return R.layout.cot_individual;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}
