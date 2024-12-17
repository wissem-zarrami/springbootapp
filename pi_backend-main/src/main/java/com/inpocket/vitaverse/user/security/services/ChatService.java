package com.inpocket.vitaverse.user.security.services;



import com.inpocket.vitaverse.user.entity.Chat;
import com.inpocket.vitaverse.user.entity.Message;
import com.inpocket.vitaverse.user.exception.ChatAlreadyExistException;
import com.inpocket.vitaverse.user.exception.ChatNotFoundException;
import com.inpocket.vitaverse.user.exception.NoChatExistsInTheRepository;

import java.util.HashSet;
import java.util.List;

import java.util.HashSet;
import java.util.List;

public interface ChatService {

    Chat addChat(Chat chat) throws ChatAlreadyExistException;

    List<Chat> findallchats() throws NoChatExistsInTheRepository;

    Chat getById(int id)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatBySecondUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserNameOrSecondUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserNameAndSecondUserName(String firstUserName, String secondUserName)  throws ChatNotFoundException;

    Chat addMessage(Message add, int chatId)  throws ChatNotFoundException;

    Message addMessage2(Message message);

    List<Message> getAllMessagesInChat(int chatId) throws NoChatExistsInTheRepository;
}