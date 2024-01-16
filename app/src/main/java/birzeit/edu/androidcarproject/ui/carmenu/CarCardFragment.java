package birzeit.edu.androidcarproject.ui.carmenu;

import android.app.AlertDialog;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import birzeit.edu.androidcarproject.Car;
import birzeit.edu.androidcarproject.R;

public class CarCardFragment extends Fragment {

    // Define UI elements
    private ImageView carPhoto;
    private TextView carYear, carName, carType, carFactory, carModel, carFuelType,
            carPrice, carOffer, carRating;
    private ImageView favoriteButton;
    private Button reserveButton;
    private boolean isFavorite = false;

    // Empty constructor is required
    public CarCardFragment() {
    }

    // Create a new instance of the fragment with necessary data
    public static CarCardFragment newInstance(Car car) {
        CarCardFragment fragment = new CarCardFragment();
        Bundle args = new Bundle();
        args.putSerializable("car", car);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        carPhoto = view.findViewById(R.id.car_photo);
        carYear = view.findViewById(R.id.car_year);
        carName = view.findViewById(R.id.car_name);
        carType = view.findViewById(R.id.car_type);
        carFactory = view.findViewById(R.id.car_factory);
        carModel = view.findViewById(R.id.car_model);
        carFuelType = view.findViewById(R.id.car_fuel_type);
        carPrice = view.findViewById(R.id.car_price);
        carOffer = view.findViewById(R.id.car_offer);
        carRating = view.findViewById(R.id.car_rating);
        favoriteButton = view.findViewById(R.id.favorite);
        reserveButton = view.findViewById(R.id.reserveButton);

        // Set up click listeners or other interactions as needed


        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("car")) {
            Car car = (Car) arguments.getSerializable("car");

            // Set up the UI elements with data from the Car object
            carName.setText(car.getName());
            carType.setText(car.getType());
            carFactory.setText(car.getFactoryName());
            carModel.setText(car.getModel());
            carFuelType.setText(car.getFuelType());
            carPrice.setText(String.valueOf(car.getPrice()));
            carOffer.setText(String.valueOf(car.getOffer()));
            carRating.setText(String.valueOf(car.getRating()));
            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle reserve button click
                    showReservationPopup();
                }
            });
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle favorite button click
                    isFavorite = !isFavorite;

                    TransitionDrawable transitionDrawable = (TransitionDrawable) favoriteButton.getDrawable();
                    transitionDrawable.startTransition(1000);
                }
            });
            // Load the car photo using an image loading library
            // Example using Picasso: Picasso.get().load(car.getPhotoUrl()).into(carPhoto);
        }
    }

    private void showReservationPopup() {
        if (getArguments() != null && getArguments().containsKey("car")) {
            Car car = (Car) getArguments().getSerializable("car");

            // Inflate the reservation popup layout
            View popupView = getLayoutInflater().inflate(R.layout.reserve_popup_card, null);

            // Create a dialog to display the popup
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setView(popupView);
            AlertDialog dialog = builder.create();

            // Get TextViews and set car information
            TextView factoryNameTextView = popupView.findViewById(R.id.factory_name);
            factoryNameTextView.setText("Factory Name: " + car.getFactoryName());

            TextView nameTextView = popupView.findViewById(R.id.name);
            nameTextView.setText("Name: " + car.getName());

            TextView offerTextView = popupView.findViewById(R.id.offer);
            offerTextView.setText("Offer: " + String.valueOf(car.getOffer()));

            // Set other TextViews with respective car information

            // Buttons for reservation
            Button acceptButton = popupView.findViewById(R.id.accept_button);
            Button rejectButton = popupView.findViewById(R.id.reject_button);

            // Set up click listeners for the buttons or handle reservation logic here
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle reservation acceptance logic
                    // For example, you can close the dialog
                    dialog.dismiss();
                }
            });

            rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle reservation rejection logic
                    // For example, you can close the dialog
                    dialog.dismiss();
                }
            });

            // Show the dialog
            dialog.show();
        }
    }
}
