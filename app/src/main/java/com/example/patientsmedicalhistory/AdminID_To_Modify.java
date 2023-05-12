package com.example.patientsmedicalhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminID_To_Modify extends AppCompatActivity {

    Button aid;
    EditText id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_id_to_modify);
        setTitle("Collect Admin ID to Modify");


        aid = findViewById(R.id.btn_aid);
        aid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = findViewById(R.id.DID);
                final String adminID = id.getText().toString();
                Intent intent = new Intent(AdminID_To_Modify.this, Modify_Admins_Data.class);
                intent.putExtra("AdminID", adminID);
                startActivity(intent);
                finish();
            }
        });

    }
}