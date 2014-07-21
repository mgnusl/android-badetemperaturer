package no.kreativo.badetemperaturer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import no.kreativo.badetemperaturer.data.Place;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance = null;

    private static final String DATABASE_NAME = "favorites_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PLACE = "place";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";

    private static final String[] COLUMNS_PLACE = {KEY_ID, KEY_TITLE};

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     *
     * All entries that exists in the db are favorites
     *
     */

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_NEWSPAPER_TABLE = "CREATE TABLE " + TABLE_PLACE + "( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TITLE + " TEXT )";

        db.execSQL(CREATE_NEWSPAPER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
        onCreate(db);
    }


    public boolean addFavorite(Place place) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, place.getShortName());

        int rowId = safeLongToInt(db.insert(TABLE_PLACE, null, values));
        db.close();
        return rowId > -1;

    }

    public boolean removeFavorite(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.delete(TABLE_PLACE, KEY_TITLE + " = ?", new String[]{String.valueOf(place.getShortName())}) > 0;
        db.close();
        return success;
    }

    public List<String> getAllFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PLACE;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> favoritesList = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                favoritesList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return favoritesList;
    }

    // Helper methods
    private static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}
