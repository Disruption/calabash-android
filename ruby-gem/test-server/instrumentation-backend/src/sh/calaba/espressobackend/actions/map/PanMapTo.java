package sh.calaba.espressobackend.actions.map;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

/**
 * Center on lat, lon
 *
 * @author Nicholas Albion
 */
public class PanMapTo implements Action {

    @Override
    public Result execute(String... args) {
        EspressoInstrumentationBackend.mapViewUtils.panTo(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
        return Result.successResult();
    }

    @Override
    public String key() {
        return "pan_map_to";
    }
}
