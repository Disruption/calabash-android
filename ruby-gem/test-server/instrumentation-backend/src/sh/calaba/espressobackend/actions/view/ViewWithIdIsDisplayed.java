package sh.calaba.espressobackend.actions.view;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;

public class ViewWithIdIsDisplayed implements Action {
	@Override
	public Result execute(String... args) {
		String id = args[0];
		int resourceId = EspressoInstrumentationBackend
				.getCurrentActivity()
				.getResources()
				.getIdentifier(
						id,
						"id",
						EspressoInstrumentationBackend.getCurrentActivity()
								.getPackageName());
		Espresso.onView(ViewMatchers.withId(resourceId)).check(
				ViewAssertions.matches(ViewMatchers.isDisplayed()));

		return Result.successResult();
	}

	@Override
	public String key() {
		return "view_with_id_is_displayed";
	}
}
