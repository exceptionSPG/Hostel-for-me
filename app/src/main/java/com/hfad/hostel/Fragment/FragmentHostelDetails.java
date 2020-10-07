package com.hfad.hostel.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hfad.hostel.Adapters.ViewPagerAdapter;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.OwnerAllDetailsByHostelCode;
import com.hfad.hostel.model.hostelInfoByHostelCodeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHostelDetails extends Fragment {

    View view;
    TextView type, phone, email, about;
    String ht_type, ht_email, ht_phone, ht_about, code;
    ViewPagerAdapter viewPagerAdapter;

    public FragmentHostelDetails() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = view.findViewById(R.id.tv_type);
        phone = view.findViewById(R.id.tv_phone);
        email = view.findViewById(R.id.tv_email);
        about = view.findViewById(R.id.tv_about);
        code = SharedPrefManager.getInstance(getActivity()).getCode();

        fillDetails();
    }

    private void fillDetails() {
        if (code != null) {
//            SharedPrefManager.getInstance(getActivity()).resetCode();
            Call<hostelInfoByHostelCodeResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getHostelInfoByHostelCode(code);

            call.enqueue(new Callback<hostelInfoByHostelCodeResponse>() {
                @Override
                public void onResponse(Call<hostelInfoByHostelCodeResponse> call, Response<hostelInfoByHostelCodeResponse> response) {
                    if (!response.body().isError()) {
                        Toast.makeText(getActivity(), "Hostel info fetching.", Toast.LENGTH_SHORT).show();
                        OwnerAllDetailsByHostelCode ht = response.body().getOwnerAllDetailsByHostelCode();
                        type.setText(ht.getHostel_type());
                        phone.setText(ht.getContact_number());
                        email.setText(ht.getHostel_email());
                        String tex = ht.getFacility();
                        String text = tex.concat(". " + ht.getPricing());
                        about.setText(text);
                    } else {
                        Toast.makeText(getActivity(), "Error: " + response.body().getOwnerAllDetailsByHostelCode().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<hostelInfoByHostelCodeResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getActivity(), "Null code. Try again.", Toast.LENGTH_SHORT).show();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.hostel_details_fragment, container, false);

        return view;

    }
}
