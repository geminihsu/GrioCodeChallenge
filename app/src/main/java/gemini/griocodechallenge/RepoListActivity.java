package gemini.griocodechallenge;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gemini.griocodechallenge.fragment.Fragment_loser;
import gemini.griocodechallenge.fragment.Fragment_winner;
import gemini.griocodechallenge.model.GithubRepoList;
import gemini.griocodechallenge.util.FetchDataUtil;


/**
 * Created by geminihsu on 04/09/2017.
 */

public class RepoListActivity extends AppCompatActivity {
    public final static String TAG = RepoListActivity.class.toString();
    public final static String GithubUser = "GithubUser";
    public final static String GithubUserRepoList = "GithubUserRepoList";


    private SectionsPageAdapter mSectionsPageAdapter;


    private ViewPager mViewPager;
    private TabLayout tabLayout;

    private List<String> users;
    private FetchDataUtil fetchDataUtil;
    private HashMap<String,List<GithubRepoList>> githubRepoListMap = new HashMap<String,List<GithubRepoList>>();
    private HashMap<String,Integer> githubCountMap = new HashMap<String,Integer>();

    private String winnerName = "";
    private String loserName = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repolist_activity);
        fetchDataUtil = new FetchDataUtil();
        fetchDataUtil.setServerQueryRepoListManagerCallBackFunction(new FetchDataUtil.ServerQueryRepoListManagerCallBackFunction(){

            @Override
            public void setRepoList(List<GithubRepoList> repoLists,String userName) {
                for (int i = 0; i < repoLists.size(); i++) {
                    GithubRepoList githubRepo = repoLists.get(i);
                    //Log.e(TAG, githubRepo.getDescription());
                }
                githubRepoListMap.put(userName,repoLists);

            }

            @Override
            public void setRepoTotal(String userName,int count) {
                githubCountMap.put(userName,count);
            }

            @Override
            public void isDone(boolean isDone) {
                if(isDone)
                {
                    sortStartByCount();
                    setupViewPager(mViewPager);
                    tabLayout.setupWithViewPager(mViewPager);
                }
            }
        });
        //Log.d(TAG, "onCreate: Starting.");
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
           // winnerUser = bundle.getString(MainActivity.BUNDLE_WINNER);
           // loserUser = bundle.getString(MainActivity.BUNDLE_LOSER);
            users = (ArrayList<String>)bundle.getSerializable(MainActivity.BUNDLE_ACCOUNT);
        }

        this.findViews();
        for(int i = 0; i < users.size(); i++) {
        fetchDataUtil.fetchDataList(users.get(i),users.size());

        }

    }

    private void findViews()
    {
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());


    }

    @Override
    public void onStart() {
        super.onStart();

    }
    private void setupViewPager(ViewPager viewPager) {

        List<GithubRepoList> winnerData = githubRepoListMap.get(winnerName);
        List<GithubRepoList> loserData = githubRepoListMap.get(loserName);

        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        Fragment winner = new Fragment_winner();
        Bundle b1 = new Bundle();
        b1.putString(GithubUser, winnerName);
        b1.putSerializable(GithubUserRepoList, (ArrayList<GithubRepoList>)winnerData);
        winner.setArguments(b1);

        Fragment loser = new Fragment_loser();
        Bundle b2 = new Bundle();
        b2.putString(GithubUser, loserName);
        b2.putSerializable(GithubUserRepoList, (ArrayList<GithubRepoList>)loserData);
        loser.setArguments(b2);

        adapter.addFragment(winner, getString(R.string.winner) +"-"+ winnerName+"\ntotal:"+githubCountMap.get(winnerName));
        adapter.addFragment(loser, getString(R.string.loser) +"-"+ loserName+"\ntotal:"+githubCountMap.get(loserName));
        //adapter.addFragment(new Tab3Fragment(), "TAB3");
        viewPager.setAdapter(adapter);
    }

    private void sortStartByCount()
    {
        List<Map.Entry<String, Integer>> list_Data =
                new ArrayList<Map.Entry<String, Integer>>(githubCountMap.entrySet());


        Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>(){
            public int compare(Map.Entry<String, Integer> entry1,
                               Map.Entry<String, Integer> entry2){
                return (entry2.getValue() - entry1.getValue());
            }
        });
        winnerName = list_Data.get(0).getKey();
        loserName  = list_Data.get(list_Data.size()-1).getKey();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
