package map.wayne.com.plantdominator2099.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "tree.db";
    private final static int DB_VERSION = 1;
    private final static String COMMAND_CREATE_TABLE =
        " CREATE TABLE " + TreeDatabase.TreeEntry.TABLE_NAME
            + "("
            + TreeDatabase.TreeEntry._ID
            + " INTEGER PRIMARY KEY NOT NULL, "
            + TreeDatabase.TreeEntry.COLUMN_NAME
            + " TEXT, "
            + TreeDatabase.TreeEntry.COLUMN_ID
            + " INTEGER, "
            + TreeDatabase.TreeEntry.COLUMN_DES
            + " TEXT, "
            + TreeDatabase.TreeEntry.COLUMN_SIZE
            + " INTEGER, "
            + TreeDatabase.TreeEntry.COLUMN_STATUS
            + " INTEGER, "
            + TreeDatabase.TreeEntry.COLUMN_LAT
            + " DOUBLE, "
            + TreeDatabase.TreeEntry.COLUMN_LONG
            + " DOUBLE) ";
    private final static String COMMAND_DELETE_TABLE =
        "DROP TABLE " + TreeDatabase.TreeEntry.COLUMN_NAME;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE);
    }
}
