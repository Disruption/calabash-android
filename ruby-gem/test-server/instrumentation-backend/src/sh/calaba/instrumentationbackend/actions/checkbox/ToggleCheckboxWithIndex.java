package sh.calaba.instrumentationbackend.actions.checkbox;


import android.widget.CheckBox;

import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.RobotiumInstrumentationBackend;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.query.Query;
import sh.calaba.instrumentationbackend.query.QueryResult;


public class ToggleCheckboxWithIndex implements Action {
    @Override
    public Result execute(String... args) {
        int index = Integer.parseInt(args[0]);
        final int timeoutInMillis = 1000 * (args.length > 1 ? Integer.parseInt(args[1]) : 0);
        final long startTime = System.currentTimeMillis();
        final Query query = new Query("android.widget.CheckBox index:" + index);

        QueryResult queryResult = query.executeQuery();
        while (queryResult.isEmpty()) {
            if (System.currentTimeMillis() > startTime + timeoutInMillis) {
                return Result.failedResult("CheckBox with index " + index + " not found");
            }
            RobotiumInstrumentationBackend.solo.sleep(100);
            queryResult = query.executeQuery();
        }

        CheckBox checkBox = (CheckBox) queryResult.getResult().get(0);
        checkBox.toggle();

        return Result.successResult();
    }

    @Override
    public String key() {
        return "toggle_checkbox_with_index";
    }
}
