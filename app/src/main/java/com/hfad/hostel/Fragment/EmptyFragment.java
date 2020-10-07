package com.hfad.hostel.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hfad.hostel.R;

public class EmptyFragment extends Fragment {
    View view;
    TextView emptyTV;
    public EmptyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.no_internet,container,false);
        emptyTV = view.findViewById(R.id.emptyTv);
        emptyTV.setText(R.string.no_internet);
        return view;

    }
}
