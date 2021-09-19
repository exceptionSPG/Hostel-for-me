package com.hfad.hostel.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.hfad.hostel.model.User;
import com.hfad.hostel.model.UserInfo;
import com.hfad.hostel.model.hostelInfoByHostelCodeResponse;
import com.hfad.hostel.model.userAllInfoByUid;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostelDetail extends AppCompatActivity {
    static String TAG = "HostelDetail: ";
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    ViewPager viewPager;
    SharedPrefManager sharedPrefManager;
    int ownerId;
    String ownerName;

    List<SlideModel> imageList = new ArrayList<>(); // Create image list
    ImageSlider imageSlider;
    String about_us_all = " ";
    //static String h_name, hostel_address, user_name,user_email,user_phone;

    OwnerAllDetailsByHostelCode allDetailsByHostelCode;  //first call api then access data
    Owner owner;
    TextView tv_name, tv_hostel_location, tv_hostel_facility, tv_pricing, tv_about_us;
    String hostel_code, hostel_name,hostel_location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_hostel_detail);
        setContentView(R.layout.collapsing_details_layout);

        statusColorChange();

        tv_name = (TextView) findViewById(R.id.tvHostelName);
        tv_hostel_location = (TextView) findViewById(R.id.tvLocation);
        tv_hostel_facility = (TextView) findViewById(R.id.tv_facility);
        tv_pricing = (TextView) findViewById(R.id.tv_pricing);
        tv_about_us = (TextView) findViewById(R.id.tv_about_us);


        Intent intent = getIntent();
        hostel_code = intent.getStringExtra("hostel_code");
        hostel_name = intent.getStringExtra("name");
        hostel_location = intent.getStringExtra("location");

        //tv_name.setText(allDetailsByHostelCode.getContact_number());
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        show the title for the ToolBar and it looks like
        collapsingToolbarLayout.setTitle(hostel_name);

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
        imageList.add(new SlideModel(" abcd",hostel_name,ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("abcd ",hostel_location,ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(" abcd",hostel_code,ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imageList);
//        tst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Kaj kore",Toast.LENGTH_SHORT).show();
//            }
//        });



        SharedPrefManager.getInstance(this).saveCode(hostel_code);


        getHostelAllDetails();
    }





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
                User user = SharedPrefManager.getInstance(this).getUser();
                userAllInfoByUid userInfo = SharedPrefManager.getInstance(this).getUserInfo();
                Intent intent = new Intent(HostelDetail.this,EnquiryActivity.class);
                //`userid`, `ownerid`, `user_name`, `user_email`, `user_phone`, `owner_name`, `hostel_name`, `hostel_address`, `, `enquiry_message`,
                intent.putExtra("userid",user.getUser_id());
                intent.putExtra("ownerid",ownerId);
                intent.putExtra("owner_name",ownerName);
                intent.putExtra("hname",hostel_name);
                intent.putExtra("haddress",hostel_location);
                intent.putExtra("user_name",user.getUsername());
                intent.putExtra("user_email",user.getEmail());
                intent.putExtra("user_phone",userInfo.getUser_phone_number());
                Toast.makeText(this, "user phone:"+userInfo.getUser_phone_number(), Toast.LENGTH_SHORT).show();
                startActivity(intent);

                //customDialog(getApplicationContext(),hostel_name,hostel_location,user.getUsername(),user.getEmail(),userInfo.getUser_phone_number());
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
        public static void fillDialogbox(Context context, String hname,
                                         String haddress, String uname, String uemail, String uphone) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.enquiry_dialog_layout, null);
            alertDialogBuilder.setView(view);
            alertDialogBuilder.setCancelable(false);
            final AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();

    }
    public void customDialog(Context context,String hname,
                             String haddress, String uname, String uemail, String uphone){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the
        // dialog layout
        builder.setTitle("title");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_email);
        View view = inflater.inflate(R.layout.enquiry_dialog_layout, null);
        builder.setView(view);


        // Add action buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //send data
                    }
    })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    builder.create();
    builder.show();
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
                allDetailsByHostelCode = response.body().getOwnerAllDetailsByHostelCode();



                Toast.makeText(HostelDetail.this, "on details load onResponse."+allDetailsByHostelCode.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: fetching.."+allDetailsByHostelCode.getMessage());
                tv_hostel_facility.setText(allDetailsByHostelCode.getFacility());
                tv_pricing.setText(allDetailsByHostelCode.getPricing());
                about_us_all = about_us_all .concat("Owned By: "+allDetailsByHostelCode.getHostel_owner_name()+"\nHostel Type: "+allDetailsByHostelCode.getHostel_type()+"\nContact Number: "+allDetailsByHostelCode.getContact_number()+"\nHostel e-mail: "+allDetailsByHostelCode.getHostel_email()+"\nRegistered on Hostel Finder: "+allDetailsByHostelCode.getHostel_registered_date());
                tv_about_us.setText(about_us_all);
                ownerId = allDetailsByHostelCode.getSid();
                ownerName = allDetailsByHostelCode.getHostel_owner_name();

            }

            @Override
            public void onFailure(Call<hostelInfoByHostelCodeResponse> call, Throwable t) {
                Toast.makeText(HostelDetail.this, "on details load onFailure.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: error: ");
            }
        });
    }
}