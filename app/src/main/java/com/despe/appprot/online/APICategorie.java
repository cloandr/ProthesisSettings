package com.despe.appprot.online;

import android.util.Log;

import com.despe.appprot.ProfilPatient;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface APICategorie {
    @FormUrlEncoded
    @POST("api/find/patientCalls.php")
    Call<MSG> newPatient(@Field("insertPatient") String keyPatient,@Field("nom") String nom,
                         @Field("prenom") String prenom, @Field("dateNaissance") String dateNaissance,
                         @Field("poids") String poids, @Field("taille") String taille,
                         @Field("sexe") String sexe, @Field("pathologie") String pathologie,
                         @Field("dateRDV") String dateRDV, @Field("bilan") String bilan);

    @GET("api/find/getPatients.php")
    Call<ArrayList<ProfilPatient>>  getPatients();
}
