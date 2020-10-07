package com.hfad.hostel.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.hostel.Activity.Owner_Login;
import com.hfad.hostel.Activity.RegisterActivity;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;


public class OwnerProfileFragment extends Fragment {

    SharedPrefManager userSession;

    public OwnerProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userSession = new SharedPrefManager(getActivity());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_profile, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        if(!userSession.isOwnerLoggedIn()){
            Intent intent = new Intent(getActivity(), Owner_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}