package com.edu.aj.onduty;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class hod_signup extends AppCompatActivity {

    private EditText inputEmail, inputPassword, cfmPassword, name;
    private Spinner spinner;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_signup);

        auth= FirebaseAuth.getInstance();

        inputEmail= (EditText) findViewById(R.id.hup_email);
        inputPassword= (EditText) findViewById(R.id.hup_pass);
        cfmPassword= (EditText) findViewById(R.id.hup_cfmpass);
        name= (EditText) findViewById(R.id.hup_name);
        spinner= (Spinner) findViewById(R.id.hup_dept);
        btnSignup= (Button) findViewById(R.id.hup_register);
        btnLogin= (Button) findViewById(R.id.hup_login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(hod_signup.this, hod_login.class));
            }
        });

        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(hod_signup.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dept_name));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email= inputEmail.getText().toString().trim();
                String password= inputPassword.getText().toString().trim();
                String confirm= inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"Enter Email Address!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),"Enter the Password!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length()<6)
                {
                    Toast.makeText(getApplicationContext(),"Password length too short, Enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirm))
                {
                    Toast.makeText(getApplicationContext(),"Password not matching!", Toast.LENGTH_SHORT).show();
                    return;
                }

//                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(hod_signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(hod_signup.this,"createUserWithEmail:onComplete:"+task.isSuccessful(),Toast.LENGTH_SHORT
                        ).show();
//                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful())
                        {
                            Toast.makeText(hod_signup.this,"Authentication Failed!!"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            startActivity(new Intent(hod_signup.this,hod_main.class));
                            finish();
                        }
                    }
                });
            }
        });

        }

        @Override
        protected void onResume()
        {
            super.onResume();
//            progressBar.setVisibility(View.GONE);
        }
}
