package gemini.griocodechallenge;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;


import gemini.griocodechallenge.fragment.Fragment_loser;
import gemini.griocodechallenge.fragment.Fragment_winner;


/**
 * Created by geminihsu on 04/09/2017.
 */

public class RepoListActivity extends AppCompatActivity {
    public final static String GithubUser = "GithubUser";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private String winnerUser;
    private String loserUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repolist_activity);

        //Log.d(TAG, "onCreate: Starting.");
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            winnerUser = bundle.getString(MainActivity.BUNDLE_WINNER);
            loserUser = bundle.getString(MainActivity.BUNDLE_LOSER);
        }
            mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        Fragment winner = new Fragment_winner();
        Bundle b1 = new Bundle();
        b1.putString(GithubUser, winnerUser);
        winner.setArguments(b1);

        Fragment loser = new Fragment_winner();
        Bundle b2 = new Bundle();
        b2.putString(GithubUser, loserUser);
        loser.setArguments(b2);

        adapter.addFragment(winner, getString(R.string.winner));
        adapter.addFragment(loser, getString(R.string.loser));
        //adapter.addFragment(new Tab3Fragment(), "TAB3");
        viewPager.setAdapter(adapter);
    }
}
