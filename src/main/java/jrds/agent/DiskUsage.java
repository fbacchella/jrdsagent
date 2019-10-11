package jrds.agent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DiskUsage extends LProbe {

    String path;

    public Boolean configure(String path) {
        this.path = path;
        return true;
    }

    @Override
    public Map<String, Number> query() {
        File pathFile = new File(path);
        if(! pathFile.isDirectory()) {
            throw new CollectException("Path " + path + " is not a directory");
        }
        Map<String, Number> values = new HashMap<>();
        values.put("free", pathFile.getFreeSpace());
        values.put("total", pathFile.getTotalSpace());
        values.put("usable", pathFile.getUsableSpace());
        return values;
    }

    @Override
    public String getName() {
        return "du" + path.replace('/', '_').replace(':', '_').replace('\\', '_');
    }

}
