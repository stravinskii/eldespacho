package me.stravinskii.eldespacho.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import me.stravinskii.eldespacho.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MisCitasFragment extends Fragment {


    public MisCitasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mis_citas, container, false);
    }


}
