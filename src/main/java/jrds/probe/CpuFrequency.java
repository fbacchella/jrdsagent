package jrds.probe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.rrd4j.DsType;

import jrds.GraphDesc;
import jrds.GraphDesc.GraphType;
import jrds.ProbeDesc;
import jrds.ProbeDesc.DataSourceBuilder;
import jrds.factories.ProbeBean;

@ProbeBean({"count"})
public class CpuFrequency extends RMI {

    private short count = 1;

    @Override
    public Boolean configure() {
        List<DataSourceBuilder> dsList = new ArrayList<>(count);
        for (int i = 0 ; i < count ; i++) {
            dsList.add(ProbeDesc.getDataSourceBuilder(String.valueOf(i), DsType.GAUGE));
        }
        setPd(new ProbeDesc<String>(getPd(), dsList));
        return super.configure();
    }

    @Override
    public void addGraph(GraphDesc gd) {
        if ("CpuFrequency".equals(gd.getName())) {
            Color color = new Color(0, 0, 255, (int) Math.ceil(255.0/count));
            GraphDesc.Builder builder = GraphDesc.getBuilder().fromGraphDesc(gd).setWithSummary(true);
            for(int i = 0 ; i < count ; i++) {
                builder.addDsDesc(GraphDesc.getDsDescBuilder()
                                  .setName(Integer.toString(i))
                                  .setDsName(Integer.toString(i))
                                  .setColor(color)
                                  .setGraphType(GraphType.LINE)
                                );
            }
            super.addGraph(builder.build());
        } else {
            super.addGraph(gd);
        }
    }

    public Short getCount() {
        return count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

}
