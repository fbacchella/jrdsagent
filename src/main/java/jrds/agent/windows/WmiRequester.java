package jrds.agent.windows;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com4j.ComException;
import com4j.typelibs.wmi.ClassFactory;
import com4j.typelibs.wmi.ISWbemLocator;
import com4j.typelibs.wmi.ISWbemRefreshableItem;
import com4j.typelibs.wmi.ISWbemRefresher;
import com4j.typelibs.wmi.ISWbemServices;

public abstract class WmiRequester {

    private final static Runnable refresher = new Runnable() {
        private long lastUpdate = 0;
        @Override
        public void run() {
            if(cache.size() <= 0) {
            }
            Date now = new Date();
            // Only one refresh / second
            if(now.getTime() - lastUpdate > 1000) {
                wbemRefresher.refresh(0);
                lastUpdate = now.getTime();
            }
        }
    };

    private final static ExecutorService executor = Executors.newSingleThreadExecutor();
    private final static Map<String, ISWbemRefreshableItem> cache = new HashMap<String, ISWbemRefreshableItem>();
    private final static ISWbemRefresher wbemRefresher;
    private final static ISWbemServices wbemServices;
    static {
        try {
            wbemRefresher = ClassFactory.createSWbemRefresher();
            ISWbemLocator wbemLocator = ClassFactory.createSWbemLocator();
            wbemServices = wbemLocator.connectServer(".", "Root\\CIMv2", "", "", "", "", 0, null);
        } catch (ComException e) {
            throw new RuntimeException(e.getMessage());
        }
    }        

    final synchronized static ISWbemRefreshableItem getItem(String name) {
        return getItemIndexed(name, "", "@");
    }

    final synchronized static ISWbemRefreshableItem getItemIndexed(String name, String key, String index) {
        String query = buildQuery(name, key, index);
        return getItemByQuery(query);
    }

    final synchronized static ISWbemRefreshableItem getItemByQuery(String query) {
        try {
            ISWbemRefreshableItem item;
            if(! cache.containsKey(query)) {
                item = wbemRefresher.add(wbemServices, query, 0, null);
                cache.put(query, item);
            } else {
                item = cache.get(query);
            }
            return item;
        } catch (com4j.ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ComException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static void refresh() {
        try {
            executor.submit(refresher).get();
        } catch (com4j.ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ComException e) {
            throw new RuntimeException(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("can't refresh wmi objects", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("can't refresh wmi objects", e);
        }

    }

    static Object[] getFromClass(String name, String... fields ) {
        return getFromClassIndexed(name, "", "@", fields);
    }

    static Object[] getFromClassIndexed(String name, String key, String index, String... fields ) {
        try {
            String resolvedName = buildQuery(name, key, index);
            return executor.submit(new WmiGetFields(resolvedName, fields)).get();
        } catch (com4j.ExecutionException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ComException e) {
            throw new RuntimeException(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("can't read WMI object " + name, e);
        }
    }

    static void terminate() {
        executor.shutdown();
        try {
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
            executor.shutdownNow();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static String buildQuery(String name, String key, String index) {
        String newIndex = key.isEmpty() ? "@" : '"' + index + '"';
        String resolvedName = name + (key.isEmpty() ? "" : "." + key) + "=" + newIndex;
        return resolvedName;
    }

}
