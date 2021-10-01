package com.hfad.hostel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.model.DefaultResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostelRequestActivity extends AppCompatActivity {

    //hostel_name, hostel_location,-->
    //<!--    hostel_type, owner_name, contact_number, hostel_email
    EditText etHostelName, etEid,etHostelAddress, etHostelType,etOwnerName,etContactNumber,etHostelEmail;
    String hostelName, hostelAddress, hostelType, ownerName, contactNumber,hostelEmail;

    TextView tvSendRequest;
    View myview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_request);
        myview = getCurrentFocus();

        etHostelName = findViewById(R.id.et_hostel_request_hostel_name);
        etHostelAddress = findViewById(R.id.et_hostel_request_hostel_address);
        etHostelType = findViewById(R.id.et_hostel_request_hostel_type);
        etOwnerName = findViewById(R.id.et_hostel_request_owner_name);
        etContactNumber = findViewById(R.id.et_hostel_request_contact_number);
        etHostelEmail = findViewById(R.id.et_hostel_request_hostel_email);
        tvSendRequest = findViewById(R.id.tv_send_request);

        tvSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostelName = etHostelName.getText().toString().trim();
                hostelAddress = etHostelAddress.getText().toString().trim();
                hostelType = etHostelType.getText().toString().trim();
                hostelEmail = etHostelEmail.getText().toString().trim();
                ownerName = etOwnerName.getText().toString().trim();
                contactNumber = etContactNumber.getText().toString().trim();
                if(hostelName.isEmpty()){
                    etHostelName.setError("Please enter hostel name: ");
                    etHostelName.requestFocus();
                }else if(hostelAddress.isEmpty()){
                    etHostelAddress.setError("Please enter hostel name: ");
                    etHostelAddress.requestFocus();
                }else if(hostelType.isEmpty()){
                    etHostelType.setError("Please enter hostel name: ");
                    etHostelType.requestFocus();
                }else if(hostelEmail.isEmpty()){
                    etHostelEmail.setError("Please enter hostel name: ");
                    etHostelEmail.requestFocus();
                }else if(ownerName.isEmpty()){
                    etOwnerName.setError("Please enter hostel name: ");
                    etOwnerName.requestFocus();
                }else if(contactNumber.isEmpty()){
                    etContactNumber.setError("Please enter hostel name: ");
                    etContactNumber.requestFocus();
                }else {
                    //Snackbar.make(v,"All set ready to go.", Snackbar.LENGTH_LONG).show();
                    Call<DefaultResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .insertRequest(hostelName,hostelAddress,hostelType,ownerName,contactNumber,hostelEmail);

                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                            assert response.body() != null;
                            if(response.body().isErr()){
                                Toast.makeText(HostelRequestActivity.this, "Success response: "+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                Snackbar.make(v,"Success: "+response.body().getMsg(),Snackbar.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(HostelRequestActivity.this, "Success: "+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                Snackbar.make(v,"Success: "+response.body().getMsg(),Snackbar.LENGTH_LONG).show();
                                lastID();
                            }


                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            Snackbar.make(v,"Success: ",Snackbar.LENGTH_LONG).show();
                            lastID();
                        }
                    });
                }
            }
        });



    }

    private void lastID(){
        Call<DefaultResponse> req_id = RetrofitClient
                .getInstance()
                .getApi()
                .getLastInsertetId();

        req_id.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                String id = response.body().getMsg();
                showAlert("Your Token: "+id,"Keep your token number to check for request approval.");
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(HostelRequestActivity.this, "Failed response: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //we inflate menu to our layout
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.request_approval_check,menu);
        Log.d("Hostel Request Activity", "onCreateOptionsMenu: Menu inflated.");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.req_check_approval:
                Toast.makeText(this, "Checking result..", Toast.LENGTH_SHORT).show();
                customDialog(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void customDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the
        // dialog layout
        builder.setTitle("Your Ruquest Status:");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_email);
        View view = inflater.inflate(R.layout.request_result_dialog_layout, null);
        builder.setView(view);

        etEid = view.findViewById(R.id.et_request_result_dialog_check_eid);
        Button btnCheck = view.findViewById(R.id.btn_request_result_check);
        btnCheck.setVisibility(View.INVISIBLE);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hrids = etEid.getText().toString().trim();
                
                if(hrids.isEmpty()){
                    etEid.setError("Enter your token.");
                    etEid.requestFocus();
                }else{
                    Snackbar.make(v,"Checking..",Snackbar.LENGTH_SHORT);
                    int hrid = Integer.parseInt(hrids);
                    Call<DefaultResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getRequestResultByEid(hrid);
                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            //Snackbar.make(v,"Status: "+response.body().getMsg(),Snackbar.LENGTH_SHORT).show();
                            String msg = response.body().getMsg();
                            if(msg.equals("Pending")){
                                Toast.makeText(HostelRequestActivity.this, "Pending. hai", Toast.LENGTH_SHORT).show();
                                Snackbar.make(v,"Your request is still Pending.\nCheck after 2-3 days.",Snackbar.LENGTH_SHORT).show();
                            }else if(response.body().getMsg().equalsIgnoreCase("approved")){
                                Toast.makeText(HostelRequestActivity.this, "Approved.", Toast.LENGTH_LONG).show();
                                Snackbar.make(v,"Your request is approved.\nPlease, contact admin for credentials.",Snackbar.LENGTH_SHORT).show();



                            }else if(response.body().getMsg().equalsIgnoreCase("rejected")){
                                Toast.makeText(HostelRequestActivity.this, "Approved.", Toast.LENGTH_SHORT).show();
                                Snackbar.make(v,"Your request is rejected.",Snackbar.LENGTH_LONG).show();

                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            Toast.makeText(HostelRequestActivity.this, "Error:."+ t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });


        // Add action buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //send data
                String hrids = etEid.getText().toString().trim();

                if(hrids.isEmpty()){
                    etEid.setError("Enter your token.");
                    etEid.requestFocus();
                }else{
                    Snackbar.make(view,"Checking..",Snackbar.LENGTH_SHORT).show();
                    int hrid = Integer.parseInt(hrids);
                    Call<DefaultResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getRequestResultByEid(hrid);
                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            //Snackbar.make(v,"Status: "+response.body().getMsg(),Snackbar.LENGTH_SHORT).show();
                            String msg = response.body().getMsg();
                            if(msg.equals("Pending")){
                                Toast.makeText(HostelRequestActivity.this, "Pending. hai", Toast.LENGTH_SHORT).show();
                                showAlert(msg,"Your Request is still on Pending.\nPlease, check after some time.");

                            }else if(response.body().getMsg().equalsIgnoreCase("approved")){
                                Toast.makeText(HostelRequestActivity.this, "Approved.", Toast.LENGTH_LONG).show();
                                showAlert(msg,"Your Request is approved.\nPlease, contact admin for credentials.");



                            }else if(response.body().getMsg().equalsIgnoreCase("rejected")){
                                Toast.makeText(HostelRequestActivity.this, "Approved.", Toast.LENGTH_SHORT).show();
                                showAlert(msg,"Your Request is Rejected.\n");

                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            Toast.makeText(HostelRequestActivity.this, "Error:."+ t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }

    private void showAlert(String status, String msg){
        //no data , data magne
        //Toast.makeText(getActivity(), "empty array : ", Toast.LENGTH_SHORT).show();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HostelRequestActivity.this)
                .setTitle(status)
                .setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }

        });
        //call api for insertion
        builder.setCancelable(false);
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

}