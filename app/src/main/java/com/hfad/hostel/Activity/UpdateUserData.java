package com.hfad.hostel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.DefaultResponse;
import com.hfad.hostel.model.User;
import com.hfad.hostel.model.UserInfo;
import com.hfad.hostel.model.UserInfoUpdateResponse;
import com.hfad.hostel.model.userAllInfoByUid;
import com.hfad.hostel.model.userInfoResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserData extends AppCompatActivity {

    public static final String TAG = "UpdateUser";
    EditText et_user_name_update,et_phone_update,et_address_update,et_user_dob_update,et_user_guardian_name_update,et_user_guardian_number_update,et_user_about_you_update;
    String user_phone_number, user_guardian_number,user_name_update, address_update, user_dob_update, user_guardian_name_update,user_about_you_update,user_education_update,user_gender_update,user_level,user_stream;
    Spinner spnr_gender_update,spnr_level_update, spnr_stream_update;
    Button btn_update;
    SharedPrefManager userSession;
    UserInfo info;
    TextView tv_user_email,tv_user_name;
    int mYear, mMonth, mDay;
    DatePickerDialog dp;
    int id, position;
    User loggedUser;
    userAllInfoByUid userAllInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_data);


        userSession = new SharedPrefManager(this);
        id = userSession.getUser().getUser_id();
        loggedUser = userSession.getUser();
        tv_user_email = (TextView) findViewById(R.id.tv_user_email_update);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name_update);
        et_phone_update = (EditText) findViewById(R.id.et_user_phone_number_update);
        et_address_update = (EditText) findViewById(R.id.et_user_address_update);
        et_user_dob_update = (EditText) findViewById(R.id.et_user_dob_update);
        et_user_guardian_name_update = (EditText) findViewById(R.id.et_user_guardian_name_update);
        et_user_guardian_number_update = (EditText) findViewById(R.id.et_guardian_contact_number_update);
        et_user_about_you_update = (EditText) findViewById(R.id.et_user_about_you_update);
        btn_update = (Button) findViewById(R.id.btn_updateInfo);

        spnr_gender_update = (Spinner) findViewById(R.id.spinner_update_gender);
        spnr_level_update = (Spinner) findViewById(R.id.spinner_update_level);
        spnr_stream_update = (Spinner) findViewById(R.id.spinner_update_stream);

        tv_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUserData.this)
                        .setTitle("You can not update Name.");
                //call api for insertion
                builder.setNegativeButton("Okay", null);
                builder.setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        tv_user_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //no data , data magne
                //Toast.makeText(getActivity(), "empty array : ", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUserData.this)
                        .setTitle("You can not update email.");
                    //call api for insertion
                    builder.setNegativeButton("Okay", null);
                builder.setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //calendar class's instance and get date, month and year from calendar
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR); //current year
        mMonth = c.get(Calendar.MONTH); //current month
        mDay = c.get(Calendar.DAY_OF_MONTH); //current day
        et_user_dob_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date picker dialog
                dp = new DatePickerDialog(UpdateUserData.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //set day of month
                        String dob = year + "-" + (month + 1) + "-" + dayOfMonth;
                        et_user_dob_update.setText(dob);
                    }
                }, mYear, mMonth, mDay);
                dp.show();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });


    }
    
    private void updateData(){
            //Toast.makeText(this, "proceed clicked, ", Toast.LENGTH_SHORT).show();
            user_name_update = tv_user_name.getText().toString();
            user_phone_number = et_phone_update.getText().toString();

            address_update = et_address_update.getText().toString();
            user_dob_update = et_user_dob_update.getText().toString();
            user_guardian_name_update = et_user_guardian_name_update.getText().toString();
            user_guardian_number = et_user_guardian_number_update.getText().toString();

            user_about_you_update = et_user_about_you_update.getText().toString();
            user_gender_update = spnr_gender_update.getSelectedItem().toString();
            userSession.saveGenderSpnrPosition(spnr_gender_update.getSelectedItemPosition());
            

            user_level = spnr_level_update.getSelectedItem().toString();
            userSession.saveLevelSpnrPosition(spnr_level_update.getSelectedItemPosition());



            user_stream = spnr_stream_update.getSelectedItem().toString();
            userSession.saveStreamSpnrPosition(spnr_stream_update.getSelectedItemPosition());
            user_education_update = user_level.concat(", " + user_stream);


            if (user_phone_number.equals("")) {
                et_phone_update.setError("Enter phone number.");
                et_phone_update.requestFocus();
            } else if (address_update.equals("")) {
                et_address_update.setError("Address field can not be empty.");
                et_address_update.requestFocus();
            } else if (user_dob_update.equals("")) {
                et_user_dob_update.setError("Date of birth is required.");
                et_user_dob_update.requestFocus();
            } else if (user_guardian_name_update.equals("")) {
                et_user_guardian_name_update.setError("Guardian name is required.");
                et_user_guardian_name_update.requestFocus();
            } else if (user_guardian_number.equals("")) {
                et_user_guardian_number_update.setError("Please provide Guardian phone number.");
                et_user_guardian_number_update.requestFocus();
            } else if (user_about_you_update.equals("")) {
                et_user_about_you_update.setError("Provide something about you.");
                et_user_about_you_update.requestFocus();
            } else {
//                user_phone_number = Long.parseLong(ph_num);
//                user_guardian_number = Long.parseLong(guardian_num);
                //Toast.makeText(this, "Validated all. phone is: "+ user_phone_number, Toast.LENGTH_SHORT).show();
                apiUpdate();
            }
        Toast.makeText(this, "Update info clicked.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!userSession.isUserInfoExists()) {

            Intent intent = new Intent(this, StudentFurtherDetails.class);
           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //calendar_generate();
            //callApi(id);
            //showAlert();

        }else {
            Toast.makeText(this, "Info exists in session.", Toast.LENGTH_SHORT).show();
            fillBySession();
        }

    }

    private void fillBySession(){
        tv_user_name.setText(userSession.getUserInfo().getUsername());
        tv_user_email.setText(userSession.getUserInfo().getEmail());
        //Long phn = userSession.getUserInfo().getUser_phone_number();
        //String fon = String.valueOf(phn);
        et_phone_update.setText(userSession.getUserInfo().getUser_phone_number());
        et_user_dob_update.setText(userSession.getUserInfo().getUser_DOB());
        et_address_update.setText(userSession.getUserInfo().getUser_address());

        String val = userSession.getUserInfo().getUser_gender();
        position = userSession.getGenderPosition();
        spnr_gender_update.setSelection(position);
        et_user_guardian_name_update.setText(userSession.getUserInfo().getUser_guardian_name());
        //Long gphn = userSession.getUserInfo().getUser_guardian_contact_number();
        et_user_guardian_number_update.setText(userSession.getUserInfo().getUser_guardian_contact_number());

        spnr_level_update.setSelection(userSession.getLevelPosition());
        spnr_stream_update.setSelection(userSession.getStreamPosition());
        et_user_about_you_update.setText(userSession.getUserInfo().getAbout_you());
    }

    private void apiUpdate() {

        //Toast.makeText(this, "input are: "+user_phone_number, Toast.LENGTH_SHORT).show();
        Log.d(TAG,"apiUpdate started.");
        Call<UserInfoUpdateResponse> call1 = RetrofitClient.getInstance().getApi().userInfoUpdate(id,user_phone_number,address_update,user_dob_update,user_gender_update,user_guardian_name_update,user_guardian_number,user_education_update,user_about_you_update, user_phone_number,address_update,user_dob_update,user_gender_update,user_guardian_name_update,user_guardian_number,user_education_update,user_about_you_update);
        Log.d(TAG,"apiUpdate params sent.");
        call1.enqueue(new Callback<UserInfoUpdateResponse>() {
            @Override
            public void onResponse(Call<UserInfoUpdateResponse> call, Response<UserInfoUpdateResponse> response) {
                if(!response.body().isError() && response.body().getInfoResponse().getUserAllInfo().getUsername()!=null){
                    Log.d(TAG,"apiUpdate onSuccess.");
                    Toast.makeText(UpdateUserData.this, "Info updated successfully.", Toast.LENGTH_SHORT).show();
                   // SharedPrefManager.getInstance(UpdateUserData.this).saveUserInfo(response.body().getUserAllInfo());
                    Toast.makeText(UpdateUserData.this, "Session updated.", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d(TAG,"apiUpdate error.");
                    //SharedPrefManager.getInstance(UpdateUserData.this).saveUserInfo(response.body().getUserAllInfo());
                    Toast.makeText(UpdateUserData.this, "Info updation Error."+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfoUpdateResponse> call, Throwable t) {
                Log.d(TAG,"apiUpdate failure.");
                Toast.makeText(UpdateUserData.this, "info updation Failure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}