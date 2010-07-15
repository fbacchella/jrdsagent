package jrds.probe;

import java.util.ArrayList;
import java.util.List;

public class RMIIndexed extends RMI implements IndexedProbe {
	private String index;
	private String label;

	public Boolean configure(String indexKey) {
		if(!configure()) {
			return false;
		}
		this.index = indexKey;
		List<Object> l = new ArrayList<Object>(1);
		l.add(indexKey);
		setArgs(l);
		return true;
	}

	public Boolean configure(Integer port, String indexKey) {
		return configure(indexKey);
	}
	
	public Boolean configure(Integer port, Boolean local) {
		if(!configure()) {
			return false;
		}
		this.index = port.toString();
		List<Comparable<?>> l = new ArrayList<Comparable<?>> (2);
		l.add(port);
		l.add(local);
		setArgs(l);
		return true;
	}

	public String getIndexName() {
		return index;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
