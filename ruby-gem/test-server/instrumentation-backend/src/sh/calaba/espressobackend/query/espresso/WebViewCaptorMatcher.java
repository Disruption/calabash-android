package sh.calaba.espressobackend.query.espresso;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import android.view.View;
import android.webkit.WebView;

public class WebViewCaptorMatcher extends TypeSafeMatcher<View> {

	private final List<WebView> affectedViews = new ArrayList<WebView>();
	private boolean hasAlreadyReturnedTrue = false;
	
	public List<WebView> getCapturedViews() {
		return affectedViews;
	}

	@Override
	public void describeTo(Description description) {
		description
				.appendText("Captures the views on the onView so it can be used to find views by querying");
	}

	@Override
	public boolean matchesSafely(View view) {
		if (view instanceof WebView) {
			affectedViews.add((WebView) view);
		}

		if (!hasAlreadyReturnedTrue) {
			hasAlreadyReturnedTrue = true;
			return true;
		}
		return false;
	}

}
