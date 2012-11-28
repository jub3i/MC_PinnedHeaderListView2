package dev.atga.android.mcphl2;

import android.graphics.Bitmap;

public class ListCellLarge {

	static final String LOC_JHB = "Joburg";
	static final String LOC_DBN = "Durban";
	static final String LOC_CPT = "CapeTown";

	public String mLocation;
	public Bitmap mImage;
	public String mImageText;

	public ListCellLarge(String location, String imageText, Bitmap image) {
		this.mLocation = location;
		this.mImage = image;
		this.mImageText = imageText;
	}
}
