package com.xiaoheizi.client.entity;

import java.time.LocalDateTime;

public class CommonUserFactory implements UserFactory {


    @Override
    public User create(String name, String password, LocalDateTime ltd) {
        return new CommonUser(name, password, ltd);
    }
}
