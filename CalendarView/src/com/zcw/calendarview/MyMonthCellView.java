package com.zcw.calendarview;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcw.FontManager;
import com.zcw.FontManager.Fonts;
import com.zcw.drawable.StyledGradientDrawable;
import com.zcw.widget.MonthCellView;

/**
 * @author ThinkPad
 *
 */
public class MyMonthCellView extends MonthCellView{
	private static SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
	
	static CellMode cellMode = CellMode.EDIT_DISABLE;
	
	private TextView textDay;
	private ImageView imageDot;
	
	
	public MyMonthCellView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public MyMonthCellView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MyMonthCellView(Context context, LayoutInflater inflater) {
		super(context, inflater);
	}
	public MyMonthCellView(Context context) {
		super(context);
	}
	
	@Override
	public int getLayout(){
		return R.layout.layout_month_cell;
	}
	@Override
	public void onLayoutInflated(MonthCellView monthCellView) {
		textDay = (TextView) findViewById(R.id.text_day);
		textDay.setTypeface(FontManager.getInstance().getTypeface(Fonts.BARIOL_REGULAR));
		
		imageDot = (ImageView) findViewById(R.id.image_dot);
	}
	
	public void onDayChenged(Date time, final int mYear, final int mMonth){
		textDay.setText(sdf.format(time));
	}
	
	static StyledGradientDrawable disabledDrawable;
	static StyledGradientDrawable todayDrawable;
	
	static{//#d45270
		disabledDrawable = new StyledGradientDrawable();
		disabledDrawable.setColor(0x22ffffff);
		disabledDrawable.setCornerRadii(true, true, true, true);
		disabledDrawable.setPadding(10);
		
		todayDrawable = new StyledGradientDrawable();
		todayDrawable.setCornerRadii(true, true, true, true);
		todayDrawable.setPadding(10);
		todayDrawable.setStroke(2, Color.WHITE);
	}
	
	boolean disabled;
	boolean isWork;
	
	public boolean isIsWork() {
		return isWork;
	}
	public void setIsWork(boolean isWork) {
		this.isWork = isWork;
		//applyDisplay();
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		//applyDisplay();
	}
	
	public void onCellTypeChanged(CellType oldState, CellType state){
		applyDisplay();
	}
	
	public void applyDisplay() {
		CellType cellType = getCellType();
		//setEnabled(state != CELL_STATE.DISABLED);
		imageDot.setVisibility(isWork ? View.VISIBLE : View.GONE);
		
		if(disabled){
			textDay.setTextColor(0x33ffffff);
			setBackgroundDrawable(disabledDrawable);
		}else{
			switch (cellType) {
			case IN_MONTH:
//				textDay.setTextColor(normalDayColor);
				textDay.setTextColor(Color.WHITE);
				setBackgroundResource(R.drawable.month_cell_normal_selector);
				break;
			case OUT_MONTH:
				final int overdueDayColor = 0xffbbbbbb;
				textDay.setTextColor(overdueDayColor);
				setBackgroundDrawable(null);
				break;
			case TODAY:
//				setBackgroundResource(R.drawable.month_cell_today_selector);
				textDay.setTextColor(Color.WHITE);
				setBackgroundDrawable(todayDrawable);
				break;
			default:
				break;
			}
		}
	}

	
	public enum CellMode{
		NORMAL,
		EDIT_DISABLE,
	}
}
