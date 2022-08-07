package com.despe.appprot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.despe.appprot.ProfilPatient;

import java.util.ArrayList;

public class PatientsAdapter extends ArrayAdapter<ProfilPatient> {
    public PatientsAdapter(Context context, ArrayList<ProfilPatient> patients){
        super(context,0,patients);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ProfilPatient patient = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_patient, parent, false);
        }
        // Lookup view for data population

        TextView prenom = (TextView) convertView.findViewById(R.id.prenom);
        TextView nom = (TextView) convertView.findViewById(R.id.nom);

        // Populate the data into the template view using the data object

        prenom.setText(patient.getPrenom());
        nom.setText(patient.getNom());

        // Return the completed view to render on screen
        return convertView;
    }

}
