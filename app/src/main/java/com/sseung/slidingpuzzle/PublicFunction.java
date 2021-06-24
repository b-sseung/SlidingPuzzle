package com.sseung.slidingpuzzle;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.HashMap;

public class PublicFunction {

    public static String DataBaseNAME = "PuzzleDatabase";
    public static Context context;

    public static String loadNickName(){
        PuzzleDatabase db = PuzzleDatabase.getInstance(context);

        String sql = "select nickname "
                + "from " + PuzzleDatabase.TABLE1 + " ";

        Log.d("database", sql);

        Cursor cursor = db.rawQuery(sql);
        int recordCound = cursor.getCount();
        if (cursor == null || recordCound == 0) {
            return "noData";
        }

        for (int i = 0; i < recordCound; i++){
            cursor.moveToNext();

            String nickname = cursor.getString(0);

            return nickname;
        }

        return "noData";
    }

    public static void addData(String name) {
        PuzzleDatabase db = PuzzleDatabase.getInstance(context);

        String sql = "insert into " + PuzzleDatabase.TABLE1 +
                "(nickname) values ("
                + "'" + name + "')";

        Log.d("tlqkf", "sql : " + sql);

        db.execSQL(sql);
    }
}
