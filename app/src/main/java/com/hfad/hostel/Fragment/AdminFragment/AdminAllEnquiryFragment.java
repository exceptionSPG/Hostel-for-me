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
import com.hfad.hostel.Adapters.RecyclerAllUserAdminHomeAdapter;
import com.hfad.hostel.Adapters.RecyclerHostelAdminHomeAdapter;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.model.AdminAllEnquiryModel;
import com.hfad.hostel.model.AllUserResponse;
import com.hfad.hostel.model.EnquiryModel;
import com.hfad.hostel.model.Owner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminAllEnquiryFragment extends Fragment {
    SearchView searchView;
    RecyclerView recyclerView;
    List<EnquiryModel> enquiryList;
    SwipeRefreshLayout refreshLayout;
    RecyclerAllEnquiryAdminAdapter myAdapter;
    TextView tvAdminEnquiryTotal, tvAdminEnquiryPending, tvAdminEnquiryReviewed;



    public AdminAllEnquiryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(), "Onview created.", Toast.LENGTH_SHORT).show();
        recyclerView = view.findViewById(R.id.recycler_view_admin_all_enquiry);
        searchView = view.findViewById(R.id.sv_search_admin_enquiry);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout_admin_enquiry);
        tvAdminEnquiryTotal = view.findViewById(R.id.tv_admin_enquiry_total);
        tvAdminEnquiryPending = view.findViewById(R.id.tv_admin_enquiry_pending);
        tvAdminEnquiryReviewed = view.findViewById(R.id.tv_admin_enquiry_reviewed);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllEnquiry();
            }
        });

        fetchAllEnquiry();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchAllEnquiry();
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });


    }

    private void fetchAllEnquiry(){
        refreshLayout.setRefreshing(true);
        Call<AdminAllEnquiryModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllEnquiry();

        call.enqueue(new Callback<AdminAllEnquiryModel>() {
            @Override
            public void onResponse(Call<AdminAllEnquiryModel> call, Response<AdminAllEnquiryModel> response) {
                if(!response.body().isError()){
                    refreshLayout.setRefreshing(false);
                    enquiryList = response.body().getAllEnquiry();
                    tvAdminEnquiryTotal.setText(String.valueOf(response.body().getTotal()));
                    tvAdminEnquiryPending.setText(String.valueOf(response.body().getPending()));
                    tvAdminEnquiryReviewed.setText(String.valueOf(response.body().getReviewed()));
                    Toast.makeText(getContext(), "Enquiry Fetching..."+enquiryList.size(), Toast.LENGTH_SHORT).show();
                    myAdapter = new RecyclerAllEnquiryAdminAdapter(enquiryList,getContext());

                    recyclerView.setAdapter(myAdapter);
                    Toast.makeText(getContext(), "Adapter Called..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminAllEnquiryModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Error occured.", Toast.LENGTH_SHORT).show();
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
        return inflater.inflate(R.layout.fragment_admin_all_enquiry, container, false);
    }
}