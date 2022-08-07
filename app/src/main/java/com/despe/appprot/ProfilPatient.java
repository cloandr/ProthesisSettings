
package com.despe.appprot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ProfilPatient {

    // propriétés
    @SerializedName("nom")
    @Expose
    private String nom;

    @SerializedName("prenom")
    @Expose
    private String prenom;

    @SerializedName("dateNaissance")
    @Expose
    private String dateNaissance;

    @SerializedName("sexe")
    @Expose
    private String sexe;

    @SerializedName("poids")
    @Expose
    private String poids;

    @SerializedName("taille")
    @Expose
    private String taille;

    @SerializedName("pathologie")
    @Expose
    private String pathologie;

    @SerializedName("dateRDV")
    @Expose
    private String dateRDV;

    @SerializedName("bilan")
    @Expose
    private String bilan;

    public ProfilPatient(String nom, String prenom, String dateNaissance, String sexe, String poids, String taille, String pathologie, String dateRDV, String bilan) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.pathologie = pathologie;
        this.dateRDV = dateRDV;
        this.bilan = bilan;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public String getPoids() {
        return poids;
    }

    public String getTaille() {
        return taille;
    }

    public String getPathologie() {
        return pathologie;
    }

    public String getDateRDV() {
        return dateRDV;
    }

    public String getBilan() {
        return bilan;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public void setPathologie(String pathologie) {
        this.pathologie = pathologie;
    }

    public void setDateRDV(String dateRDV) {
        this.dateRDV = dateRDV;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

}