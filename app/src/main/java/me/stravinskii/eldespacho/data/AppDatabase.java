package me.stravinskii.eldespacho.data;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Clase para la definición de la base de datos SQLite declarando las especificaciones de la misma
 *
 * La clase se basa en la utilización de objetos estáticos de java.util.Map para las especificaciones
 * Created by mauricio on 4/19/15.
 */
public class AppDatabase extends SQLiteOpenHelper {

    /**
     * Versión de la base de datos
     */
    private final static int databaseVersion = 102;

    /**
     * Nombre de la base de datos dentro de SQLite
     */
    private final static String databaseName = "eldespacho";

    /*
     * Nombre de las tablas de la base de datos
     */
    public interface Tables {
        public static final String USUARIOS = "usuarios";
        public static final String ADMINISTRADORES = "administradores";
        public static final String CITAS = "citas";
        public static final String PRECIOS = "precios";
    }

    /*
     * Columnas para la tabla 'usuarios'
     */
    public interface Usuarios {
        public static final String IDUSUARIO = "idusuario";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDO = "apellido";
        public static final String EMAIL = "email";
        public static final String SALT = "salt";
        public static final String PASSWORD = "password";
        public static final String TELEFONO = "telefono";

        /* 0 => cliente | 1 => administrador */
        public static final String TIPO = "tipo";

        public static final String NOTIFICACION_SMS = "notificacion_sms";
        public static final String NOTIFICACION_APP = "notificacion_app";
        public static final String NOTIFICACION_CALL = "notificacion_call";
        public static final String NOTIFICACION_MAIL = "notificacion_mail";
    }

    /**
     * Especificación de la tabla 'usuarios'
     */
    public final static Map<String, String> usuarios;
    static {
        usuarios = new HashMap<String, String>();
        usuarios.put(Usuarios.IDUSUARIO, "INTEGER PRIMARY KEY AUTOINCREMENT");
        usuarios.put(Usuarios.NOMBRE, "TEXT");
        //usuarios.put(Usuarios.APELLIDO, "TEXT NOT NULL");
        usuarios.put(Usuarios.EMAIL, "TEXT NOT NULL");
        //usuarios.put(Usuarios.SALT, "TEXT NOT NULL");
        usuarios.put(Usuarios.PASSWORD, "TEXT NOT NULL");
        usuarios.put(Usuarios.TELEFONO, "TEXT NOT NULL");
        usuarios.put(Usuarios.TIPO, "TEXT");

        // Tipos de notificaciones para el usuario
        /*
        usuarios.put(Usuarios.NOTIFICACION_SMS, "NUMERIC");
        usuarios.put(Usuarios.NOTIFICACION_APP, "NUMERIC");
        usuarios.put(Usuarios.NOTIFICACION_CALL, "NUMERIC");
        usuarios.put(Usuarios.NOTIFICACION_MAIL, "NUMERIC");
        */
    };

    private final static Map<String, String> citas = new HashMap<String, String>();

    public interface Precios {
        public static final String IDPRECIO = "idprecio";
        public static final String CONCEPTO = "concepto";
        public static final String PRECIO = "precio";
    }

    public final static Map<String, String> precios;
    static {
        precios = new HashMap<String,String>();
        precios.put(Precios.IDPRECIO, "INTEGER PRIMARY KEY AUTOINCREMENT");
        precios.put(Precios.CONCEPTO, "TEXT NOT NULL");
        precios.put(Precios.PRECIO, "TEXT NOT NULL");
    }

    /**
     * Especificación de las tablas de la base de datos
     */
    public final static Map<String, Map<String, String>> databaseTables;
    static {
        databaseTables = new HashMap<String, Map<String, String>>();
        databaseTables.put(Tables.USUARIOS, usuarios);
        databaseTables.put(Tables.PRECIOS, precios);
        //databaseTables.put(Tables.ADMINISTRADORES, administradores);
        //databaseTables.put(Tables.CITAS, citas);
    }

    public static final String[] seeds = new String[] {
        "INSERT INTO " + Tables.USUARIOS + " (nombre, email, password, telefono, tipo)" +
                " VALUES ('Sys Admin', 'admin@eldespacho.com', '123456', '56451325', '1')",
        "INSERT INTO " + Tables.USUARIOS + " (nombre, email, password, telefono, tipo)" +
                " VALUES ('Usuario', 'usuario@eldespacho.com', '123456', '56642778', '0')",

        "INSERT INTO " + Tables.PRECIOS + " (concepto, precio)" +
                " VALUES ('Corte de 10am a 6pm', '$350 x hora')," +
                " ('Corte de 6pm a 9pm', '$450 x hora')," +
                " ('Corte de 9pm a 12am', '$550 x hora')," +
                " ('Corte de 12am a 7am', '$650 x hora')," +
                " ('Corte de 7am a 10am', '$400 x hora')"
    };

    /**
     * Constructor de la clase
     * @param context
     */
    public AppDatabase(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Map.Entry<String, Map<String, String>> table : databaseTables.entrySet()) {
            String tableName = table.getKey();
            Map<String, String> tableColumns = table.getValue();

            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " ( ";

            String[] columns = new String[tableColumns.size()];
            for (Map.Entry<String, String> column : tableColumns.entrySet()) {
                String columnName = column.getKey();
                String columnType = column.getValue();

                Log.d("DEBUG", columnName + " " + columnType);
                //createTableQuery.concat(columnName + " " + columnType + ", ");
                createTableQuery += columnName + " " + columnType + ", ";
            }

            createTableQuery = createTableQuery.substring(0, createTableQuery.lastIndexOf(','));

            //createTableQuery.concat(")");
            createTableQuery += ");";
            Log.d("DEBUG", createTableQuery);
            db.execSQL(createTableQuery);
        }

        for (String seed : seeds) {
            Log.d("DEBUG", seed);
            db.execSQL(seed);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Map.Entry<String, Map<String, String>> table : databaseTables.entrySet()) {
            String tableName = table.getKey();
            String createTableQuery = "DROP TABLE IF EXISTS " + tableName + ";";
            db.execSQL(createTableQuery);
        }
        onCreate(db);
    }
}
