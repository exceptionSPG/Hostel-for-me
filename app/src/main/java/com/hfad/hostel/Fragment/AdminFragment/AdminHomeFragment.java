package com.hfad.hostel.Fragment.AdminFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Adapters.RecyclerAllUserAdminHomeAdapter;
import com.hfad.hostel.Adapters.RecyclerHostelAdminHomeAdapter;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.model.AdminDashboardResponse;
import com.hfad.hostel.model.AllUserResponse;
import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.OwnerInfoResponse;
import com.hfad.hostel.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeFragment extends Fragment {

    SearchView searchView;
    RecyclerView recyclerView;
    CardView cardTotalHostel, cardTotalUsers;
    List<Owner> ownerList;
    RecyclerHostelAdminHomeAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    TextView tv_total_user, tv_total_hostel, tv_pending_request, tv_total_enquiry;
    Context myContext;
    int total_hostels=0, total_users=0, total_enquiry=0, pending_request=0;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_total_hostel = (TextView) view.findViewById(R.id.tv_total_hostel);
        tv_total_user = (TextView) view.findViewById(R.id.tv_total_users);
        tv_pending_request = (TextView) view.findViewById(R.id.tv_pending_request);
        tv_total_enquiry = (TextView) view.findViewById(R.id.tv_new_enquiry);
        cardTotalHostel = (CardView) view.findViewById(R.id.total_hostel_card);
        cardTotalUsers = (CardView) view.findViewById(R.id.total_users_card);

        myContext = view.getContext();
        cardTotalHostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new AllHostelOwnerFragment()) .commit();

            }
        });

        cardTotalUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new AdminUserManageFragment()) .commit();

            }
        });

        //dummy initial values
        tv_total_hostel.setText("25");
        tv_total_user.setText("30");
        tv_pending_request.setText("7");
        tv_total_enquiry.setText("12");

        fetchAdminData();



        Log.d("on AdminHomeFragment:", "onViewCreated: total hostel: "+total_hostels);
        Log.i("on AdminHomeFragment", "onViewCreated: total users: "+total_users);


        //recyclerView = view.findViewById(R.id.recycler_view_admin_home);
//        searchView = view.findViewById(R.id.sv_search_admin_home);
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        //refreshLayout = view.findViewById(R.id.swipe_refresh_layout_admin_home);

//        StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(st);

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        //recyclerView.setLayoutManager(gridLayoutManager);


//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);


//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
//        recyclerView.setLayoutManager(layoutManager);
/*       refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAdminData();
            }
        }); */

    //        adapter = new RecyclerHostelAdapter(ownerList,getActivity());
//        recyclerView.setAdapter(adapter);
        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        }); */


    }

    private void updateUI(){
        tv_total_hostel.setText(String.valueOf(total_hostels));
        tv_total_user.setText(String.valueOf(total_users));
        tv_pending_request.setText(String.valueOf(pending_request));
        tv_total_enquiry.setText(String.valueOf(total_enquiry));
    }

    private void  fetchAdminData() {
        Call<AdminDashboardResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAdminData();
        call.enqueue(new Callback<AdminDashboardResponse>() {
            @Override
            public void onResponse(Call<AdminDashboardResponse> call, Response<AdminDashboardResponse> response) {
                if(response.body() == null){
                    Toast.makeText(myContext, "Error on getting admin Data.", Toast.LENGTH_SHORT).show();
                }else{
                    System.out.println("Hello: "+response.body().toString());

                    Toast.makeText(myContext, "On success else: ", Toast.LENGTH_SHORT).show();
                    total_hostels = response.body().getOwnersCount();
                    Toast.makeText(myContext, "On success else: "+total_hostels, Toast.LENGTH_SHORT).show();

                    total_users = response.body().getUsersCount();
                    total_enquiry = response.body().getEnquiryCount();
                    pending_request = response.body().getAllOwnerPendingRequest();
                    System.out.println(total_hostels);
                    Toast.makeText(myContext, "Hel"+response.body().toString(), Toast.LENGTH_SHORT).show();


                    updateUI();
                }


            }

            @Override
            public void onFailure(Call<AdminDashboardResponse> call, Throwable t) {
                //refreshLayout.setRefreshing(false);
            }
        });

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
                adapter = new RecyclerHostelAdminHomeAdapter(ownerList,getActivity());
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
        //return inflater.inflate(R.layout.fragment_admin_home, container, false);
        return inflater.inflate(R.layout.admin_home_dashboard_card,container,false);
    }
}