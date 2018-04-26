package com.shanlian.genetic.api;



import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @创建者 CSDN_LQR
 * @描述 server端api
 */

public interface MyApi {

    public static final String BASE_URL = "http://api.sealtalk.im/";

//    //检查手机是否被注册
//    @POST("user/check_phone_available")
//    Observable<CheckPhoneResponse> checkPhoneAvailable(@Body RequestBody body);
//
//    //发送验证码
//    @POST("user/send_code")
//    Observable<SendCodeResponse> sendCode(@Body RequestBody body);
//
//    //验证验证码是否正确(必选先用手机号码调sendcode)
//    @POST("user/verify_code")
//    Observable<VerifyCodeResponse> verifyCode(@Body RequestBody body);
//
    //注册
    @POST("user/register")
    Observable<String> register(@Body RequestBody body);
//
    //登录
    @POST("user/login")
    Observable<String> login(@Body RequestBody body);


    //下载图片
    @GET
    Observable<ResponseBody> downloadPic(@Url String url);

}
