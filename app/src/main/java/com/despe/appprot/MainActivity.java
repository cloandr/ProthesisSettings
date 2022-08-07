package com.despe.appprot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.despe.appprot.auth.ProfileActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    protected static final String TAG = "appprot";

    @BindView(R.id.main_activity_coordinator_layout) CoordinatorLayout coordinatorLayout;
    //@BindView(R.id.button_logIn) Button buttonLogin;
    @BindView(R.id.patient_card) CardView patientCard;

    // chloe begin

    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;
    private CardView profileCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patientCard = (CardView) findViewById(R.id.patient_card);
        profileCard = (CardView) findViewById(R.id.myprofile_card);

        final Context context = this;

        profileCard.setOnClickListener(new CardView.OnClickListener(){
            public void onClick(View v){
                onClickSubscribeButton();
            }
        });

        patientCard.setOnClickListener(new CardView.OnClickListener(){
            public void onClick(View v){
                onClickLoginButton();
            }
        });
    }

    @OnClick(R.id.patient_card)
    public void onClickLoginButton(){
        if(this.isCurrentUserLogged()) {
            //this.startProfileActivity();
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
        } else {
            this.startSignInActivity();
        }
    }

    @OnClick(R.id.profile_card)
    public void onClickSubscribeButton(){
        if(this.isCurrentUserLogged()){
            this.startProfileActivity();
        } else {
            this.startSignInActivity();
        }
    }

    private void startProfileActivity(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }



    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null);}


    private void startSignInActivity(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.justin_tete)
                        .build(),
                RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // Handle SignIn Activity response on activity result
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    private void showSnackBar(String message){
        Snackbar.make(findViewById(R.id.main_activity_coordinator_layout), message, Snackbar.LENGTH_SHORT).show();
    }

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                showSnackBar(getString(R.string.connection_succeed));
                Intent intent = new Intent(this, LogIn.class);
                startActivity(intent);
            } else {
                if(response == null){
                    showSnackBar(getString(R.string.error_authentification_canceled));
                } else if(response.getErrorCode()== ErrorCodes.NO_NETWORK){
                    showSnackBar(getString(R.string.error_no_internet));
                } else if(response.getErrorCode()==ErrorCodes.UNKNOWN_ERROR){
                    showSnackBar(getString(R.string.error_unknown_error));
                }
            }
        }
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
