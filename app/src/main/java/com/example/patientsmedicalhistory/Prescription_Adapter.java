package com.example.patientsmedicalhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Prescription_Adapter extends RecyclerView.Adapter<Prescription_Adapter.PrescriptionViewHolder>
{
    Context context;
    ArrayList<RetrievePrescriptions> list;

    public Prescription_Adapter(Context context, ArrayList<RetrievePrescriptions> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Prescription_Adapter.PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_prescription_cv, parent, false);
        return new Prescription_Adapter.PrescriptionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Prescription_Adapter.PrescriptionViewHolder holder, int position)
    {
        RetrievePrescriptions prescription = list.get(position);
        holder.pres_No.setText(String.valueOf(prescription.getPrescriptionNo()));
        holder.docID.setText(prescription.getDoctor_ID());
        holder.patID.setText(prescription.getPatient_ID());
        holder.patName.setText(prescription.getPatient_Name());
        holder.trt.setText(prescription.getPrescription());
        holder.adv.setText(prescription.getAdvices());
        holder.symp.setText(prescription.getSymptoms());

    }

    @Override
    public int getItemCount()
    {

        return list.size();
    }

    public static class PrescriptionViewHolder extends RecyclerView.ViewHolder
    {

        TextView pres_No,patID,docID,patName,trt,adv,symp;
        public PrescriptionViewHolder(@NonNull View itemView)
        {
            super(itemView);

            pres_No = itemView.findViewById(R.id.prescription_no);
            docID = itemView.findViewById(R.id.doctors_id);
            patID = itemView.findViewById(R.id.patient_id);
            patName = itemView.findViewById(R.id.Patient_name);
            trt= itemView.findViewById(R.id.treatment);
            adv=itemView.findViewById(R.id.advices);
            symp=itemView.findViewById(R.id.symptoms);

        }
    }
}
