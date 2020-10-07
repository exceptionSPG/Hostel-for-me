package com.hfad.hostel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.hfad.hostel.Adapters.ViewPagerAdapter;
import com.hfad.hostel.Fragment.FragmentFacility;
import com.hfad.hostel.Fragment.FragmentHostelDetails;
import com.hfad.hostel.Fragment.FragmentPricing;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.OwnerAllDetailsByHostelCode;
import com.hfad.hostel.model.OwnerInfoResponse;
import com.hfad.hostel.model.hostelInfoByHostelCodeResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostelDetail extends AppCompatActivity {
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    ViewPager viewPager;

    OwnerAllDetailsByHostelCode allDetailsByHostelCode;
    Owner owner;
    TextView tv_name, tv_hostel_location, tv_hostel_facility;
    String hostel_code, hostel_name,hostel_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_detail);

        tv_name = (TextView) findViewById(R.id.tvHostelName);
        tv_hostel_location = (TextView) findViewById(R.id.tvLocation);

        Intent intent = getIntent();
        hostel_code = intent.getStringExtra("hostel_code");
        hostel_name = intent.getStringExtra("name");
        hostel_location = intent.getStringExtra("location");

        SharedPrefManager.getInstance(this).saveCode(hostel_code);

        getSupportActionBar().setTitle((CharSequence) hostel_name);
        tv_name.setText(hostel_name);
        tv_hostel_location.setText(hostel_location);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adding fragment
        viewPagerAdapter.AddFragment(new FragmentHostelDetails(),"Hostel Details");
        viewPagerAdapter.AddFragment(new FragmentPricing(),"Hostel Price");
        viewPagerAdapter.AddFragment(new FragmentFacility(),"Hostel Facility");
        //setup viewpager
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicator(R.color.colorChamkilo);


//

        //detailShow();
//        showOwnerDetail();
    }

   /* private void showOwnerDetail() {
        //api call which will produce hostelInfoByHostelCodeResponse
        Call<hostelInfoByHostelCodeResponse> call = RetrofitClient.getInstance().getApi().getHostelInfoByHostelCode(hostel_code);

        call.enqueue(new Callback<hostelInfoByHostelCodeResponse>() {
            @Override
            public void onResponse(Call<hostelInfoByHostelCodeResponse> call, Response<hostelInfoByHostelCodeResponse> response) {
                //        if() {
                //String msg = null;

                // assert response.body() != null;
                // allDetailsByHostelCode = response.body().;
//                try {
//                    if (response.isSuccessful()) {
//                        //msg = response.body().string();
//                        //Toast.makeText(HostelDetail.this, "success :: " + msg, Toast.LENGTH_SHORT).show();
//                    } else
//                        Toast.makeText(HostelDetail.this, "unsuccess ", Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if( msg !=null){
//                    try {
//                        JSONObject js = new JSONObject(msg);
//                        Toast.makeText(HostelDetail.this, "message:: "+js.getString("users"), Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                //viewcreat ma rakhne
           //     tv_name = (TextView) findViewById(R.id.tv_hostel_nam);
//        tv_hostel_price = (TextView) findViewById(R.id.tv_price);
//        tv_hostel_facility = (TextView) findViewById(R.id.tv_facility);
//                }
//                }

                if (!response.body().isError()) {
                    //if (allDetailsByHostelCode!=null) {
                    allDetailsByHostelCode = response.body().getOwnerAllDetailsByHostelCode();
                        tv_name.setText(allDetailsByHostelCode.getHostel_name());
                        tv_hostel_facility.setText(allDetailsByHostelCode.getFacility());
                        tv_hostel_price.setText(allDetailsByHostelCode.getPricing());
                        Toast.makeText(HostelDetail.this, "Success ::" + allDetailsByHostelCode.getMessage(), Toast.LENGTH_SHORT).show();

                   // }else Toast.makeText(HostelDetail.this, "User data null ::", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(HostelDetail.this, "User data null ::", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<hostelInfoByHostelCodeResponse> call, Throwable t) {
                Toast.makeText(HostelDetail.this, "Failure ::" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    } */

   /* private void detailShow(){
        Call<hostelInfoByHostelCodeResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getHostelInfoByHostelCode(hostel_code);
       call.enqueue(new Callback<hostelInfoByHostelCodeResponse>() {
           @Override
           public void onResponse(Call<hostelInfoByHostelCodeResponse> call, Response<hostelInfoByHostelCodeResponse> response) {
               if(response.body()!=null){
                 //  Owner ow = response.body()
                   List<OwnerAllDetailsByHostelCode> ht = response.body().getAllDetailsByHostelCode();
                   if(ht.isEmpty()){

                       Toast.makeText(HostelDetail.this, "body not null", Toast.LENGTH_SHORT).show();
                       //Toast.makeText(HostelDetail.this, ht.getHostel_name(), Toast.LENGTH_SHORT).show();
//                       tv_name.setText(response.body().getHostel_name());
//                       tv_hostel_facility.setText(response.body().getHostel_owner_name());
//                       tv_hostel_price.setText(response.body().getHostel_location());
                   }else Toast.makeText(HostelDetail.this, "ht null", Toast.LENGTH_SHORT).show();

                   /*if(tv_name!=null && tv_hostel_facility!=null && tv_hostel_price!=null && ht!=null){
                       Toast.makeText(HostelDetail.this, ht.getHostel_name(), Toast.LENGTH_SHORT).show();
                       tv_name.setText(ht.getHostel_name());
                       tv_hostel_facility.setText(ht.getFacility());
                       tv_hostel_price.setText(ht.getPricing());

                   }else Toast.makeText(HostelDetail.this, "Null aayo bro tv", Toast.LENGTH_SHORT).show();

               }
               else Toast.makeText(HostelDetail.this, "Null aayo bro", Toast.LENGTH_SHORT).show();
              
           }

           @Override
           public void onFailure(Call<hostelInfoByHostelCodeResponse> call, Throwable t) {
               Toast.makeText(HostelDetail.this, "failure", Toast.LENGTH_SHORT).show();
           }
       });
    }*/

}