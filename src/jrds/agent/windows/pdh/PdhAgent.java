package jrds.agent.windows.pdh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.arkanosis.jpdh.Counter;
import com.arkanosis.jpdh.JPDH;
import com.arkanosis.jpdh.JPDHException;
import com.arkanosis.jpdh.Query;

import jrds.agent.LProbe;

public class PdhAgent extends LProbe {
    private static final Logger logger = Logger.getLogger("jrds.agent.windows.pdh.PdhAgent");

    // Coming from configuration
    protected String name;

    // Constructed
    protected Query query;
    protected Map<String, Counter> counters = new HashMap<>();

    @Override
    public Boolean configure() {
        try {
            this.query = JPDH.openQuery();
        } catch (JPDHException e) {
            logger.error("Could not open Pdh query", e);
            return false;
        }
        return true;
    }

    public Boolean configure(String name) {
        this.name = name;
        return configure();
    }

    protected void addCounterList(String object, String instance,
                                  List<String> counters) throws IllegalArgumentException,
                                                         JPDHException {
        for (String s : counters) {
            addCounter(PdhHelper.constructPdhPath(object, instance, s));
        }

        // Necessary call to initialize the counters that necessitate 2 values
        // to calculate
        query.collectData();
    }

    protected void addCounter(String counterPath) throws JPDHException {
        Counter tmp = query.addCounter(counterPath);
        String[] elements = tmp.getFullPath().split("\\\\");
        this.counters.put(elements[elements.length - 1], tmp);
    }

    @Override
    public Map<String, Number> query() {
        Map<String, Number> m = new HashMap<String, Number>();
        try {
            query.collectData();
        } catch (JPDHException e) {
            logger.error("Could not collect Query data for probe : "+ getName() + ", aborting this polling", e);
            return m;
        }
        for (Map.Entry<String, Counter> entry : this.counters.entrySet()) {
            try {
                m.put(entry.getKey(), entry.getValue().getDoubleValue());
            } catch (JPDHException e) {
                if (entry.getValue() != null) {
                    logger.error("Error while retrieving counter value : " +
                                 entry.getValue().getFullPath(), e);
                } else {
                    logger.error("Error while retrieving counter value", e);
                }
            }
        }

        m.put("uptime", Double.MAX_VALUE);

        return m;
    }

    @Override
    public String getName() {
        return name;
    }

}
