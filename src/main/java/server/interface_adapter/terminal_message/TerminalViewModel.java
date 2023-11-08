package server.interface_adapter.terminal_message;

import java.util.concurrent.LinkedBlockingQueue;

public class TerminalViewModel {
    private final LinkedBlockingQueue<String> messages = new LinkedBlockingQueue<>();

    /**
     * Adds a message to the queue of terminal messages.
     *
     * @param message The message to be added to the terminal messages queue.
     */
    public void put(String message) {
        messages.add(message);
    }

    /**
     * Retrieves and removes the next message from the queue. If the queue is empty,
     * this method blocks until a message is available.
     *
     * @return The next available message from the terminal messages queue.
     * @throws InterruptedException if the thread is interrupted while waiting for a message.
     */
    public String getMessage() throws InterruptedException {
        return messages.take();
    }
}
