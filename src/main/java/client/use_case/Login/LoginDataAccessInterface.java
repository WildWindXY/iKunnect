package client.use_case.Login;

import client.entity.User;

public interface LoginDataAccessInterface {
    boolean existsByName(String identifier);

    void save(User user);

    User get(String username);
}
