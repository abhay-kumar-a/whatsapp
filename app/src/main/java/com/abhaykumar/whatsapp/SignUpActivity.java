package com.abhaykumar.whatsapp;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abhaykumar.whatsapp.databinding.ActivitySignInBinding;
import com.abhaykumar.whatsapp.databinding.ActivitySignUpBinding;
import com.abhaykumar.whatsapp.module.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth auth;  // firebaseAuth class variable
    ProgressDialog progressDialog; // it used for show a dialog on screen when email is created.

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());  //  (R.layout.activity_sign_up)
        // for hide tool bar
        getSupportActionBar().hide();

        // creating progressDialogBox
        progressDialog = new ProgressDialog(SignUpActivity.this);
        // setTile
        progressDialog.setTitle("Creating Account");
        //setMessage
        progressDialog.setMessage("We are creating your account");
        // connect with firebase for Authentication

        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        // setOnClickListener
        binding.signUpSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //use of progressDialog box
                progressDialog.show();
                // use of Authentication with email and password  // passing two parameter and also use addOnCompleteOnClickListener(its use Module package variables so .. let's create module package)
                auth.createUserWithEmailAndPassword(binding.signUpEmail.getText().toString(),binding.signUpPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // for close dialogBox use Dismiss keyword
                        progressDialog.dismiss();
                        // here using if and else for make condition for show An error or success message for email and password Authentication
                        if(task.isSuccessful()){

                            //save data into firebase by the help of model package(User constructor)
                            Users users =new Users(binding.signUpUserName.getText().toString(),binding.signUpEmail.getText().toString(),binding.signUpPassword.getText().toString());
                            // take user id from firebase
                            String id = task.getResult().getUser().getUid();
                            // set all user data through id  in Firebase Node(realTimeDatabase)
                            database.getReference().child("Users").child(id).setValue(users);
                            Toast.makeText(SignUpActivity.this, "UserCreated Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        // setOnclickListener on AlreadyHave An account;
        binding.signUpAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

    }


}

