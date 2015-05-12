package sh.calaba.espressobackend.actions.map;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

/**
 * @author Nicholas Albion
 */
public class SetMapZoom implements Action {

    @Override
    public Result execute(String... args) {
        if ("in".equals(args[0])) {
            return new Result(EspressoInstrumentationBackend.mapViewUtils.zoomIn());
        } else if ("out".equals(args[0])) {
            return new Result(EspressoInstrumentationBackend.mapViewUtils.zoomOut());
        }

        int zoomLevel = Integer.parseInt(args[0]);
        int newZoom = EspressoInstrumentationBackend.mapViewUtils.setZoom(zoomLevel);

        if (newZoom == zoomLevel) {
            return Result.successResult();
        } else {
            return new Result(false, "Requested zoom level: " + zoomLevel + " but current zoom level is " + newZoom);
        }
    }

    @Override
    public String key() {
        return "set_map_zoom";
    }
}