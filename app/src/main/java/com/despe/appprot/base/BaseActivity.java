package com.despe.appprot.base;

import android.os.Bundle;
import android.widget.Toast;

import com.despe.appprot.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    //-----------------------
    // LIFE CYCLE
    //-----------------------

    @Override
    //protected void onCreate(@Nullable Bundle savedInstanceState){
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(this.getFragmentLayout());
        ButterKnife.bind(this); // Configure Butterknife
    }

    public abstract int getFragmentLayout();

    /*protected void configureToolBar(){
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }**/

    protected OnFailureListener onFailureListener(){
        return new OnFailureListener() {
            @Override
           // public void onFailure(@NonNull Exception e)
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();
            }
        };
    }

   // @Nullable
    protected FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    protected Boolean isCurrentUserLogged(){
        return(this.getCurrentUser() != null);
    }

}
