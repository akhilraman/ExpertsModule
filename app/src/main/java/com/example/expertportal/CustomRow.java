package com.example.expertportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomRow extends ArrayAdapter<Complaint> {
    int likes;
    List<String> likedby;
    public CustomRow(Context context, ArrayList<Complaint> arrayList){
        super(context,0,arrayList);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View  currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }
        TextView row_title=currentItemView.findViewById(R.id.row_title);
        TextView row_username=currentItemView.findViewById(R.id.row_name);
        TextView row_regno=currentItemView.findViewById(R.id.row_regno);
        TextView row_status=currentItemView.findViewById(R.id.row_status);



        // get the position of the view from the ArrayAdapter
        Complaint currentNumberPosition = getItem(position);


        assert currentNumberPosition != null;

        row_title.setText(currentNumberPosition.getTitle());
        row_username.setText(currentNumberPosition.getName());
        row_regno.setText(currentNumberPosition.getRegno());
        row_status.setText(currentNumberPosition.getStatus());


        ImageButton transact_button=currentItemView.findViewById(R.id.row_chat);

        transact_button.setFocusable(false);
        transact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });

        return currentItemView;
    }
}
