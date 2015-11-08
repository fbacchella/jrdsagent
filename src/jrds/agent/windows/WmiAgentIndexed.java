package jrds.agent.windows;

import java.util.ArrayList;

public class WmiAgentIndexed extends WmiAgent {

    private String index;
    private String key;

    public Boolean configure(String index, ArrayList<String> fields) {
        this.index = index;
        return configure(fields);
    }

    @Override
    public void setProperty(String specific, String value) {
        if("key".equals(specific)) {
            key = value;
        } else {
            super.setProperty(specific, value);
        }
    }

    @Override
    public String getName() {
        return query + "." + key + "=" + index;
    }

    @Override
    protected Object[] doQuery() {
        return WmiRequester.getFromClassIndexed(query, key, index, fields);
    }

}
