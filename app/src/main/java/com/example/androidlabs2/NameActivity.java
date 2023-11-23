package com.example.androidlabs2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NameActivity extends AppCompatActivity {
    private TextView welcomeTextView;
    private Button dontCallMeThatButton;
    private Button thankYouButton;

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        welcomeTextView = findViewById(R.id.welcoming);

        dontCallMeThatButton = findViewById(R.id.dontCallMeThat);

        thankYouButton = findViewById(R.id.thankYou);

        String userName = getIntent().getStringExtra("userName");

        welcomeTextView.setText(getString(R.string.welcoming, userName));

        thankYouButton.setOnClickListener(v -> {
            setResult(1);
            finish();
        });

        dontCallMeThatButton.setOnClickListener(v -> {
            Intent intent = new Intent(NameActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}