package sh.calaba.espressobackend.actions.l10n;

import android.app.Activity;
import android.content.res.Resources;

import sh.calaba.espressobackend.EspressoInstrumentationBackend;

/**
 * Helper to access Android L10n files.
 *
 * @author Dominik Dary
 */
public class L10nHelper {
    /**
     * get the translated value based on the current active locale.
     *
     * @param l10nKey The l10n key to use
     * @param pckg    Optional package to find the resource, defaults to the application's package if null
     * @return The translated value.
     */
    public static String getValue(String l10nKey, String pckg) {

        final Activity activity = EspressoInstrumentationBackend.getCurrentActivity();
        final Resources resources = activity.getResources();

        if (pckg == null) {
            pckg = activity.getPackageName();
        }
        int resourceId = resources.getIdentifier(l10nKey, "string", pckg);

        return resources.getString(resourceId);
    }
}
