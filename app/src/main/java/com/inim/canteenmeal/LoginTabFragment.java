package com.inim.canteenmeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginTabFragment extends Fragment {
    EditText email, pass;
    TextView forgetPass;
    Button login;

    // String emailPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    ProgressBar progressBar;
    FirebaseAuth mAuth;
//    FirebaseUser mUser;

    float v = 0;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.addr);
        forgetPass = root.findViewById(R.id.forget_pass);
        login = root.findViewById(R.id.btn_login);

        progressBar = root.findViewById(R.id.progressBar);


        //Database code for authentication
        mAuth = FirebaseAuth.getInstance();
//        mUser=mAuth.getCurrentUser();

        email.setTranslationX(800);
        pass.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PerformLogin();
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgotPassActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return root;
    }

    private void PerformLogin() {

        String Email = email.getText().toString();
        String Password = pass.getText().toString();


        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Enter Correct Email");
            email.requestFocus();
            return;
        }
        if (Password.isEmpty() || Password.length() < 6) {
            pass.setError("Enter the Proper Password!");
            pass.requestFocus();
            return;
        } else {
            progressBar.setVisibility(View.VISIBLE);
//            progressDialog.setMessage("Please Wait!");
//            progressDialog.setTitle("Loging IN!!");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();

            mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user.isEmailVerified()) {
                            sendUserToMainActivity();
                            // progressDialog.dismiss();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_LONG).show();

                        } else {
                            user.sendEmailVerification();
                            Toast.makeText(getContext(), "Check your Email To verify your Account!!", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        //progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();

                    }
                }
            });


        }
    }

    private void sendUserToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
