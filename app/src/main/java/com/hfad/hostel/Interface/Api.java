package com.hfad.hostel.Interface;

import com.hfad.hostel.model.AdminDashboardResponse;
import com.hfad.hostel.model.AllUserResponse;
import com.hfad.hostel.model.DefaultResponse;
import com.hfad.hostel.model.LoginResponse;
import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.OwnerInfoResponse;
import com.hfad.hostel.model.OwnerLoginResponse;
import com.hfad.hostel.model.UserInfoUpdateResponse;
import com.hfad.hostel.model.hostelInfoByHostelCodeResponse;
import com.hfad.hostel.model.userInfoResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded
    @POST("createuser")
    Call<DefaultResponse> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("adminlogin")
    Call<LoginResponse> adminLogin(
            @Field("email") String email,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("ownerlogin")
    Call<OwnerLoginResponse> ownerLogin(
            @Field("hostel_code") String hostel_code,
            @Field("login_pwd") String login_pwd
    );
//                //`userid`, `ownerid`, `user_name`, `user_email`, `user_phone`, `owner_name`, `hostel_name`, `hostel_address`, `, `enquiry_message`,
    //userid','ownerid','username','user_email','user_phone','hostel_name','hostel_address','enquiry_message
    @Headers("Content-Type: multipart/form-data")
    @Multipart
    @POST("insertenquiry")
    Call<DefaultResponse> insertEnquiry(

//            @Part("useris") RequestBody userid,
//            @Part("ownerid") RequestBody ownerid,
//            @Part("user_name") RequestBody user_name,
//            @Part("user_email") RequestBody user_email,
//            @Part("user_phone") RequestBody user_phone,
//            @Part("owner_name") RequestBody owner_name,
//            @Part("hostel_name") RequestBody hostel_name,
//            @Part("hostel_address") RequestBody hostel_address,
//            @Part("enquiry_message") RequestBody enquiry_message
            @Part("userid") RequestBody buserid,
            @Part("ownerid") RequestBody bownerid,
            @Part("user_name") RequestBody buser_name,
            @Part("user_email") RequestBody buser_email,
            @Part("user_phone") RequestBody buser_phone,
            @Part("owner_name") RequestBody bowner_name,
            @Part("hostel_name") RequestBody bhostel_name,
            @Part("hostel_address") RequestBody bhostel_address,
            @Part("enquiry_message") RequestBody benquiry_message
            );

    @GET("allHostelOwners")
    Call<OwnerInfoResponse> getAllOwnerInfo();

    @GET("allusers")
    Call<AllUserResponse> getAllUser();

    @GET("hostelinfobyhcode/{hostel_code}")
    Call<hostelInfoByHostelCodeResponse> getHostelInfoByHostelCode(
            @Path("hostel_code") String hostel_code
    );

    @GET("userinfobyuid/{uid}")
    Call<userInfoResponse> getUserInfoByUid(
            @Path("uid") int id
    );

    @GET("isUserInfoExist/{uid}")
    Call<ResponseBody> checkExistUserInfo(
            @Path("uid") int id
    );

    @FormUrlEncoded
    @POST("insertUserDetails")
    Call<DefaultResponse> insertUserInfo(
            @Field("uid") int uid,
            @Field("user_phone_number") String user_phone_number,
            @Field("user_address") String user_address,
            @Field("user_DOB") String user_DOB,
            @Field("user_gender") String user_gender,
            @Field("user_guardian_name") String user_guardian_name,
            @Field("user_guardian_contact_number") String user_guardian_contact_number,
            @Field("education") String education,
            @Field("about_you") String about_you
    );


    @FormUrlEncoded
   // @Headers({"Accept:application/json"})
    @PUT("updateuserinfo/{uid}")
    Call<UserInfoUpdateResponse> userInfoUpdate(
            @Path("uid") int uid,
            @Query("user_phone_number") String user_phone_number,
            @Query("user_address") String user_address,
            @Query("user_DOB") String user_DOB,
            @Query("user_gender") String user_gender,
            @Query("user_guardian_name") String user_guardian_name,
            @Query("user_guardian_contact_number") String user_guardian_contact_number,
            @Query("education") String education,
            @Query("about_you") String about_you,

            @Field("user_phone_number") String user_phone,
            @Field("user_address") String address,
            @Field("user_DOB") String DOB,
            @Field("user_gender") String gender,
            @Field("user_guardian_name") String guardian_name,
            @Field("user_guardian_contact_number") String guardian_contact_number,
            @Field("education") String user_education,
            @Field("about_you") String user_about_you

    );

    @FormUrlEncoded
    @PUT("userupdate/{uid}")
    Call<DefaultResponse> userUpdate(
            @Path("uid") int uid,
            @Field("name") String username,
            @Field("email") String email
    );

    @GET("allcount")
    Call<AdminDashboardResponse> getAdminData();



}
