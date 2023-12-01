package client.app;

import client.interface_adapter.add_friend.AddFriendPresenter;
import client.interface_adapter.channel_chats.ChatsRequestPresenter;
import client.interface_adapter.main.MainController;
import client.interface_adapter.main.MainViewModel;
import client.interface_adapter.receive_message.ReceiveMessagePresenter;
import client.interface_adapter.send_message.*;
import client.interface_adapter.options.HighContrastPresenter;
import client.use_case.add_friend.AddFriendDataAccessInterface;
import client.use_case.add_friend.AddFriendInputBoundary;
import client.use_case.add_friend.AddFriendInteractor;
import client.use_case.add_friend.AddFriendOutputBoundary;
import client.use_case.channel_chats.ChatsRequestDataAccessInterface;
import client.use_case.channel_chats.ChatsRequestInputBoundary;
import client.use_case.channel_chats.ChatsRequestInteractor;
import client.use_case.channel_chats.ChatsRequestOutputBoundary;
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
import client.use_case.high_contrast.HighContrastDataAccessInterface;
import client.use_case.high_contrast.HighContrastInputBoundary;
import client.use_case.high_contrast.HighContrastInteractor;
import client.use_case.high_contrast.HighContrastOutputBoundary;
import client.view.MainView;

public class MainUseCaseFactory {
    private MainUseCaseFactory() {}
    public static MainView create(String myUsername,
                                            SendMessageDataAccessInterface sendMessageDataAccessObject,
                                            ReceiveMessageDataAccessInterface receiveMessageDataAccessObject,
                                            TranslateDataAccessInterface translateDataAccessObject,
                                            HighContrastDataAccessInterface highContrastDataAccessObject,
                                            AddFriendDataAccessInterface addFriendDataAccessObject,
                                            ChatsRequestDataAccessInterface chatsRequestDataAccessObject,
                                            MainViewModel mainViewModel) {
        MainController mainController = createMainUseCase(myUsername, sendMessageDataAccessObject, receiveMessageDataAccessObject, translateDataAccessObject, highContrastDataAccessObject, addFriendDataAccessObject, chatsRequestDataAccessObject, mainViewModel.getCurrentChannel(), mainViewModel);
        return new MainView(mainController, mainViewModel, highContrastDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST));
    }

    private static MainController createMainUseCase(String myUsername,
                                                    SendMessageDataAccessInterface sendMessageDataAccessObject,
                                                    ReceiveMessageDataAccessInterface receiveMessageDataAccessObject,
                                                    TranslateDataAccessInterface translateDataAccessObject,
                                                    HighContrastDataAccessInterface highContrastDataAccessObject,
                                                    AddFriendDataAccessInterface addFriendDataAccessObject,
                                                    ChatsRequestDataAccessInterface chatsRequestDataAccessObject,
                                                    String currentChannel,
                                                    MainViewModel mainViewModel) {
        SendMessageOutputBoundary sendMessageOutputBoundary = new SendMessagePresenter(mainViewModel);
        ReceiveMessageOutputBoundary receiveMessageOutputBoundary = new ReceiveMessagePresenter(mainViewModel);
        HighContrastOutputBoundary highContrastOutputBoundary = new HighContrastPresenter(mainViewModel);
        AddFriendOutputBoundary addFriendOutputBoundary = new AddFriendPresenter(mainViewModel);
        ChatsRequestOutputBoundary chatsRequestOutputBoundary = new ChatsRequestPresenter(mainViewModel);
        SendMessageInputBoundary sendMessageInteractor = new SendMessageInteractor(sendMessageDataAccessObject, sendMessageOutputBoundary);
        ReceiveMessageInputBoundary receiveMessageInteractor = new ReceiveMessageInteractor(receiveMessageDataAccessObject, receiveMessageOutputBoundary);
        ChatsRequestInputBoundary chatsRequestInteractor = new ChatsRequestInteractor(chatsRequestDataAccessObject, chatsRequestOutputBoundary);
        TranslationInputBoundary translationInteractor = new TranslationInteractor(translateDataAccessObject);
        HighContrastInputBoundary highContrastInteractor = new HighContrastInteractor(highContrastDataAccessObject, highContrastOutputBoundary);
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(addFriendDataAccessObject, addFriendOutputBoundary);
        return new MainController(myUsername, sendMessageInteractor, receiveMessageInteractor, translationInteractor, highContrastInteractor, addFriendInteractor, chatsRequestInteractor, currentChannel);
    }
}