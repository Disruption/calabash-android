package sh.calaba.instrumentationbackend.actions.date;


import java.util.Date;

import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.RobotiumInstrumentationBackend;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.query.Query;
import sh.calaba.instrumentationbackend.query.QueryResult;
import android.widget.DatePicker;


public class SetDateOnPickerWithIndex implements Action {

    @SuppressWarnings("unchecked")
	@Override
    public Result execute(String... args) {
    	
        final String index = args[0];
        final Date date = new Date(Date.parse(args[1]));
        final int timeoutInMillis = 1000 * (args.length > 1 ? Integer.parseInt(args[1]) : 0);
        final long startTime = System.currentTimeMillis();
        final Query query = new Query("android.widget.DatePicker index:'"+index+"'");

        QueryResult queryResult = query.executeQuery();
        while (queryResult.isEmpty()) {
            if (System.currentTimeMillis() > startTime + timeoutInMillis) {
                return Result.failedResult("DatePicker with index " + index + " not found");
            }
            RobotiumInstrumentationBackend.solo.sleep(100);
            queryResult = query.executeQuery();
        }

        DatePicker datePicker = (DatePicker) queryResult.getResult().get(0);
		datePicker.updateDate(date.getYear(), date.getMonth(), date.getDate());

        return Result.successResult();
    }

    @Override
    public String key() {
        return "set_date_with_index";
    }
    
}
