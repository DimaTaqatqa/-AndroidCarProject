package birzeit.edu.androidcarproject.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FavoritesFragment extends Fragment {
    private ArrayList<Car> favoriteCars = new ArrayList<>();
    private String customerEmail;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize database helper
        dbHelper = new DataBaseHelper(requireContext(), "Cars_Dealer", null, 20);

        // Retrieve the customer's email from the intent extras
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }

        // Retrieve favorite cars from the database
        favoriteCars = dbHelper.getFavoriteCars(customerEmail);

        // Set up RecyclerView and adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        CarAdapter carAdapter = new CarAdapter(favoriteCars, customerEmail);

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(carAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Close the database when the fragment is destroyed
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
