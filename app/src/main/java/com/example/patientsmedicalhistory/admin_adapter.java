package com.example.patientsmedicalhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class admin_adapter extends RecyclerView.Adapter<admin_adapter.AdminViewHolder>
{
    Context context;
    ArrayList<retrieve_admin_list> list;

    public admin_adapter(Context context, ArrayList<retrieve_admin_list> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public admin_adapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_admin, parent, false);
        return new admin_adapter.AdminViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull admin_adapter.AdminViewHolder holder, int position)
    {
        retrieve_admin_list admin = list.get(position);
        holder.name.setText(admin.getName());
        holder.id.setText(admin.getNID());
        holder.phone.setText(admin.getPhone());

    }

    @Override
    public int getItemCount()
    {

        return list.size();
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder
    {

        TextView id, name, phone;
        public AdminViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.Admins_name);
            id = itemView.findViewById(R.id.Admins_id);
            phone = itemView.findViewById(R.id.Admins_phone);

        }
    }
}

