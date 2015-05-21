package sh.calaba.espressobackend.query.espresso;

import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;

import android.support.test.espresso.Root;

public class AllRootsCaptorMatcher extends TypeSafeMatcher<Root> {

	private final List<Root> affectedViews = new ArrayList<Root>();
	private boolean hasAlreadyReturnedTrue = false;

	public List<Root> getCapturedViews() {
		return affectedViews;
	}

	@Override
	public void describeTo(Description description) {
		description
				.appendText("Captures the views on the onView so it can be used to find views by querying");
	}

	@Override
	public boolean matchesSafely(Root root) {
		if (!affectedViews.contains(root)) {
			affectedViews.add(root);
		}

		/*if (!hasAlreadyReturnedTrue) {
			hasAlreadyReturnedTrue = true;
			return true;
		}*/
		return true;
	}

}
