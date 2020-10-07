package com.hfad.hostel.Fragment.AdminFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.hfad.hostel.Adapters.RecyclerAllUserAdminHomeAdapter;
import com.hfad.hostel.Adapters.RecyclerHostelAdminHomeAdapter;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.model.AllUserResponse;
import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.OwnerInfoResponse;
import com.hfad.hostel.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminUserManageFragment extends Fragment {

    SearchView searchView;
    RecyclerView recyclerView;
    List<User> userList;
    User user;
    RecyclerAllUserAdminHomeAdapter mAdapter;
    SwipeRefreshLayout refreshLayout;
    public AdminUserManageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(), "Onview created.", Toast.LENGTH_SHORT).show();
        recyclerView = view.findViewById(R.id.recycler_view_admin_all_user);
        searchView = view.findViewById(R.id.sv_search_admin_manage_user);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout_user_all);

//        StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(st);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);


//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
//        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllUser();
            }
        });

        fetchAllUser();

//        adapter = new RecyclerHostelAdapter(ownerList,getActivity());
//        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });


    }
    private void fetchAllUser(){
        refreshLayout.setRefreshing(true);
        Call<AllUserResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllUser();

        call.enqueue(new Callback<AllUserResponse>() {
            @Override
            public void onResponse(Call<AllUserResponse> call, Response<AllUserResponse> response) {
                if(!response.body().isError()){
                    refreshLayout.setRefreshing(false);
                   userList = response.body().getUserList();
                    Toast.makeText(getActivity(), "Data fetching..All user ", Toast.LENGTH_SHORT).show();
                    mAdapter = new RecyclerAllUserAdminHomeAdapter(userList,getActivity());
                    Toast.makeText(getActivity(), "Adapter called.", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(mAdapter);
                    //recyclerView.setAdapter(new RecyclerAllUserAdminHomeAdapter(userList,getActivity()));
                }else {
                    Toast.makeText(getActivity(), "Error occured.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllUserResponse> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Failure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Toast.makeText(getActivity(), "on create view.", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_admin_user_manage, container, false);
    }

}