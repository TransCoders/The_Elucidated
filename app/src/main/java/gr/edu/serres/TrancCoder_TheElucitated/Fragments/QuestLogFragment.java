package gr.edu.serres.TrancCoder_TheElucitated.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gr.edu.serres.TrancCoder_TheElucitated.R;


public class QuestLogFragment extends Fragment {

    public QuestLogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_quest_log, container, false);
    }
}
