package client.data_access.user_data;

import client.entity.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDataAccessObject {//TODO: What is this class used for?

    private final Map<String, User> users = new HashMap<>();

}
