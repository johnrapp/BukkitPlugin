package se.jrp.testplugin;

import java.util.HashMap;

public interface FileListener {
	void onLoad(HashMap<? extends Object, ? extends Object> map);
	HashMap<? extends Object, ? extends Object> onSave();
}
