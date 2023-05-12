package com.example.patientsmedicalhistory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Appointment_PID_Adapter extends RecyclerView.Adapter<Appointment_PID_Adapter.AppointmentViewHolder>
{
    Context context;
    ArrayList<retrieve_appointment_DID> list;
    private OnItemClickListener listener;
    //interface for deleting
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    //method for deleting
    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    public Appointment_PID_Adapter(Context context, ArrayList<retrieve_appointment_DID> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Appointment_PID_Adapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_appointment_pid, parent, false);
        //need pass "listener" for delete order
        return new Appointment_PID_Adapter.AppointmentViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Appointment_PID_Adapter.AppointmentViewHolder holder, int position)
    {
        retrieve_appointment_DID appointment = list.get(position);
        holder.pid.setText(appointment.getPatient_ID());
        holder.did.setText(appointment.getDoctor_ID());
        holder.date.setText(appointment.getDate());
    }

    @Override
    public int getItemCount()
    {

        return list.size();
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder
    {

        TextView pid, did, date;
        ImageButton prescribeButton, deleteAppointment;
        public AppointmentViewHolder(@NonNull View itemView, OnItemClickListener listener)
        {
            super(itemView);

            did = itemView.findViewById(R.id.DoctorsID);
            pid = itemView.findViewById(R.id.PatientsID);
            date = itemView.findViewById(R.id.AppointmentDate);
            prescribeButton = itemView.findViewById(R.id.prescribeButton);
            deleteAppointment = itemView.findViewById(R.id.delete_appointmentButton);
            deleteAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}

