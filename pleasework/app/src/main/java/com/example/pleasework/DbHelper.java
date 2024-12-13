package com.example.pleasework;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "PlayerDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table and Columns
    public static final String TABLE_NAME = "Player";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_HIGHSCORE = "highscore";

    // Create Table SQL
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_HIGHSCORE + " INTEGER NOT NULL)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE); // Create the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // Drop the table if it exists
        onCreate(db); // Recreate the table
    }
    public Cursor getAllPlayers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    public void insertPlayer(String name, int highScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_HIGHSCORE, highScore);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public String GetTop5() {

        SQLiteDatabase db = this.getReadableDatabase();
      
        StringBuilder topPlayers = new StringBuilder();
        String sql= "SELECT "+COLUMN_NAME+","+COLUMN_HIGHSCORE+" FROM " + DbHelper.TABLE_NAME + " ORDER BY " + DbHelper.COLUMN_HIGHSCORE + " DESC LIMIT 5";
        // Query to get top 5 players sorted by high score in descending order
        Cursor cursor = db.rawQuery(sql, null);
        Log.d("s",sql);
        if (cursor.moveToFirst()) {
            int rank = 1; // Add ranking numbers
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_NAME));
                int highScore = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_HIGHSCORE));
                Log.d("s",name);
                // Append formatted name and score
                topPlayers.append(rank).append(". ").append(name).append(": ").append(highScore).append("\n");
                rank++;
            } while (   cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return topPlayers.toString(); // Return the formatted string
    }
public void DeleteAll(SQLiteDatabase db){
    db.execSQL("DELETE FROM "+ TABLE_NAME); // Create the table
}



}
