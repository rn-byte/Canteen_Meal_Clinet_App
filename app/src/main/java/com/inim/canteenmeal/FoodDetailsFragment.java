package com.inim.canteenmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailsFragment extends Fragment {

    FoodDetailsAdapter foodDetailsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view =inflater.inflate(R.layout.fragment_food_details,container,false);


        RecyclerView rv = view.findViewById(R.id.rv_foodDetails);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<MenuModel> options=new FirebaseRecyclerOptions.Builder<MenuModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("menu"),MenuModel.class ).build();

        foodDetailsAdapter= new FoodDetailsAdapter(options);
        rv.setAdapter(foodDetailsAdapter);

    return view;
    }

    @Override
    public void onStart() {

        super.onStart();
        foodDetailsAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        foodDetailsAdapter.stopListening();
    }
}
