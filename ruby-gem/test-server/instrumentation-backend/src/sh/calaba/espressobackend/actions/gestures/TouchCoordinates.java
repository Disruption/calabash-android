package sh.calaba.espressobackend.actions.gestures;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.matcher.ViewMatchers;

import android.view.Display;
import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;


public class TouchCoordinates implements Action {

    @Override
    public Result execute(String... args) {
        float x = Float.parseFloat(args[0]);
        float y = Float.parseFloat(args[1]);

        Espresso.onView(ViewMatchers.withId(android.R.id.content))
        .perform(PerformGestureOnCoordinates.performPressOnSpecificCoordinates(Tap.SINGLE, (int)x, (int) y));
        return Result.successResult();
    }

    @Override
    public String key() {
        return "touch_coordinate";
    }

}
