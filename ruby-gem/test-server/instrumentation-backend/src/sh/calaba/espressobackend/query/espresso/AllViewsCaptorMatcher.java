package sh.calaba.espressobackend.query.espresso;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import android.view.View;

public class AllViewsCaptorMatcher extends TypeSafeMatcher<View> {

	private final List<View> affectedViews = new ArrayList<View>();
	private boolean hasAlreadyReturnedTrue = false;

	public List<View> getCapturedViews() {
		return affectedViews;
	}

	@Override
	public void describeTo(Description description) {
		description
				.appendText("Captures the views on the onView so it can be used to find views by querying");
	}

	@Override
	public boolean matchesSafely(View view) {
		if (!affectedViews.contains(view)) {
			affectedViews.add(view);
		}

		if (!hasAlreadyReturnedTrue) {
			hasAlreadyReturnedTrue = true;
			return true;
		}
		return false;
	}

}
