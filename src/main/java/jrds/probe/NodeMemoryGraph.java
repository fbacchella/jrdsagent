package jrds.probe;

import java.awt.Color;

import jrds.Graph;
import jrds.GraphDesc;
import jrds.GraphDesc.GraphType;
import jrds.GraphNode;
import jrds.factories.ProbeBean;

@ProbeBean({"count"})
public class NodeMemoryGraph extends Graph {

    private int count;
    private GraphDesc gd = null;

    public NodeMemoryGraph(GraphNode node) {
        super(node);
    }

    @Override
    protected synchronized GraphDesc getGraphDesc() {
        if (gd == null) {
            String hostName = getNode().getProbe().getHost().getName();
            GraphDesc.Builder builder = GraphDesc.getBuilder().fromGraphDesc(getNode().getGraphDesc());
            for (int i = 0; i < count; i++) {
                builder.addDsDesc(GraphDesc.getDsDescBuilder()
                                        .setName("used." + i)
                                        .setDsName("used")
                                        .setGraphType(GraphType.NONE)
                                        .setPath(hostName, "nodemem" + i));
                builder.addDsDesc(GraphDesc.getDsDescBuilder()
                                        .setName("free." + i)
                                        .setDsName("free")
                                        .setGraphType(GraphType.NONE)
                                        .setPath(hostName, "nodemem" + i));
                builder.addDsDesc(GraphDesc.getDsDescBuilder()
                                        .setName("used.rpn" + i)
                                        .setRpn("used." + i + ", 1024, *")
                                        .setGraphType( i== 0 ? GraphType.AREA : GraphType.STACK)
                                        .setColor(Color.BLUE)
                                        .setLegend("Node" + i + " memory used"));
                builder.addDsDesc(GraphDesc.getDsDescBuilder()
                                        .setName("free.rpn" + i)
                                        .setRpn("free." + i + ", 1024, *")
                                        .setGraphType(GraphType.STACK)
                                        .setColor(Color.GREEN)
                                        .setLegend("Node" + i + " memory free"));
            }
            gd = builder.build();
        }
        return gd;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
