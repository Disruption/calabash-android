package sh.calaba.espressobackend.actions.date;


import java.util.Date;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.text.StringContains;

import sh.calaba.espressobackend.Result;
import sh.calaba.espressobackend.actions.Action;
import sh.calaba.espressobackend.matchers.WithIndex;
import sh.calaba.espressobackend.query.espresso.DatePickerSetter;
import android.view.View;
import android.widget.DatePicker;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;


public class SetDateOnPickerWithDescription implements Action {

    @SuppressWarnings("unchecked")
	@Override
    public Result execute(String... args) {
    	
        String contentDescription = args[0];
        Date date = new Date(Date.parse(args[1]));
        Matcher<View> matcher = Matchers.allOf(ViewMatchers.isAssignableFrom(DatePicker.class), 
        		ViewMatchers.withContentDescription(StringContains.containsString(contentDescription)));
        
        Espresso.onView(matcher).perform(new DatePickerSetter(date));

        return Result.successResult();
    }

    @Override
    public String key() {
        return "set_date_with_description";
    }
    
}
