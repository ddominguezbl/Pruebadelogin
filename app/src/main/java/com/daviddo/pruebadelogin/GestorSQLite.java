package com.daviddo.pruebadelogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GestorSQLite extends SQLiteOpenHelper {
    // Objeto de la bbdd que se usa en todos los metodos de esta clase
    SQLiteDatabase objetoBBDD;

    // Constructor, que no se toca,
    public GestorSQLite(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(contexto, nombre, factory, version);
        objetoBBDD = getWritableDatabase();
    }


    public void guardarUsuario(Usuario j) {
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("nombre", j.getNombre());
        nuevoRegistro.put("password", j.getPassword());
        objetoBBDD.insert("usuarios", null, nuevoRegistro);
    }



    public boolean verSiYaExisteUsuarioConUnNombre(String nombrebuscado) {
        // Ejemplo con cursor SIN parametros;
        String sql = "SELECT * FROM usuarios where nombre = '" + nombrebuscado + "'";

        Cursor c = objetoBBDD.rawQuery(sql, null);

        // Se mueve al primer regiustro y se entra en el if si hay al menos un registro en el cursor
        if (c.moveToFirst()) {
            // SI SE LLEGA AQUI ES QUE LA CONSULTA HA DEVUELTO RESGISTROS, ERGO EL USUARIO YA EXISTE EN LA BBDD
           return true;
        }
        return false;
    }


    public boolean verSiYaExisteUsuarioConUnNombreYPassword(String nombrebuscado, String passwordbuscada) {
        // Ejemplo con cursor SIN parametros;
        String sql = "SELECT * FROM usuarios where nombre = '" + nombrebuscado + "'";

        Cursor c = objetoBBDD.rawQuery(sql, null);

        // Se mueve al primer regiustro y se entra en el if si hay al menos un registro en el cursor
        if (c.moveToFirst()) {
            // SI SE LLEGA AQUI ES QUE LA CONSULTA HA DEVUELTO RESGISTROS, ERGO EL USUARIO YA EXISTE EN LA BBDD

            String password = c.getString(c.getColumnIndex("password"));  // se puede acceder al campo por su nombre
            if (password.equals(passwordbuscada)) {
                return true;
            }
        }
        return false;
    }


    public ArrayList<Usuario> leerTodosLosUsuarios() {

        // creo un arraylist para rellenarlo
        ArrayList<Usuario> listaUsuario = new ArrayList<>();

        // Ejemplo con cursor SIN parametros;
        String sql = "SELECT * FROM usuarios";
        Cursor c = objetoBBDD.rawQuery(sql, null);

        // Se mueve al primer regiustro y se entra en el if si hay al menos un registro en el cursor
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no hay más registros
            // Cada campo se identifica por su ordinal, y se debe recuperar con el metodo adecuado
            // a su tipo (getString(), getInt(), etc)
            do {

                String nombre = c.getString(c.getColumnIndex("nombre"));  // se puede acceder al campo por su nombre
                String password = c.getString(c.getColumnIndex("password"));  // se puede acceder al campo por su nombre

                // hacer con los datos leidos lo que haga falta
                Usuario u = new Usuario(nombre, password);
                listaUsuario.add(u);
            } while (c.moveToNext());                    // dará false cuando al moverse al siguiente registro se salga del cursor
        }
        return listaUsuario;
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE usuarios (rowid INTEGER , nombre TEXT, password TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE ranking (rowid INTEGER, nombre TEXT, puntuacion TEXT)";
        db.execSQL(sql);
    }
    //OnUpdate()  se ejecuta cuando se adviete que existe una nueva version de la base de datos  
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        // paso de una vesion a otra
    }






}