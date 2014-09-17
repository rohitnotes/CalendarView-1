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
import com.zcw.MonthCellView;

/**
 * @author ThinkPad
 *
 */
public class MyMonthCellView  extends MonthCellView{
	private static SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
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
	
	public void onStateChanged(CELL_STATE oldState, CELL_STATE state){
		setEnabled(state != CELL_STATE.DISABLED);
		imageDot.setVisibility(state == CELL_STATE.DISABLED ? View.VISIBLE : View.GONE);
		
		switch (state) {
		case DISABLED:
//			final int disabledBgColor = Color.parseColor("#DDDDDD");
//			setBackgroundColor(disabledBgColor);
//			textDay.setTextColor(Color.BLACK);
			textDay.setTextColor(Color.WHITE);
			setBackgroundResource(R.drawable.month_cell_disabled_normal);
			break;
		case NORMAL:
//			setBackgroundColor(Color.WHITE);
//			final int normalDayColor = Color.parseColor("#666666");
//			textDay.setTextColor(normalDayColor);
			textDay.setTextColor(Color.WHITE);
			setBackgroundResource(R.drawable.month_cell_normal_selector);
			break;
		case OVERDUE:
//			final int overdueBgColor = Color.parseColor("#EEEEEE");
//			setBackgroundColor(overdueBgColor);
//			final int overdueDayColor = Color.parseColor("#888888");
			final int overdueDayColor = Color.parseColor("#bbbbbb");
			textDay.setTextColor(overdueDayColor);
			break;
		case TODAY:
//			final int todayBgColor = Color.parseColor("#4EBB7F");
//			setBackgroundColor(todayBgColor);
			setBackgroundResource(R.drawable.month_cell_today_selector);
			textDay.setTextColor(Color.WHITE);
//			textDay.setTypeface(Typeface.DEFAULT_BOLD);
			break;
		default:
			break;
		}
	}

}
