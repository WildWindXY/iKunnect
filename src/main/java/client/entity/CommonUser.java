package client.entity;

import java.time.LocalDateTime;

class CommonUser implements User {

    private final String name;
    private final String uuid;
    private final String password;
    private final LocalDateTime creationTime;


    CommonUser(String name, String password, String uuid, LocalDateTime creationTime) {
        this.name = name;
        this.uuid = uuid;
        this.password = password;
        this.creationTime = creationTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override public String getUUID(){
        return uuid;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}
