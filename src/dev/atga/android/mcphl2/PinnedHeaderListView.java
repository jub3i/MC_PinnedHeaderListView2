package dev.atga.android.mcphl2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PinnedHeaderListView extends ListView {

	TextView mDynamicHeader;
	FrameLayout mFrameLayout;
	Context mContext;
	Rect mClippingRect = new Rect();
	int mHeight;
	int mWidth;
	int mTop;
	boolean mIsStartup = true;
	PinnedHeaderAdapter mPinnedHeaderAdapter;

	public PinnedHeaderListView(Context context) {
		super(context);
		mContext = context;
		setup();
	}

	public PinnedHeaderListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setup();
	}

	public PinnedHeaderListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setup();
	}

	private void setup() {
		Log.d("ATGA", "setup width: " + getWidth());
		mFrameLayout = (FrameLayout) ((LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.list_dynamic_header, null);

		setBackgroundColor(Color.BLACK);
		setDivider(null);
		setDividerHeight(0);
	}

	// drawing of the dynamic header in here
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);

		if (mIsStartup) {
			TextView listHeader = (TextView) findViewById(R.id.header);
			mHeight = listHeader.getHeight();
			mWidth = getWidth();
			mIsStartup = false;
		}

		// determine if there is a header near the dynamic header
		mPinnedHeaderAdapter = (PinnedHeaderAdapter) getAdapter();
		mTop = 0;
		if (mPinnedHeaderAdapter.isHeader[getFirstVisiblePosition()]) {
			if (getChildAt(0) != null) {
				mTop = getChildAt(0).getTop();
			}
		} else if ((getFirstVisiblePosition() + 1 < mPinnedHeaderAdapter.isHeader.length)
				&& mPinnedHeaderAdapter.isHeader[getFirstVisiblePosition() + 1]) {
			if (getChildAt(1) != null) {
				mTop = getChildAt(1).getTop();
			}
		}

		mFrameLayout.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, mHeight));
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(getWidth(),
				MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		mFrameLayout.measure(widthMeasureSpec, heightMeasureSpec);
		mFrameLayout.layout(0, 0, (int) (mWidth * 0.24), mHeight);

		mDynamicHeader = (TextView) mFrameLayout.findViewById(R.id.text);
		mDynamicHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

		// adjust the dynamic headers text, while the old header
		// is still slightly visible
		String headerText = "";
		if (mTop <= 0) {
			headerText = mPinnedHeaderAdapter.data[getFirstVisiblePosition() + 1].location;
		} else {
			headerText = mPinnedHeaderAdapter.data[getFirstVisiblePosition()].location;
		}

		mDynamicHeader.setText(headerText);

		canvas.save();
		mClippingRect.top = 0;
		mClippingRect.left = (int) (mWidth * 0.03);
		mClippingRect.bottom = mHeight;
		mClippingRect.right = (int) (mWidth * 0.28);
		canvas.clipRect(mClippingRect);
		// decide where to draw the dynamic header
		if (mTop <= 0) {
			canvas.translate((int) (mWidth * 0.03), 0);
		} else if (mTop < mHeight + 10) {
			canvas.translate((int) (mWidth * 0.03), mTop - mHeight - 10);
		} else {
			canvas.translate((int) (mWidth * 0.03), 0);
		}
		mFrameLayout.draw(canvas);
		canvas.restore();
	}
}
