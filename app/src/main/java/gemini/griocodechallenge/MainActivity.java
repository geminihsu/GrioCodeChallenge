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

    public final static int GITHUB_ACCOUNT_LIST = 1;

    private Button fetch;
    private TextView user1;
    private TextView user2;
    private TextView result;

    private String resultInfo = "";
    private ArrayList<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        this.findViews();

    }

    @Override
    protected void onStop() {
        super.onStop();
        resultInfo = null;
    }
    private void findViews()
    {
        user1 = (TextView) findViewById(R.id.account_one);
        result = (TextView) findViewById(R.id.result);

        user2 = (TextView) findViewById(R.id.account_two);


        fetch = (Button) findViewById(R.id.start);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!users.contains(user1.getText().toString()))
                     users.add(user1.getText().toString());
                if(!users.contains(user2.getText().toString()))
                     users.add(user2.getText().toString());

                Intent intent = new Intent(MainActivity.this, RepoListActivity.class);
                Bundle data = new Bundle();
                data.putSerializable(BUNDLE_ACCOUNT,(ArrayList<String>)users);
                intent.putExtras(data);
                startActivityForResult(intent,GITHUB_ACCOUNT_LIST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GITHUB_ACCOUNT_LIST:
                if(data!=null) {
                    Bundle info = data.getExtras();
                    if (info != null) {

                        users = (ArrayList<String>) info.getSerializable(BUNDLE_ACCOUNT);
                        resultInfo = info.getString(MainActivity.BUNDLE_RESULT);
                        if (resultInfo != null) {
                            if (!resultInfo.equals("")) {
                                result.setVisibility(View.VISIBLE);
                                result.setText(resultInfo);
                            }
                        }
                        break;
                    }
                }
        }
    }

}
