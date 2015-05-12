package sh.calaba.espressobackend.actions.softkey;


import android.support.test.espresso.Espresso;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;


public class GoBack implements Action {

    @Override
    public Result execute(String... args) {
        Espresso.pressBack();
        return Result.successResult();
    }

    @Override
    public String key() {
        return "go_back";
    }

}
