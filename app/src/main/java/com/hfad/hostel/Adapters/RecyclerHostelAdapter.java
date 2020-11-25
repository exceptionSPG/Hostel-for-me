package com.hfad.hostel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.hostel.Activity.HostelDetail;
import com.hfad.hostel.Activity.LoginActivity;
import com.hfad.hostel.R;
import com.hfad.hostel.Storage.SharedPrefManager;
import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.RecyclerModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerHostelAdapter extends RecyclerView.Adapter<RecyclerHostelAdapter.viewHolder> implements Filterable {

    List<Owner> list;

    List<Owner> listAll;
    Context mCtx;

    public RecyclerHostelAdapter(List<Owner> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
        listAll = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.sample_recycler_view_user_home,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {



        Owner owner = list.get(position);
        holder.hostel_image.setImageResource(R.drawable.main_logo);
        holder.tv_hostel_name.setText(owner.getHostel_name());
        holder.tv_hostel_location.setText(owner.getHostel_location());

        //onclick listener
        holder.hostel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, HostelDetail.class);
                String hostel_code = owner.getHostel_code();
                intent.putExtra("hostel_code",hostel_code);
                intent.putExtra("name", owner.getHostel_name());
                intent.putExtra("location",owner.getHostel_location());
                mCtx.startActivity(intent);
                Toast.makeText(mCtx, owner.getHostel_name()+" is clicked.", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //implementing method for filter
    @Override
    public Filter getFilter() {
        return ownerFilter;
    }

    Filter ownerFilter = new Filter() {
        //this method runs on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Owner> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                //show all data
                filteredList.addAll(listAll);
            }else {
                //filter using keys
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Owner owner : listAll){
                    if(owner.getHostel_name().toLowerCase().contains(filterPattern)|| owner.getHostel_location().toLowerCase().contains(filterPattern)||owner.getHostel_type().toLowerCase().contains(filterPattern)){
                        filteredList.add(owner); //store those owner whose hostel name contains list as asked.
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

            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class viewHolder extends RecyclerView.ViewHolder{

        ImageView hostel_image;
        TextView tv_hostel_name, tv_hostel_location;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            hostel_image = itemView.findViewById(R.id.imageview_hostel_image);
            tv_hostel_name = itemView.findViewById(R.id.tv_hostel_name_recycler);
            tv_hostel_location = itemView.findViewById(R.id.tv_hostel_location_recycler);

        }
    }
}
