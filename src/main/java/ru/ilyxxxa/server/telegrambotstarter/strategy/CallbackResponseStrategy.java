package ru.ilyxxxa.server.telegrambotstarter.strategy;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackResponseStrategy {

    String id();
    EditMessageText callbackEditMessage(CallbackQuery callbackQuery);
}
