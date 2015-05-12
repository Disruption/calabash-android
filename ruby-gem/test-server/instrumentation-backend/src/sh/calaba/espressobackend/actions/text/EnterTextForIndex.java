package sh.calaba.espressobackend.actions.text;

import org.hamcrest.Matcher;

import android.view.View;
import android.widget.EditText;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import sh.calaba.espressobackend.matchers.WithIndex;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

public class EnterTextForIndex implements Action {
    @Override
    public Result execute(String... args) {
        String text = args[0];
        int index = Integer.parseInt(args[1]);
        
        Matcher<View> matcher = allOf(isAssignableFrom(EditText.class), new WithIndex(index));
		Espresso.onView(matcher).perform(ViewActions.typeText(text));
        
        return Result.successResult();
    }

    @Override
    public String key() {
        return "enter_text_for_index";
    }
}
