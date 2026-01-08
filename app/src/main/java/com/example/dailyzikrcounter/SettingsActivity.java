package com.example.dailyzikrcounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.PointerIcon;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        auth = FirebaseAuth.getInstance();

        Button buttonResetPassword = findViewById(R.id.buttonResetPassword);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            buttonResetPassword.setPointerIcon(PointerIcon.getSystemIcon(this, PointerIcon.TYPE_HAND));
            buttonLogout.setPointerIcon(PointerIcon.getSystemIcon(this, PointerIcon.TYPE_HAND));
        }

        buttonResetPassword.setOnClickListener(v -> {
            String email = auth.getCurrentUser().getEmail();
            if (email != null) {
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(SettingsActivity.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SettingsActivity.this, "Failed to send reset email.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(SettingsActivity.this, "Could not find user\'s email.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonLogout.setOnClickListener(v -> {
            auth.signOut();
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
