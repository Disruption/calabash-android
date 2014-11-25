package sh.calaba.espressobackend.actions.softkey;


import android.view.KeyEvent;

import com.jayway.android.robotium.solo.Solo;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;


public class RightKey implements Action {

    @Override
    public Result execute(String... args) {
    	EspressoInstrumentationBackend.instrumentation.sendCharacterSync(KeyEvent.KEYCODE_DPAD_RIGHT);
        return Result.successResult();
    }

    @Override
    public String key() {
        return "send_key_right";
    }

}
