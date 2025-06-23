package com.chetaru.tribe365_new.UI.UserInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chetaru.tribe365_new.API.retrofit.ConstantAPI;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.API.retrofit.BaseRequest;
import com.chetaru.tribe365_new.API.retrofit.Functions;
import com.chetaru.tribe365_new.API.retrofit.RequestReciever;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.ErrorLayout;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Act_Forgot_Password extends BaseActivity {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_email)
    EditText et_email;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    Utility utility;
    BaseRequest baseRequest;
    SessionParam sessionParam;
    private ErrorLayout errorLayout;

    public Act_Forgot_Password(Context mMockContext) {
        mContext=mMockContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.act_forgot_password);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        errorLayout = new ErrorLayout(findViewById(R.id.error_rl));

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.tv_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (et_email.getText().toString().trim().isEmpty()) {
                   // utility.showToast(mContext, "Please enter email id");
                    utility.showToast(mContext, getString(R.string.enter_email_id));

                }
                if (!Utility.isValidEmail(et_email.getText().toString().trim())) {
                   // utility.showToast(mContext, "Please enter valid Email.");
                    utility.showToast(mContext, getString(R.string.valid_email));
                    return;

                } else {
                    api_ForgotPassword();
                }
                break;
        }
    }

    public void api_ForgotPassword() {
        baseRequest = new BaseRequest(mContext);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                //utility.showToast(mContext, "Email sent successfully.");
                try {
                    String msg = "";
                    JSONObject obj = new JSONObject(Json);
                    msg = obj.optString("message");
                    utility.showToast(mContext, msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
                mContext.startActivity(new Intent(Act_Forgot_Password.this,ActLogin.class));
                finish();
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                if (message.equalsIgnoreCase("Email sent to you successfully")) {
                    utility.showToast(mContext, message);
                    mContext.startActivity(new Intent(Act_Forgot_Password.this,ActLogin.class));
                    finish();
                } else {
                    utility.showToast(mContext, message);
                }

            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                errorLayout.showError(message);
            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject("email", et_email.getText().toString()
        );
        baseRequest.callAPIPost(1, object, ConstantAPI.api_forgotPassword);
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_forgot_password;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
