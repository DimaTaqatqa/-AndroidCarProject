package birzeit.edu.androidcarproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import birzeit.edu.androidcarproject.ui.carmenu.CarMenuFragment;

public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        // Find views
        EditText editTextReview = findViewById(R.id.editTextReview);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Set click listener for the Save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get review text and rating
                String reviewText = editTextReview.getText().toString();
                float rating = ratingBar.getRating();

                // Show a Toast message
                String toastMessage = "Thank you for your feedback!";
                Toast.makeText(RatingActivity.this, toastMessage, Toast.LENGTH_SHORT).show();


                finish();
            }
        });
    }
}
