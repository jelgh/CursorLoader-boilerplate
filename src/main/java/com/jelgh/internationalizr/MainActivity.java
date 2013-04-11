package com.jelgh.internationalizr;

import android.os.Bundle;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;


public class MainActivity extends SherlockFragmentActivity {


	private static final String MAIN_FRAGMENT = "main-fragment";

	@Override
	public void onCreate(Bundle savedData) {
		super.onCreate(savedData);

		this.setContentView(R.layout.main);

		if (savedData == null) {

			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.fragment_container, new ContactListFragment())
					.commit();
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.options, menu);
		return (super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
				return (true);

			case R.id.about:
				Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}
}