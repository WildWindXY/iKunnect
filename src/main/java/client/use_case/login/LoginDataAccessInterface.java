package client.use_case.login;

import client.entity.User;

public interface LoginDataAccessInterface {
    boolean existsByName(String identifier);

    void save(User user);

    User get(String username);
}
