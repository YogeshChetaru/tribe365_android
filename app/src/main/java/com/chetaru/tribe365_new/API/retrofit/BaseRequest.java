package com.chetaru.tribe365_new.API.retrofit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.chetaru.tribe365_new.UI.UserInfo.ActLogin;
import com.chetaru.tribe365_new.R;
import com.chetaru.tribe365_new.UI.Base.BaseActivity;
import com.chetaru.tribe365_new.utility.SessionParam;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Prakhar on 11/17/2016.
 */
public class BaseRequest<T> extends BaseRequestParser {
    public int TYPE_NOT_CONNECTED = 0;
    public int TYPE_WIFI = 1;
    public int TYPE_MOBILE = 2;
    String token = "";
    SessionParam sessionParam;
    String network_error_message = "Check internet connection";
    Handler handler = new Handler();
    private final Context mContext;
    private final ApiInterface apiInterface;
    private RequestReciever requestReciever;
    private boolean runInBackground = false;

    private Dialog dialog = null;
    private View loaderView = null;
    private int APINumber_ = 1;
    Runnable r = new Runnable() {
        @Override
        public void run() {
            try {
                hideLoader();
                if (null != requestReciever) {
                    requestReciever.onNetworkFailure(APINumber_, network_error_message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    public Callback<JsonElement> responseCallback = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";
            try {
                hideLoader();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null != response.body()) {
                JsonElement jsonElement = response.body();
                if (null != jsonElement) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonElement.toString());
                        String code = jsonObject.optString("code");
                        if (code.equals("401")) {
                            Toast.makeText(mContext, "unauthorised", Toast.LENGTH_SHORT).show();
                            return;
                            // BaseActivity.finishAllActivitiesStatic();

                        } else {
                            responseServer = jsonElement.toString();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
                try {
                    JSONObject jsonObject = new JSONObject(responseServer);
                    String code = jsonObject.optString("code");
                    if (code.equals("401")) {
                        //Toast.makeText(mContext, "unauthorised", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(mContext);
                        }

                        builder.setTitle("Unauthorized")
                                .setMessage("You are no longer the authorized user of this application.")
                                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            // continue with delete
                                            SessionParam sessionParam = new SessionParam(mContext);
                                            sessionParam.clearPreferences(mContext);
                                            BaseActivity.finishAllActivitiesStatic();

                                            mContext.startActivity(new Intent(mContext, ActLogin.class));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })*/
                                .setIcon(android.R.drawable.ic_dialog_alert);
                        try {
                        if (builder != null) {
                                builder.show();
                        }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        return;
                        // BaseActivity.finishAllActivitiesStatic();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            logFullResponse(responseServer, "OUTPUT");

            if (parseJson(responseServer)) {
                if (null != requestReciever) {
                    if (null != getDataArray()) {
                        requestReciever.onSuccess(APINumber_, responseServer, getDataArray());
                    } else if (null != getDataObject()) {
                        requestReciever.onSuccess(APINumber_, responseServer, getDataObject());
                    } else {
                        requestReciever.onSuccess(APINumber_, responseServer, message);
                    }
                }
            } else {
                if (null != requestReciever) {
                    requestReciever.onFailure(1, "" + mResponseCode, message);
                }
            }
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
            try {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(r, 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
           /* if (t.getMessage().startsWith("Unable to resolve")) {
               r.run();
            }*/
        }
    };
    public Callback<JsonElement> responseCallbackCustom = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";
            hideLoader();
            if (null != response.body()) {
                JsonElement jsonElement = response.body();
                if (null != jsonElement) {
                    responseServer = jsonElement.toString();
                }

            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }
            logFullResponse(responseServer, "OUTPUT");
            requestReciever.onSuccess(APINumber_, responseServer, null);

        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(r, 1000);
            /*if (t.getMessage().startsWith("Unable to resolve")) {
               r.run();
            }*/
        }
    };
    private final boolean showErrorDialog = true;
    private RequestType requestType = null;

    public BaseRequest(Context context) {
        mContext = context;
        apiInterface =
                ApiClient.getClient().create(ApiInterface.class);
        dialog = getProgressesDialog(context);
        sessionParam = new SessionParam(mContext);
        token = sessionParam.token;

        //dialog.setTitle("Fetching details...");
    }
    public BaseRequest(Context context, Fragment fm) {
        mContext = context;
        apiInterface =
                ApiClient.getClient().create(ApiInterface.class);
        dialog = getProgressesDialog(context);
        sessionParam = new SessionParam(mContext);
        token = sessionParam.token;
    }

    public boolean isRunInBackground() {
        return runInBackground;
    }

    public void setRunInBackground(boolean runInBackground) {
        this.runInBackground = runInBackground;
    }

    public void setLoaderView(View loaderView_) {
        this.loaderView = loaderView_;
    }

    public void setBaseRequestListner(RequestReciever requestListner) {
        this.requestReciever = requestListner;
    }

    @SuppressLint("TimberArgCount")
    public void callAPIPostCustomURL(final int APINumber, JsonObject jsonObject, String remainingURL) {
        requestType = RequestType.Post;
        APINumber_ = APINumber;
        showLoader();

        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }

        //  String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : " + remainingURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.postDataCustomURL(remainingURL, jsonObject);

        call.enqueue(responseCallback);
    }

    public ArrayList<Object> getDataList(JSONArray mainArray, Class<T> t) {
        Gson gsm = null;
        ArrayList<Object> list = null;
        list = new ArrayList<>();
        if (null != mainArray) {

            for (int i = 0; i < mainArray.length(); i++) {
                gsm = new Gson();
                Object object = gsm.fromJson(mainArray.optJSONObject(i).toString(), t);
                list.add(object);
            }
        }
        return list;
    }

    @SuppressLint("TimberArgCount")
    public void callAPIPost(final int APINumber, JsonObject jsonObject, String remainingURL) {
        requestType = RequestType.Post;
        APINumber_ = APINumber;
        showLoader();
        if (jsonObject == null) {
            jsonObject = new JsonObject();

        }
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : "
                        + baseURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        Call<JsonElement> call = apiInterface.postData(baseURL, jsonObject, "Bearer " + token);

        Log.d("Token", token);

        call.enqueue(responseCallback);
    }

    @SuppressLint("TimberArgCount")
    public void callAPIPostWOLoader(final int APINumber, JsonObject jsonObject, String remainingURL) {
        requestType = RequestType.Post;
        APINumber_ = APINumber;
        //showLoader();
        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : "
                        + baseURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        Call<JsonElement> call = apiInterface.postData(remainingURL, jsonObject, "Bearer " + token);
        Timber.d("Token", token);
        call.enqueue(responseCallback);
    }

    @SuppressLint("TimberArgCount")
    public void callAPIPostIMAGE(final int APINumber, JsonObject jsonObject, String remainingURL, MultipartBody.Part body, RequestBody dotId, RequestBody description, RequestBody section, RequestBody sectionId) {

        APINumber_ = APINumber;
        showLoader();
        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : "
                        + baseURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        //Call<JsonElement> call = apiInterface.uploadImage(body, "Bearer " + token);
        Call<JsonElement> call = apiInterface.uploadImage(body, dotId, description, section, sectionId, "Bearer " + token);
        Log.d("Token", token);
        call.enqueue(responseCallback);
    }

    @SuppressLint("TimberArgCount")
    public void callAPIPostUpdateEvidence(final int APINumber, JsonObject jsonObject, String remainingURL, MultipartBody.Part body, RequestBody dotId, RequestBody description) {

        APINumber_ = APINumber;
        showLoader();
        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }

        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : "
                        + baseURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        //Call<JsonElement> call = apiInterface.uploadImage(body, "Bearer " + token);
        Call<JsonElement> call = apiInterface.updateEvidence(body, dotId, description, "Bearer " + token);
        Log.d("Token", token);
        call.enqueue(responseCallback);
    }

    public void callAPIGET(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        //token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImVjYmE4Y2YyZjQyYzQxZWZmMTUwZTA0NWM5YmFmZDM3MTE2ODU0MDczMzQ4NTc4Y2ZlNGU1ZmEyZjQyZWMxNzBjYzM0NWMzM2NmZjIyYzY5In0";
        Call<JsonElement> call = apiInterface.postDataGET(remainingURL, map, "Bearer " + token);
        call.enqueue(responseCallback);
        Log.d("Token", token);
    }
   /* Call<JsonElement> getUserAnswer(@Part("email_id") RequestBody changeit_id,
                                    @Part("ques_id") RequestBody device_id,
                                    @Part("reply") RequestBody email_id,
                                    @Part("app_name") RequestBody app_name);*/

    public void callAPITELLSID(final int APINumber, String remainingURL, RequestBody images, RequestBody latitude, RequestBody fcm_token, RequestBody msg_detail, RequestBody app_name, RequestBody email_id_to, RequestBody ssecrete, RequestBody device_id, RequestBody longitude, RequestBody location_detail) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.formData(images, latitude, fcm_token, msg_detail, app_name, email_id_to, ssecrete, device_id, longitude, location_detail);
        call.enqueue(responseCallback);
    }

    public void callAPISendMsg(final int APINumber, String remainingURL, RequestBody changeit_id, RequestBody device_id, RequestBody email_id, RequestBody post_type, RequestBody message, RequestBody app_name) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        //showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.sendMsg(changeit_id, device_id, email_id, app_name, post_type, message);
        call.enqueue(responseCallback);
    }

