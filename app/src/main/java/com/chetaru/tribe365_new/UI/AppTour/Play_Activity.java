package com.chetaru.tribe365_new.UI.AppTour;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chetaru.tribe365_new.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Play_Activity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener, View.OnClickListener {

    private static final int RECOVERY_REQUEST = 1;
    YouTubePlayer player;
    View view_show;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_show_ll)
    RelativeLayout text_show_ll;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.video_player_view_ll)
    RelativeLayout video_player_view_ll;
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
    String titleName = "", subTitleName = "", desString = "", linkUrlString = "";
    String urlLink = "";
    private YouTubePlayerView youTubeView;
    private static final String DEVELOPER_KEY = "AIzaSyCX3TmFHeNtxi3H1lmjqd9yIBJ2zBireFM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.play_activity);
        ButterKnife.bind(this);

        youTubeView = findViewById(R.id.youtube_view);
        youTubeView.initialize(DEVELOPER_KEY, this);

        topClick = findViewById(R.id.top_text_click);
        bottomClick = findViewById(R.id.bottom_text_click);

        topClick.setOnClickListener(this);
        bottomClick.setOnClickListener(this);
        linkClickTxt.setOnClickListener(this);
        backImageClick.setOnClickListener(this);
        closeImage.setOnClickListener(this);

        video_player_view_ll.setVisibility(View.VISIBLE);
        text_show_ll.setVisibility(View.GONE);
        linkClickTxt.setVisibility(View.GONE);
        backImageClick.setVisibility(View.VISIBLE);

        titleName = getIntent().getStringExtra("TitleName");
        subTitleName = getIntent().getStringExtra("subTitleName");
        desString = getIntent().getStringExtra("Description");
        linkUrlString = getIntent().getStringExtra("videoURL");

        title_name.setText(titleName);
        sub_title_name.setText(subTitleName);
        if (desString.equals("")) {
            backImageClick.setVisibility(View.GONE);
        } else {
            backImageClick.setVisibility(View.VISIBLE);
        }
        if (!linkUrlString.equals("")) {
            urlLink = getYouTubeId(linkUrlString);
            //linkUrlString=urlLink;
        }


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewOutlineProvider mViewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(final View view, final Outline outline) {
                    int left =0;
                    int top=0;
                    int right=view.getWidth();
                    int bottom=view.getHeight();
                    float cornerRadiusDP = 16f;
                    float cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadiusDP, getResources().getDisplayMetrics());
                   // outline.setRoundRect(0, 0, view.getWidth(), (int) (view.getHeight() + cornerRadius), cornerRadius);

                   // bottom right corner
                    //outline.setRoundRect((int) (left - cornerRadius), (int)( top - cornerRadius), right, bottom, cornerRadius);
                     //bottom left corner
                //outline.setRoundRect(left, (int) (top - cornerRadius), (int) (right + cornerRadius), bottom, cornerRadius);
                    // all corners
                    //outline.setRoundRect(left, top, right, bottom, cornerRadius);
                    // bottom corners
                outline.setRoundRect(left, (int)(top - cornerRadius), right, bottom, cornerRadius);



                }
            };
            youTubeView.setOutlineProvider(mViewOutlineProvider);
            youTubeView.setClipToOutline(true);
        }*/

    }

    private String getYouTubeId(String youTubeUrl) {
        String pattern = "https?://(?:[0-9A-Z-]+\\.)?(?:youtu\\.be/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|</a>))[?=&+%\\w]*";

        Pattern compiledPattern = Pattern.compile(pattern,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // finish();
    }

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
                text_show_ll.setVisibility(View.GONE);
                video_player_view_ll.setVisibility(View.VISIBLE);
                backImageClick.setVisibility(View.VISIBLE);
                try {
                    //player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                    if (player != null) {
                        player.play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
               /* Intent intent=new Intent(this,Link_click_Activity.class);
                intent.putExtra("TitleName",titleName);
                intent.putExtra("subTitleName",subTitleName);
                intent.putExtra("Description",desString);
                intent.putExtra("videoURL",linkUrlString);
                startActivity(intent);*/
                break;
            case R.id.back_image:
               /* text_show_ll.setVisibility(View.VISIBLE);
                video_player_view_ll.setVisibility(View.GONE);
                backImageClick.setVisibility(View.GONE);
                //video_player_view_ll.setBackground(R.drawable.rounded_video_background);
               video_player_view_ll.setBackground(this.getResources().getDrawable(R.drawable.rounded_video_background));
                try {
                    //player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                    player.pause();
                }catch (Exception e){
                    e.printStackTrace();
                }
                youTubeView.setVisibility(View.GONE);*/
                //youTubeView.onConfigurationChanged();
                Intent intent = new Intent(this, Link_click_Activity.class);
                intent.putExtra("TitleName", titleName);
                intent.putExtra("subTitleName", subTitleName);
                intent.putExtra("Description", desString);
                intent.putExtra("videoURL", linkUrlString);
                startActivity(intent);
                finish();
                //overridePendingTransition(R.anim.fade_in, R.anim.nothing);
                overridePendingTransition(0, 0);
                try {
                    //player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                    if (player != null) {
                        player.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.close_image:
                finish();
                break;
        }

    }

    public void stopPlayer() {
        //player.stop();
        //player.release();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            this.player = player;
            try {
                if (urlLink != null) {
                    player.loadVideo(urlLink);
                    // player.loadVideo(ApiClient.YOUTUBE_VIDEO_CODE);
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                } else if (!linkUrlString.equals("")) {
                    urlLink = getYouTubeId(linkUrlString);
                    player.loadVideo(urlLink);
                    // player.loadVideo(ApiClient.YOUTUBE_VIDEO_CODE);
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition(R.anim.cb_fade_in, R.anim.cb_face_out);
        overridePendingTransition(0, 0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        // Call.Details.this.overridePendingTransition(R.anim.nothing,R.anim.nothing);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return findViewById(R.id.youtube_view);
    }

    @Override
    public void onLoading() {


    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }
}
