package com.example.moovi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminEditReservationsFragment extends Fragment {
    View view;
    MyRecyclerViewAdapter adapter;
    ArrayList<Reservation> res;
    HallSystem hallSystem = HallSystem.getInstance();
    Database database = Database.getInstance();
    FirebaseUser user;
    String email;
    Button refresh;
    EditText userEmail;
    final ArrayList<Reservation> reservations = new ArrayList<>();

    public AdminEditReservationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        user = hallSystem.getUser();
        view = inflater.inflate(R.layout.fragment_admin_edit_reservations, container, false);
        userEmail = view.findViewById(R.id.editText6);
        refresh = view.findViewById(R.id.button10);


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hallSystem.setEditEmail(userEmail.getText().toString());
                database.writeCurrentUserReservationList(userEmail.getText().toString(), new OnGetDataListener() {



                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                    }

                    @Override
                    public void onSuccess(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot documentsnapshot : task.getResult()){

                            String[] tokens = documentsnapshot.getId().split("[,]");


                            if (tokens[0].equalsIgnoreCase(userEmail.getText().toString())){

                                reservations.add(documentsnapshot.toObject(Reservation.class));
                            } else {

                            }



                        }
                        HallSystem.getInstance().setCurUserResList(reservations);
                    }



                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
                res = hallSystem.getCurUserResList();


                ArrayList<String> resList = new ArrayList<>();

                for (Reservation r : res){
                    String[] date = r.resDate.split("-");
                    String md = date[2] + "." + date[1];



                    resList.add(r.hall+" "+r.room+ " " +md+" "+r.startTime);
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
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
