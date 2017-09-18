package kov.improve;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Processor {

    private ExecutorService service;
    private int nThread;

    public Processor(int nThread) {
        this.nThread = nThread;
        this.service = Executors.newFixedThreadPool(nThread);
    }

    public void start() {
        for (int i = 1; i <= nThread; i++) {
            try {
                service.submit(new Thread(new ProcessorImpl()));
            } catch (Exception e) {
                break;
            }
        }
    }

    public void shutdown() {

        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
    }
}