package com.xiaoheizi.client.use_case.login;

public interface LoginOutputBoundary {
    void prepareSuccessView(LoginOutputData user);

    void prepareFailView(String error);

    void prepareSignupView();
}
