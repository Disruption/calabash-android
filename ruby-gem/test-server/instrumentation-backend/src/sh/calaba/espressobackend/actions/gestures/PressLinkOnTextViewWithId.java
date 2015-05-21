package sh.calaba.espressobackend.actions.gestures;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import sh.calaba.espressobackend.query.espresso.ViewCaptor;

public class PressLinkOnTextViewWithId implements Action {

    @Override
    public Result execute(String... args) {

        String id = args[0];
        int linkIndex = args.length > 1 ? Integer.parseInt(args[1]) : 0;
        int resourceId = EspressoInstrumentationBackend
            .getCurrentActivity()
            .getResources()
            .getIdentifier(
                id,
                "id",
                EspressoInstrumentationBackend.getCurrentActivity()
                    .getPackageName());
        int androidResourceId = EspressoInstrumentationBackend
            .getCurrentActivity()
            .getResources()
            .getIdentifier(
                id,
                "id",
                "android");
        ViewCaptor viewCaptor = new ViewCaptor();
        // We try with the id as android resource id. If that throws an exception, we try with the id as our app's package id. If both fail, the step fails
        try {
            Espresso.onView(ViewMatchers.withId(resourceId)).perform(viewCaptor);
        }
        catch (Exception e) {
            Espresso.onView(ViewMatchers.withId(androidResourceId)).perform(viewCaptor);
        }

        View view = viewCaptor.getCapturedView();

        if (!(view instanceof TextView)) {
            return Result.failedResult("View to press link on must inherit from TextView");
        }

        TextView textView = (TextView) view;
        if (!(textView.getText() instanceof Spanned)) {
            return Result.failedResult("TextView text field is not a Spanned instance");
        }

        ClickableSpan[] clickableSpans = ((Spanned) textView.getText()).getSpans(0, textView.getText().length(), ClickableSpan.class);
        if (clickableSpans.length <= linkIndex) {
            return Result.failedResult("Found textview does not have expected clickable span, tried to press link at position " + linkIndex + " but only found " + clickableSpans.length + " clickable spans");
        }

        clickableSpans[linkIndex].onClick(textView);

        return Result.successResult();
    }

    @Override
    public String key() {
        return "press_link_on_textview_with_id";
    }

}
