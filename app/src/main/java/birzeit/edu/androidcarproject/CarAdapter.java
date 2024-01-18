package birzeit.edu.androidcarproject;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import birzeit.edu.androidcarproject.Car;
import birzeit.edu.androidcarproject.R;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private ArrayList<Car> cars;
    private String customerEmail; // Add a field to store the customer's email

    public CarAdapter(ArrayList<Car> cars, String customerEmail) {
        this.cars = cars;
        this.customerEmail = customerEmail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_car_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.bind(car);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView carYear, carName, carType, carFactory, carModel, carFuelType,
                carPrice, carOffer, carRating, carAccident;
        private Button reserveButton;
        private ImageView favoriteButton;

        public ViewHolder(@NonNull View view) {
            super(view);

            // Initialize UI elements
            carYear = view.findViewById(R.id.car_year);
            carName = view.findViewById(R.id.car_name);
            carType = view.findViewById(R.id.car_type);
            carFactory = view.findViewById(R.id.car_factory);
            carModel = view.findViewById(R.id.car_model);
            carFuelType = view.findViewById(R.id.car_fuel_type);
            carPrice = view.findViewById(R.id.car_price);
            carOffer = view.findViewById(R.id.car_offer);
            carRating = view.findViewById(R.id.car_rating);
            reserveButton = view.findViewById(R.id.reserveButton);
            favoriteButton = view.findViewById(R.id.favorite); // Assuming this is your favorite button ImageView

            // Set up click listener for reserve button
            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Car clickedCar = cars.get(position);
                        showReservationPopup(clickedCar);
                    }
                }
            });

            // Set up click listener for favorite button
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavoriteState(getAdapterPosition());
                }
            });
        }

        public void bind(Car car) {
            // Bind data to UI elements
            carYear.setText(car.getYear());
            carName.setText(car.getName());
            carType.setText(car.getType());
            carFactory.setText(car.getFactoryName());
            carModel.setText(car.getModel());
            carFuelType.setText(car.getFuelType());
            carPrice.setText(String.valueOf(car.getPrice()));
            carOffer.setText(String.valueOf(car.getOffer()));
            carRating.setText(String.valueOf(car.getRating()));
            //carAccident.setText(String.valueOf(car.getAccident()));

        }

        private void showReservationPopup(Car car) {
            // Inflate the reservation popup layout
            View popupView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.reserve_popup_card, null);

            // Create a dialog to display the popup
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setView(popupView);
            AlertDialog dialog = builder.create();

            // Get TextViews and set car information
            TextView factoryNameTextView = popupView.findViewById(R.id.factory_name);
            factoryNameTextView.setText("Factory Name: " + car.getFactoryName());

            TextView nameTextView = popupView.findViewById(R.id.name);
            nameTextView.setText("Name: " + car.getName());

            TextView offerTextView = popupView.findViewById(R.id.offer);
            offerTextView.setText("Offer: " + String.valueOf(car.getOffer()));

            TextView typeTextView = popupView.findViewById(R.id.type);
            typeTextView.setText("Type: " + String.valueOf(car.getType()));

            TextView modelTextView = popupView.findViewById(R.id.model);
            modelTextView.setText("Model: " + String.valueOf(car.getModel()));

            TextView yearTextView = popupView.findViewById(R.id.year);
            yearTextView.setText("Year: " + String.valueOf(car.getYear()));

            TextView fuelTypeTextView = popupView.findViewById(R.id.fuel_type);
            fuelTypeTextView.setText("Fuel Type: " + String.valueOf(car.getFuelType()));

            TextView priceTextView = popupView.findViewById(R.id.price);
            priceTextView.setText("Price: " + String.valueOf(car.getPrice()));

            TextView ratingTextView = popupView.findViewById(R.id.rating);
            ratingTextView.setText("Rating: " + String.valueOf(car.getRating()));

            TextView accidentTextView = popupView.findViewById(R.id.accident);
            accidentTextView.setText("Accident: " + String.valueOf(car.getAccident()));

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

        private void toggleFavoriteState(int position) {
            Car clickedCar = cars.get(position);
            // Create a TransitionDrawable with your animation XML
            TransitionDrawable transitionDrawable = (TransitionDrawable) favoriteButton.getDrawable();

            // Toggle the state of the TransitionDrawable
            transitionDrawable.reverseTransition(300); // You can adjust the duration as needed

            // After the transition, perform database operations
            performDatabaseOperation(clickedCar);
        }

        private void performDatabaseOperation(Car car) {
            // Create an instance of your database helper
            DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext(), "Cars_Dealer", null, 20);

            // Begin a transaction
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.beginTransaction();

            try {
                // Check if the car is already in favorites
                boolean isFavorite = dbHelper.isCarInFavorites(car.getId(), customerEmail);

                // If the car is not in favorites, add it; otherwise, remove it
                if (!isFavorite) {
                    Favorites favorites = new Favorites(car.getId(), customerEmail);
                    boolean inserted = dbHelper.insertFavorites(favorites);

                    if (inserted) {
                        // Car added to favorites successfully
                        Toast.makeText(itemView.getContext(), "Car added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        // Error adding car to favorites
                        Toast.makeText(itemView.getContext(), "Error adding car to favorites", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean deleted = dbHelper.deleteFavorites(car.getId(), customerEmail);

                    if (deleted) {
                        // Car removed from favorites successfully
                        Toast.makeText(itemView.getContext(), "Car removed from favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        // Error removing car from favorites
                        Toast.makeText(itemView.getContext(), "Error removing car from favorites", Toast.LENGTH_SHORT).show();
                    }

                }

                // Set the transaction as successful
                db.setTransactionSuccessful();
            } catch (Exception e) {
                // Handle the exception, log, or throw as needed
                e.printStackTrace();
            } finally {
                // End the transaction
                db.endTransaction();
                // Close the database
                db.close();
            }
        }


    }
}
