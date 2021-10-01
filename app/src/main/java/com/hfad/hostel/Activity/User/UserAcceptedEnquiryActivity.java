package com.hfad.hostel.Activity.User;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.security.NetworkSecurityPolicy;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Adapters.RecyclerAllEnquiryAdminAdapter;
import com.hfad.hostel.Adapters.RecyclerPendingEnquiryOwnerAdapter;
import com.hfad.hostel.Adapters.UserAcceptedEnquiryAdapter;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.AdminAllEnquiryModel;
import com.hfad.hostel.model.EnquiryModel;
import com.hfad.hostel.model.User;
import com.hfad.hostel.model.UserEnquiryResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.lang.Object;

public class UserAcceptedEnquiryActivity extends AppCompatActivity {

    RecyclerView rvAcceptedEnquiry;
    UserAcceptedEnquiryAdapter myAdapter;
    LinearLayoutManager layoutManager;
    List<EnquiryModel> enquiryModelList = new ArrayList<>();
    SharedPrefManager userSession;
    private int id;
    User loggedUser;
    TextView tvNoDataFound;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_accepted_enquiry);
        tvNoDataFound = findViewById(R.id.tv_user_accepted_enquiry_no_data_found);
        userSession = new SharedPrefManager(this);

        rvAcceptedEnquiry = findViewById(R.id.rv_user_accepted_enquiry_list);

        layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        rvAcceptedEnquiry.setLayoutManager(layoutManager);

        loggedUser = userSession.getUser();
        id = loggedUser.getUser_id();

        NetworkSecurityPolicy networkSecurityPolicy = NetworkSecurityPolicy.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Boolean nsStatus = networkSecurityPolicy.isCleartextTrafficPermitted ("http://192.168.221.73:8080/");
            Toast.makeText(this, "Status: "+nsStatus, Toast.LENGTH_SHORT).show();
            if(nsStatus){
                fetchAllEnquiry();
            }else{
                fetchAllEnquiry();
                Toast.makeText(this, "Network Security Policy.", Toast.LENGTH_SHORT).show();
            }
        }
        





        Toast.makeText(this, "Welcome to accepted. I will show all accepted and implement payment.", Toast.LENGTH_SHORT).show();
    }


    private void fetchEnquiry(){
        Call<AdminAllEnquiryModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllEnquiry();

        call.enqueue(new Callback<AdminAllEnquiryModel>() {
            @Override
            public void onResponse(Call<AdminAllEnquiryModel> call, Response<AdminAllEnquiryModel> response) {
                Toast.makeText(UserAcceptedEnquiryActivity.this, "Success response.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AdminAllEnquiryModel> call, Throwable t) {

            }
        });
    }

    private void fetchAllEnquiry(){
        Call<UserEnquiryResponseModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUserAllAcceptedEnquiry(id);

        call.enqueue(new Callback<UserEnquiryResponseModel>() {
            @Override
            public void onResponse(Call<UserEnquiryResponseModel> call, Response<UserEnquiryResponseModel> response) {

                if(response.body().isError()){
                    //error true hunu vaneko hami sang data xaina ho so data xaina vanne kura dekham
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    Toast.makeText(UserAcceptedEnquiryActivity.this, "No data found.", Toast.LENGTH_SHORT).show();
                    

                }else {
                    enquiryModelList = response.body().getAllEnquiry();

                    Toast.makeText(getApplicationContext(), "Enquiry Fetching..."+enquiryModelList.size(), Toast.LENGTH_SHORT).show();
                    myAdapter = new UserAcceptedEnquiryAdapter(enquiryModelList);

                    rvAcceptedEnquiry.setAdapter(myAdapter);
                    Toast.makeText(getApplicationContext(), "Adapter Called..", Toast.LENGTH_SHORT).show();
                    
                   
                }
                
               
            }

            @Override
            public void onFailure(Call<UserEnquiryResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error occured."+t.getMessage(), Toast.LENGTH_SHORT).show();
                tvNoDataFound.setVisibility(View.VISIBLE);
                setContentView(R.layout.no_data_foung);
                Toast.makeText(UserAcceptedEnquiryActivity.this, "here:"+call.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}