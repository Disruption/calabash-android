package sh.calaba.espressobackend.actions.text;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.core.AllOf.allOf;

import org.hamcrest.Matcher;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import sh.calaba.espressobackend.matchers.WithIndex;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.view.View;
import android.widget.EditText;

public class ClearTextForIndex implements Action {
    @Override
    public Result execute(String... args) {
        int index = Integer.parseInt(args[0]);
        Matcher<View> matcher = allOf(isAssignableFrom(EditText.class), new WithIndex(index));
        Espresso.onView(matcher)
                .perform(ViewActions.clearText());
        
        return Result.successResult();
    }

    @Override
    public String key() {
        return "clear_text_for_index";
    }
}
