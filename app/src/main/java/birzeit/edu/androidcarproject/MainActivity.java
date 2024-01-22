package birzeit.edu.androidcarproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button connectButton;
    private List<Car> carList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Execute the AsyncTask to fetch data
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("https://androidproject.free.beeceptor.com/cars");

            }
        });

        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, "Cars_Dealer", null, 21);

        String hashedPassword = PasswordHasher.hashPassword("Ahmad123@");
        Admin admin = new Admin("Ahmadsalah@hotmail.com", hashedPassword, "Ahmad", "Salah", "Male", "Palestine", "Jerusalem", "970595959595", null);
        admin.setUserType(1);
        dbHelper.insertAdmin(admin);

    }

    public void setButtonText(String text) {
        connectButton.setText(text);
    }

    public void handleCarsList(List<Car> cars) {
        // Handle the list of cars as needed, for example, fill them into an ArrayList
        if (cars != null) {
            // Create an instance of your database helper
            DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, "Cars_Dealer", null, 21);

            for (Car car : cars) {
                boolean isInserted = dbHelper.insertCar(car);

                if (isInserted) {
                    // Car inserted successfully
                    Log.d("Database", "Car inserted successfully");
                } else {
                    // Car already exists in the database
                    Log.d("Database", "Car already exists in the database");
                    // You can choose to update the existing record or handle it in some other way
                }
            }
        }
    }

}
