package com.xiaoheizi.client.use_case.high_contrast;

public interface HighContrastDataAccessInterface {

    int HIGH_CONTRAST = 0;

    void toggle(int index);

    HighContrastOutputData get(int index);

}
