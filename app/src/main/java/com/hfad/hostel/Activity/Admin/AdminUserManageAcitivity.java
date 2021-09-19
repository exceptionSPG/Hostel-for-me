package com.hfad.hostel.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.hfad.hostel.Adapters.RecyclerAllUserAdminHomeAdapter;
import com.hfad.hostel.R;
import com.hfad.hostel.model.User;

import java.util.List;

public class AdminUserManageAcitivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    DrawerLayout myDrawer;
    List<User> userList;
    User user;
    RecyclerAllUserAdminHomeAdapter mAdapter;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_manage_acitivity);

    }
}