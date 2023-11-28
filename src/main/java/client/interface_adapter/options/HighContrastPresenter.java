package client.interface_adapter.options;

import client.data_access.high_contrast.HighContrastState;
import client.interface_adapter.Main.MainViewModel;
import client.use_case.HighContrast.HighContrastOutputBoundary;
import client.use_case.HighContrast.HighContrastOutputData;

public class HighContrastPresenter implements HighContrastOutputBoundary {
    private final MainViewModel mainViewModel;
    public HighContrastPresenter(MainViewModel mainViewModel){
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void setHighContrast(HighContrastOutputData outputData) {
        HighContrastState highContrastState = mainViewModel.getOptionsState();
        highContrastState.setHighContrast(outputData.getHighContrast());
        mainViewModel.setOptionsState(highContrastState);
        mainViewModel.fireHighContrastPropertyChanged();
        System.out.println("OptionsPresenter HC " + outputData.getHighContrast());
    }

}
