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

public class Admin_Panel extends AppCompatActivity {
    //Database connecting url
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");
    //variable declaration part
    private Button Admin_Patient_Portal, Admin_Admin_Portal, Admin_Staff_Portal, Admin_Doctor_Portal,userProfile;
    public TextView admin_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        //collect passed data from login page-------------------------------------------------------
        String ID = getIntent().getStringExtra("AdminID");
        String Pass = getIntent().getStringExtra("AdminPass");
        //set admin name in textview----------------------------------------------------------------
        databaseReference.child("Admins").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Check whether this user id exists or not------------------------------------------
                if (snapshot.hasChild(ID)) {
                    final String name = snapshot.child(ID).child("Name").getValue(String.class);
                    admin_name = findViewById(R.id.txtview_admin_name);
                    admin_name.setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(Admin_Panel.this, "User Data Retrival Failed", Toast.LENGTH_SHORT).show();
            }
        });
        //Patient Control Portal--------------------------------------------------------------------
        Admin_Patient_Portal = findViewById(R.id.btn_Patient_Portal);
        Admin_Patient_Portal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Admin_Panel.this, Admin_control_Patient.class);
                startActivity(intent);
            }
        });

        //Admin Control Portal----------------------------------------------------------------------
        Admin_Admin_Portal = findViewById(R.id.btn_Admin_Portal);
        Admin_Admin_Portal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Admin_Panel.this, Admin_Control_Admin.class);
                startActivity(intent);
            }
        });
        //Staff Control Portal-----------------------------------------------------------------------
        Admin_Staff_Portal = findViewById(R.id.btn_Staff_Portal);
        Admin_Staff_Portal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Admin_Panel.this, Admin_Control_Staff.class);
                startActivity(intent);
            }
        });

        //Doctor Control Portal-----------------------------------------------------------------------
        Admin_Doctor_Portal = findViewById(R.id.btn_Doctor_Portal);
        Admin_Doctor_Portal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Admin_Panel.this, Admin_Control_Doctor.class);
                startActivity(intent);
            }
        });
        //See User Own Profile
        userProfile = findViewById(R.id.btn_User_Profile);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Admin_Panel.this, Admin_Profile.class);
                intent.putExtra("AdminID",ID );
                intent.putExtra("AdminPass",Pass);
                startActivity(intent);
                finish();
            }
        });
    }
}