package me.stravinskii.eldespacho.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import me.stravinskii.eldespacho.MyApplication;
import me.stravinskii.eldespacho.R;
import me.stravinskii.eldespacho.data.AppDatabase;
import me.stravinskii.eldespacho.data.UsuarioEntity;

public class RegistroActivity extends Activity {

    private EditText nombre;
    private EditText phone;
    private EditText email;
    private EditText pswd;
    private EditText pswdConfirm;

    public void registrar(View view) {
        Editable nombreText = nombre.getText();
        Editable phoneText = phone.getText();
        Editable emailText = email.getText();
        Editable pswdText = pswd.getText();
        Editable pswdConfirmText = pswdConfirm.getText();

//        Toast.makeText(this, pswdText.toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, pswdConfirmText.toString(), Toast.LENGTH_SHORT).show();
        if (pswdText.toString().equals(pswdConfirmText.toString())) {

            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setNombre(nombreText.toString());
            usuario.setEmail(emailText.toString());
            usuario.setTelefono(phoneText.toString());
            usuario.setPassword(pswdText.toString());

            AppDatabase appDB = new AppDatabase(getBaseContext());
            SQLiteDatabase db = appDB.getWritableDatabase();
            //Toast.makeText(this, "DEBUG: Los passwords coinciden", Toast.LENGTH_SHORT).show();

            if (usuario.insert(db)) {
                // Escribir en sesión/parcelable e ir a otra actividad
                Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();

                MyApplication app = (MyApplication) getApplication();
                app.setSharedPreferences(getSharedPreferences(
                        MyApplication.appPreferences, Context.MODE_PRIVATE));
                app.setUsuario(usuario);
                app.setSesion(true);

                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                // En caso de fallo mostrar alerta (ver action example)
                Toast.makeText(this, "Falló registro de usuario. Intente más tarde",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // En caso de passwords diferentes mostrar alerta
            Toast.makeText(this, "Error: Los passwords no coinciden", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = (EditText) findViewById(R.id.register_name);
        phone = (EditText) findViewById(R.id.register_phone);
        email = (EditText) findViewById(R.id.register_email);
        pswd = (EditText) findViewById(R.id.register_password);
        pswdConfirm = (EditText) findViewById(R.id.register_pswdconfirm);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
        return true;
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
}
