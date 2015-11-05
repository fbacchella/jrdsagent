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
        private final static ExecutorService executor;
        private final static Map<String, ISWbemRefreshableItem> cache = new HashMap<String, ISWbemRefreshableItem>();
        private final static ISWbemRefresher wbemRefresher;
        private final static ISWbemServices wbemServices;
        static {
            wbemRefresher = ClassFactory.createSWbemRefresher();
            ISWbemLocator wbemLocator = ClassFactory.createSWbemLocator();
            wbemServices = wbemLocator.connectServer(".", "Root\\CIMv2", "", "", "", "", 0, null);
            executor = Executors.newSingleThreadExecutor();
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

    //
    //    public static final String WMI_PROGID = "WbemScripting.SWbemLocator";
    //
    //    /** Holder */
    //    private static class Holder {               
    //        private final static ExecutorService executor;
    //        //private final static ActiveXComponent wmiconnect;
    //        //private final static Map<String, List<Variant>> cache = new HashMap<String, List<Variant>>();
    //        static {
    //            //ActiveXComponent wmi = new ActiveXComponent(WMI_PROGID);
    //            //Variant conRet = wmi.invoke("ConnectServer");
    //            //wmiconnect = new ActiveXComponent(conRet.toDispatch());
    //            executor = Executors.newSingleThreadExecutor();
    //        }
    //    }
    //
    public static<T> T query(WmiRequester<T> request) throws InterruptedException, ExecutionException {
        return Holder.executor.submit(request).get();
    }
    //
    //    public static List<Map<String, Object>> query(final String query, final String... fields) throws InterruptedException, ExecutionException {
    //        WmiRequester<List<Map<String, Object>>> requester = new WmiRequester<List<Map<String, Object>>>() {
    //            @Override
    //            public List<Map<String, Object>> call() throws Exception {
    //                return execQuery(query, fields);
    //            }
    //        };
    //        return Holder.executor.submit(requester).get();
    //    }
    //    
    public static List<Object> getFromClass(final String domClassName, final String... fields ) throws InterruptedException, ExecutionException{
        WmiRequester<List<Object>> requester = new WmiRequester<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                return this.get(domClassName + "=@", fields);
            }

        };
        return Holder.executor.submit(requester).get();
    }

    public static List<Object> getFromClassIndexed(final String domClassName, final String key, final String index, final String... fields ) throws InterruptedException, ExecutionException{
        WmiRequester<List<Object>> requester = new WmiRequester<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                return this.get(domClassName + "." + key + "=\"" + index + "\"", fields);
            }
        };
        return Holder.executor.submit(requester).get();
    }
    //
    //    public static List<Variant> getInstance(final String domClassName) throws InterruptedException, ExecutionException{
    //        WmiRequester<List<Variant>> requester = new WmiRequester<List<Variant>>() {
    //            @Override
    //            public List<Variant> call() throws Exception {
    //                return this.getInstances(domClassName);
    //            }
    //        };
    //        return Holder.executor.submit(requester).get();
    //    }
    //
    public static void terminate() {
        Holder.executor.shutdown();
        try {
            Holder.executor.awaitTermination(100, TimeUnit.MILLISECONDS);
            for(Runnable r: Holder.executor.shutdownNow()) {
                System.out.println(r);
            }
        } catch (InterruptedException e) {
        }
    }
    //
    //    protected List<Variant> getInstances(String domClassName) {
    //        if (Holder.cache.containsKey(domClassName)) {
    //            return Holder.cache.get(domClassName);
    //        } else {
    //            Variant vCollection = Holder.wmiconnect.invoke("InstancesOf", new Variant(domClassName), new Variant(0x20));
    //            EnumVariant enumVariant = new EnumVariant(vCollection.toDispatch());
    //            List<Variant> instances = new ArrayList<Variant>();
    //            while (enumVariant.hasMoreElements()) {
    //                instances.add(enumVariant.nextElement());
    //            }
    //            Holder.cache.put(domClassName, instances);
    //            return instances;
    //        }
    //    }
    //
    //    protected List<Map<String, Object>> execQuery(String query, String... fields) {
    //        Variant vCollection = Holder.wmiconnect.invoke("ExecQuery", new Variant(query));
    //
    //        EnumVariant enumVariant = new EnumVariant(vCollection.toDispatch());
    //
    //        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
    //
    //        while (enumVariant.hasMoreElements()) {
    //            Dispatch item = enumVariant.nextElement().toDispatch();
    //            Map<String, Object> values = new HashMap<>(fields.length);
    //            for(String field: fields) {
    //                Variant v = Dispatch.call(item, field);
    //                if(v != null) {
    //                    values.put(field, v.toJavaObject());
    //                }
    //            }
    //        }
    //        return results;
    //    }
    //
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
    //
}
