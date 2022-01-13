package com.inim.canteenmeal;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DuoDrawerLayout drawerLayout;


    private FirebaseUser user;
    private DatabaseReference reference;

    private String UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        init();
        userDetail();
    }

    private void userDetail() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();

        TextView nav_username, nav_userEmail;
        ImageView user_image;

        nav_username = findViewById(R.id.nav_username);
        nav_userEmail = findViewById(R.id.nav_userEmail);
        user_image = findViewById(R.id.user_image);

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfiles = snapshot.getValue(User.class);

                if (userProfiles != null) {
                    String Name, Phone, Email;
                    Name = userProfiles.Name;
                    Email = userProfiles.email;

                    nav_username.setText(Name);
                    nav_userEmail.setText(Email);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "User Data Not Received", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View contentView = drawerLayout.getContentView();
        View menuView = drawerLayout.getMenuView();

        LinearLayout ll_Home = menuView.findViewById(R.id.ll_Home);
        LinearLayout ll_Profile = menuView.findViewById(R.id.ll_Profile);
        LinearLayout ll_Setting = menuView.findViewById(R.id.ll_Notice);
        LinearLayout ll_Share = menuView.findViewById(R.id.ll_Share);
        LinearLayout ll_Logout = menuView.findViewById(R.id.ll_Logout);
        LinearLayout ll_cart = menuView.findViewById(R.id.ll_order);


        ll_Home.setOnClickListener(this);
        ll_Profile.setOnClickListener(this);
        ll_Setting.setOnClickListener(this);
        ll_Share.setOnClickListener(this);
        ll_Logout.setOnClickListener(this);
        ll_cart.setOnClickListener(this);


        replace(new FoodDetailsFragment());


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_Home:
                replace(new FoodDetailsFragment(), "Home");
                break;

            case R.id.ll_Profile:
                replace(new ProfileFragment(), "Profile");
                break;

            case R.id.ll_Notice:
                replace(new NoticeFragment(), "Notice");
                break;

            case R.id.ll_order:
                replace(new YourCartFragment(), "Your Order");
                break;

            case R.id.ll_Share:
                ShareFun();
                //Toast.makeText(this, "Share...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_Logout:
                SignOut();
                Toast.makeText(this, "Logout...", Toast.LENGTH_SHORT).show();
                break;

        }
        drawerLayout.closeDrawer();
    }

    private void ShareFun() {
        Intent intentSend = new Intent(Intent.ACTION_SEND);
        intentSend.setType("text/plain");
        String body = "Download This App";
        String sub = "http://play.google.com";
        intentSend.putExtra(Intent.EXTRA_TEXT, body);
        intentSend.putExtra(Intent.EXTRA_TEXT, sub);
        startActivity(Intent.createChooser(intentSend, "Share Via"));

    }

    private void SignOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void replace(Fragment fragment, String s) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(s);
        transaction.commit();
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

}