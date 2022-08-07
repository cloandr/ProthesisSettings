package com.despe.appprot;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AddPatient extends AppCompatActivity {
    Toast toast;
    Toast toast2;
    TextView test;
    EditText saisieNom, saisiePrenom, saisieSexe, saisiedateNaissance, saisiePoids, saisieTaille, saisiePathologie, saisieDateRDV, saisieBilan;
    String nom, prenom, sexe, dateNaissance, poids, taille, pathologie, dateRDV ,bilan;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MSG_DEBUG", "[AddPatient] initialiser nouveau patient");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        Button buttonModif = findViewById(R.id.button2);
        buttonModif.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClickUpdateButton();
            }
        });

        saisieNom = (EditText) findViewById(R.id.nom);
        saisiePrenom = (EditText) findViewById(R.id.prenom);
        saisieSexe = (EditText) findViewById(R.id.sexe);
        saisiedateNaissance = (EditText) findViewById(R.id.dateNaissance);
        saisiePoids = (EditText) findViewById(R.id.poids);
        saisieTaille = (EditText) findViewById(R.id.taille);
        saisiePathologie = (EditText) findViewById(R.id.pathologie);
        saisieDateRDV = (EditText) findViewById(R.id.dateRDV);
        saisieBilan= (EditText) findViewById(R.id.bilan);

        nom = saisieNom.getText().toString(); // récupère la saisie
        prenom = saisiePrenom.getText().toString();
        sexe = saisieSexe.getText().toString();
        dateNaissance = saisiedateNaissance.getText().toString();
        poids = saisiePoids.getText().toString();
        taille = saisieTaille.getText().toString();
        pathologie = saisiePathologie.getText().toString();
        dateRDV = saisieDateRDV.getText().toString();
        bilan = saisieBilan.getText().toString();

        toast = Toast.makeText(this, "Attention, un ou plusieurs champs sont vides. Saisie non enregistrée", Toast.LENGTH_LONG);
        toast2 = Toast.makeText(this, "Changement(s) enregistré(s)", Toast.LENGTH_LONG);
    }

    public void onClickUpdateButton(){
        String newNom = saisieNom.getText().toString();
        String newPrenom = saisiePrenom.getText().toString();
        String newSexe = saisieSexe.getText().toString();
        String newDateNaissance = saisiedateNaissance.getText().toString();
        String newPoids = saisiePoids.getText().toString();
        String newTaille = saisieTaille.getText().toString();
        String newPathologie = saisiePathologie.getText().toString();
        String newDateRDV = saisieDateRDV.getText().toString();
        String newBilan = saisieBilan.getText().toString();

        if (TextUtils.isEmpty(newNom) || TextUtils.isEmpty(newPrenom) || TextUtils.isEmpty(newSexe) || TextUtils.isEmpty(newDateNaissance) ||
                TextUtils.isEmpty(newPoids) || TextUtils.isEmpty(newTaille) || TextUtils.isEmpty(newPathologie) || TextUtils.isEmpty(newDateRDV)
                || TextUtils.isEmpty(newBilan)) {
            toast.show();
        }
        else {
            Intent intent = new Intent(this, LogIn.class);
            intent.putExtra("nom", newNom);
            intent.putExtra("prenom", newPrenom);
            intent.putExtra("sexe", newSexe);
            intent.putExtra("dateNaissance", newDateNaissance);
            intent.putExtra("poids", newPoids);
            intent.putExtra("taille", newTaille);
            intent.putExtra("pathologie", newPathologie);
            intent.putExtra("dateRDV", newDateRDV);
            intent.putExtra("bilan", newBilan);
            startActivity(intent);
            finish();
        }
    }
}
