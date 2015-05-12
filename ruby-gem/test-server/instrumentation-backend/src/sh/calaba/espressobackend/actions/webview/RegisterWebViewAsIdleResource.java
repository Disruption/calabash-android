package sh.calaba.espressobackend.actions.webview;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.TestHelpers;
import sh.calaba.espressobackend.actions.Action;
import sh.calaba.espressobackend.idleResource.WebViewIdleResource;
import sh.calaba.espressobackend.query.espresso.AllViewsCaptorMatcher;
import sh.calaba.espressobackend.query.espresso.ViewCaptor;
import android.view.View;
import android.webkit.WebView;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;

public class RegisterWebViewAsIdleResource implements Action {

	@Override
	public Result execute(String... args) {
		final ViewCaptor viewCaptor = new ViewCaptor();
		final String webViewId = args == null || args.length == 0 ? ""
				: args[0];

		int resourceId = TestHelpers.getIdFromString(webViewId);
		WebView webView = null;
		if (resourceId > 0) {
			Espresso.onView(ViewMatchers.withId(resourceId))
					.perform(viewCaptor);
			webView = (WebView) viewCaptor.getCapturedView();
		} else {
			AllViewsCaptorMatcher allViewsCaptorMatcher = new AllViewsCaptorMatcher();
			Espresso.onView(allViewsCaptorMatcher).perform(viewCaptor);
			for (View view : allViewsCaptorMatcher.getCapturedViews()) {
				try {
					if (view instanceof WebView) {
						webView = (WebView) view;
						break;
					}
				} catch (ClassCastException cce) {
					// Ignored
				}
			}
		}
		Espresso.registerIdlingResources(new WebViewIdleResource(webView));

		return Result.successResult();
	}

	@Override
	public String key() {
		return "register_webview_as_idle_resource";
	}

}
