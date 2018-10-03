package com.edu.aj.onduty;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class hod_login extends AppCompatActivity {


    private EditText inputEmail, inputPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private Button btnLogin, btnReset, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth= FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(hod_login.this, hod_main.class));
            finish();
        }

        setContentView(R.layout.activity_hod_login);

        inputEmail= (EditText) findViewById(R.id.hlog_email);
        inputPassword= (EditText) findViewById(R.id.hlog_pass);
        progressBar= (ProgressBar) findViewById(R.id.hlog_progressBar);
        btnLogin= (Button) findViewById(R.id.hlog_btn);
        btnReset= (Button) findViewById(R.id.hlog_frg);
        btnSignup= (Button) findViewById(R.id.hlog_mem);

        firebaseAuth= FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(hod_login.this, hod_signup.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(hod_login.this, hod_reset.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= inputEmail.getText().toString();
                final String password= inputPassword.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"Enter Email Address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),"Enter the Password!", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(hod_login.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                inputPassword.setError("Password too short, enter minimum 6 characters!");
                            } else {
                                Toast.makeText(hod_login.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(hod_login.this, hod_main.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        });

    }
}
