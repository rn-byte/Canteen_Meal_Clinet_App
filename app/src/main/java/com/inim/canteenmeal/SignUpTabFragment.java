package com.inim.canteenmeal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class SignUpTabFragment extends Fragment {
    EditText email,pass,phone,cPass,name;
    Button sgnUp;

    //Added for firebase Authentication
    ProgressBar progressBar;
   private FirebaseAuth mAuth;
   // FirebaseUser mUser;


    float v=0;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container,false);


        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.nPass);
        phone = root.findViewById(R.id.s_phone);
        cPass = root.findViewById(R.id.cpass);
        sgnUp = root.findViewById(R.id.btn_sgnup);
        name = root.findViewById(R.id.Name);

        progressBar=root.findViewById(R.id.progress_Bar);

        //Database code for authentication

        mAuth=FirebaseAuth.getInstance();
       // mUser=mAuth.getCurrentUser();


        name.setTranslationX(800);
        email.setTranslationX(800);
        phone.setTranslationX(800);
        pass.setTranslationX(800);
        cPass.setTranslationX(800);
        sgnUp.setTranslationX(800);

        name.setAlpha(v);
        email.setAlpha(v);
        phone.setAlpha(v);
        pass.setAlpha(v);
        cPass.setAlpha(v);
        sgnUp.setAlpha(v);

        name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        cPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        sgnUp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();


        sgnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });


        return root;
    }

    private void PerformAuth() {
        String sEmail = email.getText().toString().trim();
        String Password = pass.getText().toString().trim();
        String sCpassword = cPass.getText().toString().trim();
        String sPhone = phone.getText().toString().trim();
        String sName = name.getText().toString().trim();

        if (sName.isEmpty()) {
            name.setError("Full Name Is Required");
            name.requestFocus();
            return;
        }
        if (sPhone.isEmpty() || sPhone.length() < 10) {
            phone.setError("Phone Number should be of 10 Digit !");
            phone.requestFocus();
            return;
        }
        if (sEmail.isEmpty()) {
            email.setError("Email Should not be Empty !");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            email.setError("Please Provide Valid Email !");
            email.requestFocus();
            return;
        }
        if (Password.isEmpty() || Password.length() < 6) {
            pass.setError("Password Shouldn't be empty and less than 6 character!");
            pass.requestFocus();
            return;
        }
        if (!Password.equals(sCpassword)) {
            cPass.setError("Password Didn't Match!");
            cPass.requestFocus();
            return;
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(sEmail, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        User user = new User(sName, sEmail, sPhone);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "User Registration Successful !", Toast.LENGTH_LONG).show();
                                    //Redirecting to login In Activity
                                    sendUserToNextActivity();
                                } else {
                                    Toast.makeText(getContext(), "Failed To Register, Try Again !", Toast.LENGTH_LONG).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });

                    } else {
                        Toast.makeText(getContext(), "Failed To Register, Try Again !!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
    private void sendUserToNextActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
