package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    static ExecutorService service;
    static ArrayList<Command> proc;


    public static void main(String[] args) {
        System.out.println("======! Start !=======");
        TimeReporter.startCounter("processor");
        proc = new ArrayList<>();
        proc.add(new Command(2240));
        proc.add(new Command(1140));
        proc.add(new Command(2640));
        proc.add(new Command(3140));


        for (int i = 0; i < 5; i++) {


            service = Executors.newFixedThreadPool(4);

         /*   for (Command cmd : proc) {
                cmd.runCommand();
            }*/
            for (Command cmd : proc) {
                service.submit(cmd);
            }
            service.shutdown();
            TimeReporter.startCounter("processor2");

            while (!service.isTerminated()) {
                System.out.print(".");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println();

            TimeReporter.stopCounter("processor2");
            TimeReporter.addSepar();
            System.out.println("*************************** " + (i + 1) + " ********************************");
        }
        TimeReporter.stopCounter("processor");

    }
}
