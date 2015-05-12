package sh.calaba.espressobackend.actions.map;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

public class GetMapZoom implements Action {

    @Override
    public Result execute(String... args) {
        int zoomLevel = EspressoInstrumentationBackend.mapViewUtils.getZoom();
        return new Result(true, Integer.toString(zoomLevel));
    }

    @Override
    public String key() {
        return "get_map_zoom";
    }
}
