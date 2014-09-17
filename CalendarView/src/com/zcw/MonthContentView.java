package com.zcw;

import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author ThinkPad
 *
 */
public class MonthContentView extends ViewGroup{
	private static Calendar calendar = Calendar.getInstance(Locale.getDefault());
	static{
		calendar.setTimeInMillis(System.currentTimeMillis());
	}
	
	private int cellPadding = 2;
	private int cellWidth;
	private MonthAdapter adapter;
	private int mYear;
	private int mMonth;
	
	public MonthContentView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup(context);
	}

	public MonthContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup(context);
	}

	public MonthContentView(Context context) {
		super(context);
		setup(context);
	}

	/**
	 * @param context
	 */
	public void setup(Context context) {}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);  
	    cellWidth = (int)((widthSize - getPaddingLeft() - getPaddingRight() - cellPadding * 6) / 7);
	    int childMeasureSpec = MeasureSpec.makeMeasureSpec(cellWidth, MeasureSpec.EXACTLY);
	    
	    int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			child.measure(childMeasureSpec, childMeasureSpec);
		}
	    
	    final int height = cellWidth * 6 + 5 * cellPadding + getPaddingTop() + getPaddingBottom();
		final int width = cellWidth * 7 + 6 * cellPadding + getPaddingLeft() + getPaddingRight();
		setMeasuredDimension(width, height);
	}
	
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		int cleft = getPaddingLeft();
		int ctop = getPaddingTop();
		
		int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
	    	final View child = getChildAt(i);
			final int measureHeight = child.getMeasuredHeight();  
	    	final int measuredWidth = child.getMeasuredWidth();
	    	
	    	child.layout(cleft, ctop, cleft + measuredWidth, ctop + measureHeight);
	    	
	    	cleft = getPaddingLeft() + ((i + 1) % 7) * (cellWidth + cellPadding);
	    	ctop = getPaddingTop() + ((int)((i + 1) / 7f)) * (cellWidth + cellPadding);
		}
	}
	
	/**
	 * @param date
	 */
	public void setDate(long date){
		calendar.setTimeInMillis(date);
		
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		final int firstDayOfWeekInMonth = calendar.get(Calendar.DAY_OF_WEEK);
		
		int between = firstDayOfWeekInMonth - Calendar.MONDAY + 1;
		calendar.add(Calendar.DAY_OF_WEEK, -between);
		
		int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
	    	final MonthCellView child = (MonthCellView) getChildAt(i);
	    	adapter.updateCell(child, i, calendar.getTime(), mYear, mMonth);
	    	calendar.add(Calendar.DAY_OF_WEEK, 1);
	    }
	    
	    adapter.onMonthChanged(this, date, mYear, mMonth);
	}
	
	/**
	 * @return
	 */
	public MonthAdapter getAdapter() {
		return adapter;
	}
	
	/**
	 * @param adapter
	 */
	public void setAdapter(MonthAdapter adapter) {
		this.adapter = adapter;
		
		final LayoutInflater inflater = LayoutInflater.from(getContext());
		for(int i = 0; i < 6 * 7; i ++){
			final MonthCellView cellView = adapter.getCellView(inflater, getContext(), i);
			cellView.setOnClickListener(onCellClick);
			addView(cellView, 0);
		}
	}
	
	/** */
	final OnClickListener onCellClick = new OnClickListener() {
		@Override
		public void onClick(View cellView) {
			final MonthCellView cell = (MonthCellView) cellView;
			
			int childCount = getChildCount();
		    for (int i = 0; i < childCount; i++) {
		    	final MonthCellView child = (MonthCellView) getChildAt(i);
		    	child.setChecked(false);
		    }
			cell.setChecked(true);
			adapter.onDayChecked(cell, cell.getDay(), mYear, mMonth);
		}
	};
}
