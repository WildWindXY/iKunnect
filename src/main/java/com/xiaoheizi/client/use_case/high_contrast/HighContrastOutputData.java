package com.xiaoheizi.client.use_case.high_contrast;

public class HighContrastOutputData {
    private final boolean highContrast;

    public HighContrastOutputData(boolean highContrast) {
        this.highContrast = highContrast;
    }

    public boolean getHighContrast() {
        return highContrast;
    }
}
