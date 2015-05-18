package sh.calaba.espressobackend;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import sh.calaba.espressobackend.actions.Actions;
import sh.calaba.espressobackend.actions.HttpServer;
import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.test.internal.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.jayway.android.robotium.solo.PublicViewFetcher;
import com.jayway.android.robotium.solo.SoloEnhanced;

public class EspressoInstrumentationBackend extends ActivityInstrumentationTestCase2<Activity> {
    public static String testPackage;
    public static String mainActivityName;
    public static Class<? extends Activity> mainActivity;
    public static Bundle extras;
    
    private static final String TAG = "EspressoInstrumentationBackend";
    
    public static Instrumentation instrumentation;
    public static Actions actions;
    public static EspressoMapViewUtils mapViewUtils;
	private static Activity currentActivity;

    public EspressoInstrumentationBackend() {
        super((Class<Activity>)mainActivity);
    }

    @Override
    public Activity getActivity() {
        if (mainActivity != null) {
            return super.getActivity();
        }
        
        try {
            setMainActivity(Class.forName(mainActivityName).asSubclass(Activity.class));
            return super.getActivity();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setMainActivity(Class<? extends Activity> mainActivity) {
        try {
            Field mActivityClass = ActivityInstrumentationTestCase2.class.getDeclaredField("mActivityClass");
            mActivityClass.setAccessible(true);
            mActivityClass.set(this, mainActivity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setClassName(testPackage, mainActivityName);
        i.addCategory("android.intent.category.LAUNCHER");
        i.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        if (extras != null) {
            i.putExtras(extras);
        }

        setActivityIntent(i);

        actions = new Actions(getInstrumentation(), this);
        instrumentation = getInstrumentation();
    }

    /**
     * Here to have JUnit3 start the EspressoInstrumentationBackend
     */

    public void testHook() throws Exception {

        final AtomicReference<Activity> activityReference = new AtomicReference<Activity>();
        Thread activityStarter = new Thread() {
            public void run() {
                activityReference.set(getActivity());
            }
        };
        activityStarter.start();
        activityStarter.join(10000);

        Activity activity = null;
        if (activityReference.get() != null) {
            activity = activityReference.get();
            System.out.println("testHook: Activity set to: " + activity);
        } else {
            System.out.println("testHook: Activity not set");
            try {

                Field mQueue = Looper.getMainLooper().getClass().getDeclaredField("mQueue");
                mQueue.setAccessible(true);
                MessageQueue messageQueue = (MessageQueue)mQueue.get(Looper.getMainLooper());

                Field f = messageQueue.getClass().getDeclaredField("mIdleHandlers");
                f.setAccessible(true);
                List<?> waiters = (List<?>)f.get(messageQueue);
                for(Object o : waiters) {
                    Class<?> activityGoingClazz = o.getClass();
                    if (!activityGoingClazz.getName().equals("android.app.Instrumentation$ActivityGoing")) {
                        continue;
                    }


                    Field mWaiterField = activityGoingClazz.getDeclaredField("mWaiter");
                    mWaiterField.setAccessible(true);
                    Object waiter = mWaiterField.get(o);
                    Class<?> activityWaiterClazz = waiter.getClass();

                    Field activityField = activityWaiterClazz.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    activity = (Activity)activityField.get(waiter);

                    instrumentation.addMonitor(new Instrumentation.ActivityMonitor(activity.getClass().getName(), null, false));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (activity != null) {
            mapViewUtils = new EspressoMapViewUtils();
            setActivity(activity);

            HttpServer httpServer = HttpServer.getInstance();
            httpServer.setReady();
            httpServer.waitUntilShutdown();
            System.exit(0);
        } else {
            throw new RuntimeException("Could not get detect the first Activity");
        }
    }

    @Override
    public void tearDown() throws Exception {
        HttpServer httpServer = HttpServer.getInstance();
        httpServer.stop();

        System.out.println("Finishing");

        removeTestLocationProviders(this.getActivity());

        this.getActivity().finish();
        super.tearDown();

    }

    public static void log(String message) {
        Log.i(TAG, message);
    }

    public static void logError(String message) {
        Log.e(TAG, message);
    }
    
    public static Activity getCurrentActivity(){
    	if (Looper.myLooper() == Looper.getMainLooper()) {
            Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
            for (Activity activity : resumedActivities){
                currentActivity = activity;
                break;
            }	
    	} else {
	        instrumentation.runOnMainSync(new Runnable() {
	            public void run() {
	                Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
	                for (Activity activity : resumedActivities){
	                    currentActivity = activity;
	                    break;
	                }
	            }
	        });
    	}
        return currentActivity;
    }

    private void removeTestLocationProviders(Activity activity) {
        int hasPermission = getCurrentActivity().checkCallingOrSelfPermission(Manifest.permission.ACCESS_MOCK_LOCATION);

        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationService = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            for (final String provider : locationService.getAllProviders()) {
                locationService.removeTestProvider(provider);
            }
        }
    }
}
