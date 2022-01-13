package com.inim.canteenmeal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class FoodDetailsAdapter extends FirebaseRecyclerAdapter<MenuModel, FoodDetailsAdapter.MyViewHolder> {

    public int qt = 1;
    private BottomSheetDialog bottomSheetDialog;

    public FoodDetailsAdapter(@NonNull FirebaseRecyclerOptions<MenuModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_selected_items, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, MenuModel model) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.ItemName.setText(model.getFoodName());

        myViewHolder.FoodPrice.setText("" + model.getFoodPrice());


        myViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetTheme);
                View sheetView = LayoutInflater.from(v.getContext()).inflate(R.layout.bottom_sheet, null);
                TextView fTitle = sheetView.findViewById(R.id.botton_fTitle);
                TextView fPrice = sheetView.findViewById(R.id.tv_Price);
                TextView TotalPrice = sheetView.findViewById(R.id.cart_total);
                TextView TotalQua = sheetView.findViewById(R.id.botton_itemQt);
                fTitle.setText(model.getFoodName());
                fPrice.setText("" + model.getFoodPrice());
                TotalPrice.setText("" + model.getFoodPrice());


                sheetView.findViewById(R.id.bt_cart).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String FoodName;
                        int quantity, Price;
                        Long TotalAmt;
                        FoodName = fTitle.getText().toString().trim();
                        quantity = Integer.parseInt(TotalQua.getText().toString().trim());
                        TotalAmt = Long.parseLong(TotalPrice.getText().toString().trim());
                        Price = Integer.parseInt(fPrice.getText().toString().trim());
                        final int OrderNum = new Random().nextInt(1000) + 75;

                        //String DateTime = DateFormat.getDateInstance().format(new Date());
                        String DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());



                        // ValuePass obj = new ValuePass(OrderNum);
                        //cartReference = FirebaseDatabase.getInstance().getReference("Users").child("cart");
                        String userID = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();

                        CartModel cartModel = new CartModel(FoodName, quantity, Price, TotalAmt, OrderNum, DateTime);

                        FirebaseDatabase.getInstance().getReference("Cart").child(userID).child(OrderNum + "")
                                .setValue(cartModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Toast.makeText(v.getContext(), "cart added to database !", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(v.getContext(), "Cart addition to database unsuccessful", Toast.LENGTH_SHORT).show();

                            }
                        });

                        //Toast.makeText(v.getContext(), "Added To cart", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                        FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame, new CartFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                });

                sheetView.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                Button btnPlus = sheetView.findViewById(R.id.botton_plus);
                Button btnMinus = sheetView.findViewById(R.id.botton_minus);
                TextView txtQt = sheetView.findViewById(R.id.botton_itemQt);

                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qt = qt + 1;
                        int tprice = 0;
                        tprice = qt * (model.getFoodPrice());
                        TotalPrice.setText("" + tprice);
                        txtQt.setText("" + qt);

                    }
                });
                btnMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (qt == 1) {

                        } else {
                            qt = qt - 1;
                            int tprice = 0;
                            tprice = qt * (model.getFoodPrice());
                            TotalPrice.setText("" + tprice);
                        }
                        txtQt.setText("" + qt);
                    }
                });

                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView ItemName, FoodPrice;
        public Button add;
        //public ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.sel_item_name);
            //ItemDetails = itemView.findViewById(R.id.sel_item_details);
            FoodPrice = itemView.findViewById(R.id.food_price);
            add = itemView.findViewById(R.id.add_food);
            //imageView = itemView.findViewById(R.id.food_img);


        }


    }

}
