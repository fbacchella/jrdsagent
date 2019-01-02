package jrds.probe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import jrds.probe.RMI;

public class Wmiprobe extends RMI {

    class BaseFields {
        public Double Frequency_Sys100NS;
        public Double Timestamp_Sys100NS;
    };

    @Override
    public Boolean configure() {
        if(getPd().getSpecific("remoteSpecifics") == null) {
            getPd().addSpecific("remoteSpecifics", "wbemClass");
        }
        if(getPd().getSpecific("remote") == null) {
            getPd().addSpecific("remote", "jrds.agent.windows.WmiAgent");
        }
        List<Object> args = new ArrayList<Object>();
        buildArgs(args);
        setArgs(args);
        log(Level.DEBUG, "remote args for this probe: %s", args);
        return super.configure();
    }

    protected void buildArgs(List<Object> args) {
        List<String> fields = new ArrayList<String>();
        for(String key: getPd().getCollectMapping().keySet()) {
            fields.add(key);
        }
        fields.add("Timestamp_Sys100NS");
        fields.add("Frequency_Sys100NS");
        args.add(fields);
    }

    @Override
    public Map<String, Number> filterValues(Map<String, Number> valuesList) {
        BaseFields bf = new BaseFields();
        bf.Timestamp_Sys100NS = valuesList.remove("Timestamp_Sys100NS").doubleValue();
        bf.Frequency_Sys100NS = valuesList.remove("Frequency_Sys100NS").doubleValue();

        return super.filterValues(valuesList);
    }

}
