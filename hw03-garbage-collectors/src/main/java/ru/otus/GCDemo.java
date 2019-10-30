package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/*
О формате логов
http://openjdk.java.net/jeps/158
-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
*/

/*
1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
    -XX:GCPauseIntervalMills=
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec
4)
-Xms2048m
-Xmx2048m
    time: 81 sec
5)
-Xms5120m
-Xmx5120m
    time: 80 sec
5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)
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

                    GarbageFirstGCStats.collectGarbageFirstGCStats(gcName, gcAction, duration);
                    ParallelGCStats.collectParallelGCStats(gcName, gcAction, duration);
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
