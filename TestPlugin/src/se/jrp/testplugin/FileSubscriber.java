package se.jrp.testplugin;

import java.util.HashMap;

public interface FileSubscriber {
	void onLoad(String id, HashMap<? extends Object, ? extends Object> map);
	HashMap<? extends Object, ? extends Object> onSave(String id);
}
