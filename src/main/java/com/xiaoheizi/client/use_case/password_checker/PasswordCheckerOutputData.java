package com.xiaoheizi.client.use_case.password_checker;

public class PasswordCheckerOutputData {
    private final boolean isValid;

    public PasswordCheckerOutputData(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }
}
