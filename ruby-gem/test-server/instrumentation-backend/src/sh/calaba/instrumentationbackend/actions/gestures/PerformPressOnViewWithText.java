package sh.calaba.instrumentationbackend.actions.gestures;

import android.view.View;

import android.support.test.espresso.action.Tap;

import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.RobotiumInstrumentationBackend;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.query.Query;
import sh.calaba.instrumentationbackend.query.QueryResult;


public class PerformPressOnViewWithText implements Action {

    @Override
    public Result execute(String... args) {

        String text = args[0];
        String type = args[1];

        final int timeoutInMillis = 1000 * (args.length > 1 ? Integer.parseInt(args[1]) : 0);
        final long startTime = System.currentTimeMillis();
        final Query query = new Query("* {text CONTAINS[c] '" + text + "'}");

        QueryResult queryResult = query.executeQuery();
        while (queryResult.isEmpty()) {
            if (System.currentTimeMillis() > startTime + timeoutInMillis) {
                return Result.failedResult("View with text " + text + " not found");
            }
            RobotiumInstrumentationBackend.solo.sleep(100);
            queryResult = query.executeQuery();
        }

        View view = (View) queryResult.getResult().get(0);
        final int[] screenPosition = new int[2];
        view.getLocationOnScreen(screenPosition);

        Tap typeOfTap;
        if (type == null || type.equals("SINGLE")) {
            RobotiumInstrumentationBackend.solo.clickOnScreen(screenPosition[0], screenPosition[1]);
        } else if (type.equals("LONG")) {
            RobotiumInstrumentationBackend.solo.clickLongOnScreen(screenPosition[0], screenPosition[1]);
        } else if (type.equals("DOUBLE")) {
            RobotiumInstrumentationBackend.solo.doubleTapOnScreen(screenPosition[0], screenPosition[1]);
        } else {
            return Result.failedResult("Unrecognized press type " + type + ". Valid values are SINGLE LONG or DOUBLE");
        }

        return Result.successResult();
    }

    @Override
    public String key() {
        return "press_view_with_text";
    }

}
