package com.example.patientsmedicalhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity
{
    //Database connecting url
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otpactivity);

        //method
        final EditText nid  = findViewById(R.id.input_NID);
        Button buttonGETOPT = findViewById(R.id.ButtonGetOTP);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        buttonGETOPT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String u_nid = nid.getText().toString();
                if(u_nid.trim().isEmpty())
                {
                    Toast.makeText(SendOTPActivity.this, "Enter your NID number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    databaseReference.child("Patient").addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            //Check if this user id exists?------------------------------------------
                            if(snapshot.hasChild(u_nid))
                            {
                                final String inputmobile = snapshot.child(u_nid).child("Phone").getValue(String.class);
                                final String ID = snapshot.child(u_nid).child("NID").getValue(String.class);
                                progressBar.setVisibility(view.VISIBLE);
                                buttonGETOPT.setVisibility(view.INVISIBLE);
                                //
                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                        "+88" + inputmobile.toString(),
                                        60,
                                        TimeUnit.SECONDS,
                                        SendOTPActivity.this,
                                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                                        {

                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
                                            {
                                                progressBar.setVisibility(view.GONE);
                                                buttonGETOPT.setVisibility(view.VISIBLE);
                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e)
                                            {
                                                progressBar.setVisibility(view.GONE);
                                                buttonGETOPT.setVisibility(view.VISIBLE);
                                                Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                                            {
                                                progressBar.setVisibility(view.GONE);
                                                buttonGETOPT.setVisibility(view.VISIBLE);
                                                Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                                                intent.putExtra("Mobile", inputmobile.toString());
                                                intent.putExtra("PatientID",ID);
                                                intent.putExtra("verificationID", verificationID);
                                                startActivity(intent);

                                            }
                                        }//PhoneAuthProvider.OnVerificationStateChangedCallbacks
                                );  //PhoneAuthProvider
                            }//if
                            else
                            {
                                Toast.makeText(SendOTPActivity.this, "This NID is not registered", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }


                        //onDataChange
                    });//valueEventListener
                    } //else
                } //onClick
            });//buttonGETOPT.setOnClickListener
    }//onCreate
} //ending
