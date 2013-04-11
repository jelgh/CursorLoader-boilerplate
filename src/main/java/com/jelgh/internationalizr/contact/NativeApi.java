package com.jelgh.internationalizr.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.content.CursorLoader;


public class NativeApi {


	public static final String[] PHONE_PROJECTION = new String[]{
			Phone._ID,
			Phone.CONTACT_ID,
			Phone.RAW_CONTACT_ID,
			Phone.LOOKUP_KEY,
			Phone.DISPLAY_NAME,
			Phone.STARRED,
			Phone.TYPE,
			Phone.LABEL,
			Phone.NUMBER,
			Phone.NORMALIZED_NUMBER
	};
	private static NativeApi mInstance;
	private final Context mContext;
	private final ContentResolver mContentResolver;

	private NativeApi(Context context) {
		mContext = context;
		mContentResolver = mContext.getContentResolver();
	}

	public static NativeApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new NativeApi(context.getApplicationContext());
		}
		return mInstance;
	}

	public static Contact getContactFromCursor(Cursor cursor) {
		if (cursor != null && (cursor.isBeforeFirst() || cursor.isAfterLast())) {
			return null;
		}

		Contact contact = new Contact();
		contact.contactId = cursor.getString(cursor.getColumnIndex(Phone.CONTACT_ID));
		contact.rawContactId = cursor.getString(cursor.getColumnIndex(Phone.RAW_CONTACT_ID));
		contact.lookupKey = cursor.getString(cursor.getColumnIndex(Phone.LOOKUP_KEY));
		contact.displayName = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
		contact.starred = (cursor.getInt(cursor.getColumnIndex(Phone.STARRED)) == 1);
		contact.type = cursor.getString(cursor.getColumnIndex(Phone.TYPE));
		contact.label = cursor.getString(cursor.getColumnIndex(Phone.LABEL));
		contact.number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
		contact.normalizedNumber = cursor.getString(cursor.getColumnIndex(Phone.NORMALIZED_NUMBER));

		return contact;
	}

	public CursorLoader getContactsWithPhoneNumber() {

		String[] projection = PHONE_PROJECTION;
		String where = Phone.NUMBER + " IS NOT NULL";
		String sortBy = Phone.CONTACT_ID + " ASC";

		return new CursorLoader(mContext, Phone.CONTENT_URI, projection, where, null, sortBy);
	}

}
