package birzeit.edu.androidcarproject.ui.carmenu;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

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
import birzeit.edu.androidcarproject.R;

public class CarMenuFragment extends Fragment {
    private ArrayList<Car> cars = new ArrayList<>();
    private String customerEmail; // Add a field to store the customer's email
    private CarAdapter carAdapter; // Declare carAdapter as a class-level variable

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
        SearchView searchView = view.findViewById(R.id.searchViewCarMenu);

        // Retrieve the customer's email from the intent extras
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }


        carAdapter = new CarAdapter(cars, customerEmail);


        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(carAdapter);

        // Set a query listener for the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query change
                filterCars(newText);
                return true;
            }
        });

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

    }

    private List<Car> originalCars = new ArrayList<>();

    private void filterCars(String searchText) {
        // If the original list is empty, initialize it with the current cars list
        if (originalCars.isEmpty()) {
            originalCars.addAll(cars);
        }

        // Use the selected filter to determine which property to filter
        ArrayList<Car> filteredCars = new ArrayList<>();

        // If the search text is empty, show all cars
        if (searchText.isEmpty()) {
            filteredCars.addAll(originalCars);
        } else {
            // Filter the cars based on the search text
            for (Car car : cars) {
                if (car.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                        car.getType().toLowerCase().contains(searchText.toLowerCase()) ||
                        car.getFactoryName().toLowerCase().contains(searchText.toLowerCase()) ||
                        String.valueOf(car.getPrice()).toLowerCase().contains(searchText.toLowerCase()) ||
                        car.getModel().toLowerCase().contains(searchText.toLowerCase()) ||
                        String.valueOf(car.getOffer()).toLowerCase().contains(searchText.toLowerCase()) ||
                        car.getYear().toLowerCase().contains(searchText.toLowerCase()) ||
                        car.getFuelType().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredCars.add(car);
                }
            }
        }

        // Update the adapter with the filtered cars
        carAdapter.updateData(filteredCars);
    }

}
