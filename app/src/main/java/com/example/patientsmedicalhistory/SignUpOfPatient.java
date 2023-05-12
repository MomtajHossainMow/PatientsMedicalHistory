package com.example.patientsmedicalhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SignUpOfPatient extends AppCompatActivity {
    //---------------importing database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");
    //-----------------
    private DatePickerDialog datePickerDialog;
    private TextView dateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_of_patient);
        //Copied for DatePicker
        dateButton = findViewById(R.id.patient_DOB);
        initDatePicker();

        //name, address, nid, phone, dob, pass
        final EditText name = findViewById(R.id.patient_name);
        final EditText address = findViewById(R.id.patient_address);
        final EditText nid = findViewById(R.id.patient_nid);
        final EditText phone = findViewById(R.id.patient_phone);
        final EditText mail = findViewById(R.id.patient_mail);
        final EditText pass= findViewById(R.id.patient_password);
        final EditText pass2 = findViewById(R.id.patient_password_2);
        final Button signupButton = findViewById(R.id.button_patient_signup);

//--------------------------------------------------------------------------------------
        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String sname= name.getText().toString();
                final String snid= nid.getText().toString();
                final String saddress= address.getText().toString();
                final String sphone= phone.getText().toString();
                final String smail=mail.getText().toString().trim();
                final String sDOB= dateButton.getText().toString();
                final String spass= pass.getText().toString();
                final String spass2= pass2.getText().toString();

                if(sname.isEmpty() || saddress.isEmpty() || snid.isEmpty() || sphone.isEmpty() || sDOB.isEmpty() || spass.isEmpty() ||spass2.isEmpty()) {
                    Toast.makeText(SignUpOfPatient.this, "Please fill all the required information", Toast.LENGTH_SHORT).show();
                }
                else if(snid.length()!=10)
                {
                    Toast.makeText(SignUpOfPatient.this, "Enter valid NID number", Toast.LENGTH_SHORT).show();
                }
                else if(sphone.length()!=11)
                {
                    Toast.makeText(SignUpOfPatient.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                }
                else if(!spass.equals(spass2))
                {
                    Toast.makeText(SignUpOfPatient.this, "Passwords dosen't match", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("Patient").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(snid)) {
                                Toast.makeText(SignUpOfPatient.this, "This NID is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //storing data to firebase realtime database
                                databaseReference.child("Patient").child(snid).child("Name").setValue(sname);
                                databaseReference.child("Patient").child(snid).child("Address").setValue(saddress);
                                databaseReference.child("Patient").child(snid).child("NID").setValue(snid);
                                databaseReference.child("Patient").child(snid).child("Phone").setValue(sphone);
                                databaseReference.child("Patient").child(snid).child("Email").setValue(smail);
                                databaseReference.child("Patient").child(snid).child("Date of Birth").setValue(sDOB);
                                databaseReference.child("Patient").child(snid).child("Password").setValue(spass);

                                Toast.makeText(SignUpOfPatient.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

        });

//-------------------------------------------------------------------------------------
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)  return "JAN";
        if(month == 2)  return "FEB";
        if(month == 3)  return "MAR";
        if(month == 4)  return "APR";
        if(month == 5)  return "MAY";
        if(month == 6)  return "JUN";
        if(month == 7)  return "JUL";
        if(month == 8)  return "AUG";
        if(month == 9)  return "SEP";
        if(month == 10) return "OCT";
        if(month == 11) return "NOV";
        if(month == 12) return "DEC";
        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

}