    public void callAPIgetUserAnswer(final int APINumber, String remainingURL, RequestBody email_id, RequestBody ques_id, RequestBody reply, RequestBody app_name) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        //showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.getUserAnswer(email_id, ques_id, reply, app_name);
        call.enqueue(responseCallback);
    }

    public void callAPITellSidSignup(final int APINumber, String remainingURL, RequestBody email_id, RequestBody device_id, RequestBody fcm_token, RequestBody app_name, RequestBody ssecrete) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.tellSidSignup(email_id, device_id, fcm_token, app_name, ssecrete);
        call.enqueue(responseCallback);
    }

    public void callAPITellSid_Logout(final int APINumber, String remainingURL, RequestBody emp_email) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        //showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.tellSidLogout(emp_email);
        call.enqueue(responseCallback);
    }

    @SuppressLint("TimberArgCount")
    public void callAPIDELETE(final int APINumber, Map<String, String> map, String remainingURL, String id) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL;
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        //token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImVjYmE4Y2YyZjQyYzQxZWZmMTUwZTA0NWM5YmFmZDM3MTE2ODU0MDczMzQ4NTc4Y2ZlNGU1ZmEyZjQyZWMxNzBjYzM0NWMzM2NmZjIyYzY5In0";
        Call<JsonElement> call = apiInterface.callAPIDELETE(remainingURL + id, map, "Bearer " + token);
        call.enqueue(responseCallback);
        Log.d("Token", token);
    }

    /*public void callAPIGETinbox(final int APINumber,  String baseURL_) {
        APINumber_ = APINumber;
        showLoader();
        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getClientmsg().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.getINbox(baseURL_);
        call.enqueue(responseCallback);
    }*/

    public void callAPIGETCustomURL(final int APINumber, Map<String, String> map, String baseURL_) {
        APINumber_ = APINumber;
        showLoader();
        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(baseURL_).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.postDataGET("", map, "Bearer " + token);
        call.enqueue(responseCallbackCustom);
    }

    public void callAPIGETCustomURLTellSid(final int APINumber, Map<String, String> map, String baseURL_) {
        APINumber_ = APINumber;
        showLoader();
        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(baseURL_).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.postDataGET(baseURL_, map);
        call.enqueue(responseCallback);
    }

    @SuppressLint("TimberArgCount")
    public void logFullResponse(String response, String inout) {
        final int chunkSize = 3000;

        if (null != response && response.length() > chunkSize) {
            int chunks = (int) Math.ceil((double) response.length()
                    / (double) chunkSize);


            for (int i = 1; i <= chunks; i++) {
                if (i != chunks) {
                    Log.i("BaseReq",
                            inout + " : "
                                    + response.substring((i - 1) * chunkSize, i
                                    * chunkSize));
                } else {
                    Log.i("BaseReq",
                            inout + " : "
                                    + response.substring((i - 1) * chunkSize
                            ));
                }
            }
        } else {

            try {
                JSONObject jsonObject = new JSONObject(response);
                Timber.d("BaseReq", inout + " : " + jsonObject.toString(jsonObject.length()));

            } catch (JSONException e) {
                e.printStackTrace();
                Timber.d("BaseReq", " logFullResponse=>  " + response);
            }

        }
    }

    private String readStreamFully(long len, InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public Dialog getProgressesDialog(Context ct) {
        Dialog dialog = new Dialog(ct);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("Fetching details...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog_loader);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }

    public void showErrorDialog(Context ct, String msg, final int APINumber, final JsonObject jsonObject, String remainingURL) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ct);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public void showLoader() {
        try {
            if (mContext != null && !((Activity) mContext).isDestroyed()) {
                if (!runInBackground) {
                    if (null != loaderView) {
                        loaderView.setVisibility(View.VISIBLE);
                    } else if (null != dialog) {
                        dialog.show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideLoader() {
        try {
            if (mContext != null && !((Activity) mContext).isDestroyed()) {
                if (!runInBackground) {
                    if (null != loaderView) {
                        loaderView.setVisibility(View.GONE);
                    } else if (null != dialog) {
                        dialog.dismiss();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getConnectivityStatus(Context context) {
        if (null == context) {
            return TYPE_NOT_CONNECTED;

        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (null != activeNetwork && activeNetwork.isConnected()) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public enum RequestType {
        Post, Get
    }


}
