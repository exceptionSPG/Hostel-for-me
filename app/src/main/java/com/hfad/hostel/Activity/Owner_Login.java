package com.hfad.hostel.Activity;

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
import com.hfad.hostel.MapApi.AddLocationActivity;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.LoginResponse;
import com.hfad.hostel.model.OwnerLoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Owner_Login extends AppCompatActivity {

    EditText et_hostel_code, et_hostel_pwd;
    Button btn_owner_login;
    TextView tv_goto_user,tv_add_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__login);
        et_hostel_code = (EditText) findViewById(R.id.et_hostel_code);
        et_hostel_pwd = (EditText) findViewById(R.id.et_hostel_password);
        btn_owner_login = (Button) findViewById(R.id.btn_Owner_Login);
        tv_goto_user = (TextView) findViewById(R.id.tv_goto_user_login);
        tv_add_new = (TextView) findViewById(R.id.tvAddNew);
        tv_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.tvAddNew:
                        startActivity(new Intent(getApplicationContext(),HostelRequestActivity.class));
                        //startActivity(new Intent(getApplicationContext(), AddLocationActivity.class));
                }
            }
        });

        tv_goto_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.tv_goto_user_login:
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(SharedPrefManager.getInstance(this).isOwnerLoggedIn()){
//            Intent intent = new Intent(this,MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//
//    }

    public void btn_ownerloginClick(View view) {
        loginOwner();
    }

    private void loginOwner(){
        String hostel_code  = et_hostel_code.getText().toString().trim();
        String login_pwd = et_hostel_pwd.getText().toString().trim();

        if(hostel_code.isEmpty()){
            et_hostel_code.setError("Hostel code cannot be empty.");
            et_hostel_code.requestFocus();
//        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            et_email.setError("Please, Enter valid email.");
//            et_email.requestFocus();
        }else if(login_pwd.isEmpty()){
            et_hostel_pwd.setError("Password cannot be empty.");
            et_hostel_pwd.requestFocus();
        }else if (Patterns.EMAIL_ADDRESS.matcher(hostel_code).matches()){
            //call api to admin login
            Call<LoginResponse> call = RetrofitClient.getInstance().getApi().adminLogin(hostel_code,login_pwd);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(!response.body().isErr()){
                        //login success, save admin details in sharedpref
                        SharedPrefManager.getInstance(Owner_Login.this).saveUser(response.body().getUser());
                        SharedPrefManager.getInstance(Owner_Login.this).saveKey(response.body().getKey());
                        Toast.makeText(Owner_Login.this, response.body().getKey()+" Login Success. ", Toast.LENGTH_SHORT).show();
                        //open new activity
                        Intent intent = new Intent(Owner_Login.this,AdminHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(Owner_Login.this, "Key saved is: "+SharedPrefManager.getInstance(Owner_Login.this).getKey(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Owner_Login.this, "Error: "+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(Owner_Login.this, "Failure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            //call to api to login
            Call<OwnerLoginResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .ownerLogin(hostel_code,login_pwd);

            call.enqueue(new Callback<OwnerLoginResponse>() {
                @Override
                public void onResponse(Call<OwnerLoginResponse> call, Response<OwnerLoginResponse> response) {
                    OwnerLoginResponse ownerLoginResponse = response.body();
                    if(!ownerLoginResponse.isError()){
                        //proceed to login
                        //save owner in sharedpreferences

                        SharedPrefManager.getInstance(Owner_Login.this).saveOwner(ownerLoginResponse.getOwner());

                        //open new activity
                        Intent intent = new Intent(Owner_Login.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        Toast.makeText(Owner_Login.this, ownerLoginResponse.getMessage()+" key is "+ownerLoginResponse.getKeys(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Owner_Login.this, ownerLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OwnerLoginResponse> call, Throwable t) {
                    Toast.makeText(Owner_Login.this, "failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}