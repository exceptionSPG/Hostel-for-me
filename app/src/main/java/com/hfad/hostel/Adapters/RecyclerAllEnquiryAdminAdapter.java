package com.hfad.hostel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.hostel.R;
import com.hfad.hostel.model.EnquiryModel;
import com.hfad.hostel.model.User;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAllEnquiryAdminAdapter extends RecyclerView.Adapter<RecyclerAllEnquiryAdminAdapter.viewHolder> implements Filterable {//



    List<EnquiryModel> enquiryModels;
    EnquiryModel enquiry;
    List<EnquiryModel> enquiryAll ;
    Context mCtx;

    public RecyclerAllEnquiryAdminAdapter(List<EnquiryModel> enquiryModels, Context mCtx) {
        this.enquiryModels = enquiryModels;
        this.mCtx = mCtx;
        enquiryAll = new ArrayList<>(enquiryModels);
        Toast.makeText(mCtx, "constructor called."+enquiryModels.size(), Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.admin_enquiry_card,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        enquiry = enquiryModels.get(position);
        Toast.makeText(mCtx, "Adapter Bindviewholder : "+enquiry.getUser_name(), Toast.LENGTH_SHORT).show();
        holder.tvEnquiryCardUserName.setText(enquiry.getUser_name());
        holder.tvEnquiryCardHostelName.setText(enquiry.getHostel_name());
        holder.tvEnquiryCardUserEmail.setText(enquiry.getUser_email());
        holder.tvEnquiryCardEnquiryID.setText(String.valueOf(enquiry.getEid()));
        holder.tvEnquiryCardEnquiryStatus.setText(enquiry.getEnquiry_status());
        holder.tvEnquiryCardEnquiryDate.setText(enquiry.getEnquiry_date());
        holder.tvEnquiryCardEnquiryMessage.setText(enquiry.getEnquiry_message());


        holder.btnEnquiryDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Delete Clicked..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(enquiryModels==null){
            Toast.makeText(mCtx, "size 0", Toast.LENGTH_SHORT).show();
            return 0;
        }else {
            Toast.makeText(mCtx, "size: "+enquiryModels.size(), Toast.LENGTH_SHORT).show();
            return enquiryModels.size();

        }

    }

    @Override
   public Filter getFilter() {
        return enquiryFilter;
    }

    Filter enquiryFilter = new Filter() {
        //this method runs on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<EnquiryModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                //show all data
                filteredList.addAll(enquiryAll);
            }else {
                //filter using keys
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(EnquiryModel enquiryModel : enquiryAll){
                    if(enquiryModel.getUser_name().toLowerCase().contains(filterPattern) ||  enquiryModel.getUser_email().toLowerCase().contains(filterPattern)|| enquiryModel.getHostel_name().contains(filterPattern)|| enquiryModel.getEnquiry_status().contains(filterPattern)){
                        filteredList.add(enquiryModel); //store those enquiry whose hostel name contains list as asked.
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        //this method runs on ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            enquiryModels.clear();
            enquiryModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tvEnquiryCardUserName, tvEnquiryCardHostelName,tvEnquiryCardUserEmail,tvEnquiryCardEnquiryID,tvEnquiryCardEnquiryStatus;
        TextView tvEnquiryCardEnquiryDate;
        TextView tvEnquiryCardEnquiryMessage;
        ImageButton btnEnquiryDelete, btnEnquiryEdit;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvEnquiryCardUserName= itemView.findViewById(R.id.tv_enquiry_card_user_name);
            tvEnquiryCardHostelName = itemView.findViewById(R.id.tv_enquiry_card_hostel_name);
            tvEnquiryCardUserEmail = itemView.findViewById(R.id.tv_enquiry_card_user_email);
            tvEnquiryCardEnquiryID = itemView.findViewById(R.id.tv_enquiry_card_enquiry_id);
            tvEnquiryCardEnquiryStatus = itemView.findViewById(R.id.tv_enquiry_card_status);
            tvEnquiryCardEnquiryDate = itemView.findViewById(R.id.tv_enquiry_card_enquiry_date);
            tvEnquiryCardEnquiryMessage = itemView.findViewById(R.id.tv_enquiry_card_message);

            btnEnquiryDelete = itemView.findViewById(R.id.btn_enquiry_delete);
            btnEnquiryEdit = itemView.findViewById(R.id.btn_enquiry_edit);

        }
    }
}
