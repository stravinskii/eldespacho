package me.stravinskii.eldespacho;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendarCitaNextFragment extends Fragment implements View.OnClickListener {


    public AgendarCitaNextFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agendar_cita_next, container, false);

        ImageView driveIcon = (ImageView) view.findViewById(R.id.driveIcon);
        driveIcon.setImageDrawable(getResources().getDrawable(R.drawable.drive));
        ImageView dropboxIcon = (ImageView) view.findViewById(R.id.dropboxIcon);
        dropboxIcon.setImageDrawable(getResources().getDrawable(R.drawable.dropbox));

        Button cancelarButton = (Button) view.findViewById(R.id.buttonCancelar);
        cancelarButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new AgendarCitaFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
