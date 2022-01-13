package com.inim.canteenmeal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class PaymentMethodFragment extends Fragment  {

    private static final String TAG = "demo";
    public long TotalKaltiPrice,TotalPrice;
    KhaltiButton kBuy;
    TextView totalPay;

    String Name,Phone;
    //ArrayList<Object> list=new ArrayList<>();
    String Meal;
    private FirebaseUser user;
    private DatabaseReference reference,ref;
    private String UserID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_method, container, false);
        PaymentModel vPass = new PaymentModel();
        kBuy = view.findViewById(R.id.khalti_button);
        totalPay = view.findViewById(R.id.totala_amt_t0_pay);

        //totalPaymentPrice(view);
        //TotalPay=Long.parseLong(totalPay.getText().toString().trim());

        /// Retriving User Details start///
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        UserID=user.getUid();
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfiles =snapshot.getValue(User.class);
                if (userProfiles !=null){

                    Name=userProfiles.Name;
                    Phone=userProfiles.Phone;

                    //prof_userName.setText(Name);
                    Log.d("Name","Your Name is : "+Name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "User Data Not Received", Toast.LENGTH_SHORT).show();
            }
        });



        /// Retriving User Details End///
        String userID = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
        FirebaseDatabase.getInstance().getReference("Cart").child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TotalKaltiPrice = 0;
                        String Status="Ordered";
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Map<String, Object> data = (Map<String, Object>) snap.getValue();
                            String fName=String.valueOf(data.get("food"));
                            String oNum=String.valueOf(data.get("orderNum"));
                            String Qty=String.valueOf(data.get("qty"));
                            String Date=String.valueOf(data.get("date"));
                            String Price=String.valueOf(data.get("price"));
                            String tP = String.valueOf(data.get("totalPrice"));

                            Log.d(TAG,"DAta "+oNum);
                            TotalPrice=Long.parseLong(tP);
                            TotalKaltiPrice += Long.parseLong(tP);
                            ModelClassForOrder modelClassForOrder=new ModelClassForOrder(Date,fName,oNum,Price,Qty,TotalPrice,Name,Phone,Status);
                            FirebaseDatabase.getInstance().getReference("Cart").child("order").child(oNum).setValue(modelClassForOrder)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //Toast.makeText(v.getContext(), "cart added to database !", Toast.LENGTH_LONG).show();
                                            } else
                                                Toast.makeText(getContext(), "Cart addition to database unsuccessful", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            ///For Your Order Code Start/////

                            FirebaseDatabase.getInstance().getReference("Cart").child("yourOrder").child(userID).child(oNum).setValue(modelClassForOrder)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //Toast.makeText(v.getContext(), "cart added to database !", Toast.LENGTH_LONG).show();
                                            } else
                                                Toast.makeText(getContext(), "Cart addition to database unsuccessful", Toast.LENGTH_SHORT).show();

                                        }
                                    });




                            ///For Your Order Code End/////



                        }


                        Log.d(TAG, "onDataChange Food Total Price : " +TotalKaltiPrice);
                        totalPay.setText(String.valueOf(TotalKaltiPrice));
                        khaltiImplement(TotalKaltiPrice, "meal1", kBuy, "Canteen Meal Food", view.getContext());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "User Data Not Received", Toast.LENGTH_SHORT).show();

                    }
                });

        return view;
    }


    public void khaltiImplement(long Totalprice, String productId, KhaltiButton kBuy, String Food, Context mCtx) {

        long price = Totalprice * 100L;
        Config.Builder builder = new Config.Builder(Constant.pub, productId, Food, price, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
                Toast.makeText(mCtx, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Intent intent = new Intent(getActivity(), PaymentCompleteAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("order");

                Log.i("success", data.toString());
                Toast.makeText(mCtx, "Payment Successful" + data.toString(), Toast.LENGTH_LONG).show();
//
                Runnable obj1=new DeleteOrder();
                Thread t1=new Thread(obj1);
                t1.start();


            }
        });

        Config config = builder.build();
        kBuy.setCheckOutConfig(config);
        KhaltiCheckOut khaltiCheckOut1 = new KhaltiCheckOut(mCtx, config);
        kBuy.setOnClickListener(v -> khaltiCheckOut1.show());

    }
//
//    @Override
//    public void run() {
//        FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
//
//    }
    public  class DeleteOrder implements Runnable{

        @Override
        public void run() {
            FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();

        }
    }
//    public class Work implements Runnable{
//
//    }
}