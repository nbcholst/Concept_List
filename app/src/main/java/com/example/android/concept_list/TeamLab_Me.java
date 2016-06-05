package com.example.android.concept_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.concept_list.database.TeamDbSchema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cholni01 on 6/4/2016.
 */
public class TeamLab_Me {

    private static TeamLab_Me sTeamlab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static final String DATABASE_NAME = "myTeamBase.db";

    private TeamLab_Me(Context context) {

        //retrieve Team database. Getwriteabledatabase also creates a new database file if it doesn't exist already
        mContext = context.getApplicationContext();
        mDatabase = new TeamBaseHelper(mContext, DATABASE_NAME).getWritableDatabase();
    }

    //fill current database with teams from csv file
    public void updateDatabase(Context context) {

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.teamlist);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line = "";
            mDatabase.beginTransaction();

            while ((line = buffer.readLine()) != null) {

                //put row values into str
                String[] str = line.split(",");
                String ID = str[0];
                String NAME = str[1];
                String LEAGUE = str[2];
                String COUNTRY = str[3];
                Boolean SELECTED = false;

                //create team
                Team mTeam = new Team();
                mTeam.setId(ID);
                mTeam.setName(NAME);
                mTeam.setLeague(LEAGUE);
                mTeam.setCountry(COUNTRY);
                mTeam.setSelected(SELECTED);

                //add team to database
                addTeam(mTeam);
            }

        } catch (IOException ioe){
            Log.e("ERROR", "Could not load " + ioe);
        }

        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public void addTeam(Team c) {
        ContentValues values = getContentValues(c);

        //first value is name of the table, third value is the value itself
        // second value is nullColumnHack which allows you to insert empty rows
        mDatabase.insert(TeamDbSchema.TeamTable.NAME, null, values);
    }

    public List<Team> getTeams(){

        //initialize Teams array, retrieve all Team cursors by using null parameters
        List<Team> teams = new ArrayList<>();
        TeamCursorWrapper cursor = queryTeams(null, null);

        //cycle through cursors and add to array
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                teams.add(cursor.getTeam());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return teams;
    }

    //convert team class to a ContentValue for database storing
    private static ContentValues getContentValues(Team team) {
        ContentValues values = new ContentValues();
        values.put(TeamDbSchema.TeamTable.Cols.ID, team.getId());
        values.put(TeamDbSchema.TeamTable.Cols.NAME, team.getName());
        values.put(TeamDbSchema.TeamTable.Cols.LEAGUE, team.getLeague());
        values.put(TeamDbSchema.TeamTable.Cols.COUNTRY, team.getCountry());
        values.put(TeamDbSchema.TeamTable.Cols.SELECTED, team.isSelected() ? 1 : 0);
        return values;
    }

    //use SQL cursor to retrieve data from database.
    // cursors give you raw column values based on inputs
    private TeamCursorWrapper queryTeams(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TeamDbSchema.TeamTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new TeamCursorWrapper(cursor);
    }

    public static TeamLab_Me get(Context context){
        if (sTeamlab == null){
            Log.d("No Old Teamlab Found", "True");
            sTeamlab = new TeamLab_Me(context);
        }

        Log.d("Old Teamlab Found", "True");

        return sTeamlab;
    }
}