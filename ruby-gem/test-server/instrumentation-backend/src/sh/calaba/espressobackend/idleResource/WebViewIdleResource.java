package sh.calaba.espressobackend.idleResource;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;

import android.support.test.espresso.IdlingResource;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebViewIdleResource extends WebChromeClient implements IdlingResource {

    private static final int FINISHED = 100;

    private WebView webView;
    private ResourceCallback callback;

    public WebViewIdleResource(WebView webView) {
    	this.webView = webView;
    	EspressoInstrumentationBackend.instrumentation.runOnMainSync(new Runnable() {
			
			@Override
			public void run() {
				WebViewIdleResource.this.webView.setWebChromeClient(WebViewIdleResource.this);
			}
		});
    }

    @Override public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == FINISHED && view.getTitle() != null && callback != null) {
            callback.onTransitionToIdle();
        }
    }

    @Override public void onReceivedTitle(WebView view, String title) {
        if (view.getProgress() == FINISHED && callback != null) {
            callback.onTransitionToIdle();
        }
    }

    @Override public String getName() {
    	// Reason for adding the hashcode is that espresso has an issue registering several resources with the same name
    	// Check https://code.google.com/p/android-test-kit/issues/detail?id=65 for more details
        return "WebView idling resource " + webView.hashCode();
    }

    @Override public boolean isIdleNow() {
        final boolean isIdle = webView.getProgress() == FINISHED && webView.getTitle() != null;
        if (isIdle && callback != null) {
        	callback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.callback = resourceCallback;
        isIdleNow();
    }
}

