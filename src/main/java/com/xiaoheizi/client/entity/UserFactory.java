package com.xiaoheizi.client.entity;

import java.time.LocalDateTime;

public interface UserFactory {
    User create(String name, String password, LocalDateTime ltd);
}
