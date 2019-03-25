package jrds.agent;

import java.util.HashMap;
import java.util.Map;

public class TestProbe extends LProbe {

    public String getName() {
        return "TestProbe";
    }

    public Map<String,Number> query() {
        Map<String,Number> m = new HashMap<>(1);
        m.put("time", System.currentTimeMillis());
        return m;
    }

}
