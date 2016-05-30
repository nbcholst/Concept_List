package com.example.android.concept_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.concept_list.database.TeamDbSchema.TeamTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cholni01 on 5/30/2016.
 */
public class TeamLab {

    private static TeamLab sTeamlab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private TeamLab(Context context){

        //retrieve Team database. Getwriteabledatabase also creates a new database file if it doesn't exist already
        mContext = context.getApplicationContext();
        mDatabase = new TeamBaseHelper(mContext).getWritableDatabase();

    }

    public void addTeam(Team c) {
        ContentValues values = getContentValues(c);

        //first value is name of the table, third value is the value itself
        // second value is nullColumnHack which allows you to insert empty rows
        mDatabase.insert(TeamTable.NAME, null, values);
    }

    public void removeTeam(Team c) {
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

    public Team getTeam(String id) {
        TeamCursorWrapper cursor = queryTeams(
                TeamTable.Cols.ID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTeam();
        } finally {
            cursor.close();
        }
    }

    public void updateTeam (Team team) {
        String idString = team.getId();
        ContentValues values = getContentValues(team);

        //update database and specify which rows get updated
        //we use ? text identifier to make sure input idString is treated as a string and not a SQL injection attack
        mDatabase.update(TeamTable.NAME, values,
                TeamTable.Cols.ID + " = ?",
                new String[]{idString});
    }

    //convert team class to a ContentValue for database storing
    private static ContentValues getContentValues(Team team) {
        ContentValues values = new ContentValues();
        values.put(TeamTable.Cols.ID, team.getId().toString());
        values.put(TeamTable.Cols.NAME, team.getName());
        values.put(TeamTable.Cols.LEAGUE, team.getLeague());
        values.put(TeamTable.Cols.COUNTRY, team.getCountry());
        values.put(TeamTable.Cols.SELECTED, team.isSelected() ? 1 : 0);
        return values;
    }

    //use SQL cursor to retrieve data from database.
    // cursors give you raw column values based on inputs
    private TeamCursorWrapper queryTeams(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TeamTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new TeamCursorWrapper(cursor);
    }

    public static TeamLab get(Context context){
        if (sTeamlab == null){
            sTeamlab = new TeamLab(context);
        }
        return sTeamlab;
    }
}
