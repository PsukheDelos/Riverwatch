package nz.co.android.riverwatch.fragments;

import nz.co.android.cowseye2.R;
import nz.co.android.riverwatch.activity.MainScreenActivity;
import android.app.Activity;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class WaterReadingFragment extends Fragment{
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static WaterReadingFragment newInstance(int sectionNumber) {
		WaterReadingFragment fragment = new WaterReadingFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public WaterReadingFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScrollView sv = new ScrollView(this.getActivity().getApplicationContext());
		TableLayout tableLayout = new TableLayout(this.getActivity().getApplicationContext());
		sv.addView(tableLayout);
		TableRow tableRow;
		TextView textView;
		File dir = new File(Environment.getExternalStorageDirectory() + "/PhotoAR/");
		File[] filelist = dir.listFiles();
//			f.getName()
//		{ // do your stuff here }
		for (File f : filelist){
			tableRow = new TableRow(this.getActivity().getApplicationContext());
			textView = new TextView(this.getActivity().getApplicationContext());
			Calendar cal = Calendar.getInstance(Locale.ENGLISH);
			cal.setTimeInMillis(Long.valueOf(f.getName().replace(".jpg","")));
			String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
			ExifInterface exif = null;
			String message = "";
			try {
				exif = new ExifInterface(f.getCanonicalPath());
				message = exif.getAttribute("UserComment");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textView.setText(date + " " + message);
			textView.setPadding(20, 20, 20, 20);
			tableRow.addView(textView);
			tableLayout.addView(tableRow);
		}
//		setContentView(tableLayout);
		View rootView = sv;
//		View rootView = inflater.inflate(R.layout.coming_soon_layout, container, false);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainScreenActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
	
}