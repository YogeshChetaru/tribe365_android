package com.chetaru.tribe365_new.UI.Home;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.chetaru.tribe365_new.API.retrofit.ApiClient;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.databinding.ActHelpBinding;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.squareup.picasso.Picasso;

public class Act_Help extends BaseActivity {

    private static final int SPLASH_TIME_OUT = 3000;
    //TextView tv_connection_check;
    //ProgressBar progressBar;
    ImageView tribe365;
    SessionParam sessionParam;
    //private WebView webView;
    ActHelpBinding binding;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_help);
        binding =ActHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //webView = findViewById(R.id.webview);
        //progressBar = findViewById(R.id.progressBar);
        //tv_connection_check = findViewById(R.id.tv_connection_check);
        tribe365 = findViewById(R.id.tribe365);
        sessionParam = new SessionParam(mContext);
        if (sessionParam.role.equalsIgnoreCase("3")) {
            if (!sessionParam.organisation_logo.equals("")) {
                Picasso.get().load(sessionParam.organisation_logo).into(tribe365);
            }
        }
        // on Click replace with lambda expressions  function ****/
        tribe365.setOnClickListener(view -> callHomeAct(mContext));

        String url = getIntent().getStringExtra("url");
        //webView.loadUrl("http://production.chetaru.co.uk/tribe365/public/support/" + url); //demo
        // webView.loadUrl("https://tribe365.chetaru.co.uk/resources/views/support/" + url); //demo
        binding.webview.loadUrl(ApiClient.SUPPORT_URL + url); //help url
        Log.d("supportUrl", ApiClient.SUPPORT_URL + url);
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.setWebViewClient(new WebViewClient());
        binding.webview.clearCache(true);
        binding.webview.clearHistory();
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
       // webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            binding.webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            binding.webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        binding.webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        binding.webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                binding.progressBar.setProgress(progress);
//              textView.setText(progress + " %");
                if (progress == 100) {
                    new Handler().postDelayed(() -> checkInternetConnection(), SPLASH_TIME_OUT);
                }
            }
        });
        //Load a webpage
    }

    private void checkInternetConnection() {
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        try {
            if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                binding.webview.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            } else {
                binding.tvConnectionCheck.setVisibility(View.VISIBLE);
                binding.webview.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_help;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}
