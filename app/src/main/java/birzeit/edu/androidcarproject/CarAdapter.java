package birzeit.edu.androidcarproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import birzeit.edu.androidcarproject.Car;
import birzeit.edu.androidcarproject.R;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private ArrayList<Car> cars;
    private String customerEmail; // Add a field to store the customer's email

    private ArrayList<Car> filteredCars;


    public CarAdapter(ArrayList<Car> cars, String customerEmail) {
        this.cars = cars;
        this.customerEmail = customerEmail;
    }


    public void updateData(ArrayList<Car> updatedCars) {
        this.cars.clear();
        this.cars.addAll(updatedCars);
        notifyDataSetChanged();
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
                carPrice, carOffer, carRating, carAccident, reservedTime, reservedDate;
        private Button reserveButton, rateButton;
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
            reservedTime = view.findViewById(R.id.reservedTime);
            reservedDate = view.findViewById(R.id.reservedDate);
            reserveButton = view.findViewById(R.id.reserveButton);
            favoriteButton = view.findViewById(R.id.favorite); // Assuming this is your favorite button ImageView
            rateButton = view.findViewById(R.id.rateButton);
            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext(), "Cars_Dealer", null, 21);
                    if (position != RecyclerView.NO_POSITION) {
                        Car clickedCar = cars.get(position);
                        if (dbHelper.isCarReservedBy(clickedCar.getId(), customerEmail)) {
                            // Car is already reserved by a customer
                            boolean deleted = dbHelper.deleteReservation(clickedCar.getId(), customerEmail);
                            if (deleted) {
                                Toast.makeText(itemView.getContext(), "Unreserved Successfully", Toast.LENGTH_SHORT).show();
                                reserveButton.setText("Reserve");
                            }

                        } else if (dbHelper.isCarReserved(clickedCar.getId())) {
                            reserveButton.setText("Reserved");
                            reserveButton.setEnabled(false);

                        } else {
                            showReservationPopup(clickedCar);
                        }
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
            rateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle the button click, e.g., start a new activity
                    Intent intent = new Intent(itemView.getContext(), RatingActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });

        }

        public void bind(Car car) {
            DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext(), "Cars_Dealer", null, 21);

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


            if (!car.getReservedTime().isEmpty() && !car.getReservedDate().isEmpty()) {
                reservedTime.setText(car.getReservedTime());
                reservedDate.setText(car.getReservedDate());
            } else {
                reservedTime.setText("");
                reservedDate.setText("");
            }
            if (dbHelper.isCarReservedBy(car.getId(), customerEmail)) {
                // Car is already reserved by a customer
                reserveButton.setText("Un-Reserve");


            } else if (dbHelper.isCarReserved(car.getId())) {
                reserveButton.setText("Reserved");
                reserveButton.setEnabled(false);

            } else {
                reserveButton.setText("Reserve");
                reserveButton.setEnabled(true);
            }
            // Set the initial state of the favorite button based on SharedPreferences
            boolean isCarInFavorites = dbHelper.isCarInFavorites(car.getId(), customerEmail);
            if (isCarInFavorites) {
                favoriteButton.setImageResource(R.drawable.like);

            } else {
                favoriteButton.setImageResource(R.drawable.unlike);

            }
        }

        private void showReservationPopup(Car car) {
            // Inflate the reservation popup layout
            View popupView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.reserve_popup_card, null);
            DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext(), "Cars_Dealer", null, 21);

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
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Car clickedCar = cars.get(position);

                        // Car is not reserved, proceed with reservation
                        clickedCar.setReservedBy(customerEmail);
                        insertReservationIntoDatabase(clickedCar.getId());
                        reserveButton.setText("Un-Reserve");

                        dialog.dismiss();
                    }
                }
            });

            rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // close the dialog
                    dialog.dismiss();
                }
            });

            // Show the dialog
            dialog.show();
        }

        // Add this method to the ViewHolder class
        private void insertReservationIntoDatabase(int carID) {
            // Get the current date and time
            String currentDate = getCurrentDate();
            String currentTime = getCurrentTime();

            // Create a Reservation object with current date, time, and other details
            Reservation reservation = new Reservation(carID, customerEmail, currentDate, currentTime);

            // Create an instance of your database helper
            DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext(), "Cars_Dealer", null, 21);

            // Insert the reservation into the database
            boolean inserted = dbHelper.insertReservation(reservation);

            // Handle the result if needed (e.g., show a toast)
            if (inserted) {
                Toast.makeText(itemView.getContext(), "Reservation added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(itemView.getContext(), "Error adding reservation, try again", Toast.LENGTH_SHORT).show();
            }
        }

        // Helper method to get the current date
        private String getCurrentDate() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = Calendar.getInstance().getTime();
            return dateFormat.format(currentDate);
        }

        // Helper method to get the current time
        private String getCurrentTime() {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date currentTime = Calendar.getInstance().getTime();
            return timeFormat.format(currentTime);
        }

        private void toggleFavoriteState(int position) {
            Car clickedCar = cars.get(position);

            // After the transition, perform database operations
            performDatabaseOperation(clickedCar);
        }


        private boolean checkFavoriteState(Car car) {
            // Retrieve the favorite state from SharedPreferences
            SharedPreferences preferences = itemView.getContext().getSharedPreferences("FavoriteState", Context.MODE_PRIVATE);
            return preferences.getBoolean(String.valueOf(car.getId()), false);
        }

        private void saveFavoriteState(Car car, boolean isFavorite) {
            // Save the favorite state to SharedPreferences
            SharedPreferences preferences = itemView.getContext().getSharedPreferences("FavoriteState", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(String.valueOf(car.getId()), isFavorite);
            editor.apply();
        }

        private void performDatabaseOperation(Car car) {
            // Create an instance of your database helper
            DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext(), "Cars_Dealer", null, 21);

            // Check if the car is already in favorites
            boolean isCarInFavorites = dbHelper.isCarInFavorites(car.getId(), customerEmail);

            // If the car is not in favorites, add it; otherwise, remove it
            if (!isCarInFavorites) {
                Favorites favorites = new Favorites(car.getId(), customerEmail);
                boolean inserted = dbHelper.insertFavorites(favorites);

                if (inserted) {
                    // Car added to favorites successfully
                    Toast.makeText(itemView.getContext(), "Car added to favorites", Toast.LENGTH_SHORT).show();
                    favoriteButton.setImageResource(R.drawable.like);
                } else {
                    // Error adding car to favorites
                    Toast.makeText(itemView.getContext(), "Error adding car to favorites", Toast.LENGTH_SHORT).show();
                }
            } else {
                boolean deleted = dbHelper.deleteFavorites(car.getId(), customerEmail);

                if (deleted) {
                    // Car removed from favorites successfully
                    Toast.makeText(itemView.getContext(), "Car removed from favorites", Toast.LENGTH_SHORT).show();
                    favoriteButton.setImageResource(R.drawable.unlike);

                } else {
                    // Error removing car from favorites
                    Toast.makeText(itemView.getContext(), "Error removing car from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
