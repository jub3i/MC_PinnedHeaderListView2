package dev.atga.android.mcphl2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PinnedHeaderAdapter extends ArrayAdapter<ListCellLarge> {

	public ListCellLarge[] data;
	public boolean[] isHeader = null;
	private LayoutInflater inflater;
	private int width;

	public PinnedHeaderAdapter(Context context, int layoutResourceId,
			ListCellLarge[] data, int width) {
		super(context, layoutResourceId, data);
		this.data = data;
		this.width = width;
		inflater = LayoutInflater.from(context);
		setupHeaderArray();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item, parent, false);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.textHeader = (TextView) convertView
					.findViewById(R.id.header);
			holder.imageText = (TextView) convertView
					.findViewById(R.id.image_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.image.setImageBitmap(data[position].image);
		holder.imageText.setText(data[position].imageText);

		if (isHeader[position] == true) {
			holder.textHeader.setHeight((int) (width * 0.24));
			holder.textHeader.setText(data[position].location);
			holder.textHeader.setBackgroundColor(0xFFFF00FF);
		} else {
			holder.textHeader.setHeight((int) (width * 0.24));
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
		isHeader = new boolean[data.length];
		for (int position = 0; position < data.length; position++) {
			if (position == 0) {
				isHeader[0] = true;
			} else {
				String current = data[position].location;
				String prev = data[position - 1].location;
				if (!current.equals(prev)) {
					isHeader[position] = true;
				} else {
					isHeader[position] = false;
				}
			}
		}
	}
}
