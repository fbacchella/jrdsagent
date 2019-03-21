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

    private short count;

    @Override
    public Boolean configure() {
        try {
            @SuppressWarnings("unchecked")
            ProbeDesc<String> localpd = (ProbeDesc<String>) getPd().clone();
            List<DataSourceBuilder> dsList = new ArrayList<>(count);
            for (int i = 0 ; i < count ; i++) {
                dsList.add(ProbeDesc.getDataSourceBuilder(String.valueOf(i), DsType.GAUGE));
            }
            localpd.replaceDs(dsList);
            setPd(localpd);
            return super.configure();
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void addGraph(GraphDesc gd) {
        if ("CpuFrequency".equals(gd.getName())) {
            Color color = new Color(0.0f, 0.0f, 1.0f, 1f/count);
            GraphDesc.Builder builder = GraphDesc.getBuilder().fromGraphDesc(gd).setWithSummary(false);
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
