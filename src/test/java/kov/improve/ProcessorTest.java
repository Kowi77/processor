package kov.improve;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ProcessorTest {

    private static final Logger log = LogManager.getLogger(ProcessorTest.class);

    private static Processor processor;

    private static int nThread;

    @BeforeClass
    public static void setUp(){
        nThread = 100;
        processor = new Processor(nThread);
    }

    @Test
    public void testStart() throws Exception {
        processor.start();
        Thread.sleep(nThread * 120);
        Assert.assertEquals(nThread, ProcessorImpl.getSupplierCounter().get());
        Assert.assertEquals(nThread, ProcessorImpl.getFunctionCounter().get());
        Assert.assertEquals(nThread, ProcessorImpl.getConsumerCounter().get());
        log.info("Process with {} threads successfully finished", nThread);
    }

    @Test
    public void testShutdown() throws Exception {

        Thread myThread = new Thread(new Runnable()
        {
            public void run()
            {
                try {
                    Thread.sleep(5 + (long) Math.random()*15);
                } catch (InterruptedException e) {
                }
                processor.shutdown();
            }
        });
        myThread.setDaemon(true);
        myThread.start();

        processor.start();
        Thread.sleep(nThread * 50);
        Assert.assertEquals(ProcessorImpl.getSupplierCounter().get(), ProcessorImpl.getConsumerCounter().get());
        Assert.assertEquals(ProcessorImpl.getSupplierCounter().get(), ProcessorImpl.getFunctionCounter().get());
        log.info("Process with {} threads before shutdown successfully finished", ProcessorImpl.getSupplierCounter().get());
        myThread.interrupt();
        Thread.sleep(1);
    }
}
