package com.mobprog.promee.service;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobprog.promee.HomeActivity;
import com.mobprog.promee.LoginActivity;
import com.mobprog.promee.model.UserDataClass;

public class AuthenticationService {

    String userId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    private DatabaseReference root, user_name;
    private Activity activity;

    public AuthenticationService(Activity activity) {
        this.activity = activity;
    }

    public String getUserId() {
        this.userId =  currentUser.getUid();
        return this.userId;
    }

    public void SignUp (String username, String email, String password) {
        //UserDataClass userData = new UserDataClass(username,email,password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if(CheckUserLoggedIn()){
                                userId = currentUser.getUid();
                                writeNewUser(userId, username, email);
                                // Now 'userId' contains the unique identifier for the newly created user
                                Toast.makeText(activity, "User ID:"+userId, Toast.LENGTH_SHORT).show();
                                gotoHome();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
     public void writeNewUser(String userId, String username, String email) {
        UserDataClass user = new UserDataClass(username, email);
        root = FirebaseDatabase.getInstance().getReference();
        user_name = root.child("users").child(userId);
        user_name.setValue(user);
    }
    public void LogIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(activity, "Login Successful.", Toast.LENGTH_SHORT).show();
                            gotoHome();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean CheckUserLoggedIn(){
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            return true;
        }else{
            return false;
        }
    }
    public void gotoHome(){
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public void gotoLogin(){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

}
