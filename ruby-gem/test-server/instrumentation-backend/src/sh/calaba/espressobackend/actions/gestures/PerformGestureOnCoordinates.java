package sh.calaba.espressobackend.actions.gestures;

import android.view.View;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;

class PerformGestureOnCoordinates {
	
    static ViewAction performPressOnSpecificCoordinates(Tap tap, final int x, final int y) {
        return new GeneralClickAction(
            tap,
            new CoordinatesProvider() {
                @Override
                public float[] calculateCoordinates(View view) {

                   final int[] screenPos = new int[2];
                   view.getLocationOnScreen(screenPos);

                   final float screenX = screenPos[0] + x;
                   final float screenY = screenPos[1] + y;
                   float[] coordinates = {screenX, screenY};

                   return coordinates;
                }
            },
            Press.FINGER);
    }
    
}
