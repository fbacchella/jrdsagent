package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import jrds.agent.LProbe;
import jrds.agent.Start;

public abstract class AbstractProcessParser  extends LProbe {

    static private final int USER_HZ = 100; 

    static private final Pattern PIDDIRPATTERN = Pattern.compile("^(\\d+)$");
    
    static protected final Charset LINUXFSCHARSET = Charset.forName("US-ASCII");

    protected Pattern cmdFilter = null;

    public Boolean configure(String cmdFilter) {
        this.cmdFilter = Pattern.compile(cmdFilter);
        return true;
    }

    public Map<String, Number> query() {
        Map<String, Number> retValues = new HashMap<String, Number>();
        int count = 0;
        long mostRecentTick = 0;
        for(int pid: getPids()) {
            // getPids() might return -1 if reached end of processes
            if (pid < 0) {
                break;
            }
            String cmdLine = getCmdLine(pid);
            if (cmdLine != null && cmdFilter.matcher(cmdLine).matches()) {
                Map<String, Number> bufferMap = parseProc(pid);
                long startTimeTick = getProcUptime(bufferMap);
                if (startTimeTick < 0) {
                    continue;
                }
                mostRecentTick = Math.max(startTimeTick, mostRecentTick);
                count++;
                for(Map.Entry<String, Number> e: bufferMap.entrySet()) {
                    Number previous = retValues.get(e.getKey());
                    if(previous == null)
                        retValues.put(e.getKey(), e.getValue());
                    else {
                        retValues.put(e.getKey(), previous.doubleValue() + e.getValue().doubleValue());
                    }
                }
            }
        }
        long uptime = computeUpTime(mostRecentTick);
        retValues.put("uptime", uptime);
        retValues.put("count", count);
        return retValues;
    }

    private Iterable<Integer> getPids() {
        final File procFile = new File("/proc");
        //If launched on a non linux os, avoid a NPE
        if ( ! procFile.isDirectory())
            return Collections.emptySet();
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                final File[] pids = procFile.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return PIDDIRPATTERN.matcher(name).matches();
                    }
                });
                return new Iterator<Integer>() {
                    int cursor = 0;

                    @Override
                    public boolean hasNext() {
                        return cursor < pids.length;
                    }

                    @Override
                    public Integer next() {
                        do {
                            File current = pids[cursor++];
                            if (current.exists()) {
                                return Integer.decode(current.getName());
                            }
                        } while (cursor < pids.length);
                        return -1;
                     }
                    
                };
            }
            
        };
    }

    protected String getCmdLine(int pid) {
        FileInputStream is = null;
        File cmdFile = new File("/proc/" + pid + "/cmdline");
        try {
            byte[] content = new byte[4096];
            is = new FileInputStream(cmdFile);
            int read;
            StringBuilder buffer = new StringBuilder();
            while ((read = is.read(content)) > 0) {
                for (int i = 0 ; i < read ; i++) {
                    if (content[i] == 0) {
                        content[i] = ' ';
                    }
                }
                buffer.append(new String(content, 0, read, LINUXFSCHARSET));
            }
            // The last character is an extra space
            // Some command don't have a cmdline
            return buffer.substring(0, Math.max(buffer.length() - 1, 0));
        } catch (FileNotFoundException e){
            //An file not found is not a problem just return null
            return null;
        } catch (IOException e) {
            throw new RuntimeException("unable to read " + cmdFile.getPath() + " for " + getName(), e);
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    throw new RuntimeException(getName(), e1);
                }
            }
        }
    }

    protected abstract Map<String, Number> parseProc(int pid);
    
    protected abstract long getProcUptime(Map<String, Number> values);

    protected Map<String, Number> parseKeyFile(int pid, String file) {
        File stat = new File("/proc/" + pid + "/" + file);
        BufferedReader r = null;
        Map<String, Number> retValues = new HashMap<String, Number>();
        try {
            r = new BufferedReader(new FileReader(stat));
            String line;
            while((line = r.readLine()) != null) {
                String[] values = line.trim().split(":");
                if (values.length == 2) {
                    String key = values[0].trim();
                    Number value = Start.parseStringNumber(values[1].trim(), 0l);
                    retValues.put(file + ":" + key, value);
                }
            }
            return retValues;
        } catch (FileNotFoundException e){
            //An file not found is not a problem just return nothing
            return Collections.emptyMap();
        } catch (Exception e) {
            throw new RuntimeException(getName(), e);
        } finally {
            if(r != null) {
                try {
                    r.close();
                } catch (IOException e1) {
                    throw new RuntimeException(getName(), e1);
                }
            }
        }

    }

    protected Map<String, Number> parseFile(int pid, String file, String[] keys) {
        File stat = new File("/proc/" + pid + "/" + file);
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader(stat));
            String statLine = r.readLine();
            statLine.replaceFirst("(.*)", "()");
            String[] statArray = statLine.split(" +");
            Map<String, Number> retValues = new HashMap<String, Number>(statArray.length);
            //Number of column in /proc/<pid>/stat is unpredictable in linux
            int column = Math.min(keys.length, statArray.length);
            for(int i=0; i < column; i++ ) {
                String value = statArray[i];
                String key = keys[i];
                if(key != null) {
                    Number dvalue = Start.parseStringNumber(value, 0l);
                    retValues.put(file + ":" + key, dvalue);
                }
            }
            return retValues;
        } catch (FileNotFoundException e){
            //An file not found is not a problem just return nothing
            return Collections.emptyMap();
        } catch (Exception e) {
            throw new RuntimeException(getName(), e);
        } finally {
            if(r != null) {
                try {
                    r.close();
                } catch (IOException e1) {
                    throw new RuntimeException(getName(), e1);
                }
            }
        }
    }

    /**
     * Compute the uptime, given the relative start time of a process
     * @param startTime in tick
     * @return uptime, in second
     */
    protected long computeUpTime(long startTime) {
        if(startTime == 0)
            return 0;
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader(new File("/proc/uptime")));
            String uptimeLine = r.readLine();
            // We talks minutes here, so a precision down to second is good enough
            long uptimeSystem = (long) Double.parseDouble(uptimeLine.split(" ")[0]);
            return uptimeSystem - startTime / USER_HZ;
        } catch (Exception e) {
            throw new RuntimeException(getName(), e);
        } finally {
            if(r != null) {
                try {
                    r.close();
                } catch (IOException e1) {
                    throw new RuntimeException(getName(), e1);
                }
            }
        }

    }
}
