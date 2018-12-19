package edu.uga.cs.cs4060.project4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

//Used to access database
public class DatabaseCommand {
    final String TAG = "DatabaseCommand";
    public SQLiteDatabase db;
    public SQLiteOpenHelper dbHelper;

    /**
     * this function is to return the table in states
     * @param tableName: name of the table
     * @return a result set of ArrayList
     */
    public ArrayList<String[]> readTable(String tableName) {
        String selectQuery = "SELECT  * FROM " + tableName;
        ArrayList<String[]> resultSet = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String[] s = new String[cursor.getColumnCount()];
                for(int j = 0; j < cursor.getColumnCount(); j++){
                    s[j] = cursor.getString(j);
                }
                resultSet.add(s);
                cursor.moveToNext();
            }
        } finally {
            try { cursor.close(); } catch (Exception ignore) {}
        }
        return resultSet;
    }
    public ArrayList<String[]> readTableQuery(String query) {
        String selectQuery = query;
        ArrayList<String[]> resultSet = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String[] s = new String[cursor.getColumnCount()];
                for(int j = 0; j < cursor.getColumnCount(); j++){
                    s[j] = cursor.getString(j);
                }
                resultSet.add(s);
                cursor.moveToNext();
            }
        } finally {
            try { cursor.close(); } catch (Exception ignore) {}
        }
        return resultSet;
    }

    public String getLastPrimaryKey(String tableName) {
        String selectQuery = "SELECT  * FROM " + tableName;
        ArrayList<String[]> resultSet = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String id = "0";
        try {
            cursor.moveToLast();
                String[] s = new String[cursor.getColumnCount()];
                id = cursor.getString(0);


        }catch(Exception e){
            System.out.print(e.getMessage());
        } finally {
            try {
                cursor.close();
            } catch (Exception ignore) {
            }
        }
        return id;
    }

    public void importStateSCV(Context context, DatabaseCommand database){
        Cursor cursor = database.db.rawQuery("SELECT  * FROM " + "state", null);
        if (cursor.getCount() > 0) {
            Log.d(TAG, "doInBackground: data has already inserted before.");
        } else {
            ReadSCV read = new ReadSCV(context, R.raw.state_capitals);
            ArrayList<String[]> data = read.getData();
            for (int i = 1; i < data.size(); i++) {
                Log.d(TAG, "Insert " + i + " row");
                ContentValues values = new ContentValues();
                values.put(MyDatabaseHelper.STATE_COLUMN_STATE, data.get(i)[0]);
                values.put(MyDatabaseHelper.STATE_COLUMN_CAPITAL, data.get(i)[1]);
                values.put(MyDatabaseHelper.STATE_COLUMN_CITY1, data.get(i)[2]);
                values.put(MyDatabaseHelper.STATE_COLUMN_CITY2, data.get(i)[3]);
                database.db.insert(MyDatabaseHelper.TABLE_STATE, null, values);
            }
            Log.d(TAG, "doInBackground: data first time insert.");
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     *  open database
     * @param currentContext the current context
     */
    public void open(Context currentContext) {
        dbHelper = MyDatabaseHelper.getInstance(currentContext);
        db = dbHelper.getWritableDatabase();
        Log.d(TAG, "MyDatabase: db open.");
    }

    /**
     * close database
     */
    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
            Log.d(TAG, "MyDatabase: db closed.");
        }
    }
}
