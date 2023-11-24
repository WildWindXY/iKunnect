package client.interface_adapter.options;

import client.data_access.options.OptionsState;
import client.interface_adapter.main.MainViewModel;
import client.use_case.options.OptionsOutputBoundary;
import client.use_case.options.OptionsOutputData;

public class OptionsPresenter implements OptionsOutputBoundary {
    private final MainViewModel mainViewModel;
    public OptionsPresenter(MainViewModel mainViewModel){
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void setHighContrast(OptionsOutputData outputData) {
        OptionsState optionsState = mainViewModel.getOptionsState();
        optionsState.setHighContrast(outputData.getHighContrast());
        mainViewModel.setOptionsState(optionsState);
        mainViewModel.fireOptionsPropertyChanged();
        System.out.println("OptionsPresenter HC " + outputData.getHighContrast());
    }

}
