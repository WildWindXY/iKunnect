package com.xiaoheizi.client.interface_adapter.signup;

public class SignupState {
    private String username = "";
    private String usernameError = null;
    private String password = "";
    private String repeatPassword = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameAndClear() {
        String username = this.username;
        this.username = null;
        return username;
    }

    public String getPasswordAndClear() {
        String password = this.password;
        this.password = null;
        return password;
    }

    public String getRepeatPasswordAndClear() {
        String repeatPassword = this.repeatPassword;
        this.repeatPassword = null;
        return repeatPassword;
    }


    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
