/**
 * 
 */
package jrds.agent.jmx;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

import jrds.agent.LProbe;

import com.sun.management.OperatingSystemMXBean;

/**
 * @author bacchell
 *
 */
public class SystemInfo extends LProbe {

    private enum OSBEAN {
        CVM  {
            @Override
            public Number getValue(OperatingSystemMXBean bean) {
                return bean.getCommittedVirtualMemorySize();
            }
        },
        FPM {
            @Override
            public Number getValue(OperatingSystemMXBean bean) {
                return bean.getFreePhysicalMemorySize();
            }

        },
        FSS {
            @Override
            public Number getValue(OperatingSystemMXBean bean) {
                return  bean.getFreeSwapSpaceSize();
            }
        },
        LA {
            @Override
            public Number getValue(OperatingSystemMXBean bean) {
                return bean.getSystemLoadAverage();
            }
        },
        TPM {
            @Override
            public Number getValue(OperatingSystemMXBean bean) {
                return bean.getTotalPhysicalMemorySize();
            }
        },
        TSS {
            @Override
            public Number getValue(OperatingSystemMXBean bean) {
                return bean.getTotalSwapSpaceSize();
            }
        };
        public abstract Number getValue(OperatingSystemMXBean bean);
    };

    public String getName() {
        return "jmxsysteminfo";
    }

    public Map<String, Number> query() {
        com.sun.management.OperatingSystemMXBean systemBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Map<String, Number> retValues = new HashMap<String, Number>();
        for(OSBEAN value: OSBEAN.values()) {
            retValues.put(value.name(), value.getValue(systemBean));
        }
        return retValues;
    }
}
