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
import com.google.firebase.auth.UserProfileChangeRequest;

public class activityRegister extends AppCompatActivity {

    //deklarasi
    private EditText etFullname, etEmail, etPassword;
    private Button  btnRegister;
    private TextView txtSignIn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //inisialisai
        etFullname  = findViewById(R.id.etFullname);
        etEmail     = findViewById(R.id.etEmail);
        etPassword  = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtSignIn   = findViewById(R.id.txtSignIn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //progresDialog
        progressDialog = new ProgressDialog(activityRegister.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        //goto login
        txtSignIn.setOnClickListener(view -> {
            finish();
        });

        //register
        btnRegister.setOnClickListener(view -> {

            if(etFullname.getText().length() > 0 && etEmail.getText().length() > 0 && etPassword.getText().length() > 0){
                register(etFullname.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
            }else{
                Toast.makeText(this, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //fungsi register
    private void register(String fullname, String email, String password){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult() != null){
                    FirebaseUser user = task.getResult().getUser();
                    if(user != null) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullname)
                                .build();
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    }else{
                        Toast.makeText(activityRegister.this, "Register Gagal!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(activityRegister.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            }
        });
    }

    //fungsi reload
    private void reload(){
        startActivity(new Intent(getApplicationContext(), activityDashboard.class));
    }

    //cek auth
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