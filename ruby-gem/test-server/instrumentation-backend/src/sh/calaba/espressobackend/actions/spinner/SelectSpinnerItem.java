package sh.calaba.espressobackend.actions.spinner;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import org.hamcrest.text.StringContains;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

import static org.hamcrest.core.AnyOf.anyOf;

public class SelectSpinnerItem implements Action {
    @Override
    public Result execute(String... args) {
        String itemIdentifier = args[0];
        String marked = args[1];
        int resourceId = EspressoInstrumentationBackend
                .getCurrentActivity()
                .getResources()
                .getIdentifier(
                        marked,
                        "id",
                        EspressoInstrumentationBackend.getCurrentActivity()
                                .getPackageName());
        int itemResourceId = EspressoInstrumentationBackend
                .getCurrentActivity()
                .getResources()
                .getIdentifier(
                        itemIdentifier,
                        "id",
                        EspressoInstrumentationBackend.getCurrentActivity()
                                .getPackageName());

        Espresso.onView(
                anyOf(ViewMatchers.withId(resourceId),
                        ViewMatchers.withText(StringContains.containsString(marked)),
                        ViewMatchers.withContentDescription(StringContains.containsString(marked))))
                .perform(ViewActions.click());

        Espresso.onView(anyOf(ViewMatchers.withId(itemResourceId),
                ViewMatchers.withText(StringContains.containsString(itemIdentifier)),
                ViewMatchers.withContentDescription(StringContains.containsString(itemIdentifier))))
                .perform(ViewActions.click());

        return Result.successResult();
    }

    @Override
    public String key() {
        return "select_spinner_item";
    }
}
