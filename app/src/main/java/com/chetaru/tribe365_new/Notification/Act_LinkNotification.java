package com.chetaru.tribe365_new.Notification;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.UI.Home.Act_Home;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.chetaru.tribe365_new.utility.Utility;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

public class Act_LinkNotification extends BaseActivity {


    SessionParam sessionParam;
    Utility utility;
    Activity activity;

    //handel Notification value on extra data
    String noti_Title, noti_Description, date, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_descfull);

        sessionParam = new SessionParam(mContext);
        utility = new Utility();
        activity = this;

        ButterKnife.bind(this);

        noti_Title = getIntent().getStringExtra("noti_title");
        noti_Description = getIntent().getStringExtra("noti_desc");
        date = getIntent().getStringExtra("noti_date");
        link = getIntent().getStringExtra("noti_link");
        final TextView tv_notiName = findViewById(R.id.tv_dia_notiName);
        final TextView tv_desc = findViewById(R.id.tv_dia_desc);
        final TextView tv_date = findViewById(R.id.tv_dia_date);
        final LinearLayout date_dia_ll = findViewById(R.id.ll_dia_date);
        final Button valueButton = findViewById(R.id.link_dia_button);
        final ImageView diaIcon = findViewById(R.id.iv_dia_logo);
        final ImageView tribe = findViewById(R.id.tribe365);

        /*************** send notification read Status ***************/
        if (getIntent().getStringExtra("readNotificationId") != null) {
            String readNotificationId = getIntent().getStringExtra("readNotificationId");
            api_notificationRead(readNotificationId);
        }

        tv_notiName.setText(noti_Title);
        tv_desc.setText(noti_Description);
        tv_date.setText(utility.getDate(date));

        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe);
            }
        }

        try {
            if (!link.equals("")) {
                valueButton.setVisibility(View.VISIBLE);
            } else {
                valueButton.setVisibility(View.GONE);
            }
            valueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // dialog.dismiss();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(browserIntent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //custom notification with link and description
    private void Dialog_customNotification(String noti_Title, String noti_Description, String link, String date) {
        final Dialog dialog = new Dialog(mContext);
        //final Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_descfull);

        //dialog.setCanceledOnTouchOutside(true);

        final TextView tv_notiName = dialog.findViewById(R.id.tv_dia_notiName);
        final TextView tv_desc = dialog.findViewById(R.id.tv_dia_desc);
        final TextView tv_date = dialog.findViewById(R.id.tv_dia_date);
        final LinearLayout date_dia_ll = dialog.findViewById(R.id.ll_dia_date);
        final Button valueButton = dialog.findViewById(R.id.link_dia_button);
        final ImageView diaIcon = dialog.findViewById(R.id.iv_dia_logo);
        final ImageView tribe = dialog.findViewById(R.id.tribe365);


        tv_notiName.setText(noti_Title);
        tv_desc.setText(noti_Description);
        tv_date.setText(utility.getDate(date));

        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe);
            }
        }
        try {
            if (!link.equals("")) {
                valueButton.setVisibility(View.VISIBLE);
            } else {
                valueButton.setVisibility(View.GONE);
            }
            valueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // dialog.dismiss();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(browserIntent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Picasso.get().load(NotiList.getmUserImage()).into(diaIcon);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, Act_Home.class));
        finish();
    }
    @Override
    public int getLayoutId() {
        return R.layout.dialog_descfull;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
