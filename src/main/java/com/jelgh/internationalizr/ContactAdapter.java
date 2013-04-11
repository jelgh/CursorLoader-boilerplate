package com.jelgh.internationalizr;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jelgh.internationalizr.contact.Contact;
import com.jelgh.internationalizr.contact.NativeApi;

public class ContactAdapter extends SimpleCursorAdapter {

	private Context mContext;
	private NativeApi mNativeApi;

	public ContactAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		mContext = context;
		mNativeApi = NativeApi.getInstance(mContext);
	}


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		//To change body of implemented methods use File | Settings | File Templates.
	}




	@Override
	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder viewHolder;

		if (view == null) {
			LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(R.layout.contact_item, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.txtName = (TextView) view.findViewById(R.id.txtName);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.listPosition = position;

		Cursor cursor = getCursor();
		cursor.moveToPosition(position);

		Contact contact = NativeApi.getContactFromCursor(cursor);
		viewHolder.txtName.setText(contact.displayName);

		return view;
	}



	static class ViewHolder {
		int listPosition;
		TextView txtName;
	}
}
