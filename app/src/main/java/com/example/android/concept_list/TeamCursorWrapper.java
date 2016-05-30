package com.example.android.concept_list;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.android.concept_list.database.TeamDbSchema.TeamTable;


/**
 * Created by cholni01 on 5/30/2016.
 */
public class TeamCursorWrapper extends CursorWrapper {

    public TeamCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Team getTeam() {
        String idString = getString(getColumnIndex(TeamTable.Cols.ID));
        String name = getString(getColumnIndex(TeamTable.Cols.NAME));
        String league = getString(getColumnIndex(TeamTable.Cols.LEAGUE));
        String country = getString(getColumnIndex(TeamTable.Cols.COUNTRY));
        int isSelected = getInt(getColumnIndex(TeamTable.Cols.SELECTED));

        Team team = new Team();
        team.setId(idString);
        team.setName(name);
        team.setLeague(league);
        team.setCountry(country);
        team.setSelected(isSelected != 0);
        return team;

    }
}
