package com.hfad.hostel.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText et_email,et_password;
    Button btn_Log_in;
    TextView tv_go_to_signin,tv_goto_owner_login;
    public ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        this.progressDialog = new ProgressDialog(this);

        btn_Log_in = (Button) findViewById(R.id.btn_Login_In);
        tv_go_to_signin = (TextView) findViewById(R.id.tv_click_to_signin);
        tv_goto_owner_login = (TextView) findViewById(R.id.tv_goto_owner_login);
        tv_goto_owner_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Owner_Login.class));
            }
        });
        tv_go_to_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isUserLoggedIn()){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    public void btn_loginClick(View view) {

        loginData();
    }

    private void loginData(){
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(email.isEmpty()){
            et_email.setError("Email address field cannot be empty.");
            et_email.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please, Enter valid email.");
            et_email.requestFocus();
        }else if(password.isEmpty()){
            et_password.setError("Password cannot be empty.");
            et_password.requestFocus();
        }else {

            this.progressDialog.setTitle("Logging in");
            this.progressDialog.setMessage("Please wait, while we are logging in your account");
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.show();
            //Toast.makeText(LoginActivity.this, "Btn login clicked.", Toast.LENGTH_SHORT).show();

            //call api to login
            Call<LoginResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .userLogin(email,password);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    if(!loginResponse.isErr()){
                        progressDialog.dismiss();
                        //proceed to login
                        //save user
                        //open new home
                        SharedPrefManager.getInstance(LoginActivity.this).saveUser(loginResponse.getUser());
                        SharedPrefManager.getInstance(LoginActivity.this).saveKey(loginResponse.getKey());
                        //open new activity
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        Toast.makeText(LoginActivity.this, loginResponse.getMsg()+" key is "+loginResponse.getKey(), Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast to = new Toast(LoginActivity.this);
                    @SuppressLint("ResourceType") View view = LayoutInflater.from(LoginActivity.this).inflate(R.drawable.round_bg,null);
                    to.setView(view);
                    to.makeText(LoginActivity.this, "There was something wrong with your login. Please try again!", Toast.LENGTH_SHORT).show();
                }
            });

        }



    }
}