package jrds.probe;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.event.Level;

public class Wmiprobe extends RMI {

    @Override
    public Boolean configure() {
        if(getPd().getSpecific("remoteSpecifics") == null) {
            getPd().addSpecific("remoteSpecifics", "wbemClass");
        }
        if(getPd().getSpecific("remote") == null) {
            getPd().addSpecific("remote", "jrds.agent.windows.WmiAgent");
        }
        List<Object> args = new ArrayList<>();
        buildArgs(args);
        setArgs(args);
        log(Level.DEBUG, "remote args for this probe: %s", args);
        return super.configure();
    }

    protected void buildArgs(List<Object> args) {
        List<String> fields = new ArrayList<>();
        for(String key: getPd().getCollectMapping().keySet()) {
            fields.add(key);
        }
        args.add(fields);
    }

}
