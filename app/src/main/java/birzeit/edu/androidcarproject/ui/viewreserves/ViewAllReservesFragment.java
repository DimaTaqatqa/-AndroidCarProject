package birzeit.edu.androidcarproject.ui.viewreserves;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import birzeit.edu.androidcarproject.AdminReservesAdapter;
import birzeit.edu.androidcarproject.Car;
import birzeit.edu.androidcarproject.DataBaseHelper;
import birzeit.edu.androidcarproject.R;

public class ViewAllReservesFragment extends Fragment {
    private ArrayList<Car> reservedCars = new ArrayList<>();
    private String customerEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_view_all_reserves, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Retrieve the customer's email from the intent extras
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }
        // Retrieve reserved cars from the database
        reservedCars = fetchDataFromDatabase();
        // Set up RecyclerView and adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAllReserves);

        AdminReservesAdapter adminReservesAdapter = new AdminReservesAdapter(requireContext(), reservedCars);

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adminReservesAdapter);
    }

    private ArrayList<Car> fetchDataFromDatabase() {
        // Create an instance of your database helper
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Cars_Dealer", null, 21);

        // Get the reserved cars for the current customer
        ArrayList<Car> retrievedReservedCars = dbHelper.getReservedCars();

        // Log the number of reserved cars retrieved
        Log.d("ViewAllReservesFragment", "Number of reserved cars: " + retrievedReservedCars.size());

        // Add the retrieved cars to your existing list or use it as needed
        reservedCars.addAll(retrievedReservedCars);

        // Log details of each reserved car
        for (Car car : reservedCars) {
            Log.d("ViewAllReservesFragment", car.toString());
        }
        return retrievedReservedCars;
    }
}