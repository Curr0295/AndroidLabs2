package com.example.androidlabs2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText;
    private Button nextButton;
    private static final int NAME_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.namePlace);
        nextButton = findViewById(R.id.nextbtn);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String savedName = preferences.getString("userName", "");

        nameEditText.setText(savedName);

        nextButton.setOnClickListener(v -> {
            String enteredName = nameEditText.getText().toString();

            startActivityForResult(new Intent(MainActivity.this, NameActivity.class)
                    .putExtra("userName", enteredName), NAME_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        getPreferences(MODE_PRIVATE).edit()
                .putString("userName", nameEditText.getText().toString())
                .apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NAME_ACTIVITY_REQUEST_CODE) {
            if (resultCode == 0) {
           } else if (resultCode == 1) {
                finish();
            }
        }
    }
}