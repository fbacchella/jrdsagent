package jrds.agent.windows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jrds.agent.LProbe;
import jrds.agent.Start;

public class WmiAgent extends LProbe {

    protected String query;
    protected String[] fields;

    public Boolean configure(ArrayList<String> fields) {
        this.fields = fields.toArray(new String[]{});
        return configure();
    }

    @Override
    public String getName() {
        return query;
    }

    @Override
    public void setProperty(String specific, String value) {
        if("wbemClass".equals(specific)) {
            query = value;
        } else {
            super.setProperty(specific, value);
        }
    }

    protected Object[] doQuery() {
        return WmiRequester.getFromClass(query, fields);
    }

    @Override
    public Map<String, Number> query() {
        WmiRequester.refresh();
        Object[] values = doQuery();
        Map<String, Number> returned = new HashMap<String, Number>();
        for(int i=0; i < fields.length; i++) {
            Object o = values[i];
            if(o instanceof Number) {
                returned.put(fields[i], (Number) o);
            } else if(o instanceof String) {
                Double value = Start.parseStringNumber((String) o, Double.NaN);
                returned.put(fields[i], value);
            }
        }
        return returned;
    }

}
