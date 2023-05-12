package com.example.patientsmedicalhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button staff_login, patient_login, doctor_login, admin_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        staff_login = findViewById(R.id.button_staff);
        staff_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Staff_Login.class);
                startActivity(intent);
            }
        });

        patient_login = findViewById(R.id.button_patient);
        patient_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Patient_Login.class);
                startActivity(intent);
            }
        });

        doctor_login = findViewById(R.id.button_doctor);
        doctor_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Doctor_Login.class);
                startActivity(intent);
            }
        });

        admin_login = findViewById(R.id.button_admin);
        admin_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Admin_Login.class);
                startActivity(intent);
            }
        });
    }
}