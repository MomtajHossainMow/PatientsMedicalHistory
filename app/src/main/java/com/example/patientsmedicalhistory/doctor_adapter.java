package com.example.patientsmedicalhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class doctor_adapter extends RecyclerView.Adapter<doctor_adapter.DoctorViewHolder> //implements Filterable
{
    Context context;
    ArrayList<retrieve_doctor_list> list;
    //ArrayList<retrieve_doctor_list> backup;

    public doctor_adapter(Context context, ArrayList<retrieve_doctor_list> list)
    {
        this.context = context;
        this.list = list;
        //backup = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position)
    {
            retrieve_doctor_list doctor = list.get(position);
            holder.name.setText(doctor.getName());
            holder.id.setText(doctor.getNID());
            holder.phone.setText(doctor.getPhone());
            holder.specs.setText(doctor.getSpecifications());

    }

    @Override
    public int getItemCount()
    {

        return list.size();
    }

//    @Override
//    public Filter getFilter() {
//        return filter;
//    }
//
//    Filter filter = new Filter() {
//        @Override
//        //background thread
//        protected FilterResults performFiltering(CharSequence keyword)
//        {
//            ArrayList<retrieve_doctor_list>filetereddata= new ArrayList<>();
//            if(keyword.toString().isEmpty())
//                filetereddata.addAll(backup);
//            else
//            {
//                for(retrieve_doctor_list obj: backup)
//                {
//                    if(obj.getSpecifications().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
//                    {
//                        filetereddata.add(obj);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values=filetereddata;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results)
//        {
//            list.clear();
//            list.addAll((ArrayList<retrieve_doctor_list>)results.values);
//            notifyDataSetChanged();
//        }
//    };

    public static class DoctorViewHolder extends RecyclerView.ViewHolder
    {

        TextView id, name, phone, specs;
        public DoctorViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.Doctors_name);
            id = itemView.findViewById(R.id.Doctors_id);
            phone = itemView.findViewById(R.id.Doctors_phone);
            specs = itemView.findViewById(R.id.Doctors_Spec);

        }
    }
}
