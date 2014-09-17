package com.zcw;

import android.graphics.drawable.GradientDrawable;

public class MyDrawable extends GradientDrawable{

	public MyDrawable() {
		super();
	}

	public MyDrawable(Orientation orientation, int[] colors) {
		super(orientation, colors);
	}

	@Override
	public void setStroke(int width, int color, float dashWidth, float dashGap) {
		// TODO Auto-generated method stub
		super.setStroke(width, color, dashWidth, dashGap);
	}


}
