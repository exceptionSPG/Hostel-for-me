package com.hfad.hostel.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.hostel.Activity.HostelDetail;
import com.hfad.hostel.R;
import com.hfad.hostel.model.UserInfo;
import com.hfad.hostel.model.User;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAllUserAdminHomeAdapter extends RecyclerView.Adapter<RecyclerAllUserAdminHomeAdapter.viewHolder> implements Filterable {

    List<User> list;
    User user;
    List<User> listAll;
    Context mCtx;
    AlertDialog.Builder builder;


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

        builder = new AlertDialog.Builder(mCtx);
        //btn delete onclick listener
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "Delete? item: "+id, Toast.LENGTH_SHORT).show();
                // when delete is clicked, we want to show a dialog and when yes is clicked we want to delete the data from
                //database using api call boom
                //Uncomment the below code to Set the message and title from the strings.xml file
                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to delete user?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //dialog.dismiss();
                                Toast.makeText(v.getContext(), "you choose yes action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                                if(deleteUser(id)){
                                    Toast.makeText(mCtx, "Deleted Successfully.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(mCtx, "Failure on Deletion.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(v.getContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("!!Are you Sure!!");
                alert.show();
            }
        });

    }
    private boolean deleteUser(int user_id){
        //here we will call api to delete user
        //haha first I need to make api endpoint then I will continue this .. boom
        return true;
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
        ImageButton btnDelete, btnEdit;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_user_name= itemView.findViewById(R.id.tv_user_name_recycler);
            tv_user_email = itemView.findViewById(R.id.tv_user_email_recycler);
            tv_user_id = itemView.findViewById(R.id.tv_user_id_recycler);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);

        }
    }
}
