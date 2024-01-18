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
        // Call the method to print the details of the inserted car
    }

    public void setButtonText(String text) {
        connectButton.setText(text);
    }

    public void handleCarsList(List<Car> cars) {
        // Handle the list of cars as needed, for example, fill them into an ArrayList
        if (cars != null) {
            // Create an instance of your database helper
            DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this,"Cars_Dealer",null,20);

            // Get a writable database
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Begin a transaction to ensure atomicity
            db.beginTransaction();

            try {
                // Drop the existing Car table if it exists
                db.execSQL("DROP TABLE IF EXISTS Car");

                // Recreate the Car table
                dbHelper.onCreate(db);

                // Insert each car into the database
                for (Car car : cars) {
                    boolean isInserted = dbHelper.insertCar(car);
                    if (isInserted) {
                        // Car inserted successfully
                        Log.d("Database", "Car inserted successfully");
                    } else {
                        // Error inserting car
                        Log.d("Database", "Error inserting car");
                    }
                }

                // Set the transaction as successful
                db.setTransactionSuccessful();
            } catch (Exception e) {
                // Handle the exception, log, or throw as needed
                e.printStackTrace();
            } finally {
                // End the transaction
                db.endTransaction();
                // Close the database
                db.close();
            }
        }
    }

    private boolean insertCarIntoDatabase(Car car) {
        // Create an instance of your database helper
        DataBaseHelper dbHelper =new DataBaseHelper(MainActivity.this,"Cars_Dealer",null,20);

        // Insert the car into the database
        return dbHelper.insertCar(car);
    }

}
