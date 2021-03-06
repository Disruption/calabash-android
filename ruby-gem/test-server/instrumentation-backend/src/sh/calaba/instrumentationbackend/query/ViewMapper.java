package sh.calaba.instrumentationbackend.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sh.calaba.instrumentationbackend.RobotiumInstrumentationBackend;
import sh.calaba.instrumentationbackend.query.ast.UIQueryUtils;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ViewMapper {

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object extractDataFromView(View v) {

		Map data = new HashMap();
		data.put("class", getClassNameForView(v));
		data.put("description", v.toString());
		data.put("contentDescription", getContentDescriptionForView(v));
		data.put("enabled", v.isEnabled());

		data.put("id", getIdForView(v));
		data.put("tag", getTagForView(v));

		Map<String,Integer> rect = getRectForView(v);

		data.put("rect", rect);

		if (v instanceof Button) {
			Button b = (Button) v;
			data.put("text", b.getText().toString());
		}
		if (v instanceof CheckBox) {
			CheckBox c = (CheckBox) v;
			data.put("checked", c.isChecked());
		}
		if (v instanceof TextView) {
			TextView t = (TextView) v;
			data.put("text", t.getText().toString());
		}
		return data;

	}

    public static Map<String, Integer> getRectForView(View v) {
        Map<String,Integer> rect = new HashMap<String,Integer>();
		int[] location = new int[2];
		v.getLocationOnScreen(location);

		rect.put("x", location[0]);
		rect.put("y", location[1]);
        
		rect.put("center_x", (int)(location[0] + v.getWidth()/2.0));
		rect.put("center_y", (int)(location[1] + v.getHeight()/2.0));

		rect.put("width", v.getWidth());
		rect.put("height", v.getHeight());

		return rect;
	}

	public static String getContentDescriptionForView(View v) {
		CharSequence description = v.getContentDescription();
		return description != null ? description.toString() : null;
	}

	public static String getClassNameForView(View v) {
		return v.getClass().getName();
	}

    public static String getIdForView(View v) {
        int id = v.getId();
        try {
            if(v.getResources() != null) {
                return v.getResources().getResourceEntryName(id);
            } else {
                return RobotiumInstrumentationBackend.solo.getCurrentActivity().getResources().getResourceEntryName(id);
            }
        } catch (Resources.NotFoundException e) {
            if(id == -1) {
                return null;
            }
            return "NoResourceEntry-" + Integer.toString(id);
        }
    }


    public static String getTagForView(View v) {
		if (v.getTag() instanceof String || v.getTag() instanceof Integer) {
			return v.getTag().toString();
		}
		return null;
	}

	@SuppressWarnings({"unchecked", "rawtypes" })
	public static Object mapView(Object o) {
		if (o instanceof View) {
			return extractDataFromView((View) o);
		}
		else if (o instanceof Map) {
			Map copy = new HashMap();
			for (Object e : ((Map) o).entrySet()) {
				Map.Entry entry = (Entry) e;
				Object value = entry.getValue();
				if (value instanceof View) {
					copy.put(entry.getKey(), UIQueryUtils.getId((View) value));
				}
				else {
					copy.put(entry.getKey(),entry.getValue());
				}
			}

			return copy;
		}
		else if (o instanceof CharSequence) {
			return o.toString();
		}
		return o;
	}

}
