package birzeit.edu.androidcarproject.ui.favorites;

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
import birzeit.edu.androidcarproject.Favorites;
import birzeit.edu.androidcarproject.R;

public class FavoritesFragment extends Fragment {
    private ArrayList<Car> favoriteCars = new ArrayList<>();
    private String customerEmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavorites);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }

        // Retrieve favorite cars from the database
        favoriteCars = fetchDataFromDatabase();

        // Set up RecyclerView and adapter
        CarAdapter carAdapter = new CarAdapter(favoriteCars, customerEmail);

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(carAdapter);
    }


    private ArrayList<Car> fetchDataFromDatabase() {
        // Create an instance of your database helper
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext(), "Cars_Dealer", null, 21);

        ArrayList<Car> retrievedFavoriteCars = dbHelper.getFavoriteCars(customerEmail);
        // Log the number of favorite cars retrieved
        Log.d("FavoritesFragment", "Number of favorite cars: " + retrievedFavoriteCars.size());
        // Close the database when done
        dbHelper.close();

        return retrievedFavoriteCars;
    }
}
