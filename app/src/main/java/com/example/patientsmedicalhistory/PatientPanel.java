package com.example.patientsmedicalhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientPanel extends AppCompatActivity {
    //Database reference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");

    Button btn_records,doctorslist,profile, appointments;
    TextView patient_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_panel);
        setTitle("Patient Panel");
        //collect passed data from login page-------------------------------------------------------
        String ID = getIntent().getStringExtra("PatientID");
        //set patient name in textview----------------------------------------------------------------
        databaseReference.child("Patient").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Check whether this user id exists or not------------------------------------------
                if (snapshot.hasChild(ID)) {
                    final String name = snapshot.child(ID).child("Name").getValue(String.class);
                    patient_name = findViewById(R.id.patient_panel_name);
                    patient_name.setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(PatientPanel.this, "User Data Retrival Failed", Toast.LENGTH_SHORT).show();
            }
        });
        //See Health Records--------------------------------------------------------------------------------
        btn_records=findViewById(R.id.btn_records);
        btn_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientPanel.this,PrescriptionList.class);
                intent.putExtra("PatientID",ID);
                startActivity(intent);
            }
        });
        //See doctors list------------------------------------------------------------------------------------
        doctorslist=findViewById(R.id.btn_doctor_list);
        doctorslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientPanel.this, Doctors_List.class);
                startActivity(intent);
            }
        });
        //See doctors list------------------------------------------------------------------------------------
        appointments=findViewById(R.id.btn_appointments);
        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientPanel.this, Appointment_List_PID.class);
                intent.putExtra("PatientID",ID);
                startActivity(intent);
            }
        });
        //Manage Own Profile(Patient)--------------------------------------------------------------------------------
        profile=findViewById(R.id.btn_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientPanel.this,Patient_Profile.class);
                intent.putExtra("PatientID",ID);
                startActivity(intent);
                finish();
            }
        });
    }
}