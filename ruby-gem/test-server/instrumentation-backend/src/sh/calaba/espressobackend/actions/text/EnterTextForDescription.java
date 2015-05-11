package sh.calaba.espressobackend.actions.text;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.core.AllOf.allOf;

import org.hamcrest.Matcher;
import org.hamcrest.text.StringContains;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.EditText;

public class EnterTextForDescription implements Action {
    @Override
    public Result execute(String... args) {
        String text = args[0];
        String description = args[1];
        
        Matcher<View> matcher = allOf(isAssignableFrom(EditText.class), ViewMatchers.withContentDescription(StringContains.containsString(description)));
		Espresso.onView(matcher).perform(ViewActions.typeText(text));

        return Result.successResult();
    }

    @Override
    public String key() {
        return "enter_text_for_description";
    }
}
