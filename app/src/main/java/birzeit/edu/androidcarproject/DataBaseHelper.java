package birzeit.edu.androidcarproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
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

        db.execSQL(createAdminTable);
        db.execSQL(createCustomerTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertAdmin(Admin admin){
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

    public boolean insertCustomer(Customer customer){
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
        String[] tableNames = {"Admin", "Student"};

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
}
