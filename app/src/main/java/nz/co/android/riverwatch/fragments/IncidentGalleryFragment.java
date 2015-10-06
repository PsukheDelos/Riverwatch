package nz.co.android.riverwatch.fragments;

import nz.co.android.cowseye2.R;
import nz.co.android.riverwatch.activity.MainScreenActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class IncidentGalleryFragment extends Fragment{
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static IncidentGalleryFragment newInstance(int sectionNumber) {
		IncidentGalleryFragment fragment = new IncidentGalleryFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public IncidentGalleryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TableLayout tableLayout = new TableLayout(this.getActivity().getApplicationContext());
		TableRow tableRow;
		TextView textView;

		for (int i = 0; i < 4; i++) {
			tableRow = new TableRow(this.getActivity().getApplicationContext());
			for (int j = 0; j < 3; j++) {
				textView = new TextView(this.getActivity().getApplicationContext());
				textView.setText("test");
				textView.setPadding(20, 20, 20, 20);
				tableRow.addView(textView);
			}
			tableLayout.addView(tableRow);
		}
//		setContentView(tableLayout);
		View rootView = tableLayout;
//		View rootView = inflater.inflate(R.layout.coming_soon_layout, container, false);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainScreenActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
	
}