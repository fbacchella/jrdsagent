package jrds.probe;

import java.util.ArrayList;
import java.util.List;

import jrds.factories.ProbeBean;

@ProbeBean({"index"})
public class MultiNoKeys extends RMI implements IndexedProbe {

    String index = null;

    @Override
    public Boolean configure() {
        List<Object> args = new ArrayList<Object>(2);

        // The colon key index
        Integer keyIndex = jrds.Util.parseStringNumber(getPd().getSpecific("keyIndex"), 0);
        args.add(keyIndex);

        // The regex to use as a separator
        String separator = getPd().getSpecific("separator");
        if(separator != null && ! separator.isEmpty()) {
            args.add(separator);
        } else {
            args.add("\\s+");
        }
        setArgs(args);

        if(getPd().getSpecific("remote") == null) {
            getPd().addSpecific("remote", "jrds.agent.linux.MultiNoKeys");
        }
        if(getPd().getSpecific("remoteSpecifics") == null) {
            getPd().addSpecific("remoteSpecifics", "statFile");
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
