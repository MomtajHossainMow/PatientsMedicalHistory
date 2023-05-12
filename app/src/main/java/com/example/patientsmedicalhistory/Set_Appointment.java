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

public class Set_Appointment extends AppCompatActivity
{
    //--------------- Database connecting URL ------------------------------------------------------
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");
    //----------------- Date Picker ----------------------------------------------------------------
    private DatePickerDialog datePickerDialog;
    private TextView dateButton;
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment);
        //----------Copied DatePicker code for DOB--------------------------------------------------
        dateButton = findViewById(R.id.date);
        initDatePicker();
        //Connecting text fields--------------------------------------------------------------------
        final EditText PatientID = findViewById(R.id.text_PID);
        final EditText DoctorID = findViewById(R.id.text_DID);
        final Button setAppointment = findViewById(R.id.btn_setAppointment);

        //----On Click------------------------------------------------------------------------------
        setAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //collecting data-------------------------------------------------------------------
                final String pid = PatientID.getText().toString().trim();
                final String did = DoctorID.getText().toString().trim();
                final String date = dateButton.getText().toString();
                if(pid.isEmpty()||did.isEmpty()||date.isEmpty())
                {
                    Toast.makeText(Set_Appointment.this,"Please enter all the values", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("Patient").addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.hasChild(pid))
                            {
                                Toast.makeText(Set_Appointment.this, "This patient ID doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                databaseReference.child("Doctors").addListenerForSingleValueEvent(new ValueEventListener(){
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (!snapshot.hasChild(did))
                                        {
                                            Toast.makeText(Set_Appointment.this, "This Doctor ID doesn't exist", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            databaseReference.child("Appointments").addListenerForSingleValueEvent(new ValueEventListener()
                                            {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot)
                                                {
                                                    //Storing data to database table
                                                    databaseReference.child("Appointments").child(pid+did).child("Patient_ID").setValue(pid);
                                                    databaseReference.child("Appointments").child(pid+did).child("Doctor_ID").setValue(did);
                                                    databaseReference.child("Appointments").child(pid+did).child("Date").setValue(date);

                                                    Toast.makeText(Set_Appointment.this, "Make Appointment Successful", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
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

    }

    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month) + " " + year;
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