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

import com.google.android.material.snackbar.Snackbar;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.DefaultResponse;
import com.hfad.hostel.model.User;
import com.hfad.hostel.model.hostelInfoByHostelCodeResponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryActivity extends AppCompatActivity {

    String hostelName,hostelAddress,userName,userEmail,userPhone,ownerName, enquiryMessage;
    int ownerId,userId;
    User loggedUser;
    SharedPrefManager userSession;
   // @Field("userid") String userId,
   //            @Field("ownerid") String ownerId,
   //            @Field("username") String userName,
   //            @Field("user_email") String userEmail,
   //            @Field("user_phone") String userPhone,
   //            @Field("owner_name") String ownerName,
   //            @Field("hostel_address") String hostelAddress,
   //            @Field("enquiry_message") String enquiryMessage
    static TextView eq_hname, eq_haddress,eq_uname, eq_uemail,eq_uphone, eq_user_message;
    static EditText et_user_message;
    static Button eq_btnSend, eq_btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        userSession = new SharedPrefManager(this);
        Intent intent = getIntent();
        hostelName = intent.getStringExtra("hname");
        hostelAddress= intent.getStringExtra("haddress");
        userName = intent.getStringExtra("user_name");
        userEmail=intent.getStringExtra("user_email");
        userPhone = intent.getStringExtra("user_phone");
        ownerId = intent.getIntExtra("ownerid",-1);
        ownerName = intent.getStringExtra("owner_name");
        userId = intent.getIntExtra("userid",-1);


        eq_hname = (TextView) findViewById(R.id.eq_dbox_tv_hostel_name);
        eq_haddress = (TextView) findViewById(R.id.eq_dbox_tv_hostel_address);
        eq_uname = (TextView) findViewById(R.id.eq_dbox_tv_user_name);
        eq_uemail = (TextView) findViewById(R.id.eq_dboxtv_user_email);
        eq_uphone = (TextView) findViewById(R.id.eq_dbox_tv_user_phone);
        et_user_message = (EditText) findViewById(R.id.aeq_et_user_message);

        eq_hname.setText(hostelName);
        eq_haddress.setText(hostelAddress);
        eq_uname.setText(userName);
        eq_uemail.setText(userEmail);
        eq_uphone.setText(userPhone);

        eq_btnSend = (Button) findViewById(R.id.eq_btn_send);
        eq_btnCancel = (Button) findViewById(R.id.eq_btn_cancel);
//        enquiryMessage = et_user_message.getText().toString().trim();


        eq_btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enquiryMessage = et_user_message.getText().toString().trim();
                Toast.makeText(EnquiryActivity.this, "Typed: "+enquiryMessage, Toast.LENGTH_SHORT).show();
                if(enquiryMessage.isEmpty()){
                    et_user_message.setError("Enter message.");
                    et_user_message.requestFocus();
                }else {
                    Call<DefaultResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .createEnquiry(userId,ownerId,userName,userEmail,userPhone,ownerName,hostelName,hostelAddress,enquiryMessage);

                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            if(response.body().isErr()){
                                Toast.makeText(EnquiryActivity.this, "Response body is error.", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(EnquiryActivity.this, "Successfully sent enquiry."+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                try
                                {
                                    Thread.sleep(1000);
                                }
                                catch(InterruptedException ex)
                                {
                                    Thread.currentThread().interrupt();
                                }
                                onBackPressed();

                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            Toast.makeText(EnquiryActivity.this, "Error occured: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
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


    @Override
    protected void onStart() {
        super.onStart();
        if (!userSession.isUserLoggedIn()) {
            //Snackbar.make(getCurrentFocus(),"You must log in to send enquiry.", Snackbar.LENGTH_LONG);
            Toast.makeText(this, "You must logged in to send enquiry.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }


    /*

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

     */
}