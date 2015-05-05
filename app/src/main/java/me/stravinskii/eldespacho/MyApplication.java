package me.stravinskii.eldespacho;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import me.stravinskii.eldespacho.data.UsuarioEntity;
import me.stravinskii.eldespacho.fragments.AgendaCitasFragment;
import me.stravinskii.eldespacho.fragments.AgendarCitaFragment;
import me.stravinskii.eldespacho.fragments.HorariosFragment;
import me.stravinskii.eldespacho.fragments.MisCitasFragment;
import me.stravinskii.eldespacho.fragments.UbicacionFragment;

/**
 * Created by mauricio on 23/03/15.
 */
public class MyApplication extends Application {

    public static final String appPreferences = "appPreferences";

    private SharedPreferences sharedPreferences;
    private UsuarioEntity usuario;

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
        MenuItem item = new MenuItem(0, getString(R.string.title_horarios), HorariosFragment.class);
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

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", usuario.getId());
        editor.putString("name", usuario.getNombre());
        editor.commit();
    }

    public static class MenuItem {
        public int position;
        public String title;
        public Class fragment;

        public MenuItem(int position, String title, Class fragment) {
            this.title = title;
            this.position = position;
            this.fragment = fragment;
        }
    }
}
