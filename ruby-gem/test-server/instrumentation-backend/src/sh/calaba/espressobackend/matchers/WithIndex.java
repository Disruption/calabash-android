package sh.calaba.espressobackend.matchers;


import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class WithIndex extends TypeSafeMatcher<View> {

    private final int expectedIndex;
    private int currentIndex = 0;

    public WithIndex(int expectedIndex) {
        this.expectedIndex = expectedIndex;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("Captures the views on the onView so it can be used to find views by querying");
    }

    @Override
    public boolean matchesSafely(View view) {
        return currentIndex++ == expectedIndex;
    }
}