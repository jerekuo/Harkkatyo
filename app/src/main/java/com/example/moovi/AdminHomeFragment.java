package com.example.moovi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminHomeFragment extends Fragment {
    View view;
    ImageView imageView;
    TextView textView;

    public AdminHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
        // Inflate the layout for this fragment
        return view;
    }
}
