package com.padel.uasmobileprogramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activityLogin extends AppCompatActivity {

    //deklarasi
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView txtSignup;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inisialisai
        etEmail     = findViewById(R.id.etUsername);
        etPassword  = findViewById(R.id.etPassword);
        btnLogin    = findViewById(R.id.btnLogin);
        txtSignup   = findViewById(R.id.txtsignUp);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //progresDialog
        progressDialog = new ProgressDialog(activityLogin.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        //Goto Register
        txtSignup.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), activityRegister.class));
        });

        //login
        btnLogin.setOnClickListener(view -> {

            //check field
            if(etEmail.getText().length() > 0 && etPassword.getText().length() > 0){
                login(etEmail.getText().toString(), etPassword.getText().toString());
            }else{
                Toast.makeText(this, "Username & Password Masih Kosong! ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful() && task.getResult() != null){
                   if(task.getResult().getUser() != null){
                        reload();
                   }else{
                       Toast.makeText(activityLogin.this, "Login Gagal!", Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(activityLogin.this, "Login Gagal!", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private void reload(){
        startActivity(new Intent(getApplicationContext(), activityDashboard.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

}