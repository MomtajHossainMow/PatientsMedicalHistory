package com.example.patientsmedicalhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_Control_Doctor extends AppCompatActivity
{
    private Button Create_DID, Show_Doclist, Modify_DID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control_doctor);
        //Create New Dcotor ID----------------------------------------------------------------------
        Create_DID = findViewById(R.id.btn_Create_DID);
        Create_DID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Admin_Control_Doctor.this, SignUp_of_Doctor.class);
                startActivity(intent);
            }
        });

        //Modify Doctor ID
        Modify_DID = findViewById(R.id.btn_modify_DID);
        Modify_DID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Control_Doctor.this, DoctorID_To_Modify.class);
                startActivity(intent);
            }
        });

        //Show Doctors List
        Show_Doclist = findViewById(R.id.btn_doclist);
        Show_Doclist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Control_Doctor.this, Doctors_List.class);
                startActivity(intent);
            }
        });

    }
}