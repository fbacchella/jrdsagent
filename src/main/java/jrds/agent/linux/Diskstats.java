package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessControlException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import jrds.agent.Start;

public class Diskstats extends LProbeProc {

    private static final Pattern SPACE_SPLIT = Pattern.compile("\\s+");

    private static final Path[] PREFIXES = { Path.of("/dev/mapper/"), Path.of("/dev/disk/"), Path.of("/dev/")};

    private String disk;

    public Boolean configure(String disk) {
        return doConfigure(disk, true);
    }

    public Boolean configure(String disk, Boolean checkPath) {
        return doConfigure(disk, checkPath);
    }

    private boolean doConfigure(String disk, boolean checkPath) {
        Path diskPath = Paths.get(disk);
        Path diskFile = null;
        if (checkPath) {
            // Search in standards prefix if the real IO device can be detected.
            // It allows to use device mapper names instead of variable dm-xx
            // or to look into /dev/disk/by-*
            for (Path tryprefix: PREFIXES) {
                try {
                    diskFile = tryprefix.resolve(diskPath);
                    if (Files.isReadable(diskFile)) {
                        break;
                    } else {
                        diskFile = null;
                    }
                } catch (AccessControlException e) {
                    // Tried to resolve an invalid path, skip it
                    diskFile = null;
                }
            }
            if (diskFile != null) {
                // We found the real path, that will be used in /proc/diskstats
                String realdisk = DEV_PATH.relativize(diskFile).toString();
                this.disk = realdisk;
            } else {
                // Nothing found, hope user know what he is doing
                this.disk = disk;
            }
        } else {
            // Nothing checked, hope user know what he is doing
            this.disk = disk;
        }
        return super.configure();
    }

    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<>();
        String line;
        boolean found = false;
        while(! found && ((line = r.readLine()) != null)) {
            if(line.contains(" " + disk + " ")) {
                found = true;
                String[] values = SPACE_SPLIT.split(line);
                //Full line, with all the values
                if(values.length >= 13 ) {
                    retValues.put("r", Start.parseStringNumber(values[3], Double.NaN));
                    retValues.put("rrqm", Start.parseStringNumber(values[4], Double.NaN));
                    retValues.put("rsec", Start.parseStringNumber(values[5], Double.NaN));
                    retValues.put("rwait", Start.parseStringNumber(values[6], Double.NaN));
                    retValues.put("w", Start.parseStringNumber(values[7], Double.NaN));
                    retValues.put("wrqm", Start.parseStringNumber(values[8], Double.NaN));
                    retValues.put("wsec", Start.parseStringNumber(values[9], Double.NaN));
                    retValues.put("wwait", Start.parseStringNumber(values[10], Double.NaN));
                    retValues.put("qu-sz", Start.parseStringNumber(values[11], Double.NaN));
                    retValues.put("waittm", Start.parseStringNumber(values[12], Double.NaN));
                    retValues.put("wwaittm", Start.parseStringNumber(values[13], Double.NaN));
                    // Added in kernel 4.18
                    if(values.length >= 17 ) {
                        retValues.put("d", Start.parseStringNumber(values[14], Double.NaN));
                        retValues.put("drqm", Start.parseStringNumber(values[15], Double.NaN));
                        retValues.put("dsec", Start.parseStringNumber(values[16], Double.NaN));
                        retValues.put("dwait", Start.parseStringNumber(values[17], Double.NaN));
                    } else {
                        retValues.put("d", 0);
                        retValues.put("drqm", 0);
                        retValues.put("dsec", 0);
                        retValues.put("dwait", 0);
                    }
                    // Added in kernel 5.5
                    if(values.length >= 19 ) {
                        retValues.put("f", Start.parseStringNumber(values[18], Double.NaN));
                        retValues.put("fwait", Start.parseStringNumber(values[19], Double.NaN));
                    } else {
                        retValues.put("f", 0);
                        retValues.put("fwait", 0);
                    }
                }
                else {
                    retValues.put("r", Start.parseStringNumber(values[3], Double.NaN));
                    retValues.put("rsec", Start.parseStringNumber(values[4], Double.NaN));
                    retValues.put("w", Start.parseStringNumber(values[5], Double.NaN));
                    retValues.put("wsec", Start.parseStringNumber(values[6], Double.NaN));
                }
            }
        }
        return retValues;
    }

    @Override
    public String getName() {
        return "ds-" + disk;
    }

}
