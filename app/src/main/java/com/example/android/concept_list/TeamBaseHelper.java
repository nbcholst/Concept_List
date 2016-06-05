package com.example.android.concept_list;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.concept_list.database.TeamDbSchema;
import com.example.android.concept_list.database.TeamDbSchema.TeamTable;

/**
 * Created by cholni01 on 5/30/2016.
 */
public class TeamBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public TeamBaseHelper(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //create the database
    public void onCreate(SQLiteDatabase db) {
        //create the table
        db.execSQL("create table " + TeamDbSchema.TeamTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TeamTable.Cols.ID + ", " +
                TeamTable.Cols.NAME + ", " +
                TeamTable.Cols.LEAGUE + ", " +
                TeamTable.Cols.COUNTRY + ", " +
                TeamTable.Cols.SELECTED +
                ")"
        );
    }

    //update the database if it's an older version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
