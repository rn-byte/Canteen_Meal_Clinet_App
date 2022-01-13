package com.inim.canteenmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String UserID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        showDetails(view);
        return view;
    }

    private void showDetails(View view) {

            user= FirebaseAuth.getInstance().getCurrentUser();
            reference= FirebaseDatabase.getInstance().getReference("Users");
            UserID=user.getUid();

            TextView prof_userName,prof_email,prof_userPhone;
            ImageView user_image;

            prof_userName = view.findViewById(R.id.prof_userName);
            prof_email = view.findViewById(R.id.prof_email);
            prof_userPhone = view.findViewById(R.id.prof_userPhone);
            user_image = view.findViewById(R.id.profile_image);

            reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfiles =snapshot.getValue(User.class);

                    if (userProfiles !=null){
                        String Name,Phone,Email;
                        Name=userProfiles.Name;
                        Phone=userProfiles.Phone;
                        Email=userProfiles.email;

                        prof_userName.setText(Name);
                        prof_email.setText(Email);
                        prof_userPhone.setText(Phone);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "User Data Not Received", Toast.LENGTH_SHORT).show();
                }
            });
    }
}