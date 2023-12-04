package com.xiaoheizi.client.use_case.signup;

public class SignupOutputData {

    private final String username;
    private final int userId;
    private String creationTime;

    public SignupOutputData(String username, String creationTime, int userId) {
        this.username = username;
        this.creationTime = creationTime;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public int getUserId() {
        return userId;
    }

}
