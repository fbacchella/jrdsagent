package jrds.probe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        List<String> fields = new ArrayList<String>();
        for(String key: getPd().getCollectStrings().keySet()) {
            fields.add(key);
        }
        fields.add("Timestamp_Sys100NS");
        fields.add("Frequency_Sys100NS");
        List<Object> args = new ArrayList<Object>(1);
        args.add(fields);
        setArgs(args);
        return super.configure();
    }

    @Override
    public Map<String, Number> filterValues(Map<String, Number> valuesList) {
        BaseFields bf = new BaseFields();
        bf.Timestamp_Sys100NS = valuesList.remove("Timestamp_Sys100NS").doubleValue();
        bf.Frequency_Sys100NS = valuesList.remove("Frequency_Sys100NS").doubleValue();

        return super.filterValues(valuesList);
    }

}
