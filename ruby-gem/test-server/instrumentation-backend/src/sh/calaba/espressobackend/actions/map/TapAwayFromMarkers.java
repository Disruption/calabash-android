package sh.calaba.espressobackend.actions.map;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

/**
 * An optional "step" parameter lets the test script specify the number of pixels to increment down/across when
 * searching for a marker - defaults to 5
 *
 * @author Nicholas Albion
 */
public class TapAwayFromMarkers implements Action {

    @Override
    public Result execute(String... args) {
        int step = (args.length == 0) ? 5 : Integer.parseInt(args[0]);
        if (EspressoInstrumentationBackend.mapViewUtils.tapAwayFromMarkerItems(step)) {
            return Result.successResult();
        }
        return new Result(false, "Could not find any where to tap away from markers");
    }

    @Override
    public String key() {
        return "tap_map_away_from_markers";
    }
}
