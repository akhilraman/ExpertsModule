package com.example.expertportal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintFragment extends Fragment {

    ListView listview;
    public static ArrayList arrayList;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    public static CustomRow adapter;

    DatabaseReference referenceExpert;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintFragment newInstance(String param1, String param2) {
        ComplaintFragment fragment = new ComplaintFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complaint, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ProgressBar simpleProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Complaints");


        listview=view.findViewById(R.id.list);

        listview.setItemsCanFocus(false);
        arrayList = new ArrayList<Complaint>();


        adapter = new CustomRow(getContext(), arrayList);
        listview.setAdapter(adapter);
        simpleProgressBar.setVisibility(View.VISIBLE);


        //arrayList.add(new Complaint("123","this is title","akhil","19bce1564","ragging","rahul","registered"));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!arrayList.isEmpty()){
                    arrayList.clear();
                }
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Complaint complaint=dataSnapshot.getValue(Complaint.class);
                    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

                    Log.i("value",firebaseAuth.getCurrentUser().getEmail());
                    Log.i("value",complaint.getExpert().getEmail());
                    if(complaint.getExpert().getEmail().equals(firebaseAuth.getCurrentUser().getEmail())){
                        arrayList.add(complaint);
                    }
                }
                adapter.notifyDataSetChanged();
                simpleProgressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Complaint present = (Complaint) arrayList.get(position);
                ComplaintDetailsFragment complaintDetailsFragment= new ComplaintDetailsFragment(present);
                complaintDetailsFragment.show(((FragmentActivity)getContext()).getSupportFragmentManager(), complaintDetailsFragment.getTag());
                //Toast.makeText(getContext(), "hii", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton settingButton=view.findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                SettingDetailsFragment settingDetailsFragment= new SettingDetailsFragment();
                settingDetailsFragment.show(((FragmentActivity)getContext()).getSupportFragmentManager(), settingDetailsFragment.getTag());
            }
        });





    }


    }
