package com.krasimirvasilev.loadersanddatabase;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.krasimirvasilev.loadersanddatabase.db.DBHelper;

public class MainActivity extends Activity {

	public static final int LOADER_ID = 1;

	public EditText nameEditText;
	public EditText phoneEditText;
	public ListView contactsListView;

	private DBHelper dbHelper;
	private ContactsAdapter adapter;
	private List<Contact> allContacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		nameEditText = (EditText) findViewById(R.id.nameEditText);
		phoneEditText = (EditText) findViewById(R.id.phoneEditText);
		contactsListView = (ListView) findViewById(R.id.contactsListView);
	}

	@Override
	protected void onResume() {
		super.onResume();

		dbHelper = DBHelper.getInstance(this);

		allContacts = dbHelper.getAllContacts();

		if (allContacts == null) {
			allContacts = new ArrayList<Contact>();
		}

		adapter = new ContactsAdapter(this, allContacts);
		contactsListView.setAdapter(adapter);
	}

	/**
	 * Add Contact into database after click on "Add Contact" button
	 * 
	 * @param view
	 */
	public void addContact(View view) {

		if (view.getId() == R.id.addButton) {

			String enteredName = nameEditText.getText().toString().trim();
			String enteredPhone = phoneEditText.getText().toString().trim();

			if (!TextUtils.isEmpty(enteredName) && !TextUtils.isEmpty(enteredPhone)) {

				Contact contact = new Contact(enteredName, enteredPhone);

				dbHelper.addContact(contact);

				adapter.setData(dbHelper.getAllContacts());
				adapter.notifyDataSetChanged();

				nameEditText.setText("");
				phoneEditText.setText("");

			} else {
				Toast.makeText(this, "Name and Phone are required both!", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
