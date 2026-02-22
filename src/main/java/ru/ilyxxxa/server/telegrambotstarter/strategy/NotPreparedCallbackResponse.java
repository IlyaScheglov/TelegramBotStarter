package ru.ilyxxxa.server.telegrambotstarter.strategy;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public abstract class NotPreparedCallbackResponse implements CallbackResponseStrategy {

    @Override
    public String id() {
        return null;
    }

    @Override
    public abstract EditMessageText callbackEditMessage(CallbackQuery callbackQuery);
}
