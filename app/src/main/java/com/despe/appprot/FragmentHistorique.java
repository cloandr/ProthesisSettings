package com.despe.appprot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHistorique#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHistorique extends Fragment {

    public static FragmentHistorique newInstance() {
        return (new FragmentHistorique());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MSG_DEBUG", "[FragmentHistorique] onCreateView");
        return inflater.inflate(R.layout.fragment_historique, container, false);
    }
}