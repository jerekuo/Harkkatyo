package com.example.moovi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View view;
    MyRecyclerViewAdapter adapter;
    ArrayList<Reservation> res;
    HallSystem hallSystem = HallSystem.getInstance();
    Database database = Database.getInstance();
    FirebaseUser user;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        user = hallSystem.getUser();
        view = inflater.inflate(R.layout.fragment_home, container, false);

        res = hallSystem.getCurUserResList();

        ArrayList<String> resList = new ArrayList<>();

        for (Reservation r : res) {
            String[] date = r.resDate.split("-");
            String md = date[2] + "." + date[1];

            resList.add(r.hall + " " + r.room + " " + md + " " + r.startTime);
        }

        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), resList);
        adapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Reservation resChoice = res.get(position);
                HallSystem.getInstance().setChosenRes(resChoice);

                Fragment fragment = new EditReservationsFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentView, fragment);
                transaction.commit();

            }
        });
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

}
