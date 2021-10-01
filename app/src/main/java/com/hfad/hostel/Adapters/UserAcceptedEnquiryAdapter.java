package com.hfad.hostel.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.hostel.R;
import com.hfad.hostel.model.EnquiryModel;
//import com.khalti.checkout.helper.Config;
//import com.khalti.checkout.helper.OnCheckOutListener;
//import com.khalti.utils.Constant;
//import com.khalti.widget.KhaltiButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class UserAcceptedEnquiryAdapter extends RecyclerView.Adapter<UserAcceptedEnquiryAdapter.viewHolder> {

    List<EnquiryModel> acceptedEnquiries;
    EnquiryModel enquiry;

    public UserAcceptedEnquiryAdapter(List<EnquiryModel> acceptedEnquiries) {
        this.acceptedEnquiries = acceptedEnquiries;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_accepted_enquiry_recycler_items,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        enquiry = acceptedEnquiries.get(position);
        holder.tvHostelName.setText(enquiry.getHostel_name());
        holder.tvHostelLocation.setText(enquiry.getHostel_address());
        holder.tvAcceptedDate.setText("Accepted on: "+enquiry.getEnquiry_status_update_date());
        holder.kbPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked..", Toast.LENGTH_SHORT).show();
            }
        });

        //khaltiImplement(holder.itemView.getContext(),holder.kbPay,String.valueOf(enquiry.getOwnerid()),enquiry.getHostel_name(),2500L);

    }

    @Override
    public int getItemCount() {
        return acceptedEnquiries.size();
    }

   /* public void khaltiImplement(Context mCtx, KhaltiButton kBuy, String productId, String productName, Long price){

        Long priceInPaisa = price*100;

        Config.Builder builder = new Config.Builder(Constant.pub, productId, productName, priceInPaisa, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());

            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
                Toast.makeText(mCtx, "Success: "+data.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Config config = builder.build();
        kBuy.setCheckOutConfig(config);
//        KhaltiCheckOut khaltiCheckOut1 = new KhaltiCheckOut(mCtx, config);
//        kBuy.setOnClickListener(v -> khaltiCheckOut1.show());



    }
*/

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvHostelName, tvHostelLocation, tvAcceptedDate;
        //KhaltiButton kbPay;
        Button kbPay;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvHostelName = itemView.findViewById(R.id.tv_user_accepted_enquiry_hostel_name);
            tvHostelLocation = itemView.findViewById(R.id.tv_user_accepted_enquiry_hostel_address);
            tvAcceptedDate = itemView.findViewById(R.id.tv_user_accepted_enquiry_accpted_date);
            //kbPay = itemView.findViewById(R.id.kb_user_accepted_enquiry_btn_pay);
            kbPay=itemView.findViewById(R.id.kb_user_accepted_enquiry_btn_pay);
        }
    }
}
