package ru.otus.atm_department;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;


/**
 * Getting statistics from garbage collectors
 * <br>
 * GarbageFirst: -XX:+UseG1GC<br>
 * ParallelGC:   -XX:+UseParallelGC<br>
 * <br>
 * actions: - the total number of operations of this type for the entire program cycle<br>
 * minor time - how many seconds were spent on this type of assembly<br>
 * action average time - average time of one assembly of this type<br>
 * total time - the total time the collectors work
 */
public class GCDemo {
    private static String gcName;
    private static String gcAction;

    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        int size = 3_000_000;
        int loopCounter = 1000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");
        Benchmark mbean = new Benchmark(loopCounter);
        mbs.registerMBean(mbean, name);
        mbean.setSize(size);
        mbean.run();
        if (gcName.contains("G1")) {
            GarbageFirstGCStats.setG1Stats();
            GarbageFirstGCStats.printStats();
        } else {
            ParallelGCStats.setParallelGCStats();
            ParallelGCStats.printStats();
        }
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    gcName = info.getGcName();
                    gcAction = info.getGcAction();
                    long duration = info.getGcInfo().getDuration();
                    //System.out.println("Start: " + info.getGcInfo().getStartTime() + " | Name:" + gcName + " | action:" + gcAction + " | gcCause:" + info.getGcCause() + " | action time:" + duration + " ms");
                    if (gcName.contains("G1")) GarbageFirstGCStats.collectGarbageFirstGCStats(gcName, gcAction, duration);
                    else ParallelGCStats.collectParallelGCStats(gcName, gcAction, duration);
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
