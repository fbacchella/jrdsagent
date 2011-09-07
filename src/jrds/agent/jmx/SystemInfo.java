/**
 * 
 */
package jrds.agent.jmx;

import java.lang.management.ManagementFactory;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import com.sun.management.OperatingSystemMXBean;

import jrds.agent.LProbe;

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

    public String getName() throws RemoteException {
        return "jmxsysteminfo";
    }

    public Map<String, Number> query() throws RemoteException {
        com.sun.management.OperatingSystemMXBean systemBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Map<String, Number> retValues = new HashMap<String, Number>();
        for(OSBEAN value: OSBEAN.values()) {
            retValues.put(value.name(), value.getValue(systemBean));
        }
        return retValues;
    }
}
