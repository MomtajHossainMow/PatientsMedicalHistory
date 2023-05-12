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

import java.util.ArrayList;

public class PrescriptionList extends AppCompatActivity {
    //Database connecting url
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");
    //variable declaration
    RecyclerView recyclerview;
    //DatabaseReference database;
    Prescription_Adapter prescriptionAdapter;
    ArrayList<RetrievePrescriptions> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);
        setTitle("Prescription List");

        //Collecting the passed data from Check Health Record java file
        String ID = getIntent().getStringExtra("PatientID");

        //my code
        recyclerview = findViewById(R.id.prescription_list_view);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        list = new ArrayList<>();
        prescriptionAdapter = new Prescription_Adapter(this,list);
        recyclerview.setAdapter(prescriptionAdapter);


        Query query= databaseReference.child("Prescriptions").orderByChild("Patient_ID").equalTo(ID);
        query.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int count =0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    RetrievePrescriptions prescription_a = dataSnapshot.getValue(RetrievePrescriptions.class);
                    list.add(prescription_a);
                    count = count + 1;
                }

                if(count>0){
                    prescriptionAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(PrescriptionList.this, "No Prescription Ever", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}