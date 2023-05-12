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

public class SignUp_of_Doctor extends AppCompatActivity
{
    //--------------- Database connecting URL ------------------------------------------------------
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");
    //----------------- Date Picker ----------------------------------------------------------------
    private DatePickerDialog datePickerDialog;
    private TextView dateButton;
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_of_doctor);
        //----------Copied DatePicker code for DOB--------------------------------------------------
        dateButton = findViewById(R.id.doctor_DOB);
        initDatePicker();
        //name, address, nid, phone, mail, pass, confirm pass---------------------------------------
        final EditText name = findViewById(R.id.admin_profile_name);
        final EditText address = findViewById(R.id.admin_profile_address);
        final EditText nid = findViewById(R.id.admin_profile_nid);
        final EditText phone = findViewById(R.id.admin_profile_phone);
        final EditText email = findViewById(R.id.admin_profile_main);
        final EditText pass= findViewById(R.id.doctor_password);
        final EditText pass2 = findViewById(R.id.doctor_password_2);
        final Button signupButton = findViewById(R.id.button_doctor_signup);
        //----------after clicking sign up button --------------------------------------------------
        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //---------collecting data from the signup form-------------------------------------
                final String dname = name.getText().toString();
                final String daddress = address.getText().toString();
                final String dnid = nid.getText().toString();
                final String dphone = phone.getText().toString();
                final String demail = email.getText().toString();
                final String dDOB = dateButton.getText().toString();
                final String dpass = pass.getText().toString();
                final String dpass2 = pass2.getText().toString();
                //----------checking validity of data ----------------------------------------------
                if (dname.isEmpty() || daddress.isEmpty() || dnid.isEmpty() || dphone.isEmpty() || demail.isEmpty() || dpass.isEmpty() || dpass2.isEmpty())
                {
                    Toast.makeText(SignUp_of_Doctor.this, "Please fill all the information", Toast.LENGTH_SHORT).show();
                }
                else if (dphone.length() != 11)
                {
                    Toast.makeText(SignUp_of_Doctor.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                }
                else if (dnid.length() != 10)
                {
                    Toast.makeText(SignUp_of_Doctor.this, "Enter valid NID number", Toast.LENGTH_SHORT).show();
                }
                else if (!dpass.equals(dpass2))
                {
                    Toast.makeText(SignUp_of_Doctor.this, "Passwords are different. Enter correct password.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("Doctors").addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if (snapshot.hasChild(dnid))
                            {
                                Toast.makeText(SignUp_of_Doctor.this, "This NID is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                //storing data to firebase realtime database
                                databaseReference.child("Doctors").child(dnid).child("Name").setValue(dname);
                                databaseReference.child("Doctors").child(dnid).child("Address").setValue(daddress);
                                databaseReference.child("Doctors").child(dnid).child("NID").setValue(dnid);
                                databaseReference.child("Doctors").child(dnid).child("Phone").setValue(dphone);
                                databaseReference.child("Doctors").child(dnid).child("Email").setValue(demail);
                                databaseReference.child("Doctors").child(dnid).child("Date of Birth").setValue(dDOB);
                                databaseReference.child("Doctors").child(dnid).child("Password").setValue(dpass);
                                databaseReference.child("Doctors").child(dnid).child("Specifications").setValue("");

                                Toast.makeText(SignUp_of_Doctor.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {
                            Toast.makeText(SignUp_of_Doctor.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    //-------------------------------Date picker----------------------------------------------------
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
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

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
//--------------------------------------------------------------------------------------------------
}