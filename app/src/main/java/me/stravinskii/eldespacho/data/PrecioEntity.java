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
import java.util.ArrayList;

/**
 * Clase PrecioEntity hace un mapeo de un registro de la base de datos precio
 */
public class
        PrecioEntity implements Parcelable {

    private int id;
    private String concepto;
    private String precio;

    public PrecioEntity() {
        // Empty constructor
    }

    public PrecioEntity(Parcel parcel) {
        id = parcel.readInt();
        concepto = parcel.readString();
        precio = parcel.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    /**
     * Método que regresa un arreglo con todos los registros de precios en la bd
     * @param db base de datos en la que se buscará
     * @return arreglo de precios en objetos PrecioEntity
     */
    public static ArrayList<PrecioEntity> all(SQLiteDatabase db) {
        ArrayList<PrecioEntity> precios = new ArrayList<>();
        Cursor cursor = db.query(AppDatabase.Tables.PRECIOS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                PrecioEntity precio = new PrecioEntity();
                precio.setId(cursor.getInt(cursor.getColumnIndex(AppDatabase.Precios.IDPRECIO)));
                precio.setConcepto(cursor.getString(cursor.getColumnIndex(AppDatabase.Precios.CONCEPTO)));
                precio.setPrecio(cursor.getString(cursor.getColumnIndex(AppDatabase.Precios.PRECIO)));
                precios.add(precio);
            }
        }
        return precios;
    }

    /**
     * Busca un registro en la base de datos a un precio con un 'id'
     * @param db base de datos en la que se buscará
     * @param id número de id por el que se buscará
     * @return true si se encontró un registro, false en otro caso
     */
    public boolean findByID(SQLiteDatabase db, int id) {
        Cursor cursor = db.query(false, AppDatabase.Tables.PRECIOS, null,
                AppDatabase.Precios.IDPRECIO + " = " + id, null, null, null, null, "1");
        if (cursor.moveToFirst()) {
            setId(cursor.getInt(cursor.getColumnIndex(AppDatabase.Precios.IDPRECIO)));
            setConcepto(cursor.getString(cursor.getColumnIndex(AppDatabase.Precios.CONCEPTO)));
            setPrecio(cursor.getString(cursor.getColumnIndex(AppDatabase.Precios.PRECIO)));
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * Inserta un nuevo 'precio' en la base de datos
     * @param db base de datos en la que se insertará
     * @return número de id insertado o -1 en caso de error
     */
    public boolean insert(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(AppDatabase.Precios.CONCEPTO, concepto);
        values.put(AppDatabase.Precios.PRECIO, precio);

        long insert = db.insert(AppDatabase.Tables.PRECIOS, null, values);
        if (insert == -1) { return false; } else { id = (int) insert; };
        return true;
    }

    /**
     * Actualiza 'precio' en la base de datos utilizando su id
     * @param db base de datos en la que se actualizará
     * @return regresa número de filas actualizadas
     */
    public int update(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(AppDatabase.Precios.IDPRECIO, id);
        values.put(AppDatabase.Precios.CONCEPTO, concepto);
        values.put(AppDatabase.Precios.PRECIO, precio);

        return db.update(AppDatabase.Tables.PRECIOS, values,
                AppDatabase.Precios.IDPRECIO + " = ?", new String[] {String.valueOf(id)});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(concepto);
        dest.writeString(precio);
    }

    public static final Creator<PrecioEntity> CREATOR = new Creator<PrecioEntity>() {
        @Override
        public PrecioEntity createFromParcel(Parcel source) {
            PrecioEntity precio = new PrecioEntity();

            precio.setId(source.readInt());
            precio.setConcepto(source.readString());
            precio.setPrecio(source.readString());

            return precio;
        }

        @Override
        public PrecioEntity[] newArray(int size) {
            return new PrecioEntity[size];
        }
    };
}
