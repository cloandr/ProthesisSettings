package com.despe.appprot;

import static java.security.AccessController.getContext;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.despe.appprot.online.APICategorie;
import com.despe.appprot.online.ApiClient;
import com.despe.appprot.online.MSG;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends MainActivity implements AdapterView.OnItemClickListener {

    public final static String TAG = "appprot";

    private ListView liste_patient;
    String nom, prenom, sexe, dateNaissance, taille, poids, dateRDV, bilan, pathologie;
    private ProgressDialog pDialog;
    RelativeLayout rel1;
    static ArrayList<ProfilPatient> arrayOfPatients = new ArrayList<ProfilPatient>();
    ArrayList<ProfilPatient> responsePatients = new ArrayList<ProfilPatient>();
    ArrayList<String> patients = new ArrayList<>();

    private Context context = this;
    PatientAdapter adapter;

    protected static ProfilPatient patient_clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        liste_patient = findViewById(R.id.liste_patient);

        liste_patient.setOnItemClickListener(this);

        // Create the adapter to convert the array to views
        adapter = new PatientAdapter(this, arrayOfPatients);

        Intent intent = getIntent();
        if (intent != null & intent.hasExtra("nom")){
            Log.d("MSG_DEBUG", "[LogIn] dans if intent");
            if (intent.hasExtra("nom")){ // vérifie qu'une valeur est associée à la clé “edittext”
                nom = intent.getStringExtra("nom"); // on récupère la valeur associée à la clé
            }
            if (intent.hasExtra("prenom")){ // vérifie qu'une valeur est associée à la clé “prenom”
                prenom = intent.getStringExtra("prenom"); // on récupère la valeur associée à la clé
            }
            if (intent.hasExtra("sexe")){
                sexe = intent.getStringExtra("sexe");
            }
            if (intent.hasExtra("dateNaissance")){
                dateNaissance = intent.getStringExtra("dateNaissance");
            }
            if (intent.hasExtra("poids")){
                poids = intent.getStringExtra("poids");
            }
            if (intent.hasExtra("taille")){
                taille = intent.getStringExtra("taille");
            }
            if (intent.hasExtra("pathologie")){
                pathologie = intent.getStringExtra("pathologie");
            }
            if (intent.hasExtra("dateRDV")){
                dateRDV = intent.getStringExtra("dateRDV");
            }
            if (intent.hasExtra("bilan")){
                bilan = intent.getStringExtra("bilan");
            }
            register();
        }
        populatePatient();
        liste_patient.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddPatient.class);
                Log.d("MSG_DEBUG", "[LogIn] Appui sur le bouton + pour ajouter un patient");
                startActivity(intent);
            }
        });
    }

    public void setPatientClicked(ProfilPatient patient) {
        patient_clicked = patient;
    }

    public ProfilPatient getPatientClicked() {
        return this.patient_clicked;
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setPatientClicked(arrayOfPatients.get(position));
        Log.d("MSG_DEBUG", "[LogIn] Choix du patient effectué");
        Intent intent = new Intent(this, PatientFile.class);
        intent.putExtra("nom", patient_clicked.getNom());
        intent.putExtra("prenom", patient_clicked.getPrenom());
        intent.putExtra("sexe", patient_clicked.getSexe());
        intent.putExtra("dateNaissance", patient_clicked.getDateNaissance());
        intent.putExtra("poids", patient_clicked.getPoids());
        intent.putExtra("taille", patient_clicked.getTaille());
        intent.putExtra("pathologie", patient_clicked.getPathologie());
        intent.putExtra("dateRDV", patient_clicked.getDateRDV());
        intent.putExtra("bilan", patient_clicked.getBilan());
        startActivity(intent);
    }

    /**
     * Mise à jour de la liste patient
     */
    private void populatePatient() {
        APICategorie service = ApiClient.getClient2().create(APICategorie.class);
        Call<ArrayList<ProfilPatient>> call = service.getPatients();
        call.enqueue(new Callback<ArrayList<ProfilPatient>>() {
            @Override
            public void onResponse(Call<ArrayList<ProfilPatient>> call, final Response<ArrayList<ProfilPatient>> response) {
                if(!response.isSuccess()){
                    Log.d("MSG_DEBUG", "[LogIn] Response non successful");
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        Log.d("MSG_DEBUG", response.errorBody().string());
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("MSG_DEBUG", e.getMessage(),e);
                    }
                }else {
                    responsePatients = response.body();
                    arrayOfPatients.clear();
                    for (ProfilPatient p : responsePatients) {
                        arrayOfPatients.add(new ProfilPatient(p.getNom(), p.getPrenom(), p.getDateNaissance(),
                                p.getSexe(), p.getPoids(), p.getTaille(), p.getPathologie(), p.getDateRDV(),
                                p.getBilan()));
                    }
                    Log.d("MSG_DEBUG", "[LogIn] MAJ de la liste des patients via la BDD");
                    liste_patient.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProfilPatient>> call, Throwable t) {
                Log.e("Error",t.getMessage());
                //Toast.makeText(getApplicationContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Ajout d'un nouveau patient
     */
    protected void register(){
        pDialog = new ProgressDialog(LogIn.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Ajout d'un nouveau patient...");
        pDialog.setCancelable(false);
        showpDialog();
        APICategorie service = ApiClient.getClient2().create(APICategorie.class);
        Call<MSG> userCall = service.newPatient(" ", nom, prenom, dateNaissance,
                poids, taille, sexe,
                pathologie, dateRDV, bilan);
        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                //onSignupSuccess();
                int rep = response.body().getSuccess();

                if (rep > 0) {
                    ProfilPatient patient = new ProfilPatient(nom, prenom, dateNaissance, sexe, poids, taille, pathologie, dateRDV, bilan);
                    arrayOfPatients.add(patient);
                    liste_patient.setAdapter(adapter);
                    populatePatient();
                } else {
                    Log.d("MSG_DEBUG", "[LogIn_register_onResponse] Erreur rep<= 0, dans else");
                }
                Log.d("register", response.body().getSuccess() + "-->" + response.body().getMessage());
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                Log.d("MSG_DEBUG", "[LogIn_register_onFailure] dans onFailure");
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "LogIn started");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "LogIn paused");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "LogIn resumed");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "LogIn stopped");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "LogIn destroyed");
        super.onDestroy();
    }
}
