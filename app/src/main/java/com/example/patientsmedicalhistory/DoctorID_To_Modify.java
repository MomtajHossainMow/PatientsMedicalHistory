package com.example.patientsmedicalhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DoctorID_To_Modify extends AppCompatActivity {

    Button did;
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_id_to_modify);
        setTitle("Collect Doctor ID to Modify");


        did = findViewById(R.id.btn_did);
        did.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = findViewById(R.id.DID);
                final String doctorID = id.getText().toString();
                Intent intent = new Intent(DoctorID_To_Modify.this, Modify_Doctors_Data.class);
                intent.putExtra("DoctorID", doctorID);
                startActivity(intent);
                finish();
            }
        });

    }
}