package com.example.android.concept_list;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new TeamListFragment();
    }
}
