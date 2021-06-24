package com.sseung.slidingpuzzle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PuzzleDatabase {

    private static PuzzleDatabase database;
    public static String TABLE1 = "NICKNAME";
    public static int DATABASE_VERSION = 1;

    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db;
    private static Context context;

    private PuzzleDatabase(Context context) {
        this.context = context;
    }
    public static void print(String text){
        Log.d("database", text);
    }

    public static PuzzleDatabase getInstance(Context context){
        if (database == null){
            print("database null");
            database = new PuzzleDatabase(context);
        }

        return database;
    }

    public void create(){
        dbHelper.onCreate(db);
    }

    public static boolean open(){
        print("opening database [" + PublicFunction.DataBaseNAME + "].");


        dbHelper = new DatabaseHelper(context, DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    public void close(){
        print("closing database [" + PublicFunction.DataBaseNAME + "].");

        db.close();
        database = null;
    }

    public Cursor rawQuery(String SQL){
        print("\nexecuteQuery called.\n");

        Cursor cursor = null;

        try{
            cursor = db.rawQuery(SQL, null);
            print("cursor count : " + cursor.getCount());
        } catch (Exception e){
            print("Exception in executeQuery : " + e);
        }

        return cursor;
    }

    public boolean execSQL(String SQL){
        print("\nexecute called.\n");

        try{
            print("SQL : " + SQL);
            db.execSQL(SQL);
        } catch (Exception e){
            print("Exception in executeQuery : " + e);
            return false;
        }

        return true;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, int version){
            super(context, PublicFunction.DataBaseNAME, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            print("creating database [" + PublicFunction.DataBaseNAME + "].");

            print("creating table [" + TABLE1 + "].");

            String DROP_SQL1 = "drop table if exists " + TABLE1;

            try{
                db.execSQL(DROP_SQL1);
            } catch (Exception e) {print("Exception in DROP_SQL1 : " + e);}

            String CREATE_SQL1 = "create table " + TABLE1 + "("
                    + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + " nickname TEXT DEFAULT '' "
                    + ")";

            try {
                db.execSQL(CREATE_SQL1);
            } catch (Exception e){print("Exception in CREATE_SQL1 : " + e);}

            String CREATE_INDEX_SQL1 = "create index " + TABLE1 + "_IDX ON " + TABLE1 + "("
                    + "CREATE_DATE" + ")";

            try {
                db.execSQL(CREATE_INDEX_SQL1);
            } catch (Exception e) {
                print("Exception in CREATE_INDEX_SQL1 : " + e);
            }
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);

            print("opened database [" + PublicFunction.DataBaseNAME + "].");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            print("upgrade database from version " + oldVersion + " to " + newVersion + ".");
        }
    }
}
