package birzeit.edu.androidcarproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 17;
    // Database Name
    private static final String DATABASE_NAME = "Cars_Dealer";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
                "rating REAL,"+
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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
        db.close();
        // If insertion is successful then result != -1
        return result != -1;
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
    // -- TODO:
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

    private void clearDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Car");
    }

    public boolean deleteCustomer(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Customer", "email = ?", new String[]{email});
        db.close();
        return result != 0;
    }
    // -- TODO: display all Cars' data in addition to the reservation thing
    public Cursor displayCustomerReservations(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Reservation WHERE customerEmail=?", new String[]{email});
        return cursor;
    }

    public Cursor displayAllCars(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Car", null);
        return cursor;
    }

    public boolean deleteFavorite(String email, int carID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Specify the deletion condition in the whereClause parameter
        String whereClause = "customerEmail = ? AND carID = ?";

        // Specify the values for the placeholders in the whereArgs parameter
        String[] whereArgs = {email, String.valueOf(carID)};

        // Perform the deletion
        int result = db.delete("Favorites", whereClause, whereArgs);

        // Close the database
        db.close();

        // Return true if any rows were deleted, false otherwise
        return result != 0;
    }


// -- TODO: in the java file, we invoke a getAllCars method, using Select * from Car, and we handle the resulting cursor in the java file
//    public List<Car> getAllCars() {
//        List<Car> carList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM Car", null);
//        int idIndex = cursor.getColumnIndex("id");
//        int typeIndex = cursor.getColumnIndex("type");
//        int factoryIndex = cursor.getColumnIndex("factory");
//        int modelIndex = cursor.getColumnIndex("model");
//        int priceIndex = cursor.getColumnIndex("price");
//
//        while (cursor.moveToNext() && idIndex != -1 && typeIndex != -1 && factoryIndex != -1 && modelIndex != -1 && priceIndex != -1) {
//            Car car = new Car();
//            car.setId(cursor.getInt(idIndex));
//            car.setType(cursor.getString(typeIndex));
//            car.setFactory(cursor.getString(factoryIndex));
//            car.setModel(cursor.getString(modelIndex));
//            car.setPrice(cursor.getDouble(priceIndex));
//
//            carList.add(car);
//        }
//
//        cursor.close();
//        db.close();
//        return carList;
//    }
    // -- TODO: Delete it, as it is changed according to the Car class

//    public boolean insertCar(Car car) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("id", car.getId());
//        values.put("type", car.getType());
//        values.put("factory", car.getFactory());
//        values.put("model", car.getModel());
//        values.put("price", car.getPrice());
//
//        // Insert the values into the "Car" table
//        long result = db.insert("Car", null, values);
//        db.close();
//        // If insertion is successful then result != -1
//        return result != -1;
//    }

    // -- TODO: No need for the seed database as the info is gonna be got from the API
//    public void seedDatabase() {
//        clearDatabase();
//
//        // Create car
//        String[] types = {"Sedan", "SUV", "Truck", "Coupe", "Convertible"};
//        String[] factories = {"Toyota", "Honda", "Ford", "Chevrolet", "BMW"};
//        String[] models = {"Camry", "Civic", "F-150", "Malibu", "3 Series"};
//
//        for (int i = 0; i < 25; i++) {
//            // Randomly select values for type, factory, and model
//            String type = types[i % types.length];
//            String factory = factories[i % factories.length];
//            String model = models[i % models.length];
//
//            // Create car objects with unique IDs and values
//            Car car = new Car(
//                    i + 1,
//                    type,
//                    factory,
//                    model,
//                    50000 + i * 1000
//            );
//
//            // Insert each car into the database
//            insertCar(car);
//        }
//    }



}
