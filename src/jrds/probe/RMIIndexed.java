package jrds.probe;

import java.util.ArrayList;
import java.util.List;

public class RMIIndexed extends RMI implements IndexedProbe {
	private String index;
	private String label;
	public RMIIndexed(String indexKey) {
		super();
		this.index = indexKey;
		List l = new ArrayList(1);
		l.add(indexKey);
		setArgs(l);
	}
	public RMIIndexed(Integer port, Boolean local) {
		super();
		this.index = port.toString();
		List l = new ArrayList (2);
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
