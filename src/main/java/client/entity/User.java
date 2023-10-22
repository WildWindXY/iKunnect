package client.entity;

import java.time.LocalDateTime;

public interface User {

    String getName();

    String getUUID();

    String getPassword();

    LocalDateTime getCreationTime();
}