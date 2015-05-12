package sh.calaba.espressobackend.actions.gestures;


import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import android.view.Display;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.matcher.ViewMatchers;


public class ClickOnScreen implements Action {

    @Override
    public Result execute(String... args) {
        Display display = EspressoInstrumentationBackend.getCurrentActivity().getWindowManager().getDefaultDisplay();
        
        float x = Float.parseFloat(args[0]);
        float y = Float.parseFloat(args[1]);
        
        int width = display.getWidth();
        int height = display.getHeight();
        
        Espresso.onView(ViewMatchers.withId(android.R.id.content))
        .perform(PerformGestureOnCoordinates.performPressOnSpecificCoordinates(Tap.SINGLE, (int)(x/100)*width, (int) (y/100)*height));
        return Result.successResult();
    }

    @Override
    public String key() {
        return "click_on_screen";
    }
    
}
