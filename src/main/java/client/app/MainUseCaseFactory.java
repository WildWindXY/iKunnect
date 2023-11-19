package client.app;

import client.interface_adapter.Main.MainController;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.ReceiveMessage.ReceiveMessagePresenter;
import client.interface_adapter.SendMessage.SendMessagePresenter;
import client.use_case.ReceiveMessage.ReceiveMessageDataAccessInterface;
import client.use_case.ReceiveMessage.ReceiveMessageInputBoundary;
import client.use_case.ReceiveMessage.ReceiveMessageInteractor;
import client.use_case.ReceiveMessage.ReceiveMessageOutputBoundary;
import client.use_case.SendMessage.SendMessageDataAccessInterface;
import client.use_case.SendMessage.SendMessageInputBoundary;
import client.use_case.SendMessage.SendMessageInteractor;
import client.use_case.SendMessage.SendMessageOutputBoundary;
import client.use_case.Translate.TranslateDataAccessInterface;
import client.use_case.Translate.TranslationInputBoundary;
import client.use_case.Translate.TranslationInteractor;
import client.view.MainView;

public class MainUseCaseFactory {
    private MainUseCaseFactory() {}
    public static MainView create(String myUsername,
                                            SendMessageDataAccessInterface sendMessageDataAccessObject,
                                            ReceiveMessageDataAccessInterface receiveMessageDataAccessObject,
                                            TranslateDataAccessInterface translateDataAccessObject,
                                            MainViewModel mainViewModel) {
        MainController mainController = createMainUseCaser(myUsername, sendMessageDataAccessObject, receiveMessageDataAccessObject, translateDataAccessObject, mainViewModel);
        MainView mainView = new MainView(mainController, mainViewModel);
        return mainView;
    }

    private static MainController createMainUseCaser(String myUsername,
                                                     SendMessageDataAccessInterface sendMessageDataAccessObject,
                                                     ReceiveMessageDataAccessInterface receiveMessageDataAccessObject,
                                                     TranslateDataAccessInterface translateDataAccessObject,
                                                     MainViewModel mainViewModel) {
        SendMessageOutputBoundary sendMessageOutputBoundary = new SendMessagePresenter(mainViewModel);
        ReceiveMessageOutputBoundary receiveMessageOutputBoundary = new ReceiveMessagePresenter(mainViewModel);
        SendMessageInputBoundary sendMessageInteractor = new SendMessageInteractor(sendMessageDataAccessObject, sendMessageOutputBoundary);
        ReceiveMessageInputBoundary receiveMessageInteractor = new ReceiveMessageInteractor(receiveMessageDataAccessObject, receiveMessageOutputBoundary);
        TranslationInputBoundary translationInteractor = new TranslationInteractor(translateDataAccessObject);
        return new MainController(myUsername, sendMessageInteractor, receiveMessageInteractor, translationInteractor);
    }
}