package jrds.probe;

import java.util.List;

import jrds.factories.ProbeBean;

@ProbeBean({"index"})
public class WmiProbeIndexed extends Wmiprobe implements IndexedProbe {
    private String index;
    private String label;

    @Override
    public Boolean configure() {
        if(getPd().getSpecific("remoteSpecifics") == null) {
            getPd().addSpecific("remoteSpecifics", "wbemClass, key");
        }
        if(getPd().getSpecific("remote") == null) {
            getPd().addSpecific("remote", "jrds.agent.windows.WmiAgentIndexed");
        }

        return super.configure();
    }
    
    @Override
    protected void buildArgs(List<Object> args) {
        args.add(index);
        super.buildArgs(args);
    }

    @Override
    public String getIndexName() {
        return index;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
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
