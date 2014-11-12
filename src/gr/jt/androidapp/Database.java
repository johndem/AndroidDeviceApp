package gr.jt.androidapp;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database {

	public Database(Context c){
		ourContext = c;
	}
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DATE = "date";
	public static final String KEY_INT = "internal";
	public static final String KEY_EXT = "external";
	
	private static final String DATABASE_NAME = "SQLite";
	private static final String DATABASE_TABLE = "History";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
		

		// create table
		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + 
						KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
						KEY_DATE + " TEXT NOT NULL, " +
						KEY_INT + " TEXT NOT NULL, " +
						KEY_EXT + " TEXT NOT NULL);"
					   );
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
	}

	
	// open database
	public Database open() {
		
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
		
	}
	

	// close database
	public void close() {
		
		ourHelper.close();
		
	}
	
	
	// add an entry(date, internal memory, external memory)
	public long createEntry(String date, String internal, String external) {
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_DATE, date);
		cv.put(KEY_INT, internal);
		cv.put(KEY_EXT, external);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
		
	}
	
	
	// get data from database to be displayed in app
	public ArrayList<String> getData() {
		
		String[] columns = new String[] { KEY_ROWID, KEY_DATE, KEY_INT, KEY_EXT};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		ArrayList<String> result = new ArrayList<String>();
		String temp;
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iDate = c.getColumnIndex(KEY_DATE);
		int iInt = c.getColumnIndex(KEY_INT);
		int iExt = c.getColumnIndex(KEY_EXT);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			temp = c.getString(iRow) + " " + c.getString(iDate) + " " + c.getString(iInt) + " " + c.getString(iExt);
			result.add(temp);
		}
		
		return result; // return id of entry, date, internal memory and external memory
		
	}
	
}
