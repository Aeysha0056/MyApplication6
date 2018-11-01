package com.example.aeiys.myapplication6;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;


public class AddPatientActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTextMessage;
    EditText editTextPatientName , editTextDescription, editTextAge;
    ProgressBar progressBar;
    FirebaseDatabase database;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        editTextPatientName = (EditText)findViewById(R.id.editText_PatientName);
        editTextDescription = (EditText)findViewById(R.id.editText_description);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

         //database = FirebaseDatabase.getInstance();

        findViewById(R.id.floatingActionAddButton).setOnClickListener(this);

    }

    public void registerPatient(){
       final String patientName = editTextPatientName.getText().toString().trim();
       final String diseaseDescription = editTextDescription.getText().toString().trim();
       final String patientAge = editTextAge.getText().toString().trim();

        if (patientName.isEmpty()) {
            editTextPatientName.setError("Patient Name is required");
            editTextPatientName.requestFocus();
            return;
        }
        if (diseaseDescription.isEmpty()) {
            editTextDescription.setError("Description is required");
            editTextDescription.requestFocus();
            return;
        }

        if (patientAge.isEmpty()) {
            editTextAge.setError("Patient Age is required");
            editTextAge.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //String patientId = database.getReference().child("Patients").push().getKey();
        Patient patient = new Patient(patientName,diseaseDescription,patientAge);
        FirebaseDatabase.getInstance().getReference("Patients")
                .child(FirebaseDatabase.getInstance().getReference().push().getKey())
                .setValue(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Patient Registered Successful",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingActionAddButton:
                registerPatient();
                break;
        }

    }
}
