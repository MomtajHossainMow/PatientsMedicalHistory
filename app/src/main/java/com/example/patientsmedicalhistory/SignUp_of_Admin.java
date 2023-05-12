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

public class SignUp_of_Admin extends AppCompatActivity
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
        setContentView(R.layout.activity_sign_up_of_admin);
        //----------Copied DatePicker code for DOB--------------------------------------------------
        dateButton = findViewById(R.id.admin_DOB);
        initDatePicker();
        //name, address, nid, phone, mail, pass, confirm pass---------------------------------------
        final EditText name = findViewById(R.id.admin_name);
        final EditText address = findViewById(R.id.admin_address);
        final EditText nid = findViewById(R.id.admin_nid);
        final EditText phone = findViewById(R.id.admin_phone);
        final EditText email = findViewById(R.id.admin_mail);
        final EditText pass= findViewById(R.id.admin_password);
        final EditText pass2 = findViewById(R.id.admin_password_2);
        final EditText code = findViewById(R.id.admin_code);
        final Button signupButton = findViewById(R.id.button_admin_signup);
        //----------after clicking sign up button --------------------------------------------------
        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //---------collecting data from the signup form-------------------------------------
                final String aname = name.getText().toString();
                final String aaddress = address.getText().toString();
                final String anid = nid.getText().toString();
                final String aphone = phone.getText().toString();
                final String aemail = email.getText().toString().trim();
                final String aDOB = dateButton.getText().toString();
                final String acode = code.getText().toString().trim();
                final String apass = pass.getText().toString().trim();
                final String apass2 = pass2.getText().toString().trim();
                //----------checking validity of data ----------------------------------------------
                if (aname.isEmpty() || aaddress.isEmpty() || anid.isEmpty() || aphone.isEmpty() || aemail.isEmpty() || apass.isEmpty() || apass2.isEmpty() || acode.isEmpty())
                {
                    Toast.makeText(SignUp_of_Admin.this, "Please fill in all the information", Toast.LENGTH_SHORT).show();
                }
                else if (aphone.length() != 11)
                {
                    Toast.makeText(SignUp_of_Admin.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                }
                else if (anid.length() != 10)
                {
                    Toast.makeText(SignUp_of_Admin.this, "Enter valid NID number", Toast.LENGTH_SHORT).show();
                }
                else if (!apass.equals(apass2))
                {
                    Toast.makeText(SignUp_of_Admin.this, "Passwords are different. Enter correct password.", Toast.LENGTH_SHORT).show();
                }
                else if(!acode.equals("1122"))
                {
                    Toast.makeText(SignUp_of_Admin.this, "Enter valid admin code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("Admins").addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if (snapshot.hasChild(anid))
                            {
                                Toast.makeText(SignUp_of_Admin.this, "This NID is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                //storing data to firebase realtime database
                                databaseReference.child("Admins").child(anid).child("Name").setValue(aname);
                                databaseReference.child("Admins").child(anid).child("Address").setValue(aaddress);
                                databaseReference.child("Admins").child(anid).child("NID").setValue(anid);
                                databaseReference.child("Admins").child(anid).child("Phone").setValue(aphone);
                                databaseReference.child("Admins").child(anid).child("Email").setValue(aemail);
                                databaseReference.child("Admins").child(anid).child("Date of Birth").setValue(aDOB);
                                databaseReference.child("Admins").child(anid).child("Password").setValue(apass);

                                Toast.makeText(SignUp_of_Admin.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {
                            Toast.makeText(SignUp_of_Admin.this, "Registration failed.", Toast.LENGTH_SHORT).show();
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