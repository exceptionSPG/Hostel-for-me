package com.hfad.hostel.Fragment.OwnerFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Adapters.RecyclerAllEnquiryAdminAdapter;
import com.hfad.hostel.Adapters.RecyclerPendingEnquiryOwnerAdapter;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.AdminAllEnquiryModel;
import com.hfad.hostel.model.EnquiryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OwnerEnquiryFragment extends Fragment {

    TextView tvNoDataFound;
    RecyclerView recyclerView;
    List<EnquiryModel> enquiryList;
    public SwipeRefreshLayout refreshLayout;
    RecyclerPendingEnquiryOwnerAdapter myAdapter;
    TextView tvOwnerEnquiryTotal, tvOwnerEnquiryPending, tvOwnerEnquiryReviewed;
    int sahu_id;

    public OwnerEnquiryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sahu_id = SharedPrefManager.getInstance(getContext()).getOwner().getSid();
        tvNoDataFound = view.findViewById(R.id.tv_no_data_found);
        tvOwnerEnquiryTotal = view.findViewById(R.id.tv_owner_enquiry_total);
        tvOwnerEnquiryPending = view.findViewById(R.id.tv_owner_recyerlermathi_enquiry_pending);
        tvOwnerEnquiryReviewed = view.findViewById(R.id.tv_owner_enquiry_reviewed);
        recyclerView = view.findViewById(R.id.recycler_view_owner_pending_enquiry);
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout_owner_enquiry);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPendingEnquiry();
            }
        });
        fetchPendingEnquiry();


        //tvNoDataFound.setVisibility(View.VISIBLE);


    }

    public void fetchPendingEnquiry(){
        refreshLayout.setRefreshing(true);
        //call api to get data
        Call<AdminAllEnquiryModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .getOwnerAllPendingEnquiry(sahu_id);

        call.enqueue(new Callback<AdminAllEnquiryModel>() {
            @Override
            public void onResponse(Call<AdminAllEnquiryModel> call, Response<AdminAllEnquiryModel> response) {
                if(response.body()!=null){
                    refreshLayout.setRefreshing(false);
                    if(response.body().isError()){
                        //error true hunu vaneko hami sang data xaina ho so data xaina vanne kura dekham
                        tvNoDataFound.setVisibility(View.VISIBLE);
                        updateTV(response.body().getTotal(),0,response.body().getReviewed());

                    }else {
                        enquiryList = response.body().getAllEnquiry();
                        updateTV(response.body().getTotal(),response.body().getPending(),response.body().getReviewed());
                        myAdapter = new RecyclerPendingEnquiryOwnerAdapter(getContext(),enquiryList);
                        recyclerView.setAdapter(myAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<AdminAllEnquiryModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Error occured."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateTV(int total, int pending, int reviewed){
        tvOwnerEnquiryTotal.setText(String.valueOf(total));
        tvOwnerEnquiryPending.setText(String.valueOf(pending));
        tvOwnerEnquiryReviewed.setText(String.valueOf(reviewed));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_enquiry, container, false);
    }
}