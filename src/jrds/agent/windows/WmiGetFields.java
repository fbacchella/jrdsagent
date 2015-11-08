package jrds.agent.windows;

import java.util.concurrent.Callable;

import com4j.typelibs.wmi.ISWbemObject;
import com4j.typelibs.wmi.ISWbemRefreshableItem;

public class WmiGetFields implements Callable<Object[]> {

    private final String query;
    private final String[] fields;

    WmiGetFields(String query, String... fields) {
        this.query = query;
        this.fields = fields;
    }

    @Override
    public Object[] call() {
        ISWbemRefreshableItem item = WmiRequester.getItemByQuery(query);

        ISWbemObject object = item.object();
        Object[] values = new Object[fields.length];

        for(int i = 0; i < fields.length; i++) {
            values[i] = object.properties_().item(fields[i] , 0).value();
        }
        return values;
    }

}
