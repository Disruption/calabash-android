package sh.calaba.espressobackend.actions.map;


import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

/**
 * Allows the test script to retreive a specific marker by title
 *
 * @author Nicholas Albion
 */
public class GetMapMarker implements Action {

    @Override
    public Result execute(String... args) {
        String title = args[0];
        String marker = EspressoInstrumentationBackend.mapViewUtils.getMarkerItem(title);

        if (marker == null) {
            return new Result(false, "Could not find marker " + title);
        } else {
            return new Result(true, marker);
        }
    }

    @Override
    public String key() {
        return "get_map_marker";
    }
}
