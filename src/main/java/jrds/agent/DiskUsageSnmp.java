package jrds.agent;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * This probe mimic the data from the SNMP probe PartitionSpace to be able to keep history
 */
public class DiskUsageSnmp extends LProbe {

    FileStore fs;

    public Boolean configure(String path) {
        try {
            fs = Files.getFileStore(Path.of(path));
        } catch (IOException ex) {
            throw new CollectException("Invalid file system", ex);
        }
        return true;
    }

    @Override
    public Map<String, Number> query() {
        try {
            return Map.of(
                    "Total", fs.getTotalSpace(),
                    "Used", fs.getTotalSpace() -  fs.getUsableSpace(),
                    "Avail", fs.getUsableSpace()
            );
        } catch (IOException ex) {
            throw new CollectException("Invalid collect name: ", ex);
        }
    }

    @Override
    public String getName() {
        return "fs-" + fs.name();
    }

}
