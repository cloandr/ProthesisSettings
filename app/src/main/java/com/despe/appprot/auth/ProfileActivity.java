package com.despe.appprot.auth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.despe.appprot.R;
import com.despe.appprot.base.BaseActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity {

    private static final int SIGN_OUT_TASK = 10;
    private static final int DELETE_USER_TASK = 20;

    // For Design

    @BindView(R.id.username)
    TextInputEditText textInputEditTextUsername;
    @BindView(R.id.viewemail)
    TextView textViewEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //this.configureToolBar();
        this.updateUIWhenCreating();
    }

    @Override
    public int getFragmentLayout(){
        return R.layout.activity_profile;
    }

    // --------
    // ACTIONS
    //---------
    // @OnClick(R.id.button_update)
    @OnClick(R.id.card_edit)
    public void onClickUpdateButton(){
            EditText saisie = (EditText) findViewById(R.id.username);
            String nom = saisie.getText().toString(); // récupère la saisie
            Log.d("MSG_DEBUG", nom);
            if(TextUtils.isEmpty(nom)) {
                Toast.makeText(this, "Attention, le nom est vide. Saisie non enregistrée", Toast.LENGTH_LONG).show();
            }
            else {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nom).build();
                this.getCurrentUser().updateProfile(profileUpdates); // MAJ du nom saisi
                Toast.makeText(this, "Nom modifié", Toast.LENGTH_LONG).show();
                Log.d("MSG_DEBUG", "nom changé");
            }
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(saisie.getWindowToken(), 0); // permet de fermer le clavier
    }

    //@OnClick(R.id.button_signout)
    @OnClick(R.id.card_logout)
    public void onClickSignOutButton(){
        this.signOutUserFromFirebase();
    }

    //@OnClick(R.id.button_delete)
    @OnClick(R.id.card_delete)
    public void onClickDeleteButton(){
        new AlertDialog.Builder(this)
                .setMessage(R.string.popup_message_confirmation_delete_account)
                .setPositiveButton(R.string.popup_message_choice_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUserFromFirebase();
                    }
                })
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
    }

    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
    }

    private void deleteUserFromFirebase(){
        if(this.getCurrentUser() != null){
            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));
        }
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin) {
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch(origin){
                    case SIGN_OUT_TASK:
                        finish();
                        break;
                    case DELETE_USER_TASK:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void updateUIWhenCreating(){

        String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ?
                getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();
        String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ?
                getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();

        this.textInputEditTextUsername.setText(username);
        this.textViewEmail.setText(email);
    }
}