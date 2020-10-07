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

import com.hfad.hostel.Adapters.RecyclerHostelAdminHomeAdapter;
import com.hfad.hostel.R;
import com.hfad.hostel.model.Owner;

import java.util.List;


public class AdminAllEnquiryFragment extends Fragment {
    SearchView searchView;
    RecyclerView recyclerView;
    List<Owner> ownerList;
    RecyclerHostelAdminHomeAdapter adapter;
    SwipeRefreshLayout refreshLayout;


    public AdminAllEnquiryFragment() {
        // Required empty public constructor
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