package jrds.agent;

/**
 * A fail-safe empty rprobe Actor
 * @author bacchell
 *
 */
public class RProbeActorEmpty extends RProbeActor {
    final private long starttime;

    public RProbeActorEmpty() {
        starttime = System.currentTimeMillis();
    }

    @Override
    public long getUptime() {
        return System.currentTimeMillis() - starttime;
    }

}
