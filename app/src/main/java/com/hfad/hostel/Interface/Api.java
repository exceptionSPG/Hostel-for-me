package com.hfad.hostel.Interface;

import com.hfad.hostel.model.AdminAllEnquiryModel;
import com.hfad.hostel.model.AdminAllRequestModel;
import com.hfad.hostel.model.AdminDashboardResponse;
import com.hfad.hostel.model.AllUserResponse;
import com.hfad.hostel.model.DefaultResponse;
import com.hfad.hostel.model.LoginResponse;
import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.OwnerInfoResponse;
import com.hfad.hostel.model.OwnerLoginResponse;
import com.hfad.hostel.model.UserEnquiryResponseModel;
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

    //Required parameters userid, ownerid, username, user_email,
    // user_phone, owner_name, hostel_name, hostel_address, enquiry_message are missing or empty"

    @FormUrlEncoded
    @POST("insertenquiry")
    Call<DefaultResponse> createEnquiry(
            @Field("userid") int userId,
            @Field("ownerid") int ownerId,
            @Field("username") String userName,
            @Field("user_email") String userEmail,
            @Field("user_phone") String userPhone,
            @Field("owner_name") String ownerName,
            @Field("hostel_name") String hostelName,
            @Field("hostel_address") String hostelAddress,
            @Field("enquiry_message") String enquiryMessage
    );

    //hostel_owner_name,hostel_name,hostel_location,
    // hostel_type,contact_number,hostel_email,hostel_code,login_pwd
    @FormUrlEncoded
    @POST("createhostelowner")
    Call<DefaultResponse> createOwner(
            @Field("hostel_owner_name") String hostelOwnerName,
            @Field("hostel_name") String hostelName,
            @Field("hostel_location") String hostelLocation,
            @Field("hostel_type") String hostelType,
            @Field("hostel_email") String hostelEmail,
            @Field("hostel_code") String hostelCode,
            @Field("login_pwd") String hostelLoginPwd
    );


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

    @GET("allenquiry")
    Call<AdminAllEnquiryModel> getAllEnquiry();

    @GET("allrequest")
    Call<AdminAllRequestModel> getAllRequest();

    @GET("pendingenquirybyowner_id/{ownerid}")
    Call<AdminAllEnquiryModel> getOwnerAllPendingEnquiry(
            @Path("ownerid") int sahu_id
    );

    @GET("acceptedenquirybyuser_id/{userid}")
    Call<UserEnquiryResponseModel> getUserAllAcceptedEnquiry(
            @Path("userid") int id
    );

    @GET("pendingenquirybyuser_id/{userid}")
    Call<UserEnquiryResponseModel> getUserAllPendingEnquiry(
            @Path("userid") int userID
    );



    @PUT("reviewenquirystatus/{eid}")
    Call<DefaultResponse> approveEnquiry(
            @Path("eid") int enquiryId
    );

    @PUT("cancelenquirystatus/{enq_id}")
    Call<DefaultResponse> cancelEnquiry(
            @Path("enq_id") int enquiryId
    );



    @FormUrlEncoded
    @POST("requesthostelowner")
    Call<DefaultResponse> insertRequest(
            //Required parameters hostel_name, hostel_location, hos_lati, hos_longi,
            // hostel_type, owner_name, contact_number, hostel_email are missing or empty

            @Field("hostel_name") String hostelName,
            @Field("hostel_location") String hostelAddress,
            @Field("hostel_type") String hostelType,
            @Field("owner_name") String ownerName,
            @Field("contact_number") String contactNumber,
            @Field("hostel_email") String hostelEmail
    );


    @GET("statusofrequestbyhrid/{hrid}")
    Call<DefaultResponse> getRequestResultByEid(
            @Path("hrid") int hrid
    );

    @GET("lastinsertedrequestid")
    Call<DefaultResponse> getLastInsertetId();





    @GET("approvehostelrequeststatus/{hrid}")
    Call<DefaultResponse> approveHostelRequestStatus(
            @Path("hrid") int hrid
    );
}
