package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private static long totalTime = 0;
    // region G1
    static Map<String, String> g1Stats = new HashMap<>();
    private static int youngCount = 0;
    private static int oldCount = 0;
    private static String g1Minor;
    private static String g1Major;
    private static long g1MinorTotalDuration = 0;
    private static long g1MajorTotalDuration = 0;
    // endregion
    // region Parallel Collector
    static Map<String, String> psStats = new HashMap<>();
    private static int scavengeCount = 0;
    private static int markSweepCount = 0;
    private static String psMinor;
    private static String psMajor;
    private static long psMinorTotalDuration = 0;
    private static long psMajorTotalDuration = 0;
    // endregion

    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();
        int size = 5 * 1000 * 1000;
        int loopCounter = 1000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");
        Benchmark mbean = new Benchmark(loopCounter);
        mbs.registerMBean(mbean, name);
        mbean.setSize(size);
        mbean.run();
        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);

        totalTime = (System.currentTimeMillis() - beginTime) / 1000;
        // region G1 Collector
        try {
            long g1MinorTimeSec = TimeUnit.MILLISECONDS.toSeconds(g1MinorTotalDuration);
            g1Stats.put("young", "action:" + g1Minor + ", total actions: " + String.valueOf(youngCount) +
                    ", minor total time (Sec):" + g1MinorTimeSec + ", action average time:" + g1MinorTotalDuration / youngCount + " ms");

            long g1MajorTimeSec = TimeUnit.MILLISECONDS.toSeconds(g1MajorTotalDuration);
            g1Stats.put("old", "action:" + g1Major + ", total actions: " + String.valueOf(oldCount) +
                    ", major total time (Sec):" + g1MajorTimeSec + ", action average time:" + g1MajorTotalDuration / oldCount + " ms");
            g1Stats.put("total time", String.valueOf(totalTime));
        } catch (ArithmeticException exception) {
            System.out.println("skip divide by zero");
        }
        // print stats
        System.out.println("Garbage First (G1)");
        System.out.println("Young Generation: " + g1Stats.get("young"));
        System.out.println("Old Generation: " + g1Stats.get("old"));
        System.out.println("Total time: " + g1Stats.get("total time"));
        // endregion

        // region Parallel Collector
        try {

            long psMinorTimeSec = TimeUnit.MILLISECONDS.toSeconds(psMinorTotalDuration);
            psStats.put("scavenge", "action:" + psMinor + ", total actions: " + String.valueOf(scavengeCount) +
                    ", minor total time (Sec):" + psMinorTimeSec + ", action average time:" + psMinorTotalDuration / scavengeCount + " ms");

            long psMajorTimeSec = TimeUnit.MILLISECONDS.toSeconds(psMajorTotalDuration);
            psStats.put("markSweep", "action:" + psMajor + ", total actions: " + String.valueOf(markSweepCount) +
                    ", major total time (Sec):" + psMajorTimeSec + ", action average time:" + psMajorTotalDuration / markSweepCount + " ms");
            psStats.put("total time", String.valueOf(totalTime));
        } catch (ArithmeticException exception) {
            System.out.println("skip divide by zero");
        }
        // Print stats
        System.out.println("Parallel Collector");
        System.out.println("PS Scavenge: " + psStats.get("scavenge"));
        System.out.println("PS MarkSweep: " + psStats.get("markSweep"));
        System.out.println("Total time: " + psStats.get("total time"));

        // endregion
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();
                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();
                    System.out.println("start" + startTime + " Name:" + gcName + " | action:" + gcAction + " | gcCause:" + gcCause + " | action time:" + duration + " ms");

                    // region get G1 stats
                    if (gcName.equals("G1 Young Generation")) {
                        youngCount++;
                        g1Minor = gcAction;
                        g1MinorTotalDuration = g1MajorTotalDuration + duration;
                    } else if (gcName.equals("G1 Old Generation")) {
                        oldCount++;
                        g1Major = gcAction;
                        g1MajorTotalDuration = g1MajorTotalDuration + duration;
                    }
                    // endregion

                    // region get Parallel Collector stats
                    if (gcName.equals("PS Scavenge")) {
                        scavengeCount++;
                        psMinor = gcAction;
                        psMinorTotalDuration = psMinorTotalDuration + duration;
                    } else if (gcName.equals("PS MarkSweep")) {
                        markSweepCount++;
                        psMajor = gcAction;
                        psMajorTotalDuration = psMajorTotalDuration + duration;
                    }
                    // endregion
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
