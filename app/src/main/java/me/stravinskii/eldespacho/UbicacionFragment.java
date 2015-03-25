package me.stravinskii.eldespacho;


import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UbicacionFragment extends Fragment {


    public UbicacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_ubicacion, container, false);
        ImageView mapa = (ImageView) fragmentView.findViewById(R.id.imageMapa);
        mapa.setImageDrawable(getResources().getDrawable(R.drawable.ubicacion));
        return fragmentView;
    }


}
