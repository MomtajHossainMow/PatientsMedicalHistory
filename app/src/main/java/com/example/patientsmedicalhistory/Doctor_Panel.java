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

public class Doctor_Panel extends AppCompatActivity {
    //Database reference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");
    //variable declaration part
    private Button Appointments, Health_Record, Manage_Profile;
    public TextView doctor_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_panel);
        setTitle("Doctor Panel");

        //collect passed data from login page-------------------------------------------------------
        String ID = getIntent().getStringExtra("DoctorID");
        //set doctor name in textview----------------------------------------------------------------
        databaseReference.child("Doctors").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Check whether this user id exists or not------------------------------------------
                if (snapshot.hasChild(ID)) {
                    final String name = snapshot.child(ID).child("Name").getValue(String.class);
                    doctor_name = findViewById(R.id.txtView_doctor_name);
                    doctor_name.setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(Doctor_Panel.this, "User Data Retrival Failed", Toast.LENGTH_SHORT).show();
            }
        });

        //Health Record-----------------------------------------------------------------------------
        Health_Record =findViewById(R.id.btn_check_patient_health_record);
        Health_Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doctor_Panel.this, CheckHealthRecord.class);
                startActivity(intent);
            }
        });

        //Appointment lists-------------------------------------------------------------------------
        Appointments = findViewById(R.id.btn_appointments);
        Appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doctor_Panel.this, Appointment_List_DID.class);
                intent.putExtra("DoctorID", ID);
                startActivity(intent);
            }
        });

        //Manage User Profile-------------------------------------------------------------------------
        Manage_Profile = findViewById(R.id.btn_manage_profile);
        Manage_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doctor_Panel.this, Doctor_Profile.class);
                intent.putExtra("DoctorID", ID);
                startActivity(intent);
            }
        });
    }
}