package com.hfad.hostel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.model.DefaultResponse;
import com.hfad.hostel.model.hostelInfoByHostelCodeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryActivity extends AppCompatActivity {

    String hostel_name,hostel_address,user_name,user_email,user_phone,owner_name, enquiry_message;
    int ownerid,userid;
    //,
    //            @Field("") int ownerid,
    //            @Field("") String user_name,
    //            @Field("") String user_email,
    //            @Field("") String user_phone,
    //            @Field("") String owner_name,
    //            @Field("") String hostel_name,
    //            @Field("") String hostel_address,
    //            @Field("enquiry_message") String enquiry_message
    static TextView eq_hname, eq_haddress,eq_uname, eq_uemail,eq_uphone, eq_user_message;
    static EditText et_user_message;
    static Button eq_btnSend, eq_btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        Intent intent = getIntent();
        hostel_name = intent.getStringExtra("hname");
        hostel_address= intent.getStringExtra("haddress");
        user_name = intent.getStringExtra("user_name");
        user_email=intent.getStringExtra("user_email");
        user_phone = intent.getStringExtra("user_phone");
        ownerid = intent.getIntExtra("ownerid",-1);
        owner_name = intent.getStringExtra("owner_name");
        userid = intent.getIntExtra("userid",-1);
        //`userid`, `ownerid`, `user_name`, `user_email`, `user_phone`, `owner_name`, `hostel_name`, `hostel_address`, `, `enquiry_message`,
        eq_hname = (TextView) findViewById(R.id.eq_dbox_tv_hostel_name);
        eq_haddress = (TextView) findViewById(R.id.eq_dbox_tv_hostel_address);
        eq_uname = (TextView) findViewById(R.id.eq_dbox_tv_user_name);
        eq_uemail = (TextView) findViewById(R.id.eq_dboxtv_user_email);
        eq_uphone = (TextView) findViewById(R.id.eq_dbox_tv_user_phone);
        et_user_message = (EditText) findViewById(R.id.eq_et_user_message);

        eq_hname.setText(hostel_name);
        eq_haddress.setText(hostel_address);

        eq_uname.setText(user_name);
        eq_uemail.setText(user_email);
        eq_uphone.setText(user_phone);

        eq_btnSend = (Button) findViewById(R.id.eq_btn_send);
        eq_btnCancel = (Button) findViewById(R.id.eq_btn_cancel);
        enquiry_message = et_user_message.getText().toString();
        eq_btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Toast.makeText(EnquiryActivity.this, "Button Send.", Toast.LENGTH_SHORT).show();
                        //send enquiry gare paxi database ma data update garera ani owner lai msg ra mail jawos hai

                        Toast.makeText(getApplicationContext(), "Enquiry Sent.", Toast.LENGTH_SHORT).show();
                        Call<DefaultResponse> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .insertEnquiry(userid, ownerid, user_name, user_email, user_phone, owner_name, hostel_name, hostel_address, enquiry_message);
                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                DefaultResponse defaultResponse = response.body();
                                Toast.makeText(EnquiryActivity.this, "Message: " + response.errorBody()+response.body().toString(), Toast.LENGTH_SHORT).show();
                                //db ma save vayo aba owner lai mail garne ani mbl ma msg garne

                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                Toast.makeText(EnquiryActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            });



        eq_btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();

            }
        });

    }
}