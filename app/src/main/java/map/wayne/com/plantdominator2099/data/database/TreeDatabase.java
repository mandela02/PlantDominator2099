package map.wayne.com.plantdominator2099.data.database;

import android.provider.BaseColumns;

public class TreeDatabase {
    public class TreeEntry implements BaseColumns {
        public static final String TABLE_NAME = "tbl_tree";
        public static final String COLUMN_NAME = "column_name";
        public static final String COLUMN_ID = "column_id";
        public static final String COLUMN_SIZE = "column_size";
        public static final String COLUMN_LONG = "column_long";
        public static final String COLUMN_LAT = "column_lat";
        public static final String COLUMN_STATUS = "column_status";
        public static final String COLUMN_DES = "column_des";
    }
}
