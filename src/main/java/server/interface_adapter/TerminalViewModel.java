package server.interface_adapter;

import java.util.concurrent.LinkedBlockingQueue;

public class TerminalViewModel {
    private final LinkedBlockingQueue<String> messages = new LinkedBlockingQueue<>();

    public void put(String message) {
        messages.add(message);
    }

    public String getMessage() throws InterruptedException {
        return messages.take();
    }
}
