package com.example.android.concept_list;

/**
 * Created by cholni01 on 5/30/2016.
 */
public class Team {

    private String mId;
    private String mName;
    private String mLeague;
    private String mCountry;
    private boolean mSelected;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLeague() {
        return mLeague;
    }

    public void setLeague(String league) {
        mLeague = league;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }
}
