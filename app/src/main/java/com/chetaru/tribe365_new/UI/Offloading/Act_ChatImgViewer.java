package com.chetaru.tribe365_new.UI.Offloading;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.CustomerSupport.Act_CustomerSupport;
import com.chetaru.tribe365_new.UI.CustomerSupport.Act_SupportListDetails;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_ChatImgViewer extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tribe365)
    ImageView tribe365;

    ImageView iv_image;
    String imgLink = "",backHandel="";
    SessionParam sessionParam;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.act_chat_img_viewer);
        ButterKnife.bind(this);

        tribe365.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });

        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        iv_image = findViewById(R.id.iv_image);
        imgLink = getIntent().getStringExtra("img");
        backHandel = getIntent().getStringExtra("backHandel");
        /*Picasso.get().
                 load(imgLink.trim())   //live
                .placeholder(R.drawable.noimage)
                .into(iv_image);*/
        Glide.with(this)
                .load(imgLink.trim())
                .placeholder(R.drawable.noimage)
                .into(iv_image);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        return mScaleGestureDetector.onTouchEvent(event);

    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        // when a scale gesture is detected, use it to resize the image
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            iv_image.setScaleX(mScaleFactor);
            iv_image.setScaleY(mScaleFactor);
            return true;
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_chat_img_viewer;
    }

    @Override
    public int getBottomNavigationMenuItemId() {

        if (!backHandel.equals("")){
            if (backHandel.equals("support")){
                return R.id.nav_profile;
            }else if (backHandel.equals("backHandel")){
                return R.id.nav_offloading;
            }
        }
         return R.id.nav_offloading;

    }

    @Override
    public void onBackPressed() {

        if (!backHandel.equals("")){
            if (backHandel.equals("support")){
                mContext.startActivity(new Intent(this, Act_CustomerSupport.class));
            }else if (backHandel.equals("backHandel")){
                mContext.startActivity(new Intent(this, Act_IOTHome.class));
            }
        }else {
            super.onBackPressed();
        }
    }
}