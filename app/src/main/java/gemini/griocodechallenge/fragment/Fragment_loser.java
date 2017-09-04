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

/**
 * Created by User on 2/28/2017.
 */

public class Fragment_loser extends Fragment {
    private static final String TAG = "Fragment_loser";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loser,container,false);

        return view;
    }
}
