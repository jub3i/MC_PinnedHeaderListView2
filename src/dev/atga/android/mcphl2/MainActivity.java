package dev.atga.android.mcphl2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// create some data for the listview
		ListCellLarge[] listCellArray = new ListCellLarge[10];
		int[] drawables = { R.drawable.p1, R.drawable.p2, R.drawable.p3,
				R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7,
				R.drawable.p8, R.drawable.p9, R.drawable.p10 };
		String loc;
		for (int i = 0; i <= 9; i++) {
			if (i < 3)
				loc = ListCellLarge.LOC_CPT;
			else if (i <= 6)
				loc = ListCellLarge.LOC_DBN;
			else
				loc = ListCellLarge.LOC_JHB;
			// this should be off ui thread (farm it off to an async task)
			listCellArray[i] = new ListCellLarge(loc, "Sample Description "
					+ (i + 1), BitmapFactory.decodeResource(
					this.getResources(), drawables[i]));
		}

		// get the width and height of display
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		int width;
		int height;
		// try-catch for API_LEVEL < 13 depreciated methods
		try {
			display.getSize(size);
			width = size.x;
			height = size.y;
		} catch (NoSuchMethodError e) {
			width = display.getWidth();
			height = display.getHeight();
		}

		// set the adapter
		PinnedHeaderListView phlv = (PinnedHeaderListView) findViewById(R.id.phlv);
		phlv.setAdapter(new PinnedHeaderAdapter(this, R.layout.list_item,
				listCellArray, width));
	}
}
