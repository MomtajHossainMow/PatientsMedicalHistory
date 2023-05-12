package com.example.patientsmedicalhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Staff_Login extends AppCompatActivity {

    //Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");

    private Button signup, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);
        final EditText id = findViewById(R.id.text_staff_id);
        final EditText pass = findViewById(R.id.admin_pass);
        signup = findViewById(R.id.Sign_Up);
        login = findViewById(R.id.btn_login);
        //signup
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Staff_Login.this, SignUp_of_Staff.class);
                startActivity(intent);
            }
        });
        //login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String userid = id.getText().toString();
                final String spass = pass.getText().toString();
                if(userid.isEmpty()|| spass.isEmpty())
                {
                    Toast.makeText(Staff_Login.this, "Please enter all the values", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("Staffs").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Check if this user id exists?
                            if(snapshot.hasChild(userid)){
                                final String getPassword = snapshot.child(userid).child("Password").getValue(String.class);
                                if(getPassword.equals(spass))
                                {
                                    Toast.makeText(Staff_Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Staff_Login.this, Staff_Panel.class);
                                    intent.putExtra("StaffID", userid);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(Staff_Login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Staff_Login.this, "This user id is not registered", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}