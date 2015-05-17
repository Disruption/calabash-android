package sh.calaba.espressobackend.actions.gestures;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import org.hamcrest.text.StringContains;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

public class PerformPressOnViewWithId implements Action {

    @Override
    public Result execute(String... args) {

        String id = args[0];
        String type = args[1];
		int resourceId = EspressoInstrumentationBackend
				.getCurrentActivity()
				.getResources()
				.getIdentifier(
						id,
						"id",
						EspressoInstrumentationBackend.getCurrentActivity()
								.getPackageName());
		int androidResourceId = EspressoInstrumentationBackend
				.getCurrentActivity()
				.getResources()
				.getIdentifier(
						id,
						"id",
						"android");
		// We try with the id as android resource id. If that throws an exception, we try with the id as our app's package id. If both fail, the step fails
		try {
	        if (type == null || type.equals("SINGLE")) {
	            Espresso.onView(ViewMatchers.withId(androidResourceId))
	                    .perform(ViewActions.click());
	        } else if (type.equals("LONG")) {
	            Espresso.onView(ViewMatchers.withId(androidResourceId))
	                    .perform(ViewActions.longClick());
	        } else if (type.equals("DOUBLE")) {
	            Espresso.onView(ViewMatchers.withId(androidResourceId))
	                    .perform(ViewActions.doubleClick());
	        } else {
	            return Result.failedResult("Unrecognized press type " + type + ". Valid values are SINGLE LONG or DOUBLE");
	        }
		} catch (Exception e) {
	        if (type == null || type.equals("SINGLE")) {
	            Espresso.onView(ViewMatchers.withId(resourceId))
	                    .perform(ViewActions.click());
	        } else if (type.equals("LONG")) {
	            Espresso.onView(ViewMatchers.withId(resourceId))
	                    .perform(ViewActions.longClick());
	        } else if (type.equals("DOUBLE")) {
	            Espresso.onView(ViewMatchers.withId(resourceId))
	                    .perform(ViewActions.doubleClick());
	        } else {
	            return Result.failedResult("Unrecognized press type " + type + ". Valid values are SINGLE LONG or DOUBLE");
	        }
		}
        return Result.successResult();
    }

    @Override
    public String key() {
        return "press_view_with_id";
    }

}
