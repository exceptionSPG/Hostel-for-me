package com.hfad.hostel.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hfad.hostel.Activity.HostelRequestActivity;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.model.DefaultResponse;
import com.hfad.hostel.model.EnquiryModel;
import com.hfad.hostel.model.RequestModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerAllRequestAdminAdapter extends RecyclerView.Adapter<RecyclerAllRequestAdminAdapter.viewHolder> implements Filterable {

    Context mCtx;
    List<RequestModel> requestModels;
    List<RequestModel> allRequests;
    RequestModel myRequest;

    public RecyclerAllRequestAdminAdapter(Context mCtx, List<RequestModel> requestModels) {
        this.mCtx = mCtx;
        this.requestModels = requestModels;
        allRequests = new ArrayList<>(requestModels);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.admin_request_card,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        myRequest = requestModels.get(position);

        holder.tvRequestCardHostelName.setText(myRequest.getHostel_name());
        holder.tvRequestCardOwnerName.setText(myRequest.getHostel_owner_name());
        holder.tvRequestCardRequestStatus.setText(myRequest.getStatus());
        holder.tvRequestCardRequestDate.setText(myRequest.getRequest_date());
        if(myRequest.getStatus().equals("Pending")){
            int color = mCtx.getResources().getColor(R.color.request_pending);
            holder.requestCardview.setCardBackgroundColor(color);
        }else {
            int color = mCtx.getResources().getColor(R.color.request_approved);
            holder.requestCardview.setCardBackgroundColor(color);
        }

        holder.requestCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Card clicked for request details of : "+myRequest.getHrid(), Toast.LENGTH_SHORT).show();
                fillDetails(mCtx,myRequest.getHrid());
            }
        });
    }

    private void showAlertDetails(int req_id) {


    }

    public void fillDetails(Context context, int hrid){
        //get reference to all the widgets of layout
        TextView tvHostelName,tvHostelAddress,tvHostelOwnerName;
        TextView tvContactNumber,tvHostelType,tvHostelEmail;
        EditText etHostelCode;


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        //LayoutInflater inflater = getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the
        // dialog layout
        builder.setTitle("Your Request Status:");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_email);
        View view = inflater.inflate(R.layout.admin_request_details_layout, null);
        builder.setView(view);

        tvHostelName = view.findViewById(R.id.tv_admin_hostel_request_details_hostel_name);
        tvHostelAddress = view.findViewById(R.id.tv_admin_hostel_request_details_hostel_address);
        tvHostelType = view.findViewById(R.id.tv_admin_hostel_request_details_hostel_type);
        tvHostelEmail = view.findViewById(R.id.tv_admin_hostel_request_details_hostel_email);
        tvContactNumber = view.findViewById(R.id.tv_admin_hostel_request_details_contact_number);
        tvHostelOwnerName = view.findViewById(R.id.tv_admin_hostel_request_details_hostel_name);
        etHostelCode = view.findViewById(R.id.et_admin_request_details_hostel_code);

        tvHostelName.setText(myRequest.getHostel_name());
        tvHostelAddress.setText(myRequest.getHostel_location());
        tvContactNumber.setText(myRequest.getContact_number());
        tvHostelType.setText(myRequest.getHostel_type());
        tvHostelEmail.setText(myRequest.getHostel_email());
        tvHostelOwnerName.setText(myRequest.getHostel_name());




        if(myRequest.getStatus().equals("Pending")){

            etHostelCode.setVisibility(View.VISIBLE);
            String hCode = etHostelCode.getText().toString().trim();
            // Add action buttons
            builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //send data
//                    etHostelCode.setVisibility(View.VISIBLE);
//                    String hCode = etHostelCode.getText().toString().trim();
                    int hrids = myRequest.getHrid();
                    Call<DefaultResponse> callOwner = RetrofitClient
                            .getInstance()
                            .getApi()
                            .createOwner(myRequest.getHostel_owner_name(),myRequest.getHostel_name(),
                                    myRequest.getHostel_location(),myRequest.getHostel_type(),myRequest.getHostel_email()
                                    ,hCode,hCode);

                    callOwner.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            //we successfully inserted new owner
                            //now we have to update status on request
                            if(response.body() !=null){
                                Toast.makeText(context, "Body null: ", Toast.LENGTH_SHORT).show();
                            }else {
                                if(response.body().isErr()){
                                    Toast.makeText(context, "Error on approving: "+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }else{
                                    Call<DefaultResponse> approver = RetrofitClient
                                            .getInstance()
                                            .getApi()
                                            .approveHostelRequestStatus(hrids);
                                    approver.enqueue(new Callback<DefaultResponse>() {
                                        @Override
                                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                            if(response.body().isErr()){
                                                Toast.makeText(mCtx.getApplicationContext(), "Error: ."+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                                            }else {
                                                Toast.makeText(context, "Successfully approved.", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                            Toast.makeText(context, "Failure Approval Response: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            Toast.makeText(context, "Failure Insertion Owner Response: "+t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    }

            });

        }else if(myRequest.getStatus().equals("Approved")){
            // Add action buttons
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //send data

                    dialog.dismiss();

                }
            });



        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    @Override
    public Filter getFilter() {
        return requestFilter;
    }

    Filter requestFilter =  new Filter() {
        //this method runs on background thread
        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            List<RequestModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                //show all data
                filteredList.addAll(allRequests);
            }else {
                //filter using keys
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(RequestModel requestModel : allRequests){
                    if(requestModel.getHostel_name().toLowerCase().contains(filterPattern) ||  requestModel.getHostel_owner_name().toLowerCase().contains(filterPattern)|| requestModel.getStatus().contains(filterPattern)){
                        filteredList.add(requestModel); //store those enquiry whose hostel name contains list as asked.
                    }
                }
            }

            Filter.FilterResults filterResults = new Filter.FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        //this method runs on ui thread
        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {

            requestModels.clear();
            requestModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tvRequestCardHostelName, tvRequestCardOwnerName,tvRequestCardRequestStatus;
        TextView tvRequestCardRequestDate;
        CardView requestCardview;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvRequestCardHostelName= itemView.findViewById(R.id.tv_request_card_hostel_name);
            tvRequestCardOwnerName = itemView.findViewById(R.id.tv_request_card_owner_name);
            tvRequestCardRequestStatus = itemView.findViewById(R.id.tv_request_card_status);
            requestCardview = itemView.findViewById(R.id.admin_request_cardview);
            tvRequestCardRequestDate = itemView.findViewById(R.id.tv_request_card_request_date);

        }
    }
}
