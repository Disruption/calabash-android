package sh.calaba.instrumentationbackend.actions.gestures;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.RobotiumInstrumentationBackend;
import sh.calaba.instrumentationbackend.query.Query;
import sh.calaba.instrumentationbackend.query.QueryResult;

public class PressLinkOnTextViewWithId implements Action {

    @Override
    public Result execute(String... args) {

        String id = args[0];
        int linkIndex = args.length > 1 ? Integer.parseInt(args[1]) : 0;

        final int timeoutInMillis = 1000 * (args.length > 1 ? Integer.parseInt(args[1]) : 0);
        final long startTime = System.currentTimeMillis();
        final Query query = new Query("* {id:'" + id + "'}");

        QueryResult queryResult = query.executeQuery();
        while (queryResult.isEmpty()) {
            if (System.currentTimeMillis() > startTime + timeoutInMillis) {
                return sh.calaba.instrumentationbackend.Result.failedResult("View with id " + id + " not found");
            }
            RobotiumInstrumentationBackend.solo.sleep(100);
            queryResult = query.executeQuery();
        }

        View view = (View) queryResult.getResult().get(0);

        if (!(view instanceof TextView)) {
            return Result.failedResult("View to press link on must inherit from TextView");
        }

        TextView textView = (TextView) view;
        if (!(textView.getText() instanceof Spanned)) {
            return Result.failedResult("TextView text field is not a Spanned instance");
        }
        ClickableSpan[] clickableSpans =((Spanned)  textView.getText()).getSpans(0, textView.getText().length(), ClickableSpan.class);
        if (clickableSpans.length <= linkIndex) {
            return Result.failedResult("Found textview does not have expected link, tried to press link at position " + linkIndex + " but only found " + clickableSpans.length + " links");
        }

        clickableSpans[linkIndex].onClick(textView);

        return Result.successResult();
    }

    @Override
    public String key() {
        return "press_link_on_textview_with_id";
    }

}
