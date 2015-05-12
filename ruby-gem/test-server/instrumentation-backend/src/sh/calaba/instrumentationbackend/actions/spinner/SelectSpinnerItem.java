package sh.calaba.instrumentationbackend.actions.spinner;

import android.view.View;
import android.widget.Spinner;

import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.RobotiumInstrumentationBackend;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.query.Query;
import sh.calaba.instrumentationbackend.query.QueryResult;

public class SelectSpinnerItem implements Action {
    @Override
    public Result execute(String... args) {
        String itemIdentifier = args[0];
        String marked = args[1];

        final int timeoutInMillis = 1000 * (args.length > 1 ? Integer.parseInt(args[1]) : 0);
        final long startTime = System.currentTimeMillis();
        final Query spinnerQuery = new Query("android.widget.Spinner marked:'" + marked + "'");

        QueryResult spinnerQueryResult = spinnerQuery.executeQuery();
        while (spinnerQueryResult.isEmpty()) {
            if (System.currentTimeMillis() > startTime + timeoutInMillis) {
                return Result.failedResult("Spinner marked as " + marked + " not found");
            }
            RobotiumInstrumentationBackend.solo.sleep(100);
            spinnerQueryResult = spinnerQuery.executeQuery();
        }

        Spinner spinner = (Spinner) spinnerQueryResult.getResult().get(0);
        spinner.performClick();

        final Query itemQuery = new Query("android.widget.PopupWindow$PopupViewContainer * marked:'" + itemIdentifier + "'");
        QueryResult itemQueryResult = itemQuery.executeQuery();

        while (itemQueryResult.isEmpty()) {
            if (System.currentTimeMillis() > startTime + timeoutInMillis) {
                return Result.failedResult("Spinner item marked as " + marked + " not found");
            }
            RobotiumInstrumentationBackend.solo.sleep(100);
            itemQueryResult = itemQuery.executeQuery();
        }

        View item = (View) itemQueryResult.getResult().get(0);
        item.performClick();

        return Result.successResult();
    }

    @Override
    public String key() {
        return "select_spinner_item";
    }
}
