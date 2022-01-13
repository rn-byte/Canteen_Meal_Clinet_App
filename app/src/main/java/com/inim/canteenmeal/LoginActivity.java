package com.inim.canteenmeal;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {

    //private ActivityMainBinding binding;
    TabLayout tabLayout;
    ViewPager viewPager;
    //FloatingActionButton google,fb;
    float v =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tabLayout= findViewById(R.id.tab_layout);
        viewPager= findViewById(R.id.view_pager);
        /*google= findViewById(R.id.fab_G);
        fb= findViewById(R.id.fab_F);*/



        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

       /* google.setTranslationY(300);
        fb.setTranslationY(300);*/
        tabLayout.setTranslationY(300);

        //google.setAlpha(v);
        tabLayout.setAlpha(v);

        /*google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();*/
        /*google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();*/
       tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();

    }
}