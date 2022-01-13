package com.inim.canteenmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button ResetPassButton;

    ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        emailEditText=findViewById(R.id.reset_passEmail);
        ResetPassButton=findViewById(R.id.reset_pass_btn);

        progressBar=findViewById(R.id.progressfBar);

        auth=FirebaseAuth.getInstance();
        ResetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }
        });

    }

    private void resetPass() {
        String Email = emailEditText.getText().toString().trim();
        if(Email.isEmpty()){
            emailEditText.setError("Enter Your Email To Reset Password!");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            emailEditText.setError("Enter Correct Email");
            emailEditText.requestFocus();
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassActivity.this, "Check Your Email to Reset Your Password!", Toast.LENGTH_LONG ).show();
                    Intent intent = new Intent(ForgotPassActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else {
                    Toast.makeText(ForgotPassActivity.this,"Something Went Wrong Try Again!", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}