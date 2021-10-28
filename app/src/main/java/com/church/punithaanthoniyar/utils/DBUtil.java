package com.church.punithaanthoniyar.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.church.punithaanthoniyar.BuildConfig;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBUtil extends SQLiteOpenHelper {

    private final String DB_PATH;
    private final String DB_NAME;
    private final Context myContext;
    private SQLiteDatabase db;

    public DBUtil(Context ctx, String mDBName) {
        super(ctx, mDBName, null, 1);
        this.myContext = ctx;
        this.DB_NAME = mDBName;
        this.DB_PATH = myContext.getDatabasePath(DB_NAME).getPath();
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
    }

    public void insertSQL(String tbl, String columns, String content) {
        String sql = "insert into " + tbl + "(" + columns + ") values("
                + content + ")";
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }

        db.execSQL(sql);
    }

    public void insertSQLWithCond(String tbl, String columns, String content, String condVal) {
        String sql = "insert into " + tbl + "(" + columns + ") Select " + content
                + " WHERE NOT EXISTS(Select 1 From " + tbl + " Where UID='" + condVal + "')";
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }

        db.execSQL(sql);
    }

    public void multiInsert(String sql) {
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }

        db.execSQL(sql);
    }

    public void updateSQL(String sql) {
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }

        db.execSQL(sql);

    }

    public void deleteSQL(String tbl, String where, boolean all) {
        String sql;
        if (all)
            sql = "delete from " + tbl;
        else
            sql = "delete from " + tbl + " where " + where;
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }

        db.execSQL(sql);
    }

    public void executeQ(String sql) {
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }

        db.execSQL(sql);

    }

    public Cursor selectSQL(String sql) {
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }

        return db.rawQuery(sql, null);
    }

    public Cursor selectSQL(String sql, String[] args) {
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql + args);
        }

        return db.rawQuery(sql, args);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    public void closeDB() {
        if (db != null)
            this.db.close();
    }

    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
        }
    }

    public boolean isDbNullOrClosed() {
        return db == null || !db.isOpen();
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     * <p>
     * Last Modified Mansoor
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        return myContext.getDatabasePath(DB_NAME).exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(DB_PATH);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        db = SQLiteDatabase.openDatabase(DB_PATH, null,
                    SQLiteDatabase.OPEN_READWRITE);
    }

}
