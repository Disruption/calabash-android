package sh.calaba.espressobackend;

import java.lang.reflect.Method;

import android.app.Activity;
import sh.calaba.espressobackend.actions.HttpServer;
import android.content.Context;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import android.test.InstrumentationTestRunner;

public class CalabashInstrumentationTestRunner extends AndroidJUnitRunner {
	@Override
    public void onCreate(Bundle arguments) {
		try {
			Context context = getTargetContext();
			Class<?> c = Class.forName("mono.MonoPackageManager");
			Method  method = c.getDeclaredMethod ("LoadApplication", Context.class, String.class, String[].class);
			method.invoke (null, context, null, new String[]{context.getApplicationInfo ().sourceDir});
			System.out.println("Calabash loaded Mono");
            EspressoInstrumentationBackend.mainActivity = Class.forName(arguments.getString("main_activity")).asSubclass(Activity.class);
		} catch (Exception e) {
			System.out.println("Calabash did not load Mono. This is only a problem if you are trying to test a Mono application");
		}

        // Start the HttpServer as soon as possible in a not-ready state
        HttpServer.instantiate(Integer.parseInt(arguments.getString("test_server_port")));

        EspressoInstrumentationBackend.testPackage = arguments.getString("target_package");

        Bundle extras = (Bundle)arguments.clone();
        extras.remove("target_package");
        extras.remove("main_activity");
        extras.remove("test_server_port");
        extras.remove("class");
        
        if (extras.isEmpty()) {
            extras = null;
        }

        EspressoInstrumentationBackend.extras = extras;

        try {
            EspressoInstrumentationBackend.mainActivityName = arguments.getString("main_activity");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        super.onCreate(arguments);

	}	
}
