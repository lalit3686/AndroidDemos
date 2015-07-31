package abhi.color;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class GestureDemo extends Activity {

	private int action_down_x = 0;
	private int action_up_x = 0;
	private int difference = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			action_down_x = (int) event.getX();
			Log.e("action", "ACTION_DOWN - ");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.e("action", "ACTION_MOVE - ");
			action_up_x = (int) event.getX();
			difference = action_down_x - action_up_x;
			break;
		case MotionEvent.ACTION_UP:
			Log.e("action", "ACTION_UP - ");
			calcuateDifference();
			action_down_x = 0;
			action_up_x = 0;
			difference = 0;
			break;
		}
		return true;
	}

	private void calcuateDifference() {
		if (difference == 0) {
		}
		if (difference > 75) {
			Log.e("action", "Left To Right");
		}
		if (difference < -75) {
			Log.e("action", "Right To Left");
		}
	}
}
