package ru.otus.gc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Collection of statistics for the collector G1
 */
class GarbageFirstGCStats {
    private static Map<String, String> g1Stats = new HashMap<>();
    private static long beginTime = System.currentTimeMillis();
    private static int youngCounter = 0;
    private static int oldCounter = 0;
    private static long minorTotalDuration = 0;
    private static long majorTotalDuration = 0;
    private static String minor;
    private static String major;

    static void collectGarbageFirstGCStats(String gcName, String gcAction, long duration) {
        if (gcName.equals("G1 Young Generation")) {
            GarbageFirstGCStats.youngCounter++;
            GarbageFirstGCStats.minor = gcAction;
            GarbageFirstGCStats.minorTotalDuration = GarbageFirstGCStats.minorTotalDuration + duration;
        } else if (gcName.equals("G1 Old Generation")) {
            GarbageFirstGCStats.oldCounter++;
            GarbageFirstGCStats.major = gcAction;
            GarbageFirstGCStats.majorTotalDuration = GarbageFirstGCStats.majorTotalDuration + duration;
        }
    }

    static void setG1Stats() {
        long totalTime = (System.currentTimeMillis() - beginTime) / 1000;
        try {
            long g1MinorTimeSec = TimeUnit.MILLISECONDS.toSeconds(minorTotalDuration);
            g1Stats.put("young", "action:" + minor + ", total actions: " + String.valueOf(youngCounter) +
                    ", minor total time (Sec):" + g1MinorTimeSec + ", action average time:" + minorTotalDuration / youngCounter + " ms");

            long g1MajorTimeSec = TimeUnit.MILLISECONDS.toSeconds(majorTotalDuration);
            g1Stats.put("old", "action:" + major + ", total actions: " + String.valueOf(oldCounter) +
                    ", major total time (Sec):" + g1MajorTimeSec + ", action average time:" + majorTotalDuration / oldCounter + " ms");

            g1Stats.put("total time", String.valueOf(totalTime));
        } catch (ArithmeticException exception) {
            System.out.print("");
        }
    }

    static Map<String, String> getG1Stats() {
        return g1Stats;
    }

    static void printStats() {
        System.out.println("Garbage First (G1)");
        System.out.println("Young Generation: " + g1Stats.get("young"));
        System.out.println("Old Generation: " + g1Stats.get("old"));
        System.out.println("Total time: " + g1Stats.get("total time"));
    }
}
