package sh.calaba.espressobackend.actions.gestures;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

public class Swipe implements Action {

    @Override
    public Result execute(String... args) {
        String direction = args[0];

        if (args.length == 1) {
            if(direction.equalsIgnoreCase("left")) {
            	Espresso.onView(ViewMatchers.withId(android.R.id.content)).perform(ViewActions.swipeLeft());
                return Result.successResult();
            } else if(direction.equalsIgnoreCase("right")) {
            	Espresso.onView(ViewMatchers.withId(android.R.id.content)).perform(ViewActions.swipeRight());
                return Result.successResult();
            }
            return Result.failedResult("Invalid direction to swipe: " + direction);
        }
        return Result.failedResult("You must provide a direction. Either 'left' or 'right'");
    }

    @Override
    public String key() {
        return "swipe";
    }

}
