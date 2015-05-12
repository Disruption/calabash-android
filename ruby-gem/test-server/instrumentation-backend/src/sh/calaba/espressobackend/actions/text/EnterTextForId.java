package sh.calaba.espressobackend.actions.text;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

import org.hamcrest.Matcher;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.view.View;
import android.widget.EditText;

public class EnterTextForId implements Action {
    @Override
    public Result execute(String... args) {
        String text = args[0];
        String id = args[1];
        int resourceId = EspressoInstrumentationBackend
                .getCurrentActivity()
                .getResources()
                .getIdentifier(
                        id,
                        "id",
                        EspressoInstrumentationBackend.getCurrentActivity()
                                .getPackageName());
        Matcher<View> matcher = allOf(isAssignableFrom(EditText.class), withId(resourceId));
        
		Espresso.onView(matcher).perform(ViewActions.typeText(text));

        return Result.successResult();
    }

    @Override
    public String key() {
        return "enter_text_for_id";
    }
}
