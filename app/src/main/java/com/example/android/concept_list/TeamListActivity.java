package com.example.android.concept_list;

import android.support.v4.app.Fragment;

/**
 * Created by cholni01 on 5/30/2016.
 */
public class TeamListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new TeamListFragment();
    }
}
