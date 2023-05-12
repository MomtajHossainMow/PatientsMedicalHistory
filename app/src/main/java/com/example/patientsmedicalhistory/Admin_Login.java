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

public class Admin_Login extends AppCompatActivity
{
    //Database connecting url
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");
    //variable declaration for buttons
    private Button signup, login;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        // redirecting to signup page of admin-----------------------------------------------------
        signup = findViewById(R.id.btn_SignUp_Admin);
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent( Admin_Login.this, SignUp_of_Admin.class);
                startActivity(intent);
            }
        });
        //------------after clicking the login button-----------------------------------------------
        final EditText a_id = findViewById(R.id.admin_id);
        final EditText a_pass = findViewById(R.id.admin_pass);
        login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String userid = a_id.getText().toString();
                final String apass = a_pass.getText().toString();
                if(userid.isEmpty()|| apass.isEmpty())
                {
                    Toast.makeText(Admin_Login.this, "Please enter all the values", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("Admins").addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            //Check whether this user id exists or not------------------------------------------
                            if(snapshot.hasChild(userid))
                            {
                                final String getPassword = snapshot.child(userid).child("Password").getValue(String.class);
                                if(getPassword.equals(apass))
                                {
                                    Toast.makeText(Admin_Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Admin_Login.this, Admin_Panel.class);
                                    intent.putExtra("AdminID",userid );
                                    intent.putExtra("AdminPass",apass);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(Admin_Login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Admin_Login.this, "This user id is not registered", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {
                            Toast.makeText(Admin_Login.this, "Logged in failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}