package jrds.probe;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import jrds.factories.ProbeBean;

@ProbeBean({"instance"})
public class PdhProbeUniqueInstance extends PdhProbe {
    private String instance;

    @Override
    public Boolean configure() {
        if (getPd().getSpecific("remoteSpecifics") == null) {
            getPd().addSpecific("remoteSpecifics", "object");
        }
        if (getPd().getSpecific("remote") == null) {
            getPd().addSpecific("remote", "jrds.agent.windows.pdh.PdhAgentUniqueInstance");
        }

        log(Level.DEBUG, "remote args for this probe: %s", args);
        return super.configure();
    }
    
    @Override
    protected void buildArgs(List<Object> args) {
        super.buildArgs(args);
        args.add(instance);
        List<String> counters = new ArrayList<String>();
        for (String key: getPd().getCollectStrings().keySet()) {
            counters.add(key);
        }
        args.add(counters);
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

}
