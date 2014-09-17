package com.zcw.calendarview;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zcw.FontManager;
import com.zcw.FontManager.Fonts;
import com.zcw.widget.MonthAdapter;
import com.zcw.widget.MonthCellView;
import com.zcw.widget.MonthContentView;
import com.zcw.widget.MonthTitleView;

/**
 * @author ThinkPad
 */
public class MainActivity extends Activity {
	private MonthContentView monthView;
	private MyMonthAdapter adapter;
	private TextView textYearMonth;
	
	MonthMode mode = MonthMode.EDIT_DISABLE;
	
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
		//monthView.setCheckable(true);
		
		
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
		
		
		ToggleButton toggle = (ToggleButton)findViewById(R.id.toggleButton1);
		toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean on) {
				if(on){
					monthView.setCheckable(false);
					mode = MonthMode.EDIT_DISABLE;
				}else{
					monthView.setCheckable(true);
					mode = MonthMode.NORMAL;
				}
				monthView.notifyCellChanged();
			}
		});
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
			
			//fake work data...
			MyMonthCellView mcell = (MyMonthCellView) child;
			boolean hasWork = false;
			if(child.getCellMonth() == mMonth){
				int ran = (int) (Math.random() * 100);
				hasWork = ran < 30;
			}
			mcell.setIsWork(hasWork);
			if(mode == MonthMode.EDIT_DISABLE){
				mcell.setEnabled(! hasWork);
			}
			mcell.applyDisplay();
		}
		
		@Override
		public void onCellClicked(MonthCellView cell, Date time, final int mYear, final int mMonth){
			if(cell.getCellYear() != mYear || cell.getCellMonth() != mMonth){
				monthView.setDate(time.getTime());
			}else if(mode == MonthMode.EDIT_DISABLE){
				MyMonthCellView mcell = (MyMonthCellView) cell;
				mcell.setDisabled(! mcell.isDisabled());
				mcell.applyDisplay();
			}else{
				MyMonthCellView mcell = (MyMonthCellView) cell;
				mcell.setDisabled(false);
				mcell.applyDisplay();
			}
		}
		
		public void onMonthChanged(MonthContentView monthContentView, long date, int mYear, int mMonth) {
			textYearMonth.setText(mYear + " • " + (mMonth + 1));
		};
	}
	
	
	public enum MonthMode{
		NORMAL,
		EDIT_DISABLE,
	}
}
