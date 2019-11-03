package ru.otus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class ParallelGCStats {
    private static Map<String, String> psStats = new HashMap<>();
    private static long beginTime = System.currentTimeMillis();
    private static int scavengeCount = 0;
    private static int markSweepCount = 0;
    private static long minorTotalDuration = 0;
    private static long majorTotalDuration = 0;
    private static String minor;
    private static String major;

    static void collectParallelGCStats(String gcName, String gcAction, long duration) {
        if (gcName.equals("PS Scavenge")) {
            ParallelGCStats.scavengeCount++;
            ParallelGCStats.minor = gcAction;
            ParallelGCStats.minorTotalDuration = ParallelGCStats.minorTotalDuration + duration;
        } else if (gcName.equals("PS MarkSweep")) {
            ParallelGCStats.markSweepCount++;
            ParallelGCStats.major = gcAction;
            ParallelGCStats.majorTotalDuration = ParallelGCStats.majorTotalDuration + duration;
        }
    }

    static void setParallelGCStats() {
        long totalTime = (System.currentTimeMillis() - beginTime) / 1000;
        try {
            long psMinorTimeSec = TimeUnit.MILLISECONDS.toSeconds(minorTotalDuration);
            psStats.put("scavenge", "action:" + minor + ", total actions: " + String.valueOf(scavengeCount) +
                    ", minor total time (Sec):" + psMinorTimeSec + ", action average time:" + minorTotalDuration / scavengeCount + " ms");

            long psMajorTimeSec = TimeUnit.MILLISECONDS.toSeconds(majorTotalDuration);
            psStats.put("markSweep", "action:" + major + ", total actions: " + String.valueOf(markSweepCount) +
                    ", major total time (Sec):" + psMajorTimeSec + ", action average time:" + majorTotalDuration / ParallelGCStats.markSweepCount + " ms");

            psStats.put("total time", String.valueOf(totalTime));
        } catch (ArithmeticException exception) {
            System.out.println();
        }
    }

    static Map<String, String> getPsStats() {
        return psStats;
    }

    static void printStats() {
        System.out.println("Parallel Collector");
        System.out.println("PS Scavenge: " + psStats.get("scavenge"));
        System.out.println("PS MarkSweep: " + psStats.get("markSweep"));
        System.out.println("Total time: " + psStats.get("total time"));
    }
}
