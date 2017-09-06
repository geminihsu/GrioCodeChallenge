package gemini.griocodechallenge.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gemini.griocodechallenge.R;
import gemini.griocodechallenge.RepoListActivity;
import gemini.griocodechallenge.adapter.GitHubRepoAdapter;
import gemini.griocodechallenge.model.GithubRepoList;


/**
 * Created by User on 2/28/2017.
 */

public class Fragment_repository extends Fragment {
    private static final String TAG = "Fragment_repository";

    private GitHubRepoAdapter gitHubRepoAdapter = new GitHubRepoAdapter();
    private RecyclerView mRecyclerView;

    private String githubUserName;
    private List<GithubRepoList> githubRepoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository,container,false);



        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle.containsKey(RepoListActivity.GithubUser)) {
            githubUserName = bundle.getString(RepoListActivity.GithubUser);
            githubRepoList = (ArrayList<GithubRepoList>)bundle.getSerializable(RepoListActivity.GithubUserRepoList);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        findViews();

        for(int i = 0; i <githubRepoList.size(); i++) {
            gitHubRepoAdapter.addData(githubRepoList.get(i));
        }
        gitHubRepoAdapter.notifyDataSetChanged();

    }

    private void findViews()
    {
        /**
         * Set up Android CardView/RecycleView
         */
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(gitHubRepoAdapter);


    }



}
