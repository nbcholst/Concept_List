package com.example.android.concept_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cholni01 on 5/30/2016.
 */
public class MyTeamListFragment extends Fragment {

    private RecyclerView mTeamRecyclerView;
    private TeamAdapter mAdapter;
    private TextView mWarningTextView;
    private int itemPosition;

    //create options menu
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myteam_list, container, false);
        mTeamRecyclerView = (RecyclerView) view.findViewById(R.id.my_team_recycler_view);

        //layout manager handles the positioning of items and also defines the scrolling behavior
        mTeamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //define warning text
        mWarningTextView = (TextView) view.findViewById(R.id.my_no_team_warning);

        //add button click listener to bring team select activity
        Button mAddTeamButton = (Button) view.findViewById(R.id.add_to_my_team);

        //add a team selection button to click
        mAddTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeamListActivity.class);
                startActivity(intent);
            }
        });

        //update
        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }


    private void updateUI() {
        TeamLab teamLab = TeamLab.get(getActivity());
        List<Team> teams = teamLab.getTeams();

        if (mAdapter == null) {
            mAdapter = new TeamAdapter(teams);
            mTeamRecyclerView.setAdapter(mAdapter);
        } else {

            mAdapter.setTeams(teams);
            mAdapter.notifyItemChanged(itemPosition);
        }

        //Retrieve number of Teams, set warning textview visibility off if there are Teams
        int teamSize = teams.size();
        if (teamSize > 0) {
            mWarningTextView.setVisibility(TextView.INVISIBLE);
        }
    }

    //define a viewholder as an inner class
    private class TeamHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDescTextView;

        private Team mTeam;

        public TeamHolder(View itemView){
            //super refers to parent object (in this case TeamHolder, so this
            //function effectively sets the public textview to the input of TeamHolder
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.teamTitle);
            mDescTextView = (TextView)
                    itemView.findViewById(R.id.teamDesc);
        }

        public void bindTeam(Team team) {
            mTeam = team;
            mTitleTextView.setText(mTeam.getName());
            mDescTextView.setText(mTeam.getLeague());
        }

        @Override
        public void onClick(View v){
            //get item position that was clicked on
            itemPosition = mTeamRecyclerView.getChildAdapterPosition(v);

            //start new intent, pass on ID and item position
            //old version Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId(), itemPosition);

            //TODO: Add intent later
        }
    }

    //define the adapter now
    private class TeamAdapter extends RecyclerView.Adapter<TeamHolder>{
        private List<Team> mTeams;
        public TeamAdapter (List<Team> teams){
            mTeams = teams;
        }

        //call this function when recycler needs a new view to display an item
        //create a view and wrap it in a viewholder
        @Override
        public TeamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            //simple list item 1 is a simple textview
            View view = layoutInflater.inflate(R.layout.list_item_team, parent, false);
            return new TeamHolder(view);
        }

        //recieves the viewholder and a position in your dataset, binds it to model object
        //retrieves crime using position, adds text from teams into viewholder
        @Override
        public void onBindViewHolder(TeamHolder holder, int position) {
            Team team = mTeams.get(position);
            holder.bindTeam(team);
        }
        @Override
        public int getItemCount() {
            return mTeams.size();
        }

        //we use this to update teans
        public void setTeams(List<Team> teams) {
            mTeams = teams;
        }
    }

}