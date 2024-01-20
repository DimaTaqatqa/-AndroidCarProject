package birzeit.edu.androidcarproject.ui.carmenu;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import birzeit.edu.androidcarproject.Car;
import birzeit.edu.androidcarproject.CarAdapter;
import birzeit.edu.androidcarproject.DataBaseHelper;
import birzeit.edu.androidcarproject.MainActivity;
import birzeit.edu.androidcarproject.R;

public class CarMenuFragment extends Fragment {
    private ArrayList<Car> cars = new ArrayList<>();
    private String customerEmail; // Add a field to store the customer's email

    public CarMenuFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fetchDataFromDatabase();
        return inflater.inflate(R.layout.fragment_car_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Assuming you have an ArrayList<Car> named 'cars'
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCarMenu);
        // Retrieve the customer's email from the intent extras
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }

        CarAdapter carAdapter = new CarAdapter(cars, customerEmail);

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(carAdapter);
    }
    private void fetchDataFromDatabase() {
        // Create an instance of your database helper
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Cars_Dealer", null, 21);

        // Retrieve the list of cars from the database
        List<Car> retrievedCars = dbHelper.getAllCars();

        // Clear the existing list before adding new data
        cars.clear();

        // Add the retrieved cars to your existing list or use it as needed
        cars.addAll(retrievedCars);

        // Add the retrieved cars to your existing list or use it as needed
        this.cars.addAll(cars);
        for (Car car : cars) {
            Log.d("CarMenuFragment", car.toString());
        }
    }

}
