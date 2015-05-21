package sh.calaba.espressobackend.query.espresso;

import java.util.Date;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import android.view.View;
import android.widget.DatePicker;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;

public class DatePickerSetter implements ViewAction {
	
	private Date date;

	public DatePickerSetter(Date date) {
		this.date = date;
	}
	
	@Override
	public Matcher<View> getConstraints() {
		return ViewMatchers.isAssignableFrom(DatePicker.class);
	}

	@Override
	public String getDescription() {
		return "Sets a date to the matching datepicker";
	}

	@Override
	public void perform(UiController controller, View affectedView) {
		DatePicker datePicker = (DatePicker) affectedView;
		datePicker.updateDate(date.getYear() + 1900, date.getMonth(), date.getDate());
	}

}
