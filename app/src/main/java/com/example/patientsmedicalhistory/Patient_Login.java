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

public class Patient_Login extends AppCompatActivity {
    //Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");

    private Button signup, login,login_OTP;
    private EditText id, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        //
        id = findViewById(R.id.txtPatientId);
        pass= findViewById(R.id.admin_pass);

        signup = findViewById(R.id.btn_sign_up);
        login = findViewById(R.id.btn_login);
        login_OTP = findViewById(R.id.login_otp);

        //login OTP
        login_OTP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Patient_Login.this, SendOTPActivity.class);
                startActivity(intent);
            }
        });
        //signup
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Patient_Login.this, SignUpOfPatient.class);
                startActivity(intent);
            }
        });
        //login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userid = id.getText().toString();
                final String spass = pass.getText().toString();
                if(userid.isEmpty()|| spass.isEmpty())
                {
                    Toast.makeText(Patient_Login.this, "Please enter all the values", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("Patient").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Check if this user id exists?
                            if(snapshot.hasChild(userid)){
                                final String getPassword = snapshot.child(userid).child("Password").getValue(String.class);
                                if(getPassword.equals(spass))
                                {
                                    Toast.makeText(Patient_Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Patient_Login.this, PatientPanel.class);
                                    intent.putExtra("PatientID",userid);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(Patient_Login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Patient_Login.this, "This user id is not registered", Toast.LENGTH_SHORT).show();
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