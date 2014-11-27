package com.krasimirvasilev.loadersanddatabase;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactsAdapter extends BaseAdapter {

	private List<Contact> contacts;
	private LayoutInflater inflater;

	public ContactsAdapter(Context context, List<Contact> contacts) {
		this.contacts = contacts;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {

		return contacts.size();
	}

	@Override
	public Contact getItem(int position) {

		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public void add(Contact contact) {
		if (contact != null)
			contacts.add(contact);
	}

	public void remove(Contact contact) {
		if (contact != null)
			contacts.remove(contact);
	}

	public void setData(List<Contact> contacts) {
		if (contacts != null)
			this.contacts = contacts;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		ViewHolder viewHolder;

		if (view == null) {

			view = inflater.inflate(R.layout.contact_listview_item, parent, false);
			viewHolder = new ViewHolder();

			viewHolder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
			viewHolder.phoneTextView = (TextView) view.findViewById(R.id.phoneTextView);

			view.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) view.getTag();
		}

		Contact contact = contacts.get(position);

		viewHolder.nameTextView.setText(contact.getName());
		viewHolder.phoneTextView.setText(contact.getPhoneNumber());

		return view;
	}

	private class ViewHolder {

		private TextView nameTextView;
		private TextView phoneTextView;

	}

}
