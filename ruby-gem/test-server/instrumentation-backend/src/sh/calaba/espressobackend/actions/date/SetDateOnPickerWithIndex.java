package sh.calaba.espressobackend.actions.date;


import java.util.Date;
import java.text.SimpleDateFormat;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import sh.calaba.espressobackend.matchers.WithIndex;
import sh.calaba.espressobackend.query.espresso.DatePickerSetter;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.DatePicker;


public class SetDateOnPickerWithIndex implements Action {

    @SuppressWarnings("unchecked")
	@Override
    public Result execute(String... args) {
    	try {
	        int expectedIndex = Integer.parseInt(args[0]);
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = formatter.parse(args[1]);
	        Matcher<View> matcher = Matchers.allOf(ViewMatchers.isAssignableFrom(DatePicker.class), 
	        		new WithIndex(expectedIndex));
	        
	        Espresso.onView(matcher).perform(new DatePickerSetter(date));
	
	        return Result.successResult();
    	} catch (Exception e) {
    		return Result.failedResult();
    	}
    }

    @Override
    public String key() {
        return "set_date_on_picker";
    }
    
}