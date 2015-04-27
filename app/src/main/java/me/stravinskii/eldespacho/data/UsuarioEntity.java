package me.stravinskii.eldespacho.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mauricio on 4/19/15.
 */
public class UsuarioEntity implements Parcelable {

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

    public UsuarioEntity(int id, String email, String salt, String password, String nombre, String apellidos, String telefono) {
        this.id = id;
        this.email = email;
        this.salt = salt;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }

    public UsuarioEntity(int id, String email, String salt, String password, String nombre, String apellidos, String telefono,
                         boolean notificacionSMS, boolean notificacionMAIL, boolean notificacionCALL, boolean notificacionAPP) {
        this.id = id;
        this.email = email;
        this.salt = salt;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;

        this.notificacionAPP = notificacionAPP;
        this.notificacionCALL = notificacionCALL;
        this.notificacionMAIL = notificacionMAIL;
        this.notificacionSMS = notificacionSMS;
    }

    public UsuarioEntity(Parcel parcel) {
        this.email = parcel.readString();
        this.salt = parcel.readString();
        this.password = parcel.readString();
        this.nombre = parcel.readString();
        this.apellidos = parcel.readString();
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
     * Inserta un nuevo 'usuario' en la base de datos
     * @param db base de datos en la que se insertará
     * @return número de id insertado o -1 en caso de error
     */
    public long insert(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(AppDatabase.Usuarios.IDUSUARIO, id);
        values.put(AppDatabase.Usuarios.NOMBRE, nombre);
        values.put(AppDatabase.Usuarios.APELLIDO, apellidos);
        values.put(AppDatabase.Usuarios.EMAIL, email);
        values.put(AppDatabase.Usuarios.SALT, salt);
        values.put(AppDatabase.Usuarios.PASSWORD, password);
        values.put(AppDatabase.Usuarios.TELEFONO, telefono);
        values.put(AppDatabase.Usuarios.NOTIFICACION_SMS, notificacionSMS);
        values.put(AppDatabase.Usuarios.NOTIFICACION_APP, notificacionAPP);
        values.put(AppDatabase.Usuarios.NOTIFICACION_CALL, notificacionCALL);
        values.put(AppDatabase.Usuarios.NOTIFICACION_MAIL, notificacionMAIL);

        return db.insert(AppDatabase.Tables.USUARIOS, null, values);
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
        values.put(AppDatabase.Usuarios.APELLIDO, apellidos);
        values.put(AppDatabase.Usuarios.EMAIL, email);
        values.put(AppDatabase.Usuarios.SALT, salt);
        values.put(AppDatabase.Usuarios.PASSWORD, password);
        values.put(AppDatabase.Usuarios.TELEFONO, telefono);
        values.put(AppDatabase.Usuarios.NOTIFICACION_SMS, notificacionSMS);
        values.put(AppDatabase.Usuarios.NOTIFICACION_APP, notificacionAPP);
        values.put(AppDatabase.Usuarios.NOTIFICACION_CALL, notificacionCALL);
        values.put(AppDatabase.Usuarios.NOTIFICACION_MAIL, notificacionMAIL);

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
        dest.writeString(email);
        dest.writeString(salt);
        dest.writeString(password);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(telefono);
        dest.writeBooleanArray(new boolean[] {
                notificacionAPP,
                notificacionCALL,
                notificacionMAIL,
                notificacionSMS
        });
    }

    public static final Creator<UsuarioEntity> CREATOR = new Creator<UsuarioEntity>() {
        @Override
        public UsuarioEntity createFromParcel(Parcel source) {
            UsuarioEntity usuario = new UsuarioEntity();
            boolean[] notificaciones = new boolean[4];

            usuario.setId(source.readInt());
            usuario.setEmail(source.readString());
            usuario.setSalt(source.readString());
            usuario.setPassword(source.readString());
            usuario.setNombre(source.readString());
            usuario.setApellidos(source.readString());
            usuario.setTelefono(source.readString());
            source.readBooleanArray(notificaciones);
            usuario.setNotificacionAPP(notificaciones[0]);
            usuario.setNotificacionCALL(notificaciones[1]);
            usuario.setNotificacionMAIL(notificaciones[2]);
            usuario.setNotificacionSMS(notificaciones[3]);

            return usuario;
        }

        @Override
        public UsuarioEntity[] newArray(int size) {
            return new UsuarioEntity[size];
        }
    };
}
