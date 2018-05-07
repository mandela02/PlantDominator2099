package map.wayne.com.plantdominator2099.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import map.wayne.com.plantdominator2099.data.model.TreeData;

public class TreeDataSource extends DatabaseHelper {
    public TreeDataSource(Context context) {
        super(context);
    }
    public List<TreeData> getAllTree() {
        List<TreeData> result = new ArrayList<>();
        String[] Column = {TreeDatabase.TreeEntry._ID,
            TreeDatabase.TreeEntry.COLUMN_NAME,
            TreeDatabase.TreeEntry.COLUMN_LAT,
            TreeDatabase.TreeEntry.COLUMN_ID,
            TreeDatabase.TreeEntry.COLUMN_LONG,
            TreeDatabase.TreeEntry.COLUMN_SIZE,
            TreeDatabase.TreeEntry.COLUMN_STATUS,
            TreeDatabase.TreeEntry.COLUMN_DES
        };
        SQLiteDatabase database = getWritableDatabase();
        String orderBY = TreeDatabase.TreeEntry.COLUMN_ID;
        Cursor cursor = database.query(TreeDatabase.TreeEntry.TABLE_NAME,
            Column,
            null,
            null,
            null,
            null,
            orderBY
        );
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    TreeData contacts = new TreeData(cursor);
                    result.add(contacts);
                }
                while (cursor.moveToNext());
                return result;
            }
        } catch (Exception e) {
            return null;
        } finally {
            cursor.close();
            database.close();
        }
        return null;
    }

    public long insertTree(TreeData tree) {
        if (tree != null) {
            SQLiteDatabase database = getWritableDatabase();
            ContentValues values = new ContentValues();
            try {
                values.put(TreeDatabase.TreeEntry.COLUMN_ID, tree.getId());
                values.put(TreeDatabase.TreeEntry.COLUMN_NAME, tree.getTreeName());
                values.put(TreeDatabase.TreeEntry.COLUMN_LONG, tree.getLong());
                values.put(TreeDatabase.TreeEntry.COLUMN_LAT, tree.getLat());
                values.put(TreeDatabase.TreeEntry.COLUMN_SIZE, tree.getSize());
                values.put(TreeDatabase.TreeEntry.COLUMN_STATUS, tree.getStatus());
                values.put(TreeDatabase.TreeEntry.COLUMN_DES, tree.getDes());
                return database.insert(TreeDatabase.TreeEntry.TABLE_NAME, null, values);
            } catch (Exception e) {
                return -1;
            } finally {
                database.close();
            }
        }
        return -1;
    }

   public boolean isIndb(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String whereClause = TreeDatabase.TreeEntry.COLUMN_ID + " =?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor cursor = database.query(TreeDatabase.TreeEntry.TABLE_NAME,
            null,
            whereClause,
            whereArgs,
            null,
            null,
            null);
        try {
            return cursor.getCount() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            cursor.close();
            database.close();
        }
    }
}
