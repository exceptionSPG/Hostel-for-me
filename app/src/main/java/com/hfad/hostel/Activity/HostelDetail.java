package com.hfad.hostel.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.OwnerAllDetailsByHostelCode;
import com.hfad.hostel.model.OwnerInfoResponse;
import com.hfad.hostel.model.hostelInfoByHostelCodeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostelDetail extends AppCompatActivity {
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    ViewPager viewPager;

    List<SlideModel> imageList = new ArrayList<>(); // Create image list
    ImageSlider imageSlider;

    OwnerAllDetailsByHostelCode allDetailsByHostelCode;  //first call api then access data
    Owner owner;
    TextView tv_name, tv_hostel_location, tv_hostel_facility;
    String hostel_code, hostel_name,hostel_location;

    //hostel all details
    //OwnerAllDetailsByHostelCode ownerAllDetailsByHostelCode = new OwnerAllDetailsByHostelCode();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_hostel_detail);
        setContentView(R.layout.collapsing_details_layout);

        statusColorChange();

        tv_name = (TextView) findViewById(R.id.tvHostelName);
        tv_hostel_location = (TextView) findViewById(R.id.tvLocation);

        Intent intent = getIntent();
        hostel_code = intent.getStringExtra("hostel_code");
        hostel_name = intent.getStringExtra("name");
        hostel_location = intent.getStringExtra("location");

        //tv_name.setText(allDetailsByHostelCode.getContact_number());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        show the title for the ToolBar and it looks like
        collapsingToolbarLayout.setTitle(hostel_name);
        //tv_name.setText(hostel_name);
        tv_hostel_location.setText(hostel_location);
        getHostelAllDetails();
        ImageView img_back = findViewById(R.id.img_back);
        //img_back.setVisibility(View.INVISIBLE);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0)
                {

                    // Fully expanded
                }
                else
                {
                    // Not fully expanded or collapsed
                   // img_back.setVisibility(View.VISIBLE);

                }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        adding transparency
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
//        Adding parallax color
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));//#3f1f09
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#D50000"));

        //TextView tst=(TextView)findViewById(R.id.first);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageSlider = (ImageSlider) findViewById(R.id.image_slider);

        //image slider works perfectly, add it from db
        imageList.add(new SlideModel("https://scontent.fktm4-1.fna.fbcdn.net/v/t1.0-9/86451663_2759572170998924_5881915509523349504_o.jpg?_nc_cat=102&ccb=2&_nc_sid=dd9801&_nc_ohc=gmSADsAaPL4AX86Pp2i&_nc_ht=scontent.fktm4-1.fna&oh=a1732e318304e7663fbbdb46ed050450&oe=5FE85E71",hostel_name,ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://scontent.fktm4-1.fna.fbcdn.net/v/t1.0-9/39064447_2275500172739462_2393256923811020800_n.jpg?_nc_cat=106&ccb=2&_nc_sid=8024bb&_nc_ohc=H9xbMdxp8a0AX-HXzoc&_nc_ht=scontent.fktm4-1.fna&oh=af1a0d0548906a458eba9264e3dd300f&oe=5FE900A9",hostel_location,ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://scontent.fktm4-1.fna.fbcdn.net/v/t1.0-9/38923523_2275500176072795_4044981039523692544_n.jpg?_nc_cat=109&ccb=2&_nc_sid=8024bb&_nc_ohc=UIyYTUAFOMcAX8vYxeH&_nc_oc=AQlPnvvIk9tFf3dZ9CFoURHoZbzTpyChdvE5ZmufXahKytOyY91Hvat2mP_EO0EQKuo&_nc_ht=scontent.fktm4-1.fna&oh=ccb88ae7e39154fc4ed335982a76ecd7&oe=5FEA6760",hostel_code,ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imageList);
//        tst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Kaj kore",Toast.LENGTH_SHORT).show();
//            }
//        });



        SharedPrefManager.getInstance(this).saveCode(hostel_code);


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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_act_menu, menu); //your file name
        Log.e("Detail: ","Menu inflated.");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {


        switch (item.getItemId()) {
            case R.id.det_enquiry:
                Toast.makeText(this, "Enquiry to owner..", Toast.LENGTH_SHORT).show();
                //your code
                // EX : call intent if you want to swich to other activity
                fillDialogbox();
                return true;
            case R.id.det_facebook:
                Toast.makeText(this, "owner Facebook..", Toast.LENGTH_SHORT).show();

                //your code
                return true;
            case R.id.det_twitter:
                Toast.makeText(this, "owner Twitter..", Toast.LENGTH_SHORT).show();

                //your code
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //to fill enquiry and send
    public void fillDialogbox(){

    }

    public void statusColorChange(){
        /*-------Status Color Code To Change--------*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
    }

    //method to access all info by hostel code
    public void getHostelAllDetails(){
        Call<hostelInfoByHostelCodeResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getHostelInfoByHostelCode(hostel_code);
        call.enqueue(new Callback<hostelInfoByHostelCodeResponse>() {
            @Override
            public void onResponse(Call<hostelInfoByHostelCodeResponse> call, Response<hostelInfoByHostelCodeResponse> response) {
                Toast.makeText(HostelDetail.this, "on details load onResponse."+allDetailsByHostelCode.getMessage(), Toast.LENGTH_SHORT).show();

                allDetailsByHostelCode = response.body().getOwnerAllDetailsByHostelCode();
                tv_name.setText(allDetailsByHostelCode.getMessage());
            }

            @Override
            public void onFailure(Call<hostelInfoByHostelCodeResponse> call, Throwable t) {
                Toast.makeText(HostelDetail.this, "on details load onFailure.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}