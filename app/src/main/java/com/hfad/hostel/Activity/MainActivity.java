package com.hfad.hostel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.hfad.hostel.Fragment.EmptyFragment;
import com.hfad.hostel.Fragment.Hostel_Near_MeFragment;
import com.hfad.hostel.Fragment.OwnerEnquiryFragment;
import com.hfad.hostel.Fragment.OwnerHomeFragment;
import com.hfad.hostel.Fragment.HomeFragment;
import com.hfad.hostel.Helper.Utilities;
import com.hfad.hostel.Fragment.ProfileFragement;
import com.hfad.hostel.MapApi.AddLocationActivity;
import com.hfad.hostel.MapApi.MapsActivity;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.User;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int INTERNET_REQUEST_CODE = 1;
    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    CircleImageView icon;
    User user;
    Owner owner;
    TextView emptyTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (!Utilities.isConnectionAvailable(this)) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EmptyFragment()).commit();
//        }
        if(SharedPrefManager.getInstance(this).isUserLoggedIn()){
            if(SharedPrefManager.getInstance(this).getKey().equals("Admin")){
                startActivity(new Intent(this,AdminHomeActivity.class));
                Toast.makeText(this, "Key: "+SharedPrefManager.getInstance(this).isAdminLoggedIn(), Toast.LENGTH_SHORT).show();
            }

        }
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        init();
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        }
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET},INTERNET_REQUEST_CODE);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (!Utilities.isConnectionAvailable(this)) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EmptyFragment()).commit();
//        }else{
            if(SharedPrefManager.getInstance(this).isOwnerLoggedIn()){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OwnerHomeFragment()).commit();
            }
    }

    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hostel Finder");
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
       if(SharedPrefManager.getInstance(this).isUserLoggedIn()){
           if(SharedPrefManager.getInstance(this).getKey().equals("Admin")){
              startActivity(new Intent(this,AdminHomeActivity.class));
               Toast.makeText(this, "Key: "+SharedPrefManager.getInstance(this).isAdminLoggedIn(), Toast.LENGTH_SHORT).show();
           }else {
               Toast.makeText(this, "Key: "+SharedPrefManager.getInstance(this).isAdminLoggedIn(), Toast.LENGTH_SHORT).show();
               navigationView.setCheckedItem(R.id.nav_home);
               openUserLogActivity();
           }

        }else if(SharedPrefManager.getInstance(this).isOwnerLoggedIn()){
            openOwnerLog();
        }else{
            navigationView.setCheckedItem(R.id.nav_home);
            openUserLogActivity();
        }
    }

    //method for inflating user menu when user login is done.
    private void openUserLogActivity(){

        this.navigationView.inflateMenu(R.menu.drawer_user_menu);
        MenuItem findlogoutItem = this.navigationView.getMenu().findItem(R.id.nav_Login);
        View inflateHeaderView = this.navigationView.inflateHeaderView(R.layout.nav_header);
        TextView tvUserName = (TextView) inflateHeaderView.findViewById(R.id.tv_name);
        TextView tvUserEmail = (TextView) inflateHeaderView.findViewById(R.id.tv_email);
        //this.icon = (CircleImageView) inflateHeaderView.findViewById(R.id.profile_image);
        if(SharedPrefManager.getInstance(this).isUserLoggedIn()){
            //user has been logged in.

            findlogoutItem.setTitle("Log Out");
            user = SharedPrefManager.getInstance(this).getUser();
            tvUserName.setText(this.user.getUsername());
            tvUserEmail.setText(this.user.getEmail());


        }else {
            //no login
            findlogoutItem.setIcon(getResources().getDrawable(R.drawable.ic_login,null));
            findlogoutItem.setTitle("Log In");
            tvUserName.setText("Not Logged In");
            tvUserEmail.setText("Please Log In");
        }
    }

    //method for owner menu
    private void openOwnerLog(){
        navigationView.setCheckedItem(R.id.owner_nav_home);
        this.navigationView.inflateMenu(R.menu.drawer_owner_menu);
        MenuItem findlogoutItem = this.navigationView.getMenu().findItem(R.id.owner_nav_Login);
        View inflateHeaderView = this.navigationView.inflateHeaderView(R.layout.owner_nav_header);
        TextView tvhostelName = (TextView) inflateHeaderView.findViewById(R.id.tv_hostel_name);
        TextView tvhostelEmail = (TextView) inflateHeaderView.findViewById(R.id.tv_hostel_email);
        //this.icon = (CircleImageView) inflateHeaderView.findViewById(R.id.profile_image);
        if(SharedPrefManager.getInstance(this).isOwnerLoggedIn()){
            //owner has been logged in.

            findlogoutItem.setTitle("Log Out");
            owner = SharedPrefManager.getInstance(this).getOwner();
            tvhostelName.setText(this.owner.getHostel_name());
            tvhostelEmail.setText(this.owner.getHostel_email());



        }else {
            //no login
            findlogoutItem.setIcon(getResources().getDrawable(R.drawable.ic_login,null));
            findlogoutItem.setTitle("Log In");
            tvhostelName.setText("Not Logged In");
            tvhostelEmail.setText("Please Log In");
        }
    }

    //method for owner menu
    private void openAdminLog(){
        navigationView.setCheckedItem(R.id.admin_nav_home);
        this.navigationView.inflateMenu(R.menu.drawer_admin_menu);
        MenuItem findlogoutItem = this.navigationView.getMenu().findItem(R.id.admin_nav_Login);
        View inflateHeaderView = this.navigationView.inflateHeaderView(R.layout.owner_nav_header);
        TextView tvhostelName = (TextView) inflateHeaderView.findViewById(R.id.tv_hostel_name);
        TextView tvhostelEmail = (TextView) inflateHeaderView.findViewById(R.id.tv_hostel_email);
        //this.icon = (CircleImageView) inflateHeaderView.findViewById(R.id.profile_image);
        if(SharedPrefManager.getInstance(this).isAdminLoggedIn()){
            //owner has been logged in.

            findlogoutItem.setTitle("Log Out");
            User admin = SharedPrefManager.getInstance(this).getUser();
            tvhostelName.setText(admin.getUsername());
            tvhostelEmail.setText(admin.getEmail());



        }else {
            //no login
            findlogoutItem.setIcon(getResources().getDrawable(R.drawable.ic_login,null));
            findlogoutItem.setTitle("Log In");
            tvhostelName.setText("Not Logged In");
            tvhostelEmail.setText("Please Log In");
        }
    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Do you want to exit?")
                    .setMessage("Come soon.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }

            });
            //call api for insertion
            builder.setNegativeButton("Later", null)
                    .setCancelable(false);
            AlertDialog alert = builder.create();
            alert.show();

        }

    }

    private void showAlert(){
        //no data , data magne
        //Toast.makeText(getActivity(), "empty array : ", Toast.LENGTH_SHORT).show();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this)
                .setTitle("Do you want to exit?")
                .setMessage("Come soon.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }

        });
        //call api for insertion
        builder.setNegativeButton("No", null)
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportActionBar().setTitle((CharSequence) "Hostel Finder");
//                if (!Utilities.isConnectionAvailable(this)) {
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EmptyFragment()).commit();
//                  }else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                //}
                break;
            case R.id.nav_profile:
                getSupportActionBar().setTitle((CharSequence) "Profile");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragement()).commit();
                break;
            case R.id.nav_location:
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                //getSupportActionBar().setTitle((CharSequence) "Hostel Near Me");
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Hostel_Near_MeFragment()).commit();
                break;
            case R.id.nav_Login:
                if(SharedPrefManager.getInstance(this).isUserLoggedIn() || SharedPrefManager.getInstance(this).isOwnerLoggedIn()) {
                    SharedPrefManager.getInstance(this).clear();
                    Intent intent = new Intent(this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(this,LoginActivity.class));
                }
                break;

            case R.id.nav_share:
                Intent intent = new Intent(MainActivity.this, AddLocationActivity.class);
