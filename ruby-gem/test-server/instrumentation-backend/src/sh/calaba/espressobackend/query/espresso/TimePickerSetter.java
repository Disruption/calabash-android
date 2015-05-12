package sh.calaba.espressobackend.query.espresso;

import java.sql.Time;

import org.hamcrest.Matcher;

import android.view.View;
import android.widget.TimePicker;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;

public class TimePickerSetter implements ViewAction {
	
	private Time time;

	public TimePickerSetter(Time time) {
		this.time = time;
	}
	
	@Override
	public Matcher<View> getConstraints() {
		return ViewMatchers.isAssignableFrom(TimePicker.class);
	}

	@Override
	public String getDescription() {
		return "Sets a date to the matching datepicker";
	}

	@Override
	public void perform(UiController controller, View affectedView) {
		TimePicker timePicker = (TimePicker) affectedView;        
		timePicker.setCurrentHour(time.getHours());
        timePicker.setCurrentMinute(time.getMinutes());

	}

}
