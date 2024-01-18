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
import birzeit.edu.androidcarproject.R;
import birzeit.edu.androidcarproject.databinding.FragmentReservationsBinding;

public class ReservationsFragment extends Fragment {

    private ArrayList<Car> cars = new ArrayList<>();
    private String customerEmail; // Add a field to store the customer's email

    public ReservationsFragment() {

        // Add the first car
        Car car1 = new Car(
                1,
                "Toyota",
                "Sedan",
                30000,
                "Camry",
                "Camry XLE",
                2000,
                "2023",
                "Gasoline",
                4.5,
                "No"
        );
        cars.add(car1);

        // Add the second car
        Car car2 = new Car(
                2,
                "Honda",
                "SUV",
                35000,
                "CR-V",
                "CR-V EX",
                1500,
                "2023",
                "Hybrid",
                4.7,
                "No"
        );
        cars.add(car2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Assuming you have an ArrayList<Car> named 'cars'
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewReservation);

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
}