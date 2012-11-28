package dev.atga.android.mcphl2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PinnedHeaderAdapter extends ArrayAdapter<ListCellLarge> {

	public ListCellLarge[] mData;
	public boolean[] mIsHeader = null;
	private LayoutInflater mInflater;
	private int mWidth;

	public PinnedHeaderAdapter(Context context, int layoutResourceId,
			ListCellLarge[] data, int width) {
		super(context, layoutResourceId, data);
		this.mData = data;
		this.mWidth = width;
		mInflater = LayoutInflater.from(context);
		setupHeaderArray();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.textHeader = (TextView) convertView
					.findViewById(R.id.header);
			holder.imageText = (TextView) convertView
					.findViewById(R.id.image_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.image.setImageBitmap(mData[position].mImage);
		holder.imageText.setText(mData[position].mImageText);

		if (mIsHeader[position] == true) {
			holder.textHeader.setHeight((int) (mWidth * 0.24));
			holder.textHeader.setText(mData[position].mLocation);
			holder.textHeader.setBackgroundColor(0xFFFF00FF);
		} else {
			holder.textHeader.setHeight((int) (mWidth * 0.24));
			holder.textHeader.setText("");
			holder.textHeader.setBackgroundColor(0x00000000);
		}

		return convertView;
	}

	class ViewHolder {
		TextView textHeader;
		ImageView image;
		TextView imageText;
	}

	private void setupHeaderArray() {
		mIsHeader = new boolean[mData.length];
		for (int position = 0; position < mData.length; position++) {
			if (position == 0) {
				mIsHeader[0] = true;
			} else {
				String current = mData[position].mLocation;
				String prev = mData[position - 1].mLocation;
				if (!current.equals(prev)) {
					mIsHeader[position] = true;
				} else {
					mIsHeader[position] = false;
				}
			}
		}
	}
}
