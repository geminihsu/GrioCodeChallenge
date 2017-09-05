package gemini.griocodechallenge.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gemini.griocodechallenge.R;
import gemini.griocodechallenge.model.GithubRepoList;
import gemini.griocodechallenge.model.GithubRepoListComparator;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by geminihsu on 04/09/2017.
 */

public class FetchData extends Service {
    private final String TAG = this.getClass().getSimpleName();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private List<GithubRepoList> fetchDataList(String userName)
    {
        List<GithubRepoList> result = new ArrayList<>();
        GitHubClient service = ServiceFactory.createRetrofitService(GitHubClient.class, GitHubClient.SERVICE_ENDPOINT);
        //for(String githubUserName : data) {
            service.reposForUser(userName)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<GithubRepoList>>() {

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("GithubDemo", e.getMessage());


                        }

                        @Override
                        public void onNext(List<GithubRepoList> githubRepoLists) {

                            Collections.sort(githubRepoLists, new GithubRepoListComparator());

                            for (int i = 0; i < githubRepoLists.size(); i++) {
                                GithubRepoList githubRepo = githubRepoLists.get(i);

                                //gitHubRepoAdapter.addData(githubRepo);
                                
                                Log.e(TAG, githubRepoLists.get(i).getDescription());
                            }
                        }
                    });
       // }
    }
}
