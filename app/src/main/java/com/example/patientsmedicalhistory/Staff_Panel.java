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

public class Staff_Panel extends AppCompatActivity {
    //Database reference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");

    TextView staffname;
    private Button CreatePID, setAppointment, doclist, profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_panel);
        setTitle("Staff Panel");
        //collect passed data from login page-------------------------------------------------------
        String ID = getIntent().getStringExtra("StaffID");
        //set staff name in textview----------------------------------------------------------------
        databaseReference.child("Staffs").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Check whether this user id exists or not------------------------------------------
                if (snapshot.hasChild(ID)) {
                    final String name = snapshot.child(ID).child("Name").getValue(String.class);
                    staffname = findViewById(R.id.textView_Staff_Name);
                    staffname.setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(Staff_Panel.this, "User Data Retrival Failed", Toast.LENGTH_SHORT).show();
            }
        });
        //Create New Patient ID---------------------------------------------------------------------
        CreatePID = findViewById(R.id.Create_Patient_ID);
        CreatePID.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Staff_Panel.this, SignUpOfPatient.class));
            }
        });

        //Set Appointment---------------------------------------------------------------------------
        setAppointment = findViewById(R.id.Set_Appointment);
        setAppointment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Staff_Panel.this, Set_Appointment.class));
            }
        });

        //See Doctor's List---------------------------------------------------------------------------
        doclist = findViewById(R.id.btn_see_doctors_list);
        doclist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Staff_Panel.this, Doctors_List.class));
            }
        });

        //Manage Staff's Own Profile---------------------------------------------------------------------------
        profile = findViewById(R.id.btn_manage_staff_profile);
        profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Staff_Panel.this, Staff_Profile.class);
                intent.putExtra("StaffID",ID);
                startActivity(intent);
                finish();
            }
        });


    }
}