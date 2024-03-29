package jrds.agent.linux;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ProcessCpu extends AbstractStatProcessParser {

    //See fs/proc/array.c
    private static final String[] statKey = {
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
        "maj_flt",                      //
        "cmaj_flt",                     //
        "task_times.tms_utime",         //
        "task_times.tms_stime",         //
        "task_times.tms_cutime",        //
        "task_times.tms_cstime",        //
        null,                           // priority
        null,                           // nice
        "num_threads",                  //
        null,                           // it_real_value
        "start_time",                   // time the process started after system boot
        null,                        //
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
        "blkio_ticks",                  // (unsigned long long)delayacct_blkio_ticks(task),
        "gtime",                        // cputime_to_clock_t(gtime)
        "cgtime",                       // cputime_to_clock_t(cgtime))
    };

    @Override
    protected Map<String, Number> parseProc(Path piddir) {
        return new HashMap<>(parseStatFile(piddir, "stat", statKey));
    }

    @Override
    public String getName() {
        return "picpu-" + getNameSuffix();
    }

}
