package gemini.griocodechallenge.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import gemini.griocodechallenge.R;
import gemini.griocodechallenge.RepoListActivity;
import gemini.griocodechallenge.adapter.GitHubRepoAdapter;
import gemini.griocodechallenge.model.GithubRepoList;
import gemini.griocodechallenge.model.GithubRepoListComparator;
import gemini.griocodechallenge.service.GitHubClient;
import gemini.griocodechallenge.service.ServiceFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by User on 2/28/2017.
 */

public class Fragment_winner extends Fragment {
    private static final String TAG = "Fragment_winner";

    private GitHubRepoAdapter gitHubRepoAdapter = new GitHubRepoAdapter();
    private RecyclerView mRecyclerView;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    private String githubUserName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_winner,container,false);



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
        getActivity().setTitle(getString(R.string.winner)+githubUserName);
        findViews();
        fetchDataList();
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


    private void fetchDataList()
    {
        GitHubClient service = ServiceFactory.createRetrofitService(GitHubClient.class, GitHubClient.SERVICE_ENDPOINT);
            service.reposForUser(githubUserName)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<GithubRepoList>>() {

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("GithubDemo", e.getMessage());
                            alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            // set title
                            alertDialogBuilder.setTitle(getString(R.string.fetch));
                            alertDialogBuilder
                                    .setMessage(getString(R.string.fetch_error))
                                    .setCancelable(false)
                                    .setNegativeButton(getString(R.string.g_ok), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                                            // startActivity(intent);
                                            alertDialog.dismiss();
                                            alertDialog = null;
                                        }
                                    });
                            if(alertDialog==null) {
                                alertDialog = alertDialogBuilder.create();
                                // show it
                                alertDialog.show();
                            }


                        }

                        @Override
                        public void onNext(List<GithubRepoList> githubRepoLists) {

                            Collections.sort(githubRepoLists, new GithubRepoListComparator());
                            for(int i = 0 ; i < githubRepoLists.size() ; i++) {
                                GithubRepoList githubRepo = githubRepoLists.get(i);

                                gitHubRepoAdapter.addData(githubRepo);
                                Log.e(TAG, githubRepoLists.get(i).getDescription());
                            }
                        }
                    });

    }
}
