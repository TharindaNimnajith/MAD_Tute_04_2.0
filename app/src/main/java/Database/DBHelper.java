package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UserMaster.Users.TABLE_NAME + " (" +
                        UserMaster.Users._ID + " INTEGER PRIMARY KEY," +
                        UserMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                        UserMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //
    }

    public void addInfo(String userName, String password) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserMaster.Users.COLUMN_NAME_USERNAME, userName);
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD, password);

        long newRowId = sqLiteDatabase.insert(UserMaster.Users.TABLE_NAME, null, values);
    }

    public Cursor readAllInfo() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UserMaster.Users.COLUMN_NAME_USERNAME,
                UserMaster.Users.COLUMN_NAME_PASSWORD
        };

        //String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " = ?";
        //String[] selectionArgs = { "username" };

        String sortOrder = UserMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                UserMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List usernames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_PASSWORD));

            usernames.add(username);
            passwords.add(password);
        }

        return cursor;
    }

    public boolean validate(String u, String p) {
        //String query = "SELECT * FROM " + UserMaster.Users.TABLE_NAME;
        //Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UserMaster.Users.COLUMN_NAME_USERNAME,
                UserMaster.Users.COLUMN_NAME_PASSWORD
        };

        //String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " = ?";
        //String[] selectionArgs = { "username" };

        //String sortOrder = UserMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                UserMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List usernames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_PASSWORD));

            usernames.add(username);
            passwords.add(password);
        }

        cursor.close();

        for (int i = 0; i < usernames.size(); i++) {
            if (u.equals(usernames.get(i)) && p.equals(passwords.get(i))) {
                return true;
            }
        }

        return false;
    }

    public boolean deleteInfo(String un) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {un};

        int rows = sqLiteDatabase.delete(UserMaster.Users.TABLE_NAME, selection, selectionArgs);

        return rows != 0;
    }

    public boolean updateInfo(String un, String pw) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserMaster.Users.COLUMN_NAME_PASSWORD, pw);

        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {un};

        int rows = sqLiteDatabase.update(
                UserMaster.Users.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs
        );

        return rows != 0;
    }
}
