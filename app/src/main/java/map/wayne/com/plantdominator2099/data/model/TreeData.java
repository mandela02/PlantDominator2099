package map.wayne.com.plantdominator2099.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import map.wayne.com.plantdominator2099.data.database.TreeDatabase;

public class TreeData implements Parcelable {
    private int mId;
    private String mTreeName;
    private double mLong;
    private double mLat;
    private int mStatus;
    private boolean mChoosen;
    private int mSize;
    private String mDes;
    private int mDbId;
    /*thêm lượng nước.
    * tần suát tưới.
    * private list<history> mHistory
    *       ngày tưới
    *       người tưới
    *       stt
    *
    * */


    public TreeData(int id, String treeName, double lat, double aLong, int status,
                    int size, String des) {
        mId = id;
        mTreeName = treeName;
        mLong = aLong;
        mLat = lat;
        mStatus = status;
        mSize = size;
        mDes = des;
    }
    public TreeData(Cursor cursor) {
        this.mStatus = cursor.getInt(cursor.getColumnIndex(TreeDatabase.TreeEntry.COLUMN_STATUS));
        this.mId = cursor.getInt(cursor.getColumnIndex(TreeDatabase.TreeEntry.COLUMN_ID));
        this.mSize = cursor.getInt(cursor.getColumnIndex(TreeDatabase.TreeEntry.COLUMN_SIZE));
        this.mTreeName =
            cursor.getString(cursor.getColumnIndex(TreeDatabase.TreeEntry.COLUMN_NAME));
        this.mDes = cursor.getString(cursor.getColumnIndex(TreeDatabase.TreeEntry.COLUMN_DES));
        this.mLong = cursor.getDouble(cursor.getColumnIndex(TreeDatabase.TreeEntry
            .COLUMN_LONG));
        this.mLat = cursor.getDouble(cursor.getColumnIndex(TreeDatabase.TreeEntry
            .COLUMN_LAT));
        this.mDbId = cursor.getInt(cursor.getColumnIndex(TreeDatabase.TreeEntry._ID));
    }

    protected TreeData(Parcel in) {
        mId = in.readInt();
        mTreeName = in.readString();
        mLong = in.readDouble();
        mLat = in.readDouble();
        mStatus = in.readInt();
        mChoosen = in.readByte() != 0;
        mSize = in.readInt();
        mDes = in.readString();
        mDbId = in.readInt();
    }

    public static final Creator<TreeData> CREATOR = new Creator<TreeData>() {
        @Override
        public TreeData createFromParcel(Parcel in) {
            return new TreeData(in);
        }

        @Override
        public TreeData[] newArray(int size) {
            return new TreeData[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public boolean isChoosen() {
        return mChoosen;
    }

    public void setChoosen(boolean choosen) {
        mChoosen = choosen;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }



    public String getTreeName() {
        return mTreeName;
    }

    public void setTreeName(String treeName) {
        mTreeName = treeName;
    }

    public double getLong() {
        return mLong;
    }

    public void setLong(double aLong) {
        mLong = aLong;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public String getDes() {
        return mDes;
    }

    public void setDes(String des) {
        mDes = des;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTreeName);
        dest.writeDouble(mLong);
        dest.writeDouble(mLat);
        dest.writeInt(mStatus);
        dest.writeByte((byte) (mChoosen ? 1 : 0));
        dest.writeInt(mSize);
        dest.writeString(mDes);
        dest.writeInt(mDbId);
    }
}
