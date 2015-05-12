package sh.calaba.espressobackend.actions.checkbox;

import org.hamcrest.Matcher;

import android.view.View;
import android.widget.CheckBox;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import sh.calaba.espressobackend.matchers.WithIndex;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.Matchers.allOf;

public class ToggleCheckboxWithIndex implements Action {
	@Override
	public Result execute(String... args) {
		int index = Integer.parseInt(args[0]);
		Matcher<View> viewMatcher = allOf(isAssignableFrom(CheckBox.class), new WithIndex(index));
		Espresso.onView(viewMatcher).perform(ViewActions.click());

		return Result.successResult();
	}

	@Override
	public String key() {
		return "toggle_checkbox_with_index";
	}
}
