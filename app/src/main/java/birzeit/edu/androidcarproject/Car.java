package birzeit.edu.androidcarproject;

public class Car {
    private int id;
    private String factoryName;
    private String type;
    private int price;
    private String model;
    private String name;
    private int offer;
    private String year;
    private String fuelType;
    private double rating;
    private String accident;

    public Car(){

    }
    public Car(int id, String factoryName, String type, int price, String model, String name, int offer, String year, String fuelType, float rating, String accident) {
        this.id = id;
        this.factoryName = factoryName;
        this.type = type;
        this.price = price;
        this.model = model;
        this.name = name;
        this.offer = offer;
        this.year = year;
        this.fuelType = fuelType;
        this.rating = rating;
        this.accident = accident;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAccident() {
        return accident;
    }

    public void setAccident(String accident) {
        this.accident = accident;
    }


}

