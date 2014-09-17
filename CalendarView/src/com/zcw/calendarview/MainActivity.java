package com.zcw.calendarview;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.TextView;

import com.zcw.FontManager;
import com.zcw.FontManager.Fonts;
import com.zcw.widget.MonthAdapter;
import com.zcw.widget.MonthCellView;
import com.zcw.widget.MonthContentView;
import com.zcw.widget.MonthTitleView;
import com.zcw.widget.MonthCellView.CELL_STATE;

/**
 * @author ThinkPad
 */
public class MainActivity extends Activity {
	private MonthContentView monthView;
	private MyMonthAdapter adapter;
	private TextView textYearMonth;
	
	
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
