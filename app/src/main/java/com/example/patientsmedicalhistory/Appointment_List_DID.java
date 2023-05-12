package com.example.patientsmedicalhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Appointment_List_DID extends AppCompatActivity {

    //Database connecting url
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");
    //variable declaration
    RecyclerView recyclerview;
    appointment_DID_adapter appointmentAdapter_DID;
    ArrayList<retrieve_appointment_DID> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list_did);
        setTitle("Appointment list of Doctor");

        //my code for finding appointments list of doctor
        recyclerview = findViewById(R.id.appointments_list_DID_view);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        appointmentAdapter_DID = new appointment_DID_adapter(this,list);
        recyclerview.setAdapter(appointmentAdapter_DID);

        //delete function call
        appointmentAdapter_DID.setOnItemClickListener(new appointment_DID_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Continue with delete operation
                retrieve_appointment_DID appointment = list.get(position);
                final String patID = appointment.getPatient_ID();
                final String docID = appointment.getDoctor_ID();
                databaseReference.child("Appointments").child(patID+docID).removeValue();
                Toast.makeText(Appointment_List_DID.this, "Appointment Is Deleted.", Toast.LENGTH_SHORT).show();
                //---------------------------------------------------------------------------------------------------------
                list.remove(position);
                appointmentAdapter_DID.notifyItemRemoved(position);
            }
        });

        //Collecting the passed data from Doctor_Panel java file
        String ID = getIntent().getStringExtra("DoctorID");

        Query query= databaseReference.child("Appointments").orderByChild("Doctor_ID").equalTo(ID);
        query.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    retrieve_appointment_DID appointments = dataSnapshot.getValue(retrieve_appointment_DID.class);
                    list.add(appointments);
                }
                appointmentAdapter_DID.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}