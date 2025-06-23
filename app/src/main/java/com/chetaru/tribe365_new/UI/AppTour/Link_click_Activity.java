package com.chetaru.tribe365_new.UI.AppTour;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chetaru.tribe365_new.R;
import com.google.android.youtube.player.YouTubeBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Link_click_Activity extends YouTubeBaseActivity implements View.OnClickListener {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_show_ll)
    LinearLayout text_show_ll;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.top_text_click)
    TextView topClick;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_text_click)
    TextView bottomClick;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_video_link)
    TextView linkClickTxt;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.back_image)
    ImageView backImageClick;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.close_image)
    ImageView closeImage;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_name)
    TextView title_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sub_title_name)
    TextView sub_title_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_desc)
    TextView text_desc;

    String titleName = "", subTitleName = "", desString = "", linkUrlString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.link_click_activity);
        ButterKnife.bind(this);


        topClick = findViewById(R.id.top_text_click);
        bottomClick = findViewById(R.id.bottom_text_click);

        topClick.setOnClickListener(this);
        bottomClick.setOnClickListener(this);
        linkClickTxt.setOnClickListener(this);
        backImageClick.setOnClickListener(this);
        closeImage.setOnClickListener(this);

        text_show_ll.setVisibility(View.VISIBLE);
        linkClickTxt.setVisibility(View.VISIBLE);
        backImageClick.setVisibility(View.GONE);

        titleName = getIntent().getStringExtra("TitleName");
        subTitleName = getIntent().getStringExtra("subTitleName");
        desString = getIntent().getStringExtra("Description");
        linkUrlString = getIntent().getStringExtra("videoURL");
        title_name.setText(titleName);
        if (subTitleName != null) {
            sub_title_name.setText(subTitleName);
        }
        text_desc.setText(desString);
        text_desc.setMovementMethod(new ScrollingMovementMethod());
        /*desString="";
        linkUrlString= ApiClient.YOUTUBE_VIDEO_CODE;*/
        if (desString.equals("") && !linkUrlString.equals("")) {
            Intent intent = new Intent(this, Play_Activity.class);
            intent.putExtra("TitleName", titleName);
            intent.putExtra("subTitleName", subTitleName);
            intent.putExtra("Description", desString);
            intent.putExtra("videoURL", linkUrlString);
            startActivity(intent);
            finish();
        }
        try {
            if (linkUrlString.equals("")) {
                linkClickTxt.setVisibility(View.GONE);
            } else {
                linkClickTxt.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.top_text_click:
                finish();
                break;
            case R.id.bottom_text_click:
                finish();
                break;
            case R.id.tv_video_link:
            case R.id.back_image:
                Intent intent = new Intent(this, Play_Activity.class);
                intent.putExtra("TitleName", titleName);
                intent.putExtra("subTitleName", subTitleName);
                intent.putExtra("Description", desString);
                intent.putExtra("videoURL", linkUrlString);
                startActivity(intent);
                finish();
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                overridePendingTransition(0, 0);
                break;
            case R.id.close_image:

                break;
        }

    }


    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.cb_fade_in, R.anim.cb_face_out);
        overridePendingTransition(0, 0);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        // overridePendingTransition(R.anim.cb_face_out, R.anim.cb_fade_in);
        //  Call.Details.this.overridePendingTransition(R.anim.nothing,R.anim.nothing);
    }


}
