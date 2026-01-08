package ru.ilyxxxa.server.telegrambotstarter.strategy;

import org.telegram.telegrambots.meta.api.objects.Message;


public abstract class NoCommandAnswer implements AnswerStrategy {

    @Override
    public String command() {
        return null;
    }

    @Override
    public abstract String answer(Message message);
}
