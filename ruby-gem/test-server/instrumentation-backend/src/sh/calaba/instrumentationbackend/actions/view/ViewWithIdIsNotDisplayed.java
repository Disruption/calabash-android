package sh.calaba.instrumentationbackend.actions.view;

import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.RobotiumInstrumentationBackend;
import sh.calaba.instrumentationbackend.query.Query;
import sh.calaba.instrumentationbackend.query.QueryResult;

public class ViewWithIdIsNotDisplayed implements Action {
    @Override
    public Result execute(String... args) {
		final String id = args[0];
		final int timeoutInMillis = 1000 * (args.length > 1 ? Integer.parseInt(args[1]) : 0);
		final long startTime = System.currentTimeMillis();
		final Query query = new Query("* id:'" + id + "'"); 
		
		QueryResult queryResult = query.executeQuery();
		while (!queryResult.isEmpty()) {
			if (System.currentTimeMillis() > startTime + timeoutInMillis) {
				return Result.failedResult("View with id " + id + " still displaying");
			}
			RobotiumInstrumentationBackend.solo.sleep(100);
			queryResult = query.executeQuery();
		}
		
		return Result.successResult();
    }

    @Override
    public String key() {
        return "view_with_id_is_not_displayed";
    }
}
