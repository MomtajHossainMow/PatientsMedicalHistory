package com.example.patientsmedicalhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CheckHealthRecord extends AppCompatActivity {
    Button check;
    EditText PatientID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_health_record);

        check = findViewById(R.id.btn);
        PatientID = findViewById(R.id.PID);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckHealthRecord.this,PrescriptionList.class);
                final String pid = PatientID.getText().toString();
                intent.putExtra("PatientID", pid);
                startActivity(intent);
                finish();
            }
        });
    }
}