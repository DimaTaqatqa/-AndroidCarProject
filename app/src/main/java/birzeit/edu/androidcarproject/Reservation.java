package birzeit.edu.androidcarproject;

public class Reservation {
    int carID;
    String customerEmail;
    String date;
    String time;

    public Reservation() {
    }

    public Reservation(int carID, String customerEmail, String date, String time) {
        this.carID = carID;
        this.customerEmail = customerEmail;
        this.date = date;
        this.time = time;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "carID=" + carID +
                ", customerEmail='" + customerEmail + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
