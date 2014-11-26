package com.krasimirvasilev.loadersanddatabase.db;

import com.krasimirvasilev.loadersanddatabase.Contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "db_contacts";
	private static final String TABLE_NAME = "contacts";

	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PHONE_NUMBER = "phone_number";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
				+ " TEXT," + KEY_PHONE_NUMBER + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(db);
	}

	/**
	 * Add Contact into database
	 * 
	 * @param contact
	 */
	public void addContact(Contact contact) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_NAME, contact.getName());
		values.put(KEY_PHONE_NUMBER, contact.getPhoneNumber());

		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close();
	}

	/**
	 * Get Contact from database
	 * 
	 * @param id
	 * @return
	 */
	public Contact getContact(int id) {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NAME, KEY_PHONE_NUMBER }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		int contactId = Integer.parseInt(cursor.getString(0));
		String name = cursor.getString(1);
		String phoneNumber = cursor.getString(2);

		Contact contact = new Contact(name, phoneNumber);

		return contact;
	}

}
