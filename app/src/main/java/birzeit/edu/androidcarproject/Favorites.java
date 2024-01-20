package birzeit.edu.androidcarproject;

public class Favorites {

    private int carID;
    private String customerEmail;

    public Favorites() {
    }

    public Favorites(int carID, String customerEmail) {
        this.carID = carID;
        this.customerEmail = customerEmail;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "carID=" + carID +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}
