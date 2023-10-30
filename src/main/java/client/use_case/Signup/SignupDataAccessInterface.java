package client.use_case.Signup;


import client.entity.User;

public interface SignupDataAccessInterface {

    boolean existsByName(String username);

    void save(User user);
}
