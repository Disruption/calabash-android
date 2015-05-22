package sh.calaba.espressobackend.actions.activity;

import android.content.pm.ActivityInfo;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

public class SetActivityOrientation implements Action {

    @Override
    public Result execute(String... args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("No orientation provided. Use 'landscape' or 'portrait'");
        }
        String orientation = args[0].toLowerCase();

        if (orientation.equals("landscape")) {
            EspressoInstrumentationBackend.getCurrentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if(orientation.equals("portrait")) {
            EspressoInstrumentationBackend.getCurrentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            throw new IllegalArgumentException("Invalid orientation '" + orientation + "'. Use 'landscape' or 'portrait'");
        }
        // Wait for orientation change to happen.
        EspressoInstrumentationBackend.instrumentation.waitForIdleSync();

        return Result.successResult();
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String key() {
        return "set_activity_orientation";
    }
}
