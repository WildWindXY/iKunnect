package client.entity;

import java.time.LocalDateTime;

public class CommonUserFactory implements UserFactory {


    @Override
    public User create(String name, String password, String uuid, LocalDateTime ltd) {
        return new CommonUser(name, password, uuid, ltd);
    }
}
