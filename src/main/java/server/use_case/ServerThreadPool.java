package server.use_case;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * The ServerThreadPool class provides a simple thread pool implementation for executing
 * asynchronous tasks on the server. It manages a collection of threads and supports graceful
 * shutdown of the thread pool.
 * <p>
 * This thread pool aims for the concurrent execution of unlimited number of core thread.
 */
public class ServerThreadPool {
    private static final ConcurrentLinkedDeque<Thread> threads = new ConcurrentLinkedDeque<>();
    private static volatile boolean shutdown = false;

    /**
     * Submits a task to the thread pool for execution.
     *
     * @param task The runnable task to be executed asynchronously.
     * @param name The name of the task
     * @throws IllegalStateException if the thread pool is already shutdown.
     */
    public static void submit(Runnable task, String name) {
        if (shutdown) {
            throw new IllegalStateException("Thread pool is shutdown");
        }
        Thread thread = new Thread(() -> {
            try {
                task.run();
            } finally {
                threads.remove(Thread.currentThread());
            }
        }, name);
        thread.start();
        threads.addLast(thread);
    }

    /**
     * Initiates a graceful shutdown of the thread pool.
     * This method interrupts all active threads and prevents new tasks from being submitted.
     */
    public static void shutdown() {
        shutdown = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static String getString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("List of active threads:\n");
        for (Thread thread : threads) {
            stringBuilder.append(thread.getName()).append("\n");
        }

        return stringBuilder.toString();
    }
}
