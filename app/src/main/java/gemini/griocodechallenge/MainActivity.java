package gemini.griocodechallenge;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //public final static String BUNDLE_WINNER = "winner";
    //public final static String BUNDLE_LOSER = "loser";
    public final static String BUNDLE_ACCOUNT = "users";


    private Button fetch;
    private TextView winner;
    private TextView loser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.findViews();

        //this.setLister();

    }

    private void findViews()
    {
        winner = (TextView) findViewById(R.id.account_one);
        loser = (TextView) findViewById(R.id.account_two);
        final ArrayList<String> users = new ArrayList<>();


        fetch = (Button) findViewById(R.id.start);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.add(winner.getText().toString());
                users.add(loser.getText().toString());

                Intent intent = new Intent(getApplicationContext(), RepoListActivity.class);
                Bundle data = new Bundle();
                data.putSerializable(BUNDLE_ACCOUNT,(ArrayList<String>)users);
                //data.putString(BUNDLE_LOSER,loser.getText().toString());
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }
}
