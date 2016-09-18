package com.company;


public class Command implements Runnable {

    long time;

    public Command(long time) {
        this.time = time;
    }

    public void runCommand() {
        try {

            System.out.println("start....."+this.toString());
            Thread.sleep(time);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        runCommand();
    }
}
