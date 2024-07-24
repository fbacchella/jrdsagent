package jrds.agent;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DiskUsageFileStore extends LProbe {

    FileStore fs;
    boolean asSnmp = false;

    public Boolean configure(String path) {
        try {
            fs = Files.getFileStore(Path.of(path));
        } catch (IOException ex) {
            throw new CollectException("Invalid file system", ex);
        }
        return true;
    }

    @Override
    public void setProperty(String specific, String value) {
        if (specific.equals("asSnmp")) {
            asSnmp = Boolean.parseBoolean(value);
        } else {
            super.setProperty(specific, value);
        }
    }

    @Override
    public Map<String, Number> query() {
        try {
            Map<String, Number> values = Map.of(
                    "Total", fs.getTotalSpace(),
                    "Avail", fs.getUsableSpace(),
                    "BlockSize", fs.getBlockSize(),
                    "Unallocated", fs.getUnallocatedSpace()
            );
            if (asSnmp) {
                values = new HashMap<>(values);
                values.put("Used", fs.getTotalSpace() -  fs.getUsableSpace());
            }
            return values;
        } catch (IOException ex) {
            throw new CollectException("Invalid collect name: ", ex);
        }
    }

    @Override
    public String getName() {
        return "fs-" + fs.name();
    }

}
