package jrds.probe;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

public class PdhProbe extends RMI {

    @Override
    public Boolean configure() {
        if (getPd().getSpecific("remote") == null) {
            getPd().addSpecific("remote", "jrds.agent.windows.pdh.PdhAgent");
        }

        List<Object> args = new ArrayList<Object>();
        buildArgs(args);
        setArgs(args);

        log(Level.DEBUG, "remote args for this probe: %s", args);
        return super.configure();
    }
    
    protected void buildArgs(List<Object> args) {
        args.add(getPd().getName());
    }

}
