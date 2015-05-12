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
import android.widget.SearchView;

public class EnterSearchForIndex implements Action {
    @Override
    public Result execute(String... args) {
        String text = args[0];
        int index = Integer.parseInt(args[1]);
        Matcher<View> matcher = allOf(isAssignableFrom(SearchView.class), new WithIndex(index));
        
		Espresso.onView(matcher).perform(ViewActions.typeText(text));
        
        return Result.successResult();
    }

    @Override
    public String key() {
        return "enter_search_for_index";
    }
}
