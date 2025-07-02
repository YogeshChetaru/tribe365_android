package com.chetaru.tribe365_new.UI.Home;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Act_imageShow extends BaseActivity {

    /********************* initialize variable ******************/
    FrameLayout showFragment;
    @BindView(R.id.tribe365)
    ImageView tribe365;
    Utility utility;
    SessionParam sessionParam;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    private String imageString;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_show);
        ButterKnife.bind(this);
        utility = new Utility();
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        tribe365.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHomeAct(mContext);
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageString = bundle.getString("image");
            ImageZoomFragment fragment = ImageZoomFragment.newInstance(imageString, "");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.show_fragment, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            Glide.with(this)
                    .load(imageString.trim())
                    .placeholder(R.drawable.noimage_big)
                    .into(iv_image);
            mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_image_show;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_offloading;
    }

    /********* handel touch event *************/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        return mScaleGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
}