package com.example.android.concept_list.database;

/**
 * Created by cholni01 on 5/30/2016.
 */
public class TeamDbSchema {
    public static final class TeamTable {
        public static final String NAME = "teams";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String LEAGUE = "league";
            public static final String COUNTRY = "country";
            public static final String SELECTED = "selected";
        }
    }
}
