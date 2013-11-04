package jrds.probe;

import java.awt.Color;

import jrds.Graph;
import jrds.GraphDesc;
import jrds.GraphDesc.GraphType;
import jrds.GraphNode;
import jrds.factories.ProbeBean;

import org.apache.log4j.Logger;
import org.rrd4j.ConsolFun;

@ProbeBean({"count"})
public class NodeMemoryGraph extends Graph {
    static final private Logger logger = Logger.getLogger(NodeMemoryGraph.class);

    int count;
    private GraphDesc gd = null;

    public NodeMemoryGraph(GraphNode node) {
        super(node);
        logger.trace(gd);
    }

    @Override
    protected synchronized GraphDesc getGraphDesc() {
        if(gd == null) {
            try {
                String hostName = getNode().getProbe().getHost().getName();
                gd = (GraphDesc) getNode().getGraphDesc().clone();
                for (int i = 0; i < count; i++) {
                    gd.add("used." + i, "used", null, GraphType.NONE, null,
                            null, ConsolFun.AVERAGE, false, null, hostName,
                            "nodemem" + i);
                    gd.add("free." + i, "free", null, GraphType.NONE,
                            null, null, ConsolFun.AVERAGE, false, null,
                            hostName, "nodemem" + i);
                    if (i == 0) {
                        gd.add("used.rpn" + i, null, "used." + i + ", 1024, *",
                                GraphType.AREA, Color.BLUE,
                                "Node0 memory used", ConsolFun.AVERAGE,
                                false, null, null, null);
                    } else {
                        gd.add("used.rpn" + i, null, "used." + i + ", 1024, *",
                                GraphType.STACK, Color.BLUE,
                                "Node" + i + " memory used", ConsolFun.AVERAGE,
                                false, null, null, null);
                    }
                    gd.add("free.rpn" + i, null, "free." + i + ", 1024, *",
                            GraphType.STACK, Color.GREEN,
                            "Node" + i + " memory free", ConsolFun.AVERAGE,
                            false, null, null, null);
                }
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Can't clone this graphdesc", e);
            }
        }
        return gd;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count.intValue();
    }

}
