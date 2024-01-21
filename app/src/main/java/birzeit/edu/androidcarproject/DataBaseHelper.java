package birzeit.edu.androidcarproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {
    //private static final int DATABASE_VERSION = 19;
    // Database Name
    //private static final String DATABASE_NAME = "Cars_Dealer";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Database", "onCreate called");
        String createAdminTable = "CREATE TABLE Admin (" +
                "email TEXT PRIMARY KEY," +
                "password_hash TEXT," +
                "first_name TEXT," +
                "last_name TEXT," +
                "gender TEXT," +
                "country TEXT," +
                "city TEXT," +
                "phone TEXT," +
                "photo BLOB," +
                "user_type INTEGER);";

        String createCustomerTable = "CREATE TABLE Customer (" +
                "email TEXT PRIMARY KEY," +
                "password_hash TEXT," +
                "first_name TEXT," +
                "last_name TEXT," +
                "gender TEXT," +
                "country TEXT," +
                "city TEXT," +
                "phone TEXT," +
                "photo BLOB," +
                "user_type INTEGER);";

        String createCarTable = "CREATE TABLE Car (" +
                "id INTEGER PRIMARY KEY," +
                "factoryName TEXT," +
                "type TEXT," +
                "price INTEGER," +
                "model TEXT," +
                "name TEXT," +
                "offer INTEGER," +
                "year TEXT," +
                "fuelType TEXT," +
                "rating REAL," +
                "accident TEXT);";

        String createReservationTable = "CREATE TABLE Reservation (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "carID INTEGER," +
                "customerEmail TEXT," +
                "date TEXT," +
                "time TEXT);";

        String createFavoritesTable = "CREATE TABLE Favorites (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "carID INTEGER," +
                "customerEmail TEXT);";

        db.execSQL(createAdminTable);
        db.execSQL(createCustomerTable);
        db.execSQL(createCarTable);
        db.execSQL(createReservationTable);
        db.execSQL(createFavoritesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS Customer");
        db.execSQL("DROP TABLE IF EXISTS Car");
        db.execSQL("DROP TABLE IF EXISTS Reservation");
        db.execSQL("DROP TABLE IF EXISTS Favorites");

        // Recreate the tables
        onCreate(db);
    }

    public boolean insertAdmin(Admin admin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email", admin.getEmail());
        contentValues.put("password_hash", admin.getPassword());
        contentValues.put("first_name", admin.getFirstName());
        contentValues.put("last_name", admin.getLastName());
        contentValues.put("gender", admin.getGender());
        contentValues.put("country", admin.getCountry());
        contentValues.put("city", admin.getCity());
        contentValues.put("phone", admin.getPhone());
        contentValues.put("photo", admin.getPhoto());
        contentValues.put("user_type", admin.getUserType());

        // If insertion is successful then result != -1
        long result = db.insert("Admin", null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean insertCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email", customer.getEmail());
        contentValues.put("password_hash", customer.getPassword());
        contentValues.put("first_name", customer.getFirstName());
        contentValues.put("last_name", customer.getLastName());
        contentValues.put("gender", customer.getGender());
        contentValues.put("country", customer.getCountry());
        contentValues.put("city", customer.getCity());
        contentValues.put("phone", customer.getPhone());
        contentValues.put("photo", customer.getPhoto());
        contentValues.put("user_type", customer.getUserType());

        // If insertion is successful then result != -1
        long result = db.insert("Customer", null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean insertCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the car already exists in the database based on its ID
        Cursor cursor = db.rawQuery("SELECT * FROM Car WHERE id = ?", new String[]{String.valueOf(car.getId())});

        // If the cursor has a non-zero count, the car already exists
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            Log.d("Database", "Car already exists in the database");
            return false;
        }

        // If the cursor is empty, the car does not exist, proceed with insertion
        cursor.close();

        ContentValues values = new ContentValues();
        values.put("id", car.getId());
        values.put("factoryName", car.getFactoryName());
        values.put("type", car.getType());
        values.put("price", car.getPrice());
        values.put("model", car.getModel());
        values.put("name", car.getName());
        values.put("offer", car.getOffer());
        values.put("year", car.getYear());
        values.put("fuelType", car.getFuelType());
        values.put("rating", car.getRating());
        values.put("accident", car.getAccident());

        // Insert the values into the "Car" table
        long result = db.insert("Car", null, values);

        // Close the database
        db.close();

        // If insertion is successful then result != -1
        if (result != -1) {
            Log.d("Database", "Car inserted successfully");
            return true;
        } else {
            // Error inserting car
            Log.d("Database", "Error inserting car");
            return false;
        }
    }

    public boolean insertReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("carID", reservation.getCarID());
        contentValues.put("customerEmail", reservation.getCustomerEmail());
        contentValues.put("date", reservation.getDate());
        contentValues.put("time", reservation.getTime());

        // If insertion is successful then result != -1
        long result = db.insert("Reservation", null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean insertFavorites(Favorites favorites) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("carID", favorites.getCarID());
        contentValues.put("customerEmail", favorites.getCustomerEmail());

        // If insertion is successful then result != -1
        long result = db.insert("Favorites", null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean emailExists(String table, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table + " WHERE email=?", new String[]{email});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public int validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Check in each table (Admin, and Customer) one by one.
        // Assuming the user_type is stored as 0 for Admin, 1 for Customer, so after the i+1 it
        // will give the right user_type
        String[] tableNames = {"Admin", "Customer"};

        for (int i = 0; i < tableNames.length; i++) {
            if (i == 0) {
                Cursor cursor = db.rawQuery("SELECT * FROM " + "Admin" + " WHERE email=? AND password_hash=?", new String[]{email, password});
                if (cursor.getCount() > 0) {
                    return i + 1;
                }
                cursor.close();
            } else if (i == 1) {
                Cursor cursor = db.rawQuery("SELECT * FROM " + "Customer" + " WHERE email=? AND password_hash=?", new String[]{email, password});
                if (cursor.getCount() > 0) {
                    return i + 1;
                }
                cursor.close();
            }
        }
        return -1;
    }

    public boolean deleteCustomer(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Customer", "email = ?", new String[]{email});
        db.close();
        return result != 0;
    }


    //______________________________________________________________________________
    public List<Car> getAllCars() {
        // Create a list to store the cars
        List<Car> cars = new ArrayList<>();

        // Create a SQLite database connection
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the SQL query
        String query = "SELECT * FROM Car";

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Loop through the results
        while (cursor.moveToNext()) {
            // Extract the data
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String factoryName = cursor.getString(cursor.getColumnIndexOrThrow("factoryName"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
            String model = cursor.getString(cursor.getColumnIndexOrThrow("model"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int offer = cursor.getInt(cursor.getColumnIndexOrThrow("offer"));
            String year = cursor.getString(cursor.getColumnIndexOrThrow("year"));
            String fuelType = cursor.getString(cursor.getColumnIndexOrThrow("fuelType"));
            double rating = cursor.getDouble(cursor.getColumnIndexOrThrow("rating"));
            String accident = cursor.getString(cursor.getColumnIndexOrThrow("accident"));

            // Create a new car object
            Car car = new Car(id, factoryName, type, price, model, name, offer, year, fuelType, rating, accident, "", "");

            // Add the car to the list
            cars.add(car);
        }

        // Close the database connection
        cursor.close();
        db.close();

        // Return the list of cars
        return cars;
    }

    public Cursor getCarById(int carId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Car WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(carId)});

        return cursor;
    }

    public boolean isCarInFavorites(int carId, String customerEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Favorites WHERE carID = ? AND customerEmail = ?", new String[]{String.valueOf(carId), customerEmail});

        boolean isFavorite = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isFavorite;
    }

    public boolean deleteFavorites(int carId, String customerEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("Favorites", "carID = ? AND customerEmail = ?", new String[]{String.valueOf(carId), customerEmail});
        db.close();

        return deletedRows > 0;
    }

    public ArrayList<Car> getFavoriteCars(String customerEmail) {
        ArrayList<Car> favoriteCars = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all reserved cars for a specific customer
        String query = "SELECT * FROM Favorites F INNER JOIN Car C ON F.carID = C.id WHERE F.customerEmail = ?";
        String[] selectionArgs = (customerEmail != null) ? new String[]{customerEmail} : null;

        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Get the column indices once to avoid repeated calls
        int carIdIndex = cursor.getColumnIndex("id");
        int factoryNameIndex = cursor.getColumnIndex("factoryName");
        int typeIndex = cursor.getColumnIndex("type");
        int priceIndex = cursor.getColumnIndex("price");
        int modelIndex = cursor.getColumnIndex("model");
        int nameIndex = cursor.getColumnIndex("name");
        int offerIndex = cursor.getColumnIndex("offer");
        int yearIndex = cursor.getColumnIndex("year");
        int fuelTypeIndex = cursor.getColumnIndex("fuelType");
        int ratingIndex = cursor.getColumnIndex("rating");
        int accidentIndex = cursor.getColumnIndex("accident");

        // Iterate through the result set and add cars to the reservations list
        while (cursor.moveToNext()) {
            int carID = cursor.getInt(carIdIndex);
            String factoryName = cursor.getString(factoryNameIndex);
            String type = cursor.getString(typeIndex);
            int price = cursor.getInt(priceIndex);
            String model = cursor.getString(modelIndex);
            String name = cursor.getString(nameIndex);
            int offer = cursor.getInt(offerIndex);
            String year = cursor.getString(yearIndex);
            String fuelType = cursor.getString(fuelTypeIndex);
            double rating = cursor.getDouble(ratingIndex);
            String accident = cursor.getString(accidentIndex);

            // Create a Car object and add it to the reservations list
            Car car = new Car(carID, factoryName, type, price, model, name, offer, year, fuelType, rating, accident, "", "");
            favoriteCars.add(car);
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return favoriteCars;
    }

    public ArrayList<Favorites> getAllFavorites() {
        ArrayList<Favorites> allFavorites = new ArrayList<>();

        // Create a SQLite database connection
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the SQL query to get carID and customerEmail from the Favorites table
        String query = "SELECT * FROM Favorites";

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Loop through the results
        while (cursor.moveToNext()) {
            // Extract the data
            int carID = cursor.getInt(cursor.getColumnIndexOrThrow("carID"));
            String customerEmail = cursor.getString(cursor.getColumnIndexOrThrow("customerEmail"));

            // Create a new Favorites object
            Favorites favorites = new Favorites(carID, customerEmail);

            // Add the favorites to the list
            allFavorites.add(favorites);
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return allFavorites;
    }

    // Check if a car is already reserved
    public boolean isCarReservedBy(int carID, String customerEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Reservation WHERE carID = ? AND customerEmail = ?", new String[]{String.valueOf(carID), customerEmail});

        boolean isReserved = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isReserved;
    }

    public boolean deleteReservation(int carID, String customerEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "carID = ? AND customerEmail = ?";
        String[] whereArgs = {String.valueOf(carID), customerEmail};

        int deletedRows = db.delete("Reservation", query, whereArgs);
        db.close();

        return deletedRows > 0;


    }

    // Check if the car is reserved
    public boolean isCarReserved(int carID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Reservation WHERE carID = ?",
                new String[]{String.valueOf(carID)});

        boolean isReserved = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isReserved;
    }

    public ArrayList<Car> getReservations(String customerEmail) {
        ArrayList<Car> reservations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all reserved cars for a specific customer
        String query = "SELECT * FROM Reservation R INNER JOIN Car C ON R.carID = C.id WHERE R.customerEmail = ?";
        Cursor cursor = db.rawQuery(query, new String[]{customerEmail});

        // Get the column indices once to avoid repeated calls
        int carIdIndex = cursor.getColumnIndex("id");
        int factoryNameIndex = cursor.getColumnIndex("factoryName");
        int typeIndex = cursor.getColumnIndex("type");
        int priceIndex = cursor.getColumnIndex("price");
        int modelIndex = cursor.getColumnIndex("model");
        int nameIndex = cursor.getColumnIndex("name");
        int offerIndex = cursor.getColumnIndex("offer");
        int yearIndex = cursor.getColumnIndex("year");
        int fuelTypeIndex = cursor.getColumnIndex("fuelType");
        int ratingIndex = cursor.getColumnIndex("rating");
        int accidentIndex = cursor.getColumnIndex("accident");
        int dateIndex = cursor.getColumnIndex("date");
        int timeIndex = cursor.getColumnIndex("time");
        int reservedByIndex = cursor.getColumnIndex("customerEmail");

        // Iterate through the result set and add cars to the reservations list
        while (cursor.moveToNext()) {
            int carID = cursor.getInt(carIdIndex);
            String factoryName = cursor.getString(factoryNameIndex);
            String type = cursor.getString(typeIndex);
            int price = cursor.getInt(priceIndex);
            String model = cursor.getString(modelIndex);
            String name = cursor.getString(nameIndex);
            int offer = cursor.getInt(offerIndex);
            String year = cursor.getString(yearIndex);
            String fuelType = cursor.getString(fuelTypeIndex);
            double rating = cursor.getDouble(ratingIndex);
            String accident = cursor.getString(accidentIndex);
            String date = cursor.getString(dateIndex);
            String time = cursor.getString(timeIndex);
            String reservedBy = cursor.getString(reservedByIndex);


            // Create a Car object and add it to the reservations list
            Car car = new Car(carID, factoryName, type, price, model, name, offer, year, fuelType, rating, accident, reservedBy, time, date);
            reservations.add(car);
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return reservations;
    }

    public ArrayList<Car> getReservedCars() {
        ArrayList<Car> reservedCars = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all reserved cars with details
        String query = "SELECT * FROM Reservation R INNER JOIN Car C ON R.carID = C.id";
        Cursor cursor = db.rawQuery(query, null);

        // Get the column indices once to avoid repeated calls
        int carIdIndex = cursor.getColumnIndex("id");
        int factoryNameIndex = cursor.getColumnIndex("factoryName");
        int typeIndex = cursor.getColumnIndex("type");
        int priceIndex = cursor.getColumnIndex("price");
        int modelIndex = cursor.getColumnIndex("model");
        int nameIndex = cursor.getColumnIndex("name");
        int offerIndex = cursor.getColumnIndex("offer");
        int yearIndex = cursor.getColumnIndex("year");
        int fuelTypeIndex = cursor.getColumnIndex("fuelType");
        int ratingIndex = cursor.getColumnIndex("rating");
        int accidentIndex = cursor.getColumnIndex("accident");
        int reservedTimeIndex = cursor.getColumnIndex("time");
        int reservedDateIndex = cursor.getColumnIndex("date");
        int reservedByIndex = cursor.getColumnIndex("customerEmail");

        // Iterate through the result set and add cars to the reservedCars list
        while (cursor.moveToNext()) {
            int carID = cursor.getInt(carIdIndex);
            String factoryName = cursor.getString(factoryNameIndex);
            String type = cursor.getString(typeIndex);
            int price = cursor.getInt(priceIndex);
            String model = cursor.getString(modelIndex);
            String name = cursor.getString(nameIndex);
            int offer = cursor.getInt(offerIndex);
            String year = cursor.getString(yearIndex);
            String fuelType = cursor.getString(fuelTypeIndex);
            double rating = cursor.getDouble(ratingIndex);
            String accident = cursor.getString(accidentIndex);
            String reservedTime = cursor.getString(reservedTimeIndex);
            String reservedDate = cursor.getString(reservedDateIndex);
            String reservedBy = cursor.getString(reservedByIndex);

            // Create a Car object and add it to the reservedCars list
            Car car = new Car(carID, factoryName, type, price, model, name, offer, year, fuelType, rating, accident, reservedBy, reservedTime, reservedDate);
            reservedCars.add(car);
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return reservedCars;
    }

    public String getCustomerName(String customerEmail) {
        String customerName = null;
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get the customer name based on the email
        String query = "SELECT first_name, last_name FROM Customer WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{customerEmail});
        int firstNameIndex = cursor.getColumnIndex("first_name");
        int lastNameIndex = cursor.getColumnIndex("last_name");
        // Check if the cursor has data
        if (cursor.moveToFirst()) {
            // Retrieve the first and last name from the result set
            String firstName = cursor.getString(firstNameIndex);
            String lastName = cursor.getString(lastNameIndex);

            // Concatenate first and last name to get the full name
            customerName = firstName + " " + lastName;
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return customerName;
    }

    public ArrayList<Car> searchCars(String searchText) {
        ArrayList<Car> cars = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {"id", "factoryName", "type", "price", "model", "name", "offer", "year", "fuelType", "rating", "accident"};

        // Define the selection criteria
        String selection = "factoryName LIKE ? OR type LIKE ? OR price LIKE ? OR model LIKE ? OR name LIKE ? OR offer LIKE ? OR year LIKE ? OR fuelType LIKE ? OR rating LIKE ? OR accident LIKE ?";
        String[] selectionArgs = new String[10];
        for (int i = 0; i < 10; i++) {
            selectionArgs[i] = "%" + searchText + "%";
        }

        Cursor cursor = db.query("Car", columns, selection, selectionArgs, null, null, null);

        int carIdIndex = cursor.getColumnIndex("id");
        int factoryNameIndex = cursor.getColumnIndex("factoryName");
        int typeIndex = cursor.getColumnIndex("type");
        int priceIndex = cursor.getColumnIndex("price");
        int modelIndex = cursor.getColumnIndex("model");
        int nameIndex = cursor.getColumnIndex("name");
        int offerIndex = cursor.getColumnIndex("offer");
        int yearIndex = cursor.getColumnIndex("year");
        int fuelTypeIndex = cursor.getColumnIndex("fuelType");
        int ratingIndex = cursor.getColumnIndex("rating");
        int accidentIndex = cursor.getColumnIndex("accident");

        while (cursor.moveToNext()) {

            Car car = new Car();
            car.setId(cursor.getInt(carIdIndex));
            car.setFactoryName(cursor.getString(factoryNameIndex));
            car.setType(cursor.getString(typeIndex));
            car.setPrice(cursor.getInt(priceIndex));
            car.setModel(cursor.getString(modelIndex));
            car.setName(cursor.getString(nameIndex));
            car.setOffer(cursor.getInt(offerIndex));
            car.setYear(cursor.getString(yearIndex));
            car.setFuelType(cursor.getString(fuelTypeIndex));
            car.setRating(cursor.getFloat(ratingIndex));
            car.setAccident(cursor.getString(accidentIndex));

            cars.add(car);

        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return cars;
    }


}
