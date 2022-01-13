package com.inim.canteenmeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartViewAdapter extends FirebaseRecyclerAdapter<CartModel,CartViewAdapter.MyViewHolder> {


    public CartViewAdapter(@NonNull FirebaseRecyclerOptions<CartModel> options) {
        super(options);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView c_foodName,c_foodPrice,c_priceTotal,c_foodQ,c_date,OrderNumber;
        ImageButton delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            c_foodName=itemView.findViewById(R.id.cart_fName);
            c_foodPrice=itemView.findViewById(R.id.cart_fPrice);
            c_priceTotal=itemView.findViewById(R.id.total);
            c_foodQ=itemView.findViewById(R.id.qunt);
            delete=itemView.findViewById(R.id.btn_delete);
            c_date=itemView.findViewById(R.id.Date);
            OrderNumber=itemView.findViewById(R.id.order_number);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //String iD=OrderNumber.getText().toString().trim();
                    warning(v);
                }
            });

        }
        private void warning2(View v,String id){
            FirebaseDatabase.getInstance().getReference("Cart").child("order").child(id).
                    removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(v.getContext(), "Values Deleted", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(v.getContext(), "Values NOt Deleted", Toast.LENGTH_LONG).show();

                }
            });

        }

        private void warning(View v) {
            String iD=OrderNumber.getText().toString().trim();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Warning");
            builder.setMessage("Do You Want To Delete?");
            builder.setIcon(android.R.drawable.ic_delete);
            //String iD=FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(iD).
                    removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                warning2(v,iD);
                                Toast.makeText(builder.getContext(), "Values Deleted", Toast.LENGTH_LONG).show();

                            }
                            else
                                Toast.makeText(builder.getContext(), "Values NOt Deleted", Toast.LENGTH_LONG).show();

                        }
                    });

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog deleteDialog=builder.create();
            deleteDialog.show();
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view,parent,false);
        //showCartDetails(view);
        return new MyViewHolder(view);
    }



    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull CartModel model) {

        holder.c_foodName.setText(model.getFood());
        holder.c_foodPrice.setText(""+model.getPrice());
        holder.c_priceTotal.setText(""+model.getTotalPrice());
        holder.c_foodQ.setText(""+model.getqty());
        holder.c_date.setText(""+model.getDate());
        holder.OrderNumber.setText(""+model.getOrderNum());


    }

}
