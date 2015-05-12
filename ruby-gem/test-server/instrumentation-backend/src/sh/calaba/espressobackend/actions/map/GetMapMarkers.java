package sh.calaba.espressobackend.actions.map;


import com.google.android.maps.ItemizedOverlay;

import java.util.List;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

/**
 * Allows the test script to retreive a list of markers on {@link ItemizedOverlay}s.
 * The optional arg can be used to require a specific number of markers
 *
 * @author Nicholas Albion
 */
public class GetMapMarkers implements Action {

    @Override
    public Result execute(String... args) {
        List<String> markers = EspressoInstrumentationBackend.mapViewUtils.getMarkerItems();

        Result result;
        if (args.length != 0) {
            int expectedNumberOfStops = Integer.parseInt(args[0]);

            if (expectedNumberOfStops != markers.size()) {
                result = new Result(false, "Expected " + expectedNumberOfStops + " markers, but found " + markers.size());
            } else {
                result = new Result(true);
            }
        } else {
            result = new Result(true);
        }

        for (String markerJson : markers) {
//        	Log.i("get_map_markers", markerJson);
            result.addBonusInformation(markerJson);
        }
        return result;
    }

    @Override
    public String key() {
        return "get_map_markers";
    }
}
