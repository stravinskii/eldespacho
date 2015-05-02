package me.stravinskii.eldespacho.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLClientInfoException;

import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;

/**
 * Clase UsuarioEntity hace un mapeo de un registro de la base de datos Usuario
 * TODO: Implementar notificaciones y cifrado de password (salt)
 */
public class
        UsuarioEntity implements Parcelable {

    private int id;
    private String email;
    private String salt;
    private String password;
    private String nombre;
    private String apellidos;
    private String telefono;

    private boolean notificacionSMS;
    private boolean notificacionMAIL;
    private boolean notificacionCALL;
    private boolean notificacionAPP;

    public UsuarioEntity() {
        // Empty constructor
    }

    public UsuarioEntity(Parcel parcel) {
        id = parcel.readInt();
        nombre = parcel.readString();
        apellidos = parcel.readString();
        telefono = parcel.readString();
        email = parcel.readString();
        //salt = parcel.readString();
        password = parcel.readString();
    }

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getTelefono() { return telefono; }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setNotificacionAPP(boolean notificacionAPP) {
        this.notificacionAPP = notificacionAPP;
    }

    public void setNotificacionCALL(boolean notificacionCALL) {
        this.notificacionCALL = notificacionCALL;
    }

    public void setNotificacionMAIL(boolean notificacionMAIL) {
        this.notificacionMAIL = notificacionMAIL;
    }

    public void setNotificacionSMS(boolean notificacionSMS) {
        this.notificacionSMS = notificacionSMS;
    }

    /**
     * Busca un registro en la base de datos a un Usuario con un 'id'
     * @param db base de datos en la que se buscará
     * @param id número de id por el que se buscará
     * @return true si se encontró un registro, false en otro caso
     */
    public boolean findByID(SQLiteDatabase db, int id) {
        Cursor cursor = db.query(false, AppDatabase.Tables.USUARIOS, null,
                AppDatabase.Usuarios.IDUSUARIO + " = " + id, null, null, null, null, "1");
        if (cursor.moveToFirst()) {
            setId(cursor.getInt(cursor.getColumnIndex(AppDatabase.Usuarios.IDUSUARIO)));
            setNombre(cursor.getString(cursor.getColumnIndex(AppDatabase.Usuarios.NOMBRE)));
            //setApellidos(cursor.getString(cursor.getColumnIndex(AppDatabase.Usuarios.APELLIDO)));
            setTelefono(cursor.getString(cursor.getColumnIndex(AppDatabase.Usuarios.TELEFONO)));
            setEmail(cursor.getString(cursor.getColumnIndex(AppDatabase.Usuarios.EMAIL)));
            //setSalt(cursor.getString(cursor.getColumnIndex(AppDatabase.Usuarios.SALT)));
            setPassword(cursor.getString(cursor.getColumnIndex(AppDatabase.Usuarios.PASSWORD)));
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * Busca un registro de Usuario y hace la validación de credenciales
     * @param db base de datos en la que se buscará
     * @param email email del usuario
     * @param password password del usuario
     * @return true si se encontró registro válido, false en otro caso
     */
    public boolean findByCredential(SQLiteDatabase db, String email, String password) {
        Cursor cursor = db.query(false, AppDatabase.Tables.USUARIOS, null,
                AppDatabase.Usuarios.EMAIL + " = " + email, null, null, null, null, "1");
        if (cursor.moveToFirst()) {
            String salt = cursor.getString(cursor.getColumnIndex(AppDatabase.Usuarios.SALT));
            String pswd = cursor.getString(cursor.getColumnIndex(AppDatabase.Usuarios.PASSWORD));

            if (password.equals(pswd)) {
                cursor.close();
                return true;
            }

            // TODO: Descomentar cuando se haya implementado cifrado de password
            /*
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                String compund = password + salt;
                byte[] hash = digest.digest(compund.getBytes("UTF-8"));


                if (hash.equals(pswd.getBytes("UTF-8"))) {
                    cursor.close();
                    return findByID(db, cursor.getInt(
                            cursor.getColumnIndex(AppDatabase.Usuarios.IDUSUARIO)));
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
            */
        }
        cursor.close();
        return false;
    }

    /**
     * Inserta un nuevo 'usuario' en la base de datos
     * @param db base de datos en la que se insertará
     * @return número de id insertado o -1 en caso de error
     */
    public boolean insert(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(AppDatabase.Usuarios.NOMBRE, nombre);
        //values.put(AppDatabase.Usuarios.APELLIDO, apellidos);
        values.put(AppDatabase.Usuarios.EMAIL, email);
        //values.put(AppDatabase.Usuarios.SALT, salt);
        values.put(AppDatabase.Usuarios.PASSWORD, password);
        values.put(AppDatabase.Usuarios.TELEFONO, telefono);
        //values.put(AppDatabase.Usuarios.NOTIFICACION_SMS, notificacionSMS);
        //values.put(AppDatabase.Usuarios.NOTIFICACION_APP, notificacionAPP);
        //values.put(AppDatabase.Usuarios.NOTIFICACION_CALL, notificacionCALL);
        //values.put(AppDatabase.Usuarios.NOTIFICACION_MAIL, notificacionMAIL);

        long insert = db.insert(AppDatabase.Tables.USUARIOS, null, values);
        if (insert == -1) { return false; } else { id = (int) insert; };
        return true;
    }

    /**
     * Actualiza 'usuario' en la base de datos utilizando su id
     * @param db base de datos en la que se actualizará
     * @return regresa número de filas actualizadas
     */
    public int update(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(AppDatabase.Usuarios.IDUSUARIO, id);
        values.put(AppDatabase.Usuarios.NOMBRE, nombre);
        //values.put(AppDatabase.Usuarios.APELLIDO, apellidos);
        values.put(AppDatabase.Usuarios.EMAIL, email);
        //values.put(AppDatabase.Usuarios.SALT, salt);
        values.put(AppDatabase.Usuarios.PASSWORD, password);
        values.put(AppDatabase.Usuarios.TELEFONO, telefono);
        //values.put(AppDatabase.Usuarios.NOTIFICACION_SMS, notificacionSMS);
        //values.put(AppDatabase.Usuarios.NOTIFICACION_APP, notificacionAPP);
        //values.put(AppDatabase.Usuarios.NOTIFICACION_CALL, notificacionCALL);
        //values.put(AppDatabase.Usuarios.NOTIFICACION_MAIL, notificacionMAIL);

        return db.update(AppDatabase.Tables.USUARIOS, values,
                AppDatabase.Usuarios.IDUSUARIO + " = ?", new String[] {String.valueOf(id)});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        //dest.writeString(apellidos);
        dest.writeString(telefono);
        dest.writeString(email);
        //dest.writeString(salt);
        dest.writeString(password);
        /*
        dest.writeBooleanArray(new boolean[] {
                notificacionAPP,
                notificacionCALL,
                notificacionMAIL,
                notificacionSMS
        });
        */
    }

    public static final Creator<UsuarioEntity> CREATOR = new Creator<UsuarioEntity>() {
        @Override
        public UsuarioEntity createFromParcel(Parcel source) {
            UsuarioEntity usuario = new UsuarioEntity();
            boolean[] notificaciones = new boolean[4];

            usuario.setId(source.readInt());
            usuario.setNombre(source.readString());
            //usuario.setApellidos(source.readString());
            usuario.setTelefono(source.readString());
            usuario.setEmail(source.readString());
            //usuario.setSalt(source.readString());
            usuario.setPassword(source.readString());
            /*
            source.readBooleanArray(notificaciones);
            usuario.setNotificacionAPP(notificaciones[0]);
            usuario.setNotificacionCALL(notificaciones[1]);
            usuario.setNotificacionMAIL(notificaciones[2]);
            usuario.setNotificacionSMS(notificaciones[3]);
            */

            return usuario;
        }

        @Override
        public UsuarioEntity[] newArray(int size) {
            return new UsuarioEntity[size];
        }
    };
}
