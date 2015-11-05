package jrds.agent.windows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import jrds.agent.LProbe;

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

    @Override
    public Map<String, Number> query() {
        try {
            WmiRequester.refresh();
        } catch (InterruptedException e) {
            throw new RuntimeException("can't refresh wmi objects", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("can't refresh wmi objects", e);
        }
        try {
            List<Object> values = WmiRequester.getFromClass(query, fields);
            Map<String, Number> returned = new HashMap<String, Number>();
            for(int i=0; i < fields.length; i++) {
                Object o = values.get(i);
                if(o instanceof Number) {
                    returned.put(fields[i], (Number) o);
                } else if(o instanceof String) {
                    try {
                        Double value = Double.parseDouble((String) o);
                        returned.put(fields[i], value);
                    } catch (NumberFormatException e) {
                        returned.put(fields[i], Double.NaN);
                    }                    
                }
            }
            return returned;
        } catch (InterruptedException e) {
            throw new RuntimeException(getName(), e);
        } catch (ExecutionException e) {
            throw new RuntimeException(getName(), e);
        }
    }

}
