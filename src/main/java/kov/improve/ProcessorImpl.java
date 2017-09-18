package kov.improve;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ProcessorImpl implements Runnable{

    private static AtomicInteger supplierCounter = new AtomicInteger(0);
    private static AtomicInteger functionCounter = new AtomicInteger(0);
    private static AtomicInteger consumerCounter = new AtomicInteger(0);

    public static AtomicInteger getSupplierCounter() {
        return supplierCounter;
    }

    public static AtomicInteger getFunctionCounter() {
        return functionCounter;
    }

    public static AtomicInteger getConsumerCounter() {
        return consumerCounter;
    }

    public static void setSupplierCounter() {
        supplierCounter.set(0);
    }

    public static void setFunctionCounter() {
        functionCounter.set(0);
    }

    public static void setConsumerCounter() {
        consumerCounter.set(0);
    }

    public void run(){
        Double d = supplier.get();
        Long l = function.apply(d);
        consumer.accept(l);
    }

    Function<Double, Long> function = new Function<Double, Long>() {
        @Override
        public  Long apply(Double dbl) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            functionCounter.getAndIncrement();
            return Math.round(dbl);
        }
    };

    Supplier<Double> supplier = new Supplier<Double>() {
        @Override
        public Double get() {
            synchronized (ProcessorImpl.class){
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                }
                supplierCounter.getAndIncrement();
                return Math.random() * 100;}
        }
    };

    Consumer<Long> consumer = new Consumer<Long>() {
        @Override
        public void accept(Long lng) {
            synchronized (ProcessorImpl.class){
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                }
                consumerCounter.getAndIncrement();
            }
        }
    };
}
