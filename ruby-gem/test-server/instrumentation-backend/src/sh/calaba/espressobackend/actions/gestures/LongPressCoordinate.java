package sh.calaba.espressobackend.actions.gestures;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.matcher.ViewMatchers;

import android.view.Display;
import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

/**
 * <p>
 * Action that performs a long press on given coordinates.
 * </p>
 * <p>
 * Parameters:
 * <ul>
 * <li>args[0]: x coordinate (float)</li>
 * <li>args[1]: y coordinate (float)</li>
 * <li>args[2]: length of the long press in millisecond (optional, integer)</li>
 * </ul>
 * </p>
 */
public class LongPressCoordinate implements Action {
    @Override
    public Result execute(String... args) {
        Display display = EspressoInstrumentationBackend.getCurrentActivity().getWindowManager().getDefaultDisplay();

        float x = Float.parseFloat(args[0]);
        float y = Float.parseFloat(args[1]);
		
        int width = display.getWidth();
        int height = display.getHeight();
		
        Espresso.onView(ViewMatchers.withId(android.R.id.content))
        .perform(PerformGestureOnCoordinates.performPressOnSpecificCoordinates(Tap.LONG, (int)(x/100)*width, (int) (y/100)*height));
        
        return Result.successResult();
    }

    @Override
    public String key() {
        return "long_press_coordinate";
    }
}
