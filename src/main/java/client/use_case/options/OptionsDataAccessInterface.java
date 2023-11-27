package client.use_case.options;

public interface OptionsDataAccessInterface {

    int HIGH_CONTRAST = 0;

    void toggle(int index);
    OptionsOutputData get(int index);

    void set(int index, int value);

}
