package com.zcw;

import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author ThinkPad
 *
 */
public class MonthTitleView extends ViewGroup{
	private MonthAdapter adapter;
	private int titlePadding = 2;
	private int titleWidth;
	private int titleHeight;
	
	public MonthTitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MonthTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MonthTitleView(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);  
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		int titleMaxHeight = 0;
		
		titleWidth = (int)((widthSize - getPaddingLeft() - getPaddingRight() - titlePadding * 6) / 7);
	    int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(titleWidth, MeasureSpec.EXACTLY);
	    int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.UNSPECIFIED);
			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
			
			titleMaxHeight = Math.max(child.getMeasuredHeight(), titleMaxHeight);
		}
	    titleHeight = titleMaxHeight + getPaddingBottom() + getPaddingTop();
	    setMeasuredDimension(widthSize, titleHeight);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int cleft = getPaddingLeft();
		int ctop = getPaddingTop();
		
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
	        View childView = getChildAt(i);
	        int measureHeight = childView.getMeasuredHeight();  
	        int measuredWidth = childView.getMeasuredWidth();
	        
	        childView.layout(cleft, ctop, cleft + measuredWidth, ctop + measureHeight);
	        cleft = getPaddingLeft() + ((i + 1) % 7) * (titleWidth + titlePadding);
		}
	}

	int [] daysOfWeek = new int[]{
			Calendar.SUNDAY, Calendar.MONDAY, 
			Calendar.TUESDAY, Calendar.WEDNESDAY, 
			Calendar.THURSDAY, Calendar.FRIDAY, 
			Calendar.SATURDAY};
	
	public MonthAdapter getAdapter() {
		return adapter;
	}
	
	public void setAdapter(MonthAdapter adapter) {
		this.adapter = adapter;
		
		final LayoutInflater inflater = LayoutInflater.from(getContext());
		for (int dayOfWeek : daysOfWeek) {
			final View titleView = adapter.getTitleView(inflater, getContext(), dayOfWeek);
			addView(titleView);
		}
		
	}
}
