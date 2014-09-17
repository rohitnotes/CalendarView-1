package com.zcw;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

/**月视图单元格
 * @author ThinkPad
 *
 */
public abstract class MonthCellView extends ViewGroup implements Checkable{
	protected static final int currentYear, currentMonth, currentDay;
	protected static Calendar calendar;
	static{
		calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTimeInMillis(System.currentTimeMillis());
		currentYear = calendar.get(Calendar.YEAR);
		currentMonth = calendar.get(Calendar.MONTH);
		currentDay = calendar.get(Calendar.DAY_OF_MONTH);
	}
	private static final int[] CHECKED_STATE_SET = {
        android.R.attr.state_checked
    };
	
	private Date time;
	private boolean checked = false;
	private CELL_STATE mState;
	private int cellYear;
	private int cellMonth;
	private int cellDay;
	
	public MonthCellView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setup(context);
	}
	public MonthCellView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup(context);
	}
	public MonthCellView(Context context, LayoutInflater inflater) {
		super(context);
		setup(inflater);
	}
	
	public MonthCellView(Context context) {
		super(context);
		setup(context);
	}
	
	public final void setup(Context context) {
		setup(LayoutInflater.from(context));
	}
	
	public final void setup(LayoutInflater inflater) {
		inflater.inflate(getLayout(), this, true);
		onLayoutInflated(this);
	}
	
	/**
	 * @param monthCellView
	 */
	public void onLayoutInflated(MonthCellView monthCellView) {}
	
	/**
	 * @return
	 */
	public abstract int getLayout();
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);  
	    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
	    int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
	    int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
	    
		int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
	    	final View child = getChildAt(i);
	    	child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
	    }
	    setMeasuredDimension(widthSize, heightSize);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(! changed)
			return ;
		final int width = getWidth();
		final int height = getHeight();
		
		int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
	    	final View child = getChildAt(i);
	    	if(child.getVisibility() != View.GONE){
	    		child.layout(0, 0, width, height);
	    	}
	    }
	}
	
	/**
	 * @param time
	 * @param mYear
	 * @param mMonth
	 */
	public final void setDay(Date time, final int mYear, final int mMonth){
		this.time = time;
		calendar.setTime(time);
		
		cellYear = calendar.get(Calendar.YEAR);
		cellMonth = calendar.get(Calendar.MONTH);
		cellDay = calendar.get(Calendar.DAY_OF_MONTH);
		
    	if(cellYear == currentYear && cellMonth == currentMonth && cellDay == currentDay){
			setState(CELL_STATE.TODAY);
		}else if(mYear != cellYear || mMonth != cellMonth){
			setState(CELL_STATE.OVERDUE);
		}else{
			setState(CELL_STATE.NORMAL);
		}
    	onDayChenged(time, mYear, mMonth);
	};
	
	/**
	 * @param time2
	 * @param mYear
	 * @param mMonth
	 */
	public void onDayChenged(Date time, int mYear, int mMonth){};
	
	/**
	 * @param state
	 */
	public final void setState(CELL_STATE state) {
		CELL_STATE oldState = mState;
		mState = state;
		onStateChanged(oldState, state);
	}
	
	/**
	 * @param oldState
	 * @param newState
	 */
	public void onStateChanged(CELL_STATE oldState, CELL_STATE newState){};

	/**
	 * @author ThinkPad
	 *
	 */
	public enum CELL_STATE{
		DISABLED,
		NORMAL,
		TODAY,
		OVERDUE,
		;
	}

	public Date getDay() {
		return time;
	}
	
	@Override
    public boolean isChecked() {
        return checked;
    }
	
	@Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        refreshDrawableState();
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if(child instanceof Checkable) {
                ((Checkable)child).setChecked(checked);
            }
        }
    }
    
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void toggle() {
        this.checked = !this.checked;
    }
	public int getCellYear() {
		return cellYear;
	}
	public void setCellYear(int cellYear) {
		this.cellYear = cellYear;
	}
	public int getCellMonth() {
		return cellMonth;
	}
	public void setCellMonth(int cellMonth) {
		this.cellMonth = cellMonth;
	}
	public int getCellDay() {
		return cellDay;
	}
	public void setCellDay(int cellDay) {
		this.cellDay = cellDay;
	}
    
    
}
