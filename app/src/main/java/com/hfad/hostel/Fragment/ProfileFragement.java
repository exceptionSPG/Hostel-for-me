package com.hfad.hostel.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.hostel.Activity.LoginActivity;
import com.hfad.hostel.Activity.MainActivity;
import com.hfad.hostel.Activity.RegisterActivity;
import com.hfad.hostel.Activity.StudentFurtherDetails;
import com.hfad.hostel.Activity.UpdateUserData;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.User;
import com.hfad.hostel.model.UserInfo;
import com.hfad.hostel.model.userAllInfoByUid;
import com.hfad.hostel.model.userInfoResponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragement extends Fragment {

    View view;
    SharedPrefManager userSession;
    UserInfo info;
    DatePickerDialog dp;
    TextView tv_phone_number, tv_address, tv_dateOB, tv_guardian_name, tv_guardian_contact_number, tv_about_you, tv_user_name,tv_user_email;
    TextView tv_gender, tv_education;
    Button btn_edit_info;
    String user_phone_number, user_guardian_number,user_address, user_dob, user_guardian_name, user_about_you, user_gender, user_level, user_stream, user_education;
    int id;
    User loggedUser;
    userAllInfoByUid userAllInfo;

    public ProfileFragement() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userSession = new SharedPrefManager(getActivity());
        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        tv_user_email = (TextView) view.findViewById(R.id.tv_user_email);
        tv_phone_number = (TextView) view.findViewById(R.id.tv_user_phone_number);
        tv_dateOB = (TextView) view.findViewById(R.id.tv_user_dob);
        tv_address = (TextView) view.findViewById(R.id.tv_user_address);
        tv_gender = (TextView) view.findViewById(R.id.tv_gender);
        tv_guardian_name = (TextView) view.findViewById(R.id.tv_user_guardian_name);
        tv_guardian_contact_number = (TextView) view.findViewById(R.id.tv_guardian_contact_number);
        tv_education = (TextView) view.findViewById(R.id.tv_user_education);
        tv_about_you = (TextView) view.findViewById(R.id.tv_user_about_you);
        btn_edit_info = (Button) view.findViewById(R.id.btn_editInfo);

        btn_edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home,null);

                Toast.makeText(getActivity(), "Edit info clicked.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), UpdateUserData.class);
                //intent.putExtra("user_name", );
                getActivity().startActivity(intent);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!userSession.isUserLoggedIn()) {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if(!userSession.isUserInfoExists()) {
            loggedUser = userSession.getUser();
            id = loggedUser.getUser_id();
            //calendar_generate();
            callApi(id);
            //showAlert();

        }else {
            Toast.makeText(getActivity(), "Info exists in session.", Toast.LENGTH_SHORT).show();
            fillBySession();
        }

    }
    private void fillBySession(){
        tv_user_name.setText(userSession.getUserInfo().getUsername());
        tv_user_email.setText(userSession.getUserInfo().getEmail());
        //Long phn = userSession.getUserInfo().getUser_phone_number();
        //String fon = String.valueOf(phn);
        tv_phone_number.setText(userSession.getUserInfo().getUser_phone_number());
        tv_dateOB.setText(userSession.getUserInfo().getUser_DOB());
        tv_address.setText(userSession.getUserInfo().getUser_address());
        tv_gender.setText(userSession.getUserInfo().getUser_gender());
        tv_guardian_name.setText(userSession.getUserInfo().getUser_guardian_name());
        //Long gphn = userSession.getUserInfo().getUser_guardian_contact_number();
        tv_guardian_contact_number.setText(userSession.getUserInfo().getUser_guardian_contact_number());
        tv_education.setText(userSession.getUserInfo().getEducation());
        tv_about_you.setText(userSession.getUserInfo().getAbout_you());
    }
    private void showAlert(){
        //no data , data magne
        //Toast.makeText(getActivity(), "empty array : ", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Your further details please:")
                .setView(view);
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent it = new Intent(getActivity(), StudentFurtherDetails.class);
                getActivity().startActivity(it);
            }

        });
        //call api for insertion
        builder.setNegativeButton("Later", null)
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void fillData(){
        Toast.makeText(getActivity(), userSession.getUserInfo().getUser_address()+" Session::"+userSession.isUserInfoExists(), Toast.LENGTH_SHORT).show();
        tv_user_name.setText(userAllInfo.getUsername());
        tv_user_email.setText(userAllInfo.getEmail());
        //Long phn = userAllInfo.getUser_phone_number();
        //String fon = String.valueOf(phn);
        tv_phone_number.setText(userAllInfo.getUser_phone_number());
        tv_dateOB.setText(userAllInfo.getUser_DOB());
        tv_address.setText(userAllInfo.getUser_address());
        tv_gender.setText(userAllInfo.getUser_gender());
        tv_guardian_name.setText(userAllInfo.getUser_guardian_name());
        //Long gphn = userAllInfo.getUser_guardian_contact_number();
        tv_guardian_contact_number.setText(userAllInfo.getUser_guardian_contact_number());
        tv_education.setText(userAllInfo.getEducation());
        tv_about_you.setText(userAllInfo.getAbout_you());
    }

    private void callApi(int id) {

        Call<userInfoResponse> call = RetrofitClient.getInstance().getApi().getUserInfoByUid(id);
        call.enqueue(new Callback<userInfoResponse>() {
            @Override
            public void onResponse(Call<userInfoResponse> call, Response<userInfoResponse> response) {
                if (response.body() != null) {
                    userAllInfo = response.body().getUserAllInfo();
                    if (userAllInfo.getUsername() != null) {
                        //show data
                        SharedPrefManager.getInstance(getActivity()).saveUserInfo(userAllInfo);
                       fillData();
                        Toast.makeText(getActivity(), "Hello : " + userAllInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        //no data , data magne
                        showAlert();
                        Toast.makeText(getActivity(), "empty array : " + userAllInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(getActivity(), "Body null produced. : ", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<userInfoResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}