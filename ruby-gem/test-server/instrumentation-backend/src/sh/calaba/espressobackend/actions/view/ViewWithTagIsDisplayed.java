package sh.calaba.espressobackend.actions.view;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

import static org.hamcrest.CoreMatchers.is;

public class ViewWithTagIsDisplayed implements Action {
	@Override
	public Result execute(String... args) {
		String tagValue = args[0];

		Espresso.onView(ViewMatchers.withTagValue(is((Object) tagValue))).check(
				ViewAssertions.matches(ViewMatchers.isDisplayed()));

		return Result.successResult();
	}

	@Override
	public String key() {
		return "view_with_tag_is_displayed";
	}
}
