package com.hfad.hostel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.model.DefaultResponse;
import com.hfad.hostel.model.EnquiryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerPendingEnquiryOwnerAdapter extends RecyclerView.Adapter<RecyclerPendingEnquiryOwnerAdapter.viewHolder>{

    Context mCtx;
    List<EnquiryModel> enquiryList;
    EnquiryModel enquiry;

    public RecyclerPendingEnquiryOwnerAdapter(Context mCtx, List<EnquiryModel> enquiryList) {
        this.mCtx = mCtx;
        this.enquiryList = enquiryList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.owner_pending_enquiry_card,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        enquiry = enquiryList.get(position);
        holder.tvOwnerEnquiryCardUserName.setText(enquiry.getUser_name());
        holder.tvOwnerEnquiryCardUserEmail.setText(enquiry.getUser_email());
        holder.tvOwnerEnquiryCardPhone.setText(enquiry.getUser_phone());
        holder.tvOwnerEnquiryCardEnquiryStatus.setText(enquiry.getEnquiry_status());
        holder.tvOwnerEnquiryCardEnquiryDate.setText(enquiry.getEnquiry_date());
        holder.tvOwnerEnquiryCardEnquiryMessage.setText(enquiry.getEnquiry_message());
        holder.cvShowUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Showing details of user: "+enquiry.getUserid()+" name: "+enquiry.getUser_name(), Toast.LENGTH_SHORT).show();

            }
        });
        
        holder.btnOwnerEnquiryApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx, "Proceed for approve.", Toast.LENGTH_SHORT).show();
                Call<DefaultResponse> approveCall = RetrofitClient
                        .getInstance()
                        .getApi()
                        .approveEnquiry(enquiry.getEid());
                approveCall.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if(response.body().isErr()){
                            Toast.makeText(mCtx, "Error caught: "+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(mCtx, "Successfully Approved: "+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            Snackbar.make(v,"Successfully approved. "+response.body().getMsg(),Snackbar.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Snackbar.make(v,"Failure : . "+t.getMessage(),Snackbar.LENGTH_LONG).show();

                    }
                });

            }
        });
        holder.btnOwnerEnquiryDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Proceed for delete.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return enquiryList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tvOwnerEnquiryCardUserName,tvOwnerEnquiryCardUserEmail,tvOwnerEnquiryCardEnquiryStatus;
        TextView tvOwnerEnquiryCardEnquiryDate, tvOwnerEnquiryCardPhone;
        TextView tvOwnerEnquiryCardEnquiryMessage;
        ImageButton btnOwnerEnquiryDelete, btnOwnerEnquiryApprove;
        CardView cvShowUserDetails;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvOwnerEnquiryCardUserName= itemView.findViewById(R.id.tv_owner_enquiry_card_user_name);
            tvOwnerEnquiryCardUserEmail = itemView.findViewById(R.id.tv_owner_enquiry_card_user_email);
            tvOwnerEnquiryCardPhone = itemView.findViewById(R.id.tv_owner_enquiry_card_user_phone);
            tvOwnerEnquiryCardEnquiryDate = itemView.findViewById(R.id.tv_owner_enquiry_card_enquiry_date);
            tvOwnerEnquiryCardEnquiryStatus = itemView.findViewById(R.id.tv_owner_enquiry_card_status);
            tvOwnerEnquiryCardEnquiryMessage = itemView.findViewById(R.id.tv_owner_enquiry_card_message);
            cvShowUserDetails = itemView.findViewById(R.id.cv_owner_enquiry_show_details_onclick);

            btnOwnerEnquiryDelete = itemView.findViewById(R.id.btn_owner_enquiry_delete);
            btnOwnerEnquiryApprove = itemView.findViewById(R.id.btn_owner_enquiry_approve);

        }
    }
}
