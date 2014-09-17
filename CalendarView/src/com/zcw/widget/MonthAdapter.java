package com.zcw.widget;

import java.util.Calendar;
import java.util.Date;

import com.zcw.DpUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**月视图适配器
 * @author ThinkPad
 */
public abstract class MonthAdapter {
	
	/**获取月视图中某天的布局
	 * @param inflater
	 * @param context
	 * @param index 某天布局所处的索引
	 * @return 某天的布局
	 */
	public abstract MonthCellView getCellView(LayoutInflater inflater, Context context, int index);

	/**更新某天的数据
	 * @param child 某天的布局
	 * @param index 某天布局所处的索引
	 * @param time 某天的时间
	 * @param mYear 某天所处的年
	 * @param mMonth 某天所处的月
	 */
	public abstract void updateCell(MonthCellView child, int index, Date time, int mYear, int mMonth);
	
	/**当某天被选中
	 * @param cell 当天的布局
	 * @param time 当天的时间
	 * @param mYear 当前日历显示所处的年
	 * @param mMonth 当前日历显示所处的月
	 */
	public void onDayChecked(MonthCellView cell, Date time, final int mYear, final int mMonth){}

	/**获取标题布局中某一个标题
	 * @param inflater
	 * @param context
	 * @param dayOfWeek 星期 [Calendar.SUNDAY, Calendar.MONDAY, 
			Calendar.TUESDAY, Calendar.WEDNESDAY, 
			Calendar.THURSDAY, Calendar.FRIDAY, 
			Calendar.SATURDAY]
	 * @return 某标题布局，默认TextView
	 */
	public View getTitleView(LayoutInflater inflater, Context context, int dayOfWeek) {
		TextView titleView  = new TextView(context);
		titleView.setTextColor(Color.WHITE);
		titleView.setTextSize(DpUtil.sp2px(context, 5f));
		titleView.setGravity(Gravity.CENTER);
		 
		final int parseColor = Color.parseColor("#bbbbbb");
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			titleView.setText("日");
			titleView.setTextColor(parseColor);
			break;
		case Calendar.MONDAY: 
			titleView.setText("一");
			titleView.setTextColor(Color.WHITE);
			break;
		case Calendar.TUESDAY: 
			titleView.setText("二");
			titleView.setTextColor(Color.WHITE);
			break;
		case Calendar.WEDNESDAY: 
			titleView.setText("三");
			titleView.setTextColor(Color.WHITE);
			break;
		case Calendar.THURSDAY: 
			titleView.setText("四");
			titleView.setTextColor(Color.WHITE);
			break;
		case Calendar.FRIDAY: 
			titleView.setText("五");
			titleView.setTextColor(Color.WHITE);
			break;
		case Calendar.SATURDAY:
			titleView.setText("六");
			titleView.setTextColor(parseColor);
			break;
		default:
			break;
		}
		return titleView;
	}

	/**
	 * @param monthContentView
	 * @param date
	 * @param mYear
	 * @param mMonth
	 */
	public void onMonthChanged(MonthContentView monthContentView, long date, int mYear, int mMonth) { };
}
