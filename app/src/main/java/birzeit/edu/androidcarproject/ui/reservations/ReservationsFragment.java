package birzeit.edu.androidcarproject.ui.reservations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import birzeit.edu.androidcarproject.Car;
import birzeit.edu.androidcarproject.CarAdapter;
import birzeit.edu.androidcarproject.DataBaseHelper;
import birzeit.edu.androidcarproject.R;
import birzeit.edu.androidcarproject.Reservation;
import birzeit.edu.androidcarproject.databinding.FragmentReservationsBinding;

public class ReservationsFragment extends Fragment {

    private ArrayList<Car> reservations = new ArrayList<>();
    private String customerEmail; // Add a field to store the customer's email

    public ReservationsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewReservation);

        // Retrieve the customer's email from the intent extras
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }

        // Fetch reservations from the database
        reservations = fetchReservationsFromDatabase();

        // Pass the reservations to the adapter
        CarAdapter carAdapter = new CarAdapter(reservations, customerEmail);




        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(carAdapter);
    }

    private ArrayList<Car> fetchReservationsFromDatabase() {
        // Create an instance of your database helper
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Cars_Dealer", null, 21);

        // Retrieve reservations from the database
        ArrayList<Car> reservations = dbHelper.getReservations(customerEmail);

        // Close the database when done
        dbHelper.close();

        return reservations;
    }

}