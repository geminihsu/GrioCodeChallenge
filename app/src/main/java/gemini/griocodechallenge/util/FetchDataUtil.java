package gemini.griocodechallenge.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import gemini.griocodechallenge.model.GithubRepoList;
import gemini.griocodechallenge.service.GitHubClient;
import gemini.griocodechallenge.service.ServiceFactory;
import gemini.griocodechallenge.comparator.GithubRepoListComparator;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by geminihsu on 05/09/2017.
 */

public class FetchDataUtil {
    private final String TAG = this.getClass().getSimpleName();
    private int count;

    public void fetchDataList(final String userName, final int userCount)
    {

        final List<GithubRepoList> result = new ArrayList<>();
        GitHubClient service = ServiceFactory.createRetrofitService(GitHubClient.class, GitHubClient.SERVICE_ENDPOINT);
        //for(String githubUserName : userName) {
        service.reposForUser(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GithubRepoList>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("GithubDemo", e.toString());


                    }

                    @Override
                    public void onNext(List<GithubRepoList> githubRepoLists) {

                        Collections.sort(githubRepoLists, new GithubRepoListComparator());
                        count++;
                        int total = 0;
                        for (int i = 0; i < githubRepoLists.size(); i++) {
                            GithubRepoList githubRepo = githubRepoLists.get(i);

                            //gitHubRepoAdapter.addData(githubRepo);
                            result.add(githubRepo);
                            total+=githubRepo.getStargazers_count();
                            //Log.e(TAG, githubRepoLists.get(i).getDescription());
                        }


                        if(mServerQueryRepoListManagerCallBackFunction != null) {
                            mServerQueryRepoListManagerCallBackFunction.setRepoList(result,userName);
                            mServerQueryRepoListManagerCallBackFunction.setRepoTotal(userName,total);
                            if(count == userCount)
                                mServerQueryRepoListManagerCallBackFunction.isDone(true);
                        }
                    }
                });


        // }
    }

    private ServerQueryRepoListManagerCallBackFunction mServerQueryRepoListManagerCallBackFunction;

    public void setServerQueryRepoListManagerCallBackFunction(ServerQueryRepoListManagerCallBackFunction serverQueryNewsListManagerCallBackFunction) {
        mServerQueryRepoListManagerCallBackFunction = serverQueryNewsListManagerCallBackFunction;

    }

    public interface ServerQueryRepoListManagerCallBackFunction {
        public void setRepoList(List<GithubRepoList> repoLists,String userName);
        public void setRepoTotal(String userName,int count);
        public void isDone(boolean isDone);

    }

}
