package client.app;

import client.interface_adapter.add_friend.AddFriendPresenter;
import client.interface_adapter.main.MainController;
import client.interface_adapter.main.MainViewModel;
import client.interface_adapter.options.HighContrastPresenter;
import client.interface_adapter.receive_message.ReceiveMessagePresenter;
import client.interface_adapter.send_message.SendMessagePresenter;
import client.use_case.add_friend.AddFriendDataAccessInterface;
import client.use_case.add_friend.AddFriendInputBoundary;
import client.use_case.add_friend.AddFriendInteractor;
import client.use_case.add_friend.AddFriendOutputBoundary;
import client.use_case.high_contrast.HighContrastDataAccessInterface;
import client.use_case.high_contrast.HighContrastInputBoundary;
import client.use_case.high_contrast.HighContrastInteractor;
import client.use_case.high_contrast.HighContrastOutputBoundary;
import client.use_case.receive_message.ReceiveMessageDataAccessInterface;
import client.use_case.receive_message.ReceiveMessageInputBoundary;
import client.use_case.receive_message.ReceiveMessageInteractor;
import client.use_case.receive_message.ReceiveMessageOutputBoundary;
import client.use_case.send_message.SendMessageDataAccessInterface;
import client.use_case.send_message.SendMessageInputBoundary;
import client.use_case.send_message.SendMessageInteractor;
import client.use_case.send_message.SendMessageOutputBoundary;
import client.use_case.translate.TranslateDataAccessInterface;
import client.use_case.translate.TranslationInputBoundary;
import client.use_case.translate.TranslationInteractor;
import client.view.MainView;

public class MainUseCaseFactory {
    private MainUseCaseFactory() {
    }

    public static MainView create(String myUsername, SendMessageDataAccessInterface sendMessageDataAccessObject, ReceiveMessageDataAccessInterface receiveMessageDataAccessObject, TranslateDataAccessInterface translateDataAccessObject, HighContrastDataAccessInterface highContrastDataAccessObject, AddFriendDataAccessInterface addFriendDataAccessObject, MainViewModel mainViewModel) {
        MainController mainController = createMainUseCase(myUsername, sendMessageDataAccessObject, receiveMessageDataAccessObject, translateDataAccessObject, highContrastDataAccessObject, addFriendDataAccessObject, mainViewModel);
        return new MainView(mainController, mainViewModel, highContrastDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST));
    }

    private static MainController createMainUseCase(String myUsername, SendMessageDataAccessInterface sendMessageDataAccessObject, ReceiveMessageDataAccessInterface receiveMessageDataAccessObject, TranslateDataAccessInterface translateDataAccessObject, HighContrastDataAccessInterface highContrastDataAccessObject, AddFriendDataAccessInterface addFriendDataAccessObject, MainViewModel mainViewModel) {
        SendMessageOutputBoundary sendMessageOutputBoundary = new SendMessagePresenter(mainViewModel);
        ReceiveMessageOutputBoundary receiveMessageOutputBoundary = new ReceiveMessagePresenter(mainViewModel);
        HighContrastOutputBoundary highContrastOutputBoundary = new HighContrastPresenter(mainViewModel);
        AddFriendOutputBoundary addFriendOutputBoundary = new AddFriendPresenter(mainViewModel);
        SendMessageInputBoundary sendMessageInteractor = new SendMessageInteractor(sendMessageDataAccessObject, sendMessageOutputBoundary);
        ReceiveMessageInputBoundary receiveMessageInteractor = new ReceiveMessageInteractor(receiveMessageDataAccessObject, receiveMessageOutputBoundary);
        TranslationInputBoundary translationInteractor = new TranslationInteractor(translateDataAccessObject);
        HighContrastInputBoundary highContrastInteractor = new HighContrastInteractor(highContrastDataAccessObject, highContrastOutputBoundary);
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(addFriendDataAccessObject, addFriendOutputBoundary);
        return new MainController(myUsername, sendMessageInteractor, receiveMessageInteractor, translationInteractor, highContrastInteractor, addFriendInteractor);
    }
}