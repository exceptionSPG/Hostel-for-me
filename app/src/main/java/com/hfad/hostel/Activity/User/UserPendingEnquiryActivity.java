package com.hfad.hostel.Activity.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Adapters.UserPendingEnquiryAdapter;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.EnquiryModel;
import com.hfad.hostel.model.UserEnquiryResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPendingEnquiryActivity extends AppCompatActivity {

    RecyclerView rvUserPendingEnquiry;
    TextView tvNoDataFound;

    UserPendingEnquiryAdapter myAdapter;
    List<EnquiryModel> myList = new ArrayList<>();
    LinearLayoutManager layoutManager;
    private int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pending_enquiry2);

        rvUserPendingEnquiry = findViewById(R.id.rv_user_pending_enquiry_list);
        tvNoDataFound = findViewById(R.id.tv_user_pending_enquiry_no_data_found);
        userID = new SharedPrefManager(this).getUser().getUser_id();

        fetchPendingEnquiry();





    }

    private void fetchPendingEnquiry() {

        Call<UserEnquiryResponseModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUserAllPendingEnquiry(userID);


        call.enqueue(new Callback<UserEnquiryResponseModel>() {
            @Override
            public void onResponse(Call<UserEnquiryResponseModel> call, Response<UserEnquiryResponseModel> response) {
                if(response.body().isError()){

                    setContentView(R.layout.no_data_foung);
                    Toast.makeText(UserPendingEnquiryActivity.this, "No data found.", Toast.LENGTH_SHORT).show();
                }else {
                    //we have data
                    myList = response.body().getAllEnquiry();
                    layoutManager = new LinearLayoutManager(UserPendingEnquiryActivity.this,RecyclerView.VERTICAL,false);
                    rvUserPendingEnquiry.setLayoutManager(layoutManager);

                    myAdapter = new UserPendingEnquiryAdapter(myList);
                    rvUserPendingEnquiry.setAdapter(myAdapter);


                }
            }

            @Override
            public void onFailure(Call<UserEnquiryResponseModel> call, Throwable t) {
                tvNoDataFound.setVisibility(View.VISIBLE);
                setContentView(R.layout.no_data_foung);
                Toast.makeText(UserPendingEnquiryActivity.this, "Error occured: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchEnquiryPend(){
        Call<UserEnquiryResponseModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUserAllPendingEnquiry(userID);

        call.enqueue(new Callback<UserEnquiryResponseModel>() {
            @Override
            public void onResponse(Call<UserEnquiryResponseModel> call, Response<UserEnquiryResponseModel> response) {
                Toast.makeText(UserPendingEnquiryActivity.this, "Success.."+response.body().getTotal(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserEnquiryResponseModel> call, Throwable t) {
                Toast.makeText(UserPendingEnquiryActivity.this, userID+"Failed: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}