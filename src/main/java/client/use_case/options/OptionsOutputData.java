package client.use_case.options;

public class OptionsOutputData {
    private final boolean highContrast;

    public OptionsOutputData(int hc){
        highContrast = hc == 1;
    }

    public boolean getHighContrast() {
        return highContrast;
    }
}
