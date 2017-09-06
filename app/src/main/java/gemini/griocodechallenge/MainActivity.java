package gemini.griocodechallenge;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public final static String BUNDLE_ACCOUNT = "users";
    public final static String BUNDLE_RESULT = "result";


    private Button fetch;
    private TextView winner;
    private TextView loser;
    private TextView result;

    private String resultInfo = "";
    private ArrayList<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            resultInfo = bundle.getString(MainActivity.BUNDLE_RESULT);
            users = (ArrayList<String>)bundle.getSerializable(MainActivity.BUNDLE_ACCOUNT);
        }else
            users = new ArrayList<>();

        this.findViews();

    }

    @Override
    protected void onStop() {
        super.onStop();
        resultInfo = null;
    }
    private void findViews()
    {
        winner = (TextView) findViewById(R.id.account_one);
        result = (TextView) findViewById(R.id.result);
        if(resultInfo!=null) {
            if (!resultInfo.equals("")) {
                result.setVisibility(View.VISIBLE);
                result.setText(resultInfo);
            }
        }
        loser = (TextView) findViewById(R.id.account_two);


        fetch = (Button) findViewById(R.id.start);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.add(winner.getText().toString());
                users.add(loser.getText().toString());

                Intent intent = new Intent(getApplicationContext(), RepoListActivity.class);
                Bundle data = new Bundle();
                data.putSerializable(BUNDLE_ACCOUNT,(ArrayList<String>)users);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }
}
