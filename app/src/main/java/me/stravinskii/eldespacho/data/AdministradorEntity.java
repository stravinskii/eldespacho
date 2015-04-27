package me.stravinskii.eldespacho.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mauricio on 4/26/15.
 */
public class AdministradorEntity implements Parcelable {

	private int id;
	private String nombre;
	private String apellido;
	private String email;
    private String salt;
	private String password;
	private String telefono;

	public AdministradorEntity() {
		// Empty constructor
	}

	public AdministradorEntity(int id, SQLiteDatabase db) {
		// Get entity from db
        db.execSQL("SELECT * FROM " + AppDatabase.Tables.ADMINISTRADORES
                + " WHERE " + AppDatabase.Administradores.IDADMINISTRADOR + " = " + id
        );

        Cursor cursor = db.query(false, AppDatabase.Tables.ADMINISTRADORES, null, "WHERE "
                + AppDatabase.Administradores.IDADMINISTRADOR + " = " + id, null, null, null, null, null);
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public long insert(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(AppDatabase.Administradores.IDADMINISTRADOR, id);
        values.put(AppDatabase.Administradores.NOMBRE, nombre);
        values.put(AppDatabase.Administradores.APELLIDO, apellido);
        values.put(AppDatabase.Administradores.EMAIL, email);
        values.put(AppDatabase.Administradores.PASSWORD, password);
        values.put(AppDatabase.Administradores.TELEFONO, telefono);

        return db.insert(AppDatabase.Tables.ADMINISTRADORES, null, values);
    }

    public int update(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(AppDatabase.Administradores.IDADMINISTRADOR, id);
        values.put(AppDatabase.Administradores.NOMBRE, nombre);
        values.put(AppDatabase.Administradores.APELLIDO, apellido);
        values.put(AppDatabase.Administradores.EMAIL, email);
        values.put(AppDatabase.Administradores.PASSWORD, password);
        values.put(AppDatabase.Administradores.TELEFONO, telefono);

        return db.update(AppDatabase.Tables.ADMINISTRADORES, values,
                "WHERE " + AppDatabase.Administradores.IDADMINISTRADOR + " = " + id, null);

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
        dest.writeString(apellido);
        dest.writeString(telefono);
    }

    public static final Parcelable.Creator<AdministradorEntity> CREATOR = new Parcelable.Creator<AdministradorEntity>() {
        @Override
        public AdministradorEntity createFromParcel(Parcel source) {
            AdministradorEntity admin = new AdministradorEntity();
            admin.setId(source.readInt());
            admin.setEmail(source.readString());
            admin.setSalt(source.readString());
            admin.setPassword(source.readString());
            admin.setNombre(source.readString());
            admin.setApellido(source.readString());
            admin.setTelefono(source.readString());
            return admin;
        }

        @Override
        public AdministradorEntity[] newArray(int size) {
            return new AdministradorEntity[size];
        }
    };
}
