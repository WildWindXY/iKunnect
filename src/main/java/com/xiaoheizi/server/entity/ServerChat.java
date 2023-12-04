package com.xiaoheizi.server.entity;

import java.util.List;

public interface ServerChat {
    int getChatId();

    List<Integer> getMessages();

    void addMessage(int messageId);
}
