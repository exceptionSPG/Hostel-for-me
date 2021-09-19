package com.hfad.hostel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.model.DefaultResponse;
import com.hfad.hostel.model.hostelInfoByHostelCodeResponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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
        et_user_message = (EditText) findViewById(R.id.aeq_et_user_message);

        eq_hname.setText(hostel_name);
        eq_haddress.setText(hostel_address);

        eq_uname.setText(user_name);
        eq_uemail.setText(user_email);
        eq_uphone.setText(user_phone);

        eq_btnSend = (Button) findViewById(R.id.eq_btn_send);
        eq_btnCancel = (Button) findViewById(R.id.eq_btn_cancel);
        enquiry_message = et_user_message.getText().toString().trim();

        eq_btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(enquiry_message.isEmpty()){
//                        et_user_message.setError("Please enter message.");
//                        et_user_message.requestFocus();
                        enquiry_message = "I want to hyaa mula lyaang layisakyo";
                    }
                        Toast.makeText(EnquiryActivity.this, "Button Send.", Toast.LENGTH_SHORT).show();
                        //send enquiry gare paxi database ma data update garera ani owner lai msg ra mail jawos hai

                    RequestBody buserid = RequestBody.create(MediaType.parse("text/plain"),
                            String.valueOf(userid));
                    RequestBody bownerid = RequestBody.create(MediaType.parse("text/plain"),
                            String.valueOf(ownerid));
                    RequestBody buser_name = RequestBody.create(MediaType.parse("text/plain"),
                            user_name);
                    RequestBody buser_email = RequestBody.create(MediaType.parse("text/plain"),
                            user_email);
                    RequestBody buser_phone = RequestBody.create(MediaType.parse("text/plain"),
                            user_phone);
                    RequestBody bowner_name = RequestBody.create(MediaType.parse("text/plain"),
                            owner_name);
                    RequestBody bhostel_name = RequestBody.create(MediaType.parse("text/plain"),
                            hostel_name);
                    RequestBody bhostel_address = RequestBody.create(MediaType.parse("text/plain"),
                            hostel_address);
                    RequestBody benquiry_message = RequestBody.create(MediaType.parse("text/plain"),
                            enquiry_message);
                    Log.d("Request Body: ", bhostel_name.toString());
                    Toast.makeText(EnquiryActivity.this, "Request body: "+buser_name, Toast.LENGTH_SHORT).show();
                        Call<DefaultResponse> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .insertEnquiry(buserid, bownerid, buser_name, buser_email, buser_phone, bowner_name, bhostel_name, bhostel_address, benquiry_message);



                        Log.d("Call Onclick: ", "onClick: user message: "+enquiry_message);
                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                                DefaultResponse defaultResponse = response.body();
                                Log.d("Call request", call.request().toString());
                                Log.d("Call request header", call.request().headers().toString());
                                Toast.makeText(EnquiryActivity.this, "request header: "+ call.request().headers().toString(), Toast.LENGTH_SHORT).show();


                                Log.d("Response raw header", response.headers().toString());
                                Toast.makeText(EnquiryActivity.this, "response code: "+ response.headers(), Toast.LENGTH_SHORT).show();

                                Log.d("Response raw", String.valueOf(response.raw().body()));
                                Toast.makeText(EnquiryActivity.this, "response raw: "+ response.raw(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(EnquiryActivity.this, "response error: "+ response.errorBody(), Toast.LENGTH_SHORT).show();

                                Log.d("Response code", String.valueOf(response.code()));
                                Toast.makeText(EnquiryActivity.this, "response code: "+ response.code(), Toast.LENGTH_SHORT).show();

                                //stack overflow code snippets
                                if(response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Enquiry Sent.", Toast.LENGTH_SHORT).show();
                                    //the response-body is already parseable to your ResponseBody object
                                    DefaultResponse responseBody = (DefaultResponse) response.body();
                                    //you can do whatever with the response body now...
                                    assert responseBody != null;
                                    String responseBodyString= responseBody.toString();
                                    Log.d("Response body: ", responseBodyString);
                                }
                                else  {
                                    Log.d("Response errorBody", String.valueOf(response.errorBody()));
                                }

//                                Log.d("Enquiry Activity: ", "onResponse:Heyluu mayaluu ");
//
//                                Toast.makeText(EnquiryActivity.this, "Message: ", Toast.LENGTH_SHORT).show();
//                                //db ma save vayo aba owner lai mail garne ani mbl ma msg garne

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