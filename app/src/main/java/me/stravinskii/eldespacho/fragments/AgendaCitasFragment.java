package me.stravinskii.eldespacho.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.stravinskii.eldespacho.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaCitasFragment extends Fragment {


    public AgendaCitasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agenda_citas, container, false);
    }


}
