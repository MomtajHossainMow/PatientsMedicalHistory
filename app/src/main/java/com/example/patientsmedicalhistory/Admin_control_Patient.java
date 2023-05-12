package com.example.patientsmedicalhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_control_Patient extends AppCompatActivity
{
    private Button create_PID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control_patient);
        //----------------------Create Patient ID Button -----------------------------------------------------
        create_PID = findViewById(R.id.btn_create_pid);
        create_PID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Admin_control_Patient.this,SignUpOfPatient.class);
                startActivity(intent);
            }
        });
        //---------------------------------------------------------------------------------------------------
    }
}