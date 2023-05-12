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

public class Doctor_Login extends AppCompatActivity
{
    //Database connecting url
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");
    //variable declaration for buttons
    private Button signup, login;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //By default created codes-----------------------
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        // redirecting to signup page of doctor-----------------------------------------------------
        signup = findViewById(R.id.btn_SignUp_Doc);
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent( Doctor_Login.this, SignUp_of_Doctor.class);
                startActivity(intent);
            }
        });
        final EditText d_id = findViewById(R.id.doctor_id);
        final EditText d_pass = findViewById(R.id.admin_pass);
        //------------after clicking the login button-----------------------------------------------
        login = findViewById(R.id.btn_Login);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String userid = d_id.getText().toString();
                final String dpass = d_pass.getText().toString();
                if(userid.isEmpty()|| dpass.isEmpty())
                {
                    Toast.makeText(Doctor_Login.this, "Please enter all the values", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("Doctors").addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            //Check if this user id exists?------------------------------------------
                            if(snapshot.hasChild(userid))
                            {
                                final String getPassword = snapshot.child(userid).child("Password").getValue(String.class);
                                if(getPassword.equals(dpass))
                                {
                                    Toast.makeText(Doctor_Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Doctor_Login.this, Doctor_Panel.class);
                                    intent.putExtra("DoctorID",userid );
                                    intent.putExtra("DoctorPass",dpass);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    Toast.makeText(Doctor_Login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Doctor_Login.this, "This doctor id is not registered", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {
                            Toast.makeText(Doctor_Login.this, "Logged in failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}