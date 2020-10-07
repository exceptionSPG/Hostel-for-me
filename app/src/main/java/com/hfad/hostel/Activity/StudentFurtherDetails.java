package com.hfad.hostel.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hfad.hostel.Fragment.ProfileFragement;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.DefaultResponse;
import com.hfad.hostel.model.User;
import com.hfad.hostel.model.userAllInfoByUid;
import com.hfad.hostel.model.userInfoResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentFurtherDetails extends AppCompatActivity {

    DatePickerDialog dp;
    SharedPrefManager userSession;
    EditText et_phone_number, et_address, et_dateOB, et_guardian_name, et_guardian_contact_number, et_about_you;
    Spinner spnr_gender, spnr_level, spnr_stream;
    String plus_two_stream[] = {"Science", "Management"};
    String bachelor_stream[] = {"MBBS", "Engineering", "BBA", "BBM", "BBS", "Others"};
    String master_stream[] = {"MD", "ME", "MBA", "MBM", "MBS", "Others"};
    String user_contact_number,guardian_contact_number, user_address, user_dob, user_guardian_name, user_about_you, user_gender, user_level, user_stream, user_education;
    int id,position_gender,position_level,position_stream;
    int mYear, mMonth, mDay;
    User loggedUser;
    Button btn_save_info;
    userAllInfoByUid userAllInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_further_details);

        et_phone_number = (EditText) findViewById(R.id.et_user_phone_number);
        et_address = (EditText) findViewById(R.id.et_user_address);
        et_dateOB = (EditText) findViewById(R.id.et_user_DOB);
        et_guardian_name = (EditText) findViewById(R.id.et_user_guardian_name);
        et_guardian_contact_number = (EditText) findViewById(R.id.et_guardian_contact_number);
        et_about_you = (EditText) findViewById(R.id.et_user_about_you);


        spnr_gender = (Spinner) findViewById(R.id.spinner_gender);
        spnr_level = (Spinner) findViewById(R.id.spinner_level);
        spnr_stream = (Spinner) findViewById(R.id.spinner_stream);

        btn_save_info = (Button) findViewById(R.id.btn_saveInfo);

        userSession = new SharedPrefManager(this);

        //calendar class's instance and get date, month and year from calendar
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR); //current year
        mMonth = c.get(Calendar.MONTH); //current month
        mDay = c.get(Calendar.DAY_OF_MONTH); //current day

        et_dateOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date picker dialog
                dp = new DatePickerDialog(StudentFurtherDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //set day of month
                        String dob = year + "-" + (month + 1) + "-" +dayOfMonth ;
                        et_dateOB.setText(dob);
                    }
                }, mYear, mMonth, mDay);
                dp.show();

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!userSession.isUserLoggedIn()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            loggedUser = userSession.getUser();
            id = loggedUser.getUser_id();
            //calendar_generate();

        }
    }

    private void insertInfo() {
        //Toast.makeText(this, "proceed clicked, ", Toast.LENGTH_SHORT).show();
        user_contact_number = et_phone_number.getText().toString();

        user_address = et_address.getText().toString();
        user_dob = et_dateOB.getText().toString();
        user_guardian_name = et_guardian_name.getText().toString();
        guardian_contact_number = et_guardian_contact_number.getText().toString();

        user_about_you = et_about_you.getText().toString();
        user_gender = spnr_gender.getSelectedItem().toString();
        position_gender = spnr_gender.getSelectedItemPosition();
        userSession.saveGenderSpnrPosition(position_gender);

        user_level = spnr_level.getSelectedItem().toString();
        position_level = spnr_level.getSelectedItemPosition();
        userSession.saveLevelSpnrPosition(spnr_level.getSelectedItemPosition());



        user_stream = spnr_stream.getSelectedItem().toString();
        userSession.saveStreamSpnrPosition(spnr_stream.getSelectedItemPosition());
        user_education = user_level.concat(", " + user_stream);


        if (user_contact_number.equals("")) {
            et_phone_number.setError("Enter phone number.");
            et_phone_number.requestFocus();
        } else if (user_address.equals("")) {
            et_address.setError("Address field can not be empty.");
            et_address.requestFocus();
        } else if (user_dob.equals("")) {
            et_dateOB.setError("Date of birth is required.");
            et_dateOB.requestFocus();
        } else if (user_guardian_name.equals("")) {
            et_guardian_name.setError("Guardian name is required.");
            et_guardian_name.requestFocus();
        } else if (guardian_contact_number.equals("")) {
            et_guardian_contact_number.setError("Please provide Guardian phone number.");
            et_guardian_contact_number.requestFocus();
        } else if (user_about_you.equals("")) {
            et_about_you.setError("Provide something about you.");
            et_about_you.requestFocus();
        } else {
//            user_contact_number = Long.parseLong(ph_num);
//            guardian_contact_number = Long.parseLong(guardian_num);
            apiInsert();
        }
    }

    private void apiInsert() {
        Toast.makeText(this, "khoi checking", Toast.LENGTH_SHORT).show();
        //call api
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().insertUserInfo(id, user_contact_number, user_address, user_dob, user_gender, user_guardian_name, guardian_contact_number, user_education, user_about_you);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isErr()) {
                        //success insertion
                        callApi(id);
                        Toast.makeText(StudentFurtherDetails.this, "Success: " + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        //error occured
                        Toast.makeText(StudentFurtherDetails.this, "Error: " + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(StudentFurtherDetails.this, "Operation unsuccess: " + response.message(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(StudentFurtherDetails.this, "Failure::" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void btn_SaveInfoClick(View view) {
        insertInfo();
        //Toast.makeText(this, "you typed ::"+user_phone_number, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, ProfileFragement.class));

    }

    private void callApi(int id) {

        Call<userInfoResponse> call = RetrofitClient.getInstance().getApi().getUserInfoByUid(id);
        call.enqueue(new Callback<userInfoResponse>() {
            @Override
            public void onResponse(Call<userInfoResponse> call, Response<userInfoResponse> response) {
                if (response.body() != null) {
                    userAllInfo = response.body().getUserAllInfo();
                    if (userAllInfo.getUsername() != null) {
                        //show data
                        SharedPrefManager.getInstance(StudentFurtherDetails.this).saveUserInfo(userAllInfo);
                        Toast.makeText(StudentFurtherDetails.this, "Hello : " + userAllInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        //no data , data magne
                        Toast.makeText(StudentFurtherDetails.this, "empty array : " + userAllInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(StudentFurtherDetails.this, "Body null produced. : ", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<userInfoResponse> call, Throwable t) {
                Toast.makeText(StudentFurtherDetails.this, "Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}
