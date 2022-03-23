package com.example.expertportal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintDetailsFragment extends BottomSheetDialogFragment {
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Complaint present;

    public ComplaintDetailsFragment() {
        // Required empty public constructor
    }

    public ComplaintDetailsFragment(Complaint present){
        this.present=present;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintDetailsFragment newInstance(String param1, String param2) {
        ComplaintDetailsFragment fragment = new ComplaintDetailsFragment();
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
        return inflater.inflate(R.layout.fragment_complaint_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(present.getStatus().equals("registered")){
            ComplaintFragment.arrayList.clear();
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Complaints");
            present.setStatus("Seen");
            HashMap hashMap=new HashMap();
            hashMap.put("status","Seen");
            reference.child(present.complaintID).updateChildren(hashMap);
            Log.i("this",reference.child("complaintID").child(present.complaintID).child("status").toString());
        }


        TextView title=view.findViewById(R.id.details_title);
        title.setText(present.getTitle());

        TextView name=view.findViewById(R.id.details_name);
        name.setText(present.getName());

        TextView regno=view.findViewById(R.id.details_regno);
        regno.setText(present.getRegno());

        TextView incident=view.findViewById(R.id.details_info);
        incident.setText(present.getIncident_info());

        TextView ComplaintFrom=view.findViewById(R.id.complaintFrom);
        ComplaintFrom.setText(present.getComplaintFrom().getName());

        TextView status=view.findViewById(R.id.details_status);
        status.setText(present.getStatus().toUpperCase(Locale.ROOT));

        TextView expert=view.findViewById(R.id.details_expert);
        expert.setText("Complaint handled by "+present.getExpert().getName());


        ComplaintFragment.adapter.notifyDataSetChanged();



    }
}