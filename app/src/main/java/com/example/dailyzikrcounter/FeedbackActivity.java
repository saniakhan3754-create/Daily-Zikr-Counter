package com.example.dailyzikrcounter;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        EditText editTextFeedback = findViewById(R.id.editTextFeedback);
        Button buttonSubmitFeedback = findViewById(R.id.buttonSubmitFeedback);

        buttonSubmitFeedback.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String feedback = editTextFeedback.getText().toString();

            // TODO: Implement feedback submission to Firebase
            if (rating > 0 && !feedback.isEmpty()) {
                Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                // Here you would typically send the data to Firebase
                ratingBar.setRating(0);
                editTextFeedback.setText("");
            } else {
                Toast.makeText(this, "Please provide a rating and a comment.", Toast.LENGTH_SHORT).show();
            }
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
