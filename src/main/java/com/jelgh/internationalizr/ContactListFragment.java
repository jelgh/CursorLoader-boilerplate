package com.jelgh.internationalizr;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListFragment;
import com.jelgh.internationalizr.contact.NativeApi;


public class ContactListFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	protected SimpleCursorAdapter mAdapter;
	protected ListView mListView;
	protected View mProgressContainer;
	protected NativeApi mNativeApi;
	protected LoaderManager mLoaderManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.contact_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		MainActivity activity = (MainActivity) getActivity();

		mNativeApi = NativeApi.getInstance(activity);

		String[] dataColumns = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
		int[] viewIds = {R.id.txtName};
		mAdapter = new ContactAdapter(activity, R.layout.contact_item, null, dataColumns, viewIds, 0);
		setListAdapter(mAdapter);


		mListView = (ListView) activity.findViewById(android.R.id.list);
		mProgressContainer = activity.findViewById(R.id.progress_spinner);

		getLoaderManager().initLoader(0, null, this);
		setListShown(false);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return mNativeApi.getContactsWithPhoneNumber();
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// The asynchronous load is complete and the data is available.
		mAdapter.swapCursor(cursor);
		setListShown(true);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Remove any references to the old data if data is unavailable
		mAdapter.swapCursor(null);
	}

	protected void restartCursorLoader() {
		mLoaderManager.restartLoader(0, null, this);
	}

	protected void initializeCursorLoader() {
		mLoaderManager.initLoader(0, null, this);
	}

	public void setListShown(boolean show) {
		if (show) {
			mListView.setVisibility(View.VISIBLE);
			mProgressContainer.setVisibility(View.GONE);
		} else {
			mListView.setVisibility(View.GONE);
			mProgressContainer.setVisibility(View.VISIBLE);
		}
	}

}
