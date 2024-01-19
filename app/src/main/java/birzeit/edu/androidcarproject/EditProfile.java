package birzeit.edu.androidcarproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import birzeit.edu.androidcarproject.ui.profile.ProfileFragment;


public class EditProfile extends AppCompatActivity {
    private DataBaseHelper db;
    private int userType;
    private static final int PICK_IMAGE_REQUEST = 1;

    private SharedViewModel sharedViewModel;
    ImageView ivProfilePicture;
    Button photoButton;

    EditText editFirstName;
    EditText editLastName;
    EditText editPassword;
    EditText editConfirmPassword;
    EditText editPhone;
    Button save;
    String email = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        db = new DataBaseHelper(this);
        // Obtain the shared ViewModel from the hosting activity
        // Retrieve email from the Fragment
        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra("email");
        } else {
            Toast.makeText(EditProfile.this, "Failed to Update! Email: " + email, Toast.LENGTH_LONG).show();

        }
        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);
        editPhone = (EditText) findViewById(R.id.editPhone);
        save = (Button) findViewById(R.id.saveButton);
        ivProfilePicture = findViewById(R.id.iv_inst_pic);
        photoButton = (Button) findViewById(R.id.changePhotoButton);


        userType = db.getUserType(email);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateFields()) {
                    String firstName = editFirstName.getText().toString();
                    String lastName = editLastName.getText().toString();
                    String password = editPassword.getText().toString();
                    String hashedPassword = PasswordHasher.hashPassword(password);
                    String phone = editPhone.getText().toString();
                    // Obtain the shared ViewModel from the hosting activity


                    if (userType == 1) {
                        Admin admin = new Admin();
                        admin.setFirstName(firstName);
                        admin.setLastName(lastName);
                        admin.setPassword(hashedPassword);
                        admin.setPhone(phone);

                        if (db.updateAdmin(email, admin)) {
                            Toast.makeText(EditProfile.this, "Successfully Updated!", Toast.LENGTH_LONG).show();

                            // Navigate back to CustomerHomeActivity and indicate the need to navigate to the Profile fragment
                            Intent intent = new Intent(EditProfile.this, CustomerHomeActivity.class);
                            intent.putExtra("navigateToProfile", true);
                            intent.putExtra("email", email);  // Include the email as an extra
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(EditProfile.this, "Failed to Update!", Toast.LENGTH_LONG).show();

                        }
                    } else if (userType == 2) {
                        Customer customer = new Customer();
                        customer.setFirstName(firstName);
                        customer.setLastName(lastName);
                        customer.setPassword(hashedPassword);
                        customer.setPhone(phone);

                        if (db.updateCustomer(email, customer)) {
                            Toast.makeText(EditProfile.this, "Successfully Updated!", Toast.LENGTH_LONG).show();

                            // Navigate back to CustomerHomeActivity and indicate the need to navigate to the Profile fragment
                            Intent intent = new Intent(EditProfile.this, CustomerHomeActivity.class);
                            intent.putExtra("navigateToProfile", true);
                            intent.putExtra("email", email);  // Include the email as an extra

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(EditProfile.this, "Failed to Update!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(EditProfile.this, "Error! Unknown user type.", Toast.LENGTH_LONG).show();
                    }


                }
            }


        });
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

// Handle the result of the image picker
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                try {
                    // Get the dimensions of the View
                    int targetW = ivProfilePicture.getWidth();
                    int targetH = ivProfilePicture.getHeight();

                    // Get the dimensions of the bitmap
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, bmOptions);
                    int photoW = bmOptions.outWidth;
                    int photoH = bmOptions.outHeight;

                    int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                    bmOptions.inJustDecodeBounds = false;
                    bmOptions.inSampleSize = scaleFactor;
                    bmOptions.inPurgeable = true; // if necessary purge pixels into disk

                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, bmOptions);
                    ivProfilePicture.setImageBitmap(bitmap);

                    // Convert the Bitmap to a byte array
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] photo = byteArrayOutputStream.toByteArray();

                    // Update the photo in the database
                    if (db.updatePhoto(email, userType, photo)) {
                        Toast.makeText(EditProfile.this, "Successfully Updated Photo!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EditProfile.this, "Failed to Update Photo!", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        private boolean validateFields() {

        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String password = editPassword.getText().toString();
        String confirmPassword = editConfirmPassword.getText().toString();
        String phone = editPhone.getText().toString();

        // Set the initial background color to white
        editFirstName.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        editLastName.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        editPassword.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        editConfirmPassword.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        editPhone.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        boolean isValid = true;
        if (firstName.isEmpty() || firstName.length() < 3) {
            Toast.makeText(this, "First name must be more than 2 characters", Toast.LENGTH_LONG).show();
            editFirstName.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        } else if (lastName.isEmpty() || lastName.length() < 3) {
            Toast.makeText(this, "Last name must be more than 2 characters", Toast.LENGTH_LONG).show();
            editLastName.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        } else if (password.isEmpty() || password.length() < 5 || !password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$")) {
            Toast.makeText(this, "Fill the password field, it must not be less than 5 characters and must include at least 1\n" +
                    "character, 1 number, and one special character (@#$%^&+=)", Toast.LENGTH_LONG).show();
            editPassword.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        } else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            Toast.makeText(this, "Confirm password must match password", Toast.LENGTH_LONG).show();
            editConfirmPassword.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        } else if (phone.isEmpty() || !phone.matches("[0-9]+")) {
            Toast.makeText(this, "Fill in the phone with numbers only please", Toast.LENGTH_LONG).show();
            editPhone.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }
        return isValid;
    }
}