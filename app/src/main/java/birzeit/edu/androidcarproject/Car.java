package birzeit.edu.androidcarproject;

import java.io.Serializable;

public class Car implements Serializable {
    private int id;
    private String factoryName;
    private String type;
    private double price;
    private String model;
    private String name;
    private double offer;
    private String year;
    private String fuelType;
    private double rating;
    private String accident;

    private String reservedTime;
    private String reservedDate;

    private boolean favorite;
    private boolean reserved;

    private String reservedBy; // Add a field to store the email of the customer who reserved the car


    public Car() {

    }

    public Car(int id, String factoryName, String type, double price, String model, String name, double offer, String year, String fuelType, double rating, String accident, String reservedBy, String reservedTime, String reservedDate) {
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
        this.reservedBy = reservedBy; // Initialize reservedBy as null when the car is created
        this.reservedTime = reservedTime;
        this.reservedDate = reservedDate;

    }
    public Car(int id, String factoryName, String type, double price, String model, String name, double offer, String year, String fuelType, double rating, String accident, String reservedTime, String reservedDate) {
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
        //this.reservedBy = reservedBy; // Initialize reservedBy as null when the car is created
        this.reservedTime = reservedTime;
        this.reservedDate = reservedDate;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
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

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAccident() {
        return accident;
    }

    public void setAccident(String accident) {
        this.accident = accident;
    }

    public String getReservedBy() {
        return reservedBy;
    }

    // Add a method to set the customer who reserved the car
    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    public String getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(String reservedTime) {
        this.reservedTime = reservedTime;
    }

    public String getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(String reservedDate) {
        this.reservedDate = reservedDate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", factoryName='" + factoryName + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", model='" + model + '\'' +
                ", name='" + name + '\'' +
                ", offer=" + offer +
                ", year='" + year + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", rating=" + rating +
                ", accident='" + accident + '\'' +
                '}';
    }
}

