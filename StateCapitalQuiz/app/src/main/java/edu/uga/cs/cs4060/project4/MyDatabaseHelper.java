package edu.uga.cs.cs4060.project4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Singleton class used to help with database calls.
public class MyDatabaseHelper extends SQLiteOpenHelper {
    final String TAG = "MyDatabaseHelper";
    private static final String DB_NAME = "MyDatabase.db";
    private static final int DB_VERSION = 1;
    private static MyDatabaseHelper helperInstance;

    // state table
    public static final String TABLE_STATE = "state";
    public static final String STATE_COLUMN_ID = "_id";
    public static final String STATE_COLUMN_STATE = "state";
    public static final String STATE_COLUMN_CAPITAL = "capital";
    public static final String STATE_COLUMN_CITY1 = "city1";
    public static final String STATE_COLUMN_CITY2 = "city2";
    public static final String CREATE_STATE =
            "CREATE TABLE " +  TABLE_STATE + "("
                    + STATE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + STATE_COLUMN_STATE + " TEXT,"
                    + STATE_COLUMN_CAPITAL  +" TEXT,"
                    + STATE_COLUMN_CITY1 + " TEXT,"
                    + STATE_COLUMN_CITY2 + " TEXT"
                    + ")";

    // result table
    public static final String TABLE_RESULT = "result";
    public static final String RESULT_COLUMN_ID = "_id";
    public static final String RESULT_COLUMN_DATE = "date";
    public static final String RESULT_COLUMN_SCORE = "score";
    public static final String CREATE_RESULT =
            "CREATE TABLE " + TABLE_RESULT  + "("
                    + RESULT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + RESULT_COLUMN_DATE + " TEXT, "
                    + RESULT_COLUMN_SCORE + " TEXT "
                    + ")";

    // question table
    public static final String TABLE_QUESTIONS = "questions";
    public static final String QUESTIONS_COLUMN_ID = "_id";
    public static final String QUESTIONS_COLUMN_RESULT_ID = "result_id";
    public static final String QUESTIONS_COLUMN_STATE_ID = "state_id";
    public static final String QUESTIONS_COLUMN_ANSWER = "answer";
    public static final String QUESTIONS_COLUMN_CORRECTNESS = "correctness";
    public static final String CREATE_QUESTIONS =
            "CREATE TABLE " + TABLE_QUESTIONS  + "("
                    + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUESTIONS_COLUMN_RESULT_ID + " INTEGER,"
                    + QUESTIONS_COLUMN_STATE_ID + " INTEGER,"
                    + QUESTIONS_COLUMN_ANSWER + " TEXT,"
                    + QUESTIONS_COLUMN_CORRECTNESS + " TEXT" + ")";

    private MyDatabaseHelper(Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    // Access method to the single instance of the class
    public static synchronized MyDatabaseHelper getInstance(Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new MyDatabaseHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STATE);
        sqLiteDatabase.execSQL(CREATE_QUESTIONS);
        sqLiteDatabase.execSQL(CREATE_RESULT);
        Log.d(TAG,"created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL( "drop table if exists capital" );
        onCreate( sqLiteDatabase );
        Log.d(TAG,"upgraded");
    }
}
