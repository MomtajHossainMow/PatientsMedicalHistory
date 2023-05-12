package com.example.patientsmedicalhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Prescription extends AppCompatActivity {
    //--------------- Database connecting URL ------------------------------------------------------
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");

    private Button submitBtn;
    private TextView doctorID, doctorName, doctorSpec;
    EditText patientID,patientName, pdate;
    EditText symptoms, advices,treatment;

    long prescriptionNo =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        //collect passed data from login page-------------------------------------------------------
        String docID = getIntent().getStringExtra("DoctorID");
        String patID = getIntent().getStringExtra("PatientID");
        String date = getIntent().getStringExtra("Date");

        submitBtn = findViewById(R.id.buttonSubmit);

        doctorID = findViewById(R.id.pres_doc_id);
        doctorName = findViewById(R.id.doctor_name_added);
        doctorSpec = findViewById(R.id.textView_doctor_spec);

        patientID = findViewById(R.id.editText_Patient_ID);
        patientName = findViewById(R.id.editText_patient_name);
        pdate = findViewById(R.id.apt_date);

        symptoms = findViewById(R.id.editText_Symptoms);
        advices = findViewById(R.id.editTextAdvices);
        treatment = findViewById(R.id.editTextPrescription);
        //set patient info in textview----------------------------------------------------------------
        databaseReference.child("Patient").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Check whether this user id exists or not------------------------------------------
                if (snapshot.hasChild(patID)) {
                    final String Pat_Name = snapshot.child(patID).child("Name").getValue(String.class);
                    final String pid = snapshot.child(patID).child("NID").getValue(String.class);
                    patientName.setText(Pat_Name);
                    patientID.setText(pid);
                    pdate.setText(date);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(Prescription.this, "User Data Retrival Failed", Toast.LENGTH_SHORT).show();
            }
        });
        //set doctor name in textview----------------------------------------------------------------
        databaseReference.child("Doctors").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Check whether this user id exists or not------------------------------------------
                if (snapshot.hasChild(docID)) {
                    final String name = snapshot.child(docID).child("Name").getValue(String.class);
                    final String id = snapshot.child(docID).child("NID").getValue(String.class);
                    final String specs = snapshot.child(docID).child("Specifications").getValue(String.class);
                    doctorName.setText(name);
                    doctorID.setText(id);
                    doctorSpec.setText(specs);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(Prescription.this, "User Data Retrival Failed", Toast.LENGTH_SHORT).show();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String docID = doctorID.getText().toString();
                final String docName = doctorName.getText().toString();
                final String docSpec = doctorSpec.getText().toString();
                final String patID = patientID.getText().toString();
                final String patName = patientName.getText().toString();
                final String adate = pdate.getText().toString();
                final String sym = symptoms.getText().toString();
                final String adv = advices.getText().toString();
                final String trt = treatment.getText().toString();

                databaseReference.child("Prescriptions").addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        prescriptionNo= snapshot.getChildrenCount();
                        prescriptionNo+=1;

                        //storing data to firebase realtime database
                        databaseReference.child("Prescriptions").child(String.valueOf(prescriptionNo)).child("Doctor_ID").setValue(docID);
                        databaseReference.child("Prescriptions").child(String.valueOf(prescriptionNo)).child("Doctor_Name").setValue(docName);
                        databaseReference.child("Prescriptions").child(String.valueOf(prescriptionNo)).child("Doctor_Spec").setValue(docSpec);

                        databaseReference.child("Prescriptions").child(String.valueOf(prescriptionNo)).child("Patient_ID").setValue(patID);
                        databaseReference.child("Prescriptions").child(String.valueOf(prescriptionNo)).child("Patient_Name").setValue(patName);
                        databaseReference.child("Prescriptions").child(String.valueOf(prescriptionNo)).child("Date").setValue(adate);

                        databaseReference.child("Prescriptions").child(String.valueOf(prescriptionNo)).child("Symptoms").setValue(sym);
                        databaseReference.child("Prescriptions").child(String.valueOf(prescriptionNo)).child("Advices").setValue(adv);
                        databaseReference.child("Prescriptions").child(String.valueOf(prescriptionNo)).child("Prescription").setValue(trt);
                        databaseReference.child("Prescriptions").child(String.valueOf(prescriptionNo)).child("prescriptionNo").setValue(prescriptionNo);
                        Toast.makeText(Prescription.this, "Prescription Submission Successful.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        Toast.makeText(Prescription.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}