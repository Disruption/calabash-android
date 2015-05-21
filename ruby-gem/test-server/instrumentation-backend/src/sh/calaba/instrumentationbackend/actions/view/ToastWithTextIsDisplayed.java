package sh.calaba.instrumentationbackend.actions.view;

import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.RobotiumInstrumentationBackend;
import sh.calaba.instrumentationbackend.query.Query;
import sh.calaba.instrumentationbackend.query.QueryResult;

public class ToastWithTextIsDisplayed implements Action {
    @Override
    public Result execute(String... args) {
		final String text = args[0];
		final int timeoutInMillis = 1000 * (args.length > 1 ? Integer.parseInt(args[1]) : 0);
		final long startTime = System.currentTimeMillis();
		final Query query = new Query("* {text CONTAINS[c] '"+text+"'}"); 
		
		QueryResult queryResult = query.executeQuery();
		while (queryResult.isEmpty()) {
			if (System.currentTimeMillis() > startTime + timeoutInMillis) {
				return Result.failedResult("View with text " + text + " not found");
			}
			RobotiumInstrumentationBackend.solo.sleep(100);
			queryResult = query.executeQuery();
		}
		
		return Result.successResult();
    }

    @Override
    public String key() {
        return "toast_with_text_is_displayed";
    }
}
