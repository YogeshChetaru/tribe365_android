package com.chetaru.tribe365_new.API.retrofit;

import com.chetaru.tribe365_new.API.Models.MultipartAddEvidence;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface ApiInterface {


    @POST
    Call<JsonElement> postData(@Url String remainingURL, @Body JsonObject jsonObject, @Header("Authorization") String token);
    //Map<String, String> params

    @GET
    Call<JsonElement> postDataGET(@Url String remainingURL, @QueryMap Map<String, String> map, @Header("Authorization") String token);

    @GET
    Call<JsonElement> getINbox(@Url String remainingURL);


    @GET
    Call<JsonElement> postDataTellSid(@Url String remainingURL, @QueryMap Map<String, String> map);

    @DELETE
    Call<JsonElement> callAPIDELETE(@Url String remainingURL, @QueryMap Map<String, String> map, @Header("Authorization") String token);


    @POST
    Call<JsonElement> postDataCustomURL(@Url String remainingURL, @Body JsonObject jsonObject);

    @GET
    Call<JsonElement> postDataGET(@Url String remainingURL, @QueryMap Map<String, String> map);

    @Multipart
    @POST("addDotEvidence")
    Call<JsonElement> uploadImage(@Part MultipartBody.Part file,
                                  @Part("dotId") RequestBody dotId,
                                  @Part("description") RequestBody description,
                                  @Part("section") RequestBody section,
                                  @Part("sectionId") RequestBody sectionId,
                                  @Header("Authorization") String token);

    @Multipart
    @POST("postdetails/")
    Call<JsonElement> formData(@Part("images") RequestBody image,
                               @Part("latitude") RequestBody latitude,
                               @Part("fcm_token") RequestBody fcm_token,
                               @Part("msg_detail") RequestBody msg_detail,
                               @Part("app_name") RequestBody app_name,
                               @Part("email_id_to") RequestBody email_id_to,
                               @Part("ssecrete") RequestBody ssecrete,
                               @Part("device_id") RequestBody device_id,
                               @Part("longitude") RequestBody longitude,
                               @Part("location_detail") RequestBody location_detail);

    @Multipart
    @POST("sendchatmsg/")
    Call<JsonElement> sendMsg(@Part("changeit_id") RequestBody changeit_id,
                              @Part("device_id") RequestBody device_id,
                              @Part("email_id") RequestBody email_id,
                              @Part("app_name") RequestBody app_name,
                              @Part("post_type") RequestBody post_type,
                              @Part("message") RequestBody message);

    @Multipart
    @POST("getuseranser/")
    Call<JsonElement> getUserAnswer(@Part("email_id") RequestBody email_id,
                                    @Part("ques_id") RequestBody ques_id,
                                    @Part("reply") RequestBody reply,
                                    @Part("app_name") RequestBody app_name);

    @Multipart

    @POST("signup/")
    Call<JsonElement> tellSidSignup(@Part("email_id_to") RequestBody email_id,
                                    @Part("device_id") RequestBody device_id,
                                    @Part("fcm_token") RequestBody fcm_token,
                                    @Part("app_name") RequestBody app_name,
                                    @Part("ssecrete") RequestBody ssecrete);

    @Multipart
    @POST("logout/")
    Call<JsonElement> tellSidLogout(@Part("emp_email") RequestBody email_id);


    @Multipart
    @POST("postdetails/")
    Call<JsonElement> formData(@PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("updateDotEvidence")
    Call<JsonElement> updateEvidence(@Part MultipartBody.Part file,
                                     @Part("id") RequestBody dotId,
                                     @Part("description") RequestBody description,
                                     @Header("Authorization") String token);

    @Multipart
    @POST("addUserDirectiveFile")
    Call<JsonElement> testBlueData(@Part MultipartBody.Part answer,
                                   @Header("Authorization") String token);

    @Multipart
    @POST("addDotEvidence")
    Call<MultipartAddEvidence> addEvidence(@Header("Authorization") String token, @Part("file\"; filename=\"pp.png\" ") RequestBody file, @Part("dotId") RequestBody dotId, @Part("description") RequestBody description);

}

