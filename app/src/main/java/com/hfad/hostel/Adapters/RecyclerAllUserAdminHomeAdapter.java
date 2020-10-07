package com.hfad.hostel.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.hfad.hostel.R;
import com.hfad.hostel.model.Owner;
import com.hfad.hostel.model.User;
import com.hfad.hostel.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAllUserAdminHomeAdapter extends RecyclerView.Adapter<RecyclerAllUserAdminHomeAdapter.viewHolder> implements Filterable {

    List<User> list;
    User user;
    List<User> listAll;
    Context mCtx;

    public RecyclerAllUserAdminHomeAdapter(List<User> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
        listAll = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.sample_recycler_view_alluser_admin,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        user = list.get(position);
        String name = user.getUsername();
        holder.tv_user_name.setText(name);
        holder.tv_user_email.setText(user.getEmail());
        int id = user.getUser_id();
        String str = String.valueOf(id);
        holder.tv_user_id.setText(str);

        //onclick listener
        holder.tv_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mCtx, HostelDetail.class);
//                int user_id = user.getUser_id();
//                intent.putExtra("user_id",user_id);
//                intent.putExtra("name", user.getUsername());
                //mCtx.startActivity(intent);
                Toast.makeText(mCtx, name+" is clicked.", Toast.LENGTH_SHORT).show();

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
            List<User> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                //show all data
                filteredList.addAll(listAll);
            }else {
                //filter using keys
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(User user : listAll){
                    if(user.getUsername().toLowerCase().contains(filterPattern)|| user.getEmail().toLowerCase().contains(filterPattern)||String.valueOf(user.getUser_id()).contains(filterPattern)){
                        filteredList.add(user); //store those user whose hostel name contains list as asked.
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

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tv_user_name, tv_user_email,tv_user_id;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user_name= itemView.findViewById(R.id.tv_user_name_recycler);
            tv_user_email = itemView.findViewById(R.id.tv_user_email_recycler);
            tv_user_id = itemView.findViewById(R.id.tv_user_id_recycler);

        }
    }
}
