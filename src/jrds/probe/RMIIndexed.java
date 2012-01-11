package jrds.probe;

import java.util.ArrayList;
import java.util.List;

import jrds.Util;
import jrds.factories.ProbeBean;

@ProbeBean({"index"})
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

    /**
     * A generic configurator that pass directly the elements of list to the remote probes.
     * The index will used the index template defined in the probedesc
     * @param args the argument of the remote probe
     * @return true if configuration succeeds
     */
    public Boolean configure(List<?> args) {
        if(!super.configure(args)) {
            return false;
        }
        index = Util.parseTemplate(getPd().getIndex(), this, args);
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

    /**
     * @return the index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(String index) {
        this.index = index;
    }
}
