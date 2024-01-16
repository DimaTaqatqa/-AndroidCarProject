package birzeit.edu.androidcarproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarJsonParser {
    public static List<Car> getObjectFromJson(String json) {
        List<Car> cars;
        try {
            JSONArray jsonArray = new JSONArray(json);
            cars = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Car car = new Car();
                car.setId(jsonObject.getInt("ID"));
                car.setFactoryName(jsonObject.getString("FACTORY_NAME"));
                car.setType(jsonObject.getString("TYPE"));
                car.setPrice(jsonObject.getInt("PRICE"));
                car.setModel(jsonObject.getString("MODEL"));
                car.setName(jsonObject.getString("NAME"));
                car.setOffer(jsonObject.getDouble("OFFER"));
                car.setYear(jsonObject.getString("YEAR"));
                car.setFuelType(jsonObject.getString("FUEL_TYPE"));
                car.setRating(jsonObject.getDouble("RATING"));
                car.setAccident(jsonObject.getString("ACCIDENT"));
                cars.add(car);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return cars;
    }
}
