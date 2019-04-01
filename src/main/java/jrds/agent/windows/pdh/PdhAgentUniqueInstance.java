package jrds.agent.windows.pdh;

import java.util.ArrayList;

import com.arkanosis.jpdh.JPDHException;

import jrds.agent.CollectException;

public class PdhAgentUniqueInstance extends PdhAgent {

    // Coming from configuration
    protected String object;
    protected String instance;

    public Boolean configure(String name, String instance, ArrayList<String> counters) {
        this.instance = instance;

        if (!super.configure(name)) {
            return false;
        }
        try {
            addCounterList(this.object, this.instance, counters);
        } catch (IllegalArgumentException e) {
            try {
                this.query.close();
            } catch (JPDHException e1) {
            }
            throw new CollectException("Configuration for " + name + ":" + instance + " failed: " + e.getMessage(), e);
        } catch (JPDHException e) {
            try {
                this.query.close();
            } catch (JPDHException e1) {
            }
            throw new CollectException("Configuration for " + name + ":" + instance + " failed: " + e.getMessage(), e);
        }
        return true;
    }

    @Override
    public void setProperty(String specific, String value) {
        if (specific.equals("object")) {
            object = value;
        } else {
            super.setProperty(specific, value);
        }
    }

}
