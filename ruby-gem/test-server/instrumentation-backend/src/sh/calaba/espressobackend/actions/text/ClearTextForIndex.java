package sh.calaba.espressobackend.actions.text;

import android.widget.EditText;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import sh.calaba.espressobackend.matchers.WithIndex;

import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.core.AllOf.allOf;

public class ClearTextForIndex implements Action {
    @Override
    public Result execute(String... args) {
        int index = Integer.parseInt(args[0]);
        Espresso.onView(allOf(isAssignableFrom(EditText.class), new WithIndex(index)))
                .perform(ViewActions.clearText());
        
        return Result.successResult();
    }

    @Override
    public String key() {
        return "clear_text_for_index";
    }
}
