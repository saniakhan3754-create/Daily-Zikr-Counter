package com.example.dailyzikrcounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.PointerIcon;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnGoToCounter = findViewById(R.id.btnGoToCounter);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            btnGoToCounter.setPointerIcon(PointerIcon.getSystemIcon(this, PointerIcon.TYPE_HAND));
            buttonLogout.setPointerIcon(PointerIcon.getSystemIcon(this, PointerIcon.TYPE_HAND));
        }

        btnGoToCounter.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, MainActivity.class)));

        buttonLogout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("DailyZikrCounterPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }
}
