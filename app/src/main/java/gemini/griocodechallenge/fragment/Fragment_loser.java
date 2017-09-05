package gemini.griocodechallenge.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import gemini.griocodechallenge.R;
import gemini.griocodechallenge.RepoListActivity;

/**
 * Created by User on 2/28/2017.
 */

public class Fragment_loser extends Fragment {
    private static final String TAG = "Fragment_loser";

    private String githubUserName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loser,container,false);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle.containsKey(RepoListActivity.GithubUser)) {
            githubUserName = bundle.getString(RepoListActivity.GithubUser);

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.loser)+githubUserName);

    }
}
