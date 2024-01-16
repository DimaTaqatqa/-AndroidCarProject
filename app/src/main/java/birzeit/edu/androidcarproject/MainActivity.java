package birzeit.edu.androidcarproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.database.Cursor;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button connectButton;
    private List<Car> carList = new ArrayList<>();

    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);


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
            for (Car car : cars) {
                System.out.println(car.toString());
            }
        }
    }
    public boolean insertCarToDatabase(Car car) {
        return dataBaseHelper.insertCar(car);
    }

}