//                intent.setAction(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, "Sharing App");
                startActivity(intent);
                Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_exit:
                Toast.makeText(this, "Exit clicked", Toast.LENGTH_SHORT).show();
                showAlert();
                break;
            case R.id.owner_nav_home:
                getSupportActionBar().setTitle((CharSequence) "Hostel Finder");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OwnerHomeFragment()).commit();
                break;
            case R.id.owner_nav_profile:
                getSupportActionBar().setTitle((CharSequence) "Profile");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OwnerHomeFragment()).commit();
                break;
            case R.id.nav_enquiry:
                getSupportActionBar().setTitle((CharSequence) "Enquiry");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OwnerEnquiryFragment()).commit();
                break;
            case R.id.owner_nav_Login:
                if(SharedPrefManager.getInstance(this).isOwnerLoggedIn()) {
                    SharedPrefManager.getInstance(this).clear();
                    Intent it = new Intent(this,Owner_Login.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(it);
                } else {
                    startActivity(new Intent(this,LoginActivity.class));
                }
                break;
            case R.id.admin_nav_home:
                //getSupportActionBar().setTitle((CharSequence) "Hostel Finder");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OwnerHomeFragment()).commit();
                break;
            case R.id.admin_nav_AllUser:
                getSupportActionBar().setTitle((CharSequence) "Manage User");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OwnerHomeFragment()).commit();
                break;
            case R.id.admin_nav_enquiry:
                getSupportActionBar().setTitle((CharSequence) "All Enquiry");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OwnerEnquiryFragment()).commit();
                break;
            case R.id.admin_nav_Login:
                if(SharedPrefManager.getInstance(this).isUserLoggedIn()) {
                    SharedPrefManager.getInstance(this).clear();
                    Intent it = new Intent(this,Owner_Login.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(it);
                } else {
                    startActivity(new Intent(this,LoginActivity.class));
                }
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}