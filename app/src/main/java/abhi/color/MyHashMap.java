package abhi.color;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MyHashMap extends LinkedHashMap<String, List<String>> {    
	private static final long serialVersionUID = 1L;

	public void put(String key, String value) {
        List<String> current = get(key);
        if (current == null) {
            current = new ArrayList<String>();
            super.put(key, current);
        }
        current.add(value);
    }
}
