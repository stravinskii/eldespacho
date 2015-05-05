package me.stravinskii.eldespacho;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import me.stravinskii.eldespacho.activities.LoginActivity;
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

    public MenuItem[] getMenu() {
        MenuItem[] menu = null;
        if (usuario != null) {
            switch (usuario.getTipo()) {
                case "0": // Usuario / Cliente
                    menu = new MenuItem[] {
                            new MenuItem(0, "Horarios Disponibles", HorariosFragment.class),
                            new MenuItem(1, "Agendar Cita", AgendarCitaFragment.class),
                            new MenuItem(2, "Mis Citas", MisCitasFragment.class),
                            new MenuItem(3, "Ubicación", UbicacionFragment.class)
                    };
                    break;
                case "1": // Usuario / Admin
                    menu = new MenuItem[] {
                            new MenuItem(0, "Horarios Disponibles", HorariosFragment.class),
                            new MenuItem(1, "Mis Citas", MisCitasFragment.class),
                            //new MenuItem(2, "Precios", UbicacionFragment.class)
                    };
                    break;
            }
        } else {
            menu = new MenuItem[] {
                    new MenuItem(0, "Horarios Disponibles", HorariosFragment.class),
                    new MenuItem(1, "Agendar Cita", AgendarCitaFragment.class),
                    new MenuItem(2, "Ubicación", UbicacionFragment.class),
                    //new MenuItem(3, "Precios", UbicacionFragment.class)
                    new MenuItem(3, "Iniciar Sesión", LoginActivity.class)
            };
        }
        return menu;
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

    public void removeUsuario() {
        usuario = null;
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
