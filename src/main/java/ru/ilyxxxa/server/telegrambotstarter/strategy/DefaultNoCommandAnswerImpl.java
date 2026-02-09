package ru.ilyxxxa.server.telegrambotstarter.strategy;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DefaultNoCommandAnswerImpl extends NoCommandAnswer {

    @Override
    public SendMessage answer(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("Введите команду, например /start")
                .build();
    }
}
