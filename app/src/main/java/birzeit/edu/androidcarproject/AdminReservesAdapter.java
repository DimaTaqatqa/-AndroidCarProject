package birzeit.edu.androidcarproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminReservesAdapter extends RecyclerView.Adapter<AdminReservesAdapter.AdminReservesViewHolder> {

    private Context context;
    private List<Car> reservedCars;

    public AdminReservesAdapter(Context context, List<Car> reservedCars) {
        this.context = context;
        this.reservedCars = reservedCars;
    }

    @NonNull
    @Override
    public AdminReservesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_customer_card, parent, false);
        return new AdminReservesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminReservesViewHolder holder, int position) {
        Car car = reservedCars.get(position);
        holder.bind(car);
    }

    @Override
    public int getItemCount() {
        return reservedCars.size();
    }

    public class AdminReservesViewHolder extends RecyclerView.ViewHolder {

        private ImageView carPhoto;
        private TextView carYear, carName, carType, carFactory, carModel, carFuelType,
                carPrice, carOffer, carRating, reservedTime, reservedDate, customerName;

        public AdminReservesViewHolder(@NonNull View itemView) {
            super(itemView);

            carPhoto = itemView.findViewById(R.id.car_photo);
            carYear = itemView.findViewById(R.id.car_year);
            carName = itemView.findViewById(R.id.car_name);
            carType = itemView.findViewById(R.id.car_type);
            carFactory = itemView.findViewById(R.id.car_factory);
            carModel = itemView.findViewById(R.id.car_model);
            carFuelType = itemView.findViewById(R.id.car_fuel_type);
            carPrice = itemView.findViewById(R.id.car_price);
            carOffer = itemView.findViewById(R.id.car_offer);
            carRating = itemView.findViewById(R.id.car_rating);
            reservedTime = itemView.findViewById(R.id.reservedTime);
            reservedDate = itemView.findViewById(R.id.reservedDate);
            customerName = itemView.findViewById(R.id.customer_name);
        }

        public void bind(Car car) {
            // Set up the UI elements with data from the Car object
            DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext(), "Cars_Dealer", null, 21);

            carName.setText(car.getName());
            carType.setText(car.getType());
            carFactory.setText(car.getFactoryName());
            carModel.setText(car.getModel());
            carFuelType.setText(car.getFuelType());
            carPrice.setText(String.valueOf(car.getPrice()));
            carOffer.setText(String.valueOf(car.getOffer()));
            carRating.setText(String.valueOf(car.getRating()));
            reservedTime.setText(car.getReservedTime()); // Set the reserved time
            reservedDate.setText(car.getReservedDate()); // Set the reserved date
            customerName.setText("Reserved By: "+dbHelper.getCustomerName(String.valueOf(car.getReservedBy())));

            // Load the car photo using an image loading library
            // Example using Picasso: Picasso.get().load(car.getPhotoUrl()).into(carPhoto);
        }
    }
}
