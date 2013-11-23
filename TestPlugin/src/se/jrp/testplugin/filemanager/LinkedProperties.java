package se.jrp.testplugin.filemanager;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;

public class LinkedProperties extends Properties {

    private final LinkedHashSet<Object> keys = new LinkedHashSet<>();

	@Override
    public Enumeration<Object> keys() {
        return Collections.<Object>enumeration(keys);
    }

	@Override
    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }
}