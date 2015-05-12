
package sh.calaba.espressobackend.actions.l10n;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;
import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;

/**
 * Action is getting the translated value of the provided #l10nKey and is
 * searching for the text and then clicking on it.
 * 
 * @author Dominik Dary
 * @see L10nHelper
 */
public class PressElement implements Action {
	public static final String BUTTON = "button";
	public static final String TOGGLE_BUTTON = "toggle_button";
	public static final String MENU_ITEM = "menu_item";

	@Override
	public Result execute(String... args) {
		String l10nKey = args[0];
		String elementType = args.length > 1 ? args[1] : null;
		String pckg = (args.length > 2)? args[2] : null;
		
		String myLocalizedString = L10nHelper.getValue(l10nKey, pckg);
		if (elementType == null 
				|| BUTTON.equalsIgnoreCase(elementType) 
				|| TOGGLE_BUTTON.equalsIgnoreCase(elementType)) {
			Espresso.onView(ViewMatchers.withText(myLocalizedString)).perform(ViewActions.click());
		} else if (MENU_ITEM.equalsIgnoreCase(elementType)) {
			Espresso.openActionBarOverflowOrOptionsMenu(EspressoInstrumentationBackend.instrumentation.getTargetContext());
			Espresso.onView(ViewMatchers.withText(myLocalizedString)).perform(ViewActions.click());
		} else {
			return Result.fromThrowable(new Throwable(
					"specified element type: '" + elementType
							+ " ' is not supported."));
		}

		return Result.successResult();
	}

	@Override
	public String key() {
		return "press_l10n_element";
	}

}
