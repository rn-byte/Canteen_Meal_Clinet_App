package com.inim.canteenmeal;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class YourCartAdapter  extends FirebaseRecyclerAdapter<YourOrderCartModel,YourCartAdapter.MyViewHolder> {


    public YourCartAdapter(@NonNull FirebaseRecyclerOptions<YourOrderCartModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull YourOrderCartModel model) {

        holder.yc_foodName.setText(model.getFood());
        holder.yc_foodPrice.setText(""+model.getPrice());
        holder.yc_priceTotal.setText(""+model.getTotalPrice());
        holder.yc_foodQ.setText(""+model.getqty());
        holder.yc_date.setText(""+model.getDate());
        holder.yOrderNumber.setText("O.N:"+model.getOrderId());

        Log.d("Order","Order Id : "+model.getOrderId());



    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView yc_foodName,yc_foodPrice,yc_priceTotal,yc_foodQ,yc_date,yOrderNumber;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            yc_foodName=itemView.findViewById(R.id.cartY_fName);
            yc_foodPrice=itemView.findViewById(R.id.carty_fPrice);
            yc_priceTotal=itemView.findViewById(R.id.totaly);
            yc_foodQ=itemView.findViewById(R.id.qunty);
            yc_date=itemView.findViewById(R.id.yDate);
            yOrderNumber=itemView.findViewById(R.id.yorder_number);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_cart_list,parent,false);


        return new MyViewHolder(view);
    }



}
