package com.hfad.hostel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.hfad.hostel.Activity.Admin.AdminUserManageAcitivity;
import com.hfad.hostel.Fragment.AdminFragment.AdminAllEnquiryFragment;
import com.hfad.hostel.Fragment.AdminFragment.AdminHomeFragment;
import com.hfad.hostel.Fragment.AdminFragment.AdminUserManageFragment;
import com.hfad.hostel.Fragment.AdminFragment.AllHostelOwnerFragment;
import com.hfad.hostel.Fragment.HomeFragment;
import com.hfad.hostel.Fragment.OwnerEnquiryFragment;
import com.hfad.hostel.Fragment.OwnerHomeFragment;
import com.hfad.hostel.Fragment.ProfileFragement;

import com.hfad.hostel.MapApi.AddLocationActivity;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.User;

import java.util.Objects;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    FrameLayout frag_container_admin;
    //String selectedFrag = "AdminHomeFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        frag_container_admin = findViewById(R.id.fragment_container_admin);

        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        navigationView.setCheckedItem(R.id.admin_nav_home);
        this.navigationView.inflateMenu(R.menu.drawer_admin_menu);

        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        openAdminLog();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(frag_container_admin.getId(), new AdminHomeFragment()).commit();

        }

    }

    //method for owner menu
    private void openAdminLog(){
        Objects.requireNonNull(getSupportActionBar()).setTitle("Hostel Finder");
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
            tvhostelName.setText(R.string.not_log_in);
            tvhostelEmail.setText(R.string.please_log_in);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.admin_nav_home:

                //getSupportActionBar().setTitle((CharSequence) "Hostel Finder");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new AdminHomeFragment()).commit();

                break;
            case R.id.admin_nav_AllUser:
//                startActivity(new Intent(AdminHomeActivity.this, AdminUserManageAcitivity.class));
                frag_container_admin.setBackgroundColor(Color.WHITE);
                Objects.requireNonNull(getSupportActionBar()).setTitle((CharSequence) "Manage User");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new AdminUserManageFragment()) .commit();
                break;
            case R.id.admin_nav_enquiry:
                //startActivity(new Intent(AdminHomeActivity.this, AddLocationActivity.class));
                Objects.requireNonNull(getSupportActionBar()).setTitle((CharSequence) "All Enquiry");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new AdminAllEnquiryFragment()).commit();
                break;

            case R.id.admin_nav_AllOwner:
                //startActivity(new Intent(AdminHomeActivity.this, AddLocationActivity.class));
                Objects.requireNonNull(getSupportActionBar()).setTitle((CharSequence) "Manage Owner");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new AllHostelOwnerFragment()).commit();
                break;
            case R.id.admin_nav_request:
                //startActivity(new Intent(AdminHomeActivity.this, AddLocationActivity.class));
                Objects.requireNonNull(getSupportActionBar()).setTitle((CharSequence) "Pending Requests");

                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new AdminAllEnquiryFragment()).commit();
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