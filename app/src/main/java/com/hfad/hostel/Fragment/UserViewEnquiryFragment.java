package com.hfad.hostel.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Activity.RegisterActivity;
import com.hfad.hostel.Activity.User.UserAcceptedEnquiryActivity;
import com.hfad.hostel.Activity.User.UserPendingEnquiryActivity;
import com.hfad.hostel.Activity.User.UserRejectedEnquiryActivity;
import com.hfad.hostel.Activity.User.UserRequestedEnquiryActivity;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.User;

import org.jetbrains.annotations.NotNull;


public class UserViewEnquiryFragment extends Fragment {

    TextView tvRequestedEnquiry, tvPendingEnquiry,tvAcceptedEnquiry,tvRejectedEnquiry;
    SharedPrefManager userSession;
    int id;
    User loggedUser;

    public UserViewEnquiryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onStart() {
        super.onStart();
        if (!userSession.isUserLoggedIn()) {
            Toast.makeText(getContext(), "You must logged in to see enquiry.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if(!userSession.isUserInfoExists()) {
            loggedUser = userSession.getUser();
            id = loggedUser.getUser_id();

            //calendar_generate();


        }else {
           // Toast.makeText(getActivity(), "Info exists in session.", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userSession = new SharedPrefManager(getActivity());
        tvRequestedEnquiry = view.findViewById(R.id.tv_requested_enquiry);
        tvRejectedEnquiry = view.findViewById(R.id.tv_rejected_enquiry);
        tvPendingEnquiry = view.findViewById(R.id.tv_pending_enquiry);
        tvAcceptedEnquiry = view.findViewById(R.id.tv_accepted_enquiry);


        tvAcceptedEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserAcceptedEnquiryActivity.class));
            }
        });
        tvPendingEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserPendingEnquiryActivity.class));
            }
        });
        tvRejectedEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserRejectedEnquiryActivity.class));
            }
        });
        tvRequestedEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserRequestedEnquiryActivity.class));
            }
        });



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_view_enquiry, container, false);
    }
}