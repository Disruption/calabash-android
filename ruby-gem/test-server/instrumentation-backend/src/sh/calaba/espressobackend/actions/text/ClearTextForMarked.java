package sh.calaba.espressobackend.actions.text;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

import org.hamcrest.Matcher;
import org.hamcrest.text.StringContains;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.EditText;

public class ClearTextForMarked implements Action {
    @Override
    public Result execute(String... args) {
        String marked = args[1];
        int resourceId = EspressoInstrumentationBackend
                .getCurrentActivity()
                .getResources()
                .getIdentifier(
                        marked,
                        "id",
                        EspressoInstrumentationBackend.getCurrentActivity()
                                .getPackageName());

        Matcher<View> matcher = allOf(isAssignableFrom(EditText.class),
                anyOf(ViewMatchers.withId(resourceId),
                        ViewMatchers.withText(StringContains.containsString(marked)),
                        ViewMatchers.withContentDescription(StringContains.containsString(marked))));
        
        Espresso.onView(matcher).perform(ViewActions.clearText());

        return Result.successResult();
    }

    @Override
    public String key() {
        return "clear_text_for_marked";
    }
}
