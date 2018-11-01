package com.example.aeiys.myapplication6;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextUsername , editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUsername = (EditText)findViewById(R.id.editText4);
        editTextEmail = (EditText)findViewById(R.id.editText3);
        editTextPassword = (EditText)findViewById(R.id.editText5);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!= null){
            //handle the already register
            //Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();


        }
    }

    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String useremail = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty()) {
            editTextUsername.setError("Name is required");
            editTextUsername.requestFocus();
            return;
        }

        if (useremail.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            editTextEmail.setError("Please enter a valid email ");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(useremail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(username,useremail);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"User Registered Successful",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else{
                    /*if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {*/
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    //}
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                registerUser();
                break;

            //case R.id.button2:
            //    startActivity(new Intent(this,MainActivity.class));
            //   break;
        }

    }
}
