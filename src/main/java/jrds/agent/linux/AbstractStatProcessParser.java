package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import jrds.agent.CollectException;
import jrds.agent.Start;

public abstract class AbstractStatProcessParser extends AbstractProcessParser {

    private static final Pattern COLON_SEPARATOR = Pattern.compile(":");

    protected Map<String, Number> parseStatFile(Path piddir, String file, String[] keys) {
        Path stat = piddir.resolve(file);
        try (BufferedReader r = newAsciiReader(stat)){
            String statLine = r.readLine();
            String[] statArray = SPACE_SEPARATOR.split(statLine);
            Map<String, Number> retValues = new HashMap<>(statArray.length);
            //Number of column in /proc/<pid>/stat is unpredictable in linux
            int column = Math.min(keys.length, statArray.length);
            for (int i = 0; i < column; i++ ) {
                String value = statArray[i];
                String key = keys[i];
                if (key != null) {
                    Number dvalue = Start.parseStringNumber(value, 0L);
                    retValues.put(file + ":" + key, dvalue);
                }
            }
            return retValues;
        } catch (FileNotFoundException e){
            // A file not found is not a problem just return nothing
            return Map.of();
        } catch (IOException e) {
            throw new CollectException("Collect for " + getName() + " failed: " + e.getMessage(), e);
        }
    }

    protected long getProcUptime(Map<String, Number> values) {
        Number startTimeTickObject = values.remove("stat:start_time");
        if (startTimeTickObject == null) {
            //invalid start_time, or empty map, skip this process
            return -1;
        }
        return startTimeTickObject.longValue();
    }

    protected Map<String, Number> parseKeyFile(Path pidDir, String file) {
        Path stat = pidDir.resolve(file);
        Map<String, Number> retValues = new HashMap<>();
        try (BufferedReader r = newAsciiReader(stat)){
            String line;
            while((line = r.readLine()) != null) {
                String[] values = COLON_SEPARATOR.split(line.trim());
                if (values.length == 2) {
                    String key = values[0].trim();
                    Number value = Start.parseStringNumber(values[1].trim(), 0L);
                    retValues.put(file + ":" + key, value);
                }
            }
            return retValues;
        } catch (FileNotFoundException e){
            // A file not found is not a problem just return nothing
            return Collections.emptyMap();
        } catch (IOException e) {
            throw new CollectException("Collect for " + getName() + " failed: " + e.getMessage(), e);
        }
    }

}
