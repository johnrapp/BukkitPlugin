package se.jrp.testplugin.filemanager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;

public class CustomProperties extends Properties {
    private final LinkedHashSet<Object> keys = new LinkedHashSet<>();
	private String comment = null;

	public CustomProperties() {
	}
	
	public CustomProperties(String comment) {
		this.comment = comment;
	}
	
	public void store(OutputStream out) throws IOException {
		super.store(out, comment);
	}
	
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