package me.stravinskii.eldespacho;


import android.app.FragmentManager;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class UbicacionFragment extends Fragment {

    private SupportMapFragment fragment;
    private static GoogleMap mMap;
    private static Double latitude, longitude;
    private final static LatLng ubicacion = new LatLng(19.3544158, -99.24548010000001);

    public UbicacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        latlng 19.3544158, -99.24548010000001
//        View fragmentView = inflater.inflate(R.layout.fragment_ubicacion, container, false);
//        ImageView mapa = (ImageView) fragmentView.findViewById(R.id.imageMapa);
//        mapa.setImageDrawable(getResources().getDrawable(R.drawable.ubicacion));
        View fragmentView = inflater.inflate(R.layout.fragment_ubicacion, container, false);
//        mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();


//        Marker ubicacion = map.
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v4.app.FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }

        mMap = fragment.getMap();
        mMap.addMarker(new MarkerOptions().position(ubicacion)
                .title("El Despacho")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
    }

    /*
    public void onResume() {
        super.onResume();
        if (mMap == null) {
            mMap = fragment.getMap();
            mMap.addMarker(new MarkerOptions().position(ubicacion)
                    .title("El Despacho")).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        }
    }
    */

}
