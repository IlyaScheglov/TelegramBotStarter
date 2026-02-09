package ru.ilyxxxa.server.telegrambotstarter.strategy;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DefaultCallbackResponseImpl implements CallbackResponseStrategy {

    @Override
    public String id() {
        return null;
    }

    @Override
    public EditMessageText callbackEditMessage(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        return EditMessageText.builder()
                .chatId(message.getChatId())
                .messageId(message.getMessageId())
                .text("Недоработка разработчика")
                .build();
    }
}
