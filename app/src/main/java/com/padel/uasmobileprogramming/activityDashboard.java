package com.padel.uasmobileprogramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activityDashboard extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private TextView txtFullname, txtLogout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new homeFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.fragment1:
                        selectedFragment = new homeFragment();
                        break;
                    case R.id.fragment2:
                        selectedFragment = new exploreFragment();
                        break;
                    case R.id.fragment3:
                        selectedFragment = new profileFragment();
                        break;
                    case R.id.fragment4:
                        selectedFragment = new aboutFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
                return true;
            }
        });

//        txtFullname = findViewById(R.id.fullname);
//        txtLogout   = findViewById(R.id.logout);
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if(firebaseUser != null){
//            txtFullname.setText(firebaseUser.getDisplayName());
//        }else{
//            Toast.makeText(this, "Login Gagal!", Toast.LENGTH_SHORT).show();
//        }
//
//        txtLogout.setOnClickListener(view -> {
//            FirebaseAuth.getInstance().signOut();
//            startActivity(new Intent(getApplicationContext(), activityLogin.class));
//            finish();
//        });
//
    }
}