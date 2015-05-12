package sh.calaba.espressobackend.actions.softkey;


import android.view.KeyEvent;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.TestHelpers;
import sh.calaba.espressobackend.actions.Action;


public class PressMenu implements Action {

    @Override
    public Result execute(String... args) {
        EspressoInstrumentationBackend.instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
        TestHelpers.wait(1);
        return Result.successResult();
    }

    @Override
    public String key() {
        return "press_menu";
    }

}
