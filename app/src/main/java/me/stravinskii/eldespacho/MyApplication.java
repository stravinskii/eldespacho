package me.stravinskii.eldespacho;

import android.app.Application;
import android.support.v4.app.Fragment;

/**
 * Created by mauricio on 23/03/15.
 */
public class MyApplication extends Application {

    private boolean sesion = false;
    private int fragmentReferer = 0;
    private String tipoDeUsuario = "";

    public String getTipoDeUsuario() {
        return tipoDeUsuario;
    }

    public void setTipoDeUsuario(String tipoDeUsuario) {
        this.tipoDeUsuario = tipoDeUsuario;
    }

    public boolean hasSesion() {
        return sesion;
    }

    public void setSesion(boolean sesion) {
        this.sesion = sesion;
    }

    public int getFragmentReferer() {
        return fragmentReferer;
    }

    public void setFragmentReferer(int fragmentReferer) {
        this.fragmentReferer = fragmentReferer;
    }

    public String[] getNavigationDrawerMenu() {
        String[] mDrawerMenu;
        switch (tipoDeUsuario) {
            case "usuario":
                mDrawerMenu = new String[]{
                        getString(R.string.title_horarios),
                        getString(R.string.title_agendarcita),
                        getString(R.string.title_miscitas),
                        getString(R.string.title_ubicacion),
                        //getString(R.string.title_configuracion),
                        //getString(R.string.title_cerrarsesion),
                };
                break;
            case "admin":
                mDrawerMenu = new String[]{
                        getString(R.string.title_horarios),
                        getString(R.string.title_miscitas),
                        //getString(R.string.title_configuracion),
                        //getString(R.string.title_cerrarsesion),
                };
                break;
            default:
                mDrawerMenu = new String[]{
                        getString(R.string.title_horarios),
                        getString(R.string.title_agendarcita),
                        getString(R.string.title_ubicacion),
                };
                break;
        }
        return mDrawerMenu;
    }

    public Fragment getFragment(int position) {
        if (tipoDeUsuario.equals("usuario")) {
            switch (position) {
                case 0: //Horarios
                    return new HorariosFragment();
                case 1: //Agendar Cita
                    return new AgendarCitaFragment();
                case 2: //Mis Citas
                    return new MisCitasFragment();
                case 3: //Ubicacion
                    return new UbicacionFragment();
                case 4: //Configuracion
                    break;
                case 5: //Cerrar Session
                    break;
            }
        } else if (tipoDeUsuario.equals("admin")) {
            switch (position) {
                case 0: //Horarios
                    return new HorariosFragment();
                case 1: //Mis Citas
                    return new AgendaCitasFragment();
                case 2: //Configuracion
                    break;
                case 3: //Cerrar Session
                    break;
            }
        } else {
            switch (position) {
                case 0: //Horarios
                    return new HorariosFragment();
                case 1: //Agendar Cita
                    return new AgendarCitaFragment();
                case 2: //Ubicacion
                    return new UbicacionFragment();
            }
        }

        return null;
    }
}
