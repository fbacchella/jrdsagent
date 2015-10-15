package jrds.probe;

import java.util.ArrayList;
import java.util.List;

import jrds.factories.ProbeBean;

@ProbeBean({"index"})
public class MultiNoKeys extends RMI implements IndexedProbe {

    String index = null;

    @Override
    public Boolean configure() {
        List<Object> args = new ArrayList<Object>();

        // The colon key index
        Integer keyIndex = jrds.Util.parseStringNumber(getPd().getSpecific("keyIndex"), -1);
        args.add(keyIndex);            

        // The regex to use as a separator
        String separator = getPd().getSpecific("separator");
        if(separator == null || separator.isEmpty()) {
            separator = "\\s+";
        }
        args.add(separator);

        if(getPd().getSpecific("remote") == null) {
            getPd().addSpecific("remote", "jrds.agent.linux.MultiNoKeys");
        }
        if(getPd().getSpecific("remoteSpecifics") == null) {
            getPd().addSpecific("remoteSpecifics", "statFile");
        }
        // Only set args if one parsing specific is set, so old jrdsagent and old probes are still usable
        if(getPd().getSpecific("separator") != null || getPd().getSpecific("keyIndex") != null) {
            setArgs(args);
        }
        return super.configure();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String getIndexName() {
        return index;
    }

}
