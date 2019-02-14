package jrds.agent.linux;

import java.util.HashMap;
import java.util.Map;

public class ProcessIO extends AbstractProcessParser {

    //See fs/proc/array.c
    static final String[] statKey = {
            null,                           // pid_nr_ns(pid, ns)
            null,                           // tcomm
            null,                           // state
            null,                           // ppid
            null,                           // pgid
            null,                           // sid
            null,                           // tty_nr
            null,                           // tty_pgrp
            null,                           // task_flags
            null,                           // min_flt
            null,                           // cmin_flt
            null,                           //
            null,                           //
            null,                           //
            null,                           //
            null,                           //
            null,                           //
            null,                           // priority
            null,                           // nice
            null,                           //
            null,                           // it_real_value
            "start_time",                   // time the process started after system boot
            null,                           //
            null,                           // mm ? get_mm_rss(mm) : 0,
            null,                           // rsslim
            null,                           // mm ? mm->start_code : 0
            null,                           // mm ? mm->end_code : 0
            null,                           // mm ? mm->start_stack
            null,                           // esp
            null,                           // eip
            null,                           // task->pending.signal.sig[0] & 0x7fffffffUL
            null,                           // task->blocked.sig[0] & 0x7fffffffUL
            null,                           // sigign      .sig[0] & 0x7fffffffUL
            null,                           // sigcatch    .sig[0] & 0x7fffffffUL,
            null,                           // wchan
            null,
            null,
            null,                           // task->exit_signal
            null,                           // task_cpu(task),
            null,                           // task_rt_priority
            null,                           // task_policy
            null,                           // (unsigned long long)delayacct_blkio_ticks(task),
            null,                           // cputime_to_clock_t(gtime)
            null,                           // cputime_to_clock_t(cgtime))
    };

    @Override
    protected Map<String, Number> parseProc(int pid) {
        Map<String, Number> bufferMap = new HashMap<String, Number>();
        bufferMap.putAll(parseFile(pid, "stat", statKey));
        bufferMap.putAll(parseKeyFile(pid, "io"));
        return bufferMap;
    }

    @Override
    protected long getProcUptime(Map<String, Number> values) {
        Number startTimeTickObject = values.remove("stat:start_time");
        if (startTimeTickObject == null) {
            //invalid start_time, or empty map, skip this process
            return -1;
        }
        return startTimeTickObject.longValue();
    }

    @Override
    public String getName() {
        return "piio-" + getNameSuffix();
    }

}
