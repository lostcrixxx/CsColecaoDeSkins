package br.com.envolvedesenvolve.cscolecaodeskins.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;

import br.com.envolvedesenvolve.cscolecaodeskins.Utils;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHelper.class.getName();

    // Versão atual do banco de dados
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";

    private final Context context;

    public static final String TABLE_SKINS = "skins";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_HASH_NAME = "hash_name";
    public static final String COLUMN_CLASSID = "classid";
    public static final String COLUMN_INSTANCEID = "instanceid";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_IMAGE_LARGE = "image_large";
    public static final String COLUMN_WEAR = "wear";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CREATED_AT = "created_at";

    private static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SKINS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_HASH_NAME + " TEXT, " +
                    COLUMN_CLASSID + " TEXT, " +
                    COLUMN_INSTANCEID + " TEXT, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_IMAGE_LARGE + " TEXT, " +
                    COLUMN_WEAR + " TEXT, " +
                    COLUMN_TYPE + " TEXT, " +
                    COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKINS);
            onCreate(db); // ?

            Log.e(TAG, "passed copyDatabaseToApp() new database.db");
            try {
                Utils.getInstance().copyDatabaseToApp(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
