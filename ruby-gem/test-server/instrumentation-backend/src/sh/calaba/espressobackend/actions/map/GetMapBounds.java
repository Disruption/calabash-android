package sh.calaba.espressobackend.actions.map;

import java.util.List;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

/**
 * A succesful response includes bonusInformation: [top, right, bottom, left] in decimal degrees
 *
 * @author Nicholas Albion
 */
public class GetMapBounds implements Action {

    @Override
    public Result execute(String... args) {
        List<String> bounds = EspressoInstrumentationBackend.mapViewUtils.getBounds();
        Result result = new Result(true);
        result.setExtras(bounds);
        return result;
    }

    @Override
    public String key() {
        return "get_map_bounds";
    }
}
