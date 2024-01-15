package birzeit.edu.androidcarproject;
public class Car {
    private int id;
    private String type;

    // we may use this latter id we need these attributes
    // so far we don't need
    private String factory;
    private String model;
    private double price;

    // not used in car menu nav so far
    private String fuelType;
    private String transmission;
    private int mileage;
    private boolean isFavorite;
    private double averageRating;
    ///////
    public Car(){

    }

    public Car(int id, String type, String factory, String model, double price) {
        this.id = id;
        this.type = type;
        this.factory = factory;
        this.model = model;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                ", id=" + id +
                ", factory='" + factory + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
}
