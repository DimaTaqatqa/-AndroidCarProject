package birzeit.edu.androidcarproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private ArrayList<Car> cars;

    public CarAdapter(ArrayList<Car> cars) {
        this.cars = cars;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Define UI elements for each item in the RecyclerView
        private ImageView carPhoto;
        private TextView carYear, carName, carType, carFactory, carModel, carFuelType,
                carPrice, carOffer, carRating;
        private ImageView favoriteButton;
        private Button reserveButton;

        public ViewHolder(@NonNull View view) {
            super(view);
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

            // Bind other data as needed
        }
    }
}
