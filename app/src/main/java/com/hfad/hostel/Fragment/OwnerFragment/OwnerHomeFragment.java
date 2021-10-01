package com.hfad.hostel.Fragment.OwnerFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.Owner;

import org.w3c.dom.Text;

import java.util.Locale;

public class OwnerHomeFragment extends Fragment {
    TextView tvOwnerDashboard;
    Owner owner;
    TextView tvHostelCode;


    public OwnerHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvOwnerDashboard = view.findViewById(R.id.textGrid);
        tvHostelCode = view.findViewById(R.id.tv_owner_hostel_code);
        tvOwnerDashboard.setText(owner.getHostel_code().toUpperCase(Locale.ROOT)+"  Dashboard");
        tvHostelCode.setText(owner.getHostel_code());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        owner = SharedPrefManager.getInstance(getContext()).getOwner();
        Toast.makeText(getContext(), "Owner logged in : "+owner.getHostel_name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_home, container, false);
    }
}