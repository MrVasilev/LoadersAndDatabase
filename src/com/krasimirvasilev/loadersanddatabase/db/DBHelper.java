package com.krasimirvasilev.loadersanddatabase.db;

import java.util.ArrayList;
import java.util.List;

import com.krasimirvasilev.loadersanddatabase.Contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Singleton class which will work with the local database.
 * 
 * @author krasimir.vasilev
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "db_contacts";
	private static final String TABLE_NAME = "contacts";

	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PHONE_NUMBER = "phone_number";

	private static DBHelper instance;

	private DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public static DBHelper getInstance(Context context) {

		if (instance == null)
			instance = new DBHelper(context);

		return instance;
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

		Contact contact = new Contact(contactId, name, phoneNumber);

		return contact;
	}

	/**
	 * Update existing Contact row in database
	 * 
	 * @param contact
	 * @return
	 */
	public void updateContact(Contact contact) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_NAME, contact.getName());
		values.put(KEY_PHONE_NUMBER, contact.getPhoneNumber());

		db.update(TABLE_NAME, values, KEY_ID + "= ?", new String[] { String.valueOf(contact.getId()) });

		db.close();
	}

	/**
	 * Delete existing Contact from database
	 * 
	 * @param contact
	 */
	public void deleteContact(Contact contact) {

		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_NAME, KEY_ID + "= ?", new String[] { String.valueOf(contact.getId()) });

		db.close();
	}

	/**
	 * Get all contacts from database
	 * 
	 * @return
	 */
	public List<Contact> getAllContacts() {

		List<Contact> allContacts = new ArrayList<Contact>();

		String selectQuery = "SELECT * FROM " + TABLE_NAME;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {

			do {

				Contact contact = new Contact();
				int contactId = Integer.parseInt(cursor.getString(0));
				String name = cursor.getString(1);
				String phoneNumber = cursor.getString(2);

				contact.setId(contactId);
				contact.setName(name);
				contact.setPhoneNumber(phoneNumber);

				allContacts.add(contact);

			} while (cursor.moveToNext());
		}

		return allContacts;
	}

	/**
	 * Get numbers of all contacts in database
	 * 
	 * @return
	 */
	public int getContactsCount() {

		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_NAME;

		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.close();

		return cursor.getCount();
	}

}
