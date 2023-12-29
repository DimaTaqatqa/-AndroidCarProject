package birzeit.edu.androidcarproject;
public class Car {
    private String type;
    private String id;

    // we may use this latter id we need these attributes
    // so far we don't need
    private String factoryName;
    private String model;
    private double price;
    private String fuelType;
    private String transmission;
    private int mileage;
    private boolean isFavorite;
    private double averageRating;
    ///////
    public Car(){

    }
    public Car(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
