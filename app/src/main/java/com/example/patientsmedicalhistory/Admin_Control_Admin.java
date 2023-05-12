package com.example.patientsmedicalhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_Control_Admin extends AppCompatActivity {
    private Button btn_create_admin, btn_adminlist, btn_modify;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control_admin);

        // Create New Admin ID
        // Button----------------------------------------------------------------
        btn_create_admin = findViewById(R.id.btn_create_AID);
        btn_create_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Control_Admin.this, SignUp_of_Admin.class);
                startActivity(intent);
            }
        });
        // Modify Admin
        // Data-------------------------------------------------------------------------
        btn_modify = findViewById(R.id.btn_modify_aid);
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Control_Admin.this, AdminID_To_Modify.class);
                startActivity(intent);
            }
        });

        // Check Admin
        // List--------------------------------------------------------------------------
        btn_adminlist = findViewById(R.id.btn_admin_list);
        btn_adminlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Control_Admin.this, Admin_List.class);
                startActivity(intent);
            }
        });
    }
}