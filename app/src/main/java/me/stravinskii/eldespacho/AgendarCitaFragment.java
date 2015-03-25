package me.stravinskii.eldespacho;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendarCitaFragment extends Fragment implements View.OnClickListener {

    private final int fragmentPosition = 1;

    public AgendarCitaFragment() {
        // Required empty public constructor
    }

    public void onClick(View view) {
        Fragment fragmentNext = new AgendarCitaNextFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragmentNext);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MyApplication app = ((MyApplication) getActivity().getApplication());
        if (app.hasSesion()) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_agendar_cita, container, false);
            Button nextButton = (Button) view.findViewById(R.id.agendarNext);
            nextButton.setOnClickListener(this);
            return view;
        } else {
            app.setFragmentReferer(fragmentPosition);
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            return null;
        }
    }


}
