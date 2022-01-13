package com.inim.canteenmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class YourCartFragment extends Fragment {

    YourCartAdapter yourCartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_your_cart, container, false);


        RecyclerView rv = v.findViewById(R.id.cart_recy_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<YourOrderCartModel> options=new FirebaseRecyclerOptions.Builder<YourOrderCartModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child("yourOrder").child(FirebaseAuth.getInstance().getCurrentUser().getUid()),YourOrderCartModel.class ).build();


        yourCartAdapter= new YourCartAdapter(options);
        rv.setAdapter(yourCartAdapter);


        return v;
    }

    @Override
    public void onStart() {

        super.onStart();
        yourCartAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        yourCartAdapter.stopListening();
    }
}