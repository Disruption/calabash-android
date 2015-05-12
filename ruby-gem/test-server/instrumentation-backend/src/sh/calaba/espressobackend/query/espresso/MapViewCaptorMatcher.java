package sh.calaba.espressobackend.query.espresso;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import android.util.Log;
import android.view.View;
import android.view.ViewManager;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import com.google.android.maps.MapView;

public class MapViewCaptorMatcher extends TypeSafeMatcher<View> {

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
		if (view.getClass().isAssignableFrom(MapView.class)) {
			affectedViews.add(view);
		}

		if (!hasAlreadyReturnedTrue) {
			hasAlreadyReturnedTrue = true;
			return true;
		}
		return false;
	}

}
