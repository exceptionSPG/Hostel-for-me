package com.hfad.hostel.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hfad.hostel.Helper.RetrofitClient;
import com.hfad.hostel.R;
import com.hfad.hostel.model.DefaultResponse;
import com.hfad.hostel.model.EnquiryModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPendingEnquiryAdapter extends RecyclerView.Adapter<UserPendingEnquiryAdapter.viewHolder>{
    List<EnquiryModel> myEnquiries;
    EnquiryModel myEnquiry;

    public UserPendingEnquiryAdapter(List<EnquiryModel> myEnquiries) {
        this.myEnquiries = myEnquiries;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_pending_enquiry_recycler_items,parent,false);


        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        myEnquiry = myEnquiries.get(position);

        holder.tvHostelName.setText(myEnquiry.getHostel_name());
        holder.tvHostelAddress.setText(myEnquiry.getHostel_address());
        holder.tvEnquiryDate.setText("Enquiry on: "+myEnquiry.getEnquiry_date());

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int enquiryId = myEnquiry.getEid();
                Call<DefaultResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .cancelEnquiry(enquiryId);
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch(InterruptedException ex)
                        {
                            Thread.currentThread().interrupt();
                        }
                        Toast.makeText(v.getContext(), "Cancel enquiry.."+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar
                                .make(v, "Canceled successfully.", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Toast.makeText(v.getContext(), "Cancel enquiry.."+t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return myEnquiries.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        public TextView tvHostelName, tvHostelAddress, tvEnquiryDate;
        public Button btnCancel;


        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvHostelAddress = itemView.findViewById(R.id.tv_user_pending_enquiry_hostel_address);
            tvHostelName = itemView.findViewById(R.id.tv_user_pending_enquiry_hostel_name);
            tvEnquiryDate = itemView.findViewById(R.id.tv_user_pending_enquiry_enquiry_date);

            btnCancel = itemView.findViewById(R.id.kb_user_pending_enquiry_btn_cancel);

        }
    }
}
