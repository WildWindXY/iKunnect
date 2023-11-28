package client.use_case.HighContrast;

public class HighContrastOutputData {
    private final boolean highContrast;

    public HighContrastOutputData(int hc){
        highContrast = hc == 1;
    }

    public boolean getHighContrast() {
        return highContrast;
    }
}
