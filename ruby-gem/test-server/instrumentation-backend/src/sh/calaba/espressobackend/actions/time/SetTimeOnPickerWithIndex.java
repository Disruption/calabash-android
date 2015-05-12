package sh.calaba.espressobackend.actions.time;

import java.sql.Time;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import sh.calaba.espressobackend.matchers.WithIndex;
import sh.calaba.espressobackend.query.espresso.TimePickerSetter;
import android.view.View;
import android.widget.TimePicker;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;

public class SetTimeOnPickerWithIndex implements Action {

	@SuppressWarnings("unchecked")
	@Override
	public Result execute(String... args) {

		int expectedIndex = Integer.parseInt(args[0]);
		Time time = new Time(Time.parse(args[1]));

		Matcher<View> matcher = Matchers.allOf(ViewMatchers.isAssignableFrom(TimePicker.class),
				new WithIndex(expectedIndex));
		
		Espresso.onView(matcher).perform(new TimePickerSetter(time));

		return Result.successResult();
	}

	@Override
	public String key() {
		return "set_time_with_index";
	}

}
