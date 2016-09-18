package com.company;

import java.io.*;
import java.util.*;

public class TimeReporter {

    private static Set<Map<String, Object>> counters;
    private static String filename;

    static {
        counters = new HashSet<>();
        System.out.println("Reporter...DONE");
        System.out.println("List.......DONE");
        filename = "c:\\report.log";
    }

    public static void setFileReportName(String newname) {
        filename = newname;
    }

    public static synchronized void startCounter(String name) {

        Map<String, Object> m = new HashMap<>();
        for (Map m$ : counters) {
            if (m$.containsValue(name)) m = m$;
        }
        m.put("name", name);
        m.put("time", 0);
        m.put("start", System.currentTimeMillis());
        System.out.println("start.......... " + name);
        counters.add(m);
    }

    public static synchronized void log(String line) {
        File report = null;

        try {
            report = new File(filename);
            if (!report.exists()) {
                report.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (report != null) {
            try (FileWriter fw = new FileWriter(report, true)) {

                fw.append(line);
                fw.append('\n');


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void stopCounter(String name) {

        for (Map<String, Object> m : counters) {
            if (m.containsValue(name)) {
                double time = ((double) (System.currentTimeMillis() - Long.valueOf((m.get("start").toString())))) / 1000;
                m.put("time", time);

                StringBuffer str = new StringBuffer();
                str.append("[");
                str.append(Calendar.getInstance().getTime());
                str.append("] - ");
                str.append("[     ");
                str.append(m.get("name").toString());
                str.append("\t]:\t");
                str.append(m.get("time").toString());
                str.append("s");
                log(str.toString());
                counters.remove(m);

                System.out.println("stop.......... " + name);

                return;
            }

        }
        System.out.println("Counter " + name + " doesn't exist");


    }

    public static void addSepar() {
        log("*********************************************************");
    }


    public static String getLog() {
        StringBuffer str = new StringBuffer();

        for (Map<String, Object> m : counters) {
            str.append(" ---------- Counter (");
            str.append(m.get("name").toString());
            str.append("): time { ");
            str.append(m.get("time").toString());
            str.append("s } ----------");
            str.append("\n");
        }

        return str.toString();

    }

}
