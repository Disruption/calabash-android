package sh.calaba.espressobackend.actions.time;

import java.sql.Time;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.text.StringContains;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import sh.calaba.espressobackend.query.espresso.TimePickerSetter;
import android.view.View;
import android.widget.TimePicker;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;

public class SetTimeOnPickerWithDescription implements Action {

	@SuppressWarnings("unchecked")
	@Override
	public Result execute(String... args) {

		String contentDescription = args[0];
		Time time = new Time(Time.parse(args[1]));

		Matcher<View> matcher = Matchers.allOf(ViewMatchers.isAssignableFrom(TimePicker.class),
				ViewMatchers.withContentDescription(StringContains
						.containsString(contentDescription)));
		
		Espresso.onView(matcher).perform(new TimePickerSetter(time));

		return Result.successResult();
	}

	@Override
	public String key() {
		return "set_time_with_description";
	}

}
