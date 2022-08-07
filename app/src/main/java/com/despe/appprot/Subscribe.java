package com.despe.appprot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Subscribe extends MainActivity {

    protected boolean passwordMatch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        final Context context = this;
        Button buttonSubscribe = findViewById(R.id.button);

        buttonSubscribe.setEnabled(false);
        buttonSubscribe.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(context, LogIn.class);
                startActivity(intent);
            }
        });
    }

    private String getNom(){
        final EditText nom = findViewById(R.id.editNom);
        return nom.getText().toString();
    }

    private String getPrenom(){
        final EditText prenom = findViewById(R.id.editPrenom);
        return prenom.getText().toString();
    }

    private String getMail(){
        final EditText mail = findViewById(R.id.editMail);
        return mail.getText().toString();
    }

    private String getPassword(){
        final EditText password = findViewById(R.id.editPassword);
        return password.getText().toString();
    }

    private boolean matchPassword(){
        final EditText password_2 = findViewById(R.id.confirmPassword);
        if(password_2.getText().toString() == getPassword()){
            passwordMatch = true;
        }
        return passwordMatch;
    }

    private String getKey(){
        final EditText productKey = findViewById(R.id.editKey);
        return productKey.getText().toString();
    }

    @Override
    protected void onStart(){
        Log.i(TAG, getLocalClassName() + " started");
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,getLocalClassName() + " paused");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, getLocalClassName() + " resumed");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, getLocalClassName() + " stopped");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, getLocalClassName() + " destroyed");
    }
}
