package dev.atga.android.mcphl2;

import android.graphics.Bitmap;

public class ListCellLarge {

	static final String LOC_JHB = "Joburg";
	static final String LOC_DBN = "Durban";
	static final String LOC_CPT = "CapeTown";

	public String location;
	public Bitmap image;
	public String imageText;

	public ListCellLarge(String location, String imageText, Bitmap image) {
		this.location = location;
		this.image = image;
		this.imageText = imageText;
	}
}
