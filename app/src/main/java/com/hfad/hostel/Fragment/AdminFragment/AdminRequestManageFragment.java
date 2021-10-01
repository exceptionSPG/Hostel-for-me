package com.hfad.hostel.Fragment.AdminFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Adapters.RecyclerAllEnquiryAdminAdapter;
import com.hfad.hostel.Adapters.RecyclerAllRequestAdminAdapter;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.model.AdminAllEnquiryModel;
import com.hfad.hostel.model.AdminAllRequestModel;
import com.hfad.hostel.model.EnquiryModel;
import com.hfad.hostel.model.RequestModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRequestManageFragment extends Fragment {

    SearchView searchView;
    RecyclerView recyclerView;
    List<RequestModel> requestList;
    SwipeRefreshLayout refreshLayout;
    RecyclerAllRequestAdminAdapter myAdapter;
    TextView tvAdminRequestTotal, tvAdminRequestPending, tvAdminRequestApproved;


    public AdminRequestManageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_admin_all_request);
        searchView = view.findViewById(R.id.sv_search_admin_request);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout_admin_request);
        tvAdminRequestTotal = view.findViewById(R.id.tv_admin_request_total);
        tvAdminRequestPending = view.findViewById(R.id.tv_admin_request_pending);
        tvAdminRequestApproved = view.findViewById(R.id.tv_admin_request_approved);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllRequest();

            }
        });

        fetchAllRequest();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchAllRequest();
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void fetchAllRequest(){
        refreshLayout.setRefreshing(true);
        Call<AdminAllRequestModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllRequest();

        call.enqueue(new Callback<AdminAllRequestModel>() {
            @Override
            public void onResponse(Call<AdminAllRequestModel> call, Response<AdminAllRequestModel> response) {
                if (!response.body().isError()){
                    refreshLayout.setRefreshing(false);
                    requestList = response.body().getRequests();
                    tvAdminRequestTotal.setText(String.valueOf(response.body().getTotal()));
                    tvAdminRequestPending.setText(String.valueOf(response.body().getPending()));
                    tvAdminRequestApproved.setText(String.valueOf(response.body().getApproved()));

                    myAdapter = new RecyclerAllRequestAdminAdapter(getContext(),requestList);
                    recyclerView.setAdapter(myAdapter);


                }
            }

            @Override
            public void onFailure(Call<AdminAllRequestModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Error occured."+t.getMessage(), Toast.LENGTH_SHORT).show();

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
        return inflater.inflate(R.layout.fragment_admin_request_manage, container, false);
    }
}