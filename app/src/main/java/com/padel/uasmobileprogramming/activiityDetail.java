package com.padel.uasmobileprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class activiityDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activiity_detail);

        String num = "+6282175572310";
        String text = "Hello";
        Button btnCp = findViewById(R.id.cp);
        btnCp.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+num+"&text="+text));
            startActivity(intent);

//                Toast.makeText(getActivity(),"whastapp no instaled",Toast.LENGTH_SHORT).show();

        });
    }
}