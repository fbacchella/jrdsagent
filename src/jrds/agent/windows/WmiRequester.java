package jrds.agent.windows;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com4j.typelibs.wmi.ClassFactory;
import com4j.typelibs.wmi.ISWbemLocator;
import com4j.typelibs.wmi.ISWbemObject;
import com4j.typelibs.wmi.ISWbemRefreshableItem;
import com4j.typelibs.wmi.ISWbemRefresher;
import com4j.typelibs.wmi.ISWbemServices;

public abstract class WmiRequester<RESULT> implements Callable<RESULT> {
    private final static Callable<Object> refresher = new Callable<Object>() {
        @Override
        public Object call() throws Exception {
            Holder.wbemRefresher.refresh(0);
            return null;
        }
    };

    private static class Holder {
        private final static ExecutorService executor = Executors.newSingleThreadExecutor();
        private final static Map<String, ISWbemRefreshableItem> cache = new HashMap<String, ISWbemRefreshableItem>();
        private final static ISWbemRefresher wbemRefresher = ClassFactory.createSWbemRefresher();
        private final static ISWbemServices wbemServices;
        static {
            ISWbemLocator wbemLocator = ClassFactory.createSWbemLocator();
            wbemServices = wbemLocator.connectServer(".", "Root\\CIMv2", "", "", "", "", 0, null);
        }        

        private final synchronized static ISWbemRefreshableItem getItem(String name) {
            ISWbemRefreshableItem item;
            if(! cache.containsKey(name)) {
                item = wbemRefresher.add(wbemServices, name, 0, null);
                cache.put(name, item);
            } else {
                item = cache.get(name);
            }
            return item;
        }
    }

    public static void refresh() throws InterruptedException, ExecutionException {
        Holder.executor.submit(refresher).get();
    }

    public static<T> T query(WmiRequester<T> request) throws InterruptedException, ExecutionException {
        return Holder.executor.submit(request).get();
    }

    public static List<Object> getFromClass(final String domClassName, final String... fields ) throws InterruptedException, ExecutionException{
        WmiRequester<List<Object>> requester = new WmiRequester<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                return get(domClassName + "=@", fields);
            }
        };
        return Holder.executor.submit(requester).get();
    }

    public static List<Object> getFromClassIndexed(final String domClassName, final String key, final String index, final String... fields ) throws InterruptedException, ExecutionException{
        WmiRequester<List<Object>> requester = new WmiRequester<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                return get(domClassName + "." + key + "=\"" + index + "\"", fields);
            }
        };
        return Holder.executor.submit(requester).get();
    }

    public static void terminate() {
        Holder.executor.shutdown();
        try {
            Holder.executor.awaitTermination(100, TimeUnit.MILLISECONDS);
            Holder.executor.shutdownNow();
        } catch (InterruptedException e) {
            // don't care
        }
    }

    protected List<Object> get(String name, String... fields) {
        ISWbemRefreshableItem item = Holder.getItem(name);
        Holder.wbemRefresher.refresh(0);

        ISWbemObject object = item.object();
        Object[] values = new Object[fields.length];

        for(int i = 0; i < fields.length; i++) {
            values[i] = object.properties_().item(fields[i] , 0).value();
        }
        return Arrays.asList(values);
    }

}
