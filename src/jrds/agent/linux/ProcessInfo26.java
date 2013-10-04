package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jrds.agent.LProbe;

public class ProcessInfo26  extends LProbe {
    static final private int USER_HZ = 100; 
    //See fs/proc/array.c
    static final String[] statKey = {
        null,		 		// pid_nr_ns(pid, ns)
        null,	 			// tcomm
        null, 				// state
        null, 				// ppid
        null,	 			// pgid
        null, 				// sid
        null,	 			// tty_nr
        null, 				// tty_pgrp
        null,		 		// task_flags
        null, 				// min_flt
        null,		 		// cmin_flt
        "maj_flt", 			//
        "cmaj_flt", 			//
        "task_times.tms_utime",         //
        "task_times.tms_stime",         //
        "task_times.tms_cutime",        //
        "task_times.tms_cstime",        //
        null,		 		// priority
        null,	 			// nice
        "num_threads", 			//
        null,				// it_real_value
        "start_time", 			// time the process started after system boot
        "vsize", 			//
        null,	 			// mm ? get_mm_rss(mm) : 0,
        null,	 			// rsslim
        null,			 	// mm ? mm->start_code : 0
        null,				// mm ? mm->end_code : 0
        null,				// mm ? mm->start_stack
        null, 				// esp
        null, 				// eip
        null, 				// task->pending.signal.sig[0] & 0x7fffffffUL
        null, 				// task->blocked.sig[0] & 0x7fffffffUL
        null,			 	// sigign      .sig[0] & 0x7fffffffUL
        null,			 	// sigcatch    .sig[0] & 0x7fffffffUL,
        null,	 			// wchan
        null,
        null,
        null,				// task->exit_signal
        null, 				// task_cpu(task),
        null,				// task_rt_priority
        null,				// task_policy
        "blkio_ticks",			// (unsigned long long)delayacct_blkio_ticks(task),
        "gtime", 			// cputime_to_clock_t(gtime)
        "cgtime",		 	// cputime_to_clock_t(cgtime))
    };
    static final String[] statmKey = {
        "size",
        "resident",
        "shared",
        null,				// text
        null, 				//lib
        "data",
        null				// dt
    };

    static final Pattern pidFile = Pattern.compile("^(\\d+)$");
    Pattern cmdFilter = null;

    public Boolean configure(String cmdFilter) {
        this.cmdFilter = Pattern.compile(cmdFilter);
        return true;
    }

    public String getName() {
        return "pi26-" + cmdFilter.toString();
    }

    public Map<String, Number> query() {
        Map<String, Number> retValues = new HashMap<String, Number>();
        int count = 0;
        long mostRecentTick = 0;
        for(int pid: getPids()) {
            String cmdLine = getCmdLine(pid);
            if(cmdFilter.matcher(cmdLine).matches()) {
                count++;
                Map<String, Number> bufferMap = new HashMap<String, Number>(retValues.size());
                bufferMap.putAll(parseFile(pid, "stat", statKey));
                bufferMap.putAll(parseFile(pid, "statm", statmKey));
                long startTimeTick = bufferMap.get("stat:start_time").longValue();
                mostRecentTick = Math.max(startTimeTick, mostRecentTick);
                bufferMap.remove("stat:start_time");
                for(Map.Entry<String, Number> e: bufferMap.entrySet()) {
                    Number previous = retValues.get(e.getKey());
                    if(previous == null)
                        retValues.put(e.getKey(), e.getValue());
                    else {
                        retValues.put(e.getKey(), previous.doubleValue() +  e.getValue().doubleValue());
                    }
                }
            }
        }
        long uptime = computeUpTime(mostRecentTick);
        retValues.put("uptime", uptime);
        retValues.put("count", count);
        return retValues;
    }

    private Collection<Integer> getPids() {
        Collection<Integer> retValue =  new HashSet<Integer>();
        File procFile = new File("/proc");
        //If launched in a non linux os, avoid a NPE
        if( ! procFile.isDirectory())
            return Collections.emptySet();
        for(String path: procFile.list()) {
            Matcher m = pidFile.matcher(path);
            if(m.matches()) {
                String pidString = m.group();
                if(pidString != null) {
                    retValue.add(Integer.decode(pidString));
                }
            }
        }
        return retValue;
    }

    private String getCmdLine(int pid) {
        FileInputStream is = null;
        try {
            File cmdFile = new File("/proc/" + pid + "/cmdline");
            byte[] content = new byte[4096];
            is = new FileInputStream(cmdFile);
            int read = is.read(content);
            for(int i = 0 ; i < read ; i++) {
                if(content[i] == 0) {
                    content[i] = ' ';
                }
            }
            String retValue = new String(content);
            is.close();
            return retValue.trim();
        } catch (Exception e) {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    throw new RuntimeException(getName(), e1);
                }                
            }
            throw new RuntimeException(getName(), e);
        }
    }

    Map<String, Number> parseFile(int pid, String file, String[] keys) {
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
                    try {
                        double dvalue = Double.parseDouble(value);
                        retValues.put(file + ":" + key, dvalue);
                    } catch (NumberFormatException e) {
                    }
                }
            }
            r.close();
            return retValues;
        } catch (Exception e) {
            if(r != null) {
                try {
                    r.close();
                } catch (IOException e1) {
                    throw new RuntimeException(getName(), e1);
                }                
            }
            throw new RuntimeException(getName(), e);
        }
    }

    /**
     * Compute the uptime, given the relative start time of a process
     * @param startTime in tick
     * @return uptime, in second
     */
    private long computeUpTime(long startTime) {
        if(startTime == 0)
            return 0;
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader(new File("/proc/uptime")));
            String uptimeLine = r.readLine();
            // We talks minutes here, so a precision down to second is good enough
            long uptimeSystem = (long) Double.parseDouble(uptimeLine.split(" ")[0]);
            r.close();
            return uptimeSystem - startTime / USER_HZ;
        } catch (Exception e) {
            if(r != null) {
                try {
                    r.close();
                } catch (IOException e1) {
                    throw new RuntimeException(getName(), e1);
                }                
            }
            throw new RuntimeException(getName(), e);
        }

    }
}
