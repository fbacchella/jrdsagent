package jrds.probe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.rrd4j.DsType;

import jrds.GraphDesc;
import jrds.GraphDesc.GraphType;
import jrds.ProbeDesc;
import jrds.ProbeDesc.DataSourceBuilder;
import jrds.PropertiesManager;
import jrds.factories.ProbeBean;

@ProbeBean({"count"})
public class MpStat extends RMI {

    private short count = 1;
    private String[] columns;

    @Override
    public Boolean configure() {
        List<DataSourceBuilder> dsList = new ArrayList<>(count * columns.length);
        for (int i = 0 ; i < count ; i++) {
            for (String s: columns) {
                dsList.add(ProbeDesc.getDataSourceBuilder(s + i, DsType.COUNTER));
            }
        }
        setPd(new ProbeDesc<>(getPd(), dsList));
        return super.configure();
    }

    @Override
    public void setPd(ProbeDesc<String> pd) {
        columns = pd.getSpecific("columns").split(" *, *");
        super.setPd(pd);
    }

    @Override
    public void addGraph(GraphDesc gd) {
        if("MpStatHeat".equals(gd.getName())) {
            GraphDesc.Builder builder = GraphDesc.getBuilder().fromGraphDesc(gd).setWithSummary(true);
            Color color = new Color(0, 0, 255, (int) Math.ceil(255.0/count));
            for(int i = 0 ; i < count ; i++) {
                StringBuilder rpn = new StringBuilder("0");
                for(String key: columns) {
                    if ("idle".equals(key) || "iowait".equals(key) || "guest".equals(key) || "guestnice".equals(key)) {
                        continue;
                    }
                    builder.addDsDesc(GraphDesc.getDsDescBuilder()
                                      .setDsName(key + i)
                                      .setName(key + i)
                                      .setGraphType(GraphType.NONE)
                                    );
                    rpn.append(",");
                    rpn.append(key);
                    rpn.append(i);
                    rpn.append(",ADDNAN");
                }
                builder.addDsDesc(GraphDesc.getDsDescBuilder()
                                  .setName("cpu" + i)
                                  .setColor(color)
                                  .setRpn(rpn.toString())
                                  .setGraphType(GraphType.AREA)
                                );
            }
            super.addGraph(builder.build());
        } else if("MpStatIndividual".equals(gd.getName())) {
            for(int i = 0 ; i < count ; i++) {
                GraphDesc.Builder builder = GraphDesc.getBuilder()
                                .fromGraphDesc(gd)
                                .setGraphTitle("CPU " + i + " utilisation on ${host}")
                                .setGraphName("mpstatindividual" + i)
                                .emptyTrees();
                for(String key: columns) {
                    builder.addDsDesc(GraphDesc.getDsDescBuilder()
                                      .setDsName(key + i)
                                      .setName(key)
                                      .setGraphType(GraphType.NONE)
                                    );
                }
                builder.addTree(PropertiesManager.HOSTSTAB, "${host}", "System", "Load", "Individual CPU usage", Integer.toString(i));
                builder.addTree(PropertiesManager.VIEWSTAB, "System", "Load", "Individual CPU usage", "${host}", Integer.toString(i));
                super.addGraph(builder.build());
            }
        }
    }

    public Short getCount() {
        return count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

}
