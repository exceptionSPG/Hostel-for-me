package com.hfad.hostel.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText et_name,et_email,et_password;
    Button btn_sign_in;
    TextView tv_go_to_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_sign_in = (Button) findViewById(R.id.btn_Sign_In);
        tv_go_to_login = (TextView) findViewById(R.id.tv_click_to_login);

        tv_go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isUserLoggedIn()){
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    public void btn_SignClick(View view) {
        registerData();
    }

    private void registerData() {
//        Toast.makeText(RegisterActivity.this, "Btn Register click", Toast.LENGTH_SHORT).show();
        String email = et_email.getText().toString().trim();
        String name = et_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(name.isEmpty()){
            et_name.setError("Name field cannot be empty.");
            et_name.requestFocus();
        }else if(email.isEmpty()){
            et_email.setError("Email address field cannot be empty.");
            et_email.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please, Enter valid email.");
            et_email.requestFocus();
        }else if(password.isEmpty()){
            et_password.setError("Password cannot be empty.");
            et_password.requestFocus();
        }else if(password.length()<4){
            et_password.setError("Password must be at leat 4 character.");
            et_password.requestFocus();
        }else {
            //do registration using api call

            Call<DefaultResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .createUser(name,email,password);

            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    DefaultResponse dr = response.body();
                    if(response.code()==201){
                        //dr = response.body();
                        Toast.makeText(getApplicationContext(), dr.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }else if(response.code() == 422){ //&& dr.getMsg()=="User Already exists"
                        Toast.makeText(getApplicationContext(), "User Already exists.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                }
            });

        }



    }
}