package com.example.m999g.memecreator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class TopSectionFragment extends Fragment {
    private static EditText top;
    private static EditText bottom;
    private Button button;
    TopSectionInterface activityCommander;

    public interface TopSectionInterface {
        public void createMeme(String top, String Bottom);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityCommander = (TopSectionInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_fragment, container, false);

        top = (EditText) view.findViewById(R.id.topText);
        bottom = (EditText) view.findViewById(R.id.BottomText);
        button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        buttonClicked(view);
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
        );

        return view;
    }

    public void buttonClicked(View view) {
        activityCommander.createMeme(top.getText().toString(), bottom.getText().toString());
    }
}
