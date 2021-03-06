package sh.calaba.espressobackend.actions.view;

import org.hamcrest.Matchers;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.NoMatchingViewException;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

public class ViewWithTextIsNotDisplayed implements Action {
    @Override
    public Result execute(String... args) {
		String text = args[0];
		try {
			Espresso.onView(ViewMatchers.withText(text)).check(ViewAssertions.matches((Matchers.not(ViewMatchers.isDisplayed()))));
		} catch (NoMatchingViewException nme) {
			Espresso.onView(ViewMatchers.withText(text)).check(ViewAssertions.doesNotExist());
		}
		return Result.successResult();
    }

    @Override
    public String key() {
        return "view_with_text_is_not_displayed";
    }
}
