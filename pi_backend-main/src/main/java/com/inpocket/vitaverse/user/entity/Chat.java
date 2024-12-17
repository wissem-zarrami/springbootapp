package com.inpocket.vitaverse.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import org.springframework.data.annotation.Transient;


import java.util.List;

@Data
@Table(name="chats")
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int chatId;
    private String firstUserName;
    private String secondUserName;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> messageList;

    public Chat() {
    }

    public Chat(int chatId, String firstUserName, String secondUserName, List<Message> messageList) {
        this.chatId = chatId;
        this.firstUserName = firstUserName;
        this.secondUserName = secondUserName;
        this.messageList = messageList;
    }
}