package client.use_case.signup;


import client.entity.User;

public interface SignupDataAccessInterface {

    boolean existsByName(String username);

    void save(User user);
}
