package com.hfad.hostel.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import com.hfad.hostel.Adapters.RecyclerHostelAdapter;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.Helper.Utilities;
import com.hfad.hostel.R;
import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.OwnerInfoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    SearchView searchView;
    RecyclerView recyclerView;
    List<Owner> ownerList;
    RecyclerHostelAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        recyclerView = view.findViewById(R.id.recycler_view_user_home);
        searchView = view.findViewById(R.id.sv_search);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout);


//        StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(st);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
//        recyclerView.setLayoutManager(gridLayoutManager);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
//        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchHostel();
            }
        });

        fetchHostel();

//        adapter = new RecyclerHostelAdapter(ownerList,getActivity());
//        recyclerView.setAdapter(adapter);
        if(adapter!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }


    }

    private void fetchHostel(){
        refreshLayout.setRefreshing(true);
        Call<OwnerInfoResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllOwnerInfo();

        call.enqueue(new Callback<OwnerInfoResponse>() {
            @Override
            public void onResponse(Call<OwnerInfoResponse> call, Response<OwnerInfoResponse> response) {
                refreshLayout.setRefreshing(false);
                ownerList = response.body().getOwner();
                adapter = new RecyclerHostelAdapter(ownerList,getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<OwnerInfoResponse> call, Throwable t) {
                refreshLayout.setRefreshing(false);
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
