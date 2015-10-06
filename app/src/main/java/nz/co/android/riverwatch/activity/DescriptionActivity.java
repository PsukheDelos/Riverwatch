package nz.co.android.riverwatch.activity;


import nz.co.android.cowseye2.R;
import nz.co.android.riverwatch.fragments.DescriptionFragment;
import nz.co.android.riverwatch.fragments.NavigationDrawerFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import android.widget.TextView;
import android.widget.Toast;

/**
 * The activity for inputting the description for a pollution event
 *
 * This will allow the user to enter a description and select appropriate tags
 *
 * @author lanemitc (edited by WaiApp)
 *
 */
public class DescriptionActivity extends AbstractSubmissionActivity {

	private String imageDescription;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupDrawer();
		setupUI();
	}

	private void setupDrawer() {
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		mNavigationDrawerFragment.showDrawerToggle(false);
	}

	/* Sets up the UI */
	@Override
	protected void setupUI() {
		super.setupUI();

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	}

	@Override
	protected void nextActivety() {
		if (!f.hasDescription()) {
			Toast.makeText(DescriptionActivity.this,
					getString(R.string.pleaseEnterDescription),
					Toast.LENGTH_LONG).show();
		}

		// description has been entered and recognized by user and this
		// will move the application onto the recorfd location activity
		else {
			imageDescription = f.getText();
			submissionEventBuilder.setImageDescription(imageDescription);

				Intent intent = new Intent(DescriptionActivity.this,
						RecordLocationActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				intent.setFlags(IntentCompat.FLAG_ACTIVITY_TASK_ON_HOME
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
//			}
		}
	}

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private boolean justopened = true;
	private DescriptionFragment f;

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments

		if (justopened) {
			// the activity has just been opened, so we dont want to navigate
			// away
			justopened = false;

			FragmentManager fragmentManager = getSupportFragmentManager();
			f = DescriptionFragment.newInstance(position);

			fragmentManager.beginTransaction()
					.replace(R.id.container, f, "tag_Select_Image_frag")
					.commit();

		} else {
			// a drawer button has been selected sonavigate to that page
			myApplication.deleteImage(submissionEventBuilder.getImagePath()
					.toString());

			Intent i = new Intent(DescriptionActivity.this,
					MainScreenActivity.class);

			// stops the go back buton taking us here
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// tells MainScreenActivity which item was selected
			i.putExtra("selectedItem", position);

			startActivity(i);
		}
	}

	@Override
	public void onResume(){
		super.onResume();
		String title = null;

		title = "Nitrite";
		TextView nitrite = (TextView)f.getActivity().findViewById(R.id.nitrite_value);
		nitrite.setText(title + ": " + String.format("%.4f", submissionEventBuilder.submissionEvent.nitrite));
		title = "Nitrate";
		TextView nitrate = (TextView)f.getActivity().findViewById(R.id.nitrate_value);
		nitrate.setText(title + ": " + String.format("%.4f", submissionEventBuilder.submissionEvent.nitrate));
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
