package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class App {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService single = Executors.newSingleThreadExecutor();
        ExecutorService fixed = Executors.newFixedThreadPool(2);
        ExecutorService cached = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(10);

        Runnable runnable = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Runnable Call");
        };

        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Callable call");
            int i = 1 / 0;
            return "Callable Return";
        };

        Future<String> fCall = single.submit(callable);
        if (fCall.isDone())
            System.out.println(fCall.get());

        single.execute(runnable);
        single.shutdown();

        for (int i = 0; i < 5; i++) {
            fixed.submit(runnable);
            fixed.submit(callable);
        }
        fixed.shutdown();

        List<Callable<String>> callables = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            callables.add(callable);
        }

        cached.invokeAll(callables);
        cached.shutdown();
        cached.shutdownNow();

        scheduled.schedule(runnable, 5, TimeUnit.SECONDS);
        scheduled.shutdown();
    }
}
