package com.example.patientsmedicalhistory;

public class RetrievePrescriptions {
    String Doctor_ID, Doctor_Name, Patient_ID, Patient_Name, Prescription,Symptoms,Advices;
    long prescriptionNo;

    public String getDoctor_ID() {
        return Doctor_ID;
    }

    public String getDoctor_Name() {
        return Doctor_Name;
    }

    public String getPatient_ID() {
        return Patient_ID;
    }

    public String getPatient_Name() {
        return Patient_Name;
    }

    public String getPrescription() {
        return Prescription;
    }

    public String getSymptoms() {
        return Symptoms;
    }

    public String getAdvices() {
        return Advices;
    }

    public long getPrescriptionNo() {
        return prescriptionNo;
    }
}
