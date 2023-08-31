package jrds.probe;

import java.util.ArrayList;
import java.util.List;

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

        return super.configure();
    }

    @Override
    protected void buildArgs(List<Object> args) {
        super.buildArgs(args);
        args.add(instance);
        List<String> counters = new ArrayList<>(getPd().getCollectMapping().keySet());
        args.add(counters);
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

}
