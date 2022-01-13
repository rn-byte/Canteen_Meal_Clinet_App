package com.inim.canteenmeal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment {

    CartViewAdapter cartViewAdapter;
   // List<CartModel> lists;
    Button btnProceed;
    TextView CartTotalPrice;
//    private static final String TAG = "demo";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_cart, container, false);
        //CartTotalPrice=root.findViewById(R.id.cart_totalPrice);
        //showCartDetails(root);

//        lists = new ArrayList<>();
//        CartTotalPrice.setText(FTotal);


        RecyclerView rv = root.findViewById(R.id.cart_recy);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<CartModel> options=new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()),CartModel.class ).build();

//        FirebaseRecyclerOptions<CartModel> options=new FirebaseRecyclerOptions.Builder<CartModel>()
//                .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cart"),CartModel.class ).build();


        cartViewAdapter= new CartViewAdapter(options);
        rv.setAdapter(cartViewAdapter);

        btnProceed=root.findViewById(R.id.btn_proceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new PaymentMethodFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void showCartDetails(View root) {


//        String userID=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
//        FirebaseDatabase.getInstance().getReference("Cart").child(userID)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                long TotalPrice=0;
//                //CartModel cartModel =snapshot.getValue(CartModel.class);
//                for (DataSnapshot snap:snapshot.getChildren()) {
//                    Map<String,Object>data=(Map<String, Object>)snap.getValue();
//                    String tP=String.valueOf(data.get("totalPrice"));
//                    TotalPrice+=Long.parseLong(tP);
//                    Log.d(TAG,"onDataChange Food : "+TotalPrice);
//                }
//
//
//                //CartModel cartModel =snapshot.getValue(CartModel.class);
//
////                if (cartModel !=null) {
//////                    Long TotalPrice;
//////                    TotalPrice = cartModel.getTotalPrice();
//////                    //CartTotalPrice.setText("" + TotalPrice);
//////                    Toast.makeText(getContext(), "Total Price : "+TotalPrice, Toast.LENGTH_LONG).show();
////
////
////                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "User Data Not Received", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        //userID=user.getUid();
    }

    @Override
    public void onStart() {

        super.onStart();
        cartViewAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        cartViewAdapter.stopListening();
    }

}