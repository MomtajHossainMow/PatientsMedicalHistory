package com.example.patientsmedicalhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class Modify_Doctors_Data extends AppCompatActivity {

    //--------------- Database connecting URL ------------------------------------------------------
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-history-default-rtdb.firebaseio.com/");
    //----------------- Date Picker ----------------------------------------------------------------
    private DatePickerDialog datePickerDialog;
    private TextView dateButton;
    private Button btnUpdate, btnDelete;
    //Variable Declaration----------------------------------------------------------------------
    private TextView name, address, nid, dob, contact, mail, specification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_doctors_data);
        setTitle("Modify Doctor Profile");

        //----------Copied DatePicker code for DOB--------------------------------------------------
        dateButton = findViewById(R.id.doctor_profile_dob);
        initDatePicker();
        //collect passed data from login page-------------------------------------------------------
        String ID = getIntent().getStringExtra("DoctorID");
        //connecting the variables with text views--------------------------------------------------
        name = findViewById(R.id.doctor_profile_name);
        address = findViewById(R.id.doctor_profile_address);
        nid = findViewById(R.id.doctor_profile_nid);
        dob = findViewById(R.id.doctor_profile_dob);
        contact = findViewById(R.id.doctor_profile_phone);
        mail = findViewById(R.id.doctor_profile_mail);
        specification = findViewById(R.id.doctor_profile_specification);
        btnUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);
        //set doctor info in textview----------------------------------------------------------------
        databaseReference.child("Doctors").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Check whether this user id exists or not------------------------------------------
                if (snapshot.hasChild(ID)) {
                    final String Name = snapshot.child(ID).child("Name").getValue(String.class);
                    final String Address = snapshot.child(ID).child("Address").getValue(String.class);
                    final String Phone = snapshot.child(ID).child("Phone").getValue(String.class);
                    final String Email = snapshot.child(ID).child("Email").getValue(String.class);
                    final String NID = snapshot.child(ID).child("NID").getValue(String.class);
                    final String DOB = snapshot.child(ID).child("Date of Birth").getValue(String.class);
                    final String spec = snapshot.child(ID).child("Specifications").getValue(String.class);
                    //Setting the values collected from database------------------------------------
                    name.setText(Name);
                    address.setText(Address);
                    nid.setText(NID);
                    nid.setEnabled(false); // making it unable so that the user can't change the nid
                    dob.setText(DOB);
                    contact.setText(Phone);
                    mail.setText(Email);
                    specification.setText(spec);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(Modify_Doctors_Data.this, "User Data Retrieval Failed", Toast.LENGTH_SHORT).show();
            }
        });

        //After clicking the update button--------------------------------------------------------------------
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String anid = nid.getText().toString();
                String aname = name.getText().toString();
                String aaddress = address.getText().toString();
                String aphone = contact.getText().toString();
                String amail = mail.getText().toString();
                String adob = dob.getText().toString();
                String aspec = specification.getText().toString();
                HashMap hashMap = new HashMap();
                hashMap.put("Name",aname);
                hashMap.put("Address", aaddress);
                hashMap.put("Phone", aphone);
                hashMap.put("Email",amail);
                hashMap.put("Date of Birth", adob);
                hashMap.put("Specifications", aspec);

                databaseReference.child("Doctors").child(anid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Modify_Doctors_Data.this, "Data is Successfully Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
        //After clicking the delete button--------------------------------------------------------------------
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Modify_Doctors_Data.this)
                        .setTitle("Delete Doctor Profile")
                        .setMessage("Are you sure you want to delete this profile?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                databaseReference.child("Doctors").child(ID).removeValue();
                                Toast.makeText(Modify_Doctors_Data.this, "Your Doctor Profile Is Deleted.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("NO", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    //-------------------------------Date picker----------------------------------------------------
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
