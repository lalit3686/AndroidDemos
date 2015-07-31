package abhi.color;

import android.app.Application;

public class MyApplication extends Application{

	private String Name;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
}
