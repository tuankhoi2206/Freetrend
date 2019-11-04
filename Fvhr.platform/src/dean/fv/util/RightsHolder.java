package fv.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Store FV Right info for every login user.
 * 
 * @author Hieu *
 */
public class RightsHolder {
	private static Map<String, RightManagement> rightsMap = new HashMap<String, RightManagement>();

	public void put(String key, RightManagement rightInfo) {
		rightsMap.put(key, rightInfo);
	}

	public void remove(String key) {
		rightsMap.remove(key);
	}

	public boolean check(String key) {
		return rightsMap.containsKey(key);
	}

	public void update(String key, RightManagement rightInfo) {
		if (rightsMap.containsKey(key)) {
			rightsMap.remove(key);
		}
		rightsMap.put(key, rightInfo);
	}

	public RightManagement getRights(String key) {
		return rightsMap.get(key);
	}
}
