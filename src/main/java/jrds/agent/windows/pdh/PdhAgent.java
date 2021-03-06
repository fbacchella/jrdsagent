package jrds.agent.windows.pdh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arkanosis.jpdh.Counter;
import com.arkanosis.jpdh.JPDH;
import com.arkanosis.jpdh.JPDHException;
import com.arkanosis.jpdh.Query;

import jrds.agent.CollectException;
import jrds.agent.LProbe;

public class PdhAgent extends LProbe {

    // Coming from configuration
    protected String name;

    // Constructed
    protected Query query;
    protected final Map<String, Counter> counters = new HashMap<>();

    @Override
    public Boolean configure() {
        try {
            query = JPDH.openQuery();
        } catch (JPDHException e) {
            throw new CollectException("Unable to prepare JPDH", e);
        }
        return true;
    }

    public Boolean configure(String name) {
        this.name = name;
        return configure();
    }

    protected void addCounterList(String object, String instance,
                                  List<String> counters) throws JPDHException {
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
        counters.put(elements[elements.length - 1], tmp);
    }

    @Override
    public Map<String, Number> query() {
        try {
            query.collectData();
        } catch (JPDHException e) {
            throw new CollectException("Query for " + getName() + " failed: " + e.getMessage(), e);
        }
        Map<String, Number> m = new HashMap<>(counters.size() + 1);
        for (Map.Entry<String, Counter> entry : counters.entrySet()) {
            try {
                m.put(entry.getKey(), entry.getValue().getDoubleValue());
            } catch (JPDHException e) {
                throw new CollectException("Query for " + getName() + " failed: " + e.getMessage(), e);
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
