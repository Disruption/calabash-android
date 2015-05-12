package sh.calaba.espressobackend.query.espresso;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import android.view.View;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;

public class ViewCaptor implements ViewAction {

	private View affectedView;
	
	@Override
	public Matcher<View> getConstraints() {
		return Matchers.any(View.class);
	}

	@Override
	public String getDescription() {
		return "Captures the view on the onView so it can be used to fetch a view matching a specific criteria";
	}

	@Override
	public void perform(UiController controller, View affectedView) {
		this.affectedView = affectedView;
	}

	public View getCapturedView() {
		return affectedView;
	}

}
