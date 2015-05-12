package sh.calaba.instrumentationbackend.actions.time;


import java.sql.Time;

import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.RobotiumInstrumentationBackend;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.query.Query;
import sh.calaba.instrumentationbackend.query.QueryResult;
import android.widget.TimePicker;


public class SetTimeOnPickerWithDescription implements Action {

    @SuppressWarnings("unchecked")
	@Override
    public Result execute(String... args) {
    	
        final String description = args[0];
        final Time time = new Time(Time.parse(args[1]));
        final int timeoutInMillis = 1000 * (args.length > 1 ? Integer.parseInt(args[1]) : 0);
        final long startTime = System.currentTimeMillis();
        final Query query = new Query("android.widget.TimePicker {contentDescription LIKE[c] '" + description + "'");

        QueryResult queryResult = query.executeQuery();
        while (queryResult.isEmpty()) {
            if (System.currentTimeMillis() > startTime + timeoutInMillis) {
                return Result.failedResult("TimePicker with description " + description + " not found");
            }
            RobotiumInstrumentationBackend.solo.sleep(100);
            queryResult = query.executeQuery();
        }

        TimePicker timePicker = (TimePicker) queryResult.getResult().get(0);
        timePicker.setCurrentHour(time.getHours());
        timePicker.setCurrentMinute(time.getMinutes());

        return Result.successResult();
    }

    @Override
    public String key() {
        return "set_time_with_description";
    }
    
}
