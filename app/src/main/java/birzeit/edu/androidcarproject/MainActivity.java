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
    private List<Car> carListDB = new ArrayList<>();

    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper = new DataBaseHelper(this);

//        Admin admin;
//        try {
//            admin = new Admin("Ahmadsalah10@gmail.com", "Ahmad123@", "Ahmad", "Salah", "Male", "Palestine", "Jerusalem", "0599123456", Files.readAllBytes(Paths.get("C:\\Users\\NOVO\\Desktop\\H.JPG")));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        dataBaseHelper.insertAdmin(admin);


        connectButton = findViewById(R.id.connectButton);
//        carList = dataBaseHelper.getAllCars();

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Execute the AsyncTask to fetch data
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("https://658582eb022766bcb8c8c86e.mockapi.io/api/mock/rest-apis/encs5150/car-types");
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
            carList.clear(); // Clear the existing list
            carList.addAll(cars); // Add all cars to yourArrayList
            // Now you have the list of cars in yourArrayList
            // Print the ArrayList to the console
            for (Car car : carList) {
                System.out.println(car.toString());
            }

        }
        // Proceed with any other actions or UI updates
        printCars(carListDB);
    }

    private void printCars(List<Car> carList) {
        System.out.println( "in print function");

        for (Car car : carList) {
            // Print car information using System.out.println or Log.d
            System.out.println( car.toString());
        }
    }
}
