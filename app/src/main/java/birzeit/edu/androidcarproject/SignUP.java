package birzeit.edu.androidcarproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.hbb20.CountryCodePicker;

public class SignUP extends AppCompatActivity {
    Button sign_up_button;

    EditText email_EditText;
    EditText password_EditText;
    EditText confirmPassword_editText;
    EditText firstName_EditText;
    EditText lastName_EditText;
    EditText phone_EditText;
//    CountryCodePicker ccp;
    DataBaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new DataBaseHelper(SignUP.this,"Cars_Dealer",null,20);

        sign_up_button = findViewById(R.id.signUpButton);
        email_EditText = findViewById(R.id.emailInput);
        password_EditText = findViewById(R.id.passwordInput);
        confirmPassword_editText = findViewById(R.id.confirmPassInput);
        firstName_EditText = findViewById(R.id.firstNameInput);
        lastName_EditText = findViewById(R.id.lastNameInput);
        phone_EditText = findViewById(R.id.editTextPhone);
//        ccp = findViewById(R.id.ccp);

        String[] gender_options = {"Male", "Female"};
        final Spinner genderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gender_options);
        genderSpinner.setAdapter(objGenderArr);

        String[] country_options = {"Palestine", "Italy", "Hungary", "Germany"};
        final Spinner countrySpinner = (Spinner) findViewById(R.id.spinner_country);
        ArrayAdapter<String> objCountryArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, country_options);
        countrySpinner.setAdapter(objCountryArr);

        String[] city_options = {"Jerusalem", "Ramallah", "Akka",
                "Messina", "Venice", "Florance", "Taormina",
                "Budapest", "Debrecen", "Szeged", "Miskolc",
                "Berlin", "Potsdam", "Munich", "Düsseldorf"};
        final Spinner citySpinner = (Spinner) findViewById(R.id.spinner_city);
        ArrayAdapter<String> objCityArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, city_options);
        citySpinner.setAdapter(objCityArr);


        Intent intent = getIntent();
        if (intent != null) {
            int userTypeReceived = intent.getIntExtra("userType", -1);
            if (userTypeReceived != 1 && userTypeReceived != 2)
                Toast.makeText(SignUP.this, "Error! Unknown user type.", Toast.LENGTH_LONG).show();

            sign_up_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validateFields()) {
                        String gender = genderSpinner.getSelectedItem().toString();
                        String country = countrySpinner.getSelectedItem().toString();
                        String city = citySpinner.getSelectedItem().toString();
                        if (gender == null)
                            Toast.makeText(SignUP.this, "Choose a gender, please!", Toast.LENGTH_SHORT).show();

                        else if (country == null)
                            Toast.makeText(SignUP.this, "Choose a country, please!", Toast.LENGTH_SHORT).show();

                        else if (city == null)
                            Toast.makeText(SignUP.this, "Choose a city, please!", Toast.LENGTH_SHORT).show();
                        else {
                            String email = email_EditText.getText().toString().toLowerCase();
                            String password = password_EditText.getText().toString();
                            String hashedPassword = PasswordHasher.hashPassword(password);

                            String firstName = firstName_EditText.getText().toString();
                            String lastName = lastName_EditText.getText().toString();
                            String phone = phone_EditText.getText().toString();

                            // After checking if the phone field is not empty
//                            String fullPhoneNumber = ccp.getFullNumber() + phone;
                            String fullPhoneNumber = phone;

                            // An admin is chosen
                            if (userTypeReceived == 1) {
                                Admin admin = new Admin();
                                admin.setEmail(email);
                                admin.setPassword(hashedPassword);
                                admin.setPhone(fullPhoneNumber);
                                admin.setFirstName(firstName);
                                admin.setLastName(lastName);
                                admin.setGender(gender);
                                admin.setCountry(country);
                                admin.setCity(city);
                                admin.setUserType(userTypeReceived);

                                DataBaseHelper db  = new DataBaseHelper(SignUP.this,"Cars_Dealer",null,21);
                                if (db.emailExists("Admin", admin.getEmail()) || db.emailExists("Customer", admin.getEmail())) {
                                    Toast.makeText(SignUP.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    db.insertAdmin(admin);
                                    Toast.makeText(SignUP.this, "Successfully Signed Up!", Toast.LENGTH_LONG).show();
                                    Intent intent =new Intent(SignUP.this, RegistrationAndLogin.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            // A customer is chosen
                            else if (userTypeReceived == 2) {
                                Customer customer = new Customer();
                                customer.setEmail(email);
                                customer.setPassword(hashedPassword);
                                customer.setPhone(fullPhoneNumber);
                                customer.setFirstName(firstName);
                                customer.setLastName(lastName);
                                customer.setGender(gender);
                                customer.setCountry(country);
                                customer.setCity(city);
                                customer.setUserType(userTypeReceived);

                                DataBaseHelper db = db = new DataBaseHelper(SignUP.this,"Cars_Dealer",null,21);
                                if (db.emailExists("Customer", customer.getEmail()) || db.emailExists("Admin", customer.getEmail())) {
                                    Toast.makeText(SignUP.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    db.insertCustomer(customer);
                                    Toast.makeText(SignUP.this, "Successfully Signed Up!", Toast.LENGTH_LONG).show();
                                    Intent intent =new Intent(SignUP.this, RegistrationAndLogin.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(SignUP.this, "Error! Unknown user type.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });
        }
    }

    private boolean validateFields() {

        String email = email_EditText.getText().toString().toLowerCase();
        String password = password_EditText.getText().toString();
        String confirmPassword = confirmPassword_editText.getText().toString();
        String firstName = firstName_EditText.getText().toString();
        String lastName = lastName_EditText.getText().toString();
        String phone = phone_EditText.getText().toString();

        // Set the initial background color to white
        email_EditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        password_EditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        confirmPassword_editText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        firstName_EditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        lastName_EditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        phone_EditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        boolean isValid = true;
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_LONG).show();
            email_EditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }else if (password.isEmpty() || password.length() < 5 || !password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$")) {
            Toast.makeText(this, "Fill the password field, it must not be less than 5 characters and must include at least 1\n" +
                    "character, 1 number, and one special character (@#$%^&+=)", Toast.LENGTH_LONG).show();
            password_EditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        } else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            Toast.makeText(this, "Confirm password must match password", Toast.LENGTH_LONG).show();
            confirmPassword_editText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }  else if (firstName.isEmpty() || firstName.length() < 3) {
            Toast.makeText(this, "First name must be more than 2 characters", Toast.LENGTH_LONG).show();
            firstName_EditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        } else if (lastName.isEmpty() || lastName.length() < 3) {
            Toast.makeText(this, "Last name must be more than 2 characters", Toast.LENGTH_LONG).show();
            lastName_EditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        } else if (phone.isEmpty() || !phone.matches("[0-9]+")) {
            Toast.makeText(this, "Fill in the phone with numbers only please", Toast.LENGTH_LONG).show();
            phone_EditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isValid = false;
        }
        return isValid;
    }
}
