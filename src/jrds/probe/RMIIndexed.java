package jrds.probe;

import java.util.ArrayList;
import java.util.List;

public class RMIIndexed extends RMI implements IndexedProbe {
	private String index;
	private String label;

	public void configure(String indexKey) {
		this.index = indexKey;
		List<String> l = new ArrayList<String>(1);
		l.add(indexKey);
		setArgs(l);
	}
	public void configure(Integer port, Boolean local) {
		this.index = port.toString();
		List<Comparable<?>> l = new ArrayList<Comparable<?>> (2);
		l.add(port);
		l.add(local);
		setArgs(l);
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
