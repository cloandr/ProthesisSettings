package com.despe.appprot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.Serializable;

import butterknife.OnClick;

//import javax.xml.xpath.XPath;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfil extends Fragment  {

    Toast toast;
    Toast toast2;
    TextView test;
    TextView saisieNom, saisiePrenom, saisieSexe, saisiedateNaissance, saisiePoids, saisieTaille, saisiePathologie, saisieDateRDV, saisieBilan;
    String nom, prenom, sexe, dateNaissance, poids, taille, pathologie, dateRDV ,bilan;
    public static FragmentProfil newInstance() { return (new FragmentProfil());}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MSG_DEBUG", "[FragmentProfil] onCreateView");
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        //Button buttonModif = view.findViewById(R.id.button2);
        saisieNom = view.findViewById(R.id.nom);
        saisiePrenom = view.findViewById(R.id.prenom);
        saisieSexe = view.findViewById(R.id.sexe);
        saisiedateNaissance = view.findViewById(R.id.dateNaissance);
        saisiePoids = view.findViewById(R.id.poids);
        saisieTaille = view.findViewById(R.id.taille);
        saisiePathologie = view.findViewById(R.id.pathologie);
        saisieDateRDV =view.findViewById(R.id.dateRDV);
        saisieBilan= view.findViewById(R.id.bilan);

        //test = view.findViewById(R.id.textviewnom);


        toast = Toast.makeText(getActivity(), "Attention, au moins un des champs est vide. Saisie non enregistrée", Toast.LENGTH_LONG);
        toast2 = Toast.makeText(getActivity(), "Changement(s) enregistré(s)", Toast.LENGTH_LONG);
        Intent intent = getActivity().getIntent();
        if (intent != null & intent.hasExtra("nom")) {
            if (intent.hasExtra("nom")) { // vérifie qu'une valeur est associée à la clé “nom”
                nom = intent.getStringExtra("nom"); // on récupère la valeur associée à la clé
                Log.d("MSG_DEBUG", nom);
            }
            if (intent.hasExtra("prenom")) { // vérifie qu'une valeur est associée à la clé “prenom”
                prenom = intent.getStringExtra("prenom"); // on récupère la valeur associée à la clé
            }
            if (intent.hasExtra("sexe")) {
                sexe = intent.getStringExtra("sexe");
            }
            if (intent.hasExtra("dateNaissance")) {
                dateNaissance = intent.getStringExtra("dateNaissance");
            }
            if (intent.hasExtra("poids")) {
                poids = intent.getStringExtra("poids");
            }
            if (intent.hasExtra("taille")) {
                taille = intent.getStringExtra("taille");
            }
            if (intent.hasExtra("pathologie")) {
                pathologie = intent.getStringExtra("pathologie");
            }
            if (intent.hasExtra("dateRDV")) {
                dateRDV = intent.getStringExtra("dateRDV");
            }
            if (intent.hasExtra("bilan")) {
                bilan = intent.getStringExtra("bilan");
            }
            saisieNom.setText(nom);
            saisiePrenom.setText(prenom);
            saisieSexe.setText(sexe);
            saisieTaille.setText(taille);
            saisiePoids.setText(poids);
            saisiePathologie.setText(pathologie);
            saisiedateNaissance.setText(dateNaissance);
            saisieDateRDV.setText(dateRDV);
            saisieBilan.setText(bilan);
        }


        //buttonModif.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("MSG_DEBUG", "[FragmentProfil] onCreate");
        super.onCreate(savedInstanceState);
    }

}