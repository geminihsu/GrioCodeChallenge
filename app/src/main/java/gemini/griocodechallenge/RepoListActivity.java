package gemini.griocodechallenge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gemini.griocodechallenge.fragment.Fragment_repository;
import gemini.griocodechallenge.model.GithubRepoList;
import gemini.griocodechallenge.util.FetchDataUtil;


/**
 * Created by geminihsu on 04/09/2017.
 */

public class RepoListActivity extends AppCompatActivity {
    public final static String TAG = RepoListActivity.class.toString();
    public final static String GithubUser = "GithubUser";
    public final static String GithubUserRepoList = "GithubUserRepoList";
    //actionBar item Id
    private final int ACTIONBAR_MENU_ITEM_ADD = 0x0001;


    private SectionsPageAdapter mSectionsPageAdapter;

    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;


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

            @Override
            public void error(boolean isError) {
                alertDialogBuilder = new AlertDialog.Builder(RepoListActivity.this);
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
                                finish();
                            }
                        });
                if(alertDialog==null) {
                    alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }

            }
        });

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(Menu.NONE, ACTIONBAR_MENU_ITEM_ADD, Menu.NONE, getString(R.string.add));
        SpannableString spanString = new SpannableString(item.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spanString.length(), 0); //fix the color to white
        item.setTitle(spanString);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case ACTIONBAR_MENU_ITEM_ADD:
                String result = "";
                for (int i = 0; i < users.size() ; i++)
                {
                    result += users.get(i);
                    if(i!= users.size()-1)
                        result += " V.S ";
                }
                result +="\nWinner: "+winnerName+", Loser: "+loserName;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle data = new Bundle();
                data.putString(MainActivity.BUNDLE_RESULT,result);
                data.putSerializable(MainActivity.BUNDLE_ACCOUNT,(ArrayList<String>)users);
                intent.putExtras(data);
                startActivity(intent);
                return true;
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setupViewPager(ViewPager viewPager) {

        List<GithubRepoList> winnerData = githubRepoListMap.get(winnerName);
        List<GithubRepoList> loserData = githubRepoListMap.get(loserName);

        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        //created winner page
        Fragment winner = new Fragment_repository();
        Bundle b1 = new Bundle();
        b1.putString(GithubUser, winnerName);
        b1.putSerializable(GithubUserRepoList, (ArrayList<GithubRepoList>)winnerData);
        winner.setArguments(b1);

        //created loser page
        Fragment loser = new Fragment_repository();
        Bundle b2 = new Bundle();
        b2.putString(GithubUser, loserName);
        b2.putSerializable(GithubUserRepoList, (ArrayList<GithubRepoList>)loserData);
        loser.setArguments(b2);

        adapter.addFragment(winner, getString(R.string.winner) +"-"+ winnerName+"\ntotal:"+githubCountMap.get(winnerName));
        adapter.addFragment(loser, getString(R.string.loser) +"-"+ loserName+"\ntotal:"+githubCountMap.get(loserName));

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
