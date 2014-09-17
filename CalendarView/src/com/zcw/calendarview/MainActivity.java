package com.zcw.calendarview;

import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.zcw.FontManager;
import com.zcw.FontManager.Fonts;
import com.zcw.DpUtil;
import com.zcw.MonthAdapter;
import com.zcw.MonthCellView;
import com.zcw.MonthCellView.CELL_STATE;
import com.zcw.MonthContentView;
import com.zcw.MonthTitleView;
import com.zcw.drawable.StyledGradientDrawable;

/**
 * @author ThinkPad
 */
public class MainActivity extends Activity {
	private MonthContentView monthView;
	private MyMonthAdapter adapter;
	private TextView textYearMonth;
	
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		FontManager.init(getAssets());
		
		textYearMonth = (TextView) findViewById(R.id.text_yearMonth);
		monthView = (MonthContentView) findViewById(R.id.monthView1);
		adapter = new MyMonthAdapter();
		monthView.setAdapter(adapter);
		
		MonthTitleView titleView  = (MonthTitleView) findViewById(R.id.monthTitleView1);
		titleView.setAdapter(adapter);
		
		titleView.postDelayed(new Runnable() {
			@Override
			public void run() {
				Calendar calendar = Calendar.getInstance();
//				calendar.add(Calendar.MONTH, 1);
				monthView.setDate(calendar.getTimeInMillis());
			}
		}, 1000);
		
		textYearMonth.setTypeface(FontManager.getInstance().getTypeface(Fonts.BARIOL_REGULAR));
		
	}
	
	/**
	 * @author ThinkPad
	 *
	 */
	public class MyMonthAdapter extends MonthAdapter{
		
		@Override
		public MonthCellView getCellView(LayoutInflater inflater, Context context, int index) {
			return new MyMonthCellView(context, inflater);
		}
		
		@Override
		public void updateCell(MonthCellView child, int index, Date time, int mYear, int mMonth) {
			child.setDay(time, mYear, mMonth);
			
			if(index == 9){
				child.setState(CELL_STATE.DISABLED);
			}
		}
		
		@Override
		public void onDayChecked(MonthCellView cell, Date time, final int mYear, final int mMonth){
			if(cell.getCellYear() != mYear || cell.getCellMonth() != mMonth){
				monthView.setDate(time.getTime());
			}
		}
		
		public void onMonthChanged(MonthContentView monthContentView, long date, int mYear, int mMonth) {
			textYearMonth.setText(mYear + " • " + (mMonth + 1));
		};
	}
}
