package com.inim.canteenmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class PaymentCompleteAct extends AppCompatActivity {

    Button yourOrder;
    TextView OrderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_complete);

        yourOrder = findViewById(R.id.bt_goto_Your_order);
        OrderNumber = findViewById(R.id.orderNumber);
        final int OrderNum = new Random().nextInt( 5000) + 99;
        OrderNumber.setText(OrderNum+"");

        yourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentCompleteAct.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}