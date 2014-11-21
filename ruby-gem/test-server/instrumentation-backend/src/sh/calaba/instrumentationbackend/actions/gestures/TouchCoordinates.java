package sh.calaba.instrumentationbackend.actions.gestures;


import android.view.Display;
import sh.calaba.instrumentationbackend.RobotiumInstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;


public class TouchCoordinates implements Action {

    @Override
    public Result execute(String... args) {
        Display display = RobotiumInstrumentationBackend.solo.getCurrentActivity().getWindowManager().getDefaultDisplay();
        
        float x = Float.parseFloat(args[0]);
        float y = Float.parseFloat(args[1]);

        RobotiumInstrumentationBackend.solo.clickOnScreen(x, y);
        return Result.successResult();
    }

    @Override
    public String key() {
        return "touch_coordinate";
    }

}
