package com.example.amrit.hellokitty;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText et_emailid, et_password;
    private Button btn_signin, btn_signup;
    private FirebaseAuth mAuth;
    private  FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        et_emailid  = findViewById(R.id.et_emailid);
        et_password = findViewById(R.id.et_password);
        btn_signin = findViewById(R.id.btn_signin);
        btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cust_emailid = et_emailid.getText().toString();
                String cust_password = et_password.getText().toString();
                signUpFunc(cust_emailid,cust_password);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cust_emailid = et_emailid.getText().toString();
                String cust_password = et_password.getText().toString();
                signInFunc(cust_emailid,cust_password);
            }
        });

    }

    private void signInFunc(String cust_emailid, String cust_password) {

        if(cust_emailid.length() > 0 && cust_password.length() >= 8){

            mAuth.signInWithEmailAndPassword(cust_emailid, cust_password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                currentUser = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "SignIn failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{

            Toast.makeText(MainActivity.this, "Incomplete credentials", Toast.LENGTH_SHORT).show();
        }

    }

    private void signUpFunc(String cust_emailid, String cust_password) {

        if(cust_emailid.length() > 0 && cust_password.length() >= 8){

            mAuth.createUserWithEmailAndPassword(cust_emailid, cust_password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this,"Authentication Successful",Toast.LENGTH_LONG).show();
                                currentUser = mAuth.getCurrentUser();
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }else{

            Toast.makeText(MainActivity.this,"Missing Data",Toast.LENGTH_LONG).show();
        }
    }
}
