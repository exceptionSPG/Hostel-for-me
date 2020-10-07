package com.hfad.hostel.Storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.User;
import com.hfad.hostel.model.UserInfo;
import com.hfad.hostel.model.userAllInfoByUid;

import java.util.Objects;

public class SharedPrefManager {
    private static final String TAG = "Session_My_TAG";
    private static final String SHARED_PREF_NAME = "my_shared_preff";
    private static SharedPrefManager mInstance;
    private Context mCtx;

    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getUser_id());
        editor.putString("email", user.getEmail());
        editor.putString("username", user.getUsername());
        editor.putString("registration_date", user.getReg_date());
        editor.putString("updation_date", user.getUpdation_date());

        editor.apply();
    }

    public void saveKey(String key){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("key", key);

        editor.apply();
    }

    public String getKey(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String key = sharedPreferences.getString("key", null);
        return key;
    }

    public void saveCode(String code){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("code", code);

        editor.apply();
    }

    public String getCode(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String code = sharedPreferences.getString("code", null);
        return code;
    }
    public boolean isAdminLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
         if((Objects.equals(sharedPreferences.getString("key", null), "Admin"))){
            return true;
        }
        return false;

    }


    public boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return (sharedPreferences.getInt("id", -1) != -1);
//         if((Objects.equals(sharedPreferences.getString("key", null), "User"))){
//            return true;
//        }
//         return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user = new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("registration_date", null),
                sharedPreferences.getString("updation_date", null)
        );
        return user;
    }

    public void saveOwner(Owner owner) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("sid", owner.getSid());
        editor.putString("hostel_owner_name", owner.getHostel_owner_name());
        editor.putString("hostel_name", owner.getHostel_name());
        editor.putString("hostel_location", owner.getHostel_location());
        editor.putString("hostel_type", owner.getHostel_type());
        editor.putString("contact_number", owner.getContact_number());
        editor.putString("hostel_email", owner.getHostel_email());
        editor.putString("hostel_code", owner.getHostel_code());
        editor.putString("login_pwd", owner.getLogin_pwd());
        editor.putString("hostel_registered_date", owner.getHostel_registered_date());
        editor.putString("updation_date", owner.getUpdation_date());


        editor.apply();
    }

    public boolean isOwnerLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return (sharedPreferences.getInt("sid", -1) != -1);
    }

    public Owner getOwner() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Owner owner = new Owner(
                sharedPreferences.getInt("sid", -1),
                sharedPreferences.getString("hostel_owner_name", null),
                sharedPreferences.getString("hostel_name", null),
                sharedPreferences.getString("hostel_location", null),
                sharedPreferences.getString("hostel_type", null),
                sharedPreferences.getString("contact_number", null),
                sharedPreferences.getString("hostel_email", null),
                sharedPreferences.getString("hostel_code", null),
                sharedPreferences.getString("login_pwd", null),
                sharedPreferences.getString("hostel_registered_date", null),
                sharedPreferences.getString("updation_date", null)
        );
        return owner;
    }


    public void saveUserInfo(userAllInfoByUid info) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("uiid", info.getUiid());
        editor.putString("phone_number",info.getUser_phone_number());
        editor.putString("guardian_number", info.getUser_guardian_contact_number());
        editor.putString("user_address", info.getUser_address());
        editor.putString("user_dob", info.getUser_DOB());
        editor.putString("user_guardian_name", info.getUser_guardian_name());
        editor.putString("user_about_you", info.getAbout_you());
        editor.putString("user_gender", info.getUser_gender());
//        editor.putString("user_level", info.getUser_level());
//        editor.putString("user_stream", info.getUser_stream());
        editor.putString("user_education", info.getEducation());

        Log.d(TAG, "saveUserInfo: Inserted data in session");

        editor.apply();
    }
    public void saveGenderSpnrPosition(int pos){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("position_gender",pos);
        editor.apply();
    }
    public int getGenderPosition(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return (sharedPreferences.getInt("position_gender", -1));
    }

    public void saveLevelSpnrPosition(int pos){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("position_level",pos);
        editor.apply();
    }
    public int getLevelPosition(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return (sharedPreferences.getInt("position_level", -1));
    }

    public void saveStreamSpnrPosition(int pos){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("position_stream",pos);
        editor.apply();
    }
    public int getStreamPosition(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return (sharedPreferences.getInt("position_stream", -1));
    }




    public userAllInfoByUid getUserInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userAllInfoByUid userInfo = new userAllInfoByUid(
                sharedPreferences.getInt("uiid", -1),
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("registration_date", null),
                sharedPreferences.getString("updation_date", null),
                //"986728763",
                sharedPreferences.getString("phone_number", null),
                sharedPreferences.getString("user_address", null),
                sharedPreferences.getString("user_dob", null),
                sharedPreferences.getString("user_gender", null),
                sharedPreferences.getString("user_guardian_name", null),
                //"9867463532",
                sharedPreferences.getString("guardian_number", null),
                sharedPreferences.getString("user_education", null),
                sharedPreferences.getString("user_about_you", null),
                sharedPreferences.getString("info_updation_date", null),
                sharedPreferences.getString("message", null)
        );
        return userInfo;
    }

    public boolean isUserInfoExists(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return (sharedPreferences.getInt("uiid", -1) != -1);
    }

    //method for logout
    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void resetCode(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String code = sharedPreferences.getString("code", null);
        if(code.contains("hf")){
            saveCode("null");
        }
    }
}
