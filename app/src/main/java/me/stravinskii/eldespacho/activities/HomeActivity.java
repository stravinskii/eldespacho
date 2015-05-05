package me.stravinskii.eldespacho.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;

import me.stravinskii.eldespacho.MyApplication;
import me.stravinskii.eldespacho.fragments.NavigationDrawerFragment;
import me.stravinskii.eldespacho.R;
import me.stravinskii.eldespacho.fragments.AgendarCitaFragment;
import me.stravinskii.eldespacho.fragments.HorariosFragment;
import me.stravinskii.eldespacho.fragments.MisCitasFragment;
import me.stravinskii.eldespacho.fragments.UbicacionFragment;


public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static FragmentManager fragmentManager;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        fragmentManager = getSupportFragmentManager();
        //setContentView(R.layout.activity_home);
        /*
        if (savedInstanceState != null) {
            int fragmentPosition = getIntent().getIntExtra("fragmentPosition", 0);
            onNavigationDrawerItemSelected(fragmentPosition);
        }
        */

        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        MyApplication app = ((MyApplication) this.getApplication());
        Fragment fragment = null; Class activity = null;
        for (MyApplication.MenuItem menuItem : app.getMenu()) {
            if (menuItem.position == position) {
                mTitle = menuItem.title;

                try {
                    if (menuItem.fragment.getSuperclass() == Fragment.class){
                        fragment = (Fragment) menuItem.fragment.newInstance();
                    } else {
                        activity = menuItem.fragment;
                    }
                    break;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        }
    }

    public void onSectionAttached(int number) {
        MyApplication app = ((MyApplication) this.getApplication());
        for (MyApplication.MenuItem menuItem : app.getMenu()) {
            if (menuItem.position + 1 == number) {
                mTitle = menuItem.title;
                break;
            }
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);

            MyApplication app = ((MyApplication) this.getApplication());
            if (app.getUsuario() != null) {
                menu.findItem(R.id.action_login).setVisible(false);
            } else {
                menu.findItem(R.id.action_logout).setVisible(false);
            }

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            ImageView logo = (ImageView) rootView.findViewById(R.id.eldespacho_img);
            logo.setImageDrawable(getResources().getDrawable(R.drawable.logo));
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
